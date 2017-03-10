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

public class CreateEnvelopeIndicium_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Credentials_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Credentials");
    private static final QName ns1_Credentials_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Credentials");
    private CombinedSerializer myns1_Credentials__Credentials_LiteralSerializer;
    private static final QName ns1_Authenticator_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Authenticator");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_IntegratorTxID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "IntegratorTxID");
    private static final QName ns1_Rate_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Rate");
    private static final QName ns1_RateV14_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "RateV14");
    private CombinedSerializer myns1_RateV14__RateV14_LiteralSerializer;
    private static final QName ns1_From_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "From");
    private static final QName ns1_Address_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Address");
    private CombinedSerializer myns1_Address__Address_LiteralSerializer;
    private static final QName ns1_To_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "To");
    private static final QName ns1_CustomerID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CustomerID");
    private static final QName ns1_Mode_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Mode");
    private static final QName ns1_CreateIndiciumModeV1_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CreateIndiciumModeV1");
    private CombinedSerializer myns1_CreateIndiciumModeV1__CreateIndiciumModeV1_LiteralSerializer;
    private static final QName ns1_ImageType_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ImageType");
    private static final QName ns1_ImageType_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ImageType");
    private CombinedSerializer myns1_ImageType__ImageType_LiteralSerializer;
    private static final QName ns1_CostCodeId_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CostCodeId");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__java_lang_Integer_Int_Serializer;
    private static final QName ns1_HideFIM_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "HideFIM");
    private static final QName ns2_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer myns2__boolean__java_lang_Boolean_Boolean_Serializer;
    
    public CreateEnvelopeIndicium_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public CreateEnvelopeIndicium_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns1_Credentials__Credentials_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.Credentials.class, ns1_Credentials_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_RateV14__RateV14_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.RateV14.class, ns1_RateV14_TYPE_QNAME);
        myns1_Address__Address_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.Address.class, ns1_Address_TYPE_QNAME);
        myns1_CreateIndiciumModeV1__CreateIndiciumModeV1_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.CreateIndiciumModeV1.class, ns1_CreateIndiciumModeV1_TYPE_QNAME);
        myns1_ImageType__ImageType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.ImageType.class, ns1_ImageType_TYPE_QNAME);
        myns2__int__java_lang_Integer_Int_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Integer.class, ns2_int_TYPE_QNAME);
        myns2__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Boolean.class, ns2_boolean_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium instance = new com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("IntegratorTxID");
        requiredElements.add("Rate");
        for (int memberIndex = 0; memberIndex <11; memberIndex++) {
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Mode_QNAME))) {
                myns1_CreateIndiciumModeV1__CreateIndiciumModeV1_LiteralSerializer.setNullable( false );
                member = myns1_CreateIndiciumModeV1__CreateIndiciumModeV1_LiteralSerializer.deserialize(ns1_Mode_QNAME, reader, context);
                requiredElements.remove("Mode");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMode((com.aasc.erp.carrier.stampsws.proxy.CreateIndiciumModeV1)member);
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CostCodeId_QNAME))) {
                myns2__int__java_lang_Integer_Int_Serializer.setNullable( false );
                member = myns2__int__java_lang_Integer_Int_Serializer.deserialize(ns1_CostCodeId_QNAME, reader, context);
                requiredElements.remove("CostCodeId");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCostCodeId((java.lang.Integer)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_HideFIM_QNAME))) {
                myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_HideFIM_QNAME, reader, context);
                requiredElements.remove("HideFIM");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setHideFIM((java.lang.Boolean)member);
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
        com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium instance = (com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium instance = (com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium instance = (com.aasc.erp.carrier.stampsws.proxy.CreateEnvelopeIndicium)obj;
        
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
        myns1_RateV14__RateV14_LiteralSerializer.setNullable( false );
        myns1_RateV14__RateV14_LiteralSerializer.serialize(instance.getRate(), ns1_Rate_QNAME, null, writer, context);
        if (instance.getFrom() != null) {
            myns1_Address__Address_LiteralSerializer.setNullable( false );
            myns1_Address__Address_LiteralSerializer.serialize(instance.getFrom(), ns1_From_QNAME, null, writer, context);
        }
        if (instance.getTo() != null) {
            myns1_Address__Address_LiteralSerializer.setNullable( false );
            myns1_Address__Address_LiteralSerializer.serialize(instance.getTo(), ns1_To_QNAME, null, writer, context);
        }
        if (instance.getCustomerID() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getCustomerID(), ns1_CustomerID_QNAME, null, writer, context);
        }
        if (instance.getMode() != null) {
            myns1_CreateIndiciumModeV1__CreateIndiciumModeV1_LiteralSerializer.setNullable( false );
            myns1_CreateIndiciumModeV1__CreateIndiciumModeV1_LiteralSerializer.serialize(instance.getMode(), ns1_Mode_QNAME, null, writer, context);
        }
        if (instance.getImageType() != null) {
            myns1_ImageType__ImageType_LiteralSerializer.setNullable( false );
            myns1_ImageType__ImageType_LiteralSerializer.serialize(instance.getImageType(), ns1_ImageType_QNAME, null, writer, context);
        }
        if (instance.getCostCodeId() != null) {
            myns2__int__java_lang_Integer_Int_Serializer.setNullable( false );
            myns2__int__java_lang_Integer_Int_Serializer.serialize(instance.getCostCodeId(), ns1_CostCodeId_QNAME, null, writer, context);
        }
        if (instance.getHideFIM() != null) {
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.setNullable( false );
            myns2__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getHideFIM(), ns1_HideFIM_QNAME, null, writer, context);
        }
    }
}
