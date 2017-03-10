/*==========================================================================+
|  DESCRIPTION                                                              |
|    aasc_tracking_js.js javascript file for aascTrackingPageMain.jsp       |
|    Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.                      |
|    All rights reserved.                                                   |
|    Author Veena Vakity                                                    |
|    Version   1.0                                                          |                                                                            
|    Creation 27/02/2006                                                    |                                         |
+===========================================================================*/
/*========================================================================================
Date        Resource       Change history
------------------------------------------------------------------------------------------
02/05/2006  Narasimha      Modified Strings in alert message to Init Cap Format.
10/05/2006  Veena          Code cleanup.
11/09/2007  Sambit         Modified getDeliveryIds() function to check null values  in 
                           result while calling Ajax. 
12/09/2007  Sambit         Added functions for calendar in tracking page.
18/09/2007  Sambit         Modified getDeliveryIds() to display proper massege when no
                           deliveries are present on a day.
26/09/2007  Sambit         Added a function disableGo() to disable go button when no 
                           deliveries are present on a day. 
12/11/2010  Madhavi        Added validation to inv organization in displayInfo()
24/09/2013  Eshwari M      Modified code for Role Id 2
17/12/2014  Eshwari M      Modified code to resolve issued while testing tracking functionality with shipping data
29/12/2014  Pradeep Y      Fixed all issues in tracking page for all roles.
16/12/2014  Pradeep Y      Removed if condition to check Order Number is numberic or not.
16/02/2015  Suman G        Modified alerts and if condition to fix #2616.
15/03/2015  Eshwari M      Added setActionType()function
01/04/2015  Y Pradeep      Added alert to to enter date if date filed is empty and clicked on go button. Bug #2765.
13/04/2015  Y Pradeep      Added validation for location field to select a location. Bug #2839.
27/05/2015  Y Pradeep      Modified code to display and allow Order Numbers with special characters(Encode and Decode).
03/07/2015  Suman G        Added code to fix #3147
31/08/2015  Suman G        Added user id for Trial user modifications.
20/11/2015  Suman G        Added code to restrict user to track DHL & Stamps carriers.
========================================================================================*/
 
/**
Function to set the Focus to the List 
TextField at the time of Page Loading
*/
	function load()	
  {
   	document.SigTrackingPageMainForm.inputNumberText.focus();
	}
  
  
  
  function trim(str)
{
  return str.replace(/^\s*|\s*$/g,"");
}






/**
function to display information relevant to 
selected tab (OrderInfo, DeliveryInfo, TrackInfo)
*/
  function validateInfo()
  {
     if ((document.SigTrackingPageMainForm.inputNumberText.value=='')){
           alert("Enter a Valid Delivery Id or Order No or Tracking No and click 'Go'");
           return false;
     }
   }

  function displayInfo()
  {
  
     var selectedIndex = document.SigTrackingPageMainForm.locationIdSelect.value ;
    
     if(selectedIndex == "Select" || selectedIndex == ""){
        alert("Please select a location");
        document.SigTrackingPageMainForm.locationIdSelect.focus();
        return false;
     }
        
     if(document.SigTrackingPageMainForm.ShipmentTrackDate.value == ''){
        alert("Please select a Date");
        document.SigTrackingPageMainForm.ShipmentTrackDate.focus();
        return false;
     }
    
     if ((document.SigTrackingPageMainForm.inputNumberText.value=='')
       && (document.SigTrackingPageMainForm.inputNumberList.value=='')) 
     {
      
          alert("Enter a Valid Order Number and click 'Go'");
          document.SigTrackingPageMainForm.inputNumberList.focus();
          return false;
     }
    
    
     if(document.SigTrackingPageMainForm.InputTypeSelect.value=='OrderNumber')
     {	
       if(document.SigTrackingPageMainForm.inputNumberText.value=='Select')
       {	
         alert("Please select Order Number in drop down list");
         document.SigTrackingPageMainForm.inputNumberText.focus();
         return false;
       }
     }
     //  Added code to fix #3147
     if(document.SigTrackingPageMainForm.InputTypeSelect.value=='TrackingNumber')
     {	
       if(document.SigTrackingPageMainForm.inputNumberText.value=='Select')
       {	
         alert("Please select Tracking Number in drop down list");
         document.SigTrackingPageMainForm.inputNumberText.focus();
         return false;
       }
     }
   
     if((document.SigTrackingPageMainForm.InputTypeSelect.value=='OrderNumber')
         && (document.SigTrackingPageMainForm.inputNumberText.value.length > 9 ))
     {	
//        alert("Enter Numeric Value for Order Number");
//        document.SigTrackingPageMainForm.inputNumberText.focu();
//        return false;
     }
   
     /*if(document.SigTrackingPageMainForm.InputTypeSelect.value=='DeliveryName')
     {	
        if(isInteger(document.SigTrackingPageMainForm.inputNumberText.value)==false)
        {	
        alert("Select Numeric Value for Delivery Number");
        document.SigTrackingPageMainForm.inputNumberText.focus();
        return false;
        }
     }*/
      
 } // end of displayInfo()
 
  function isInteger(s)
  {
      var i;
      for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9")))
        return false;
      }
      // All characters are numbers.
      return true;
	}
  
/**
function to remove the previous value from the list when selected and 
focussing to the textbox.
*/  
  function deleteNo()
  {
    //alert("Inside deleteNo");
    document.SigTrackingPageMainForm.inputNumberText.value='Select';
    document.SigTrackingPageMainForm.inputNumberText.focus();
  }
    
   ////////////////////////////////////////////////////////////////////////////////////////////////////////
   //For editable select box
    ////////////////////////////////////////////////////////////////////////////////////////////////////////   
   

  function fnKeyDownHandler(getdropdown, e)
  {
    fnSanityCheck(getdropdown);

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
      //e.which = ''; //this property has only a getter.
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

  --------------------------- Subrata Chakrabarty */

  /*----------------------------------------------
  The dropdown specific functions
  (which manipulate dropdown specific global variables)
  used by all dropdowns are:
  -----------------------------------------------
  1) function fnChangeHandler_A(getdropdown)
  2) function fnKeyPressHandler_A(getdropdown, e)
  3) function fnKeyUpHandler_A(getdropdown, e)

  --------------------------- Subrata Chakrabarty */

  /*------------------------------------------------
  IMPORTANT: Global Variable required to be SET by programmer
  -------------------------- Subrata Chakrabarty  */

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
  -------------------------- Subrata Chakrabarty  */

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
  -------------------------- Subrata Chakrabarty  */

  function fnChangeHandler_A(getdropdown)
  {
    fnSanityCheck(getdropdown);

    vPreviousSelectIndex_A = vSelectIndex_A;
    // Contains the Previously Selected Index

    vSelectIndex_A = getdropdown.options.selectedIndex;
    // Contains the Currently Selected Index

    if ((vPreviousSelectIndex_A == (vEditableOptionIndex_A)) && (vSelectIndex_A != (vEditableOptionIndex_A))&&(vSelectChange_A != 'MANUAL_CLICK'))
    // To Set value of Index variables - Subrata Chakrabarty
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
    fnSanityCheck(getdropdown);

    keycode = FindKeyCode(e);
    keychar = FindKeyChar(e);

    // Check for allowable Characters
    // The various characters allowable for entry into Editable option..
    // may be customized by minor modifications in the code (if condition below)
    // (you need to know the keycode/ASCII value of the  character to be allowed/disallowed.
    // - Subrata Chakrabarty

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

        var vEditString = getdropdown[vEditableOptionIndex_A].text;

        // make Editable option Null if it is being edited for the first time
        if((vAllowableCharacter == "yes")||(keychar=="backspace"))
        {
          if (vEditString == vEditableOptionText_A)
            vEditString = "";
        }
        if (keychar=="backspace")
        // To handle backspace - Subrata Chakrabarty
        {
          vEditString = vEditString.substring(0,vEditString.length-1);
          // Decrease length of string by one from right

          vSelectChange_A = 'MANUAL_CLICK';
          // Indicates that the Change in dropdown selected
          // option was due to a Manual Click

        }

        if (vAllowableCharacter == "yes")
        // To handle addition of a character - Subrata Chakrabarty
        {
          vEditString+=String.fromCharCode(keycode);
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
    fnSanityCheck(getdropdown);

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

  ////////////////////////////////////////////////////////////////////////////////////////////////////////
   //For editable select box
 ////////////////////////////////////////////////////////////////////////////////////////////////////////   


function getOrderNumbers()
{

    var roleId = document.SigTrackingPageMainForm.roleId.value ;
 
    var clientId = 0 ;
    //alert("roleId : "+roleId);
    if(roleId == 2)
    {
        clientId = document.SigTrackingPageMainForm.clientIdSelect.options[document.SigTrackingPageMainForm.clientIdSelect.selectedIndex].value ;
        //alert("clientId : "+clientId);
        document.SigTrackingPageMainForm.clientIdHidden.value = clientId;
        //orgId = document.SigTrackingPageMainForm.orgIdSelect.options[document.SigTrackingPageMainForm.orgIdSelect.selectedIndex].value ;
    }
    else
    {
       clientId = document.SigTrackingPageMainForm.clientIdHidden.value ;
    }
    
    var selectedIndex = document.SigTrackingPageMainForm.locationIdSelect.selectedIndex;
    //alert("selectedIndex : "+selectedIndex);
    var locationId = 0 ;
    if(selectedIndex != -1)
       locationId = document.SigTrackingPageMainForm.locationIdSelect.options[selectedIndex].value ;
//     alert("locationId : "+locationId);
    
    var locationIdHidden =document.SigTrackingPageMainForm.locationIdHidden.value ;
//     alert("locationIdHidden : "+locationIdHidden);
    if(locationId != "" && locationId != null)
    {
       //alert("Inside if");
       document.SigTrackingPageMainForm.locationIdHidden.value = locationId;
    }
    else{
      locationId = locationIdHidden ;
    }
    //alert("document.SigTrackingPageMainForm.invOrgIdHidden.value : "+document.SigTrackingPageMainForm.invOrgIdHidden.value);
  
    var orderNumber=document.SigTrackingPageMainForm.inputNumberText.options[document.SigTrackingPageMainForm.inputNumberText.selectedIndex].value;
    var shipTrackDate =document.SigTrackingPageMainForm.ShipmentTrackDate.value;
    //alert("333 SAMBIT DT AJAX"+shipTrackDate);
    var idType= document.SigTrackingPageMainForm.InputTypeSelect.options[document.SigTrackingPageMainForm.InputTypeSelect.selectedIndex].value; 
    var orderNo=document.SigTrackingPageMainForm.orderName.value;

    // alert(document.forms['SigTrackingPageMainForm'].inputNumberText.options[0].value);
    
    //if(orderNumber==''||orderNumber==null)
    {
    document.forms['SigTrackingPageMainForm'].inputNumberText.options.length = 0; 
    document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("Select","Select",true,false);
    // if(orderNumber==''||orderNumber==null)
    // {
    //  alert("2.in if(orderNumber==''||orderNumber==null)");
    //   document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("","");
    //  }
   
    var trackIds = new Array();
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
      //  xmlHttp.responseText='none';
      var responseStringDummy=trim(xmlHttp.responseText);
      var index = responseStringDummy.indexOf("@");
      responseStringDummy = responseStringDummy.substring(index+3);
            
      //alert("resp string::::::::::::"+responseStringDummy);
      if( responseStringDummy != 'none' || responseStringDummy!=null )
      {
          trackIds=responseStringDummy.split("***");
           
          for (var i=0;i<trackIds.length-1 ;i++ )
          { 
            if(orderNumber==trackIds[i] )
            {
             //alert("inside 1");
             document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("Select","Select",true,true);
             document.forms['SigTrackingPageMainForm'].inputNumberText.options[i+1] =  new Option(trackIds[i],trackIds[i],true,true);
             document.getElementById("txtMessage").innerHTML=" ";
             
            }
            else if(orderNumber=="Select")
            {
            // alert("Inside else if Loop");
             document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("Select","Select",true,true);
             document.forms['SigTrackingPageMainForm'].inputNumberText.options[i+1] =  new Option(trackIds[i],trackIds[i],true,false);
             document.getElementById("txtMessage").innerHTML=" ";             
            }
            else
            {
             document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("Select","Select",true,false);
             document.forms['SigTrackingPageMainForm'].inputNumberText.options[i+1] =  new Option(trackIds[i],trackIds[i],true,false);
             document.getElementById("txtMessage").innerHTML=" ";
            }
          } 
      }  
         
      if( responseStringDummy == 'none' && orderNo!='') // checking for null values at first time
      {          
       //alert("Inside if 2");      
       document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("Select","Select",true,true);
       document.forms['SigTrackingPageMainForm'].inputNumberText.options[1] =  new Option(orderNo,orderNo,true,true);
       //alert("2:"+document.getElementById("txtMessage").innerHTML);
       document.getElementById("txtMessage").innerHTML=" ";       
      }
      
   
      if(shipTrackDate != null && locationId !=0 && responseStringDummy == 'none')
      { 
       // alert("Inside if 3");
       //alert("No Shipment has done in this date for selected Organization ");
       document.forms['SigTrackingPageMainForm'].inputNumberText.options.length = 0; 
       document.forms['SigTrackingPageMainForm'].inputNumberText.options[0] =  new Option("Select","Select",true,false);
       var message="** No Shipment has done in this date for selected Organization ";
       document.getElementById("txtMessage").innerHTML=message;
       document.getElementById("goButton1").disabled = true ;
      }
     }
   }
   locationId=document.SigTrackingPageMainForm.locationIdSelect.options[selectedIndex].value;
   var userId = document.getElementById('userId').value;
   var url="aascAjaxTrakingInfo.jsp?trakingType="+idType+"&shipTrack="+shipTrackDate+"&locationId="+locationId+"&clientId="+clientId+"&userId="+userId;
   xmlHttp.open("GET",url,false);  // Calling 
   xmlHttp.send(null); 
   return true ;
      
  }
}
  
function getLocationValue()
{
   //alert("Inside getLocationValue ");
   var locationId = "" ;
  
   var roleId = document.SigTrackingPageMainForm.roleId.value ;
   //alert("roleId : "+roleId);
   var selectedIndex = "" ;
   if(roleId == 2)
   {
     locationId =document.getElementById("locationIdHidden").value ;   
   }  
  
   selectedIndex = document.SigTrackingPageMainForm.locationIdSelect.selectedIndex ;
   //alert("selectedIndex : "+selectedIndex );
   
   if(selectedIndex != -1 && selectedIndex != 0)
     locationId = document.SigTrackingPageMainForm.locationIdSelect.options[selectedIndex].value ;
 
   if(roleId == 4) 
   {
        // alert("inside the alert disable"); 
        document.SigTrackingPageMainForm.locationIdSelect.disabled = true;
   }  

   //alert("locationId : "+locationId);
   if(locationId == 'Select' || locationId == '')
     locationId =document.getElementById("locationIdHidden").value ;
   //alert("locationId : "+locationId);     
   if(locationId != 0 && locationId != '' && locationId != null)
   {
      //alert("Insideeeeeeee");
      document.SigTrackingPageMainForm.locationValue.value = locationId;
      document.SigTrackingPageMainForm.locationIdSelect.value = locationId ;
   } 
   //var nn=document.SigTrackingPageMainForm.orgValue.value
   //alert("Organization Value :"+nn);
}
  
function disableGo()
{
     var orderNumber = document.SigTrackingPageMainForm.inputNumberText.value;
     //alert("Delivery Id:"+deliveryName+":Delivery Id");
     if ( orderNumber == ''){
        document.getElementById("goButton1").disabled = true;
     }else{
        document.getElementById("goButton1").disabled = false;
     }
}

function getLocations()
{
  //alert("Inside getLocations()");
  
  var roleId = document.SigTrackingPageMainForm.roleId.value ;
 // alert("roleId : "+roleId);
  
  var clientId = "" ;
  var locationId = "" ;
  if(roleId == 2)
  {
      clientId = document.SigTrackingPageMainForm.clientIdSelect.options[document.SigTrackingPageMainForm.clientIdSelect.selectedIndex].value ;
  }
  else{
     clientId = document.SigTrackingPageMainForm.clientIdHidden.value ; 
     //locationId = document.SigTrackingPageMainForm.locationIdHidden.value ;
  }
  //alert("clientId : "+clientId);
  //alert("locationId : "+locationId);
      
  var locationIds = new Array();
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
      //alert("resp string dummy : "+responseStringDummy);
      var index = responseStringDummy.indexOf("@");
      responseStringDummy = responseStringDummy.substring(index+3);
      //alert("resp string dummy : "+responseStringDummy);
      if( responseStringDummy != '' && responseStringDummy!=null)
      {
          locationIds = responseStringDummy.split("***");       
          for (var i=0; i < locationIds.length ; i++ )
          { 
              //alert("invOrgIds[i] : "+invOrgIds[i]);
              var locationName = locationIds[i].substring(0 , locationIds[i].indexOf('$'));
              //alert('locationName : '+locationName);
              var locationId = locationIds[i].substring(locationIds[i].indexOf('$')+1);
              //alert('locationId : '+locationId);
              document.forms['SigTrackingPageMainForm'].locationIdSelect.options[0] =  new Option("Select","Select",true,true);
              document.forms['SigTrackingPageMainForm'].locationIdSelect.options[i+1] =  new Option(locationName , locationId , true , false) ;  // new Option(invOrgIds[i],invOrgIds[i],true,false);
              document.getElementById("txtMessage").innerHTML=" ";             
          } 
      } 
         
      if( responseStringDummy == 'none') // checking for null values at first time
      {          
        //alert("Inside if 2");      
        document.forms['SigTrackingPageMainForm'].locationIdSelect.options[0] =  new Option("Select","Select",true,true);
        document.getElementById("txtMessage").innerHTML=" ";       
      } 
    }
    
    var locationIdHidden = document.SigTrackingPageMainForm.locationIdHidden.value ;
//    alert("locationIdHidden : "+locationIdHidden);
//    if((roleId == 2 || roleId == 4 || roleId == 5) )
//    {
        if(locationIdHidden != "" && locationIdHidden != null && locationIdHidden != 0)
        {
//           alert("Inside if");
           document.SigTrackingPageMainForm.locationIdSelect.value = locationIdHidden;
        }
//    }
    
  }

  var url="aascAjaxLocationsInfo.jsp?clientId="+clientId;
  xmlHttp.open("GET",url,false);  // Calling 
  xmlHttp.send(null);       

}

function setActionType(actionType)
{
    if(actionType == 'TRACKING' || actionType == 'WAYBILL'){
        var carrierName = document.getElementById('carrierNameId').value;

        if(carrierName == 'Stamps.com'){   //Removed DHL as we implemented DHL Tracking
            alert('Tracking facility is not available for the selected carrier ');
            return false;
        }    
    }
   var orderNumber = document.getElementById("inputNumberText").value;
    if (orderNumber != "" && orderNumber != null && (orderNumber.substring(0,2))!="SC") {
         var orderNumberTemp=encodeURIComponent(orderNumber);
         document.getElementById("orderNameEncode").value = orderNumberTemp;
    }
   document.SigTrackingPageMainForm.mainActionType.value = actionType ;
}

  
  //////////////////////////////////////////////////////////////////////////////////////
  // FOR  CALENDER 
  ///////////////////////////////////////////////////////////////////////////////////////




 
 
//Date created: 11-Sep-2007 16:19
//Author: Sambit
//Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.


//Global variables
var winCal;
var dtToday=new Date();
var Cal;
var docCal;
var MonthName=["January", "February", "March", "April", "May", "June","July", 
	"August", "September", "October", "November", "December"];
var WeekDayName=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];	
var exDateTime;//Existing Date and Time

//Configurable parameters
var cnTop="200";//top coordinate of calendar window.
var cnLeft="550";//left coordinate of calendar window
var WindowTitle ="SC Calender";//Date Time Picker title.
var WeekChar=3;//number of character for week day. if 2 then Mo,Tu,We. if 3 then Mon,Tue,Wed.
var CellWidth=20;//Width of day cell.
var DateSeparator="-";//Date Separator, you can change it to "/" if you want.
var TimeMode=24;//default TimeMode value. 12 or 24

var ShowLongMonth=true;//Show long month name in Calendar header. example: "January".
var ShowMonthYear=true;//Show Month and Year in Calendar header.
var MonthYearColor="#cc0033";//Font Color of Month and Year in Calendar header.
var WeekHeadColor="#0099CC";//Background Color in Week header.
var SundayColor="#6699FF";//Background color of Sunday.
var SaturdayColor="#CCCCFF";//Background color of Saturday.
var WeekDayColor="white";//Background color of weekdays.
var FontColor="blue";//color of font in Calendar day cell.
var TodayColor="#FFFF33";//Background color of today.
var SelDateColor="#FFFF99";//Backgrond color of selected date in textbox.
var YrSelColor="#cc0033";//color of font of Year selector.
var ThemeBg="";//Background image of Calendar window.
//end Configurable parameters
//end Global variable

function NewCal(pCtrl,pFormat,pShowTime,pTimeMode)
{
  Cal=new Calendar(dtToday);
	if ((pShowTime!=null) && (pShowTime))
	{
		Cal.ShowTime=true;
		if ((pTimeMode!=null) &&((pTimeMode=='12')||(pTimeMode=='24')))
		{
			TimeMode=pTimeMode;
		}		
	}	
	if (pCtrl!=null)
		Cal.Ctrl=pCtrl;
	if (pFormat!=null)
		Cal.Format=pFormat.toUpperCase();
	
	exDateTime=document.getElementById(pCtrl).value;
	if (exDateTime!="")//Parse Date String
	{
		var Sp1;//Index of Date Separator 1
		var Sp2;//Index of Date Separator 2 
		var tSp1;//Index of Time Separator 1
		var tSp1;//Index of Time Separator 2
		var strMonth;
		var strDate;
		var strYear;
		var intMonth;
		var YearPattern;
		var strHour;
		var strMinute;
		var strSecond;
		//parse month
		Sp1=exDateTime.indexOf(DateSeparator,0)
		Sp2=exDateTime.indexOf(DateSeparator,(parseInt(Sp1)+1));
		
		if ((Cal.Format.toUpperCase()=="DDMMYYYY") || (Cal.Format.toUpperCase()=="DDMMMYYYY"))
		{
			strMonth=exDateTime.substring(Sp1+1,Sp2);
			strDate=exDateTime.substring(0,Sp1);
		}
		else if ((Cal.Format.toUpperCase()=="MMDDYYYY") || (Cal.Format.toUpperCase()=="MMMDDYYYY"))
		{
			strMonth=exDateTime.substring(0,Sp1);
			strDate=exDateTime.substring(Sp1+1,Sp2);
		}
		if (isNaN(strMonth))
			intMonth=Cal.GetMonthIndex(strMonth);
		else
			intMonth=parseInt(strMonth,10)-1;	
		if ((parseInt(intMonth,10)>=0) && (parseInt(intMonth,10)<12))
			Cal.Month=intMonth;
		//end parse month
		//parse Date
		if ((parseInt(strDate,10)<=Cal.GetMonDays()) && (parseInt(strDate,10)>=1))
			Cal.Date=strDate;
		//end parse Date
		//parse year
		strYear=exDateTime.substring(Sp2+1,Sp2+5);
		YearPattern=/^\d{4}$/;
		if (YearPattern.test(strYear))
			Cal.Year=parseInt(strYear,10);
		//end parse year
		//parse time
		if (Cal.ShowTime==true)
		{
			tSp1=exDateTime.indexOf(":",0)
			tSp2=exDateTime.indexOf(":",(parseInt(tSp1)+1));
			strHour=exDateTime.substring(tSp1,(tSp1)-2);
			Cal.SetHour(strHour);
			strMinute=exDateTime.substring(tSp1+1,tSp2);
			Cal.SetMinute(strMinute);
			strSecond=exDateTime.substring(tSp2+1,tSp2+3);
			Cal.SetSecond(strSecond);
		}	
	}
	winCal=window.open("","DateTimePicker","toolbar=0,status=0,menubar=0,fullscreen=no,width=255,alwaysRaised=yes,height=240,resizable=0,top="+cnTop+",left="+cnLeft);
	docCal=winCal.document;
	RenderCal();
  //document.SigTrackingPageMainForm.ShipmentTrackDate.focus();
}

function RenderCal()
{
	var vCalHeader;
	var vCalData;
	var vCalTime;
	var i;
	var j;
	var SelectStr;
	var vDayCount=0;
	var vFirstDay;

	docCal.open();
	docCal.writeln("<html><head><title>"+WindowTitle+"</title>");
	docCal.writeln("<script>var winMain=window.opener;</script>");
	docCal.writeln("</head><body class='gradient' background='"+ThemeBg+"' link="+FontColor+" vlink="+FontColor+"><form name='Calendar'>");

	vCalHeader="<table border=1 cellpadding=1 cellspacing=1 width='100%' align=\"center\" valign=\"top\">\n";
	//Month Selector
	vCalHeader+="<tr>\n<td colspan='7'><table border=0 width='100%' cellpadding=0 cellspacing=0><tr><td align='left'>\n";
	vCalHeader+="<select name=\"MonthSelector\" onChange=\"javascript:winMain.Cal.SwitchMth(this.selectedIndex);winMain.RenderCal();\">\n";
	for (i=0;i<12;i++)
	{
		if (i==Cal.Month)
			SelectStr="Selected";
		else
			SelectStr="";	
		vCalHeader+="<option "+SelectStr+" value >"+MonthName[i]+"\n";
	}
	vCalHeader+="</select></td>";
	//Year selector
	vCalHeader+="\n<td align='right'><a href=\"javascript:winMain.Cal.DecYear();winMain.RenderCal()\"><b><font color=\""+YrSelColor+"\"><</font></b></a><font face=\"Verdana\" color=\""+YrSelColor+"\" size=2><b> "+Cal.Year+" </b></font><a href=\"javascript:winMain.Cal.IncYear();winMain.RenderCal()\"><b><font color=\""+YrSelColor+"\">></font></b></a></td></tr></table></td>\n";	
	vCalHeader+="</tr>";
	//Calendar header shows Month and Year
	if (ShowMonthYear)
		vCalHeader+="<tr><td colspan='7'><font face='Verdana' size='2' align='center' color='"+MonthYearColor+"'><b>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</b></font></td></tr>\n";
	//Week day header
	vCalHeader+="<tr bgcolor="+WeekHeadColor+">";
	for (i=0;i<7;i++)
	{
		vCalHeader+="<td align='center'><font face='Verdana' size='2'>"+WeekDayName[i].substr(0,WeekChar)+"</font></td>";
	}
	vCalHeader+="</tr>";	
	docCal.write(vCalHeader);
	
	//Calendar detail
	CalDate=new Date(Cal.Year,Cal.Month);
	CalDate.setDate(1);
	vFirstDay=CalDate.getDay();
	vCalData="<tr>";
	for (i=0;i<vFirstDay;i++)
	{
		vCalData=vCalData+GenCell();
		vDayCount=vDayCount+1;
	}
	for (j=1;j<=Cal.GetMonDays();j++)
	{
		var strCell;
		vDayCount=vDayCount+1;
		if ((j==dtToday.getDate())&&(Cal.Month==dtToday.getMonth())&&(Cal.Year==dtToday.getFullYear()))
			strCell=GenCell(j,true,TodayColor);//Highlight today's date
		else
		{
			if (j==Cal.Date)
			{
				strCell=GenCell(j,true,SelDateColor);
			}
			else
			{	 
				if (vDayCount%7==0)
					strCell=GenCell(j,false,SaturdayColor);
				else if ((vDayCount+6)%7==0)
					strCell=GenCell(j,false,SundayColor);
				else
					strCell=GenCell(j,null,WeekDayColor);
			}		
		}						
		vCalData=vCalData+strCell;

		if((vDayCount%7==0)&&(j<Cal.GetMonDays()))
		{
			vCalData=vCalData+"</tr>\n<tr>";
		}
	}
	docCal.writeln(vCalData);	
	//Time picker
	if (Cal.ShowTime)
	{
		var showHour;
		showHour=Cal.getShowHour();		
		vCalTime="<tr>\n<td colspan='7' align='center'>";
		vCalTime+="<input type='text' name='hour' maxlength=2 size=1 style=\"WIDTH: 22px\" value="+showHour+" onchange=\"javascript:winMain.Cal.SetHour(this.value)\">";
		vCalTime+=" : ";
		vCalTime+="<input type='text' name='minute' maxlength=2 size=1 style=\"WIDTH: 22px\" value="+Cal.Minutes+" onchange=\"javascript:winMain.Cal.SetMinute(this.value)\">";
		vCalTime+=" : ";
		vCalTime+="<input type='text' name='second' maxlength=2 size=1 style=\"WIDTH: 22px\" value="+Cal.Seconds+" onchange=\"javascript:winMain.Cal.SetSecond(this.value)\">";
		if (TimeMode==12)
		{
			var SelectAm =(parseInt(Cal.Hours,10)<12)? "Selected":"";
			var SelectPm =(parseInt(Cal.Hours,10)>=12)? "Selected":"";

			vCalTime+="<select name=\"ampm\" onchange=\"javascript:winMain.Cal.SetAmPm(this.options[this.selectedIndex].value);\">";
			vCalTime+="<option "+SelectAm+" value=\"AM\">AM</option>";
			vCalTime+="<option "+SelectPm+" value=\"PM\">PM<option>";
			vCalTime+="</select>";
		}	
		vCalTime+="\n</td>\n</tr>";
		docCal.write(vCalTime);
	}	
	//end time picker
  var white="White";
  docCal.writeln("\n<tr bgcolor="+WeekHeadColor+"><B><font color="+white+"><div align=\"right\">* Ship Console &nbsp;&nbsp;</div></font></B></tr></table>");
	docCal.writeln("</form></body></html>");
	docCal.close();
}

function GenCell(pValue,pHighLight,pColor)//Generate table cell with value
{
	var PValue;
	var PCellStr;
	var vColor;
	var vHLstr1;//HighLight string
	var vHlstr2;
	var vTimeStr;
	
	if (pValue==null)
		PValue="";
	else
		PValue=pValue;
	
	if (pColor!=null)
		vColor="bgcolor=\""+pColor+"\"";
	else
		vColor="";	
	if ((pHighLight!=null)&&(pHighLight))
		{vHLstr1="color='red'><b>";vHLstr2="</b>";}
	else
		{vHLstr1=">";vHLstr2="";}	
	
	if (Cal.ShowTime)
	{
		vTimeStr="winMain.document.getElementById('"+Cal.Ctrl+"').value+=' '+"+"winMain.Cal.getShowHour()"+"+':'+"+"winMain.Cal.Minutes"+"+':'+"+"winMain.Cal.Seconds";
		if (TimeMode==12)
			vTimeStr+="+' '+winMain.Cal.AMorPM";
	}	
	else
		vTimeStr="";		
	PCellStr="<td "+vColor+" width="+CellWidth+" align='center'><font face='verdana' size='2'"+vHLstr1+"<a href=\"javascript:winMain.document.getElementById('"+Cal.Ctrl+"').value='"+Cal.FormatDate(PValue)+"';"+vTimeStr+";window.close();\">"+PValue+"</a>"+vHLstr2+"</font></td>";
	return PCellStr;
}

function Calendar(pDate,pCtrl)
{
	//Properties
	this.Date=pDate.getDate();//selected date
	this.Month=pDate.getMonth();//selected month number
	this.Year=pDate.getFullYear();//selected year in 4 digits
	this.Hours=pDate.getHours();	
	
	if (pDate.getMinutes()<10)
		this.Minutes="0"+pDate.getMinutes();
	else
		this.Minutes=pDate.getMinutes();
	
	if (pDate.getSeconds()<10)
		this.Seconds="0"+pDate.getSeconds();
	else		
		this.Seconds=pDate.getSeconds();
		
	this.MyWindow=winCal;
	this.Ctrl=pCtrl;
	this.Format="ddMMyyyy";
	this.Separator=DateSeparator;
	this.ShowTime=false;
	if (pDate.getHours()<12)
		this.AMorPM="AM";
	else
		this.AMorPM="PM";	
}

function GetMonthIndex(shortMonthName)
{
	for (i=0;i<12;i++)
	{
		if (MonthName[i].substring(0,3).toUpperCase()==shortMonthName.toUpperCase())
		{	return i;}
	}
}
Calendar.prototype.GetMonthIndex=GetMonthIndex;

function IncYear()
{	Cal.Year++;}
Calendar.prototype.IncYear=IncYear;

function DecYear()
{	Cal.Year--;}
Calendar.prototype.DecYear=DecYear;
	
function SwitchMth(intMth)
{	Cal.Month=intMth;}
Calendar.prototype.SwitchMth=SwitchMth;

function SetHour(intHour)
{	
	var MaxHour;
	var MinHour;
	if (TimeMode==24)
	{	MaxHour=23;MinHour=0}
	else if (TimeMode==12)
	{	MaxHour=12;MinHour=1}
	else
		alert("TimeMode can only be 12 or 24");		
	var HourExp=new RegExp("^\\d\\d$");
	if (HourExp.test(intHour) && (parseInt(intHour,10)<=MaxHour) && (parseInt(intHour,10)>=MinHour))
	{	
		if ((TimeMode==12) && (Cal.AMorPM=="PM"))
		{
			if (parseInt(intHour,10)==12)
				Cal.Hours=12;
			else	
				Cal.Hours=parseInt(intHour,10)+12;
		}	
		else if ((TimeMode==12) && (Cal.AMorPM=="AM"))
		{
			if (intHour==12)
				intHour-=12;
			Cal.Hours=parseInt(intHour,10);
		}
		else if (TimeMode==24)
			Cal.Hours=parseInt(intHour,10);	
	}
}
Calendar.prototype.SetHour=SetHour;

function SetMinute(intMin)
{
	var MinExp=new RegExp("^\\d\\d$");
	if (MinExp.test(intMin) && (intMin<60))
		Cal.Minutes=intMin;
}
Calendar.prototype.SetMinute=SetMinute;

function SetSecond(intSec)
{	
	var SecExp=new RegExp("^\\d\\d$");
	if (SecExp.test(intSec) && (intSec<60))
		Cal.Seconds=intSec;
}
Calendar.prototype.SetSecond=SetSecond;

function SetAmPm(pvalue)
{
	this.AMorPM=pvalue;
	if (pvalue=="PM")
	{
		this.Hours=(parseInt(this.Hours,10))+12;
		if (this.Hours==24)
			this.Hours=12;
	}	
	else if (pvalue=="AM")
		this.Hours-=12;	
}
Calendar.prototype.SetAmPm=SetAmPm;

function getShowHour()
{
	var finalHour;
    if (TimeMode==12)
    {
    	if (parseInt(this.Hours,10)==0)
		{
			this.AMorPM="AM";
			finalHour=parseInt(this.Hours,10)+12;	
		}
		else if (parseInt(this.Hours,10)==12)
		{
			this.AMorPM="PM";
			finalHour=12;
		}		
		else if (this.Hours>12)
		{
			this.AMorPM="PM";
			if ((this.Hours-12)<10)
				finalHour="0"+((parseInt(this.Hours,10))-12);
			else
				finalHour=parseInt(this.Hours,10)-12;	
		}
		else
		{
			this.AMorPM="AM";
			if (this.Hours<10)
				finalHour="0"+parseInt(this.Hours,10);
			else
				finalHour=this.Hours;	
		}
	}
	else if (TimeMode==24)
	{
		if (this.Hours<10)
			finalHour="0"+parseInt(this.Hours,10);
		else	
			finalHour=this.Hours;
	}	
	return finalHour;	
}				
Calendar.prototype.getShowHour=getShowHour;		

function GetMonthName(IsLong)
{
	var Month=MonthName[this.Month];
	if (IsLong)
		return Month;
	else
		return Month.substr(0,3);
}
Calendar.prototype.GetMonthName=GetMonthName;

function GetMonDays()//Get number of days in a month
{
	var DaysInMonth=[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if (this.IsLeapYear())
	{
		DaysInMonth[1]=29;
	}	
	return DaysInMonth[this.Month];	
}
Calendar.prototype.GetMonDays=GetMonDays;

function IsLeapYear()
{
	if ((this.Year%4)==0)
	{
		if ((this.Year%100==0) && (this.Year%400)!=0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		return false;
	}
}
Calendar.prototype.IsLeapYear=IsLeapYear;

function FormatDate(pDate)
{
	if (this.Format.toUpperCase()=="DDMMYYYY")
		return (pDate+DateSeparator+(this.Month+1)+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="DDMMMYYYY")
		return (pDate+DateSeparator+this.GetMonthName(false)+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMDDYYYY")
		return ((this.Month+1)+DateSeparator+pDate+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMMDDYYYY")
		return (this.GetMonthName(false)+DateSeparator+pDate+DateSeparator+this.Year);			
}
Calendar.prototype.FormatDate=FormatDate;	

//added for online help

function openNewWindow (URL, windowName, windowOptions)
      {
          var window = getWindow (URL, windowName, windowOptions);
      }

      function getWindow(URL, windowName, windowOptions)
      {
        //alert("URL"+URL);
        //alert("windowName"+windowName);
        //alert("windowOptions"+windowOptions);
          var newWindow = open (URL, windowName, windowOptions)
          newWindow.focus();
          return window;
      }

  //////////////////////////////////////////////////////////////////////////////////////
  // FOR  CALENDER 
  ///////////////////////////////////////////////////////////////////////////////////////
  
  
