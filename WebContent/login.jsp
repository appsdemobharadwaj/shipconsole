<!--


11/06/2015  Suman G     Modified image to fix #2992
01/07/2015  Rakesh K    Added Code to fix #3124
01/07/2015  Suman G     Added code to fix #3127
07/07/2015  Rakesh K    Added code to fix #3128
24/07/2015  Rakesh K    Modified Code to work application in offline.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015   Dinakar G   Modified UI according to mobile devices. 
26/08/2015  Rakesh K       Added code to solve 3496.
16/03/2016  Y Pradeep   Added code to download QZ Tray.
-->


<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
  <head>
    <title>
      <s:property value="getText(\'SCLogin\')"/>
    </title>
    <meta charset="utf-8"></meta>
    
    <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
    
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>
     <!--<link type="text/css" rel="stylesheet" href="bootstrap/Kendo.meterial.min.css"/>-->
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    
   <!-- <style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style>-->
    
    <!--<meta name="viewport" content="width=device-width, initial-scale=1"></meta>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
    <style type="text/css">
    body{
    background-image: url(images/scbackground1.jpg); 
    background-repeat:no-repeat;
    background-size:cover;
    }
  </style>
    <script language="javascript" type="text/javascript">
     var button = "N";
     
     function onload(){
      if(navigator.platform=="iPhone"||(navigator.platform=="Linux armv7l"&&screen.height<=600&&screen.width<=1024)){
    var divmain1=document.getElementById("divMain");
    divmain1.style.width="90%";  
   document.getElementById("idbody").style.backgroundImage = 'url()';
  // document.getElementById("userimg").style.image = none;
  // document.getElementById("passwordimg").style.image = none;
     //alert(document.getElementById('divMain').style.width);
    }
     
     //Sanjay added below code for copy/paste disable 
    //window.onload = function() 
    //{
    //alert("hello");
   document.aascLoginForm.userName.focus(); // Kalyani added code for bug 1425.
//         var myInput = document.getElementById('password');
//         myInput.onpaste = function(e) 
//         {
//         //alert("Prevented from copy/paste, please type the password.");
//         return false;
//           //e.preventDefault();
//         }
//         
//                  var myCaptchaInput =  document.getElementById('txtInput');
//
//          myCaptchaInput.onpaste = function(e) 
//         {
//         //alert("Prevented from copy/paste, please type the password.");
//         return false;
//           //e.preventDefault();
//         }
         
   // }
     
   var checkLoginFail= document.getElementById('checkLoginFailID').value;
    // alert("checkLoginFail :"+checkLoginFail);
     
     document.getElementById('captcha1').style.display= 'none';
     document.getElementById('captcha2').style.display= 'none';
     document.getElementById('captcha3').style.display= 'none';
     document.getElementById('captcha4').style.display= 'none';
     if(checkLoginFail=="aasc405"){
       document.getElementById('captcha1').style.display= 'block';
       document.getElementById('captcha2').style.display= 'block';
       document.getElementById('captcha3').style.display= 'block';
       document.getElementById('captcha4').style.display= 'block';
      DrawCaptcha();
     }
    
     }
    function disableSubmit()
    {
        if(button=="Y")
        {
            alert("Request already submitted. Please Wait.");
            return false;
        }
    }
    
    function loader()
    {
   
    var pb;
    pb = document.getElementById("indexLoad");
    pb.innerHTML = '<img src="images/ajax-loader.gif" width="80" height ="80"/>';
    pb.style.display = '';
   
    
    }
    function onSubmit(){
        document.aascLoginForm.userName.value = trim(document.aascLoginForm.userName.value);
       var userName=document.aascLoginForm.userName.value;
       if(userName == '' || userName == null){
          //alert("User Name should not be empty");
          alert("Username/Password fields cannot be left blank");
          document.aascLoginForm.userName.focus();
          return false;
       }
       var password=document.aascLoginForm.password.value;
       if(password == '' || password == null){
          //alert("Password should not be empty");
          document.aascLoginForm.password.focus();
          alert("Username/password fields cannot be left blank");
          return false;
       }
       if(ValidCaptcha()==false){
       alert("The entered text did'nt match the image")
       DrawCaptcha();
       document.getElementById('txtInput').value="";
       document.getElementById('txtInput').focus();
       return false;
       }
    }
    
  

   //Created / Generates the captcha function    
    function DrawCaptcha()
    { //alert("khaja back :"+document.getElementById('txtCaptcha').style.backgroundImage);
//	var chap= document.getElementById('txtCaptcha').style.backgroundImage;
//	//alert(chap.substring(11,12));
//	var chap2 = chap.substring(11,12);
//
//var chap3 = parseInt(chap2);
//if(chap3==9){
//chap3 =2;
//}else{
//chap3=chap3+1;
//}


//document.getElementById('txtCaptcha').style.backgroundImage = 'url("images/'+chap3+'.jpg")';
//alert("khaja back 2:"+document.getElementById('txtCaptcha').style.backgroundImage);
        var a = Math.ceil(Math.random() * 9)+ '';
        var b = Math.ceil(Math.random() * 9)+ '';       
        var c = Math.ceil(Math.random() * 9)+ '';  
        var d = Math.ceil(Math.random() * 9)+ '';  
        var e = Math.ceil(Math.random() * 9)+ '';  
        var f = Math.ceil(Math.random() * 9)+ '';  
        var g = Math.ceil(Math.random() * 9)+ '';  
        var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' '+ f + ' ' + g;
        document.getElementById("txtCaptcha").value = code
    }

    // Validate the Entered input aganist the generated security code function   
    function ValidCaptcha(){
        var str1 = removeSpaces(document.getElementById('txtCaptcha').value);
        var str2 = removeSpaces(document.getElementById('txtInput').value);
        if (str1 == str2) return true;        
        return false;
        
    }

    // Remove the spaces from the entered and generated code
    function removeSpaces(string)
    {
        return string.split(' ').join('');
    }
    
    function openForgotPassword()
    {
    
        //Shiva added code for bug 1509
        var userName=document.aascLoginForm.userName.value;
        if(userName == '' || userName == null){
          //alert("User Name should not be empty");
          alert("Username field cannot be left blank");
          //document.aascLoginForm.userName.focus();
            //window.location.close();
          }
          else{
        forgetPasswordWindow =  window.open("aascForgotPassword.jsp","Post",'width=450,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
        forgetPasswordWindow.focus();
        }
        
        
    }
    
    function removeSpaces(string) {
 return string.split(' ').join('');
    }
    </script>
    <style type="text/css">

.href {color: #003399}

    </style>
  </head>
 
 
 
  <body id="idbody" onload="onload();" style="height:100%;" class="gradient">

    <div class="container">
       <s:form action="login" name="aascLoginForm"
      class="form-horizontal" role="form" >
 
        <%
           String userName =(String)request.getAttribute("userName");
           pageContext.setAttribute("userName",userName);
           %>
        <s:set name="userName" value="%{#attr.userName}"/>
        <s:hidden name="actiontype" value="%{\'login\'}"/>
        
      
            <center>
                      
              <div id="divMain" class="container-fluid" style="background-color:#ffffff; border-width: 0px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-top:5%;margin-right:1%;width:60%">
               <div class="row"><br/></div>
                <div class="row">
                   <img src="images/shiplogo.png" ></img>
                  <img src="images/cloud.png" ></img>
                </div>
               <div class="row"><br/></div>
               
                <div class="row" id="divSub" style="width:100%; ">
                
                <div class="col-sm-12">
                <div class="col-sm-2">
                </div>
                <div class="col-sm-2">
                <img src="images/user_img.png"
                       style="vertical-align:middle;"/>
                       </div>
                <div class="col-sm-5">
                      <s:textfield name="userName" class="form-control"
                               placeholder="User Name" value="%{#userName}"
                               onblur="this.value=removeSpaces(this.value);"
                               title="UserName"/>
                      </div>
                 
                </div>
                </div>
                <div class="row"><br/></div>
               
                <div class="row" id="divSub" style="width:100%; ">
                
                <div class="col-sm-12">
                <div class="col-sm-2">
                </div>
                <div class="col-sm-2">
                <img src="images/password_img.png"
                       style="vertical-align:middle;"/>
                       </div>
                <div class="col-sm-5">
                      <s:password name="password" id="password" class="form-control"
                              onpaste="return false;" placeholder="Password"
                              oncontextmenu="return false;" title="Password"/>
                      </div>
                 
                </div>
                </div>
                <br/>
                
          <s:set name="key" value="%{#attr.key}"/>
          <s:hidden name="checkLoginFail" id="checkLoginFailID"
                    value="%{#attr.key}"/>
           
          <s:if test="%{#key != null}">
            <span class="displayMessage">
              <s:property value="getText(#key)"/>
              <br></br>
              <s:property value="%{\'Please Check and re-enter.\'}"/>
            </span>
          </s:if>
       <div id="captcha1">
                <div class="row"><br/></div>
                  <input type="text" id="txtCaptcha"
                         style="background-image:url(images/1.JPG); text-align:center; border:none; font-weight:bold; font-family:Modern"
                         readonly="readOnly"/>
                </div>
                <div class="row"><br/></div>
                <div id="captcha2">
                <button type="button" class="btn btn-primary" id="btnrefresh" value="Refresh" onclick="DrawCaptcha();"  align="middle"> Refresh <span class="badge"><span class="glyphicon glyphicon-refresh"></span></span></button>
                
                  
                </div>
                
                <div id="captcha4">
                <div class="row"><br/></div>
                  <b><s:property value="%{\'Enter the numbers above\'}"/>
                     </b>
                </div>
               
                <div id="captcha3">
                 <div class="row"><br/></div>
                  <input type="text" name="txtInput" id="txtInput"
                         onpaste="return false;" oncontextmenu="return false;"/>
                </div>
               
                
               <!-- <div class="checkbox">
                  <label>
                    <input type="checkbox"></input>
                    Remember me
                  </label>
                </div>-->
                <div class="col-sm-12">
                    <div class="col-sm-5">
                        <s:a href="https://github.com/qzind/qz-print/releases/download/v2.0.0-RC2/qz-tray-2.0.0-RC2.exe" ><font  size="2" color="003366"><b> <u>Download QZ Tray for Windows</u></b></font></s:a>
                    </div>
                    
                    <div class="col-sm-4">
                        <s:a href="https://github.com/qzind/qz-print/releases/download/v2.0.0-RC2/qz-tray-2.0.0-RC2.pkg" ><font  size="2" color="003366"><b> <u>Download QZ Tray for Mac</u></b></font></s:a>
                    </div>
                    
                    <div class="col-sm-3">
                        <s:a href="javascript:openForgotPassword()" ><font  size="2" color="003366"><b> <u>Forgot Password?</u></b></font></s:a>
                    </div>
                </div>
                <div class="row"><br/></div>
                <div class="row"><br/></div>
                <div class="row">
                <button class="btn btn-primary" id="login" name="login"  onclick="return onSubmit();" > Submit <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                
                        </div>
         
             <div class="row"><br/></div>
          
          
       
        
        <div class="row" align=center>
    <footer><p><font size="1" color="blue">Copyright 2017 &copy; Apps Associates. All rights reserved</font></p></footer>    
</div>
       
      </div>
      </center>
      </s:form>
    
 
   
    
   
   </div>
    
    </body>
</html>
