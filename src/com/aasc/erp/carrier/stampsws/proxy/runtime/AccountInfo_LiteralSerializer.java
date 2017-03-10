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

public class AccountInfo_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_CustomerID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CustomerID");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__int_Int_Serializer;
    private static final QName ns1_MeterNumber_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MeterNumber");
    private static final QName ns1_UserID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "UserID");
    private static final QName ns1_PostageBalance_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PostageBalance");
    private static final QName ns1_PostageBalance_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PostageBalance");
    private CombinedSerializer myns1_PostageBalance__PostageBalance_LiteralSerializer;
    private static final QName ns1_MaxPostageBalance_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MaxPostageBalance");
    private static final QName ns2_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer myns2_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final QName ns1_LPOCity_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "LPOCity");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_LPOState_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "LPOState");
    private static final QName ns1_LPOZip_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "LPOZip");
    private static final QName ns1_AccountId_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "AccountId");
    private static final QName ns2_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer myns2__long__java_lang_Long_Long_Serializer;
    private static final QName ns1_CorpID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CorpID");
    private static final QName ns1_StoreID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "StoreID");
    private static final QName ns1_CostCodeLimit_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CostCodeLimit");
    private static final QName ns1_MeterBalanceLimit_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MeterBalanceLimit");
    private static final QName ns1_MonthlyPostagePurchaseLimit_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MonthlyPostagePurchaseLimit");
    private static final QName ns1_MaxUsers_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MaxUsers");
    private static final QName ns1_Capabilities_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Capabilities");
    private static final QName ns1_CapabilitiesV7_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CapabilitiesV7");
    private CombinedSerializer myns1_CapabilitiesV7__CapabilitiesV7_LiteralSerializer;
    private static final QName ns1_MeterPhysicalAddress_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MeterPhysicalAddress");
    private static final QName ns1_Address_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Address");
    private CombinedSerializer myns1_Address__Address_LiteralSerializer;
    private static final QName ns1_ResubmitStatus_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ResubmitStatus");
    private static final QName ns1_ResubmissionStatus_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ResubmissionStatus");
    private CombinedSerializer myns1_ResubmissionStatus__ResubmissionStatus_LiteralSerializer;
    private static final QName ns1_ResubmitCookie_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ResubmitCookie");
    private static final QName ns1_PlanID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PlanID");
    private static final QName ns1_PendingPlanId_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PendingPlanId");
    private CombinedSerializer myns2__int__java_lang_Integer_Int_Serializer;
    private static final QName ns1_Username_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Username");
    
    public AccountInfo_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public AccountInfo_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
        myns1_PostageBalance__PostageBalance_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.PostageBalance.class, ns1_PostageBalance_TYPE_QNAME);
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer("", java.math.BigDecimal.class, ns2_decimal_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns2__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Long.class, ns2_long_TYPE_QNAME);
        myns1_CapabilitiesV7__CapabilitiesV7_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.CapabilitiesV7.class, ns1_CapabilitiesV7_TYPE_QNAME);
        myns1_Address__Address_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.Address.class, ns1_Address_TYPE_QNAME);
        myns1_ResubmissionStatus__ResubmissionStatus_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.ResubmissionStatus.class, ns1_ResubmissionStatus_TYPE_QNAME);
        myns2__int__java_lang_Integer_Int_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Integer.class, ns2_int_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.AccountInfo instance = new com.aasc.erp.carrier.stampsws.proxy.AccountInfo();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("CustomerID");
        requiredElements.add("MeterNumber");
        requiredElements.add("UserID");
        requiredElements.add("PostageBalance");
        requiredElements.add("MaxPostageBalance");
        requiredElements.add("LPOCity");
        requiredElements.add("LPOState");
        requiredElements.add("LPOZip");
        requiredElements.add("CorpID");
        requiredElements.add("StoreID");
        requiredElements.add("CostCodeLimit");
        requiredElements.add("MeterBalanceLimit");
        requiredElements.add("MonthlyPostagePurchaseLimit");
        requiredElements.add("MaxUsers");
        requiredElements.add("Capabilities");
        requiredElements.add("MeterPhysicalAddress");
        requiredElements.add("ResubmitStatus");
        requiredElements.add("PlanID");
        requiredElements.add("Username");
        for (int memberIndex = 0; memberIndex <22; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CustomerID_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_CustomerID_QNAME, reader, context);
                requiredElements.remove("CustomerID");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCustomerID(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MeterNumber_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_MeterNumber_QNAME, reader, context);
                requiredElements.remove("MeterNumber");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMeterNumber(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_UserID_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_UserID_QNAME, reader, context);
                requiredElements.remove("UserID");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setUserID(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_PostageBalance_QNAME))) {
                myns1_PostageBalance__PostageBalance_LiteralSerializer.setNullable( false );
                member = myns1_PostageBalance__PostageBalance_LiteralSerializer.deserialize(ns1_PostageBalance_QNAME, reader, context);
                requiredElements.remove("PostageBalance");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setPostageBalance((com.aasc.erp.carrier.stampsws.proxy.PostageBalance)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MaxPostageBalance_QNAME))) {
                myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
                member = myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_MaxPostageBalance_QNAME, reader, context);
                requiredElements.remove("MaxPostageBalance");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMaxPostageBalance((java.math.BigDecimal)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_LPOCity_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_LPOCity_QNAME, reader, context);
                requiredElements.remove("LPOCity");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setLPOCity((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_LPOState_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_LPOState_QNAME, reader, context);
                requiredElements.remove("LPOState");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setLPOState((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_LPOZip_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_LPOZip_QNAME, reader, context);
                requiredElements.remove("LPOZip");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setLPOZip((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_AccountId_QNAME))) {
                myns2__long__java_lang_Long_Long_Serializer.setNullable( false );
                member = myns2__long__java_lang_Long_Long_Serializer.deserialize(ns1_AccountId_QNAME, reader, context);
                requiredElements.remove("AccountId");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setAccountId((java.lang.Long)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CorpID_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_CorpID_QNAME, reader, context);
                requiredElements.remove("CorpID");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCorpID(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_StoreID_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_StoreID_QNAME, reader, context);
                requiredElements.remove("StoreID");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setStoreID((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CostCodeLimit_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_CostCodeLimit_QNAME, reader, context);
                requiredElements.remove("CostCodeLimit");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCostCodeLimit(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MeterBalanceLimit_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_MeterBalanceLimit_QNAME, reader, context);
                requiredElements.remove("MeterBalanceLimit");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMeterBalanceLimit(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MonthlyPostagePurchaseLimit_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_MonthlyPostagePurchaseLimit_QNAME, reader, context);
                requiredElements.remove("MonthlyPostagePurchaseLimit");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMonthlyPostagePurchaseLimit(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MaxUsers_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_MaxUsers_QNAME, reader, context);
                requiredElements.remove("MaxUsers");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMaxUsers(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Capabilities_QNAME))) {
                myns1_CapabilitiesV7__CapabilitiesV7_LiteralSerializer.setNullable( false );
                member = myns1_CapabilitiesV7__CapabilitiesV7_LiteralSerializer.deserialize(ns1_Capabilities_QNAME, reader, context);
                requiredElements.remove("Capabilities");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCapabilities((com.aasc.erp.carrier.stampsws.proxy.CapabilitiesV7)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MeterPhysicalAddress_QNAME))) {
                myns1_Address__Address_LiteralSerializer.setNullable( false );
                member = myns1_Address__Address_LiteralSerializer.deserialize(ns1_MeterPhysicalAddress_QNAME, reader, context);
                requiredElements.remove("MeterPhysicalAddress");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMeterPhysicalAddress((com.aasc.erp.carrier.stampsws.proxy.Address)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_ResubmitStatus_QNAME))) {
                myns1_ResubmissionStatus__ResubmissionStatus_LiteralSerializer.setNullable( false );
                member = myns1_ResubmissionStatus__ResubmissionStatus_LiteralSerializer.deserialize(ns1_ResubmitStatus_QNAME, reader, context);
                requiredElements.remove("ResubmitStatus");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setResubmitStatus((com.aasc.erp.carrier.stampsws.proxy.ResubmissionStatus)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_ResubmitCookie_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_ResubmitCookie_QNAME, reader, context);
                requiredElements.remove("ResubmitCookie");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setResubmitCookie((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_PlanID_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_PlanID_QNAME, reader, context);
                requiredElements.remove("PlanID");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setPlanID(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_PendingPlanId_QNAME))) {
                myns2__int__java_lang_Integer_Int_Serializer.setNullable( true );
                member = myns2__int__java_lang_Integer_Int_Serializer.deserialize(ns1_PendingPlanId_QNAME, reader, context);
                requiredElements.remove("PendingPlanId");
                instance.setPendingPlanId((java.lang.Integer)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Username_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_Username_QNAME, reader, context);
                requiredElements.remove("Username");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setUsername((java.lang.String)member);
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
        com.aasc.erp.carrier.stampsws.proxy.AccountInfo instance = (com.aasc.erp.carrier.stampsws.proxy.AccountInfo)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.AccountInfo instance = (com.aasc.erp.carrier.stampsws.proxy.AccountInfo)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.AccountInfo instance = (com.aasc.erp.carrier.stampsws.proxy.AccountInfo)obj;
        
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getCustomerID()), ns1_CustomerID_QNAME, null, writer, context);
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getMeterNumber()), ns1_MeterNumber_QNAME, null, writer, context);
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getUserID()), ns1_UserID_QNAME, null, writer, context);
        myns1_PostageBalance__PostageBalance_LiteralSerializer.setNullable( false );
        myns1_PostageBalance__PostageBalance_LiteralSerializer.serialize(instance.getPostageBalance(), ns1_PostageBalance_QNAME, null, writer, context);
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getMaxPostageBalance(), ns1_MaxPostageBalance_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getLPOCity(), ns1_LPOCity_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getLPOState(), ns1_LPOState_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getLPOZip(), ns1_LPOZip_QNAME, null, writer, context);
        if (instance.getAccountId() != null) {
            myns2__long__java_lang_Long_Long_Serializer.setNullable( false );
            myns2__long__java_lang_Long_Long_Serializer.serialize(instance.getAccountId(), ns1_AccountId_QNAME, null, writer, context);
        }
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getCorpID()), ns1_CorpID_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getStoreID(), ns1_StoreID_QNAME, null, writer, context);
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getCostCodeLimit()), ns1_CostCodeLimit_QNAME, null, writer, context);
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getMeterBalanceLimit()), ns1_MeterBalanceLimit_QNAME, null, writer, context);
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getMonthlyPostagePurchaseLimit()), ns1_MonthlyPostagePurchaseLimit_QNAME, null, writer, context);
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getMaxUsers()), ns1_MaxUsers_QNAME, null, writer, context);
        myns1_CapabilitiesV7__CapabilitiesV7_LiteralSerializer.setNullable( false );
        myns1_CapabilitiesV7__CapabilitiesV7_LiteralSerializer.serialize(instance.getCapabilities(), ns1_Capabilities_QNAME, null, writer, context);
        myns1_Address__Address_LiteralSerializer.setNullable( false );
        myns1_Address__Address_LiteralSerializer.serialize(instance.getMeterPhysicalAddress(), ns1_MeterPhysicalAddress_QNAME, null, writer, context);
        myns1_ResubmissionStatus__ResubmissionStatus_LiteralSerializer.setNullable( false );
        myns1_ResubmissionStatus__ResubmissionStatus_LiteralSerializer.serialize(instance.getResubmitStatus(), ns1_ResubmitStatus_QNAME, null, writer, context);
        if (instance.getResubmitCookie() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getResubmitCookie(), ns1_ResubmitCookie_QNAME, null, writer, context);
        }
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getPlanID()), ns1_PlanID_QNAME, null, writer, context);
        myns2__int__java_lang_Integer_Int_Serializer.setNullable( true );
        myns2__int__java_lang_Integer_Int_Serializer.serialize(instance.getPendingPlanId(), ns1_PendingPlanId_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getUsername(), ns1_Username_QNAME, null, writer, context);
    }
}
