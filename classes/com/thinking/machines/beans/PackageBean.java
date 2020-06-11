package com.thinking.machines.beans;
import java.lang.reflect.*;
import java.io.*;
public class PackageBean implements java.io.Serializable
{
 private String[] scanPackages;

 public void setScanPackages(String[] scanPackages)
 {
  this.scanPackages=scanPackages;
 }
 public String[] getScanPackages()
 {
  return this.scanPackages;
 }
 
}