<%/*========================================================================+
@(#)aascShipError.jsp 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
=========================================================================*/
/***
* JSP For Displaying Error details
* @version 1
* @author       N Srisha
* @history
*  
* 15/04/2015  Eshwari M     Modified code to fix bug # 2589
* 09/06/2015  Suman G       Modified code to fix #2986
*/
%>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page isErrorPage="true"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="aasc_ss.css">
 <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
<style type="text/css">
 html{height:100%}
 </style>
<title>Shipment Error Page</title>
</head>
<body class="gradientbg" bgcolor = "#CCCCCC">
  <P>
    <FONT color="#ff3333">
      <img src="images/aasc_Apps_Logo.png" alt="" width="202" height="48" onclick="checkForm()">
    </FONT> 
  </P>
  <P>&nbsp;</P>
  <P>&nbsp;</P>
 <c:choose>
    <c:when test="${pageContext.session['new']}" >
            <center><P>
               <FONT color="#ff3333" class="displayMessage"> <c:out value="Your session is Timed Out." /> 
                Click&nbsp;&nbsp;<a class="readon" href="login.jsp"><span>here</span></a> &nbsp;to login again. </FONT>
             </P></center>
    </c:when>
    <c:otherwise>
        <center><P>
            <FONT color="#ff3333" class="displayMessage"> <c:out value="Application Error...  " /> </FONT>
    <FONT color="#ff3333" class="displayMessage">Click&nbsp;&nbsp;<a class="readon" href="login.jsp"><span>here</span></a> &nbsp;to login again. </FONT></FONT>
       &nbsp;</P>
        </center>
    </c:otherwise>
</c:choose>        
 <div style="position:relative;top:500px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
</body>
</html>
