// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ArrayOfBoxType implements java.io.Serializable {
    private com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BoxType[] boxType;
    
    public ArrayOfBoxType() {
    }
    
    public ArrayOfBoxType(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BoxType[] sourceArray) {
        boxType = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BoxType[] sourceArray) {
        this.boxType = sourceArray;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BoxType[] toArray() {
        return boxType;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BoxType[] getBoxType() {
        return boxType;
    }
    
    public void setBoxType(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BoxType[] boxType) {
        this.boxType = boxType;
    }
}