// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ArrayOfHazMatContent implements java.io.Serializable {
    private com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.HazMatContent[] hazMatContent;
    
    public ArrayOfHazMatContent() {
    }
    
    public ArrayOfHazMatContent(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.HazMatContent[] sourceArray) {
        hazMatContent = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.HazMatContent[] sourceArray) {
        this.hazMatContent = sourceArray;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.HazMatContent[] toArray() {
        return hazMatContent;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.HazMatContent[] getHazMatContent() {
        return hazMatContent;
    }
    
    public void setHazMatContent(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.HazMatContent[] hazMatContent) {
        this.hazMatContent = hazMatContent;
    }
}
