package it.myprojects.nieels.xmlparser;

import java.io.File;
import java.util.ArrayList;

public class Test4 {

	public static void main (String [] args) {
		XMLObject objFile;
		
		System.out.println("Creating object from XML file...");
		objFile = XMLParser.extractXMLObject("./xml/test4input.xml");
		System.out.println("DONE!");
		
		System.out.println("Object to string:");
		System.out.println(objFile.toString());
		
		ArrayList<XMLElement> allElements = objFile.findElements("TROVALO");
		
		System.out.println("FOUND ELEMENTS:");
		
		for(XMLElement el : allElements) {
			System.out.println(el);
		}
		
	}

}
