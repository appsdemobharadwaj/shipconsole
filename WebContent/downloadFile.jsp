<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>downloadFile</title>
  </head>
  <body>
  
<%@ page import="java.io.*" %>
<%@ page import="java.servlet.*" %>

 
 
<%
    /*
    @param: This code expects "csv_data" to contain the data to download as csv
    @param: In "file_name" its an optional param for naming the .csv file
    */
    //String csv_string = request.getParameter("csv_data");
    String file_name = "upload_ship_to_locations_csv.csv";
    if(file_name.length() == 0) file_name = "data.csv";
    response.setContentType("application/csv");
    response.setHeader("content-disposition","filename=\""+file_name+"\""); // Filename
    //PrintWriter outx = response.getWriter();
    //outx.println(file_name);
    //outx.flush();
    //outx.close();

%>
  </body>
</html>
