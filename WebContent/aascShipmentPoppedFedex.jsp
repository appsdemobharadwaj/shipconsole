 
<%        
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP ShipmentPoppedFedex for displaying the Fedex related                  | 
|    information                                                            |
|    Author swapna soni                                                     |
|    Version   1.1                                                          |                                                                            
|    Creation 10/1/2006                                                     |
|    History SwapnaSoni 12/04/2006 change the code for getting the values   |
|            dropOfType and Packaging dropdownlist                          |        
|    ------- SwapnaSoni 17/04/2006 increased the length of city textfeild   |
|    ------- SwapnaSoni 26/04/2006 changed the code of dropOfType and       |
|            packaging list box. and added to hidden feilds dropOfTypeHidden|
|            and packagesHidden.                                            |   
|            Madhavi    11/03/09       Modified logging code
+===========================================================================*/%>
<%@ include file="aascHeader.jsp"%>
<%! private static Logger logger=AascLogger.getLogger("aascShipmentPoppedFedex"); %>
<%@ page errorPage="aascShipError.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<HTML>
  <HEAD>
    <TITLE>FEDEX</TITLE>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <script language="JavaScript" SRC="aasc_Shipment_JS.js">
    </script>
    
  </HEAD>

   <body class="gradient" onload="loadInfo();fedexDisplayInfo();">
   <%   try {  
   logger.info("inisde try of popped ");
   %>
 
    <p>
      <br class="messageCell"/>
    </p>
    <center>
      <s:form name="popperform" method="post">
      
       <s:hidden name="dropOfTypeHidden" value="%{''}" />
       <s:hidden name="packagesHidden" value="%{''}" />
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <center>
            <tr>
              <td>
                <p class="tableDataTrackCell">
                  <FONT color="#c0c0c0">
                    <table border="1" cellpadding="3" cellspacing="2" class="tableDataCell">
                      <center>
                        <tr>
                          <td colspan="6" class="tableSmallHeaderCell">
                            <div align="center" class="headings"><s:property value="getText('ShipFromDetails')"/>
                            <c:out value="Error in Retrieving session attribute locationDetailsMap"/>
                            </div>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td width="100" class="dispalyFields" nowrap><s:property value="getText('CompanyName')"/></td>
                          <td width="132">
                            <s:textfield name="shipFromLocation"  cssClass="inputFields"  id="shipFromLocation" size="20" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td width="115" class="dispalyFields"><s:property value="getText('AddressLine1')"/></td>
                          <td width="162">
                            <s:textfield name="shipFromAddressLine1" cssClass="inputFields" id="shipFromAddressLine1" size="20" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td width="115" class="dispalyFields"><s:property value="getText('AddressLine2')"/></td>
                          <td width="162">
                            <s:textfield name="shipFromAddressLine2" cssClass="inputFields" id="shipFromAddressLine2" size="20" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td width="145" class="dispalyFields"><s:property value="getText('City')"/></td>
                          <td width="151">
                            <s:textfield name="shipFromCity"  cssClass="inputFields" id="shipFromCity" size="20" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td height="23" class="dispalyFields"><s:property value="getText('State')"/></td>
                          <td>
                            <s:textfield name="shipFromState"  cssClass="inputFields" id="shipFromState" size="20" maxlength="2" onblur="shipFromStateChgCase()" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td height="23" class="dispalyFields"><s:property value="getText('Country')"/></td>
                          <td>
                            <s:textfield name="shipFromCountry"  cssClass="inputFields" id="shipFromCountry" size="20" maxlength="2" onblur="shipFromCountryChgCase()"  value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td class="dispalyFields"><s:property value="getText('PostalCode')"/></td>
                          <td>
                            <s:textfield name="shipFromPostalCode"  cssClass="inputFields" id="shipFromPostalCode" size="20" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td class="dispalyFields"><s:property value="getText('Phonenumber1')"/></td>
                          <td>
                            <s:textfield name="shipFromPhoneNumber1"  cssClass="inputFields" id="shipFromPhoneNumber1" size="20" maxlength="12" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                        <tr align="left" valign="top">
                          <td class="dispalyFields"><s:property value="getText('Phonenumber2')"/></td>
                          <td>
                            <s:textfield name="shipFromPhoneNumber2"  cssClass="inputFields" id="shipFromPhoneNumber2" size="20" maxlength="12" value="%{#ShipmentFlags.readOnlyFlag}"/>
                          </td>
                        </tr>
                       
                      </center>
                    </table>
                  </FONT>
                </p>
              </td>
            </tr>
            <tr>
              <td>
                <center>
                
                                
    <s:a onclick="putValue()"> <img src="buttons/aascSave1.png" name="saveButton"/></s:a> 
    <s:a href="javascript:cancelValue()"><img src="buttons/aascCancel1.png" name="cancel" border="0"></s:a>
    </center>
              </td>
            </tr>
          </center>
        </table>
      </s:form>
      <%   }
        catch(Exception e)
        {
        logger.info("inside exception of popped jsp");
          logger.severe("exception in ShipmentPoppedFedex.jsp"+e.getMessage());
        }
      %>
    </center>
     <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </BODY>
</HTML>
