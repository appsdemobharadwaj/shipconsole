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

public class CreateUnfundedIndicium_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Credentials_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Credentials");
    private static final QName ns1_Credentials_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Credentials");
    private CombinedSerializer myns1_Credentials__Credentials_LiteralSerializer;
    private static final QName ns1_Authenticator_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Authenticator");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_IntegratorTxID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "IntegratorTxID");
    private static final QName ns1_TrackingNumber_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "TrackingNumber");
    private static final QName ns1_Rate_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Rate");
    private static final QName ns1_RateV14_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "RateV14");
    private CombinedSerializer myns1_RateV14__RateV14_LiteralSerializer;
    private static final QName ns1_From_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "From");
    private static final QName ns1_Address_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Address");
    private CombinedSerializer myns1_Address__Address_LiteralSerializer;
    private static final QName ns1_To_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "To");
    private static final QName ns1_CustomerID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CustomerID");
    private static final QName ns1_Customs_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Customs");
    private static final QName ns1_CustomsV2_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CustomsV2");
    private CombinedSerializer myns1_CustomsV2__CustomsV2_LiteralSerializer;
    private static final QName ns1_SampleOnly_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "SampleOnly");
    private static final QName ns2_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer myns2__boolean__java_lang_Boolean_Boolean_Serializer;
    private static final QName ns1_ImageType_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ImageType");
    private static final QName ns1_ImageType_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ImageType");
    private CombinedSerializer myns1_ImageType__ImageType_LiteralSerializer;
    private static final QName ns1_EltronPrinterDPIType_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "EltronPrinterDPIType");
    private static final QName ns1_EltronPrinterDPIType_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "EltronPrinterDPIType");
    private CombinedSerializer myns1_EltronPrinterDPIType__EltronPrinterDPIType_LiteralSerializer;
    private static final QName ns1_memo_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "memo");
    private static final QName ns1_cost_code_id_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "cost_code_id");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__java_lang_Integer_Int_Serializer;
    private static final QName ns1_recipient_email_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "recipient_email");
    private static final QName ns1_deliveryNotification_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "deliveryNotification");
    private static final QName ns1_shipmentNotificationCC_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "shipmentNotificationCC");
    private static final QName ns1_shipmentNotificationCCToMain_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "shipmentNotificationCCToMain");
    private static final QName ns1_shipmentNotificationFromCompany_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "shipmentNotificationFromCompany");
    private static final QName ns1_shipmentNotificationCompanyInSubject_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "shipmentNotificationCompanyInSubject");
    private static final QName ns1_rotationDegrees_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "rotationDegrees");
    
    public CreateUnfundedIndicium_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public CreateUnfundedIndicium_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns1_Credentials__Credentials_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.Credentials.class, ns1_Credentials_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_RateV14__RateV14_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.RateV14.class, ns1_RateV14_TYPE_QNAME);
        myns1_Address__Address_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.Address.class, ns1_Address_TYPE_QNAME);
        myns1_CustomsV2__CustomsV2_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.CustomsV2.class, ns1_CustomsV2_TYPE_QNAME);
        myns2__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Boolean.class, ns2_boolean_TYPE_QNAME);
        myns1_ImageType__ImageType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.ImageType.class, ns1_ImageType_TYPE_QNAME);
        myns1_EltronPrinterDPIType__EltronPrinterDPIType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.EltronPrinterDPIType.class, ns1_EltronPrinterDPIType_TYPE_QNAME);
        myns2__int__java_lang_Integer_Int_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Integer.class, ns2_int_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium instance = new com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("IntegratorTxID");
        requiredElements.add("Rate");
        requiredElements.add("From");
        requiredElements.add("To");
        for (int memberIndex = 0; memberIndex <21; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Credentials_QNAME))) {
                myns1_Credentials__Credentials_LiteralSerializer.setNullable( false );
                member = myns1_Credentials__Credentials_LiteralSerializer.deserialize(ns1_Credentials_QNAME, reader, context);
                requiredElements.remove("Credentials");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCredentials((com.aasc.erp.carrier.stampsws.proxy.Credentials)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Authenticator_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_Authenticator_QNAME, reader, context);
                requiredElements.remove("Authenticator");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setAuthenticator((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_IntegratorTxID_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_IntegratorTxID_QNAME, reader, context);
                requiredElements.remove("IntegratorTxID");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setIntegratorTxID((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_TrackingNumber_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_TrackingNumber_QNAME, reader, context);
                requiredElements.remove("TrackingNumber");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setTrackingNumber((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Rate_QNAME))) {
                myns1_RateV14__RateV14_LiteralSerializer.setNullable( false );
                member = myns1_RateV14__RateV14_LiteralSerializer.deserialize(ns1_Rate_QNAME, reader, context);
                requiredElements.remove("Rate");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setRate((com.aasc.erp.carrier.stampsws.proxy.RateV14)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_From_QNAME))) {
                myns1_Address__Address_LiteralSerializer.setNullable( false );
                member = myns1_Address__Address_LiteralSerializer.deserialize(ns1_From_QNAME, reader, context);
                requiredElements.remove("From");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setFrom((com.aasc.erp.carrier.stampsws.proxy.Address)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_To_QNAME))) {
                myns1_Address__Address_LiteralSerializer.setNullable( false );
                member = myns1_Address__Address_LiteralSerializer.deserialize(ns1_To_QNAME, reader, context);
                requiredElements.remove("To");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setTo((com.aasc.erp.carrier.stampsws.proxy.Address)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CustomerID_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_CustomerID_QNAME, reader, context);
                requiredElements.remove("CustomerID");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCustomerID((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Customs_QNAME))) {
                myns1_CustomsV2__CustomsV2_LiteralSerializer.setNullable( false );
                member = myns1_CustomsV2__CustomsV2_LiteralSerializer.deserialize(ns1_Customs_QNAME, reader, context);
                requiredElements.remove("Customs");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCustoms((com.aasc.erp.carrier.stampsws.proxy.CustomsV2)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_SampleOnly_QNAME))) {
                myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_SampleOnly_QNAME, reader, context);
                requiredElements.remove("SampleOnly");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSampleOnly((java.lang.Boolean)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_ImageType_QNAME))) {
                myns1_ImageType__ImageType_LiteralSerializer.setNullable( false );
                member = myns1_ImageType__ImageType_LiteralSerializer.deserialize(ns1_ImageType_QNAME, reader, context);
                requiredElements.remove("ImageType");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setImageType((com.aasc.erp.carrier.stampsws.proxy.ImageType)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_EltronPrinterDPIType_QNAME))) {
                myns1_EltronPrinterDPIType__EltronPrinterDPIType_LiteralSerializer.setNullable( false );
                member = myns1_EltronPrinterDPIType__EltronPrinterDPIType_LiteralSerializer.deserialize(ns1_EltronPrinterDPIType_QNAME, reader, context);
                requiredElements.remove("EltronPrinterDPIType");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setEltronPrinterDPIType((com.aasc.erp.carrier.stampsws.proxy.EltronPrinterDPIType)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_memo_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_memo_QNAME, reader, context);
                requiredElements.remove("memo");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMemo((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_cost_code_id_QNAME))) {
                myns2__int__java_lang_Integer_Int_Serializer.setNullable( false );
                member = myns2__int__java_lang_Integer_Int_Serializer.deserialize(ns1_cost_code_id_QNAME, reader, context);
                requiredElements.remove("cost_code_id");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCost_code_id((java.lang.Integer)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_recipient_email_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_recipient_email_QNAME, reader, context);
                requiredElements.remove("recipient_email");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setRecipient_email((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_deliveryNotification_QNAME))) {
                myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_deliveryNotification_QNAME, reader, context);
                requiredElements.remove("deliveryNotification");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setDeliveryNotification((java.lang.Boolean)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_shipmentNotificationCC_QNAME))) {
                myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_shipmentNotificationCC_QNAME, reader, context);
                requiredElements.remove("shipmentNotificationCC");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setShipmentNotificationCC((java.lang.Boolean)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_shipmentNotificationCCToMain_QNAME))) {
                myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_shipmentNotificationCCToMain_QNAME, reader, context);
                requiredElements.remove("shipmentNotificationCCToMain");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setShipmentNotificationCCToMain((java.lang.Boolean)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_shipmentNotificationFromCompany_QNAME))) {
                myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_shipmentNotificationFromCompany_QNAME, reader, context);
                requiredElements.remove("shipmentNotificationFromCompany");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setShipmentNotificationFromCompany((java.lang.Boolean)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_shipmentNotificationCompanyInSubject_QNAME))) {
                myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_shipmentNotificationCompanyInSubject_QNAME, reader, context);
                requiredElements.remove("shipmentNotificationCompanyInSubject");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setShipmentNotificationCompanyInSubject((java.lang.Boolean)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_rotationDegrees_QNAME))) {
                myns2__int__java_lang_Integer_Int_Serializer.setNullable( false );
                member = myns2__int__java_lang_Integer_Int_Serializer.deserialize(ns1_rotationDegrees_QNAME, reader, context);
                requiredElements.remove("rotationDegrees");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setRotationDegrees((java.lang.Integer)member);
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
        com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium instance = (com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium instance = (com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium instance = (com.aasc.erp.carrier.stampsws.proxy.CreateUnfundedIndicium)obj;
        
        if (instance.getCredentials() != null) {
            myns1_Credentials__Credentials_LiteralSerializer.setNullable( false );
            myns1_Credentials__Credentials_LiteralSerializer.serialize(instance.getCredentials(), ns1_Credentials_QNAME, null, writer, context);
        }
        if (instance.getAuthenticator() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getAuthenticator(), ns1_Authenticator_QNAME, null, writer, context);
        }
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getIntegratorTxID(), ns1_IntegratorTxID_QNAME, null, writer, context);
        if (instance.getTrackingNumber() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getTrackingNumber(), ns1_TrackingNumber_QNAME, null, writer, context);
        }
        myns1_RateV14__RateV14_LiteralSerializer.setNullable( false );
        myns1_RateV14__RateV14_LiteralSerializer.serialize(instance.getRate(), ns1_Rate_QNAME, null, writer, context);
        myns1_Address__Address_LiteralSerializer.setNullable( false );
        myns1_Address__Address_LiteralSerializer.serialize(instance.getFrom(), ns1_From_QNAME, null, writer, context);
        myns1_Address__Address_LiteralSerializer.setNullable( false );
        myns1_Address__Address_LiteralSerializer.serialize(instance.getTo(), ns1_To_QNAME, null, writer, context);
        if (instance.getCustomerID() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getCustomerID(), ns1_CustomerID_QNAME, null, writer, context);
        }
        if (instance.getCustoms() != null) {
            myns1_CustomsV2__CustomsV2_LiteralSerializer.setNullable( false );
            myns1_CustomsV2__CustomsV2_LiteralSerializer.serialize(instance.getCustoms(), ns1_Customs_QNAME, null, writer, context);
        }
        if (instance.getSampleOnly() != null) {
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getSampleOnly(), ns1_SampleOnly_QNAME, null, writer, context);
        }
        if (instance.getImageType() != null) {
            myns1_ImageType__ImageType_LiteralSerializer.setNullable( false );
            myns1_ImageType__ImageType_LiteralSerializer.serialize(instance.getImageType(), ns1_ImageType_QNAME, null, writer, context);
        }
        if (instance.getEltronPrinterDPIType() != null) {
            myns1_EltronPrinterDPIType__EltronPrinterDPIType_LiteralSerializer.setNullable( false );
            myns1_EltronPrinterDPIType__EltronPrinterDPIType_LiteralSerializer.serialize(instance.getEltronPrinterDPIType(), ns1_EltronPrinterDPIType_QNAME, null, writer, context);
        }
        if (instance.getMemo() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getMemo(), ns1_memo_QNAME, null, writer, context);
        }
        if (instance.getCost_code_id() != null) {
            myns2__int__java_lang_Integer_Int_Serializer.setNullable( false );
            myns2__int__java_lang_Integer_Int_Serializer.serialize(instance.getCost_code_id(), ns1_cost_code_id_QNAME, null, writer, context);
        }
        if (instance.getRecipient_email() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getRecipient_email(), ns1_recipient_email_QNAME, null, writer, context);
        }
        if (instance.getDeliveryNotification() != null) {
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getDeliveryNotification(), ns1_deliveryNotification_QNAME, null, writer, context);
        }
        if (instance.getShipmentNotificationCC() != null) {
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getShipmentNotificationCC(), ns1_shipmentNotificationCC_QNAME, null, writer, context);
        }
        if (instance.getShipmentNotificationCCToMain() != null) {
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getShipmentNotificationCCToMain(), ns1_shipmentNotificationCCToMain_QNAME, null, writer, context);
        }
        if (instance.getShipmentNotificationFromCompany() != null) {
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getShipmentNotificationFromCompany(), ns1_shipmentNotificationFromCompany_QNAME, null, writer, context);
        }
        if (instance.getShipmentNotificationCompanyInSubject() != null) {
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getShipmentNotificationCompanyInSubject(), ns1_shipmentNotificationCompanyInSubject_QNAME, null, writer, context);
        }
        if (instance.getRotationDegrees() != null) {
            myns2__int__java_lang_Integer_Int_Serializer.setNullable( false );
            myns2__int__java_lang_Integer_Int_Serializer.serialize(instance.getRotationDegrees(), ns1_rotationDegrees_QNAME, null, writer, context);
        }
    }
}