package aa;

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


		String soapEndpointUrl = "http://{host}/{fileName}.asmx?wsdl";
		String protocol = SOAPConstants.SOAP_1_1_PROTOCOL;
		String soapAction = "GetUser";
		
		callSoapWebService(soapEndpointUrl, soapAction, protocol);
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage, String soapAction) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody soapBody = envelope.getBody();
		SOAPElement actionNode = soapBody.addChildElement(soapAction);
		SOAPElement userIdNode = actionNode.addChildElement("userid");
		
		actionNode.setAttribute("xmlns", nameSpace);
	
		envelope.setAttribute("xmlns:soap", "http://www.w3.org/2003/05/soap-envelope");
		envelope.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		envelope.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		
		userIdNode.addTextNode("wjddy89");

		envelope.appendChild(soapBody);
	}

	private static void callSoapWebService(String soapEndpointUrl, String soapAction, String protocol) {
		try {
			

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, protocol), soapEndpointUrl);

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

	private static SOAPMessage createSOAPRequest(String soapAction, String protocol) throws Exception {
		

		MessageFactory messageFactory = MessageFactory.newInstance(protocol);

		SOAPMessage soapMessage = messageFactory.createMessage();

		createSoapEnvelope(soapMessage, soapAction);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", nameSpace + soapAction);

		soapMessage.saveChanges();

		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");

		return soapMessage;
	}
}
