package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;

public class MakeSerializable {

	public static void main(String[] args) throws IOException {
		File directory = new File("./target/gen-java-src/");
		if (!directory.isDirectory()) {
			System.out.println("Il path non corrisponde ad alcuna directory");
			System.exit(1);
		}

		elaboraFiles(directory);
	}

	private static void elaboraFiles(File directory) throws IOException {
		File[] listaFiles = directory.listFiles();
		for (File file : listaFiles) {
			if (file.isDirectory()) {
				elaboraFiles(file);
			} else {
				System.out.println(file.getPath());
				String fileExtension = FilenameUtils.getExtension(file.getName());
				if (fileExtension.equals("java")) {
					file.renameTo(new File(file.getAbsolutePath() + ".old"));

					File file2 = new File(file.getAbsolutePath() + ".old");
					FileReader f = new FileReader(file2);
					Scanner fIN = new Scanner(f);
					FileWriter f2 = new FileWriter(file.getAbsolutePath());
					PrintWriter fOUT = new PrintWriter(f2);

					while (fIN.hasNextLine()) {
						String line = fIN.nextLine();
						if (line.startsWith("public class")) {
							if(!line.contains("implements")) {
								int idx = line.indexOf("{");
								line = new StringBuilder(line).insert(idx, " implements java.io.Serializable ").toString();
							} else if(line.contains("implements") && !line.contains("Serializable")) {
								int idx = line.indexOf("{");
								line = new StringBuilder(line).insert(idx, ", java.io.Serializable ").toString();
							}
						}
						fOUT.println(line);
					}
					fOUT.flush();
					fOUT.close();
					f2.close();
					fIN.close();
					f.close();

					if (file2.exists())
						file2.delete();
				}
			}
		}
	}

}
