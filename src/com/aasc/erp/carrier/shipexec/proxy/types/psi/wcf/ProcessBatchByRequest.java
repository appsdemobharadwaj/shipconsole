// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ProcessBatchByRequest implements java.io.Serializable {
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BatchRequest batchRequest;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ProcessBatchMode batchMode;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] params;
    
    public ProcessBatchByRequest() {
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BatchRequest getBatchRequest() {
        return batchRequest;
    }
    
    public void setBatchRequest(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BatchRequest batchRequest) {
        this.batchRequest = batchRequest;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ProcessBatchMode getBatchMode() {
        return batchMode;
    }
    
    public void setBatchMode(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ProcessBatchMode batchMode) {
        this.batchMode = batchMode;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] getParams() {
        return params;
    }
    
    public void setParams(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem[] params) {
        this.params = params;
    }
}
