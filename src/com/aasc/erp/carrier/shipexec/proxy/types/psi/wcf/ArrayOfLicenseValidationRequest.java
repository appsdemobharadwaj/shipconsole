// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf;


public class ArrayOfLicenseValidationRequest implements java.io.Serializable {
    private com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.LicenseValidationRequest[] licenseValidationRequest;
    
    public ArrayOfLicenseValidationRequest() {
    }
    
    public ArrayOfLicenseValidationRequest(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.LicenseValidationRequest[] sourceArray) {
        licenseValidationRequest = sourceArray;
    }
    
    public void fromArray(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.LicenseValidationRequest[] sourceArray) {
        this.licenseValidationRequest = sourceArray;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.LicenseValidationRequest[] toArray() {
        return licenseValidationRequest;
    }
    
    public com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.LicenseValidationRequest[] getLicenseValidationRequest() {
        return licenseValidationRequest;
    }
    
    public void setLicenseValidationRequest(com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.LicenseValidationRequest[] licenseValidationRequest) {
        this.licenseValidationRequest = licenseValidationRequest;
    }
}
