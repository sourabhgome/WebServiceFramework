package com.thinking.machines.beans;
import java.io.*;
public class SquareAndFactorialBean implements java.io.Serializable
{
 private int square;
 private int factorial;

 public void setSquare(int square)
 {
  this.square=square;
 }
 public int getSquare()
 {
  return this.square;
 }

 public void setFactorial(int factorial)
 {
  this.factorial=factorial;
 }
 public int getFactorial()
 {
  return this.factorial;
 }

}
