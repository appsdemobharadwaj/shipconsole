// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class ArrayOfPlan implements java.io.Serializable {
    private com.aasc.erp.carrier.stampsws.proxy.Plan[] plan;
    
    public ArrayOfPlan() {
    }
    
    public ArrayOfPlan(com.aasc.erp.carrier.stampsws.proxy.Plan[] sourceArray) {
        plan = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.stampsws.proxy.Plan[] sourceArray) {
        this.plan = sourceArray;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Plan[] toArray() {
        return plan;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Plan[] getPlan() {
        return plan;
    }
    
    public void setPlan(com.aasc.erp.carrier.stampsws.proxy.Plan[] plan) {
        this.plan = plan;
    }
}
