<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP to display POD info from Fedex                                |
|    Author Eshwari M                                                       |
|    Version - 1.0                                                          |
|    History                                                                |
|        -20/01/2015 Y Pradeep  Modified auther and version, also removed commented code.
+===========================================================================*/%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.util.Date" %>
<%@page import="com.aasc.erp.bean.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>fedexPODPage</title>
    </head>

    <body>
        <%
            String carrierMode=(String)request.getParameter("carrierMode");
            if (!carrierMode.equalsIgnoreCase("FedexWebServices"))
            {
        %>
                <img src="fedexpod.png" width="130%" height="130%" alt="">
        <%
            }
            else
            {
                response.setContentType("image/png");
                response.addHeader("Pragma","no-cache");
                response.addIntHeader("Expires",-1);
                OutputStream outStr=null;
                InputStream inputStream=null;
                String labelPath="";
                String urlPath="";
                String filePath="";
                OutputStream ouputStream = null;
                try 
                { 

                    urlPath = request.getContextPath();
                    filePath =  urlPath+"fedexpod.png";
                    inputStream= new FileInputStream(filePath);
                    ouputStream = response.getOutputStream();
                    byte[] buffer = new byte[8192];
                    int count=0;
                    while ((count = inputStream.read(buffer)) != -1)
                    {
                        ouputStream.write(buffer, 0, count);
                    }
                }
                catch (FileNotFoundException e) 
                { 
                     
                }
                catch (IOException e) 
                { 
                    
                    
                }
                finally
                {
                    try  {
                        ouputStream.close();
                    } catch (Exception ex)  {
                        ex.getMessage();
                    }
                }
                
            }
        %>
    </body>
</html>
