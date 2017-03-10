// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy.runtime;

import oracle.j2ee.ws.common.encoding.*;
import oracle.j2ee.ws.common.encoding.literal.*;
import oracle.j2ee.ws.common.encoding.literal.DetailFragmentDeserializer;
import oracle.j2ee.ws.common.encoding.simpletype.*;
import oracle.j2ee.ws.common.soap.SOAPEncodingConstants;
import oracle.j2ee.ws.common.soap.SOAPEnvelopeConstants;
import oracle.j2ee.ws.common.soap.SOAPVersion;
import oracle.j2ee.ws.common.streaming.*;
import oracle.j2ee.ws.common.wsdl.document.schema.SchemaConstants;
import oracle.j2ee.ws.common.util.xml.UUID;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.AttachmentPart;

public class CreditCard_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_CreditCardType_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CreditCardType");
    private static final QName ns1_CreditCardType_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CreditCardType");
    private CombinedSerializer myns1_CreditCardType__CreditCardType_LiteralSerializer;
    private static final QName ns1_AccountNumber_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "AccountNumber");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_CVN_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CVN");
    private static final QName ns1_ExpirationDate_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ExpirationDate");
    private static final QName ns2_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final QName ns1_BillingAddress_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "BillingAddress");
    private static final QName ns1_Address_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Address");
    private CombinedSerializer myns1_Address__Address_LiteralSerializer;
    
    public CreditCard_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public CreditCard_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns1_CreditCardType__CreditCardType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.CreditCardType.class, ns1_CreditCardType_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer("", java.util.Calendar.class, ns2_dateTime_TYPE_QNAME);
        myns1_Address__Address_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.Address.class, ns1_Address_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreditCard instance = new com.aasc.erp.carrier.stampsws.proxy.CreditCard();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("CreditCardType");
        requiredElements.add("ExpirationDate");
        for (int memberIndex = 0; memberIndex <5; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CreditCardType_QNAME))) {
                myns1_CreditCardType__CreditCardType_LiteralSerializer.setNullable( false );
                member = myns1_CreditCardType__CreditCardType_LiteralSerializer.deserialize(ns1_CreditCardType_QNAME, reader, context);
                requiredElements.remove("CreditCardType");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCreditCardType((com.aasc.erp.carrier.stampsws.proxy.CreditCardType)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_AccountNumber_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_AccountNumber_QNAME, reader, context);
                requiredElements.remove("AccountNumber");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setAccountNumber((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CVN_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_CVN_QNAME, reader, context);
                requiredElements.remove("CVN");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCVN((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_ExpirationDate_QNAME))) {
                myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.setNullable( false );
                member = myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_ExpirationDate_QNAME, reader, context);
                requiredElements.remove("ExpirationDate");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setExpirationDate((java.util.Calendar)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_BillingAddress_QNAME))) {
                myns1_Address__Address_LiteralSerializer.setNullable( false );
                member = myns1_Address__Address_LiteralSerializer.deserialize(ns1_BillingAddress_QNAME, reader, context);
                requiredElements.remove("BillingAddress");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setBillingAddress((com.aasc.erp.carrier.stampsws.proxy.Address)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
        }
        if (!requiredElements.isEmpty()) {
            throw new DeserializationException( "literal.expectedElementName" , requiredElements.iterator().next().toString(), DeserializationException.FAULT_CODE_CLIENT );
        }
        
        if( reader.getState() != XMLReader.END)
        {
            reader.skipElement();
        }
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreditCard instance = (com.aasc.erp.carrier.stampsws.proxy.CreditCard)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreditCard instance = (com.aasc.erp.carrier.stampsws.proxy.CreditCard)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreditCard instance = (com.aasc.erp.carrier.stampsws.proxy.CreditCard)obj;
        
        myns1_CreditCardType__CreditCardType_LiteralSerializer.setNullable( false );
        myns1_CreditCardType__CreditCardType_LiteralSerializer.serialize(instance.getCreditCardType(), ns1_CreditCardType_QNAME, null, writer, context);
        if (instance.getAccountNumber() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getAccountNumber(), ns1_AccountNumber_QNAME, null, writer, context);
        }
        if (instance.getCVN() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getCVN(), ns1_CVN_QNAME, null, writer, context);
        }
        myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.setNullable( false );
        myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getExpirationDate(), ns1_ExpirationDate_QNAME, null, writer, context);
        if (instance.getBillingAddress() != null) {
            myns1_Address__Address_LiteralSerializer.setNullable( false );
            myns1_Address__Address_LiteralSerializer.serialize(instance.getBillingAddress(), ns1_BillingAddress_QNAME, null, writer, context);
        }
    }
}