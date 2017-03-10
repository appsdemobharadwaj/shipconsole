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

public class CloseGroupResponse_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_error_code_QNAME = new QName("http://PSI.Wcf", "error_code");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__int_Int_Serializer;
    private static final QName ns1_error_message_QNAME = new QName("http://PSI.Wcf", "error_message");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_group_QNAME = new QName("http://PSI.Wcf", "group");
    private static final QName ns1_Group_TYPE_QNAME = new QName("http://PSI.Wcf", "Group");
    private CombinedSerializer myns1_Group__Group_LiteralSerializer;
    private static final QName ns1_carrier_QNAME = new QName("http://PSI.Wcf", "carrier");
    private static final QName ns1_groupDocuments_QNAME = new QName("http://PSI.Wcf", "groupDocuments");
    private static final QName ns1_ArrayOfSoxDocument_TYPE_QNAME = new QName("http://PSI.Wcf", "ArrayOfSoxDocument");
    private CombinedSerializer myns1_ArrayOfSoxDocument__ArrayOfSoxDocument_LiteralSerializer;
    
    public CloseGroupResponse_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public CloseGroupResponse_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_Group__Group_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Group.class, ns1_Group_TYPE_QNAME);
        myns1_ArrayOfSoxDocument__ArrayOfSoxDocument_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDocument.class, ns1_ArrayOfSoxDocument_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse();
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
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_group_QNAME))) {
                myns1_Group__Group_LiteralSerializer.setNullable( false );
                member = myns1_Group__Group_LiteralSerializer.deserialize(ns1_group_QNAME, reader, context);
                requiredElements.remove("group");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setGroup((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Group)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_carrier_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_carrier_QNAME, reader, context);
                requiredElements.remove("carrier");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCarrier((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_groupDocuments_QNAME))) {
                myns1_ArrayOfSoxDocument__ArrayOfSoxDocument_LiteralSerializer.setNullable( false );
                member = myns1_ArrayOfSoxDocument__ArrayOfSoxDocument_LiteralSerializer.deserialize(ns1_groupDocuments_QNAME, reader, context);
                requiredElements.remove("groupDocuments");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setGroupDocuments((member == null)? null : ((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDocument)member).toArray());
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
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.CloseGroupResponse)obj;
        
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getError_code()), ns1_error_code_QNAME, null, writer, context);
        if (instance.getError_message() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getError_message(), ns1_error_message_QNAME, null, writer, context);
        }
        if (instance.getGroup() != null) {
            myns1_Group__Group_LiteralSerializer.setNullable( false );
            myns1_Group__Group_LiteralSerializer.serialize(instance.getGroup(), ns1_group_QNAME, null, writer, context);
        }
        if (instance.getCarrier() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getCarrier(), ns1_carrier_QNAME, null, writer, context);
        }
        if (instance.getGroupDocuments() != null) {
            myns1_ArrayOfSoxDocument__ArrayOfSoxDocument_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDocument instanceGetGroupDocuments_arrayWrapper = (instance.getGroupDocuments() == null) ? null : new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDocument(instance.getGroupDocuments());
            myns1_ArrayOfSoxDocument__ArrayOfSoxDocument_LiteralSerializer.serialize(instanceGetGroupDocuments_arrayWrapper, ns1_groupDocuments_QNAME, null, writer, context);
        }
    }
}
