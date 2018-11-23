package ml_prgramming_task_2.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ml_prgramming_task_2.TreeLeaf;
import ml_prgramming_task_2.TreeNode;
import ml_prgramming_task_2.TreePart;
import ml_prgramming_task_2.TreeToXMLParser;

public class TreeToXMLParserTest 
{
	String resultXMLWithDeclaration;
	String resultWithoutDeclaration;
	TreePart rootNode;
	TreeToXMLParser treeToXML;
	
	@Before
	public void setUp() throws Exception 
	{
		// XML result of the decision tree. Decision tree is constructed below manually
		resultXMLWithDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><tree entropy=\"0.89\">"
				+ "<node entropy=\"0.75\" feature=\"att2\" value=\"low\"><node entropy=\"0.65\" feature=\"att1\" "
				+ "value=\"kind\">good</node><node entropy=\"0.65\" feature=\"att1\" value=\"unkind\"><node "
				+ "entropy=\"0.55\" feature=\"att0\" value=\"positive\">not good</node><node entropy=\"0.55\" "
				+ "feature=\"att0\" value=\"negative\">good</node></node></node><node entropy=\"0.75\" feature=\"att2\" "
				+ "value=\"med\">not good</node><node entropy=\"0.75\" feature=\"att2\" value=\"high\"><node "
				+ "entropy=\"0.65\" feature=\"att0\" value=\"positive\">good</node><node entropy=\"0.65\" feature=\"att0\" "
				+ "value=\"negative\"><node entropy=\"0.55\" feature=\"att1\" value=\"kind\">good</node><node "
				+ "entropy=\"0.55\" feature=\"att1\" value=\"unkind\">not good</node></node></node></tree>";
		resultWithoutDeclaration = "<tree entropy=\"0.89\"><node entropy=\"0.75\" feature=\"att2\" value=\"low\"><node "
				+ "entropy=\"0.65\" feature=\"att1\" value=\"kind\">good</node><node entropy=\"0.65\" feature=\"att1\" "
				+ "value=\"unkind\"><node entropy=\"0.55\" feature=\"att0\" value=\"positive\">not good</node><node "
				+ "entropy=\"0.55\" feature=\"att0\" value=\"negative\">good</node></node></node><node entropy=\"0.75\" "
				+ "feature=\"att2\" value=\"med\">not good</node><node entropy=\"0.75\" feature=\"att2\" value=\"high\">"
				+ "<node entropy=\"0.65\" feature=\"att0\" value=\"positive\">good</node><node entropy=\"0.65\" "
				+ "feature=\"att0\" value=\"negative\"><node entropy=\"0.55\" feature=\"att1\" value=\"kind\">good</node>"
				+ "<node entropy=\"0.55\" feature=\"att1\" value=\"unkind\">not good</node></node></node></tree>";
		
		// Constructing decision tree manually
		rootNode = new TreeNode(0.75, "att2", new String[]{"low", "med", "high"});
		
		// Assigning Nodes and leaves to rootNode
		TreeNode layer1Attribute1 = new TreeNode(0.65, "att1", new String[] {"kind", "unkind"});
		((TreeNode)rootNode).setTreePartAt(0, layer1Attribute1);
		TreeLeaf leafToRootNode = new TreeLeaf(0, "not good");
		((TreeNode)rootNode).setTreePartAt(1, leafToRootNode);
		TreeNode layer1Attribute0 = new TreeNode(0.65, "att0", new String[] {"positive", "negative"});
		((TreeNode)rootNode).setTreePartAt(2, layer1Attribute0);
		
		// Assigning Nodes and leaves to layer1Attribute1
		TreeLeaf leafTolayer1Attribute1 = new TreeLeaf(0, "good");
		layer1Attribute1.setTreePartAt(0, leafTolayer1Attribute1);
		TreeNode layer2Attribute0 = new TreeNode(0.55, "att0", new String[] {"positive", "negative"});
		layer1Attribute1.setTreePartAt(1, layer2Attribute0);
		
		// Assigning leaves to layer2Attribute0
		TreeLeaf leafToLayer2Attribute0 = new TreeLeaf(0, "not good");
		layer2Attribute0.setTreePartAt(0, leafToLayer2Attribute0);
		TreeLeaf leaf2ToLayer2Attribute0 = new TreeLeaf(1, "good");
		layer2Attribute0.setTreePartAt(1, leaf2ToLayer2Attribute0);
		
		// Assigning Nodes and leaves to layer1Attribute0
		TreeLeaf leafToLayer1Attribute0 = new TreeLeaf(0, "good");
		layer1Attribute0.setTreePartAt(0, leafToLayer1Attribute0);
		TreeNode layer2Attribute1 = new TreeNode(0.55, "att1", new String[] {"kind", "unkind"});
		layer1Attribute0.setTreePartAt(1, layer2Attribute1);
		
		// Assigning leaves to layer2Attribute1
		TreeLeaf leafToLayer2Attribute1 = new TreeLeaf(0, "good");
		layer2Attribute1.setTreePartAt(0, leafToLayer2Attribute1);
		TreeLeaf leaf2ToLayer2Attribute1 = new TreeLeaf(1, "not good");
		layer2Attribute1.setTreePartAt(1, leaf2ToLayer2Attribute1);
		
		treeToXML = new TreeToXMLParser(rootNode, 0.89);
	}

	@Test
	public void testToString() 
	{
		treeToXML.includeXMLDeclaration(true);
		String afterParsing = treeToXML.toString();
		assertEquals(resultXMLWithDeclaration, afterParsing);
		
		treeToXML.includeXMLDeclaration(false);
		afterParsing = treeToXML.toString();
		assertEquals(resultWithoutDeclaration, afterParsing);
	}

	@Test
	public void testToStringBoolean() 
	{
		String afterParsing = treeToXML.toString(true);
		assertEquals(resultXMLWithDeclaration, afterParsing);
		
		afterParsing = treeToXML.toString(false);
		assertEquals(resultWithoutDeclaration, afterParsing);
	}

}
