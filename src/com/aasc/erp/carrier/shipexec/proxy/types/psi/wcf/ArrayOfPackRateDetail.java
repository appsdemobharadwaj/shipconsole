// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ArrayOfPackRateDetail implements java.io.Serializable {
    private com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackRateDetail[] packRateDetail;
    
    public ArrayOfPackRateDetail() {
    }
    
    public ArrayOfPackRateDetail(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackRateDetail[] sourceArray) {
        packRateDetail = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackRateDetail[] sourceArray) {
        this.packRateDetail = sourceArray;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackRateDetail[] toArray() {
        return packRateDetail;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackRateDetail[] getPackRateDetail() {
        return packRateDetail;
    }
    
    public void setPackRateDetail(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackRateDetail[] packRateDetail) {
        this.packRateDetail = packRateDetail;
    }
}