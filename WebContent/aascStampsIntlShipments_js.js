/*===========================================================================================================+

|  DESCRIPTION                                                                                               |

|    javascript file for the aascStampsIntlShipments.jsp  validation                                         |

|    Author Mahesh Vattipelly                                                                             |

|    Version   1                                                                                            |                                                                            

|    Creation 17-Oct-2014                                                                                    |

History@
    10/11/2015  Suman G     Added code to fix #3946
    24/11/2015  Mahesh V    Added code for fix #4021
+===========================================================================================================*/

 var shipFlagTemp="";
 function load()
 {


  var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;       
   
 var descriptionVal = document.aascStampsIntlShipmentsForm.Description.value;
    if(descriptionVal.length < 1){
    getIntlCommodityLineDetails(); 
   }

 if(shipFlag == 'Y')
 {
           document.aascStampsIntlShipmentsForm.Description.disabled = true;
           document.aascStampsIntlShipmentsForm.HarmonizedCode.disabled = true;
           document.aascStampsIntlShipmentsForm.CountryOfManufacture.disabled = true;
           document.aascStampsIntlShipmentsForm.Quantity.disabled = true;
           document.aascStampsIntlShipmentsForm.UnitPrice.disabled = true;

         document.aascStampsIntlShipmentsForm.addComm.src = "buttons/addcommodityitem-off1.png";
         document.aascStampsIntlShipmentsForm.editComm.src = "buttons/edititem-on1.png"; 
         document.aascStampsIntlShipmentsForm.delComm.src = "buttons/deleteitem-off1.png";

   }
   
  shipFlagTemp=shipFlag;
 
}

function saveDetails()
{    
       var desc = trim(document.aascStampsIntlShipmentsForm.Description.value);
       var tariffcode = trim(document.aascStampsIntlShipmentsForm.HarmonizedCode.value);
       var countryOfmanufacture = trim(document.aascStampsIntlShipmentsForm.CountryOfManufacture.value);
       var noOfPieces = trim(document.aascStampsIntlShipmentsForm.Quantity.value);
       var unitPrice = trim(document.aascStampsIntlShipmentsForm.UnitPrice.value);
       var alertStr = "";
       var contentType = trim(document.aascStampsIntlShipmentsForm.Purpose.value);
       var otherDescribe = trim(document.aascStampsIntlShipmentsForm.otherDescribe.value);
       var comments = trim(document.aascStampsIntlShipmentsForm.comments.value);
       var invoiceNumber = trim(document.aascStampsIntlShipmentsForm.InvoiceNumber.value);
       var licenseNumber = trim(document.aascStampsIntlShipmentsForm.stampsLicenseNumber.value);
       var certificateNumber = trim(document.aascStampsIntlShipmentsForm.stampsCertificateNumber.value);
       var commLength = document.aascStampsIntlShipmentsForm.commodityLine.length;
     
         if(commLength <3){
         alert('Please Enter Details of Atleast One Commodity');
         return false;
         }
          if (contentType == '')
         {
           alert('Please select Valid Content Type');
           document.aascStampsIntlShipmentsForm.Purpose.focus();
           return false;

         }
         if (contentType == 'Other' && otherDescribe == '')
         {
           alert("Other Describe Should not be empty for the Content Type \'Other\'");
           document.aascStampsIntlShipmentsForm.otherDescribe.focus();
           return false;
         }
        if(otherDescribe.length > 20)  
        {
          alert('Other Describe Should not exceeds more than 20 Characters');
          document.aascStampsIntlShipmentsForm.otherDescribe.focus();
          return false;
        }  
        
          if(invoiceNumber.length > 15)  
        {
          alert('Invoice Number Should not exceeds more than 15 digits');
          document.aascStampsIntlShipmentsForm.InvoiceNumber.focus();
          return false;
        }  
        
         if(comments.length > 76)  
        {
          alert('Comments Should not be more than 76 characters');
          document.aascStampsIntlShipmentsForm.comments.focus();
          return false;
        } 
        
        if(licenseNumber.length > 6)  
        {
          alert('License Number Should not be more than 6 characters');
          document.aascStampsIntlShipmentsForm.stampsLicenseNumber.focus();
          return false;
        } 
        
        if(certificateNumber.length > 8)  
        {
          alert('Certificate Number Should not be more than 8 characters');
          document.aascStampsIntlShipmentsForm.stampsCertificateNumber.focus();
          return false;
        }
        
       document.aascStampsIntlShipmentsForm.actionType.value='SAVE';
       document.aascStampsIntlShipmentsForm.submit();
       
       window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";
       
       window.opener.document.DynaShipmentShipSaveForm.intTotalCustomsValue.value = "Y";
}


      //validate Date started

		var dtCh= "-";

		var minYear=1990;

		var maxYear=2100;

		function isInteger(s){

			var i;

			for (i = 0; i < s.length; i++){   

				// Check that current character is number.

				var c = s.charAt(i);

				if (((c < "0") || (c > "9"))) return false;

			}

			// All characters are numbers.

			return true;

		}
   function stripCharsInBag(s, bag){

			var i;

			var returnString = "";

			for (i = 0; i < s.length; i++){   

				var c = s.charAt(i);

				if (bag.indexOf(c) == -1) returnString += c;

			}

			return returnString;
		       }

  function daysInFebruary (year){

                    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
		     }

  function DaysArray(n) {

			for (var i = 1; i <= n; i++) {

				this[i] = 31

				if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}

				if (i==2) {this[i] = 29}

		   } 

		   return this

		}

//date validation

  function isDate(dtStr){
      if(dtStr != ''){
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
        
				alert("The Date Format Should Be : YYYY-MM-DD")

				return false
       

			}

			if (strMonth.length<1 || month<1 || month>12){

				alert("Please Enter A Valid Month")

				return false

			}

			if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){

				alert("Please Enter A Valid Day")

				return false

			}

			if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){

				alert("Please Enter A Valid 4 Digit Year Between "+minYear+" And "+maxYear)

				return false

			}

			if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){

				alert("Please Enter A Valid Date")

				return false

			}

		return true
     }
		}

		//end of date validation

    
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}

function saveProductDetails()
{
//alert("saveProductDetails");
var alertStr = "";

 var desc = trim(document.aascStampsIntlShipmentsForm.Description.value);
       var tariffcode = trim(document.aascStampsIntlShipmentsForm.HarmonizedCode.value);
       var countryOfmanufacture = trim(document.aascStampsIntlShipmentsForm.CountryOfManufacture.value);
       var noOfPieces = trim(document.aascStampsIntlShipmentsForm.Quantity.value);
       var unitPrice = trim(document.aascStampsIntlShipmentsForm.UnitPrice.value);
       var pkgWeight = trim(document.aascStampsIntlShipmentsForm.Weight.value);


     if(desc ==''||desc == null)
       {
       alert("Please enter \'Product Description\' in Commodity Line Item Section \n");
       document.aascStampsIntlShipmentsForm.Description.focus();
       return false;
       }
         
        for (var i = 0; i < desc.length; i++) 
        {
  	
        if(desc.charCodeAt(i)==43||desc.charCodeAt(i)==42||desc.charCodeAt(i)==60||desc.charCodeAt(i)==62||desc.charCodeAt(i)==34)
           {
            alert("Please enter valid Product Description");
            document.aascStampsIntlShipmentsForm.Description.focus();
            return false;
            }
        }

      if(tariffcode.length > 6)
       {
       alert("Tariff code length should be 6 digits only");
       document.aascStampsIntlShipmentsForm.HarmonizedCode.focus();
       return false;
       } 

       
       if(noOfPieces ==''||noOfPieces == null)
       {
       alert("Please Enter No Of Pieces");
       document.aascStampsIntlShipmentsForm.Quantity.focus();
       return false;
       }
       
       if(noOfPieces == 0.0 || noOfPieces == 0)  
       {
       alert("Please enter value more than zero for \'Number Of Units of the Package\' in Commodity Line Item Section");
       document.aascStampsIntlShipmentsForm.Quantity.focus();
              return false;
       }
       if(unitPrice ==''||unitPrice == null)
       {
       alert("Please Enter Price Per Unit");
       document.aascStampsIntlShipmentsForm.UnitPrice.focus();
       return false;
       }
       
        if(isNaN(unitPrice))
       {
       alert("Please Enter Numeric Number for Price per unit");
       document.aascStampsIntlShipmentsForm.UnitPrice.focus();
       return false;
       } 
       if(unitPrice < 0)  //Added by Narasimha 16/11/2010
       {
      alert("Please Enter Positive Value for \'Price per unit\' in Commodity Line Item Section");
       document.aascStampsIntlShipmentsForm.UnitPrice.focus();
              return false;
       }
     if(unitPrice == 0.0 || unitPrice == 0)  
       {
       alert("Please enter value more than zero for \'Price per Unit of the Package\' in Commodity Line Item Section");
       document.aascStampsIntlShipmentsForm.UnitPrice.focus();
              return false;
       }  
       
      if(pkgWeight < 0)  
       {
       alert("Please enter Positive Numeric Value for \'Package Weight\' in Commodity Line Item Section");
       document.aascStampsIntlShipmentsForm.Weight.focus();
              return false;
       }
       
         if(pkgWeight ==''||pkgWeight == null)
       {
       alert("Please enter \'Package Weight\' in Commodity Line Item Section.");
       document.aascStampsIntlShipmentsForm.Weight.focus();
       return false;
       }
          if(isNaN(pkgWeight))
       {
       alert("Please enter Numeric Value for \'Package Weight\' in Commodity Line Item Section");
       document.aascStampsIntlShipmentsForm.Weight.focus();
       return false;
       }
       if(pkgWeight == 0.0 || pkgWeight == 0)  
       {
       alert("Please enter value more than zero for \'Package Weight\' in Commodity Line Item Section");
       document.aascStampsIntlShipmentsForm.Weight.focus();
              return false;
       }
       
      if(alertStr != "" && alertStr !=null)
      {
      alert(alertStr);
      return false;
      }

      document.aascStampsIntlShipmentsForm.addCommodityFlag.value='Y';


document.aascStampsIntlShipmentsForm.selectLength.value = document.aascStampsIntlShipmentsForm.commodityLine.length + 1;         
if(window.opener.document.DynaShipmentShipSaveForm.flagShip.value!='Y')
{
document.aascStampsIntlShipmentsForm.actionType.value='ADD';
document.aascStampsIntlShipmentsForm.submit();
}
window.opener.document.DynaShipmentShipSaveForm.intlSaveFlag.value = "Y";

}

function editOptions()
{
  var form = document.aascStampsIntlShipmentsForm.commodityLine;
  var value = form.options.value;
  var form = document.aascStampsIntlShipmentsForm.commodityLine;

for (var i=0; i<form.options.length; i++){
 if (form.options[i].selected==true){
  var value = form.options[document.aascStampsIntlShipmentsForm.commodityLine.selectedIndex].value;    
  break;
 }else{
 var value ="";
}
 
}
  
  
var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;

if(value == 111 || value == 222){
alert("  Please select a Commodity Item to Edit  ");
return false;
}
if(value == '')
{
alert("  Please select a Commodity Item to Edit  ");
return false;
}
document.aascStampsIntlShipmentsForm.opValue.value = value;
enableCommodityDetailDiv();
document.aascStampsIntlShipmentsForm.actionType.value='EDIT';
document.aascStampsIntlShipmentsForm.submit();
}

function delOptions()
{

var form = document.aascStampsIntlShipmentsForm.commodityLine;

for (var i=0; i<form.options.length; i++){
 if (form.options[i].selected==true){
  var value = form.options[document.aascStampsIntlShipmentsForm.commodityLine.selectedIndex].value;    
  break;
 }else{
 var value ="";
}
 
}

var shipFlag = window.opener.document.DynaShipmentShipSaveForm.flagShip.value;

if(value == 111 || value == 222)
{
alert("  Please select a Commodity Item to Delete  ");
return false;
}
if(value == '')
{
alert("  Please select a Commodity Item to Delete  ");
return false;
}
if(shipFlag!= 'Y')
{
document.aascStampsIntlShipmentsForm.actionType.value='DELETE';
document.aascStampsIntlShipmentsForm.submit();
}
}

//Global variables

var winCal;
var dtToday;
var Cal;
var MonthName;
var WeekDayName1;
var WeekDayName2;
var exDateTime;//Existing Date and Time
var selDate;//selected date. version 1.7
var calSpanID = "calBorder"; // span ID
var domStyle=null; // span DOM object with style
var cnLeft="0";//left coordinate of calendar span
var cnTop="0";//top coordinate of calendar span
var xpos=0; // mouse x position
var ypos=0; // mouse y position
var calHeight=0; // calendar height
var CalWidth=208;// calendar width
var CellWidth=30;// width of day cell.
var TimeMode=24;// TimeMode value. 12 or 24
var StartYear =1990; //First Year in drop down year selection
var EndYear = 5; //End Year offset. i.e. Current Year + 5


//Configurable parameters

//var WindowTitle="DateTime Picker";//Date Time Picker title.

var SpanBorderColor = "#FFFFFF";//span border color
var SpanBgColor = "#FFFFFF";//span background color
var WeekChar=2;//number of character for week day. if 2 then Mo,Tu,We. if 3 then Mon,Tue,Wed.
var DateSeparator="-";//Date Separator, you can change it to "-" if you want.
var ShowLongMonth=true;//Show long month name in Calendar header. example: "January".
var ShowMonthYear=true;//Show Month and Year in Calendar header.
var MonthYearColor="#cc0033";//Font Color of Month and Year in Calendar header.
var WeekHeadColor="#18861B";//var WeekHeadColor="#18861B";//Background Color in Week header.
var SundayColor="#C0F64F";//var SundayColor="#C0F64F";//Background color of Sunday.
var SaturdayColor="#C0F64F";//Background color of Saturday.
var WeekDayColor="white";//Background color of weekdays.
var FontColor="#FFFF33";//color of font in Calendar day cell.
var TodayColor="#FFFF33";//var TodayColor="#FFFF33";//Background color of today.
var SelDateColor = "#8DD53C";//var SelDateColor="#8DD53C";//Backgrond color of selected date in textbox.
var YrSelColor="#cc0033";//color of font of Year selector.
var MthSelColor="#cc0033";//color of font of Month selector if "MonthSelector" is "arrow".
var HoverColor="#E0FF38"; //color when mouse move over.
var ThemeBg="";//Background image of Calendar window.
var CalBgColor="";//Backgroud color of Calendar window.
var PrecedeZero=true;//Preceding zero [true|false]
var MondayFirstDay=false;//true:Use Monday as first day; false:Sunday as first day. [true|false]  //added in version 1.7
var UseImageFiles = true;//Use image files with "arrows" and "close" button

//use the Month and Weekday in your preferred language.

var MonthName=["January", "February", "March", "April", "May", "June","July","August", "September", "October", "November", "December"];
var WeekDayName1=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
var WeekDayName2=["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"];

document.onmousedown = pickIt;
document.onmousemove = dragIt;
document.onmouseup = dropIt;

function NewCssCal(pCtrl,pFormat,pScroller,pShowTime,pTimeMode,pHideSeconds) {
	// get current date and time
        
	dtToday = new Date();
        
//        alert("dtToday        "+dtToday);
        
	Cal=new Calendar(dtToday);

	if ((pShowTime!=null) && (pShowTime)) {
		Cal.ShowTime=true;
		if ((pTimeMode!=null) &&((pTimeMode=='12')||(pTimeMode=='24')))	{
			TimeMode=pTimeMode;
		}

		else TimeMode='24';

        if (pHideSeconds!=null)
        {
            if (pHideSeconds)
            {Cal.ShowSeconds=false;}
            else
            {Cal.ShowSeconds=true;}

        }

        else
        {
            Cal.ShowSeconds=false;
        }

	}

	if (pCtrl!=null)

		Cal.Ctrl=pCtrl;


	if (pFormat!=null)
		Cal.Format=pFormat.toUpperCase();
	else
	    Cal.Format="MMDDYYYY";

	if (pScroller!=null) {
		if (pScroller.toUpperCase()=="ARROW") {
			Cal.Scroller="ARROW";
		}
		else {
			Cal.Scroller="DROPDOWN";
		}
    }

	exDateTime=document.getElementById(pCtrl).value;

	if (exDateTime!="")	{ //Parse existing Date String
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
		var winHeight;
		//parse month

		Sp1=exDateTime.indexOf(DateSeparator,0)
		Sp2=exDateTime.indexOf(DateSeparator,(parseInt(Sp1)+1));
		var offset=parseInt(Cal.Format.toUpperCase().lastIndexOf("M"))-parseInt(Cal.Format.toUpperCase().indexOf("M"))-1;

		if ((Cal.Format.toUpperCase()=="DDMMYYYY") || (Cal.Format.toUpperCase()=="DDMMMYYYY")) {
			if (DateSeparator=="") {
				strMonth=exDateTime.substring(2,4+offset);
				strDate=exDateTime.substring(0,2);
				strYear=exDateTime.substring(4+offset,8+offset);
			}
			else {
				if(exDateTime.indexOf("D*") != -1) {   //DTG
					strMonth = exDateTime.substring(8, 11);
					strDate  = exDateTime.substring(0, 2);
					strYear  = "20" + exDateTime.substring(11, 13);  //Hack, nur für Jahreszahlen ab 2000

				} else {
					strMonth=exDateTime.substring(Sp1+1,Sp2);
					strDate=exDateTime.substring(0,Sp1);
					strYear=exDateTime.substring(Sp2+1,Sp2+5);
				}
			}

		}

		else if ((Cal.Format.toUpperCase()=="MMDDYYYY") || (Cal.Format.toUpperCase()=="MMMDDYYYY")) {

			if (DateSeparator=="") {
				strMonth=exDateTime.substring(0,2+offset);
				strDate=exDateTime.substring(2+offset,4+offset);
				strYear=exDateTime.substring(4+offset,8+offset);
			}

			else {

				strMonth=exDateTime.substring(0,Sp1);
				strDate=exDateTime.substring(Sp1+1,Sp2);
				strYear=exDateTime.substring(Sp2+1,Sp2+5);
			}

		}

		else if ((Cal.Format.toUpperCase()=="YYYYMMDD") || (Cal.Format.toUpperCase()=="YYYYMMMDD")) {

			if (DateSeparator=="") {
				strMonth=exDateTime.substring(4,6+offset);
				strDate=exDateTime.substring(6+offset,8+offset);
				strYear=exDateTime.substring(0,4);
			}

			else {
				strMonth=exDateTime.substring(Sp1+1,Sp2);
				strDate=exDateTime.substring(Sp2+1,Sp2+3);
				strYear=exDateTime.substring(0,Sp1);
			}

		}

		else if ((Cal.Format.toUpperCase()=="YYMMDD") || (Cal.Format.toUpperCase()=="YYMMMDD")) {

			if (DateSeparator=="") {
				strMonth=exDateTime.substring(2,4+offset);
				strDate=exDateTime.substring(4+offset,6+offset);
				strYear=exDateTime.substring(0,2);
			}

			else {
				strMonth=exDateTime.substring(Sp1+1,Sp2);
				strDate=exDateTime.substring(Sp2+1,Sp2+3);
				strYear=exDateTime.substring(0,Sp1);
			}

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

		YearPattern=/^\d{4}$/;
		if (YearPattern.test(strYear))
			Cal.Year=parseInt(strYear,10);

		//end parse year

		//parse time

		if (Cal.ShowTime==true)	{

			//parse AM or PM

			if (TimeMode==12) {
				strAMPM=exDateTime.substring(exDateTime.length-2,exDateTime.length)
				Cal.AMorPM=strAMPM;

			}

			tSp1=exDateTime.indexOf(":",0)
			tSp2=exDateTime.indexOf(":",(parseInt(tSp1)+1));
			if (tSp1>0)	{

				strHour=exDateTime.substring(tSp1,(tSp1)-2);
				Cal.SetHour(strHour);

				strMinute=exDateTime.substring(tSp1+1,tSp1+3);
				Cal.SetMinute(strMinute);

				strSecond=exDateTime.substring(tSp2+1,tSp2+3);
				Cal.SetSecond(strSecond);

			} else if(exDateTime.indexOf("D*") != -1) {   //DTG
				strHour = exDateTime.substring(2, 4);
				Cal.SetHour(strHour);
				strMinute = exDateTime.substring(4, 6);
				Cal.SetMinute(strMinute);

			}
		}

	}

	selDate=new Date(Cal.Year,Cal.Month,Cal.Date);//version 1.7
        RenderCssCal(true);
	
}

function RenderCssCal(bNewCal) {

	if (typeof bNewCal == "undefined" || bNewCal != true) {bNewCal = false;}
	var vCalHeader;
	var vCalData;
	var vCalTime="";

	var i;
	var j;

	var SelectStr;
	var vDayCount=0;
	var vFirstDay;

	calHeight = 0; // reset the window height on refresh

	// Set the default cursor for the calendar

	winCalData="<span style='cursor:auto;'>\n";

	if (ThemeBg==""){CalBgColor="bgcolor='"+WeekDayColor+"'"}
	vCalHeader="<table "+CalBgColor+" background='"+ThemeBg+"' border=1 cellpadding=1 cellspacing=1 width='200px' valign='top'>\n";

	//Table for Month & Year Selector

	vCalHeader+="<tr>\n<td colspan='7'>\n<table border='0' width='200px' cellpadding='0' cellspacing='0'>\n<tr>\n";
	//******************Month and Year selector in dropdown list************************

	if (Cal.Scroller=="DROPDOWN") {
		vCalHeader+="<td align='center'><select name=\"MonthSelector\" onChange=\"javascript:Cal.SwitchMth(this.selectedIndex);RenderCssCal();\">\n";
		for (i=0;i<12;i++) {
			if (i==Cal.Month)
				SelectStr="Selected";
			else
				SelectStr="";
			    vCalHeader+="<option "+SelectStr+" value="+i+">"+MonthName[i]+"</option>\n";

		}

		vCalHeader+="</select></td>\n";
		//Year selector

		vCalHeader+="<td align='center'><select name=\"YearSelector\" size=\"1\" onChange=\"javascript:Cal.SwitchYear(this.value);RenderCssCal();\">\n";
		for (i = StartYear; i <= (dtToday.getFullYear() + EndYear);i++)	{
			if (i==Cal.Year)
				SelectStr="Selected";
			else
				SelectStr="";
			vCalHeader+="<option "+SelectStr+" value="+i+">"+i+"</option>\n";

		}

		vCalHeader+="</select></td>\n";
		calHeight += 30;
	}

	//******************End Month and Year selector in dropdown list*********************

	//******************Month and Year selector in arrow*********************************

    else if (Cal.Scroller=="ARROW")
    {

    if (UseImageFiles)
    {
  		vCalHeader+="<td><img onmousedown='javascript:Cal.DecYear();RenderCssCal();' src='images/cal_fastreverse.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Year scroller (decrease 1 year)
  		vCalHeader+="<td><img onmousedown='javascript:Cal.DecMonth();RenderCssCal();' src='images/cal_reverse.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Month scroller (decrease 1 month)
  		vCalHeader+="<td width='70%' class='calR'><font color='"+YrSelColor+"'>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</font></td>\n"//Month and Year
  		vCalHeader+="<td><img onmousedown='javascript:Cal.IncMonth();RenderCssCal();' src='images/cal_forward.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Month scroller (increase 1 month)
  		vCalHeader+="<td><img onmousedown='javascript:Cal.IncYear();RenderCssCal();' src='images/cal_fastforward.gif' width='13px' height='9' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>\n";//Year scroller (increase 1 year)

  	    calHeight += 22;
	  }
	  else
	  {
	  	vCalHeader+="<td><span id='dec_year' title='reverse year' onmousedown='javascript:Cal.DecYear();RenderCssCal();' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white; color:"+YrSelColor+"'>-</span></td>";//Year scroller (decrease 1 year)
	  	vCalHeader+="<td><span id='dec_month' title='reverse month' onmousedown='javascript:Cal.DecMonth();RenderCssCal();' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'>&lt;</span></td>\n";//Month scroller (decrease 1 month)
  		vCalHeader+="<td width='70%' class='calR'><font color='"+YrSelColor+"'>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</font></td>\n"//Month and Year
  		vCalHeader+="<td><span id='inc_month' title='forward month' onmousedown='javascript:Cal.IncMonth();RenderCssCal();' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'>&gt;</span></td>\n";//Month scroller (increase 1 month)
  		vCalHeader+="<td><span id='inc_year' title='forward year' onmousedown='javascript:Cal.IncYear();RenderCssCal();'  onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white; color:"+YrSelColor+"'>+</span></td>\n";//Year scroller (increase 1 year)
  	    calHeight += 22;
	  }
	}

	vCalHeader+="</tr>\n</table>\n</td>\n</tr>\n"

  //******************End Month and Year selector in arrow******************************

	//Calendar header shows Month and Year
	if ((ShowMonthYear)&&(Cal.Scroller=="DROPDOWN")) {
        
		vCalHeader+="<tr><td colspan='7' class='calR'>\n<font color='"+MonthYearColor+"'>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</font>\n</td></tr>\n";
	    calHeight += 19;
	}

	//Week day header

	vCalHeader+="<tr><td colspan=\"7\"><table cellspacing=1><tr>\n";
	var WeekDayName=new Array();//Added version 1.7
	if (MondayFirstDay==true)
		WeekDayName=WeekDayName2;
	else
		WeekDayName=WeekDayName1;
	for (i=0;i<7;i++) {
		vCalHeader+="<td bgcolor="+WeekHeadColor+" width='"+CellWidth+"px' class='calTD'><font color='white'>"+WeekDayName[i].substr(0,WeekChar)+"</font></td>\n";
	}

	calHeight += 19;
	vCalHeader+="</tr>\n";
	//Calendar detail
	CalDate=new Date(Cal.Year,Cal.Month);
	CalDate.setDate(1);

	vFirstDay=CalDate.getDay();

	//Added version 1.7

	if (MondayFirstDay==true) {
		vFirstDay-=1;
		if (vFirstDay==-1)
			vFirstDay=6;
	}

	//Added version 1.7

	vCalData="<tr>";
	calHeight += 19;
        
        
	for (i=0;i<vFirstDay;i++) {
		vCalData=vCalData+GenCell();
		vDayCount=vDayCount+1;
	}

	//Added version 1.7

	for (j=1;j<=Cal.GetMonDays();j++) {
		var strCell;
		if((vDayCount%7==0)&&(j > 1)) {
			vCalData=vCalData+"\n<tr>";
		}

		vDayCount=vDayCount+1;
		if ((j==dtToday.getDate())&&(Cal.Month==dtToday.getMonth())&&(Cal.Year==dtToday.getFullYear()))
			strCell=GenCell(j,true,TodayColor);//Highlight today's date
		else {
			if ((j==selDate.getDate())&&(Cal.Month==selDate.getMonth())&&(Cal.Year==selDate.getFullYear())) { //modified version 1.7
				strCell=GenCell(j,true,SelDateColor);
			}
			else {
				if (MondayFirstDay==true) {
					if (vDayCount%7==0)
						strCell=GenCell(j,false,SundayColor);
					else if ((vDayCount+1)%7==0)
						strCell=GenCell(j,false,SaturdayColor);
					else
						strCell=GenCell(j,null,WeekDayColor);
				}
				else {
					if (vDayCount%7==0)
						strCell=GenCell(j,false,SaturdayColor);
					else if ((vDayCount+6)%7==0)
						strCell=GenCell(j,false,SundayColor);
					else
						strCell=GenCell(j,null,WeekDayColor);
				}
			}
		}

		vCalData=vCalData+strCell;

		if((vDayCount%7==0)&&(j<Cal.GetMonDays())) {
			vCalData=vCalData+"\n</tr>";
			calHeight += 19;
		}
	}

	// finish the table proper

	if(!(vDayCount%7) == 0) {
		while(!(vDayCount % 7) == 0) {
			vCalData=vCalData+GenCell();
			vDayCount=vDayCount+1;
		}
	}

	vCalData=vCalData+"\n</table></td></tr>";


	//Time picker
	if (Cal.ShowTime)
	{
		var showHour;
		var ShowArrows=false;
		var HourCellWidth="35px"; //cell width with seconds.
		showHour=Cal.getShowHour();

		if (Cal.ShowSeconds==false && TimeMode==24 )
        {
		   ShowArrows=true;
		   HourCellWidth="10px";
		}

		vCalTime="\n<tr>\n<td colspan='7' align='center'><center>\n<table border='0' width='199px' cellpadding='0' cellspacing='2'>\n<tr>\n<td height='5px' width='"+HourCellWidth+"px'>&nbsp;</td>\n";

		if (ShowArrows && UseImageFiles)
		{
            vCalTime+="<td align='center'><table cellspacing='0' cellpadding='0' style='line-height:0pt'><tr><td><img onmousedown='startSpin(\"Hour\", \"plus\");' onmouseup='stopSpin();' src='images/cal_plus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr><tr><td><img onmousedown='startSpin(\"Hour\", \"minus\");' onmouseup='stopSpin();' src='images/cal_minus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr></table></td>\n";
		}

		vCalTime+="<td align='center' width='22px'><input type='text' name='hour' maxlength=2 size=1 style='WIDTH: 22px;color:"+MonthYearColor+";' value="+showHour+" onChange=\"javascript:Cal.SetHour(this.value)\">";
		vCalTime+="</td><td align='center' style='font-size:100%;'>:</td><td align='center' width='22px'>";
		vCalTime+="<input type='text' name='minute' maxlength=2 size=1 style='WIDTH: 22px;color:"+MonthYearColor+";' value="+Cal.Minutes+" onChange=\"javascript:Cal.SetMinute(this.value)\">";

		if (Cal.ShowSeconds) {
			vCalTime+="</td><td align='center' style='font-size:100%;'>:</td><td align='center' width='22px'>";
			vCalTime+="<input type='text' name='second' maxlength=2 size=1 style='WIDTH: 22px;color:"+MonthYearColor+";' value="+Cal.Seconds+" onChange=\"javascript:Cal.SetSecond(this.value)\">"; //parseInt(this.value,10)
		}

		if (TimeMode==12) {
			var SelectAm =(Cal.AMorPM=="AM")? "Selected":"";
			var SelectPm =(Cal.AMorPM=="PM")? "Selected":"";

            vCalTime+="</td><td>";
			vCalTime+="<select name=\"ampm\" onChange=\"javascript:Cal.SetAmPm(this.options[this.selectedIndex].value);\">\n";
			vCalTime+="<option "+SelectAm+" value=\"AM\">AM</option>";
			vCalTime+="<option "+SelectPm+" value=\"PM\">PM<option>";
			vCalTime+="</select>";
		}

		if (ShowArrows && UseImageFiles) {
		   vCalTime+="</td>\n<td align='center'><table cellspacing='0' cellpadding='0' style='line-height:0pt'><tr><td><img onmousedown='startSpin(\"Minute\", \"plus\");' onmouseup='stopSpin();' src='images/cal_plus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr><tr><td><img onmousedown='startSpin(\"Minute\", \"minus\");' onmouseup='stopSpin();' src='images/cal_minus.gif' width='13px' height='9px' onmouseover='changeBorder(this, 0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td></tr></table>";
		}

		vCalTime+="</td>\n<td align='right' valign='bottom' width='"+HourCellWidth+"px'>";
	}

	else
		{vCalTime+="\n<tr>\n<td colspan='7' align='right'>";}

    if (UseImageFiles)
    {
       vCalTime+="<img onmousedown='javascript:closewin(\"" + Cal.Ctrl + "\"); stopSpin();' src='images/cal_close.gif' width='16px' height='14px' onmouseover='changeBorder(this,0)' onmouseout='changeBorder(this, 1)' style='border:1px solid white'></td>";
    }
    else
    {
       vCalTime+="<span id='close_cal' title='close'onmousedown='javascript:closewin(\"" + Cal.Ctrl + "\");' onmouseover='changeBorder(this, 0)'onmouseout='changeBorder(this, 1)' style='border:1px solid white; font-family: Arial;font-size: 10pt;'>x</span></td>";
    }

    vCalTime+="</tr>\n</table></center>\n</td>\n</tr>";
    calHeight += 31;
	vCalTime+="\n</table>\n</span>";

	//end time picker
    var funcCalback="function callback(id, datum) {\n";
    funcCalback+=" var CalId = document.getElementById(id); if (datum== 'undefined') { var d = new Date(); datum = d.getDate() + '/' +(d.getMonth()+1) + '/' + d.getFullYear(); } window.calDatum=datum;CalId.value=datum;\n";
    funcCalback+=" if (Cal.ShowTime) {\n";
    funcCalback+=" CalId.value+=' '+Cal.getShowHour()+':'+Cal.Minutes;\n";
    funcCalback+=" if (Cal.ShowSeconds)\n  CalId.value+=':'+Cal.Seconds;\n";
    funcCalback+=" if (TimeMode==12)\n  CalId.value+=''+Cal.getShowAMorPM();\n";
    funcCalback+="}\n winCal.style.visibility='hidden';\n}\n";


	// determines if there is enough space to open the cal above the position where it is called
	if (ypos > calHeight) {
	   ypos = ypos - calHeight;
	}

	if (winCal == undefined) {
	   var headID = document.getElementsByTagName("head")[0];

     // add javascript function to the span cal
       var e = document.createElement("script");
       e.type = "text/javascript";
       e.language = "javascript";
       e.text = funcCalback;
       headID.appendChild(e);
	   // add stylesheet to the span cal

	   var cssStr = ".calTD {font-family: verdana; font-size: 12px; text-align: center; border:0 }\n";
	   //cssStr+= ".calR {font-family: verdana; font-size: 12px; text-align: center; font-weight: bold; color: red;}"
		cssStr+= ".calR {font-family: verdana; font-size: 12px; text-align: center; font-weight: bold;}"

	   var style = document.createElement("style");
       style.type = "text/css";
       style.rel = "stylesheet";
       if(style.styleSheet) { // IE
          style.styleSheet.cssText = cssStr;
        }

	   else { // w3c
          var cssText = document.createTextNode(cssStr);
          style.appendChild(cssText);
		}

       headID.appendChild(style);
	   // create the outer frame that allows the cal. to be moved
	   var span = document.createElement("span");
       span.id = calSpanID;

       with (span.style) {position = "absolute"; left = (xpos+8)+'px'; top = (ypos-8)+'px'; width = CalWidth+'px'; border = "solid 2pt " + SpanBorderColor; padding = "0pt"; cursor = "move"; backgroundColor = SpanBgColor; zIndex = 100;}
       document.body.appendChild(span)
       winCal=document.getElementById(calSpanID);
    }

    else {
	  winCal.style.visibility = "visible";
	  winCal.style.Height = calHeight;

	  // set the position for a new calendar only
	  if(bNewCal==true){
	     winCal.style.left = (xpos+8)+'px';
	     winCal.style.top = (ypos-8)+'px';
	   }
	}

	winCal.innerHTML=winCalData + vCalHeader + vCalData + vCalTime;
	return true;
}

function GenCell(pValue,pHighLight,pColor) { //Generate table cell with value
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
		vColor=CalBgColor;
	    if ((pHighLight!=null)&&(pHighLight)) {
		   vHLstr1="<font class='calR'>";vHLstr2="</font>";
		 }
	    else {
		   vHLstr1="";vHLstr2="";
		 }

	if (Cal.ShowTime) {
		vTimeStr=' '+Cal.Hours+':'+Cal.Minutes;
		if (Cal.ShowSeconds)
			vTimeStr+=':'+Cal.Seconds;
		if (TimeMode==12)
			vTimeStr+=' '+Cal.AMorPM;
	}

	else
		vTimeStr="";

	if (PValue!="") {
		//PCellStr="\n<td "+vColor+" class='calTD' style='cursor: pointer;' onmouseover='changeBorder(this, 0);' onmouseout='changeBorder(this, 1);' onClick=\"javascript:callback('"+Cal.Ctrl+"','"+Cal.FormatDate(PValue)+"');\">"+vHLstr1+PValue+vHLstr2+"</td>";

		if(pColor == SaturdayColor || pColor == SundayColor || pColor == SelDateColor || pColor == TodayColor) {
			PCellStr="\n<td "+vColor+" class='calTD' style='cursor: pointer;' onmouseover='changeBorder(this, 0);' onmouseout=\"changeBorder(this, 1, '"+pColor+"');\" onClick=\"javascript:callback('"+Cal.Ctrl+"','"+Cal.FormatDate(PValue)+"');\">"+vHLstr1+PValue+vHLstr2+"</td>";

		}
		else {
			PCellStr="\n<td "+vColor+" class='calTD' style='cursor: pointer;' onmouseover='changeBorder(this, 0);' onmouseout='changeBorder(this, 1);' onClick=\"javascript:callback('"+Cal.Ctrl+"','"+Cal.FormatDate(PValue)+"');\">"+vHLstr1+PValue+vHLstr2+"</td>";
		}
	}
	else

		PCellStr="\n<td "+vColor+" class='calTD'>&nbsp;</td>";

	return PCellStr;

}

function Calendar(pDate,pCtrl) {

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
	this.Scroller="DROPDOWN";
	if (pDate.getHours()<12)
		this.AMorPM="AM";
	else
		this.AMorPM="PM";

	this.ShowSeconds=true;
}



function GetMonthIndex(shortMonthName) {
	for (i=0;i<12;i++) {
		if (MonthName[i].substring(0,3).toUpperCase()==shortMonthName.toUpperCase())
		   {return i;}
	}
}

Calendar.prototype.GetMonthIndex=GetMonthIndex;

function IncYear() {
	Cal.Year++;}
	Calendar.prototype.IncYear=IncYear;

function DecYear() {
	Cal.Year--;}
	Calendar.prototype.DecYear=DecYear;

function IncMonth() {
	Cal.Month++;
	if (Cal.Month>=12) {
		Cal.Month=0;
		Cal.IncYear();
	}
}

Calendar.prototype.IncMonth=IncMonth;

function DecMonth() {
	Cal.Month--;
	if (Cal.Month<0) {
		Cal.Month=11;
		Cal.DecYear();
	}
}

Calendar.prototype.DecMonth=DecMonth;


function SwitchMth(intMth) {
	Cal.Month=intMth;}
	Calendar.prototype.SwitchMth=SwitchMth;

function SwitchYear(intYear) {
	Cal.Year=intYear;}
	Calendar.prototype.SwitchYear=SwitchYear;

function SetHour(intHour) {
	var MaxHour;
	var MinHour;
	if (TimeMode==24) {
		MaxHour=23;MinHour=0}
	else if (TimeMode==12) {
		MaxHour=12;MinHour=1}
	else
		alert("TimeMode can only be 12 or 24");

	var HourExp=new RegExp("^\\d\\d");
	var SingleDigit=new RegExp("\\d");

	if ((HourExp.test(intHour) || SingleDigit.test(intHour)) && (parseInt(intHour,10)>MaxHour)) {
	    alert('please enter hours less than 24');
            document.getElementById('hour').value='00';
            document.getElementById('hour').style.backgroundColor="#A79DFE";
            intHour = MinHour;
	}

	else if ((HourExp.test(intHour) || SingleDigit.test(intHour)) && (parseInt(intHour,10)<MinHour)) {
  	    alert('enter valid hours');
            document.getElementById('hour').value='00';
            document.getElementById('hour').style.backgroundColor="#A79DFE";
            intHour = '00';
	}

         if(intHour==null || intHour=='')
        {

                intHour = '00';
        }
          if(isNaN(intHour))
        {
        alert('enter valid hours');
        document.getElementById('hour').value='00';
        document.getElementById('hour').style.backgroundColor="#A79DFE";
        intHour = '00';
        }
        if(isInteger(intHour)==false)
        {
        alert('enter valid hours');
        document.getElementById('hour').value='00';
        document.getElementById('hour').style.backgroundColor="#A79DFE";
        intHour = '00';
        }
	if (SingleDigit.test(intHour)) {
		intHour="0"+intHour+"";
	}

	if (HourExp.test(intHour) && (parseInt(intHour,10)<=MaxHour) && (parseInt(intHour,10)>=MinHour)) {
		if ((TimeMode==12) && (Cal.AMorPM=="PM")) {
			if (parseInt(intHour,10)==12)
				Cal.Hours=12;
			else
				Cal.Hours=parseInt(intHour,10)+12;
		}

		else if ((TimeMode==12) && (Cal.AMorPM=="AM")) {
			if (intHour==12)
				intHour-=12;

			Cal.Hours=parseInt(intHour,10);
		}

		else if (TimeMode==24)
			Cal.Hours=parseInt(intHour,10);
	}
        //alert('intHour=='+intHour);
}

Calendar.prototype.SetHour=SetHour;

function SetMinute(intMin) {
	var MaxMin=59;
	var MinMin=0;

	var SingleDigit=new RegExp("\\d");
	var SingleDigit2=new RegExp("^\\d{1}$");
	var MinExp=new RegExp("^\\d{2}$");

	if ((MinExp.test(intMin) || SingleDigit.test(intMin)) && (parseInt(intMin,10)>MaxMin)) {
		alert('please enter minutes less than 60');
                document.getElementById('minute').value='00';
                document.getElementById('minute').style.backgroundColor="#A79DFE";
                intMin = MinMin;
	}

	else if ((MinExp.test(intMin) || SingleDigit.test(intMin)) && (parseInt(intMin,10)<MinMin))	{
		alert('enter valid minutes');
                document.getElementById('minute').value='00';
                document.getElementById('minute').style.backgroundColor="#A79DFE";
                intMin = '00';
	}
        if(intMin==null || intMin=='')
        {

                intMin = '00';
        }
          if(isNaN(intMin))
        {
        alert('enter valid minutes');
        document.getElementById('minute').value='00';
        document.getElementById('minute').style.backgroundColor="#A79DFE";
        intMin = '00';
        }
        if(isInteger(intMin)==false)
        {
        alert('enter valid minutes');
        document.getElementById('minute').value='00';
        document.getElementById('minute').style.backgroundColor="#A79DFE";
        intMin = '00';
        }

	var strMin = intMin + "";
	if (SingleDigit2.test(intMin)) {
		strMin="0"+strMin+"";
	}

	if ((MinExp.test(intMin) || SingleDigit.test(intMin))
	 && (parseInt(intMin,10)<=59) && (parseInt(intMin,10)>=0)) {

	 	Cal.Minutes=strMin;
	}


    //   alert('strMin=='+strMin);
}

Calendar.prototype.SetMinute=SetMinute;

function SetSecond(intSec) {
	var MaxSec=59;
	var MinSec=0;
//alert('intSec=='+intSec);
	var SingleDigit=new RegExp("\\d");
	var SingleDigit2=new RegExp("^\\d{1}$");
	var SecExp=new RegExp("^\\d{2}$");

	if ((SecExp.test(intSec) || SingleDigit.test(intSec)) && (parseInt(intSec,10)>MaxSec)) {
		alert('please enter seconds less than 60');
                document.getElementById('second').value='00';
                document.getElementById('second').style.backgroundColor="#A79DFE";
                intSec = MinSec;
	}

       else if ((SecExp.test(intSec) || SingleDigit.test(intSec)) && (parseInt(intSec,10)<MinSec))	{
		alert('enter valid seconds');
                document.getElementById('second').value='00';
                document.getElementById('second').style.backgroundColor="#A79DFE";
                intSec = '00';
	}
        if(intSec==null || intSec=='')
        {

               // alert('Enter');
                intSec = '00';
        }

        if(isNaN(intSec))
        {
        alert('enter valid seconds');
        document.getElementById('second').value='00';
        document.getElementById('second').style.backgroundColor="#A79DFE";
        intSec = '00';
        }
       //  alert('intSec=='+intSec);

        if(isInteger(intSec)==false)
        {
        alert('enter valid seconds');
        document.getElementById('second').value='00';
        document.getElementById('second').style.backgroundColor="#A79DFE";
        intSec = '00';
        }
	var strSec = intSec + "";
	if (SingleDigit2.test(intSec)) {
		strSec="0"+strSec+"";
	}

	if ((SecExp.test(intSec) || SingleDigit.test(intSec))
	 && (parseInt(intSec,10)<=59) && (parseInt(intSec,10)>=0)) {

	 	Cal.Seconds=strSec;
	}

   //   alert('strSec=='+strSec);

}

Calendar.prototype.SetSecond=SetSecond;

function SetAmPm(pvalue) {
	this.AMorPM=pvalue;
	if (pvalue=="PM") {

		this.Hours=(parseInt(this.Hours,10))+12;
		if (this.Hours==24)
  		    this.Hours=12;
	}

	else if (pvalue=="AM")
		this.Hours-=12;
}

Calendar.prototype.SetAmPm=SetAmPm;

function getShowHour() {
	var finalHour;

    if (TimeMode==12) {
    	if (parseInt(this.Hours,10)==0) {
			this.AMorPM="AM";
			finalHour=parseInt(this.Hours,10)+12;
		}

		else if (parseInt(this.Hours,10)==12) {
			this.AMorPM="PM";
			finalHour=12;
		}

		else if (this.Hours>12)	{
			this.AMorPM="PM";
			if ((this.Hours-12)<10)

				finalHour="0"+((parseInt(this.Hours,10))-12);
			else
				finalHour=parseInt(this.Hours,10)-12;
		}
		else {
			this.AMorPM="AM";
			if (this.Hours<10)

				finalHour="0"+parseInt(this.Hours,10);
			else
				finalHour=this.Hours;
		}
	}

	else if (TimeMode==24) {
		if (this.Hours<10)
			finalHour="0"+parseInt(this.Hours,10);
		else
    		finalHour=this.Hours;
	}

	return finalHour;
}

Calendar.prototype.getShowHour=getShowHour;

function getShowAMorPM() {
	return this.AMorPM;
}

Calendar.prototype.getShowAMorPM=getShowAMorPM;

function GetMonthName(IsLong) {
	var Month=MonthName[this.Month];
	if (IsLong)
		return Month;
	else
		return Month.substr(0,3);
}

Calendar.prototype.GetMonthName=GetMonthName;

function GetMonDays() { //Get number of days in a month

	var DaysInMonth=[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if (this.IsLeapYear()) {
		DaysInMonth[1]=29;
	}

	return DaysInMonth[this.Month];
}

Calendar.prototype.GetMonDays=GetMonDays;

function IsLeapYear() {
	if ((this.Year%4)==0) {
		if ((this.Year%100==0) && (this.Year%400)!=0) {
			return false;
		}
		else {
			return true;
		}
	}
	else {
		return false;
	}

}

Calendar.prototype.IsLeapYear=IsLeapYear;

function FormatDate(pDate)
{
	var MonthDigit=this.Month+1;
	if (PrecedeZero==true) {
		if (pDate<10)
			pDate="0"+pDate;
		if (MonthDigit<10)
			MonthDigit="0"+MonthDigit;
	}

	if (this.Format.toUpperCase()=="DDMMYYYY")
		return (pDate+DateSeparator+MonthDigit+DateSeparator+this.Year);

	else if (this.Format.toUpperCase()=="DDMMMYYYY")
		return (pDate+DateSeparator+this.GetMonthName(false)+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMDDYYYY")
		return (MonthDigit+DateSeparator+pDate+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMMDDYYYY")
		return (this.GetMonthName(false)+DateSeparator+pDate+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="YYYYMMDD")
		return (this.Year+DateSeparator+MonthDigit+DateSeparator+pDate);
	else if (this.Format.toUpperCase()=="YYMMDD")
		return (String(this.Year).substring(2,4)+DateSeparator+MonthDigit+DateSeparator+pDate);
	else if (this.Format.toUpperCase()=="YYMMMDD")
		return (String(this.Year).substring(2,4)+DateSeparator+this.GetMonthName(false)+DateSeparator+pDate);
	else if (this.Format.toUpperCase()=="YYYYMMMDD")
		return (this.Year+DateSeparator+this.GetMonthName(false)+DateSeparator+pDate);
	else
		return (pDate+DateSeparator+(this.Month+1)+DateSeparator+this.Year);
}

Calendar.prototype.FormatDate=FormatDate;

function closewin(id) {
   var CalId = document.getElementById(id);
   CalId.focus();
   winCal.style.visibility='hidden';
 }

function changeBorder(element, col, oldBgColor) {
  if (col == 0) {
	element.style.background = HoverColor;
    element.style.borderColor = "black";
    element.style.cursor = "pointer";
  }

  else {
	if(oldBgColor) {
		element.style.background = oldBgColor;
	} else {
		element.style.background = "white";
	}
    element.style.borderColor = "white";
    element.style.cursor = "auto";
  }
}


function pickIt(evt) {
   // accesses the element that generates the event and retrieves its ID
   if (window.addEventListener) { // w3c
	  var objectID = evt.target.id;
      if (objectID.indexOf(calSpanID) != -1){
         var dom = document.getElementById(objectID);
         cnLeft=evt.pageX;
         cnTop=evt.pageY;

         if (dom.offsetLeft){
           cnLeft = (cnLeft - dom.offsetLeft); cnTop = (cnTop - dom.offsetTop);
          }
       }

	  // get mouse position on click
	  xpos = (evt.pageX);
	  ypos = (evt.pageY);
	}

   else { // IE
	  var objectID = event.srcElement.id;
      cnLeft=event.offsetX;
      cnTop=(event.offsetY);

	  // get mouse position on click
	  var de = document.documentElement;
      var b = document.body;

      xpos = event.clientX + (de.scrollLeft || b.scrollLeft) - (de.clientLeft || 0);
      ypos = event.clientY + (de.scrollTop || b.scrollTop) - (de.clientTop || 0);
    }
   // verify if this is a valid element to pick
   if (objectID.indexOf(calSpanID) != -1){
      domStyle = document.getElementById(objectID).style;
    }

   if (domStyle) {
      domStyle.zIndex = 100;
      return false;
    }

   else {
      domStyle = null;
      return;
    }
 }



function dragIt(evt) {
   if (domStyle) {
      if (window.event) { //for IE
         domStyle.left = (event.clientX-cnLeft + document.body.scrollLeft)+'px';
         domStyle.top = (event.clientY-cnTop + document.body.scrollTop)+'px';
       } else {  //Firefox
         domStyle.left = (evt.clientX-cnLeft + document.body.scrollLeft)+'px';
         domStyle.top = (evt.clientY-cnTop + document.body.scrollTop)+'px';
       }
    }
 }



function dropIt() {
	stopSpin();

   if (domStyle) {
      domStyle = null;
    }
 }
 
 //stops the time spinner
function stopSpin() {
	clearInterval(document.thisLoop);
}



function viewPrinted()
{
    popupWindow = window.open('aascIntlDocViewPrint.jsp','_blank');
    popupWindow.focus();
}       

 function getAjaxInlCommodityDetails(){
  // alert('inside getAjaxInlCommodityDetails');
    var currentCommItem=document.aascStampsIntlShipmentsForm.selCommItems.options[document.aascStampsIntlShipmentsForm.selCommItems.selectedIndex].text;
    
   // alert("currentCommItem  : "+currentCommItem);
    
    
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
          var responseStringDummy=xmlHttp.responseText;
          startIndex  = responseStringDummy.indexOf('*');
          responseStringDummy=responseStringDummy.substring(startIndex+1);
           
          startIndex  = responseStringDummy.indexOf('*');
          countryOfManufacture     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascStampsIntlShipmentsForm.CountryOfManufacture.value=countryOfManufacture;

          startIndex  = responseStringDummy.indexOf('*');
          numberOfPieces     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          startIndex  = responseStringDummy.indexOf('*');
          numberOfPieces     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
         
          startIndex  = responseStringDummy.indexOf('*');
          quantity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascStampsIntlShipmentsForm.Quantity.value=quantity;

          startIndex  = responseStringDummy.indexOf('*');
          unitPrice     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);

          startIndex  = responseStringDummy.indexOf('*');
          unitPrice     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascStampsIntlShipmentsForm.UnitPrice.value=unitPrice;
          
         
          startIndex  = responseStringDummy.indexOf('*');
          desc     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascStampsIntlShipmentsForm.Description.value=desc;
          
          startIndex  = responseStringDummy.indexOf('*');
          weight     = responseStringDummy.substring(0,startIndex); //previosuly the name was harmonizedCode
          document.aascStampsIntlShipmentsForm.Weight.value=weight;
          
              
          harmonizedCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
       
          startIndex  = responseStringDummy.indexOf('*');
          tariffCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.aascStampsIntlShipmentsForm.HarmonizedCode.value=tariffCode;

          
          startIndex  = responseStringDummy.indexOf('*');
          quantityUnits     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
       
          startIndex  = responseStringDummy.indexOf('*');
          UOM     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
   
          startIndex  = responseStringDummy.indexOf('*');
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);
         
         startIndex  = responseStringDummy.indexOf('*');
         responseStringDummy=responseStringDummy.substring(startIndex+1);

       }
    }
    var locationId = document.getElementById("locationId").value;
    var currentItemtemp=encodeURIComponent(currentCommItem);
    var url="aascAjaxIntlCommodityDetail.jsp?currentItem="+currentItemtemp+"&locationId="+locationId;
    xmlHttp.open("GET",url,true);  // Calling 
    xmlHttp.setRequestHeader("Cache-Control","no-cache");
    xmlHttp.setRequestHeader("Pragma","no-cache");
    xmlHttp.setRequestHeader("If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT");
    xmlHttp.send(null);  
}


function getIntlImporterDetails(){
    var currentImporter = document.UPSIntlAddrForm.selImporterName.value;
    if(currentImporter == 'Select')
    {

    }
  
    else {
        getAjaxInlImporterDetail();
    }
}

function getAjaxInlImporterDetail(){
    var currentImporter = document.UPSIntlAddrForm.selImporterName.value;
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
          var responseStringDummy=xmlHttp.responseText;
          startIndex  = responseStringDummy.indexOf('*');
          responseStringDummy=responseStringDummy.substring(startIndex+1);
           
          startIndex  = responseStringDummy.indexOf('*');
          ImporterTINOrDUNS = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.TaxId.value=ImporterTINOrDUNS;
          
          startIndex  = responseStringDummy.indexOf('*');
          ImporterTINOrDUNSType     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress1     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.address.value=importerAddress1;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerAddress2     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCity     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.city.value=importerCity;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCompName     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.companyName.value=importerCompName;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerCountryCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.countryCode.value=importerCountryCode;
                             
          if(document.UPSIntlAddrForm.addressType.value=='NAFTAAddr')
          {
          
          startIndex  = responseStringDummy.indexOf('*');
          importerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          
          }else{
          startIndex  = responseStringDummy.indexOf('*');
          importerPhoneNum     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.PhoneNum.value=importerPhoneNum;
          
           }
          startIndex  = responseStringDummy.indexOf('*');
          importerPostalCode     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.postalCode.value=importerPostalCode;
          
          startIndex  = responseStringDummy.indexOf('*');
          importerState     = responseStringDummy.substring(0,startIndex);
          responseStringDummy=responseStringDummy.substring(startIndex+1);
          document.UPSIntlAddrForm.state.value=importerState;
          
          importerName = responseStringDummy;
         if(document.UPSIntlAddrForm.addressType.value=='SoldToAddr')
          {
          document.UPSIntlAddrForm.AttentionName.value=importerName;
       }
       }
    }
    var url="aascAjaxIntlImporterDetail.jsp?currentImporter="+currentImporter;
    xmlHttp.open("GET",url,true);  // Calling 
    xmlHttp.send(null);  
}

function getIntlCommodityLineDetails(){
    var currentCommItem = document.aascStampsIntlShipmentsForm.selCommItems.value;
    var shipFlag = document.aascStampsIntlShipmentsForm.shipFlagStr.value;
    if(currentCommItem == 'Select')
    {
       disableCommodityDetailDiv();
       document.aascStampsIntlShipmentsForm.CountryOfManufacture.value="US";
       document.aascStampsIntlShipmentsForm.Quantity.value="";
       document.aascStampsIntlShipmentsForm.UnitPrice.value="";
       document.aascStampsIntlShipmentsForm.Description.value="";
       document.aascStampsIntlShipmentsForm.HarmonizedCode.value="";
       document.aascStampsIntlShipmentsForm.addComm.disabled=true;
    }
    else if(currentCommItem == 'Create'){
       enableCommodityDetailDiv();
       document.aascStampsIntlShipmentsForm.CountryOfManufacture.value="US";
       document.aascStampsIntlShipmentsForm.Quantity.value="";
       document.aascStampsIntlShipmentsForm.UnitPrice.value="";
       document.aascStampsIntlShipmentsForm.Description.value="";
       document.aascStampsIntlShipmentsForm.HarmonizedCode.value="";
       
       document.aascStampsIntlShipmentsForm.Weight.value="";
 
       if(shipFlag == 'Y'){
        document.aascStampsIntlShipmentsForm.addComm.disabled=true;
       }else{
        document.aascStampsIntlShipmentsForm.addComm.disabled=false;
       }
    }
    else {
        if(shipFlag == 'Y'){
        document.aascStampsIntlShipmentsForm.addComm.disabled=true;
       }else{
        document.aascStampsIntlShipmentsForm.addComm.disabled=false;
       }        
       enableCommodityDetailDiv();
        
        getAjaxInlCommodityDetails();
    }
}

function enableCommodityDetailDiv(){
 document.getElementById('commodityDetailDiv').style.display ="";
 document.getElementById('updateCommodityDetailDiv').style.display ="";
 document.getElementById('addCommodityDetailDiv').style.display ="";
}

function disableCommodityDetailDiv(){
 document.getElementById('commodityDetailDiv').style.display ="none";
 document.getElementById('updateCommodityDetailDiv').style.display ="none";
 document.getElementById('addCommodityDetailDiv').style.display ="none";

}

function totalCustomValue()
{
    
     var frCharge = document.aascStampsIntlShipmentsForm.FreightCharges.value;
     var inCharge = document.aascStampsIntlShipmentsForm.InsuranceCharges.value;
     var msCharge = document.aascStampsIntlShipmentsForm.OtherCharges.value;
     var lineCustomValue = document.aascStampsIntlShipmentsForm.commCustomValue.value;
     var InvVal = document.aascStampsIntlShipmentsForm.InvVal.value;
     if(msCharge == '')
          msCharge = 0.0;
     if(inCharge == '')
          inCharge = 0.0;
     if(frCharge == '')
          frCharge = 0.0;
    for (var i = 0; i < frCharge.length; i++) {
  	    if(!(frCharge.charCodeAt(i)>47 && frCharge.charCodeAt(i)<58) && frCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascStampsIntlShipmentsForm.FreightCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < inCharge.length; i++) {
  	    if(!(inCharge.charCodeAt(i)>47 && inCharge.charCodeAt(i)<58) && inCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascStampsIntlShipmentsForm.InsuranceCharge.focus();
  	       return false;
        }
     }
     for (var i = 0; i < msCharge.length; i++) {
  	    if(!(msCharge.charCodeAt(i)>47 && msCharge.charCodeAt(i)<58) && msCharge.charCodeAt(i)!=46 )
        { 
           alert("Please Enter valid Decimal Value only");
           document.aascStampsIntlShipmentsForm.TaxesOrMiscellaneousCharge.focus();
  	       return false;
        }
     }      
     
     var rnum = parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge);
     if (rnum > 8191 && rnum < 10485) {
		     rnum = rnum-5000;
		     var newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
		     newnumber = newnumber+5000;
	  } else {
		     var newnumber = Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);
	  }
      
      document.aascStampsIntlShipmentsForm.InvVal.value = newnumber;
     //document.aascStampsIntlShipmentsForm.TotalCustomsValue.value = Math.round(parseFloat(lineCustomValue)+parseFloat(msCharge)+parseFloat(inCharge)+parseFloat(frCharge));
     
     
}
// Added by Ravi Teja to display Invoice details for Canada and Puerto Rico country. Also this function call the totalCustomValue function which calculates Invoice total
function showInvoiceForCA()
{
   
  if((window.opener.document.DynaShipmentShipSaveForm.shipToAddressCountry.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.shipToAddressCountry.value=='PR') && window.opener.document.DynaShipmentShipSaveForm.shipFromCountrylistValue.value == 'US' && !document.aascStampsIntlShipmentsForm.CommercialInvoice.checked){
       
       document.getElementById('cominvHR').style.display ="block";
      document.getElementById('ComInvoiceID').style.display ="block";
       document.getElementById('CIDetailsRow1').style.display ="none";
        document.getElementById('CIDetailsRow2').style.display ="none";
         document.getElementById('CIDetailsRow3').style.display ="none";
         document.getElementById('CMInvoice').style.display ="none";
         
  }
  else{ 
      
        document.getElementById('CIDetailsRow1').style.display ="block";
        document.getElementById('CIDetailsRow2').style.display ="block";
         document.getElementById('CIDetailsRow3').style.display ="block";
         document.getElementById('CMInvoice').style.display ="block";
         
         if((window.opener.document.DynaShipmentShipSaveForm.shipToAddressCountry.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.shipToAddressCountry.value=='PR') && window.opener.document.DynaShipmentShipSaveForm.shipFromCountrylistValue.value == 'US'){  // && document.aascStampsIntlShipmentsForm.InvVal.value == 0){
            totalCustomValue();
         }
  }
}
// Added by Ravi Teja to call the totalCustomValue function when user enters data for Freight Charges, Insurance Charges and Other Charges.
//function calculateInvoiceTotal()
//{
//   if((window.opener.document.DynaShipmentShipSaveForm.shipToAddressCountry.value=='CA' || window.opener.document.DynaShipmentShipSaveForm.shipToAddressCountry.value=='PR') && window.opener.document.DynaShipmentShipSaveForm.shipFromCountrylistValue.value == 'US'){
//            totalCustomValue();
//         }
//}

function validateProducerDetails()
{

if(document.aascStampsIntlShipmentsForm.InvVal.value == 'OpenPopup')
{
  
    document.aascStampsIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
       document.aascStampsIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetails1.png";
       

}else{

 document.aascStampsIntlShipmentsForm.NAFTA_ProduceInfo.disabled = true;
       document.aascStampsIntlShipmentsForm.NAFTA_ProduceInfo.src ="buttons/aascDetailsOff1.png";
     

}

}
function assignOrderNumber(){
    window.opener.document.DynaShipmentShipSaveForm.orderNum.value=document.aascStampsIntlShipmentsForm.orderNumber.value;
}
