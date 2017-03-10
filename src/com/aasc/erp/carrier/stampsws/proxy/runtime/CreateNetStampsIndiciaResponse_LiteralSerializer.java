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

public class CreateNetStampsIndiciaResponse_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Authenticator_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Authenticator");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_IntegratorTxId_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "IntegratorTxId");
    private static final QName ns1_StampsTxId_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "StampsTxId");
    private static final QName ns1_URL_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "URL");
    private static final QName ns1_PostageBalance_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PostageBalance");
    private static final QName ns1_PostageBalance_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PostageBalance");
    private CombinedSerializer myns1_PostageBalance__PostageBalance_LiteralSerializer;
    private static final QName ns1_NetstampsStatus_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "NetstampsStatus");
    private static final QName ns1_NetstampsStatus_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "NetstampsStatus");
    private CombinedSerializer myns1_NetstampsStatus__NetstampsStatus_LiteralSerializer;
    private static final QName ns1_NetstampsIssued_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "NetstampsIssued");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__int_Int_Serializer;
    private static final QName ns1_ErrorReason_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ErrorReason");
    private static final QName ns1_Mac_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Mac");
    private static final QName ns1_IndiciaData_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "IndiciaData");
    private static final QName ns1_ArrayOfIndiciumData_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ArrayOfIndiciumData");
    private CombinedSerializer myns1_ArrayOfIndiciumData__ArrayOfIndiciumData_LiteralSerializer;
    
    public CreateNetStampsIndiciaResponse_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public CreateNetStampsIndiciaResponse_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_PostageBalance__PostageBalance_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.PostageBalance.class, ns1_PostageBalance_TYPE_QNAME);
        myns1_NetstampsStatus__NetstampsStatus_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.NetstampsStatus.class, ns1_NetstampsStatus_TYPE_QNAME);
        myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
        myns1_ArrayOfIndiciumData__ArrayOfIndiciumData_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.ArrayOfIndiciumData.class, ns1_ArrayOfIndiciumData_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse instance = new com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("Authenticator");
        requiredElements.add("IntegratorTxId");
        requiredElements.add("StampsTxId");
        requiredElements.add("PostageBalance");
        requiredElements.add("NetstampsStatus");
        requiredElements.add("NetstampsIssued");
        requiredElements.add("ErrorReason");
        requiredElements.add("Mac");
        for (int memberIndex = 0; memberIndex <10; memberIndex++) {
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_IntegratorTxId_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_IntegratorTxId_QNAME, reader, context);
                requiredElements.remove("IntegratorTxId");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setIntegratorTxId((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_StampsTxId_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_StampsTxId_QNAME, reader, context);
                requiredElements.remove("StampsTxId");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setStampsTxId((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_URL_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_URL_QNAME, reader, context);
                requiredElements.remove("URL");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setURL((java.lang.String)member);
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_NetstampsStatus_QNAME))) {
                myns1_NetstampsStatus__NetstampsStatus_LiteralSerializer.setNullable( false );
                member = myns1_NetstampsStatus__NetstampsStatus_LiteralSerializer.deserialize(ns1_NetstampsStatus_QNAME, reader, context);
                requiredElements.remove("NetstampsStatus");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setNetstampsStatus((com.aasc.erp.carrier.stampsws.proxy.NetstampsStatus)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_NetstampsIssued_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_NetstampsIssued_QNAME, reader, context);
                requiredElements.remove("NetstampsIssued");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setNetstampsIssued(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_ErrorReason_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_ErrorReason_QNAME, reader, context);
                requiredElements.remove("ErrorReason");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setErrorReason((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Mac_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_Mac_QNAME, reader, context);
                requiredElements.remove("Mac");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMac((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_IndiciaData_QNAME))) {
                myns1_ArrayOfIndiciumData__ArrayOfIndiciumData_LiteralSerializer.setNullable( false );
                member = myns1_ArrayOfIndiciumData__ArrayOfIndiciumData_LiteralSerializer.deserialize(ns1_IndiciaData_QNAME, reader, context);
                requiredElements.remove("IndiciaData");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setIndiciaData((member == null)? null : ((com.aasc.erp.carrier.stampsws.proxy.ArrayOfIndiciumData)member).toArray());
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
        com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse instance = (com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse instance = (com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse instance = (com.aasc.erp.carrier.stampsws.proxy.CreateNetStampsIndiciaResponse)obj;
        
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getAuthenticator(), ns1_Authenticator_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getIntegratorTxId(), ns1_IntegratorTxId_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getStampsTxId(), ns1_StampsTxId_QNAME, null, writer, context);
        if (instance.getURL() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getURL(), ns1_URL_QNAME, null, writer, context);
        }
        myns1_PostageBalance__PostageBalance_LiteralSerializer.setNullable( false );
        myns1_PostageBalance__PostageBalance_LiteralSerializer.serialize(instance.getPostageBalance(), ns1_PostageBalance_QNAME, null, writer, context);
        myns1_NetstampsStatus__NetstampsStatus_LiteralSerializer.setNullable( false );
        myns1_NetstampsStatus__NetstampsStatus_LiteralSerializer.serialize(instance.getNetstampsStatus(), ns1_NetstampsStatus_QNAME, null, writer, context);
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getNetstampsIssued()), ns1_NetstampsIssued_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getErrorReason(), ns1_ErrorReason_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getMac(), ns1_Mac_QNAME, null, writer, context);
        if (instance.getIndiciaData() != null) {
            myns1_ArrayOfIndiciumData__ArrayOfIndiciumData_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.stampsws.proxy.ArrayOfIndiciumData instanceGetIndiciaData_arrayWrapper = (instance.getIndiciaData() == null) ? null : new com.aasc.erp.carrier.stampsws.proxy.ArrayOfIndiciumData(instance.getIndiciaData());
            myns1_ArrayOfIndiciumData__ArrayOfIndiciumData_LiteralSerializer.serialize(instanceGetIndiciaData_arrayWrapper, ns1_IndiciaData_QNAME, null, writer, context);
        }
    }
}