// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class DeleteHistoryItems implements java.io.Serializable {
    protected java.lang.String carrier;
    protected java.lang.String shipper;
    protected int[] historyItems;
    
    public DeleteHistoryItems() {
    }
    
    public java.lang.String getCarrier() {
        return carrier;
    }
    
    public void setCarrier(java.lang.String carrier) {
        this.carrier = carrier;
    }
    
    public java.lang.String getShipper() {
        return shipper;
    }
    
    public void setShipper(java.lang.String shipper) {
        this.shipper = shipper;
    }
    
    public int[] getHistoryItems() {
        return historyItems;
    }
    
    public void setHistoryItems(int[] historyItems) {
        this.historyItems = historyItems;
    }
}
