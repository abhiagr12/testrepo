/**
 * Created: 04-May-2015 10:17:33 pm
 *
 * Copyright (c) 2000 - 2015, Valtech.
 * All rights reserved.
 *
 */
package com.soaptest.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.blog.samples.webservices.accountservice.AccountDetailsRequest;
import com.blog.samples.webservices.accountservice.AccountDetailsResponse;

@WebService
public interface B2BAddAccounts
{
	@WebMethod(operationName="createAccount")
	@WebResult(name="accountDetailsResponse")
	public AccountDetailsResponse createCustomerAccounts(@WebParam(name="accountInfo") AccountDetailsRequest addAccounts, @WebParam(name="accountName") String str);
}
