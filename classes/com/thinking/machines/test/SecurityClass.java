package com.thinking.machines.test;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.beans.*;

public class SecurityClass implements SecurityInterface
{
 public boolean toBeProcessed(HttpServletRequest request,ServletContext servletContext,HttpSession httpSession)
 {
  if(servletContext.getAttribute("username")!=null) return true;
  return false;
 }
 public AjaxResponse doThisInstead()
 {
  AjaxResponse ajax=new AjaxResponse(); 
  ajax.setException("Wrong username or password");
  ajax.setSuccess(false);
  return ajax;
 }
}