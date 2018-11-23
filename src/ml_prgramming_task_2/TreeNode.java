package ml_prgramming_task_2;

import java.util.ArrayList;
import java.util.List;

public class TreeNode extends TreePart
{	
	private final String[] possibleValues;
	private final List<TreePart> treePartsAtAssociatedValues = new ArrayList<TreePart>();
	private final List<Double> entropyAtAssociatedValues = new ArrayList<Double>();
	
	public TreeNode(double EntropyAtPart, String NameOfPart, List<String> PossibleValues)
	{
		super(EntropyAtPart, NameOfPart);
		String[] temp = new String[PossibleValues.size()];
		possibleValues = PossibleValues.toArray(temp);
		for(int i = 0; i < PossibleValues.size(); i++)
		{
			treePartsAtAssociatedValues.add(null);
			entropyAtAssociatedValues.add((double) 0);
		}
	}
	
	public TreeNode(double EntropyAtPart, String NameOfPart, String[] PossibleValues)
	{
		super(EntropyAtPart, NameOfPart);
		possibleValues = PossibleValues.clone();
		for(int i = 0; i < PossibleValues.length; i++)
		{
			treePartsAtAssociatedValues.add(null);
			entropyAtAssociatedValues.add((double) 0);
		}
	}

	@Override
	public boolean isNode() 
	{
		return true;
	}
	
	public String[] getPossibleAttributeValues()
	{
		return possibleValues.clone();
	}

	private int indexOfAttributeValue(String attributeValue)
	{
		int resultIndex = -1;
		for(int i = 0; i < possibleValues.length; i++)
		{
			if(possibleValues[i].equals(attributeValue))
			{
				resultIndex = i;
				break;
			}
		}
		return resultIndex;
	}
	
	public void setTreePartAt(String associatedValue, TreePart treePart)
	{
		int containsAtIndex = indexOfAttributeValue(associatedValue);
		if(containsAtIndex != -1)
		{
			treePartsAtAssociatedValues.add(containsAtIndex, treePart);
			treePartsAtAssociatedValues.remove(containsAtIndex+1);
		}
	}
	
	public void setTreePartAt(int indexOfAssociatedValue, TreePart treePart)
	{
		treePartsAtAssociatedValues.add(indexOfAssociatedValue, treePart);
		treePartsAtAssociatedValues.remove(indexOfAssociatedValue+1);
	}
	
	public void setEntropyAt(String associatedValue, double entropy)
	{
		int containsAtIndex = indexOfAttributeValue(associatedValue);
		if(containsAtIndex != -1)
		{
			entropyAtAssociatedValues.add(containsAtIndex, entropy);
			entropyAtAssociatedValues.remove(containsAtIndex+1);
		}
	}
	
	public void setEntropyAt(int indexOfAssociatedValue, double entropy)
	{
		entropyAtAssociatedValues.add(indexOfAssociatedValue, entropy);
		entropyAtAssociatedValues.remove(indexOfAssociatedValue+1);
	}
	
	public TreePart getTreePartAt(int indexOfAssociatedValue)
	{
		return treePartsAtAssociatedValues.get(indexOfAssociatedValue);
	}
	
	public TreePart getTreePartAt(String associatedValue)
	{
		TreePart result = null;
		int containsAtIndex = indexOfAttributeValue(associatedValue);
		if(containsAtIndex != -1)
		{
			result = treePartsAtAssociatedValues.get(containsAtIndex);
		}
		return result;
	}
	
	public double getEntropyValueAt(int indexOfAssociatedValue)
	{
		return entropyAtAssociatedValues.get(indexOfAssociatedValue);
	}
	
	public double getEntropyValueAt(String associatedValue)
	{
		double result = -1;
		int containsAtIndex = indexOfAttributeValue(associatedValue);
		if(containsAtIndex != -1)
		{
			result = entropyAtAssociatedValues.get(containsAtIndex);
		}
		return result;
	}
}
