/**
 * AccountDetailsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.b2baccount.generated;

public class AccountDetailsResponse  implements java.io.Serializable {
    private com.b2baccount.generated.Account accountDetails;

    public AccountDetailsResponse() {
    }

    public AccountDetailsResponse(
           com.b2baccount.generated.Account accountDetails) {
           this.accountDetails = accountDetails;
    }


    /**
     * Gets the accountDetails value for this AccountDetailsResponse.
     * 
     * @return accountDetails
     */
    public com.b2baccount.generated.Account getAccountDetails() {
        return accountDetails;
    }


    /**
     * Sets the accountDetails value for this AccountDetailsResponse.
     * 
     * @param accountDetails
     */
    public void setAccountDetails(com.b2baccount.generated.Account accountDetails) {
        this.accountDetails = accountDetails;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccountDetailsResponse)) return false;
        AccountDetailsResponse other = (AccountDetailsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accountDetails==null && other.getAccountDetails()==null) || 
             (this.accountDetails!=null &&
              this.accountDetails.equals(other.getAccountDetails())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAccountDetails() != null) {
            _hashCode += getAccountDetails().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccountDetailsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com/blog/samples/webservices/accountservice", "AccountDetailsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com/blog/samples/webservices/accountservice", "AccountDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.samples.blog.com", "Account"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
