package it.tasgroup.report.servlet;

import java.util.HashMap;

public class MimeTypesMapper {

	private static HashMap<String, String> MIME_TYPES_MAP = init();
	private static String DEFAULT_MIME_TYPE = "application/octet-stream"; 
	
	private static HashMap<String, String> init() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("aac", "audio/aac");
		map.put("abw", "application/x-abiword");
		map.put("arc", "application/octet-stream");
		map.put("avi", "video/x-msvideo");
		map.put("azw", "application/vnd.amazon.ebook");
		map.put("bin", "application/octet-stream");
		map.put("bz", "application/x-bzip");
		map.put("bz2", "application/x-bzip2");
		map.put("csh", "application/x-csh");
		map.put("css", "text/css");
		map.put("csv", "text/csv");
		map.put("doc", "application/msword");
		map.put("epub", "application/epub+zip");
		map.put("gif", "image/gif");
		map.put("htm", "text/htm");
		map.put("html", "text/html");
		map.put("ico", "image/x-icon");
		map.put("ics", "text/calendar");
		map.put("jar", "application/java-archive");
		map.put("jpeg", "image/jpeg");
		map.put("jpg", "image/jpeg");
		map.put("js", "application/javascript");
		map.put("json", "application/json");
		map.put("mid", "audio/midi");
		map.put("midi", "audio/midi");
		map.put("mpeg", "video/mpeg");
		map.put("mpkg", "application/vnd.apple.installer+xml");
		map.put("odp", "application/vnd.oasis.opendocument.presentation");
		map.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
		map.put("odt", "application/vnd.oasis.opendocument.text");
		map.put("oga", "audio/ogg");
		map.put("ogv", "video/ogg");
		map.put("ogx", "application/ogg");
		map.put("png", "image/png");
		map.put("pdf", "application/pdf");
		map.put("ppt", "application/vnd.ms-powerpoint");
		map.put("rar", "application/x-rar-compressed");
		map.put("rtf", "application/rtf");
		map.put("sh", "application/x-sh");
		map.put("svg", "image/svg+xml");
		map.put("swf", "application/x-shockwave-flash");
		map.put("tar", "application/x-tar");
		map.put("tif", "image/tiff");
		map.put("tiff", "image/tiff");
		map.put("ttf", "font/ttf");
		map.put("vsd", "application/vnd.visio");
		map.put("wav", "audio/x-wav");
		map.put("weba", "audio/webm");
		map.put("webm", "video/webm");
		map.put("webp", "image/webp");
		map.put("woff", "font/woff");
		map.put("woff2", "font/woff2");
		map.put("xhtml", "application/xhtml+xml");
		map.put("xls", "application/vnd.ms-excel");
		map.put("xml", "application/xml");
		map.put("xul", "application/vnd.mozilla.xul+xml");
		map.put("zip", "application/zip");
		map.put("3gp", "video/3gpp");
		map.put("3g2", "video/3gpp2");
		map.put("7z", "application/x-7z-compressed");
		
		return map;
	}
	
	
	public static String getMimeType(String extension) {
		String mime = MIME_TYPES_MAP.get(extension); 
		return (mime == null ? DEFAULT_MIME_TYPE : mime);
	}
}
