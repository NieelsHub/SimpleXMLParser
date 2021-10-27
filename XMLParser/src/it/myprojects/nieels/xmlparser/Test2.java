package it.myprojects.nieels.xmlparser;

public class Test2 {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		XMLObject objFile;
		
		/*
		System.out.println("Creating object from XML file...");
		objFile = XMLParser.extractXMLObject("./xml/PgAr_Map_5.xml");
		System.out.println("DONE!");
		
		/////System.out.println(objFile.toString());
		
		long step1 = System.currentTimeMillis();
		System.out.println("\nOperation time: \t" + (step1-start) + " (" + ((step1-start)/1000.0) + " seconds)");
		
		
		System.out.println("\nCreating new XML file back from object...");
		XMLParser.writeDocument(objFile, "./xml/PgAr_Map_5output.xml");
		System.out.println("DONE!");
		
		
		long step2 = System.currentTimeMillis();
		System.out.println("\nOperation time: \t" + (step2-step1) + " (" + ((step2-step1)/1000.0) + " seconds)");
		*/
		
		String [] filesIn = {"./xml/PgAr_Map_5.xml", "./xml/PgAr_Map_12.xml", "./xml/PgAr_Map_50.xml", "./xml/PgAr_Map_200.xml", "./xml/PgAr_Map_2000.xml", "./xml/PgAr_Map_10000.xml"};
		String [] filesOut = {"./xml/PgAr_Map_5output.xml", "./xml/PgAr_Map_12output.xml", "./xml/PgAr_Map_50output.xml", "./xml/PgAr_Map_200output.xml", "./xml/PgAr_Map_2000output.xml", "./xml/PgAr_Map_10000output.xml"};
		
		for (int i = 0; i < filesIn.length; i++) {
			long step0 = System.currentTimeMillis();
			System.out.println(filesIn[i]);
			System.out.println("Creating object from XML file number " + (i+1) + "...");
			objFile = XMLParser.extractXMLObject(filesIn[i]);
			System.out.println("DONE!");
			
			/////System.out.println(objFile.toString());
			
			long step1 = System.currentTimeMillis();
			System.out.println("\nOperation time: \t" + (step1-step0) + " (" + ((step1-step0)/1000.0) + " seconds)");
			
			
			System.out.println("\nCreating new XML file back from object number " + (i+1) + "...");
			XMLParser.writeDocument(objFile, filesOut[i]);
			System.out.println("DONE!");
			
			
			long step2 = System.currentTimeMillis();
			System.out.println("\nOperation time: \t" + (step2-step1) + " (" + ((step2-step1)/1000.0) + " seconds)");
		}
		
		long end = System.currentTimeMillis();
		System.out.println("\nWhole execution:\n");
	    System.out.println("Start: \t" + start);
	    System.out.println("End: \t" + end);
	    System.out.println("\nTotal time: \t" + (end-start) + " (" + ((end-start)/1000.0) + " seconds)");
		
	}

}
