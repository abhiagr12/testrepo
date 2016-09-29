/**
 * Created: Dec 28, 2015 4:42:33 PM
 *
 * Copyright (c) 2000 - 2015, Valtech.
 * All rights reserved.
 *
 */
package com.valtech.test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 2.0
 *
 * @author abhilash.agrawal
 */
public class DateTest {

    public static void main(String[] abc){ 
    	System.out.println("-----"+getStrDate(getUtilDate("20150122", "231135")));
    	long ime = System.currentTimeMillis();
    	System.out.println("start time"+new Date(ime));
    	//for(int i = 10000000;i>0;i--){
    	//	java.sql.Date sqlDate = java.sql.Date.valueOf("2016-02-05");
    	//	java.sql.Time sqlTime = java.sql.Time.valueOf("10:34:54");
    	//}
    	System.out.println("end time"+new Date(System.currentTimeMillis() - (System.currentTimeMillis() - ime)));
    	
    	java.util.Date date = new java.util.Date();
		date.getTime();
		
		java.sql.Date sqlDate = java.sql.Date.valueOf("2016-02-05");
		java.sql.Time sqlTime = java.sql.Time.valueOf("10:34:54");
		
		System.out.println("=============="+sqlDate);
		System.out.println("=============="+sqlTime);
		System.out.println("=============="+sqlTime);
		//java.util.Calendar cal = java.util.Calendar.getInstance();
		long tzoffset = -(Time.valueOf("00:00:00").getTime());
		  
		Date testDate = new java.util.Date(sqlDate.getTime()+sqlTime.getTime()+tzoffset);
		
		//System.out.println(date);
		System.out.println(testDate);
    	
        int count = 2015;
        int start=0;
        
        while(count>start){
            int end =  count<(start+200)?count-start:start+200;
            System.out.println(start+1+","+end);
            start = start+200;
        }
        
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr1="01-02-1985";
        String dateStr2="02-02-1985";
        try {
        Date date1 = formatter.parse(dateStr1);
        Date date2 = formatter.parse(dateStr2);
        System.out.println(date1.after(date2));
        //System.out.println(date);
        }catch (final ParseException e){
            e.printStackTrace();
        }
        
        
        final Date lastModificationDate = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastModificationDate);
		calendar.add(Calendar.HOUR, -2);
		System.out.println(calendar.getTime());
    }
    
    
    public static Map<String, String> getQuoteNumber(final String subject){
        final Map<String, String> returnMap = new HashMap<String, String>();
        final String[] tokens = subject.split("#");
        if (tokens != null && tokens.length > 2)
        {
            returnMap.put("quoteId", tokens[1].replaceAll("\\D+", ""));
            returnMap.put("versionId", tokens[2].replaceAll("\\D+", ""));
        }
        System.out.println(returnMap);
        return returnMap;
    }
    public static String getCommentFromMessageBody(final String body)
    {
        String comment = null;
        //remove user signature form email body
        comment = removeSignatureFromEmailBody(body);
        
        //remove mail trail from email body
        comment = removeEmailTrail(comment);
        
        System.out.println(comment);
        
        return comment;
    }
    private static String removeSignatureFromEmailBody(final String messageBody){
        StringBuilder commentSB = new StringBuilder();
        String lines[] = messageBody.split("\n");
        List<String> signatures = new ArrayList<String>();
        signatures.add("thanks");
        signatures.add("regards");
        int i=0;
        boolean flag = false;
        for(i = lines.length-1;i>0;i--){
            for(String signature:signatures){
                if(lines[i].trim().toLowerCase().startsWith(signature.toLowerCase().trim())){
                    flag=true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        for(int j=0;j<i;j++){
            commentSB.append(lines[j]).append("\n");
        }
        return commentSB.toString();
    }
    
    private static String removeEmailTrail(final String messageBody){
        StringBuilder commentSB = new StringBuilder();
        for(String line:messageBody.split("\n")){
            if(line.trim().startsWith("From:")){
                break;
            }
            commentSB.append(line).append("\n");
        }
        return commentSB.toString();
    }
    
    private static Date getUtilDate(final String strDate, final String strTime)
	{
		if (strDate != null && strTime != null)
		{
			final java.sql.Date sqlDate = java.sql.Date.valueOf(strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-"
					+ strDate.substring(6, 8));
			final Time sqlTime = Time.valueOf(strTime.substring(0, 2) + ":" + strTime.substring(2, 4) + ":"
					+ strTime.substring(4, 6));
			final long tzoffset = -(Time.valueOf("00:00:00").getTime());
			final java.util.Date date2 = new java.util.Date(sqlDate.getTime() + sqlTime.getTime() + tzoffset);
			return date2;
		}
		return null;
	}
    
    private static String getStrDate(Date date){
    	return new java.sql.Time(date.getTime()).toString().replace(":", "");
    }
    
}
