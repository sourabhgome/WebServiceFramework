package com.thinking.machines.beans;
import java.lang.reflect.*;
import java.lang.annotation.*;
import java.io.*;
public class Service implements java.io.Serializable
{
 private String path;
 private Class classInstance;
 private Method method;
 private Object instance;
 private Annotation[] annotations;

 public void setPath(String path)
 {
  this.path=path;
 }
 public String getPath()
 {
  return this.path;
 }

 public void setClassInstance(Class classInstance)
 {
  this.classInstance=classInstance;
 }
 public Class getClassInstance()
 {
  return this.classInstance;
 }

 public void setMethod(Method method)
 {
  this.method=method;
 }
 public Method getMethod()
 {
  return this.method;
 }

 public void setInstance(Object instance)
 {
  this.instance=instance;
 }
 public Object getInstance()
 {
  return this.instance;
 }

 public void setAnnotations(Annotation[] annotations)
 {
  this.annotations=annotations; 
 }
 public Annotation[] getAnnotations()
 {
  return this.annotations;
 }
} 