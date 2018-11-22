package ml_prgramming_task_2.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ml_prgramming_task_2.TreeNode;

public class TreeNodeTest 
{
	private List<String> possibleValues;
	private double entropy;
	private String treePartName;
	private TreeNode node;
	private TreeNode lowNode;
	private TreeNode medNode;
	private TreeNode highNode;
	
	@Before
	public void setUp() throws Exception 
	{
		possibleValues = new ArrayList<String>();
		possibleValues.add("low");
		possibleValues.add("medium");
		possibleValues.add("high");
		entropy = 0.897;
		treePartName = "temperature";
		
		node = new TreeNode(entropy, treePartName, possibleValues);
		lowNode = new TreeNode(entropy, "low", possibleValues);
		medNode = new TreeNode(entropy, "medium", possibleValues);
		highNode = new TreeNode(entropy, "high", possibleValues);
		
		node.setTreePartAt("low", lowNode);
		node.setTreePartAt("medium", medNode);
		node.setTreePartAt(2, highNode);
	}

	@Test
	public void testIsNode() 
	{
		assertEquals(true, node.isNode());
	}
	
	@Test
	public void testGetPossibleAttributeValues() 
	{
		assertEquals(possibleValues.size(), node.getPossibleAttributeValues().length);
		for(int i = 0; i < possibleValues.size(); i++)
		{
			assertTrue(possibleValues.get(i).equals(node.getPossibleAttributeValues()[i]));
		}
	}

	@Test
	public void testGetTreePartAtInt() 
	{
		assertSame(node.getTreePartAt(0), lowNode);
		assertSame(node.getTreePartAt(1), medNode);
		assertSame(node.getTreePartAt(2), highNode);
	}

	@Test
	public void testGetTreePartAtString() 
	{
		assertSame(node.getTreePartAt("low"), lowNode);
		assertSame(node.getTreePartAt("medium"), medNode);
		assertSame(node.getTreePartAt("high"), highNode);
	}

	@Test
	public void testGetEntropyAtThisPart() 
	{
		assertEquals(entropy, node.getEntropyAtThisPart(), 0.0001);
	}

	@Test
	public void testGetNameOfPart() 
	{
		assertTrue(node.getNameOfPart().equals(treePartName));
	}

}
