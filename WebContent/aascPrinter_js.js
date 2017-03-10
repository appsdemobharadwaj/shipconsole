    /*==========================================================================+
    |  DESCRIPTION                                                              |
    |    javascript file for the aascShipment.jsp and                   |
    +===========================================================================*/
    /*========================================================================================
    Date        Resource       Change history
    ------------------------------------------------------------------------------------------
    13/07/2015  Y Pradeep     Added this file for Printing Labels using applets.
    22/07/2015  Y Pradeep     Added printMessage variable to get print status from aascPrinter method. Bug #3199.
    28/07/2015  Y Pradeep     Modified code to call printing functionality and resolve security exceptions. Bug #3230.
    30/07/2015  Y Pradeep     Added code in printLabel() function to check printer name is not empty. If it is empty then display alert for configuring printer. Bug #3293.
    06/08/2015  Y Pradeep     Modified code to show message when Print Label button is clicked. Bug #3324.
    25/08/2015  Y Pradeep     Modified to set label names into a array and also added Hazmat Document printing to resolve browser hanging issue. But issue still exist as applet is dependent on browser.
    30/10/2015  Shiva         Added code to print the label with waybill number instead of tracking number for DHL 
    01/11/2015  Mahesh V      Added code related to Stamps.com Integration.
    05/11/2015  Y Pradeep     Modified code to print DHL, Stamps, FedEx domestic and international and UPS domestic and international labels as expected. Bug #3907.
    05/11/2015  Y Pradeep     Added code for printing fedex return labels. Bug #3901.
    10/11/2015  Y Pradeep     Modified code in aascPrinter_js.js file by getting cloud label path and parsing it accordingly to get Customer Folders also. Modified aascPrinter_js.js file to check Hazmath Id is enable or not using a flag and getting accurate message to display success or error. Bugs #3945 and #3901.
    17/11/2015  Y Pradeep     Modified code to stop printing labels immediately after shipping and bugs #3964.
    17/11/2015  Shiva G       Added ZPL2 and EPL2 label types for DHL
    24/11/2015  Mahesh V      Added code for Bug #4024.
    09/03/2016  Y Pradeep     Modified code to call digital certificat from the application and bug #4387 and #4388.
    15/03/2016  Y Pradeep     Modified code to handle error cases for stamps international documents. Bug #4408.
    ========================================================================================*/ 
    /* Start of adding certificate */
    //uses GET to load the Intermdediate Certificate
    qz.security.setCertificatePromise(function(resolve, reject) {
        $.ajax("digital-certificate.txt").then(resolve, reject);   // Uncomment for server\
    });

    qz.security.setSignaturePromise(function(toSign) {
        return function(resolve, reject) {
            var path = window.location.href;
            path = path.substring(0, path.lastIndexOf("/")) ;
            path = path.substring(0, path.lastIndexOf("/")) + "/";
            $.ajax(path+"MessageSignerWebApp/index.jsp?request=" + toSign).then(resolve, reject);   // Uncomment for server
//            $.ajax("http://scqademo.appsassociates.com:8082/MessageSignerWebApp/index.jsp?request=" + toSign).then(resolve, reject);  // Uncomment for local use.
            //$.ajax("aascCertificateAjax.jsp?request=" + toSign).then(resolve, reject);
        };
    });
    /* End of certificiate */
    
/* For starting connection with QZ Tray*/
    qz.websocket.connect().catch(function(e) { console.error(e); });
    function startConnection() {
    
        if (!qz.websocket.isActive()) {
            qz.websocket.connect().catch(function(e) { console.error(e); });
        }
    }
    
    /* For disconnecting connection with QZ Tray*/
    function endConnection() {
        if (qz.websocket.isActive()) {
            qz.websocket.disconnect().catch(function(e) { console.error(e); });
        }
    }

    function printLabel() {
//        qz.websocket.connect().catch(function(e) { console.error(e); });
        var labelFormat = document.getElementById("labelFormat").value ;
    //var intlLabelFormat = document.getElementById("intLabelFormat").value ;
        var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        var carrierCode=strValue.substring(0,3);
        var labelPath = document.getElementById("labelPath").value; 
        var carrierName = document.getElementById("carrierNameHidden").value ;
        var printMessage = '';
        var printerName = '';
        var check = 'false';
        var packcount = document.getElementById("countPacketsID").value; 
        if(labelFormat == 'EPL' || labelFormat == 'ELTRON' || labelFormat == 'Epl' || labelFormat == 'EPL2')  // ELTRON for Fedex EPL2 format
            printerName = document.getElementById('eplPrinter').value;
        else if(labelFormat == 'ZPL' || labelFormat == 'ZEBRA' || labelFormat == 'Zpl' || labelFormat == 'AZpl' || labelFormat == 'ZPL2')  //  Zebra is for Fedex ZPLII format
            printerName = document.getElementById('zplPrinter').value;
        else if(labelFormat == 'GIF' || labelFormat == 'PNG' || labelFormat == 'Gif' || labelFormat == 'Png' || labelFormat == 'Jpg')  // for UPS GIF format or Fedex PNG Format
            printerName = document.getElementById('gifPrinter').value;
        else if(labelFormat == 'PDF' || labelFormat == 'Pdf')  // for Fedex DPL format 
            printerName = document.getElementById('pdfPrinter').value;

        var strValue = document.getElementById("shipMethodId").options[document.getElementById("shipMethodId").selectedIndex].value;
        var carrierCode = strValue.substring(0,3);    
        var packcount = document.getElementById("countPacketsID").value;
        var savedLabelPath = document.getElementById("labelPathID").value;
        savedLabelPath = savedLabelPath.substring(savedLabelPath.lastIndexOf("labels")) ;
        
        // Uncomment to get server label path.
        var path = window.location.href;
        path = path.substring(0, path.lastIndexOf("/")) ;
        path = path.substring(0, path.lastIndexOf("/")) + "/" + savedLabelPath;
        
    
//        var path = "E:\\labels\\" ;// Uncomment to get local label path.
        var labelPath = '';
        labelPath = path ;
        var returnTrackingNumber = '';
        var trackingNumber = '';
        var waybill = '';
        var errorMessage = '';
        
        if(printerName != '' && printerName != null) {
            if(labelFormat == 'PDF' || labelFormat == 'Pdf') { 
                if(carrierCode == 114) {
                        waybill = document.getElementById("wayBillId").value ;
                        printPDFLabels(printerName, labelPath, waybill);
                        
                    } else {
                        for(var index = 1; index <= packcount; index++) {
                            trackingNumber = document.getElementById('trackingNumberID'+index).value;
                            printPDFLabels(printerName, labelPath, trackingNumber);
                            
                            if(carrierCode == 111 || carrierCode == 110 || carrierCode == 100){
                                if(document.getElementById('returnShipmentID'+index).value == 'PRINTRETURNLABEL'){                        
                                    returnTrackingNumber = document.getElementById('rtnTrackingNumberID'+index).value;
                                    if(returnTrackingNumber != null && returnTrackingNumber != ''){
                                        printPDFLabels(printerName, labelPath, returnTrackingNumber+'_ReturnLabel');
                                        
                                    }
                                }
                            }
                            
                            if(carrierCode == 111){
                                if(document.getElementById('HazMatFlagID'+index).value == "Y"){
                                    if(trackingNumber != null && trackingNumber != '') {
                                        hazPrinter = document.getElementById('pdfPrinter').value;
                                        if(hazPrinter != '' &&  hazPrinter != null){
                                            printPDFLabels(printerName, labelPath, trackingNumber + '_Op900');

                                        } else {
                                            alert('Please configure printer in Options for PDF label format');
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                    
            }
            
            if(labelFormat == 'GIF' || labelFormat == 'PNG' || labelFormat == 'Gif' || labelFormat == 'Png' || labelFormat == 'Jpg') {
                if(carrierCode == 114) {
                    waybill = document.getElementById("wayBillId").value ;
                    printImageLabels(printerName, labelPath, waybill);
                    
                } else {
                    for(var index = 1; index <= packcount; index++) {
                
                        trackingNumber = document.getElementById('trackingNumberID'+index).value;
                        printImageLabels(printerName, labelPath, trackingNumber);
                        
                        if(carrierCode == 111 || carrierCode == 110 || carrierCode == 100){
                            if(document.getElementById('returnShipmentID'+index).value == 'PRINTRETURNLABEL'){                        
                                returnTrackingNumber = document.getElementById('rtnTrackingNumberID'+index).value;
                                if(returnTrackingNumber != null && returnTrackingNumber != ''){
                                    printImageLabels(printerName, labelPath, returnTrackingNumber+'_ReturnLabel');
                                    
                                }
                            }
                        }
                        
                        if(carrierCode == 111){
                            if(document.getElementById('HazMatFlagID'+index).value == "Y"){
                                if(trackingNumber != null && trackingNumber != '') {
                                    hazPrinter = document.getElementById('pdfPrinter').value;
                                    if(hazPrinter != '' &&  hazPrinter != null){
                                        printPDFLabels(printerName, labelPath, trackingNumber + '_Op900');
                                        
                                    } else {
                                        alert('Please configure printer in Options for PDF label format');
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            if(labelFormat == 'ZPL' || labelFormat == 'ZEBRA' || labelFormat == 'Zpl' || labelFormat == 'AZpl' || labelFormat == 'ZPL2') {
                if(carrierCode == 114) {
                    waybill = document.getElementById("wayBillId").value ;
                    printZPLLabels(printerName, labelPath, waybill);
                    
                } else {
                    for(var index = 1; index <= packcount; index++) {
                    
                        trackingNumber = document.getElementById('trackingNumberID'+index).value;
                        printZPLLabels(printerName, labelPath, trackingNumber);
                        
                        if(carrierCode == 111 || carrierCode == 110 || carrierCode == 100){
                            if(document.getElementById('returnShipmentID'+index).value == 'PRINTRETURNLABEL'){                        
                                returnTrackingNumber = document.getElementById('rtnTrackingNumberID'+index).value;
                                if(returnTrackingNumber != null && returnTrackingNumber != ''){
                                    printZPLLabels(printerName, labelPath, returnTrackingNumber+'_ReturnLabel');
                                    
                                }
                            }
                        }
                        
                        if(carrierCode == 111){
                            if(document.getElementById('HazMatFlagID'+index).value == "Y"){
                                if(trackingNumber != null && trackingNumber != '') {
                                    hazPrinter = document.getElementById('pdfPrinter').value;
                                    if(hazPrinter != '' &&  hazPrinter != null){
                                        printPDFLabels(printerName, labelPath, trackingNumber + '_Op900');
                                        
                                    } else {
                                        alert('Please configure printer in Options for PDF label format');
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            if(labelFormat == 'EPL' || labelFormat == 'ELTRON' || labelFormat == 'Epl' || labelFormat == 'EPL2') {
                if(carrierCode == 114) {
                    waybill = document.getElementById("wayBillId").value ;
                    printEPLLabels(printerName, labelPath, waybill);
                    
                } else {
                    for(var index = 1; index <= packcount; index++) {
                    
                        trackingNumber = document.getElementById('trackingNumberID'+index).value;
                        printEPLLabels(printerName, labelPath, trackingNumber);
                        
                        if(carrierCode == 111 || carrierCode == 110 || carrierCode == 100){
                            if(document.getElementById('returnShipmentID'+index).value == 'PRINTRETURNLABEL'){                        
                                returnTrackingNumber = document.getElementById('rtnTrackingNumberID'+index).value;
                                if(returnTrackingNumber != null && returnTrackingNumber != ''){
                                    printEPLLabels(printerName, labelPath, returnTrackingNumber+'_ReturnLabel');
                                    
                                }
                            }
                        }
                        
                        if(carrierCode == 111){
                            if(document.getElementById('HazMatFlagID'+index).value == "Y"){
                                if(trackingNumber != null && trackingNumber != '') {
                                    hazPrinter = document.getElementById('pdfPrinter').value;
                                    if(hazPrinter != '' &&  hazPrinter != null){
                                        printPDFLabels(printerName, labelPath, trackingNumber + '_Op900');
                                        
                                    } else {
                                        alert('Please configure printer in Options for PDF label format');
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        
            if(document.getElementById('intlFlag').value == 'Y' && carrierCode != 114)
            {
                //var intlDocPath = labelPath + 'intlDocs/' ;
                var pdfPrinterName = document.getElementById('pdfPrinter').value;
                var shipmentDate = document.getElementById("shipmentDateId").value ;
                var carrierName = document.getElementById("carrierNameHidden").value ;
                var orderNumber = document.getElementById("orderNumberID").value ;
                var upsMode = document.getElementById("upsMode").value;
                var displayMessage = document.getElementById("displayMessage");
            
                printIntlDocs(labelPath, labelFormat, carrierCode, printerName, trackingNumber, pdfPrinterName, shipmentDate, carrierName, orderNumber, upsMode, displayMessage);   //For printing intl docs . Uncomment this before after testing labels printing
                
            } 

            var key = document.getElementById("shipKey").value ;

        //if(key != 'aasc225'){
            var displayMessage = document.getElementById("displayMessage");

            /*if(errorMessage == ''){
                displayMessage.innerHTML = "Printed the labels Successfully ";
            }else if(errorMessage != ''){
                displayMessage.style.color = '#cc0000';
                displayMessage.innerHTML = errorMessage;
            }*/
        //}
            //return printMessage;
        } else {
            alert('Please configure printer in Options for '+labelFormat+' label format');
            return false;
        }
        
        //qz.websocket.disconnect().catch(function(e) { console.error(e); });
        
    }
    
    function printPDFLabels(printerName, labelPath, trackingNumber){
        
        //var trackingNumber = '' ;
//        var returnTrackingNumber = '';
        var displayMessage = document.getElementById("displayMessage");
        qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
            var config = qz.configs.create(printer, { jobName: trackingNumber });     // Create a default config for the found printer
            var data = [
                    { 
                        type: 'pdf', 
                        data: labelPath + trackingNumber
                    }
                   ];   // pdf
            return qz.print(config, data).then(function(){
                if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                    displayMessage.style.color = '#0000b3';
                    displayMessage.innerHTML = "Printed the labels Successfully ";
                }
            });
        }).catch(function(e) { 
            console.error(e);
            displayMessage.style.color = '#cc0000';
            displayMessage.innerHTML = 'Error in printing. '+ e.message;
        });     
        
        //endConnection();        
    }
    
    function printImageLabels(printerName, labelPath, trackingNumber){
        
        //var trackingNumber = '' ;
//        var returnTrackingNumber = '';
        var displayMessage = document.getElementById("displayMessage");

        qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
            var config = qz.configs.create(printer, { jobName: trackingNumber });     // Create a default config for the found printer
            var data = [
                    { 
                        type: 'image', 
                        data: labelPath + trackingNumber
                    }
                   ];   // Image
            return qz.print(config, data).then(function(){
                if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                    displayMessage.style.color = '#0000b3';
                    displayMessage.innerHTML = "Printed the labels Successfully ";
                }
            });
        }).catch(function(e) { 
            console.error(e);
            displayMessage.style.color = '#cc0000';
            displayMessage.innerHTML = 'Error in printing. '+ e.message;
        });                
        
        //endConnection();   
    }
    
    function printZPLLabels(printerName, labelPath, trackingNumber){
        
        //var trackingNumber = '' ;
//        var returnTrackingNumber = '';
        var displayMessage = document.getElementById("displayMessage");        
        qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
            var config = qz.configs.create(printer, { jobName: trackingNumber });       // Create a default config for the found printer
            var data = [{
                    type: 'raw', 
                    format: 'file',
                    data: labelPath + trackingNumber
                 }];   // ZPL
            return qz.print(config, data).then(function(){
                if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                    displayMessage.style.color = '#0000b3';
                    displayMessage.innerHTML = "Printed the labels Successfully ";
                }
            });
        }).catch(function(e) { 
            console.error(e); 
            displayMessage.style.color = '#cc0000';
            displayMessage.innerHTML = 'Error in printing. '+ e.message;
        }); 
    }
    
    function printEPLLabels(printerName, labelPath, trackingNumber){
        
        //var trackingNumber = '' ;
//        var returnTrackingNumber = '';
        var displayMessage = document.getElementById("displayMessage");
        
        qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
            var config = qz.configs.create(printer, { jobName: trackingNumber });       // Create a default config for the found printer
            var data = [{
                    type: 'raw', 
                    format: 'file',
                    data: labelPath + trackingNumber
                 }];   // EPL
            return qz.print(config, data).then(function(){
                if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                    displayMessage.style.color = '#0000b3';
                    displayMessage.innerHTML = "Printed the labels Successfully ";
                }
            });
        }).catch(function(e) { 
            console.error(e); 
            displayMessage.style.color = '#cc0000';
            displayMessage.innerHTML = 'Error in printing. '+ e.message;
        });                
    }
    
    function printIntlDocs(labelPath, labelFormat, carrierCode, printerName, trackingNumber, pdfPrinterName, shipmentDate, carrierName, orderNumber, upsMode, displayMessage){
        
        if(carrierCode == 115){            
            if(labelFormat == 'GIF' || labelFormat == 'PNG' || labelFormat == 'Gif' || labelFormat == 'Png' || labelFormat == 'Jpg') {
                qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
                    var config = qz.configs.create(printer, { jobName: trackingNumber + '_2' });     // Create a default config for the found printer
                    var data = [
                            { 
                                type: 'image', 
                                data: labelPath + trackingNumber + '_2'
                            }
                           ];   // Image
                    return qz.print(config, data).then(function(){
                        if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                            displayMessage.style.color = '#0000b3';
                            displayMessage.innerHTML = "Printed the labels Successfully ";
                        }
                    });
                }).catch(function(e) { 
                    console.error(e);
                    //displayMessage.style.color = '#cc0000';
                    //displayMessage.innerHTML = 'Error in printing. '+ e.message;
                });
                
                qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
                    var config = qz.configs.create(printer, { jobName: trackingNumber + '_3' });     // Create a default config for the found printer
                    var data = [
                            { 
                                type: 'image', 
                                data: labelPath + trackingNumber + '_3'
                            }
                           ];   // Image
                    return qz.print(config, data).then(function(){
                        if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                            displayMessage.style.color = '#0000b3';
                            displayMessage.innerHTML = "Printed the labels Successfully ";
                        }
                    });
                }).catch(function(e) { 
                    console.error(e);
                });
            }
            
            if(labelFormat == 'ZPL' || labelFormat == 'ZEBRA' || labelFormat == 'Zpl' || labelFormat == 'AZpl' || labelFormat == 'ZPL2') {
                qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
                    var config = qz.configs.create(printer, { jobName: trackingNumber + '_2' });       // Create a default config for the found printer
                    var data = [{
                            type: 'raw', 
                            format: 'file',
                            data: labelPath + trackingNumber + '_2'
                         }];   // ZPL
                    return qz.print(config, data).then(function(){
                        if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                            displayMessage.style.color = '#0000b3';
                            displayMessage.innerHTML = "Printed the labels Successfully ";
                        }
                    });
                }).catch(function(e) { 
                    console.error(e); 
                    //displayMessage.style.color = '#cc0000';
                    //displayMessage.innerHTML = 'Error in printing. '+ e.message;
                });
                
                qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
                    var config = qz.configs.create(printer, { jobName: trackingNumber + '_3' });       // Create a default config for the found printer
                    var data = [{
                            type: 'raw', 
                            format: 'file',
                            data: labelPath + trackingNumber + '_3'
                         }];   // ZPL
                    return qz.print(config, data).then(function(){
                        if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                            displayMessage.style.color = '#0000b3';
                            displayMessage.innerHTML = "Printed the labels Successfully ";
                        }
                    });
                }).catch(function(e) { 
                    console.error(e);
                });
            }
            
            if(labelFormat == 'EPL' || labelFormat == 'ELTRON' || labelFormat == 'Epl' || labelFormat == 'EPL2') {
                qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
                    var config = qz.configs.create(printer, { jobName: trackingNumber + '_2' });       // Create a default config for the found printer
                    var data = [{
                            type: 'raw', 
                            format: 'file',
                            data: labelPath + trackingNumber + '_2'
                         }];   // EPL
                    return qz.print(config, data).then(function(){
                        if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                            displayMessage.style.color = '#0000b3';
                            displayMessage.innerHTML = "Printed the labels Successfully ";
                        }
                    });
                }).catch(function(e) { 
                    console.error(e); 
                    displayMessage.style.color = '#cc0000';
                    displayMessage.innerHTML = 'Error in printing. '+ e.message;
                });
                
                qz.printers.find(printerName).then(function(printer) {              // Pass the printer name into the next Promise
                    var config = qz.configs.create(printer, { jobName: trackingNumber + '_3' });       // Create a default config for the found printer
                    var data = [{
                            type: 'raw', 
                            format: 'file',
                            data: labelPath + trackingNumber + '_3'
                         }];   // EPL
                    return qz.print(config, data);
                }).catch(function(e) { 
                    console.error(e); 
                });
            }
        
        } else {
            var intlDocPath = labelPath + 'intlDocs/' ;
            var year = shipmentDate.substring(0,4);
            var mon = shipmentDate.substring(5,7);
            var day = shipmentDate.substring(8,10);
            var CIDocPath = '' ;
            var document = '' ;
            if(carrierName == 'UPS') {
                if(upsMode == 'ShipExec'){
                    document = orderNumber +'_'+year+'_'+mon+'_'+day+'_CI' ;
                    CIDocPath = intlDocPath + document ;
                    
                    qz.printers.find(pdfPrinterName).then(function(printer) {              // Pass the printer name into the next Promise
                        var config = qz.configs.create(printer, { jobName: document });     // Create a default config for the found printer
                        var data = [
                                { 
                                    type: 'pdf', 
                                    data: CIDocPath
                                }
                               ];   // pdf intl doc
                        return qz.print(config, data).then(function(){
                            if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                                displayMessage.style.color = '#0000b3';
                                displayMessage.innerHTML = "Printed the labels Successfully ";
                            }
                        });
                    }).catch(function(e) { 
                        //console.error(e);
                        qz.printers.find(pdfPrinterName).then(function(printer) {              // Pass the printer name into the next Promise
                            var config = qz.configs.create(printer, { jobName: document });     // Create a default config for the found printer
                            var data = [
                                    { 
                                        type: 'image', 
                                        data: CIDocPath
                                    }
                                   ];   // pdf intl doc
                            return qz.print(config, data).then(function(){
                                if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                                    displayMessage.style.color = '#0000b3';
                                    displayMessage.innerHTML = "Printed the labels Successfully ";
                                }
                            });
                        }).catch(function(e) { 
                            console.error(e);
                            displayMessage.style.color = '#cc0000';
                            displayMessage.innerHTML = 'Error in printing. '+ e.message;
                        });
                    });
                    
                } else {
                    document = orderNumber +'_Shipment_'+year+'_'+mon+'_'+day+'_'+carrierName+'.pdf' ;
                    CIDocPath = intlDocPath + document ;
                    
                    qz.printers.find(pdfPrinterName).then(function(printer) {              // Pass the printer name into the next Promise
                        var config = qz.configs.create(printer, { jobName: document });     // Create a default config for the found printer
                        var data = [
                                { 
                                    type: 'pdf', 
                                    data: CIDocPath
                                }
                               ];   // pdf intl doc
                        return qz.print(config, data).then(function(){
                            if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                                displayMessage.style.color = '#0000b3';
                                displayMessage.innerHTML = "Printed the labels Successfully ";
                            }
                        });
                    }).catch(function(e) { 
                        console.error(e);
                        displayMessage.style.color = '#cc0000';
                        displayMessage.innerHTML = 'Error in printing. '+ e.message;
                    });
                }
            } else if(carrierName == 'FDXE') {
                document = orderNumber +'_Shipment_'+year+'_'+mon+'_'+day+'_'+'FDXECI.pdf' ;
                CIDocPath = intlDocPath + document ;
            
                qz.printers.find(pdfPrinterName).then(function(printer) {              // Pass the printer name into the next Promise
                    var config = qz.configs.create(printer, { jobName: document });     // Create a default config for the found printer
                    var data = [
                            { 
                                type: 'pdf', 
                                data: CIDocPath
                            }
                           ];   // pdf intl doc
                    return qz.print(config, data).then(function(){
                        if(displayMessage.style.color == 'rgb(0, 0, 179)'){
                            displayMessage.style.color = '#0000b3';
                            displayMessage.innerHTML = "Printed the labels Successfully ";
                        }
                    });
                }).catch(function(e) { 
                    console.error(e);
                    displayMessage.style.color = '#cc0000';
                    displayMessage.innerHTML = 'Error in printing. '+ e.message;
                });
            }
        }
    }
    
    function openStramWeighingScale(packCount){
    
        qz.usb.setUsbCallbacks(function(keys, data) {
            //console.log(keys[0] + "," + keys[1] + "," + data);  
            readScaleData(packCount, data, 2);
        });
           
        // Stores device information
        var usb = { vendor: $("#vendorIdInMainId").val(), product: $("#productId1InMainId").val(), interface: null, endpoint: null };
        
        releaseDevice(usb.vendor, usb.product);
        
        qz.usb.listInterfaces(usb.vendor, usb.product).then(function(interfaces) {
            usb.interface = '0x' + interfaces[0];
            return qz.usb.listEndpoints(usb.vendor, usb.product, usb.interface);
        }).then(function(endpoints) {
            usb.endpoint = '0x' + endpoints[endpoints.length - 1];
            return claimDevice(usb.vendor, usb.product, usb.interface);
        }).then(function() {
           return openStream(usb.vendor, usb.product, usb.endpoint);            
        }).catch(function(e) { 
            console.error(e);
        });
        
    }
    
    function closingStram(){
       var usb = { vendor: $("#vendorIdInMainId").val(), product: $("#productId1InMainId").val(), interface: null, endpoint: null }; 
       
        qz.usb.releaseDevice(usb.vendor, usb.product).catch(function(e) { 
            console.error(e);
        });
        
    }
    
    
    /** Attempts to parse scale reading from USB raw output */
    function readScaleData(packCount, data) {
        // Get status
        var status = parseInt(data[1], 16);
        switch(status) {
            case 1: // fault
            case 5: // underweight
            case 6: // overweight
            case 7: // calibrate
            case 8: // re-zero
                status = 'Error';
                break;
            case 3: // busy
                status = 'Busy';
                break;
            case 2: // stable at zero
            case 4: // stable non-zero
            default:
                status = 'Stable';
        }

        // Get precision
        var precision = parseInt(data[3], 16);
        precision = precision ^ -256; //unsigned to signed
        
        // Get units
        var units = parseInt(data[2], 16);
        switch(units) {
            case 3:
                units = 'KG';
                break;
            case 11:
                units = 'OZ';
                break;
            case 12:
                units = 'LB';
                break;
            default:
                units = 'LB';
        }
        
        // Get weight
        data.splice(0, 4);
        data.reverse();
        var weight = parseInt(data.join(''), 16);

        weight *= Math.pow(10, precision);
        weight = weight.toFixed(Math.abs(precision));

        
        //console.log(weight + units + ' - ' + status);
        
        document.getElementById('weightID'+packCount).value = weight;
        
        document.getElementById('uomID'+packCount).value = units;
                   
        return weight + units + ' - ' + status;
    }
    
    function claimDevice(vendor, product, interface){    
        return qz.usb.claimDevice(vendor, product, interface);
    }
    
    function openStream(vendor, product, endpoint){
        return qz.usb.openStream(vendor, product, endpoint, '8', '10'); 
    }

    function releaseDevice(vendor, product){
        qz.usb.releaseDevice(vendor, product);    
    }
