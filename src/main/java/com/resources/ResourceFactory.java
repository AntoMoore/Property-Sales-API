package com.resources;

public class ResourceFactory {
	
	public Resource getResource(String resourceType) {
		//check for null input
		if(resourceType == null) {
			return null;
		}
		
		// create specified object
		if(resourceType.equalsIgnoreCase("AGENT")) {
			return new Agent();
		}
		else if(resourceType.equalsIgnoreCase("PROPERTY")) {
			return new Property();
		}
		else if(resourceType.equalsIgnoreCase("SALE")) {
			return new Sale();
		}
		
		return null;
	}
}
