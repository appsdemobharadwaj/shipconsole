// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class ContentTypeV2 implements java.io.Serializable {
    private java.lang.String value;
    private static final String _value1String = "Commercial Sample";
    private static final String _value2String = "Gift";
    private static final String _value3String = "Document";
    private static final String _value4String = "Returned Goods";
    private static final String _value5String = "Other";
    private static final String _value6String = "Merchandise";
    private static final String _value7String = "Humanitarian Donation";
    private static final String _value8String = "Dangerous Goods";
    
    public static final java.lang.String _value1 = new java.lang.String(_value1String);
    public static final java.lang.String _value2 = new java.lang.String(_value2String);
    public static final java.lang.String _value3 = new java.lang.String(_value3String);
    public static final java.lang.String _value4 = new java.lang.String(_value4String);
    public static final java.lang.String _value5 = new java.lang.String(_value5String);
    public static final java.lang.String _value6 = new java.lang.String(_value6String);
    public static final java.lang.String _value7 = new java.lang.String(_value7String);
    public static final java.lang.String _value8 = new java.lang.String(_value8String);
    
    public static final ContentTypeV2 value1 = new ContentTypeV2(_value1);
    public static final ContentTypeV2 value2 = new ContentTypeV2(_value2);
    public static final ContentTypeV2 value3 = new ContentTypeV2(_value3);
    public static final ContentTypeV2 value4 = new ContentTypeV2(_value4);
    public static final ContentTypeV2 value5 = new ContentTypeV2(_value5);
    public static final ContentTypeV2 value6 = new ContentTypeV2(_value6);
    public static final ContentTypeV2 value7 = new ContentTypeV2(_value7);
    public static final ContentTypeV2 value8 = new ContentTypeV2(_value8);
    
    public ContentTypeV2(java.lang.String value) {
        this.value = value;
    }
    
    public java.lang.String getValue() {
        return value;
    }
    
    public static ContentTypeV2 fromValue(java.lang.String value)
        throws java.lang.IllegalStateException {
        if (value1.value.equals(value)) {
            return value1;
        }if (value2.value.equals(value)) {
            return value2;
        }if (value3.value.equals(value)) {
            return value3;
        }if (value4.value.equals(value)) {
            return value4;
        }if (value5.value.equals(value)) {
            return value5;
        }if (value6.value.equals(value)) {
            return value6;
        }if (value7.value.equals(value)) {
            return value7;
        }if (value8.value.equals(value)) {
            return value8;
        }
        throw new IllegalArgumentException();
    }
    
    public static ContentTypeV2 fromString(String value)
        throws java.lang.IllegalStateException {
        if (value.equals(_value1String)) {
            return value1;
        }if (value.equals(_value2String)) {
            return value2;
        }if (value.equals(_value3String)) {
            return value3;
        }if (value.equals(_value4String)) {
            return value4;
        }if (value.equals(_value5String)) {
            return value5;
        }if (value.equals(_value6String)) {
            return value6;
        }if (value.equals(_value7String)) {
            return value7;
        }if (value.equals(_value8String)) {
            return value8;
        }
        throw new IllegalArgumentException();
    }
    
    public String toString() {
        return value.toString();
    }
    
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContentTypeV2)) {
            return false;
        }
        return ((ContentTypeV2)obj).value.equals(value);
    }
    
    public int hashCode() {
        return value.hashCode();
    }
}
