(defproject svntagsdiff "0.1.0"
  :description "Creates a directory with added or modified files and a delete script with deleted files between specified revisions or tags"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [me.raynes/conch "0.8.0"]
                 [me.raynes/fs "1.4.6"]]
  :main svntagsdiff.core
  :aot :all
  :javac-options ["-target" "1.7" "-source" "1.7"])
