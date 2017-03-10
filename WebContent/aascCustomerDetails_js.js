/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascCustomerDetails.jsp validation             |
|    Author Suman Gunnda                                                    |
|    Version   1.1                                                          |
|    Creation 25/07/2014                                                    |
+===========================================================================*/

function methodOnEdit(rowCount)
{         
    document.CustomerDetailsForm.currentRow.value=rowCount;
} 

function methodOnView(rowCount)
{         
    document.CustomerDetailsForm.currentRow.value=rowCount;
    if(document.CustomerDetailsForm.actiontype.value == 'View')
    {
        document.CustomerDetailsForm.submit();
    }
} 

function validateSubmit()
{
    if(document.CustomerDetailsForm.CreateButtonId.value == "0")
    {
        document.CustomerDetailsForm.CreateButtonId.value="1";
        return true;
    }
    else
    {
        alert("Request already submitted. Please Wait.");
        return false; 
    }
}
