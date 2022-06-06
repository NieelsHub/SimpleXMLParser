package it.myprojects.nieels.xmlparser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a single XML element as a java object.
 * @author Nicol Stocchetti
 */
public class XMLElement {
	private String tag;
	private HashMap <String, String> attributes;
	private ArrayList <String> orderedAttributes; //saves the attributes' keys in a specific order (since hash maps are not ordered). Could have used a LinkedHashMap, but this method is more efficient and faster in computation
	private ArrayList <Object> content; //the contents of this XML element, an list of Objects which can be either Strings or
										//other XMLElements. If it's = null it means the XMLElement represents an empty tag.
	
	/**
	 * The most complete constructor.
	 * @param _tag the name of this element's tag, String.
	 * @param _attributes this tag's attributes, HashMap <String, String>.
	 * @param _orderedAttributes the _attributes keys in an ordered list, ArrayList <String>.
	 * @param _content this element's contents, ArrayList <Object>.
	 */
	public XMLElement (String _tag, HashMap <String, String> _attributes, ArrayList <String> _orderedAttributes, ArrayList <Object> _content){
		this.tag = _tag;
		this.attributes = _attributes;
		this.orderedAttributes = _orderedAttributes;
		this.content = _content; 
	}
	
	/**
	 * A constructor which creates an empty and attributeless element.
	 * @param _tag the name of this element's tag, String.
	 */
	public XMLElement (String _tag){
		this(_tag, null, null, null);
	}
	
	/**
	 * Checks whether this object represents an empty element.
	 * @return true if it's an empty element, otherwise false.
	 */
	public boolean isEmpty() {
		return this.content == null;
	}
	
	/**
	 * Adds an attribute to this element's tag.
	 * @param name the attribute's name, String.
	 * @param value the attribute's value, String.
	 */
	public void addAttribute(String name, String value) {
		if (this.attributes == null) {
			this.attributes = new HashMap <String, String>();
			this.orderedAttributes = new ArrayList <String>();
		}
		this.attributes.put(name, value);
		this.orderedAttributes.add(name);
	}
	
	/**
	 * Adds a string of text to this element's content.
	 * @param text the string of text, String.
	 */
	public void addText(String text) {
		if (this.content == null) {
			this.content = new ArrayList <Object>();
		}
		this.content.add(text);
	}
	
	/**
	 * Adds a child element to the content of this element.
	 * @param childElement the child element, XMLElement.
	 */
	public void addChildElement(XMLElement childElement) {
		if (this.content == null) {
			this.content = new ArrayList <Object>();
		}
		this.content.add(childElement);
	}
	
	/**
	 * Returns the child elements of this XML element.
	 * @return the child elements, ArrayList<XMLElement>.
	 */
	public ArrayList<XMLElement> getChildElements() {
		ArrayList<XMLElement> childElements = new ArrayList<XMLElement>();
		
		if (this.content == null) {
			return childElements;
		}
		
		for (Object obj : this.content) {
			if (obj instanceof XMLElement) {
				childElements.add((XMLElement)obj);
			}
		}
		
		return childElements;
	}
	
	/**
	 * Finds all the child elements enclosed in this XML element which have a specific tag.
	 * @param tag the element tag to search for, String.
	 * @return all the elements named by the chosen tag, ArrayList<XMLElement>.
	 */
	public ArrayList<XMLElement> findAllEnclosedElements(String tag) {
		ArrayList<XMLElement> foundTagElements = new ArrayList<XMLElement>();
		ArrayList<XMLElement> childElements;
		
		childElements = this.getChildElements();
		for (XMLElement child : childElements) {
			if (child.getTag().equals(tag)) {
				foundTagElements.add(child);
			}
			foundTagElements.addAll(child.findAllEnclosedElements(tag));
		}
		
		return foundTagElements;
	}
	
	/**
	 * Returns a String describing the XML element.
	 * @return String.
	 */
	@Override
	public String toString() {
		String output;
		
		output = "<" + this.tag;
		
		if (this.attributes != null) {
			for (String key : this.orderedAttributes) {
				output += " " + key + " = \"" + this.attributes.get(key) + "\"";
			}
		}
		
		if (this.isEmpty()) {
			output += "/>";
			return output;
		}
			
		output +=">";
		
		if (this.content != null) {
			for (Object element : this.content) {
				output += element.toString();
			}
		}
		
		output += "</" + this.tag + ">";
		return output;
	}
	
	/**
	 * Returns the element's tag.
	 * @return the tag, String.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Returns the element attributes (unordered).
	 * @return the attributes, HashMap<String, String>.
	 */
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	
	/**
	 * Returns the attributes' names in the same order they're placed inside the xml tag.
	 * @return the ordered attributes' names, ArrayList<String>.
	 */
	public ArrayList<String> getOrderedAttributes() {
		return orderedAttributes;
	}
	
	/**
	 * Returns this element's contents.
	 * @return the contents of this element, ArrayList<Object>.
	 */
	public ArrayList<Object> getContent() {
		return content;
	}

}
