// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class RateResponse implements java.io.Serializable {
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipmentResponse rateResult;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] params;
    
    public RateResponse() {
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipmentResponse getRateResult() {
        return rateResult;
    }
    
    public void setRateResult(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipmentResponse rateResult) {
        this.rateResult = rateResult;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] getParams() {
        return params;
    }
    
    public void setParams(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] params) {
        this.params = params;
    }
}
