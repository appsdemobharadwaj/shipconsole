// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ModifyPackageList implements java.io.Serializable {
    protected java.lang.String carrier;
    protected int[] msnlist;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] packageAttributes;
    
    public ModifyPackageList() {
    }
    
    public java.lang.String getCarrier() {
        return carrier;
    }
    
    public void setCarrier(java.lang.String carrier) {
        this.carrier = carrier;
    }
    
    public int[] getMsnlist() {
        return msnlist;
    }
    
    public void setMsnlist(int[] msnlist) {
        this.msnlist = msnlist;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] getPackageAttributes() {
        return packageAttributes;
    }
    
    public void setPackageAttributes(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] packageAttributes) {
        this.packageAttributes = packageAttributes;
    }
}
