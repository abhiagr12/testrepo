package com.security;

import java.util.Locale;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CoreAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware{
	private UserDetailsService userDetailsService;
	private MessageSourceAccessor messageSource;
	
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = new MessageSourceAccessor(messageSource);;
	}

	@Override
	public final void afterPropertiesSet() throws Exception {
		if(userDetailsService == null){
			throw new Exception("A UserDetailsService must be set");
		}
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal() != null ? authentication.getName() : "NONE_PROVIDED";
		UserDetails userDetails = null;
		try
        {
            userDetails = retrieveUser(username);
            String registeredPassword = userDetails.getPassword();
            Object credential = authentication.getCredentials();
            if(credential instanceof String){
            	String enteredPassword = (String)credential;
            	if(!registeredPassword.equals(enteredPassword)){
            		throw new BadCredentialsException(getMessageSource().getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"));
            	}
            }
        } catch(UsernameNotFoundException notFound) {
            throw new BadCredentialsException(getMessageSource().getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"), notFound);
        }
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), authentication.getCredentials(), userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
		return result;
	}
	
	protected final UserDetails retrieveUser(String username) throws AuthenticationException
	{
		UserDetails loadedUser;
		try
		{
			loadedUser = userDetailsService.loadUserByUsername(username);
		} catch(Exception repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}
		if(loadedUser == null)
			throw new AuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
		else{
			return loadedUser;
		}
	}
	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	public MessageSourceAccessor getMessageSource() {
		return messageSource;
	}
	
	public CoreAuthenticationProvider()
    {
		messageSource = SpringSecurityMessageSource.getAccessor();
    }
}
