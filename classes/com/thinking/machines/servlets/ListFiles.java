package com.thinking.machines.servlets;
import java.io.*;
import java.util.*;

public class ListFiles
{
 private static List<String> classes;
 public static List getClassList(String mainDirPath,String[] packages)
 {
  String path;
  classes=new ArrayList<>();
  if(packages.length==0)
  {
   RecursivePrint(new File(mainDirPath).listFiles(),"");
  }
  for(String p:packages)
  {
   path=mainDirPath+p.replace(".","\\");
   RecursivePrint(new File(path).listFiles(),p);
  }
  return classes;
 }
 public static void RecursivePrint(File[] arr,String path)
 {
  for (File f : arr)  
  { 
   if(f.isFile() && f.getName().substring(f.getName().length()-6,f.getName().length()).equals(".class")) classes.add(path+"."+f.getName().substring(0,f.getName().length()-6));
   if(f.isDirectory())  
   {
    if(path=="") 
     RecursivePrint(f.listFiles(),path+f.getName());
    else
     RecursivePrint(f.listFiles(),path+"."+f.getName());
   }
  }
 }
}