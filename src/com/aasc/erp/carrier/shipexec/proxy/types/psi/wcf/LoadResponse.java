// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class LoadResponse implements java.io.Serializable {
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipmentRequest loadResult;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] params;
    
    public LoadResponse() {
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipmentRequest getLoadResult() {
        return loadResult;
    }
    
    public void setLoadResult(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipmentRequest loadResult) {
        this.loadResult = loadResult;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] getParams() {
        return params;
    }
    
    public void setParams(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] params) {
        this.params = params;
    }
}