/*========================================================================+
@(#)aasc_User_Details_js.js 06/08/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
+===========================================================================*/
/*========================================================================================
Date        Resource       Change history
------------------------------------------------------------------------------------------
06/01/2015  Y Pradeep      Merged Sunanda's code : Related to update(), save() functions and comments.
05/01/2015  Suman G        Added code for country validaiton and also removed unused validations for country
15/01/2015  Y Pradeep      Merged Sunanda's code on 1.0 release bugs.
20/01/2015  Sunanda K      Modified code to pass alernate emails address. For bug #2557.
21/01/2015  Sunanda K      Modified code to allow all locations for Admin role. For bug #2571.
10/03/2015  Sunanda K      Added code for preventing backspace rendering to previous page
16/03/2015  Suman G        Commented unnecessary alert.
20/03/2015  Y Pradeep      Modified if condition while saving to disable Location selection alert for role 3 and 5. Bug #2707.
07/04/2015  Y Pradeep      Modified if condition while updating to disable Location selection alert for role 3 and 5. Bug #2707.
14/04/2014  Eshwari M      Modified disableFields() to disable the clear button after save for fixing bug # 2830
========================================================================================*/

//This function stores the current row number value whenever Edit of User Details 

     function methodOnEdit(rowCount)
      {         
        document.UserDetailsForm.currentRow.value=rowCount;
       
      } 
      
        //  function disableEdit()
        //  {
        //document.UpdateUserForm.emailAddress.disabled="true";
        // }

//This function sets the CreateButtonId value to '1' whenever submit button is clicked and 
//If already CreateButtonId value is '1' displays message
      function validateSubmit()
      {
        if(document.UserDetailsForm.CreateButtonId.value == "0")
        {
       
            document.UserDetailsForm.CreateButtonId.value="1";
            return true;
        }
        else
        {
            // alert('entered ELSE');
            alert("Request already submitted. Please Wait.");
            return false; 
        }
      } 



//This function sets the validations for all the User Details whenever save button ic clicked
 function save(){
        var firstName = document.CreateUserForm.firstName.value;
        var lastName = document.CreateUserForm.lastName.value;
        var role = document.CreateUserForm.role.value;
        var locationId = document.CreateUserForm.locationId.value;
        var emailAddress = document.CreateUserForm.emailAddress.value;
        var alternateEmailAddress = document.CreateUserForm.alternateEmailAddress.value;
        var status = document.CreateUserForm.status.value;
        
       
        
        if(firstName.length == 0 || firstName.length == null ){
            alert("Please enter First Name");
            document.CreateUserForm.firstName.focus();
            return false;
        }
               
        if(emailAddress.length == 0 || emailAddress.length == null ){
            alert("Please enter Email Address ");
            document.CreateUserForm.emailAddress.focus();
            return false;
        }
        else if(emailAddress.length > 50){
            alert("Email Address cannot be greater than 50 char");
            document.CreateUserForm.emailAddress.focus();
            return false;
        }
        else if(!validateEmail(emailAddress)){
            alert("Please enter valid Email Address.");
            document.CreateUserForm.emailAddress.focus();
            return false;
        }

        if(alternateEmailAddress==null || alternateEmailAddress=='')
        {
        // alert("inside if");
        }
       else
        {
        if(alternateEmailAddress.length > 50){
            alert("Alternate Email Address cannot be greater than 50 char");
            document.CreateUserForm.alternateEmailAddress.focus();
            return false;
        }
            else if(!validateEmail(alternateEmailAddress)){
                alert("Please enter valid Alternate Email Address.");
            document.CreateUserForm.alternateEmailAddress.focus();
            return false;
            }
          
//            alert("alternateEmailAddress::::"+alternateEmailAddress+":::::::");
//            alert("emailAddress::::"+emailAddress+":::::::");
//            alert("condition :: "+alternateEmailAddress==emailAddress);
            if(alternateEmailAddress==emailAddress)
            {
             alert("Alternate Email Address should not be same as Email Address");
             document.CreateUserForm.alternateEmailAddress.focus();
            return false;
        }
        }
        // Modified if condition to get below alert for role 4 only. By Y Pradeep
        if(role == 4){
            if(locationId =='Select'){
                alert("Please select Location ");
                document.CreateUserForm.locationId.focus();
                return false;
            }
        }
        if(document.CreateUserForm.SaveButtonId.value == "0"){
            //alert('entered IF');
            document.CreateUserForm.SaveButtonId.value="1";
            return true;
        }
        else{
        // alert('entered ELSE');
        alert("Request already submitted. Please Wait.");
        return false; 
        }  
    }
    
      

//This function validates the email address provided in the User Details page
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

//This function disables the UserDetails in the page after saving those details into DB
    function disableFields()
    {
      var key=document.CreateUserForm.key.value;
      if(key == "aasc410")
      {
	  document.CreateUserForm.firstName.disabled="true";
	  document.CreateUserForm.lastName.disabled="true";
          document.CreateUserForm.role.disabled="true";
          document.CreateUserForm.locationId.disabled="true";
          document.CreateUserForm.emailAddress.disabled="true";
          document.CreateUserForm.alternateEmailAddress.disabled="true";
          document.CreateUserForm.status.disabled="true";
          document.getElementById('clearButton').disabled = true ;
          document.getElementById('clearButton').src = "buttons/aascClearOff1.png" ;
      }
    }
    

//This function clears the UserDetails in the page after clear button is clicked
    function clearFields()
    {
      var key=document.CreateUserForm.key.value;
      if(key != "aasc410")
      {
          document.CreateUserForm.firstName.value="";
          document.CreateUserForm.lastName.value="";
          document.CreateUserForm.role.value="3";
          document.CreateUserForm.locationId.value="Select";
          document.CreateUserForm.emailAddress.value="";
          document.CreateUserForm.alternateEmailAddress.value="";
          document.CreateUserForm.status.value="Y";
       }   
    }
    
   function removeSpaces(val) {
   return val.split(' ').join('');
}
//This funcion for Browser Campatability
function stopEnterKeyPress(evt) {
  var evt = (evt) ? evt : ((event) ? event : null);
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
  var k = document.getElementById("deliveryIDID");
//alert("k :"+k+":  node :"+node);
  if(k!=node){
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;}
}
}
document.onkeypress = stopEnterKeyPress;   ///This line belong to the  above function stopEnterKeyPress(evt) to stop Form submition on press of Enter key

// below validations for update user 

  function update()
  {
                 
        var firstName = document.UpdateUserForm.firstName.value;
        var lastName = document.UpdateUserForm.lastName.value;
        var role = document.UpdateUserForm.role.value;
        var locationId = document.UpdateUserForm.locationId.value;
        var emailAddress = document.UpdateUserForm.emailAddress.value;
        var alternateEmailAddress = document.UpdateUserForm.alternateEmailAddress.value;
        var status = document.UpdateUserForm.status.value;
        
       
       if(firstName.length == 0 || firstName.length == null ){
            alert("Please enter First Name");
            document.UpdateUserForm.firstName.focus();
            return false;
        }
        
        if(role == 4){
        if(locationId== 'Select' ){
            alert("Please select Location ");
            document.UpdateUserForm.locationId.focus();
            return false;
        }
        }
       
        if(emailAddress.length == 0 || emailAddress.length == null ){
            alert("Please enter Email Address ");
            document.UpdateUserForm.emailAddress.focus();
            return false;
        }
        else if(emailAddress.length > 50){
            alert("Email Address cannot be greater than 50 char");
            document.UpdateUserForm.emailAddress.focus();
            return false;
        }
        else if(!validateEmail(emailAddress)){
            alert("Please enter valid Email Address.");
            document.UpdateUserForm.emailAddress.focus();
            return false;
        }

       if(alternateEmailAddress==null || alternateEmailAddress=='')
        {
        // alert("inside if");
        }
       else
        {
        if(alternateEmailAddress.length > 50){
            alert("Alternate Email Address cannot be greater than 50 char");
            document.UpdateUserForm.alternateEmailAddress.focus();
            return false;
        }
        else if(!validateEmail(alternateEmailAddress)){
            alert("Please enter valid Alternate Email Address.");
            document.UpdateUserForm.alternateEmailAddress.focus();
            return false;
        }
          
            if(alternateEmailAddress==emailAddress)
            {
             alert("Alternate Email Address should not be same as Email Address");
             document.UpdateUserForm.alternateEmailAddress.focus();
            return false;
            }
        }
                  if(document.UpdateUserForm.UpdateButtonId.value == "0")
                    {
                   // alert('entered IF');
                    document.UpdateUserForm.UpdateButtonId.value="1";
                    return true;
                    }
                  else
                    {
                   // alert('entered ELSE');
                    alert("Request already submitted. Please Wait.");
                    return false; 
                    }
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

//Sunanda code start
        
 function FindKeyCode(e)
  {
    if(e.which)//!=''|| e.which!=null)
    {
    keycode=e.which;  //Netscape
    }
    else
    {
    keycode=e.keyCode; //Internet Explorer
    }

    return keycode;
  }
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

    function backspace(e)
    {
   
    var vEventKeyCode= "";
    var vEventKeyCode = FindKeyCode(e);
  
    // backspace key pressed
    if(vEventKeyCode == 8 || vEventKeyCode==127)
    {
    
    var browser=getBrowser();
//    alert("browser :: "+browser);
    if(browser=="Chrome"||browser=="Safari"||browser=="IE")
    {
    
         e.preventDefault();
        e.returnValue=false;
         
    }
    else
    {

    return false;
        }
        }
    }
    //Sunanda code end
