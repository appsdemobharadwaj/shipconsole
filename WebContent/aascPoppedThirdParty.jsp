 
<%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascPoppedThirdParty for displaying the Third Party related       | 
|    information                                                            |
|    Author swapna soni                                                     |
|    Version   1.1                                                          |                                                                            
|    Creation 10/1/2006                                                     |
|    24/04/06 Bhanu Prasad added new StyleSheet                             |
|    10/06/09 Madhavi      Modified logging code                            |
|    19/11/14 Eshwari      Added this file from cloud 1.2 version           |
|    15/04/15 Suman G      Added session to fix #2743
|    11/06/15 Suman G      Removed fevicon to fix #2992 
     24/07/2015  Rakesh K    Modified Code to work application in offline.
     28/07/2015  Rakesh K    Modified Code #3265.
     04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
     26/08/2015  Rakesh K       Added code to solve 3496.
+===========================================================================*/%>
<%@page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="java.util.*" %>
<%@page import="java.util.logging.*"%>
<%!  private static Logger logger=AascLogger.getLogger("aascPoppedThirdParty"); %>
<%@ page errorPage="aascShipError.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<HTML>
  <HEAD>
    <TITLE>THIRD PARTY DETAILS</TITLE>
    
    
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
   
   <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <script language="JavaScript" SRC="aasc_Shipment_JS.js">
    </script>
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
   
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
     <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">-->


    <style type="text/css">
      html {
        height: 100%;
            }
    </style>
  </HEAD>
  
   <body class="gradientbg" style="height:100%" onLoad="fill();displayInfo();setTpCountryName()">
   
    <%  
            String url = request.getContextPath();
            if(session.isNew())
            {System.out.println("Inside session");
                response.sendRedirect(url+"/aascShipPopUpsError.jsp");
            }
    %>
   
   
    <%  
      AascShipMethodInfo aascShipMethodInfo=null;
      AascShipmentOrderInfo aascOrderInfo=null;
      AascShipmentHeaderInfo aascHeaderInfo=null;
      AascShipmentFlags shipmentFlags=null;
      String tpCountryName=""; 
      LinkedList countryName=null;
      ListIterator countrySymbolIterator=null;
      
      try
      {
          aascShipMethodInfo=(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
          aascOrderInfo=(AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
          aascHeaderInfo=aascOrderInfo.getShipmentHeaderInfo();
          shipmentFlags=new AascShipmentFlags(aascHeaderInfo);
          
          countryName=aascShipMethodInfo.getCountryDetailList();
          countrySymbolIterator=countryName.listIterator();
          logger.info("country size : "+countryName.size());
      }
      catch(Exception e)
      {
          logger.severe("exception in aaascPoppedThirdParty.jsp while creating object"+e.getMessage());
      }
    %>
    <script>
      var tpCountry=new Array();
      var tpCountryLength = 0;
      function fill()
      {
        <%
          int i1 =0;
          try
          {
            while(countrySymbolIterator.hasNext())
            { 
               aascShipMethodInfo = (AascShipMethodInfo) countrySymbolIterator.next();
               logger.info("country : "+aascShipMethodInfo.getCountryName()); 
               %>tpCountry[<%=i1%>]="<%=aascShipMethodInfo.getCountryName()%>";<%
               i1++;
            }
          }
          catch(Exception e)
          {
            logger.severe("exception in the fill() method in the aascPoppedThirdParty jsp"+e.getMessage());
            response.sendRedirect(url+"/aascShipError.jsp");
          }
        %>
        tpCountryLength="<%=i1%>";
      }
    </script>
  <%! 
   /**
    * nullStringToSpace() method is used when the string is null it replaces with space.
    * @param object of type Object
    * @return object of type String.
    */  
    public String nullStringToSpace(Object object) {
      String spcStr = "";
      if (object == null) {
        return spcStr;
      }
      else {
        return object.toString();
      }
    }    
  %>
  <p>
      <br class="messageCell"/>
  </p>
    <center>
       <s:form name="tppopperform" id="tppopperform" method="post">
         
        
        <div class="container">
        <div class="row" align="center">
        
         <div class="col-sm-1">
   </div>
        
        <div class="col-sm-10"> <!--maindiv-->
    
       <div class="well" style="border-color:#3d566d; border-radius:10px">
       
       <!--style="border-color:#3d566d; border-radius:10px" ><!--welldiv-->
        
        <div style="background-color:#3d566d;" align="center">
   <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 20px;font-weight:bold"><s:property value="getText('ThirdPartyDetails')"/></label>
   </div>
        
          <!-- <div align="center" style="font-weight:bold;font-size:20px;color:white;background-color:#3d566d" >        
                 <s:property value="getText('ThirdPartyDetails')"/>
                  </div>-->
              
              <br> 
              <div class="row" align="left"> <!--mini div-->
                  <div class="row"> 
                   <div class="col-sm-12">
                   
                   <div class="col-sm-6">
                   <p class="dispalyFields"><s:property value="getText('CompanyName')"/></p>
               </div>
               
               <div class="col-sm-6">
                   <s:textfield name="tpCompanyName"  cssClass="inputs" id="tpCompanyName" size="20" value="%{#shipmentFlags.getReadOnlyFlag}" maxlength="45" />
             </div>
            </div>
          </div>
          <br/>
              <div class="row"> 
           <div class="col-sm-12">
          
            <div class="col-sm-6">
        <p class="dispalyFields">  <s:property value="getText('Address')"/> </p>
               </div>
               
               
               <div class="col-sm-6">
                   <s:textfield name="tpAddress" cssClass="inputs" id="tpAddress" size="20" value="%{#shipmentFlags.getReadOnlyFlag}" maxlength="45"/>
               </div>
               </div>
               </div>
               <br/>
              <div class="row"> 
                <div class="col-sm-12">
               
             <div class="col-sm-6">
          <p class="dispalyFields">  <s:property value="getText('City')"/> </p>
               </div>
               
               <div class="col-sm-6">
                 <s:textfield name="tpCity"  cssClass="inputs" id="tpCity" size="20" value="%{#shipmentFlags.getReadOnlyFlag}" maxlength="45"/>
               </div>
               </div>
            </div>
               
             <br/>
              <div class="row">  
            <div class="col-sm-12">
          
           <div class="col-sm-6">
            <p class="dispalyFields">   <s:property value="getText('State')"/> </p>
             </div>
             
             <div class="col-sm-6">
                   <s:textfield name="tpState"  cssClass="inputs" id="tpState" size="20" value="%{#shipmentFlags.getReadOnlyFlag}" maxlength="45"/>
              </div>
              </div>
            </div>
            <br/>
              <div class="row"> 
             <div class="col-sm-12">
           
              <div class="col-sm-6">
           <p class="dispalyFields">    <s:property value="getText('PostalCode')"/> </p>
               </div>
               
               <div class="col-sm-6">
                   <s:textfield name="tpPostalCode"  cssClass="inputs" id="tpPostalCode" size="20" value="%{#shipmentFlags.getReadOnlyFlag}" maxlength="45" />
                </div>
                </div>
              </div>  
             <br/>
              <div class="row"> 
              <div class="col-sm-12">
             <div class="col-sm-6">
             <s:hidden name="hidden" value="%{''}" />
             <p class="dispalyFields">   <s:property value="getText('CountrySymbol')"/> </p>
                </div>
                
                <div class="col-sm-6">
                   <select name="tpCountrySymbol" size="1" class="inputs" id="tpCountrySymbol" <%=nullStringToSpace(shipmentFlags.getSelectDisableFlag())%>>
                   </select>
               </div>
               </div>
               </div>
               
                </div> <!--mini div-->
                
               
                <br>
                
                <div>
    
     <div class="row">
     
     <div class="col-sm-1"> </div>
    
     <div class="col-sm-10">
                   <center>
                      <!--  <a href="javascript:tpPutValue()"><button type="button" class="btn btn-info" name="saveButton" id="fedexSaveId" border="0">Save <span class="glyphicon glyphicon-ok"></span>
                        </button></a>-->
                    <button class="btn btn-success" name="saveButton" id="fedexSaveId" onclick="javascript:tpPutValue()"> Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                    
                    
                       <!-- <a href="javascript:tpCancelValue()"><button type="button" class="btn btn-info" name="cancel" id="fedexCancelId" border="0">Cancel <span class="glyphicon glyphicon-remove"></span></button></a>-->
                    <button class="btn btn-danger" name="cancel" id="fedexCancelId" onclick="javascript:tpCancelValue();" > Cancel <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                 
                                         
                   </center>
               </div>
               
            <div class="col-sm-1"> </div>
            
            </div>
         
         </div><!--well div-->
   </div><!--main div-->
        
 <div class="col-sm-3">
   </div>
 
 
  </div>
  <div class="col-sm-1">
   </div>
  </div>
         
         

       </s:form>
    </center>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </BODY>
</HTML>
