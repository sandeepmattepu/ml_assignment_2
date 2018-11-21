package ml_prgramming_task_2;

import java.util.ArrayList;
import java.util.List;

public class TrainingExample 
{
	private final String actualClassValue;
	private final List<String> attributeValues;
	
	/**
	 * @param attributeValuesAndClassValue It contains values of attribute but last value of array is class value
	 */
	public TrainingExample(String[] attributeValuesAndClassValue)
	{
		attributeValues = new ArrayList<String>();
		int numberOfAttributes = attributeValuesAndClassValue.length - 1;
		
		for(int i = 0; i < numberOfAttributes; i++)
		{
			attributeValues.add(attributeValuesAndClassValue[i]);
		}
		
		actualClassValue = attributeValuesAndClassValue[numberOfAttributes];
	}
	
	public String[] getAttributeValues()
	{
		String[] result = new String[attributeValues.size()];
		return (String[]) attributeValues.toArray(result);
	}
	
	public String getActualClassValue()
	{
		return actualClassValue;
	}
}
