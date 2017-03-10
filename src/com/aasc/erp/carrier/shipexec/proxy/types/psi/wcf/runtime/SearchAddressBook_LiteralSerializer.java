// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.runtime;

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

public class SearchAddressBook_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_category_id_QNAME = new QName("http://PSI.Wcf", "category_id");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__int_Int_Serializer;
    private static final QName ns1_search_1_value_QNAME = new QName("http://PSI.Wcf", "search_1_value");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_search_1_value_field_QNAME = new QName("http://PSI.Wcf", "search_1_value_field");
    private static final QName ns1_search_1_sort_by_QNAME = new QName("http://PSI.Wcf", "search_1_sort_by");
    private static final QName ns1_search_2_value_QNAME = new QName("http://PSI.Wcf", "search_2_value");
    private static final QName ns1_search_2_value_field_QNAME = new QName("http://PSI.Wcf", "search_2_value_field");
    private static final QName ns1_search_2_sort_by_QNAME = new QName("http://PSI.Wcf", "search_2_sort_by");
    
    public SearchAddressBook_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public SearchAddressBook_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("category_id");
        for (int memberIndex = 0; memberIndex <7; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_category_id_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_category_id_QNAME, reader, context);
                requiredElements.remove("category_id");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCategory_id(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_search_1_value_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_search_1_value_QNAME, reader, context);
                requiredElements.remove("search_1_value");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSearch_1_value((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_search_1_value_field_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_search_1_value_field_QNAME, reader, context);
                requiredElements.remove("search_1_value_field");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSearch_1_value_field((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_search_1_sort_by_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_search_1_sort_by_QNAME, reader, context);
                requiredElements.remove("search_1_sort_by");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSearch_1_sort_by((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_search_2_value_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_search_2_value_QNAME, reader, context);
                requiredElements.remove("search_2_value");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSearch_2_value((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_search_2_value_field_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_search_2_value_field_QNAME, reader, context);
                requiredElements.remove("search_2_value_field");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSearch_2_value_field((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_search_2_sort_by_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_search_2_sort_by_QNAME, reader, context);
                requiredElements.remove("search_2_sort_by");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSearch_2_sort_by((java.lang.String)member);
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
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SearchAddressBook)obj;
        
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getCategory_id()), ns1_category_id_QNAME, null, writer, context);
        if (instance.getSearch_1_value() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getSearch_1_value(), ns1_search_1_value_QNAME, null, writer, context);
        }
        if (instance.getSearch_1_value_field() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getSearch_1_value_field(), ns1_search_1_value_field_QNAME, null, writer, context);
        }
        if (instance.getSearch_1_sort_by() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getSearch_1_sort_by(), ns1_search_1_sort_by_QNAME, null, writer, context);
        }
        if (instance.getSearch_2_value() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getSearch_2_value(), ns1_search_2_value_QNAME, null, writer, context);
        }
        if (instance.getSearch_2_value_field() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getSearch_2_value_field(), ns1_search_2_value_field_QNAME, null, writer, context);
        }
        if (instance.getSearch_2_sort_by() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getSearch_2_sort_by(), ns1_search_2_sort_by_QNAME, null, writer, context);
        }
    }
}
