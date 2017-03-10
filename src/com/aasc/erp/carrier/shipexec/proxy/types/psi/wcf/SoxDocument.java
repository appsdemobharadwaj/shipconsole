// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class SoxDocument extends com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ErrorBase implements java.io.Serializable {
    protected java.lang.String docFormat_FriendlyName;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.DocType doc_type;
    protected java.lang.String printer_name;
    protected java.lang.Integer printer_id;
    protected java.lang.String local_printer_port;
    protected java.lang.Integer table_id;
    protected java.lang.Integer doc_id;
    protected java.lang.Integer copies;
    protected java.lang.String[] PDFs;
    protected java.lang.String[] rawPrinterLanguage;
    protected java.lang.String[] images;
    protected java.lang.String[] positionDocXML;
    protected java.lang.String[] lineData;
    protected java.lang.Boolean autoRotatePDF;
    protected java.lang.String URL;
    protected java.lang.Long itemToPrint;
    protected java.lang.String shipdate;
    protected java.lang.String containerCode;
    protected java.lang.String shipper;
    protected java.lang.String carrier;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] doc_params;
    protected java.lang.String documentFormat;
    protected boolean overridePassThrough;
    
    public SoxDocument() {
    }
    
    public java.lang.String getDocFormat_FriendlyName() {
        return docFormat_FriendlyName;
    }
    
    public void setDocFormat_FriendlyName(java.lang.String docFormat_FriendlyName) {
        this.docFormat_FriendlyName = docFormat_FriendlyName;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.DocType getDoc_type() {
        return doc_type;
    }
    
    public void setDoc_type(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.DocType doc_type) {
        this.doc_type = doc_type;
    }
    
    public java.lang.String getPrinter_name() {
        return printer_name;
    }
    
    public void setPrinter_name(java.lang.String printer_name) {
        this.printer_name = printer_name;
    }
    
    public java.lang.Integer getPrinter_id() {
        return printer_id;
    }
    
    public void setPrinter_id(java.lang.Integer printer_id) {
        this.printer_id = printer_id;
    }
    
    public java.lang.String getLocal_printer_port() {
        return local_printer_port;
    }
    
    public void setLocal_printer_port(java.lang.String local_printer_port) {
        this.local_printer_port = local_printer_port;
    }
    
    public java.lang.Integer getTable_id() {
        return table_id;
    }
    
    public void setTable_id(java.lang.Integer table_id) {
        this.table_id = table_id;
    }
    
    public java.lang.Integer getDoc_id() {
        return doc_id;
    }
    
    public void setDoc_id(java.lang.Integer doc_id) {
        this.doc_id = doc_id;
    }
    
    public java.lang.Integer getCopies() {
        return copies;
    }
    
    public void setCopies(java.lang.Integer copies) {
        this.copies = copies;
    }
    
    public java.lang.String[] getPDFs() {
        return PDFs;
    }
    
    public void setPDFs(java.lang.String[] PDFs) {
        this.PDFs = PDFs;
    }
    
    public java.lang.String[] getRawPrinterLanguage() {
        return rawPrinterLanguage;
    }
    
    public void setRawPrinterLanguage(java.lang.String[] rawPrinterLanguage) {
        this.rawPrinterLanguage = rawPrinterLanguage;
    }
    
    public java.lang.String[] getImages() {
        return images;
    }
    
    public void setImages(java.lang.String[] images) {
        this.images = images;
    }
    
    public java.lang.String[] getPositionDocXML() {
        return positionDocXML;
    }
    
    public void setPositionDocXML(java.lang.String[] positionDocXML) {
        this.positionDocXML = positionDocXML;
    }
    
    public java.lang.String[] getLineData() {
        return lineData;
    }
    
    public void setLineData(java.lang.String[] lineData) {
        this.lineData = lineData;
    }
    
    public java.lang.Boolean getAutoRotatePDF() {
        return autoRotatePDF;
    }
    
    public void setAutoRotatePDF(java.lang.Boolean autoRotatePDF) {
        this.autoRotatePDF = autoRotatePDF;
    }
    
    public java.lang.String getURL() {
        return URL;
    }
    
    public void setURL(java.lang.String URL) {
        this.URL = URL;
    }
    
    public java.lang.Long getItemToPrint() {
        return itemToPrint;
    }
    
    public void setItemToPrint(java.lang.Long itemToPrint) {
        this.itemToPrint = itemToPrint;
    }
    
    public java.lang.String getShipdate() {
        return shipdate;
    }
    
    public void setShipdate(java.lang.String shipdate) {
        this.shipdate = shipdate;
    }
    
    public java.lang.String getContainerCode() {
        return containerCode;
    }
    
    public void setContainerCode(java.lang.String containerCode) {
        this.containerCode = containerCode;
    }
    
    public java.lang.String getShipper() {
        return shipper;
    }
    
    public void setShipper(java.lang.String shipper) {
        this.shipper = shipper;
    }
    
    public java.lang.String getCarrier() {
        return carrier;
    }
    
    public void setCarrier(java.lang.String carrier) {
        this.carrier = carrier;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] getDoc_params() {
        return doc_params;
    }
    
    public void setDoc_params(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] doc_params) {
        this.doc_params = doc_params;
    }
    
    public java.lang.String getDocumentFormat() {
        return documentFormat;
    }
    
    public void setDocumentFormat(java.lang.String documentFormat) {
        this.documentFormat = documentFormat;
    }
    
    public boolean isOverridePassThrough() {
        return overridePassThrough;
    }
    
    public void setOverridePassThrough(boolean overridePassThrough) {
        this.overridePassThrough = overridePassThrough;
    }
}
