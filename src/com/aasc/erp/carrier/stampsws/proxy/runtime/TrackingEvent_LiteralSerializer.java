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

public class TrackingEvent_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_Timestamp_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Timestamp");
    private static final QName ns2_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final QName ns1_Event_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Event");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_TrackingEventType_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "TrackingEventType");
    private static final QName ns1_TrackingEventType_TYPE_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "TrackingEventType");
    private CombinedSerializer myns1_TrackingEventType__TrackingEventType_LiteralSerializer;
    private static final QName ns1_City_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "City");
    private static final QName ns1_State_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "State");
    private static final QName ns1_Zip_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Zip");
    private static final QName ns1_Country_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "Country");
    private static final QName ns1_SignedBy_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "SignedBy");
    private static final QName ns1_AuthorizedAgent_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "AuthorizedAgent");
    private static final QName ns2_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer myns2__boolean__boolean_Boolean_Serializer;
    
    public TrackingEvent_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public TrackingEvent_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer("", java.util.Calendar.class, ns2_dateTime_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        myns1_TrackingEventType__TrackingEventType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.stampsws.proxy.TrackingEventType.class, ns1_TrackingEventType_TYPE_QNAME);
        myns2__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer("", boolean.class, ns2_boolean_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.TrackingEvent instance = new com.aasc.erp.carrier.stampsws.proxy.TrackingEvent();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("Timestamp");
        requiredElements.add("Event");
        requiredElements.add("TrackingEventType");
        requiredElements.add("City");
        requiredElements.add("State");
        requiredElements.add("Zip");
        requiredElements.add("Country");
        requiredElements.add("SignedBy");
        requiredElements.add("AuthorizedAgent");
        for (int memberIndex = 0; memberIndex <9; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Timestamp_QNAME))) {
                myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.setNullable( false );
                member = myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_Timestamp_QNAME, reader, context);
                requiredElements.remove("Timestamp");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setTimestamp((java.util.Calendar)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Event_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_Event_QNAME, reader, context);
                requiredElements.remove("Event");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setEvent((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_TrackingEventType_QNAME))) {
                myns1_TrackingEventType__TrackingEventType_LiteralSerializer.setNullable( false );
                member = myns1_TrackingEventType__TrackingEventType_LiteralSerializer.deserialize(ns1_TrackingEventType_QNAME, reader, context);
                requiredElements.remove("TrackingEventType");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setTrackingEventType((com.aasc.erp.carrier.stampsws.proxy.TrackingEventType)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_City_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_City_QNAME, reader, context);
                requiredElements.remove("City");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCity((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_State_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_State_QNAME, reader, context);
                requiredElements.remove("State");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setState((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Zip_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_Zip_QNAME, reader, context);
                requiredElements.remove("Zip");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setZip((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_Country_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_Country_QNAME, reader, context);
                requiredElements.remove("Country");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setCountry((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_SignedBy_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_SignedBy_QNAME, reader, context);
                requiredElements.remove("SignedBy");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setSignedBy((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_AuthorizedAgent_QNAME))) {
                myns2__boolean__boolean_Boolean_Serializer.setNullable( false );
                member = myns2__boolean__boolean_Boolean_Serializer.deserialize(ns1_AuthorizedAgent_QNAME, reader, context);
                requiredElements.remove("AuthorizedAgent");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setAuthorizedAgent(((Boolean)member).booleanValue());
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
        com.aasc.erp.carrier.stampsws.proxy.TrackingEvent instance = (com.aasc.erp.carrier.stampsws.proxy.TrackingEvent)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.TrackingEvent instance = (com.aasc.erp.carrier.stampsws.proxy.TrackingEvent)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.TrackingEvent instance = (com.aasc.erp.carrier.stampsws.proxy.TrackingEvent)obj;
        
        myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.setNullable( false );
        myns2_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getTimestamp(), ns1_Timestamp_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getEvent(), ns1_Event_QNAME, null, writer, context);
        myns1_TrackingEventType__TrackingEventType_LiteralSerializer.setNullable( false );
        myns1_TrackingEventType__TrackingEventType_LiteralSerializer.serialize(instance.getTrackingEventType(), ns1_TrackingEventType_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getCity(), ns1_City_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getState(), ns1_State_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getZip(), ns1_Zip_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getCountry(), ns1_Country_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getSignedBy(), ns1_SignedBy_QNAME, null, writer, context);
        myns2__boolean__boolean_Boolean_Serializer.setNullable( false );
        myns2__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isAuthorizedAgent()), ns1_AuthorizedAgent_QNAME, null, writer, context);
    }
}
