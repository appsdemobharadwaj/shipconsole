// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class LogUserTransaction implements java.io.Serializable {
    protected java.lang.String user_id;
    protected short trans_id;
    protected java.lang.String shipper;
    protected java.lang.String carrier;
    protected long msn_shipfile_id;
    
    public LogUserTransaction() {
    }
    
    public java.lang.String getUser_id() {
        return user_id;
    }
    
    public void setUser_id(java.lang.String user_id) {
        this.user_id = user_id;
    }
    
    public short getTrans_id() {
        return trans_id;
    }
    
    public void setTrans_id(short trans_id) {
        this.trans_id = trans_id;
    }
    
    public java.lang.String getShipper() {
        return shipper;
    }
    
    public void setShipper(java.lang.String shipper) {
        this.shipper = shipper;
    }
    
    public java.lang.String getCarrier() {
        return carrier;
    }
    
    public void setCarrier(java.lang.String carrier) {
        this.carrier = carrier;
    }
    
    public long getMsn_shipfile_id() {
        return msn_shipfile_id;
    }
    
    public void setMsn_shipfile_id(long msn_shipfile_id) {
        this.msn_shipfile_id = msn_shipfile_id;
    }
}
