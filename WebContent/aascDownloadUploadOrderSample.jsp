<%@ page import="java.io.*" %>
<%    
  String filename = "aascUploadOrdersTemplate.csv";   
 // String filepath = "\\";   
  String applicationPath = application.getRealPath("/");
  System.out.println("applicationPath"+applicationPath);
  response.reset();
  response.setContentType("APPLICATION/OCTET-STREAM");   
  response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");  
 FileInputStream fileInputStream=null;
  try{
   fileInputStream= new FileInputStream(applicationPath + filename);  
  
  int i;   
  while ((i=fileInputStream.read()) != -1) {  
    out.write(i);   
  }   
  fileInputStream.close(); 
  }
  catch(Exception e){
  e.printStackTrace();
  }
  

%>   
