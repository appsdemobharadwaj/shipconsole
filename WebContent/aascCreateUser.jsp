<%/*========================================================================+
@(#)aascLocationSetup.jsp 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Customer and locations Details
* @version 1
* @author       N Srisha
* @history
*  
* 05/01/2014    Suman G     Renamed save() to saveForCreate()
* 07/01/2014    Y Pradeep   Renamed saveForCreate() to save().
* 21/01/2015    K Sunanda   Added code to display success messages in green and errored in red for bug #2506
* 04/02/2015    K Sunanda   Added Id for display messages 
* 05/03/2015    Sanjay & Khaja Added code for new UI changes.
* 30/03/2015    K Sunanda   Added ids to the fields
*  14/04/2015   Y Pradeep   Modified code to display UI in proper order while navigating using tab button. Bug #2809.
* 14/04/2015    Eshwari M   Modified clearButton tage to input from a href for resolving bug # 2830
* 15/04/2015    Suman G     Added session to fix #2743
* 23/04/2015    Y Pradeep   Removed footer.
* 11/06/2015    Suman G     Removed fevicon to fix #2992
* 22/06/2015    Rakesh K    Modified code for heading issue #3061
* 07/07/2015    Dinakar     Aligined UI as per tab
* 21/07/2015    Suman G        Modified message id for QA
* 24/07/2015    Rakesh K    Modified Code to work application in offline.
04/08/2015      Rakesh K    Modified Code bootstrap css file work across all browsers.
*  26/08/2015   Dinakar G  Added id's for Automation testing.
26/08/2015  Rakesh K       Added code to solve 3496.
*/
%>

<link type="text/css" rel="stylesheet" href="aasc_ss.css">
<%! 
  public String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null) {
            return spcStr;
        } else {
            return object.toString();
        }
    }
%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
      
    <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
   
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
     <!--<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>-->
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <script src="kendo.all.min.js" type="text/javascript"></script>
    
    <!--<style type="text/css"> 
     @font-face {   
     font-family: 'Glyphicons Halflings';   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');   
     src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.woff') format('woff'),  
     url('bootstrap/fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg'); } 
    </style>-->
      
      <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script> 
      <link href="kendo.common-material.min.css" rel="stylesheet" />
      <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
      <script language="JavaScript" src="jquery-1.8.3.js" type="text/javascript"></script>  -->
      
      
      <script  type="text/javascript" src="aasc_User_Details_js.js"> </script>
      

    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:property value="getText('ShipCreateUser')"/></title>
    <style type="text/css">
      .href {color: #003399}
       html{height:100%;}
    </style>
     <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
     <link rel="stylesheet" type="text/css" href="css/style4.css" />
     
     
  </head>
  
  <body class="gradientbg">
  <%  
            String url = request.getContextPath();
            if(session.isNew())
            {
                response.sendRedirect(url+"/aascShipError.jsp");
            }
    %>
  <div class="container-fluid" style="background-color:#ADADAD;overflow:hidden;">
    <s:form  name="CreateUserForm" method="POST" action="AascCreateUserAction" margin-left="auto" margin-right="auto" border-radius="5px">
      <s:set name="clientIDInt" value="%{#session.client_id}"/>
      <s:set name="clientIDStr" value="%{#clientIDInt + ''}"/>
      <s:hidden name="clientID" value="%{#clientIDInt}"/>
      <s:hidden name="actiontype" /> 
      <s:hidden name="key" value="%{#attr.key}"/>
      
         <div class="row">
            <s:include value="aascIndexHeader.jsp" />
           <!--21/01/2015 sunanda added-->
               <c:catch var="exception1">
                 <s:set name="key" value="%{#attr.key}"/>
                 <s:if test="%{#key != null}">
            <s:if test="%{#key=='aasc410'}">
            <div class="col-sm-8 displayMessage1" id="displayMessage" align="center" >
                    <s:property value="getText(#key)"/>
                   </div>
               </s:if>
             <s:else>
             <div class="col-sm-4 displayMessage1" id="displayMessage" align="center">
                     <s:property value="getText(#key)"/> 
                   </div>
              </s:else>
                  <s:set name="key" value="%{'null'}"/>
                 </s:if>
               </c:catch>
            <s:if test="%{#exception1 != null}">
               <c:redirect url="/aascShipError.jsp">
                 <s:param name="errorObj" value="%{#exception1.message}" />
               </c:redirect>
            </s:if>
          </div>
    <div class="row"><br/></div>
    <div class="row"><br/></div>

        <center>
 
        <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%">
                  <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                       <label style="color:#ffffff;font-size:20px">Create User</label>
                    </div>  
      
                         
           
                        <div class="row"><br/></div>
          
            <s:set name="aascUserInfo" value="%{#session.aascUserBean}"/>
            <s:set name="status" value="%{#aascUserInfo.status}"/>
            
            <s:bean id="locationDetailsMap" name="java.util.HashMap"/>
             <s:set name="locationDetailsMap" value="%{#session.locationDetailsMap}"/>
                    <div class="row" id="divSub" >
                      <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('FirstName')"/> <font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="firstName" id="firstName" cssClass="inputs" size="50" value="%{#aascUserInfo.firstName}" maxlength="50" />
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('LastName')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="lastName" id="lastName" cssClass="inputs" size="50" value="%{#aascUserInfo.lastName}" maxlength="50" />
                        </div>
                      </div>  
                    </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" >
                     <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('emailAddress')"/><font color="red">* </font></span></label>
                        <div class="col-sm-4">
                            <s:textfield name="emailAddress" id="emailAddress" cssClass="inputs" size="50" value="%{#aascUserInfo.emailAddress}" maxlength="50" />
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('alternateEmailAddress')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:textfield name="alternateEmailAddress" id="alternateEmailAddress" cssClass="inputs" size="50" value="%{#aascUserInfo.alternateEmailAddress}" maxlength="50" />
                        </div>
                      </div>  
                    </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" >
                     <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('role')"/></span></label>
                        <div class="col-sm-4">
                            <s:select name="role" id="role" list="#{'3':'Admin', '4': 'Shipping User','5': 'Tracking'}" cssClass="inputs" value="%{#aascUserInfo.role}"  />
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Location')"/> </span> </label>
                        <div class="col-sm-4">
                            <s:select list="locationDetailsMap"  listKey="key" listValue="value" headerKey="Select" headerValue="Select" cssStyle="height:25px"  name="locationId" size="1" cssClass="inputs" id="locationId" value="%{#aascUserInfo.locationId}"/>
                        </div>
                      </div>  
                    </div>
                    <div class="row"><br/></div>
                    <div class="row" id="divSub" >
                     <div class="col-sm-12">
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Status')"/></span></label>
                        <div class="col-sm-4">
                            <s:select list="#{'Y':'ACTIVE', 'N': 'INACTIVE'}" name="status" id="status" cssClass="inputs" value="#status"/>
                        </div>
                        <label for="inputEmail" class="control-label col-sm-2" style="text-align: left;"> <span class="dispalyFields"><s:property value="getText('AddressBook')"/></span></label>
                        <div class="col-sm-4">
                            <s:select list="#{'UL':'User Level', 'CL': 'Company Level'}" name="addressBookLevel" id="addressBookLevelId" cssClass="inputs" value="#aascUserInfo.addressBookLevel"/>
                        </div>
                      </div>  
                    </div>
                <div class="row"><br/></div>
                <div class="row" id="divSub">
                <div class=" col-sm-2" >
                </div>
                <div class=" col-sm-8" >
                    <s:hidden name="SaveButtonId" id="SaveButtonId" value="%{0}"/>
                    <s:if test='%{#keyTmp == "aasc410"}'>
                         <button class="btn btn-primary" name="closeButton"  id="clearButton" onclick="document.CreateUserForm.actiontype.value='Cancel';" value="Cancel"  alt="Close" align="middle"> Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                    </s:if>
                    <s:else>
                         <button class="btn btn-success" name="Save" id="SaveButton" onclick="document.CreateUserForm.actiontype.value='AddNewUser';return save();" value="AddNewUser" alt="Save" align="middle"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                         <span class="btn btn-warning" name="clearButton" id="clearButton" onclick="clearFields();">
                             Clear <span class="badge" style="background-color:marun"><span class="glyphicon glyphicon-remove"></span></span></span>

                         <!--<button class="btn btn-warning" name="clearButton" id="clearButton"   alt="Clear" align="middle"> Clear <span class="badge"><span class="glyphicon glyphicon-edit"></span></span></button> -->
                         <button class="btn btn-danger" name="closeButton" id="closeButton" onclick="document.CreateUserForm.actiontype.value='Cancel';" value="Close" alt="Close" align="middle"> Close <span class="badge"><span class="glyphicon glyphicon-off"></span></span></button>
                      </s:else>
		</div>
                    <div class=" col-sm-2" >
                </div>
                
             </div>
             </br>                    
            
                   
       </div>
       
       </center>
     
     
    </s:form>
    </div>
    
                     

 <div style="position:relative;top:300px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
</html>
