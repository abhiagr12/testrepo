import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.b2baccount.generated.AccountDetailsRequest;
import com.b2baccount.generated.AccountDetailsResponse;
import com.b2baccount.generated.B2BAddAccountsImplServiceLocator;

/**
 * To generate classes we have to run below command form CMD we can use 
 * <b>WSDL2Java</b> for that. below is the example<br>
 * <br>java org.apache.axis.wsdl.WSDL2Java 
 * <br>&nbsp;&nbsp;&nbsp;-o src 
 * <br>&nbsp;&nbsp;&nbsp;-p org.kamal.wssample.ws.generated 
 * <br>&nbsp;&nbsp;&nbsp;-s 
 * <br>&nbsp;&nbsp;&nbsp;b2bAccount.wsdl<br>
 * <br> java -cp "%CLASSPATH%;lib\*" org.apache.axis.wsdl.WSDL2Java -o genscr -p com.b2baccount.generated -s b2bAccount.wsdl
 * */

public class MainClass {
	public static void main(String[] args) throws RemoteException, ServiceException {
		AccountDetailsResponse res = new B2BAddAccountsImplServiceLocator().getB2BAddAccountsImplPort().createAccount(new AccountDetailsRequest(), "hello1");
		System.out.println(res.getAccountDetails().getAccountBalance());
		System.out.println(res.getAccountDetails().getAccountName());
		System.out.println(res.getAccountDetails().getAccountNumber());
		System.out.println(res.getAccountDetails().getAccountStatus());
	}
}