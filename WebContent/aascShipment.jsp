<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 <%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    JSP aascShipConsoleShipment for displaying the Shipment  related       | 
|    information                                                            |
|    Author Eshwari                                                         |
|    Modified Suman Gunda                                                   |
|    Version   1.1                                                          |                                                                            
|    Creation                                                               |      
|    History :                                                              |

            18/12/2014   Y Pradeep  Modified nullStringToSpace method and modified orderNumber from int to String.
            18/12/2014   Y Pradeep  Added code to display error messages from carrier and also clearing after navigating to another page.
            22/12/2014   Eshwari    Modified code to show company name from headerbean after shipping and for role 2 shipping
            29/12/2014   Eshwari    did Self Audit
            31/12/2014   Eshwari    Modified code to get dimensions on change of location by calling getDimensionsAjax() method.
                                    Removed AascOracleShipmentShippingAddr class usage and changed it to AascOracleShippingAddr class 
                                    as AascOracleShipmentShippingAddr is a duplicate of  AascOracleShippingAddr
            02/01/2015   Y Pradeep  Added code for selecting Ship To Customer name from LOV and Ship To Locations from LOV. Also made these to LOV's as editable.
            06/01/2015   Y Pradeep  Added missed code getDimensionsAjax() while merging Ship To Location name.
            07/01/2015   Y Pradeep  Corrected footer name and fixed few issues we found after porting in server.
            08/01/2015   Y Pradeep  Modified code to hold shipmethod mapping, carrier pay method and carrier account number when error occured while shipping.
            12/01/2015   Suman G    Added referenceFlag1 and referenceFlag2 hidden fields for #2491.
            14/01/20015  Suman G    Added maxlength attribute for ship to city and state for fix #2518.
            20/01/2015   Sunanda K  Added "--Select--" related code for bug #2543.
            21/01/2015   Suman G    Added errorFlag and defaultPayMethod hidden fields for solving issue #2493.
            30/01/2015   Suman G    Added "receipentAccNo" & "thirdPartyAccNo" hiddent files for #2520.
            03/02/2015   Suman G    Added "satDeliveryCheck" hiddent filed for #2490.
            12/02/2015   Eshwari M  Removed duplicates
            16/02/2015   Y Pradeep  Modified code to generate order number on click og Ship button in Shipping page.
            18/02/2015   K Sunanda  Modified code for bug fixes #2613 #2614
            24/02/2015   Y Pradeep  Modified code for Address Validation to diplay Validate Address button if Address Validation checkbox is checked in Profile Options.
            25/02/2015   Y Pradeep  Modified code for Address Validation.
            18/02/2015   Suman G    Added freight shop button.
            26/02/2015   Y Pradeep  Converted stauday flag check box to struts 2 tag.
            27/02/2015   Suman G    Modified code for freight shop alert issue.
            03/03/2015   Suman G    Added get rates button.
            09/03/2015   Suman G    Changed shipmentDate to shipDate for IE rate request
            05/03/2015    Sanjay & Khaja Added code for new UI changes.
            10/03/2015   Sunanda K  Added code for bug fix #2613
            10/03/2015  Y Pradeep   Removed unused code related to Printer and Templet fields.
            05/03/2015  Suman G     Added code for showing rates which got from Freight Shop and Get Rates and For Get Rates button.
            12/03/2015  Y Pradeep   Removed unused code related to get "ShipMethodInfo" from session.
            12/03/2015  Y Pradeep   Modified code to display confirmation message when there is a warning message from carrier.
            12/03/2015  Suman G     Added code for print button.
            13/03/2015  Y Pradeep   Modified code to displaying messages properly without affecting alignment, Email and Residential saving which was missed.
            13/03/2015  Suman G     Added code for Ajay Demo.
            14/03/2015  Suman G     Added for text field.
            15/03/2015  Y Pradeep   Renamed style attribute to cssStyle for orderNumber TextField.
            15/03/2015  Y Pradeep   Renamed commentted code for page too large issue
            15/03/2015  Y Pradeep   Added code to Advance Search link.
            18/03/2015  Suman G     Modified code at if condition for details button of Paymethod type.
            23/03/2015  Sunanda k   Modified code for bug fix #2613 for addressline 3
            23/03/2015  Suman G     Added code for issue #2694.
            24/03/2015  Suman G     Moved hidden fields to aascShipmentGetRates.jsp for page too large issue.
            24/03/2015  Suman G     Reverted the changes made for Ajay's Demo.
            26/03/2015  Y Pradeep   Renamed orderNumberId to orderNumberSearchId.
            26/03/2015  Y Pradeep   Added setResidentialFlag() for residential flag tag.
            31/03/2015  k Sunanda   Modified code for bug fix #2691
            01/04/2015  Y Pradeep   Added nullStringToSpace for packaging type and dropoff type fields. Bug #2760.
            01/04/2015  Suman G     Added buttons to fix #2784.
            07/04/2015  Y Pradeep   Added jQuery.noConflict() code to avoid conflicts for shipmentDate date picker. Bug #2806.
            16/04/2015  Y Pradeep   Added id's to the missed buttons.
            16/04/2015  Suman G     Added code to fix issues in error case and also for changing get rates ids.
            21/04/2015  Y Pradeep   Modified code to remove edit functionality for Customer Name in ship to details and added code to disable backspace functionality on select fields. Bug #2744.
            22/04/2015  Suman G     Modified code to fix the issue #2730.
            23/04/2015  Y Pradeep   Removed footer.
            27/04/2015  Y Pradeep   Added this jQuery code to disable F5 refresh button functionality for Shipping page. Bug #2857.
            27/04/2015  Eshwari     Added isItemShippedID, shipFromLocationIdHidden, shipToCountryHidden hidden fields to reduce the multiple Ajax callc and laod time
            27/05/2015  Eshwari, Pradeep  Added chngCarrierPayMethodHidden field and setCarrierPayMethodHidden() function for changing the carrier a/c no. to default a/c no. on change of any of the LOV's
            05/05/2015  Y Pradeep   Added carrierNameHide hidden field to save carrier name on change of ship method. Bug #2693 and #2901.
            08/05/2015  Suman G     Modified details button for carrier pay methods to fix #2911
            26/05/2015  Suman G     Added doctype suggested by sanjay  to fix #2945
            27/05/2015  Y Pradeep   Modified code to display proper message when package level voide is done and also removed unnecessary code to avoide page too large issue. Bug #2915.
            27/05/2015  Suman G     Added code to fix #2936
            27/05/2015  Suman G     Added if conditions to fix #2941 by Vikas.
            27/05/2015  Y Pradeep   Modified code to display and allow Order Numbers with special characters(Encode and Decode).
            03/06/2015  K Sunanda   Modified code for bug fix #2957
            03/06/2015  Y Pradeep   Added code required for Weighing Scale. Moved code to aascShipmentHelp.jsp to avoide page too large issue.
            09/06/2015  K Sunanda   Modified code for bug fix #2958
            09/06/2015  Suman G     Added code to fix #2967.
            11/06/2015  Y Pradeep   Removed applet tag to avoid 'Java(TM) was blocked because it is out of date (Update plug-in... / Run this time)' notification. Bug #2979.
            11/06/2015  K Sunanda   Modified code for bug fix #2997
            11/06/2015  Suman G     Removed fevicon to fix #2992
            12/06/2015  K Sunanda   Modified code for bug fix #2968
            12/06/2015  Y Pradeep   Removed editable functionality for Ship From Location LOV.
            16/06/2015  Y Pradeep   Modified code to get error and key variables in request instead of session when void button is clicked. Bug #2968.
            22/06/2015  Rekesh      Modified code for bug fix #3052.
            24/06/2015  Y Pradeep   Removed event at getShipFromAddress() function call.
            25/06/2015  Suman G     Added condition based on active flag to fix #3079
            02/07/2015  Rakesh K    Modified alignment in screen for tablet
            07/07/2015  Rakesh K    Added class="table-responsive" to all the 3 tables to solve alignment issue in tablets.
            07/07/2015  Rakesh K    Height of the table is increased due to alignment issue in Tablets.
            13/07/2015  Y Pradeep   Added code related to label printing and other hidden variables, added Print Label button for label printing.
            21/07/2015    Suman G        Modified message id for QA
            22/07/2015    Rakesh K  Added ID code for SHIP.
            24/07/2015  Rakesh K    Modified Code to work application in offline.
            28/07/2015  Y Pradeep   Removed code to check ship flag while loading printer applet. 
            29/07/2015  Rakesh K    Modified code to display Calender symbol. 
            30/07/2015  Y Pradeep   Removed applet tag for printer loading to load applets dynamically based on ship from location. Bug #3292 and #3289.
            04/08/2015  Rakesh K    Modified Code bootstrap css file work across all browsers.
            30/07/2015  Y Pradeep   Added applet tag for printer loading to load applets. Bug #3292 and #3289. 
            20/08/2015  Rakesh K       Removed code added for drop down issue 2895.
            26/08/2015  Rakesh K       Added code to solve 3496.
            27/08/2015  N Srisha    Added Id's for automation testing
            21/09/2015  Rakesh K    Added JS files end of the JSP to improve screen loading #3560.
            28/10/2015  Suman G     Added code to fix #3563			
            30/10/2015  Shiva       Added Addional Info field for DHL
            04/11/2015  Suman G     Added code to fix #3888, calender issue and send mail to customer when transaction balance completed.
            04/11/2015  N Srisha    Modified code to disable/enable paymethod details button. Bug #3561 and #3562
            05/11/2015  Eshwari M   Added import flag hidden field for bug #  2948 and shipFromCountryHiddenId for the bug # 2989
            06/11/2015  Eshwari M   Added maxlength = 2 for state and country in both Ship To and Ship From to fix bug #2960
            09/11/2015  Suman G     Changed alignment of showing error message to fix #3933.
            10/11/2015  Y Pradeep   Added labelPath hidden field to get cloud label path Bug #3945.
            20/11/2015  Suman G     Added condition to fix #3978
            20/11/2015  Y Pradeep   Added class and width attributes for orderNumber textfield in Shipping page to display Order Number as expected. Bug #3989.
            20/11/2015  Shiva G     Added errorStatusFlag to fix the issue 3979
            24/11/2015  Shiva G     Removed errorStatusFlag
            27/11/2015  Mahesh V    Added code for Thirdparty UPS and FedEx development.
            03/12/2015  Y Pradeep   Modified code to make Customer Name as editable in Ship To details sectionand added code to validate and allow only particular special characters.
            18/12/2015  Y Pradeep   Modified shipmentdate field format to display system timestamp. Bug #4095.
            28/12/2015  Suman G     Commented code to fix #4156
            24/02/2016  Suman G     Divided for too large issue.
+===========================================================================*/%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.*"%>
<%@page import="com.aasc.erp.model.*"%>
<%@ page import="com.aasc.erp.bean.*"%>
<%@page import="com.aasc.erp.util.*"%>
<%@page import="oracle.jdbc.driver.*"%>
<%@page import="com.aasc.erp.carrier.*" %> 
<%@ page errorPage="aascShipError.jsp"%>
<%! private static Logger logger=AascLogger.getLogger("aascShipment"); %>
<html>
  <head>
  <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link href="https://kendo.cdn.telerik.com/2014.3.1411/styles/kendo.material.min.css" rel="stylesheet" />
    <link href="kendo.common-material.min.css" rel="stylesheet" />
    
    <!-- Required scripts for QZ Tray -->
    <script type="text/javascript" src="js/dependencies/rsvp-3.1.0.min.js"></script>
    <script type="text/javascript" src="js/dependencies/sha-256.min.js"></script>
    <script type="text/javascript" src="js/qz-tray.js"></script>
    <script type="text/javascript" src="js/additional/jquery-1.11.3.min.js"></script>
    
    <script language="javascript" src="bootstrap/js/jquery11.min.js" type="text/javascript"></script>
    <script language="javascript" src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="kendo.all.min.js" type="text/javascript"></script>
    
    <script language="JavaScript" src="aasc_Shipment_JS.js" type="text/javascript"></script>
    <script language="JavaScript" src="aascPrinter_js.js" type="text/javascript"></script>
    
    <title>ShipConsole :: Shipping</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9"></meta>
    <link type="text/css" rel="stylesheet" href="aasc_ss.css"/>
    <link type="text/css" rel="stylesheet" href="aasc_index.css"/>
   
    
    
     <style type="text/css">
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
    
    
    <script type="text/javascript">
        var j = jQuery.noConflict();
        j(document).ready(function() {
        var shipDate = document.getElementById("shipmentDateTempId").value;
            // create DatePicker from input HTML element
//                $("#shipmentDate").kendoDatePicker();
            j("#shipmentDate").kendoDatePicker({
                value: shipDate == ''? new Date():shipDate,
                parseFormats:["yyyy-MM-dd HH:mm:ss"],
                format: "yyyy-MM-dd HH:mm:ss"
            }).data("kendoDatePicker");

        });
        
        j(function () {
            j(document).keydown(function (e) {
                return (e.which || e.keyCode) != 116;
            });
        });
        </script>
        
    <link type="text/css" rel="stylesheet" href="css/Theme1.css"/>
    <link rel="stylesheet" type="text/css" href="css/style4.css" />
    
    <script type="text/javascript">
 
  try{
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
          
          document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].value=$( element ).val();
          document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value=$( element ).val();
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
            //  .val( "" )
            //  .attr( "title", value + " didn't match any item" )
            //  .tooltip( "open" );
           // select.val( "" );
//            setTimeout(function() {
//             // input.tooltip( "close" ).attr( "title", "" );
//            }, 2500 );
          input.data( "ui-autocomplete" ).term = "";
          }
        }
     
        input = $( "<input>" )
          .appendTo( wrapper )
          .val( value )
          .attr( "title", "" )
          .attr("id",read())
        //  .addClass( "ui-state-default ui-combobox-input" )
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
              
            var customername=document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].value;
            var shipFromLoc=document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
            var customerNameOptionsArray= new Array();
            var customerNameOptions = document.getElementById('customerName');
            for (i = 1; i < customerNameOptions.options.length; i++) 
            {
                customerNameOptionsArray[i] = customerNameOptions .options[i].value;
              
                if(customername==customerNameOptionsArray[i])
                {
                    getShipToLocations();
                }
            }
            var shipFromLocOptionsArray= new Array();
            shipFromLocOptions = document.getElementById('shipFromLoc');
            for (i = 1; i < shipFromLocOptions.options.length; i++) 
            {
                shipFromLocOptionsArray[i] = shipFromLocOptions.options[i].value;
              
                if(shipFromLoc==shipFromLocOptionsArray[i])
                {
                    getShipFromAddress();//to add new Ship To address from shipment page
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
    $( "#customerName" ).combobox();
    $( "#toggle" ).click(function() {
      $( "#customerName" ).toggle();
    });
  });
  
  $(function() {
    $( "#shipFromLoc" ).combobox();
    $( "#toggle" ).click(function() {
      $( "#shipFromLoc" ).toggle();
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

<!-- Suman Added code for clear funcationality        -->
var a=1;
  function read(){
 
    if(a==1){
    a=2;
    return 'shipToId';}
    else{
    a=1;
    return 'shipFromId';}
    
  }
   }catch(e){
  }
  </script>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

  </head>
      <%! 
   /**
  * nullStringToSpace() method is used when the string is null it replaces with space.
  * @param object of type Object
  * @return object of type String.
  */  
  public String nullStringToSpace(Object object) {
    String spcStr = "";
    if (object == null || "null".equalsIgnoreCase(object.toString())) {
      return spcStr;
    }
    else {
      return object.toString();
    }
  }    
%>
      <%  
  int clientIdInt=-1;
  AascShipMethodInfo aascShipMethodInfo=null;   
  AascProfileOptionsBean aascProfileOptionsBean = null;
  ListIterator shipMethodIterator=null;
  AascShipmentFlags ShipmentFlags=null;
  try {  %>
      <body onload="fill();focus();load(event);" style="background-color:#ADADAD;">
  
      <div  class="container-fluid" style="background-color:#ADADAD; width:100%;overflow-x:hidden;">
         
          <s:set name="aascShipmentOrderInfo" value="%{#session.AascShipmentOrderInfo}"/>
          <s:set name="aascShipmentHeaderInfo" value="%{#aascShipmentOrderInfo.ShipmentHeaderInfo}"/>
          <s:set name="Sflag" value="%{#aascShipmentHeaderInfo.saturdayShipFlag}"/>
          <s:set name="ShipmentShipFromList" value="%{#session.locationDetailsList}"/>
          <progressbar id="progressbar" value="33"/>
          <%   
   try
   {
       aascShipMethodInfo=(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
       shipMethodIterator=(aascShipMethodInfo.getDropOffTypeList()).listIterator();   
       AascShipmentHeaderInfo aascShipmentHeaderInfo=(AascShipmentHeaderInfo)request.getAttribute("aascShipmentHeaderInfo");
       ShipmentFlags=new AascShipmentFlags(aascShipmentHeaderInfo);
   }
   catch(Exception e)
   {
        logger.severe("exception in aascShipment.jsp while creating object : "+e.getMessage());
   }
  
   
   int userId1=((Integer)session.getAttribute("user_id")).intValue();
   int roleId = ((Integer)session.getAttribute("role_id")).intValue();
   clientIdInt=(Integer)session.getAttribute("client_id");
   String clientId = ""+clientIdInt;
     
   try
   {
      String testclientId=clientId.substring(0,1);
   }
   catch(Exception e)
   {
     clientIdInt=(Integer)session.getAttribute("client_id");
   }
   pageContext.setAttribute("clientIdInt",clientIdInt);       
  
   aascProfileOptionsBean= (AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo");
   pageContext.setAttribute("companyNameProfile",aascProfileOptionsBean.getCompanyName());
  
  
%>
           
<script language="javascript">
var CarrierCodeDropOffA=new Array();
var DropOfftypeA=new Array();
var PackagingA=new Array();
var CarrierCodePackagingA=new Array();
var DropOflength = 0;
var PackagingLenth = 0;
function fillFedex()
  {
   <%  try{
   int methodCount =0; 
   while(shipMethodIterator.hasNext())
   {  
      AascShipMethodInfo aascShipMethodInfo2=(AascShipMethodInfo)shipMethodIterator.next(); 
      
   %>DropOfftypeA[<%=methodCount%>]="<%=aascShipMethodInfo2.getDropOffType()%>";<%
		     %>CarrierCodeDropOffA[<%=methodCount%>]="<%=aascShipMethodInfo2.getCarrierCode()%>";<%
         methodCount++;
         
    }  
   %>DropOflength="<%=methodCount%>";<%
   
    methodCount =0; 
    shipMethodIterator=(aascShipMethodInfo.getPackagingList()).listIterator();
   while(shipMethodIterator.hasNext())
  {  
     AascShipMethodInfo aascShipMethodBean= (AascShipMethodInfo)shipMethodIterator.next(); 
       %>PackagingA[<%=methodCount%>]="<%=aascShipMethodBean.getPackaging()%>";<%
		     %>CarrierCodePackagingA[<%=methodCount%>]="<%=aascShipMethodBean.getCarrierCode()%>";<%
         methodCount++;
         
    }  
   %>PackagingLenth="<%=methodCount%>";<%    
    }
 catch(Exception e)
    {
   logger.log(Level.SEVERE,"Exception in ship method",e);   
    }
    %>
  }
  </script><%}
 catch(Exception e)
    {
   logger.log(Level.SEVERE,"Exception in ship method",e);   
    }
    
%>
           
<s:bean id="aascShipMethodInfo" name="com.aasc.erp.bean.AascShipMethodInfo"/>
<s:bean id="carrierPayMethod" name="java.util.LinkedList"/>
<s:bean id="aascShipmentOrderInfo" name="com.aasc.erp.bean.AascShipmentOrderInfo"/>
<s:bean id="aascShipmentHeaderInfo" name="com.aasc.erp.bean.AascShipmentHeaderInfo"/>

<s:bean id="aascShipmentPackageInfo" name="com.aasc.erp.bean.AascShipmentPackageInfo"/>
<s:bean id="shipMethodList" name="java.util.LinkedList"/>
<s:set name="carrierPayMethodHide" value="%{''}"/>
<s:set name="shipMethodHidden1" value="%{''}"/>
<s:set name="carrierAccountNumberHid" value="%{''}"/>
<s:set name="carrierAccountNumber" value="%{''}"/>
<s:set name="shipMethod1" value="%{''}"/>
<s:set name="error" value="%{''}"/>
<s:set name="carrierId" value="%{0}"/>
<s:set name="carrierCode" value="%{0}"/>
<s:set name="shipMethodValidation" value="%{''}"/>
           
<s:set name="carrierProfileOptionsShipmethod" value="%{''}"/>
<s:set name="ProfileDropOffType" value="%{''}"/>
<s:set name="ProfileCarrierPaymethod" value="%{''}"/>
<s:set name="ProfileCarrierPackaging" value="%{''}"/>
<s:set name="sigOptions" value="%{''}"/>
<s:set name="CODFlag" value="%{''}"/>

<s:set name="ReturnFlag" value="%{''}"/>
<s:set name="CODFundsCode" value="%{''}"/>
<s:set name="CODCurrCode" value="%{''}"/>
<s:set name="CODType" value="%{''}"/>
<s:set name="halLine1" value="%{''}"/>
<s:set name="ProfileHazmatFlag" value="%{''}"/>
<s:hidden name="companyNameFromProfile" id="companyNameFromProfileID" value="%{#attr.companyNameProfile}" />

<s:set name="labelPathHide" value="%{#session.cloudLabelPath}"/>

<s:hidden name="labelPath" id="labelPathID" value="%{#session.cloudLabelPath}" />
<%         
//  AascShipmentFlags ShipmentFlags=null;  
  String url = request.getContextPath();
  
  // Added to pull UPS mode from Carrier configuration instead of ProfileOption page
  AascShipMethodInfo aascShipMethodInfoUPS = aascShipMethodInfo;//(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");        
  
  int carrierIdUPS = aascShipMethodInfoUPS.getCarrierIdFromCarrierCode(100);
  
  String connectionModeUPS = aascShipMethodInfoUPS.getCarrierMode(carrierIdUPS);
  
  pageContext.setAttribute("connectionModeUPS",connectionModeUPS);          
 %>
             
    <s:set name="connectionModeUPS" value="%{#attr.connectionModeUPS}"/>
    <s:set name="aascProfileOptionsInfo" value="%{#session.ProfileOptionsInfo}"/>
    
    <s:set name="defaultCarrierSessionValuesFlag" value="%{''}"/>
    <s:set name="defaultCarrierSessionValuesFlag" value="%{#aascProfileOptionsInfo.defaultCarrierSessionValuesFlag}"/>
             
    <s:if test="%{#defaultCarrierSessionValuesFlag == null}">
    <s:set name="defaultCarrierSessionValuesFlag" value="%{''}"/>
  </s:if>
           
  <s:if test='%{#defaultCarrierSessionValuesFlag == "Y"}'>
     <s:set name="aascCarrierProfileOptionsInfo" value="%{#session.CarrierProfileOptionsInfo}"/>
  </s:if>
           
  <s:set name="flag" value="%{0}"/>
  <s:set name="shipError" value="%{''}"/>
  <s:set name="errorDesc" value="%{''}"/>
           
  <s:set name="shipmentWarningStatus" value="%{''}"/>
  <s:set name="HALFlag" value="%{''}"/>
           
  <%  
  if(session.isNew())
  {
   response.sendRedirect(url+"/aascShipError.jsp");
  }
  %>
           
  <c:catch var="exception">
    <s:set name="aascShipMethodInfo" value="%{#session.ShipMethodInfo}"/>
    <s:set name="carrierPayMethod" value="%{#aascShipMethodInfo.carrierPayDetails}"/>
    <s:set name="aascShipmentOrderInfo" value="%{#session.AascShipmentOrderInfo}"/>
    <s:set name="aascShipmentHeaderInfo" value="%{#aascShipmentOrderInfo.ShipmentHeaderInfo}"/>
  </c:catch>
           
  <s:if test="%{#exception != null}">
       <c:redirect url="/aascShipError.jsp"/>
  </s:if>
           
  <%
   AascShipmentOrderInfo aascShipmentOrderInfo=null;
   AascShipmentHeaderInfo aascShipmentHeaderInfo=null;
   String shipMethodValidation="";
   LinkedList carrierPayMethod=null;
   ListIterator carrierPayMethodIterator=null;
   AascShipMethodInfo aascShipMethodInfoForTemplate=null;
   int packCount=0;
  
   String voidFlagDisable="";
   HashMap printerSetupDetailsHashMap = null;
   try
   {
     carrierPayMethod=aascShipMethodInfo.getCarrierPayDetails();
     carrierPayMethodIterator=carrierPayMethod.listIterator();
     printerSetupDetailsHashMap = aascProfileOptionsBean.getAascPrinterInfoHashMap();
     aascShipmentOrderInfo=(AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
     aascShipmentHeaderInfo=aascShipmentOrderInfo.getShipmentHeaderInfo();
     aascShipMethodInfoForTemplate = aascShipMethodInfo;//(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");

     if(aascShipmentHeaderInfo == null){
       ShipmentFlags = new AascShipmentFlags(new AascShipmentHeaderInfo());
     }else{
       ShipmentFlags=new AascShipmentFlags(aascShipmentHeaderInfo);
     }
   }
   catch(Exception e)
   {
     logger.severe("exception while creating the objects in the Shipment jsp"+e.getMessage());
     response.sendRedirect(url+"/aascShipError.jsp");
     logger.severe("Got exception:"+e.getMessage());
   }
  %>
  <s:set name="packageList" value="%{#aascShipmentOrderInfo.ShipmentPackageInfo}"/>
           
  <s:if test="%{#packageList == null}">
     <s:bean name="java.util.LinkedList" id="packageList"/>
  </s:if>
           
  <s:if test="%{#attr.key1 != null}">
    <s:set name="first" value="%{'DISABLED'}" scope="session"/>
  </s:if>
         
  <s:set name="firstDisable" value="%{''}"/>
   
  <c:catch var="ex">
    <s:set name="ProfileOptionsInfo" value="%{#session.ProfileOptionsInfo}"/>
  </c:catch>
   
  <c:if test="${ex!= null}">
    <c:out value="Exception Message while setting values at the start are: ${ex}"/>
  </c:if>
   
          <script>
 var ShipMethod=new Array();
 
 function fill()
 {
    getShipFromAddress();  //added by Jagadish to load ShipFrom details for imorted orders. 
}
</script>
    <% 
  try
  {
  %>
  

<s:set name="customerNameFlag" value="%{#aascProfileOptionsInfo.customerNameFlag}" />
<s:set name="addrLinesFlag" value="%{#aascProfileOptionsInfo.addrLinesFlag}" />
<s:set name="stateFlag" value="%{#aascProfileOptionsInfo.stateFlag}" />
<s:set name="cityFlag" value="%{#aascProfileOptionsInfo.cityFlag}" />
<s:set name="postalCodeFlag" value="%{#aascProfileOptionsInfo.postalCodeFlag}" />
<s:set name="countryCodeFlag" value="%{#aascProfileOptionsInfo.countryCodeFlag}" />


   <c:catch var="ex">
       
          <s:if test='%{#addrLinesFlag == "Y" || #addrLinesFlag == "y"}'>
           <s:set name="editAddrLines" value="%{''}"/>
          </s:if>
    
          <s:else>
             <s:set name="editAddrLines" value="%{'true'}"/>
          </s:else>
       
          <s:if test='%{#shipAddrEdit == "Y" || #shipAddrEdit == "y"}'>
          
                 <s:if test='%{#cityFlag == "Y" || #cityFlag == "y"}'>
                   <s:set name="editCity" value="%{''}"/>
                  </s:if>
                  <s:else>
                     <s:set name="editCity" value="%{'true'}"/>
                  </s:else>
                   
                  <s:if test='%{#stateFlag == "Y" || #stateFlag == "y"}'>
                   <s:set name="editState" value="%{''}"/>
                  </s:if>
            
                  <s:else>
                     <s:set name="editState" value="%{'true'}"/>
                  </s:else>
                   
                  
                  <s:if test='%{#postalCodeFlag == "Y" || #postalCodeFlag == "y"}'>
                   <s:set name="editPostalCode" value="%{''}"/>
                  </s:if>
            
                  <s:else>
                     <s:set name="editPostalCode" value="%{'true'}"/>
                  </s:else>
                  
                  <s:if test='%{#countryCodeFlag == "Y" || #countryCodeFlag == "y"}'>
                   <s:set name="editCountryCode" value="%{''}"/>
                  </s:if>
            
                  <s:else>
                     <s:set name="editCountryCode" value="%{'true'}"/>
                  </s:else>
                
           <s:set name="shipToAddEdit" value="%{''}"/>
          </s:if>
    
          <s:else>
             <s:set name="shipToAddEdit" value="%{'true'}"/>
             <s:set name="editCity" value="%{'true'}"/>
             <s:set name="editState" value="%{'true'}"/>
             <s:set name="editPostalCode" value="%{'true'}"/>
             <s:set name="editCountryCode" value="%{'true'}"/>
             
          </s:else>
   </c:catch> 
  
   <s:if test="%{#ex != null}">
        <s:set name="shipToAddEdit" value="%{''}"/>
        <!--<s:set name="editCustName" value="%{''}"/>  -->
        <s:set name="editAddrLines" value="%{''}"/>
        <s:set name="editCity" value="%{''}"/>
        <s:set name="editState" value="%{''}"/>
        <s:set name="editPostalCode" value="%{''}"/>
        <s:set name="editCountryCode" value="%{''}"/>
       <s:property value="%{'Exception While retrieving the Address Details'}" /> <s:property value="%{#ex}" />
   </s:if>
   
   
   <s:bean id="countryNameHashMap" name="java.util.HashMap"/>
    
    <c:catch var="exception1">
        <s:set name="countryNameHashMap" value="%{#session.countryNameHashMap}" />
 <!--      <s:set name="countryNameHashMap1" value="%{#attr.countryNameHashMap}" />
       aaa<s:property value="%{#countryNameHashMap}"/>bbb -->
        </c:catch>
    <s:if test="%{#exception1 != null}">
        <s:param  name="countryNameHashMap" value="%{'select'}"/>
    </s:if>
   
   <%@ include file="aascIndexHeader.jsp"%>

<%
                        String error=(String)session.getAttribute("error");
                        pageContext.setAttribute("error",error);
                    %>
<!--Include Header After all the  screen devlopment-->  

<div class="row" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #0F1819; border-style: solid;margin-left:3px;margin-right:3px;width:99%;margin-top: 3%;">
    <div class="col-sm-12" style="margin-bottom:10px">
    <s:form name="ShipGetForm" id="ShipGetFormId" method="POST" action="ShipmentOrderInfoAction"
                    onsubmit="return orderNumberValid()">
            <div class="col-md-3" style="margin-top:12px" align="center"> 
                <button type="button" class="btn btn-primary" name="submit11" id="newOrderId" onclick="ShipGetForm.submit.value='NewOrder';return orderNumberValid()">New Order <span class="badge"><span class="glyphicon glyphicon-plus"></span></span></button>   
            </div>
            <div class="col-md-4" style="margin-top:10px" align="center">
            
            <div style="background-color:#5BC0DE;height:40px;width:300px;border-radius:3px;padding-top: 5px;">
                <input type="text" name="orderNumberSearch" value="" id="orderNumberSearchId" class="inputsOrderNmber" style="color:#000" placeholder="Order Number" onclick="ShipGetForm.submit.value='Go'" />
                <input type="hidden" name="submit" value="%{''}" />
                <s:hidden name="orderNumberSearchHid" value="%{''}" />      
                     
                <button type="submit" id="GoButton"   onclick="ShipGetForm.submit.value='Go'"> <span class="glyphicon glyphicon-search"/> </button>

                <a href="#" id="advanceSearchLink" style="font-size:11px; font-weight:bold; text-decoration: underline; color:#ffffff" onclick="return advanceSearch();">Advanced Search</a> 
            </div>
            
            
            </div>
            <div class="col-md-5" style="margin-top:12px" align="center"> 
                <!-- modified Code -->
                    <div id="container_buttons">
                        <input type="hidden" name="saveButton" id="saveButtonID" value="">
                        <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                            <button type="button" class="btn btn-success" disabled>Ship <span class="badge"><span class="glyphicon glyphicon-cloud"></span></span></button>
                        </s:if>
                        <s:else>
                              <button type="button" class="btn btn-success" onclick="return shipButtonValue();" id="shipId">Ship <span class="badge"><span class="glyphicon glyphicon-cloud"></span></span></button>
                        </s:else>
                     &nbsp;&nbsp;&nbsp;
                        <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                       
                            <button type="button" class="btn btn-danger" id="voidId" onclick="return disableVoidSubmit();">Void <span class="badge"><span class="glyphicon glyphicon-minus"></span></span></button>
                        </s:if>
                        <s:else>
                            <button type="button" class="btn btn-danger" id="voidId" href="#" disabled>Void <span class="badge"><span class="glyphicon glyphicon-minus"></span></span></button>
                        </s:else>
                        &nbsp;&nbsp;&nbsp;
                        <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>                              
                            <button type="button" class="btn btn-info" id="viewLabelId" onclick="javascript:showLabelPopUp()">View Label <span class="badge"><span class="glyphicon glyphicon-minus"></span></span></button>
                            &nbsp;&nbsp;&nbsp;
                            <button type="button" class="btn btn-info" id="printLabelId" onclick="javascript:printLabel()">Print Label <span class="badge"><span class="glyphicon glyphicon-print"></span></span></button>
                        </s:if>    
                        <s:hidden value="%{#labelPathHide}"  name="labelPath" id="labelPath"/>
                        
                        <s:set name="printerName1" value="%{''}"/>  
                        <s:hidden name="printerName" id="printerName" value = "%{#printerName1}"/>
                    </div>
            </div>
     </s:form>
    
     </div>
</div>

<br/>

<div class="row">
 <s:form method="POST" action="ShipmentOrderShipSaveAction" name="DynaShipmentShipSaveForm" id="DynaShipmentShipSaveForm" >     
        <div class="row"> 
        <div class="col-sm-12">
            <s:set name="clientIdInt" value="%{#attr.clientIdInt}"/>
            <s:hidden name="clientMail" id="clientMailId" value="%{#attr.clientMailId}" />
          <s:hidden name="clientIdHid" id="clientIdHid" value="%{#clientIdInt}"/>
           
           
          <s:hidden name="InvVal" value="%{}"/>
          <s:hidden name="submitButton1" value="%{0}"/>
          <s:hidden name="submitButton" id="submitButtonId" value="%{''}"/>
          <s:hidden name="actionType" value="%{#shipSave}"/>
          <s:hidden name="upsMode" id="upsMode" value="%{#connectionModeUPS}"/>
          <s:set name="upsModeSet" id="upsModeSet" value="%{#connectionModeUPS}"/>



          <!-- Start code for print lables to get the label format -->
            <div id="lablePrinterId"></div>
                <!--<applet id="printer" name="Print" archive="./printer.jar, ./pdf-renderer.jar" code="printer.AascPrinterApplet.class"
                    width="0px" height="0px" ></applet>-->
          <%
            try{
                if(printerSetupDetailsHashMap != null)    {
                    Set keys = printerSetupDetailsHashMap.keySet();
                    String key;
                    String value;
                    Iterator printerIterator = keys.iterator();
                    while (printerIterator.hasNext()){
                        
                        key = (String)printerIterator.next();
                        value = (String)printerSetupDetailsHashMap.get(key);
                        if("PDF".equalsIgnoreCase(key)){
                            pageContext.setAttribute("pdfPrinter", value);
                        } else if("ZPL".equalsIgnoreCase(key) || "ZEBRA".equalsIgnoreCase(key)) {
                            pageContext.setAttribute("zplPrinter", value);                        
                        } else if("EPL".equalsIgnoreCase(key) || "ELTRON".equalsIgnoreCase(key)) {
                            pageContext.setAttribute("eplPrinter", value);                        
                        } else if("GIF".equalsIgnoreCase(key)) {
                            pageContext.setAttribute("gifPrinter", value);                        
                        }
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            
          %>
          
          <s:hidden name = "pdfPrinter" id="pdfPrinter" value="%{#attr.pdfPrinter}"/>
          <s:hidden name = "zplPrinter" id="zplPrinter" value="%{#attr.zplPrinter}"/>
          <s:hidden name = "eplPrinter" id="eplPrinter" value="%{#attr.eplPrinter}"/>
          <s:hidden name = "gifPrinter" id="gifPrinter" value="%{#attr.gifPrinter}"/><!-- End of code for print lables-->
          
          <s:if test='%{#aascShipmentHeaderInfo.shipFlag != "Y" || #aascShipmentHeaderInfo.shipFlag != "y"}'>      
            <div id="weightScaleId"></div>
           </s:if><!-- End of weighing scale code-->
          <s:set name="ratesFromGetRates" id="ratesFromGetRatesId" value="%{#attr.ratesFromGetRates}" />
          
          <%
                    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1);
                    AascShipmentOrderInfoDAO aascShipmentOrderInfoDAO = null;
                    aascShipmentOrderInfoDAO = aascDAOFactory.getAascShipmentOrderInfoDAO();
                    error=(String)request.getAttribute("error");
                    pageContext.setAttribute("error",error);
                    String orderNo = "";
                    if(aascShipmentHeaderInfo.getShipFlag().equalsIgnoreCase("N") && (aascShipmentHeaderInfo.getPreviousOrderNumber() != null && !"".equalsIgnoreCase(aascShipmentHeaderInfo.getPreviousOrderNumber()))){
                     orderNo=aascShipmentHeaderInfo.getPreviousOrderNumber();
                     aascShipmentHeaderInfo.setOrderNumber(orderNo+"");
                    }
                    else if(aascShipmentHeaderInfo.getShipFlag().equalsIgnoreCase("N") && aascShipmentHeaderInfo.getImportFlag().equalsIgnoreCase("N") )  //Import order condition added by Jagadish to retireve the import order from db instead of resetting. 
                    {
                      
                      if("".equalsIgnoreCase(aascShipmentHeaderInfo.getOrderNumber()) && aascShipmentHeaderInfo.getOrderNumber() == null){
                        aascShipmentHeaderInfo.setOrderNumber(orderNo+"");
                      }
                    }
                    if(orderNo != null && !"".equalsIgnoreCase(orderNo)){
                        session.setAttribute("orderNoTmp",orderNo);
                    }
                    session.removeAttribute("shipment");
                    String shipment = "Shipment";
                    session.setAttribute("shipment",shipment);
                    pageContext.setAttribute("aascShipmentHeaderInfo",aascShipmentHeaderInfo);
 String shipReadOnly=ShipmentFlags.getShipReadonlyFlag();
                             String readOnlyFlag1=ShipmentFlags.getReadOnlyFlag();  String readOnlyFlag="";

  if(readOnlyFlag1.equalsIgnoreCase("READONLY")){
                             //System.out.println("inside if");
                             readOnlyFlag="true";
                             }
                             else{
                             readOnlyFlag=readOnlyFlag1;
                             }

                           
                             pageContext.setAttribute("readOnlyFlag",readOnlyFlag);
                             pageContext.setAttribute("ShipmentFlags",ShipmentFlags);
     
                                String disableFlagTmp=ShipmentFlags.getDisableFlag();
                                if("DISABLED".equalsIgnoreCase(disableFlagTmp)){
                                    disableFlagTmp="true";          
                                }
                                pageContext.setAttribute("disableFlagTmp",disableFlagTmp);
                             %>
                               <%!int selectedCarrier=0;%>
                            <%
                              
                              aascShipmentOrderInfoDAO = aascDAOFactory.getAascShipmentOrderInfoDAO();
                              if((aascShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("Y"))
                              {
                                AascShipmentOrderInfo aascShipmentOrderInfoSaved2 =  aascShipmentOrderInfoDAO.getShipmentOrderInfo(aascShipmentHeaderInfo.getOrderNumber(),clientIdInt);       
                                Double freightcostShip=aascShipmentOrderInfoSaved2.getShipmentHeaderInfo().getFreightCost();
                                Double shipcostShip=aascShipmentOrderInfoSaved2.getShipmentHeaderInfo().getShipmentCost();
                                aascShipmentHeaderInfo.setFreightCost(freightcostShip);
                                aascShipmentHeaderInfo.setShipmentCost(shipcostShip);
                                aascShipmentHeaderInfo.setDropOfType(aascShipmentOrderInfoSaved2.getShipmentHeaderInfo().getDropOfType());
                                aascShipmentHeaderInfo.setPackaging(aascShipmentOrderInfoSaved2.getShipmentHeaderInfo().getPackaging());
                                aascShipmentHeaderInfo.setCarrierPaymentMethod(aascShipmentOrderInfoSaved2.getShipmentHeaderInfo().getCarrierPaymentMethod());
                              }
              
                              String ShipCostStr=Double.toString(aascShipmentHeaderInfo.getShipmentCost());
                              int i =ShipCostStr.indexOf(".");
                              if(i<1)
                              {
                              ShipCostStr=ShipCostStr+".00";
                              }
                              else if((ShipCostStr.length()-i)>2)
                              {
                              ShipCostStr=ShipCostStr.substring(0,i+3);
                              }
                              else
                              {
                                 while(ShipCostStr.length()!=(i+3))
                              {
                                ShipCostStr=ShipCostStr+"0";
                              }
                            }
                            pageContext.setAttribute("ShipCostStr",ShipCostStr);

                              String freightCostStr=Double.toString(aascShipmentHeaderInfo.getFreightCost());
                              i =freightCostStr.indexOf(".");
                              if(i<1)
                              {
                                    freightCostStr=freightCostStr+".00";
                              }
                              else if((freightCostStr.length()-i)>2)
                              {
                                freightCostStr=freightCostStr.substring(0,i+3);
                              }
                              else
                              {
                                 while(freightCostStr.length()!=(i+3))
                                 {
                                   freightCostStr=freightCostStr+"0";
                                 } 
                              }
                              pageContext.setAttribute("freightCostStr",freightCostStr);
                              pageContext.setAttribute("aascShipmentHeaderInfo",aascShipmentHeaderInfo);
                            %>
               <div class="col-sm-4">             
          <s:property value="getText('OrderNumber')"/>

                          <s:set name="aascShipmentHeaderInfo"  value="%{#attr.aascShipmentHeaderInfo}"/>
                        
                        <s:hidden name="isItemShipped" id="isItemShippedID" value="%{#attr.readOnlyFlag}" ></s:hidden>
                      
                      <s:textfield name="orderNumber" id="orderNumberID" cssClass="inputs" cssStyle="width:40%;" value="%{#aascShipmentHeaderInfo.orderNumber}" readonly="true" /> 
                  </div>  
        <div class="col-sm-4">
            <table width="100%" >
                        <tr>
                            <td width="100%">
                              <c:catch var="exception1">
                                <s:set name="key" value="%{#attr.key}"/>
                                    <s:set name="numOfVoidedPackagesValue" value="%{#request.numOfVoidedPackagesValue}" />
                                    <s:set name="voidStatusFlagVal" value="%{#request.voidStatusFlagVal}" />
                                    
                                    <s:if test="%{#key != null}">
                                        <s:if test="%{#key=='aasc225'||#key=='aasc130'||#key=='aasc227'||#key=='aasc301' ||#key=='aasc53' ||#key=='aasc200'}">  <!-- Fixed for #2583  -->
                                           <s:if test="%{(#voidStatusFlagVal != null) && (#numOfVoidedPackagesValue != null)}">
                                              <s:if test="%{(#key == 'aasc130' || #key1 == 'AASC130') && (#voidStatusFlagVal == 'PackageVoid' || #voidStatusFlagVal == 'PACKAGEVOID')}">
                                                <s:if test="%{#numOfVoidedPackagesValue.length() > 1}">
                                                    <s:set name="key" value="%{'aasc162'}" />
                                                </s:if>
                                                <s:if test="%{#numOfVoidedPackagesValue.length() <= 1}">
                                                    <s:set name="key" value="%{'aasc163'}" />
                                                </s:if>
                                              </s:if>
                                           </s:if>
                                                <div class="displayMessage1" id="displayMessage" style="color:#0000b3" align="left" valign="bottom">
                                                <s:property value="getText(#key)"/> 
                                                <s:hidden name="errorFlag2" id="errorFlag2" value="success" />
                                            </div>
                                        </s:if>
                                        <s:else>
                                            <div class="displayMessage" id="displayMessage" align="left" valign="bottom">
                                                <s:property value="getText(#key)"/>
                                                <s:hidden name="errorFlag2" id="errorFlag2" value="error" />
                                            </div>
                                        </s:else>
                                    </s:if>
                                    <s:hidden name="shipKey" id="shipKey" value="%{#attr.key}"/>
                                        <s:set name="key" value="%{'null'}"/>
                              </c:catch>

                            <s:set name="error" value="%{#attr.error}"/>
                            <s:set name="shipError" value="%{#request.shipError}"/>
                            <s:hidden name="shipError" value="%{#request.shipError}"/>
                            <s:hidden name="errorDesc" value="%{#request.errorDesc}"/>
                            <s:if test="%{#shipError != 'warning' && #shipError != 'note'}">
                                <s:if test="%{#error != ''}">
                                    <div class="displayMessage" id="displayMessage" align="left" valign="bottom">
                                        <s:property value="%{#error}"/>
                                        <s:hidden name="errorFlag" value='error' />
                                    </div>
                                    <s:set name="error" value="%{''}"/>
                                </s:if>
                                <s:else>
                                  <s:hidden name="errorFlag" value='' />
                                </s:else>
                            </s:if>
                            <s:else>
                               <s:hidden name="errorFlag" value='' />
                               <s:if test="%{#error != ''}">
                                    <div class="displayMessage" id="displayMessage" align="left" valign="bottom">
                                        Warning Message : <s:property value="%{#error}"/>
                                    </div>
                                    <s:set name="error" value="%{''}"/>
                                </s:if>
                            </s:else>
                          </td>
                        </tr>
                        <tr>
                           <td width="30%">
                                <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                                        <img src="images/aasc_tick.gif" width="16" height="16"/>
                                        <span class="displayMessage1" style="color:black;"> <s:property value="getText('Shipped')"/> </span>
                                </s:if>
                                &nbsp;&nbsp; 
                                <span id="tickPic"></span>
                                &nbsp;&nbsp; 
                                <span class="displayMessage" id="intlMassage"></span>
                            </td>
                        </tr>
                      </table>
        </div>
        <div class="col-sm-4">
        <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                        <s:hidden name="freightCharges" id="freightChargesId" value="%{#aascShipmentHeaderInfo.freightCost}" />
                        <s:hidden name="freightCost" id="freightCostId" value="%{#attr.ShipCostStr}" />
                        <s:hidden name="totalWeight" id="totalWeightId" value="%{#aascShipmentHeaderInfo.packageWeight}" />
                        <div class="dispalyFields" style="text-align:center; border-width: 1px;border-color:#19b698; overflow:auto;background-color:#F0F0F0;border-style: solid;border-radius:10px;width:98%">
                           <div class="row" style="margin-left:1%;margin-right:1%;margin-top:1%;margin-bottom:1%;">
                            <img src="images/currency.png" align="middle" />
                            &nbsp;&nbsp;&nbsp;
                                <s:property value="getText('FreightCost')"/>
                                &nbsp;&nbsp;&nbsp;
                                <s:property value="%{#attr.ShipCostStr}" default="0.0"/>/-
                                &nbsp;&nbsp;&nbsp;
                                 <span class="btn btn-primary" id="freightCostDetailsId" onclick="openFreightCostDetails()">Details</span>
                          </div>
                          </div>
                          </s:if> 

        </div>
        <s:if test="%{#exception1 != null}">
          <c:redirect url="/aascShipError.jsp">
            <s:param name="errorObj" value="%{#exception1.message}" />
          </c:redirect>
        </s:if>
        </div>
        </div>
         <br/>
<div class="row">
    <div class="col-sm-12">
        <div class="col-sm-4">
            <div style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #19b698; border-style: solid;height:677px">
                <div class="table-responsive" style="background-color:#F0F0F0; margin-top:10px; margin-bottom:10px; border-radius: 5px; width:94%; margin-left:13px">
                    <table class="table">
                        <thead>
                        <tr>
                            <th style="background-color:#19b698;color:#ffffff;font-size:12px;font-weight: bold;">Ship From</th>
                            <th style="background-color:#19b698"> </th>
                        </tr>
                        </thead>
                        <tbody>
                          <tr align="left" valign="top">
                            <td class="dispalyFields" nowrap="nowrap">   <s:property value="getText('ShipperName')"/></td>
                            <td align="right">
                               <s:textfield name="shipperName" id="shipperNameID" cssClass="inputs"  value="%{#aascProfileOptionsInfo.shipperName}" readonly="true" /> 
                            </td>
                          </tr>
                          <tr align="left" valign="top">
                            <td class="dispalyFields" nowrap="nowrap">  <s:property value="getText('CompanyName')"/> </td>
                            <td align="right">
                               <s:textfield name="companyName" cssClass="inputs" id="companyNameId" value="%{#aascShipmentHeaderInfo.shipFromCompanyName}"/>
                            </td>
                          </tr>
                          <tr align="left" valign="top">
                            <td class="dispalyFields" nowrap="nowrap">  <s:property value="getText('LocationName')"/>
                            </td>
                            <td align="right">
                              <%
                                int userLocationId = 0 ;
                                try{
                              %>
                              <div class="ui-widget" id="ui-widgetShipFromId">
                               <%
                                  StringTokenizer strToken=new StringTokenizer(nullStringToSpace(aascShipmentHeaderInfo.getShipFromLocation()),"****");
                                  String trimmedShipFromLoc="";
                                  while (strToken.hasMoreElements()) {
                                     trimmedShipFromLoc=strToken.nextElement().toString();
                                   
                                     break;
                                  }
                                  pageContext.setAttribute("trimmedShipFromLoc",trimmedShipFromLoc);
                               %>
                               <select name="shipFromLoc" id="shipFromLoc" onchange="getShipFromAddress();getDimensionsAjax();" onkeydown="backspace(event);" class="inputs" <%=nullStringToSpace(ShipmentFlags.getReadOnlyFlag())%> ><!--Sunanda added for bug fix #2957-->
                               <%
                                  if(ShipmentFlags.getSelectDisableFlag().equalsIgnoreCase("DISABLED") )
                                  {
                               %>
                                  <option value="<%=nullStringToSpace(aascShipmentHeaderInfo.getShipFromLocation())%>">
                                    <%=nullStringToSpace(trimmedShipFromLoc)%>
                                  </option>
                               <% }
                                  else{
                               %>
                                  <option value="--Select--"> --Select-- </option>
                               <%
                                    int count=0;
                                    LinkedList shipFromList1=(LinkedList)session.getAttribute("locationDetailsList");
                                    
                                    ListIterator shipFromIterator=null;
                                    shipFromIterator=(shipFromList1).listIterator(); 

                                    if("Y".equalsIgnoreCase(aascShipmentHeaderInfo.getShipFlag()))
                                    {  
                                       userLocationId = aascShipmentHeaderInfo.getLocationId();
                                    }
                                    else{
                                        if(aascShipmentHeaderInfo.getLocationId() == 0)
                                            userLocationId = ((Integer)session.getAttribute("location_id")).intValue() ;
                                        else
                                            userLocationId = aascShipmentHeaderInfo.getLocationId();
                                    }   
                                    while(shipFromIterator.hasNext())
                                    {
                                      AascSetupLocationBean aascSetupLocationBean=(AascSetupLocationBean)shipFromIterator.next();   // Ship From Location
                                      
                                      if("Y".equalsIgnoreCase(aascSetupLocationBean.getLocationStatus())){
                                      pageContext.setAttribute("aascSetupLocationBean",aascSetupLocationBean);
                                      
                                      if(!nullStringToSpace(aascShipmentHeaderInfo.getShipFromCompanyName()).equalsIgnoreCase(""))
                                      {
                                        if(!(aascSetupLocationBean.getLocationName()+"****"+aascSetupLocationBean.getLocationId()).equalsIgnoreCase(nullStringToSpace(aascShipmentHeaderInfo.getShipFromLocation())))
                                        {
                                          count++;
                                        }
                                      }
                                      if((aascSetupLocationBean.getLocationName()+"****"+aascSetupLocationBean.getLocationId()).equalsIgnoreCase(nullStringToSpace(aascShipmentHeaderInfo.getShipFromLocation()))
                                           ||  aascSetupLocationBean.getLocationId() == userLocationId)  //  eshwari modified this comdition to default the Shipfrom location when page is opened
                                      {
                                %>
                                  <option selected="selected" value='<%=(aascSetupLocationBean.getLocationName()+"****"+aascSetupLocationBean.getLocationId())%>'> <%=nullStringToSpace(aascSetupLocationBean.getLocationName())%> </option>
                                <%    }else
                                      {
                                %>
                                  <option value='<%=(aascSetupLocationBean.getLocationName()+"****"+aascSetupLocationBean.getLocationId())%>'> <%=nullStringToSpace(aascSetupLocationBean.getLocationName())%> </option>
                                <%
                                      }
                                      }
                                    }
                                    if(count>0)
                                    {
                                        
                                       if("Y".equalsIgnoreCase(aascShipmentHeaderInfo.getShipFlag()))
                                       {
                                %>
                                 <option selected="selected" value='<%=(aascShipmentHeaderInfo.getShipFromLocation())%>'> <%=nullStringToSpace(trimmedShipFromLoc)%> </option>
                                <%
                                    }
                                // code ended..
                                    }
                                }
                                }                 
                                catch(Exception e)
                                {logger.severe("Got Exception:"+e.getMessage());}           
                              %>
                              <s:hidden name="shipFromLocationIdHidden" id="shipFromLocationIdHidden" value="%{#aascShipmentHeaderInfo.shipFromLocation}"/> 
                              </select>
                              </div>
                            </td>
                            
                          </tr>
                          
                          
                          <tr align="left" valign="top">
                            <td class="dispalyFields" > <s:property value="getText('AddressLine1')"/> </td>
                            <td align="right">
                               <s:textfield name="shipFromAddressLine1" cssClass="inputs" id="shipFromAddressLine1" value="%{#aascShipmentHeaderInfo.shipFromAddressLine1}"/>
                            </td>
                          </tr>
                          <tr  valign="top">
                           <td class="dispalyFields" align="left">  <s:property value="getText('AddressLine2')"/></td>
                            <td align="right">
                                <s:textfield name="shipFromAddressLine2" cssClass="inputs" placeholder="Address Line 2" id="shipFromAddressLine2" value="%{#aascShipmentHeaderInfo.shipFromAddressLine2}"/>
                            </td>
                          </tr>
                             <tr align="left" valign="top">
                            <td class="dispalyFields"> <s:property value="getText('AddressLine3')"/></td>
                            <td align="right">
                                <s:textfield name="shipFromAddressLine3" cssClass="inputs" placeholder="Address Line 3" id="shipFromAddressLine3" value="%{#aascShipmentHeaderInfo.shipFromAddressLine3}"/>
                            </td>
                          </tr>
                          <tr align="left" valign="top">
                            
                           
                                <td class="dispalyFields">  <s:property value="getText('City')"/> </td>
                                <td align="right">
                                  <s:textfield name="shipFromCity" cssClass="inputs" id="shipFromCity"  value="%{#aascShipmentHeaderInfo.shipFromCity}"/>
                                </td>
                                 </tr>
                                 <tr align="left" valign="top">
                                  <td class="dispalyFields"> <s:property value="getText('State')"/> </td>
                                <td align="right">
                                  <s:textfield name="shipFromState" cssClass="inputs" id="shipFromState" onblur="shipFromStateChgCase()" value="%{#aascShipmentHeaderInfo.shipFromState}"/> </td>
                            
                                 </tr>
                        
                              <tr align="left" valign="top">
                              <td class="dispalyFields"> <s:property value="getText('CountryCode')"/> </td>
                                <td align="right">
                                <s:iterator value="countryNameHashMap">
                                                <s:set name="countryNameHashMapKey" value="%{#key}"/>
                                                <s:set name="countryNameHashMapValue" value="%{#value}"/>
                                    </s:iterator>
                                <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                                    <s:select list="countryNameHashMap" listKey="value" listValue="key" headerKey="Select" headerValue="Select" name="shipFromCountry" disabled="true"
							 class="inputs" onblur="shipFromCountryChgCase()" id="shipFromCountryId" value="%{#aascShipmentHeaderInfo.shipFromCountry}"/>
                                </s:if>
                                <s:else>
                                    <s:select list="countryNameHashMap" listKey="value" listValue="key" headerKey="Select" headerValue="Select" name="shipFromCountry"
							 class="inputs" onblur="shipFromCountryChgCase()" id="shipFromCountryId" value="%{#aascShipmentHeaderInfo.shipFromCountry}"/>
                                </s:else>
                                    <s:hidden name="shipFromCountryHiddenId" id="shipFromCountryHiddenId" value="%{#aascShipmentHeaderInfo.countrySymbol}"/>
                                </td>
                              </tr>
                                    <tr align="left" valign="top">
                                <td class="dispalyFields"> <s:property value="getText('PostalCode')"/> </td>
                                <td align="right">
                                  <s:textfield name="shipFromPostalCode" cssClass="inputs" id="shipFromPostalCode"  value="%{#aascShipmentHeaderInfo.shipFromPostalCode}"/> 
                                </td>
                                
                              </tr>
                            
                          
                          <tr align="left" valign="top">
                            <td class="dispalyFields"> <s:property value="getText('ContactName')"/> </td>
                            <td align="right">
                               <s:textfield name="shipFromContactName" cssClass="inputs" id="shipFromContactName" value="%{#aascShipmentHeaderInfo.shipperName}"/>
                            </td>
                          </tr>
                          
                      
                          
                          <tr align="left" valign="top">
                            <td class="dispalyFields">  <s:property value="getText('PhoneNumber')"/> </td>
                            <td align="right">
                              <s:textfield name="shipFromPhoneNumber1"  cssClass="inputs"  id="shipFromPhoneNumber1" value="%{#aascShipmentHeaderInfo.shipFromPhoneNumber1}"/>
                            </td>
                          </tr>
                               <tr align="left" valign="top">
                            <td class="dispalyFields">  <s:property value="getText('Email')"/> </td>
                            <td align="right">
                              <s:textfield name="shipFromEmail"  cssClass="inputs"  id="shipFromEmailId" value="%{#aascShipmentHeaderInfo.shipFromEmailId}"/>
                            </td>
                          </tr>
                        </tbody>
                     </table>   
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #ea6153; border-style: solid;height:677px">
               <div class="table-responsive" style="background-color:#F0F0F0; margin-top:10px; margin-bottom:10px; border-radius: 5px; width:94%; margin-left:13px"> 
                <table class="table">
                    <thead>
                      <tr>
                        <th style="background-color:#ea6153;color:#ffffff;font-size:12px;font-weight: bold;">Ship To</th>
                        <th style="background-color:#ea6153"> </th>
                      </tr>
                    </thead>
                <tbody>
                    <tr align="left">
                          <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('CustomerName')"/> </td>
                          <td align="right" valign="top" class="displayResults">
                            <% 
//                                int userId = (Integer)session.getAttribute("user_id");
                            
                                AascOracleShippingAddr aascOracleShippingAddr = new AascOracleShippingAddr();
                                List customerList = null;
                                Iterator customerListIterator = null;
                                AascShipToLocationsInfo aascShipToLocationsInfo = null;
                                int userId = ((Integer)session.getAttribute("user_id")).intValue();
                                String addressBookLevel = (String)session.getAttribute("addressBookLevel");
                                try
                                {
                                    customerList =(List)aascOracleShippingAddr.getShipToCustomers(clientIdInt, userId, addressBookLevel);
                                 }catch(Exception e)
                                 {
                                    logger.severe("Got Excetpion :"+e.getMessage());
                                 }
                                logger.info("customerList === "+customerList.size());
                                 try
                                 {
                                    customerListIterator = customerList.iterator();
                                 %>
                                 <div class="ui-widget" id="ui-widgetShipToId">
                                   <select name="customerName" id="customerName" size="1" onkeydown="fnKeyDownHandler(this, event);" onKeyUp="fnKeyUpHandler_A(this, event); return false;" 
                                        onKeyPress = "return fnKeyPressHandler_B(this, event);"  onChange="fnChangeHandler_A(this, event); getShipToLocations();"
                                            class="inputs" <%=nullStringToSpace(ShipmentFlags.getReadOnlyFlag())%>  >
                                      <% if(ShipmentFlags.getSelectDisableFlag().equalsIgnoreCase("DISABLED") )
                                         {
                                      %>
                                      <option value="<%=nullStringToSpace(aascShipmentHeaderInfo.getCustomerName())%>">
                                        <%=nullStringToSpace(aascShipmentHeaderInfo.getCustomerName())%>
                                      </option>
                                      <%}
                                      else{%>
                                      <option value="--Select--"> --Select-- </option>
                                      <%
                                      int count=0;
                                      while(customerListIterator.hasNext())
                                      {
                                         aascShipToLocationsInfo = (AascShipToLocationsInfo)customerListIterator.next();
                                         if(!nullStringToSpace(aascShipmentHeaderInfo.getCustomerName()).equalsIgnoreCase(""))
                                         {
                                            if(!(aascShipToLocationsInfo.getShipToCustomerName()).equalsIgnoreCase(nullStringToSpace(aascShipmentHeaderInfo.getCustomerName())))
                                            {
                                            count++;
                                            }
                                         }
                                         if(aascShipToLocationsInfo.getShipToCustomerName().equalsIgnoreCase(nullStringToSpace(aascShipmentHeaderInfo.getCustomerName())))
                                         {
                                      %>
                                      <option selected="selected" value="<%=aascShipToLocationsInfo.getShipToCustomerName()%>"> <%=aascShipToLocationsInfo.getShipToCustomerName()%> </option>
                                      <% }else
                                         {%>
                                      <option value="<%=aascShipToLocationsInfo.getShipToCustomerName()%>"> <%=aascShipToLocationsInfo.getShipToCustomerName()%> </option>
                                          <%
                                         }
                                      }
                                      if(count>0){
                                    // code start //additional IF condition  added by JOSEPH to fix #2941 issue
                                      if("Y".equalsIgnoreCase(aascShipmentHeaderInfo.getShipFlag())) {
                                      %>
                                      <option selected="selected" value="<%=nullStringToSpace(aascShipmentHeaderInfo.getCustomerName())%>"> <%=nullStringToSpace(aascShipmentHeaderInfo.getCustomerName())%> </option>
                                      <%
                                      }
                                  // code ended 
                                    }
                                  }         
                                  } catch(Exception e) {  
                                    e.printStackTrace();
                                  }
                                  %>
                                </select>
</div>
                          </td>
                        </tr>
                        <s:hidden name="shipMethodHidden" id="shipMethodHiddenId" value="%{#aascShipmentHeaderInfo.shippingMethod}"/>
                        <s:hidden name="shipMethodHide" id="shipMethodHideId" value="%{#shipMethodHidden}"/>
                        
                        
                        <s:set name="ajaxOriginRegionCode" value="%{#aascShipmentHeaderInfo.shipFromCountry}"/>
                        <s:set name="ajaxUpsMode" value="%{#connectionModeUPS}"/>
                        <s:set name="countryCodeProf" value="%{#aascProfileOptionsInfo.countryCode}"/>
                        
                        <%                            
                              String intlSaveFlag= (String)session.getAttribute("intlSaveFlag");
                              pageContext.setAttribute("intlSaveFlag",intlSaveFlag);
                        
                              session.removeAttribute("intlSaveFlag");
                              pageContext.setAttribute("locationIdint",userLocationId);
                        
                        %>
                        <s:hidden name="countryProf" id="countryProf" value="%{#countryCodeProf}"/>
                        
                        
                        <s:hidden name="intlSaveFlag" value="%{#attr.intlSaveFlag}"/>
                        <s:hidden name="shipmentType" value="%{'Shipment'}"/>
                        <s:hidden name="intTotalCustomsValue" value="%{#attr.intlSaveFlag}"/>
                        <s:hidden name="intlCustomsFlag" value="%{#session.intlCustomsFlag}"/>
                        
                        <s:hidden name="locationId" id="locationId" value="%{#attr.locationIdint}"/>
                       
                        <s:set name="intlCustomsFlag" value="%{'N'}" scope="session"/>
                        
                        <s:hidden name="ajaxOriginRegionCode" id="ajaxOriginRegionCode" value="%{#ajaxOriginRegionCode}"/>                       
                        <s:hidden name="ajaxUpsMode" id="ajaxUpsMode" value="%{#ajaxUpsMode}"/>
                        
                        
                        <tr align="left">
                           <td class="dispalyFields" nowrap="nowrap">  
                                <s:property value="getText('LocationName')"/>     
                           </td>
                           <td align="right">
                                <select name="customerLocation" id="customerLocationId" size="1" onkeydown="fnKeyDownHandler(this, event);" onkeyup="fnKeyUpHandler_A(this, event); return false;" 
                                        onkeypress="return fnKeyPressHandler_A(this, event);" onchange="fnChangeHandler_A(this, event);getShipToAddress();" class="inputs" <%=nullStringToSpace(ShipmentFlags.getReadOnlyFlag())%>  ><!--Sunanda added for bug fix #2957-->
                                    <option selected="selected" value="<%=nullStringToSpace(aascShipmentHeaderInfo.getShipToLocationName())%>"> <%=nullStringToSpace(aascShipmentHeaderInfo.getShipToLocationName())%> </option>
                                </select>
                            </td>
                        </tr>
                        <tr align="left">
                           <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('AddressLine1')"/> </td>
                           <td align="right" valign="top" class="displayResults"> 
                             <s:textfield name="shipToAddress"  id="shipToAddressId" placeholder="Address Line 1" value="%{#aascShipmentHeaderInfo.address}" cssClass="inputs" size="20" readonly="#editAddrLines" onkeydown="checkFlag2(event);" /><!--Sunanda added for bug fix #2613--> 
                           </td>
                        </tr>
                        <tr align="left">
                             <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('AddressLine2')"/> </td>
                            <td align="right">
                              <span class="displayResults">
                                <s:textfield name="shipToAddrLine2" id="shipToAddrLine2Id" placeholder="Address Line 2" value="%{#aascShipmentHeaderInfo.shipToAddrLine2}" cssClass="inputs"  size="20" maxlength="35" readonly="#editAddrLines" onkeydown="checkFlag3(event);" />
                              </span>
                            </td>
                        </tr>
                        <tr align="left">
                            <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('AddressLine3')"/> </td>
                            <td align="right">
                              <span class="displayResults">
                                <s:textfield name="shipToAddrLine3" id="shipToAddrLine3Id" placeholder="Address Line 3" value="%{#aascShipmentHeaderInfo.shipToAddrLine3}" cssClass="inputs"  size="20" maxlength="35" readonly="#editAddrLines"/>
                              </span>
                            </td>
                        </tr>
                 
                                <tr align="left">
                                  <td> <span class="dispalyFields"><s:property value="getText('City')"/>  </span> </td>
                                  <td align="right"> <span class="displayResults">
                                    <s:textfield name="city"  value="%{#aascShipmentHeaderInfo.city}"  cssClass="inputs"  id="city"  maxLength="60" readonly="#editCity" onkeydown="checkFlag4(event);" />
                                    </span> </td>
                                  
                                </tr>
                                <tr align="left">
                                <td align="left" valign="top" class="dispalyFields"><s:property value="getText('State')"/> </td>
                                  <td align="right" valign="top" class="displayResults">
                                    <s:textfield name="state" value="%{#aascShipmentHeaderInfo.state}" cssClass="inputs" id="state" maxLength="60" onblur="changeCase()" readonly="#editState" onkeydown="checkFlag5(event);" />
                                  </td>
                                </tr>
                                <tr align="left">
                                  <td align="left" valign="top" class="dispalyFields"> <s:property value="getText('CountryCode')"/> </td>
                                  <td align="right"> 
                                  <s:iterator value="countryNameHashMap">
                                                <s:set name="countryNameHashMapKey" value="%{#key}"/>
                                                <s:set name="countryNameHashMapValue" value="%{#value}"/>
                                  </s:iterator>
                                  <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                                      <s:select list="countryNameHashMap" listKey="value" listValue="key" headerKey="Select" headerValue="Select" name="countrySymbol" disabled="true"
                                                             class="inputs" onchange="countryValidate();" id="country" value="%{#aascShipmentHeaderInfo.countrySymbol}"/>
                                 </s:if>
                                 <s:else>
                                    <s:select list="countryNameHashMap" listKey="value" listValue="key" headerKey="Select" headerValue="Select" name="countrySymbol"
                                                             class="inputs" onchange="countryValidate();" id="country" value="%{#aascShipmentHeaderInfo.countrySymbol}"/>
                                 </s:else>
                                    <s:hidden name="shipToCountryHidden" id="shipToCountryHiddenId" value="%{#aascShipmentHeaderInfo.countrySymbol}"/>
                                 </td>
                                
                                </tr>
                                <tr align="left">
                                  <td align="left" valign="top" nowrap="nowrap" class="dispalyFields"> <s:property value="getText('PostalCode')"/> </td>
                                  <td align="right" valign="top" class="displayResults">
                                    <s:textfield name="postalCode" value="%{#aascShipmentHeaderInfo.postalCode}" cssClass="inputs" id="zip"  maxlength="45" readonly="#editPostalCode" onkeydown="checkFlag6(event);" />
                                  </td>
                                </tr>
                              
                        <tr align="left">
                          <td align="left" valign="middle" class="dispalyFields">  
                          <s:property value="getText('ContactName')"/>
                          </td>
                          <td align="right" valign="top">
                            <span class="style7">
                              <s:textfield name="contactName" cssClass="inputs" id="contactNameId" value="%{#aascShipmentHeaderInfo.contactName}" maxlength="30" readonly="%{#attr.readOnlyFlag}"/>
                            </span>
                          </td>
                        </tr>
                        <tr align="left">
                          <td align="left" valign="top" class="dispalyFields">
                            <s:property value="getText('PhoneNumber')"/>
                          </td>
                          <td align="right" valign="top">
                            <span class="style7">
                              <s:textfield name="phoneNumber" id="phoneNumberId" cssClass="inputs" value="%{#aascShipmentHeaderInfo.phoneNumber}" readonly="%{#attr.readOnlyFlag}"/>
                            </span>
                          </td>
                        </tr>
                       <tr align="left">
                          <td align="left" valign="top" class="dispalyFields">
                            <s:property value="getText('Email')"/>
                          </td>
                          <td align="right" valign="top">
                            <span class="style7">
                              <s:textfield name="shipToEmail" id="shipToEmailId" cssClass="inputs" value="%{#aascShipmentHeaderInfo.shipToEmailId}" readonly="%{#attr.readOnlyFlag}"/>
                            </span>
                          </td>
                        </tr>
                         <tr align="left">
                         <td class="dispalyFields" >
                         Residential 
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <s:if test='%{#aascShipmentHeaderInfo.residentialFlag == "Y"}' >
                                <s:set name="residentialCheckBoxValue" value="%{'true'}" />
                            </s:if>
                            <s:else>
                                <s:set name="residentialCheckBoxValue" value="%{'false'}" />
                            </s:else>
                            <s:checkbox name="residentialFlag" id="residentialFlagId" onclick="return setResidentialFlag();" fieldValue="%{#aascShipmentHeaderInfo.residentialFlag}" value="%{#residentialCheckBoxValue}" />
                         </td>
                            <input type="hidden" name="satDeliveryCheck" id="satDeliveryCheckID" value="${aascProfileOptionsInfo.enableSaturdayFlag}" />
                            <input type="hidden" name="freightShoppingCheck" id="freightShoppingCheckID" value="${aascProfileOptionsInfo.freightShopping}" />
                           <input type="hidden" name="addresValidationCheck" id="addresValidationCheckID" value="${aascProfileOptionsInfo.addresValidation}" />
                         <td colspan="2" align="center">
                            <s:if test='%{#aascProfileOptionsInfo.addresValidation == "Y"}' >
                                <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                                    <!--<img src="buttons/ValidateAddressOff.png" name="validateAddress" id="validateAddressId" />-->
                                    <span class="btn btn-success" name="validateAddress" id="validateAddressId" disabled>Validate Address <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></span>
                               </s:if >
                               <s:else>
                                    <span class="btn btn-success" onclick="return openAddressValidationPopup();" id="validateAddressId" >Validate Address <span class="badge"><span class="glyphicon glyphicon-ok"></span></span></span>
                               </s:else>
                            </s:if>
                         </td>
                        </tr>
                </tbody>
                </table>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
        
       
        <s:set name="freightCostStr" value="%{#attr.freightCostStr}"/>
                    <s:set name="aascShipmentHeaderInfo"  value="%{#attr.aascShipmentHeaderInfo}"/>     
                         
                      <s:hidden cssClass="inputFields"  name="totalSurcharge" id="totalSurchargeId" size="5"  value="%{#aascShipmentHeaderInfo.totalSurcharge}"  readonly="%{#attr.readOnlyFlag}"/>          
                      <s:hidden value="%{#carrierPayMethodHide}"  name="carrierPayMethodHide" id="carrierPayMethodHide"/>
           
                        <%try
                                {
                                    int CarrierCode1=0;
                                    shipMethodIterator=(aascShipMethodInfoForTemplate.getShipMethodDetailList()).listIterator();

                                    while(shipMethodIterator.hasNext())
                                    {  
                                      AascShipMethodInfo  aascShipMethodInfoBean = (AascShipMethodInfo)shipMethodIterator.next();                   
                                      if(aascShipMethodInfoBean.getEnabledFlag().equalsIgnoreCase("Y") )          
                                      {
                                        String shipMethodElementAlt=(String) aascShipMethodInfoBean.getAlternateShipMethod();//getShipMethodMeaning();// 
                                        String shipMethodElement = (String)aascShipMethodInfoForTemplate.getShipMethodFromAlt(shipMethodElementAlt);// 
                                        int carrierId2= aascShipMethodInfoForTemplate.getCarrierId(shipMethodElement);    
                                        int  CarrierCode2 = aascShipMethodInfoForTemplate.getCarrierCode(carrierId2); 
                                        String shipMethodValidationShipment= aascShipMethodInfoForTemplate.getShipmentValidationCode(shipMethodElement);
                                        String CarreirAC = aascShipMethodInfoForTemplate.getCarrierAccountNumber(carrierId2);
                                        if(aascShipmentHeaderInfo.getShipMethodMeaning().equals(shipMethodElement))
                                        {
                                          CarrierCode1=CarrierCode2;
                                          selectedCarrier=CarrierCode2;
                                          pageContext.setAttribute("selectedCarrier",selectedCarrier);
                                        }
                                      }
                                    }    
                                }
                                catch(Exception e)
                                {
                                    logger.severe("Got Exception:"+e.getMessage());
                                }
                              %>
                              
        
            <div style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #3d566d; border-style: solid;height:677px">
              <div class="table-responsive" style="background-color:#F0F0F0; margin-top:10px; margin-bottom:10px; border-radius: 5px; width:94%; margin-left:13px"> 
               <table class="table">
                    <thead>
                          <tr>
                            <th style="background-color:#3d566d;color:#ffffff;font-size:12px;font-weight: bold;">Shipping Service</th>
                            <th style="background-color:#3d566d"> </th>
                          </tr>
                        </thead>
                        <tbody>
                            <tr>
                <td align="left" valign="top" class="dispalyFields"  width="50%">  
                  <s:hidden name="shipmentCost"  id="shipmentCostId" value="%{#attr.ShipCostStr}"  size="5" cssClass="inputFields"  readonly="%{#attr.readOnlyFlag}"/>
              <s:hidden name="ShipFreightTextBox" cssClass="inputFields"  id="ShipFreightTextBox"  value="%{#freightCostStr}"  size="5" readonly="%{#attr.readOnlyFlag}"/>
                <s:property value="getText('ShipMethod')"/> <br/>
                </td>
                <td align="right" valign="top" width="50%">
                  <c:catch var="exception">
                    <s:if test="%{#aascCarrierProfileOptionsInfo == null}">
                      <s:set name="carrierProfileOptionsShipmethod"  value="%{''}"/>
                    </s:if>
                    <s:else>
                      <s:if test="%{#aascCarrierProfileOptionsInfo.shipMethodMeaning == null}">
                        <s:set name="carrierProfileOptionsShipmethod"  value="%{''}"/>
                      </s:if>
                      <s:else>
                        <s:set name="carrierProfileOptionsShipmethod"  value="%{#aascCarrierProfileOptionsInfo.shipMethodMeaning}"/>
                      </s:else>
                    </s:else>
                    <s:if test="%{#carrierProfileOptionsShipmethod == ''}">
                      <s:hidden name="carrierProfileOptionsShipmethodHidden" value="%{''}"/>
                    </s:if>
                    <s:else>
                      <s:hidden name="carrierProfileOptionsShipmethodHidden" value="%{#aascCarrierProfileOptionsInfo.shipMethodMeaning}"/>
                    </s:else>
                  </c:catch>
                  <s:if test="%{#exception != null}">
                    <s:set name="aascCarrierProfileOptionsInfo" value="%{''}"/>
                  </s:if>
                  
                  <%      
                      AascCarrierProfileOptionsInfo aascCarrierProfileOptionsInfo = null;
                      AascProfileOptionsBean aascProfileOptionsInfo =aascProfileOptionsBean;//(AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo");

                      String shipMethodMeaning=""; 
                      if(aascShipmentHeaderInfo.getShipFlag().equalsIgnoreCase("Y")){
                        shipMethodMeaning=aascShipmentHeaderInfo.getShippingMethod();
                      }           
                  %>
                  <select name="shipMethod" id="shipMethodId" size="1"  class="inputs" onkeydown="backspace(event);" title="<%=shipMethodMeaning%>" onmouseover="showTip(this,event)" onmouseout="hideTip()" onchange="setCarrierPayMethodHidden();FillCarrier();chgShipMethod();getCcCsl('2');" <%=ShipmentFlags.getSelectDisableFlag()%>>
                  <%
                   int carrierIdInt = aascShipmentHeaderInfo.getCarrierId();
                   int carrierCodeInt = aascShipMethodInfo.getCarrierCode(carrierIdInt);
                   String shipMethod = carrierCodeInt+"*"+nullStringToSpace(aascShipmentHeaderInfo.getShippingMethod());
                   shipMethodValidation = aascShipMethodInfo.getShipmentValidationCode(nullStringToSpace(aascShipmentHeaderInfo.getShippingMethod()));
                   shipMethod = carrierCodeInt+"||"+shipMethodValidation+"*"+nullStringToSpace(aascShipmentHeaderInfo.getShippingMethod());
                   if(aascShipmentHeaderInfo.getShipFlag().equalsIgnoreCase("Y") && ShipmentFlags.getSelectDisableFlag().equalsIgnoreCase("DISABLED"))
                   {
                      ListIterator shipMethodMainIterator=null;
                      AascShipMethodInfo aascShipMethodInfoMain = aascShipMethodInfo;//(AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
                      shipMethodMainIterator=(aascShipMethodInfoMain.getShipMethodDetailList()).listIterator(); 
                      String shipMethodElementValue="";
                      while(shipMethodMainIterator.hasNext())
                      {  
                        AascShipMethodInfo aascShipMethodMainInfo=(AascShipMethodInfo)shipMethodMainIterator.next(); 
                        String shipMethodElementAlt=(String) aascShipMethodMainInfo.getAlternateShipMethod();//getShipMethodMeaning();// 
                        
                        String shipMethodElement = (String)aascShipMethodInfoMain.getShipMethodFromAlt(shipMethodElementAlt);// 
                                   
                        //String shipMethodValue1=shipMethodElement;
                        String shipMethodValue2=aascShipmentHeaderInfo.getShipMethodMeaning();
                        
                        //if (shipMethodValue1.equalsIgnoreCase(shipMethodValue2))
                        if (shipMethodElement.equalsIgnoreCase(shipMethodValue2))
                        {
                          shipMethodElementValue = shipMethodElementAlt; 
                        }
                      }
                   %>
                   <option value="<%=shipMethod%>" id="<%=nullStringToSpace(aascShipmentHeaderInfo.getCarrierAccountNumber())%>"> <%=shipMethodElementValue%> </option>
                   <input type="hidden" name="shipMethodProfHid" id="shipMethodProfHidId" value="<%=shipMethodElementValue%>"/>
                   <%
                   }
                   else if(aascShipmentHeaderInfo.getShipFlag().equalsIgnoreCase("N") && (aascCarrierProfileOptionsInfo!=null))
                   {
                   %>
                    <s:hidden name="shipMethodProfHid" id="shipMethodProfHidId" value="%{#aascCarrierProfileOptionsInfo.shipMethodMeaning}"/>
                   <%
                   }else{
                   %>
                    <option value="<%=shipMethod%>" id="<%=nullStringToSpace(aascShipmentHeaderInfo.getCarrierAccountNumber())%>"> <%=aascShipmentHeaderInfo.getShippingMethod()%> </option>
                    <s:hidden name="shipMethodProfHid" id="shipMethodProfHidId" value="%{#aascShipmentHeaderInfo.shippingMethod}"/>
                   <%
                   }
                   %>
                  </select>
                  <span id="tooltip"  style="position:absolute;visibility:hidden;background:lightyellow; border:1px gray;padding:2px;font-size:7pt;font-family:Verdana;width:240;" onMouseOut="hideTip()"></span>
                  <s:if test='%{#aascCarrierProfileOptionsInfo != null && #aascCarrierProfileOptionsInfo != "" && (#aascShipmentHeaderInfo.shipFlag == "N" || #aascShipmentHeaderInfo.shipFlag == "n")}'>
                    <s:if test='%{#CODFlag == "Y" || #CODFlag == "y" || #ReturnFlag == "Y" || #ReturnFlag == "y" || #sigOptions != "NONE" && #sigOptions != "none"}'>
                      <s:hidden name="defaultPackageOption" id="defaultPackageOption"  value="%{'true'}"/>
                    </s:if>
                    <s:else>
                      <s:hidden name="defaultPackageOption" id="defaultPackageOption"  value="%{'false'}"/>
                    </s:else>
                  </s:if>
                  <s:else>
                    <s:hidden name="defaultPackageOption" id="defaultPackageOption" value="%{'false'}"/>
                    <s:set name="aascCarrierProfileOptionsInfo"  value="%{'null'}"/>
                  </s:else>
                  <s:if test="%{#aascCarrierProfileOptionsInfo != null && #aascCarrierProfileOptionsInfo != '' && (#aascShipmentHeaderInfo.shipFlag == 'N' || #aascShipmentHeaderInfo.shipFlag == 'n')}">
                    <s:set name="packCount" value="%{1}"/>
                    <s:if test='%{#CODFlag != "" || #ReturnFlag != "" || #CODFundsCode != "" || #CODCurrCode != "" || #CODType != "" || #ProfileCarrierPackaging != "" || #sigOptions != "NONE" && #sigOptions != "none"}'>
                        <s:hidden name="pkging%{#packCount}" id="pkgingID%{#packCount}"  value="%{#ProfileCarrierPackaging}"/>
                        <s:hidden name="signatureOption%{#packCount}"  id="signatureOptionID%{#packCount}"  value="%{#sigOptions}"/>
                        <s:hidden name="delConfirm%{#packCount}" id="delConfirmID%{#packCount}"  value="%{#sigOptions}"/>
                        <s:hidden name="codType%{#packCount}"  id="codTypeID%{#packCount}"  value="%{#CODType}"/>
                        <s:hidden name="codFundsCode%{#packCount}"  id="codFundsCodeID%{#packCount}" value="%{#CODFundsCode}"/>
                        <s:hidden name="codCurrCode%{#packCount}"  id="codCurrCodeID%{#packCount}" value="%{#CODCurrCode}"/>
                        <s:hidden name="chCOD%{#packCount}" id="chCODID%{#packCount}"  value="%{#CODFlag}"/>
                        <s:hidden name="returnShipment%{#packCount}" id="returnShipmentID%{#packCount}" value="%{#ReturnFlag}"/>
                        <s:hidden name="holdAtLocation%{#packCount}" id="holdAtLocationID%{#packCount}" value="%{#HALFlag}"/>
                        <s:hidden name="carrierProfileOptionsShipmethod" id="carrierProfileOptionsShipmethodID" value="%{#carrierProfileOptionsShipmethod}"/>
                        <s:hidden name="ProfileDropOffType" id="ProfileDropOffTypeID" value="%{#ProfileDropOffType}"/>
                        <s:hidden name="ProfileCarrierPackaging" id="ProfileCarrierPackagingID" value="%{#ProfileCarrierPackaging}"/>
                        <input type="HIDDEN" name="ProfileCarrierPaymethod"  id="ProfileCarrierPaymethodID" value="%{#ProfileCarrierPaymethod}"/>
                    </s:if>
                  </s:if>
                </td>
              </tr>
              <tr>
                <td align="left" valign="top" class="dispalyFields" width="50%"> <s:property value="getText('DropOfType')"/></td>
                <td align="right" width="50%">
                <select name="dropOftype" id="dropOftypeId" disabled="disabled" class="inputs" onkeydown="backspace(event);" onchange="setCarrierPayMethodHidden();getPackageList('','','');">
                <%                 
                  if((aascCarrierProfileOptionsInfo!=null)&&(aascShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("N"))
                  { 
                %>
                <option value="<%=aascCarrierProfileOptionsInfo.getDropOffType()%>"> <%=nullStringToSpace(aascCarrierProfileOptionsInfo.getDropOffType())%> </option>
                <input type="hidden" name="ajaxAfterShipDropOffType" id="ajaxAfterShipDropOffType" value="<%=aascCarrierProfileOptionsInfo.getDropOffType()%>"/>
                <%
                  } 
                  else
                  {           
                %>
                <option value="<%=aascShipmentHeaderInfo.getDropOfType()%>"> <%=nullStringToSpace(aascShipmentHeaderInfo.getDropOfType())%> </option>
                <input type="hidden" name="ajaxAfterShipDropOffType" id="ajaxAfterShipDropOffType" value="<%=aascShipmentHeaderInfo.getDropOfType()%>"/>
                <%
                  }
                %>
                </select>
                
                </td>
              </tr>
              <tr>
              <td align="left" valign="top" class="dispalyFields" width="50%">  <s:property value="getText('Packaging')"/> </td>
              <td align="right" valign="top" class="style7" width="50%">
                <select name="packages" id="packagesId"  disabled="disabled" class="inputs" onkeydown="backspace(event);" onchange="setCarrierPayMethodHidden();getCarrierPayMethod('','');changeDimensions();">
                <%
                    if((aascCarrierProfileOptionsInfo!=null)&&(aascShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("N"))
                    { 
                %>
                <option value="<%=aascCarrierProfileOptionsInfo.getPackaging()%>"> <%=nullStringToSpace(aascCarrierProfileOptionsInfo.getPackaging())%> </option>
                <input type="hidden" name="ajaxAfterShipPackaging" id="ajaxAfterShipPackaging" value="<%=aascCarrierProfileOptionsInfo.getPackaging()%>"/>
                <%
                    } 
                    else
                    { 
                %>
                <option value="<%=aascShipmentHeaderInfo.getPackaging()%>"> <%=nullStringToSpace(aascShipmentHeaderInfo.getPackaging())%> </option>
                <input type="hidden" name="ajaxAfterShipPackaging" id="ajaxAfterShipPackaging" value="<%=aascShipmentHeaderInfo.getPackaging()%>"/>
                <%
                    }
                %>
                </select>
              </td>
              </tr>
              <tr>
              <td align="left" valign="top" class="dispalyFields" width="50%"> <s:property value="getText('CarrierPayMethod')"/></td>
              <td align="right" valign="top" width="50%"> <span class="style7">
              <s:hidden name="chngCarrierPayMethodHidden" id="chngCarrierPayMethodHidden" value="%{''}" />
              <select name="carrierPayMethod" id="carrierPayMethodId" size="1" class="inputs" onkeydown="backspace(event);" onblur="checkCarrierTerm();" onchange="checkCarrierTerm();setCarrierPayMethodHidden();chgCarrierPayMethod();autoCarrierACNumber();tpNullValue();getAccountNumber();" <%=ShipmentFlags.getSelectDisableFlag()%> >><!--Sunanda modified for bug fix #2997-->
              <% 
                String localCarrierCode="";
                String payTerm = "" ; 
                try
                {

                  while(carrierPayMethodIterator.hasNext())
                  {
                    aascShipMethodInfo=(AascShipMethodInfo)carrierPayMethodIterator.next();
                    if((aascCarrierProfileOptionsInfo!=null)&&(aascShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("N"))
                    {
                      if(aascCarrierProfileOptionsInfo.getCarrierPaymentTerms().equals(aascShipMethodInfo.getCarrierPaymentTerms()))
                      {
                      localCarrierCode=aascShipMethodInfo.getCarrierPayCode();
                      payTerm = aascCarrierProfileOptionsInfo.getCarrierPaymentTerms();
                  %>
                     <option value="<%=localCarrierCode%>" selected="selected"> <%=aascCarrierProfileOptionsInfo.getCarrierPaymentTerms()%> </option>
                  <%
                      }
                    }
                    else
                    {
                       if(aascShipmentHeaderInfo.getCarrierPaymentMethod().equals( aascShipMethodInfo.getCarrierPaymentTerms()))
                       {    
                         localCarrierCode=aascShipMethodInfo.getCarrierPayCode();
                         payTerm = aascShipMethodInfo.getCarrierPaymentTerms() ;
                  %>
                     <option value="<%=localCarrierCode%>"><%= aascShipMethodInfo.getCarrierPaymentTerms()%>  </option>
                  <%
                          break;
                        }
                      }
                    }  
                  %>
                  <option value="<%=localCarrierCode%>"> <%= aascShipMethodInfo.getCarrierPaymentTerms()%> </option>
                  <s:set name="carrierPaymentTerms" value="TP"/>
                  <%
                  if("THIRD PARTY BILLING".equalsIgnoreCase(aascShipMethodInfo.getCarrierPaymentTerms()) ){
                  System.out.println("aascShipMethodInfo.getCarrierPaymentTerms():: "+aascShipMethodInfo.getCarrierPaymentTerms());
                  %>
                  <s:set name="carrierPaymentTerms" value="%{'TP'}"/>/>
                  <%
                  }
                  }
                catch(Exception e)
                {
                  logger.severe("exception while creating the carrierPayMethod"+e.getMessage());
                  
                  response.sendRedirect(url+"/aascShipError.jsp");
                }
              %>
              </select>
              
              <input type="hidden"  name="ajaxAfterShipCarrPayMthdValue"  id="ajaxAfterShipCarrPayMthdValue"  value="<%=localCarrierCode%>"/>
              <input type="hidden"  name="ajaxAfterShipCarrPayMthdStrValue"  id="ajaxAfterShipCarrPayMthdStrValue"  value="<%=payTerm%>"/>
             </span>
                </td>  </tr>
              
              <tr>
              <td width="50%" align="left"> &nbsp;</td>
              <td align="right">
               <s:if test='%{(#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y") && #carrierPaymentTerms != "TP"}'>
              
              
               <button type="button" class="btn btn-primary" name="tpPopupName" id="tpPopupId" onclick="tpOpenWin()" alt="Details" align="middle" disabled="true">Details</button> <!-- removed disabled after ship for third party billing bug #3562 -->
          <!--    <span class="btn btn-primary" name="tpPopupName" id="tpPopupId" onclick="javascript:tpOpenWin()" disabled>Details</span> -->
              
              </s:if>
              <s:else>
             
              <button type="button" class="btn btn-primary" name="tpPopupName" id="tpPopupId" onclick="tpOpenWin()" alt="Details" align="middle">Details</button> 
            <!--  <span class="btn btn-primary" name="tpPopupName" id="tpPopupId" onclick="javascript:tpOpenWin()">Details</span> -->
              </s:else>
             
              </td>
              
              
              
              </tr>
              <!-- vikas added hidden field to get the check flag from profileoptionsbean to shipment page & added carrieraccount number to the hiiden field to send it to shipmentordershipsaveaction -->
              
              <tr>
              <td align="left" valign="middle" class="dispalyFields" width="50%" onkeydown="backspace(event);"> <s:property value="getText('CarrierA/CNumber')"/> </td>
                <td align="right" valign="top" width="50%"> <span class="style7"> 
                <s:textfield name="carrierAccountNumber"  id="carrierAccountNumberId"   value="%{#aascShipmentHeaderInfo.maskCarrierAccountNumber}" OnBlur="unMask();" cssClass="inputs" maxlength="30"  readonly="%{#attr.readOnlyFlag}"/>
                
             </span>
             </td>
             </tr>
             
                         <tr>
                <td align="left" valign="middle" class="dispalyFields" width="50%"> <s:property value="getText('SaturdayShipmentDelivery')"/> ? 
                </td>
                <td align="right" valign="top" width="50%"> 
                <s:if test='%{#aascShipmentHeaderInfo.saturdayShipFlag == "Y"}' >
                    <s:set name="saturdayCheckBoxValue" value="%{'true'}" />
                </s:if>
                <s:else>
                    <s:set name="saturdayCheckBoxValue" value="%{'false'}" />
                </s:else>
                <s:checkbox name="saturdayShipFlag" id="chkSatShipment123" fieldValue="%{#aascShipmentHeaderInfo.saturdayShipFlag}" value="%{#saturdayCheckBoxValue}" onclick="chkSatShip();" />
                
                
                  
               
                </td>
                </tr>
                <s:if test='%{#aascProfileOptionsInfo.freightShopping == "Y"}' >
                    <tr>
                        <td>
                            
                            <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                                <span class="btn btn-primary" name="freightShopPopup" id="freightShopPopupId" alt="freight Shopping" align="middle" disabled>Freight Shopping</span>
                            </s:if>
                            <s:else>
                                <span class="btn btn-primary" name="freightShopPopup" onclick="return freightShopOpenWin('1');" id="freightShopPopupId" alt="freight Shopping" align="middle">Freight Shopping</span>
                                
                            </s:else>
                            
                        </td>
                        <td>
                            <s:hidden name="getRatesButton" id="getRatesButtonId" value="0"/>
                           <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                                <span class="btn btn-primary" name="getRates" id="getRatesId" alt="Get Rates" align="middle" disabled>Get Rates</span>
                            </s:if>
                            <s:else>
                                <span class="btn btn-primary" name="getRates" onclick="return validateForRates();" id="getRatesId" alt="freight Shopping" align="middle">Get Rates</span>
                            </s:else> 
                           
                    
                        </td>
                    </tr>
                </s:if>
              <tr>
              <s:if test='%{#aascProfileOptionsInfo.freightShopping == "Y"}' >
                    <td class="dispalyFields"  width="50%">
                        <s:property value="getText('ratesFromFreightShop')" />
                    </td>
                <td align="right" width="50%">
                    <span class="style7">
                        
                        <s:textfield name="ratesFromFreightShop" id="ratesFromFreightShopId" cssClass="inputs" readonly="true" value="%{#aascShipmentOrderInfo.fsRates}" />
                    </span>
                </td>
                </s:if>
              </tr>
              <tr>
                <td align="left" valign="middle" class="dispalyFields"  width="50%"> <s:property value="getText('ShipmentDate')"/></td>
                <td align="right" valign="top" width="50%"> <span class="style7">
                    <input name="shipmentDateTemp"  id="shipmentDateTempId" type="hidden" value="<%=nullStringToSpace(aascShipmentHeaderInfo.getShipTimeStamp())%>"/>
                    <s:if test='%{#aascShipmentHeaderInfo.shipFlag == "Y" || #aascShipmentHeaderInfo.shipFlag == "y"}'>
                        <input name="shipmentDateName" style="width:88%" id="shipmentDateId" class="inputs" type="text" value="<%=nullStringToSpace(aascShipmentHeaderInfo.getShipTimeStamp())%>" readonly="%{#attr.readOnlyFlag}"/>
                    </s:if>
                    <s:else>
                        <input name="shipmentDate" style="width:88%" id="shipmentDate" placeholder="YYYY-MM-DD" type="text" value="<%=nullStringToSpace(aascShipmentHeaderInfo.getShipTimeStamp())%>" />
                    </s:else>
                  </span> &nbsp;  </td>
              </tr>
              <tr>
                <td align="left" valign="middle" class="dispalyFields"  width="50%">
                  <s:property value="getText('Department')"/>
                </td>
                <td align="right" valign="top" width="50%">
                  <span class="style7">
                    <s:textfield name="department"  cssClass="inputs"  id="departmentId" value="%{#aascShipmentHeaderInfo.department}" size="25" maxlength="40"  readonly="%{#attr.readOnlyFlag}"/>
                  </span>
                </td>
              </tr>
              <tr>
                <td align="left" valign="middle" class="dispalyFields"  width="50%">
                  <s:property value="getText('Reference#1')"/>
                </td>
                <td align="right" valign="top" width="50%">
                  <span class="style7">
                    <s:textfield name="reference1"  cssClass="inputs"  id="reference1" value="%{#aascShipmentHeaderInfo.reference1}" size="20" maxlength="45"  readonly="%{#attr.readOnlyFlag}"/>
                  </span>
                </td>
              </tr>
              <tr>
                <td align="left" valign="middle" class="dispalyFields" width="50%">
                  <s:property value="getText('Reference#2')"/>
                </td>
                <td align="right" valign="top" width="50%">
                  <span class="style7">
                    <s:textfield name="reference2"  value="%{#aascShipmentHeaderInfo.reference2}" cssClass="inputs" id="reference2" size="20" maxlength="12"  readonly="%{#attr.readOnlyFlag}"/>
                  </span>
                </td>
              </tr>
            <!-- Shiva added below code for DHL -->
             <tr>
                <td align="left" valign="middle" class="dispalyFields" width="50%">
                  <s:property value="getText('AdditionalInfo')"/>
                </td>
                <td align="right" valign="top" width="50%">
                  <span class="style7">
                    <s:textfield name="shipAddInfo"  value="%{#aascShipmentHeaderInfo.shipAddInfo}" cssClass="inputs" id="shipAddInfo" size="20"  readonly="%{#attr.readOnlyFlag}"/>
                  </span>
                </td>
              </tr>
              <!-- Shiva code end -->
              
                        </tbody>
                        </table>
                        </div>
        </div>
            <br/>
    </div>

</div>
</div>

</div>

<div class="row">
<div class="col-md-12" style="background-color:#F0F0F0; border-width: 1px; border-radius: 10px; border-color: #7761a7; border-style: solid;margin-left:1%;margin-right:1%;width:98%">
   <div style="background-color:#7761a7;margin-top:5px;margin-left:5px">
   <label style="color:#ffffff;padding-left:10px;padding-top:5px;font-size: 12px;">Package Details</label>
   </div>
   <div style="width:100%;overflow:hidden;">    
        <table class="table" style="background-color:#F0F0F0; border-radius: 5px; margin-left:5px;margin-bottom:1px " >
                        <tr>
              <td  width="100%"   valign="top">
             
             <%  try {%>
                <jsp:include page="/aascShipmentPackageDetails.jsp">
                  <jsp:param name="ShipmentFlags" value="<%=ShipmentFlags%>"/>
                  <jsp:param name="aascShipmentHeaderInfo" value="<%=aascShipmentHeaderInfo%>"/>
                  <jsp:param name="shipReadonlyFlag" value='<%=nullStringToSpace(ShipmentFlags.getShipReadonlyFlag())%>'/>
                  <jsp:param name="flagUPSReadonly" value='<%=nullStringToSpace(ShipmentFlags.getFlagUPSReadonly())%>'/>
                  <jsp:param name="aascCarrierProfileOptionsInfo" value='<%=nullStringToSpace(aascCarrierProfileOptionsInfo)%>'/>
                </jsp:include>
           
           <% }
          catch(Exception e)
          {
           e.printStackTrace();
           
          }
          %>
              </td>
            </tr>
        </table>
        <%  try {%>
                <jsp:include page="/aascShipmentHelp.jsp">
                    <jsp:param name="aascShipmentHeaderInfo" value="<%=aascShipmentHeaderInfo%>"/>
                    <jsp:param name="aascCarrierProfileOptionsInfo" value='<%=nullStringToSpace(aascCarrierProfileOptionsInfo)%>'/>
                    <jsp:param name="aascProfileOptionsInfo" value='<%=nullStringToSpace(aascProfileOptionsInfo)%>'/>
                </jsp:include>
           
           <% }
          catch(Exception e)
          {
           logger.severe("Exception ::::"+e.getMessage());
           
          }
          %>  
   </div>
   </div>
</div>
</s:form>
<s:hidden name="errorFlag" value='success' />
<% }
          catch(Exception e)
          {
           logger.severe("exception in the Shipment form "+e.getMessage());
           
          }
          %> 

<!--end of Jagadish code-->  
  </div>
   <div class="container" align="left">
     <font size="2" color="003366">
                    <b>Copyright &copy; 2016 Apps Associates. All rights reserved</b>
                </font>
    </div>
  </body>
 
    
</html>
