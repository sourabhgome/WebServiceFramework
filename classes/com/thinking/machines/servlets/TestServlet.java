package com.thinking.machines.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import com.thinking.machines.test.*;

public class TestServlet extends HttpServlet
{
 public void doGet(HttpServletRequest request,HttpServletResponse response)
 {
  /*String maindirpath;
  PrintWriter pw=null;
  Class c;
  Annotation annotations;
  Method[] methods;
  RandomAccessFile raf;
  String packageName;
  Class<?> annotationClass;
  String responseString;
  try
  {
   pw=response.getWriter();
   maindirpath=getServletContext().getRealPath("/")+"WEB-INF\\classes\\";
   raf=new RandomAccessFile(new File(maindirpath+"Servlets.conf"),"r");
   packageName=raf.readLine();
   maindirpath+=packageName.replace(".","\\");
   System.out.println(packageName);
   System.out.println(maindirpath);
   responseString="";
   annotationClass=Class.forName("com.thinking.machines.test.Path");
   for(File f : (List<File>)ListFiles.getClassList(maindirpath))
   {
    System.out.println(f);
    System.out.println(packageName+f.getName().substring(0,f.getName().length()-6));
    c=Class.forName(packageName+f.getName().substring(0,f.getName().length()-6));
    System.out.println(c);
    annotations=c.getAnnotation(annotationClass);
    methods=c.getDeclaredMethods();
    for(Method m: methods)
    {
     Annotation k=m.getAnnotation(com.thinking.machines.test.Path.class);
     if(k!=null)
      responseString=m.invoke(c.newInstance()).toString();
      //System.out.println("\n"+c+" : "+annotations+"\n"+m+" : "+k);    
    }
   }


   pw.println("URI: "+request.getRequestURI()+"\n");
   pw.println("\nURL: "+request.getRequestURL());
   pw.println("\nresponse: "+responseString);

  }catch(Exception e)
   {
    System.out.println(e);
   }*/ 
 }
}