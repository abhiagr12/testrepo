package com.valtech.core.client;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.support.MarshallingUtils;

import com.blog.samples.webservices.accountservice.AccountDetailsRequest;
import com.soaptest.services.CreateAccount;
import com.soaptest.services.CreateAccountResponse;;

public class AccountServiceClient extends WebServiceGatewaySupport {
	
	public void callService(String accountNumber, String accountName){
		CreateAccount serviceRequest = new CreateAccount();
		AccountDetailsRequest acr = new AccountDetailsRequest();
		acr.setAccountNumber(accountNumber);
		serviceRequest.setAccountInfo(acr);
		serviceRequest.setAccountName(accountName);
		CreateAccountResponse res = getWebServiceTemplate().sendAndReceive(new WebServiceMessageCallback(){
			@Override
            public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
				MarshallingUtils.marshal(getWebServiceTemplate().getMarshaller(), serviceRequest, message);
			}
		}, new WebServiceMessageExtractor<CreateAccountResponse>(){
			@Override
			public CreateAccountResponse extractData(WebServiceMessage webservicemessage) throws IOException{
                return (CreateAccountResponse) MarshallingUtils.unmarshal(getWebServiceTemplate().getUnmarshaller(), webservicemessage);
			}
		});
		System.out.println(res.getAccountDetailsResponse().getAccountDetails().getAccountName());
		System.out.println(res.getAccountDetailsResponse().getAccountDetails().getAccountNumber());
		System.out.println(res.getAccountDetailsResponse().getAccountDetails().getAccountBalance());
		System.out.println(res.getAccountDetailsResponse().getAccountDetails().getAccountStatus());
	}
}
