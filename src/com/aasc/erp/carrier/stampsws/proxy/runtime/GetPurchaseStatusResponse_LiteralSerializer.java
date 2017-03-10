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

public class GetPurchaseStatusResponse_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Authenticator_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Authenticator");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_PurchaseStatus_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PurchaseStatus");
    private static final QName ns1_PurchaseStatus_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PurchaseStatus");
    private CombinedSerializer myns1_PurchaseStatus__PurchaseStatus_LiteralSerializer;
    private static final QName ns1_PostageBalance_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PostageBalance");
    private static final QName ns1_PostageBalance_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PostageBalance");
    private CombinedSerializer myns1_PostageBalance__PostageBalance_LiteralSerializer;
    private static final QName ns1_RejectionReason_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "RejectionReason");
    private static final QName ns1_MIRequired_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MIRequired");
    private static final QName ns2_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer myns2__boolean__boolean_Boolean_Serializer;
    
    public GetPurchaseStatusResponse_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public GetPurchaseStatusResponse_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_PurchaseStatus__PurchaseStatus_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.PurchaseStatus.class, ns1_PurchaseStatus_TYPE_QNAME);
        myns1_PostageBalance__PostageBalance_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.PostageBalance.class, ns1_PostageBalance_TYPE_QNAME);
        myns2__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer("", boolean.class, ns2_boolean_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse instance = new com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("Authenticator");
        requiredElements.add("PurchaseStatus");
        requiredElements.add("PostageBalance");
        requiredElements.add("MIRequired");
        for (int memberIndex = 0; memberIndex <5; memberIndex++) {
            elementName = reader.getName();
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_PurchaseStatus_QNAME))) {
                myns1_PurchaseStatus__PurchaseStatus_LiteralSerializer.setNullable( false );
                member = myns1_PurchaseStatus__PurchaseStatus_LiteralSerializer.deserialize(ns1_PurchaseStatus_QNAME, reader, context);
                requiredElements.remove("PurchaseStatus");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setPurchaseStatus((com.aasc.erp.carrier.stampsws.proxy.PurchaseStatus)member);
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_RejectionReason_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_RejectionReason_QNAME, reader, context);
                requiredElements.remove("RejectionReason");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setRejectionReason((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MIRequired_QNAME))) {
                myns2__boolean__boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__boolean_Boolean_Serializer.deserialize(ns1_MIRequired_QNAME, reader, context);
                requiredElements.remove("MIRequired");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMIRequired(((Boolean)member).booleanValue());
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
        com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse instance = (com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse instance = (com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse instance = (com.aasc.erp.carrier.stampsws.proxy.GetPurchaseStatusResponse)obj;
        
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getAuthenticator(), ns1_Authenticator_QNAME, null, writer, context);
        myns1_PurchaseStatus__PurchaseStatus_LiteralSerializer.setNullable( false );
        myns1_PurchaseStatus__PurchaseStatus_LiteralSerializer.serialize(instance.getPurchaseStatus(), ns1_PurchaseStatus_QNAME, null, writer, context);
        myns1_PostageBalance__PostageBalance_LiteralSerializer.setNullable( false );
        myns1_PostageBalance__PostageBalance_LiteralSerializer.serialize(instance.getPostageBalance(), ns1_PostageBalance_QNAME, null, writer, context);
        if (instance.getRejectionReason() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getRejectionReason(), ns1_RejectionReason_QNAME, null, writer, context);
        }
        myns2__boolean__boolean_Boolean_Serializer.setNullable( false );
        myns2__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isMIRequired()), ns1_MIRequired_QNAME, null, writer, context);
    }
}
