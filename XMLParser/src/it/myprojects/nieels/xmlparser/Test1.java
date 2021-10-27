package it.myprojects.nieels.xmlparser;

public class Test1 {
	
	public static void main (String [] args) {
		XMLObject objFile;
		
		System.out.println("Creating object from XML file...");
		objFile = XMLParser.extractXMLObject();
		System.out.println("DONE!");
		
		System.out.println("Object to string:");
		System.out.println(objFile.toString());
		
		System.out.println();
		System.out.println("Creating new XML file back from object...");
		XMLParser.writeDocument(objFile);
		System.out.println("DONE!");
	}

}
