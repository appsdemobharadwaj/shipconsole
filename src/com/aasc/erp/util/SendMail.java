package com.aasc.erp.util;

/*********************************************************************************

History

08-Dec-2014  Suman G      Modified code for sening Email while creating customer.
17-Dec-2014  Eshwari M    Merged Sunanda code for SC Lite
18-Dec-2014  Sunanda K    Replaced SOPs with logger statements
24-Dec-2014  Suman G      Modified code for both Create customer and user.
06-Jan-2015  Suman G      Modified code for send Email to alternative mail id.
06-Feb-2015  Suman G      Modified code for forgot passsword and also for showing proper message in Email and also added code for proper subject to Email.
26-Feb-2015  Suman G      Modified application resource properties file path.
09-Mar-2015  Sunanda k    Modified code to append Url also in the mail
16-Mar-2015  Suman G      Modified code for editUserDetails action type.
02-Dec-2015  Suman G      Removed , after user name.
04-Dec-2015  Suman G      Added code for subject of resend password.
*********************************************************************************/

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


public class SendMail {

    Logger logger = Logger.getLogger(SendMail.class);
    public String fromMail = "", mailPwd = "", toMail = "", host = "";
    public String subject = "";


    public SendMail() {
    }

    public synchronized boolean send(String toMailID, String alternateMailID, 
                                     String generatedPassword, String message, String url) {
        logger.info("Entered Send() in SendEmail");

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
            if(message.equalsIgnoreCase("AddNewCustomer"))
                subject = "Thanks for your interest in ShipConsole Cloud";
            if(message.equalsIgnoreCase("AddNewUser"))
                subject = "Thanks for your interest in ShipConsole Cloud";
            if(message.equalsIgnoreCase("editUserDetails"))
                subject = "ShipConsole User Updated";
            if(message.equalsIgnoreCase("forgotPassword"))
                subject = "ShipConsole Password Reset";
            if(message.equalsIgnoreCase("resendPassword"))
                subject = "ShipConsole Password";
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean sent = false;

        //	    String toMail =  "mahesh.vattipelly@appsassociates.com"; 
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
            if(!"".equalsIgnoreCase(alternateMailID)){
            
                InternetAddress[] toAddress = { new InternetAddress(alternateMailID) };
                msg.setRecipients(Message.RecipientType.TO, toAddress);
            }
            else{
                
                InternetAddress[] toAddress = { new InternetAddress(toMail) };
                msg.setRecipients(Message.RecipientType.TO, toAddress);
            }

            msg.setSubject(subject);
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();

            StringBuffer content = new StringBuffer("<!DOCTYPE html>\n");
            try {
                
                if(message.equalsIgnoreCase("AddNewUser") || message.equalsIgnoreCase("AddNewCustomer")){                         
                        content.append("<html>\n" + 
                        "\n" + 
                        "	<body>\n" + 
                        "		<font face=\"serif\" size=\"3px\">\n" + 
                        "			<br/>\n" + 
                        "			Hello,\n" + 
                        "			<br/>\n" + 
                        "			<br/>\n" + 
                        "			Your account has been created for ShiConsole Cloud, you can now login into your account and perform following steps in one screen\n" + 
                        "			<br/>\n" + 
                        "			<ul>\n" + 
                        "				<li>Import Orders</li>\n" + 
                        "				<li>Validate Addresses</li>\n" + 
                        "				<li>Freight Shop for the best rates and optimal service level</li>\n" + 
                        "				<li>Shipping</li>\n" + 
                        "				<li>Printing Labels </li>\n" + 
                        "				<li>Tracking shipments</li>\n" + 
                        "			</ul>\n" + 
                        "			<br/>\n" + 
                        "			You can also build your own reports as all data is saved in one location.\n" + 
                        "			<br/>\n" + 
                        "			<br/>\n" + 
                        "			Below are your account details:\n" + 
                        "			<br/>\n" + 
                        "			<br/>\n" + 
                        "			UserName: \n" + toMail +
                        "			<br/>\n" + 
                        "			Password&nbsp;: \n" + generatedPassword +
                        "			<br/>\n" + 
                        "			<br/>\n" + 
                        "			Application Login URL:\n" + url +
                        "			<br/>\n" + 
                        "			<br/>\n" + 
                        "			If you have any questions or comments, feel free to contact us. \n" + 
                        "			<br/>\n" + 
                        "			<br/>\n" + 
                        "			Sincerely, \n" + 
                        "			<br/>\n" + 
                        "			ShipConsole Cloud Team\n" + 
                        "			<br/>\n" + 
                        "			<br/>\n" + 
                        "			e-mail: productsupport@shipconsole.com\n" + 
                        "			<br/>\n" + 
                        "			Phone: 866-921-1852\n" + 
                        "			<br/>\n" + 
                        "			Web: <a href=\"http://www.shipconsole.com/\" target=\"_blank\">www.shipconsole.com</a>\n" + 
                        "		</font>\n" + 
                        "	</body>\n" + 
                        "\n" + 
                        "</html>");
                }else{
                    content.append("<html>\n<body><font face=\"serif\"; size=\"3px\";>\n Hello,\n");
                    if(message.equalsIgnoreCase("editUserDetails"))                         
                            content.append("\n" + "<p>User details Updated Successfully</p>");
                    if(message.equalsIgnoreCase("forgotPassword"))
                            content.append("\n" + "<p>Password Generated Successfully</p>");
                    if(message.equalsIgnoreCase("resendPassword"))
                        content.append("\n" + "<p>Your ShipConsole login details:</p>");
                    if(message.equalsIgnoreCase("resendPassword"))
                            content.append("\n" + "<p><b> UserName: " + toMail +"</b></p>"); 
                    content.append("\n<br/>" + "<p><b> Password: " + generatedPassword + "</p>\n" + "");
                    
                    content.append("\n<br/>" + "<p><b> ApplicationLoginUrl: " + url + "</p>\n" + "");
    
                    content.append("<br/>\n" + "<p>Regards,</p>\n" + 
                                   "<p>Apps Team.\n" + "</p>\n" + 
                                   "</font></body>\n" + "</html>\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            //	                logger.info(content.toString());
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
            //	        logger.info(e.getMessage());
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

