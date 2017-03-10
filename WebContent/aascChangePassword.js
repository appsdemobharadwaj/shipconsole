/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascChangePassword.jsp and                     |
|    @Author: Suman Gunda  04/02/2015                                                                       |
+===========================================================================*/
/*========================================================================================
Date        Resource       Change history
------------------------------------------------------------------------------------------

========================================================================================*/
function save(){
    
    var oldpassword = document.getElementById('oldPasswordCPwdID').value;
    var newpassword = document.getElementById('newPasswordCPwdID').value;
    var userName = document.getElementById('userNameCPwdID').value;
    var retypepassword = document.getElementById('reTypePasswordCPwdID').value;

    if(oldpassword.length == 0 || oldpassword.length == null ){
        alert("Please enter Old Password");
        document.changePasswordForm.oldPasswordCPwd.focus();
        return false;
    }

    if(newpassword.length == 0 || newpassword.length == null ){
        alert("Please enter New Password");
        document.changePasswordForm.newPasswordCPwd.focus();
        return false;
    }
    if(newpassword ==oldpassword){
        alert("Old Password and New Password are Identical.\nPlease Enter different Password");
        document.getElementById('newPasswordCPwdID').value="";
        document.getElementById('reTypePasswordCPwdID').value="";
        document.getElementById('newPasswordCPwdID').focus();
        return false;
    }

    if(newpassword !=retypepassword){
        alert("Passwords Did not Match ");
        document.getElementById('newPasswordCPwdID').value="";
        document.getElementById('reTypePasswordCPwdID').value="";
        document.getElementById('newPasswordCPwdID').focus();
        return false;
    }


    if(userName == newpassword){
        alert("User name and password cannot be same");
        document.getElementById('newPasswordCPwdID').value="";
        document.getElementById('reTypePasswordCPwdID').value="";
        document.getElementById('newPasswordCPwdID').focus();
        return false;
    }
    else if(validatePassward(newpassword)||!(isSomeSpclChar(newpassword))||!(isSomeSpclChar(retypepassword)))
    {
        alert("Password must contain 6 to 12 char and must have one letter, number and special character. ");
        document.getElementById('newPasswordCPwdID').value="";
        document.getElementById('reTypePasswordCPwdID').value="";
        document.getElementById('newPasswordCPwdID').focus();
        return false;
    }
}

function onload(){

    var status = document.getElementById('statusID').value;
    if(status==1){
        alert("Your Password is Successfully Changed");
        window.close(); 
    }
    else if(status==0||status==10){
        alert("Error in Changing the Password.");
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

function isSomeSpclChar(variable){																										
    //alert(variable);																									
    var iChars = "!@#$%^*+=[]\\\';,.{}|\:<>?";																						
    for (var i = 0; i < variable.length; i++) {																									
        if (iChars.indexOf(variable.charAt(i)) != -1) {																							
            return true;																											
        }																										
    }																										
}	
