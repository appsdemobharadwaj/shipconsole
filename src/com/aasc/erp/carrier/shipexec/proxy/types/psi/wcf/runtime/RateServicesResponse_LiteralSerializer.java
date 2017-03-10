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

public class RateServicesResponse_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_RateServicesResult_QNAME = new QName("http://PSI.Wcf", "RateServicesResult");
    private static final QName ns1_ArrayOfShipmentResponse_TYPE_QNAME = new QName("http://PSI.Wcf", "ArrayOfShipmentResponse");
    private CombinedSerializer myns1_ArrayOfShipmentResponse__ArrayOfShipmentResponse_LiteralSerializer;
    private static final QName ns1_Params_QNAME = new QName("http://PSI.Wcf", "Params");
    private static final QName ns1_ArrayOfSoxDictionaryItem_TYPE_QNAME = new QName("http://PSI.Wcf", "ArrayOfSoxDictionaryItem");
    private CombinedSerializer myns1_ArrayOfSoxDictionaryItem__ArrayOfSoxDictionaryItem_LiteralSerializer;
    
    public RateServicesResponse_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public RateServicesResponse_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns1_ArrayOfShipmentResponse__ArrayOfShipmentResponse_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfShipmentResponse.class, ns1_ArrayOfShipmentResponse_TYPE_QNAME);
        myns1_ArrayOfSoxDictionaryItem__ArrayOfSoxDictionaryItem_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDictionaryItem.class, ns1_ArrayOfSoxDictionaryItem_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        for (int memberIndex = 0; memberIndex <2; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_RateServicesResult_QNAME))) {
                myns1_ArrayOfShipmentResponse__ArrayOfShipmentResponse_LiteralSerializer.setNullable( false );
                member = myns1_ArrayOfShipmentResponse__ArrayOfShipmentResponse_LiteralSerializer.deserialize(ns1_RateServicesResult_QNAME, reader, context);
                requiredElements.remove("RateServicesResult");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setRateServicesResult((member == null)? null : ((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfShipmentResponse)member).toArray());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Params_QNAME))) {
                myns1_ArrayOfSoxDictionaryItem__ArrayOfSoxDictionaryItem_LiteralSerializer.setNullable( false );
                member = myns1_ArrayOfSoxDictionaryItem__ArrayOfSoxDictionaryItem_LiteralSerializer.deserialize(ns1_Params_QNAME, reader, context);
                requiredElements.remove("Params");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setParams((member == null)? null : ((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDictionaryItem)member).toArray());
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
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.RateServicesResponse)obj;
        
        if (instance.getRateServicesResult() != null) {
            myns1_ArrayOfShipmentResponse__ArrayOfShipmentResponse_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfShipmentResponse instanceGetRateServicesResult_arrayWrapper = (instance.getRateServicesResult() == null) ? null : new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfShipmentResponse(instance.getRateServicesResult());
            myns1_ArrayOfShipmentResponse__ArrayOfShipmentResponse_LiteralSerializer.serialize(instanceGetRateServicesResult_arrayWrapper, ns1_RateServicesResult_QNAME, null, writer, context);
        }
        if (instance.getParams() != null) {
            myns1_ArrayOfSoxDictionaryItem__ArrayOfSoxDictionaryItem_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDictionaryItem instanceGetParams_arrayWrapper = (instance.getParams() == null) ? null : new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfSoxDictionaryItem(instance.getParams());
            myns1_ArrayOfSoxDictionaryItem__ArrayOfSoxDictionaryItem_LiteralSerializer.serialize(instanceGetParams_arrayWrapper, ns1_Params_QNAME, null, writer, context);
        }
    }
}
