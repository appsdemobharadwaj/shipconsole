/*
 * @(#)AascConnectShipConnection.java        06/01/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.carrier;

import com.aasc.erp.util.AascLogger;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.security.cert.X509Certificate;


/**
 * AascConnectShipConnection Class is used to create connection 
 * to any host given the hostName,protocol,userName,password.
 * @version - 2
 * @author 	Vandana Gangisetty
 */
class

AascConnectShipConnection {

    private HttpURLConnection connection = 
        null; // url connection with support for http specific features
    private URL url = null; // url object which holds the url page
    private static Logger logger = 
        AascLogger.getLogger("AascConnectShipConnection"); // Logger object for issuing logging requests
    // AascProfileOptionsInfo aascProfileOptionsInfo;
    private static String protocol = ""; // protocol used for communication
    private static String hostName = 
        ""; // hostName of the system to which the request is sent
    private static String userName = 
        ""; // userName to obtain authorized access
    private static String password = 
        ""; // password to obtain authorised access
    private static String prefix = 
        ""; // ="Progistics/XML_Processor/Server/XMLProcDLL.asp";

    /** constructor to instantiate logger object
     * and to initialize protocol,hostName,prefix,userName,password
     */
    AascConnectShipConnection(String protocol, String hostName, String prefix, 
                              String userName, String password) {
        this.protocol = protocol;
        this.hostName = hostName;
        this.prefix = prefix;
        this.userName = userName;
        this.password = password;
    }

    /**
     * For password authentication if accessing the internet from behind a firewall.
     */
    class
    // Inner class which is used for Password Authentication
    PasswordAuthenticator extends Authenticator {

        /**
         * For password authentication if accessing the internet from behind a firewall.
         * @return PasswordAuthentication
         */
        protected PasswordAuthentication getPasswordAuthentication() {
            // getPasswordAuthentication() called for https connection
            logger.info("authenticating the password");
            return new PasswordAuthentication(userName, 
                                              password.toCharArray());
        }
    }

    /** method which returns connection that encapsulates connection to connectship
     * @return returns HttpURLConnection object which encapsulates connection
     */
    HttpURLConnection createConnection() {
        try {

            // Create new URL and connect
            if (protocol.equalsIgnoreCase("https")) {
                disableSslVerification();
                url = new URL(protocol + "://" + hostName + "/" + prefix);
                connection = (HttpURLConnection)url.openConnection();
                logger.info("created connection to:" + url.toString() + " " + 
                            connection);
            } else {
                url = new URL(protocol + "://" + hostName + "/" + prefix);
                connection = (HttpURLConnection)url.openConnection();
                logger.info("created connection to:" + url.toString() + " " + 
                            connection);
            }

        } // end of try
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            connection = null;
        }
        return connection;
    }
    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] 
            {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
              public javax.net.ssl.X509KeyManager getX509KeyManager()
              {
                return null;
              }
              public void checkClientTrusted(java.security.cert.X509Certificate[] certs, 
                                             String authType)
              {
              }
              public void checkServerTrusted(java.security.cert.X509Certificate[] certs, 
                                             String authType)
              {
              }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (Exception e1) 
            {
              e1.printStackTrace();
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

