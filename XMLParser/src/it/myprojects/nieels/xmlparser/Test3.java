package it.myprojects.nieels.xmlparser;

import java.io.File;
import java.util.ArrayList;

public class Test3 {

	public static void main (String [] args) {
		System.out.println("Creating an XMLObjet from this code...");
		
		XMLObject obj;
		XMLElement rootElement = new XMLElement("Root");
		XMLElement firstElement = new XMLElement("Trunk");
		
		rootElement.addText("\n\t");
		rootElement.addChildElement(firstElement);
		firstElement.addAttribute("Attribute_1", "1");
		firstElement.addAttribute("Attribute_", "2");
		firstElement.addAttribute("Attribute_X", "X");
		rootElement.addText("\n");
		
		ArrayList<String> newLine = new ArrayList<String>();
		newLine.add("\n");
		
		obj = new XMLObject(rootElement);
		
		System.out.println("DONE!");
		
		System.out.println("Object to string:");
		System.out.println(obj.toString());
		
		System.out.println();
		
		//This part is needed to create the directory in case it doesn't exist yet
		File outputFile = new File("./xml/test3/test3output.xml");
		if (!outputFile.isFile()) {
			System.out.println("Creating new XML file from object...");
			try {
				outputFile.getParentFile().mkdirs(); 
				outputFile.createNewFile();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		}
		
		System.out.println("Writing the XML file from object...");
		
		XMLParser.writeDocument(obj, "./xml/test3/test3output.xml");
		System.out.println("DONE!");
	}
}
