(ns svntagsdiff.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.tools.cli :as cli]
            [clojure.xml :as xml]
            [me.raynes.conch :as sh]
            [me.raynes.fs.compression :as fszip])
  (:import (java.io ByteArrayInputStream OutputStream)
           (java.net URLDecoder))
  (:gen-class))

(set! *warn-on-reflection* true)

(sh/programs svn)

(def cli-options
  [["-d" "--delete-script FILE" "The output file that collects remove statements"]
   ["-f" "--config-file FILE" "Optional base configuration file in edn format; command line options will overwrite this config file values"]
   ["-h" "--help" "Shows this help message"]
   ["-o" "--output-file (DIR | FILE)" "Alternative name for the output directory or file. If name ends with '-' tags or revisions range will be appended to the name. Extension .zip will be appended if -z option is present"]
   ["-p" "--password PASS" "Password for svn server"]
   ["-r" "--revisions-range REV_OLD:REV_NEW" "revisions range like in svn diff command; REV_NEW can be empty, HEAD will be used"]
   ["-t" "--tags-range TAG_OLD:TAG_NEW" "Tags range like revisions range for tags; TAG_NEW can be empty, HEAD will be used"]
   ["-u" "--username USER" "Username for svn server"]
   ["-v" "--verbose" "Verbosity level; may be specified multiple times" :assoc-fn (fn [m k _] (update-in m [k] (fnil inc 0)))]
   ["-z" "--zip" "Optional zip flag; will write to zip file instead of filesystem"]])

(defn usage [options-summary]
  (str/join
     \newline
     ["Creates a directory with added or modified files and a delete script with deleted files between specified revisions or tags"
      "" "Usage: svntagsdiff [options] path [tags-path]"
      "" "Options: " options-summary
      "" "Path: path to svn repository location target of diff"
      "" "Tags-path: path to svn repository location that is parent of tags; where to get a tag revision"]))

(defn error-message [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn tags-map [{:keys [tags-path username password]}]
  (into {}
        (map (fn [line]
               (-> line str/triml (str/split #"\s+")
                   ((juxt (comp #(str/replace % #"/$" "") last) first))))
             (rest
               (svn "ls" "-v" tags-path
                    "--username" username "--password" password
                    "--non-interactive" "--trust-server-cert"
                    {:seq true})))))

(defn tags->revisions [_ options tags]
  (let [tag->rev (tags-map options)]
    (->> tags (#(str/split % #":")) (map tag->rev) (str/join ":"))))

(defn resolve-command-line [command-line]
  (if-let [config-file (-> command-line :options :config-file)]
    (let [config (->> config-file io/reader slurp edn/read-string)]
      (-> config
          (update-in [:options] merge (:options command-line))
          (update-in [:arguments] #(vec (or (seq %2) (seq %1))) (:arguments command-line))))
    command-line))

(defn diff [{:keys [path path2 username password revisions-range verbose]}]
  (let [revs (str/split revisions-range #":")
        old-path (str path "@" (first revs))
        new-path (str (or path2 path) "@" (second revs))]
   #_(if verbose (println "old-path: " old-path " new-path: " new-path))
   (for [p (-> ^String (svn "diff" "--summarize" "--xml" old-path new-path
                            "--username" username "--password" password
                            "--non-interactive" "--trust-server-cert")
               .getBytes ByteArrayInputStream. xml/parse
               :content first :content)
         :when (= :path (:tag p))
         :let [url (-> p :content first (URLDecoder/decode "UTF-8"))
               url (if path2 (str path2 (subs url (count path))))
               ret (assoc (dissoc (:attrs p) :props) :url url) ]]
     (do #_(if verbose (println "diff-element: " ret))
         ret))))

(defn output-name [{:keys [path output-file revisions-range tags-range]}]
  (if (or (str/blank? output-file) (= \- (last output-file)))
    (let [name (if (str/blank? output-file)
                 (-> path
                     (str/replace #"([^/]*/)*" "")
                     (str "-"))
                 output-file)
          range (if tags-range
                  (str/replace tags-range #":" "-to-")
                  (str/replace revisions-range #":" "-to-"))]
      (str name range))
    (str/replace output-file #"\.zip$" "")))

(defn local-path [{:keys [path output-name]} url]
  (str output-name (subs url (count path))))

(defn export [{:keys [username password revisions-range verbose] :as options} url]
  (let [revision (or (second (str/split revisions-range #":")) "HEAD")
        file-path (local-path options url)]
    (if verbose (println "exporting: " url "->" file-path " with revision:" revision))
    [file-path (svn "cat" "-r" revision url
                    "--username" username "--password" password
                    "--non-interactive" "--trust-server-cert"
                    {:binary true})]))

(defn changed-files [options differences]
  (for [elem differences
        :let [{:keys [kind item url]} elem]
        :when (and (= "file" kind) (or (= "added" item) (= "modified" item)))]
    (export options url)))

(defn delete-script [{:keys [delete-script output-name] :as options} differences]
  [(if (str/blank? delete-script)
     (str output-name "-delete-script.sh")
     delete-script)
   (str/join
     \newline
     (concat
       (for [elem differences
             :let [{:keys [kind item url]} elem]
             :when (and (= "file" kind) (= "deleted" item))
             :let [file-path (local-path options url)]]
         (str "rm " file-path))
       (for [elem differences
             :let [{:keys [kind item url]} elem]
             :when (and (= "dir" kind) (= "deleted" item))
             :let [dir-path (local-path options url)]]
         (str "rm -fR " dir-path))))])

(defn svntagsdiff [{:keys [options arguments] :as command-line}]
  (let [{:keys [revisions-range tags-range zip]} options
        new-options (assoc options
                      :path (-> arguments first (str/replace #"/*$" ""))
                      :path2 (when (= 3 (count arguments)) (second arguments))
                      :tags-path (if (= 3 (count arguments)) (nth arguments 2) (second arguments)))
        new-options (if (and tags-range (not revisions-range))
                           (update-in new-options [:revisions-range]
                                      tags->revisions new-options tags-range)
                           (dissoc new-options :tags-range))
        new-options (assoc new-options
                      :output-name (output-name new-options))
        output-file (-> new-options :output-name
                        (#(if zip (str % ".zip") %)))
        new-options (assoc new-options :output-file output-file)
        differences (diff new-options)
        files (conj (changed-files new-options differences)
                    (delete-script new-options differences))]
    (if zip
      (fszip/zip output-file files)
      (doseq [[filename content] files]
        (io/make-parents filename)
        (with-open [out (io/output-stream filename)]
          (if (string? content)
            (doto (java.io.PrintStream. out true)
              (.print content))
            (.write out ^bytes content)))))))

(defn -main [& args]
  (let [command-line (-> args (cli/parse-opts cli-options) resolve-command-line)]
    (let [{:keys [options arguments errors summary]} command-line
          {:keys [revisions-range tags-range help]} options]
      (cond
        help (exit 0 (usage summary))
        (not (or (and revisions-range (#{1 2} (count arguments)))
                 (and tags-range (#{2 3} (count arguments)))))
        (exit 1 (usage summary))
        errors (exit 1 (error-message errors))))
    (svntagsdiff command-line))
  (exit 0 "Done."))
