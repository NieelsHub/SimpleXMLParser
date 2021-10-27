package it.myprojects.nieels.xmlparser;

import java.util.ArrayList;

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
	 * The constructor.
	 * @param _XMLDeclaration the XML declaration, String.
	 * @param _textBeforeRoot text added before the root element, ArrayList <String>.
	 * @param _rootElement the root element, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
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
	 * @return the XML root element, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
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
		for (String text : this.textBeforeRoot) {
			output += text;
		}
		
		output += this.rootElement.toString();
		
		for (String text : this.textAfterRoot) {
			output += text;
		}
		return output;
	}
}
