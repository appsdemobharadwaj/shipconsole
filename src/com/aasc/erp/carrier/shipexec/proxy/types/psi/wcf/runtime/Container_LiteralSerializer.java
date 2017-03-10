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

public class Container_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_container_id_QNAME = new QName("http://PSI.Wcf", "container_id");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__int_Int_Serializer;
    private static final QName ns1_container_volume_QNAME = new QName("http://PSI.Wcf", "container_volume");
    private static final QName ns2_double_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DOUBLE;
    private CombinedSerializer myns2__double__java_lang_Double_Double_Serializer;
    private static final QName ns1_dim_weight_QNAME = new QName("http://PSI.Wcf", "dim_weight");
    private static final QName ns1_Weight_TYPE_QNAME = new QName("http://PSI.Wcf", "Weight");
    private CombinedSerializer myns1_Weight__Weight_LiteralSerializer;
    private static final QName ns1_name_QNAME = new QName("http://PSI.Wcf", "name");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_dimensions_QNAME = new QName("http://PSI.Wcf", "dimensions");
    private static final QName ns1_Dimensions_TYPE_QNAME = new QName("http://PSI.Wcf", "Dimensions");
    private CombinedSerializer myns1_Dimensions__Dimensions_LiteralSerializer;
    private static final QName ns1_maximum_weight_QNAME = new QName("http://PSI.Wcf", "maximum_weight");
    private static final QName ns1_weight_QNAME = new QName("http://PSI.Wcf", "weight");
    private static final QName ns1_cost_factor_QNAME = new QName("http://PSI.Wcf", "cost_factor");
    private static final QName ns1_container_type_QNAME = new QName("http://PSI.Wcf", "container_type");
    private static final QName ns1_ContainerType_TYPE_QNAME = new QName("http://PSI.Wcf", "ContainerType");
    private CombinedSerializer myns1_ContainerType__ContainerType_LiteralSerializer;
    private static final QName ns1_additional_information_QNAME = new QName("http://PSI.Wcf", "additional_information");
    private static final QName ns2_anyType_TYPE_QNAME = SchemaConstants.QNAME_TYPE_URTYPE;
    private CombinedSerializer ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer;
    
    public Container_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public Container_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
        myns2__double__java_lang_Double_Double_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Double.class, ns2_double_TYPE_QNAME);
        myns1_Weight__Weight_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Weight.class, ns1_Weight_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_Dimensions__Dimensions_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Dimensions.class, ns1_Dimensions_TYPE_QNAME);
        myns1_ContainerType__ContainerType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ContainerType.class, ns1_ContainerType_TYPE_QNAME);
        ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Object.class, ns2_anyType_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("container_id");
        requiredElements.add("container_type");
        for (int memberIndex = 0; memberIndex <10; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_container_id_QNAME))) {
                myns2__int__int_Int_Serializer.setNullable( false );
                member = myns2__int__int_Int_Serializer.deserialize(ns1_container_id_QNAME, reader, context);
                requiredElements.remove("container_id");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setContainer_id(((Integer)member).intValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_container_volume_QNAME))) {
                myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
                member = myns2__double__java_lang_Double_Double_Serializer.deserialize(ns1_container_volume_QNAME, reader, context);
                requiredElements.remove("container_volume");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setContainer_volume((java.lang.Double)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_dim_weight_QNAME))) {
                myns1_Weight__Weight_LiteralSerializer.setNullable( false );
                member = myns1_Weight__Weight_LiteralSerializer.deserialize(ns1_dim_weight_QNAME, reader, context);
                requiredElements.remove("dim_weight");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setDim_weight((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Weight)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_name_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                requiredElements.remove("name");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setName((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_dimensions_QNAME))) {
                myns1_Dimensions__Dimensions_LiteralSerializer.setNullable( false );
                member = myns1_Dimensions__Dimensions_LiteralSerializer.deserialize(ns1_dimensions_QNAME, reader, context);
                requiredElements.remove("dimensions");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setDimensions((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Dimensions)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_maximum_weight_QNAME))) {
                myns1_Weight__Weight_LiteralSerializer.setNullable( false );
                member = myns1_Weight__Weight_LiteralSerializer.deserialize(ns1_maximum_weight_QNAME, reader, context);
                requiredElements.remove("maximum_weight");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setMaximum_weight((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Weight)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_weight_QNAME))) {
                myns1_Weight__Weight_LiteralSerializer.setNullable( false );
                member = myns1_Weight__Weight_LiteralSerializer.deserialize(ns1_weight_QNAME, reader, context);
                requiredElements.remove("weight");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setWeight((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Weight)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_cost_factor_QNAME))) {
                myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
                member = myns2__double__java_lang_Double_Double_Serializer.deserialize(ns1_cost_factor_QNAME, reader, context);
                requiredElements.remove("cost_factor");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCost_factor((java.lang.Double)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_container_type_QNAME))) {
                myns1_ContainerType__ContainerType_LiteralSerializer.setNullable( false );
                member = myns1_ContainerType__ContainerType_LiteralSerializer.deserialize(ns1_container_type_QNAME, reader, context);
                requiredElements.remove("container_type");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setContainer_type((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ContainerType)member);
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
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Container)obj;
        
        myns2__int__int_Int_Serializer.setNullable( false );
        myns2__int__int_Int_Serializer.serialize(new Integer(instance.getContainer_id()), ns1_container_id_QNAME, null, writer, context);
        if (instance.getContainer_volume() != null) {
            myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
            myns2__double__java_lang_Double_Double_Serializer.serialize(instance.getContainer_volume(), ns1_container_volume_QNAME, null, writer, context);
        }
        if (instance.getDim_weight() != null) {
            myns1_Weight__Weight_LiteralSerializer.setNullable( false );
            myns1_Weight__Weight_LiteralSerializer.serialize(instance.getDim_weight(), ns1_dim_weight_QNAME, null, writer, context);
        }
        if (instance.getName() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        }
        if (instance.getDimensions() != null) {
            myns1_Dimensions__Dimensions_LiteralSerializer.setNullable( false );
            myns1_Dimensions__Dimensions_LiteralSerializer.serialize(instance.getDimensions(), ns1_dimensions_QNAME, null, writer, context);
        }
        if (instance.getMaximum_weight() != null) {
            myns1_Weight__Weight_LiteralSerializer.setNullable( false );
            myns1_Weight__Weight_LiteralSerializer.serialize(instance.getMaximum_weight(), ns1_maximum_weight_QNAME, null, writer, context);
        }
        if (instance.getWeight() != null) {
            myns1_Weight__Weight_LiteralSerializer.setNullable( false );
            myns1_Weight__Weight_LiteralSerializer.serialize(instance.getWeight(), ns1_weight_QNAME, null, writer, context);
        }
        if (instance.getCost_factor() != null) {
            myns2__double__java_lang_Double_Double_Serializer.setNullable( false );
            myns2__double__java_lang_Double_Double_Serializer.serialize(instance.getCost_factor(), ns1_cost_factor_QNAME, null, writer, context);
        }
        myns1_ContainerType__ContainerType_LiteralSerializer.setNullable( false );
        myns1_ContainerType__ContainerType_LiteralSerializer.serialize(instance.getContainer_type(), ns1_container_type_QNAME, null, writer, context);
        if (instance.getAdditional_information() != null) {
            ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.setNullable( false );
            ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.serialize(instance.getAdditional_information(), ns1_additional_information_QNAME, null, writer, context);
        }
    }
}
