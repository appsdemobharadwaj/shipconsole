// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class ArrayOfTrackingEvent implements java.io.Serializable {
    private com.aasc.erp.carrier.stampsws.proxy.TrackingEvent[] trackingEvent;
    
    public ArrayOfTrackingEvent() {
    }
    
    public ArrayOfTrackingEvent(com.aasc.erp.carrier.stampsws.proxy.TrackingEvent[] sourceArray) {
        trackingEvent = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.stampsws.proxy.TrackingEvent[] sourceArray) {
        this.trackingEvent = sourceArray;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.TrackingEvent[] toArray() {
        return trackingEvent;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.TrackingEvent[] getTrackingEvent() {
        return trackingEvent;
    }
    
    public void setTrackingEvent(com.aasc.erp.carrier.stampsws.proxy.TrackingEvent[] trackingEvent) {
        this.trackingEvent = trackingEvent;
    }
}
