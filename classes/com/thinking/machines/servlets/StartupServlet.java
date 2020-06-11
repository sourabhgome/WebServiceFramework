package com.thinking.machines.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.google.gson.Gson;
import com.thinking.machines.beans.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

public class StartupServlet extends HttpServlet
{
 private static Map<String,Service> map;
 public void init() throws ServletException
 {
  String maindirpath;
  RandomAccessFile raf;
  StringBuilder sb;
  String JSONString;
  PackageBean packageBean;
  String url;
  Service service;
  Class annotationClass;
  Class c;
  Annotation annotation;
  Method[] methods;
  String a,b;
  try
  {
   map=new HashMap<>();
   sb=new StringBuilder();
   maindirpath=getServletContext().getRealPath("/")+"WEB-INF\\classes\\";
   raf=new RandomAccessFile(new File(maindirpath+"Service.conf"),"r");
   while(raf.getFilePointer()<raf.length())
   {
    sb.append(raf.readLine());
   }
   raf.close();
   JSONString=sb.toString();
   packageBean=new Gson().fromJson(JSONString, PackageBean.class);
   
   annotationClass=Class.forName("com.thinking.machines.annotations.Path");
   for(String s : (List<String>)ListFiles.getClassList(maindirpath,packageBean.getScanPackages()))
   {
    c=Class.forName(s);
    annotation=c.getAnnotation(annotationClass);
    methods=c.getDeclaredMethods();
    for(Method m: methods)
    {
     Annotation k=m.getAnnotation(com.thinking.machines.annotations.Path.class);
     if(k!=null)
     {
      a=annotation.toString().split("=")[1].split("\"")[1];
      b=k.toString().split("=")[1].split("\"")[1];
      url=a+b;
      service=new Service();
      service.setPath(url);
      service.setClassInstance(c);
      service.setMethod(m);
      service.setAnnotations(m.getAnnotations());
      map.put(url,service);
     }
    }
   }

  }catch(Exception e)
   {
    System.out.println("\n\n\n\n\n\n\n\n\n\n"+e+"\n\n\n\n\n\n\n\n\n");
   }
 }
 public static Map<String,Service> getMap()
 {
  return map;
 }
}