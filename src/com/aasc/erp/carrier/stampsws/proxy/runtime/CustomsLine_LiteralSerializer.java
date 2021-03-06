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

public class CustomsLine_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Description_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Description");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_Quantity_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Quantity");
    private static final QName ns2_double_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DOUBLE;
    private CombinedSerializer myns2__double__double_Double_Serializer;
    private static final QName ns1_Value_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Value");
    private static final QName ns2_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer myns2_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final QName ns1_WeightLb_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "WeightLb");
    private CombinedSerializer myns2__double__java_lang_Double_Double_Serializer;
    private static final QName ns1_WeightOz_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "WeightOz");
    private static final QName ns1_HSTariffNumber_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "HSTariffNumber");
    private static final QName ns1_CountryOfOrigin_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "CountryOfOrigin");
    
    public CustomsLine_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public CustomsLine_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns2__double__double_Double_Serializer = (CombinedSerializer)registry.getSerializer("", double.class, ns2_double_TYPE_QNAME);
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer("", java.math.BigDecimal.class, ns2_decimal_TYPE_QNAME);
        myns2__double__java_lang_Double_Double_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Double.class, ns2_double_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CustomsLine instance = new com.aasc.erp.carrier.stampsws.proxy.CustomsLine();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("Description");
        requiredElements.add("Quantity");
        requiredElements.add("Value");
        for (int memberIndex = 0; memberIndex <7; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Description_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_Description_QNAME, reader, context);
                requiredElements.remove("Description");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setDescription((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Quantity_QNAME))) {
                myns2__double__double_Double_Serializer.setNullable( false );
                member = myns2__double__double_Double_Serializer.deserialize(ns1_Quantity_QNAME, reader, context);
                requiredElements.remove("Quantity");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setQuantity(((Double)member).doubleValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Value_QNAME))) {
                myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
                member = myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_Value_QNAME, reader, context);
                requiredElements.remove("Value");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setValue((java.math.BigDecimal)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_WeightLb_QNAME))) {
                myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
                member = myns2__double__java_lang_Double_Double_Serializer.deserialize(ns1_WeightLb_QNAME, reader, context);
                requiredElements.remove("WeightLb");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setWeightLb((java.lang.Double)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_WeightOz_QNAME))) {
                myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
                member = myns2__double__java_lang_Double_Double_Serializer.deserialize(ns1_WeightOz_QNAME, reader, context);
                requiredElements.remove("WeightOz");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setWeightOz((java.lang.Double)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_HSTariffNumber_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_HSTariffNumber_QNAME, reader, context);
                requiredElements.remove("HSTariffNumber");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setHSTariffNumber((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_CountryOfOrigin_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_CountryOfOrigin_QNAME, reader, context);
                requiredElements.remove("CountryOfOrigin");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCountryOfOrigin((java.lang.String)member);
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
        com.aasc.erp.carrier.stampsws.proxy.CustomsLine instance = (com.aasc.erp.carrier.stampsws.proxy.CustomsLine)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CustomsLine instance = (com.aasc.erp.carrier.stampsws.proxy.CustomsLine)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.CustomsLine instance = (com.aasc.erp.carrier.stampsws.proxy.CustomsLine)obj;
        
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_Description_QNAME, null, writer, context);
        myns2__double__double_Double_Serializer.setNullable( false );
        myns2__double__double_Double_Serializer.serialize(new Double(instance.getQuantity()), ns1_Quantity_QNAME, null, writer, context);
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.setNullable( false );
        myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getValue(), ns1_Value_QNAME, null, writer, context);
        if (instance.getWeightLb() != null) {
            myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
            myns2__double__java_lang_Double_Double_Serializer.serialize(instance.getWeightLb(), ns1_WeightLb_QNAME, null, writer, context);
        }
        if (instance.getWeightOz() != null) {
            myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
            myns2__double__java_lang_Double_Double_Serializer.serialize(instance.getWeightOz(), ns1_WeightOz_QNAME, null, writer, context);
        }
        if (instance.getHSTariffNumber() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getHSTariffNumber(), ns1_HSTariffNumber_QNAME, null, writer, context);
        }
        if (instance.getCountryOfOrigin() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getCountryOfOrigin(), ns1_CountryOfOrigin_QNAME, null, writer, context);
        }
    }
}
