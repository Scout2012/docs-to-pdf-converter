package com.yeokhengmeng.docstopdfconverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;

import org.kohsuke.args4j.Option;

public class ConverterRunner{

	public static final String VERSION_STRING = "\nDocs to PDF Converter Version 1.7 (8 Dec 2013)\n\nThe MIT License (MIT)\nCopyright (c) 2013-2014 Yeo Kheng Meng";
	public enum DOC_TYPE {
		DOC,
		DOCX,
		PPT,
		PPTX,
		ODT
	}

	public static Converter getConvertType(String pathToBeConverted, String fileExtension) throws Exception {
			boolean shouldShowMessages = false;
			Converter converter = null;

			if(pathToBeConverted == null){
				throw new IllegalArgumentException();
			}

			String pathDiskLocation = FilenameUtils.getPrefix(pathToBeConverted);
			String basePath = FilenameUtils.getPath(pathToBeConverted);
			String fileName = FilenameUtils.getBaseName(pathToBeConverted);
			
			InputStream inStream = getInFileStream(pathToBeConverted);
			String fullPath = pathDiskLocation + basePath + fileName + ".pdf";
			OutputStream outStream = getOutFileStream(fullPath);
			
			if(pathToBeConverted.endsWith("doc")){
				converter = new DocToPDFConverter(inStream, outStream, shouldShowMessages, true);
			} else if (pathToBeConverted.endsWith("docx")){
				converter = new DocxToPDFConverter(inStream, outStream, shouldShowMessages, true);
			} else if(pathToBeConverted.endsWith("ppt")){
				converter = new PptToPDFConverter(inStream, outStream, shouldShowMessages, true);
			} else if(pathToBeConverted.endsWith("pptx")){
				converter = new PptxToPDFConverter(inStream, outStream, shouldShowMessages, true);
			} else if(pathToBeConverted.endsWith("odt")){
				converter = new OdtToPDF(inStream, outStream, shouldShowMessages, true);
			} else {
				converter = null;
			}

		return converter;
	}


	public static class CommandLineValues {

		@Option(name = "-type", aliases = "-t", required = false, usage = "Specifies doc converter. Leave blank to let program decide by input extension.")
		public DOC_TYPE type = null;

		@Option(name = "-inputPath", aliases = {"-i", "-in", "-input"}, required = false,  metaVar = "<path>",
				usage = "Specifies a path for the input file.")
		public String inFilePath = null;

		@Option(name = "-outputPath", aliases = {"-o", "-out", "-output"}, required = false, metaVar = "<path>",
				usage = "Specifies a path for the output PDF.")
		public String outFilePath = null;

		@Option(name = "-verbose", aliases = {"-v"}, required = false, usage = "To see intermediate processing messages.")
		public boolean verbose = false;

		@Option(name = "-version", aliases = {"-ver"}, required = false, usage = "To view version code.")
		public boolean version = false;
	}

	//From http://stackoverflow.com/questions/941272/how-do-i-trim-a-file-extension-from-a-string-in-java
	public static String changeExtensionToPDF(String originalPath) {

		String filename = originalPath;

		// Remove the extension.
		int extensionIndex = filename.lastIndexOf(".");

		String removedExtension;
		if (extensionIndex == -1){
			removedExtension =  filename;
		} else {
			removedExtension =  filename.substring(0, extensionIndex);
		}
		String addPDFExtension = removedExtension + ".pdf";

		return addPDFExtension;
	}	
	
	protected static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException{
		File inFile = new File(inputFilePath);
		FileInputStream iStream = new FileInputStream(inFile);
		return iStream;
	}
	
	protected static OutputStream getOutFileStream(String outputFilePath) throws IOException{
		File outFile = new File(outputFilePath);
		
		try{
			//Make all directories up to specified
			outFile.getParentFile().mkdirs();
		} catch (NullPointerException e){
			//Ignore error since it means not parent directories
		}
		
		outFile.createNewFile();
		FileOutputStream oStream = new FileOutputStream(outFile);
		return oStream;
	}
}
