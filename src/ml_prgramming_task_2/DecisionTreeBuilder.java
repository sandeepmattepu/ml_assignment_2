package ml_prgramming_task_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DecisionTreeBuilder 
{
	private final TrainingExample[] trainingExamples;
	private final LinkedHashMap<String, String[]> attributeAndItsValues;
	private final Map<String, Integer> columnNameAndColumnIndex;
	private final String[] allPossibleClassValues;
	
	public DecisionTreeBuilder(TrainingExample[] TrainingExamples, LinkedHashMap<String, String[]> AttributeAndValues,
					String[] AllClassValues)
	{
		trainingExamples = TrainingExamples;
		attributeAndItsValues = AttributeAndValues;
		allPossibleClassValues = AllClassValues;
		columnNameAndColumnIndex = new LinkedHashMap<String, Integer>();
		
		// Assign column index for each column name
		int i = 0;
		for(String key : attributeAndItsValues.keySet())
		{
			columnNameAndColumnIndex.put(key, i);
			i++;
		}
	}
	
	public double calculateEntropy(TrainingExample[] trainingExamples)
	{
		double result = 0;
		
		// List contains class count in the order of AllClassValues's values
		List<Integer> eachClassCount = new ArrayList<Integer>();
		for(int i = 0; i < allPossibleClassValues.length; i++)
		{
			int count = 0;
			for(int j = 0; j < trainingExamples.length; j++)
			{
				String actualClassValue = trainingExamples[j].getActualClassValue();
				boolean similar = allPossibleClassValues[i].equals(actualClassValue);
				if(similar)
				{
					count += 1;
				}
			}
			eachClassCount.add(count);
		}
		
		//Calculating actual entropy
		for(int i = 0; i < eachClassCount.size(); i++)
		{
			double numberOfExamplesWithClass = eachClassCount.get(i);
			double totalNumberOfExamples = trainingExamples.length;
			
			double portionOfClasses = numberOfExamplesWithClass/totalNumberOfExamples;
			if(numberOfExamplesWithClass != 0)
			{
				double logOfPortion = Math.log(portionOfClasses)/Math.log(2);
				result -= (portionOfClasses * logOfPortion);
			}
		}
		return result;
	}
	
	
	public double calculateInformationGain(TrainingExample[] trainingExamples, int attributeColumnIndex)
	{
		String nameOfColumn = columnNameWithColumnIndex(attributeColumnIndex);
		return calculateInformationGain(trainingExamples, nameOfColumn);
	}
	
	public double calculateInformationGain(TrainingExample[] trainingExamples, String attributeColumnName)
	{
		double result = 0;
		double entropyOfGivenSet = calculateEntropy(trainingExamples);
		result = entropyOfGivenSet;
		String[] allPossibleAttributeValues = attributeAndItsValues.get(attributeColumnName);
		int indexOfAttributeColumn = columnNameAndColumnIndex.get(attributeColumnName);
		
		// Finding entropy at each attribute value
		for(int i = 0; i < allPossibleAttributeValues.length; i++)
		{
			TrainingExample[] filteredExamples = filterTrainingExample(trainingExamples, indexOfAttributeColumn, 
													allPossibleAttributeValues[i]);
			double entropyAtThisValue = calculateEntropy(filteredExamples);
			double portionOfExamples = (double)filteredExamples.length/(double)trainingExamples.length;
			result -= (portionOfExamples * entropyAtThisValue);
		}
		return result;
	}
	
	private String columnNameWithColumnIndex(int indexOfColumn)
	{
		String result = null;
		for(String key : columnNameAndColumnIndex.keySet())
		{
			int index = columnNameAndColumnIndex.get(key);
			if(index == indexOfColumn)
			{
				result = key;
				break;
			}
		}
		return result;
	}
	
	/**
	 * @param filterValues It contains column index as keys and its respective attribute values to be filtered
	 */
	public TrainingExample[] filterTrainingExample(TrainingExample[] examples, Map<Integer, String> filterValues)
	{
		List<TrainingExample> result = new ArrayList<TrainingExample>();
		for(int i = 0; i < examples.length; i++)
		{
			boolean allCorrectValues = true;
			for(Integer key : filterValues.keySet())
			{
				String attributeValueToHave = filterValues.get(key);
				String actualAttributeValueExists = examples[i].getAttributeValues()[key];
				boolean areSimilar = attributeValueToHave.equals(actualAttributeValueExists);
				if(!areSimilar)
				{
					allCorrectValues = false;
					break;
				}
			}
			
			if(allCorrectValues)
			{
				result.add(examples[i]);
			}
		}
		TrainingExample[] temp = new TrainingExample[result.size()];
		return result.toArray(temp);
	}
	
	public TrainingExample[] filterTrainingExample(TrainingExample[] examples, int attributeColumnIndex, String value)
	{
		Map<Integer, String> filter = new HashMap<Integer, String>();
		filter.put(attributeColumnIndex, value);
		return filterTrainingExample(examples, filter);
	}
	
	public TreePart buildDecisionTree()
	{
		TreePart result = null;
		return result;
	}
}
