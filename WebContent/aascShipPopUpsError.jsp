<%/*========================================================================+
@(#)aascShipPopUpsError.jsp 16/04/2015
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Displaying Error details
* @version 1
* @author       Suman G
* @history
* 
*  23/04/2015   Y Pradeep       Removed footer and replaced shipexec logo with apps associates logo.
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
 <script language="JavaScript" src="aasc_Shipment_JS.js" type="text/javascript"></script>
 <script>
    window.onbeforeunload = function() {
        if (event) {
            redirectToError1();
            event.returnValue = 'Session Expired ..!';
        }
        
    };
    function redirectToError1(){
    
        window.opener.redirectToLogin();//='login.jsp';
        window.close();
        
    }
    </script>
<style type="text/css">
 html{height:100%}
 </style>
<title>Shipment PopUps Error Page</title>
</head>
<body class="gradientbg" bgcolor = "#CCCCCC">
  <P>
    <FONT color="#ff3333">
      <img src="images/aasc_Apps_Logo.png" width="202" height="48" alt="" onclick="checkForm()">
    </FONT> 
  </P>
  <P>&nbsp;</P>
  <P>&nbsp;</P>


            <center><P>
               <FONT color="#ff3333" class="displayMessage"> <c:out value="Your session is Timed Out." /> 
                Please click <a href="" onclick="redirectToError();"><span>here</span></a> to close the Window. </FONT>
                
             </P></center>
              <div style="position:relative;top:500px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
</body>
</html>
