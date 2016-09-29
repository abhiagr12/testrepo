package com.soaptest.services.impl;

import com.blog.samples.webservices.Account;
import com.blog.samples.webservices.EnumAccountStatus;
import com.blog.samples.webservices.accountservice.AccountDetailsRequest;
import com.blog.samples.webservices.accountservice.AccountDetailsResponse;
import com.soaptest.services.B2BAddAccounts;

public class B2BAddAccountsImpl implements B2BAddAccounts {
	
	@Override
	public AccountDetailsResponse createCustomerAccounts(AccountDetailsRequest addAccounts, String str) {
		AccountDetailsResponse accountDetailsResponse = new AccountDetailsResponse();
		Account account = new Account();
		account.setAccountBalance(0.0);
		account.setAccountName(str);
		account.setAccountNumber(addAccounts.getAccountNumber());
		account.setAccountStatus(EnumAccountStatus.ACTIVE);
		
		accountDetailsResponse.setAccountDetails(account);
		return accountDetailsResponse;
	}
}
