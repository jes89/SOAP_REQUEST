package aa;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

import org.xml.sax.SAXException;

public class Main {
	
	final static String nameSpace = "http://tempuri.org/";
	
	public static void main(String args[]) throws SOAPException, SAXException, IOException, ParserConfigurationException {

		WebServiceRequest webServiceRequest = new WebServiceRequest();
		String protocol = SOAPConstants.SOAP_1_1_PROTOCOL;
//		String protocol = null;
		String soapEndpointUrl = "http://192.168.3.26:9200/hspmember.asmx?wsdl";
//		String action = "GetTotaluser"; //DONE
		String action = "SetTotaluser_Update"; //TODO
//		String action = "GetMemberManageYearOfAge";	//DONE
//		String action = "SetTotaluser_Pwd_Update";	
//		HashMap<String, String> params = getTotaluser();
		HashMap<String, String> params = getTotaluser_UpdateParam();
//		HashMap<String, String> params = updatePwd();
		
		webServiceRequest.requestSoapWebService(soapEndpointUrl, action, protocol, params);
	
	}
	
	private static HashMap<String, String> getTotaluser(){
		HashMap<String, String> params = new HashMap<>();
		params.put("userid", "wjddy89");
		return params;
	}
	
	private static HashMap<String, String> updatePwd(){
		HashMap<String, String> params = new HashMap<>();
		params.put("userid", "wjddy89");
		params.put("pwd", "1111");
		return params;
	}
	
	
	private static HashMap<String, String> getTotaluser_UpdateParam(){
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("userId", "wjddy89");
		params.put("pwd", "");
		params.put("userNm", "웹서비스 테스트");
		params.put("birthday", "19890721");
		params.put("sex", "");
		params.put("email", "hiter00@nate.com");
		params.put("HP1", "010");
		params.put("HP2", "3326");
		params.put("HP3", "0722");
		params.put("post", "");
		params.put("addrSido", "");
		params.put("addrGugun", "");
		params.put("addrDong", "");
		params.put("addrEtc", "");
		params.put("roadAddr1", "");
		params.put("roadAddr2", "");
		params.put("joinSite", "LifeParticleTv");
		params.put("joinRoute", "");
		params.put("NationCode", "");

		return params;
	}


}
