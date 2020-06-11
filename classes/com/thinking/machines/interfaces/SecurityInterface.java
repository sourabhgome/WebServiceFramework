package com.thinking.machines.interfaces;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.beans.*;

public interface SecurityInterface
{
 public boolean toBeProcessed(HttpServletRequest request,ServletContext servletContext,HttpSession httpSession);
 public AjaxResponse doThisInstead();
}