package com.aasc.erp.tests;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Selenium Started....");
		Selenium selenium = new DefaultSelenium("107.23.173.189", 4445, "firefox", "http://107.23.173.189:8080/hello/");
	      selenium.start();   // Start
	      selenium.open("http://107.23.173.189:9090/hello");  // Open the URL
	      selenium.windowMaximize();
	      Thread.sleep(4000); // Wait for page load
	      
	      // Focus on text Box
	      selenium.focus("name=val");
	      // enter a value in Text box 1
	      selenium.type("css=input[name=\"val\"]", "10");
	      
	      // enter a value in Text box 2
	      selenium.focus("name=val1");
	      selenium.type("css=input[name=\"val1\"]", "hello");
	      
	      // Click Calculate button
	     // selenium.click("input[@type='Submit']");
	      System.out.println("Selenium Ended.......");
	     
	}

}
