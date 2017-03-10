/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascForgotPassword.jsp and                     |
|    @Author: Suman Gunda  04/02/2015                                       |
+===========================================================================*/
/*========================================================================================
Date        Resource       Change history
03/08/15    Rakesh K       Removed "'Error in changing the password" to fix #3304
------------------------------------------------------------------------------------------

========================================================================================*/


function onload(){

    var status = document.getElementById('statusID').value;
    if(status==0||status==10){
        //alert("Error in Changing the Password.");
    }
    if(status == 20){
        alert("Password not sent to mail, generate again");
    }
    document.forgotPasswordForm.userName.focus();

}

function validateFields()
{

var email = document.forgotPasswordForm.userName.value;
    
    if(email.length == 0 || email.length == null ){
        alert("Please enter User Name ");
        document.forgotPasswordForm.userName.focus();
        return false;
    }
    else if(email.length > 50){
        alert("User Name cannot be greater than 50 char");
        document.forgotPasswordForm.userName.focus();
        return false;
    }
    else if(!validateEmail(email)){
        alert("Please enter valid User Name");
        document.forgotPasswordForm.userName.focus();
        return false;
    }
   
    if(document.forgotPasswordForm.SaveButtonId.value == "0")
    {
        document.forgotPasswordForm.SaveButtonId.value="1";
        document.forgotPasswordForm.actiontype.value='forgotPassword';
        document.forgotPasswordForm.submit();
    }
    else
    {
        alert("Request already submitted. Please Wait.");
        return false; 
    }

    return true;       
}//end of validateFields   

function validateEmail(x)
{
    var atpos=x.indexOf("@");
    var dotpos=x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){
        return false;
    }
    else
    {
        return true;
    }
}

//----------------------------------------------------------------------------------------------------------------
//Password must contain 6 to 12 char and must have one letter, number and special character.
function validatePassward(x)
{
    var letters = /^(?=.*\d)(?=.*[a-z]).{6,12}$/; 
    if(x.match(letters)){
        return false;
        //for alpha alphanumeric 
    }
    else
        return true;
}
