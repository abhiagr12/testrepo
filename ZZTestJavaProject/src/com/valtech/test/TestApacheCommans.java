package com.valtech.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestApacheCommans {

	public static void main(String[] args) {
		String html="<html>"+
	"<head>"+
	"</head>"+
	"<body bgcolor='#e4e7e8'>"+
	"<table class='header' width='610' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>"+
     	"<tr>"+
			"<td align='center' valign='top'>"+
			 "<table width='610' border='0' align='center' cellpadding='0' cellspacing='0'>"+
						"<tbody>"+
							 "<tr>"+
                                            "<td align='center' height='140' valign='top' bgcolor='#FFFFFF' style='border-bottom:5px solid #00417a;text-align:center;margin-bottom:10px'>"+
                                                "<br/> hello <br/> "+
                                            "</td>"+
                                       "</tr>"+
						"</tbody>"+
				"</table>"+
			"</td>"+
		"</tr>"+
     "<tr>"+
       "<td align='center' valign='top'>"+

	    "<table  width='610' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#ffffff'>"+
		 "<tr>"+
            "<td align='center' valign='top' bgcolor='#FFFFFF' style='text-align:center;text-decoration:none'><a href='$!{ctx.baseUrl}' style='display:block; margin-top:10px;margin-bottom:10px;'>$!{ctx.cmsSlotContents.TopContent}</a></td>"+
         "</tr>"+
         "<tr>"+
             "<td>"+

				"<table width='610'  align='center' cellpadding='10' cellspacing='0' >"+

								"<tr>"+
									"<td valign='middle'>&nbsp;</td>"+
								"</tr>"+

								"<tr>"+
                                      "<td width='100%' align='left' style='font-family: Arial, Helvetica, sans-serif; font-size: 18px;line-height:16px; color:#00417a; font-weight:bold;'>Your Password has been Changed</td>"+
                                "</tr>"+
                                "<tr height='20px'>"+
                                    "<td valign='middle'>&nbsp;</td>"+
                                "</tr>"+
								"<tr>"+
									"<td align='left' valign='top'>"+
									"<p style=' font-size: 14px;line-height:30px; font-weight:bold;'>"+
									"<font color='#657282' size='2' face='Arial, Helvetica, sans-serif'>"+

									"#if(!${ctx.customerData.title})"+
										"${ctx.messages.getMessage('salutationName',${ctx.customerData.name})}"+
									"#else"+
									"	${ctx.messages.getMessage('salutation',${ctx.customerData.title},${ctx.customerData.name})}"+
									"#end"+
									",</font></p>"+
									"<p style='font-family: Arial, Helvetica, sans-serif; font-size: 14px;line-height:25px; color:#657282; font-weight:normal;'>${ctx.messages.passwordChangeMessage}</p>"+
									"<p style='font-size: 14px;line-height:30px;font-weight:bold;'><font color='#657282' size='2' face='Arial, Helvetica, sans-serif'>${ctx.messages.thankingMessage}</font></p>"+
									"<p style='font-size: 14px;font-weight:bold;line-height:0px;'><font color='#657282' size='2' face='Arial, Helvetica, sans-serif'>${ctx.messages.signature}</font></p>"+
								"</td>"+
								"</tr>"+
								"<tr>"+
									"<td>&nbsp;</td>"+
								"</tr>"+

                                "<tr>"+
                                    "<td align='center' valign='middle'>"+
                                        "<a href='${ctx.baseUrl}' style='display:block; margin-top:10px;margin-bottom:10px;'>${ctx.cmsSlotContents.BottomContent}</a>"+
                                    "</td>"+
                                "</tr>"+
                                "<tr>"+
                                    "<td>&nbsp;</td>"+
                                "</tr>"+
                                "<tr>"+
                                     "<td><span style='font-family: Arial, Helvetica, sans-serif; font-size: 14px; line-height:20px; color:#657282;'>Please Click here to <a href='${ctx.baseUrl}/login?site=${ctx.siteUId}'>login</a><br>"+
                                     "${ctx.messages.getMessage('westconAdminContactInfo',${ctx.westconAdminInfo})}</span>"+
                                     "</td>"+
                                 "</tr>"+

							"</table></td></tr></table></td></tr>"+

		"${ctx.cmsSlotContents.FooterContent}</table></body></html>";
		
		
		//System.out.println(StringEscapeUtils.unescapeHtml(html));
		Document doc = Jsoup.parse(html);
        String content = doc.body().text();
        System.out.println(content);
	}
}