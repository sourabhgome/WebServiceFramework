package com.thinking.machines.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import com.google.gson.Gson;
import com.thinking.machines.beans.*;

public class FileUploadServlet
{
 public static File[] getFiles(HttpServletRequest request,String path)
 {
  File[] fileList=null;
  try
  {
   InputStream initialStream=null;
   OutputStream outStream=null;
   List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());
   for (Part filePart : fileParts) 
   {
    String fileName = filePart.getSubmittedFileName();
    initialStream = filePart.getInputStream();
    String tempDestinantion=path+"WEB-INF\\uploads\\";
    File targetFile = new File(path+fileName);
    outStream = new FileOutputStream(targetFile);
    byte[] buffer = new byte[1024]; 
    int bytesRead;
    while ((bytesRead = initialStream.read(buffer)) != -1) 
    {
     outStream.write(buffer, 0, bytesRead);
    }
    initialStream.close();
    outStream.close();
   }
   fileList=new File(path).listFiles();
  }
  catch(Exception e)
  {
   System.out.println(e);
  }
  return fileList;
 }
}