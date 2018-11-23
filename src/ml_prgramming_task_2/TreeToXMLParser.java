package ml_prgramming_task_2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TreeToXMLParser 
{
	private boolean isXMLDeclarationIncluded = true;
	
	public TreeToXMLParser(TreePart rootNode, double entropy)
	{
		
	}
	
	public String toString()
	{
		return null;
	}
	
	/**
	 * @param withXMLDeclaration When true the outpult will have xml declaration
	 * 								(<?xml version="1.0" encoding="UTF-8" standalone="no"?>)
	 */
	public String toString(boolean withXMLDeclaration)
	{
		return null;
	}
	
	public void saveAtLocation(String fileName, boolean withXMLDeclaration) throws Exception
	{
		
	}
	
	public void saveAtLocation(String fileName) throws Exception
	{
		saveAtLocation(fileName, isXMLDeclarationIncluded);
	}
	
	public void includeXMLDeclaration(boolean included)
	{
		isXMLDeclarationIncluded = included;
	}
	
	private void convertTreeToXML(TreePart treePart, Element elementToAppend, Document doc)
	{
		
	}
}
