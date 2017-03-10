/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascShipMethodMapping.jsp                      |
|    Author 
|    Modified 
|    Version   1.0                                                          |                                                                            
+===========================================================================*/
/*========================================================================================
Date        Resource       Change history
------------------------------------------------------------------------------------------ 
10/12/2014  Y Pradeep      Added code to disable fields based on role.
24/12/2014  Y Pradeep      Modifed code after fixing issue while deleting shipmethod mapping.
13/01/2015  Y Pradeep      Added for loop to check checkbox is checked to fix bug #2489 in deleteShipMethod() function.
19/01/2015  Y Pradeep      Removed alert in deleteShipMethod() function for bug #2562.
20/01/2015  Y Pradeep      Removed unncessary comments in history from SC cloud.
14/03/2015  Y Pradeep      Modified code after UI changes to save and delete newly added rows.
31/03/2015  Y Pradeep      Modified code to give allert to enter shipmethod name for empty records. Bug #2740.
07/04/2015  Suman G        Modified code to fix #2811.
10/04/2015  Y Pradeep      Added stopEnterKeyPress function to avoid form submition when enter button is pressed any field is focused in jsp page.
08/06/2015  K Sunanda      Modified code for bug fix #2984
22/06/2015  Naresh         Modified code for bug fix #3046
06/08/2015  Dinakar G      Added code to fix issue #3263 & #3269
06/11/2015  Shiva  G       Modified code to fix the issue 3887
========================================================================================*/
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




function addrow(){

        var x1 = document.getElementById('PacTab').rows.length;
        var p = x1;
      var lineNum=p-1;
      var carrierServiceValue="",carrierServiceLeveText="", carrierServiceLeveOptions="";
      var validationValue="",validationText="", validationOptions="";

    for(var i=0; i<document.getElementById('carrierServiceLevelID'+lineNum).length ; i++)
        {
         carrierServiceValue=document.getElementById('carrierServiceLevelID'+lineNum).options[i].value;
         carrierServiceLeveText=document.getElementById('carrierServiceLevelID'+lineNum).options[i].text;
         
         var selectedUOMVal= document.getElementById('carrierServiceLevelID'+lineNum).options[document.getElementById('carrierServiceLevelID'+lineNum).selectedIndex].value;
        var selectedUOMText= document.getElementById('carrierServiceLevelID'+lineNum).options[document.getElementById('carrierServiceLevelID'+lineNum).selectedIndex].text;

         if((selectedUOMText == carrierServiceLeveText)) // && (selectedDimText == dText))
         {

       carrierServiceLeveOptions=carrierServiceLeveOptions+'<option value ="'+carrierServiceValue+'" selected>'+carrierServiceLeveText+'</option>';
       }else
       {
       carrierServiceLeveOptions=carrierServiceLeveOptions+'<option value ="'+carrierServiceValue+'" >'+carrierServiceLeveText+'</option>';

        }
    
      }
      
      
       for(var i=0; i<document.getElementById('validationID'+lineNum).length ; i++)
        {
         validationValue=document.getElementById('validationID'+lineNum).options[i].value;
         validationText=document.getElementById('validationID'+lineNum).options[i].text;
         
         var selectedVal= document.getElementById('validationID'+lineNum).options[document.getElementById('validationID'+lineNum).selectedIndex].value;
        var selectedText= document.getElementById('validationID'+lineNum).options[document.getElementById('validationID'+lineNum).selectedIndex].text;

         if((selectedText == validationText)) // && (selectedDimText == dText))
         {

       validationOptions=validationOptions+'<option value ="'+validationValue+'" selected>'+validationText+'</option>';
       }else
       {
       validationOptions=validationOptions+'<option value ="'+validationValue+'" >'+validationText+'</option>';

        }
    
      }
   
            var x=document.getElementById('PacTab').insertRow(x1);

              var y1=x.insertCell(0);
            var y2=x.insertCell(1);
            var y3=x.insertCell(2);
            var y4=x.insertCell(3);
            var y5=x.insertCell(4);
            var y6=x.insertCell(5);
            var y7=x.insertCell(6);
        y1.innerHTML='<div id="sno'+p+'" align="center"><b>'+p+'</b></div> <input type="hidden" name="shipmethodIndex'+p+'" id="shipmethodIndexID'+p+'" value="0" />';
                    y2.innerHTML='<div align="center"><input type="checkbox" name="shipMethod'+p+'"  id="shipMethodID'+p+'"  onclick="checkBoxValue(this)" value=""  /></div>';
            y3.innerHTML='<div align="center"><input type="text" name="userdefinedShipmethod'+p+'" class="inputs"  size="32" onblur="" value="" /></div>';
            y4.innerHTML='<div align="center"><select  name="carrierServiceLevel'+p+'" id="carrierServiceLevelID'+p+'" class="inputs" >'+carrierServiceLeveOptions+' </select></div>';
               y5.innerHTML='<div align="center"><select  name="validation'+p+'" id="validationID'+p+'" class="inputs" >'+validationOptions+' </select></div>';
           // y3.innerHTML='<s:select  name="validation'+x1+'" value="" id="validationID'+x1+'" onmouseover="showTip(this)" onmouseout="hideTip()" /> ';
            y6.innerHTML='<div align="center"><input type="checkbox" name="enabledFlag'+p+'" onclick="checkBoxValue(this)"  fieldValue=""  value=""/></div>';
            y7.innerHTML='<div align="center"><input type="checkbox" name="international'+p+'" onclick="checkBoxValue(this)" value=""  /></div>';
    
        document.getElementById('deleteId').disabled=false; 
    
        //    document.getElementById('childDelNoID'+x1).focus();
  return false;  
}

function checkBoxValue(checkbox)
{
  var Chkname=checkbox.name;     
  if(document.DynaAascShipMethodMappingForm[Chkname].checked==true)
  { 
    document.DynaAascShipMethodMappingForm[Chkname].value="Y";
  }
  else
  {      
    document.DynaAascShipMethodMappingForm[Chkname].checked=false;
    document.DynaAascShipMethodMappingForm[Chkname].value="N";   
  }
  
}
function validCarrierName()
{


  var actionType = document.getElementById('submitID').value;
  var carrierCode = document.DynaAascShipMethodMappingForm.carrierCode.value;
  var totalCount = document.getElementById('PacTab').rows.length-1;

 if (actionType=="SaveUpdate")
 {
  if (validShipMethodMeaning()==false){

                                        return false;
                                      }
   if(carrierCode == 100 || carrierCode == 110 || carrierCode== 111 || carrierCode== 115 || carrierCode== 114)
   {
  
          for(var index =  1;index<=totalCount;index ++)
          {
            var enableFlag = document.DynaAascShipMethodMappingForm['enabledFlag'+index].checked;
            if(enableFlag==true)
            {
                    if((document.DynaAascShipMethodMappingForm['validation'+index].value=="") || (document.DynaAascShipMethodMappingForm['validation'+index].value=="Select"))
                    {
                      alert("Please Select Validation");
                      document.DynaAascShipMethodMappingForm['validation'+index].focus();
                      return false;
                    }
                    
                     if((document.DynaAascShipMethodMappingForm['carrierServiceLevel'+index].value=="")|| (document.DynaAascShipMethodMappingForm['carrierServiceLevel'+index].value=="Select"))
                    {
                      alert("Please Select Carrier Service Level");
                      document.DynaAascShipMethodMappingForm['carrierServiceLevel'+index].focus();
                      return false;
                    }
             }      
         
          }
     }
  }
 
 return true;
}


function DisableField()
{
      var totalCount = document.getElementById('PacTab').rows.length-1;
      if(document.DynaAascShipMethodMappingForm.roleIdHidden.value !=3 && document.DynaAascShipMethodMappingForm.roleIdHidden.value!=1)
      {
          for(var index =  1;index<=totalCount;index ++)
          {
            document.DynaAascShipMethodMappingForm['shipMethod'+index].disabled=true; 
            document.DynaAascShipMethodMappingForm['carrierServiceLevel'+index].disabled=true; 
            document.DynaAascShipMethodMappingForm['validation'+index].disabled=true; 
            document.DynaAascShipMethodMappingForm['userdefinedShipmethod'+index].disabled=true; 
            document.DynaAascShipMethodMappingForm['enabledFlag'+index].disabled=true; 
            document.DynaAascShipMethodMappingForm['international'+index].disabled=true; 
          }
     }
     else {
        if(totalCount == 1) {
            document.getElementById('deleteId').disabled=true; 
        }
        else {
            document.getElementById('deleteId').disabled=false; 
        }
    }
}

function saverows(){
        var noRows = document.getElementById('PacTab').rows.length;
        document.DynaAascShipMethodMappingForm.totalShipMethods.value = noRows-1;        
        document.getElementById('submitID').value='SaveUpdate';  
}


function deleteShipMethod(){
    var TableLen = document.getElementById('PacTab').rows.length-1;
    var count = 0;
//    alert("TableLen === "+TableLen);
    for(var i=1;i<=TableLen;i++)          // Added for loop to check checkbox is checked to fix bug #2489.
    { 
        if(TableLen != 1){
            if(document.getElementById('shipMethodID'+i).value == 'Y')
            {
                count = 1;
                break;
            }
        }
    }
    if(count == 1){
        ConfirmStatus = confirm("Do You Want To Remove The Rows selected ?")
        if (ConfirmStatus == true){
            for(var i=1;i<=TableLen;i++)
            { 
                if(TableLen != 1){
                    if(document.getElementById('shipMethodID'+i).value == 'Y')
                    {
                            for(j=i;j<TableLen;j++){
                                k=j+1;
                                document.getElementById("sno"+k).innerHTML='<div id="sno'+k+'" align="center"><b>'+k+'</b></div> <input type="hidden" name="shipmethodIndex'+k+'" id="shipmethodIndexID'+k+'" value="0" />';
                                document.getElementById('shipMethodID'+j).value=document.getElementById('shipMethodID'+k).value;
                                document.getElementById('shipMethodID'+j).checked=document.getElementById('shipMethodID'+k).checked;
                                document.DynaAascShipMethodMappingForm['userdefinedShipmethod'+j].value =document.DynaAascShipMethodMappingForm['userdefinedShipmethod'+k].value;
                                document.DynaAascShipMethodMappingForm['carrierServiceLevel'+j].value =document.DynaAascShipMethodMappingForm['carrierServiceLevel'+k].value;
                                document.DynaAascShipMethodMappingForm['validation'+j].value =document.DynaAascShipMethodMappingForm['validation'+k].value;
                                document.DynaAascShipMethodMappingForm['enabledFlag'+j].checked =document.DynaAascShipMethodMappingForm['enabledFlag'+k].checked;
                                document.DynaAascShipMethodMappingForm['enabledFlag'+j].value =document.DynaAascShipMethodMappingForm['enabledFlag'+k].value;
                                document.DynaAascShipMethodMappingForm['international'+j].checked =document.DynaAascShipMethodMappingForm['international'+k].checked;
                                document.DynaAascShipMethodMappingForm['international'+j].value =document.DynaAascShipMethodMappingForm['international'+k].value;
                            }
                            document.getElementById('PacTab').deleteRow(document.getElementById('PacTab').rows.length-1);
                            TableLen--;
                            i--;
                        }
                    document.getElementById('deleteId').disabled=false;
                }
                else {
                    document.getElementById('shipMethodID'+i).checked = false;
                    document.getElementById('shipMethodID'+i).value = "N";
                    document.getElementById('deleteId').disabled=true; 
                }
            }
        }
        count = 0;
    }
    else{
        alert("Please select atleast one row to be deleted and click on delete");
    }
   
   return false;
}

function trimStr(str) {
  return str.replace(/^\s+|\s+$/g, '');
}

function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function validShipMethodMeaning()
{
  
  var carrierCode = document.DynaAascShipMethodMappingForm.carrierCode.value;
  var totalCount =  document.getElementById('PacTab').rows.length-1;
      for(var index = 1;index<=totalCount;index ++)
          {
                 var userdefinedShipmethod = trim(document.DynaAascShipMethodMappingForm['userdefinedShipmethod'+index].value);
                 
                 if(userdefinedShipmethod==""){
                 alert(index+" User Defined Ship Method Should not be empty");
                 document.DynaAascShipMethodMappingForm['userdefinedShipmethod'+index].focus();
                 return false;
                 }
                if(chkSplChars(userdefinedShipmethod)==false)
                 {
                 alert("User Defined Ship Method Should not contain the Special Characters");
                 return false;
                }
                  for (var index1 = index+ 1;index1<=totalCount;index1 ++)
                  {
                  // alert("document.DynaAascShipMethodMappingForm['alternateShipMethod'+index1].value.toUpperCase()=="+document.DynaAascShipMethodMappingForm['alternateShipMethod'+index1].value.toUpperCase());
                  // alert("document.DynaAascShipMethodMappingForm['alternateShipMethod'+index].value).toUpperCase()"+document.DynaAascShipMethodMappingForm['alternateShipMethod'+index].value.toUpperCase()); 
                    if(trim(document.DynaAascShipMethodMappingForm['userdefinedShipmethod'+index1].value).toUpperCase()==trim(document.DynaAascShipMethodMappingForm['userdefinedShipmethod'+index].value).toUpperCase())//Sunanda modified code for bug fix #2984
                    {
                     //alert("The main value :"+document.DynaAascShipMethodMappingForm['alternateShipMethod'+index].value+"index:"+index);
                     // alert("The compared value :"+document.DynaAascShipMethodMappingForm['alternateShipMethod'+index1].value+"index1:"+index1);
                      alert("Duplicate values in User Defined Ship Method at Row "+index+" And " +index1);
                      
                      return false;
                    }
                    
                    
             }      
         
     }

 return true;
}


function chkSplChars(message)
{
var len= (trim(message)).length;
var userDefinedShipmethod = trim(message);
for(var index = 0; index <len;index++)
      {

        var c = userDefinedShipmethod .charAt(index);

         if(c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')'||c=='~' ||c=='+')  //Shiva added '+' for issue 3887

         {
		     	return false;

         }

      }   
}

function trim(str){
         try{
	   return str.replace(/^\s*|\s*$/g,"");
         }catch(err)
             {
              } 
	}

//This funcion "stopEnterKeyPress(evt)"added by Khaja for Browser Campatability
function stopEnterKeyPress(evt) {
  var evt = (evt) ? evt : ((event) ? event : null);
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;}

}
document.onkeypress = stopEnterKeyPress;   ///This line belong to the  above function stopEnterKeyPress(evt) to stop Form submition on press of Enter key
