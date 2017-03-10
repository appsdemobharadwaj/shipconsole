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

public class PackDetailBase_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_error_code_QNAME = new QName("http://PSI.Wcf", "error_code");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__int_Int_Serializer;
    private static final QName ns1_error_message_QNAME = new QName("http://PSI.Wcf", "error_message");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_additional_information_QNAME = new QName("http://PSI.Wcf", "additional_information");
    private static final QName ns2_anyType_TYPE_QNAME = SchemaConstants.QNAME_TYPE_URTYPE;
    private CombinedSerializer ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer;
    private static final QName ns1_warehouse_code_QNAME = new QName("http://PSI.Wcf", "warehouse_code");
    private static final QName ns1_order_line_errors_QNAME = new QName("http://PSI.Wcf", "order_line_errors");
    private static final QName ns1_ArrayOfPackOrderLineError_TYPE_QNAME = new QName("http://PSI.Wcf", "ArrayOfPackOrderLineError");
    private CombinedSerializer myns1_ArrayOfPackOrderLineError__ArrayOfPackOrderLineError_LiteralSerializer;
    
    public PackDetailBase_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public PackDetailBase_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Object.class, ns2_anyType_TYPE_QNAME);
        myns1_ArrayOfPackOrderLineError__ArrayOfPackOrderLineError_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfPackOrderLineError.class, ns1_ArrayOfPackOrderLineError_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("error_code");
        for (int memberIndex = 0; memberIndex <5; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_error_code_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_error_code_QNAME, reader, context);
                requiredElements.remove("error_code");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setError_code(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_error_message_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_error_message_QNAME, reader, context);
                requiredElements.remove("error_message");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setError_message((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_additional_information_QNAME))) {
                ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.setNullable( false );
                member = ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.deserialize(ns1_additional_information_QNAME, reader, context);
                requiredElements.remove("additional_information");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setAdditional_information((java.lang.Object)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_warehouse_code_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_warehouse_code_QNAME, reader, context);
                requiredElements.remove("warehouse_code");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setWarehouse_code((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_order_line_errors_QNAME))) {
                myns1_ArrayOfPackOrderLineError__ArrayOfPackOrderLineError_LiteralSerializer.setNullable( false );
                member = myns1_ArrayOfPackOrderLineError__ArrayOfPackOrderLineError_LiteralSerializer.deserialize(ns1_order_line_errors_QNAME, reader, context);
                requiredElements.remove("order_line_errors");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setOrder_line_errors((member == null)? null : ((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfPackOrderLineError)member).toArray());
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
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackDetailBase)obj;
        
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getError_code()), ns1_error_code_QNAME, null, writer, context);
        if (instance.getError_message() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getError_message(), ns1_error_message_QNAME, null, writer, context);
        }
        if (instance.getAdditional_information() != null) {
            ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.setNullable( false );
            ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.serialize(instance.getAdditional_information(), ns1_additional_information_QNAME, null, writer, context);
        }
        if (instance.getWarehouse_code() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getWarehouse_code(), ns1_warehouse_code_QNAME, null, writer, context);
        }
        if (instance.getOrder_line_errors() != null) {
            myns1_ArrayOfPackOrderLineError__ArrayOfPackOrderLineError_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfPackOrderLineError instanceGetOrder_line_errors_arrayWrapper = (instance.getOrder_line_errors() == null) ? null : new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfPackOrderLineError(instance.getOrder_line_errors());
            myns1_ArrayOfPackOrderLineError__ArrayOfPackOrderLineError_LiteralSerializer.serialize(instanceGetOrder_line_errors_arrayWrapper, ns1_order_line_errors_QNAME, null, writer, context);
        }
    }
}