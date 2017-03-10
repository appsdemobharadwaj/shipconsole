// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class AuthenticateUserResponse implements java.io.Serializable {
    protected java.lang.String authenticator;
    protected java.util.Calendar lastLoginTime;
    protected boolean clearCredential;
    protected java.lang.String loginBannerText;
    protected boolean passwordExpired;
    
    public AuthenticateUserResponse() {
    }
    
    public java.lang.String getAuthenticator() {
        return authenticator;
    }
    
    public void setAuthenticator(java.lang.String authenticator) {
        this.authenticator = authenticator;
    }
    
    public java.util.Calendar getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(java.util.Calendar lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public boolean isClearCredential() {
        return clearCredential;
    }
    
    public void setClearCredential(boolean clearCredential) {
        this.clearCredential = clearCredential;
    }
    
    public java.lang.String getLoginBannerText() {
        return loginBannerText;
    }
    
    public void setLoginBannerText(java.lang.String loginBannerText) {
        this.loginBannerText = loginBannerText;
    }
    
    public boolean isPasswordExpired() {
        return passwordExpired;
    }
    
    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }
}