/***********************************************************************************
This is a javascript file for javascript validations for shipmentAudit jsp
History

22-Dec-2014  Eshwari M   Added this filr for Role 2 shippig validations

***********************************************************************************/

function validate()
        {
        
            var request = document.aascShipmentAuditForm.shipmentRequest.value;
            alert("request : "+request);
            if(document.aascShipmentAuditForm.submit123.value == 'Go' && document.aascShipmentAuditForm.locationId.value != "")
            {
                //alert("inside If");
                if(request == "Adhoc")
                   document.aascShipmentAuditForm.action = "IndexRequestAction.action?requestType=Adhoc";
                else              
                   document.aascShipmentAuditForm.action = "IndexRequestAction.action?requestType=AuditAction";
                
                document.aascShipmentAuditForm.submit();
            }
            else
            {
                alert("please select Customer Name, Location");
                return false;    
            }
        }
        
        
        function onClientChange()
        {
             //alert("Inside onClientChange"); 
             // document.getElementById('orgId').selectedIndex = 0;
             document.getElementById('locationId').selectedIndex = 0;
            
             getLocations('aascShipmentAuditForm'); 
             
             //document.aascShipmentAuditForm.action = "IndexRequestAction.action?requestType=Shipment";
        
             //document.aascShipmentAuditForm.submit();
        }
        
        function getLocations(f)
        {
          //alert("Inside getLocations()");
          
          var clientId = "" ;
          var locationId = "" ;
          
          clientId = document[f].clientId.options[document[f].clientId.selectedIndex].value ;
          
          var selectedIndex = document[f].locationId.selectedIndex;
          
          if(selectedIndex != -1)
                locationId = document[f].locationId.options[selectedIndex].value ;
          
          /*  else
            {
            locationId = document[f].locationIdHidden.value ;
            }*/
          
          //alert("clientId : "+clientId);
              
          if(clientId != null && clientId != '')
          {
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
                  var responseStringDummy = xmlHttp.responseText;
                  var index = responseStringDummy.indexOf("@");
                  //alert("index : "+index);
                  //alert("responseStringDummy::"+responseStringDummy);
                  responseStringDummy = responseStringDummy.substring(index+3);
                  //alert("resp string dummy:"+responseStringDummy);
                  if( responseStringDummy != 'none' || responseStringDummy!=null )
                  {
                      locationIds = responseStringDummy.split("***");       
                      for (var i=0;i<locationIds.length-1 ;i++ )
                      { 
                          // alert("invOrgIds[i] : "+invOrgIds[i]);
                          var locationName = locationIds[i].substring(0 , locationIds[i].indexOf('$'));
                          //  alert('invOgrName : '+invOrgName);
                          var locationId = locationIds[i].substring(locationIds[i].indexOf('$')+1);
                          //   alert('invOgrId : '+invOrgId);
                          document[f].locationId.options[0] =  new Option("Select","Select",true,true);
                          document[f].locationId.options[i+1] =  new Option(locationName , locationId , true , false) ;  // new Option(invOrgIds[i],invOrgIds[i],true,false);
                                       
                      } 
                  } 
                     
                  if( responseStringDummy == 'none') // checking for null values at first time
                  {          
                    //alert("Inside if 2");      
                    document[f].locationId.options[0] =  new Option("Select","Select",true,true);
                       
                  } 
             }
          }
          var url="aascAjaxLocationsInfo.jsp?clientId="+clientId;
          xmlHttp.open("POST",url,true);  // Calling 
          xmlHttp.send(null);       
          } 
        }
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
