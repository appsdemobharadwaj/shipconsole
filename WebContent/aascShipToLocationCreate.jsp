<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page import="java.io.*" %>
<%@page import="java.text.*" %>
<%@page import="java.sql.*"%>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*" %>
<%@page import="com.aasc.erp.bean.AascShipToLocationsInfo" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<link type="text/css" rel="stylesheet" href="aasc_ss.css">

<%@ taglib uri="/struts-tags" prefix="s"%>


<%/*========================================================================+
@(#)aascShipToLocationCreate.jsp 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/***
* JSP For Creating Ship To Locations Details
* @version 1
* @author  Jagadish Jain     
* @history
*  
* 17-Dec-2014  Eshwari M    Merged Sunandas code and removed the hard codings by getting from Properties file 
* 19-Dec-2014 Sunanda K     Alignment of country list box with other text fields and removed remainig HardCodings
* 26-Dec-2014 Suman G       Merged Sunanda's code
* 15/01/2015  Y Pradeep     Merged Sunanda's code on 1.0 release bugs and getting title name from Application.Property file.
* 21/01/2015  K Sunanda     Added code to display success messages in green and errored in red for bug #2506 
* 04/02/2015  K Sunanda     Added Id for display messages
* 16/02/2015  Suman G       Added style attribute for td of country to fix #2605
* 18/02/2015  Sunanda K     Modified code to fix bug #2604
* 05/03/2015  Sanjay & Khaja Added code for new UI changes.
* 10/03/2015  Y Pradeep     Added missing name for param tag
* 10/03/2015  K Sunanda     Modified code for proper Display message color 
                            and also proper clear button functioning
* 23/03/2015  K Sunanda     Added code for newly created fields email address and addressline 3
* 08/04/2015  Y Pradeep     Modified code to align fields in order and in a single table to do tab key navigation. Bug #2809.
* 14/04/2015  Suman G       Modified phoneNumber1 to phoneNumber to fix #2849.
* 23/04/2015  Y Pradeep     Removed footer.
* 20/05/2015  K Sunanda     Modified code for contact name to be mandatory and 
                            no special characters to be allowed for Ship To Location name
* 11/06/2015  Suman G          Removed fevicon to fix #2992
* 07/07/2015  Dinakar          Aligined UI as per tab
* 21/07/2015    Suman G        Modified message id for QA
* 24/07/2015  Rakesh K        Modified Code to work application in offline.
* 24/07/2015  Rakesh K        Modified Code for #3240.
04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
26/08/2015  Rakesh K       Added code to solve 3496.
*/
%>

<HTML>
  <HEAD>
  
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
  
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
       <link href="kendo.common-material.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />-->
       
    <TITLE><s:property value="getText('SCCreateShipToLocation')" /></TITLE>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
    <link type="text/css" rel="stylesheet" href="jquery-ui.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript" SRC="aasc_Ship_To_Location.js">
    </script>
    <script>
  
(function( $ ) {
    $.widget( "ui.combobox", {
      _create: function() {
        var input,
          that = this,
          wasOpen = false,
          select = this.element.hide(),
          selected = select.children( ":selected" ),
          value = selected.val() ? selected.text() : "",
          wrapper = this.wrapper = $( "<span>" )
          //  .addClass( "ui-combobox" )
            .insertAfter( select );
        function removeIfInvalid( element ) {
            document.popperform.shipToCustomerName.options[document.popperform.shipToCustomerName.selectedIndex].value=$( element ).val();
            var value = $( element ).val(),
            matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
            valid = false;
            select.children( "option" ).each(function() {
            if ( $( this ).text().match( matcher ) ) {
            this.selected = valid = true;
            return false;
            }
          });
          if ( !valid ) {
            $( element )
            input.data( "ui-autocomplete" ).term = "";
          }
        }
        input = $( "<input>" )
          .appendTo( wrapper )
          .val( value )
          .attr( "title", "" )
          .autocomplete({
            delay: 0,
            minLength: 0,
            source: function( request, response ) {
              var matcher = new RegExp("^" +$.ui.autocomplete.escapeRegex(request.term), "i" );
              response( select.children( "option" ).map(function() {
             
                var text = $( this ).text();
                if ( this.value && ( !request.term || matcher.test(text) ) )
                  return {
                    label: text.replace(
                      new RegExp(
                        "(?![^&;]+;)(?!<[^<>]*)(" +
                        $.ui.autocomplete.escapeRegex(request.term) +
                        ")(?![^<>]*>)(?![^&;]+;)", "gi"
                      ), "<strong>$1</strong>" ),
                    value: text,
                    option: this
                  };
              }) );
            },
            select: function( event, ui ) {
            
              ui.item.option.selected = true;
              that._trigger( "selected", event, {
                item: ui.item.option
              });
              
            var shipToCustomerName=document.popperform.shipToCustomerName.options[document.popperform.shipToCustomerName.selectedIndex].value;
            var customerNameOptionsArray= new Array();
            var customerNameOptions = document.getElementById('shipToCustomerName');
            for (i = 1; i < customerNameOptions.options.length; i++) 
            {
                customerNameOptionsArray[i] = customerNameOptions .options[i].value;
              
                if(shipToCustomerName==customerNameOptionsArray[i])
                {
                    
                }
            }
            
  
            },
            change: function( event, ui ) {
              if ( !ui.item ) {
                removeIfInvalid( this );
              }
            }
          })
          .addClass( "ui-widget ui-widget-content ui-corner-left" );
 
        input.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
          
          return $( "<li>" )
            .append( "<a>" + item.label + "</a>" )
            .appendTo( ul );
        };
 
        $( "<a>" )
          .attr( "tabIndex", -1 )
        //  .attr( "title", "Show All Items" )
        //  .tooltip()
          .appendTo( wrapper )
          .button({
            icons: {
              primary: "ui-icon-triangle-1-s"
            },
            text: false
          })
          .removeClass( "ui-corner-all" )
         .addClass( "ui-corner-right ui-combobox-toggle" )
          .mousedown(function() {
            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
          })
          .click(function() {
         
            input.focus();
 
            // close if already visible
            if ( wasOpen ) {
              return;
            }
 
            // pass empty string as value to search for, displaying all results
            input.autocomplete( "search", "" );
            
          });
       
        input.tooltip({
          tooltipClass: "ui-state-highlight"
        });
      },
 
      _destroy: function() {
        this.wrapper.remove();
        this.element.show();
      }
    });
  })( jQuery );
 
$(function() {
    $( "#shipToCustomerName" ).combobox();
    $( "#toggle" ).click(function() {
      $( "#shipToCustomerName" ).toggle();
    });
  });
  
function changePassword(){
    tpwindow =  window.open("aascChangePassword.jsp","Post",'width=500,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
    tpwindow.focus();
    }

function editProfile(){
tpwindow =  window.open("aascEditUserProfile.jsp","Post",'width=700,height=450,top=100,left=100,scrollbars=yes, resizable=yes');
    tpwindow.focus();
}


$(function() {
$(".dropdown").hover(
function() { $(".submenu").slideToggle(400); },
function() { $(".submenu").hide(); }
);
});
  
  </script>
  
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/> 
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    
    <style type="text/css">
      html {
        height: 100%;
            }
    fieldset.scheduler-border {
        border: 2px groove #7761a7 !important;
        padding: 1.4em 1.4em 1.4em 1.4em !important;
       border-radius: 15px;}
       .button { 
       width: 150px;
       padding: 10px; 
       background-color: #FF8C00; 
       box-shadow: -8px 8px 10px 3px rgba(0,0,0,0.2); 
       font-weight:bold; text-decoration:none; }
       .blurred {
      -webkit-filter: blur(2px);
      -webkit-transition: all 2s;
}
    </style>
  </HEAD>
 
    <body  onload="">
    <s:include value="aascIndexHeader.jsp" />
        <div class="container-fluid" style="background-color:#ADADAD;">
           <s:form name="popperform" method="post" action="AascCreateShipToLocationsAction" >
            <s:hidden name="dropOfTypeHidden" value="%{''}" />
            <s:hidden name="packagesHidden" value="%{''}" />
            <%
            int clientId1 = ((Integer)session.getAttribute("client_id")).intValue();
            int userId1=(Integer)session.getAttribute("user_id");
            int locationId1=((Integer)session.getAttribute("location_id")).intValue();
            pageContext.setAttribute("client_id",clientId1);
            pageContext.setAttribute("user_id",userId1);
            pageContext.setAttribute("location_id",locationId1);
            %>
            <s:set name="client_Id" value="%{#attr.client_id}"/>
            <s:set name="user_Id" value="%{#attr.user_id}"/>
            <s:set name="location_Id" value="%{#attr.location_id}"/>
            <s:set name="aascShipToLocationsInfo" value="%{#session.aascShipToLocationsInfo}" />              
            <s:set name="locationDetailsList" value="%{#session.shipToCustomersList}"/> 
            <!--<s:set name="locationMap"  value="%{#session.locationDetailsMap}"/>-->
            <s:bean id="countryNameHashMap" name="java.util.HashMap"/>
            <c:catch var="exception1">
                <s:set name="countryNameHashMap" value="%{#session.countryNameHashMap}" />
            </c:catch>
            <s:if test="%{#exception1 != null}">
                <s:param name="countryNameHashMap" value="%{'select'}"/>
            </s:if>
            <s:hidden name="client_Id" /> 
            <s:hidden name="user_Id" /> 
            <s:hidden name="location_Id" /> 
            <s:hidden name="actiontype" /> 
            <table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                <tr>
               <!--21/01/2015 sunanda added-->
                    <c:catch var="exception1">
                        <s:set name="key" value="%{#attr.key}"/>
                        <s:if test="%{#key != null}">
                      <s:if test="%{#key=='aasc602'||#key=='aasc600'}">
                       <td width="90%" class="displayMessage1" id="displayMessage" align="center" valign="bottom">
                        <s:property value="getText(#key)"/> 
                       </td>
                      </s:if>
                      <s:else>
                       <td width="90%" class="displayMessage" id="displayMessage" align="center" valign="bottom">
                            <s:property value="getText(#key)"/>
                       </td>
                      </s:else>
                        <s:set name="key" value="%{'null'}"/>
                        </s:if>
                    </c:catch>
                    <s:if test="%{#exception1 != null}">
                        <redirect url="/aascShipError.jsp">
                            <s:param name="errorObj" value="%{#exception1.message}"/>
                        </redirect>
                    </s:if> 
                </tr>
            </table>
            <br/>
            <div class="row"><br/></div>
            <div class="row"><br/></div>
            
            
            <center>
                
                    <div class="container-fluid" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:90%"> 
                        <div style="background-color:#7761a7;margin-top:5px;margin-left:5px;height:30px;margin-top:5px;margin-left:5px;height:30px;font-weight:bold;font-size:Small;color:white;">
                            <label style="color:#ffffff;padding-left:10px;font-size:20px" >Create Ship To Location Details</label>
                        </div>  
                        <div class="row" style="margin-top:10px">
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('CustomerName')"/> </span> </label>
                            
                            <div class="col-sm-3">
                               <div class="" id="ui-widgetShipToId"  >
                               <SELECT name="shipToCustomerName" id="shipToCustomerName" class="inputs" onKeyDown="fnKeyDownHandler(this, event);" onKeyUp="fnKeyUpHandler_A(this, event); return false;" onKeyPress = "return fnKeyPressHandler_B(this, event);"  onChange="fnChangeHandler_A(this, event);" >
                                <option value="Select"> Select </option>
                                                        <%
                                                        String key=(String)request.getAttribute("key");
                                                        AascShipToLocationsInfo aascShipToLocationsInfo = (AascShipToLocationsInfo)session.getAttribute("aascShipToLocationsInfo");
                                                        int count=0;
                                                        LinkedList ShipToList1=(LinkedList)session.getAttribute("customersList");
                                                        ListIterator ShipToIterator=null;
                                                        ShipToIterator=(ShipToList1).listIterator(); 
                                                        String customerName="";
                                                        String shipToCustomerName="";
                                                        if ("aasc602".equalsIgnoreCase(key)||"aasc600".equalsIgnoreCase(key))
                                                        {
                                                           customerName=aascShipToLocationsInfo.getShipToCustomerName();
                                                         %>
                                                        <option  value="<%=(customerName)%>" selected="selected" ><%=customerName%></option>
                                                        <%
                                                        }
                                                        else
                                                        {
                                                         while(ShipToIterator.hasNext())
                                                         {
                                                          customerName = (String)ShipToIterator.next();
                                                          %>
                                                          <option  value="<%=(customerName)%>"><%=customerName%></option>
                                                          <%
                                                         } 
                                                        }
                                                        %>
                                                    </select>
                                                    </div>
                            </div>
                            
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('ContactName')"/> </span> </label>
                               
                            <div class="col-sm-3">
                               <s:textfield name="shipToContactName"  id="shipToContactName" size="20" value="%{#aascShipToLocationsInfo.shipToContactName}" cssClass="inputs"/>
                            </div>
                           </div> 
                        </div>
                        <div class="row"><br/></div>
                        <div class="row" >
                          <div class="col-sm-12">
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('LocationName')"/> </span> </label>
                             
                             <div class="col-sm-3">
                                <s:textfield name="shipToCustLocation"  cssClass="inputs"  id="shipToCustLocation" size="20" value="%{#aascShipToLocationsInfo.shipToCustLocation}"/>
                            </div>
                            
                             <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine1')"/> </span> </label>
                               
                             <div class="col-sm-3">
                                <s:textfield name="addressLine1" cssClass="inputs" id="shipFromAddressLine1" size="20" value="%{#aascShipToLocationsInfo.addressLine1}"/>
                            </div>
                          </div>
                        </div> 
                        <div class="row"><br/></div>
                         <div class="row" >
                          <div class="col-sm-12">
                           
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine2')"/> </span> </label>
                            <div class="col-sm-3">
                                <s:textfield name="addressLine2" cssClass="inputs" id="shipFromAddressLine2" size="20" value="%{#aascShipToLocationsInfo.addressLine2}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('AddressLine3')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="addressLine3" cssClass="inputs" id="shipFromAddressLine3" size="20" value="%{#aascShipToLocationsInfo.addressLine3}"/>
                            </div>
                           </div>
                         </div>
                         <div class="row"><br/></div>
                         <div class="row" >
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('City')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="city"  cssClass="inputs" id="shipFromCity" size="20" value="%{#aascShipToLocationsInfo.city}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('State')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="state"  cssClass="inputs" id="shipFromState" size="20" onblur="stateChgCase()" value="%{#aascShipToLocationsInfo.state}"/>
                            </div>
                           </div>
                          </div> 
                          <div class="row"><br/></div>
                          <div class="row" >
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('PostalCode')"/> </span> </label>
                           
                            <div class="col-sm-3">
                                <s:textfield name="postalCode"  cssClass="inputs" id="shipFromPostalCode" size="20" value="%{#aascShipToLocationsInfo.postalCode}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('Country')"/> </span> </label>
                            
                            <div class="col-sm-3" >
                                <s:iterator value="countryNameHashMap">
                                                        <s:set name="countryNameHashMapKey" value="key"/>
                                                        <s:set name="countryNameHashMapValue" value="value"/>
                                                    </s:iterator>
                                                    <s:select list="countryNameHashMap" cssStyle="height:25px" cssClass="inputs" listKey="value" listValue="key" headerKey="Select" headerValue="Select"  name="countryCode" size="1"  id="shipFromCountry" value="%{#aascShipToLocationsInfo.countryCode}"/>
                            </div>
                           </div>
                          </div>
                          <div class="row"><br/></div>
                          <div class="row" >
                          <div class="col-sm-12">
                          <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('PhoneNumber')"/> </span> </label>
                           
                            <div class="col-sm-3">
                                <s:textfield name="phoneNumber"  cssClass="inputs" id="shipFromPhoneNumber1" size="20"  value="%{#aascShipToLocationsInfo.phoneNumber}"/>
                            </div>
                            <label for="inputEmail" class="control-label col-sm-3" style="text-align: left;"> <span class="dispalyFields" > <s:property value="getText('emailAddress1')"/> </span> </label>
                            
                            <div class="col-sm-3">
                                <s:textfield name="emailAddress" cssClass="inputs" id="emailAddressId" size="20" value="%{#aascShipToLocationsInfo.emailAddress}"/>
                            </div>
                           </div>
                          </div> 
                          <div class="row"><br/></div>
                        <div class="row" >
                          <div class="col-sm-2">
                          </div>
                            <div class="col-sm-8">
                                <button type="button" class="btn btn-success" name="Save" id="SaveButton" onclick="document.popperform.actiontype.value='AddNewLocation'; return checkData();" value="AddNewLocation" alt="Save" align="middle">Save <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></button>
                                <button type="button" class="btn btn-warning" name="clearButton" id="clearButton" onclick="clearFields();" alt="Clear" align="middle">Clear <span class="badge"><span class="glyphicon glyphicon-remove"></span></span></button>
                                <button type="button" class="btn btn-danger" name="closeButton" id="closeButton" onclick="document.popperform.actiontype.value='Cancel';document.popperform.submit();" value="Cancel" alt="Close" align="middle">Close <span class="badge"><span class="glyphicon glyphicon-off"></span></span></button>
                            </div>
                            <div class="col-sm-2">
                          </div>
                          </div>
                          
                          <div class="row"><br/></div>
                    </div>
                    
                </center>
       
                      
    </s:form>
   </div>
 <div style="position:absolute;top:620px; left:50px;">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
    </body>
</html>
