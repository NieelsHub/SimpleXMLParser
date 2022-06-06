package it.myprojects.nieels.xmlparser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a XML file.
 * @author Nicol Stocchetti
 */
public class XMLObject {
	private String XMLDeclarationVersion;
	private String XMLDeclarationEncoding;
	private ArrayList <String> textBeforeRoot;
	private XMLElement rootElement;
	private ArrayList <String> textAfterRoot;
	
	
	/**
	 * This constructor creates a version 1.0 utf-8 encoded XML file with a new line between the opening tag and the root element.
	 * @param _rootElement the root element, XMLElement.
	 */
	public XMLObject(XMLElement _rootElement) {
		this("1.0", "utf-8", new ArrayList<>(Arrays.asList("\n")), _rootElement, new ArrayList<>());
	}
	
	/**
	 * /**
	 * The most complete constructor.
	 * @param _XMLDeclarationVersion the XML version, String.
	 * @param _XMLDeclarationEncoding the XML encoding, String.
	 * @param _textBeforeRoot text added before the root element, ArrayList <String>.
	 * @param _rootElement the root element, XMLElement.
	 * @param _textAfterRoot text added after the root element, ArrayList <String>.
	 */
	public XMLObject(String _XMLDeclarationVersion, String _XMLDeclarationEncoding, ArrayList <String> _textBeforeRoot, XMLElement _rootElement, ArrayList <String> _textAfterRoot) {
		this.XMLDeclarationVersion = _XMLDeclarationVersion;
		this.XMLDeclarationEncoding = _XMLDeclarationEncoding;
		this.textBeforeRoot = _textBeforeRoot;
		this.rootElement = _rootElement;
		this.textAfterRoot = _textAfterRoot;
	}
	
	/**
	 * Finds all the elements enclosed in this XML object which have a specific tag.
	 * @param tag the element tag to search for, String.
	 * @return all the elements named by the chosen tag, ArrayList<XMLElement>.
	 */
	public ArrayList<XMLElement> findElements(String tag) {
		ArrayList<XMLElement> allElements = new ArrayList<XMLElement>();
		
		if (rootElement.getTag().equals(tag)) {
			allElements.add(rootElement);
		}
		
		allElements.addAll(this.rootElement.findAllEnclosedElements(tag));
		
		return allElements;
	}

	/**
	 * Provides the the XML version.
	 * @return the XML version, String
	 */
	public String getXMLDeclarationVersion() {
		return XMLDeclarationVersion;
	}
	
	/**
	 * Provides the the XML encoding.
	 * @return the encoding, String.
	 */
	public String getXMLDeclarationEncoding() {
		return XMLDeclarationEncoding;
	}
	
	/**
	 * Provides the the XML root element.
	 * @return the XML root element, XMLElement.
	 */
	public XMLElement getRootElement() {
		return rootElement;
	}
	
	/**
	 * Returns possible text written before the start of the root element.
	 * @return ArrayList <String>.
	 */
	public ArrayList <String> getTextBeforeRoot() {
		return textBeforeRoot;
	}
	
	/**
	 * Returns possible text written after the end of the root element.
	 * @return ArrayList <String>.
	 */
	public ArrayList <String> getTextAfterRoot() {
		return textAfterRoot;
	}
	
	/**
	 * Returns a String describing the XML file.
	 * @return String.
	 */
	@Override
	public String toString() {
		String output;
		
		output = "<?xml";
		output += " version = \"" + this.XMLDeclarationVersion + "\"";
		output += " encoding = \"" + this.XMLDeclarationEncoding + "\"";
		output += "?>";
		
		if (this.textBeforeRoot != null) {
			for (String text : this.textBeforeRoot) {
				output += text;
			}
		}
		
		if (this.rootElement != null) {
			output += this.rootElement.toString();
		}
		
		if (this.textAfterRoot != null) {
			for (String text : this.textAfterRoot) {
				output += text;
			}
		}
		
		return output;
	}
}
