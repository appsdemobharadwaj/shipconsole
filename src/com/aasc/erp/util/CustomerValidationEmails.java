/**
 * CustomerValidationEmails is a class send an email to customer if their transaction limit has expired or tolerance has reached.
 * @author 	Jagadish Jain
 * @version 1.0
 * @since
 * 13/08/2015   Jagadish Jain    Wrote the initial complete code required to send email for transaction limit expiry and tolerance has reached. 
 * 
 * 
 **/


package com.aasc.erp.util;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;


public class CustomerValidationEmails {
    Logger logger = Logger.getLogger(SendMail.class);
    public String fromMail = "", mailPwd = "", toMail = "", host = "";
    public String subject = "";


    public CustomerValidationEmails() {
    }

    public synchronized boolean send(String toMailID,  String message) {
        logger.info("Entered Send() in CustomerValidationEmails");

        TripleDES myEncryptor = null;
        ResourceBundle resources = null;
        try {
            resources = ResourceBundle.getBundle("ApplicationResources");
            

            myEncryptor = new TripleDES();
            mailPwd = 
                    myEncryptor.decrypt(resources.getString("SenderMailPwd"));
            //            mailPwd = "omsainath143";
            fromMail = resources.getString("SenderMailId");
            //            fromMail = "vijaykumar.gandra@appsassociates.com";
            logger.info("from maiL:::" + fromMail);
            host = resources.getString("host");
            if(message.equalsIgnoreCase("balance_email_flag"))
                subject = "ShipConsole Balance Exhausted! Please renew.";
            if(message.equalsIgnoreCase("tolerance_email_flag"))
                subject = "Your ShipConsole transaction limit almost reached. Please renew.";
//            if(message.equalsIgnoreCase("editUserDetails"))
//                subject = "ShipConsole User Updated";
//            if(message.equalsIgnoreCase("forgotPassword"))
//                subject = "ShipConsole Password Reset";
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean sent = false;

        //          String toMail =  "mahesh.vattipelly@appsassociates.com"; 
        String toMail = toMailID;
        String alternateMail = "";
    //        if(!"".equalsIgnoreCase(alternateMailID))
    //            alternateMail = alternateMailID;
            
        logger.info("AlternateMail :::: "+alternateMail);    
        logger.info("emailId:: " + toMailID);
        try {
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            //   props.put("mail.smtp.port","587");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.auth", "true");
            logger.info("host    :" + host);
            if (host.contains("gmail")) {
                props.put("mail.smtp.starttls.enable", "true");
            }
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.socketFactory.port", "587");
            props.put("mail.smtp.socketFactory.fallback", false);
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
    //            MimeMessage msg1 = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromMail));
            
            {
                
                InternetAddress[] toAddress = { new InternetAddress(toMail) };
                msg.setRecipients(Message.RecipientType.TO, toAddress);
            }

            msg.setSubject(subject);
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();

            StringBuffer content = new StringBuffer("<!DOCTYPE html>\n");
            try {
                content.append("<html>\n<body><font face=\"serif\"; size=\"3px\";>\n Hello,\n");
                if(message.equalsIgnoreCase("tolerance_email_flag"))                         
                        content.append("\n" + "<p>You have almost reached your transaction limit for ShipConsole Cloud," +
                        "request you to please renew the service to avoid uninterrupted service.</p>");
                if(message.equalsIgnoreCase("balance_email_flag"))                         
                        content.append("\n" + "<p>Your balance has exhausted, you wouldn't be able to do perform shipping," +
                        "request you to please renew the service to restore the service.</p>");        
//                if(message.equalsIgnoreCase("editUserDetails"))                         
//                        content.append("\n" + "<p>User details Updated Successfully</p>");
//                if(message.equalsIgnoreCase("AddNewCustomer"))
//                        content.append("\n" + "<p>Customer Created Successfully</p>");
//                if(message.equalsIgnoreCase("forgotPassword"))
//                        content.append("\n" + "<p>Password Generated Successfully</p>");
//                if(message.equalsIgnoreCase("AddNewCustomer") || message.equalsIgnoreCase("AddNewUser"))
//                        content.append("\n" + "<p><b> UserName: " + toMail +",</b></p>"); 
//                content.append("\n<br/>" + "<p><b> Password: " + generatedPassword + "</p>\n" + "");
//                
//                content.append("\n<br/>" + "<p><b> ApplicationLoginUrl: " + url + "</p>\n" + "");

                content.append("<br/>\n" + "<p>Regards,</p>\n" + 
                               "<p>Apps Team.\n" + "</p>\n" + 
                               "</font></body>\n" + "</html>\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            //                  logger.info(content.toString());
            mbp1.setText(content.toString(), "utf-8", "html");
            // create the second message part
            //  MimeBodyPart mbp2 = new MimeBodyPart();
            // MimeBodyPart mbp3 = new MimeBodyPart();
            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            // mp.addBodyPart(mbp2);
            // mp.addBodyPart(mbp3);
            // add the Multipart to the message
            msg.setContent(mp);
            // set the Date: header
            msg.setSentDate(new Date());
            // send the message
            Transport.send(msg);
            sent = true;
            logger.info("Mail Sent");
        } catch (Exception e) {
            sent = false;
            //          logger.info(e.getMessage());
            e.printStackTrace();
            logger.error(e.toString());
        }
        return sent;
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(fromMail, mailPwd);
        }
    }

    public static void main(String[] args) {
        // logger.info("Helloo"+System.getProperty("catalina.base"));
        
        boolean sent1 = new SendMail().send("sunanda.kondapalli@appsassociates.com","","welcome12#4","","");

        boolean sent = 
            new SendMail().send("sunanda.kondapalli@appsassociates.com", 
                                "eswarimallur@gmail.com", "welcome12#4","","");
        
    }

}
