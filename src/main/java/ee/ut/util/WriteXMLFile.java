package ee.ut.util;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class WriteXMLFile {
 
	public static void write(String purchaseOrderHRefValue, String totalValue, String returnEmailValue, String idValue, Date deadline) {
 
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("invoice");
		doc.appendChild(rootElement);
 
		// total elements
		Element total = doc.createElement("total");
		total.appendChild(doc.createTextNode(totalValue));
		rootElement.appendChild(total);

		// purchaseOrderHRef elements
		Element purchaseOrderHRef = doc.createElement("purchaseOrderHRef");
		purchaseOrderHRef.appendChild(doc.createTextNode(purchaseOrderHRefValue));
		rootElement.appendChild(purchaseOrderHRef);
		
		//Deadline element
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(deadline);
		try {
			XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			Element deadlineEl = doc.createElement("deadline");
			deadlineEl.appendChild(doc.createTextNode(date2.toXMLFormat()));
			rootElement.appendChild(deadlineEl);
		} catch (DatatypeConfigurationException e) {
			System.out.println("Probleem");
		}
 
		// returnEmail elements
		Element returnEmail = doc.createElement("returnEmail");
		returnEmail.appendChild(doc.createTextNode(returnEmailValue));
		rootElement.appendChild(returnEmail);
		
		// id elements
		Element id = doc.createElement("externalId");
		id.appendChild(doc.createTextNode(idValue));
		rootElement.appendChild(id);
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("invoice.xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}