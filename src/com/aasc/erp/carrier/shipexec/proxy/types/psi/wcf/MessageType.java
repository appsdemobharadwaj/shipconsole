// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class MessageType implements java.io.Serializable {
    private java.lang.String value;
    private static final String _NoneString = "None";
    private static final String _NotificationString = "Notification";
    private static final String _WarningString = "Warning";
    private static final String _FatalString = "Fatal";
    private static final String _HaltString = "Halt";
    
    public static final java.lang.String _None = new java.lang.String(_NoneString);
    public static final java.lang.String _Notification = new java.lang.String(_NotificationString);
    public static final java.lang.String _Warning = new java.lang.String(_WarningString);
    public static final java.lang.String _Fatal = new java.lang.String(_FatalString);
    public static final java.lang.String _Halt = new java.lang.String(_HaltString);
    
    public static final MessageType None = new MessageType(_None);
    public static final MessageType Notification = new MessageType(_Notification);
    public static final MessageType Warning = new MessageType(_Warning);
    public static final MessageType Fatal = new MessageType(_Fatal);
    public static final MessageType Halt = new MessageType(_Halt);
    
    protected MessageType(java.lang.String value) {
        this.value = value;
    }
    
    public java.lang.String getValue() {
        return value;
    }
    
    public static MessageType fromValue(java.lang.String value)
        throws java.lang.IllegalStateException {
        if (None.value.equals(value)) {
            return None;
        }if (Notification.value.equals(value)) {
            return Notification;
        }if (Warning.value.equals(value)) {
            return Warning;
        }if (Fatal.value.equals(value)) {
            return Fatal;
        }if (Halt.value.equals(value)) {
            return Halt;
        }
        throw new IllegalArgumentException();
    }
    
    public static MessageType fromString(String value)
        throws java.lang.IllegalStateException {
        if (value.equals(_NoneString)) {
            return None;
        }if (value.equals(_NotificationString)) {
            return Notification;
        }if (value.equals(_WarningString)) {
            return Warning;
        }if (value.equals(_FatalString)) {
            return Fatal;
        }if (value.equals(_HaltString)) {
            return Halt;
        }
        throw new IllegalArgumentException();
    }
    
    public String toString() {
        return value.toString();
    }
    
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessageType)) {
            return false;
        }
        return ((MessageType)obj).value.equals(value);
    }
    
    public int hashCode() {
        return value.hashCode();
    }
}
