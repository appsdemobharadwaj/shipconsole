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

public class AddOnV6_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Amount_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Amount");
    private static final QName ns2_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer myns2_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final QName ns1_AddOnType_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "AddOnType");
    private static final QName ns1_AddOnTypeV6_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "AddOnTypeV6");
    private CombinedSerializer myns1_AddOnTypeV6__AddOnTypeV6_LiteralSerializer;
    private static final QName ns1_RequiresAllOf_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "RequiresAllOf");
    private static final QName ns1_ArrayOfArrayOfAddOnTypeV6_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ArrayOfArrayOfAddOnTypeV6");
    private CombinedSerializer myns1_ArrayOfArrayOfAddOnTypeV6__ArrayOfArrayOfAddOnTypeV6_LiteralSerializer;
    private static final QName ns1_ProhibitedWithAnyOf_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ProhibitedWithAnyOf");
    private static final QName ns1_ArrayOfAddOnTypeV6_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "ArrayOfAddOnTypeV6");
    private CombinedSerializer myns1_ArrayOfAddOnTypeV6__ArrayOfAddOnTypeV6_LiteralSerializer;
    private static final QName ns1_MissingData_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "MissingData");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    
    public AddOnV6_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public AddOnV6_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer("", java.math.BigDecimal.class, ns2_decimal_TYPE_QNAME);
        myns1_AddOnTypeV6__AddOnTypeV6_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.AddOnTypeV6.class, ns1_AddOnTypeV6_TYPE_QNAME);
        myns1_ArrayOfArrayOfAddOnTypeV6__ArrayOfArrayOfAddOnTypeV6_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.ArrayOfArrayOfAddOnTypeV6.class, ns1_ArrayOfArrayOfAddOnTypeV6_TYPE_QNAME);
        myns1_ArrayOfAddOnTypeV6__ArrayOfAddOnTypeV6_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.ArrayOfAddOnTypeV6.class, ns1_ArrayOfAddOnTypeV6_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.AddOnV6 instance = new com.aasc.erp.carrier.stampsws.proxy.AddOnV6();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("AddOnType");
        for (int memberIndex = 0; memberIndex <5; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Amount_QNAME))) {
                myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
                member = myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_Amount_QNAME, reader, context);
                requiredElements.remove("Amount");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setAmount((java.math.BigDecimal)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_AddOnType_QNAME))) {
                myns1_AddOnTypeV6__AddOnTypeV6_LiteralSerializer.setNullable( false );
                member = myns1_AddOnTypeV6__AddOnTypeV6_LiteralSerializer.deserialize(ns1_AddOnType_QNAME, reader, context);
                requiredElements.remove("AddOnType");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setAddOnType((com.aasc.erp.carrier.stampsws.proxy.AddOnTypeV6)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_RequiresAllOf_QNAME))) {
                myns1_ArrayOfArrayOfAddOnTypeV6__ArrayOfArrayOfAddOnTypeV6_LiteralSerializer.setNullable( false );
                member = myns1_ArrayOfArrayOfAddOnTypeV6__ArrayOfArrayOfAddOnTypeV6_LiteralSerializer.deserialize(ns1_RequiresAllOf_QNAME, reader, context);
                requiredElements.remove("RequiresAllOf");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setRequiresAllOf((member == null)? null : ((com.aasc.erp.carrier.stampsws.proxy.ArrayOfArrayOfAddOnTypeV6)member).toArray());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_ProhibitedWithAnyOf_QNAME))) {
                myns1_ArrayOfAddOnTypeV6__ArrayOfAddOnTypeV6_LiteralSerializer.setNullable( false );
                member = myns1_ArrayOfAddOnTypeV6__ArrayOfAddOnTypeV6_LiteralSerializer.deserialize(ns1_ProhibitedWithAnyOf_QNAME, reader, context);
                requiredElements.remove("ProhibitedWithAnyOf");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setProhibitedWithAnyOf((member == null)? null : ((com.aasc.erp.carrier.stampsws.proxy.ArrayOfAddOnTypeV6)member).toArray());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_MissingData_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_MissingData_QNAME, reader, context);
                requiredElements.remove("MissingData");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMissingData((java.lang.String)member);
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
        com.aasc.erp.carrier.stampsws.proxy.AddOnV6 instance = (com.aasc.erp.carrier.stampsws.proxy.AddOnV6)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.AddOnV6 instance = (com.aasc.erp.carrier.stampsws.proxy.AddOnV6)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.AddOnV6 instance = (com.aasc.erp.carrier.stampsws.proxy.AddOnV6)obj;
        
        if (instance.getAmount() != null) {
            myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
            myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getAmount(), ns1_Amount_QNAME, null, writer, context);
        }
        myns1_AddOnTypeV6__AddOnTypeV6_LiteralSerializer.setNullable( false );
        myns1_AddOnTypeV6__AddOnTypeV6_LiteralSerializer.serialize(instance.getAddOnType(), ns1_AddOnType_QNAME, null, writer, context);
        if (instance.getRequiresAllOf() != null) {
            myns1_ArrayOfArrayOfAddOnTypeV6__ArrayOfArrayOfAddOnTypeV6_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.stampsws.proxy.ArrayOfArrayOfAddOnTypeV6 instanceGetRequiresAllOf_arrayWrapper = (instance.getRequiresAllOf() == null) ? null : new com.aasc.erp.carrier.stampsws.proxy.ArrayOfArrayOfAddOnTypeV6(instance.getRequiresAllOf());
            myns1_ArrayOfArrayOfAddOnTypeV6__ArrayOfArrayOfAddOnTypeV6_LiteralSerializer.serialize(instanceGetRequiresAllOf_arrayWrapper, ns1_RequiresAllOf_QNAME, null, writer, context);
        }
        if (instance.getProhibitedWithAnyOf() != null) {
            myns1_ArrayOfAddOnTypeV6__ArrayOfAddOnTypeV6_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.stampsws.proxy.ArrayOfAddOnTypeV6 instanceGetProhibitedWithAnyOf_arrayWrapper = (instance.getProhibitedWithAnyOf() == null) ? null : new com.aasc.erp.carrier.stampsws.proxy.ArrayOfAddOnTypeV6(instance.getProhibitedWithAnyOf());
            myns1_ArrayOfAddOnTypeV6__ArrayOfAddOnTypeV6_LiteralSerializer.serialize(instanceGetProhibitedWithAnyOf_arrayWrapper, ns1_ProhibitedWithAnyOf_QNAME, null, writer, context);
        }
        if (instance.getMissingData() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getMissingData(), ns1_MissingData_QNAME, null, writer, context);
        }
    }
}
