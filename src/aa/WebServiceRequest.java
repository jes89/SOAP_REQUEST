package aa;


import java.util.HashMap;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


public class WebServiceRequest {
	
	final static String nameSpace = "http://tempuri.org/";
	final static String webServiceNameSpace = "hspMember";
	
	
	public void requestSoapWebService(String soapEndpointUrl, String soapAction, String protocol, HashMap<String, String> params) {
		try {

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, protocol, params), soapEndpointUrl);

			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();

			soapConnection.close();
		} catch (Exception e) {
			System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
	}
	
	private SOAPMessage createSOAPRequest(String soapAction, String protocol, HashMap<String, String> params) throws Exception {
		

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
	

	private void createSoapEnvelope(SOAPMessage soapMessage, String soapAction, HashMap<String, String> params) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody soapBody = envelope.getBody();
		envelope.addNamespaceDeclaration(webServiceNameSpace, nameSpace);

		SOAPElement soapActionNode = soapBody.addChildElement(soapAction, webServiceNameSpace);

		Iterator<String> keys = params.keySet().iterator();

		while (keys.hasNext()) {

			String key = keys.next();
			String val = params.get(key);

			SOAPElement userIdNode = soapActionNode.addChildElement(key, webServiceNameSpace);

			userIdNode.addTextNode(val);
		}

		envelope.appendChild(soapBody);
	}
}
