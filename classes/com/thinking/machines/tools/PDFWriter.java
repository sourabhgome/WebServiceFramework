package com.thinking.machines.tools;

import com.thinking.machines.annotations.*;
import com.thinking.machines.servlets.*;
import com.thinking.machines.beans.*;
import com.itextpdf.text.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.Gson;


import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFWriter
{
 public static void main(String gg[])
 {
  String path=gg[0];
  String executionPath=System.getProperty("user.dir");
  String servicePdf=path+"\\Service.pdf";
  String errorPdf=path+"\\Error.pdf";
  Map<String,Service> map;
  java.util.List<Service> errorList;
  StringBuilder sb;
  RandomAccessFile raf;
  Class c;
  Method[] methods;
  java.lang.annotation.Annotation annotation;
  String JSONString;
  PackageBean packageBean;
  Class annotationClass;
  String a,b,url;
  Service service;
  try
  {
   map=new HashMap<>();
   errorList=new ArrayList<>();
   sb=new StringBuilder();
   raf=new RandomAccessFile(new File(executionPath+"\\Service.conf"),"r");
   while(raf.getFilePointer()<raf.length())
   {
    sb.append(raf.readLine());
   }
   raf.close();
   JSONString=sb.toString();
   packageBean=new Gson().fromJson(JSONString, PackageBean.class);
   
   annotationClass=Class.forName("com.thinking.machines.annotations.Path");
   for(String s : (java.util.List<String>)ListFiles.getClassList(executionPath+"\\",packageBean.getScanPackages()))
   {
    c=Class.forName(s);
    annotation=c.getAnnotation(annotationClass);
    methods=c.getDeclaredMethods();
    for(Method m: methods)
    {
     java.lang.annotation.Annotation k=m.getAnnotation(com.thinking.machines.annotations.Path.class);
     if(k!=null)
     {
      a=annotation.toString().split("=")[1].split("\"")[1];
      b=k.toString().split("=")[1].split("\"")[1];
      url=a+b;
      service=new Service();
      service.setPath(url);
      service.setClassInstance(c);
      service.setMethod(m);
      service.setAnnotations(m.getDeclaredAnnotations());
      if(map.containsKey(url))
      {
       errorList.add(map.get(url));
       errorList.add(service);
      }
      else map.put(url,service);
     }
    }
   }
   new File(servicePdf).delete();
   new File(errorPdf).delete();
   if(errorList.size()>0)
   {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(errorPdf));
    document.open();
    Paragraph preface = new Paragraph();
    preface.add(new Paragraph(" "));
    int i=0;
    preface.add(new Paragraph("This classes and methods have same value of Path annotation !!!"));
    preface.add(new Paragraph(" "));
    while(i<errorList.size())
    {
     Service s1=errorList.get(i);
     preface.add(new Paragraph("Annotation : "+s1.getPath()));
     preface.add(new Paragraph(" "));
     preface.add(new Paragraph("Class : "+s1.getClassInstance().getCanonicalName()));
     preface.add(new Paragraph(" "));
     preface.add(new Paragraph("Method : "+s1.getMethod().getName()));
     preface.add(new Paragraph(" "));
     preface.add(new Paragraph(" "));
     i++;
    }
    document.add(preface);
    document.close();
   }
   else
   {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(servicePdf));
    document.open();
    Paragraph preface = new Paragraph();
    preface.add(new Paragraph(" "));
    int i=0;
    for(Service s1:map.values())
    {
     preface.add(new Paragraph("Annotation : "+s1.getPath()));
     preface.add(new Paragraph(" "));
     preface.add(new Paragraph("Class : "+s1.getClassInstance().getCanonicalName()));
     preface.add(new Paragraph(" "));
     preface.add(new Paragraph("Method : "+s1.getMethod().getName()));
     preface.add(new Paragraph(" "));
     preface.add(new Paragraph("Other Annotations : "));
     for(java.lang.annotation.Annotation annot : s1.getAnnotations())
     {
      preface.add(new Paragraph("\t\t\t\t\t"+annot));
     }
     preface.add(new Paragraph(" "));
     preface.add(new Paragraph(" "));
     i++;
    }
    document.add(preface);
    document.close();
   }
  }catch(Exception e)
   {
    System.out.println("\n\n\n\n\n\n\n\n\n\n"+e+"\n\n\n\n\n\n\n\n\n");
   }
 }
}