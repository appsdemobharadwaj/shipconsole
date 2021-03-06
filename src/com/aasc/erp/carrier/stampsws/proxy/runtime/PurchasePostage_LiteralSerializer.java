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

public class PurchasePostage_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Authenticator_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Authenticator");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_Credentials_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Credentials");
    private static final QName ns1_Credentials_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Credentials");
    private CombinedSerializer myns1_Credentials__Credentials_LiteralSerializer;
    private static final QName ns1_PurchaseAmount_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "PurchaseAmount");
    private static final QName ns2_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer myns2_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final QName ns1_ControlTotal_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ControlTotal");
    private static final QName ns1_MI_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MI");
    private static final QName ns1_MachineInfo_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MachineInfo");
    private CombinedSerializer myns1_MachineInfo__MachineInfo_LiteralSerializer;
    private static final QName ns1_IntegratorTxID_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "IntegratorTxID");
    
    public PurchasePostage_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public PurchasePostage_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_Credentials__Credentials_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.Credentials.class, ns1_Credentials_TYPE_QNAME);
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer("", java.math.BigDecimal.class, ns2_decimal_TYPE_QNAME);
        myns1_MachineInfo__MachineInfo_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.MachineInfo.class, ns1_MachineInfo_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.PurchasePostage instance = new com.aasc.erp.carrier.stampsws.proxy.PurchasePostage();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("PurchaseAmount");
        requiredElements.add("ControlTotal");
        for (int memberIndex = 0; memberIndex <6; memberIndex++) {
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_PurchaseAmount_QNAME))) {
                myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
                member = myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_PurchaseAmount_QNAME, reader, context);
                requiredElements.remove("PurchaseAmount");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setPurchaseAmount((java.math.BigDecimal)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_ControlTotal_QNAME))) {
                myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
                member = myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_ControlTotal_QNAME, reader, context);
                requiredElements.remove("ControlTotal");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setControlTotal((java.math.BigDecimal)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MI_QNAME))) {
                myns1_MachineInfo__MachineInfo_LiteralSerializer.setNullable( false );
                member = myns1_MachineInfo__MachineInfo_LiteralSerializer.deserialize(ns1_MI_QNAME, reader, context);
                requiredElements.remove("MI");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMI((com.aasc.erp.carrier.stampsws.proxy.MachineInfo)member);
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
        com.aasc.erp.carrier.stampsws.proxy.PurchasePostage instance = (com.aasc.erp.carrier.stampsws.proxy.PurchasePostage)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.PurchasePostage instance = (com.aasc.erp.carrier.stampsws.proxy.PurchasePostage)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.PurchasePostage instance = (com.aasc.erp.carrier.stampsws.proxy.PurchasePostage)obj;
        
        if (instance.getAuthenticator() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getAuthenticator(), ns1_Authenticator_QNAME, null, writer, context);
        }
        if (instance.getCredentials() != null) {
            myns1_Credentials__Credentials_LiteralSerializer.setNullable( false );
            myns1_Credentials__Credentials_LiteralSerializer.serialize(instance.getCredentials(), ns1_Credentials_QNAME, null, writer, context);
        }
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getPurchaseAmount(), ns1_PurchaseAmount_QNAME, null, writer, context);
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getControlTotal(), ns1_ControlTotal_QNAME, null, writer, context);
        if (instance.getMI() != null) {
            myns1_MachineInfo__MachineInfo_LiteralSerializer.setNullable( false );
            myns1_MachineInfo__MachineInfo_LiteralSerializer.serialize(instance.getMI(), ns1_MI_QNAME, null, writer, context);
        }
        if (instance.getIntegratorTxID() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getIntegratorTxID(), ns1_IntegratorTxID_QNAME, null, writer, context);
        }
    }
}
