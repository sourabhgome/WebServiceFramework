package com.thinking.machines.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)  
public @interface FileUpload
{
 public String value();
}