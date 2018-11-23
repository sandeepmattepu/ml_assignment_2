package ml_prgramming_task_2;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TreeToXMLParser 
{
	private boolean isXMLDeclarationIncluded = true;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder documentBuilder;
	private Document document;
	private TransformerFactory transformFactory;
	private Transformer transformer;
	
	public TreeToXMLParser(TreePart rootNode, double entropy) throws Exception
	{
		documentFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentFactory.newDocumentBuilder();
		document = documentBuilder.newDocument();
		transformFactory = TransformerFactory.newInstance();
		transformer = transformFactory.newTransformer();
		         
		// root element is tree
		Element rootElement = document.createElement("tree");
		document.appendChild(rootElement);
		Attr entropyAttribute = document.createAttribute("entropy");
		entropyAttribute.setValue(Double.toString(entropy));
		rootElement.setAttributeNode(entropyAttribute);
		
		// Parsing the tree to XML
		convertTreeToXML(rootNode, rootElement, document);
	}
	
	public String toString()
	{
		return toString(isXMLDeclarationIncluded);
	}
	
	/**
	 * @param withXMLDeclaration When true the outpult will have xml declaration
	 * 								(<?xml version="1.0" encoding="UTF-8" standalone="no"?>)
	 */
	public String toString(boolean withXMLDeclaration)
	{
		String resultString = null;
		String isDeclarationIncluded = withXMLDeclaration ? "yes" : "no";
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, isDeclarationIncluded);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		DOMSource domSource = new DOMSource(document);
		try 
		{
			transformer.transform(domSource, result);
			resultString = writer.toString();
		} 
		catch (TransformerException e) 
		{
			e.printStackTrace();
		}
		return resultString;
	}
	
	public void saveAtLocation(String fileName, boolean withXMLDeclaration) throws Exception
	{
		String isDeclarationIncluded = withXMLDeclaration ? "yes" : "no";
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, isDeclarationIncluded);
		DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
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
		if(treePart.isNode())
		{
			TreeNode node = (TreeNode)treePart;
			String[] allPossibleValues = node.getPossibleAttributeValues();
			
			for(int i = 0; i < allPossibleValues.length; i++)
			{
				Element newElement = doc.createElement("node");
				Attr entropyAttribute = doc.createAttribute("entropy");
				String entropyValue = Double.toString(treePart.getEntropyAtThisPart());
		        entropyAttribute.setValue(entropyValue);
		        Attr featureAttribute = doc.createAttribute("feature");
		        featureAttribute.setValue(treePart.getNameOfPart());
		        Attr valueAttribute = doc.createAttribute("value");
		        valueAttribute.setValue(allPossibleValues[i]);
		        newElement.setAttributeNode(entropyAttribute);
		        newElement.setAttributeNode(featureAttribute);
		        newElement.setAttributeNode(valueAttribute);
		        elementToAppend.appendChild(newElement);
		        
		        TreePart associatedTreePart = node.getTreePartAt(i);
		        convertTreeToXML(associatedTreePart, newElement, doc);
			}
		}
		else
		{
			elementToAppend.appendChild(doc.createTextNode(treePart.getNameOfPart()));
		}
	}
}
