/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascPrinterSetup.jsp                      |
|    Author 
|    Modified 
|    Version   1.0                                                          |                                                                            
+===========================================================================*/
/*========================================================================================
Date            Resource            Change history
------------------------------------------------------------------------------------------ 
23/06/2015      Y Pradeep           Added this file for Printer Setup Popup page.
13/07/2015      Y Pradeep           Modified code for saving and added Print Server IP Address field and related code.
13/08/2015      N Srisha            Added code in validPrinterSetup method for bug #3380
24/08/2015      Y Pradeep           Commented if condition in addrow function to make LabelFormat LOV to be defaulted to 'Select' when add button is clicked. Bug #3467.
24/08/2015      Y Pradeep           Modified if condition to check LabelFormat LOV contains 'Select' while saving form. Bug #3467.
11/03/2016      Y Pradeep           Removed Server IP Address related code as it is not required.
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
      var labelFormatValue="",labelFormatText="", labelFormatOptions="";

        for(var i=0; i<document.getElementById('labelFormatId'+lineNum).length ; i++)
        {
         labelFormatValue=document.getElementById('labelFormatId'+lineNum).options[i].value;
         labelFormatText=document.getElementById('labelFormatId'+lineNum).options[i].text;
         
         var selectedUOMVal= document.getElementById('labelFormatId'+lineNum).options[document.getElementById('labelFormatId'+lineNum).selectedIndex].value;
        var selectedUOMText= document.getElementById('labelFormatId'+lineNum).options[document.getElementById('labelFormatId'+lineNum).selectedIndex].text;

         //if((selectedUOMText == labelFormatText)) // && (selectedDimText == dText))
         //{

       //labelFormatOptions=labelFormatOptions+'<option value ="'+labelFormatValue+'" selected>'+labelFormatText+'</option>';
       //}else
       //{
       labelFormatOptions=labelFormatOptions+'<option value ="'+labelFormatValue+'" >'+labelFormatText+'</option>';

        //}
    
      }
   
            var x=document.getElementById('PacTab').insertRow(x1);

            var y1=x.insertCell(0);
            var y2=x.insertCell(1);
            var y3=x.insertCell(2);
            var y4=x.insertCell(3);
            var y5=x.insertCell(4);
            
            y1.innerHTML='<div id="sno'+p+'" align="center"><b>'+p+'</b></div> <input type="hidden" name="printerIndex'+p+'" id="printerIndexId'+p+'" value="0" />';
            y2.innerHTML='<div align="center"><input type="checkbox" name="printer'+p+'"  id="printerId'+p+'"  onclick="checkBoxValue(this)" value=""  /></div>';
            y3.innerHTML='<div align="center"><select  name="labelFormat'+p+'" id="labelFormatId'+p+'" class="inputs" style="width:100px;">'+labelFormatOptions+' </select></div>';
            y4.innerHTML='<div align="center"><input type="text" name="printerName'+p+'" id="printerNameId'+p+'" class="inputs" size="32" style="width:200px;" onblur="" value="" /></div>';
            y5.innerHTML='<div align="center"><input type="checkbox" name="enabledFlag'+p+'" id="enabledFlagId'+p+'" onclick="checkBoxValue(this)"  fieldValue=""  value=""/></div>';
    
        document.getElementById('deleteId').disabled=false; 
    
        //    document.getElementById('childDelNoId'+x1).focus();
  return false;  
}

function checkBoxValue(checkbox)
{
  var Chkname=checkbox.name;     
  if(document.PrinterSetupForm[Chkname].checked==true)
  { 
    document.PrinterSetupForm[Chkname].value="Y";
  }
  else
  {      
    document.PrinterSetupForm[Chkname].checked=false;
    document.PrinterSetupForm[Chkname].value="N";   
  }
  
}
function validPrinterName()
{
  var actionType = document.getElementById('submitId').value;
  var totalCount = document.getElementById('PacTab').rows.length-1;

 if (actionType=="SaveUpdate")
 {
  if (validPrinterSetup()==false){

                                        return false;
                                      }
  
          for(var index = 1;index<=totalCount;index ++)
          {
//          alert(document.PrinterSetupForm['labelFormat'+index].value);
            var enableFlag = document.PrinterSetupForm['enabledFlag'+index].checked; 
            if(enableFlag==true)
            {
                     if(document.getElementById('labelFormatId'+index).options[document.getElementById('labelFormatId'+index).selectedIndex].text == "Select")
                    {
                      alert("Please Select a Label Format");
                      document.PrinterSetupForm['labelFormat'+index].focus();
                      return false;
                    }
             }      
         
          }
  }
 
 return true;
}


function DisableField()
{
      var totalCount = document.getElementById('PacTab').rows.length-1;
      if(document.PrinterSetupForm.roleId.value !=3 && document.PrinterSetupForm.roleId.value!=1)
      {
          for(var index =  1;index<=totalCount;index ++)
          {
            document.PrinterSetupForm['printerIndex'+index].disabled=true; 
            document.PrinterSetupForm['labelFormat'+index].disabled=true; 
            document.PrinterSetupForm['printerName'+index].disabled=true; 
            document.PrinterSetupForm['enabledFlag'+index].disabled=true; 
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
        document.PrinterSetupForm.totalPrinters.value = noRows-1;
        document.getElementById('submitId').value='SaveUpdate';  
}


function deletePrinterRow(){
    var TableLen = document.getElementById('PacTab').rows.length-1;
    var count = 0;
//    alert("TableLen === "+TableLen);
    for(var i=1;i<=TableLen;i++)          // Added for loop to check checkbox is checked to fix bug #2489.
    { 
        if(TableLen != 1){
            if(document.getElementById('printerId'+i).value == 'Y')
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
                    if(document.getElementById('printerId'+i).value == 'Y')
                    {
                            for(j=i;j<TableLen;j++){
                                k=j+1;
                                document.getElementById("sno"+k).innerHTML='<div id="sno'+k+'" align="center"><b>'+k+'</b></div> <input type="hidden" name="printerIndex'+k+'" id="printerIndexId'+k+'" value="0" />';
                                document.getElementById('printerId'+j).value=document.getElementById('printerId'+k).value;
                                document.getElementById('printerId'+j).checked=document.getElementById('printerId'+k).checked;
                                document.PrinterSetupForm['printerName'+j].value =document.PrinterSetupForm['printerName'+k].value;
                                document.PrinterSetupForm['labelFormat'+j].value =document.PrinterSetupForm['labelFormat'+k].value;
                                document.PrinterSetupForm['enabledFlag'+j].checked =document.PrinterSetupForm['enabledFlag'+k].checked;
                                document.PrinterSetupForm['enabledFlag'+j].value =document.PrinterSetupForm['enabledFlag'+k].value;
                            }
                            document.getElementById('PacTab').deleteRow(document.getElementById('PacTab').rows.length-1);
                            TableLen--;
                            i--;
                        }
                    document.getElementById('deleteId').disabled=false;
                }
                else {
                    document.getElementById('printerId'+i).checked = false;
                    document.getElementById('printerId'+i).value = "N";
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

function validPrinterSetup()
{
  var totalCount =  document.getElementById('PacTab').rows.length-1;
      for(var index = 1;index<=totalCount;index ++)
          {
                 var printerName = trim(document.PrinterSetupForm['printerName'+index].value);
             // srisha added code for bug #3380
                 if(document.getElementById('labelFormatId'+index).options[document.getElementById('labelFormatId'+index).selectedIndex].text == "Select")
                    {
                      alert("Please Select a Label Format");
                      document.PrinterSetupForm['labelFormat'+index].focus();
                      return false;
                    }
                 //code ends 
                 if(printerName==""){
                 alert(index+" Printer Name Should not be empty");
                 document.PrinterSetupForm['printerName'+index].focus();
                 return false;
                 }
//                if(chkSplChars(printerName)==false)
//                 {
//                 alert("User Defined Ship Method Should not contain the Special Characters");
//                 return false;
//                }
                  for (var index1 = index+ 1;index1<=totalCount;index1 ++)
                  {
                    if(trim(document.getElementById('labelFormatId'+index1).options[document.getElementById('labelFormatId'+index1).selectedIndex].value).toUpperCase() == trim(document.getElementById('labelFormatId'+index).options[document.getElementById('labelFormatId'+index).selectedIndex].value).toUpperCase())
                    {
                      alert("Duplicate Lable Formats at Row "+index+" And " +index1);
                      
                      return false;
                    }  

                    /*if(trim(document.PrinterSetupForm['printerName'+index1].value).toUpperCase()==trim(document.PrinterSetupForm['printerName'+index].value).toUpperCase())
                    {
                      alert("Duplicate values in Printer Name at Row "+index+" And " +index1);
                      
                      return false;
                    }*/
             }      
         
     }

 return true;
}


function chkSplChars(message)
{
var len= (trim(message)).length;
var printerName = trim(message);
for(var index = 0; index <len;index++)
      {

        var c = printerName .charAt(index);

         if(c == ','||c == '!'||c=='@'||c == '#'||c=='$'||c == '%'||c=='^'||c == '&'||c=='*'||c == '('||c==')'||c=='~')  

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

//This funcion "stopEnterKeyPress(evt)"added for Browser Campatability
function stopEnterKeyPress(evt) {
  var evt = (evt) ? evt : ((event) ? event : null);
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;}

}
document.onkeypress = stopEnterKeyPress;   ///This line belong to the  above function stopEnterKeyPress(evt) to stop Form submition on press of Enter key
