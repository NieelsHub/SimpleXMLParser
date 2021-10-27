package it.myprojects.nieels.xmlparser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * Manages an XML file's reading and writing, contains methods for parsing information
 * from an XML file to a it.myprojects.nieels.xmlparser.XMLObject and vice versa.
 * @author Nicol Stocchetti.
 *
 */
public class XMLParser {
	private static XMLInputFactory inputFactory = createInputFactory();
	private static XMLOutputFactory outputFactory = createOutputFactory();
	
	private static XMLStreamReader reader = null;
	private static XMLStreamWriter writer = null;

	/**
	 * Creates and returns an XMLInputFactory type object, used to initialize the class variable.
	 * @return the Input Factory, XMLInputFactory.
	 */
	private static XMLInputFactory createInputFactory() {
		XMLInputFactory inFactory = null;
		try {
			inFactory = XMLInputFactory.newInstance();
		} catch (Exception e) {
			System.err.println("Error initializing the Input Factory: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
		return inFactory;
	}
	
	/**
	 * Creates and returns an XMLOutputFactory type object, used to initialize the class variable.
	 * @return the Output Factory, XMLOutputFactory.
	 */
	private static XMLOutputFactory createOutputFactory() {
		XMLOutputFactory outFactory = null;
		try {
			outFactory = XMLOutputFactory.newInstance();
		} catch (Exception e) {
			System.err.println("Error initializing the Output Factory: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
		return outFactory;
	}
	
	/**
	 * Initializes the stream reader providing the XML file to read, terminates the program if access to the file is not possible.
	 * @param filePath the path of the file to read, String.
	 */
	private static void setReader(String filePath) {
		try {
			XMLParser.reader = XMLParser.inputFactory.createXMLStreamReader(filePath, new FileInputStream(filePath));
		} catch (Exception e) {
			System.err.println("Error initializing the reader: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			System.exit(0); // since the file is not available it's pointless for the program to keep going
		}
	}
	
	/**
	 * Initializes the stream writer and creates the XML document to write on, terminates the program if creation of the file is not possible.
	 * @param filePath the path of the file to create, String.
	 */
	private static void setWriter(String filePath) {
		try {
			XMLParser.writer = XMLParser.outputFactory.createXMLStreamWriter(new FileOutputStream(filePath), "utf-8");
			//XMLParser.writer.writeStartDocument("utf-8", "1.0");
		} catch (Exception e) {
			System.err.println("Error initializing the writer: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			System.exit(0); // since there's no file it's pointless for the program to keep going
		}
	}

	/**
	 * Cretes an XMLObject object by parsing the contents of the provided XML file, returns null in case something goes wrong.
	 * @param filePath the path of the XML file to parse, String.
	 * @return an object containing the data parsed from the provided XML file (or null in case of error), XMLObject.
	 */
	public static XMLObject extractXMLObject(String filePath) {
		XMLObject obj = null;
		String XMLDeclarationVersion = "";
		String XMLDeclarationEncoding = "";
		ArrayList <String> textBeforeRoot = new ArrayList <String>();
		XMLElement rootElement = null;
		ArrayList <String> textAfterRoot = new ArrayList <String>();
		
		boolean rootExtracted = false;
		
		try {
			setReader(filePath);
			while (reader.hasNext()) { //while there are still events to read...
				switch (reader.getEventType()) { //check the event type
					case XMLStreamConstants.START_DOCUMENT: //start of document -> extract declaration
						XMLDeclarationVersion = reader.getVersion();
						XMLDeclarationEncoding = reader.getCharacterEncodingScheme();
						textBeforeRoot.add("\n"); //Dunno why it doesn't format here as CHARACTERS...
						break;
					
					case XMLStreamConstants.START_ELEMENT: //start of an element -> it's the root element, extract it
						rootElement = extractXMLElement();
						rootExtracted = true;
						break;
					
					case XMLStreamConstants.COMMENT: //comments don't need to be parsed
						break;
						
					case XMLStreamConstants.CHARACTERS: //text inside of an element -> extract it
						if (rootExtracted) {
							textAfterRoot.add(reader.getText());
						} else {
							textBeforeRoot.add(reader.getText());
						}
						break;
					
					default:
						break;
				}
				reader.next();
			}
			obj = new XMLObject(XMLDeclarationVersion, XMLDeclarationEncoding, textBeforeRoot, rootElement, textAfterRoot);
		} catch (XMLStreamException e) {
			System.err.println("Exception: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			obj = null;
		}
		return obj;
	}
	
	/**
	 * Creates an XMLObject object parsing the contents of the file "./xml/input.xml", returns null in case something goes wrong.
	 * @return an object containing the data parsed from the default XML file input (or null in case of error), XMLObject.
	 */
	public static XMLObject extractXMLObject() {
		return extractXMLObject("./xml/input.xml");
	}
	
	/**
	 * Creates an XMLElement object parsing the contents of a single XML element, starting from the opening tag to the
	 * corresponding tag closure; returns null in case of error.
	 * @return an object containing data parsed from an XML element (or null in case of error), XMLElement.
	 */
	private static XMLElement extractXMLElement() {
		XMLElement element = null;
		
		try {
			//the reader is on the opening tag of the element -> creates the element by parsing the tag's name and its attributes
			element = new XMLElement(reader.getLocalName());
			for (int i = 0; i < reader.getAttributeCount(); i++) {
				element.addAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
			} 
			reader.next();//next event
			while (reader.hasNext()) { //while there are events to read...
				switch (reader.getEventType()) { //check the event type
					case XMLStreamConstants.START_ELEMENT: // starting of a child element -> call this method again and add the results to this element's contents (recursion)
						element.addChildElement(extractXMLElement());
						break;
					
					case XMLStreamConstants.END_ELEMENT: // end of this element -> return it and exit
						return element;
					
					case XMLStreamConstants.COMMENT: //comments don't need to be parsed
						break;
					
					case XMLStreamConstants.CHARACTERS: //text inside an element -> extract and add it to this element's contents
						element.addText(reader.getText());
						break;
						
					default:
						break;
				}
				reader.next();
			}
		} catch (XMLStreamException e) {
			System.err.println("Exception: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			element = null;
		}
		return element;
	}
	
	/**
	 * Starting from an XMLElement object, write on a file the XML coding of the represented element.
	 * @param element the object to translate into XML, XMLElement.
	 */
	private static void writeElement(XMLElement element) {
		try {
			if(element.getContent() == null) {
				writer.writeEmptyElement(element.getTag()); //tag opening
				
				if (element.getAttributes() != null) { //attributes writing
					for (String key : element.getOrderedAttributes()) {
						writer.writeAttribute(key, element.getAttributes().get(key));
					}
				}
			} else {
				writer.writeStartElement(element.getTag()); //tag opening
				
				if (element.getAttributes() != null) { //attributes writing
					for (String key : element.getOrderedAttributes()) {
						writer.writeAttribute(key, element.getAttributes().get(key));
					}
				}
				
				//contents writing
				for (Object obj : element.getContent()) {
					if (obj instanceof String) {
						writer.writeCharacters((String)obj);
					}
					if (obj instanceof XMLElement) {
						writeElement((XMLElement)obj);
					}
				}
				
				writer.writeEndElement(); //tag closing
			}
			
		} catch (Exception e) {
			System.err.println("Error writing down the XML element: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}
	
	/**
	 * Starting from a XMLObject object, creates an XML file in the specified directory.
	 * @param object the starting object, XMLObject.
	 * @param filePath the path of the XML file to create, String.
	 */
	public static void writeDocument(XMLObject object, String filePath) {
		try {
			setWriter(filePath);
			
			writer.writeStartDocument(object.getXMLDeclarationEncoding(), object.getXMLDeclarationVersion());
			
			if (object.getTextBeforeRoot() != null) {
				for (String text : object.getTextBeforeRoot()) {
					writer.writeCharacters(text);
				}
			}
			
			if(object.getRootElement() != null) {
				writeElement(object.getRootElement());
			}
			
			if (object.getTextAfterRoot() != null) {
				for (String text : object.getTextBeforeRoot()) {
					writer.writeCharacters(text);
				}
			}
			
			writer.writeEndDocument(); // writes the end of the document
			writer.flush(); // empties the buffer and writes down everything left
			writer.close(); // closes the document and frees the reserved resources
			
		} catch (Exception e) {
			System.err.println("Error in the XML file writing: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}
	
	/**
	 * Starting from a XMLObject object, creates the XML file "./xml/output.xml".
	 * @param object the starting object, XMLObject.
	 */
	public static void writeDocument(XMLObject object) {
		writeDocument(object, "./xml/output.xml");
	}
	
}

