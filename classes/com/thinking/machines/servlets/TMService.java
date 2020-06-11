package com.thinking.machines.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import com.thinking.machines.test.*;
import com.thinking.machines.beans.*;
import com.google.gson.Gson;
import javax.servlet.annotation.*;

@MultipartConfig(maxFileSize=(1024*1024*5))
public class TMService extends HttpServlet
{
 public void doPost(HttpServletRequest request,HttpServletResponse response)
 {
  ServletContext servletContext;
  HttpSession httpSession;
  PrintWriter pw;
  String URI;
  String reqURL;
  String arr[];
  Map<String,Service> map;
  String responseString;
  Service service;
  Annotation[][] paraAnnotations;
  List<Object> arguments;
  Annotation forwardAnnotation;
  Annotation securedAnnotation;
  Annotation fileUploadAnnotation;
  Annotation responseTypeAnnotation;
  Object[] args;
  Class[] argType;
  StringBuffer sb;
  InputStreamReader isr;
  Scanner sc;
  Gson gson;
  RequestDispatcher rd;
  try
  {
   servletContext=getServletContext();
   httpSession=request.getSession();
   gson=new Gson();
   isr=new InputStreamReader(request.getInputStream());
   sc=new Scanner(isr);
   pw=response.getWriter();
   sb=new StringBuffer();
   while(sc.hasNext())
   {
    sb.append(sc.nextLine());
   }
   System.out.println(sb.toString());
   pw=response.getWriter();
   URI=request.getRequestURI();
   arr=URI.split("/");
   reqURL="";
   int i=0;
   for(String s:arr)
   {
    if(i>2) reqURL+="/"+s;
    i++;
   }
   map=StartupServlet.getMap();
   if(map.containsKey(reqURL))
   {
    service=map.get(reqURL);
    forwardAnnotation=service.getMethod().getAnnotation(com.thinking.machines.annotations.Forward.class);
    securedAnnotation=service.getMethod().getAnnotation(com.thinking.machines.annotations.Secured.class);
    fileUploadAnnotation=service.getMethod().getAnnotation(com.thinking.machines.annotations.FileUpload.class);
    responseTypeAnnotation=service.getMethod().getAnnotation(com.thinking.machines.annotations.ResponseType.class);

    argType=service.getMethod().getParameterTypes();
    arguments=new ArrayList();
    for(Class argumentType:argType)
    {
     if(argumentType.getCanonicalName().equals(Class.forName("javax.servlet.http.HttpServletRequest").getCanonicalName()))
      arguments.add(request);
     else if(argumentType.getCanonicalName().equals(Class.forName("javax.servlet.http.HttpServletResponse").getCanonicalName()))
      arguments.add(response);
     else if(argumentType.getCanonicalName().equals(Class.forName("javax.servlet.ServletContext").getCanonicalName()))
      arguments.add(servletContext);
     else if(argumentType.getCanonicalName().equals(Class.forName("javax.servlet.http.HttpSession").getCanonicalName()))
      arguments.add(httpSession);
     else if(argumentType.getCanonicalName().equals(Class.forName("java.io.File").getCanonicalName()))
      arguments.add(FileUploadServlet.getFiles(request,servletContext.getRealPath("/")));
     else
      arguments.add(gson.fromJson(sb.toString(), Class.forName(argumentType.getCanonicalName())));
    }
    args=arguments.toArray();
    if(securedAnnotation!=null)
    {
     String securedClassName=((com.thinking.machines.annotations.Secured)securedAnnotation).value();
     Class securedClass=Class.forName(securedClassName);
     Object instance=securedClass.newInstance();
     boolean result=(boolean)securedClass.getDeclaredMethods()[0].invoke(instance,request,servletContext,httpSession);
     if(result==true)
     {
      if(forwardAnnotation==null)
      {
       pw.println(gson.toJson(service.getMethod().invoke(service.getInstance(),args)));
      }
      else
      {
       AjaxResponse ajax=(AjaxResponse)service.getMethod().invoke(service.getInstance(),args);
       ajax.setSuccess(true);
       ajax.setToForward(true);
       ajax.setResponse(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
       pw.println(gson.toJson(ajax));
      }
     }
     else
     {
      forwardAnnotation=securedClass.getDeclaredMethods()[1].getAnnotation(com.thinking.machines.annotations.Forward.class);
      if(forwardAnnotation==null)
      {
       pw.println(gson.toJson(securedClass.getDeclaredMethods()[1].invoke(instance)));
      }
      else
      {
       AjaxResponse ajax=(AjaxResponse)securedClass.getDeclaredMethods()[1].invoke(instance);
       ajax.setSuccess(true);
       ajax.setToForward(true);
       ajax.setResponse(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
       pw.println(gson.toJson(ajax));
      }
     }
    }
    else
    {
     if(responseTypeAnnotation==null)
     {
      if(service.getInstance()==null)
      {
       service.setInstance(service.getClassInstance().newInstance());
      }
      if(forwardAnnotation==null)
      {
       service.getMethod().invoke(service.getInstance(),args);
      }
      else
      {
       service.getMethod().invoke(service.getInstance(),args);
       AjaxResponse ajax=new AjaxResponse();
       ajax.setSuccess(true);
       ajax.setToForward(true);
       ajax.setResponse(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
       pw.println(gson.toJson(ajax));
      }
     }
     else
     {
      String responseType=((com.thinking.machines.annotations.ResponseType)responseTypeAnnotation).value();
      if(responseType.equalsIgnoreCase("JSON"))
      {
       if(service.getInstance()==null)
       {
        service.setInstance(service.getClassInstance().newInstance());
       }
       if(forwardAnnotation==null)
       {
        pw.println(gson.toJson(service.getMethod().invoke(service.getInstance(),args)));
       }
       else
       {
        Object obj=service.getMethod().invoke(service.getInstance(),args);
        AjaxResponse ajax=new AjaxResponse();
        ajax.setSuccess(true);
        ajax.setToForward(true);
        ajax.setResponse(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
        pw.println(gson.toJson(ajax));
       }
      }
      else if(responseType.equalsIgnoreCase("String"))
      {
       if(service.getInstance()==null)
       {
        service.setInstance(service.getClassInstance().newInstance());
       }
       if(forwardAnnotation==null)
       {
        pw.println(service.getMethod().invoke(service.getInstance(),args));
       }
       else
       {
        String resss=service.getMethod().invoke(service.getInstance(),args).toString();
        AjaxResponse ajax=new AjaxResponse();
        ajax.setSuccess(true);
        ajax.setToForward(true);
        ajax.setResponse(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
        pw.println(gson.toJson(ajax));
       }
      }
      else
      {
       if(service.getInstance()==null)
       {
        service.setInstance(service.getClassInstance().newInstance());
       }
       if(forwardAnnotation==null)
       {
        pw.println(gson.toJson(service.getMethod().invoke(service.getInstance(),args)));
       }
       else
       {
        service.getMethod().invoke(service.getInstance(),args);
        AjaxResponse ajax=new AjaxResponse();
        ajax.setSuccess(true);
        ajax.setToForward(true);
        ajax.setResponse(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
        pw.println(gson.toJson(ajax));
       }
      } 
     }
    }
    //Secured check -- Forward check


    //FileUpload check


    //


    //


    /*
    if(service.getInstance()==null)
    {
     service.setInstance(service.getClassInstance().newInstance());
    }
    forwardAnnotation=service.getMethod().getAnnotation(com.thinking.machines.annotations.Forward.class);
    if(forwardAnnotation==null)
    {
     pw.println(gson.toJson(service.getMethod().invoke(service.getInstance(),args)));
    }
    else
    {
     AjaxResponse ajax=(AjaxResponse)service.getMethod().invoke(service.getInstance(),args);
     ajax.setToForward(true);
     ajax.setResponse(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
     pw.println(gson.toJson(ajax));
     //service.getMethod().invoke(service.getInstance(),args);
     //System.out.println(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
     //rd=request.getRequestDispatcher(((com.thinking.machines.annotations.Forward)forwardAnnotation).value());
     //rd.include(request,response);
    }*/
   }
   else
   {
    pw.println(URI+" not found.");
   }
  }catch(Exception e)
   {
    System.out.println(e);
   } 
 }
}