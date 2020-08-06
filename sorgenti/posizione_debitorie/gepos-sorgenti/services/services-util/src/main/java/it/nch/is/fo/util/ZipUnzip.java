package it.nch.is.fo.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUnzip {
	public static void main(String[] args) {
		testZip();
	}

	private static void testZip() {
		byte[] toZip = read("./tozip.txt");
		System.out.println("[ZipUnzip::testZip] original dim = " + toZip.length);
		byte[] zipped = zip(toZip);
		System.out.println("[ZipUnzip::testZip] compressed dim = " + zipped.length);
		write("./zipped.zip", zipped);
		byte[] zippedRead = read("./zipped.zip");
		System.out.println("[ZipUnzip::testZip] read compressed dim = " + zippedRead.length);
		byte[] unzipped = unzip(zippedRead);
		System.out.println("[ZipUnzip::testZip] unzipped dim = " + unzipped.length);
		write("./unzipped.txt", unzipped);
	}

	private static byte[] read(String where) {
		try {
			FileInputStream fis = new FileInputStream(where);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			baos.close();
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] zip(byte[] toZip) {
		return zip(toZip, "noname.txt");
	}

	public static byte[] zip(byte[] toZip, String name) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
			zos.putNextEntry(new ZipEntry(name));
			zos.write(toZip);
			zos.close();
			baos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void write(String where, byte[] what) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(where);
			fos.write(what);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] unzip(byte[] toUnzip) {
		ByteArrayInputStream bais = null;
		ZipInputStream zis = null;

		try {
			bais = new ByteArrayInputStream(toUnzip);
			zis = new ZipInputStream(bais);
			zis.getNextEntry();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = zis.read(buffer, 0, 8192)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			bais.close();
			zis.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Zippa tutti i file contenuti in una Map (nomefile, contenuto)
	 * @param files è una Map (nomefile, contenuto) dei file da zippare
	 * @return
	 * @throws IOException
	 */
	public static byte[] zipFileMap(Map<String, byte[]> files) throws IOException {
		// Create the ZIP file in memory
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(bStream);

		for (String filename : files.keySet()) {
			byte[] filecontent = files.get(filename);
			out.putNextEntry(new ZipEntry(filename));
			out.write(filecontent);
			out.closeEntry();
		}

		// Complete the ZIP file
		out.close();
		byte[] zipByteArray = bStream.toByteArray();
		return zipByteArray;

	}
}
