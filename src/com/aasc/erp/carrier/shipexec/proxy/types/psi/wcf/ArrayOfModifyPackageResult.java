// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ArrayOfModifyPackageResult implements java.io.Serializable {
    private com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ModifyPackageResult[] modifyPackageResult;
    
    public ArrayOfModifyPackageResult() {
    }
    
    public ArrayOfModifyPackageResult(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ModifyPackageResult[] sourceArray) {
        modifyPackageResult = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ModifyPackageResult[] sourceArray) {
        this.modifyPackageResult = sourceArray;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ModifyPackageResult[] toArray() {
        return modifyPackageResult;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ModifyPackageResult[] getModifyPackageResult() {
        return modifyPackageResult;
    }
    
    public void setModifyPackageResult(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ModifyPackageResult[] modifyPackageResult) {
        this.modifyPackageResult = modifyPackageResult;
    }
}
