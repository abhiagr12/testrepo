/**
 * Created: Dec 24, 2015 3:58:32 PM
 *
 * Copyright (c) 2000 - 2015, Valtech.
 * All rights reserved.
 *
 */
package com.valtech.test.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import javax.mail.BodyPart;
//import javax.mail.MessagingException;
//import javax.mail.search.AndTerm;
//import javax.mail.search.SearchTerm;
//import javax.xml.bind.DatatypeConverter;
//import org.apache.commons.io.IOUtils;
//import com.sun.mail.imap.IMAPBodyPart;

/**
 * @version 2.0
 *
 * @author abhilash.agrawal
 */
public class FetchingEmail {
    public static void main(final String args[]) throws Exception
    {
    	
    	//MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
    	//mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
    	//mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
    	//mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
    	//mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
    	//mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
    	//CommandMap.setDefaultCommandMap(mc);
    	
        // Get system properties
        final Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        
        //props.put("mail.imap.host", "imap.gmail.com");
        //props.put("mail.imap.port", "465");
        
        props.put("mail.imap.host", "outlook.office365.com");
        //props.put("mail.imap.port", "995");
        //props.put("mail.imap.port", "995");
        
        /*props.put("mail.imap.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.imap.socketFactory.fallback", "false");
        props.put("mail.imap.socketFactory.port", "465");*/
        //props.put("mail.imap.auth", "USER/PASS");
        //props.put("mail.debug", "true");
        // Get the default Session object.
        final Session session = Session.getDefaultInstance(props);

        // Get a Store object that implements the specified protocol.
        final Store store = session.getStore("imaps");

        //Connect to the current host using the specified username and password.
        //store.connect("imap.gmail.com", "valtechmailtesttest@gmail.com", "valtechmail");
        //store.connect("outlook.office365.com", "Westcon.Test@valtech.com", "Valtech001");
        //Prod
        //store.connect("outlook.office365.com", "QuoteUpdate@na.westcongrp.com", "P1neappl3");
        
        //QA
        store.connect("outlook.office365.com", "QuoteUpdateQA@na.westcongrp.com", "Spring2016");
        //Create a Folder object corresponding to the given name.
        final Folder folder = store.getFolder("inbox");
        store.getDefaultFolder();
        // Open the Folder.
        //final Folder folder1 = store.getFolder("hello");
        // Open the Folder.
        
        //if(!folder1.exists()){
        //    folder1.create(Folder.HOLDS_MESSAGES);
        //}
        folder.open(Folder.READ_WRITE);
        
        //For Unread/unseen message add these lines and comment above line
        final Flags seen = new Flags(Flags.Flag.SEEN);
        final FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        
        //SearchTerm searchTerm = new SearchTerm() {
        //    private static final long serialVersionUID = 1L;
        //    @Override
        //    public boolean match(Message message) {
        //        try {
        //            return message.getSubject().toLowerCase().contains("quote".toLowerCase());
        //        } catch (MessagingException e) {
        //            e.printStackTrace();
        //       }
        //       return false;
        //    }
        //};
        final Message messages[] = folder.search(unseenFlagTerm);
        //final Message messages[] = folder.search(new AndTerm(searchTerm, unseenFlagTerm));
        //folder1.open(Folder.READ_WRITE);
        //folder1.copyMessages(messages, folder);
        //folder1.close(true);
        //final Message messages[] = folder.getMessages();
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+messages.length);
        
        // Display message.
        for(Message message : messages){
            final Object ob = message.getContent();
            String content=null;
            if(message.getContentType().toLowerCase().contains("delivery-status")){
            	continue;
            }
            System.out.println(getQuoteNumber(message.getSubject()));
            if(ob instanceof Multipart){
                content = getContent((Multipart) ob);
            }else if (message.getContentType().toLowerCase().contains("text/plain")) {
            	content =  message.getContent().toString();
    	    }else if(message.getContentType().toLowerCase().contains("text/html")){
    	    	content =  Jsoup.parse(message.getContent().toString()).body().text();
    	    }else if(ob instanceof String){
                Document doc = Jsoup.parse(ob.toString());
                content = doc.body().text();
            }
            Address address = message.getFrom()[0];
            System.out.println("Sender mail Id : "+getEmailIdFromAddress(address));
            if(content!=null){
            	System.out.println("Comment : "+ getCommentFromMessageBody(content));
            }
            message.setFlag(Flags.Flag.SEEN, false);
        }
        //folder.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
        
        folder.close(true);
        store.close();
    }
    
    public static String getContent(Multipart mimeMultipart)throws MessagingException, IOException {
    	for (int i = 0; i < mimeMultipart.getCount(); i++) {
    	    BodyPart part = mimeMultipart.getBodyPart(i);
    	    Object contentObject = part.getContent();
    	    if (contentObject instanceof Multipart) {
    	    	return getContent((Multipart) part.getContent());
    	    }
    	    if (part.getContentType().toLowerCase().contains("text/plain")) {
    	    	return part.getContent().toString();
    	    }else if(part.getContentType().toLowerCase().contains("text/html")){
    	    	return Jsoup.parse(part.getContent().toString()).body().text();
    	    }
    	}
    	return "";
    }
    
    protected static String getEmailIdFromAddress(final Address address){
    	if(StringUtils.contains(address.toString(), "<")){
    		return address.toString().substring(address.toString().indexOf("<")+1, address.toString().indexOf(">"));
    	}
    	return address.toString();
    }
    private static String getCommentFromMessageBody(final String body)
    {
        String comment = null;
        //remove user signature form email body
        comment = removeSignatureFromEmailBody(body);
        
        //remove mail trail from email body
        comment = removeEmailTrail(comment);
        
        return comment;
    }
    
    protected static Map<String, String> getQuoteNumber(final String subject)
	{
		final String emailSubject = "Your quote from Westcon, Quote #{0}, Version #{1} {2}".split("#")[0];

		final Map<String, String> returnMap = new HashMap<String, String>();
		if (subject.toLowerCase().contains(emailSubject.toLowerCase()))
		{
			final String[] tokens = subject.split("#");
			if (tokens != null && tokens.length > 2)
			{
				returnMap.put("QUOTE_KEY", tokens[1].replaceAll("\\D+", ""));
				returnMap.put("VERSION_KEY", tokens[2].replaceAll("\\D+", ""));
			}
		}

		return returnMap;
	}
    
    private static String removeSignatureFromEmailBody(final String messageBody)
	{
		List<String> signatures = new ArrayList<String>();
		signatures.add("Best Regards");
        signatures.add("thanks");
        signatures.add("regards");
        signatures.add("disclaimer");
		final String lines[] = messageBody.split("\n");
		if(lines.length==1){
			return singleLine(lines, signatures);
		}else{
			return multipleLine(lines, signatures);
		}
	}
	
	private static String singleLine(String[] lines, List<String> signatures){
		String message = lines[0];
		for (final String signature : signatures)
		{
			if (message.trim().toLowerCase().contains(signature.toLowerCase())){
				int index = message.toLowerCase().indexOf(signature.toLowerCase());
				if(index==0){
					String subString = message.substring(signature.length(), message.length());
					if(subString.toLowerCase().contains(signature.toLowerCase())){
						int index2 = subString.toLowerCase().indexOf(signature.toLowerCase());
						message = message.substring(0, index2+signature.length());
					}
				}else{
					message = message.substring(0, index);
				}
			}
		}
		return message;
	}
	private static String multipleLine(String[] lines, List<String> signatures){
		int i = 0;
		boolean flag = false;
		final StringBuilder commentSB = new StringBuilder();
		for (i = lines.length - 1; i > 0; i--)
		{
			for (final String signature : signatures)
			{
				if (lines[i].trim().toLowerCase().startsWith(signature.toLowerCase().trim()))
				{
					flag = true;
					break;
				}
			}
			if (flag)
			{
				break;
			}
		}
		if (!flag)
		{
			i = lines.length - 1;
		}
		for (int j = 0; j < i; j++)
		{
			commentSB.append(lines[j]).append("\n");
		}
		return commentSB.toString();
	}
	
	protected static String removeEmailTrail(final String messageBody)
	{
		String trail = "From:";
		String lines[] = messageBody.split("\n");
		if(lines.length==1){
			return removeEmailTrailSingleLine(lines, trail);
		}else{
			return removeEmailTrailMultipleLine(lines, trail);
		}
	}
	
	private static String removeEmailTrailSingleLine(String[] lines, String trail){
		String message = lines[0];
		
		if(message.contains(trail)){
			int index = message.indexOf(trail);
			message = message.substring(0, index);
		}
		return message;
	}
	
	private static String removeEmailTrailMultipleLine(String[] lines, String trail){
		final StringBuilder commentSB = new StringBuilder();
		for (final String line : lines)
		{
			if (line.trim().startsWith(trail))
			{
				break;
			}
			commentSB.append(line).append("\n");
		}
		return commentSB.toString();
	}
}
