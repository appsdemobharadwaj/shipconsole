// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ArrayOfSoxControlProperty implements java.io.Serializable {
    private com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxControlProperty[] soxControlProperty;
    
    public ArrayOfSoxControlProperty() {
    }
    
    public ArrayOfSoxControlProperty(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxControlProperty[] sourceArray) {
        soxControlProperty = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxControlProperty[] sourceArray) {
        this.soxControlProperty = sourceArray;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxControlProperty[] toArray() {
        return soxControlProperty;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxControlProperty[] getSoxControlProperty() {
        return soxControlProperty;
    }
    
    public void setSoxControlProperty(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxControlProperty[] soxControlProperty) {
        this.soxControlProperty = soxControlProperty;
    }
}
