/*========================================================================================================+
|  DESCRIPTION                                                                                            |
|    aasc_reports_js.js javascript file for Report JSP's.                                                 |
|    Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.                                                    |
|    All rights reserved.                                                                                 |
|    Author Eshwari M                                                                                     |
|    Version   1.1                                                                                        |                                                                            
|    Creation 28/11/2014                                                                                  |
|  HISTORY                                                                                                |
|    30/11/2015   Eshwari M       Removed unnecessary code and formatted code with descriptions           |
|    17/03/2015   Eshwari M       Modified getTrackingDetailsAjax() to sent labels path also in the call  |
     10/04/2015   Y Pradeep       Added validation for location in openPopupForHTML() finction when HTML is selected.
     15/04/2015   Suman G         Added Padmavathi's code to fix issue related to Role5 User --Get Tracking Details 
+=========================================================================================================*/

/* This function validates the data entered in the jsp page.
*/
function displayInfo(f)
{     
  var thisdate = new Date();
  var year = thisdate.getYear();
  var month = thisdate.getMonth()+1; 
  var date = thisdate.getDate();          
  var format = month+"-"+date+"-"+year;
            
  if(((document[f].fromDate.value).length) > 0)
  {
    if(((document[f].fromDate.value) != null) || ((document[f].fromDate.value) != "null") || ((document[f].fromDate.value) != ""))
    {
       if( (isDate(document[f].fromDate.value)==false) )
       {
          document[f].fromDate.focus();           
          return false;
       }
    }
  }
  if((f!="ArchiveEODReportForm")&& (f!="HazmatCustomEODForm")) // For rest all these validations are required
  {     
    if((document[f].fromDate.value == null) || (document[f].fromDate.value == ""))
    {
      alert("Please enter From Date");
      document[f].fromDate.focus();           
      return false;
    }
    if((document[f].toDate.value == null) || (document[f].toDate.value == ""))
    {
      alert("Please enter To Date");
      document[f].toDate.focus();           
      return false;
    }        
    if(((document[f].toDate.value).length) > 0)
    { 
       if(((document[f].toDate.value) != null) || ((document[f].toDate.value) != "null") || ((document[f].toDate.value) != ""))
       {
          if (isDate(document[f].toDate.value)==false )
          {
              document[f].toDate.focus();           
              return false;
          }
       }
    }
    if((((document[f].fromDate.value).length) > 0) && (((document[f].toDate.value).length) > 0))
    {
      if(((document[f].fromDate.value) != null) && ((document[f].toDate.value) != null))
      {
         if (compareDates(document[f].fromDate.value,document[f].toDate.value) == false )
         {
             document[f].toDate.focus();           
             return false;
         }
      }
    }
    if (validateLoc(f)==false )
    {
      //alert("Inside validateloc")
      document[f].locationIdSelect.focus();           
      return false;
    } 
  }
  return true;       
}      
      
//validate Date started
var dtCh= "-";
function isInteger(s)
{
  var i;
  for (i = 0; i < s.length; i++)
  {   
    // Check that current character is number.
    var c = s.charAt(i);
    if (((c < "0") || (c > "9"))) return false;
  }
  // All characters are numbers.
  return true;
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

function daysInFebruary (year)
{	
  return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) 
{
  for (var i = 1; i <= n; i++) {
    this[i] = 31
    if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
    if (i==2) {this[i] = 29}
  } 
  return this
} 
function isDate(dtStr)
{                       
    var daysInMonth = DaysArray(12);
    var pos1=dtStr.indexOf(dtCh); 
    var pos2=dtStr.indexOf(dtCh,pos1+1); 
    //var strYear=dtStr.substring(0,pos1);
    //var strMonth=dtStr.substring(pos1+1,pos2);
    //var strDay=dtStr.substring(pos2+1);	
    var strMonth=dtStr.substring(0,pos1);
    var strDay=dtStr.substring(pos1+1,pos2);
    var strYear=dtStr.substring(pos2+1);	
    
    strYr=strYear;
              
    //getting system date
    var thisdate = new Date();
    var year1 = thisdate.getFullYear();
    var month1 = thisdate.getMonth()+1; 
    var date = thisdate.getDate(); 
  
    if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
    if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
    for (var i = 1; i <= 3; i++) 
    {
      if (strYr.charAt(0)=="0" && strYr.length>1) 
       strYr=strYr.substring(1);
    }
    month=parseInt(strMonth);
    day=parseInt(strDay);
    year=parseInt(strYr);
    if (pos1==-1 || pos2==-1)
    {
      alert("The date format should be : mm-dd-yyyy");
      return false;
    }
    if (strYear.length != 4 || year==0 )
    {
      alert("Please enter a valid 4 digit year");
      return false;
    }
    if (strMonth.length<1 || month<1 || month>12){
      alert("Please enter a valid month");
      return false;
    }
    if (strDay.length <1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month])
    {
      alert("Please enter a valid day");
      return false
    }
    if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false)
    {
      alert("Please enter a valid date")
      return false
    }
    if(strYear < year1)
      return true;

    else if(strYear > year1)
    {
      alert(" You Cannot Enter Future Year ");
      return false;
    }
    else  if(strYear == year1)
    {
      if(strMonth < month1)
      {    
        return true;
      }
      else if(strMonth > month1)
      {
        alert(" You Cannot Enter Future Month ");
        return false;
      }
      else 
      {
        if(strDay < date)
           return true;
        if(strDay > date)
        {
          alert("You Cannot Enter Future Date ");
          return false;
        }
      }  
    }
    return true
} //end of isDate() method   
 
 
function compareDates(fromDate,toDate)
{        
    //Checking for FromDate
    var frompos1=fromDate.indexOf(dtCh) ;
    var frompos2=fromDate.indexOf(dtCh,frompos1+1) ;
                 
    //var fromYear=fromDate.substring(0,frompos1);
    //var fromMonth=fromDate.substring(frompos1+1,frompos2);
    //var fromDay=fromDate.substring(frompos2+1);
    
    var fromMonth=fromDate.substring(0,frompos1);
    var fromDay =fromDate.substring(frompos1+1,frompos2);
    var fromYear=fromDate.substring(frompos2+1);

           
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
    //var toYear=toDate.substring(0,topos1);
    //var toMonth=toDate.substring(topos1+1,topos2);
    //var toDay=toDate.substring(topos2+1);
    
    var toMonth=toDate.substring(0,topos1);
    var toDay=toDate.substring(topos1+1,topos2);
    var toYear=toDate.substring(topos2+1);
    
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
      alert("To-Date Year Should Be Greater Or Equal To From-Date Year");
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
        alert("To-Date Month Should Be Greater Or Equal To From-Date Month");
        return false;
      }
      else if(tmonth == fmonth)
      {
        if(tday >= fday)
          return true;
            
        else if(tday < fday)
        {
          alert("To-Date Day Should Be Greater Or Equal To From-Date Day");
          return false;
        }
      }   
    }
    return true;
}
 /* This function disables or enables the Location LOV based on the roleId
 */
  
function disableCheck(f)
{
  //alert("inside disable check");
  var dis=document.getElementById('roleId').value;  //document[f].roleId.value;
  if(dis==4)
  {
      //alert("inside alert disablecheck");
      document[f].locationIdSelect.disabled=true;
  }    
  else
  {
      document[f].locationIdSelect.disabled=false;
  }    
}

/* This function gets the values from the LOVs and assigns these values to hidden feilds
*/    
function getValueCarrier(f)
{
    //alert("inside get value carrier");
    var carrierValue=document[f].carrierSelect.options[document[f].carrierSelect.selectedIndex].value;
    document[f].carrierId.value=carrierValue;
    var shipMethodValue=document[f].shipMethodList.options[document[f].shipMethodList.selectedIndex].text;
    document[f].shipMethod.value=shipMethodValue;
}
    
/*
This function validates the customer, Location, Carrier and ShipMehtod LOV values on click og submit button
*/    
function validateLoc(f)
{
  var roleId = document.getElementById('roleId').value ;
  if(roleId !=4)
  {
    if(roleId == 2)
    {  
      var strname = document[f].clientIdSelect.options[document[f].clientIdSelect.selectedIndex].value;
      if(strname=="" || strname == null || strname == 'Select')
      {
        alert("Please select Customer Name ");
        document[f].clientIdSelect.focus();
        return false;
      }
    }
  }
  var strlocationId = document[f].locationIdSelect.options[document[f].locationIdSelect.selectedIndex].value;
  if(strlocationId=="" || strlocationId == null || strlocationId == 'Select')
  {
    alert("Please select Location");
    document[f].locationIdSelect.focus();
    return false;
  }
        
  if(f!="ArchiveEODReportForm" && f!="FedexEOD3DayReportForm" && f!="HazmatCustomEODForm")
  {
    var strCarrier = document[f].carrierSelect.options[document[f].carrierSelect.selectedIndex].value;
    if(strCarrier=="" || strCarrier == null)
    {
        alert("Please select one carrier");
        return false;
    }
    var strShipMethod = document[f].shipMethodList.options[document[f].shipMethodList.selectedIndex].value;
    if(strShipMethod=="Select shipmethod")
    {
        alert("Please select one shipMethod");
        return false;
    }
  }
  return true;
}
   
/* This function is used for Ajax call to load the shipmenthods based on the carrier selected */   
function shipmethajax(f)
{
   //alert("Inside shipmethajax");
   var carrierValue=document[f].carrierSelect.options[document[f].carrierSelect.selectedIndex].value
   var shipMethods = new Array();
   var xmlHttp;
   document[f].shipMethodList.options.length = 0; 
   /*document[f].shipMethodList.options[0] =  new Option("","",true,false);*/
  
   var shipMethodSelected = document[f].shipMethod.value ;
   
  
   //alert("shipMethodSelected : "+shipMethodSelected);
  
   var roleId = document.getElementById('roleId').value ;
   var clientId = 0 ;
   if(roleId == 2)
   {
      clientId = document[f].clientIdSelect.options[document[f].clientIdSelect.selectedIndex].value ;
   }
   else{
      clientId = document[f].clientIdHidden.value ; 
   }
       
   try
   {    // Firefox, Opera 8.0+, Safari    
        xmlHttp=new XMLHttpRequest();    
   }
   catch (e)
   {   // Internet Explorer    
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
        var responseStringDummy=xmlHttp.responseText;
        var index = responseStringDummy.indexOf("@");
        //alert("responseStringDummy::"+responseStringDummy);
        responseStringDummy = responseStringDummy.substring(index+3);
        //alert("responseStringDummy::"+responseStringDummy);
        
        if( responseStringDummy!=null || responseStringDummy!='' )
        {
          shipMethods=responseStringDummy.split("***");
    
          document[f].shipMethodList.options[0] =  new Option("ALL","ALL",true,true); 
          for (var i=0;i<shipMethods.length-1 ;i++ )
          {
             if(shipMethodSelected == shipMethods[i])
               document[f].shipMethodList.options[i+1] =  new Option(trim(shipMethods[i]),trim(shipMethods[i]),true,true);
             else
               document[f].shipMethodList.options[i+1] =  new Option(trim(shipMethods[i]),trim(shipMethods[i]),true,false);
          }
          
          //document[f].shipMethodList.options[i+1] =  new Option("Select shipmethod","Select shipmethod",true,true);
        }
      }
   }
   var url="aascAjaxShipMethodInfo.jsp?carrierType="+carrierValue+"&clientId="+clientId;
   xmlHttp.open("POST",url,true);  // Calling 
   xmlHttp.send(null); 
}
    
/* This function trims the string */
function trim(str)
{
    return str.replace(/^\s*|\s*$/g,"");
}

//////////////////////////////////////////////////////////////////////////////////////
 // FOR  CALENDER 
///////////////////////////////////////////////////////////////////////////////////////
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
  //alert("123");
  
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



  //////////////////////////////////////////////////////////////////////////////////////
  // FOR  CALENDER 
  ///////////////////////////////////////////////////////////////////////////////////////
  
  
////////////////////////////////////////////////////////////////////////////////////////
// For Opening HTML Reports in Popup
////////////////////////////////////////////////////////////////////////////////////////

function openPopupForHTML(f)
{
    
    if (validateLoc(f)==false )
    {
      //alert("Inside validateloc")
      document[f].locationIdSelect.focus();           
      return false;
    } 
    
    var locationId = document[f].locationIdSelect.value;
    var actionType =document[f].actionType.value;
    reportType = "";
    for (var i=0; i < document[f].reportType.length; i++)
    {
       if (document[f].reportType[i].checked)
       {
           reportType = document[f].reportType[i].value;
       }
    }
    
    var toDate =document[f].toDate.value;
    var fromDate =document[f].fromDate.value;
    var shipMethod =document[f].shipMethod.value;
    carrierId =document[f].carrierSelect.value;
    var roleId = document.getElementById('roleId').value ; 
    if("HTML"==reportType && (roleId==4 || roleId==5) )
    {
        window.open("aascReportPopup.jsp?locationId="+locationId+"&actionType="+actionType+"&reportType="+reportType+"&toDate="+toDate+"&fromDate="+fromDate+"&shipMethod="+shipMethod+"&carrierId="+carrierId,"POST",'width=600,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
    } 
    else if("HTML"==reportType && roleId==2)
    {
       var clientId = document[f].clientIdSelect.value ;
       window.open("aascReportPopup.jsp?locationId="+locationId+"&roleId="+roleId+"&clientId="+clientId+"&actionType="+actionType+"&reportType="+reportType+"&toDate="+toDate+"&fromDate="+fromDate+"&shipMethod="+shipMethod+"&carrierId="+carrierId,"POST",'width=600,height=500,top=100,left=100,scrollbars=yes, resizable=yes');
    }
    else
    {
      document[f].submit();
    }
    return true;
}
     
function submitHTMLReport()
{     
     document.aascReportsPopupForm.submit();     
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

/* This function loads the Locations based on the client logged in or the client selected in Role 2 */
function getLocations(f)
{
  //alert("Inside getLocations()");  
  var roleId = document.getElementById('roleId').value ;
  //alert("roleId : "+roleId);
  
  var clientId = "" ;
  var locationIdSelected = "" ;
  if(roleId == 2)
  {
      clientId = document[f].clientIdSelect.options[document[f].clientIdSelect.selectedIndex].value ;
  }
  else{
     clientId = document[f].clientIdHidden.value ; 
  }
  if(roleId != 4)
  {
      var selectedIndex = document[f].locationIdSelect.selectedIndex;
      //alert("selectedIndex : "+selectedIndex);
      if(selectedIndex != -1)
        locationIdSelected = document[f].locationIdSelect.options[selectedIndex].value ;
  }
  else
  {
    locationIdSelected = document[f].locationIdHidden.value ;
  }
  
  //alert("locationIdSelected : "+locationIdSelected);
      
      
  var locationIds = new Array();
  var xmlHttp;
            
  try
  {  // Firefox, Opera 8.0+, Safari    
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
      var index = responseStringDummy.indexOf("@");
      //alert("index : "+index);
      //alert("responseStringDummy::"+responseStringDummy);
      responseStringDummy = responseStringDummy.substring(index+3);
      if( responseStringDummy != 'none' || responseStringDummy!=null )
      {
         locationIds = responseStringDummy.split("***");       
         document[f].locationIdSelect.options[0] =  new Option("Select","Select",true,true);
         for (var i=0;i<locationIds.length-1 ;i++ )
         { 
             var locationName = locationIds[i].substring(0 , locationIds[i].indexOf('$'));
             var locationId = locationIds[i].substring(locationIds[i].indexOf('$')+1);
             if(locationIdSelected == locationId)
                document[f].locationIdSelect.options[i+1] =  new Option(locationName , locationId , true , true) ; 
             else
                document[f].locationIdSelect.options[i+1] =  new Option(locationName , locationId , true , false) ;  // new Option(invOrgIds[i],invOrgIds[i],true,false);
                            
          } 
       } 
       if( responseStringDummy == 'none') // checking for null values at first time
       {          
         //alert("Inside if 2");      
         document[f].locationIdSelect.options[0] =  new Option("Select","Select",true,true);
       } 
    }
  }
  var url="aascAjaxLocationsInfo.jsp?clientId="+clientId;
  xmlHttp.open("POST",url,true);  // Calling 
  xmlHttp.send(null);        
}

/*This function gets the location value from hidden fiedl and assigns to the Location LOV if nothing is selected*/
function getLocationValue(f)
{
  //alert("Inside getInvOrgValue ");
  var locationId = "" ; 
  var roleId = document.getElementById('roleId').value ;
  var selectedIndex = "" ;
  selectedIndex = document[f].locationIdSelect.selectedIndex ;
   
  if(selectedIndex != -1 && selectedIndex != 0)
     locationId = document[f].locationIdSelect.options[selectedIndex].value ;
 
  if(locationId == 'Select')
     locationId = document[f].locationIdHidden.value;
  if(locationId != 0)
  {
      document[f].locationIdSelect.value = locationId ;
  }  
}

/*This function is called when the Get Tracking Details button is called*/
function getTrackingDetails()
{ 

    var submitCount = document.carrierSLAReportForm.submitCount.value;
    if(submitCount == 1)
    {
       alert("Request Already Submitted... Please Wait");
       return;
    }
    var locationId=document.carrierSLAReportForm.locationIdSelect.options[document.carrierSLAReportForm.locationIdSelect.selectedIndex].value;
    if(locationId == '' || locationId == null || locationId == 'Select')
    {
       alert('Please select Location name');
       return;
    }
    else
    {
      document.carrierSLAReportForm.submitCount.value=1;
             getTrackingDetailsAjax();
    }
}
 
/* This function is used for Ajax call to load the Tracking details*/ 
function getTrackingDetailsAjax()
{

   var locationId = document.carrierSLAReportForm.locationIdSelect.options[document.carrierSLAReportForm.locationIdSelect.selectedIndex].value;
   var clientId = document.carrierSLAReportForm.clientIdHidden.value ; 
   var labelsPath = document.getElementById('labelsPath').value ; 
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
//          pb.style.display = 'none';// Commmented to fix issue for role 5 user on Click of Get Tracking Details Button
          var responseStringDummy=trim(xmlHttp.responseText);
          //alert("resp string dummy:"+responseStringDummy);
          if( responseStringDummy != 'none' || responseStringDummy!=null )
          {
             alert("Tracking Details Loaded Successfully...");
             document.carrierSLAReportForm.submitCount.value=0;
          }        
      }
   }
   var pb;
   //Commmented to fix issue for role 5 user on Click of Get Tracking Details Button
//   pb = document.getElementById("indexLoad");
//   pb.innerHTML = '<img src="images/ajax-loader.gif" width="80" height ="80"/>';
//   pb.style.display = '';
   var url="aascGetTrackingDetails.jsp?clientId="+clientId+"&locationId="+locationId+"&labelsPath="+labelsPath;
   xmlHttp.open("POST",url,true);  // Calling 

   xmlHttp.send(null);      
}
