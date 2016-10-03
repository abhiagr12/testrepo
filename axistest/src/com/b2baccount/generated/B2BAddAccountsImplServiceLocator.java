/**
 * B2BAddAccountsImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.b2baccount.generated;

public class B2BAddAccountsImplServiceLocator extends org.apache.axis.client.Service implements com.b2baccount.generated.B2BAddAccountsImplService {

    public B2BAddAccountsImplServiceLocator() {
    }


    public B2BAddAccountsImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public B2BAddAccountsImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for B2BAddAccountsImplPort
    private java.lang.String B2BAddAccountsImplPort_address = "http://localhost:8080/soapweb/services/b2bAccount";

    public java.lang.String getB2BAddAccountsImplPortAddress() {
        return B2BAddAccountsImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String B2BAddAccountsImplPortWSDDServiceName = "B2BAddAccountsImplPort";

    public java.lang.String getB2BAddAccountsImplPortWSDDServiceName() {
        return B2BAddAccountsImplPortWSDDServiceName;
    }

    public void setB2BAddAccountsImplPortWSDDServiceName(java.lang.String name) {
        B2BAddAccountsImplPortWSDDServiceName = name;
    }

    public com.b2baccount.generated.B2BAddAccounts getB2BAddAccountsImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(B2BAddAccountsImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getB2BAddAccountsImplPort(endpoint);
    }

    public com.b2baccount.generated.B2BAddAccounts getB2BAddAccountsImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.b2baccount.generated.B2BAddAccountsImplServiceSoapBindingStub _stub = new com.b2baccount.generated.B2BAddAccountsImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getB2BAddAccountsImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setB2BAddAccountsImplPortEndpointAddress(java.lang.String address) {
        B2BAddAccountsImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.b2baccount.generated.B2BAddAccounts.class.isAssignableFrom(serviceEndpointInterface)) {
                com.b2baccount.generated.B2BAddAccountsImplServiceSoapBindingStub _stub = new com.b2baccount.generated.B2BAddAccountsImplServiceSoapBindingStub(new java.net.URL(B2BAddAccountsImplPort_address), this);
                _stub.setPortName(getB2BAddAccountsImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("B2BAddAccountsImplPort".equals(inputPortName)) {
            return getB2BAddAccountsImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.services.soaptest.com/", "B2BAddAccountsImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.services.soaptest.com/", "B2BAddAccountsImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("B2BAddAccountsImplPort".equals(portName)) {
            setB2BAddAccountsImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
