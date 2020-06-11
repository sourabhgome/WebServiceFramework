package com.thinking.machines.test;
import com.thinking.machines.annotations.*;
import com.thinking.machines.beans.*;
import javax.servlet.*;
import javax.servlet.http.*;

@Path("/calculate")
public class Calculate
{
 @Secured("com.thinking.machines.test.SecurityClass")
 @Forward("/ServerFramework/test.jsp")
 @Path("/square")
 @ResponseType("JSON")
 public AjaxResponse square(NumberBean nb,HttpServletRequest req,HttpServletResponse res,HttpSession session,ServletContext servletContext)
 {
  System.out.println(nb);
  System.out.println(req);
  System.out.println(res);
  System.out.println(session);
  System.out.println(servletContext);
  AjaxResponse ajax=new AjaxResponse();
  SquareAndFactorialBean safb=new SquareAndFactorialBean();
  safb.setSquare(nb.getNum1());
  safb.setFactorial(nb.getNum2());
  ajax.setResponse(safb);
  ajax.setSuccess(true);
  ajax.setException(false);
  return ajax;
 }
 @Path("/hello")
 public void six()
 {
 }
 @Path("/world")
 public void five()
 {
 }
 @Path("/kalu")
 public void four()
 {
 }
 @Path("/singh")
 public void three()
 {
 }
 @Path("/sourabh")
 public void two()
 {
 }
 @Path("/gome")
 public void one()
 {
 }
 @Path("/rono")
 public void foo()
 {
 } 

}