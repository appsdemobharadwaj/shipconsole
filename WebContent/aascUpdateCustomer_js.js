/*==========================================================================+
|  DESCRIPTION                                                              |
|    javascript file for the aascUpdateCustomer.jsp validation              |
|    Author Suman Gunnda                                                    |
|    Version   1.1                                                          |
|    Creation 25/07/2014   
|    09/03/2015 Sunanda removed Profile Options related code and added backspace
                function to prevent backspace from rendering to previous page    
     01/04/2015 Suman G     Removed unnecessary alert.                      |
     21/10/2015 Suman G     Added code to update the customer.
     28/10/2015 Suman G     Added code to implement Transaction count for Update Customer.
     24/02/2016 Suman G     Added code for new Transaction Management design.
     09/03/2016 Suman G     Added code to fix #4383
+===========================================================================*/

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


//end Configurable parameters

//end Global variable

// Default events configuration

document.onmousedown = pickIt;
document.onmousemove = dragIt;
document.onmouseup = dropIt;

function NewCssCal(pCtrl,pFormat,pScroller,pShowTime,pTimeMode,pHideSeconds) {
	// get current date and time

	dtToday = new Date();
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


// starts the time spinner
function startSpin(whatSpinner, direction) {
	document.thisLoop = setInterval("nextStep('"+whatSpinner+"', '"+direction+"');", 125); //125 ms
}

// performs a single increment or decrement
function nextStep(whatSpinner, direction) {
	if(whatSpinner == "Hour") {
		if(direction == "plus") {
			Cal.SetHour(Cal.Hours + 1); RenderCssCal();
		} else if(direction == "minus") {
			Cal.SetHour(Cal.Hours - 1); RenderCssCal();
		}
	} else if(whatSpinner == "Minute") {
		if(direction == "plus") {
			Cal.SetMinute(parseInt(Cal.Minutes,10) + 1);
                       // return false;
                        RenderCssCal();
		} else if(direction == "minus") {
			Cal.SetMinute(parseInt(Cal.Minutes,10) - 1); RenderCssCal();
		}
	}

}

//stops the time spinner
function stopSpin() {
	clearInterval(document.thisLoop);
}

function save()
                {
//                alert('make paymennt;::::'+document.getElementById('makePaymentId').value);
                if(document.getElementById('makePaymentId').checked == true)
                    document.getElementById('makePaymentId').value = 'Y';
                else
                    document.getElementById('makePaymentId').value = 'N';
                    
                var companyName = document.UpdateCustomerForm.companyName.value;
                var contactName = document.UpdateCustomerForm.contactName.value;
                var addrLine1 = document.UpdateCustomerForm.addressLine1.value;
                var addrLine2 = document.UpdateCustomerForm.addressLine2.value;
                var City = document.UpdateCustomerForm.city.value;
                var state = document.UpdateCustomerForm.state.value;
                
                var postalcode = document.UpdateCustomerForm.postalCode.value;
                var phoneNo = document.UpdateCustomerForm.phoneNumber.value;
                var country = document.UpdateCustomerForm.countryCode.value;
//                var pricing = document.UpdateCustomerForm. .value;
                var tranCount = document.UpdateCustomerForm.transactionCount.value;
                
//                var licenseStartDt = document.UpdateCustomerForm.licenseStartDateText.value;
//                var licenseEndDt = document.UpdateCustomerForm.licenseEndDateText.value;
                
                var email = document.UpdateCustomerForm.emailAddress.value;
//                var licenseStartDateText=document.UpdateCustomerForm.licenseStartDateText.value;
//                var licenseEndDateText=document.UpdateCustomerForm.licenseEndDateText.value;
                var firstName=document.UpdateCustomerForm.firstName.value;
                 var cloudLabelPath=document.UpdateCustomerForm.CloudLabelPath.value;
                var CloudLabelPathTemp=trimStr(cloudLabelPath);
                //alert("cloudLabelPath :: "+cloudLabelPath);
                 //alert("CloudLabelPathTemp :: "+CloudLabelPathTemp);
                var con_name=/^[a-zA-Z]\w*( \w+)*$/;
                
                if(companyName.length == 0 || companyName.length == null ){
                    alert("Please enter Company Name");
                    document.UpdateCustomerForm.companyName.focus();
                    return false;
                }
                else if(companyName.length > 35){
                    alert("Company name cannot be greater than 50 char");
                    document.UpdateCustomerForm.companyName.focus();
                    return false;
            }
            else if(isSomeSpclCharInName(companyName)){
                alert("Company name cannot have special charater other than (-,/,&)");
                document.UpdateCustomerForm.companyName.focus();
                return false;
            }
            if(onlyNum(companyName))
            {
                alert(" First charecter in Company Name should not be a number ");
                document.UpdateCustomerForm.companyName.focus();
                return false;
            }          
                
                if(contactName.length == 0 || contactName.length == null ){
                    alert("Please enter Contact Name");
                    document.UpdateCustomerForm.contactName.focus();
                    return false;
                }
                else if(contactName.length > 50){
                    alert("Contact Name cannot be greater than 50 char");
                    document.UpdateCustomerForm.contactName.focus();
                    return false;
            }
            else if((!con_name.test(contactName))||/[\d]/.test(contactName))
            {
                alert("Contact Name Should have only charecters");
                document.UpdateCustomerForm.contactName.focus();
                return false;
            }
                
                if(addrLine1.length == 0 || addrLine1.length == null ){
                    alert("Please enter Address Line 1 ");
                    document.UpdateCustomerForm.addressLine1.focus();
                    return false;
                }
                else if(addrLine1.length > 36){
                    alert("Address Line 1 name cannot be greater than 36 char");
                    document.UpdateCustomerForm.addressLine1.focus();
                    return false;
                }
                  else if(isAddrCharInName(addrLine1)){
                    alert("Address Line 1 cannot have special charater other than (#,-,/,&)");
                    document.UpdateCustomerForm.addressLine1.focus();
                    return false;
                }
                    
                    
                if(addrLine2.length > 36){
                    alert("Address Line 2 name cannot be greater than 36 char");
                    document.UpdateCustomerForm.addressLine2.focus();
                    return false;
                }
                 else if(isAddrCharInName(addrLine2)){
                    alert("Address Line 2 cannot have special charater other than (#,-,/,&)");
                    document.UpdateCustomerForm.addressLine2.focus();
                    return false;
                }
                
                
                if(City.length == 0 || City.length == null ){
                    alert("Please enter City ");
                    document.UpdateCustomerForm.city.focus();
                    //document.UpdateCustomerForm.city.focus();
                    return false;
                }
                else if(City.length > 50){
                    alert("City cannot be greater than 50 char");
                    document.UpdateCustomerForm.city.focus();
                    return false;
                }
                else if(isSpclChar(City)){
                    alert("City cannot have special charater.");
                    document.UpdateCustomerForm.city.focus();
                    return false;
                }
                
                
                if(state.length == 0 || state.length == null ){
                    alert("Please enter State ");
                    document.UpdateCustomerForm.state.focus();
                    return false;
                }
                else if(state.length > 50){
                    alert("State cannot be greater than 50 char");
                    document.UpdateCustomerForm.state.focus();
                    return false;
                }
                else if(isSpclChar(state)){
                    alert("State cannot have special charater.");
                    document.UpdateCustomerForm.state.focus();
                    return false;
                }
                
                if(postalcode.length == 0 || postalcode.length == null ){
                    alert("Please enter Postal code ");
                    document.UpdateCustomerForm.postalCode.focus();
                    return false;
                }
                else if(postalcode.length > 20){
                    alert("Postal Code cannot be greater than 20 char");
                    document.UpdateCustomerForm.postalCode.focus();
                    return false;
                }
                else if(isPstlcodeSpclChar(postalcode)){
                    alert("Postal Code cannot have special charater other than (-)");
                    document.UpdateCustomerForm.postalCode.focus();
                    return false;
                }
                  
                // Suman added below if condition for select country
                 if(country == 'Select'){
                    alert("Select Country Name");
                    document.UpdateCustomerForm.countryCode.focus();
                    return false;
                }
                if(phoneNo.length == 0 || phoneNo.length == null ){
                    alert("Please enter PhoneNumber ");
                    document.UpdateCustomerForm.phoneNumber.focus();
                    return false;
                }
                else if(phoneNo.length > 20){
                    alert("PhoneNumber cannot be greater than 20 char");
                    document.UpdateCustomerForm.phoneNumber.focus();
                    return false;
                }
              
                 else if(isPhnNoSpclChar(phoneNo)){
            alert("PhoneNumber cannot have special charater other than +,-,(,)");
            document.UpdateCustomerForm.phoneNumber.focus();
            return false;
            }
            
              else if(isPhnNoChar(phoneNo))
              {
               alert("PhoneNumber cannot have Alphabet");
                document.UpdateCustomerForm.phoneNumber.focus();
                return false;
              }
              
    if(email.length == 0 || email.length == null ){
        alert("Please enter Email Address ");
        document.UpdateCustomerForm.emailAddress.focus();
        return false;
    }
    else if(email.length > 50){
        alert("Email Address cannot be greater than 50 char");
        document.UpdateCustomerForm.emailAddress.focus();
        return false;
    }
    if(!validateEmail(email)){
        alert("Please enter valid Email Address.");
        document.UpdateCustomerForm.emailAddress.focus();
        return false;
    }
                
    if(document.getElementById('makePaymentId').value == 'Y'){
        if(document.getElementById('invoiceTypeTransactionId').checked == true){
            if(tranCount.length == 0 ){
                alert("Transaction Count cannot be empty");   
                document.UpdateCustomerForm.transactionCount.focus();
                return false;
            }
            else if(isNaN(tranCount)){
                alert("Transaction Count can only be numbers");   
                document.UpdateCustomerForm.transactionCount.focus();
                return false;
            }
            else if(tranCount <= 0){
                alert("Transaction Count should be greater than 0");   
                document.UpdateCustomerForm.transactionCount.focus();
                return false;
            }
            else if(isFloat(tranCount)){
                alert("Transaction Count should be a positive number, not a decimal");   
                document.UpdateCustomerForm.transactionCount.focus();
                return false;
            }
        }    
        else{
            document.getElementById('transactionCountId').value = 0;
        }
    }
                
/*                
       if(pricing == 'Duration'){
        if(licenseStartDateText == '')
        {
            alert('Please enter the License Start Date');
            return false;
        }
        if(!validateDateFormat(licenseStartDateText)){  
            alert("Enter License Start Date in 'yyyy-mm-dd' format");
            document.UpdateCustomerForm.licenseStartDateText.focus();
            return false;
        }
        if(validateDate(licenseStartDateText)){
            alert("Entered License Start Date is wrong");
            document.UpdateCustomerForm.licenseStartDateText.focus();
            return false;            
        }
        if(licenseEndDateText == '')
        {
            alert('Please enter the License End Date');
            return false;
        }
        if(!validateDateFormat(licenseEndDateText)){
            alert("Enter License End Date in 'yyyy-mm-dd' format");
            document.UpdateCustomerForm.licenseEndDateText.focus();
            return false;
        }
        if(validateDate(licenseEndDateText)){
            alert("Entered License End Date is wrong");
            document.UpdateCustomerForm.licenseEndDateText.focus();
            return false; 
        
        }
        if(!compareDates(licenseStartDateText,licenseEndDateText)){
            alert("License End Date should be after License Start Date");
             document.UpdateCustomerForm.licenseStartDateText.focus();
            return false;            
        }
        
    }
     
   */
    if(firstName.length == 0 || firstName.length == null ){
        alert("Please enter First Name ");
        document.UpdateCustomerForm.firstName.focus();
        return false;
    }
    if(CloudLabelPathTemp.length == 0 || CloudLabelPathTemp.length == null )
    {
        alert("Please enter cloud label path");
        document.UpdateCustomerForm.CloudLabelPath.focus();
        return false;    
    }
    
    
    if(document.UpdateCustomerForm.UpdateButtonId.value == "0")
                    {
                   // alert('entered IF');
                    document.UpdateCustomerForm.UpdateButtonId.value="1";
                    return true;
                    }
                  else
                    {
                  //  alert('entered ELSE');
                    alert("Request already submitted. Please Wait.");
                    return false; 
                    }
                    //alert("enter hit");
                    //return false;
                    stopEnterKeyPress();   
                
               
                
}//end of validateFields
 
                function stopEnterKeyPress(evt) {
                  var evt = (evt) ? evt : ((event) ? event : null);
                 //alert("key event "+evt.keyCode);
                
                  if (evt.keyCode == 13)  {return false;}
                }
                
                document.onkeypress = stopEnterKeyPress; 

//sanjay added below method for create custtomer validations





 function validatePassward(x)
 {
    //alert('validate pw '+x);
   // var letters = /^(?=.*\d)(?=.*[a-z])[0-9a-zA-Z].{6,12}$/; 
     var letters = /^(?=.*\d)(?=.*[a-zA-Z]).{6,12}$/;
    if(x.match(letters)){
        return false;
        //for alpha alphanumeric 
    }
    else
        return true;
}

function isSomeSpclCharInName(variable){
    //alert(variable);
    var iChars = "!@#$%^*+=[]\\\';,.{}|\:<>?()";
    
    for (var i = 0; i < variable.length; i++) {
        if (iChars.indexOf(variable.charAt(i)) != -1) {
            return true;
        }
    }
}

function onlyNum(variable)// added by shiva
{  
    var num=/^[0-9]/;
    if(num.test(variable))
    {
        return true;
    }
}

        

         function isSpclChar(variable){
              //alert(variable);
              var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\:<>?";
    
                  for (var i = 0; i < variable.length; i++) {
                if (iChars.indexOf(variable.charAt(i)) != -1) {
                   return true;
                 }
                 }
                }
                
               function isPhnNoSpclChar(variable)
              {
                var iChars = "~`!@#$%^&*=[]\\\';,./{}|\:<>?";
                 for (var i = 0; i < variable.length; i++) {
                     if (iChars.indexOf(variable.charAt(i)) != -1  ) {
                         return true;
                         }
                 }
             }
             function isPhnNoChar(variable){
              variable = variable.replace(/[^a-zA-Z0-9]/g,'');
              if(isNaN(variable)){
                 return true;
                  }
              return false;
             }
        
             function isAddrCharInName(variable){
                  //alert(variable);
                   var iChars = "!@$%^*+=[]\';,.{}|\:<>?()";
    
               for (var i = 0; i < variable.length; i++) {
                  if (iChars.indexOf(variable.charAt(i)) != -1) {
                         return true;
                          }
                    }
             }
             function trimStr(str) {
                return str.replace(/^\s+|\s+$/g, '');
            }
function loadFunction(){
    var actionType=document.UpdateCustomerForm.actionTypeTemp.value;
    
     
//    var transactionRange = document.getElementById('transactionRangeId').value;
    
    disableFields();
    if(actionType == "ViewCustomer" || actionType == "AddNewCustomer")
    {
        document.UpdateCustomerForm.companyName.disabled="true";
        document.UpdateCustomerForm.contactName.disabled="true";
        document.UpdateCustomerForm.addressLine1.disabled="true";
        document.UpdateCustomerForm.addressLine2.disabled="true";
        document.UpdateCustomerForm.city.disabled="true";
        document.UpdateCustomerForm.state.disabled="true";
        document.UpdateCustomerForm.postalCode.disabled="true";
        document.UpdateCustomerForm.countryCode.disabled="true";
        document.UpdateCustomerForm.phoneNumber.disabled="true";
        
        document.UpdateCustomerForm.transactionCount.disabled="true";
        document.UpdateCustomerForm.firstName.disabled="true";
        document.UpdateCustomerForm.lastName.disabled="true";
        
        document.UpdateCustomerForm.emailAddress.disabled="true";
        document.UpdateCustomerForm.status.disabled="true";
        document.UpdateCustomerForm.CloudLabelPath.disabled="true";
        
        //document.UpdateCustomerForm.transactionDuration.disabled="true";
        document.UpdateCustomerForm.cummulativeCount.disabled="true";
        //document.UpdateCustomerForm.currentBalance.disabled="true";
        document.UpdateCustomerForm.discountTotal.disabled="true";
        document.UpdateCustomerForm.discount.disabled="true";
        document.UpdateCustomerForm.originalTotal.disabled="true";
        document.UpdateCustomerForm.originalCost.disabled="true";
        document.UpdateCustomerForm.transactionBalance.disabled="true";
    }
    else if(actionType == "EditCustomer"){

        document.UpdateCustomerForm.emailAddress.readOnly = true;
//        document.UpdateCustomerForm.estPkgTransc.value = "0";
//        calcOriginalCost();
        
    }

}
function isPstlcodeSpclChar(variable){
    //alert(variable);
    var iChars = "~`!@#$%^&*()+=[]\\\';,./{}|\:<>?";
    
    for (var i = 0; i < variable.length; i++) {
        if (iChars.indexOf(variable.charAt(i)) != -1) {
            return true;
        }
    }
}
function validateDateFormat(date){
        if(date.match(/^[0-9]{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])/)){
             return true;
        }
        return false;
}
function validateDate(date){
        date = date.replace(/[^a-zA-Z0-9]/g,'');
        
        var yy=date.substring(0,4);
        var mm=date.substring(4,6);
        var dd=date.substring(6,8);
        
        if(mm>12){
            return false;
        }
        else{
            var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];  
            if (mm==1 || mm>2){  
                if (dd>ListofDays[mm-1]){  
                    return false;  
                }  
            }  
            if (mm==2){
                var lyear = false;  
                if ( ((yy % 4 == 0) && ((yy % 100) != 0)) || ((yy % 400)==0)){
                    lyear = true;  
                }  
                if ((lyear==false) && (dd>=29)){
                    return false;
                }  
                if ((lyear==true) && (dd>29)){  
                    return false;
                }  
            }  
        }
}
function compareDates(date1,date2){
        date1=date1.replace(/[^a-zA-Z0-9]/g,'');
        date2=date2.replace(/[^a-zA-Z0-9]/g,'');
        var d1=new Date(date1.substring(0,4),date1.substring(4,6),date1.substring(6,8));
        var d2=new Date(date2.substring(0,4),date2.substring(4,6),date2.substring(6,8));
        if(d1>=d2){
            return false;
        }
        return true;
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
    
    // var vEventKeyCode = FindKeyCode(e);
    var vEventKeyCode= "";
    var vEventKeyCode = FindKeyCode(e);
    //alert("e.keyCode :: "+e.keyCode);
    //alert("vEventKeyCode :: "+vEventKeyCode);
    // backspace key pressed
    if(vEventKeyCode == 8 || vEventKeyCode==127)
    {
          alert(e.which);
    //    if(e.which) //Netscape
    //    {
    alert(1);
    var browser=getBrowser();
    alert("browser :: "+browser);
    if(browser=="Chrome"||browser=="Safari"||browser=="IE")
    {
        alert();
        e.preventDefault();
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
// Suman Added code to calculate cost
function calcOriginalCost(){

    document.UpdateCustomerForm.originalTotal.value=(document.UpdateCustomerForm.estPkgTransc.value)*(document.UpdateCustomerForm.originalCost.value);

    if(document.UpdateCustomerForm.discount.value!='')
    {
        document.UpdateCustomerForm.discountTotal.value=(document.UpdateCustomerForm.originalTotal.value)-((document.UpdateCustomerForm.originalTotal.value)*((document.UpdateCustomerForm.discount.value)/100));
    }
}


function hideFields(type){
//    alert(type);
    if(type == 'Duration'){
        document.getElementById('durationBased').style.display = "";
        document.getElementById('durationBased2').style.display = "";
        document.getElementById('durationBased3').style.display = "";
        document.getElementById('transactionBased').style.display = "none";
        document.getElementById('transactionBased2').style.display = "none";
        document.getElementById('transactionBased3').style.display = "none";
        getEstimatedTransactionRange();   
    }
    else if(type == 'Transaction'){
        document.getElementById('durationBased').style.display = "none";
        document.getElementById('durationBased2').style.display = "none";
        document.getElementById('durationBased3').style.display = "none";
        document.getElementById('transactionBased').style.display = "";
        document.getElementById('transactionBased2').style.display = "";
        document.getElementById('transactionBased3').style.display = "";
        getTotalFeeOnTransactionCount();
    }
}

function disableFields()
{
    var type = document.getElementById('invoiceTypeHiddenId').value;
//    alert('type::::'+type);
    document.UpdateCustomerForm.invoiceType.value = type;
    if(type == 'Duration'){
        document.getElementById('invoiceTypeDurationId').checked = true;
        
    }
    else {
        document.getElementById('invoiceTypeTransactionId').checked = true;
        getTotalFeeOnTransactionCount();
    }
//    alert('type:::'+type);
    hideFields(type);
    
    document.getElementById('totalFeeDurationId').value = document.getElementById('totalFeeId').value;
    document.getElementById('totalFeeTransactionId').value = document.getElementById('totalFeeId').value;
    
    if(document.getElementById('makePaymentId').checked == true){ 
    
        document.getElementById('customerTypeId').disabled = false;
        document.getElementById('invoiceTypeDurationId').disabled = false;
        if(document.getElementById('subscriptionExpiryFlagId').value == 'N'){
            document.getElementById('invoiceTypeTransactionId').disabled = false;
        }else{
            document.getElementById('invoiceTypeDurationId').checked = true;
            hideFields('Duration');
        }
        document.getElementById('monthlyEstimatedTransactionRangeId').disabled = false;
        document.getElementById('pricingDurationId').disabled = false;
        document.getElementById('licenseStartDateID').readonly = false;
        document.getElementById('transactionCountId').disabled = false;
        document.getElementById('totalFeeTransactionId').disabled = false;
        document.getElementById('totalFeeDurationId').disabled = false;
        document.getElementById('cumulativePackageCountTransactionId').disabled = false;
        document.getElementById('currentPackageBalanceTransactionId').disabled = false;
        document.getElementById('cumulativePackageCountDurationId').disabled = false;
        document.getElementById('currentPackageBalanceDurationId').disabled = false;
    }
    else{
        document.getElementById('customerTypeId').disabled = true;
        document.getElementById('invoiceTypeDurationId').disabled = true;
        document.getElementById('invoiceTypeTransactionId').disabled = true;
        document.getElementById('monthlyEstimatedTransactionRangeId').disabled = true;
        document.getElementById('pricingDurationId').disabled = true;
        document.getElementById('licenseStartDateID').readonly = true;
        document.getElementById('transactionCountId').disabled = true;
        document.getElementById('totalFeeTransactionId').disabled = true;
        document.getElementById('totalFeeDurationId').disabled = true;
        document.getElementById('cumulativePackageCountTransactionId').disabled = true;
        document.getElementById('currentPackageBalanceTransactionId').disabled = true;
        document.getElementById('cumulativePackageCountDurationId').disabled = true;
        document.getElementById('currentPackageBalanceDurationId').disabled = true;
            
    }
}

function getEstimatedTransactionRange()
{

    var customerType = document.getElementById('customerTypeId').value; 
    
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
            var index = responseStringDummy.indexOf('@@@');
            responseStringDummy = responseStringDummy.substr(index+3);
            document.getElementById('monthlyEstimatedTransactionRangeId').options.length = 0;
            var count=0;
            for(i=0;responseStringDummy.length > 0; i++){
                index = responseStringDummy.indexOf('***');
                var opt = responseStringDummy.substr(0,index);
//                alert('opt::'+opt);
//                alert('transaction id::::'+document.getElementById('transactionRangeId').value);
                if(opt == document.getElementById('transactionRangeId').value)
                    count = i;
                document.getElementById('monthlyEstimatedTransactionRangeId').options[i] =  new Option(opt,opt,true,true);
                responseStringDummy = responseStringDummy.substr(index+3,responseStringDummy.length);
            }
//            alert('coutn::::'+count);
            document.getElementById('monthlyEstimatedTransactionRangeId').options[count].selected = true;
            getTotalPrice();
          }
        }
        var url="aascAjaxEstimatedTransactionRange.jsp?customerType="+customerType;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null); 
    
}

function getTotalPrice()
{

    var customerType = document.getElementById('customerTypeId').value; 
    var transactionRange = document.getElementById('monthlyEstimatedTransactionRangeId').value; 
    var durationType = document.getElementById('pricingDurationId').value; 
    
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
            var index = responseStringDummy.indexOf('@@@');
            responseStringDummy = responseStringDummy.substr(index+3);
            document.getElementById('totalFeeId').value = responseStringDummy;
            document.getElementById('totalFeeDurationId').value = responseStringDummy;
//            alert(document.CreateCustomerForm.totalFee.value);
//            document.getElementById('estimatedTransactionRangeId').options.length = 0;
            
//            for(i=0;responseStringDummy.length > 0; i++){
//                index = responseStringDummy.indexOf('***');
//                var opt = responseStringDummy.substr(0,index);
//                document.getElementById('estimatedTransactionRangeId').options[i] =  new Option(opt,opt,true,true);
//                responseStringDummy = responseStringDummy.substr(index+3,responseStringDummy.length);
//            }
//            document.getElementById('estimatedTransactionRangeId').options[i].selected = true;
          }
        }
        var url="aascAjaxTotalPrice.jsp?customerType="+customerType+"&transactionRange="+transactionRange+"&durationType="+durationType;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null); 
    
}

function getTotalFeeOnTransactionCount()
{
    var transactionCount = document.getElementById('transactionCountId').value;
//    alert('transactionCount:::'+transactionCount);
    var valid=/^[0-9 ]$/;
    if(transactionCount.length == 0 || transactionCount < 0 || isNaN(transactionCount)){
        document.getElementById('transactionCountId').value = 0;
        document.getElementById('totalFeeTransactionId').value = 0;
    }else{
        if(isFloat(transactionCount)){
            alert("Transaction Count should be a positive number, not a decimal");   
            document.UpdateCustomerForm.transactionCount.focus();
            return false;
        }
        else{
            getPriceBasedOnTransactionCount();
        }
    }
}

function getPriceBasedOnTransactionCount()
{
    var customerType = document.getElementById('customerTypeId').value; 
    var transactionCount = document.getElementById('transactionCountId').value;
    
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
            var index = responseStringDummy.indexOf('@@@');
            responseStringDummy = responseStringDummy.substr(index+3);
            document.getElementById('totalFeeId').value = responseStringDummy;
            document.getElementById('totalFeeTransactionId').value = responseStringDummy;
//            alert(document.CreateCustomerForm.totalFee.value);
//            document.getElementById('estimatedTransactionRangeId').options.length = 0;
            
//            for(i=0;responseStringDummy.length > 0; i++){
//                index = responseStringDummy.indexOf('***');
//                var opt = responseStringDummy.substr(0,index);
//                document.getElementById('estimatedTransactionRangeId').options[i] =  new Option(opt,opt,true,true);
//                responseStringDummy = responseStringDummy.substr(index+3,responseStringDummy.length);
//            }
//            document.getElementById('estimatedTransactionRangeId').options[i].selected = true;
          }
        }
        
        var url="aascAjaxTotalPrice.jsp?transactionCount="+transactionCount+"&customerType="+customerType;
        xmlHttp.open("POST",url,false);
        xmlHttp.send(null); 
    
}

function trim(str)
{
  return str.replace(/^\s*|\s*$/g,"");
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

function isFloat(n){
    return n % 1 !== 0;
}
