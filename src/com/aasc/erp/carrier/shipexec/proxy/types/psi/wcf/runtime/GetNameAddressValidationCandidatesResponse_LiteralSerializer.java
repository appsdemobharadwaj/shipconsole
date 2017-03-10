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

public class GetNameAddressValidationCandidatesResponse_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_GetNameAddressValidationCandidatesResult_QNAME = new QName("http://PSI.Wcf", "GetNameAddressValidationCandidatesResult");
    private static final QName ns1_ArrayOfNameAddressValidationCandidate_TYPE_QNAME = new QName("http://PSI.Wcf", "ArrayOfNameAddressValidationCandidate");
    private CombinedSerializer myns1_ArrayOfNameAddressValidationCandidate__ArrayOfNameAddressValidationCandidate_LiteralSerializer;
    
    public GetNameAddressValidationCandidatesResponse_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public GetNameAddressValidationCandidatesResponse_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns1_ArrayOfNameAddressValidationCandidate__ArrayOfNameAddressValidationCandidate_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddressValidationCandidate.class, ns1_ArrayOfNameAddressValidationCandidate_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_GetNameAddressValidationCandidatesResult_QNAME))) {
            myns1_ArrayOfNameAddressValidationCandidate__ArrayOfNameAddressValidationCandidate_LiteralSerializer.setNullable( false );
            member = myns1_ArrayOfNameAddressValidationCandidate__ArrayOfNameAddressValidationCandidate_LiteralSerializer.deserialize(ns1_GetNameAddressValidationCandidatesResult_QNAME, reader, context);
            requiredElements.remove("GetNameAddressValidationCandidatesResult");
            if (member == null) {
                throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
            }
            instance.setGetNameAddressValidationCandidatesResult((member == null)? null : ((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddressValidationCandidate)member).toArray());
            context.setXmlFragmentWrapperName( null );
            reader.nextElementContent();
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
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.GetNameAddressValidationCandidatesResponse)obj;
        
        if (instance.getGetNameAddressValidationCandidatesResult() != null) {
            myns1_ArrayOfNameAddressValidationCandidate__ArrayOfNameAddressValidationCandidate_LiteralSerializer.setNullable( false );
            com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddressValidationCandidate instanceGetGetNameAddressValidationCandidatesResult_arrayWrapper = (instance.getGetNameAddressValidationCandidatesResult() == null) ? null : new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddressValidationCandidate(instance.getGetNameAddressValidationCandidatesResult());
            myns1_ArrayOfNameAddressValidationCandidate__ArrayOfNameAddressValidationCandidate_LiteralSerializer.serialize(instanceGetGetNameAddressValidationCandidatesResult_arrayWrapper, ns1_GetNameAddressValidationCandidatesResult_QNAME, null, writer, context);
        }
    }
}
