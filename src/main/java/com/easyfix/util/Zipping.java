package com.easyfix.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.easyfix.triggers.invoice.GenerateInvoiceDetails;

public class Zipping
{

private List<String> fileList;
private static String seperator=System.getProperty("file.separator");

private static  String OUTPUT_ZIP_FILE = "D:\\Folder.zip";
private static String SOURCE_FOLDER = "D:\\ef_documents"; // SourceFolder path
private static final String DOC_ROOT = "/var/www/html/easydoc";  /*"var/www/html/client_invoices/";*/
private static final Logger logger = LogManager.getLogger(Zipping.class);
//private static final String DOC_ROOT = "D:\\ef_documents";
public Zipping()
{
   fileList = new ArrayList<String>();
}

public static void zip()
{
	Zipping appZip = new Zipping();
	Date curdate = new Date();
	

	String filePath = DOC_ROOT;
	String curMonth = new SimpleDateFormat("MMM_yyyy").format(curdate);
	String source= filePath + seperator + curMonth;
	SOURCE_FOLDER = source;
	OUTPUT_ZIP_FILE = DOC_ROOT+seperator+"invoice.zip";
	
   appZip.generateFileList(new File(SOURCE_FOLDER));
   appZip.zipIt(OUTPUT_ZIP_FILE);
}

private void zipIt(String zipFile)
{
   byte[] buffer = new byte[1024];
   String source = "";
   FileOutputStream fos = null;
   ZipOutputStream zos = null;
   try
   {
      try
      {
         source = SOURCE_FOLDER.substring(SOURCE_FOLDER.lastIndexOf("\\") + 1, SOURCE_FOLDER.length());
      }
     catch (Exception e)
     {
        source = SOURCE_FOLDER;
     }
     fos = new FileOutputStream(zipFile);
     zos = new ZipOutputStream(fos);

     System.out.println("Output to Zip : " + zipFile);
     logger.info("Output to Zip : " + zipFile);
     FileInputStream in = null;

     for (String file : this.fileList)
     {
        System.out.println("File Added : " + file);
        logger.info("File Added : " + file);
        ZipEntry ze = new ZipEntry(source + File.separator + file);
        zos.putNextEntry(ze);
        try
        {
           in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
           int len;
           while ((len = in.read(buffer)) > 0)
           {
              zos.write(buffer, 0, len);
           }
        }
        finally
        {
           in.close();
        }
     }

     zos.closeEntry();
     System.out.println("Folder successfully compressed");
     logger.info("Folder successfully compressed");

  }
  catch (IOException ex)
  {
     ex.printStackTrace();
  }
  finally
  {
     try
     {
        zos.close();
     }
     catch (IOException e)
     {
        e.printStackTrace();
     }
  }
}

private void generateFileList(File node)
{

  // add file only
  if (node.isFile())
  {
     fileList.add(generateZipEntry(node.toString()));

  }

  if (node.isDirectory())
  {
     String[] subNote = node.list();
     for (String filename : subNote)
     {
        generateFileList(new File(node, filename));
     }
  }
}

private String generateZipEntry(String file)
{
   return file.substring(SOURCE_FOLDER.length() + 1, file.length());
}
}    