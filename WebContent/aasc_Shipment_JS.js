    /*==========================================================================+
    |  DESCRIPTION                                                              |
    |    javascript file for the aascShipment.jsp and                   |
    +===========================================================================*/
    /*========================================================================================
    Date        Resource       Change history
    ------------------------------------------------------------------------------------------
    10/12/2014  Suman Gunda     Modified code for getting dropoftype, packing and carrier pay method
    17/12/2014  Eshwari M       Modified code to remove adhoc and delviery names
    19/12/2014  Y Pradeep       Modified code for getting shipFromCountry to shipFromCountryId in Ajax call by changing true to false in Ajax call.
    29/12/2014  Eshwari M       Modified code for role 2 shipping for pulling data based on locationId
    31/12/2014  Eshwari M       Added getDimensionsAjax() to pull dimnesions throught Ajax call.
    02/01/2015  Y Pradeep       Changed getShipToAddr1() function name to getShipToLocations(). Modifed code in getShipToLocations() and getShipToAddress() as required to get data from ajax call.
    08/01/2015  Y Pradeep       Modified code to hold shipmethod mapping, carrier pay method and carrier account number when error occured while shipping.
    12/01/2015  Suman G         Added reference1() and reference2() functions and called those functions in validation() function.
    14/01/2015  Y Pradeep       Added validateFutureShipDateForFedEx() function and related code for displaying alert for feature date of shipping. To fix bug #2515.
    16/01/2015  Y Pradeep       Added code in getShipToAddress() function for ajax call if any thing is selected other than 'Select'. To fix bug #2539.
    18/01/2015  Suman G         Commented validateSearch() and some other code for future reference.
    20/01/2015  Sunanda K       Added "--Select--" instead of "Select" for bug #2542.
    21/01/2015  Suman G         Added code for fix #2491.
    28/01/2015  Y Pradeep       Modified code as required for International Shipping.
    02/02/2015  Jagadish        Added code for SearchOrder functionality.
    02/02/2015  Y Pradeep       Modified if conditions in intlValues() function for giving alert to enter required fields in international popup for both Fedex and UPS shipments.
    02/02/2015  Suman G         Added code related to saturday shipment functionality.
    02/02/2015  Suman G         Added code to fix bug #2505.
    05/02/2015  Y Pradeep       Modifed code at window.open for UPS International popup page.
    11/02/2015  Y Pradeep       Modifed code at window.open for FedEx International popup page.
    11/02/2015  Suman G         Added getDefaultAccountNumber() for getting TP and FC account numbers.
    16/02/2015  Eshwari M       Modified code to set value to numOfVoidedPackages for void functionality
    16/02/2015  Suman G         Added code to fix #2611.
    20/02/2015  Y Pradeep       Added validations while opening international popup when no shipmethod is selected.
    20/02/2015  Sunanda K       Modified code for proper shipto location functionality and validations for bug fixes #2614 #2618
    24/02/2015  Y Pradeep       Modified code for Address Validation for generating order number on click of Validate Address button.
    24/02/2015  Suman G         Added freightShopOpenWin() function for freight shop.
    26/02/2015  Y Pradeep       Added validations for Ship to address for Address Validation purpose.
    26/02/2015  Y Pradeep       Renamed getIntlOrderNumber function name to getOrderNumber function.
    05/03/2015  Suman G         Changed orderNumber to orderNumberSearch for taking order number in action through request.
    05/03/2015    Sanjay & Khaja Added code for new UI changes.
    10/03/2015  Y Pradeep       Removed unused validations related to Printer and Templet fields.
    10/03/2015   Sunanda        Added code to save the ShipTo Address if it is newly entered and also modified code for bug Fix #2613
    05/03/2015  Suman G         Added code for get rates functionality.
    10/03/2015   Sunanda        Modified code for bug fix #2663
    10/03/2015  Y Pradeep       Modified if conditions in intlValues() function, removed carrier code 100 as Shipping to MX with UPS International is not mandatory. Bug #2627
    12/03/2015  Y Pradeep       Added if codition by S Kahja to return false or true in disableSubmit() and disableVoidSubmit() functions.
    12/03/2015  Suman G         Added showLabelPopUp() function for view label functionality.
    13/03/2015  Suman G         Added condition for Ajay Demo.
    14/03/2015  Suman G         Added setvalues() calling for #2665.
    14/03/2015  Suman G         Fixed the issue of adding two packages while shipping.
    15/03/2015  Y Pradeep       Moved Search Order code to Advance Search link.
    16/03/2015  Suman G         Added code for remove null values in drop of type and packaging type for UPS.
    20/03/2015  Y Pradeep       Modified code to display proper alerts when Ship To Cuatomer and Ship To Location is not selected. Bug #2710.
    23/03/2015  Sunanda         Modified code for bug fix #2613 for address line3
    24/03/2015  Suman G         Reverted the changes made for Ajay's demo.
    25/03/2015  Sunanda K       Modified code for bug fix #2695
    26/03/2015  Y Pradeep       Renamed orderNumberId to orderNumberSearchId.
    26/03/2015  Y Pradeep       Added setResidentialFlag().
    27/03/2015  Sunanda K       Modified the code and gave proper ids for fields to fix bug #2732
    27/03/2015  Sunanda K       Modified the code and called the function in ShipOrderSearch jsp for bug fix #2731
    27/03/2015  Sunanda K       Modified the code for bug fix #2733
    30/03/2015  Suman G         Added code to fix related to issue without weight enter click on FS & Get Rates.
    13/04/2015  Y Pradeep       Added dateFormatCheck function for validating format of date. Bug #2775.
    14/04/2015  Y Pradeep       Assigning value of the selected Ship Method field to shipMethodHideId hidden field. Bug #2746.
    15/04/2015  Suman G         Modified if conditions to fix #2821.
    15/04/2015  Suman G         Added session related code to fix #2743
    21/04/2015  Y Pradeep       Modified code to remove edit functionality for Customer Name in ship to details and added code to disable backspace functionality on select fields. Bug #2744.
    24/04/2015  Suman G         Added code to fix the issue for MPS functionality.
    24/04/2015  Eshwari M       Modified code to reduce multiple time calling of same Ajax calls
    27/05/2015  Eshwari, Pradeep  Added chngCarrierPayMethodHidden field and setCarrierPayMethodHidden() function for changing the carrier a/c no. to default a/c no. on change of any of the LOV's
    30/04/2015  Y Pradeep       Added code to focus order number search field on page load. Bug #2890.
    05/05/2015  Y Pradeep       Modified fnKeyPressHandler_A function to allow spaces while entering data in Ship To Location field and Ship From Location field by getting data from value instead of text. Bug #2896.
    05/05/2015  Y Pradeep       Modified code to set carrier name in carrierNameHideId hidden field at chgShipMethod() function for saving Carrier Name properly in header field. Bug #2693 and #2901.
    05/05/2015  Y Pradeep       Added code in orderNumber() function to focus order number search field after selecting a order in Advance Serach popup. Bug #2909.
    05/05/2015  Suman G         Added code to fix #2906
    06/05/2015  Suman G         Added code to fix #2900
    08/05/2015  Y Pradeep       Added deleteRowInDB function for deleting or clearing package details in database. Bug #2910.
    08/05/2015  Y Pradeep       Added code to display alerts when Package OPtions button and Add button is clicked in Packag Details section without selecting Ship To details and Ship Method. Bugs: #2908, 2907#. Done By Padmavathi
    08/05/2015  Suman G         Modified details button for carrier pay methods to fix #2911
    11/05/2015  Y Pradeep       Added code in getShipToAddress function to load address3 and email_id in Ajax call. Bug #2845.
    25/05/2015  Y Pradeep       Added voidFlagStatus() and chkFunction() function calls in load(event) function to disable first package void checkbox after shipping. Bug #2915.
    27/05/2015  Suman G         Added code to fix #2936
    27/05/2015  Y Pradeep       Modified code to display and allow Order Numbers with special characters(Encode and Decode).
    03/06/2015  Y Pradeep       Modified code to call Weighing Scale and get details of the selected weighing scale from profile options page.
    03/06/2015  Suman G        Added code to fix #2955
    09/06/2015  Suman G        Added Padmavathi code to fix #2966, 2907 and 2908
    09/06/2015  Suman G         Added code to fix #2955  
    09/06/2015  Suman G         Removed condition to fix #2960
    11/06/2015  Y Pradeep       Modified code to add applet tag based on Ship From Location selection. Also added onFocusWeightField() function to call weighing scale when weight field is focused. Bug #2978.
    11/06/2015  K Sunanda       Added code to fix bug #2997
    12/06/2015  Y Pradeep       Modified code to set saveIntlFlag vale to N when shipFromLocation is changed after saving international page. Bug #2996.
    12/06/2015  Suman G         Added Padmavathi's code to fix #2985
    16/06/2015  Y Pradeep       Added void flag check conditions to update total weight field in totalweight() function. Bug #3002.
    19/06/2015  Y Pradeep       Modified code to make weighing scale work in all browsers.
    24/06/2015  Y Pradeep       Removed event at getShipFromAddress() function call.
    25/06/2015  Suman G         Added Padmavathi's code to disable residential flag after ship.
    26/06/2015  Rakesh K        Modified code to make FedExPackageOption popup maximize in IE 10 browser to fix #3031.
    13/07/2015  Y Pradeep       Added code to call print label functionality and modified code to create applet tag dynamically for weighing scale and modified code to get label format when shipmethod is changed.
    20/07/2015  Suman G         Added code for Implementing Email Notification.
    20/07/2015  Y Pradeep       Added AddPkgsToForm() fucntion call in validateForRates() function to submit multiple packages when Get Rates button is clicked.
    20/07/2015  Rakesh K        Modified code for UPS package options popup window #3026.
    22/07/2015  Y Pradeep       Modified code to display success or error message after successfull shipping. Bug #3199.
    29/07/2015  Rakesh K        Modified code to Maximize window in IE10 #3286.
    30/07/2015  Y Pradeep       Added code in getShipFromAddress() function to get configured printers from Printer Configuration page based on ship from location. Bug #3289.
    30/07/2015  Y Pradeep       Added code to set updated totalWeight to a hidden variable in totalWeight() function. Bug #3002.
    31/07/2015  Y Pradeep       Added a packValidate() function check condition to get valid dimensions in validateForRates() function when multiple packages are added and clicked on Get Rates button. Bug #3273.
    05/08/2015  Suman G         Modified code for issue #3294.
    06/08/2015  Y Pradeep       Modified code to show message after shipping and printing lables. Bug #3199, #3230, #3231, #3207.
    13/08/2015  Y Pradeep       Modified code to fix #3231 bug to print both International documents and lables.
    20/08/2015  Rakesh K        Removed code added for drop down issue 2895.
    24/08/2015  N Srisha        Added code to get service name in INTL page after ship for bug #3377
    24/08/2015  Jagadish Jain   Added code to for ajax call that check for  balance alert, tolerance alert and subscription expiry 
    31/08/2015  N Srisha        Added validation for shipFrom and ShipTo email address on ship button bug #3497
    29/10/2015  N Srisha        Added code to default the ship from email id from profile options  Bug #3494
    29/10/2015  Y Pradeep       Modified code to read weight from Weighing Scale.	
    30/10/015   Shiva           Added code related to DHL integration
    04/11/2015  Suman G         Modified code to fix #3870.
    04/11/2015  N Srisha        Modified code to disable/enable paymethod details button. Bug #3561    
    05/11/2015  Eshwari M       Modified code to resolve bugs 3866, 2989 and 2948
    06/11/2015  Mahesh V        Modofied code to reslove bugs 3897 and 3899
    06/11/2015  Shiva G         Modified code to fix the issue 3927
    06/11/2015  Eshwari M       Modified code to resolve bug 2965
    05/11/2015  Suman G         Added code for DHL and Stamps FS
    09/11/2015  Suman G         Added code to fix #3938
    09/11/2015  Shiva G         Modified code to fix the issue #3938
    10/11/2015  Shiva G         Modified code to fix the issue #3864
    10/11/2015  Mahesh V        Modified code to implement FedEx recepient option with popup
    10/11/2015  Mahesh V        Modified code to implement for FedEx and FDXG recepient option with popup
    13/11/2015  Dinakar         Added code to fix #2895 & #3932.
    17/11/2015  Y Pradeep       Modified code to stop printing labels immediately after shipping.
    20/11/2015  Y Pradeep       Revert back chabges made to edit Customer Name and Ship To Location names as a text field and changed Location Name LOV to editable only if "--Select--" option is selected and only `~@^_|}{[]\:? characters are supported. Also made browser compatable.
                                Added if condition to display alert on click of package options for Stamps.com and DHL carriers for not supporting package options.
                                Bugs #3990, #3971 and #3972.
    24/11/2015  Suman G         Replaced this with days to fix #4006
    24/11/2015  Mahesh V        Added code for Bug fix of #4024
    27/11/2015  Mahesh V        Added code for FedEx and UPS recepient and third party development
    02/12/2015  Suman G         Added code to edit the ship to fields based on given profile options after error case.
    03/12/2015  Y Pradeep       Modified code to make Customer Name as editable in Ship To details sectionand added code to validate and allow only particular special characters.
    07/12/2015  Y Pradeep       Modified code to set options tag to customerLocation selecte tag to make it editable. Bug #4075.
    07/12/2015  Y Pradeep       Added encodeURIComponent for customerName, ContactName, ShiptoAddress1, ShiptoAddress2, postalcode in openAddressValidationPopup() function. Bug #4076 and #4082.
    09/12/2015  Y Pradeep       Modified code to not allow . when delete button is clicked in ship to customer name field. Bug #4087.
    11/12/2015  Suman G         Added code to stop future date shipping for UPS
    16/12/2015  Suman G         Added code to implement get rates feature for DHL
    10/02/2016  Sneha           Modified alerts by Sneha to fix #4286 and 4287
    24/02/2016  Suman G         Added code for new Transaction Management design.
    09/03/2016  Y Pradeep       Added code in getShipFromAddress function to get Weighing scale details after ship button is clicked. Bug #4377.
11/03/2016  Vikas        Added code for Account Number Masking for Re cipient and Third Party Billing
    ========================================================================================*/
    
    
    var chngShipFlag ="false";
    var chngCountryFlag = "usorgin";
    function load(event) // function to call ajax on load
    {      
      var carrierPayMethod = document.getElementById("carrierPayMethodId").value;
      var callShipmethods = document.getElementById("isItemShippedID").value; //DynaShipmentShipSaveForm.isItemShipped.value;
      try{
      var companyNameFromProfileID = document.getElementById("companyNameFromProfileID").value;
      }catch(e){
      }
      var companyName = document.getElementById('companyNameId').value;
      if(companyName == null || companyName == '')
          document.getElementById('companyNameId').value = companyNameFromProfileID;
    
      var shipFromCountry = document.getElementById('shipFromCountryId').value ;
      var shipToCountry = trim(document.getElementById('country').value);
     
        if(shipFromCountry != shipToCountry && shipToCountry != null && shipToCountry != '' && shipToCountry != 'Select')
         document.DynaShipmentShipSaveForm.intlFlag.value = 'Y';
      else
         document.DynaShipmentShipSaveForm.intlFlag.value = 'N';
          
      if(document.DynaShipmentShipSaveForm.intlFlag.value == 'Y')
      {
         document.getElementById("tickPic").innerHTML='<img src="images/aasc_tick.png" width="16" height="16"/>';
         document.getElementById("intlMassage").innerHTML = '<a href="javascript:intShipmentsPopUp()" id="intlMessageId" style="color:rgb(64, 115, 134); text-decoration:underline;">International Shipments</a>' ;
      }
      else{    
         document.getElementById("tickPic").innerHTML='';
         document.getElementById("intlMassage").innerHTML = ''; 
      }   
      
      /**************  To check whether the oder is international End By Eshwari  *********************/ 
      // below code added by Jagadish to get location lov even after error case
      var shipFlag=document.getElementById("flagShipID").value;
      if(shipFlag!="Y"){
        var locationName = trim(document.getElementById("customerLocationId").value);
        getShipToLocations();
         document.getElementById("customerLocationId").value = locationName;
         document.getElementById("customerLocationId").text = locationName;
         
         // To load the ship methods, drop off, packaging, pay methods and account number for errored out orders, 
         // already saved orders and not shipped orders to reduce multiple time calling of same Ajax calls  By Eshwari start
         
         var key = document.getElementById("shipKey").value ;
         var displayMsg1 = "" ;          
         var divTag = document.getElementById("displayMessage1") ;

         if(divTag != null && divTag != '')
            displayMsg1 = document.getElementById("displayMessage1").innerHTML ;
         
         var errorFlag = document.getElementById("errorFlag").value ;
      
         if((key != 'aasc225' && key != '' && key != null) || (displayMsg1 != null && displayMsg1 != '') || (errorFlag != null || errorFlag != '')) // || (errorMsg != null && errorMsg != '')  ))
         {
            getShipMethodAjax();    
         }  // // To load the ship methods, drop off, packaging, pay methods and account number for errored out orders, 
         // already saved orders and not shipped orders to reduce multiple time calling of same Ajax calls  By Eshwari end   
      }
      // end of Jagadish code
      
      if(callShipmethods!='' && callShipmethods=='READONLY')
      {
        var testShipMethodvalue=document.getElementById("shipMethodId").value;
        if(testShipMethodvalue.substring(0,3)=='100'&&testShipMethodvalue.substring(0,3)=='110'&&testShipMethodvalue.substring(0,3)=='111' && testShipMethodvalue.substring(0,3)=='115' && testShipMethodvalue.substring(0,3)=='114')
        {   
          getCcCsl('3');  // After Ship
        }
      }
      //Added code to mask A/c no 
       var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
      var carrierCode=strValue.substring(0,3);
      var str=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
      var upsMode=trim(document.getElementById("upsMode").value);
        var str=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
                 
      if((carrierCode==100 && (str=="PP" || str=="TP" || str=="CG" || str=="FC")) || ((carrierCode==110 || carrierCode==111) && (str=="PP" || str=="TP" || str=="CG")) || (carrierCode==114 && (str=="PP" || str=="CG" )))
      { 
      if(document.getElementById("flagShipID").value == 'Y' && document.getElementById("maskAccountCheckID").value == 'Y' && document.getElementById("carrierAccountNumberId").value == ''){
                    document.getElementById("carrierAccountNumberId").value=document.getElementById("ajaxAfterShipCarrAccNumber").value;
                    }
                if(document.getElementById("maskAccountCheckID").value == "Y"){
              mask();
                document.getElementById("carrierAccountNumberId").value;
               }
        }
        else{
         document.getElementById("carrierAccountNumberId").value=document.getElementById("ajaxAfterShipCarrAccNumber").value;
        }
        
        if(((carrierCode == 100 && upsMode == "ShipExec") || carrierCode == 115) && str=="PP"){ 
                  document.getElementById("carrierAccountNumberId").value="";
                  } 
                  
      var packcount = document.getElementById("countPacketsID").value;
      for(var index =  1;index<=packcount;index ++)
      {              
        var length = document.getElementById('packageLengthID'+index).value;
        var width  = document.getElementById('packageWidthID'+index).value;
        var height = document.getElementById('packageHeightID'+index).value;
        var trackingNumber = document.getElementById('trackingNumberID'+index).value;
        var shipMethodvalue=document.getElementById("shipMethodId").value;  
        var carrierCode=shipMethodvalue.substring(0,shipMethodvalue.indexOf("|"));
        if(carrierCode=='999')
        {
          document.getElementById('packOpt'+index).disabled=true;
          document.getElementById('packOpt'+index).src ="buttons/aascDetailsOff1.png";
        }
      }
      disableDim();   
      var shipError = document.DynaShipmentShipSaveForm.shipError.value;
      var errorDesc = document.DynaShipmentShipSaveForm.errorDesc.value;
    
      /*if(document.getElementById("shipMethodId").value=="")
      { 
        getShipMethodAjax();
      }*/
      if(document.getElementById("defaultCarrierSessionValuesFlagHiddenID").value=='Y' && carrierPayMethod!="") 
      {
        document.getElementById("carrierPayMethodId").value=carrierPayMethod;
      }
       
      chkSatShip(); 
    
      totalWeight();
      FillTask();
      voidFlagStatus();   
        var orderNumber = trim(document.getElementById("orderNumberID").value);
        document.getElementById("orderNumberID").value = orderNumber;
        if (orderNumber != "" && orderNumber != null && (orderNumber.substring(0,2))!="SC") {
             var orderNumberTemp=encodeURIComponent(orderNumber);
             document.getElementById("orderNumberHidID").value = orderNumberTemp;
        }
      if((shipError == "warning") && (document.getElementById("flagShipID").value != "Y"))
      {
        ConfirmStatus = confirm(shipError+" : "+errorDesc+"\n------------------------------------\n Please Press OK to Continue.")
         if (ConfirmStatus == true) 
         {
           shipValidation(); // RAVI
           document.getElementById("shipmentWarningStatusID").value ="continue";
           document.getElementById("submitButtonId").value="Ship";
           document.getElementById("DynaShipmentShipSaveForm").submit();
         }
      }
      document.ShipGetForm.orderNumberSearch.focus(); // Added this line to focus order number search field on page load. By Pradeep. Bug #2890.
        // Below code added by Padmavathi
      var avflag=document.getElementById('avFlag').value;
      var fsflag=document.getElementById('fsFlag').value;
      if(document.getElementById('flagShipID').value=='N'||document.getElementById('flagShipID').value=='n')
      //Padma code for issue #2966     
      {
        var customerName = document.getElementById('customerName').value ;
        var customerLocationName = document.getElementById('customerLocationId').value ;
        var importFlag = document.getElementById('importFlagId').value ;
        
        var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
        if((customerName !=' --Select-- ' && customerLocationName !='' && avflag=='N' && fsflag=='N') || 
           ((importFlag == 'Y' || importFlag == 'y') && customerName !=' --Select-- ' && customerLocationName !='' && str != "--Select Ship Method--")) // Eshwari added importFlag condition to fix bug # 2965
    {
       document.getElementById('weightID1').focus();
   }
   }
   //Padmavathi's code for Issue #3056
    if(document.getElementById('flagShipID').value=="Y"||document.getElementById('flagShipID').value=="y"){
    document.getElementById('residentialFlagId').disabled=true;
    }
    else
    {
    document.getElementById('residentialFlagId').disabled=false;
    }
    if(document.getElementById("shipMethodId").disabled == true)
        { 
          document.getElementById("chkSatShipment123").disabled=true;
        }
        else{
        document.getElementById("chkSatShipment123").disabled=false;
        }
  //Padmavathi's code ends here
        /* Commented below code to remove immediate printing labels after shipping successfully.
        //Below is the code for calling printing label function after shipping successfully.
        var key = document.getElementById("shipKey").value ;
        var displayMessage = document.getElementById("displayMessage");
        var printMessage = '';
        if(key == 'aasc225')
        {
            printMessage = printLabel(); 
            if(printMessage == 'aasc125'){
                displayMessage.innerHTML = displayMessage.innerHTML + " and  Printed the labels Successfully ";
            }else if(printMessage == 'aasc126'){
                displayMessage.style.color = '#cc0000';
                displayMessage.innerHTML = displayMessage.innerHTML + " and  Error in printing the labels ";
            }
          document.getElementById("shipKey").value = "";  
        }// End of the printer code.
        */
        if(carrierCode == '100' || carrierCode == '110' || carrierCode == '111'){   //Mahesh added code 
            var shipFlagPackages=document.getElementById("flagShip1").value ;   //  Suman Added code to disble details button for PP after ship.
            if(shipFlagPackages != 'Y' && shipFlagPackages != 'y')
                document.getElementById("tpPopupId").disabled=false;
        }
        var shipFlag=document.getElementById("flagShipID").value;
        if(shipFlag != "Y"){
            if(document.getElementById('shipToCityEditFlagId').value == 'Y')
                document.getElementById('city').readOnly = false;
            else
                document.getElementById('city').readOnly = true;
                
            if(document.getElementById('shipToStateEditFlagId').value == 'Y')
                document.getElementById('state').readOnly = false;
            else
                document.getElementById('state').readOnly = true;
                
            if(document.getElementById('shipToZipEditFlagId').value == 'Y')
                document.getElementById('zip').readOnly = false;
            else
                document.getElementById('zip').readOnly = true;
                
            if(document.getElementById('shipToCountryEditFlagId').value == 'Y')
                document.getElementById('country').readOnly = false;
            else
                document.getElementById('country').readOnly = true;
        }
         if(document.getElementById('customerName').value == "--Select--"){
            document.getElementById('customerLocationId').options[0] =  new Option("","",true,true);
        }
    }
    
    function chkFunction()
    {
        var packageCount = document.getElementById("countPacketsID").value;
        var shipStatusFlag=document.getElementById("shipmentStatusFlagID").value;
        var totalShippedQuantity =0.0;
        var shippedQuantity=0.0; 
        var shipFlag=document.getElementById("flagShipID").value ;
       
        if( shipStatusFlag!="P" && shipStatusFlag!="B")
        {
           for(var index =  1;index<=packageCount;index ++)
           {
              var trckno=document.getElementById('trackingNumber'+index).value;   
              if(( trckno=="" || trckno==null))
              {
                 if(totalShippedQuantity==0)
                 {
                     document.DynaShipmentShipSaveForm['chVoid'+index].disabled=true;
                     document.DynaShipmentShipSaveForm['lineNo'+index].readOnly=true;
                     document.getElementById('weightID'+index).readOnly=true;
                     document.DynaShipmentShipSaveForm['uom'+index].readOnly=true;
                     document.DynaShipmentShipSaveForm['packageLength'+index].readOnly=true;
                     document.DynaShipmentShipSaveForm['packageWidth'+index].readOnly=true;
                     document.DynaShipmentShipSaveForm['packageHeight'+index].readOnly=true;
                     document.DynaShipmentShipSaveForm['packageDeclaredValue'+index].readOnly=true;
                 
                     document.getElementById('signatureOptionID'+index).readOnly=true;
                     document.getElementById('returnShipmentID'+index).readOnly=true;
                 }
                 else
                 {
                     document.DynaShipmentShipSaveForm['chVoid'+index].value="Y";
                     document.DynaShipmentShipSaveForm['chVoid'+index].disabled=true;;
                     document.DynaShipmentShipSaveForm['lineNo'+index].readOnly=false;
                     document.getElementById('weightID'+index).readOnly=false;
                     document.DynaShipmentShipSaveForm['uom'+index].readOnly=false;
                     document.DynaShipmentShipSaveForm['packageLength'+index].readOnly=false;
                     document.DynaShipmentShipSaveForm['packageWidth'+index].readOnly=false;
                     document.DynaShipmentShipSaveForm['packageHeight'+index].readOnly=false;
                     document.DynaShipmentShipSaveForm['packageDeclaredValue'+index].readOnly=false;
                  
                     document.getElementById('signatureOptionID'+index).readOnly=false;
                     document.getElementById('returnShipmentID'+index).readOnly=false;
                 }
              }
              else
              {
                 document.DynaShipmentShipSaveForm['chVoid'+index].value="N";
                 document.DynaShipmentShipSaveForm['lineNo'+index].readOnly=true;
                 document.getElementById('weightID'+index).readOnly=true;
                 document.DynaShipmentShipSaveForm['uom'+index].readOnly=true;
                 document.DynaShipmentShipSaveForm['packageLength'+index].readOnly=true;
                 document.DynaShipmentShipSaveForm['packageWidth'+index].readOnly=true;
                 document.DynaShipmentShipSaveForm['packageHeight'+index].readOnly=true;
                 document.DynaShipmentShipSaveForm['packageDeclaredValue'+index].readOnly=true;
             
                 document.getElementById('signatureOptionID'+index).readOnly=true;
                 document.getElementById('returnShipmentID'+index).readOnly=true;
              }
           }
        }
    }
    
    // Added for Advance Search Functionallity
    function advanceSearch(){
    
        tpWindow=window.open("aascShipOrderSearch.jsp","Post",'width=600,height=350,top=200,left=100, resizable=yes,scrollbars=yes');
        tpWindow.focus(); 
        return false;
    
    }
    
    // Moved Order Search functioanlity to Advance Search option.
    function orderNumberValid()
    {
         if(document.ShipGetForm.submit.value=="Go")
         {
            var orderNumberSearch = document.ShipGetForm.orderNumberSearch.value;
            if(document.ShipGetForm.orderNumberSearch.value==''){
                alert("Please enter Order Number");
                document.ShipGetForm.orderNumberSearch.focus();
                return false;
            }
            if ((orderNumberSearch.substring(0,2))!="SC") {
                 var orderNumberSearchTemp=encodeURIComponent(orderNumberSearch);
                 document.ShipGetForm.orderNumberSearchHid.value = orderNumberSearchTemp;
            }
             document.ShipGetForm.actiontype.value='GET';
             return true;   
             
         }
         else if(document.ShipGetForm.submit.value=="NewOrder"){
      
            document.getElementById("submitButtonId").value="New Order";
            if(document.getElementById("flagShipID").value=="N")
            {
                ConfirmStatus= confirm("Your Current Order details are incomplete ! \n -------------------------------------------------\nDo you want to proceed to New Order ?");
                if (ConfirmStatus == true) 	
                {
                    document.getElementById("DynaShipmentShipSaveForm").submit();
                }
            }
            else
            {
            document.getElementById("DynaShipmentShipSaveForm").submit();
            }
         }
         else if(document.ShipGetForm.submit.value=="Clear"){
               return false;
         }
         return false;
    }
    
    function isInteger(s)
    {
        var i;
        for (i = 0; i < s.length; i++)
        {   
             var c = s.charAt(i);
             if (((c < "0") || (c > "9")))
               return false;
        }
        return true;
    }  
    
    function getCustomerName()
    {
       //alert('document.getElementById("customerName").value : '+document.getElementById("customerName").value);
      //below if condition for spl character added by Jagadish
      if(document.getElementById("customerName").value=="" || document.getElementById("customerName").value=="--Select--")
       {
         alert("Please select a valid Customer Name");
         document.getElementById("customerName").focus();
         return false;
       }
      if(chkSplCharsCustomerName(document.getElementById("customerName").value)==false)
        {
            alert("Only @#&-_.,() special characters are allowed for Customer name.");
            document.getElementById("customerName").focus();
            return false;
        }
      
       
       return true; 
    }
    
    function chkSplCharsCustomerName(message)
    {
        var len= (trim(message)).length;
        var message = trim(message);
        
        for(var index = 0; index <len;index++)
        {
            var c = message.charAt(index);
            if(c == "=" || c == "+" || c == '?' || c == '>' || c == '<' || c == "}" || c == "{" || c == "]" || c == "[" || c == "/" || c == ';' || c == ':' || c == '"' || c == '!' || c=='$' || c == '%' || c=='^' || c=='*')  
            {
                return false;
            }
        
        }  
        return true;
    }
    
    function getShipToLocationName()
    {
        //below if condition for spl character added by Jagadish 
        if(document.getElementById("customerLocationId").value=="" || document.getElementById("customerLocationId").value=="--Select--")
       {
         alert("Please select a valid Ship To Location Name");
         document.getElementById("customerLocationId").focus();
         return false;
       }
  //Commented this code by Padmavathi inorder to allow special characters for location name for issue #2985 
  //Mahesh Uncommented this code for not allowing special characters for location name for issue #2985
        if(chkSplCharsAll(document.getElementById("customerLocationId").value)==false)
        {
            alert("No Special Characters Allowed for customer location.");
            document.getElementById("customerLocationId").focus();
            return false;
        } 
       
       return true; 
    }
    
    function getContactName()
    {
       
        //below if condition for spl character added by Jagadish
        if(chkSplCharsAll(document.DynaShipmentShipSaveForm.contactName.value)==false)
        {
            alert("No Special Characters Allowed for contact.");
            document.DynaShipmentShipSaveForm.contactName.focus();
            return false;
        }
       if(document.DynaShipmentShipSaveForm.contactName.value=="")
       {
         alert("Please enter the Ship To Contact Name");
         document.DynaShipmentShipSaveForm.contactName.focus();
         return false;
       }
       return true; 
    }
    
    function getWayBillNumber()
    {
       var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
       if(str != "--Select Ship Method--")
       {
         var strShipMethod=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
       }
     
       if ((strShipMethod.substring(0,3))=="999") 
       {
         if(document.DynaShipmentShipSaveForm.wayBill.value=="")
         {
            alert("Please enter the Waybill/Tracking No");
            document.DynaShipmentShipSaveForm.wayBill.focus();
            return false;
         }
       }
       return true; 
    }
    
    function shipmentCost()
    {
      if(document.DynaShipmentShipSaveForm.shipmentCost.value=="")
      {
        alert("Please enter the ShipmentCost");
        document.DynaShipmentShipSaveForm.shipmentCost.focus();
        return false;
      }
      return true; 
    }
    function shipToAddress()
    {
      if(document.DynaShipmentShipSaveForm.shipToAddress.value=="")
    // if( document.DynaShipmentShipSaveForm.shipToAddress.options[document.DynaShipmentShipSaveForm.shipToAddress.selectedIndex].text=="")
      {
        alert("Please enter the Ship To Address");
        document.DynaShipmentShipSaveForm.shipToAddress.focus();
        return false;
       }
       return true; 
    }
    function shipMethod()
    {
      var shipMethod= document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
      //alert('shipMethod : '+shipMethod);
      
      if(shipMethod == "--Select Ship Method--")
      { 
        var len = document.getElementById("shipMethodId").length ;
        //alert("Ship Method list length: "+len);
        
        if(length <= 1)
           getShipMethodAjax();
        
        alert("Please Select the Ship Method");
        return false;
      }
      return true; 
    }
    function city()
    {
     if(document.DynaShipmentShipSaveForm.city.value=="")
     {
       alert("Please enter the Ship To City");
       document.DynaShipmentShipSaveForm.city.focus();
       return false;
     }
     //sambit
     var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    // var str=document.DynaShipmentShipSaveForm.shipMethod.value;
     if(str.substring(0,2)==11)
     {
      if(document.DynaShipmentShipSaveForm.city.value.length < 3)
     {
       alert("City Name Should Be More Than 3 Characters");
       document.DynaShipmentShipSaveForm.city.focus();
       return false;
     }
     }
     return true; 
    }
    function state()
    {
      var str=document.DynaShipmentShipSaveForm.state.value;
      var shipToAddressCountry=document.DynaShipmentShipSaveForm.countrySymbol.value;
      if(shipToAddressCountry=="US" || shipToAddressCountry=="CA")
      {
      if(str == "" || str == null)
      {
        alert("Please enter the Ship To State Name");
        document.DynaShipmentShipSaveForm.state.focus();
        return false;
      }
      }
      //sambit
    //  var str2=document.DynaShipmentShipSaveForm.shipMethod.options[document.DynaShipmentShipSaveForm.shipMethod.selectedIndex].value;
    // // var str2=document.DynaShipmentShipSaveForm.shipMethod.value;
    //  if(str2.substring(0,2)==11)
    //  {
    //   str=str.toUpperCase();
    //   document.DynaShipmentShipSaveForm.state.value=str;
    //   if(str.length != 2)
    //   {
    //     alert("State Name should be of 2 Characters Only");
    //    document.DynaShipmentShipSaveForm.state.focus();
    //    return false;
    //   }
    //  }
            return true;
    }
    function carrierPayMethod()
    {
      if(document.getElementById("carrierPayMethodId").value=="")
      {
        alert("Please enter the CarrierPayMethod");
        document.getElementById("carrierPayMethodId").focus();
        return false;
      }
      return true; 
    }
    function reference1()
    {
      if(document.getElementById('referenceFlag1Id').value=="Y")
      {
    //  alert(document.DynaShipmentShipSaveForm.reference1.value);
        if(document.DynaShipmentShipSaveForm.reference1.value==""){
            alert("Please enter Reference 1 value");
            document.DynaShipmentShipSaveForm.reference1.focus();
            return false;
        }
      }
       return true; 
     }
    function reference2()
    {
      if(document.getElementById('referenceFlag2Id').value=='Y')
      {
    //  alert(document.DynaShipmentShipSaveForm.reference2.value);
        if(document.DynaShipmentShipSaveForm.reference2.value==""){
            alert("Please enter Reference 2 value");
            document.DynaShipmentShipSaveForm.reference2.focus();
            return false;
        }
      }
       return true; 
     }
    function country()
    {
     str=document.DynaShipmentShipSaveForm.countrySymbol.value;
      if(document.DynaShipmentShipSaveForm.countrySymbol.value=="")
      {
        alert("Please enter the Country Code");
        document.DynaShipmentShipSaveForm.countrySymbol.focus();
        return false;
      }else{
       // checkIntl();
      }
      //sambit
      if(shipMethod()==false)
      {
       return false;
      }
      var str2=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
      //var str2=document.DynaShipmentShipSaveForm.shipMethod.value;
      if(str2.substring(0,2)==11)
      {
       str=str.toUpperCase();
       document.DynaShipmentShipSaveForm.countrySymbol.value=str;
       if(str.length != 2)
       {
//         alert("Country Name should be of 2 Characters Only");
//        document.DynaShipmentShipSaveForm.countrySymbol.focus();
//        return false;
       }
      }
      return true; 
    }
    function carrierACNumber()
    {
       //sambit
       var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
       //var str=document.DynaShipmentShipSaveForm.shipMethod.value;
       var str2=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
       var upsMode=trim(document.getElementById("upsMode").value);
      /*if(((str.substring(0,3)==100) && (str2=="TP" || str2=="FC")) ||
          ((str.substring(0,2)==11) && (str2=="TP" || str2=="CG")))   */
       //   ====================================Modified by Narasimha ======================================================
          if (((str2=="FC" || str2=="TP"|| str2 == "CG" ) && !(str.substring(0,3)=="100" && upsMode=="UPS Direct"))
          ||((str.substring(0,3)=="100" && upsMode=="UPS Direct") && (str2=="FC" || str2=="TP")))
          {
          if (trim(document.getElementById("carrierAccountNumberId").value)=='' && !(trim(document.getElementById("carrierPayMethodId").value)=='FC' && str.substring(0,3)=="111")){
    
                                    alert("Enter Carrier A/C Number");
                                    document.getElementById("carrierAccountNumberId").focus();
                                    return false;
                            }
    
          }
          
          
       /* {
      if(document.DynaShipmentShipSaveForm.carrierACNumber.value=="" || isNaN(document.DynaShipmentShipSaveForm.carrierACNumber.value))
      {
       alert("please enter Valid Carrier Account Number");
       document.DynaShipmentShipSaveForm.carrierACNumber.focus();
       return false;
       }
       }*/
       
        var len= (trim(document.getElementById("carrierAccountNumberId").value)).length;
        {
         var carrierText = trim(document.getElementById("carrierAccountNumberId").value);
          for(var index = 0; index <len;index++)
          {
            var c = carrierText.charAt(index);
             if(c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')')  
             {
                    alert("Enter Carrier A/C Number. No Special Characters  Allowed");
                                    document.getElementById("carrierAccountNumberId").focus();
                            return false;
             }
          }
        } 
        if(str.substring(0,3)==100)
        {
        if(str2=="TP" || str2=="FC"){
        var ACNumber= trim(document.getElementById("carrierAccountNumberId").value);
         if(ACNumber.length < 5){
                                    alert("Please Enter valid 5 digit Carrier A/C Number");
                                    document.getElementById("carrierAccountNumberId").focus();
                                    return false;
                            }
        //  if(str2=="TP")
         //{
          if(ACNumber == trim(document.DynaShipmentShipSaveForm.CarrierAcHidden.value) && (str.substring(0,3)==100))
          {
            alert("Third Party Carrier A/C Number should be different from carrier Account Number");
                                    document.getElementById("carrierAccountNumberId").focus();
                                    return false;
          }
         // }
          }
          }
         if(str.substring(0,2)==11)
        {
        if(str2=="TP" || str2=="CG"){
        var ACNumber= trim(document.getElementById("carrierAccountNumberId").value);
         if(ACNumber.length < 8){
                                    alert("Please Enter valid 9 digit Carrier A/C Number");
                                    document.getElementById("carrierAccountNumberId").focus();
                                    return false;
                            }
          }
          }
       return true; 
    }
    function zip()
    {
      if(document.DynaShipmentShipSaveForm.postalCode.value=="") //sambit on 12/06/08// || isNaN(document.DynaShipmentShipSaveForm.zip.value))
      {
        alert("Please enter the numeric zip code ");
        document.DynaShipmentShipSaveForm.postalCode.focus();
        return false;
      }
      /*  Commented by sambit on 12/06/2008
       if(document.DynaShipmentShipSaveForm.zip.value.length <=4 && document.DynaShipmentShipSaveForm.zip.value.length !=5){
                                    alert("Please  Enter valid 5 or 9 digit  Zip Code :");
                                    document.DynaShipmentShipSaveForm.zip.focus();
                                    return false;
                            }
          if(document.DynaShipmentShipSaveForm.zip.value.length > 5 && document.DynaShipmentShipSaveForm.zip.value.length !=9){
                                    alert("Please  Enter valid 5 or 9 digit  Zip Code :");
                                    document.DynaShipmentShipSaveForm.zip.focus();
                                    return false;
                            }
        */
      return true; 
    }
    /*function shipperName()
    {
    /*=============Modified by narasimha ==================
      if(document.DynaShipmentShipSaveForm.shipperName.value=="")
      {
        alert("please enter the Shipper Name");
        document.DynaShipmentShipSaveForm.shipperName.focus();
        return false;
       }
       ==============End============================*/
      // return true; 
    //}*/
    function shippedQty()
    {
      if(document.DynaShipmentShipSaveForm.shippedQty.value=="" || parseFloat(trim(document.DynaShipmentShipSaveForm.shippedQty.value))<=0
         || isNaN(document.DynaShipmentShipSaveForm.shippedQty.value))
      {
        alert("Please Enter The Shipped Quantity Greater Than 0 And Should Be Numeric only");
        document.DynaShipmentShipSaveForm.shippedQty.focus();
        return false;
       }
       return true; 
    }
    function uom()
    {
     var str=document.DynaShipmentShipSaveForm.uom.value;
     //sambit
     var str2=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
     //  var str2=document.DynaShipmentShipSaveForm.shipMethod.value;
      if(document.DynaShipmentShipSaveForm.uom.value=="" ||  !isNaN(document.DynaShipmentShipSaveForm.uom.value))
     {
       alert("Please enter valid uom value");
       document.DynaShipmentShipSaveForm.uom.focus();
       return false;
     }
     else
     {
      if(str2.substring(0,2)==11)
      {
        str=str.toUpperCase();
        document.DynaShipmentShipSaveForm.uom.value=str;
      if((str.substring(0,2) =="LB") || (str.substring(0,2) =="KG"))
     {
       return true;
      }
     else
     {
       alert("uom value should be either KG or LB");
       document.DynaShipmentShipSaveForm.uom.focus();
       return false;
     }
     }
     }
     return true; 
    }
    
    /*function itemDescription()
    {
      if(document.DynaShipmentShipSaveForm.itemDescription.value.length <=0)
     {
       alert("Please Enter the Item Description");
       document.DynaShipmentShipSaveForm.itemDescription.focus();
       return false;
     }
     return true; 
    }
    */
    function weight()
    {
      if(document.DynaShipmentShipSaveForm.weight.value=="" || parseFloat(trim(document.DynaShipmentShipSaveForm.weight.value))<= 0
         || isNaN(document.DynaShipmentShipSaveForm.weight.value))
      {
        alert("Please enter the weight greater than 0 and should be numeric value only");
        document.DynaShipmentShipSaveForm.weight.focus();
        return false;
      }
      return true; 
    }
    //Added by gayaz on 10 may 07
    function checkCod()
    {
    
    if(document.DynaShipmentShipSaveForm.chCOD1.checked==true)
    {
    document.DynaShipmentShipSaveForm.codAmt1.disabled=false;
    document.DynaShipmentShipSaveForm.chCOD1.value="Y";
    }
    else
    {
    document.DynaShipmentShipSaveForm.codAmt1.disabled=true;
    document.DynaShipmentShipSaveForm.chCOD1.value="N";
    }
    
    }
    
    // added by sambit on 12/06/2008
    function intlValues(){
        // alert("document.DynaShipmentShipSaveForm.intlFlag.value:"+document.DynaShipmentShipSaveForm.intlFlag.value);
        if(document.DynaShipmentShipSaveForm.intlFlag.value == 'Y')
        {
           //   alert("document.DynaShipmentShipSaveForm.intTotalCustomsValue.value:"+document.DynaShipmentShipSaveForm.intTotalCustomsValue.value);
       
           var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
           var carrierCode=strValue.substring(0,3);
        
           if(carrierCode == 100 && (document.DynaShipmentShipSaveForm.country.value == 'CA' || document.DynaShipmentShipSaveForm.country.value == 'PR') && document.getElementById("shipFromCountryId").value == 'US' && document.DynaShipmentShipSaveForm.intlCustomsFlag.value != 'Y')
           {
             alert(" Please click on International Shipments link and Give Invoice Details");
             return false;
           }
           if(document.DynaShipmentShipSaveForm.intlSaveFlag.value != 'Y' && (carrierCode == 110 || carrierCode == 111 || carrierCode == 115))    // Removed carrier code 100 as Shipping to MX with UPS International is not mandatory.
           {
             //alert("intl flag"+document.DynaShipmentShipSaveForm.internationalHiddden.value);
             alert(" Please click on International Shipments link and give details ");
             return false;
           }
		//Shiva added below condition for DHL
           if(document.DynaShipmentShipSaveForm.intlSaveFlag.value != 'Y' && carrierCode == 114)    // Removed carrier code 100 as Shipping to MX with UPS International is not mandatory.
           {
             //alert("intl flag"+document.DynaShipmentShipSaveForm.internationalHiddden.value);
             alert(" Please click on International Shipments link and give details ");
             return false;
           }           
           var packcount = document.getElementById("countPacketsID").value;
           var Dvalue = 0.0;
           for(var index =  1;index<=packcount;index ++)
           {              
               Dvalue = parseFloat(Dvalue) + parseFloat(document.getElementById('packageDeclaredValueID'+index).value);
               
               //alert("weight ::"+document.getElementById('weightID'+index));
           }  
           
            if(carrierCode == 115){
            
            if(Dvalue == 0.0 || isNaN(Dvalue)){
                //alert('inside if');
                alert("Shipment Declared Value is mandatory for international shipments");
                document.getElementById('packageDeclaredValueID'+1).focus();
                return false;
           }
            }
           // var totalDeclaredValue = document.DynaShipmentShipSaveForm.totalPackageDeclaredValue.value;
           var totalCustomVAlue = document.DynaShipmentShipSaveForm.intTotalCustomsValue.value;
           //alert("Dvalue = "+Dvalue+"totalCustomVAlue ="+totalCustomVAlue);
           if(parseFloat(Dvalue) > parseFloat(totalCustomVAlue) && (carrierCode != 115))
           {
                alert(" Total Shipment Declared Value "+Dvalue+" should not be greater then \n Total Custom Value  "+totalCustomVAlue);
                return false;
           }
          
        }else
        {
           return true;
        }
    }
     // end of 12/06/2008
    
    
    
    function AddPkgsToForm(){
    
     
       var packcount = document.getElementById("countPacketsID").value;
      
        var oldpack = parseInt(document.getElementById("oldPackCountID").value);
     //  alert("oldpk"+oldpack);
     oldpack= oldpack+2;
     // alert("PC :"+packcount+" OP :"+oldpack);
           for(var index = oldpack;index<=packcount;index ++)
      {  
    
      
     // alert(" 689 weight ::"+ document.getElementById('weightID'+index).value+"  index :"+index);
       // document.getElementById('weightID'+index).value =  document.getElementById('weightID'+index).value ;
       
        var signatureOptionElement = document.getElementById('signatureOptionID'+index);
         var returnShipmentElement = document.getElementById('returnShipmentID'+index);
         var PackageSurchargeElement = document.getElementById('PackageSurchargeID'+index);
         var hideDIDElement = document.getElementById('hideDIDID'+index);
       //  alert("hideDIDElement "+hideDIDElement.value);
         var chkCODElement = document.getElementById('chkCODID'+index);
         var pkgingElement = document.getElementById('pkgingID'+index);
         var delConfirmElement = document.getElementById('delConfirmID'+index);
         var codTypeElement = document.getElementById('codTypeID'+index);
         var codFundsCodeElement = document.getElementById('codFundsCodeID'+index);
         var codCurrCodeElement = document.getElementById('codCurrCodeID'+index);
         var upsSurChargeElement = document.getElementById('upsSurChargeID'+index);
         var chCODElement = document.getElementById('chCODID'+index);
         var codAmtElement = document.getElementById('codAmtID'+index);
         var halPhoneElement = document.getElementById('halPhoneID'+index);
         var HazMatFlagElement = document.getElementById('HazMatFlagID'+index);
         var HazMatTypeElement = document.getElementById('HazMatTypeID'+index);
         var HazMatClassElement = document.getElementById('HazMatClassID'+index);
         var HazMatChargesElement = document.getElementById('HazMatChargesID'+index);
         var HazMatQtyElement = document.getElementById('HazMatQtyID'+index);
         var HazMatUnitElement = document.getElementById('HazMatUnitID'+index);
         var HazMatIdNoElement = document.getElementById('HazMatIdNoID'+index);
         var HazMatPkgGroupElement = document.getElementById('HazMatPkgGroupID'+index);
         var HazMatDOTLabelElement = document.getElementById('HazMatDOTLabelID'+index);
         var HazMatEmerContactNoElement = document.getElementById('HazMatEmerContactNoID'+index);
         var HazMatEmerContactNameElement = document.getElementById('HazMatEmerContactNameID'+index);
         var HazMatIdElement = document.getElementById('HazMatIdID'+index);
         var HazMatPackagingCntElement = document.getElementById('HazMatPackagingCntID'+index);
         var HazMatPackagingUnitsElement = document.getElementById('HazMatPackagingUnitsID'+index);
         var HazMatTechnicalNameElement = document.getElementById('HazMatTechnicalNameID'+index);
         var HazMatSignatureNameElement = document.getElementById('HazMatSignatureNameID'+index);
         var halAddrLine1Element = document.getElementById('halAddrLine1ID'+index);
              var halAddrLine2Element = document.getElementById('halAddrLine2ID'+index);
              var halCityElement = document.getElementById('halCityID'+index);
              var halStateElement = document.getElementById('halStateID'+index);
              var halZipElement = document.getElementById('halZipID'+index);
              var holdAtLocationElement = document.getElementById('holdAtLocationID'+index);
              var rtnShipFromCompanyElement = document.getElementById('rtnShipFromCompanyID'+index);
              var rtnShipToCompanyElement = document.getElementById('rtnShipToCompanyID'+index);
              var rtnShipFromContactElement = document.getElementById('rtnShipFromContactID'+index);
              var rtnShipToContactElement = document.getElementById('rtnShipToContactID'+index);
              var rtnShipFromLine1Element = document.getElementById('rtnShipFromLine1ID'+index);
              var rtnShipToLine1Element = document.getElementById('rtnShipToLine1ID'+index);
              var rtnShipFromLine2Element = document.getElementById('rtnShipFromLine2ID'+index);
              var rtnShipToLine2Element = document.getElementById('rtnShipToLine2ID'+index);          
              var rtnShipFromCityElement = document.getElementById('rtnShipFromCityID'+index);
              var rtnShipFromSateElement = document.getElementById('rtnShipFromSateID'+index);
              var rtnShipFromZipElement = document.getElementById('rtnShipFromZipID'+index);
              var rtnShipToCityElement = document.getElementById('rtnShipToCityID'+index);
              var rtnShipToStateElement = document.getElementById('rtnShipToStateID'+index);
              var rtnShipToZipElement = document.getElementById('rtnShipToZipID'+index);
              var rtnShipFromPhoneElement = document.getElementById('rtnShipFromPhoneID'+index);
              var rtnShipToPhoneElement = document.getElementById('rtnShipToPhoneID'+index);
              var rtnShipMethodElement = document.getElementById('rtnShipMethodID'+index);
              var returnDescriptionElement = document.getElementById('returnDescriptionID'+index);
              var rtnCountrySymbolElement = document.getElementById('rtnCountrySymbolID'+index);
               var HazMatPackInstructionsElement = document.getElementById('HazMatPackInstructionsID'+index);
              var rtnDropOfTypeElement = document.getElementById('rtnDropOfTypeID'+index);
              var rtnPackageListElement = document.getElementById('rtnPackageListID'+index);
              var rtnPayMethodElement = document.getElementById('rtnPayMethodID'+index);
              var rtnACNumberElement = document.getElementById('rtnACNumberID'+index);
              var rtnRMAElement = document.getElementById('rtnRMAID'+index);
              var rtnTrackingNumberElement = document.getElementById('rtnTrackingNumberID'+index);
              var rtnShipmentCostElement = document.getElementById('rtnShipmentCostID'+index);
              var packageSaveCheckElement = document.getElementById('packageSaveCheckID'+index);
              var rtnShipMethodNameElement = document.getElementById('rtnShipMethodNameID'+index);
              var rtnPayMethodCodeElement = document.getElementById('rtnPayMethodCodeID'+index);
              var rtnDeclaredValueElement = document.getElementById('rtnDeclaredValueID'+index);
              
              
              var chDryIceElement = document.getElementById('chDryIceID'+index);
              var dryIceUnitsElement = document.getElementById('dryIceUnitsID'+index);
              var dryIceWeightElement = document.getElementById('dryIceWeightID'+index);
              var largePackageFlagElement = document.getElementById('LargePackageID'+index);
              var addlHandlingFlagElement = document.getElementById('AdditionalHandlingID'+index);
    //  adding y2 row fields to form 
             var PackageShipmentCostElement = document.getElementById('PackageShipmentCostID'+index);
     
     //  adding y3 row fields to form 
             var shippedQtyElement = document.getElementById('shippedQtyID'+index);
             
            
     //  adding y4 row fields to form 
             var lineNoElement = document.getElementById('lineNoID'+index);
           
      //  adding y5 row fields to form   
             var weightElement = document.getElementById('weightID'+index);
      
      //  adding y6 row fields to form   
          var uomElement = document.getElementById('uomID'+index);
          
          
    //  adding y7 row fields to form   
          var trackingNumberElement = document.getElementById('trackingNumberID'+index);
         
     //  adding y8 row fields to form   
           var dimensionNameElement = document.getElementById('dimensionNameID'+index);
           var dimButtonElement = document.getElementById('dimButtonID'+index);
           var packageLengthElement = document.getElementById('packageLengthID'+index);
           var packageWidthElement = document.getElementById('packageWidthID'+index);
           var packageHeightElement = document.getElementById('packageHeightID'+index);
      
        //   var mappedQtyElement = document.getElementById('mappedQtyID'+index);
         
           var dimensionNameHiddenElement = document.getElementById('dimensionNameHiddenID'+index);
           var dimensionValueHiddenElement = document.getElementById('dimensionValueHiddenID'+index);
           var defaultDimeNameElement = document.getElementById('defaultDimeNameID'+index); 
          
     //  adding y9 row fields to form         
           var packageDeclaredValueElement = document.getElementById('packageDeclaredValueID'+index); 
           var declaredCurrCodeElement = document.getElementById('declaredCurrCodeID'+index); 
    
        // For Get Rates
        
        var dimUOMElement = document.getElementById('dimUOMId'+index);
        var dimValueElement = document.getElementById('dimValueId'+index);
        var weightUOMElement = document.getElementById('weightUOMId'+index);
        var weightValueElement = document.getElementById('weightValueId'+index);
        
        // For Get Rates
    
         var forms = document.getElementsByTagName('form'); 
              for (var i = 0; i < forms.length; i++) {
                var formName = forms[i].getAttribute('name');
                var formBeanName = "DynaShipmentShipSaveForm"; //Name of the form bean used by the data collection form.
                if(formName == formBeanName){
                 
                 forms[i].appendChild(signatureOptionElement);
                 forms[i].appendChild(returnShipmentElement);
                 forms[i].appendChild(PackageSurchargeElement); 
                 
//                 forms[i].appendChild(hideDIDElement);
//                 forms[i].appendChild(chkCODElement);
                 forms[i].appendChild(pkgingElement);
                 forms[i].appendChild(delConfirmElement);
                 forms[i].appendChild(codTypeElement);
                 forms[i].appendChild(codFundsCodeElement);
                 forms[i].appendChild(codCurrCodeElement);
                 forms[i].appendChild(upsSurChargeElement);
                 forms[i].appendChild(chCODElement);
                 forms[i].appendChild(codAmtElement);
                 forms[i].appendChild(halPhoneElement);
                 forms[i].appendChild(HazMatFlagElement);
                 forms[i].appendChild(HazMatTypeElement);
                 forms[i].appendChild(HazMatClassElement);
                 forms[i].appendChild(HazMatChargesElement);
                 forms[i].appendChild(HazMatQtyElement);
                 forms[i].appendChild(HazMatUnitElement);
                 forms[i].appendChild(HazMatIdNoElement);
                 forms[i].appendChild(HazMatPkgGroupElement);
                 forms[i].appendChild(HazMatDOTLabelElement);
                 forms[i].appendChild(HazMatEmerContactNoElement);
                 forms[i].appendChild(HazMatEmerContactNameElement);
                 forms[i].appendChild(HazMatIdElement);
                 forms[i].appendChild(HazMatPackagingCntElement);
                 forms[i].appendChild(HazMatPackagingUnitsElement);
                 forms[i].appendChild(HazMatTechnicalNameElement);
                 forms[i].appendChild(HazMatSignatureNameElement);
                 forms[i].appendChild(halAddrLine1Element);
                 forms[i].appendChild(halAddrLine2Element);
                 forms[i].appendChild(halCityElement);
                 forms[i].appendChild(halStateElement);
                 forms[i].appendChild(halZipElement);
                 forms[i].appendChild(holdAtLocationElement);
                 forms[i].appendChild(rtnShipFromCompanyElement);
                 forms[i].appendChild(rtnShipToCompanyElement);
                 forms[i].appendChild(rtnShipFromContactElement);
                 forms[i].appendChild(rtnShipToContactElement);
                 forms[i].appendChild(rtnShipFromLine1Element);
                 forms[i].appendChild(rtnShipToLine1Element);
                 forms[i].appendChild(rtnShipFromLine2Element); 
                 forms[i].appendChild(rtnShipToLine2Element);            
                 forms[i].appendChild(rtnShipFromCityElement);
                 forms[i].appendChild(rtnShipFromSateElement);
                 forms[i].appendChild(rtnShipFromZipElement);
                 forms[i].appendChild(rtnShipToCityElement);
                 forms[i].appendChild(rtnShipToStateElement);
                 forms[i].appendChild(rtnShipToZipElement);
                 forms[i].appendChild(rtnShipFromPhoneElement);
                 forms[i].appendChild(rtnShipToPhoneElement);
                 forms[i].appendChild(rtnShipMethodElement);
                 forms[i].appendChild(returnDescriptionElement);
                 forms[i].appendChild(rtnCountrySymbolElement);
                 forms[i].appendChild(HazMatPackInstructionsElement);
                 forms[i].appendChild(rtnDropOfTypeElement);
                 forms[i].appendChild(rtnPackageListElement);
                 forms[i].appendChild(rtnPayMethodElement);
                 forms[i].appendChild(rtnACNumberElement);
                 forms[i].appendChild(rtnRMAElement);
                 forms[i].appendChild(rtnTrackingNumberElement);
                 forms[i].appendChild(rtnShipmentCostElement);
                 forms[i].appendChild(packageSaveCheckElement);
                 forms[i].appendChild(rtnShipMethodNameElement);
                 forms[i].appendChild(rtnPayMethodCodeElement);
                 forms[i].appendChild(rtnDeclaredValueElement);
                
                
                // For Get Rates
    
                 forms[i].appendChild(dimUOMElement);
                 forms[i].appendChild(dimValueElement);
                 forms[i].appendChild(weightUOMElement);
                 forms[i].appendChild(weightValueElement);
        
                // For Get Rates
                
                
                 forms[i].appendChild(chDryIceElement);
                 forms[i].appendChild(dryIceUnitsElement);
                 forms[i].appendChild(dryIceWeightElement);
                 forms[i].appendChild(largePackageFlagElement);
                 forms[i].appendChild(addlHandlingFlagElement); 
    //  adding y3 row fields to form 
                 forms[i].appendChild(PackageShipmentCostElement);
                 
                 var shippedQtyElementNODE = shippedQtyElement.cloneNode(true);
                 forms[i].appendChild(shippedQtyElementNODE);
                 shippedQtyElementNODE.style.display ="none"; 
    
    
     //  adding y4 row fields to form 
             //    forms[i].appendChild(lineNoElement);
                 
                 var lineNoElementNODE = lineNoElement.cloneNode(true);
                 forms[i].appendChild(lineNoElementNODE);
    lineNoElementNODE.style.display ="none"; 
    
      //  adding y5 row fields to form   
      
      var weightElementNODE =weightElement.cloneNode(true);
                 forms[i].appendChild(weightElementNODE);
                 weightElementNODE.style.display ="none"; 
    
    
      //  adding y6 row fields to form  
              var uomElementNODE = uomElement.cloneNode(true);
               forms[i].appendChild(uomElementNODE);
             uomElementNODE.style.display ="none"; 
    
    
    //  adding y7 row fields to form   
                var trackingNumberElementNODE = trackingNumberElement.cloneNode(true);
                 forms[i].appendChild(trackingNumberElementNODE);
                trackingNumberElementNODE.style.display ="none"; 
    
    
     //  adding y8 row fields to form   
                 var dimensionNameElementNODE = dimensionNameElement.cloneNode(true);
                 forms[i].appendChild(dimensionNameElementNODE);
                 dimensionNameElementNODE.style.display ="none"; 
    
                 var dimButtonElementNODE = dimButtonElement.cloneNode(true);
                 forms[i].appendChild(dimButtonElementNODE);
                              dimButtonElementNODE.style.display ="none"; 
    
                 
                 
                 forms[i].appendChild(packageLengthElement);
                 forms[i].appendChild(packageWidthElement);
                 forms[i].appendChild(packageHeightElement);
               //  forms[i].appendChild(mappedQtyElement);
                 forms[i].appendChild(dimensionNameHiddenElement);
                 forms[i].appendChild(dimensionValueHiddenElement);
                 forms[i].appendChild(defaultDimeNameElement);
    
     //  adding y9 row fields to form 
            var packageDeclaredValueElementNODE = packageDeclaredValueElement.cloneNode(true);
            
                 forms[i].appendChild(packageDeclaredValueElementNODE);
           packageDeclaredValueElementNODE.style.display ="none"; 
    
                var declaredCurrCodeElementNODE =declaredCurrCodeElement.cloneNode(true);
                 forms[i].appendChild(declaredCurrCodeElementNODE);
           declaredCurrCodeElementNODE.style.display ="none"; 
    
    
                 
                 
    
                  } }
          } 
      
    
    }
    
    function contactName()
    {
      if(document.DynaShipmentShipSaveForm.contactName.value=="")
      {
        alert("Please enter the Contact Name");
        document.DynaShipmentShipSaveForm.contactName.focus();
        return false;
       }
       return true; 
    }
    function phoneNumber()
    {

                                    var phonenumber=document.DynaShipmentShipSaveForm.phoneNumber.value;
                                    /*if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
                                    {
                                            alert("Please Enter Valid 10 digit Phone Number :");
                                            document.DynaShipmentShipSaveForm.phoneNumber.focus();
                                            return false;
                                    } */
                                     if(phonenumber!="" && phonenumber!=null && phonenumber!=0)
                                     {
                                    
    //                                                                if (checkInternationalPhone(phonenumber)==false){
    //                                
    //		alert("Please Enter Phone Number Either in this Format (XXX)XXX-XXXX or with 10 Digit Numeric Number")
    //		document.DynaShipmentShipSaveForm.phoneNumber.focus();
    //		return false;
    //                }
                    }
      return true; 
    }
    
    function phoneNumberUPS()
    {
     var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
     if(str.substring(0,3)==100)
     {
      if(!document.DynaShipmentShipSaveForm.phoneNumber.value=="")
     {
      var phonenumber=document.DynaShipmentShipSaveForm.phoneNumber.value;
            /*if((phonenumber.match(/^[ ]*[(]{0,1}[ ]*[0-9]{3,3}[ ]*[)]{0,1}[-]{0,1}[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)  && (phonenumber.match(/^[ ]*[0-9]{3,3}[ ]*[-]{0,1}[ ]*[0-9]{4,4}[ ]*$/)==null)) 
            {
                                    alert("Please Enter Valid 10 digit Phone Number :");
                                            document.DynaShipmentShipSaveForm.phoneNumber.focus();
                                            return false;
            }*/
    //         if (checkInternationalPhone(phonenumber)==false){
    //                                
    //		alert("Please Enter Phone Number Either in this Format (XXX)XXX-XXXX or with 10 Digit Numeric Number")
    //		document.DynaShipmentShipSaveForm.phoneNumber.focus();
    //		return false;
    //	}
     }
     }
     return true; 
    }
    
    function shipmentDateValid()
    {
      var dt=document.DynaShipmentShipSaveForm.shipmentDate;
            if (isDate(dt.value)==false)
      {
                    dt.focus()
                    return false;
            }
            return true;
    }

    function validation()
    { 
       if(document.getElementById('saveButtonID').value=="shipSave")
       {
            if(validateFutureShipDateForFedEx() != true)
            {
                return false;
            }
            
          if(intlValues() == false)
          {
            return false;
          }
          if(getCustomerName()==false)
         {
          return false;
         }
         if(getShipToLocationName() == false)
         {
          return false;
         }
         if(shipToAddress()==false)
         {
          return false;
         }
         if(country()==false)
         {
           return false;
         }
         if(city()==false)
         {
           return false;
         }
                    
        if(shipMethod()==false)
         {
           return false;
         }
         if(state()==false)
         {
           return false;
         }
         if(phoneNumberUPS()==false)
         {
          return false;
         }
         if(carrierPayMethod()==false)
         {
           return false;
         }
         if(carrierACNumber()==false)
         {
           return false;
         }
         if(zip()==false)
         {
           return false;
         }
         if(reference1()==false) 
         {
           return false;
         }
        if(reference2()==false) 
         {
           return false;
         }
         if(getContactName()==false)
         {
           return false;
         }        
         if(getShipToPhoneNumber() == false)
         {
            return false;
         }
         if(getWayBillNumber()==false)
         {
           return false;
         } 
         
          if(FedexValid()==false)
                     {
                      return false;
                     }
           if(upsPackValid()==false)
          {
            return false;
          }
        //Shiva added for DHL
        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        var carrierTest=str.substring(0,3);
        if(carrierTest == 114)
         {
            var resul = dhlValidation();
            if(resul == false)
            {
                return false;
            }
        }
       //Shiva code end
              if (packValidate()==false)
              {
                return false;
              }
       
          if(document.getElementById("submitButtonId").value=="Ship")
          {
                if(shipValidation()==false)
               { 
                    return false;
               }
     //    if(navigator.appName !='Microsoft Internet Explorer') 
     //Suman code start to fix issue with MPS functionality.
    var count = document.getElementById('countPacketsID').value;
    for(var i=1;i<=count;i++){
    
        var dimUom;
        dimUom  = document.getElementById('dimensionValueHiddenID'+i).value; // It works only ajax call happend. Need to work on this.
        
        var lastIndex = dimUom.lastIndexOf('*');
        dimUom = dimUom.substring(0,lastIndex);
        lastIndex = dimUom.lastIndexOf('*');
        var dimValue = dimUom.substring(0,lastIndex);
        dimUom = dimUom.substring(lastIndex+1);
        
        if(dimUom == ''){
        
                dimUom = document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].value;
                lastIndex = dimUom.lastIndexOf('*');
                dimUom = dimUom.substring(0,lastIndex);
                lastIndex = dimUom.lastIndexOf('*');
                dimValue = dimUom.substring(0,lastIndex);
                dimUom = dimUom.substring(lastIndex+1);
        }
        dimValue = dimValue.replace('*','X');
        dimValue = dimValue.replace('*','X');
        
        document.getElementById('dimUOMId'+i).value = dimUom;
        document.getElementById('dimValueId'+i).value = dimValue;
        document.getElementById('weightUOMId'+i).value = document.getElementById('uomID'+i).value;
        document.getElementById('weightValueId'+i).value = document.getElementById('weightID'+i).value;
        
    }
     //Suman code end to fix issue with MPS functionality.
               AddPkgsToForm();           
        
          }
           if(checkPackageOptions() == false)
           {
             return false;
           }
       
            var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
            var CODAmt = document.DynaShipmentShipSaveForm.codAmt1.value
            var cod = document.DynaShipmentShipSaveForm.chCOD1.value;
        
             if(str.substring(0,3)=="100" || str.substring(0,3)=="111" || str.substring(0,3)=="110")
             {
                   if(cod=="Y")
                   {
                        if(trim(CODAmt)=="" || parseFloat(trim(CODAmt))<=0 || isNaN(CODAmt))
                        {
                
                          alert("Please Enter The COD Amount Correctly");
                
                          return false;
                          
                        }
                    
                    }
             }   
            if(document.getElementById("submitButtonId").value=='ADD')
            {
           
             //alert(document.DynaShipmentShipSaveForm.submitButton.value);
             document.DynaShipmentShipSaveForm.submitButton1.value="0"
             return false;
            }
            else
            {
              document.DynaShipmentShipSaveForm.submitButton1.value="1"
            }
        } 
        else
            {
                if(document.getElementById("submitButtonId").value =="Void")
                {   
                  if(voidStatusFlagValue()==false)
                  {
                  return false;
                  }
      
                }
             
            if((document.getElementById("submitButtonId").value=='ADD') || (document.getElementById("submitButtonId").value=='DELETE') || (document.getElementById("submitButtonId").value=='CLEAR'))
            {
             return false;
            }
            else
            {
            document.DynaShipmentShipSaveForm.submitButton1.value="1"
                    
            }
            }
            
    }
    
    
    function upsPackValid()
    {
             
            var packcount = document.getElementById("countPacketsID").value;
                   
            var upsMode=document.getElementById("upsMode").value;
            
             var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
            
            if(str.substring(0,3)=="100" && upsMode=="UPS Direct")
            {
                for(var index =  1;index<=packcount;index ++)
                {
                
               // var str = document.DynaShipmentShipSaveForm.shipMethod.value;
    
    // if(str.substring(0,3)=="100" )//added by vandana
           // {
                var upsServiceLevelCode=trim(document.DynaShipmentShipSaveForm.ajaxUpsServiceLevelCode.value);
               upsServiceLevelCode=parseInt(upsServiceLevelCode);
                  if(document.getElementById('pkgingID'+index).value=="" || document.getElementById('pkgingID'+index).value==null || document.getElementById('pkgingID'+index).value=="null" || document.getElementById('pkgingID'+index).value=="NaN")
                   {
                   document.getElementById('pkgingID'+index).value="02";
                   }
                   if(document.getElementById('packageDeclaredValueID'+index).value=="" || document.getElementById('packageDeclaredValueID'+index).value==null || document.getElementById('packageDeclaredValueID'+index).value=="null")
                   {
                   document.getElementById('packageDeclaredValueID'+index).value="0.00";
                   }
                   if(document.getElementById('chCODID'+index).value=="Y")
                   {
                       if(document.getElementById('codTypeID'+index).value=="" || document.getElementById('codTypeID'+index).value==null || document.getElementById('codTypeID'+index).value=="null")
                       {
                       document.getElementById('codTypeID'+index).value="3";
                       }
                       if(document.getElementById('codFundsCodeID'+index).value=="" || document.getElementById('codFundsCodeID'+index).value==null || document.getElementById('codFundsCodeID'+index).value=="null")
                       {
                       document.getElementById('codFundsCodeID'+index).value="8";
                       }
                       if(document.getElementById('codCurrCodeID'+index).value=="" || document.getElementById('codCurrCodeID'+index).value==null || document.getElementById('codCurrCodeID'+index).value=="null")
                       {
                       document.getElementById('codCurrCodeID'+index).value="USD";
                       }
                       if(document.getElementById('delConfirmID'+index).value=="" || document.getElementById('delConfirmID'+index).value==null || document.getElementById('delConfirmID'+index).value=="null")
                       {
                       //document.DynaShipmentShipSaveForm['delConfirm'+index].value="3";
                       //alert(document.DynaShipmentShipSaveForm['delConfirm'+index].value);          
                       }
                   }//end of  if(document.DynaShipmentShipSaveForm['chCOD'+index].value=="Y")
                   else
                   {
                     if(document.getElementById('codTypeID'+index).value=="" || document.getElementById('codTypeID'+index).value==null ||  document.getElementById('codTypeID'+index).value=="null")
                       {
                       document.getElementById('codTypeID'+index).value="";
                       }
                       if(document.getElementById('codFundsCodeID'+index).value=="" || document.getElementById('codFundsCodeID'+index).value==null || document.getElementById('codFundsCodeID'+index).value=="null")
                       {
                       document.getElementById('codFundsCodeID'+index).value="";
                       }
                       if(document.getElementById('codCurrCodeID'+index).value=="" || document.getElementById('codCurrCodeID'+index).value==null || document.getElementById('codCurrCodeID'+index).value=="null")
                       {
                       document.getElementById('codCurrCodeID'+index).value="";
                       }
                       if(document.getElementById('delConfirmID'+index).value=="" || document.getElementById('delConfirmID'+index).value==null || document.getElementById('delConfirmID'+index).value=="null")
                       {
              
                       }
                   }//else of if(document.DynaShipmentShipSaveForm['chCOD'+index].value=="Y")
                   if(upsServiceLevelCode==14 || upsServiceLevelCode==3 || upsServiceLevelCode==12)//&& delConfirm!=3 
                   {
                    // document.DynaShipmentShipSaveForm['delConfirm'+index].value="3";
                    //alert('Please select Adult Signature Required for Delivery Confirmation');
                    //return false;
                   }
               }
               //end of for(var index =  1;index<=packcount;index ++)
            }//end of if(str.substring(0,3)=="100" && upsMode=="UPS Direct")
            
    //alert(document.DynaShipmentShipSaveForm['codFundsCode'+index].value);
    
      if(str.substring(0,3)=="110" || str.substring(0,3)=="111")
            {
                for(var index =  1;index<=packcount;index ++)
                {
    
    
             
            
            if(document.getElementById('packageDeclaredValueID'+index).value=="" || document.getElementById('packageDeclaredValueID'+index).value==null)
    
             {
    
             document.getElementById('packageDeclaredValueID'+index).value="0.00";
    
             }
             if(document.getElementById('PackageSurchargeID'+index).value=="" || document.getElementById('PackageSurchargeID'+index).value==null)
    
             {
    
             document.getElementById('PackageSurchargeID'+index).value="0.0";
    
             }
    
    
            if(document.getElementById('returnShipmentID'+index).value=="" || document.getElementById('returnShipmentID'+index).value==null)
    
             {
    
             document.getElementById('returnShipmentID'+index).value="NONRETURN";
    
             }
    
            
             if(document.getElementById('signatureOptionID'+index).value=="" || document.getElementById('signatureOptionID'+index).value==null)
    
             {
    
             document.getElementById('signatureOptionID'+index).value="NONE";
    
             }  
             
             if(str.substring(0,3)=="111")
    
            {
           
            if(document.getElementById('returnShipmentID'+index).value =="PRINTRETURNLABEL" && document.DynaShipmentShipSaveForm['packageDeclaredValue'+index].value >100)
            {
            alert("Package Declared value cannot be greater than 100 for Package "+index);
            return false;
            }
            } 
           
         //    if(document.DynaShipmentShipSaveForm['chCOD'+index].checked==true &&window.DynaShipmentShipSaveForm['returnShipment'+index].value=="PRINTRETURNLABEL" )
             if(document.getElementById('chCODID'+index).value=="Y" &&window.document.getElementById('returnShipmentID'+index).value=="PRINTRETURNLABEL" )
            {
            alert("COD and Return shipment both can not be allowed at a time for Package "+index);
            return false;
            }
    
    
            
            }
    
       }
    
       return true;
    
    }
    
//Shiva added below function for DHL
        function dhlValidation()
        {
        
            var shipToAddressLine1 = document.DynaShipmentShipSaveForm.shipToAddress.value;
            var shipToLocation = document.getElementById("customerLocationId").options[document.getElementById("customerLocationId").selectedIndex].value;
			
            var refOneText = document.DynaShipmentShipSaveForm.reference1.value;
            var refTwoText = document.DynaShipmentShipSaveForm.reference2.value;
            var shipAddInfoVar = document.getElementById('shipAddInfo').value; 
             
            if(shipAddInfoVar == null || shipAddInfoVar == ''){
                alert("Please enter additional infomation.");
                document.getElementById('shipAddInfo').focus();
                return false;
            }
            if(shipToAddressLine1.length> 35)
            {
//                alert('Ship To Address line 1 field should not exceed 35 characters');
//                document.DynaShipmentShipSaveForm.shipToAddress.focus();
//                return false;
            }
            
            if(shipToLocation.length> 25)
            {
//                alert('Ship to Location name cannot exceed 25 characters');
//                document.getElementById('customerLocationId').focus();
//                return false;
            }
            //Shiva commented to fix the bug #3882
//            if(refOneText == 0 && refTwoText == 0)
//            {
//                alert('Reference # 1 and Reference # 2 field cannot be empty');
//                document.DynaShipmentShipSaveForm.reference1.focus();
//                return false;
//            }
//            else
//          Commented code end
            if((refOneText+refTwoText).length > 35)
            {
                alert('Length of Reference # 1 and Reference # 2 field both put together should be less than 35 characters');
                document.DynaShipmentShipSaveForm.reference1.focus();
                return false;
            }
            var packcount = document.getElementById("countPacketsID").value;    
            for(var i=1;i<=packcount;i++)
            {
                var dimens= document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].value;
     
                var n=dimens.split('*');
                var length = n[0];
                var width = n[1];
                var height = n[2];
                var unit = n[3];
                //alert(length+" "+ width+" "+ height +" "+unit);
                
                var uom=document.getElementById('uomID'+i).value;
                
                //Shiva added below code for #3938
                uom=uom.toUpperCase();
                document.getElementById('uomID'+i).value=uom;
                if(uom!='LB' && uom!='KG'){
                    alert("UOM value should be either KG or LB");
                    document.getElementById('uomID'+i).focus();
                    return false;
                }
                //Shiva code end
                
                if(uom == 'KG' && unit !='CM')
                {
                    alert("If UOM is in KG then Dimension Unit must be in CM for the package "+i);
                    document.getElementById('uomID'+i).focus();
                    return false;
                }
                if(uom == 'LB' && unit !='IN')
                {
                    alert("If UOM is in LB then Dimension Unit must be in IN for the package "+i);
                    document.getElementById('uomID'+i).focus();
                    return false;
                }
                if(length <= 0 || width <= 0 || height <= 0)
                {
                    alert("Package dimension (length, width, height) should be greater than 0 for package number "+i);
                    document.getElementById('dimButtonID'+i).focus();
                    return false;
                }
                
            }
        
            return true;
        }
    
    function focus()
    {
    
     // document.DynaShipmentShipSaveForm.customerName.focus();
      /*
      var str=document.DynaShipmentShipSaveForm.itemDescription.value;
      if(str.substring(0,72))
       document.DynaShipmentShipSaveForm.itemDescription.value=trim(document.DynaShipmentShipSaveForm.itemDescription.value);
       */
    }
    function voidDisable()
    {
      document.DynaShipmentShipSaveForm.voidButton.disabled=true;
    }
    function openWin()
    {
     //sambit
     var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    // var str=document.DynaShipmentShipSaveForm.shipMethod.value;
     if(str.substring(0,2)==11)
     {
      fedexWindow=window.open("aascShipmentPoppedFedex.jsp","Post",'width=470,height=470,top=100,left=100,scrollbars=yes, resizable=yes');
      fedexWindow.focus();
     }
     
     
      if(str.substring(0,3)==100)
     {
      fedexWindow=window.open("aascShipmentPoppedFedex.jsp","Post",'width=470,height=470,top=100,left=100,scrollbars=yes, resizable=yes');
      fedexWindow.focus();
     }
     
    }
    function tpOpenWin()
    {
    //======================Modified By Narasimha Earla================================
    var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    // alert("strValue  ::"+strValue);
     // var str=document.ShipInsertForm.shipMethod.options[document.ShipInsertForm.shipMethod.selectedIndex].value;
      var carrierCode=strValue.substring(0,3);
      var upsMode=trim(document.getElementById("upsMode").value);
      //Shiva modified #2090
      var payMethod=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
      var carrierPaymethodSelected = document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].text;

      if(carrierCode=="100" && carrierPaymethodSelected == "RECIPIENT"){              //Mahesh added code for UPS RECIPIENT
      payMethod="FC";
      }
    // if(carrierCode=="100" && upsMode=="UPS Direct")   //Shiva commented #2090
    if(carrierCode=="100")
     {
      upsPayMethodPopUp(); 
      
      if(payMethod=="FC")
      {
       tpwindow =  window.open("aascUpsDirectPayMethodDetails.jsp?payMethod="+payMethod+"&carrierCode="+carrierCode,"Post",'width=400,height=300,top=100,left=100,scrollbars=yes, resizable=yes');
         tpwindow.focus();
      }
    
    //end #2090
     }
     else if((carrierCode=="110" || carrierCode=="111") && payMethod=="CG"){                       //Mahesh added code for FedEx RECIPIENT     
             var countryCode= document.getElementById("tpCountrySymbolID").value;
             tpWindow=window.open("aascUpsDirectPayMethodDetails.jsp?payMethod="+payMethod+"&carrierCode="+carrierCode,"Post",'width=400,height=300,top=100,left=100,scrollbars=yes, resizable=yes');
            // tpwindow.focus();
     }
     else
     {
    openTpPopup();
     }
     //==================== 
    }
    //===================Added by Narasimha Earla========================
    function upsPayMethodPopUp()
    {
    var payMethod=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
    
    var upsMode=trim(document.getElementById("upsMode").value);
     if(upsMode=='ConnectShip')
       {
              if(payMethod=="TP")
             {
            var countryCode= document.getElementById("tpCountrySymbolID").value;
             tpWindow=window.open("aascPoppedThirdParty.jsp?countryCode="+countryCode,"Post",'width=400,height=300,top=100,left=100,scrollbars=yes, resizable=yes');
            // tpwindow =  window.open("aascAdhocUpsDirectPayMethodDetails.jsp?payMethod="+payMethod,"Post",'width=400,height=300,top=100,left=100,scrollbars=yes, resizable=no');
        tpwindow.focus();
        
       }
       }
       else if (upsMode == "ShipExec"){
        if(payMethod=="TP")
        {
        var countryCode= document.getElementById("tpCountrySymbolID").value;//Shiva added  #2090
        tpWindow=window.open("aascSEThirdParty.jsp?countryCode="+countryCode,"Post",'width=700,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
        tpWindow.focus();
       }
      
        if(payMethod=="DU")
        {
        var countryCode= document.getElementById("tpCountrySymbolID").value;//Shiva added  #2090
        tpWindow=window.open("aascSEThirdParty.jsp?countryCode="+countryCode,"Post",'width=700,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
        tpWindow.focus();
       }
       }
       else
       {
        if(payMethod=="TP")
        {
         tpwindow =  window.open("aascUpsDirectPayMethodDetails.jsp?payMethod="+payMethod,"Post",'width=400,height=300,top=100,left=100,scrollbars=yes, resizable=yes');
         tpwindow.focus();
        }
       }
    }
    function openTpPopup()
    {
    
    var str=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
      if(str=="TP" || str=="DU")
       {
       var upsMode=trim(document.getElementById("upsMode").value);
//       alert("upsMode::"+upsMode);
       if (upsMode == "ShipExec"){
        var countryCode= document.getElementById("tpCountrySymbolID").value;//Shiva added  #2090
        tpWindow=window.open("aascSEThirdParty.jsp?countryCode="+countryCode,"Post",'width=700,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
        tpWindow.focus();
       }
       else {
       var countryCode= document.getElementById("tpCountrySymbolID").value;//Shiva added  #2090
        tpWindow=window.open("aascPoppedThirdParty.jsp?countryCode="+countryCode,"Post",'width=400,height=300,top=100,left=100,scrollbars=yes, resizable=yes');
        tpWindow.focus();
       }
       }
    }
    
    
    function autoCarrierACNumber()
    {
      var str=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
      var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
      var carrierCode=strValue.substring(0,3);
       var upsMode=trim(document.getElementById("upsMode").value);
       if((carrierCode==100 && (str=="PP" || str=="TP" || str=="CG" || str=="FC")) || ((carrierCode==110 || carrierCode==111) && (str=="PP" || str=="TP" || str=="CG")) || (carrierCode==114 && (str=="PP" || str=="CG" ))){ 
         document.getElementById("carrierAccountNumberId").value=document.getElementById("CarrierAcHiddenId").value;
          // vikas added code to mask te account number 
              if(document.getElementById("maskAccountCheckID").value == "Y"){
              mask(); 
              } 
              // vikas code ended
              }
      else{
         document.getElementById("carrierAccountNumberId").readOnly=false;
         document.getElementById("carrierAccountNumberId").value="";
          }
      var intlFlag4=document.DynaShipmentShipSaveForm.intlFlag.value;
      //Sunanda modified the below code for bug fix #2997
      //Mahesh Added CG and modified below conditions for fedEx Recepient
      if(str=="TP" || str=="CG") // removed intlFlag condition bug #3561 
      {
//         document.getElementById("tpPopupId").src="buttons/aascDetails1.png";
         document.getElementById("tpPopupId").disabled=false;
         
         if(str=="TP")
         tpOpenWin();
      }
    }
    
    function chgShipMethod()
    { 
    
       var shipMethod= document.getElementById("shipMethodId").value;  //document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
       
       if(shipMethod.indexOf('Select Ship Method') == -1) // If ship method LOV is not Select
       {
          var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
          var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
       }
       else{
          var str=document.getElementById("shipMethodId").value;
          var strValue = document.getElementById("shipMethodId").value;
       }
       
       var smstr = strValue.substring(strValue.indexOf("*")+1,strValue.indexOf("@@"));
       
       document.getElementById("shipMethodHiddenId").value= str;
       document.getElementById("shipMethodHideId").value= smstr;    // Assigning value of the selected Ship Method field to shipMethodHideId hidden field. Bug #2746. By Y Pradeep
       var carrierCode = strValue.substring(0,strValue.indexOf("||"));
       if(carrierCode == "100"){
            document.getElementById("carrierNameHideId").value = "UPS";
       }
       else if(carrierCode == "110"){
            document.getElementById("carrierNameHideId").value = "FDXE";
       }
       else if(carrierCode == "111"){
            document.getElementById("carrierNameHideId").value = "FDXG";
       }
      //Mahesh added for Stamps.com 
       else if(carrierCode == "115"){
            document.getElementById("carrierNameHideId").value = "Stamps.com";
       }
       //Shiva added below code for DHL
       else if(carrierCode == "114"){
            document.getElementById("carrierNameHideId").value = "DHL";
       }
       document.getElementById("shipMethodValueId").value = strValue;
       var fedexstr = document.getElementById("shipMethodId").value;
       
       if(fedexstr.substring(0,3)==110||fedexstr.substring(0,3)==111  || fedexstr.substring(0,3)==100 || fedexstr.substring(0,3)==114 || fedexstr.substring(0,3)==115)
       {
          if(fedexstr.substring(0,3)==110||fedexstr.substring(0,3)==111 ||fedexstr.substring(0,3)==115)
          {
              if(document.getElementById("shipMethodId").disabled ==false )
              {
               document.getElementById("dropOftypeId").disabled =false;
               document.getElementById("packagesId").disabled =false;
              }
              else
              {
               document.getElementById("dropOftypeId").disabled=true;
               document.getElementById("packagesId").disabled=true; 
              }
          } 
          
       }
       else
       {
         document.getElementById("dropOftypeId").disabled =true;
         document.getElementById("packagesId").disabled =true;
       }
       
       var defaultCarrierSessionValue=document.getElementById("defaultCarrierSessionValuesFlagHiddenID").value;
       var errorKey = document.getElementById("errorKeyID").value;
       var size=strValue.length;
    //   var smstr = strValue.substring(strValue.indexOf("*")+1,strValue.indexOf("@@"));
       var ShipFlag = document.getElementById("flagShipID").value;
       if(defaultCarrierSessionValue=='Y')
       {
         var carrierProfileOptionsShipmethod=document.getElementById("carrierProfileOptionsShipmethodHiddenID").value;
         if(carrierProfileOptionsShipmethod==smstr)
         {
            var sigOptionsHidden=document.getElementById("sigOptionsHiddenID").value;
            var CODFlagHidden=document.getElementById("CODFlagHiddenID").value;
            var HALFlagHidden=document.getElementById("HALFlagHiddenID").value;
            var ReturnFlagHidden=document.getElementById("ReturnFlagHiddenID").value;
            var CODFundsCodeHidden=document.getElementById("CODFundsCodeHiddenID").value;
            var CODCurrCodeHidden=document.getElementById("CODCurrCodeHiddenID").value;
            var CODTypeHidden=document.getElementById("CODTypeHiddenID").value;
            var ProfileHazmatFlagHidden=document.getElementById("ProfileHazmatFlagHiddenID").value;
            var ProfileCarrierPackagingHidden=document.getElementById("ProfileCarrierPackagingHiddenID").value;
            
            document.getElementById("defaultPackageOption").value = "true";
            document.getElementById("pkging1").value = ProfileCarrierPackagingHidden;
            document.getElementById("LargePackage1").value = '';
            document.getElementById("AdditionalHandling1").value = '';
            document.getElementById("signatureOption1").value = sigOptionsHidden;
            document.getElementById("delConfirm1").value = sigOptionsHidden;
            document.getElementById("codType1").value = CODTypeHidden;
            document.getElementById("codFundsCode1").value = CODFundsCodeHidden; 
            document.getElementById("codCurrCode1").value = CODCurrCodeHidden;
            document.getElementById("chCOD1").value = CODFlagHidden;
            document.getElementById("holdAtLocation1").value = HALFlagHidden;
            document.getElementById("returnShipment1").value = ReturnFlagHidden;
            document.getElementById("HazMatFlag1").value = ProfileHazmatFlagHidden;
         }
         else
         {
           if((errorKey==null || errorKey=="" || errorKey=="null") && (ShipFlag !="Y"))
           {
            document.getElementById("defaultPackageOption").value = "false";
            document.getElementById("pkging1").value = "";
             document.getElementById("LargePackage1").value = "";
            document.getElementById("AdditionalHandling1").value = "";
            document.getElementById("signatureOption1").value = "";
            document.getElementById("delConfirm1").value = "";
            document.getElementById("codType1").value = "";
            document.getElementById("codFundsCode1").value = "";
            document.getElementById("codCurrCode1").value = "";
            document.getElementById("chCOD1").value = "";
            document.getElementById("holdAtLocation1").value = "";
            document.getElementById("returnShipment1").value = "";
            document.getElementById("HazMatFlag1").value = "";
          }
        }
      }


      if(fedexstr.substring(5,6)=='1' || fedexstr.substring(5,6)=='3' || fedexstr.substring(5,6)=='7')
      {

      
        if(document.getElementById("shipMethodId").disabled == false)
        { 
          document.getElementById("chkSatShipment123").disabled=false;
        } else{
          document.getElementById("chkSatShipment123").disabled=true;
        }
      }
      else{    
        document.getElementById("chkSatShipment123").checked=false;
        document.getElementById("chkSatShipment123").value="N";
        document.getElementById("chkSatShipmentHidden").value="N";
        document.getElementById("chkSatShipment123").disabled=true;
      }
      
      //------------------Suman Added code for SCCloud to fix #2490
        var str=document.DynaShipmentShipSaveForm.shipMethod.options[document.DynaShipmentShipSaveForm.shipMethod.selectedIndex].value;

        if(document.DynaShipmentShipSaveForm.shipMethod.disabled == true)
        {
                document.DynaShipmentShipSaveForm.saturdayShipFlag.disabled=true;
                if(document.DynaShipmentShipSaveForm.saturdayShipFlag.value=="CHECKED")
                {
                        document.DynaShipmentShipSaveForm.saturdayShipFlag.checked=true;
                        document.DynaShipmentShipSaveForm.saturdayShipFlag.value="Y";
                        document.DynaShipmentShipSaveForm.chkSatShipmentHidden.value="Y";
                }
        }
        else
        {
                if(document.getElementById("satDeliveryCheckID").value=="Y")
                {
        
                        var satflagcheck=document.DynaShipmentShipSaveForm.shipMethod.options[document.DynaShipmentShipSaveForm.shipMethod.selectedIndex].text;
                        var satval =satflagcheck.search(/Sat Delivery|Sat|Saturday/i);      // Modified by Suman Gunda
        
                        if(satval>=0){
                                document.DynaShipmentShipSaveForm.saturdayShipFlag.checked=true;
                                document.DynaShipmentShipSaveForm.saturdayShipFlag.value="Y";
                                document.DynaShipmentShipSaveForm.chkSatShipmentHidden.value="Y";
                        }
                        else
                        {
                                if(str.substring(5,6)=='1' || str.substring(5,6)=='3' || str.substring(5,6)=='7')
                                {
                                        if(document.DynaShipmentShipSaveForm.shipMethod.disabled == false)
                                        {
                                                document.DynaShipmentShipSaveForm.saturdayShipFlag.disabled=false;
                                        }
                                }
                                else
                                {
                                        document.DynaShipmentShipSaveForm.saturdayShipFlag.checked=false;
                                        document.DynaShipmentShipSaveForm.saturdayShipFlag.value="N";
                                        document.DynaShipmentShipSaveForm.chkSatShipmentHidden.value="N";
                                        document.DynaShipmentShipSaveForm.saturdayShipFlag.disabled=true;
                                }
                        }
                }   
        }
        
        var carrierServiceLevel = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        var carrierCode = carrierServiceLevel.substring(0,3);
//        alert('carriercode::::::'+carrierCode);
        if(carrierCode != '000')
            getEmailDetails();
    //------------------Suman code end for SCCloud
        /*if(fedexstr.substring(0,3)==110||fedexstr.substring(0,3)==111)
        {  
            setvalues();  // Added by Suman G for #2665 issue
        }  */
    }
    
    function chgCarrierPayMethod()
    {
       var index=document.getElementById("carrierPayMethodId").selectedIndex;
      
       if(index!='-1')
       {
          var str=document.getElementById("carrierPayMethodId").options[index].value;
          var str2=document.getElementById("carrierPayMethodId").options[index].text;
          document.getElementById("carrierPayMethodHide").value= str2;
       }
    
    //   if(str=="TP")
    //   {
    //     
    //     document.getElementById("tpPopupId").src="buttons/aascMore1.png";
    //   }
    //   else
    //   {
    //     
    //     document.getElementById("tpPopupId").src="buttons/aascMoreOff1.png";
    //   }
       var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
       var carrierCode=strValue.substring(0,3);
       var upsMode=trim(document.getElementById("upsMode").value);
       if(carrierCode=="100" && upsMode=="UPS Direct" &&(str=="FC"))
       {
//            document.getElementById("tpPopupId").src="buttons/aascDetails1.png";
            document.getElementById("tpPopupId").disabled=false;
            upsPayMethodPopUp();
       }
       
       else
       {
         try{ 
//         document.getElementById("tpPopupId").src="buttons/aascDetailsOff1.png"; 
           document.getElementById("tpPopupId").disabled=true;
         } catch(e){}
       }
    } 
  //Sunanda added the below code for bug fix #2997  
  function checkCarrierTerm()	{
  //alert("inside carrier term");
  var carrierTerm = document.getElementById("carrierPayMethodId").selectedIndex;
  //var intlFlag4=document.DynaShipmentShipSaveForm.intlFlag.value;
  //alert("inside carrier term"+carrierTerm);
  //alert("inside intlFlag4 term"+intlFlag4);

//   if(carrierTerm =='3'&& document.DynaShipmentShipSaveForm.intlFlag.value=="Y" )
//   {
//    alert("International Shipments Does Not Support Third Party Billing");
//    document.DynaShipmentShipSaveForm.carrierPayMethod.focus();
//    return ;
//
//   }

   }
    /*function templateCheck()
    {
       if(document.getElementById("shipMethodId").value != '' )
         var strShipMethod=document.getElementById("shipMethodId").options[document.getElementById(".shipMethodId").selectedIndex].value;
       else
         var strShipMethod=document.getElementById("shipMethodId").value;
       if(strShipMethod.substring(0,3)==110||strShipMethod.substring(0,3)==100||strShipMethod.substring(0,3)==111)
       {
           
       }
       else
       {
          if(document.getElementById("shipMethodId").value != '' ){
            
          }
       }
    }*/
    
    function weightValid()
    {
      var str3=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
      if(str3.substring(0,2)==11 || str3.substring(0,3)==100)
      {
        if(document.DynaShipmentShipSaveForm.weight.value >150)
        {
          alert("Weight value should be less than 150");
          document.DynaShipmentShipSaveForm.weight.focus()
          return false;
        }
      }     
      return true;
    }    
    function FedexValid()
    {
    
      var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
     
      if(str=="100||3")
      {
        if(contactName()==false)
        {
         return false;
        }
      }
     
      {
        if(phoneNumber()==false)
        {
         return false;
        }
    
      }
      return true;
    }
    
    function tpCompanyName()
    {
        if(document.tppopperform.tpCompanyName.value=="")
        {
         alert("Please enter the Third Party Company Name");
         document.tppopperform.tpCompanyName.focus();
         return false;
         }
        return true; 
    }
    function tpAddress()
    {
        if(document.tppopperform.tpAddress.value=="")
        {
         alert("Please enter the Third Party Address");
         document.tppopperform.tpAddress.focus();
         return false;
         }
        return true; 
    }
    function tpCity()
    {
        if(document.tppopperform.tpCity.value=="")
        {
         alert("Please enter the Third Party City");
         document.tppopperform.tpCity.focus();
         return false;
         }
        return true; 
    }
    function tpState()
    {
        if(document.tppopperform.tpState.value=="")
        {
         alert("Please enter the Third Party State");
         document.tppopperform.tpState.focus();
         return false;
         }
        return true; 
    }
    function tpPostalCode()
    {
        if(document.tppopperform.tpPostalCode.value=="")// || isNaN(document.tppopperform.tpPostalCode.value))
        {
         alert("Please Enter valid Third Party Postal Code ");
         document.tppopperform.tpPostalCode.focus();
         return false;
         }
         if(document.tppopperform.tpPostalCode.value.length <=4  && document.tppopperform.tpPostalCode.value.length !=5){
//                                    alert("Please enter valid 5 or 9 digit Third Party Postal Code");
//                                    document.tppopperform.tpPostalCode.focus();
//                                    return false;
                            }
          if(document.tppopperform.tpPostalCode.value.length > 5  &&  document.tppopperform.tpPostalCode.value.length !=9){
//                                    alert("Please enter valid 5 or 9 digit Third Party Postal Code");
//                                    document.tppopperform.tpPostalCode.focus();
//                                    return false;
                            }
        return true; 
    }
    function tpCountrySymbol()
    {
       var str=document.tppopperform.tpCountrySymbol.options[document.tppopperform.tpCountrySymbol.selectedIndex].text;
        if(str=="Select CountrySymbol")
        {
         alert("Please select the Third Party Country Symbol");
         document.tppopperform.tpCountrySymbol.focus();
         return false;
         }
        return true; 
    }
    function tpPutValue()
    {
       window.opener.document.getElementById("tpCountrySymbolID").value =document.tppopperform.tpCountrySymbol.options[document.tppopperform.tpCountrySymbol.selectedIndex].text;
       window.opener.document.getElementById("tpCompanyNameID").value=document.tppopperform.tpCompanyName.value;
       window.opener.document.getElementById("tpAddressID").value=document.tppopperform.tpAddress.value;
       window.opener.document.getElementById("tpCityID").value=document.tppopperform.tpCity.value;
       window.opener.document.getElementById("tpStateID").value=document.tppopperform.tpState.value;
       window.opener.document.getElementById("tpPostalCodeID").value=document.tppopperform.tpPostalCode.value;
       if(tpvalidation())
            window.close();
    }
    function tpCancelValue()
    {
       window.opener.document.getElementById("tpCountrySymbolID").value ='';
       window.opener.document.getElementById("tpCompanyNameID").value='';
       window.opener.document.getElementById("tpAddressID").value='';
       window.opener.document.getElementById("tpCityID").value='';
       window.opener.document.getElementById("tpStateID").value='';
       window.opener.document.getElementById("tpPostalCodeID").value='';

       window.close();
    }
    function displayInfo()
    {
      var tpCountrySymbol=window.opener.document.getElementById("tpCountrySymbolID").value;
      if(tpCountrySymbol != "")
      {
        document.tppopperform.tpCountrySymbol.options[0]=new Option(tpCountrySymbol, tpCountrySymbol, true, true);
      } 
      else
      {
        document.tppopperform.tpCountrySymbol.options[0]=new Option("Select CountrySymbol", "Select CountrySymbol", true, true);
      }
      var tpCompanyName=window.opener.document.getElementById("tpCompanyNameID").value;
      document.tppopperform.tpCompanyName.value=tpCompanyName;
      var tpAddress=window.opener.document.getElementById("tpAddressID").value;
      document.tppopperform.tpAddress.value=tpAddress;
      var tpCity=window.opener.document.getElementById("tpCityID").value;
      document.tppopperform.tpCity.value=tpCity;
      var tpState=window.opener.document.getElementById("tpStateID").value;
      document.tppopperform.tpState.value=tpState;
      var tpPostalCode=window.opener.document.getElementById("tpPostalCodeID").value;
      document.tppopperform.tpPostalCode.value=tpPostalCode;

     }
     function tpvalidation()
    {
      if(tpCompanyName()==false)
      {
        return false;
      }
      if(tpAddress()==false)
      {
        return false;
      }
      if(tpCity()==false)
      {
        return false;
      }
      if(tpState()==false)
      {
        return false;
      }
      if(tpPostalCode()==false)
      {
        return false;
      }
      if(tpCountrySymbol()==false)
      {
        return false;
      }
      return true;
    }


    
    /*function loadInfo()
    {
      var  str =  window.opener.document.DynaShipmentShipSaveForm.shipMethodHidden.value;
    }*/
    function trim(str)
    {
      return str.replace(/^\s*|\s*$/g,"");
    }
    function putValue()
    {     // DynaShipmentShipSaveForm.shipFromOrg changed to DynaShipmentShipSaveForm.shipFromLoc for new Shipment Order
          window.opener.document.DynaShipmentShipSaveForm.shipFromLoc.value=document.popperform.shipFromLocation.value;
          window.opener.document.DynaShipmentShipSaveForm.shipFromAddressLine1.value=document.popperform.shipFromAddressLine1.value;
          window.opener.document.DynaShipmentShipSaveForm.shipFromAddressLine2.value=document.popperform.shipFromAddressLine2.value;
          window.opener.document.DynaShipmentShipSaveForm.shipFromCity.value=document.popperform.shipFromCity.value;
          window.opener.document.DynaShipmentShipSaveForm.shipFromState.value=document.popperform.shipFromState.value;
          window.opener.document.DynaShipmentShipSaveForm.shipFromPostalCode.value=document.popperform.shipFromPostalCode.value;
          window.opener.document.DynaShipmentShipSaveForm.shipFromCountry.value=document.popperform.shipFromCountry.value;
          window.opener.document.DynaShipmentShipSaveForm.shipFromPhoneNumber1.value=document.popperform.shipFromPhoneNumber1.value;
          
         
          var postalCode= trim(document.popperform.shipFromPostalCode.value);
          if( postalCode.length > 5)
          {			
                             window.opener.document.DynaShipmentShipSaveForm.shipFromPostalCode.value=postalCode.substring(0,5);	 
                            }
    
    
    
                if(trim(document.popperform.shipFromLocation.value)==""){
                                    alert("Please enter The Ship-From Company name");
                                    document.popperform.shipFromLocation.focus();
                                    return false;
                            }
     
                            if(trim(document.popperform.shipFromAddressLine1.value)==""){
                                    alert(" Please enter The Ship-From AddressLine1 Value");
                                    document.popperform.shipFromAddressLine1.focus();
                                    return false;
                            }
      
                            if(trim(document.popperform.shipFromCity.value)==""){
                                    alert(" Please enter The Ship-From City Value");
                                    document.popperform.shipFromCity.focus();
                                    return false;
                            }
      
       
                            if(trim(document.popperform.shipFromState.value)==""){
                                    alert(" Please enter The Ship-From State Value");
                                    document.popperform.shipFromState.focus();
                                    return false;
                            }
            
                            if(trim(document.popperform.shipFromPostalCode.value)==""){
                                    alert(" Please enter The Ship-From PostalCode Value");
                                    document.popperform.shipFromPostalCode.focus();
                                    return false;
                            }
       
      
        
       /*  if(postalCode.length != 5 || postalCode.length > 10){
                                    alert(" Please enter Valid 5 Or 9 Digit Ship-From PostalCode Value :");
                                    document.popperform.shipFromPostalCode.focus();
                                    return false;
                            }*/
                            if(trim(document.popperform.shipFromCountry.value)==""){
                                    alert(" Please enter The Ship-From Country Value");
                                    document.popperform.shipFromCountry.focus();
                                    return false;
                            } 
          
          if(trim(document.popperform.shipFromPhoneNumber1.value)==""){
                                    alert(" Please enter The Ship-From PhoneNumber1 Value");
                                    document.popperform.shipFromPhoneNumber1.focus();
                                    return false;
                            }
          
//           if(isNaN(trim(document.popperform.shipFromPhoneNumber1.value))){
//                                    alert(" Please enter numeric value as  PhoneNumber1 ");
//                                    document.popperform.shipFromPhoneNumber1.focus();
//                                    return false;
//                            }
          
            if((trim(document.popperform.shipFromPhoneNumber1.value)).length>10)
          {
              alert("Please enter shipFromPhoneNumber1 less than 10 digits ");
                    return false;
          }
           
       window.close();
    }
    

    
    
    function fedexPopupValidate()
    {
    
            if(trim(document.popperform.shipFromLocation.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From Company name");
                                    document.popperform.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.popperform.shipFromAddressLine1.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From AddressLine1 Value");
                                    document.popperform.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.popperform.shipFromCity.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From City Value ");
                                    document.popperform.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.popperform.shipFromState.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From State Value");
                                    document.popperform.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.popperform.shipFromPostalCode.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From PostalCode Value ");
                                    document.popperform.popup.focus();
                                    return false;
                            }
          var postalCode= trim(document.popperform.shipFromPostalCode.value);
          
         if(postalCode.length != 5 ){
                                    alert("Please Click On More Button And Enter Valid 5 Or 9 Digit Ship-From PostalCode Value");
                                    document.popperform.shipFromPostalCode.focus();
                                    return false;
                            }
                            if(trim(document.popperform.shipFromCountry.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From Country Value");
                                    document.popperform.popup.focus();
                                    return false;
                            } 
          if(trim(document.popperform.shipFromPhoneNumber1.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From PhoneNumber Value");
                                    document.popperform.popup.focus();
                                    return false;
                            }
          
        
          
          
          return true;
         }
    
    
    function cancelValue()
    {
    
     window.close();
    }
    function packaging()
    {
        if(document.popperform.packages.options[document.popperform.packages.selectedIndex].text=="SelectPackaging")
        {
         alert("Please Select Packaging");
         return false;
         }
        return true; 
    }
    function dropOfType()
    {
        if(document.popperform.dropOftype.options[document.popperform.dropOftype.selectedIndex].text=="SelectType")
        {
         alert("Please Select Drop-Off Type");
         return false;
         }
        return true; 
    }
    function shipFromLocation()
    {
        if(document.popperform.shipFromLocation.value=="")
        {
         alert("Please Enter CustomerName Value");
         document.popperform.shipFromLocation.focus();
         return false;
         }
        return true; 
    }
    function shipFromAddressLine1()
    {
        if(document.popperform.shipFromAddressLine1.value=="")
        {
         alert("Please enter  the Address1 value");
         document.popperform.shipFromAddressLine1.focus();
         return false;
         }
        return true; 
    }
    function shipFromCity()
    {
        if(document.popperform.shipFromCity.value=="")
        {
         alert("Please enter  the City value");
         document.popperform.shipFromCity.focus();
         return false;
         }
        if(document.popperform.shipFromCity.value.length<3)
        {
         alert("City Name Should Be More Than 3 Characters");
         document.popperform.shipFromCity.focus();
         return false;
         }
        return true; 
    }
    function shipFromState()
    {
        var str=document.popperform.shipFromState.value;
        if(!document.popperform.shipFromState.value=="")
        {
          str=str.toUpperCase();
          document.popperform.shipFromState.value=str;
        }
        if(document.popperform.shipFromState.value=="")
        {
         alert("Please enter  the State value");
         document.popperform.shipFromState.focus();
         return false;
         }
        return true; 
    }
    function shipFromStateChgCase()
    {
         
        var str=document.getElementById("shipFromState").value;
        if(document.getElementById("shipFromState").value!="")
        {
          str=str.toUpperCase();
          document.getElementById("shipFromState").value=str;
        }
    }
    function shipFromCountryChgCase()
    {
        var str=document.getElementById("shipFromCountryId").value;
      
        if(str!="")
        {
          str=str.toUpperCase();
     
          document.getElementById("shipFromCountryId").value=str;
          // Eshwari added below 2 lines to fix bug # 2989
          checkIntl();
          document.getElementById("shipFromCountryHiddenId").value=str;
        }
    }
    function shipFromCountry()
    {
        if(document.popperform.shipFromCountry.value=="")
        {
         alert("Please Enter The Country");
         document.popperform.shipFromCountry.focus();
         return false;
         }
        return true; 
    }
    function shipFromPostalCode()
    {
        if(document.popperform.shipFromPostalCode.value=="" || isNaN(document.popperform.shipFromPostalCode.value))
        {
         alert("Please enter  the numeric PostalCode value");
         document.popperform.shipFromPostalCode.focus();
         return false;
         }
          if(document.popperform.shipFromPostalCode.value.length <=4 && document.popperform.shipFromPostalCode.value.length !=5){
                                    alert("Please Enter Valid 5 Or 9 Digit Postal Code");
                                    document.popperform.shipFromPostalCode.focus();
                                    return false;
                            }
          if(document.popperform.shipFromPostalCode.value.length >5 && document.popperform.shipFromPostalCode.value.length !=9){
                                    alert("Please  Enter valid 5 or 9 digit  Postal Code ");
                                    document.popperform.shipFromPostalCode.focus();
                                    return false;
                            }
        return true; 
    }
    function shipFromPhoneNumber1()
    {
        if(document.popperform.shipFromPhoneNumber1.value=="")
        {
         alert("Please Enter The Ship-From PhoneNumber");
         document.popperform.shipFromPhoneNumber1.focus();
         return false;
         }
      
     else{
                                    var phonenumber=document.popperform.shipFromPhoneNumber1.value;
    
                            }
        return true; 
    }
    function shipFromPhoneNumber2()
    {
     if(!document.popperform.shipFromPhoneNumber2.value=="")
     {
        var phonenumber=document.popperform.shipFromPhoneNumber2.value;
                                    
        return true; 
    }
    }
    function fedexValidation()
    {
      if(shipFromLocation()==false)
      {
        return false;
      }
      if(shipFromAddressLine1()==false)
      {
        return false;
      }
      if(shipFromCity()==false)
      {
        return false;
      }
      if(shipFromState()==false)
      {
        return false;
      }
      if(shipFromCountry()==false)
      {
        return false;
      }
      if(shipFromPostalCode()==false)
      {
        return false;
      }
      if(shipFromPhoneNumber1()==false)
      {
        return false;
      }
       if(shipFromPhoneNumber2()==false)
      {
        return false;
      }
      if(dropOfType()==false)
      {
        return false;
      }
       if(packaging()==false)
      {
        return false;
      }
       return true;
    }
    function fedexDisplayInfo()
    {
    
      var shipFromLocation=window.opener.document.DynaShipmentShipSaveForm.shipFromLoc.value;
      document.popperform.shipFromLocation.value=shipFromLocation;
      var shipFromAddressLine1=window.opener.document.DynaShipmentShipSaveForm.shipFromAddressLine1.value;
      document.popperform.shipFromAddressLine1.value=shipFromAddressLine1;
      var shipFromAddressLine2=window.opener.document.DynaShipmentShipSaveForm.shipFromAddressLine2.value;
      document.popperform.shipFromAddressLine2.value=shipFromAddressLine2;
      var shipFromCity=window.opener.document.DynaShipmentShipSaveForm.shipFromCity.value;
      document.popperform.shipFromCity.value=shipFromCity;
      var shipFromState=window.opener.document.DynaShipmentShipSaveForm.shipFromState.value;
      document.popperform.shipFromState.value=shipFromState; 
      var shipFromCountry=window.opener.document.DynaShipmentShipSaveForm.shipFromCountry.value;
      document.popperform.shipFromCountry.value=shipFromCountry; 
      var shipFromPostalCode=window.opener.document.DynaShipmentShipSaveForm.shipFromPostalCode.value;
      document.popperform.shipFromPostalCode.value=shipFromPostalCode; 
      var shipFromPhoneNumber1=window.opener.document.DynaShipmentShipSaveForm.shipFromPhoneNumber1.value;
      document.popperform.shipFromPhoneNumber1.value=shipFromPhoneNumber1; 
    }
    function FillCarrier()
    {
       var paymethod="";
       var shipMethod= document.getElementById("shipMethodId").value;  //document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
       if(shipMethod.indexOf('Select Ship Method') == -1) // If ship method LOV is not Select
       {
           var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
           var strac = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].id;
       }
       else{
          var str=document.getElementById("shipMethodId").value;
          var strac = document.getElementById("shipMethodId").id;
       }
       
       var index=document.getElementById("carrierPayMethodId").selectedIndex; 
       var paymethod="";
      
       if(index!=-1)
       {
         var paymethod = document.getElementById("carrierPayMethodId").options[index].value;
       }
       else
       {
          paymethod="";
       }
       if(paymethod =="PP")
       {
         document.getElementById("carrierAccountNumberId").value =strac;
         
       }
       
       document.getElementById("CarrierAcHiddenId").value =strac;  
    }
    
    
    function unMask(){
//    alert("document.getElementById(carrierPayMethodId).value::"+document.getElementById("carrierPayMethodId").value);
      if(document.getElementById("maskAccountCheckID").value == "Y" && (document.getElementById("carrierPayMethodId").value == "PP" || document.getElementById("carrierPayMethodId").value == "TP" || document.getElementById("carrierPayMethodId").value == "CG" || document.getElementById("carrierPayMethodId").value == "FC")){
            var unmask = document.getElementById("carrierAccountNumberId").value;
            document.getElementById("carrierAccountNumberHidId").value=unmask;
            }
    }
    
    // vikas added code
    function mask(){
        var account1 = document.getElementById("carrierAccountNumberId").value;
    //  alert("account1::"+account1.value);
        var account = document.getElementById("carrierAccountNumberId");
    //alert("account::"+account.value);
        document.getElementById("carrierAccountNumberHidId").value=account1;
        if(account.value != "" && account.value != null)
          {
            var maskedAcc = new Array(account.value.length-3).join('X') + account.value.substr(account.value.length-4, 4);
            document.getElementById("carrierAccountNumberId").value = maskedAcc;
          }  
    }
    // vikas code ended
    
    function FillTask()  
    {
    
    }
    
    
    var dtCh= "-";
    var minYear=1990;
    var maxYear=2100;
    function daysInFebruary (year)
    {
      return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
    }
    function DaysArray(n) 
    {
    // Changed this to days for fix issue in firefox... #4006 (this keyword is not working in Firefox)
        var days = new Array();
            for (var i = 1; i <= n; i++) 
      {
          days[i] = 31
                      if (i==4 || i==6 || i==9 || i==11) {days[i] = 30}
                            if (i==2) {days[i] = 29}
            } 
            return days
    }
    function stripCharsInBag(s, bag)
    {
      var i;
            var returnString = "";
            // Search through string's characters one by one.
            // If character is not in bag, append to returnString.
      for (i = 0; i < s.length; i++)
      {   
                    var c = s.charAt(i);
                    if (bag.indexOf(c) == -1) returnString += c;
            }
            return returnString;
    }
    function isDate(dtStr)
    {
                            var daysInMonth = DaysArray(12)
                            var pos1=dtStr.indexOf(dtCh)
                            var pos2=dtStr.indexOf(dtCh,pos1+1)
                            var strYear=dtStr.substring(0,pos1)
                            var strMonth=dtStr.substring(pos1+1,pos2)
                            var strDay=dtStr.substring(pos2+1)	
                            strYr=strYear
                            if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
                            if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
                            for (var i = 1; i <= 3; i++) {
                                    if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
                            }
                            month=parseInt(strMonth)
                            day=parseInt(strDay)
                            year=parseInt(strYr)
                            if (pos1==-1 || pos2==-1){
                                    alert("The date format should be : YYYY-MM-DD")
                                    return false
                            }
                            if (strMonth.length<1 || month<1 || month>12){
                                    alert("Please enter a valid month")
                                    return false
                            }
                            if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
                                    alert("Please enter a valid day")
                                    return false
                            }
                            if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
                                    alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
                                    return false
                            }
                            if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
                                    alert("Please enter a valid date")
                                    return false
                            }
                    return true
    }
    function tpValidation2()
    {
     if(trim(document.getElementById("carrierPayMethodId").value)=="TP"){
            if(trim(document.getElementById("tpCompanyNameID").value)==""){
                                    alert("Please click on more button and Enter the Third Party Company Name ");
                                    document.getElementById("tpPopupId").focus();
                                    return false;
                            }
    
                            if(trim(document.getElementById("tpAddressID").value)==""){
                                    alert("Please click on more button and Enter the Third Party Address ");
                                    document.getElementById("tpPopupId").focus();
                                    return false;
                            }
    
                            if(trim(document.getElementById("tpCityID").value)==""){
                                    alert("Please click on more button and Enter the Third Party City ");
                                    document.getElementById("tpPopupId").focus();
                                    return false;
                            }
    
                            if(trim(document.getElementById("tpStateID").value)==""){
                                    alert("Please click on more button and Enter the Third Party State ");
                                    document.getElementById("tpPopupId").focus();
                                    return false;
                            }
    
                            if(trim(document.getElementById("tpPostalCodeID").value)==""){
                                    alert("Please click on more button and Enter the Third Party Postal Code ");
                                    document.getElementById("tpPopupId").focus();
                                    return false;
                            }
          var postalCode= trim(document.getElementById("tpPostalCodeID").value);
          if(postalCode.length != 5 || postalCode.length > 9){
                                    alert("Please click on  more button and Enter valid Third Party Postal Code ");
                                    document.getElementById("tpPopupId").focus();
                                    return false;
                            }
                            if(trim(document.getElementById("tpCountrySymbol").value)==""){
                                    alert("Please click on  more  button and Select The Third Party Country Symbol ");
                                    document.getElementById("tpPopupId").focus();
                                    return false;
                            } 
          return true;
           }
    }
    function fedexValidation2()
    {
     
     var str2=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
     
     if(str2.substring(0,3)==110||str2.substring(0,3)==100||str2.substring(0,3)==111)
     {
            if(trim(document.DynaShipmentShipSaveForm.shipFromLocation.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From Company name ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.DynaShipmentShipSaveForm.shipFromAddressLine1.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From AddressLine1 Value ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.DynaShipmentShipSaveForm.shipFromCity.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From City Value ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.DynaShipmentShipSaveForm.shipFromState.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From State Value");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
    
                            if(trim(document.DynaShipmentShipSaveForm.shipFromPostalCode.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From PostalCode Value ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
          var postalCode= trim(document.DynaShipmentShipSaveForm.shipFromPostalCode.value);
          
           if( postalCode.length > 5)
          {			
                             document.DynaShipmentShipSaveForm.shipFromPostalCode.value=postalCode.substring(0,5);
                            }
          
                            if(trim(document.DynaShipmentShipSaveForm.shipFromCountry.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From Country Value ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            } 
          if(trim(document.DynaShipmentShipSaveForm.shipFromPhoneNumber1.value)==""){
                                    alert("Please Click On More Button And Enter The Ship-From PhoneNumber1 Value ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
          
           if(str2.substring(0,3)==110||str2.substring(0,3)==111){
          if(trim(document.getElementById("dropOftypeId").value)==""){
                                    alert("Please Click On More Button And Select The Drop-OfF Type Value ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
          if(trim(document.getElementById("packagesId").value)==""){
                                    alert("Please click on  more  button and Select The packaging value ");
                                    document.DynaShipmentShipSaveForm.popup.focus();
                                    return false;
                            }
          }
          return true;
           }
    }
    
    function shipButtonValue()
    {
    
    document.DynaShipmentShipSaveForm.submitButton.value='Ship';
    var orderNumber = document.getElementById("orderNumberID").value;
    if (orderNumber != "" && orderNumber != null && (orderNumber.substring(0,2))!="SC") {
         var orderNumberTemp=encodeURIComponent(orderNumber);
         document.getElementById("orderNumberHidID").value = orderNumberTemp;
    }
    
    var payMethod = document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].text;
     if(payMethod=='--Select Paymethod--')
      {
      alert(" Please Select Pay Method");
      document.getElementById("carrierPayMethodId").focus();
      return false;
      }
    
      var shipMethod= document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
    
      if(shipMethod=='--Select Ship Method--')
      {
      alert(" Please Select the Ship Method");
      document.getElementById("shipMethodId").focus();
      return false;
      }
      else
      {      
      document.getElementById('saveButtonID').value="shipSave";
      
      //Added below code for Stamps.com
             var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
             var shipFlag1 = document.getElementById("flagShipID").value;
             var packcount = document.getElementById("countPacketsID").value;
             var carrierCode = str.substring(0,3);
             if(carrierCode == "115" && packcount > 1 && shipFlag1 == "N")
             {
                alert("Cannot add a package. Stamps.com supports only single package shipment.");   //  Added code for Stamps.com
                return false;
             }
    
      var strTxt=''; //document.DynaShipmentShipSaveForm.shipMethod.options[document.DynaShipmentShipSaveForm.shipMethod.selectedIndex].text;
      var strValue=''; //document.DynaShipmentShipSaveForm.shipMethod.options[document.DynaShipmentShipSaveForm.shipMethod.selectedIndex].value;
      document.DynaShipmentShipSaveForm.shipMethodText.value = strTxt;
      document.DynaShipmentShipSaveForm.shipMethodValue.value = strValue;
      var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
      
      if(str.substring(0,3)=="111" || str.substring(0,3)=="110")
      {
        if(document.DynaShipmentShipSaveForm.shipToAddress.value.length > 35 && (document.DynaShipmentShipSaveForm.intlFlag.value == 'Y' || document.DynaShipmentShipSaveForm.intlFlag.value == 'y'))
        {
            alert('Ship To Address Line 1 Can not be greater than 35 characters');
            document.DynaShipmentShipSaveForm.shipToAddress.focus();
            return false;
        }
      }
      //return true;
      }
      if(document.getElementById('acctPostalCode').value == '' ){
        document.getElementById('acctPostalCode').value = document.getElementById('zip').value;
      }
      
      if(document.getElementById('countryCodeVal').value == '' ){
        document.getElementById('countryCodeVal').value = document.getElementById('country').value;
      }
      var emailNotificationFlag = document.getElementById('emailNotificationFlagId').value ;
      if((emailNotificationFlag == 'Y' || emailNotificationFlag == 'y') &&  document.getElementById('shipFromEmailId').value=='')
      {
       
        alert('Please enter ShipFrom Email Address');
        document.getElementById('shipFromEmailId').focus();
        return false;
        
      }
      var shipFromEmailId = document.getElementById('shipFromEmailId').value ;
      if((emailNotificationFlag == 'Y' || emailNotificationFlag == 'y') && !validateEmail(shipFromEmailId)) // Added emailNotificationFlag to fix bug # 3866
      {
            alert("Please enter valid Ship From Email Address");
            document.getElementById('shipFromEmailId').focus();
            return false;
        }
//     if(shipFromEmailId.length != 0 && shipFromEmailId.length != null ) {
//        if(!/^[a-zA-Z0-9_.]+@[a-zA-Z]+.com$/.test(shipFromEmailId)){
//            alert("Please enter valid Ship From Email Address. in the form 'name@domain.com'");
//            document.getElementById('shipFromEmailId').focus();
//            return false;
//        }
//      }
      
    var  shipToEmailId  = document.getElementById('shipToEmailId').value;

    if((emailNotificationFlag == 'Y' || emailNotificationFlag == 'y') && shipToEmailId == '')
    {
       
        alert('Please enter Ship To Email Address');
        document.getElementById('shipToEmailId').focus();
        return false;
        
    }

    if(shipToEmailId != '' ){
      if((emailNotificationFlag == 'Y' || emailNotificationFlag == 'y') && !validateEmail(shipToEmailId)) // Added emailNotificationFlag to fix bug # 3866
      {
            alert("Please enter valid Ship To Email Address");
            document.document.getElementById('shipToEmailId').focus();
            return false;
      }
    }
    
    var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    var carrierCode=str.substring(0,3);
    var carrierPayMethod = document.getElementById("carrierPayMethodId").value;
    if((carrierCode == '100' && carrierPayMethod != "CG") || carrierCode == '110' || (carrierCode == '111' && carrierPayMethod != "FC") || carrierCode == '114'){
        var accountNumber= document.DynaShipmentShipSaveForm.carrierAccountNumber.value;
        var mode1=trim(document.getElementById("upsMode").value);
//        alert("mode1::"+mode1);
        if(mode1 != "ShipExec"){
            if(accountNumber == '')
            {
                alert("Account Number should not be empty");
                document.DynaShipmentShipSaveForm.carrierAccountNumber.focus();
                return false;
            }
        }
    }
        
    
    var shipFromCountry = document.getElementById('shipFromCountryId').value ;
    var shipToCountry = trim(document.getElementById('country').value);
    
    if(shipFromCountry == 'Select' || shipToCountry == 'Select')
    {
        alert("Please Select the Country Code");
        document.DynaShipmentShipSaveForm.countrySymbol.focus();
        return false;
    }
    
    var department=document.DynaShipmentShipSaveForm.department.value;
    if(department.indexOf('"') != -1)
      {
           alert("Department Should not contain Double Quote");
           document.DynaShipmentShipSaveForm.department.readOnly=false;
           document.DynaShipmentShipSaveForm.department.focus();
           return false;
      }
      
     if((department.length >30 && carrierCode=="110")
          ||(department.length >30 && carrierCode=="111"))
        {
                    alert("Department is "+department.length+ " Char. Please limit to 30 Char.");
                    document.DynaShipmentShipSaveForm.department.focus();
                    return false;
        }
     if((department.length >30 && carrierCode=="110")
              ||(department.length >30 && carrierCode=="111"))
            {
                        document.DynaShipmentShipSaveForm.department.value = department.substring(0,30);
            }
    
    checkCustomerValidation();
    var checkVal= document.getElementById('checkValidationID').value;
    if(checkVal!='true')
    {
    return false;
    }
    var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    var upsMode1 = document.getElementById("ajaxUpsMode").value;
    if(str.substring(0,3)=="100" && upsMode1=="UPS Direct" )
      {
        var shipDate=document.getElementById("shipmentDate").value;
        if(shipDate=='')
        {
         alert('Please Enter A Value For Shipment Date Field.');
         document.getElementById("shipmentDate").focus();
         return false;
        }
        var thisdate = new Date();
        var year1 = thisdate.getFullYear();
        var month1 = thisdate.getMonth()+1;
        var date = thisdate.getDate();
        var sysDate=year1+"-"+month1+"-"+date;
//        alert('sysdate:::::'+sysDate)
        if(isEqual(shipDate,sysDate)==false)
        {
           alert("Please Enter Today's Date In Shipment date field.");
           document.getElementById("shipmentDate").focus();
           return false;
        }
      }
      return disableSubmit(); 
      
      
    }
    
    function shipButtonValueNull()
    {
      document.DynaShipmentShipSaveForm.saveButton.value="New Order";
    }
    function tpNullValue()
    {
       window.document.getElementById("tpCountrySymbolID").value ='';
       window.document.getElementById("tpCompanyNameID").value='';
       window.document.getElementById("tpAddressID").value='';
       window.document.getElementById("tpCityID").value='';
       window.document.getElementById("tpStateID").value='';
       window.document.getElementById("tpPostalCodeID").value='';

    }
    function fedexNullValue()
    {
       document.getElementById("packaging").value ='';
       document.getElementById("dropOftypeId").value = '';
       document.DynaShipmentShipSaveForm.shipFromLocation.value ='';
       document.DynaShipmentShipSaveForm.shipFromAddressLine1.value ='';
       document.DynaShipmentShipSaveForm.shipFromAddressLine2.value ='';
       document.DynaShipmentShipSaveForm.shipFromCity.value ='';
       document.DynaShipmentShipSaveForm.shipFromState.value ='';
       document.DynaShipmentShipSaveForm.shipFromCountry.value ='';
       document.DynaShipmentShipSaveForm.shipFromPostalCode.value ='';
       document.DynaShipmentShipSaveForm.shipFromPhoneNumber1.value ='';
       document.DynaShipmentShipSaveForm.shipFromPhoneNumber2.value ='';
    }
    
/*function setvalues()
{
     alert("Inside setvalues");
     var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
     var carrierCode = str.substring(0,3);
     var dropOfType=document.getElementById("dropOftypeId").value;
     var packages=document.getElementById("packaging").value;
     document.getElementById("dropOftypeId").options.length=1;
     if(dropOfType !="")
     {
       document.getElementById("dropOftypeId").options[0]=new Option(dropOfType,dropOfType,true,true);
     }
     else
     {
       document.getElementById("dropOftypeId").options[0]=new Option("Select Type","Select Type");
     }
     var count = 1;
     for(var index = 0; index <DropOflength; index++)
     { 
         
       if(CarrierCodeDropOffA[index] == carrierCode)
       {
         document.getElementById("dropOftypeId").options[count]=new Option(DropOfftypeA[index],DropOfftypeA[index]);
         count = count +1;
       }
     }
         
     document.getElementById("packagesId").options.length=1;
     count = 1;
     if(packages !="")
     {
     document.getElementById("packagesId").options[0]=new Option(packages,packages,true,true);
     }
     else
     {
     document.getElementById("packagesId").options[0]=new Option("Select Packages","Select Packages");
     }    
     for(var index = 0; index <PackagingLenth;index++)
     {
      if(CarrierCodePackagingA[index] == carrierCode)
     {
        document.getElementById("packagesId").options[count]=new Option(PackagingA[index],PackagingA[index]);
        count++
     }
     }   
}  */
       
       
       
       function dimension()
    {
     var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
            
                            var length=document.DynaShipmentShipSaveForm.packageLength.value;
                            var width=document.DynaShipmentShipSaveForm.packageWidth.value;
                              var height=document.DynaShipmentShipSaveForm.packageHeight.value;
          
       if(isNaN(length)||isNaN(width) ||isNaN(height))
     {
     alert("Please enter the dimentions as numeric values");
     return false;
     }
     var totalDimentions=parseInt(length,10)+parseInt(width,10)+parseInt(height,10);
     
     if(isNaN(totalDimentions))
    { alert("Dont Enter null values as the dimmentions  ");return false;}
    
    
      if ((isNaN(length) || length=="" ||length==0) && width!="" && totalDimentions >0 )
     {
     alert("Enter the length  as numeric value or greater than 0");
     document.DynaShipmentShipSaveForm.packageLength.focus();
     return false;
     }
    
      if ((isNaN(length) || length=="" ||length==0 ) && height!="" && totalDimentions >0 )
     {
     alert("Enter the length as numeric value or greater than 0");
     document.DynaShipmentShipSaveForm.packageLength.focus();
     return false;
     }
     
      if (length!="" && (width=="" || isNaN(width) || width==0 ) && totalDimentions >0)
     {
     alert("Enter the width as numeric value or greater than 0");
     document.DynaShipmentShipSaveForm.packageWidth.focus();
     return false;
     }
     
        if (height!="" && (width=="" || isNaN(width)   || width==0 ) && totalDimentions >0 )
     {
     alert("Enter the width as numeric value or greater than 0");
     document.DynaShipmentShipSaveForm.packageWidth.focus();
     return false;
     }
      if (length!="" && (height=="" || isNaN(height) || height==0) && totalDimentions >0)
     {
     alert("Enter the height as numeric value or greater than 0");
    
     document.DynaShipmentShipSaveForm.packageHeight.focus();
     return false;
     }
     
       if (width!="" && (height=="" || isNaN(height) || height==0) && totalDimentions >0)
     {
     alert("Enter the height as numeric value or greater than 0")
    
    document.DynaShipmentShipSaveForm.packageHeight.focus();
     return false;
     }
          return true;
    }
       
    function setTpCountryName()
    {
       var count = 1;
       for(var index = 0; index <tpCountryLength; index++)
         { 
           document.tppopperform.tpCountrySymbol.options[count]=new Option(tpCountry[index],tpCountry[index]);
            count = count +1;
           }
      }
          
    function openPackPopup(num1)
    {
        // Added by Padmavathi for bug #2907
      var shipMethodValue = document.getElementById("shipMethodId").options[document.getElementById('shipMethodId').selectedIndex].text;
        var custName =document.getElementById('customerName').options[document.getElementById('customerName').selectedIndex].text;
        var custLocation =document.getElementById("customerLocationId").value;
        if(custName =='--Select--' ) 
        {
        alert('Enter Customer Name');
        document.getElementById('customerName').focus();
        return false;
        }
        if(custLocation=='--Select--')
        {
        alert('Enter Ship To Location');
        document.getElementById("customerLocationId").focus();
        return false;
        }        
        if (shipMethodValue=='--Select Ship Method--')
       {
       alert('Please Choose a  Ship Method');
       document.getElementById("shipMethodId").focus();
       return false;
       }
       // End of Padmavathi's code for bug #2907
      var defaultPackageOption = document.getElementById("defaultPackageOption").value;
      if((defaultPackageOption=="true")&&(num1==1))
      {
         alert('Default Package options selected...');
      }
        
      var fedexstr = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
      
      if(fedexstr.substring(0,3) == 115 || fedexstr.substring(0,3) == 114){
            if(fedexstr.substring(0,3) == 115){
                alert('Package Options are not supported for Stamps.com carrier.');
            } else {
                alert('Package Options are not supported for DHL carrier.');
            }
            
            document.getElementById('packOptId'+num1).disabled=true;
            return false;
      }
      
      if(fedexstr.substring(0,3)==110 || fedexstr.substring(0,3)==111)
      {
         var carrierCode = fedexstr.substring(0,3);
         var strTemp=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
         var len = strTemp.length;
         if(strTemp !=null && strTemp!='')
         {
             fdxCarrierName = strTemp.substring(7,len);
         }
         // Modified code to fix #2955
         var shipFlagPackages=document.getElementById("flagShip1").value ;
         packwindow =  window.open("aascFedexPackageOptions.jsp?packCount="+num1+"&carrierCode="+carrierCode+"&fdxCarrierName="+fdxCarrierName+"&shipFlagPackages="+shipFlagPackages,"Post",'width=930,height=670,top=100,left=100,scrollbars=yes, resizable=yes');
           
         if (window.focus) 
         {
               packwindow.focus();
         }
      }
      
      var ajaxUpsMode = document.getElementById("ajaxUpsMode").value;
//      alert("ajaxUpsMode:::"+ajaxUpsMode);
      if(fedexstr.substring(0,3)==100 && ajaxUpsMode=="UPS Direct")
      {
          if(navigator.appName !='Microsoft Internet Explorer'){
            var pkging=document.getElementById('pkgingID'+num1).value;               // for IE
          }
          else
          {
             var pkging=document.getElementById('pkgingID'+num1).value;             // for Firefox and Chrome
          }
          var codType=document.getElementById('codTypeID'+num1).value;
          var codFundsCode=document.getElementById('codFundsCodeID'+num1).value;  
          var delConfirm=document.getElementById('delConfirmID'+num1).value;
          var codCurrCode=document.getElementById('codCurrCodeID'+num1).value;
          var codFlag=document.getElementById('chCODID'+num1).value;
          var upsServiceLevelCode=trim(document.DynaShipmentShipSaveForm.ajaxUpsServiceLevelCode.value);
          upsServiceLevelCode=parseInt(upsServiceLevelCode);
        
          packwindow =  window.open("aascUpsDirectPkgingOptions.jsp?codFlag="+codFlag+"&packCount="+num1+"&pkgingTemp="+pkging+"&delConfirm="+delConfirm+"&codType="+codType+"&codFundsCode="+codFundsCode+"&codCurrCode="+codCurrCode+"&upsServiceLevelCode="+upsServiceLevelCode,"Post",'width=780,height=500,top=50,left=100,scrollbars=yes, resizable=yes');
          if (window.focus) 
          {
              packwindow.focus();
          }
      }
      
      if(fedexstr.substring(0,3)==100 && ajaxUpsMode=="ShipExec")
      {
        if(document.DynaShipmentShipSaveForm.orderNum.value == null || document.DynaShipmentShipSaveForm.orderNum.value == ''){
            getOrderNumber(0);
        }
        var orderNum = document.DynaShipmentShipSaveForm.orderNumber.value;
//        alert('orderNum:::'+orderNum);
          if(navigator.appName !='Microsoft Internet Explorer'){
            var pkging=document.getElementById('pkgingID'+num1).value;               // for IE
          }
          else
          {
             var pkging=document.getElementById('pkgingID'+num1).value;             // for Firefox and Chrome
          }
          var codType=document.getElementById('codTypeID'+num1).value;
          var codFundsCode=document.getElementById('codFundsCodeID'+num1).value;  
          var delConfirm=document.getElementById('delConfirmID'+num1).value;
          var codCurrCode=document.getElementById('codCurrCodeID'+num1).value;
          var codFlag=document.getElementById('chCODID'+num1).value;
            var LargePackage=document.getElementById('LargePackageID'+num1).value; 
            var AdditionalHandling=document.getElementById('AdditionalHandlingID'+num1).value;
//          var upsServiceLevelCode=trim(document.DynaShipmentShipSaveForm.ajaxUpsServiceLevelCode.value);
//          upsServiceLevelCode=parseInt(upsServiceLevelCode);
        var cariierCode = fedexstr.substring(0,3);
        var strTemp = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        var len = strTemp.length;
        if(strTemp !=null && strTemp!='')
        {
         upsCarrierName = encodeURIComponent(strTemp.substring(7,len));  // vikas encoded the shipmethod if the shipmethod consists of any special characters
        }
//          packwindow =  window.open("aascShipExecPkgingOptions.jsp?codFlag="+codFlag+"&packCount="+num1+"&pkgingTemp="+pkging+"&delConfirm="+delConfirm+"&codType="+codType+"&codFundsCode="+codFundsCode+"&codCurrCode="+codCurrCode,"Post",'width=780,height=500,top=50,left=100,scrollbars=yes, resizable=yes');
packwindow =  window.open("aascShipExecPkgingOptions.jsp?codFlag="+codFlag+"&packCount="+num1+"&pkgingTemp="+pkging+"&delConfirm="+delConfirm+"&codType="+codType+"&codFundsCode="+codFundsCode+"&codCurrCode="+codCurrCode+"&cariierCode="+cariierCode+"&upsCarrierName="+upsCarrierName+"&shipFlagPackages="+shipFlagPackages+"&orderNum="+orderNum,"Post",'width=1130,height=700,top=100,left=100,scrollbars=yes, resizable=yes');
        if (window.focus)
        // vikas pkgoptions 
          {
              packwindow.focus();
          }
      }
    }
    
    
    function checkUnits(dimensionText)
    {
        var dimension = trim(dimensionText.value);
        var length=document.DynaShipmentShipSaveForm.packageLength.value;
        var width=document.DynaShipmentShipSaveForm.packageWidth.value;
        var height=document.DynaShipmentShipSaveForm.packageHeight.value;
     
     if ((length!=0 && !isNaN(length))  && (width!=0 && !isNaN(width)) &&  ( height!=0 && !isNaN(height)))
      {
      document.DynaShipmentShipSaveForm.dimensionUnit.disabled = false;
     }
     else{
     document.DynaShipmentShipSaveForm.dimensionUnit.disabled = true;
     }
    }
    
    function changeCase()
    {
     document.DynaShipmentShipSaveForm.state.value =  document.DynaShipmentShipSaveForm.state.value.toUpperCase();
    }
    
    function codNreturnShipment()
    { 
     var fedexstr = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        if(fedexstr.substring(0,3)==110||fedexstr.substring(0,3)==111)
             {
       if(document.DynaShipmentShipSaveForm.chCOD1.checked==true && document.DynaShipmentShipSaveForm.returnShipment1.value=="PRINTRETURNLABEL")
       {
       alert("Cod and return shipment cant be checked once ");
       return false;
       }
       }
        
    }
    
    
    function selectDropOffType()
    {
    var dropOff=document.getElementById("dropOftypeId").options[document.getElementById("dropOftypeId").selectedIndex].value;
    
    document.getElementById("dropOftypeId").value =  dropOff ;
    }
    
    function SelectPackaging()
    {
    
    var pack=document.getElementById("packagesId").options[document.getElementById("packagesId").selectedIndex].value;
    document.getElementById("packaging").value=  pack ;
    }
    
    
     function packageDeclaredValuefn()
    {
     var packCount=1;
     var declareval=document.DynaShipmentShipSaveForm['packageDeclaredValue'+packCount].value;
    if(isNaN(declareval))
    {
    alert('Enter Only Number in Declared value Field');
    return false;
    }
    var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
      
      if(str.substring(0,3)=="111"||str.substring(0,3)=="110")
    {
    
    declareval=parseInt(declareval);
    
    if(declareval >=500)
    {
    alert("Signature Option Direct is selected for this package");
    document.getElementById('signatureOptionID'+packCount).value="DIRECT";
    }
    
     
    }
    
    } 
    
    
    function getCcCsl(testForErrorCase)
    {  
      
      var localDropOfType="";
      var localPackageList="";
      var localCarrierPayMethodText="";
      var localCarrierAccNumber="";
      if(testForErrorCase=="1")
      {
        localDropOfType           =   document.getElementById("dropOftypeId").value ; 
        localPackageList          =   document.getElementById("packagesId").value  ;
        localCarrierPayMethodText =   document.getElementById("carrierPayMethodId").value  ;
        localCarrierAccNumber     =   document.getElementById("carrierAccountNumberId").value  ;
      }
      else
      {
        if(testForErrorCase=="3") 
        {
          
          localDropOfType           =   document.getElementById("ajaxAfterShipDropOffType").value; 
          localPackageList          =   document.getElementById("ajaxAfterShipPackaging").value; 
          localCarrierPayMethodText =   document.getElementById("ajaxAfterShipCarrPayMthdValue").value
          localCarrierAccNumber     =   document.getElementById("ajaxAfterShipCarrAccNumber").value;
        }
        else
        {
          localDropOfType           =   '';
          localPackageList          =   '';
          localCarrierPayMethodText =   '';
          localCarrierAccNumber     =   '';
        }
      }
       
      var ajaxOriginRegionCode = document.getElementById("ajaxOriginRegionCode").value;
      var ajaxUpsMode = document.getElementById("ajaxUpsMode").value;
      
      
      var ajaxshipMethodvalue = document.getElementById("shipMethodId").value;
      
        
      document.getElementById("dropOftypeId").options.length=0 // added for clearing all the option fields in select tag
      document.getElementById("packagesId").options.length=0 // added for clearing all the option fields in select tag
      document.getElementById("carrierPayMethodId").options.length=0 // added for clearing all the option fields in select tag
      document.getElementById("carrierAccountNumberId").value='';         //setting default
      document.getElementById("ajaxCCodeCServiceLevel").value='';                      // setting default
      document.getElementById("ajaxCarrierCode").value='';             // setting default
      document.getElementById("ajaxcarrierservicelevel").value='';     // setting default
      document.getElementById("ajaxDropOffType").value='';                         // setting default
      document.getElementById("ajaxPackaging").value='';                         // setting default
      document.getElementById("ajaxCarrierPaymentTerms").value='';                         // setting default
      document.getElementById("ajaxUpsServiceLevelCode").value='';                         // setting default
      document.getElementById("ajaxDimensionReq").value='';                         // setting default
      document.getElementById("ajaxMaxWeight").value='';                         // setting default
      
      document.getElementById("ajaxMaxLength").value='';                         // setting default
      document.getElementById("ajaxMinLength").value='';                         // setting default
      document.getElementById("ajaxMaxWidth").value='';                         // setting default
      document.getElementById("ajaxMinWidth").value='';                         // setting default
      document.getElementById("ajaxMaxHeight").value='';                         // setting default
      document.getElementById("ajaxMinHeight").value='';                         // setting default
       
      if(ajaxshipMethodvalue.substring(0,3)!='100'&&ajaxshipMethodvalue.substring(0,3)!='110'&&ajaxshipMethodvalue.substring(0,3)!='111' &&ajaxshipMethodvalue.substring(0,3)!='114'  && ajaxshipMethodvalue.substring(0,3)!='115') //Shiva added 114
      {
        fillCarrierPayMethod();
      }
      else
      {
      
        var ajaxshipMethodParse = ajaxshipMethodvalue.substring(ajaxshipMethodvalue.indexOf("*")+1,ajaxshipMethodvalue.indexOf("@@"));
        document.getElementById("ajaxShipMethod").value=ajaxshipMethodParse;
      
        var locationIDTemp = document.getElementById("locationId").value;
        
        var xmlHttp;
        try
        {    // Firefox, Opera 8.0+, Safari    
          xmlHttp=new XMLHttpRequest();    
        }
        catch (e)
        {    // Internet Explorer    
          try
          {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
          }
          catch (e)
          {      
             try
             {        
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
             }
             catch (e)
             {        
                alert("Your browser does not support AJAX!");        
                return false;        
              }      
          }    
        }
        
        xmlHttp.onreadystatechange=function()
        {
          if(xmlHttp.readyState==4)
          {
             document.getElementById("ajaxCCodeCServiceLevel").value=xmlHttp.responseText;
             var responseString  = document.getElementById("ajaxCCodeCServiceLevel").value;
               
             var counter=0;
             var responseStringDummy=responseString;
             var responseStringlen = responseStringDummy.length;        
             
             var testindex  = responseStringDummy.indexOf('*');
             if(testindex!=-1)
             {
                var parsetest;
                var parse1;
                var parse2;
                var parse3;
                var parse4;
                var index2;
                var index3;
                var index4;
                var subindex1;
                var hazmatIndex;
                var hazmatString;

                responseStringDummy=responseStringDummy.substring(testindex+1);
                if(responseStringDummy!='')
                {
                    //alert('responseStringDummy === '+responseStringDummy);
                    subindex1=responseStringDummy.indexOf('@');
                    var responseStringDummy2 = responseStringDummy.substring(subindex1+1);
                    index2=responseStringDummy2.indexOf('@');
                    index3=responseStringDummy2.indexOf('#');
                    index4=responseStringDummy2.indexOf('*');
                                        
                    parse1=responseStringDummy.substring(0,subindex1);
                    document.getElementById("ajaxCarrierCode").value=parse1;
                    
                    parse2=responseStringDummy2.substring(0,index2);
                    document.getElementById("ajaxcarrierservicelevel").value=parse2;
                
                    parse3=responseStringDummy2.substring(index2+1,index3);
                    document.getElementById("upsMode").value = parse3;
                    document.getElementById("ajaxUpsMode").value=parse3;
                    document.getElementById("stampsMode").value = parse3;
                     
                    var freightShoppingCheckFlag = document.getElementById("freightShoppingCheckID").value;
                    var addresValidationCheckFlag = document.getElementById("addresValidationCheckID").value;

                    if(freightShoppingCheckFlag == 'Y'){
                    var carrierConnectionMode = document.getElementById("ajaxCarrierMode").value=parse3;
                    if(document.getElementById("upsMode").value == 'ShipExec'){
                    document.getElementById("freightShopPopupId").setAttribute("disabled","disabled");
                    document.getElementById("getRatesId").setAttribute("disabled","disabled");
//                    document.getElementById("freightShopPopupId").disabled = true;
//                      document.getElementById("getRatesId").disabled = true;
                    }
                    else{
                    document.getElementById("freightShopPopupId").removeAttribute("disabled");
                    document.getElementById("getRatesId").removeAttribute("disabled");
//                    document.getElementById("freightShopPopupId").disabled = false;
//                    document.getElementById("getRatesId").disabled = false;
                    }
                   }
                   
                    if(addresValidationCheckFlag == 'Y'){
                    var carrierConnectionMode = document.getElementById("ajaxCarrierMode").value=parse3;
                    if(document.getElementById("upsMode").value == 'ShipExec'){
                    document.getElementById("validateAddressId").setAttribute("disabled","disabled");
//                     document.getElementById("validateAddressId").disabled = true;
                    }
                    else{
                    document.getElementById("validateAddressId").removeAttribute("disabled");
//                    document.getElementById("validateAddressId").disabled = false;
                    }
                   }
                   
                    parse4=responseStringDummy2.substring(index3+1,index4);
                    document.getElementById("labelFormat").value = parse4;
                    hazmatString = responseStringDummy2.substring(index4+2);
                    //alert("hazmatString : "+hazmatString);
                    hazmatIndex = hazmatString.indexOf('$');
                    //alert("hazmatIndex : "+hazmatIndex);
                    var hazmatEnabledFlag  = hazmatString.substring(0,hazmatIndex);
                    //alert("hazmatEnabledFlag : "+hazmatEnabledFlag);
                
                    // To ifx 1147 issue
                    var ltlHazmatString = hazmatString.substring(1);
                    //alert('ltlHazmatString : '+ltlHazmatString);
              
                    var ltlIndex = ltlHazmatString.indexOf('$');
                    //alert('ltlIndex : '+ltlIndex);
                    var ltlHazmatShippingSupport = ltlHazmatString.substring(1 , (ltlHazmatString.lastIndexOf('$')));
                    document.getElementById("ltlHazmatShippingSupport").value = ltlHazmatShippingSupport;
                    //alert('ltlHazmatShippingSupport : '+ltlHazmatShippingSupport);
              
                    // To ifx 1147 issue

                    if((hazmatEnabledFlag != null && hazmatEnabledFlag != "" && (hazmatEnabledFlag != 'N' && hazmatEnabledFlag !='n')) || ltlHazmatShippingSupport == 'Yes')
                        document.getElementById("hazmatSupportFlag").value = true ;
                    else   
                        document.getElementById("hazmatSupportFlag").value = false ; 
                    //alert("document.ShipInsertForm.hazmatSupportFlag.value : "+document.ShipInsertForm.hazmatSupportFlag.value);

                    //if((parse2=="" || parse2==null || parse2=="null") && parse1!="999" && document.ShipInsertForm.firstMain.value=="N" )
                    if((parse2=="" || parse2==null || parse2=="null")) // && document.ShipInsertForm.firstMain.value=="N" 
                    {
                        //    alert('parse2:'+parse2);
//                            alert('testForFedexUps:'+testForFedexUps+' carrierConnectionMode'+carrierConnectionMode);
var testForFedexUps=ajaxshipMethodvalue.substring(0,ajaxshipMethodvalue.indexOf("|"));
                        if((testForFedexUps!='100'&&testForFedexUps!='110'&&testForFedexUps!='111'  &&testForFedexUps!='113' &&testForFedexUps!='114' &&testForFedexUps!='115') && (carrierConnectionMode!='ConnectShip')) // sanjay added 113/114 for TNT/dhl Integration
                        {

                        }
                        else
                        {
                            alert("Please Contact ShipConsole Administrator. Setup is Missing for this ShipMethod.");
                            return false;
                        }
                    }

                    parsetest=parse1+'@'+parse2;     
                    //parsetest=responseStringDummy.substring(0,index2);
                    counter=counter+1;
                }
            }
            else
            {
                responseStringDummy='';          
            }
             
             if(document.getElementById("ajaxUpsMode").value=='UPS Direct'&&document.getElementById("ajaxCarrierCode").value=='100')
             {
                getUpsServiceLevelCode();
             }
                  
             getDropOffType(localDropOfType,localPackageList,localCarrierPayMethodText,localCarrierAccNumber);               
               
                   
           }
        }
          
        var shipMethod= document.getElementById("shipMethodId").value;
        var indexTemp=shipMethod.indexOf('@@');
        var carrierId=shipMethod.substring(indexTemp+2);
        var url="aascAjaxRetrieveCarrierCodeServiceLevel.jsp?shipMethod="+ajaxshipMethodParse+"&locationIDTemp="+locationIDTemp+"&carrierId="+carrierId;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null);  
      }
    }
            
    function getDropOffType(localDropOfType,localPackageList,localCarrierPayMethodText,localCarrierAccNumber)
      { 
      var xmlHttp;
      try
        {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
        }
      catch (e)
        {    // Internet Explorer    
          try
            {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
            }
          catch (e)
            {      
              try
                {        
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
                }
              catch (e)
                {        
                  alert("Your browser does not support AJAX!");        
                  return false;        
                }      
            }    
        }
        document.getElementById("dropOftypeId").options.length=0 // added for clearing all the fields
        document.getElementById("packagesId").options.length=0 // added for clearing all the fields
        document.getElementById("carrierPayMethodId").options.length=0 // added for clearing all the fields
        document.getElementById("carrierAccountNumberId").value='';         //setting default
        
        document.getElementById("ajaxDimensionReq").value='';                         // setting default
        document.getElementById("ajaxMaxWeight").value='';                         // setting default
        
        document.getElementById("ajaxMaxLength").value='';                         // setting default
        document.getElementById("ajaxMinLength").value='';                         // setting default
        document.getElementById("ajaxMaxWidth").value='';                         // setting default
        document.getElementById("ajaxMinWidth").value='';                         // setting default
        document.getElementById("ajaxMaxHeight").value='';                         // setting default
        document.getElementById("ajaxMinHeight").value='';                         // setting default
        
        xmlHttp.onreadystatechange=function()
          {
          if(xmlHttp.readyState==4)
            {
                document.getElementById("ajaxDropOffType").value=xmlHttp.responseText;
                var responseString  ="";
                
                responseString  = document.getElementById("ajaxDropOffType").value;
                var responseStringlen = responseString.length;        
                
                var counter=0;
                
                
                var responseStringDummy=responseString;
                for(;responseStringDummy.length>1;)
                  {
                    var testindex  = responseStringDummy.indexOf('*');
                    if(testindex!=-1)
                    {
                      var parsetest;
                      var index2;
                      responseStringDummy=responseStringDummy.substring(testindex+1);
                      
                      if(responseStringDummy!='')
                        {
                          index2=responseStringDummy.indexOf('*');
                          parsetest=responseStringDummy.substring(0,index2);
                          if(parsetest=='null'||parsetest=='')
                          {
                          }
                          else
                          {
                          
                          
                          if(localDropOfType!='' && parsetest==localDropOfType)
                          {
                            document.getElementById("dropOftypeId").options[counter]=new Option(parsetest, parsetest, true, true);
                            
                          }
                          else
                          {
                              
                              if(document.getElementById("ajaxCarrierCode").value=='110' && parsetest=='REGULARPICKUP' && localDropOfType=='')
                              { 
    
                                document.getElementById("dropOftypeId").options[counter]=new Option(parsetest,parsetest, true, true);
                              }
                              else
                              {
                            document.getElementById("dropOftypeId").options[counter]=new Option(parsetest, parsetest, true, false);
                            }                     
                            
                          }
                          
                          counter=counter+1;
                          }
                          
                        }
                    }
                    else
                    {
                        responseStringDummy='';          
                    }
                  } // end of for
                  
                  if(counter==0)
                  {
                    document.getElementById("ajaxDropOffType").value='';
                    document.getElementById("dropOftypeId").options.length=1;
                    document.getElementById("dropOftypeId").options.value='';
                    document.getElementById("dropOftypeId").disabled=true;
                    getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber);     
                  
                  }
                  else
                  {
                    if(localDropOfType!='')
                    {
    
                      getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber);     
                    }
                    else
                    {
                      
                      getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber);   
                    }
                  }
                  
              }
          }
    
        
        var url="aascAjaxRetrieveDropOffType.jsp?ajaxCarrierCode="+document.getElementById("ajaxCarrierCode").value+"&ajaxcarrierservicelevel="+document.DynaShipmentShipSaveForm.ajaxcarrierservicelevel.value;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null);  
        }
    
    function getPackageList(localPackageList,localCarrierPayMethodText,localCarrierAccNumber)
      { 
    
      
      var xmlHttp;
      try
        {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
        }
      catch (e)
        {    // Internet Explorer    
          try
            {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
            }
          catch (e)
            {      
              try
                {        
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
                }
              catch (e)
                {        
                  alert("Your browser does not support AJAX!");        
                  return false;        
                }      
            }    
        }
        
        
        document.getElementById("packagesId").options.length=0 // added for clearing all the fields
        document.getElementById("carrierPayMethodId").options.length=0 // added for clearing all the fields
        document.getElementById("carrierAccountNumberId").value='';         //setting default
        
        document.getElementById("ajaxDimensionReq").value='';                         // setting default
        document.getElementById("ajaxMaxWeight").value='';                         // setting default
        
        document.getElementById("ajaxMaxLength").value='';                         // setting default
        document.getElementById("ajaxMinLength").value='';                         // setting default
        document.getElementById("ajaxMaxWidth").value='';                         // setting default
        document.getElementById("ajaxMinWidth").value='';                         // setting default
        document.getElementById("ajaxMaxHeight").value='';                         // setting default
        document.getElementById("ajaxMinHeight").value='';                         // setting default
        
        xmlHttp.onreadystatechange=function()
          {
          if(xmlHttp.readyState==4)
            {
                document.DynaShipmentShipSaveForm.ajaxPackaging.value=xmlHttp.responseText;
                var responseString  = document.DynaShipmentShipSaveForm.ajaxPackaging.value;
    
                var responseStringlen = responseString.length;        
                var counter=0;
                
                   
                var responseStringDummy=responseString;
                for(;responseStringDummy.length>1;)
                  {
                    var testindex  = responseStringDummy.indexOf('*');
                    if(testindex!=-1)
                    {
                      var parsetest;
                      var index2;
                      responseStringDummy=responseStringDummy.substring(testindex+1);
                      if(responseStringDummy!='')
                        {
                          index2=responseStringDummy.indexOf('*');
                          parsetest=responseStringDummy.substring(0,index2);
                          if(parsetest=='null'||parsetest=='')
                          {
    
                          }
                          else
                          {
                          
                          
                          if(localPackageList!='' && parsetest==localPackageList)
                          {
                            document.getElementById("packagesId").options[counter]=new Option(parsetest, parsetest, true, true);
                          }
                          else
                          {
                             if(document.getElementById("ajaxCarrierCode").value=='110' && parsetest=='YOURPACKAGING' && localPackageList=='')
                              { 
                                   document.getElementById("packagesId").options[counter]=new Option(parsetest,parsetest, true, true);
                              }
                              else
                              {
                              document.getElementById("packagesId").options[counter]=new Option(parsetest, parsetest, true, false);
                              }
                          }
                          counter=counter+1;
                          }
                        }
                    }
                    else
                    {
                        responseStringDummy='';          
                    }
                  }// end of for
                  
                  if(counter==0)
                  {
                    document.DynaShipmentShipSaveForm.ajaxPackaging.value='';
                    document.getElementById("packagesId").options.length=1;
                    document.getElementById("packagesId").options.value='';
                    document.getElementById("packagesId").disabled=true;
                    getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber); 
                  }
                  else
                  {
                    if(localPackageList!='')
                    {
    
                      getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber);     
                    }
                    else
                    {
    
                      getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber);
                    }
                    
                  }
                  
              }
          }
        var url="aascAjaxRetrievePackaging.jsp?ajaxCarrierCode="+document.getElementById("ajaxCarrierCode").value+"&ajaxcarrierservicelevel="+document.DynaShipmentShipSaveForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.getElementById("dropOftypeId").value;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null);  
        }
        
        
    function getCarrierPayMethod(localCarrierPayMethodText,localCarrierAccNumber)
      { 
      var xmlHttp;
      try
        {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
        }
      catch (e)
        {    // Internet Explorer    
          try
            {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
            }
          catch (e)
            {      
              try
                {        
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
                }
              catch (e)
                {        
                  alert("Your browser does not support AJAX!");        
                  return false;        
                }      
            }    
        }
        
        
        document.getElementById("carrierPayMethodId").options.length=0 // added for clearing all the fields
        document.getElementById("carrierAccountNumberId").value='';         //setting default
        
        document.getElementById("ajaxDimensionReq").value='';                         // setting default
        document.getElementById("ajaxMaxWeight").value='';                         // setting default
        
        document.getElementById("ajaxMaxLength").value='';                         // setting default
        document.getElementById("ajaxMinLength").value='';                         // setting default
        document.getElementById("ajaxMaxWidth").value='';                         // setting default
        document.getElementById("ajaxMinWidth").value='';                         // setting default
        document.getElementById("ajaxMaxHeight").value='';                         // setting default
        document.getElementById("ajaxMinHeight").value='';                         // setting default
        
        xmlHttp.onreadystatechange=function()
          {
          if(xmlHttp.readyState==4)
            {
                document.getElementById("ajaxCarrierPaymentTerms").value=xmlHttp.responseText;
                var responseString  = document.getElementById("ajaxCarrierPaymentTerms").value;
                var responseStringlen = responseString.length;        
                var counter=1;
                var responseStringDummy=responseString;
                document.getElementById("carrierPayMethodId").options[0]=new Option('--Select Paymethod--','--Select Paymethod--', true, false);
                for(;responseStringDummy.length>1;)
                  {
                    var testindex  = responseStringDummy.indexOf('*');
                    if(testindex!=-1)
                    {
                      var parsetest;
                      var parsetestvalue;
                      var index2;
                      responseStringDummy=responseStringDummy.substring(testindex+1);
                      if(responseStringDummy!='')
                        {
                          index2=responseStringDummy.indexOf('@');
                          
                          parsetest=responseStringDummy.substring(0,index2);
                           if(parsetest=='null'||parsetest=='')
                          {
                          }
                          else
                          {
                          parsetestvalue=responseStringDummy.substring(index2+1,responseStringDummy.indexOf('*'));
                          
                          var carrierCode = document.getElementById("ajaxCarrierCode").value ;
                          var localCarrierPayTerm  = document.getElementById("ajaxAfterShipCarrPayMthdStrValue").value;
                          
                          if("COLLECT" == localCarrierPayTerm && 111 == carrierCode){    
                                localCarrierPayMethodText = "FC";
                          }
                          else if("RECIPIENT"== localCarrierPayTerm && (111 == carrierCode || 110 == carrierCode || 112 == carrierCode)){   
                                localCarrierPayMethodText = "CG";
                          }
                          
                          if("RECIPIENT" == localCarrierPayTerm && 100 == carrierCode){    
                                localCarrierPayMethodText = "FC";
                          }
                          if("CONSIGNEE" == localCarrierPayTerm && 100 == carrierCode){    
                                localCarrierPayMethodText = "CG";
                          }
                          if("THIRD PARTY BILLING" == localCarrierPayTerm && (111 == carrierCode || 110 == carrierCode || 100 == carrierCode)){    
                                localCarrierPayMethodText = "TP";
                          }
                          if("PREPAID" == localCarrierPayTerm){    
                                localCarrierPayMethodText = "PP";
                          }
                          
                          if(localCarrierPayMethodText!='' && parsetestvalue==localCarrierPayMethodText)
                          {
                            document.getElementById("carrierPayMethodId").options[counter]=new Option(parsetest,parsetestvalue, true, true);
                            
                            var fillCarrierAccNumber=localCarrierAccNumber;
                                if(fillCarrierAccNumber!='')
                                {
                                document.getElementById("carrierAccountNumberId").value=fillCarrierAccNumber;
                                }
                                else
                                {
                                var upsMode=trim(document.getElementById("upsMode").value); 
                                var str=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
                                 var acctNumberHiddenValue = document.getElementById("ajaxAfterShipCarrAccNumber").value ;
                               if(upsMode != "ShipExec" && upsMode != "Stamps.com"){    
                                    if(acctNumberHiddenValue != null && acctNumberHiddenValue != '')
                                      {
                                       document.getElementById("carrierAccountNumberId").value = acctNumberHiddenValue;
                                    }
                                 }
                                if(upsMode == "ShipExec" && str=="PP"){
                                  document.getElementById("carrierAccountNumberId").value = "";
                                }
                             }                            
                          }
                          else
                          {
                               var defaultPayMethod = document.getElementById('defaultPayMethodId').value;
                               var defaultPayMethodText = '';
                               if("COLLECT" == defaultPayMethod && 111 == carrierCode){    
                                    defaultPayMethodText = "FC";
                              }
                              else if("RECIPIENT"== defaultPayMethod && (111 == carrierCode || 110 == carrierCode || 112 == carrierCode)){   
                                    defaultPayMethodText = "CG";
                              }
                              
                              if("RECIPIENT" == defaultPayMethod && 100 == carrierCode){    
                                    defaultPayMethodText = "FC";
                              }
                              if("CONSIGNEE" == defaultPayMethod && 100 == carrierCode){    
                                    defaultPayMethodText = "CG";
                              }
                              if("THIRD PARTY BILLING" == defaultPayMethod && (111 == carrierCode || 110 == carrierCode || 100 == carrierCode)){    
                                    defaultPayMethodText = "TP";
                              }
                          
                          
                              if(parsetestvalue == defaultPayMethodText)
                              { 
                                document.getElementById("carrierPayMethodId").options[counter]=new Option(parsetest,parsetestvalue, true, true);
                              }
                              else
                              {
                              document.getElementById("carrierPayMethodId").options[counter]=new Option(parsetest,parsetestvalue, true, false);                      
                              }
                          }
                          counter=counter+1;
                        }
                    }
                    }
                    else
                    {
                        responseStringDummy='';          
                    }
                  }//end of for
                  chgCarrierPayMethod(); 
                  
                            //-----------------Suman G code start to fix #2493 ----------------//
       
       var shipFlag=document.getElementById("flagShip1").value ;
       try{
            
            var errorFlagForDropOffType = document.getElementById("errorFlag2").value;
            
        }catch(err){
            
            var errorFlagForDropOffType = 'success';
        }
        if( shipFlag!="Y")// && errorFlagForDropOffType != 'error')
        {
       var paymethodLength = document.getElementById("carrierPayMethodId").options.length;
       for(var i=0;i<paymethodLength;i++)
       {
            var selectedPayMethod = document.getElementById("ajaxAfterShipCarrPayMthdStrValue").value; // To default to already saved or selected pay method in error case 
       
            //To select the default pay method when any Lov is changed
            if(document.getElementById("chngCarrierPayMethodHidden").value == 'true')//if(selectedPayMethod == '' || selectedPayMethod == null)
            {
                if(document.getElementById("carrierPayMethodId").options[i].text==document.getElementById('defaultPayMethodId').value){
                    document.getElementById("carrierPayMethodId").options[i].selected = true;
                    document.getElementById("carrierPayMethodHide").value = document.getElementById('defaultPayMethodId').value;
                }
            }
            else   // To default to already saved or selected pay method in error case
            {
               if(document.getElementById("carrierPayMethodId").options[i].text == selectedPayMethod){
                 document.getElementById("carrierPayMethodId").options[i].selected = true;
                 document.getElementById("carrierPayMethodHide").value = selectedPayMethod;
              }
            }
          }
       }
       //-----------------Suman G code end ----------------// 
                 var mode=trim(document.getElementById("upsMode").value);
                  var str=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
                  if(!(upsMode == "ShipExec" && str=="PP")){  //(mode != 'ShipExec' ){
                        getAccountNumber();       
                }            
                  voidFlagStatus();   
              }
          }
        var url="aascAjaxRetrieveCarrierPayTerms.jsp?ajaxCarrierCode="+document.getElementById("ajaxCarrierCode").value+"&ajaxcarrierservicelevel="+document.DynaShipmentShipSaveForm.ajaxcarrierservicelevel.value+"&dropOfType="+document.getElementById("dropOftypeId").value+"&packageList="+document.getElementById("packagesId").value;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null);  
        }
    function getAccountNumber()
    {
    
        document.getElementById("ajaxDimensionReq").value='';                         // setting default
        document.getElementById("ajaxMaxWeight").value='';                         // setting default
        
        document.getElementById("ajaxMaxLength").value='';                         // setting default
        document.getElementById("ajaxMinLength").value='';                         // setting default
        document.getElementById("ajaxMaxWidth").value='';                         // setting default
        document.getElementById("ajaxMinWidth").value='';                         // setting default
        document.getElementById("ajaxMaxHeight").value='';                         // setting default
        document.getElementById("ajaxMinHeight").value='';                         // setting default
     
      
      var ajaxshipMethodvalue=document.getElementById("shipMethodId").value;
      var testForFedexUps=ajaxshipMethodvalue.substring(0,ajaxshipMethodvalue.indexOf("|"));
      //
      if(testForFedexUps!='100'&&testForFedexUps!='110'&&testForFedexUps!='111'&&testForFedexUps!='114' &&testForFedexUps!='115') //Shiva added 114
      {
    
      }
      else
      {     
          var CarrierPayMethodTextstr=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
          if(CarrierPayMethodTextstr=='PP')
          {
            var str1=document.getElementById('shipMethodId').options[document.getElementById('shipMethodId').selectedIndex].id;
    
            if(str1!='')
            {
              document.getElementById("carrierAccountNumberId").value=str1;
              // vikas added code to mask te account number 
              document.getElementById("carrierAccountNumberHidId").value=str1;
              if(document.getElementById("maskAccountCheckID").value == "Y"){
              mask(); 
              } 
              // vikas code ended
            }
          }
          
          var carrierPaymethodSelected = document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].text;
          if(carrierPaymethodSelected == "RECIPIENT" || carrierPaymethodSelected == "THIRD PARTY BILLING"){
                getDefaultAccountNumber();
                 // vikas added code to mask te account number 
              if(document.getElementById("maskAccountCheckID").value == "Y"){
              mask(); 
              } 
              // vikas code ended
                
          }
          
          if(document.getElementById("chngCarrierPayMethodHidden").value != 'true')// To check whether paymethod changed manually and load its a/c no. accordingly
          {
            var upsMode=trim(document.getElementById("upsMode").value); 
               var acctNumberHiddenValue = document.getElementById("ajaxAfterShipCarrAccNumber").value ;
//               alert("upsMode::"+upsMode);
//               alert("document.getElementById(carrierPayMethodId).value:2:"+document.getElementById("carrierPayMethodId").value);
               if((upsMode == "ShipExec" && document.getElementById("carrierPayMethodId").value != "PP") || upsMode != "Stamps.com")
               { 
              if(acctNumberHiddenValue != null && acctNumberHiddenValue != '')
              {
                document.getElementById("carrierAccountNumberId").value = acctNumberHiddenValue;
                 // vikas added code to mask te account number 
//              if(document.getElementById("maskAccountCheckID").value == "Y"){
//              mask(); 
//              } 
              // vikas code ended
              }
              }
              else{                   
                populateSavedAccNo(); // To default to already saved or selected pay method in error case end
             }
          }
          else   // To default to already saved or selected pay method in error case end
          populateSavedAccNo();
      }
    }
    
    
    function getUpsServiceLevelCode()
      { 
     
      var xmlHttp;
      try
        {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
        }
      catch (e)
        {    // Internet Explorer    
          try
            {      
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
            }
          catch (e)
            {      
              try
                {        
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
                }
              catch (e)
                {        
                  alert("Your browser does not support AJAX!");        
                  return false;        
                }      
            }    
        }
        
        xmlHttp.onreadystatechange=function()
          {
          if(xmlHttp.readyState==4)
          {
                
                var responseString="";
                responseString=xmlHttp.responseText;
                
                var index = responseString.indexOf('@@@');
                responseString = responseString.substr(index+3);
                
                
                document.getElementById("ajaxUpsServiceLevelCode").value=responseString;
            }
          }
          var url="aascAjaxGetUpsServiceLevelCode.jsp?ajaxCarrierCode="+document.getElementById("ajaxCarrierCode").value+"&ajaxcarrierservicelevel="+document.getElementById("ajaxcarrierservicelevel").value+"&ajaxOriginRegionCode="+document.getElementById("shipFromCountryId").value;
        
          xmlHttp.open("POST",url,false);
          xmlHttp.send(null);  
        }
        
    function fillCarrierPayMethod()
    {
    document.getElementById("carrierPayMethodId").options[0]=new Option('PREPAID','PP' , true, true);
    document.getElementById("carrierPayMethodId").options[1]=new Option('FREIGHT COLLECT','FC' , true, false);
    document.getElementById("carrierPayMethodId").options[2]=new Option('THIRD PARTY BILLING','TP' , true, false);
    document.getElementById("carrierPayMethodId").options[3]=new Option('CONSIGNEE','CG' , true, false);
      
      /***************  Added below code to select the default pay method that is configured in Profile options  By Eshwari  Start ****************/
      var defaultPayMethod = document.getElementById("defaultPayMethodId").value ;
      var paymethodLength = document.getElementById("carrierPayMethodId").options.length;
      
      var payMethod = "" ;
      
      for(var i=0;i<paymethodLength;i++)
      {
          payMethod = document.getElementById("carrierPayMethodId").options[i].text ;
          
          if(payMethod == defaultPayMethod)
          {
              document.getElementById("carrierPayMethodId").options[i].selected = true;
          }        
      }
      
      chgCarrierPayMethod();
      /***************  Added above code to select the default pay method that is configured in Profile options  By Eshwari  End ****************/
      
    }
    
    
     
       ////////////////////////////////////////////////////////////////////////////////////////////////////////
       //For editable select box
        ////////////////////////////////////////////////////////////////////////////////////////////////////////   
       
    
      function getBrowser() 
    {
      if( navigator.userAgent.indexOf("Chrome") != -1 ) {
        return "Chrome";
      } else if( navigator.userAgent.indexOf("Opera") != -1 ) {
        return "Opera";
      } else if( navigator.userAgent.indexOf("MSIE") != -1 ) {
        return "IE";
      } else if( navigator.userAgent.indexOf("Firefox") != -1 ) {
        return "Firefox";
      }else if( navigator.userAgent.indexOf("Safari") != -1 ) {
        return "Safari";
      } else {
      return "unknown";
      }
    }
    
    
    
    
    
      function fnKeyDownHandler(getdropdown, e)
      {
//        fnSanityCheck(getdropdown);
    
        // Press [ <- ] and [ -> ] arrow keys on the keyboard to change alignment/flow.
        // ...go to Start : Press  [ <- ] Arrow Key
        // ...go to End : Press [ -> ] Arrow Key
        // (this is useful when the edited-text content exceeds the ListBox-fixed-width)
        // This works best on Internet Explorer, and not on Netscape
    
        var vEventKeyCode = FindKeyCode(e);
    
        // Press left/right arrow keys
        if(vEventKeyCode == 37)
        {
        fnLeftToRight(getdropdown);
        }
        if(vEventKeyCode == 39)
        {
        fnRightToLeft(getdropdown);
        }
    
        // Delete key pressed
        if(vEventKeyCode == 46)
        {
        fnDelete(getdropdown);
        }
    
        // backspace key pressed
        if(vEventKeyCode == 8 || vEventKeyCode==127)
        {
        if(e.which) //Netscape
        {
             
            var browser=getBrowser();
          
            if(browser=="Firefox" || browser=="Chrome"||browser=="Safari"||browser=="IE")
            {
                
          fnKeyPressHandler_B(getdropdown, e);
                  e.preventDefault();
                 
            }
            else
            {
          //  e.which = '';
          return false;
            }
        }
        else //Internet Explorer
        {
          //To prevent backspace from activating the -Back- button of the browser
          e.keyCode = '';
          if(window.event.keyCode)
          {
          window.event.keyCode = '';
          }
        }
        return true;
        }
    
        // Tab key pressed, use code below to reorient to Left-To-Right flow, if needed
        //if(vEventKeyCode == 9)
        //{
        //  fnLeftToRight(getdropdown);
        //}
//        if(getdropdown.value.length > 33)
//        {
//          alert('Please Enter Upto 35 Characters Only ');
//          getdropdown.focus();
//          return false;
//        }
        
      }
    
      function fnLeftToRight(getdropdown)
      {
        getdropdown.style.direction = "ltr";
      }
    
      function fnRightToLeft(getdropdown)
      {
        getdropdown.style.direction = "rtl";
      }
    
      function fnDelete(getdropdown)
      {
        if(getdropdown.options.length != 0)
        // if dropdown is not empty
        {
        if (getdropdown.options.selectedIndex == vEditableOptionIndex_A)
        // if option the Editable field
        {
          getdropdown.options[getdropdown.options.selectedIndex].text = '';
          getdropdown.options[getdropdown.options.selectedIndex].value = ''; //Use this line only if want to change the internal value too; else this line is not required.
        }
        }
      }
    
    
      /*
      Since Internet Explorer and Netscape have different
      ways of returning the key code, displaying keys
      browser-independently is a bit harder.
      However, you can create a script that displays keys
      for either browser.
      The following function will display each key
      in the status line:
    
      The "FindKey.." function receives the "event" object
      from the event handler and stores it in the variable "e".
      It checks whether the "e.which" property exists (for Netscape),
      and stores it in the "keycode" variable if present.
      Otherwise, it assumes the browser is Internet Explorer
      and assigns to keycode the "e.keyCode" property.
      */
    
      function FindKeyCode(e)
      {
        if(e.which)
        {
        keycode=e.which;  //Netscape
        }
        else
        {
        keycode=e.keyCode; //Internet Explorer
        }
    
        return keycode;
      }
    
      function FindKeyChar(e)
      {
        keycode = FindKeyCode(e);
        if((keycode==8)||(keycode==127))
        {
        character="backspace"
        }
        else if((keycode==46))
        {
        character="delete"
        }
        else if((keycode==32)){
        character="space"
        }
        else
        {
        character=String.fromCharCode(keycode);
        }
    
        return character;
      }
    
      function fnSanityCheck(getdropdown)
      {
        if(vEditableOptionIndex_A>(getdropdown.options.length-1))
        {
        alert("PROGRAMMING ERROR: The value of variable vEditableOptionIndex_... cannot be greater than (length of dropdown - 1)");
        return false;
        }
      }
     
      /*----------------------------------------------
      Dropdown specific global variables are:
      -----------------------------------------------
      1) vEditableOptionIndex_A   --> this needs to be set by Programmer!! See explanation.
      2) vEditableOptionText_A    --> this needs to be set by Programmer!! See explanation.
      3) vPreviousSelectIndex_A
      4) vSelectIndex_A
      5) vSelectChange_A
    
      ---------------------------  */
    
      /*----------------------------------------------
      The dropdown specific functions
      (which manipulate dropdown specific global variables)
      used by all dropdowns are:
      -----------------------------------------------
      1) function fnChangeHandler_A(getdropdown)
      2) function fnKeyPressHandler_A(getdropdown, e)
      3) function fnKeyUpHandler_A(getdropdown, e)
    
      --------------------------- */
    
      /*------------------------------------------------
      IMPORTANT: Global Variable required to be SET by programmer
      --------------------------   */
    
      var vEditableOptionIndex_A = 0;
    
      // Give Index of Editable option in the dropdown.
      // For eg.
      // if first option is editable then vEditableOptionIndex_A = 0;
      // if second option is editable then vEditableOptionIndex_A = 1;
      // if third option is editable then vEditableOptionIndex_A = 2;
      // if last option is editable then vEditableOptionIndex_A = (length of dropdown - 1).
      // Note: the value of vEditableOptionIndex_A cannot be greater than (length of dropdown - 1)
    
      var vEditableOptionText_A = "--?--";
    
      // Give the default text of the Editable option in the dropdown.
      // For eg.
      // if the editable option is <option ...>--?--</option>,
      // then set vEditableOptionText_A = "--?--";
    
      /*------------------------------------------------
      Global Variables required for
      fnChangeHandler_A(), fnKeyPressHandler_A() and fnKeyUpHandler_A()
      for Editable Dropdowns
      --------------------------   */
    
      var vPreviousSelectIndex_A = 0;
      // Contains the Previously Selected Index, set to 0 by default
    
      var vSelectIndex_A = 0;
      // Contains the Currently Selected Index, set to 0 by default
    
      var vSelectChange_A = 'MANUAL_CLICK';
      // Indicates whether Change in dropdown selected option
      // was due to a Manual Click
      // or due to System properties of dropdown.
    
      // vSelectChange_A = 'MANUAL_CLICK' indicates that
      // the jump to a non-editable option in the dropdown was due
      // to a Manual click (i.e.,changed on purpose by user).
    
      // vSelectChange_A = 'AUTO_SYSTEM' indicates that
      // the jump to a non-editable option was due to System properties of dropdown
      // (i.e.,user did not change the option in the dropdown;
      // instead an automatic jump happened due to inbuilt
      // dropdown properties of browser on typing of a character )
    
      /*------------------------------------------------
      Functions required for  Editable Dropdowns
      --------------------------  */
    
      function fnChangeHandler_A(getdropdown)
      {
//        fnSanityCheck(getdropdown);

        vPreviousSelectIndex_A = vSelectIndex_A;
        // Contains the Previously Selected Index
    
        vSelectIndex_A = getdropdown.options.selectedIndex;
        // Contains the Currently Selected Index
    
        if ((vPreviousSelectIndex_A == (vEditableOptionIndex_A)) && (vSelectIndex_A != (vEditableOptionIndex_A))&&(vSelectChange_A != 'MANUAL_CLICK'))
        // To Set value of Index variables - 
        {
          getdropdown[(vEditableOptionIndex_A)].selected=true;
          vPreviousSelectIndex_A = vSelectIndex_A;
          vSelectIndex_A = getdropdown.options.selectedIndex;
          vSelectChange_A = 'MANUAL_CLICK';
          // Indicates that the Change in dropdown selected
          // option was due to a Manual Click
        }
        
      }
    
      function fnKeyPressHandler_A(getdropdown, e)
      {
//        fnSanityCheck(getdropdown);        
        keycode = e.which;//FindKeyCode(e);
        keychar = String.fromCharCode(keycode);//FindKeyChar(e);
        // Check for allowable Characters
        // The various characters allowable for entry into Editable option..
        // may be customized by minor modifications in the code (if condition below)
        // (you need to know the keycode/ASCII value of the  character to be allowed/disallowed.
    
        if ((keycode>47 && keycode<59)||(keycode>62 && keycode<127) ||(keycode==32))
        {
          var vAllowableCharacter = "yes";
        }
        else
        {
          var vAllowableCharacter = "no";
        }
    
        if(getdropdown.options.length != 0)
        // if dropdown is not empty
          if (getdropdown.options.selectedIndex == (vEditableOptionIndex_A))
          // if selected option the Editable option of the dropdown
          {
    
            var vEditString = getdropdown[vEditableOptionIndex_A].value;
            
            // make Editable option Null if it is being edited for the first time
            if((vAllowableCharacter == "yes")||(keychar=="backspace"))
            {
              if (vEditString == vEditableOptionText_A)
                vEditString = "";
            }
            if (keychar=="backspace")
            // To handle backspace - 
            {
              vEditString = vEditString.substring(0,vEditString.length-1);
              // Decrease length of string by one from right
    
              vSelectChange_A = 'MANUAL_CLICK';
              // Indicates that the Change in dropdown selected
              // option was due to a Manual Click
    
            }            
            if (vAllowableCharacter == "yes")
            // To handle addition of a character - 
            {              
                //e = window.event || e;
                //alert('keycode == '+keycode);
                //var keyChar = String.fromCharCode(keycode);
                
                vEditString = vEditString+""+keychar;
              // Concatenate Enter character to Editable string
    
              // The following portion handles the "automatic Jump" bug
              // The "automatic Jump" bug (Description):
              //   If a alphabet is entered (while editing)
              //   ...which is contained as a first character in one of the read-only options
              //   ..the focus automatically "jumps" to the read-only option
              //   (-- this is a common property of normal dropdowns
              //    ..but..is undesirable while editing).
    
              var i=0;
              vEnteredChar = String.fromCharCode(keycode);
              var vUpperCaseEnteredChar = vEnteredChar;
              var vLowerCaseEnteredChar = vEnteredChar;
              if(((keycode)>=97)&&((keycode)<=122))
              // if vEnteredChar lowercase
                vUpperCaseEnteredChar = String.fromCharCode(keycode - 32);
                // This is UpperCase
    
    
              if(((keycode)>=65)&&((keycode)<=90))
              // if vEnteredChar is UpperCase
                vLowerCaseEnteredChar = String.fromCharCode(keycode + 32);
                // This is lowercase
    
              if(e.which) //For Netscape
              {
                // Compare the typed character (into the editable option)
                // with the first character of all the other
                // options (non-editable).
    
                // To note if the jump to the non-editable option was due
                // to a Manual click (i.e.,changed on purpose by user)
                // or due to System properties of dropdown
                // (i.e.,user did not change the option in the dropdown;
                // instead an automatic jump happened due to inbuilt
                // dropdown properties of browser on typing of a character )
    
                for (i=0;i<=(getdropdown.options.length-1);i++)
                {
                  if(i!=vEditableOptionIndex_A)
                  {
                    var vReadOnlyString = getdropdown[i].text;
                    var vFirstChar = vReadOnlyString.substring(0,1);
                    if((vFirstChar == vUpperCaseEnteredChar)||(vFirstChar == vLowerCaseEnteredChar))
                    {
                      vSelectChange_A = 'AUTO_SYSTEM';
                      // Indicates that the Change in dropdown selected
                      // option was due to System properties of dropdown
                      break;
                    }
                    else
                    {
                      vSelectChange_A = 'MANUAL_CLICK';
                      // Indicates that the Change in dropdown selected
                      // option was due to a Manual Click
                    }
                  }
                }
              }
            }
            // Set the new edited string into the Editable option
            getdropdown.options[vEditableOptionIndex_A].text = vEditString;
            getdropdown.options[vEditableOptionIndex_A].value = vEditString; //Use this line only if want to change the internal value too; else this line is not required.
    
            return false;
          }
        
        return true;
      }
    
      function fnKeyUpHandler_A(getdropdown, e)
      {
//        fnSanityCheck(getdropdown);
        if(e.which) // Netscape
        {
          if(vSelectChange_A == 'AUTO_SYSTEM')
          {
            // if editable dropdown option jumped while editing
            // (due to typing of a character which is the first character of some other option)
            // then go back to the editable option.
            getdropdown[(vEditableOptionIndex_A)].selected=true;
          }
    
          var vEventKeyCode = FindKeyCode(e);
          // if [ <- ] or [ -> ] arrow keys are pressed, select the editable option
          if((vEventKeyCode == 37)||(vEventKeyCode == 39))
          {
            getdropdown[vEditableOptionIndex_A].selected=true;
          }
        }
        
      }
    
    /*--------------------------------------------------------------------------------------------  */
    
      /*----------------------------------------------
      Dropdown specific global variables are:
      -----------------------------------------------
      1) vEditableOptionIndex_B   --> this needs to be set by Programmer!! See explanation.
      2) vEditableOptionText_B    --> this needs to be set by Programmer!! See explanation.
      3) vPreviousSelectIndex_B
      4) vSelectIndex_B
      5) vSelectChange_B
    
      ---------------------------  */
    
      /*----------------------------------------------
      The dropdown specific functions
      (which manipulate dropdown specific global variables)
      used by all dropdowns are:
      -----------------------------------------------
      1) function fnChangeHandler_B(getdropdown)
      2) function fnKeyPressHandler_B(getdropdown, e)
      3) function fnKeyUpHandler_B(getdropdown, e)
    
      ---------------------------  */
    
      /*------------------------------------------------
      IMPORTANT: Global Variable required to be SET by programmer
      --------------------------   */
    
      var vEditableOptionIndex_B = 0;
    
      // Give Index of Editable option in the dropdown.
      // For eg.
      // if first option is editable then vEditableOptionIndex_B = 0;
      // if second option is editable then vEditableOptionIndex_B = 1;
      // if third option is editable then vEditableOptionIndex_B = 2;
      // if last option is editable then vEditableOptionIndex_B = (length of dropdown - 1).
      // Note: the value of vEditableOptionIndex_B cannot be greater than (length of dropdown - 1)
    
      var vEditableOptionText_B = "--?--";
    
      // Give the default text of the Editable option in the dropdown.
      // For eg.
      // if the editable option is <option ...>--?--</option>,
      // then set vEditableOptionText_B = "--?--";
    
      /*------------------------------------------------
      Global Variables required for
      fnChangeHandler_B(), fnKeyPressHandler_B() and fnKeyUpHandler_B()
      for Editable Dropdowns
      --------------------------   */
    
      var vPreviousSelectIndex_B = 0;
      // Contains the Previously Selected Index, set to 0 by default
     var space=0;
      var vSelectIndex_B = 0;
      // Contains the Currently Selected Index, set to 0 by default
    
      var vSelectChange_B = 'MANUAL_CLICK';
      // Indicates whether Change in dropdown selected option
      // was due to a Manual Click
      // or due to System properties of dropdown.
    
      // vSelectChange_B = 'MANUAL_CLICK' indicates that
      // the jump to a non-editable option in the dropdown was due
      // to a Manual click (i.e.,changed on purpose by user).
    
      // vSelectChange_B = 'AUTO_SYSTEM' indicates that
      // the jump to a non-editable option was due to System properties of dropdown
      // (i.e.,user did not change the option in the dropdown;
      // instead an automatic jump happened due to inbuilt
      // dropdown properties of browser on typing of a character )
    
      /*------------------------------------------------
      Functions required for  Editable Dropdowns
      --------------------------   */
    
      function fnChangeHandler_B(getdropdown)
      {
//        fnSanityCheck(getdropdown);
    
        vPreviousSelectIndex_B = vSelectIndex_B;
        // Contains the Previously Selected Index
    
        vSelectIndex_B = getdropdown.options.selectedIndex;
        // Contains the Currently Selected Index
    
        if ((vPreviousSelectIndex_B == (vEditableOptionIndex_B)) && (vSelectIndex_B != (vEditableOptionIndex_B))&&(vSelectChange_B != 'MANUAL_CLICK'))
        // To Set value of Index variables - 
        {
          getdropdown[(vEditableOptionIndex_B)].selected=true;
          vPreviousSelectIndex_B = vSelectIndex_B;
          vSelectIndex_B = getdropdown.options.selectedIndex;
          vSelectChange_B = 'MANUAL_CLICK';
          // Indicates that the Change in dropdown selected
          // option was due to a Manual Click
        }
        
        
       // getShipToAddress();
      }
    
      function fnKeyPressHandler_B(getdropdown, e)
      {
//        fnSanityCheck(getdropdown);
        keycode = e.which;
        keychar = String.fromCharCode(keycode);//FindKeyChar(e);
        // Check for allowable Characters
        // The various characters allowable for entry into Editable option..
        // may be customized by minor modifications in the code (if condition below)
        // (you need to know the keycode/ASCII value of the  character to be allowed/disallowed.
        // - 
    
        if ((keycode>32 && keycode<59)||(keycode>57 && keycode<127) ||(keycode==32) || (keycode>185 && keycode<192) || (keycode>219 && keycode<222))
        {
          var vAllowableCharacter = "yes";
        }
        else
        {
          var vAllowableCharacter = "no";
        }
    
    
        if(getdropdown.options.length != 0)
        // if dropdown is not empty
          if (getdropdown.options.selectedIndex == (vEditableOptionIndex_B))
          // if selected option the Editable option of the dropdown
          {
    
            var vEditString = getdropdown[vEditableOptionIndex_B].text;
    
            // make Editable option Null if it is being edited for the first time
            if((vAllowableCharacter == "yes")||(keychar=="backspace"))
            {
              if (vEditString == vEditableOptionText_B)
                vEditString = "";
            }
            if (keychar=="backspace")
            // To handle backspace - 
            {
              vEditString = vEditString.substring(0,vEditString.length-1);
              // Decrease length of string by one from right
    
              vSelectChange_B = 'MANUAL_CLICK';
              // Indicates that the Change in dropdown selected
              // option was due to a Manual Click
    
            }
    
            if (vAllowableCharacter == "yes")
            // To handle addition of a character - 
            {
              
               if(keycode==32)
                 {
                 space=space+1;
                 }
                 
    
            if(space>0 && keycode!=32){
            var k = vEditString;
            for(p=1;p<=space;p++)
            {
               k = k+" ";    
             //  alert("in for loop:"+p+" & K ="+k.length)
               }
             //  alert("k in for loop:"+k+"k len"+k.length);
                      vEditString=k+keychar;//String.fromCharCode(keycode);
                   //   alert("vEditString in if loop"+vEditString+"^ len :"+vEditString.length);
                   //   alert("space 2 :"+space);
                    space=0;
            }else{
            
              vEditString=vEditString+keychar;//String.fromCharCode(keycode);
                             //   alert("vEditString in else loop"+vEditString);
    
              }
              
              
              
              //vEditString+=String.fromCharCode(keycode);
              // Concatenate Enter character to Editable string
    
              // The following portion handles the "automatic Jump" bug
              // The "automatic Jump" bug (Description):
              //   If a alphabet is entered (while editing)
              //   ...which is contained as a first character in one of the read-only options
              //   ..the focus automatically "jumps" to the read-only option
              //   (-- this is a common property of normal dropdowns
              //    ..but..is undesirable while editing).
    
              var i=0;
              var vEnteredChar = String.fromCharCode(keycode);
              var vUpperCaseEnteredChar = vEnteredChar;
              var vLowerCaseEnteredChar = vEnteredChar;
    
    
              if(((keycode)>=97)&&((keycode)<=122))
              // if vEnteredChar lowercase
                vUpperCaseEnteredChar = String.fromCharCode(keycode - 32);
                // This is UpperCase
    
    
              if(((keycode)>=65)&&((keycode)<=90))
              // if vEnteredChar is UpperCase
                vLowerCaseEnteredChar = String.fromCharCode(keycode + 32);
                // This is lowercase
    
              if(e.which) //For Netscape
              {
                // Compare the typed character (into the editable option)
                // with the first character of all the other
                // options (non-editable).
    
                // To note if the jump to the non-editable option was due
                // to a Manual click (i.e.,changed on purpose by user)
                // or due to System properties of dropdown
                // (i.e.,user did not change the option in the dropdown;
    
                // instead an automatic jump happened due to inbuilt
                // dropdown properties of browser on typing of a character )
                
    
                for (i=0;i<=(getdropdown.options.length-1);i++)
                {
                  if(i!=vEditableOptionIndex_B)
                  {
                    var vReadOnlyString = getdropdown[i].text;
                    var vFirstChar = vReadOnlyString.substring(0,1);
                    if((vFirstChar == vUpperCaseEnteredChar)||(vFirstChar == vLowerCaseEnteredChar))
                    {
                      vSelectChange_B = 'AUTO_SYSTEM';
                      // Indicates that the Change in dropdown selected
                      // option was due to System properties of dropdown
                      break;
                    }
                    else
                    {
                      vSelectChange_B = 'MANUAL_CLICK';
                      // Indicates that the Change in dropdown selected
                      // option was due to a Manual Click
                    }
                  }
                }
              }
            }
    
            // Set the new edited string into the Editable option
            
            getdropdown.options[vEditableOptionIndex_B].text = vEditString;
            getdropdown.options[vEditableOptionIndex_B].value = vEditString; //Use this line only if want to change the internal value too; else this line is not required.
    
            return false;
          }
       
        return true;
      }
    
      function fnKeyUpHandler_B(getdropdown, e)
      {
//        fnSanityCheck(getdropdown);
    
        if(e.which) // Netscape
        {
          if(vSelectChange_B == 'AUTO_SYSTEM')
          {
            // if editable dropdown option jumped while editing
            // (due to typing of a character which is the first character of some other option)
            // then go back to the editable option.
            getdropdown[(vEditableOptionIndex_B)].selected=true;
          }
    
          var vEventKeyCode = FindKeyCode(e);
          // if [ <- ] or [ -> ] arrow keys are pressed, select the editable option
          if((vEventKeyCode == 37)||(vEventKeyCode == 39))
          {
            getdropdown[vEditableOptionIndex_B].selected=true;
          }
        }
        
      }
    
       ////////////////////////////////////////////////////////////////////////////////////////////////////////
       //Ajax for ship to locations retrival
        ////////////////////////////////////////////////////////////////////////////////////////////////////////   
       
    function getShipToLocations()
    {
    
       var customerName= document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].text;
       if(customerName=='--Select--')
       {
            document.getElementById('customerLocationId').value='--Select--';
            document.getElementById('shipToAddressId').value='';
            document.getElementById('shipToAddrLine2Id').value='';
            document.getElementById('city').value='';
            document.getElementById('state').value='';
//            document.getElementById('country').value='';
            document.getElementById('zip').value='';
            document.getElementById('contactNameId').value='';
            document.getElementById('phoneNumberId').value='';
            document.DynaShipmentShipSaveForm.intlFlag.value = 'N';
         try {   document.getElementById("tickPic").innerHTML=''; 
            document.getElementById("intlMassage").innerHTML = '' ;  
            } catch(err){ }
       }
      var clientIdHid=document.getElementById("clientIdHid").value;
      if(customerName =='' || customerName == null)
      {
           document.forms['DynaShipmentShipSaveForm'].shipToAddress.options[0] = new Option('','');
      }
      else
      {
        var xmlHttp;
        try
        {    // Firefox, Opera 8.0+, Safari  
          xmlHttp=new XMLHttpRequest(); 
        }
        catch (e)
        {    // Internet Explorer 
          try
          {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
          }
          catch (e)
          {    
            try
            {     
              xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
            }
            catch (e)
            {   
                alert("Your browser does not support AJAX!");  
                return false;      
            }    
          }  
        }
        xmlHttp.onreadystatechange=function()
        {
          if(xmlHttp.readyState==4)
          {
              //Sunanda added for bug fix #2695
              var custLocation=document.getElementById('customerLocationId');
              var i;
              for(i=custLocation.options.length-1;i>=0;i--)
               {
                custLocation.remove(i);
               }
              var responseStringDummy=trim(xmlHttp.responseText);
              var index = responseStringDummy.indexOf('@@@');
              responseStringDummy = responseStringDummy.substr(index+3);
              var testindex = responseStringDummy.indexOf('@');
              var option12;
              var i=1;
              var indextest;
              if(responseStringDummy == ''){
                document.getElementById('customerLocationId').options[0] =  new Option("","",true,true);
              } else {
                  while(testindex!=-1)
                  {
                    option12=responseStringDummy.substr(0,testindex)
                    indextest = option12.indexOf('***');
                    optionValue = option12.substr(0,indextest);
        
                    document.getElementById('customerLocationId').options[0] =  new Option("--Select--","--Select--",true,true);
                    document.getElementById('customerLocationId').options[i] = new Option(optionValue, optionValue, false, false);
                    responseStringDummy=responseStringDummy.slice(testindex+1,responseStringDummy.length);
                    testindex  = responseStringDummy.indexOf('@');
                    i++;
                  } 
              }
           }
         }
         customerName = encodeURIComponent(customerName);
         var url="aascAjaxShipToAddr1.jsp?customerName="+customerName+"&clientIdHid="+clientIdHid;
         xmlHttp.open("POST",url,false);   //false by jagadish to load locations loc even after error cases
         xmlHttp.send(null); 
          
      }
    }
       
    function getCustLocation()
    {
        var customer = document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].text;
        if(customer=='--Select--')
        {
           alert("Please select customer name");
            //return false;
        }
    }
       
    function getShipToAddress()
    {
        var customer = document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].text;
        var shipToLocation= trim(document.getElementById('customerLocationId').options[document.getElementById('customerLocationId').selectedIndex].text);
    
       var clientIdHid=document.getElementById("clientIdHid").value;
       var xmlHttp;
        if(customer!='--Select--' && shipToLocation != '--Select-- ')
        {
            try
            {    // Firefox, Opera 8.0+, Safari  
                xmlHttp=new XMLHttpRequest(); 
            }
            catch (e)
            {    // Internet Explorer 
                try
                {          
                    xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
                }  
                catch (e)
                {    
                    try
                    {     
                        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
                    }
                    catch (e)
                    {        
                        alert("Your browser does not support AJAX!");  
                        return false;      
                    }    
                }     
            }
            xmlHttp.onreadystatechange=function()
            {
                if(xmlHttp.readyState==4)
                {
                    var responseStringDummy=trim(xmlHttp.responseText);
                    var tempVal;
                    var i=0;
    
             
                    var index = responseStringDummy.indexOf('@@@');
                    responseStringDummy = responseStringDummy.substr(index+3);
             
                    var testindex  = responseStringDummy.indexOf('***'); 
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    if(tempVal!="" )
                        document.getElementById('shipToAddressId').value=tempVal;       
                    var testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    document.getElementById('shipToAddrLine2Id').value=tempVal;       
                    
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    document.getElementById('shipToAddrLine3Id').value=tempVal;       
                    
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('city').value=tempVal;
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('state').value=tempVal;
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('country').value=tempVal;
                    document.getElementById('countryCodeVal').value = document.getElementById('country').value;
                    var shipFromCountry = document.getElementById('shipFromCountryId').value ;
                    
                    
                    var previousShipToCountry = document.getElementById("shipToCountryHiddenId").value ;    
                        
                    if( shipFromCountry != null && shipFromCountry != ''&& tempVal !=null && tempVal!='')//Sunanda added for for bug fix #2733
                    {
                        if(shipFromCountry != tempVal || (previousShipToCountry != '' && previousShipToCountry != null && previousShipToCountry != tempVal))
                        {
                              chngShipFlag = 'false' ;
                              
                              if(shipFromCountry != tempVal)
                        document.DynaShipmentShipSaveForm.intlFlag.value = 'Y';
                              else
                                 document.DynaShipmentShipSaveForm.intlFlag.value = 'N';
                        }
                        else
                        {
                           chngShipFlag ='true' ;
                           document.DynaShipmentShipSaveForm.intlFlag.value = 'N';   
                        }     
                    
                        /*if(chngShipFlag=='false')  // Eshwari commented this code to reduce multiple Ajax call when Ship To Location is changed
                        { 
                            getShipMethodAjax();
                            chngShipFlag ='true' ;
                        }    */
                        
                        if(document.DynaShipmentShipSaveForm.intlFlag.value == 'Y')
                        {
                         document.getElementById("tickPic").innerHTML='<img src="images/aasc_tick.png" width="16" height="16"/>';
                         document.getElementById("intlMassage").innerHTML = '<a href="javascript:intShipmentsPopUp()" id="intlMessageId" style="color:rgb(64, 115, 134); text-decoration:underline;">International Shipments</a>' ;
                        }
                        else{
                         document.getElementById("tickPic").innerHTML='';
                         document.getElementById("intlMassage").innerHTML = ''; 
                        }
                        countryValidate();
                    }else{
                        document.DynaShipmentShipSaveForm.intlFlag.value = 'N';
                     try{   document.getElementById("tickPic").innerHTML='';
                        document.getElementById("intlMassage").innerHTML = '' ;
                        }catch(e){}
                    }
                    document.getElementById("shipToCountryHiddenId").value = tempVal ;
                    
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('zip').value=tempVal;
                  
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('contactNameId').value=tempVal;
                  
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('phoneNumberId').value=tempVal;
                    
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex)
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('shipToEmailId').value=tempVal;
                    
                    if(document.DynaShipmentShipSaveForm.intlFlag.value == 'N')
                    {
                    }
                }
            }
            var toLocation= trim(document.getElementById('customerLocationId').options[document.getElementById('customerLocationId').selectedIndex].text);
            customer = encodeURIComponent(customer);
            var url="aascAjaxShipToAddress.jsp?shipToLocation="+toLocation+"&customer="+customer+"&clientIdHid="+clientIdHid;
            xmlHttp.open("POST",url,false);
            xmlHttp.send(null); 
        }
        else if(shipToLocation == '--Select--' || shipToLocation == ''){
            document.getElementById('shipToAddressId').value='';
            document.getElementById('shipToAddrLine2Id').value='';
            document.getElementById('city').value='';
            document.getElementById('state').value='';
            document.getElementById('country').value='';
            document.getElementById('zip').value='';
            document.getElementById('contactNameId').value='';
            document.getElementById('phoneNumberId').value='';
            document.DynaShipmentShipSaveForm.intlFlag.value = 'N';
            document.getElementById("tickPic").innerHTML='';
            document.getElementById("intlMassage").innerHTML = '' ;
        }
    }
    
    function countryValidate()
    {
      var shipToLocation= trim(document.getElementById('customerLocationId').options[document.getElementById('customerLocationId').selectedIndex].text);
      if(shipToLocation  !='--Select--' && shipToLocation  !='')
      {
      if(document.DynaShipmentShipSaveForm.countrySymbol.value=='Select')
      {
        alert("Please enter the Country Code");
        document.DynaShipmentShipSaveForm.countrySymbol.focus();
        return false;
      }
      // Added code by Suman Gunda
      else if(document.DynaShipmentShipSaveForm.countrySymbol.value.length!=2){
//            alert("Country code should contain 2 characters");
//            document.DynaShipmentShipSaveForm.countrySymbol.focus();
//            return false;    
      }
      else
      {
      //checkIntl();
      }
      //chngShipFlag="false";
      }
            //Padma code starts here for issue 2961
          
//      alert('checked two country codes');
      checkIntl();
      
     
      //Padma code ends here
      return true; 
    }
    function checkIntl()
    {
        var shipFromCountry = document.getElementById('shipFromCountryId').value ;           
        var shipToCountry = trim(document.getElementById('country').value);

        if(shipToCountry == 'us' || shipToCountry == 'Us' || shipToCountry == 'uS'|| shipToCountry == 'US')
        {
           shipToCountry = 'US';
           document.DynaShipmentShipSaveForm.countrySymbol.value = 'US';
           if(chngCountryFlag == "usorgin")
           {
              chngShipFlag="true";
           }
           else
           {
              chngShipFlag="false";
           }
             
        }
        else if(shipToCountry != "")
        {
           if(chngCountryFlag == "intl")
           {
             chngShipFlag="true";
           }
           else
           {
             chngShipFlag="false";
           }
           chngCountryFlag = "intl";
          
        }
              
        var previousShipFromLocation = document.getElementById('shipFromLocationIdHidden').value ;
        var ShipFromLocation = document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
        var shipToCountryPrevious = document.getElementById('shipToCountryHiddenId').value;
        var shipFromCountryPrevious = document.getElementById('shipFromCountryHiddenId').value;  // Eshwari added this line to fix bug # 2989
        
        // Added second condition by Padmavathi
        if((previousShipFromLocation != ShipFromLocation) || (shipFromCountry != shipToCountry) || 
           (shipToCountryPrevious != shipToCountry) || (shipFromCountryPrevious != shipFromCountry))  // Eshwari added shipFromCountryPrevious condition check to fix bug # 2989
        {
           if(shipFromCountry != 'Select' && shipFromCountry != shipToCountry && shipToCountry != '' && shipToCountry != 'Select')
           document.DynaShipmentShipSaveForm.intlFlag.value = 'Y';
           else
              document.DynaShipmentShipSaveForm.intlFlag.value = 'N';
 
           chngShipFlag = 'false' ;           
           //var internationalFlag = 'Y';//document.getElementById('internationalFlagId').value;
           //if(internationalFlag == 'Y')
           if(document.DynaShipmentShipSaveForm.intlFlag.value == 'Y')
           {
             document.getElementById("tickPic").innerHTML='<img src="images/aasc_tick.png" width="16" height="16"/>';
             document.getElementById("intlMassage").innerHTML = '<a href="javascript:intShipmentsPopUp()" id="intlMessageId" style="color:rgb(64, 115, 134); text-decoration:underline;">International Shipments</a>' ;
           }
           else{
             document.getElementById("tickPic").innerHTML='';
             document.getElementById("intlMassage").innerHTML = ''; 
           }
           document.getElementById("shipToCountryHiddenId").value = shipToCountry;   
        }else{
            document.getElementById("intlFlag").value = 'N';
            chngShipFlag = 'true' ;
           try{ document.getElementById("tickPic").innerHTML='';
            document.getElementById("intlMassage").innerHTML = '';
            }catch(e){}
        }
        if(chngShipFlag=='false')
        { 
           getShipMethodAjax();
           chngShipFlag = 'true' ;
        }
    }
       
    function getShipMethodAjax()
    {
       document.getElementById("shipMethodId").options.length = 0; 
       document.getElementById("shipMethodId").options[0] = new Option('','');
       var intlFlagStr = document.getElementById("intlFlag").value;
       var locationId = document.getElementById("locationId").value;
       var shipMethodProf = document.getElementById("shipMethodProfHidId").value;
       var xmlHttp;
       try
       {    // Firefox, Opera 8.0+, Safari  
         xmlHttp=new XMLHttpRequest(); 
       }
       catch (e)
       {    // Internet Explorer 
         try
         {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
         }
         catch (e)
         {    
          
           try
           {     
             xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
           }
           catch (e)
           {        alert("Your browser does not support AJAX!");  
              return false;      
           }    
         }  
            
       }
       xmlHttp.onreadystatechange=function()
       {
          if(xmlHttp.readyState==4)
          {
                var responseStringDummy=trim(xmlHttp.responseText);
                var index = responseStringDummy.indexOf('@@@');
                responseStringDummy = responseStringDummy.substr(index+3);
                var testindex  = responseStringDummy.indexOf('@@@@@@@');
                var indextest;
                var option12;
                var optionValue;
                var optionEle;
                var optionId;
                //below code added by Jagadish to provide --Select Ship Method-- as option in Shipmethod lov 
                document.getElementById("shipMethodId").options[0] = new Option("--Select Ship Method--","000||0*Select Ship Method@@00");
                document.getElementById("shipMethodId").options[0].id = optionId;
                //end of Jagadish code. 
                var i=1;
                
                if(responseStringDummy == "Carrier Configuration not Configured")
                {
                    alert("Please Contact your Admin and configure Carrier Configuration for the selected Ship From Location ");
                }
                else
                {                
                while(testindex!=-1)
                {
                  option12=responseStringDummy.substr(0,testindex);
                
                  indextest = option12.indexOf('#######');
                  acctIndex = option12.indexOf('^^^^^');
                  optionValue = option12.substr(0,indextest);
                  optionEle = option12.substring(indextest+7,acctIndex);
    
                  optionId = option12.substr(acctIndex+5,option12.length);
                    
                  document.getElementById("shipMethodId").options[i] = new Option(optionEle,optionValue);
                  document.getElementById("shipMethodId").options[i].id = optionId;
                  if(shipMethodProf == optionEle)
                  {
                    document.getElementById("shipMethodId").options[i].selected = true;
                    FillCarrier();
                    chgShipMethod();
                 
                    getCcCsl('1');
                  }
                  responseStringDummy=responseStringDummy.slice(testindex+7,responseStringDummy.length);
                  testindex  = responseStringDummy.indexOf('@@@@@@@');
                  i++;
                }
                if(shipMethodProf == '')
                {
                  document.getElementById("shipMethodId").options[0].selected = true;
                  FillCarrier();
                  chgShipMethod();
                  getCcCsl('2');
                }
            }
       }
       }
       var url="aascAjaxShipMethod.jsp?intlFlag="+intlFlagStr+"&locationId="+locationId;
       xmlHttp.open("POST",url,false);
       xmlHttp.send(null); 
       chngShipFlag = "false";  
        
    }
    
    function intShipmentsPopUp()
    {
        if(document.DynaShipmentShipSaveForm.orderNum.value == null || document.DynaShipmentShipSaveForm.orderNum.value == ''){
            getOrderNumber(0);
        }
     //document.DynaShipmentShipSaveForm.orderNumber.value="111222";
     var locationValue= document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
     var testIndex = locationValue.indexOf('****');
     var locationId = locationValue.slice(testIndex+4,locationValue.length); 
     var shipFlag1 = document.getElementById("flagShipID").value;
     var intlSaveFlag = document.DynaShipmentShipSaveForm.intlSaveFlag.value;
     var shipment = 'Shipping';
      var upsMode=trim(document.getElementById("upsMode").value);
//     alert("upsMode"+upsMode);
     var orderNO = document.DynaShipmentShipSaveForm.orderNum.value;
     var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
     var carrierCode=str.substring(0,3);
      var service = str.substring(7,(str.length)); // Srisha added code to get service name in INTL page after ship for bug #3377

    //Shiva added below code for DHL
	var accountNumber= document.DynaShipmentShipSaveForm.carrierAccountNumber.value;
    var payerType=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
     orderNO = encodeURIComponent(orderNO);
    if(payerType=='PP'){
        payerType='SENDER';
    }
    else if(payerType=='CG'){
        payerType='RECIPIENT';
    }
    else
    {
        payerType='SENDER';
    }
    //Shiva code end
      if(carrierCode == 100){
             if (upsMode=="UPS Direct"){
                     tpwindow =  window.open("AascUpsIntlShipAction.action?actionType=UpsInternational&shipFlagStr="+shipFlag1+"&intlSaveFlag="+intlSaveFlag+"&carrierCode="+carrierCode+"&shipmentType="+shipment+"&orderNo="+orderNO+"&locationId="+locationId,"Post",'width=1050,height=600,top=100,left=100,scrollbars=yes, resizable=yes');
                     tpwindow.focus();
                  }
             else {
                    tpwindow =  window.open("AascShipExecIntlShipAction.action?actionType=ShipExecInternational&shipFlagStr="+shipFlag1+"&intlSaveFlag="+intlSaveFlag+"&carrierCode="+carrierCode+"&shipmentType="+shipment+"&orderNo="+orderNO+"&locationId="+locationId+"&upsMode="+upsMode,"Post",'width=1050,height=700,top=100,left=100,scrollbars=yes, resizable=yes');
                    tpwindow.focus();
                  }
         }
      else if(carrierCode == 110 || carrierCode == 111){
        tpwindow =  window.open("AascIntlShipAction.action?actionType=FedExInternational&shipFlagStr="+shipFlag1+"&intlSaveFlag="+intlSaveFlag+"&carrierCode="+carrierCode+"&shipmentType="+shipment+"&orderNo="+orderNO+"&locationId="+locationId+"&service="+service,"Post",'width=1050,height=700,top=100,left=100,scrollbars=yes, resizable=yes');
        tpwindow.focus();
     }
          //Mahesh
     else if(carrierCode == 115){
        tpwindow =  window.open("AascStampsIntlShipAction.action?actionType=StampsInternational&shipFlagStr="+shipFlag1+"&intlSaveFlag="+intlSaveFlag+"&carrierCode="+carrierCode+"&shipmentType="+shipment+"&orderNo="+orderNO+"&locationId="+locationId+"&service="+service,"Post",'width=1050,height=700,top=100,left=100,scrollbars=yes, resizable=yes');
        tpwindow.focus();
     }
     else if(carrierCode == 114 ){
        tpwindow =  window.open("AascDHLIntlShipAction.action?actionType=DHLInternational&shipFlagStr="+shipFlag1+"&intlSaveFlag="+intlSaveFlag+"&carrierCode="+carrierCode+"&shipmentType="+shipment+"&orderNo="+orderNO+"&locationId="+locationId+"&payerType="+payerType+"&service="+service,"Post",'width=1050,height=700,top=100,left=100,scrollbars=yes, resizable=yes');
        tpwindow.focus();
     }
     else{
        alert("Please Select a Ship Method");
        DynaShipmentShipSaveForm.shipMethod.focus();
     }
    }
    
    function carrierProfileOptionSet()
    { 
     
     if(document.DynaShipmentShipSaveForm.carrierProfileOptionsShipmethod.value==null)
     {
     document.DynaShipmentShipSaveForm.carrierProfileOptionsShipmethod.value="";
     }
     var shipMethodProf = document.DynaShipmentShipSaveForm.carrierProfileOptionsShipmethod.value;
     var dropOffProf = document.DynaShipmentShipSaveForm.ProfileDropOffType.value;
     var packagingProf = document.DynaShipmentShipSaveForm.ProfileCarrierPackaging.value;
     var payMethodProf = document.DynaShipmentShipSaveForm.ProfileCarrierPaymethod.value;
     var chCOD = document.DynaShipmentShipSaveForm.chCOD1.value;
     var signatureOption = document.DynaShipmentShipSaveForm.signatureOption.value;
     
     
     if(shipMethodProf != '' || dropOffProf != '' || packagingProf != '' )
     {
      if(chCOD == 'Y' || signatureOption != 'none')
      {
       openPackPopup(1);
       }
     }
    }
    
    function disableVoidSubmit(){
    
    //submitButtonId.value='Void';
    
    document.getElementById("submitButtonId").value="Void";
    var orderNumber = document.getElementById("orderNumberID").value;
    if (orderNumber != "" && orderNumber != null && (orderNumber.substring(0,2))!="SC") {
         var orderNumberTemp=encodeURIComponent(orderNumber);
         document.getElementById("orderNumberHidID").value = orderNumberTemp;
    }
    
     if(document.DynaShipmentShipSaveForm.submitButton1.value=="1")
      {
        alert("Request Submitted. Please wait and Click Ok");
        return false;
      }
       else{
            if( validation()==false)                                                // Added if codition to return false or true. By Kahja
            { 
                return false;
            }
         document.DynaShipmentShipSaveForm.submit();
     return true;
      }   
    }
    
    
    function disableSubmit()
    {
      
      if(document.DynaShipmentShipSaveForm.submitButton1.value=="1")
      {
        alert("Request Submitted. Please wait and Click Ok");
        return false;
      }
       else{
            if( validation()==false)                                                // Added if codition to return false or true. By Kahja
            { 
                return false;
            }
      document.DynaShipmentShipSaveForm.submit();
     return true;
      }
    }
    
    
    function carrierProfileOptionChange()
    {
    var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
     var carrierCode=str.substring(0,3);
     var num=document.getElementById("countPacketsID").value;
        var num1=parseInt(num);
      var delConfirm=trim(document.DynaShipmentShipSaveForm['delConfirm'+num1].value);
       var pkging=trim(document.DynaShipmentShipSaveForm['pkging'+num1].value);
       
     if(carrierCode == '100')
     {
     
      for(var index =  1;index<=num1;index ++)
      {
          if(pkging=='01' || pkging=='02' ||pkging=='03' ||pkging=='04' || pkging =='21' || pkging =='24' || pkging =='25')
          {
          document.DynaShipmentShipSaveForm['pkging'+num1].value = pkging
          }
          else
          {
          document.DynaShipmentShipSaveForm['pkging'+num1].value = "";
          }
          if(delConfirm=='1' || delConfirm=='2' || delConfirm=='3' ||delConfirm=='NA')
          {
          document.DynaShipmentShipSaveForm['delConfirm'+num1].value = delConfirm;
          }
          else
          {
          document.DynaShipmentShipSaveForm['delConfirm'+num1].value = "";
          }
      }
     }
     if(carrierCode ==110 || carrierCode==111)
     {
     var signatureOption = document.getElementById('signatureOptionID'+num1).value;
    if(signatureOption=='DIRECT' || signatureOption=='ADULT' || signatureOption=='DELIVERWITHOUTSIGNATURE' || signatureOption=='INDIRECT')
    {
    document.getElementById('signatureOptionID'+num1).value = signatureOption;
    }
    else
    {
    document.getElementById('signatureOptionID'+num1).value = "";
    }
     }
    
    }
    
    
    function openDimPopup(num1)
    {
        var dimValue = document.getElementById('dimensionNameID'+num1).options[document.getElementById('dimensionNameID'+num1).selectedIndex].value;
        var dimValueText = document.getElementById('dimensionNameID'+num1).options[document.getElementById('dimensionNameID'+num1).selectedIndex].text; 
        packwindow =  window.open("aascPackageDimensions.jsp?packCount="+num1+"&dimensionName="+dimValue+"&dimValueText="+dimValueText,"Post",'width=400,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
    } 
    
    
     function insRow()
     {
        // Added by Padmavathi for bug #2908
        var shipMethodValue = document.getElementById("shipMethodId").options[document.getElementById('shipMethodId').selectedIndex].text;
        var custName =document.getElementById('customerName').options[document.getElementById('customerName').selectedIndex].text;
        var custLocation =document.getElementById("customerLocationId").value;
        if(custName =='--Select--' ) 
        {
        alert('Enter Customer Name');
        document.getElementById('customerName').focus();
        return false;
        }
        if(custLocation=='--Select--')
        {
        alert('Enter Ship To Location');
        document.getElementById("customerLocationId").focus();
        return false;
        }        
        if (shipMethodValue=='--Select Ship Method--')
       {
       alert('Please Choose a  Ship Method');
       document.getElementById("shipMethodId").focus();
       return false;
       }
          //Mahesh Added below code for Stamps.com
             var str = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
             var shipFlag1 = document.getElementById("flagShipID").value;

             var carrierCode = str.substring(0,3);
			 if(carrierCode == "115" && shipFlag1 == "N")
			 {
				 alert("Cannot add a package. Stamps.com supports only single package shipment.");   // Mahesh added code for Stamps.com
				 return false;
			 }
       
       //End of Padmavathi's code for bug #2908 .
       document.getElementById("submitButtonId").value='ADD';
       var num=document.getElementById("countPacketsID").value;
       var num1=parseInt(num);
       
          var dValue = "";
          var dText = "";
          var optTag = '';
          var dimValTag='';
          var packageLength="";
          var packageWidth="";
          var dimensionUnits="";
          var dimensionDefault="";
          var defaultDimeNameVar="";
          
          //Iterating for the Dimesion Inner HTML building
          for(var i=0; i<document.getElementById('dimensionNameID'+num1).length ; i++)
          { 
             dValue=document.getElementById('dimensionNameID'+num1).options[i].value;
             dText=document.getElementById('dimensionNameID'+num1).options[i].text;
             
             defaultDimeNameVar=document.getElementById('defaultDimeNameID'+num1).value;
             
             var selectedDimVal= document.getElementById('dimensionNameID'+num1).options[document.getElementById('dimensionNameID'+num1).selectedIndex].value;
             var selectedDimText= document.getElementById('dimensionNameID'+num1).options[document.getElementById('dimensionNameID'+num1).selectedIndex].text;
            
             
             if((selectedDimText == dText)) // && (selectedDimText == dText))
             {
                 
                 
              optTag=optTag+'<option value ="'+dValue+'" selected>'+dText+'</option>';
             }
             else
             {
                optTag=optTag+'<option value ="'+dValue+'" >'+dText+'</option>';
             } 
         }//End of For loop
    
        
        
        var DimValue=" ";
        var WtValue=" ";
        var lineno="";
        var weight="";
        var uom="";
        var trackno="";
        var dimension=""; 
        var codFlag="";
        var LargePackage=""; 
        var AdditionalHandling="";
          var codAmt="";
          var quantity ="0.0";
          var QUOM =" ";
          var unitWeight ="0.0";
          var unitValue ="0.0";
          var pdesc  =" ";
    
          var num1=parseInt(num);     
          
          var length =0;
          
          var shippedQty =0;
          
          var width =0;
          
          var height =0;
          
          var packageDeclaredValue=0;
    
          var signatureOption="";
          
          var returnShipment="";
    
          var PackageSurcharge=0;
          
          var PackageShipmentCost=0;
        
          var rtnShipFromCompany ="";
          
          var rtnShipToCompany="";
           
          var rtnShipFromContact="";
          
          var rtnShipToContact="";
          
          var rtnShipFromLine1="";
          
          var rtnShipToLine1="";
          
          var rtnShipFromLine2="";
          
          var rtnShipToLine2="";
          
          var rtnCountrySymbol="";
          
          var rtnShipFromCity="";
          
          var rtnShipFromSate="";
          
          var rtnShipFromZip="";
          
          var rtnShipToCity="";
          
          var rtnShipToState="";
          
          var rtnShipToZip="";
          var rtnShipFromPhone="";
          var rtnShipToPhone="";
          var rtnShipMethod="";
          var returnDescription="";
          var rtnDropOfType="";
          var rtnPackageList="";
          var rtnPayMethod="";
          var rtnACNumber="";
          var rtnRMA="";
          var rtnTrackingNumber="";
          var rtnShipmentCost=0;
          var packageSaveCheck="";
          var rtnShipMethodName="";
          var units="";
          var upsPkging="";
          var delConfirm="";
          var declaredVal="";
          var codType="";
          var codFundsCode="";
          var codCurrCode="";
          var upsSurCharge="";
          var rtnDeclaredValue=0.0;
    
          var halPhoneNum= "";
          var halLine1="";
          var halLine2="";
          var halCity= "";
          var halState= "";
          var halZip=""; 
          var hal= ""; 
          
          var hazMatFlag = "";
          var hazMatType = "";
          var hazMatClass = "";
          var hazMatCharges = 0.0;
          
          var hazMatQty = 0.0;
          var hazMatUnit ="";
    
           var hazMatIdNo ="";
           var hazMatPkgGroup ="";
           var hazMatDOTLabel ="";
           var hazMatEmerContactNo ="";
           var hazMatEmerContactName ="";
           var hazMatId ="";
           
           var HazMatPackagingCnt = "";
           var HazMatPackagingUnits = "";
           var HazMatTechnicalName = "";
           
          var HazMatSignatureName = "";     
          var HazMatPackInstructions = "";     
                 
          length =document.getElementById('packageLengthID'+num1).value;
          
          width =document.getElementById('packageWidthID'+num1).value;
          
          height =document.getElementById('packageHeightID'+num1).value;
           
          packageDeclaredValue=document.getElementById('packageDeclaredValueID'+num1).value;
    
          signatureOption=document.getElementById('signatureOptionID'+num1).value;
    
          returnShipment=document.getElementById('returnShipmentID'+num1).value;
             
           if(shipValidation()==false)
           {
                return;// false;
          }
              rtnShipFromCompany=document.getElementById('rtnShipFromCompanyID'+num1).value;
              
              rtnShipToCompany=document.getElementById('rtnShipToCompanyID'+num1).value;
              
              rtnShipFromContact=document.getElementById('rtnShipFromContactID'+num1).value;
              
              rtnShipToContact=document.getElementById('rtnShipToContactID'+num1).value;
              
              rtnShipFromLine1=document.getElementById('rtnShipFromLine1ID'+num1).value;
              
              rtnShipToLine1=document.getElementById('rtnShipToLine1ID'+num1).value;
              
              rtnShipFromLine2=document.getElementById('rtnShipFromLine2ID'+num1).value;
              
              rtnShipToLine2=document.getElementById('rtnShipToLine2ID'+num1).value;
              rtnCountrySymbol=document.getElementById('rtnCountrySymbolID'+num1).value;
              rtnShipFromCity=document.getElementById('rtnShipFromCityID'+num1).value;
              rtnShipFromSate=document.getElementById('rtnShipFromSateID'+num1).value;
              rtnShipFromZip=document.getElementById('rtnShipFromZipID'+num1).value;
              rtnShipToCity=document.getElementById('rtnShipToCityID'+num1).value;
              rtnShipToState=document.getElementById('rtnShipToStateID'+num1).value;
              rtnShipToZip=document.getElementById('rtnShipToZipID'+num1).value;
              rtnShipFromPhone=document.getElementById('rtnShipFromPhoneID'+num1).value;
              rtnShipToPhone=document.getElementById('rtnShipToPhoneID'+num1).value;
              rtnShipMethodName=document.getElementById('rtnShipMethodNameID'+num1).value;
              rtnShipMethod=document.getElementById('rtnShipMethodID'+num1).value;
              returnDescription=document.getElementById('returnDescriptionID'+num1).value;
              rtnDropOfType=document.getElementById('dropOftypeId').value;
              rtnPackageList=document.getElementById("packagesId").value;
              rtnPayMethod=document.getElementById('rtnPayMethodID'+num1).value;  
             rtnPayMethodCode =document.getElementById('rtnPayMethodCodeID'+num1).value;//SC_7.0_GVK
              rtnACNumber=document.getElementById("carrierAccountNumberId").value;
              rtnDeclaredValue=document.getElementById('rtnDeclaredValueID'+num1).value;
              rtnRMA=document.getElementById('rtnRMAID'+num1).value;
              halPhoneNum= document.getElementById('halPhoneID'+num1).value;
              halLine1= document.getElementById('halAddrLine1ID'+num1).value;
              halLine2= document.getElementById('halAddrLine2ID'+num1).value;
              halCity= document.getElementById('halCityID'+num1).value;
              halState= document.getElementById('halStateID'+num1).value;
              halZip= document.getElementById('halZipID'+num1).value;
               hal =document.getElementById('holdAtLocationID'+num1).value;
              
              hazMatFlag = document.getElementById('HazMatFlagID'+num1).value;
              hazMatType = document.getElementById('HazMatTypeID'+num1).value;
              hazMatClass = document.getElementById('HazMatClassID'+num1).value;
              
              hazMatQty = document.getElementById('HazMatQtyID'+num1).value;
              hazMatUnit = document.getElementById('HazMatUnitID'+num1).value;
              
                        
              hazMatIdNo = document.getElementById('HazMatIdNoID'+num1).value;
              hazMatPkgGroup = document.getElementById('HazMatPkgGroupID'+num1).value;
              hazMatDOTLabel = document.getElementById('HazMatDOTLabelID'+num1).value;
              hazMatEmerContactNo = document.getElementById('HazMatEmerContactNoID'+num1).value;
              hazMatEmerContactName = document.getElementById('HazMatEmerContactNameID'+num1).value;
              hazMatId = document.getElementById('HazMatIdID'+num1).value;
              
              HazMatPackagingCnt = document.getElementById('HazMatPackagingCntID'+num1).value;
              HazMatPackagingUnits = document.getElementById('HazMatPackagingUnitsID'+num1).value;
              HazMatTechnicalName = document.getElementById('HazMatTechnicalNameID'+num1).value;
              
              HazMatSignatureName = document.getElementById('HazMatSignatureNameID'+num1).value;
              HazMatPackInstructions = document.getElementById('HazMatPackInstructionsID'+num1).value;
              WtValue=document.getElementById('weightID'+num1).value;
    
              WtValue=trim(WtValue);
    
              lineno=document.getElementById('lineNoID'+num1).value;
              
              shippedQty=document.getElementById('shippedQtyID'+num1).value;
    
              weight=document.getElementById('weightID'+num1).value;
    
              uom=document.getElementById('uomID'+num1).value;
            
          var packageDeclaredValueChk=document.getElementById('packageDeclaredValueID'+num1).value;
          var position=parseInt(packageDeclaredValueChk.indexOf("."));
          
          var decLen=parseInt(packageDeclaredValueChk.length);
         
          var subdeclare=packageDeclaredValueChk.substring(position+1,decLen);
          
          var subdeclarelen=parseInt(subdeclare.length);
          
          
          if(document.getElementById('chCODID'+num1).value=="Y")
          {
             codFlag ="CHECKED";
    
             codAmt = document.getElementById('codAmtID'+num1).value;
          
          }
          
          var linecnt=document.DynaShipmentShipSaveForm.txtPacCnt.value;
          
         var dryIceUnits=document.getElementById('dryIceUnitsID'+num1).value;
         var chDryIce=document.getElementById('chDryIceID'+num1).value;
         var dryIceWeight=document.getElementById('dryIceWeightID'+num1).value;
          
           if(WtValue== 0 || isNaN(WtValue))
           {
             
           }
            if(isInteger(linecnt)){   
    
                    for (var i = 1; i <=linecnt; i++) 
                    {
                        var shipMethod=document.DynaShipmentShipSaveForm.ajaxcarrierservicelevel.value;  
                        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
                        var paking;
                        var upsPkging=trim(document.getElementById('pkgingID'+num1).value);
                        
                        if(str.substring(0,3)=="100")
                        {
                            upsPkging=trim(document.getElementById('pkgingID'+num1).value);
                            delConfirm=document.getElementById('delConfirmID'+num1).value;
                            packageDeclaredValue=document.getElementById('packageDeclaredValueID'+num1).value;
                            codType=document.getElementById('codTypeID'+num1).value;
                            codFundsCode=document.getElementById('codFundsCodeID'+num1).value;
                            codCurrCode=document.getElementById('codCurrCodeID'+num1).value;
                             LargePackage=trim(document.getElementById('LargePackageID'+num1).value);
                             AdditionalHandling=trim(document.getElementById('AdditionalHandlingID'+num1).value);
                        }
                        
                         if(WtValue!="" && WtValue!=0 && upsPkging!=1 )
                         {
                         if(isRightCurveBracket(document.getElementById('lineNoID'+num1))){ // condition for handling '>' symbol
                            num1=num1 + 1;
    
                            var x1=document.getElementById('PacTab').rows.length-2;
                            
    
                            document.getElementById("countPacketsID").value =num1;
    
                            var x=document.getElementById('PacTab').insertRow(x1);
    
                            var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
           
    
                            var y1=x.insertCell(0);
                            var y2=x.insertCell(1);
                            var y3=x.insertCell(2);
                            var y4=x.insertCell(3);
                            var y5=x.insertCell(4);
                            var y6=x.insertCell(5);
                            var y7=x.insertCell(6);
                            var y8=x.insertCell(7);
                            var y9=x.insertCell(8);
    //                        var y10=x.insertCell(9);
    //                        var y11=x.insertCell(10);
                            
                            y1.className ="innertablecss";
                            y2.className ="innertablecss";
                            y3.className ="innertablecss";
                            y4.className ="innertablecss";
                            y5.className ="innertablecss";
                            y6.className ="innertablecss";
                            y7.className ="innertablecss";
                            y8.className ="innertablecss";
                            y9.className ="innertablecss";
                            
                              y1.align ="center";
                                y2.align ="center";
                                  y3.align ="center";
                                    y4.align ="center";
                                      y5.align ="center";
                                        y6.align ="center";
                                          y7.align ="center";
                                            y8.align ="center";
                                              y9.align ="center";
                           
    //                        y10.className ="innertablecss";
    //                        y11.className ="innertablecss";
    
                            var slno=num1;
                           if(codFlag =="CHECKED")
                           {
                                y1.innerHTML='<input type="hidden" name="signatureOption'+num1+'" id="signatureOptionID'+num1+'" value="'+signatureOption+'"  ><input type="hidden" name="returnShipment'+num1+'" id="returnShipmentID'+num1+'" value="'+returnShipment+'"  >'+
                                       '<input type="hidden" name="PackageSurcharge'+num1+'" id="PackageSurchargeID'+num1+'" value="'+PackageSurcharge+'"  ><input name="hideDID'+num1+'" id="hideDIDID'+num1+'" type="hidden" value="0" ><div align="left" ><input name="chkCOD'+num1+'" id="chkCODID'+num1+'" type="hidden" value="N"> <b><center>'+ slno+'</center></div>'+
                                       '<input type="hidden" name="pkging'+num1+'"  id="pkgingID'+num1+'" value="'+upsPkging+'"><input type="hidden" name="delConfirm'+num1+'" id="delConfirmID'+num1+'" value="'+delConfirm+'"><input type="hidden" name="codType'+num1+'" id="codTypeID'+num1+'" value="'+codType+'"><input name="codFundsCode'+num1+'" id="codFundsCodeID'+num1+'" type="hidden" value="'+codFundsCode+'">'+
                                       '<input name="codCurrCode'+num1+'" id="codCurrCodeID'+num1+'" type="hidden" value="'+codCurrCode+'"><input type="hidden" name="upsSurCharge'+num1+'"  id="upsSurChargeID'+num1+'" value="'+upsSurCharge+'">'+' <input type="hidden" name="chCOD'+num1+'" id="chCODID'+num1+'" value = "Y" id="'+num1+'"  ><input type="hidden" name="codAmt'+num1+'" id="codAmtID'+num1+'"value="'+codAmt+'"  ><input type="hidden" name="halPhone'+num1+'" id="halPhoneID'+num1+'" value="'+halPhoneNum+'"  >'+
                                       '<input type="hidden" name="HazMatFlag'+num1+'"  id="HazMatFlagID'+num1+'" value="'+hazMatFlag+'"><input type="hidden" name="HazMatType'+num1+'" id="HazMatTypeID'+num1+'" value="'+hazMatType+'"><input type="hidden" name="HazMatClass'+num1+'"  id="HazMatClassID'+num1+'" value="'+hazMatClass+'"><input type="hidden" name="HazMatCharges'+num1+'" id="HazMatChargesID'+num1+'" value="'+hazMatCharges+'"><input type="hidden" name="HazMatQty'+num1+'" id="HazMatQtyID'+num1+'" value="'+hazMatQty+'"><input type="hidden" name="HazMatUnit'+num1+'" id="HazMatUnitID'+num1+'"  value="'+hazMatUnit+'"> <input type="hidden" name="HazMatIdNo'+num1+'"  id="HazMatIdNoID'+num1+'" value="'+hazMatIdNo+'"><input type="hidden" name="HazMatPkgGroup'+num1+'" id="HazMatPkgGroupID'+num1+'" value="'+hazMatPkgGroup+'"><input type="hidden" name="HazMatDOTLabel'+num1+'" id="HazMatDOTLabelID'+num1+'" value="'+hazMatDOTLabel+'"><input type="hidden" name="HazMatEmerContactNo'+num1+'" id="HazMatEmerContactNoID'+num1+'" value="'+hazMatEmerContactNo+'"><input type="hidden" name="HazMatEmerContactName'+num1+'" id="HazMatEmerContactNameID'+num1+'" value="'+hazMatEmerContactName+'"><input type="hidden" name="HazMatId'+num1+'" id="HazMatIdID'+num1+'" value="'+hazMatId+'">'+
                                       '<input name="HazMatPackagingCnt'+num1+'" id="HazMatPackagingCntID'+num1+'" type="hidden" value="'+HazMatPackagingCnt+'">'+
                                       '<input name="HazMatPackagingUnits'+num1+'" id="HazMatPackagingUnitsID'+num1+'" type="hidden" value="'+HazMatPackagingUnits+'">'+
                                       '<input name="HazMatTechnicalName'+num1+'" id="HazMatTechnicalNameID'+num1+'" type="hidden" value="'+HazMatTechnicalName+'">'+
                                       '<input name="HazMatSignatureName'+num1+'" id="HazMatSignatureNameID'+num1+'" type="hidden" value="'+HazMatSignatureName+'">'+
                                       '<input name="HazMatPackInstructions'+num1+'" id="HazMatPackInstructionsID'+num1+'" type="hidden" value="'+HazMatPackInstructions+'">'+
                                       '<input type="hidden" name="halAddrLine1'+num1+'" id="halAddrLine1ID'+num1+'" value="'+halLine1+'"  ><input type="hidden" name="halAddrLine2'+num1+'" id="halAddrLine2ID'+num1+'" value="'+halLine2+'"  ><input type="hidden" name="halCity'+num1+'" id="halCityID'+num1+'" value="'+halCity+'"  ><input type="hidden" name="halState'+num1+'" id="halStateID'+num1+'" value="'+halState+'"  ><input type="hidden" name="halZip'+num1+'"  id="halZipID'+num1+'"  value="'+halZip+'"  ><input type="hidden" name="holdAtLocation'+num1+'" id="holdAtLocationID'+num1+'" value="'+hal+'"  >'+
                                       '<input name="rtnShipFromCompany'+num1+'" id="rtnShipFromCompanyID'+num1+'" type="hidden" value="'+rtnShipFromCompany+'"><input type="hidden" name="rtnShipToCompany'+num1+'" id="rtnShipToCompanyID'+num1+'" value="'+rtnShipToCompany+'">'+' <input type="hidden" name="rtnShipFromContact'+num1+'" id="rtnShipFromContactID'+num1+'"  value = "'+rtnShipFromContact+'"   ><input type="hidden" name="rtnShipToContact'+num1+'"  id="rtnShipToContactID'+num1+'"  value="'+rtnShipToContact+'"  >'+
                                       '<input name="rtnShipFromLine1'+num1+'" id="rtnShipFromLine1ID'+num1+'" type="hidden" value="'+rtnShipFromLine1+'"><input type="hidden" name="rtnShipToLine1'+num1+'" id="rtnShipToLine1ID'+num1+'" value="'+rtnShipToLine1+'">'+' <input type="hidden" name="rtnShipFromLine2'+num1+'"  id="rtnShipFromLine2ID'+num1+'" value = "'+rtnShipFromLine2+'" ><input type="hidden" name="rtnShipToLine2'+num1+'" id="rtnShipToLine2ID'+num1+'" value="'+rtnShipToLine2+'"  >'+
                                       '<input name="rtnShipFromCity'+num1+'"  id="rtnShipFromCityID'+num1+'" type="hidden" value="'+rtnShipFromCity+'"><input type="hidden" name="rtnShipFromSate'+num1+'" id="rtnShipFromSateID'+num1+'" value="'+rtnShipFromSate+'">'+' <input type="hidden" name="rtnShipFromZip'+num1+'" id="rtnShipFromZipID'+num1+'" value = "'+rtnShipFromZip+'"   ><input type="hidden" name="rtnShipToCity'+num1+'" id="rtnShipToCityID'+num1+'" value="'+rtnShipToCity+'"  >'+
                                       '<input name="rtnShipToState'+num1+'"  id="rtnShipToStateID'+num1+'"  type="hidden" value="'+rtnShipToState+'"><input type="hidden" name="rtnShipToZip'+num1+'" id="rtnShipToZipID'+num1+'" value="'+rtnShipToZip+'">'+' <input type="hidden" name="rtnShipFromPhone'+num1+'" id="rtnShipFromPhoneID'+num1+'" value = "'+rtnShipFromPhone+'" ><input type="hidden" name="rtnShipToPhone'+num1+'" id="rtnShipToPhoneID'+num1+'" value="'+rtnShipToPhone+'"  >'+
                                       '<input name="rtnShipMethod'+num1+'"  id="rtnShipMethodID'+num1+'"  type="hidden" value="'+rtnShipMethod+'"><input type="hidden" name="rtnDropOfType'+num1+'" id="rtnDropOfTypeID'+num1+'"  value="'+rtnDropOfType+'">'+' <input type="hidden" name="rtnPackageList'+num1+'"  id="rtnPackageListID'+num1+'" value = "'+rtnPackageList+'"  ><input type="hidden" name="rtnPayMethod'+num1+'" id="rtnPayMethodID'+num1+'" value="'+rtnPayMethod+'"  >'+
                                       '<input name="returnDescription'+num1+'"  id="returnDescriptionID'+num1+'"  type="hidden" value="'+returnDescription+'">'+
                                       '<input name="rtnCountrySymbol'+num1+'"  id="rtnCountrySymbolID'+num1+'"  type="hidden" value="'+rtnCountrySymbol+'">'+
                                       '<input name="LargePackage'+num1+'"  id="LargePackageID'+num1+'"  type="hidden" value="'+LargePackage+'">'+
                                       '<input name="AdditionalHandling'+num1+'"  id="AdditionalHandlingID'+num1+'"  type="hidden" value="'+AdditionalHandling+'">'+
                                       '<input name="rtnACNumber'+num1+'"  id="rtnACNumberID'+num1+'"  type="hidden" value="'+rtnACNumber+'"><input type="hidden" name="rtnRMA'+num1+'"  id="rtnRMAID'+num1+'" value="'+rtnRMA+'"><input type="hidden" name="rtnTrackingNumber'+num1+'" id="rtnTrackingNumberID'+num1+'" value="'+rtnTrackingNumber +'"><input type="hidden" name="rtnShipmentCost'+num1+'" id="rtnShipmentCostID'+num1+'" value="'+rtnShipmentCost +'">'+
                                       '<input name="packageSaveCheck'+num1+'" id="packageSaveCheckID'+num1+'"  type="hidden"><input type="hidden" name="rtnShipMethodName'+num1+'" id="rtnShipMethodNameID'+num1+'"><input type="hidden" name="rtnPayMethodCode'+num1+'" id="rtnPayMethodCodeID'+num1+'" ><input type="hidden" name="rtnDeclaredValue'+num1+'"  id="rtnDeclaredValueID'+num1+'"  value="'+rtnDeclaredValue+'">'+
                                       '<input type="hidden" name="chDryIce'+slno+'" id="chDryIceID'+slno+'"  value="'+chDryIce+'" >'+
                                         '<input type="hidden"  name="dryIceUnits'+slno+'" id="dryIceUnitsID'+slno+'"  value="'+dryIceUnits+'" >'+
                                         '<input type="hidden"  name="dryIceWeight'+slno+'" id="dryIceWeightID'+slno+'"  value="'+dryIceWeight+'" >';
                                      
                           }
                           else
                           {
                               y1.innerHTML='<input type="hidden" name="signatureOption'+num1+'" id="signatureOptionID'+num1+'" value="'+signatureOption+'"  ><input type="hidden" name="returnShipment'+num1+'" id="returnShipmentID'+num1+'" value="'+returnShipment+'"  >'+
                                       '<input type="hidden" name="PackageSurcharge'+num1+'" id="PackageSurchargeID'+num1+'" value="'+PackageSurcharge+'"  ><input name="hideDID'+num1+'" id="hideDIDID'+num1+'" type="hidden" value="0" ><div align="left" ><input name="chkCOD'+num1+'" id="chkCODID'+num1+'" type="hidden" value="N"> <b><center>'+ slno+'</center></div>'+
                                       '<input type="hidden" name="pkging'+num1+'"  id="pkgingID'+num1+'" value="'+upsPkging+'"><input type="hidden" name="delConfirm'+num1+'" id="delConfirmID'+num1+'" value="'+delConfirm+'"><input type="hidden" name="codType'+num1+'" id="codTypeID'+num1+'" value="'+codType+'"><input name="codFundsCode'+num1+'" id="codFundsCodeID'+num1+'" type="hidden" value="'+codFundsCode+'">'+
                                       '<input name="codCurrCode'+num1+'" id="codCurrCodeID'+num1+'" type="hidden" value="'+codCurrCode+'"><input type="hidden" name="upsSurCharge'+num1+'"  id="upsSurChargeID'+num1+'" value="'+upsSurCharge+'">'+' <input type="hidden" name="chCOD'+num1+'" id="chCODID'+num1+'" value = "N" id="'+num1+'"  ><input type="hidden" name="codAmt'+num1+'" id="codAmtID'+num1+'"value=""  ><input type="hidden" name="halPhone'+num1+'" id="halPhoneID'+num1+'" value="'+halPhoneNum+'"  >'+
                                       '<input type="hidden" name="HazMatFlag'+num1+'"  id="HazMatFlagID'+num1+'" value="'+hazMatFlag+'"><input type="hidden" name="HazMatType'+num1+'" id="HazMatTypeID'+num1+'" value="'+hazMatType+'"><input type="hidden" name="HazMatClass'+num1+'"  id="HazMatClassID'+num1+'" value="'+hazMatClass+'"><input type="hidden" name="HazMatCharges'+num1+'" id="HazMatChargesID'+num1+'" value="'+hazMatCharges+'"><input type="hidden" name="HazMatQty'+num1+'" id="HazMatQtyID'+num1+'" value="'+hazMatQty+'"><input type="hidden" name="HazMatUnit'+num1+'" id="HazMatUnitID'+num1+'"  value="'+hazMatUnit+'"> <input type="hidden" name="HazMatIdNo'+num1+'"  id="HazMatIdNoID'+num1+'" value="'+hazMatIdNo+'"><input type="hidden" name="HazMatPkgGroup'+num1+'" id="HazMatPkgGroupID'+num1+'" value="'+hazMatPkgGroup+'"><input type="hidden" name="HazMatDOTLabel'+num1+'" id="HazMatDOTLabelID'+num1+'" value="'+hazMatDOTLabel+'"><input type="hidden" name="HazMatEmerContactNo'+num1+'" id="HazMatEmerContactNoID'+num1+'" value="'+hazMatEmerContactNo+'"><input type="hidden" name="HazMatEmerContactName'+num1+'" id="HazMatEmerContactNameID'+num1+'" value="'+hazMatEmerContactName+'"><input type="hidden" name="HazMatId'+num1+'" id="HazMatIdID'+num1+'" value="'+hazMatId+'">'+
                                       '<input name="HazMatPackagingCnt'+num1+'" id="HazMatPackagingCntID'+num1+'" type="hidden" value="'+HazMatPackagingCnt+'">'+
                                       '<input name="HazMatPackagingUnits'+num1+'" id="HazMatPackagingUnitsID'+num1+'" type="hidden" value="'+HazMatPackagingUnits+'">'+
                                       '<input name="HazMatTechnicalName'+num1+'" id="HazMatTechnicalNameID'+num1+'" type="hidden" value="'+HazMatTechnicalName+'">'+
                                       '<input name="HazMatSignatureName'+num1+'" id="HazMatSignatureNameID'+num1+'" type="hidden" value="'+HazMatSignatureName+'">'+
                                       '<input name="HazMatPackInstructions'+num1+'" id="HazMatPackInstructionsID'+num1+'" type="hidden" value="'+HazMatPackInstructions+'">'+
                                       '<input type="hidden" name="halAddrLine1'+num1+'" id="halAddrLine1ID'+num1+'" value="'+halLine1+'"  ><input type="hidden" name="halAddrLine2'+num1+'" id="halAddrLine2ID'+num1+'" value="'+halLine2+'"  ><input type="hidden" name="halCity'+num1+'" id="halCityID'+num1+'" value="'+halCity+'"  ><input type="hidden" name="halState'+num1+'" id="halStateID'+num1+'" value="'+halState+'"  ><input type="hidden" name="halZip'+num1+'"  id="halZipID'+num1+'"  value="'+halZip+'"  ><input type="hidden" name="holdAtLocation'+num1+'" id="holdAtLocationID'+num1+'" value="'+hal+'"  >'+
                                       '<input name="rtnShipFromCompany'+num1+'" id="rtnShipFromCompanyID'+num1+'" type="hidden" value="'+rtnShipFromCompany+'"><input type="hidden" name="rtnShipToCompany'+num1+'" id="rtnShipToCompanyID'+num1+'" value="'+rtnShipToCompany+'">'+' <input type="hidden" name="rtnShipFromContact'+num1+'" id="rtnShipFromContactID'+num1+'"  value = "'+rtnShipFromContact+'"   ><input type="hidden" name="rtnShipToContact'+num1+'"  id="rtnShipToContactID'+num1+'"  value="'+rtnShipToContact+'"  >'+
                                       '<input name="rtnShipFromLine1'+num1+'" id="rtnShipFromLine1ID'+num1+'" type="hidden" value="'+rtnShipFromLine1+'"><input type="hidden" name="rtnShipToLine1'+num1+'" id="rtnShipToLine1ID'+num1+'" value="'+rtnShipToLine1+'">'+' <input type="hidden" name="rtnShipFromLine2'+num1+'"  id="rtnShipFromLine2ID'+num1+'" value = "'+rtnShipFromLine2+'" ><input type="hidden" name="rtnShipToLine2'+num1+'" id="rtnShipToLine2ID'+num1+'" value="'+rtnShipToLine2+'"  >'+
                                       '<input name="rtnShipFromCity'+num1+'"  id="rtnShipFromCityID'+num1+'" type="hidden" value="'+rtnShipFromCity+'"><input type="hidden" name="rtnShipFromSate'+num1+'" id="rtnShipFromSateID'+num1+'" value="'+rtnShipFromSate+'">'+' <input type="hidden" name="rtnShipFromZip'+num1+'" id="rtnShipFromZipID'+num1+'" value = "'+rtnShipFromZip+'"   ><input type="hidden" name="rtnShipToCity'+num1+'" id="rtnShipToCityID'+num1+'" value="'+rtnShipToCity+'"  >'+
                                       '<input name="rtnShipToState'+num1+'"  id="rtnShipToStateID'+num1+'"  type="hidden" value="'+rtnShipToState+'"><input type="hidden" name="rtnShipToZip'+num1+'" id="rtnShipToZipID'+num1+'" value="'+rtnShipToZip+'">'+' <input type="hidden" name="rtnShipFromPhone'+num1+'" id="rtnShipFromPhoneID'+num1+'" value = "'+rtnShipFromPhone+'" ><input type="hidden" name="rtnShipToPhone'+num1+'" id="rtnShipToPhoneID'+num1+'" value="'+rtnShipToPhone+'"  >'+
                                       '<input name="rtnShipMethod'+num1+'"  id="rtnShipMethodID'+num1+'"  type="hidden" value="'+rtnShipMethod+'"><input type="hidden" name="rtnDropOfType'+num1+'" id="rtnDropOfTypeID'+num1+'"  value="'+rtnDropOfType+'">'+' <input type="hidden" name="rtnPackageList'+num1+'"  id="rtnPackageListID'+num1+'" value = "'+rtnPackageList+'"  ><input type="hidden" name="rtnPayMethod'+num1+'" id="rtnPayMethodID'+num1+'" value="'+rtnPayMethod+'"  >' +
                                       '<input name="returnDescription'+num1+'"  id="returnDescriptionID'+num1+'"  type="hidden" value="'+returnDescription+'">'+
                                       '<input name="rtnCountrySymbol'+num1+'"  id="rtnCountrySymbolID'+num1+'"  type="hidden" value="'+rtnCountrySymbol+'">'+
                                       '<input name="LargePackage'+num1+'"  id="LargePackageID'+num1+'"  type="hidden" value="'+LargePackage+'">'+
                                       '<input name="AdditionalHandling'+num1+'"  id="AdditionalHandlingID'+num1+'"  type="hidden" value="'+AdditionalHandling+'">'+
                                       '<input name="rtnACNumber'+num1+'"  id="rtnACNumberID'+num1+'"  type="hidden" value="'+rtnACNumber+'"><input type="hidden" name="rtnRMA'+num1+'"  id="rtnRMAID'+num1+'" value="'+rtnRMA+'"><input type="hidden" name="rtnTrackingNumber'+num1+'" id="rtnTrackingNumberID'+num1+'" value="'+rtnTrackingNumber +'"><input type="hidden" name="rtnShipmentCost'+num1+'" id="rtnShipmentCostID'+num1+'" value="'+rtnShipmentCost +'">'+
                                       '<input name="packageSaveCheck'+num1+'" id="packageSaveCheckID'+num1+'"  type="hidden"><input type="hidden" name="rtnShipMethodName'+num1+'" id="rtnShipMethodNameID'+num1+'"><input type="hidden" name="rtnPayMethodCode'+num1+'" id="rtnPayMethodCodeID'+num1+'" ><input type="hidden" name="rtnDeclaredValue'+num1+'"  id="rtnDeclaredValueID'+num1+'"  value="'+rtnDeclaredValue+'">'+
                                       '<input type="hidden" name="chDryIce'+slno+'" id="chDryIceID'+slno+'"  value="'+chDryIce+'" >'+
                                         '<input type="hidden"  name="dryIceUnits'+slno+'" id="dryIceUnitsID'+slno+'"  value="'+dryIceUnits+'" >'+
                                         '<input type="hidden"  name="dryIceWeight'+slno+'" id="dryIceWeightID'+slno+'"  value="'+dryIceWeight+'" >';
                                      
                            }
                            
                            // Below code Added for get rates
                            
                            y1.innerHTML = y1.innerHTML + 
                            '<input type="hidden" name="dimUOM'+num1+'" id="dimUOMId'+num1+'" >'+
                            '<input type="hidden" name="dimValueFreight'+num1+'" id="dimValueId'+num1+'" >'+
                            '<input type="hidden" name="weightUOM'+num1+'" id="weightUOMId'+num1+'" >'+
                            '<input name="weightValue'+num1+'" id="weightValueId'+num1+'" type="hidden" >';
                            
                            // Above code Added for get rates 
                            
                            y2.innerHTML='<input type="hidden" name="PackageShipmentCost'+num1+'" id="PackageShipmentCostID'+num1+'" value="'+PackageShipmentCost+'"  > <input name="chVoid'+num1+'" id="chVoidID'+num1+'" type="checkbox" maxlength="25" size="3" class="inputsPackage" disabled><input name="chVoidHidden'+num1+'" id="chVoidHiddenID'+num1+'" type="hidden"> '+
                            ' <input type="hidden" name="lineNo'+num1+'" id="lineNoID'+num1+'" value="'+lineno+'"  style="width:109px; height: 18px;" cols="45" rows="1" class="inputsPackage" >'+lineno+'</textarea>'+
                            '<input  type="hidden" name="shippedQty'+num1+'" id="shippedQtyID'+num1+'" value="'+shippedQty+'" type="text"    maxlength="5"  size="5" onBlur="" class="inputsPackage">';
                            
                          /*  y3.innerHTML =' <input type="hidden" name="lineNo'+num1+'" id="lineNoID'+num1+'" value="'+lineno+'"  style="width:109px; height: 18px;" cols="45" rows="1" class="inputsPackage" >'+lineno+'</textarea>';
                            
                            y4.innerHTML='<input  type="hidden" name="shippedQty'+num1+'" id="shippedQtyID'+num1+'" value="'+shippedQty+'" type="text"    maxlength="5"  size="5" onBlur="" class="inputsPackage">'; */
                             
                            y3.innerHTML='<input name="weight'+num1+'" id="weightID'+num1+'" value="'+weight+'" type="text" onfocus="openStramWeighingScale('+num1+')" maxlength="5"  size="5" onBlur="closingStram();totalWeight()" class="inputsPackage">';
                             
                            y4.innerHTML='<input name="uom'+num1+'"  id="uomID'+num1+'" value="'+uom+'" type="text" maxlength="3" size="3" class="inputsPackage">';
    
                            y5.innerHTML='<input name="trackingNumber'+num1+'"  id="trackingNumberID'+num1+'"  readonly value="'+trackno+'" maxlength="25" type="text"  size="40" class="inputsPackage">';
    
                            y6.innerHTML= '<select name ="dimensionName'+num1+'" id ="dimensionNameID'+num1+'"  class="inputsPackage" >'+ optTag + ' </select> <input type="BUTTON"  name="dimButton'+num1+'"  id="dimButtonID'+num1+'"  value="^" class="inputsPackage" style="width:20px" onclick=openDimPopup('+num1+') >'+
                                    //  <input type="BUTTON" name="dimVal'+num1+'" value="^" onclick=dimensionPopUp('+num1+') >' +
                            '<input  name ="packageLength'+num1+'"  id ="packageLengthID'+num1+'" type = "hidden" class="inputsPackage"  value="'+length+'"  size="1"  maxlength="4" onKeyPress="checkForFloat()" onblur="checkUnits(this)" > <input  name ="packageWidth'+num1+'" type = "hidden" class="inputsPackage"    id ="packageWidthID'+num1+'"  value="'+width+'"  size="1"  maxlength="4" onKeyPress="checkForFloat()" onblur="checkUnits(this)" >    <input  name ="packageHeight'+num1+'" type = "hidden" class="inputsPackage"   id ="packageHeightID'+num1+'"  onKeyPress="checkForFloat()"  value="'+height+'" size="1"  maxlength="4" onblur="checkUnits(this)" > <input name ="dimensionNameHidden'+num1+'"  id ="dimensionNameHiddenID'+num1+'" type = "hidden" value="" > <input name ="dimensionValueHidden'+num1+'" id ="dimensionValueHiddenID'+num1+'" type = "hidden" value="" ><input name ="defaultDimeName'+num1+'" id ="defaultDimeNameID'+num1+'"  type = "hidden" value="'+defaultDimeNameVar+'" >';
             
              
                             if(str.substring(0,3)=="111" || str.substring(0,3)=="110"|| (str.substring(0,3)=="100"))
                              {
                                    y9.innerHTML= '<a href="#" onClick=openPackPopup('+num1+')><img name="packOpt'+num1+'" id="packOptId'+num1+'" alt="" src="images/PK.png" align="center" title="Package Details"  align="center" width="40px" height="30px" border=0></a>'; 
                              }
                              else
                              {
                                    y9.innerHTML= '<a href="#" onClick=openPackPopup('+num1+')><img name="packOpt'+num1+'" id="packOptId'+num1+'" alt=""  src="images/PK.png" align="center" title="Package Details"  align="center" width="40px" height="30px" border=0></a>'; 
                              }
                       
                              y7.innerHTML='<input type="text" class="inputsPackage" name="packageDeclaredValue'+num1+'"  id="packageDeclaredValueID'+num1+'"  value="'+packageDeclaredValue+'" size="8"  onblur="javascript:packageDeclaredValuefn('+num1+')"  id='+num1+' maxlength="11"  >';
                  
                              y8.innerHTML= ' <label style="font-weight:bold; font-size:12px" > USD </label>'+ '<input type="hidden" name="declaredCurrCode'+num1+'"  id="declaredCurrCodeID'+num1+'" class="inputsPackage" value="USD"/>';
    }    
                         }//End of Wt If
                        
                   }//End of for
                   
    
                         totalWeight(); 
                         if(document.getElementById("flagShipID").value =="Y")
                         {
                           document.DynaShipmentShipSaveForm.RemoveButtonHidden.value="";
                         }
                   
             }//End of linecnt If      
             else
             {
                    alert("Enter The No. Of Packages Value Correctly");
        
                    DynaShipmentShipSaveForm.txtPacCnt.focus();
             }
             
    
    return false;
     }//End of function
      
     
     function delRow()
     {
    
        document.getElementById("submitButtonId").value='DELETE';
        
     
         if(trim(document.DynaShipmentShipSaveForm.RemoveButtonHidden.value) == "")
         {
        
              if(document.DynaShipmentShipSaveForm.phoneNumber.readOnly)
              {
                        if(parseInt(document.DynaShipmentShipSaveForm.oldPackCount.value) >= parseInt(document.getElementById("countPacketsID").value))
                        {
                          alert('There is no unshipped package to delete');
                        }
                        else
                        {
                            ConfirmStatus = confirm("Do You Want To Remove The Row ?")
                             if (ConfirmStatus == true) 	
                             {
                                    num=document.getElementById("countPacketsID").value;
    
                                    num1=parseInt(num);
                                    var packCount=num1;
                                    document.getElementById('PacTab').deleteRow(document.getElementById('PacTab').rows.length-3);
                                    var orderNumber = document.getElementById('orderNumberID').value;
                                    if(orderNumber != null && orderNumber != ''){
                                        deleteRowInDB(packCount);
                                    }
                                    num1=num1 - 1;
                                    
                                    document.getElementById("countPacketsID").value=num1;
                             
                             }
        
                          }
                  }
                   else
                   {
                   
                     num=document.getElementById("countPacketsID").value;
        
                     var num1=parseInt(num);
                    
                     if(num1 == 1)
                     {
                        alert('Shipment Order Should Contain Atleast One Package');
                
                     }
                     else
                     {
                        ConfirmStatus = confirm("Do You Want To Remove The Row ?")
        
                        if (ConfirmStatus == true) 	
                        {
                                if (num1 >1) 
                                {
                                    var packCount = num1;
                                    var orderNumber = document.getElementById('orderNumberID').value;
                                    if(orderNumber != null && orderNumber != ''){
                                        deleteRowInDB(packCount);
                                    }
                             //   alert("delete num ::"+parseInt(document.getElementById('PacTab').rows.length)-2);
                                    document.getElementById('PacTab').deleteRow(document.getElementById('PacTab').rows.length-3);
                                    
                                    num1=num1 - 1;
        
                                    document.getElementById("countPacketsID").value=num1;
        
                                }
        
                        }
                    }
                    
                    document.getElementById("countPacketsID").value=num1;
                    totalWeight();
                 }
            }
     } 
      
     function clearRow()
     {
    
               document.getElementById("submitButtonId").value='CLEAR';
        
                if(document.DynaShipmentShipSaveForm.phoneNumber.readOnly)
                {
                        num=document.getElementById("countPacketsID").value;
        
                        var num1=parseInt(num);
                        var packCount = num1;
                        num=num-1;
                        
                        var num2=parseInt(document.DynaShipmentShipSaveForm.HidePacAdd.value);
                       
                        if(parseInt(document.DynaShipmentShipSaveForm.oldPackCount.value) >= parseInt(document.getElementById("countPacketsID").value))
                        {
                            alert('There are no unshipped packages to clear');
                        }
        
                        else
                        {
                            ConfirmStatus = confirm("Do You Want To Clear The Rows ?")
                            if (ConfirmStatus == true) 	
                            {
                                           var orderNumber = document.getElementById('orderNumberID').value;
                                           if(orderNumber != null && orderNumber != ''){
                                                deleteRowInDB(packCount);
                                            }
                                           while (num1 > parseInt(document.DynaShipmentShipSaveForm.oldPackCount.value)) 
                                           {
                                                
                                                document.getElementById('PacTab').deleteRow(document.getElementById('PacTab').rows.length-2);
                
                                                num1=num1 - 1;
                
                                           }
                
                                            document.getElementById("countPacketsID").value=num1;
                                            totalWeight();
                             }
                        
                        }
                }
        
                else
                {
                    num=document.getElementById("countPacketsID").value;
        
                    var num1 = parseInt(num);
        
                    if(num1==1)
                    {
                        alert('Shipment Order Should Contain Atleast One Package');
                    }
        
                    else
                    {
                           ConfirmStatus = confirm("Do You want to Clear all the Rows?")
                    
                            if (ConfirmStatus == true) 
                            {
                                    num=document.getElementById("countPacketsID").value;
        
                                    var num1 = parseInt(num);
                                    var packCount = num1;
                                    var orderNumber = document.getElementById('orderNumberID').value;
                                    if(orderNumber != null && orderNumber != ''){
                                        deleteRowInDB(packCount);
                                    }
                                    while (num1 > 1) 
                                    {
        
                                            document.getElementById('PacTab').deleteRow(document.getElementById('PacTab').rows.length-3);
        
                                            num1=num1 - 1;
                            
                                    }
                            
                                   document.getElementById("countPacketsID").value="1";
                            
                                   document.getElementById("countPacketsID").value =1;
                                   totalWeight();
                            }
                            
                    }
        
                }
      } 
      
    
        
     function packValidate()	
     {
        var currentTime = new Date();
        var month = currentTime.getMonth() + 1;
        var day = currentTime.getDate();
        var year = currentTime.getFullYear();
    
        var str=trim(document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value);
        var upsMode=trim(document.getElementById("upsMode").value);
    
        var num=document.getElementById("countPacketsID").value;
        var num1=parseInt(num);
    
        var i=1;
    
        var y=1;
    
        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    
        var subStr = str.substring(0,2);
    
    //  		if(document.DynaShipmentShipSaveForm.wayBill.readOnly)
    //                {
    //
    //                    i=document.DynaShipmentShipSaveForm.HidePacAdd.value;
    //
    //                    y=parseInt(num);
    //
    //                    y=y-2;
    //
    //                    num1=parseInt(document.DynaShipmentShipSaveForm.HidePacAdd.value);
    //
    //                    num1=num1-1;
    //
    //                    i= parseInt(i);
    //
    //                    if(parseInt(document.DynaShipmentShipSaveForm.HidePacAdd.value)>parseInt(document.getElementById("countPacketsID").value))
    //                    {
    //                            alert("No New Packages To Save");
    //
    //                            return false;
    //                    }
    //
    //		}
            
         num1=num1+1; 
         
         var codCount=0;
         var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
         var paking;
         if(str.substring(0,3)=="111"||str.substring(0,3)=="110" || str.substring(0,3)=="115")
            {     paking=window.document.getElementById("packagesId").options[window.document.getElementById("packagesId").selectedIndex].value;
            }
         var shipMethod=document.DynaShipmentShipSaveForm.ajaxcarrierservicelevel.value;
         var declaredVal;
         var returnShipment;
    
                    while (num1 > y) {
                    
                    
          var dimensionNameHidden=document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].text;           
          document.getElementById('dimensionNameHiddenID'+i).value=dimensionNameHidden;
          var dimensionValueHidden=document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].value;           
          document.getElementById('dimensionValueHiddenID'+i).value=dimensionValueHidden;
             
       if (dimensionNameHidden == "Other" && (dimensionValueHidden == '' || dimensionValueHidden == ""))
       {
       alert("Please Provide Required Dimension Details For Package "+i); 
       return false;   
       }
                    
         var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
         var carrierTest=str.substring(0,3);           
                    
         if(carrierTest == '100')
            { 
          var upsPkgingValue = document.getElementById('pkgingID'+i).value;
    
         if(upsPkgingValue == '' || upsPkgingValue == '02' || upsPkgingValue == null)
                {
                if(dimensionNameHidden == "Standard Dimension")
                {
                alert("Standard Dimension is not allowed for Custom Package in package "+i);
                return false;
                }
                }
             }   
            if(carrierTest == '110' || carrierTest == '111')
            {
            var packageName=document.getElementById("packagesId").options[document.getElementById("packagesId").selectedIndex].value;
            if(packageName == 'YOURPACKAGING')
                {
                 if(dimensionNameHidden == "Standard Dimension")
                {
                alert("Standard Dimension is not allowed for Custom Package in package "+i);
                return false;
                }
                }          
            }
               
        
         declaredVal=document.getElementById('packageDeclaredValueID'+i).value;
            if((paking=="FEDEXENVELOPE" || paking=="FEDEXPAK") && declaredVal >=500 && (str.substring(0,3)=="111"||str.substring(0,3)=="110"))
        {
         alert("Declared value should be less than 500 for FEDEXENVELOPE and FEDEXPAK packagings at package "+i);
         return false;
        }
         returnShipment=document.getElementById('returnShipmentID'+i).value;
          
          uom=document.getElementById('uomID'+i).value;
          if(uom=="" || !( (uom=="LB" ||uom=="lb")  || (uom=="KG" || uom=="kg")))
           {
           if(uom=="OZ" && (carrierTest != 115)){
           alert("Please enter the UOM as LB or KG for package "+i);
           document.DynaShipmentShipSaveForm['uom'+i].focus();
           return false;
           }
           }
           
           if((uom=="KG" || uom=="kg") && carrierTest == 115){
           alert("Please enter the UOM as LB or OZ for package "+i);
           document.getElementById('uomID'+i).focus();
           return false;
       }
        
         var packageDeclaredValueChk=document.getElementById('packageDeclaredValueID'+i).value;
          var position=parseInt(packageDeclaredValueChk.indexOf("."));
              
          
          var decLen=parseInt(packageDeclaredValueChk.length);
          
         
          var subdeclare=packageDeclaredValueChk.substring(position+1,decLen);
         
          
          var subdeclarelen=parseInt(subdeclare.length);
          
        
           if(parseFloat(document.getElementById('packageDeclaredValueID'+i).value) > 9999999.99)
           {
           
           alert('Declared Value Should be Less Than or Equal to 9999999.99 at package '+i);
           return false;
           }
          
           if(subdeclarelen >2 && position >0)
          {
          alert('Please Give  Precision of two digits for The Declare value '+i);
          return false;
          }
                 
        var payMethod1=trim(document.getElementById("carrierPayMethodId").value);
       
    if(document.getElementById('chCODID'+i).value== "Y")
        {
        codCount=codCount+1;
          
        }
    
            var DimValue=trim(document.getElementById('uomID'+i).value);
    
            var lineno=trim(document.getElementById('lineNoID'+i).value);
    
            var itemDescription = trim(document.getElementById('lineNoID'+i).value);		
    
          var linestr = document.getElementById('lineNoID'+i).value;
    
          var WtValue=trim(document.getElementById('weightID'+i).value);     
      
    
          if(document.getElementById("shipmentStatusFlagID").value=='O' && !(parseFloat(trim(WtValue))==0))
          {
    
                                    alert("Weight For The Package Should Be 0 As Quantity Being Shipped Is 0");
    
                                    document.getElementById('weightID'+i).focus();
    
                                    return false;
    
                }
    
          else if((trim(WtValue)=="" || parseFloat(trim(WtValue))<=0 || isNaN(WtValue))){
          if(!(str.substring(0,3)=="100" && upsMode=="UPS Direct"))
                                    {
    
                                    alert("Enter Weight for the Package Correctly for Package "+i);
    
                                    document.getElementById('weightID'+i).focus();
    
                                    return false;
            }
    
                            } 
          
    
                            else if(parseInt(trim(WtValue)) > 150 && str.substring(0,3)=="100" ){	
    
                                    alert("UPS Shipment Cannot Hold Weight More Than 150");
    
                                    document.getElementById('weightID'+i).focus();
    
                                    return false;
    
                            }
    
                    
    
                            if(trim(DimValue)=="" || !isNaN(DimValue)){
    
                                    alert("Enter The UOM For The Package");
    
                                    document.getElementById('uomID'+i).focus();
    
                                    return false;
    
                            }
    
       
    
                    var CODAmt=trim(document.getElementById('codAmtID'+i).value);
    
                            var cod=trim(document.getElementById('chCODID'+i).value);
    
           
             
                            i=i+1;
    
                            num1=num1 - 1;
    
                    }
       
        if(str.substring(0,3)=="100" && upsMode=="UPS Direct")
                       {
                       }
        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
            if(str.substring(0,3)=="111"||str.substring(0,3)=="110")
            {
           
            if(codCount>0)
            {
         if(document.getElementById('chCODID1').value!= "Y")
            {
            alert("Please select the cod check box for the first package");
                    return false;
            }
            
            }
            
            }
            
            var payMethod=document.getElementById("carrierPayMethodId").options[document.getElementById("carrierPayMethodId").selectedIndex].value;
            if((str.substring(0,3)=="110" ||str.substring(0,3)=="111") && (payMethod=="TP" || payMethod=="CG" ) && codCount>0)
            {
            alert("COD is not allowed for FedEx THIRD PARTY BILLING and CONSIGNEE");
            return false;
            }
            
            if((str.substring(0,3)=="111") && payMethod=="FC" && codCount>0)
            {
            alert("COD is not allowed for Pay Method FREIGHT COLLECT");
            return false;
            }
    
    
            }
    
      
      
    function shipValidation()
    {
            var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
            var shipStatusFlag=document.getElementById("shipmentStatusFlagID").value;
            
            var shippedQuantity=0.0; 
            var i=document.getElementById("countPacketsID").value;
            var codCount=0;
            var upsMode=trim(document.getElementById("upsMode").value);
            while(i>0)
             { 
                  var  WtValue=document.getElementById('weightID'+i).value;
                  var  shippedQtyValue=document.getElementById('shippedQtyID'+i).value;
                  var packageDeclaredValueChk=document.getElementById('packageDeclaredValueID'+i).value;
                  var position=parseInt(packageDeclaredValueChk.indexOf("."));
                  var decLen=parseInt(packageDeclaredValueChk.length);
                  var subdeclare=packageDeclaredValueChk.substring(position+1,decLen);
                  var declaredVal=document.getElementById('packageDeclaredValueID'+i).value;
                  var subdeclarelen=parseInt(subdeclare.length);
                  var trackingNumber = document.getElementById('trackingNumberID'+i).value;
                  var uom=document.getElementById('uomID'+i).value;
                  var CODAmt=trim(document.getElementById('codAmtID'+i).value);
                  var cod=document.getElementById('chCODID'+i).value; 
                  var itemDescription = trim(document.getElementById('lineNoID'+i).value);		
                    
                  var dimensionNameHidden=document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].text;           
                  document.getElementById('dimensionNameHiddenID'+i).value=dimensionNameHidden;
                  
                  var dimensionValueHidden=document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].value;           
                  document.getElementById('dimensionValueHiddenID'+i).value=dimensionValueHidden;
                  if (dimensionNameHidden == "Other" && (dimensionValueHidden == '' || dimensionValueHidden == ""))
                  {
                       alert("Please Provide Required Dimension Details For Package "+i); 
                       return false;    
                  }
                    
                    
                  var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
                  var carrierTest=str.substring(0,3);
                    
                  if(carrierTest == '100')
                  {   
                        var upsPkgingValue = document.getElementById('pkgingID'+i).value;
                       
                        if(upsPkgingValue == '' || upsPkgingValue == '02' || upsPkgingValue == null) 
                        {
                            if(dimensionNameHidden == "Standard Dimension")
                            {
                            alert("Standard Dimension is not allowed for Custom Package in package "+i);
                            return false;
                            }
                        }
                  }
                    
                  if(carrierTest == '110' || carrierTest == '111')
                  {
                      var packageName=document.getElementById("packagesId").options[document.getElementById("packagesId").selectedIndex].value;
                      if(packageName == 'YOURPACKAGING')
                      {
                             if(dimensionNameHidden == "Standard Dimension")
                             {
                                alert("Standard Dimension is not allowed for Custom Package in package "+i);
                                return false;
                             }
                      }   
                  }
         
                  
        if ((str.substring(0,3)=="111")||(str.substring(0,3)=="110"))
        {
        if(document.getElementById('HazMatFlagID'+i).value == "Y")
        {
       
           if ((document.getElementById('HazMatTypeID'+i).value == '') ||(document.getElementById('HazMatTypeID'+i).value == "null"))
          {
             alert('Please Select Hazardous Material Type Correctly for Package '+i);
             return false;
          }
        }
        }
        if((str.substring(0,3)=="111")||(str.substring(0,3)=="110"))
        {       
            if(document.getElementById('holdAtLocationID'+i).value == "Y")
            {
           
                if ((document.getElementById('halPhoneID'+i).value == '')||(document.getElementById('halPhoneID'+i).value == "null") ||(document.getElementById('halAddrLine1ID'+i).value == "null") || (document.getElementById('halAddrLine1ID'+i).value == '')
                
                || (document.getElementById('halCityID'+i).value == '')||(document.getElementById('halCityID'+i).value == "null")
               
                || (document.getElementById('halStateID'+i).value == '')||(document.getElementById('halStateID'+i).value == "null")
                
                || (document.getElementById('halZipID'+i).value == '')||(document.getElementById('halZipID'+i).value == "null"))
        
                {
                     alert("Please Save  Hold At Location values in package options popup for Package :"+i);
                    
                     return false;
                }
                        
            }
        }
        if(document.getElementById('returnShipmentID'+i).value == "PRINTRETURNLABEL")
        {
            var rtnShipFromPhone =  document.getElementById('rtnShipFromPhoneID'+i).value;
            var rtnShipToPhone =  document.getElementById('rtnShipToPhoneID'+i).value;
            if(rtnShipFromPhone == "" && trackingNumber == "")
            {
                  alert("Please Enter The Return Ship From Phone Number for the package "+ i);
                  document.getElementById('rtnShipFromPhoneID'+i).readOnly=false;
                  return false;
            }
          
          var mode=trim(document.getElementById("upsMode").value);
                  if(mode != 'ShipExec' ){
            if(rtnShipToPhone == "" && trackingNumber == "")
            {
                alert("Please Enter The Return Ship To Phone Number for the package "+ i);
                document.getElementById('rtnShipToPhoneID'+i).readOnly=false;
                return false;
            }
            }
        }
            
        if(isNaN(declaredVal)  || declaredVal<0)
        {
               alert("Please enter the packageDeclaredValue Correctly");
               document.getElementById('packageDeclaredValueID'+i).focus();
               return false;
        }
       
        if(declaredVal=='')
        {
           document.getElementById('packageDeclaredValueID'+i).value=0;
        }
                 
        if(str.substring(0,3)=="100")
        {
           if(cod=="Y")
           {
                if(trim(CODAmt)=="" || parseFloat(trim(CODAmt))<=0 || isNaN(CODAmt))
                {
                  alert("Please Enter The COD Amount Correctly for Package "+i);
                  return false;
                }
           }
        }  
                       
        if(str.substring(0,3)=="111"||str.substring(0,3)=="110")
        {
             var  paking=window.document.getElementById("packagesId").options[window.document.getElementById("packagesId").selectedIndex].value;
            
             if((paking=="FEDEXENVELOPE" || paking=="FEDEXPAK") && declaredVal >=500 && (str.substring(0,3)=="111"||str.substring(0,3)=="110"))
             {
                 alert("Declared value should be less than 500 for FEDEXENVELOPE and FEDEXPAK packagings at package "+i);
                 return false;
             }
            
             if(cod=="Y")
             {
                    codCount=codCount+1; 
                    if(trim(CODAmt)=="" || parseFloat(trim(CODAmt))<=0 || isNaN(CODAmt))
                    {
                      alert("Please Enter The COD Amount Correctly for Package "+i);
                      return false;                  
                    }
              } 
              if(codCount >0 && document.DynaShipmentShipSaveForm.codAmt1.value <1 )
              {
                  alert("There is no COD for First Package.So COD is not allowed for this Shipment update ");
                  return false;          
              }
        }
        if(uom=="" || !( (uom=="LB" ||uom=="lb")  || (uom=="KG" || uom=="kg")))
        {
        
        if(uom=="OZ" && (carrierTest != 115)){
           alert("Please enter the UOM as LB or KG for package "+i);
           document.getElementById('uomID'+i).focus();
           return false;
           }
        }
        
        if((uom=="KG" || uom=="kg") && carrierTest == 115){
       alert("Please enter the UOM as LB or OZ for package "+i);
       document.getElementById('uomID'+i).focus();
       return false;
       }
       
        if(parseFloat(document.getElementById('packageDeclaredValueID'+i).value) > 9999999.99)
        {
            alert('Declared Value Should be Less Than or Equal to 9999999.99 at package '+i);
            return false;
        }
        if(subdeclarelen >2 && position >0)
        {
           alert('Please Give  Precision of two digits for The Declare value '+i);
           return false;
        }
                  
        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;        
        var carrierTest=str.substring(0,3);
        var dimensionUnits=document.getElementById('dimensionNameID'+i).value;
        var dname = document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].text;
        var dimens= document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].value;
          
        var n=dimens.split('*');
        var length = (n[0])%1;
        var width = (n[1])%1;
        var height = (n[2])%1;
    
        if ( length > 0 && carrierTest !=113)
        {
          alert("Floating numbers are not allowed in dimentions packageLength at package "+i);    
          return false;
        }        
        if ( width > 0 && carrierTest !=113)//if (string.indexOf('.') != -1)
        {
          alert("Floating numbers are not allowed in dimentions packageWidth at package "+i); 
          return false;
        }
        if ( height > 0 && carrierTest !=113)//if (string.indexOf('.') != -1)
        {
          alert("Floating numbers are not allowed in dimentions packageHeight at package "+i);
          return false;
        }
                
        if(isNaN(length)||isNaN(width) ||isNaN(height))
        {
           alert("Please enter the dimensions as numeric values at package "+i);
           return false;
        }
        var totalDimentions=parseInt(length,10)+parseInt(width,10)+parseInt(height,10);
         
        if(isNaN(totalDimentions))
        { 
            alert("Dont Enter null values as the dimension value at package "+i);
            return false;
        }
                
        if ((isNaN(length) || length=="" ||length==0) && width!="" && totalDimentions >0 )
        {
            alert("Enter the length  as numeric value or greater than 0 at package "+i);
            // document.DynaShipmentShipSaveForm['packageLength'+i].focus();
            return false;
        }
                
        if ((isNaN(length) || length=="" ||length==0 ) && height!="" && totalDimentions >0 )
        {
           alert("Enter the length as numeric value or greater than 0 at package "+i);
           //  document.DynaShipmentShipSaveForm['packageLength'+i].focus();
           return false;
        }
                 
        if (length!="" && (width=="" || isNaN(width) || width==0 ) && totalDimentions >0)
        {
           alert("Enter the width as numeric value or greater than 0 at package "+i);
           //  document.DynaShipmentShipSaveForm['packageWidth'+i].focus();
           return false;
        }
             
        if (height!="" && (width=="" || isNaN(width)   || width==0 ) && totalDimentions >0 )
        {
             alert("Enter the width as numeric value or greater than 0 at package "+i);
             //  document.DynaShipmentShipSaveForm['packageWidth'+i].focus();
             return false;
        }
        if (length!="" && (height=="" || isNaN(height) || height==0) && totalDimentions >0)
        {
           alert("Enter the height as numeric value or greater than 0 at package "+i);        
           // document.DynaShipmentShipSaveForm['packageHeight'+i].focus();
           return false;
        }
                 
         if (width!="" && (height=="" || isNaN(height) || height==0) && totalDimentions >0)
         {
           alert("Enter the height as numeric value or greater than 0 at package "+i)        
           //  document.DynaShipmentShipSaveForm['packageHeight'+i].focus();
           return false;
         }
         
         if(packageDeclaredValueChk=='')
         {
            document.getElementById('packageDeclaredValueID'+i).value = 0;
         }
         var upsPkging=parseInt(trim(document.getElementById('pkgingID'+i).value));
         if(str.substring(0,3)=="100" && upsMode=="UPS Direct" && upsPkging==1 && i>1 )
         {
               alert('Only One Package Is Allowed When Ups Envelope Package Type Is Selected.');
               return false;
         }
        
        // commented the below condition by khaja as we removed the itemDescription and shippedQty feilds
      /*   if(itemDescription=="")
         {
                alert("Please Enter the Item Description");
                document.getElementById('lineNoID'+i).focus();
                return false;
         }
      
         if(shippedQtyValue==0.0)
         {
             alert("Package Shipped Qty Should Be Greater Than Zero.");
             document.getElementById('shippedQtyID'+i).focus();
             return false;
         }
         if(isNaN(shippedQtyValue))
         {
             alert("Package Shipped Qty should be Numeric vlaue");
             document.getElementById('shippedQtyID'+i).focus();
             return false;
         } */
         if(WtValue==0.0 || isNaN(WtValue))
         {
             if(!(str.substring(0,3)=="100" && upsMode=="UPS Direct"))
             { 
                 alert(" Enter The Weight Of The Package Correctly");
                 document.getElementById('weightID'+i).focus();    
                 return false;
             }
             else
             {
                   var upsPackageType=trim(document.getElementById('pkgingID'+i).value);
                   upsPackageType=parseInt(upsPackageType);
                   if(upsPackageType==1 && WtValue== 0.0)
                   {
                   
                   }
                   else if((upsPackageType==1 && WtValue!= 0.0 ) || isNaN(WtValue))
                   {
                     alert("Weight For Ups Envelope Package Type Should Be Zero.");
                     document.getElementById('weightID'+i).focus();   
                     return false;
                   }
                   else if((upsPackageType!=1 && WtValue == 0.0) || isNaN(WtValue))
                   {
                     alert("Package Weight Should Be Greater Than Zero.");
                     document.getElementById('weightID'+i).focus();
                     return false;
                   }        
             }
         }                    
         i=i-1;  
       }//End of while.
      
       document.DynaShipmentShipSaveForm.submitButton1.value="0";
       return true;
    }
    
    function changeDimensions()
    {
     
        if(document.getElementById("flagShipID").value !="Y")
        {
              var packcount = parseInt(trim(document.getElementById("countPacketsID").value))
             
              var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
            
              var carrierTest=str.substring(0,3);
             
              if(carrierTest == '110' || carrierTest == '111')
              {
                  var packageName=document.getElementById("packagesId").options[document.getElementById("packagesId").selectedIndex].value;
            
                  if(packageName != 'YOURPACKAGING')
                  {
                    
                     for(var index =  1;index<=packcount;index ++)
                     {
                       
                            var nameLength=parseInt(document.getElementById('dimensionNameID'+index).length);
                  
                           for(var i=0; i<nameLength; i++)
                           {
                                if(document.getElementById('dimensionNameID'+index).options[i].text == 'Standard Dimension')
                                {
                              
                                  document.getElementById('dimensionNameID'+index).options[i].selected=true;
                                  document.getElementById('dimButtonID'+index).disabled=true
                                }
                           }
                          document.getElementById('dimensionNameID'+index).disabled = true; 
                     }
                  }
                  else
                 {
                 
                        for(var index =  1;index<=packcount;index ++)
                        {
                                document.getElementById('dimensionNameID'+index).disabled = false; 
                                document.getElementById('dimButtonID'+index).disabled=false;
                                var nameLength=parseInt(document.getElementById('dimensionNameID'+index).length);
                  
                               for(var i=0; i<nameLength; i++)
                               {
                                    if(document.getElementById('dimensionNameID'+index).options[i].text == document.DynaShipmentShipSaveForm.defaultDimText.value)
                                    {
                                       document.getElementById('dimensionNameID'+index).options[i].selected=true;
                                    }
                                    else 
                                    {
                                      document.getElementById('dimensionNameID'+index).options[i].selected=false;
                                    }
                               }
                       }
                
                 }
            
             }
             else
             {
            
                    for(var index =  1;index<=packcount;index ++)
                    {
                            document.getElementById('dimensionNameID'+index).disabled = false; 
                            var nameLength=parseInt(document.getElementById('dimensionNameID'+index).length);
              
                           for(var i=0; i<nameLength; i++)
                           {
                                if(document.getElementById('dimensionNameID'+index).options[i].text == document.DynaShipmentShipSaveForm.defaultDimText.value)
                                {
                                   document.getElementById('dimensionNameID'+index).options[i].selected=true;
                                }
                           }
                   }
            
             }
        }
     
     }
    
     
    function disableDim()
    {
    
         var packcount = parseInt(trim(document.getElementById("countPacketsID").value))
    
         if(document.getElementById("flagShipID").value =="Y")
         {
                for(var i=1; i<=packcount; i++)
                {
                       var trackingNumber=document.getElementById('trackingNumberID'+i).value;
                       if(trackingNumber == '' || trackingNumber == null)
                       {
                      
                       }
                       else
                       {
                            document.getElementById('dimensionNameID'+i).disabled=true;
                       }
                //Shiva added below code to disable package level void for DHL
                var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
                var carrierCode=strValue.substring(0,3);
                if(carrierCode=='114'){
                    document.getElementById('chVoidID'+i).disabled=true;
                }
            
                //Shiva code end
                }
           }
    
    }
       
    function checkPackageOptions()
    {
            var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
             
            var packcount = document.getElementById("countPacketsID").value;
                   
            var upsMode=document.getElementById("upsMode").value;
            
            if(str.substring(0,3)=="100" && upsMode=="UPS Direct")
            {
        
                for(var index =  1;index<=packcount;index ++)
                {
                      if(document.getElementById('pkgingID'+index).value=="" || document.getElementById('pkgingID'+index).value==null || document.getElementById('pkgingID'+index).value=="null" || document.getElementById('pkgingID'+index).value=="NaN")
                      {
              
                       document.getElementById('pkgingID'+index).value="02";
                           
                      }
                                                     
                       if(document.getElementById('packageDeclaredValueID'+index).value=="" || document.getElementById('packageDeclaredValueID'+index).value==null || document.getElementById('packageDeclaredValueID'+index).value=="null")
                       {
                          document.getElementById('packageDeclaredValueID'+index).value="0.00";
                       }
                   
                    if(document.getElementById('chCODID'+index).value=="Y")
                    {
                           if(document.getElementById('codTypeID'+index).value=="" || document.getElementById('codTypeID'+index).value==null || document.getElementById('codTypeID'+index).value=="null")
                           {
                  
                                document.getElementById('codTypeID'+index).value="3";
                            
                           }
                           
                           if(document.getElementById('codFundsCodeID'+index).value=="" || document.getElementById('codFundsCodeID'+index).value==null || document.getElementById('codFundsCodeID'+index).value=="null")
                           {
                  
                                document.getElementById('codFundsCodeID'+index).value="8";
                           }
                           if(document.getElementById('codCurrCodeID'+index).value=="" || document.getElementById('codCurrCodeID'+index).value==null || document.getElementById('codCurrCodeID'+index).value=="null")
                           {
                  
                                document.getElementById('codCurrCodeID'+index).value="USD";
                            }
                           if(document.getElementById('delConfirmID'+index).value=="" || document.getElementById('delConfirmID'+index).value==null || document.getElementById('delConfirmID'+index).value=="null")
                           {
                  
                           }
                   }
                   else
                   {
                     if(document.getElementById('codTypeID'+index).value=="" || document.getElementById('codTypeID'+index).value==null ||  document.getElementById('codTypeID'+index).value=="null")
                       {
              
                            document.getElementById('codTypeID'+index).value="";
                       
                       }
                       
                       if(document.getElementById('codFundsCodeID'+index).value=="" || document.getElementById('codFundsCodeID'+index).value==null || document.getElementById('codFundsCodeID'+index).value=="null")
                       {
              
                            document.getElementById('codFundsCodeID'+index).value="";
              
                       }
                        if(document.getElementById('codCurrCodeID'+index).value=="" || document.getElementById('codCurrCodeID'+index).value==null || document.getElementById('codCurrCodeID'+index).value=="null")
                       {
              
                            document.getElementById('codCurrCodeID'+index).value="";
                      }
                                       
                       if(document.getElementById('delConfirmID'+index).value=="" || document.getElementById('delConfirmID'+index).value==null || document.getElementById('delConfirmID'+index).value=="null")
                       {
              
                      
                       }
                   }
                }
            }
            
            
            if(str.substring(0,3)=="110" || str.substring(0,3)=="111")
            {
                for(var index =  1;index<=packcount;index ++)
                {
            
                    if(document.getElementById('packageDeclaredValueID'+index).value=="" || document.getElementById('packageDeclaredValueID'+index).value==null)
                    {
            
                        document.getElementById('packageDeclaredValueID'+index).value="0.00";
            
                     }
                     if(document.getElementById('PackageSurchargeID'+index).value=="" || document.getElementById('PackageSurchargeID'+index).value==null)
                     {
            
                        document.getElementById('PackageSurchargeID'+index).value="0.0";
            
                     }
                    
                     if(document.getElementById('returnShipmentID'+index).value=="" || document.getElementById('returnShipmentID'+index).value==null)
                     {
            
                         document.getElementById('returnShipmentID'+index).value="NONRETURN";
            
                     }
    
                     if(document.getElementById('signatureOptionID'+index).value=="" || document.getElementById('signatureOptionID'+index).value==null)
                     {
                         document.getElementById('signatureOptionID'+index).value="NONE";
            
                     }  
             
                    if(document.getElementById('returnShipmentID'+index).value == "PRINTRETURNLABEL")
                    {
                    
                           if(document.getElementById('rtnShipFromPhoneID'+index).value=="")
                           {
                    
                                alert("Please Enter The Return Ship From Phone Number for the package "+ index);
            
                                document.getElementById('rtnShipFromPhoneID'+index).readOnly=false;
                        
                                return false;
            
                           }
                           if(document.getElementById('rtnShipToPhoneID'+index).value=="")
                           {
                    
                                alert("Please Enter The Return Ship To Phone Number for the package "+ index);
            
                                document.getElementById('rtnShipToPhoneID'+index).readOnly=false;
            
                                return false;
            
                           }
                    }
              
                 }//For loop
            
             }//If loop
     }
    
    function voidFlagStatus()
    {
    var packcount = document.getElementById("countPacketsID").value;
    var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    var carrierTest=str.substring(0,3);
    var upsMode=document.getElementById("upsMode").value;
    
    if(((carrierTest=="110"|| carrierTest=="111") || (carrierTest=="100")) )
    { 
     document.getElementById('chVoidID1').disabled = true;
                   for(var index = 2;index<=packcount;index++)
                   {
                  if(document.getElementById('chVoidID'+index).checked==true)
                  {
            document.getElementById('chVoidID'+index).disabled = true;
            }
            else
            {
            document.getElementById('chVoidID'+index).disabled = false;
            }
    } 
    }
    }
    
    function voidStatusFlagValue()
    {
    
    var packcount = document.getElementById("countPacketsID").value;
    
    var voidStatusFlagValue;
    var count=0;
    var s="";
    var length;
    
    var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    var carrierCode=str.substring(0,3);
    var upsMode=document.getElementById("upsMode").value;
    for(var index = 1;index<=packcount;index++)
                   {
                   if(document.getElementById('chVoidID'+index).checked==true && document.getElementById('chVoidID'+index).disabled == false )
                       {
                             count=1;
                             document.getElementById('chVoidID'+index).value="Y";
                             document.getElementById('chVoidHiddenID'+index).value="Y";
                             s+=index+",";
                        }
                        if(document.getElementById('chVoidID'+index).checked==true && document.getElementById('chVoidID'+index).disabled == true 
                        && ((carrierCode=="100" && upsMode=="UPS Direct") || (carrierCode=="100" && upsMode=="ShipExec")))
                        {
                         document.getElementById('chVoidID'+index).value="N";
                         document.getElementById('chVoidHiddenID'+index).value="Y";
                         
                        }
                        else if(document.getElementById('chVoidID'+index).checked==false)
                        {
                         document.getElementById('chVoidID'+index).value="N";
                         document.getElementById('chVoidHiddenID'+index).value="N";
                        
                        }else if(document.getElementById('chVoidID'+index).checked==true && document.getElementById('chVoidID'+index).disabled == true && (carrierCode=="110" || carrierCode=="111") )
                        {
                        document.getElementById('chVoidID'+index).value="N";
                         document.getElementById('chVoidHiddenID'+index).value="Y";
                       
                        }
                        length=s.length;
                    }
                                   
                                  if(count==1)
                                     {
                                        document.getElementById("numOfVoidedPackages").value=s.substring(0,length-1);
                                            document.getElementById("voidStatusFlagId").value="PackageVoid";
                                        var status=confirm('Do you want to VOID following Package '+s.substring(0,length-1) +' ?');
                                         if(status)
                                         {
                                         return true;
                                         }
                                         else
                                         {
                                        return false;
                                          }
                                     }
                                   else
                                     {  
                                         //Shiva added below code for DHL
										 document.getElementById("voidStatusFlagId").value="ShipmentVoid";
                                         var status1;
                                            if(carrierCode == 114)
                                            {
                                                status1=confirm("Shipment can be voided at ShipConsole only, Since DHL does not support Online Voiding.\n"+
                                                "The shipment won’t be charged until it is picked up and scanned by the driver.");
                                            }
										//Shiva code end		
                                          else{
                                            status1=confirm('Do you want to VOID whole Shipment ?');
                                          }
                                          if(status1)
                                         {
                                         return true;
                                         }
                                         else
                                         {
                                        return false;
                                          }
                                     }
                 
    }
       
             function checkInternationalPhone(strPhone){
      // Declaring required variables
    var digits = "0123456789";
    // non-digit characters which are allowed in phone numbers
    var phoneNumberDelimiters = "()- ";
    // characters which are allowed in international phone numbers
    // (a leading + is OK)
    var validWorldPhoneChars = phoneNumberDelimiters + "+";
    // Minimum no of digits in an international phone no.
    var minDigitsInIPhoneNumber = 10;
    var bracket=3
    strPhone=trimPhone(strPhone)
    if(strPhone.indexOf("+")>1) 
    {
    return false
    }
    if(strPhone.indexOf("-")!=-1)
    {
    bracket=bracket+1;
    }
    if(strPhone.indexOf("(")!=-1 && strPhone.indexOf("(")>bracket)
    {
    return false
    }
    var brchr=strPhone.indexOf("(")
    if(strPhone.indexOf("(")!=-1 && strPhone.charAt(brchr+4)!=")")
    {
    return false
    }
    if(strPhone.indexOf("(")==-1 && strPhone.indexOf(")")!=-1)
    {
    return false
    }
    s=stripCharsInBagPhone(strPhone,validWorldPhoneChars);
    return (isIntegerPhone(s) && s.length == minDigitsInIPhoneNumber);
    }
    
    
    function trimPhone(s)
    {   var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not a whitespace, append to returnString.
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character isn't whitespace.
            var c = s.charAt(i);
            if (c != " ") returnString += c;
        }
        return returnString;
    }
    
    
    function isIntegerPhone(s)
    {   var i;
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        // All characters are numbers.
        return true;
    }
    
    function stripCharsInBagPhone(s, bag)
    {   var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character isn't whitespace.
            var c = s.charAt(i);
            if (bag.indexOf(c) == -1) returnString += c;
        }
        return returnString;
    }
       function truncate(num) { 
     string = "" + num; 
    if (string.indexOf('.') == -1) 
    return string; 
    seperation = string.length - string.indexOf('.'); 
    if (seperation > 3) 
    return string.substring(0,string.length-seperation+3); 
    else if (seperation == 2) 
    return string + '0'; 
    return string; 
    } 
      
      
    function totalWeight(){
    var num=document.getElementById("countPacketsID").value;
    var num1=parseFloat(num);
    var i=1;
    var wt=0;
    var wts ;
    var weightStr= document.getElementById('weightID'+i).value;  
    while (num1 >= 1) { 
    if(isNaN(document.getElementById('weightID'+i).value)) // To fix 2208
    {
    
        alert("The weight value should be a numeric value");
        document.getElementById('weightID'+i).focus();
        return false;
    
    
    }
    //Added void flag check condition to update total weight. By Y Pradeep.
    if(trim(document.getElementById('weightID'+i).value)!='' &&  !(document.getElementById('chVoidID'+i).checked && document.getElementById('chVoidID'+i).disabled))
    {
    wt=wt+parseFloat(document.getElementById('weightID'+i).value);       
    }
    i=i+1;
    num1=num1 - 1;
    }
    wts = truncate(wt);
    try {
    document.DynaShipmentShipSaveForm.packageWeight.value=wts; 
    document.getElementById("totalWeightId").value = wts;       //Added this line to update total blling weight. Bug #3002. By Y Pradeep.
    }catch(err)
    {
    //alert(err);
    }
    return true;
    }
    function openNewWindow (URL, windowName, windowOptions)
          {
              var window = getWindow (URL, windowName, windowOptions);
          }
    
          function getWindow(URL, windowName, windowOptions)
          {
            
              var newWindow = open (URL, windowName, windowOptions)
              newWindow.focus();
              return window;
          }
    // weighing scale js call
      function getWeightScaleInfo(pakageCount)
      {  
        var shipFlag=document.getElementById("flagShipID").value;
        if(shipFlag!='Y')
        {
           // Code to get the weight scale name based on the user..
           // var weightScale=<get the weight scale name>
           var weight=null;
           var uom=null;
           var vendorId = document.getElementById("vendorIdInMainId").value;
           var productId1 = document.getElementById("productId1InMainId").value;
           //alert('vendorId === '+vendorId);
           //alert('productId1 === '+productId1);
           //aascWeightScaleId.productId = vendorId;
           //aascWeightScaleId.vendorId = productId1;
           //var weightReturned = 
           //USBWeighingScale();//USBWeighingScale(vendorId, productId1);//aascWeightScaleId.weighingScale(vendorId, productId1);//getJUSBWeight(vendorId, productId1);     // For IE
           //alert('weightReturned == '+weightReturned);
           //var indexStr = weightReturned.indexOf('@@');
           //alert('indexStr === '+indexStr);
           //weight = weightReturned.substr(0,indexStr);
           //uom = weightReturned.substr(indexStr+2);
           //alert('weight=== '+weight);
           //alert('uom=== '+uom);
           /*if(document.getElementById("wsTopologyInMainId").value=="JUSB"){         // old Fairbanks  FairbankSCBR900014U
             //var vendorId = document.getElementById("vendorIdInMainId").value;
             //var productId1 = document.getElementById("productId1InMainId").value;
             weight = aascWeightScaleId.weighingScale();//getJUSBWeight(vendorId, productId1);     // For IE
             uom = aascWeightScaleId.getUOM();
           }
            else if(document.getElementById("wsTopologyInMainId").value=="PoweredUSB") //Fairbanks SCBR9050" --->   For Gaylord,   MettlerToledo PS60  ---> For Sealing Devices and SigSauer,   FairBanks SCBR900014U   --->  For Hologic
           {
             var vendorId = document.getElementById("vendorIdInMainId").value;
             var productId1 = document.getElementById("productId1InMainId").value;
             var productId2 = document.getElementById("productId2InMainId").value;
             weight = aascWeightScaleId.getPoweredUSBWeight(vendorId, productId1, productId2);
             uom = aascWeightScaleId.getUOM();
           }
           else if(document.getElementById("wsTopologyInMainId").value=="PureUSB")  // For Acco FB2250
           {
              var vendorId = document.getElementById("vendorIdInMainId").value;
              weight = aascWeightScaleId.getPureUSBWeight(vendorId);
              uom = aascWeightScaleId.getUOM();
           }*/
           if(weight == "" || weight ==null )
           {
             weight = 0.0;
           }
           else
           {
             document.getElementById('weightID'+pakageCount).value=weight;
           }
           if(uom == "" || uom ==null )
           {
             uom = 'LB';
           }
           else
           {
             document.getElementById('uomID'+pakageCount).value=uom;
           }
         }
      }
    
       // function to set the Saturday Shipment Flag checkbox Value.
    
    function chkSatShip(){
    
        if(document.getElementById("chkSatShipment123").checked)
        {
           document.getElementById("chkSatShipment123").value="Y";
           document.getElementById("chkSatShipmentHidden").value="Y";
        }
        else{
           document.getElementById("chkSatShipment123").value="N";
           document.getElementById("chkSatShipmentHidden").value="N";
        }
    }
     
    function showLabelPopUp()
        {
        //tpwindow =  window.open("aascAjaxGetLabel.jsp");
    //    tpwindow =  window.open("aascShowAdhocLabelList.jsp","Post",'width=400,height=350,top=100,left=100,resizable=yes');
        tpwindow.focus();
        }
    
    function getShipToPhoneNumber(){ // Ship To phone number is mandatory for UPS
        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        
        if(str.substring(0,3)==100)
            {
                var phonenumber = document.DynaShipmentShipSaveForm.phoneNumber.value;
               // alert("phonenumber :::::::"+phonenumber);
                if(phonenumber=="" || phonenumber==null){
                    alert("Please Enter The Phone Number");
                    document.DynaShipmentShipSaveForm.phoneNumber.focus();
                    return false;
                }
            }
        }
    
    /*function formReset()  // no where used
    {
    document.getElementById("DynaShipmentShipSaveForm").reset();
    document.getElementById("shipFromId").value="";
    document.getElementById("shipToId").value="";
    checkIntl();   // Suman added for clear functionality
    clearPackages(); // Suman Added for clearing packages
    getShipToLocations();
    
    }
    // Suman Gunda Added for added clearing packages
    function clearPackages()
    {
        var num=document.getElementById("countPacketsID").value;
        var num1 = parseInt(num);
      
        while (document.getElementById('PacTab').rows.length > 4) 
        {
        
                document.getElementById('PacTab').deleteRow(document.getElementById('PacTab').rows.length-2);
        
                num1=num1 - 1;
        
        }
        
        document.getElementById("countPacketsID").value="1";
    } */
    
    function getShipFromAddress()
    {
        //Shiva added below code for issue 3864        
        var shipFromLocHidden= document.getElementById('shipFromLocationIdHidden').value;
        var starIndex=shipFromLocHidden.indexOf('*');
        shipFromLocHidden=shipFromLocHidden.substring(0,starIndex);
        var shipFromLocationName=document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].text;
        //Added code to update Mask Flag After shipping

     if(shipFromLocHidden!=shipFromLocationName)
     {
        //Shiva code end
        
        var shipFromLocationName=document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].text;
        var location = document.getElementById('shipFromLoc').value ;
        var index = location.indexOf('*');
        var locationId = location.substring(index+4);
                
        document.getElementById('locationId').value = locationId ;
        var clientIdHid=document.getElementById("clientIdHid").value;
        var xmlHttp;
        try
        {    // Firefox, Opera 8.0+, Safari
           xmlHttp=new XMLHttpRequest();
        }
        catch (e)
        {    // Internet Explorer
          try
          {
              xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
          }
          catch (e)
          {
              try
              {
                  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
              }
              catch (e)
              {
                  alert("Your browser does not support AJAX!");
                  return false;
              }
          }
        }
        xmlHttp.onreadystatechange=function()
        {
          if(xmlHttp.readyState==4)
          {
                var responseStringDummy=trim(xmlHttp.responseText);
                var tempVal;
                var i=0;
              
                var index = responseStringDummy.indexOf("@");
                responseStringDummy = responseStringDummy.substring(index+3);
            
            if(responseStringDummy == "Profile Options not Configured")
            {
               alert("Please Contact your Admin and configure the Options for the selected Ship From Location ");
               document.getElementById('shipFromAddressLine1').value = '' ; 
               document.getElementById('shipFromAddressLine2').value = '' ;
               document.getElementById('shipFromCity').value = '' ; 
               document.getElementById('shipFromState').value = '' ;
               document.getElementById('shipFromPostalCode').value = '' ; 
               document.getElementById('shipFromCountryId').value = '' ;
               //document.getElementById('shipFromCountryHiddenId').value = '' ; 
               document.getElementById('shipFromPhoneNumber1').value = '' ;
               
               document.getElementById('shipFromContactName').value = '' ; 
               document.getElementById('shipFromEmailId').value = '' ; 
               document.getElementById('companyNameId').value = '' ;
               document.getElementById('referenceFlag1Id').value = '' ; 
               document.getElementById('referenceFlag2Id').value = '' ;
               document.getElementById('defaultPayMethodId').value = '' ; 
               document.getElementById('maskAccountCheckID').value = '' ;
               document.getElementById('satDeliveryCheckID').value = '' ;
                document.getElementById('shipperNameID').value = '' ;
               return false ;
            }
            else
            {
                //alert("Inside else");
                var testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('shipFromAddressLine1').value=tempVal;  
                
                var testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('shipFromAddressLine2').value=tempVal;  
                
                
                var testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('shipFromCity').value=tempVal;
                  
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('shipFromState').value=tempVal;
                 
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('shipFromPostalCode').value=tempVal;
                  
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                var shipFromCust=document.getElementById('shipFromCountryId');
                for(var i=1; i<shipFromCust.options.length;i++)
                {
                    if(shipFromCust.options[i].value == tempVal)
                    {
                        shipFromCust.options[i].selected=true;
                    }
                }
//                document.getElementById('shipFromCountryId').value=tempVal;
//                var shipFromCountry = document.DynaShipmentShipSaveForm.shipFromCountry.options[document.DynaShipmentShipSaveForm.shipFromCountry.selectedIndex].value;
                document.getElementById('shipFromCountryHiddenId').value = tempVal ;  // Eshwari added this line to fix bug # 2989
                var previousShipFromLocation = document.getElementById('shipFromLocationIdHidden').value ;
                var ShipFromLocation = document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
               
                if((previousShipFromLocation != ShipFromLocation))
                {
                   checkIntl();
                }
                
                document.getElementById('shipFromLocationIdHidden').value = document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                document.getElementById('shipFromPhoneNumber1').value=tempVal;
            
                testindex  = responseStringDummy.indexOf('***');
         
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('shipFromContactName').value=tempVal;
                
             /****** N Srisha added below code to default the ship from email id from profile options  Bug #3494 ********/
                 testindex  = responseStringDummy.indexOf('***');
         
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                } 
                
                 document.getElementById('shipFromEmailId').value = tempVal;
              /*********   N Srisha code end  *************/
              
                /****** eshwari added below code to default the ship from company name from profile options  ********/
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                if((document.getElementById('importFlagId').value != 'Y' && document.getElementById('importFlagId').value != 'y') || document.getElementById('companyNameId').value == null || document.getElementById('companyNameId').value == '')  // Added this condition for bug # 2948
                document.getElementById('companyNameId').value=tempVal;  
                /*********   eswhari code end  *************/
                /*********   Suman code start  *************/
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('referenceFlag1Id').value = tempVal;
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('referenceFlag2Id').value = tempVal;
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('defaultPayMethodId').value = tempVal;
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('satDeliveryCheckID').value = tempVal;
    //            alert('7973:'+document.getElementById('satDeliveryCheckID').value);
                  /*********   Suman code end  *************/
                /********* Sunanda code start ************/
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                         
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                //alert("customerNameFlag"+tempVal);
                 if(tempVal == 'Y' || tempVal == 'y')
                {
                  // alert("inside if");
                   document.getElementById('customerName').readOnly = false;
                  
                }
                else
                {
                   document.getElementById('customerName').readOnly = true;
                   //backspace(event);
                  
                }
                 
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  //alert("inside if");
                  tempVal='';
                }
                 //alert("addrLinesFlag"+tempVal);
                  if(tempVal == 'Y' || tempVal == 'y')
                {
                   //alert("inside if");
                   document.getElementById('shipToAddressId').readOnly = false;
                   document.getElementById('shipToAddrLine2Id').readOnly = false;
                   document.getElementById('shipToAddrLine3Id').readOnly = false;//Sunanda added code for bug fix #2613
                }
                else
                {
                   //alert("inside if");
                   document.getElementById('shipToAddressId').readOnly = true;
                   document.getElementById('shipToAddrLine2Id').readOnly = true;
                   document.getElementById('shipToAddrLine3Id').readOnly = true;//Sunanda added code for bug fix #2613
                   //backspace(event);
                }
                 
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                //alert("cityFlag"+tempVal);
                document.getElementById('shipToCityEditFlagId').value = tempVal;
                if(tempVal == 'Y' || tempVal == 'y')
                {
                   document.getElementById('city').readOnly = false;
                }
                else
                {
                   document.getElementById('city').readOnly = true;
                   //backspace(event);
                }               
                               
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                //alert("stateFlag"+tempVal);
                document.getElementById('shipToStateEditFlagId').value = tempVal;
                if(tempVal == 'Y' || tempVal == 'y')
                {
                   document.getElementById('state').readOnly = false;
                }
                else
                {
                   document.getElementById('state').readOnly = true;
                   //backspace(event);
                }
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                //alert("postalCodeFlag"+tempVal);
                document.getElementById('shipToZipEditFlagId').value = tempVal;
                if(tempVal == 'Y' || tempVal == 'y')
                {
                   document.getElementById('zip').readOnly = false;
                }
                else
                {
                   document.getElementById('zip').readOnly = true;
                   //backspace(event);
                }
                
                /*********   Suman code end  *************/
                 testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                //alert("countrycodeFlag"+tempVal);
                document.getElementById('shipToCountryEditFlagId').value = tempVal;
                if(tempVal == 'Y' || tempVal == 'y')
                {
                   document.getElementById('country').readOnly = false;
                }
                else
                {
                   document.getElementById('country').readOnly = true;
                }
    
                //getShipMethodAjax();
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                document.getElementById('weightClassInMainId').value = tempVal;
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                document.getElementById('vendorIdInMainId').value = tempVal;                
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                document.getElementById('productId1InMainId').value = tempVal;
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                document.getElementById('productId2InMainId').value = tempVal;
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                document.getElementById('wsTopologyInMainId').value = tempVal;

                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('maskAccountCheckID').value = tempVal;
                if(document.getElementById('maskAccountCheckID').value == "Y")
                {
                    document.getElementById('carrierAccountNumberId').value = document.getElementById('carrierAccNumHidId').value;
                }
                else
                {
                    document.getElementById('carrierAccountNumberId').value = document.getElementById('carrierAccNumHidId').value; 
                }
                testindex  = responseStringDummy.indexOf('***');
                 responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                 testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                if(tempVal == 'null')
                {
                  tempVal='';
                }
                document.getElementById('shipperNameID').value = tempVal;
               var weighingScale = document.getElementById('weightScaleId');
                if(document.getElementById('weightClassInMainId').value != '' && document.getElementById('weightClassInMainId').value != null && document.getElementById('weightClassInMainId').value != 'N')
                {
                    
                    /*var wsapplet = document.createElement('applet');
                    wsapplet.name = 'aascWeightScaleId';
                    wsapplet.id = 'aascWeightScaleId';
                    wsapplet.archive='./WeighingScale.jar, ./usb-api-1.0.2.jar, ./usb4java-javax-1.2.0.jar, ./usb4java-1.2.0.jar, ./libusb4java-1.2.0-windows-x86_64.jar, ./libusb4java-1.2.0-windows-x86.jar, ./libusb4java-1.2.0-osx-x86_64.jar, ./libusb4java-1.2.0-osx-x86.jar, ./libusb4java-1.2.0-linux-x86_64.jar, ./libusb4java-1.2.0-linux-x86.jar, ./libusb4java-1.2.0-linux-arm.jar, ./commons-lang3-3.2.1.jar';
                    wsapplet.code= 'com.AascWeighingScale.class'; 
                    wsapplet.style.width = '0px';
                    wsapplet.style.height = '0px';
                    
                    var wsparam = document.createElement('param');
                    wsparam.name = 'CODE';
                    wsparam.value='com.AascWeighingScale.class'; 
                    
                    wsapplet.appendChild(wsparam);
                    weighingScale.appendChild(wsapplet);*/
                    
                    
                    
                } else {
                    document.getElementById("weightScaleId").innerHTML = "";
                }
                
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                document.getElementById('wighingScaleFlagId').value = tempVal;
                
                if(document.DynaShipmentShipSaveForm.intlFlag.value == 'Y')
                {
                    document.DynaShipmentShipSaveForm.intlSaveFlag.value = 'N';
                }
                
                // Added below code to get configured printers from Printer Configuration page based on ship from location. Bug #3289. By Y Pradeep
                document.getElementById('eplPrinter').value = '';
                document.getElementById('gifPrinter').value = ''
                document.getElementById('pdfPrinter').value = '';
                document.getElementById('zplPrinter').value = '';
//                alert("responseStringDummy::"+responseStringDummy);
                testindex  = responseStringDummy.indexOf('***');
                tempVal=responseStringDummy.substr(0,testindex);
                if(tempVal == 'EPL'){
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('eplPrinter').value = tempVal;
                    
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                }
                
                if(tempVal == 'GIF'){
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('gifPrinter').value = tempVal;
                    
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                }
                
                if(tempVal == 'PDF'){
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('pdfPrinter').value = tempVal;
                    
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                }
                
                if(tempVal == 'ZPL'){
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('zplPrinter').value = tempVal;
                }
                
                var lablePrinter = document.getElementById('lablePrinterId');

                if(document.getElementById('eplPrinter').value == '' && document.getElementById('gifPrinter').value == '' && document.getElementById('pdfPrinter').value == '' 
                        && document.getElementById('zplPrinter').value == ''){
                    //lablePrinter.innerHTML = "";
                } /*else {
                    lablePrinter.innerHTML = "";
                    
                    var printerApplet = document.createElement('applet');
                    printerApplet.name = 'Print';
                    printerApplet.id = 'printer';
                    printerApplet.archive='./printer.jar, ./pdf-renderer.jar';
                    printerApplet.code= 'printer.AascPrinterApplet.class'; 
                    printerApplet.style.width = '0px';
                    printerApplet.style.height = '0px';
                    
                    lablePrinter.appendChild(printerApplet);                
                }*/
                
                /*//Below is the code for calling printing label function after shipping successfully.
                var key = document.getElementById("shipKey").value ;
                var displayMessage = document.getElementById("displayMessage");
                var printMessage = '';
                if(key == 'aasc225')
                {
                    printMessage = printLabel(); 
                    if(printMessage == 'aasc125'){
                        displayMessage.innerHTML = displayMessage.innerHTML + " and  Printed the labels Successfully ";
                    }else if(printMessage == 'aasc126'){
                        displayMessage.style.color = '#cc0000';
                        displayMessage.innerHTML = displayMessage.innerHTML + " and  Error in printing the labels ";
                    }
                  document.getElementById("shipKey").value = "";  
                }// End of the printer code.*/
                
                
          }
        }
        }
    
    
        var url="aascAjaxShipFromAddr.jsp?locationId="+locationId+"&clientIdHid="+clientIdHid;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null); 
        } else {
            var shipFromLocationName=document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].text;
            var location = document.getElementById('shipFromLoc').value ;
            var index = location.indexOf('*');
            var locationId = location.substring(index+4);
                    
            document.getElementById('locationId').value = locationId ;
            var clientIdHid=document.getElementById("clientIdHid").value;
            var xmlHttp;
            try
            {    // Firefox, Opera 8.0+, Safari
               xmlHttp=new XMLHttpRequest();
        }
            catch (e)
            {    // Internet Explorer
              try
              {
                  xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
    }
              catch (e)
              {
                  try
                  {
                      xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
                  }
                  catch (e)
                  {
                      alert("Your browser does not support AJAX!");
                      return false;
                  }
              }
            }
            xmlHttp.onreadystatechange=function()
            {
              if(xmlHttp.readyState==4)
              {
                    var responseStringDummy=trim(xmlHttp.responseText);
                    var tempVal;
                    var i=0;
  
                    var index = responseStringDummy.indexOf("@");
                    responseStringDummy = responseStringDummy.substring(index+3);
                    var testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('weightClassInMainId').value = tempVal;

                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('vendorIdInMainId').value = tempVal;                
                
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('productId1InMainId').value = tempVal;
                
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('productId2InMainId').value = tempVal;
                
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('wsTopologyInMainId').value = tempVal;
                
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('wighingScaleFlagId').value = tempVal;
                    
                    testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                    document.getElementById('maskAccountCheckID').value = tempVal;
                  
                  
                   testindex  = responseStringDummy.indexOf('***');
                    tempVal=responseStringDummy.substr(0,testindex);
                    responseStringDummy=responseStringDummy.slice(testindex+3,responseStringDummy.length);
                     document.getElementById('shipperNameID').value = tempVal;
                    
              }
            }
            var url="aascAjaxWeighingScaleDetails.jsp?locationId="+locationId+"&clientIdHid="+clientIdHid;
            xmlHttp.open("POST",url,false);
            xmlHttp.send(null); 
                
        }
    }
    
    function getDimensionsAjax()
    {
    // Suman G added below if condition for hold values after error case or success case #2505
        var shipFlag=document.getElementById("flagShip1").value ;
        var errorFlag = document.getElementById("errorFlag").value ;
      //  alert('hi'+shipFlag+'hello'+errorFlag);
        if( shipFlag!="Y" && errorFlag !="error")
        {
       document.getElementById("dimensionNameID1").options.length = 0; 
       document.getElementById("dimensionNameID1").options[0] = new Option('','');
       
       var locationId = document.getElementById("locationId").value;
    
       var xmlHttp;
       try
       {    // Firefox, Opera 8.0+, Safari  
         xmlHttp=new XMLHttpRequest(); 
       }
       catch (e)
       {    // Internet Explorer 
         try
         {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
         }
         catch (e)
         {    
          
           try
           {     
             xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
           }
           catch (e)
           {        alert("Your browser does not support AJAX!");  
              return false;      
           }    
         }  
            
       }
       xmlHttp.onreadystatechange=function()
       {
          if(xmlHttp.readyState==4)
          {
             var packcount = document.getElementById("countPacketsID").value;
             for(var index = 1; index<=packcount; index++)
             {
                var responseStringDummy=trim(xmlHttp.responseText);
                var indexStr = responseStringDummy.indexOf('@@@');
                responseStringDummy = responseStringDummy.substr(indexStr+3);
                var testindex  = responseStringDummy.indexOf('@@@@@@@');
                var indextest;
                var option12;
                var optionValue;
                var optionEle;
                var unitsIndex;
                var i=0;
                var dimText = '' ;
                var dimVal = '' ;
                var dimUnits = '' ;
                var dimDefault = '' ;
                var dimId = '' ;
                while(testindex!=-1)
                {
                  option12=responseStringDummy.substr(0,testindex);
                  indextest = option12.indexOf('||');
                  dimText = option12.substr(0,indextest);
                  
                  option12 = option12.substr(indextest+2);
                  indextest = option12.indexOf('#######');
                  dimVal = option12.substr(0,indextest);
                  
                  option12 = option12.substr(indextest+7)
                  indextest = option12.indexOf('^^^^^');
                  dimUnits = option12.substr(0,indextest);
                  
                  option12 = option12.substr(indextest+5)
                  indextest = option12.indexOf('###');
                  dimDefault = option12.substr(0,indextest);
                  
                  option12 = option12.substr(indextest+3)
                  dimId = option12.substr(0,option12.length);
                  
                  
                  optionEle = dimVal+'*'+dimUnits+'*'+dimId ;
                  
                  document.getElementById('dimensionNameID'+index).options[i] = new Option(dimText , optionEle); //optionValue);
                  if(dimDefault == 'Y' || dimDefault == 'y')    
                  {
                    document.getElementById("dimensionNameID"+index).options[i].selected = true;
                    
                    document.getElementById('packageLengthID'+index).value;
                    document.getElementById('packageWidthID'+index).value;
                    document.getElementById('packageHeightID'+index).value;
                    
                    document.getElementById('dimensionNameHiddenID'+index).value = dimText;
                    document.getElementById('dimensionValueHiddenID'+index).value = optionEle;
                    document.getElementById('defaultDimeNameID'+index).value = optionEle;
                    
    
                    document.getElementById('defaultDimValueID').value = optionEle ;
                    document.getElementById('defaultDimTextID').value = dimText ;
                                    
                  }
                  responseStringDummy=responseStringDummy.substr(testindex+7,responseStringDummy.length);
                  testindex  = responseStringDummy.indexOf('@@@@@@@');
                  i++;
                }
                  
              }    
            }
       }
       var url="aascAjaxDimensions.jsp?locationId="+locationId;
       xmlHttp.open("POST",url,false);
       xmlHttp.send(null); 
      } //  Added condition for ship and error case  
    }
       
       
       function showTip(oSel,e) {
    var evt=e?e:window.event;
            var theTip = document.getElementById("tooltip");
            theTip.style.top = evt.clientY + 20;
            theTip.style.left = evt.clientX;
            
           if(navigator.appName !='Microsoft Internet Explorer')
            { theTip.textContent = oSel.options[oSel.selectedIndex].text;}
            else{theTip.innerText = oSel.options[oSel.selectedIndex].text; }
    
            theTip.style.visibility = "visible";
            }
    
    function hideTip() {
            document.getElementById("tooltip").style.visibility = "hidden";
            }
    function isRightCurveBracket(description){
    
        var iChars = ">";
       
        if(description.value.indexOf(iChars) != -1) {
         alert ("The description field do not contain > symbol");
         description.focus();
         return false;
        }
        return true;
    }
    
    // Commented by suman for future reference
    //uncommented by Jagadish for SearchOrder functionality
    //Sunanda Modified the below code and called the below function in ShipOrderSearch jsp for bug fix #2731
    function validateSearch(){
          
        if(document.OrderGetDetailsForm.fromDate.value=="" && document.OrderGetDetailsForm.customerNameId.value=="")
        {
            alert("Please enter From Date or Customer Name");
            document.getElementById("fromDate").focus()
            return false;
        }
                              
        var fromDateHiddenID = document.getElementById("fromDateHiddenID").value;
        var toDateHiddenID = document.getElementById("toDateHiddenID").value;
        var customerNameHiddenID = document.getElementById("customerNameHiddenID").value;
        if(!(document.OrderGetDetailsForm.fromDate.value==fromDateHiddenID && 
                                document.OrderGetDetailsForm.toDate.value==toDateHiddenID
                                && document.OrderGetDetailsForm.customerNameId.value==customerNameHiddenID)){
                                //alert("inside if case");
            document.getElementById("searchFlag").value="0";
            document.OrderGetDetailsForm.submit();
        
         }else{
            //alert("inside else case");
            document.getElementById("searchFlag").value="1";
           
         }
         document.getElementById("loading").style.display = "block";
        
         if(document.getElementById("searchFlag").value=="1")
         {
            alert("Request already submitted. Please Wait.");
            return false;
         }
    }
                              
    
    var index ;
    function populateOrderNumber(){    
        var rows = document.getElementById('my_table').getElementsByTagName('tbody')[0].getElementsByTagName('tr');
        for (i = 0; i < rows.length; i++) {
        
                rows[i].onclick = function() {
                    index = this.rowIndex;
                    orderNumber();
                }
            }
    }
    
    function orderNumber(){
        var orderNoSelected = document.getElementById("orderNoSelected"+index).value;
        
        window.opener.document.ShipGetForm.orderNumberSearch.value=orderNoSelected;
        window.opener.document.ShipGetForm.orderNumberSearch.focus(); // Added this line to focus order number search field after selecting a order in Advance Serach popup. By Pradeep. Bug #2909.
        window.close();
    }
    
    function stopEnterKeyPress(evt) {
      var evt = (evt) ? evt : ((event) ? event : null);
      var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
      var k = document.getElementById("orderNumberSearchId");
      if(k!=node){
      if ((evt.keyCode == 13) && (node.type=="text"))  {return false;}
    }
    }
    document.onkeypress = stopEnterKeyPress;  
    
    // Suman Gunda added below function for clear fields
    function clearFields(){
        //Sunanda gave proper ids of fields to fix bug #2732
        document.getElementById('fromDate').value='';
        document.getElementById('toDate').value='';
        document.getElementById('customerNameId').value='';
        
    }
    
    function validateFutureShipDateForFedEx(){
        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        var shipDate=document.getElementById("shipmentDate").value;
        var pos1=shipDate.indexOf(dtCh);
        var pos2=shipDate.indexOf(dtCh,pos1+1);
        var strYear=shipDate.substring(0,pos1);
        var strMonth=shipDate.substring(pos1+1,pos2);
        var strDay=shipDate.substring(pos2+1);
        var strYr=strYear;
        var futureDayFlag;
    
        if (strDay.charAt(0)=="0" && strDay.length>1)
            strDay=strDay.substring(1);
    
        if (strMonth.charAt(0)=="0" && strMonth.length>1)
            strMonth=strMonth.substring(1);
            
        for (var i = 1; i <= 3; i++)
        {
            if (strYr.charAt(0)=="0" && strYr.length>1)
                strYr=strYr.substring(1);
        }
        
        var shipDateStr=strYear+"-"+strMonth+"-"+strDay;
        var thisdate = new Date();
        var year1 = thisdate.getFullYear();
        var month1 = thisdate.getMonth()+1;
        var day = thisdate.getDate();
        var sysDateStr=year1+"-"+month1+"-"+day;
    
        if(strYr==year1 && strMonth==month1 && strDay==day)
        {
            futureDayFlag=0;
        }
        else
        {
            futureDayFlag=1;
        }
    
        if( (str.substring(0,3)=="110" || str.substring(0,3)=="111") && futureDayFlag==1)
        {
            var future10day = tenDaysFromSysDate();
            var flag1=compareDates(sysDateStr,shipDateStr);
            var flag2=compareDates(shipDateStr,future10day);
            
            if( !(compareDates(sysDateStr,shipDateStr) && compareDates(shipDateStr,future10day)) )
            {
                alert("Shipment Date for FedEx Shipments Should Be Between "+sysDateStr+" and "+future10day);
                return false;
            }
        }
        if(str.substring(0,3)=="113")
        {
            var parts = shipDateStr.split("-");
            var month = parseInt(parts[1], 10);
            var year = parseInt(parts[0], 10);
            var day = parseInt(parts[2], 10);
                
            var currentDate = new Date();
            var day2 = currentDate.getDate();
            var month2 = currentDate.getMonth() + 1;
            var year2 = currentDate.getFullYear();
            
            var one_day=1000*60*60*24;  // calculation of number of milliseconds per day
            var date2=new Date(year,month-1,day);
            var date1=new Date(year2,month2-1,day2);
                
            Diff=Math.ceil((date2.getTime()-date1.getTime())/(one_day));
    
            if(Diff > 14)
            {
                alert("Enter a date not more than 14 days in the future for TNT"); 
                return false;
            }
            else if(Diff < 0)
            {
                alert("Ship date cannot be past Date for TNT");
                return false;
            }
        }
        return true;
    }//end of the function
    
    function tenDaysFromSysDate()
    {
            var daysInMonth = DaysArray(12);
        var thisdate = new Date();
        var year1 = thisdate.getFullYear();
        var month1 = thisdate.getMonth()+1;
        var date = thisdate.getDate();
        var strYear=year1;
        var strMonth=month1;
        var strDay=date;
        var strYr=strYear;
        var month=parseInt(strMonth);
        var day=parseInt(strDay);
        var year=parseInt(strYear);
        var noOfDaysInMonth;
        var noOfDaysInNextMonth;
        var firstDayInNextMonth;
        var futureDayStr;
        var switchMonthFlag;
        var leap=0;
    
        if ((parseInt(year)%4) == 0)
        {
            if (parseInt(year)%100 == 0)
            {
                if (parseInt(year)%400 != 0)
                {
                    leap=0;
                }
                if (parseInt(year)%400 == 0)
                {
                    leap=1;
                }
            }
            if (parseInt(year)%100 != 0)
            {
                leap=1;
            }
        }
        if ((parseInt(year)%4) != 0)
        {
            leap=0
        }
    
        if(month==2 && leap==0)
        {
            noOfDaysInMonth=28;
        }
        else
        {
            noOfDaysInMonth=daysInMonth[month];
        }
    
        if(month <= 11)
        {
            noOfDaysInNextMonth=daysInMonth[month+1];
        }
        else
        {
            noOfDaysInNextMonth=daysInMonth[1];
        }
        
        firstDayInNextMonth=1;
    
        if(month!=2 && day <= 21)
        {
            day = day + 9;
            futureDayStr=year+"-"+month+"-"+day;
        }
        else if(month==2 && day <= 19 && leap==0)
        {
            day = day + 9;
            futureDayStr=year+"-"+month+"-"+day;
        }
        else if(month==2 && day <=20  && leap==1)
        {
            day = day + 9;
            futureDayStr=year+"-"+month+"-"+day;
        }
        else if((day>21) ||((day==20 || day==21)&& month==2 && leap==0 )||( day==21&& month==2 && leap==1) )
        {
            for(var i=1;i<10;i++)
            {
                if(day<noOfDaysInMonth)
                {
                    day=day+1;
                    switchMonthFlag=0;
                }
                else
                {
                    if(firstDayInNextMonth <= noOfDaysInNextMonth)
                    {
                        firstDayInNextMonth=firstDayInNextMonth+1;
                        switchMonthFlag=1;
                    }
                }
            }
        }
        var switchMonthFlagNum=parseInt(switchMonthFlag);
        if(switchMonthFlagNum==1)
        {
            day=firstDayInNextMonth-1;
            if(month==12)
            {
                month=1;
                year=year+1;
            }
            else
            {
                month=month+1;
            }
        }
        futureDayStr=year+"-"+month+"-"+day;
        return futureDayStr;
    }
    
    function compareDates(fromDate,toDate)
       {
            var frompos1=fromDate.indexOf(dtCh) ;
            var frompos2=fromDate.indexOf(dtCh,frompos1+1) ;
    
            //Checking for FromDate
            var fromYear=fromDate.substring(0,frompos1);
            var fromMonth=fromDate.substring(frompos1+1,frompos2);
            var fromDay=fromDate.substring(frompos2+1);
    
            fromYr = fromYear;
            if (fromDay.charAt(0)=="0" && fromDay.length>1) fromDay=fromDay.substring(1);
            if (fromMonth.charAt(0)=="0" && fromMonth.length>1) fromMonth=fromMonth.substring(1);
            for (var i = 1; i <= 3; i++)
            {
              if (fromYr.charAt(0)=="0" && fromYr.length>1)
               fromYr=fromYr.substring(1);
            }
            fmonth=parseInt(fromMonth);
            fday=parseInt(fromDay);
            fyear=parseInt(fromYr);
    
            //Checking for ToDate
            var topos1=toDate.indexOf(dtCh);
            var topos2=toDate.indexOf(dtCh,topos1+1);
            var toYear=toDate.substring(0,topos1);
            var toMonth=toDate.substring(topos1+1,topos2);
            var toDay=toDate.substring(topos2+1);
            toYr=toYear;
            if (toDay.charAt(0)=="0" && toDay.length>1) toDay=toDay.substring(1);
            if (toMonth.charAt(0)=="0" && toMonth.length>1) toMonth=toMonth.substring(1);
            for (var i = 1; i <= 3; i++)
            {
              if (toYr.charAt(0)=="0" && toYr.length>1)
               toYr=toYr.substring(1);
            }
            tmonth=parseInt(toMonth);
            tday=parseInt(toDay);
            tyear=parseInt(toYr);
    
            if(toYear > fromYear)
            return true;
    
           else if(toYear < fromYear)
           {
              return false;
           }
           else if(toYear == fromYear)
           {
              if(tmonth > fmonth)
              {
                return true;
              }
    
             else if(tmonth < fmonth)
             {
               return false;
             }
             else if(tmonth == fmonth)
             {
               if(tday >= fday)
                  return true;
    
               else if(tday < fday)
               {
                   return false;
               }
             }
           }
          return true;
       }
    function populateSavedAccNo()
    {
    
        //==================== Suman Gunda Code start 
        var shipFlag=document.getElementById("flagShip1").value ;
        try{
            
            var errorFlagForDropOffType = document.getElementById("errorFlag2").value;
            
        }catch(err){
            
            var errorFlagForDropOffType = 'success';
        }
        if( shipFlag!="Y")// && errorFlagForDropOffType != 'error')
        {
            var localCarrierPayTerm  = document.getElementById('carrierPayMethodId').options[document.getElementById('carrierPayMethodId').selectedIndex].text;
        
            if(localCarrierPayTerm == "RECIPIENT"){
            document.getElementById('carrierAccountNumberId').value = document.getElementById('receipentAccNoId').value;
             if(document.getElementById("maskAccountCheckID").value == "Y"){
              mask(); 
              } 
        //    alert('fghjkl'+document.getElementById('receipentAccNoId').value);
            }
            if(localCarrierPayTerm == "THIRD PARTY BILLING"){
            document.getElementById('carrierAccountNumberId').value = document.getElementById('thirdPartyAccNoId').value;
             if(document.getElementById("maskAccountCheckID").value == "Y"){
              mask(); 
              } 
        //    alert('tp'+document.getElementById('thirdPartyAccNoId').value);
            }
        //    alert('after change'+document.getElementById('carrierAccountNumberId').value);  
            //==================== Suman Gunda Code start
        }
    }
    
    function getDefaultAccountNumber(){
        
        var clientId = document.getElementById("clientIdHid").value;
        var custLocationId = document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].value;
        var locationSelected = document.DynaShipmentShipSaveForm.customerLocation.options[document.DynaShipmentShipSaveForm.customerLocation.selectedIndex].value;
        var shipMethodvalue=document.getElementById("shipMethodId").value;  
        var carrierCode=shipMethodvalue.substring(0,shipMethodvalue.indexOf("|"));
        
        var xmlHttp;
       try
       {    // Firefox, Opera 8.0+, Safari  
         xmlHttp=new XMLHttpRequest(); 
       }
       catch (e)
       {    // Internet Explorer 
         try
         {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
         }
         catch (e)
         {    
          
           try
           {     
             xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
           }
           catch (e)
           {        alert("Your browser does not support AJAX!");  
              return false;      
           }    
         }  
            
       }
       xmlHttp.onreadystatechange=function()
       {
            if(xmlHttp.readyState==4)
            {
                var responseStringDummy = xmlHttp.responseText;
                    
                    subindex1 = responseStringDummy.indexOf('###');
                    var responseStringDummy3 = responseStringDummy.substring(subindex1+3);
                    
                    index2 = responseStringDummy3.indexOf('#');
                    parse2 = responseStringDummy3.substring(0,index2);
                    
                    document.getElementById("receipentAccNoId").value=parse2;
                    document.getElementById("thirdPartyAccNoId").value=responseStringDummy3.substring(index2+1);
                    
            }	 
       }

       var url="aascAjaxGetDefaultAcNumbers.jsp?custLocationId="+encodeURIComponent(custLocationId)+"&clientId="+clientId+"&locationSelected="+encodeURIComponent(locationSelected)+"&carrierCode="+carrierCode;
       xmlHttp.open("POST",url,false);
       xmlHttp.send(null); 
    }
    //below ajax call added by Jagadish to generate ordernumber when intl popup is opened. 
    function getOrderNumber(fsFlag){
        
        var clientId = document.getElementById("clientIdHid").value;
    
        var xmlHttp;
       try
       {    // Firefox, Opera 8.0+, Safari  
         xmlHttp=new XMLHttpRequest(); 
       }
       catch (e)
       {    // Internet Explorer 
         try
         {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
         }
         catch (e)
         {    
          
           try
           {     
             xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
           }
           catch (e)
           {        alert("Your browser does not support AJAX!");  
              return false;      
           }    
         }  
            
       }
       xmlHttp.onreadystatechange=function()
       {
            if(xmlHttp.readyState==4)
            {
                var responseStringDummy = xmlHttp.responseText;
                
                document.getElementById('orderNumberID').value=trim(responseStringDummy);
                    
    //                subindex1 = responseStringDummy.indexOf('###');
    //                var responseStringDummy3 = responseStringDummy.substring(subindex1+3);
    //                
    //                index2 = responseStringDummy3.indexOf('#');
    //                parse2 = responseStringDummy3.substring(0,index2);
    //                
    //                document.getElementById("receipentAccNoId").value=parse2;
    //                document.getElementById("thirdPartyAccNoId").value=responseStringDummy3.substring(index2+1);
                    
            }	 
       }
       
       var url="aascAjaxOrderNumberGen.jsp?clientId="+clientId+"&fsFlag="+fsFlag;
       xmlHttp.open("POST",url,false);
       xmlHttp.send(null); 
    }
    function chkSplCharsAll(message)
    {
           var len= (trim(message)).length;
           var message = trim(message);
           //alert("message :"+message);
           for(var index = 0; index <len;index++)
           {
               var c = message.charAt(index);
               //   alert("c :"+c);
               //   if(c == '"'||c == ':'||c == ';'||c == '\'||c == '\'||c == '}'||c == '{'||c == ']'||c == '['||c == '?'||c == '/'||c == '>'||c == '<'||c == '='||c == '_'||c == '+'||c == '-'||c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')')  
               if(c == "="||c == "_"||c == "+"||c == ","||c == "-"||c == '?'||c == '>'||c == '<'||c == "}"||c == "{"||c == "]"||c == "["||c == "/"||c == ';'||c == ':'||c == '"'||c == '_'||c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')')  
               {
                 return false;
               }
           }  
           return true;
    }
    
    function openAddressValidationPopup(){
        var checkValue=checkCustomerValidation();
        if(trim(document.getElementById('checkValidationID').value) == 'false'){
         return false;
        }

        if(document.DynaShipmentShipSaveForm.orderNum.value == null || document.DynaShipmentShipSaveForm.orderNum.value == ''){
            getOrderNumber(0);
        }
        document.getElementById('avFlag').value = 'Y';
        var customerName = document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].text;
        var contactName = document.DynaShipmentShipSaveForm.contactName.value;
        var shipToAddress = document.DynaShipmentShipSaveForm.shipToAddress.value;
        var shipToAddrLine2 = document.DynaShipmentShipSaveForm.shipToAddrLine2.value;
        var city = document.DynaShipmentShipSaveForm.city.value;
        var state = document.DynaShipmentShipSaveForm.state.value;
        var postalCode = document.DynaShipmentShipSaveForm.postalCode.value;
        var countrySymbol = document.DynaShipmentShipSaveForm.countrySymbol.value;
        var shipToLocation = document.getElementById("customerLocationId").value;
        if(shipToAddress == ''){
            if(customerName == "" || customerName =="--Select--"){
                alert("Please select a Customer and Location");
                document.getElementById("customerName").focus();
                return false;
            } else if(shipToLocation == '' || shipToLocation == "--Select--"){
                alert("Please select Ship To Locaiton");
                document.getElementById("customerLocationId").focus();
                return false;
            }
        }
        var locationValue= document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
        var testIndex = locationValue.indexOf('****');
        var locationId = locationValue.slice(testIndex+4,locationValue.length); 
        var orderNo = document.DynaShipmentShipSaveForm.orderNumber.value;
        var str=document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        var carrierCode=str.substring(0,3);
        orderNo = encodeURIComponent(orderNo);
        customerName = encodeURIComponent(customerName);
        contactName = encodeURIComponent(contactName);
        shipToAddress = encodeURIComponent(shipToAddress);
        shipToAddrLine2 = encodeURIComponent(shipToAddrLine2);
        postalCode = encodeURIComponent(postalCode);

        tpwindow =  window.open("AddressValidationAction.action?actionType=validateAddress&carrierCode="+carrierCode+"&orderNo="+orderNo+"&locationId="+locationId+"&customerName="+customerName+"&contactName="+contactName+"&shipToAddress="+shipToAddress+"&shipToAddrLine2="+shipToAddrLine2+"&city="+city+"&state="+state+"&postalCode="+postalCode+"&countrySymbol="+countrySymbol,"Post",'width=770,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
        tpwindow.focus();
           
    }
    
    
    function freightShopOpenWin(decision)
    {
        var checkValue=checkCustomerValidation();
        if(trim(document.getElementById('checkValidationID').value) == 'false'){
         return false;
        }

        var shipToCustomerName = document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].text;
        document.getElementById('fsFlag').value = 'Y';
        if(shipToCustomerName == '--Select--'){
            alert('Please select Ship To Customer Name');
            document.DynaShipmentShipSaveForm.customerName.focus();
            return false;
        }
        
        var shipFromLocation= document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
        
        if(shipFromLocation == ''){
            alert('Please select Ship From Locaiton');
            document.DynaShipmentShipSaveForm.shipFromLoc.focus();
            return false;
        }
        try{
            var shipToCustomerLocation= document.DynaShipmentShipSaveForm.customerLocation.options[document.DynaShipmentShipSaveForm.customerLocation.selectedIndex].value;
        }catch(Err){
            var shipToCustomerLocation = '--Select--';
        }
        var weight = document.getElementById('weightID1').value;
        var weightUOM = document.getElementById('uomID1').value;
        
        
        if(shipToCustomerLocation == '--Select--'){
            alert('Please select Ship To Locaiton');
            document.DynaShipmentShipSaveForm.customerLocation.focus();
            return false;
        }
        
        if(weightUOM == ''){
            alert('Please enter Weight UOM');
            document.getElementById('uomID1').focus();
            return false;
        }
        
        var count = document.getElementById('countPacketsID').value;
        for(var i=1;i<=count;i++){
        
            var dimUom;
            dimUom  = document.getElementById('dimensionValueHiddenID'+i).value; // It works only ajax call happend. Need to work on this.
            
            var lastIndex = dimUom.lastIndexOf('*');
            dimUom = dimUom.substring(0,lastIndex);
            lastIndex = dimUom.lastIndexOf('*');
            var dimValue = dimUom.substring(0,lastIndex);
            dimUom = dimUom.substring(lastIndex+1);
            
            if(dimUom == ''){
            
                dimUom = document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].value;
                lastIndex = dimUom.lastIndexOf('*');
                dimUom = dimUom.substring(0,lastIndex);
                lastIndex = dimUom.lastIndexOf('*');
                dimValue = dimUom.substring(0,lastIndex);
                dimUom = dimUom.substring(lastIndex+1);
            }
            dimValue = dimValue.replace('*','X');
            dimValue = dimValue.replace('*','X');
            
            weight = document.getElementById('weightID'+i).value;
            if(weight == '0.0' || weight == '0' || weight == ''){
                alert('Please enter Weight for the package '+i);
                document.getElementById('weightID'+i).focus();
                return false;
            }
        }
        
        if(document.DynaShipmentShipSaveForm.orderNum.value == null || document.DynaShipmentShipSaveForm.orderNum.value == ''){
            getOrderNumber(decision);
        }
        document.getElementById('checkSubmitId').value = '0';
        if(decision == '1'){
            document.getElementById('freightShopDecisionId').value = 'FreightShop';
    //        alert('hi'+document.getElementById('checkSubmitId').value);
            window.open("aascFreightShopPopUp.jsp?countPackages="+document.getElementById('countPacketsID').value,"Post",'width=800,height=500,top=100,left=100,resizable=yes,scrollbars=yes');
        }
    }
    // Added below for function for Get Rates Functionality
    function validateForRates()
    {
    
        var checkValue=checkCustomerValidation();
        if(trim(document.getElementById('checkValidationID').value) == 'false'){
         return false;
        }

            
        document.getElementById('decisionId').value = 'GetRates' ;
        document.getElementById('fsFlag').value = 'Y';
        var shipToCustomerName = document.DynaShipmentShipSaveForm.customerName.options[document.DynaShipmentShipSaveForm.customerName.selectedIndex].text;
        
        if(shipToCustomerName == '--Select--'){
            alert('Please select Ship To Customer Name');
            document.DynaShipmentShipSaveForm.customerName.focus();
            return false;
        }
        
        var shipFromLocation= document.DynaShipmentShipSaveForm.shipFromLoc.options[document.DynaShipmentShipSaveForm.shipFromLoc.selectedIndex].value;
        
        if(shipFromLocation == ''){
            alert('Please select Ship From Locaiton');
            document.DynaShipmentShipSaveForm.shipFromLoc.focus();
            return false;
        }
        
        try{
            var shipToCustomerLocation= document.DynaShipmentShipSaveForm.customerLocation.options[document.DynaShipmentShipSaveForm.customerLocation.selectedIndex].value;
        }catch(Err){
            var shipToCustomerLocation='--Select--';
        }
        
        if(shipToCustomerLocation == '--Select--'){
            alert('Please select Ship To Locaiton');
            document.DynaShipmentShipSaveForm.customerLocation.focus();
            return false;
        }
        
        var weight = document.getElementById('weightID1').value;
        var weightUOM = document.getElementById('uomID1').value;
        var shipmethod = document.getElementById("shipMethodId").value;
        
        var carrierCode = shipmethod.substring(0,shipmethod.indexOf('||'));
//        shipmethod = shipmethod.substring(shipmethod.indexOf('*')+1,shipmethod.indexOf('@@'));
        document.getElementById('getRateShipMethodId').value = shipmethod;
        
        var shipmethodText = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].text;
        
       if(weightUOM == ''){
            alert('Please enter Weight UOM');
            document.getElementById('uomID1').focus();
            return false;
        }
//           alert("shipmethod::::"+shipmethodText);
        if(shipmethodText == '--Select Ship Method--'){
            alert('Please Select the Ship Method');
            document.getElementById('shipMethodId').focus();
            return false;
        }
        if(document.DynaShipmentShipSaveForm.orderNumber.value == null || document.DynaShipmentShipSaveForm.orderNumber.value == ''){
            getOrderNumber(0);
        } 
        
        var orderNumber = document.getElementById('orderNumberID').value;
        document.getElementById('orderNumberSearchId').value = orderNumber;
        var fromAdd1 = document.getElementById('shipFromAddressLine1').value;
        var fromAdd2 = document.getElementById('shipFromAddressLine2').value;
        var fromCity = document.getElementById('shipFromCity').value;
        var fromState = document.getElementById('shipFromState').value;
        var fromZipCode = document.getElementById('shipFromPostalCode').value;
        var fromCountry = document.getElementById('shipFromCountryId').value;
    
        document.getElementById('fromAdressLine1Id').value = fromAdd1;
        document.getElementById('fromAdressLine2Id').value = fromAdd2;
        document.getElementById('fromCityId').value = fromCity;
        document.getElementById('fromStateId').value = fromState;
        document.getElementById('fromZipCodeId').value = fromZipCode;
        document.getElementById('fromCountryCodeId').value = fromCountry;
        
        var toAdd1 = document.getElementById('shipToAddressId').value;
        var toAdd2 = document.getElementById('shipToAddrLine2Id').value;
        var toCity = document.getElementById('city').value;
        var toState = document.getElementById('state').value;
        var toZipCode = document.getElementById('zip').value;
        var toCountry = document.getElementById('country').value;
        
        document.getElementById('toAdressLine1Id').value = toAdd1;
        document.getElementById('toAdressLine2Id').value = toAdd2;
        document.getElementById('toCityId').value = toCity;
        document.getElementById('toStateId').value = toState;
        document.getElementById('toZipCodeId').value = toZipCode;
        document.getElementById('toCountryCodeId').value = toCountry;
        document.DynaShipmentShipSaveForm.shipDate.value = document.DynaShipmentShipSaveForm.shipmentDate.value;
        if(carrierCode == 100)
                document.getElementById('UPSCheckBoxId').value = true;
        else if(carrierCode == 110 || carrierCode == 111)
                document.getElementById('FedExCheckBoxId').value = true;
        else if(carrierCode == 114){
                document.getElementById('DHLCheckBoxId').value = true;

//                alert('Get Rates is not avaliable for DHL carrier. Please select other service level');
//                document.getElementById('shipMethodId').focus();
//                return false;
        }
        else if(carrierCode == 115)  
                document.getElementById('StampsCheckBoxId').value = true;
                
        var count = document.getElementById('countPacketsID').value;
        var declaredValue = 0;
        for(var i=1;i<=count;i++){
        
            var dimUom;
            dimUom  = document.getElementById('dimensionValueHiddenID'+i).value; // It works only ajax call happend. Need to work on this.
            
            var lastIndex = dimUom.lastIndexOf('*');
            dimUom = dimUom.substring(0,lastIndex);
            lastIndex = dimUom.lastIndexOf('*');
            var dimValue = dimUom.substring(0,lastIndex);
            dimUom = dimUom.substring(lastIndex+1);
            
            if(dimUom == ''){
            
                dimUom = document.getElementById('dimensionNameID'+i).options[document.getElementById('dimensionNameID'+i).selectedIndex].value;
                lastIndex = dimUom.lastIndexOf('*');
                dimUom = dimUom.substring(0,lastIndex);
                lastIndex = dimUom.lastIndexOf('*');
                dimValue = dimUom.substring(0,lastIndex);
                dimUom = dimUom.substring(lastIndex+1);
            }
            dimValue = dimValue.replace('*','X');
            dimValue = dimValue.replace('*','X');
            document.getElementById('dimUOMId'+i).value = dimUom;
            document.getElementById('dimValueId'+i).value = dimValue;
            document.getElementById('weightUOMId'+i).value = document.getElementById('uomID'+i).value;
            document.getElementById('weightValueId'+i).value = document.getElementById('weightID'+i).value;
            weight = document.getElementById('weightID'+i).value;
            if(weight == '0.0' || weight == '0' || weight == ''){
                alert('Please enter Weight for the package '+i);
                document.getElementById('weightID'+i).focus();
                return false;
            }
            declaredValue = declaredValue + parseInt(document.getElementById('packageDeclaredValueID'+i).value);
        }
        
        if(declaredValue != 0){
            document.getElementById('isDutiableId').value = 'Y';
            document.getElementById('dhlDeclaredCurrencyId').value = 'USD';
            document.getElementById('dhlDeclaredValueId').value = declaredValue;
        }
        else
            document.getElementById('isDutiableId').value = 'N';
        //added by Padma for bug #2967
        
            if(document.getElementById('getRatesButtonId').value ==1)
            {
            alert('Please wait Request already submitted   ');
            return false;
            }
        if (packValidate()==false)      // Added if condition to get valid dimensions when multiple packages are added and clicked on Get Rates button. Bug #3273. By Y Pradeep.
        {
            return false;
        }
        AddPkgsToForm();
            //Padma code ends here
        document.getElementById('submitButtonId').value = 'Get Rates';
        document.getElementById('getRatesButtonId').value =1;
        document.getElementById("DynaShipmentShipSaveForm").submit();
    }
    
    function openFreightCostDetails(){
    
    var freightCost =document.getElementById("freightCostId").value;
    var freightCharges=document.getElementById("freightChargesId").value;
    var totalWeight=document.getElementById("totalWeightId").value;
    
    tpWindow=window.open("aascFreightCostDetails.jsp?freightCost="+freightCost+"&freightCharges="+freightCharges+"&totalWeight="+totalWeight,"Post", 'width=600,height=300,top=150,left=150,scrollbars=yes, resizable=yes');
    
        tpWindow.focus();    
    }
    //Sunanda code start for bug fix #2613
    function checkFlag1(event)
    {
    if (document.getElementById('customerName').readOnly == true)
    {
     backspace(event);
     return true;
    }
    else{
    return false;
    }
    }
    function checkFlag2(event)
    {
    if(document.getElementById('shipToAddressId').readOnly == true){
    backspace(event);
    }
    else{
    return false;
    }
    }
    function checkFlag3(event)
    {
    if(document.getElementById('shipToAddrLine2Id').readOnly == true){
    backspace(event);
    }
    else{
    return false;
    }
    }
    //Sunanda Modified code for bug fix 2613
    function checkFlag9(event)
    {
    if(document.getElementById('shipToAddrLine3Id').readOnly == true){
    backspace(event);
    }
    else{
    return false;
    }
    }
    function checkFlag4(event){
    if(document.getElementById('city').readOnly == true)
    {
    backspace(event);
    }
    else{
    return false;
    }
    }
    function checkFlag5(event)
    {
    if(document.getElementById('state').readOnly == true){
    backspace(event);
    }
    else{
    return false;
    }
    }
    function checkFlag6(event)
    {
    if(document.getElementById('zip').readOnly == true){
    backspace(event);
    }
    else{
    return false;
    }
    }
    function checkFlag7(event){
    if(document.getElementById('country').readOnly == true)
    {
    backspace(event);
    }
    else{
    return false;
    }
    }
    
    
    /*GuruRaj Added for bug fix #2612 and also to prevent rendering to previous page when backspace is pressed*/
    function backspace(e)
    {
        //alert("test");
        // var vEventKeyCode = FindKeyCode(e);
        var vEventKeyCode= "";
        var vEventKeyCode = FindKeyCode(e);
        //alert("e.keyCode :: "+e.keyCode);
        //alert("vEventKeyCode :: "+vEventKeyCode);
        // backspace key pressed
        if(vEventKeyCode == 8 || vEventKeyCode==127)
        {
       
        var browser=getBrowser();
        //alert("browser :: "+browser);
        if(browser=="Firefox" || browser=="Chrome"||browser=="Safari"||browser=="IE")
        {
            //alert();
            e.returnValue=false;
             
        }
        else
        {
        //  e.which = '';
        return false;
        }
        //     }
        }
    }
    //Sunanda code end
    
    //Added code by Suman G for view label functionality.
    function showLabelPopUp()
    {
        var upsMode=document.getElementById("upsMode").value;
        tpwindow =  window.open("aascShowLabelList.jsp?upsMode="+upsMode,"Post","width=600,height=350,top=100,left=100,scrollbars=yes, resizable=yes");
        tpwindow.focus();
    }
    
    function setResidentialFlag(){
        var residentialFlag = document.getElementById("residentialFlagId").checked;
        if(residentialFlag){
            document.getElementById("residentialFlagId").value='Y';
        }else{
            document.getElementById("residentialFlagId").value='N';
        }
    }
    
    function dateFormatCheck(input){
    
        var validformat=/^[0-9]{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])/;
    //    /(\d{4})-(\d{2})-(\d{2})/ //Basic check for format validity
        if(input.value != ""){
            if (!validformat.test(input.value)){
                alert("The date format should be : YYYY-MM-DD")
                input.focus();
                return false
            }
        }
    
    }
    
    function redirectToError(){
    
        window.opener.redirectToLogin();//='login.jsp';
        window.close();
        
    }
    
    function redirectToLogin(){
        
        document.getElementById("DynaShipmentShipSaveForm").action = "login.action?actiontype=Logout";
        document.getElementById("DynaShipmentShipSaveForm").submit();
        
    }

    function setCarrierPayMethodHidden(){
        document.getElementById("chngCarrierPayMethodHidden").value = "true";
    }

    function deleteRowInDB(packCount){
        
        var clientId = document.getElementById("clientIdHid").value;
    
        var xmlHttp;
       try
       {    // Firefox, Opera 8.0+, Safari  
         xmlHttp=new XMLHttpRequest(); 
       }
       catch (e)
       {    // Internet Explorer 
         try
         {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
         }
         catch (e)
         {    
          
           try
           {     
             xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
           }
           catch (e)
           {        alert("Your browser does not support AJAX!");  
              return false;      
           }    
         }  
            
       }
       xmlHttp.onreadystatechange=function()
       {
            if(xmlHttp.readyState==4)
            {
                var responseStringDummy = xmlHttp.responseText;
         
            }	 
       }
       
       var submitButton = document.getElementById("submitButtonId").value;
       var orderNumber = document.getElementById('orderNumberID').value;
       var url="aascAjaxDeletePackages.jsp?clientId="+clientId+"&submitButton="+submitButton+"&orderNumber="+orderNumber+"&packNumber="+packCount;
       xmlHttp.open("POST",url,false);
       xmlHttp.send(null); 
    }

    function onFocusWeightField(packCount){
        if(document.getElementById('wighingScaleFlagId').value != 'N' && document.getElementById('wighingScaleFlagId').value != '')
        {
            getWeightScaleInfo(packCount);
        }
    }
    
function getEmailDetails(){


    var clientId = document.getElementById("clientIdHid").value;
    var locationId = document.getElementById("locationId").value;
    var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
    var index2 = strValue.indexOf('||');
    var carrierCode=strValue.substring(0,index2);

    var xmlHttp;
       try
       {    // Firefox, Opera 8.0+, Safari  
         xmlHttp=new XMLHttpRequest(); 
       }
       catch (e)
       {    // Internet Explorer 
         try
         {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
         }
         catch (e)
         {    
          
           try
           {     
             xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
           }
           catch (e)
           {        alert("Your browser does not support AJAX!");  
              return false;      
           }    
         }  
            
       }
       xmlHttp.onreadystatechange=function()
       {
            if(xmlHttp.readyState==4)
            {
                var responseStringDummy = xmlHttp.responseText;
                var index2 = responseStringDummy.indexOf('@@@');
                
                responseStringDummy = responseStringDummy.substring(index2+4);
                var index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('emailNotificationFlagId').value = responseStringDummy.substring(0,index1);
                    
                }
                responseStringDummy = responseStringDummy.substring(index1+1);
                
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                  //  document.getElementById('senderMailId').value = responseStringDummy.substring(0,index1);
                }
                
                responseStringDummy = responseStringDummy.substring(index1+1);
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('reference1FlagId').value = responseStringDummy.substring(0,index1);
                }
                responseStringDummy = responseStringDummy.substring(index1+1);
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('reference2FlagId').value = responseStringDummy.substring(0,index1);
                }
                responseStringDummy = responseStringDummy.substring(index1+1);
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('shipNotificationFlagId').value = responseStringDummy.substring(0,index1);
                }
                responseStringDummy = responseStringDummy.substring(index1+1);
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('exceptionNotificationId').value = responseStringDummy.substring(0,index1);
                }
                responseStringDummy = responseStringDummy.substring(index1+1);
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('deliveryNotificationId').value = responseStringDummy.substring(0,index1);
                }
                responseStringDummy = responseStringDummy.substring(index1+1);
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('formatTypeId').value = responseStringDummy.substring(0,index1);
                }
//                responseStringDummy = responseStringDummy.substring(index1+1);
//                index1 = responseStringDummy.indexOf('*');
//                if(index1 != -1){
//                    document.getElementById('salesOrderNumberId').value = responseStringDummy.substring(0,index1);
//                }
                responseStringDummy = responseStringDummy.substring(index1+1);
                index1 = responseStringDummy.indexOf('*');
                if(index1 != -1){
                    document.getElementById('emailCustomerNameId').value = responseStringDummy.substring(0,index1);
                    
                }
//                responseStringDummy = responseStringDummy.substring(index1+1);
//                index1 = responseStringDummy.indexOf('*');
//                if(index1 != -1){
//                    document.getElementById('deliveryItemNumberId').value = responseStringDummy.substring(0,index1);
//                }
               
            }	 
       }
       
       
       var url="aascAjaxRetrieveEmailDetails.jsp?locationId="+locationId+"&carrierCode="+carrierCode;
       xmlHttp.open("POST",url,false);
       xmlHttp.send(null); 
}

//below ajax call added by Jagadish for balance and tolerance alert.
function checkCustomerValidation(){


    var clientId = document.getElementById("clientIdHid").value;
    var pkg_count=document.getElementById("countPacketsID").value;
    var toMailId=document.getElementById('clientMailId').value;//"jagadish.jain@appsassociates.com";
    var checkValidation=true;
    var xmlHttp;
       try
       {    // Firefox, Opera 8.0+, Safari  
         xmlHttp=new XMLHttpRequest(); 
       }
       catch (e)
       {    // Internet Explorer 
         try
         {      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");     
         }
         catch (e)
         {    
          
           try
           {     
             xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");     
           }
           catch (e)
           {        alert("Your browser does not support AJAX!");  
              return false;      
           }    
         }  
            
       }
       xmlHttp.onreadystatechange=function()
       {
       
            if(xmlHttp.readyState==4)
            {
                var responseStringDummy = xmlHttp.responseText;
                var index=responseStringDummy.indexOf("*");
                
                var email_alert_flag = trim(responseStringDummy.substring(0,index));
                
               
//                alert('string:::::'+responseStringDummy);
//                alert('last index:::::'+index);
                var opStatus = trim(responseStringDummy.substring(index+1,responseStringDummy.lastIndexOf("$")));
//alert(opStatus);
//return false;
                if(opStatus == '101'){
                    alert("Your transaction limit has exhausted. Please renew");
                    document.getElementById('checkValidationID').value=false;
                    return false;
                }
                else if(opStatus == '109'){
                    alert("Your subscription period is over. Please renew");
                    document.getElementById('checkValidationID').value=false;
                    return false;
                }
                
                document.getElementById('checkValidationID').value=true;
                
                
            }	 
       }
       
       var url="aascAjaxClientValidation.jsp?pkg_count="+pkg_count+"&toMailId="+toMailId;
       xmlHttp.open("POST",url,false);
       xmlHttp.send(null);

}
function validateEmail(x)
{
    var atpos=x.indexOf("@");
    var dotpos=x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){
        //alert("Not a valid e-mail address");
        return false;
    }
    else
    {
    return true;
    }
    
}

function isEqual(fromDate,toDate)
{
    var frompos1=fromDate.indexOf(dtCh) ;
    var frompos2=fromDate.indexOf(dtCh,frompos1+1) ;
    var flag;
    var fromYear=fromDate.substring(0,frompos1);
    var fromMonth=fromDate.substring(frompos1+1,frompos2);
    var fromDay=fromDate.substring(frompos2+1);

    fromYr = fromYear;
    if (fromDay.charAt(0)=="0" && fromDay.length>1) fromDay=fromDay.substring(1);
    if (fromMonth.charAt(0)=="0" && fromMonth.length>1) fromMonth=fromMonth.substring(1);
    for (var i = 1; i <= 3; i++)
    {
      if (fromYr.charAt(0)=="0" && fromYr.length>1)
       fromYr=fromYr.substring(1);
    }
    fmonth=parseInt(fromMonth);
    fday=parseInt(fromDay);
    fyear=parseInt(fromYr);
    var topos1=toDate.indexOf(dtCh);
    var topos2=toDate.indexOf(dtCh,topos1+1);
    var toYear=toDate.substring(0,topos1);
    var toMonth=toDate.substring(topos1+1,topos2);
    var toDay=toDate.substring(topos2+1);


    toYr=toYear;
    if (toDay.charAt(0)=="0" && toDay.length>1) toDay=toDay.substring(1);
    if (toMonth.charAt(0)=="0" && toMonth.length>1) toMonth=toMonth.substring(1);
    for (var i = 1; i <= 3; i++)
    {
      if (toYr.charAt(0)=="0" && toYr.length>1)
       toYr=toYr.substring(1);
    }
    tmonth=parseInt(toMonth);
    tday=parseInt(toDay);
    tyear=parseInt(toYr);
    if(parseInt(toYear) == parseInt(fromYear))
    {
       flag="true";
    }
    else
    {
     flag="false";
     return false;
    }
   if(parseInt(toYear) == parseInt(fromYear))
   {
      if(parseInt(tmonth) == parseInt(fmonth))
      {
         flag="true";
       }
       else
       {
        flag="false";
        return false;
       }

      if(parseInt(tmonth) == parseInt(fmonth))
     {
       if(parseInt(tday) == parseInt(fday))
       {
           flag="true";
       }
       else
       {
        flag="false";
        return false;
       }
         }
   }
   if(flag=="true")
  return true;
}
