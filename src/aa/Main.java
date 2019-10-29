package aa;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class Main {
	
	final static String nameSpace = "http://tempuri.org/";
	
	public static void main(String args[]) {


		String soapEndpointUrl = "http://192.168.3.26:9200/hspmember.asmx?wsdl";
		String protocol = SOAPConstants.SOAP_1_1_PROTOCOL;
		String soapAction = "GetTotaluser";
		HashMap<String, String> params = new HashMap<>();
		
		params.put("userid", "wjddy89");
		
		callSoapWebService(soapEndpointUrl, soapAction, protocol, params);
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage, String soapAction, HashMap<String, String> params) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody soapBody = envelope.getBody();
		
		Iterator<String> keys = params.keySet().iterator();
		
		while (keys.hasNext()) {
			String key = keys.next();
			SOAPElement userIdNode = soapBody.addChildElement(key);
			userIdNode.addTextNode(params.get(key));
		}

		envelope.setAttribute("xmlns:soap", "http://www.w3.org/2003/05/soap-envelope");
		envelope.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		envelope.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");

		envelope.appendChild(soapBody);
	}

	private static void callSoapWebService(String soapEndpointUrl, String soapAction, String protocol, HashMap<String, String> params) {
		try {
			

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, protocol, params), soapEndpointUrl);

			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();

			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
	}

	private static SOAPMessage createSOAPRequest(String soapAction, String protocol, HashMap<String, String> params) throws Exception {
		

		MessageFactory messageFactory = MessageFactory.newInstance(protocol);

		SOAPMessage soapMessage = messageFactory.createMessage();

		createSoapEnvelope(soapMessage, soapAction, params);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", nameSpace + soapAction);

		soapMessage.saveChanges();

		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");

		return soapMessage;
	}
}
