// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class BatchRequest extends com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BatchBase implements java.io.Serializable {
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BatchRequestItem[] batch_items;
    protected com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PrintRequest print_request;
    
    public BatchRequest() {
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BatchRequestItem[] getBatch_items() {
        return batch_items;
    }
    
    public void setBatch_items(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.BatchRequestItem[] batch_items) {
        this.batch_items = batch_items;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PrintRequest getPrint_request() {
        return print_request;
    }
    
    public void setPrint_request(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PrintRequest print_request) {
        this.print_request = print_request;
    }
}
