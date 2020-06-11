# WebServiceFramework
This framework is made with an objective of saving employees from writing complex servlet's code. Basic motive of this framework is to save employee's as well as company's time. With the use of this framework one can create a servlet using simple java code and our framework will take care of that code and will convert it into proper java servlet.

# Installation 
**Step 1:** Download the TMServiceFramework.jar and copy it to WEB-INF\lib folder.

**Step 2:** Edit the contents of web.xml and write the given code in it.

```
<servlet>
 <servlet-name>StartupServlet</servlet-name>
 <servlet-class>com.thinking.machines.servlets.StartupServlet</servlet-class>
 <load-on-startup>0</load-on-startup>
</servlet>
<servlet>
 <servlet-name>Service</servlet-name>
 <servlet-class>com.thinking.machines.servlets.TMService</servlet-class>
</servlet>
<servlet-mapping>
 <servlet-name>Service</servlet-name>
 <url-pattern>/service/*</url-pattern>
</servlet-mapping>
```

# Usage
Steps are as follows :-

 1. In WEB-INF\classes folder create a file named Service.conf and in it write a JSON containing name of package which contains classes to be treated as servlets.
 For example, 
 ```
{
 "scanPackages":["com.thinking.machines.test"]
}
```

 2. To use this framework user should have a breif knowledge about annotations available in this framework. User should use those annotations in order to make their classes work as servlets.
    1. **Path Annotation:-** Can be applied to both classes and methods. It should be applied on Class and Method which should be invoked when request to a particular path is made. For example if the url is `http:\\localhost:8080\ServerFramework\service\calculate\square` then the request made is \service\calulate\square thus *@Path("/calculate")* should be applied on Class and *@Path("/square")* should be applied on method which is to be invoked.
    2. **Secured Annotation:-** Can be applied only on methods. This annotation is applied when user is not sure whether to run that particular method or not and thus this annotation should be applied to that particular method with a class name as its value, that class name is of class which implements ***com.thinking.machines.interfaces.SecurityInterface*** defined as follows :
      ```
      public interface SecurityInterface
      {
       public boolean toBeProcessed(HttpServletRequest request,ServletContext servletContext,HttpSession httpSession);
       public AjaxResponse doThisInstead();
      }
      ```
    3. **Forward Annotation:-** Can be applied only on methods. This annotation should be applied when request is to be forwarded to some other servlet or html page. Request URI to the servlet or Html page must be passed to this annotation as value. It returns an AJAXResponse object with response=url(to which request is to be forwarded) and toForward=true.For example, `@Forward("/index.html")`
    4. **FileUpload Annotation:-** Can be applied only to methods. This annotation should be used when user wants the file uploaded on the client side to user side. *true* or *false* should be passed to this annotation as value.
    5. **ResponseType Annotation:-** Can be applied only to methods. This annotation should be used to indicate what type of data user wants to send to client side.
         - `@ResponseType("JSON")`: It should be written when method is returning an object.
         - `@ResponseType("HTML/TEXT")`: It should be written when method is returning a String.
         - `@ResponseType("void")`: It should be written when method is returning nothing.
         
 3. A tool is also provided which can be used to check errors in files written by user. User should go to WEB-INF\classes and type the follwing command,
 `java -classpath ..\lib\*;c:\tomcat9\lib\*;. com.thinking.machines.tools.PDFWriter path(where user wants to save the pdf)`
 When user enters this line one of the following pdf will be generated,
    - **Error.pdf** : If classes written by user contains errors this pdf will be generated.
    - **Service.pdf** : If there are no error this pdf will be generated with a description of Annotations used, Classes and Methods.
 
