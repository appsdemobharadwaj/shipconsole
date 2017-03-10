// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy.runtime;

import oracle.j2ee.ws.common.encoding.*;
import oracle.j2ee.ws.client.ServiceExceptionImpl;
import oracle.j2ee.ws.common.util.exception.*;
import oracle.j2ee.ws.common.soap.SOAPVersion;
import oracle.j2ee.ws.client.HandlerChainImpl;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.*;
import javax.xml.rpc.handler.HandlerChain;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.namespace.QName;

public class SwsimV37_Impl extends oracle.j2ee.ws.client.BasicService implements com.aasc.erp.carrier.stampsws.proxy.SwsimV37 {
    private static final QName serviceName = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "SwsimV37");
    private static final QName ns1_SwsimV37Soap_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "SwsimV37Soap");
    private static final Class swsimV37Soap_PortClass = com.aasc.erp.carrier.stampsws.proxy.SwsimV37Soap.class;
    private static final QName ns1_SwsimV37Soap12_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "SwsimV37Soap12");
    
    public SwsimV37_Impl() {
        super(serviceName, new QName[] {
                        ns1_SwsimV37Soap_QNAME,
                        ns1_SwsimV37Soap12_QNAME
                    },
            new com.aasc.erp.carrier.stampsws.proxy.runtime.SwsimV37_SerializerRegistry().getRegistry());
        
    }
    
    public java.rmi.Remote getPort(QName portName, Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (portName.equals(ns1_SwsimV37Soap_QNAME) &&
                serviceDefInterface.equals(swsimV37Soap_PortClass)) {
                return getSwsimV37Soap();
            }
            if (portName.equals(ns1_SwsimV37Soap12_QNAME) &&
                serviceDefInterface.equals(swsimV37Soap_PortClass)) {
                return getSwsimV37Soap12();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(portName, serviceDefInterface);
    }
    
    public java.rmi.Remote getPort(Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (serviceDefInterface.equals(swsimV37Soap_PortClass)) {
                return getSwsimV37Soap();
            }
            if (serviceDefInterface.equals(swsimV37Soap_PortClass)) {
                return getSwsimV37Soap12();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(serviceDefInterface);
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.SwsimV37Soap getSwsimV37Soap() {
        String[] roles = new String[] {};
        HandlerChainImpl handlerChain = new HandlerChainImpl(getHandlerRegistry().getHandlerChain(ns1_SwsimV37Soap_QNAME));
        handlerChain.setRoles(roles);
        com.aasc.erp.carrier.stampsws.proxy.runtime.SwsimV37Soap_Stub stub = new com.aasc.erp.carrier.stampsws.proxy.runtime.SwsimV37Soap_Stub(handlerChain);
        try {
            stub._initialize(super.internalTypeRegistry);
        } catch (JAXRPCException e) {
            throw e;
        } catch (Exception e) {
            throw new JAXRPCException(e.getMessage(), e);
        }
        return stub;
    }
    public com.aasc.erp.carrier.stampsws.proxy.SwsimV37Soap getSwsimV37Soap12() {
        String[] roles = new String[] {};
        HandlerChainImpl handlerChain = new HandlerChainImpl(getHandlerRegistry().getHandlerChain(ns1_SwsimV37Soap12_QNAME));
        handlerChain.setRoles(roles);
        com.aasc.erp.carrier.stampsws.proxy.runtime.SwsimV37Soap12_Stub stub = new com.aasc.erp.carrier.stampsws.proxy.runtime.SwsimV37Soap12_Stub(handlerChain);
        try {
            stub._initialize(super.internalTypeRegistry);
        } catch (JAXRPCException e) {
            throw e;
        } catch (Exception e) {
            throw new JAXRPCException(e.getMessage(), e);
        }
        return stub;
    }
}
