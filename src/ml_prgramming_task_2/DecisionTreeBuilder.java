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
	
	private double calculateEntropy(TrainingExample[] trainingExamples)
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
				double logOfPortion = Math.log(portionOfClasses)/Math.log(allPossibleClassValues.length);
				result -= (portionOfClasses * logOfPortion);
			}
		}
		return result;
	}
	
	private double calculateInformationGain(TrainingExample[] trainingExamples, String attributeColumnName)
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
			if(filteredExamples.length != 0)
			{
				double entropyAtThisValue = calculateEntropy(filteredExamples);
				double portionOfExamples = (double)filteredExamples.length/(double)trainingExamples.length;
				result -= (portionOfExamples * entropyAtThisValue);
			}
		}
		return result;
	}
	
	/**
	 * @param filterValues It contains column index as keys and its respective attribute values to be filtered
	 */
	private TrainingExample[] filterTrainingExample(TrainingExample[] examples, Map<Integer, String> filterValues)
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
	
	private TrainingExample[] filterTrainingExample(TrainingExample[] examples, int attributeColumnIndex, String value)
	{
		Map<Integer, String> filter = new HashMap<Integer, String>();
		filter.put(attributeColumnIndex, value);
		return filterTrainingExample(examples, filter);
	}
	
	private String findBestAttribute(List<String> fromAttributes, TrainingExample[] trainingExamples)
	{
		String result = null;
		List<Double> informationGainOfAttributes = new ArrayList<Double>();
		
		// Calculate information gain for each attribute
		for(int i = 0; i < fromAttributes.size(); i++)
		{
			double informationGain = calculateInformationGain(trainingExamples, fromAttributes.get(i));
			informationGainOfAttributes.add(informationGain);
		}
		
		// Compare and find attribute with highest information gain
		int indexOfBestAttribute = -1;
		double highestInformationGain = 0;
		for(int i = 0; i < informationGainOfAttributes.size(); i++)
		{
			if(indexOfBestAttribute == -1 || highestInformationGain < informationGainOfAttributes.get(i))
			{
				indexOfBestAttribute = i;
				highestInformationGain = informationGainOfAttributes.get(i);
			}
		}
		
		result = fromAttributes.get(indexOfBestAttribute);
		return result;
	}
	
	/**
	 * 
	 * @return Root tree part(node or leaf) of the tree
	 */
	public TreePart buildDecisionTree()
	{
		TreePart result = null;
		List<String> attributes = new ArrayList<String>();
		attributes.addAll(attributeAndItsValues.keySet());
		result = performID3Algorithm(trainingExamples, attributes);
		return result;
	}
	
	private TreePart performID3Algorithm(TrainingExample[] trainingExamples, List<String> attributesToChoose)
	{
		TreePart result = null;
		double entropyOfExamples = calculateEntropy(trainingExamples);
		
		if(entropyOfExamples == 0)		// Build leaf when entropy is zero
		{
			String classValue = trainingExamples[0].getActualClassValue();
			TreeLeaf leaf = new TreeLeaf(0, classValue);
			return leaf;
		}
		else			// Build node when entropy is not zero
		{
			String bestAttributeName = findBestAttribute(attributesToChoose, trainingExamples);
			int columnIndexOfBestAttribute = columnNameAndColumnIndex.get(bestAttributeName);
			String[] allValuesOfBestAttribute = attributeAndItsValues.get(bestAttributeName);
			
			// Filter training examples based on best attribute values
			List<TrainingExample[]> filteredExamples = new ArrayList<TrainingExample[]>();
			for(int i = 0; i < allValuesOfBestAttribute.length; i ++)
			{
				String attributeValue = allValuesOfBestAttribute[i];
				TrainingExample[] examplesForThisValue = filterTrainingExample(trainingExamples, 
															columnIndexOfBestAttribute, attributeValue);
				filteredExamples.add(examplesForThisValue);
			}
			
			TreeNode node = new TreeNode(entropyOfExamples, bestAttributeName, allValuesOfBestAttribute);
			// Assign further nodes or leaves to this node through ID3
			for(int i = 0; i < filteredExamples.size(); i++)
			{
				if(filteredExamples.size() == 0)	 // When no training examples are there assign most common class value
				{
					String mostCommonClassValue = findMostCommonClassValue(trainingExamples);
					TreeLeaf leaf = new TreeLeaf(0, mostCommonClassValue);
					node.setTreePartAt(allValuesOfBestAttribute[i], leaf);
					node.setEntropyAt(allValuesOfBestAttribute[i], 0);
				}
				else
				{
					double entropyAtFilteredExamples = calculateEntropy(filteredExamples.get(i));
					node.setEntropyAt(allValuesOfBestAttribute[i], entropyAtFilteredExamples);
					// Prepare new attribute list except selected best attribute
					List<String> newAttributes = new ArrayList<String>();
					for(int j = 0; j < attributesToChoose.size(); j++)
					{
						if(!attributesToChoose.get(j).equals(bestAttributeName))
						{
							newAttributes.add(attributesToChoose.get(j));
						}
					}
					TreePart newChild = performID3Algorithm(filteredExamples.get(i), newAttributes);
					node.setTreePartAt(allValuesOfBestAttribute[i], newChild);
				}
			}
			result = node;
		}
		return result;
	}
	
	private String findMostCommonClassValue(TrainingExample[] trainingExamples)
	{
		String result = null;
		
		// Counting each class value
		List<Integer> countOfEachClassValue = new ArrayList<Integer>();
		for(int i = 0; i < allPossibleClassValues.length; i++)
		{
			String classValue = allPossibleClassValues[i];
			int count = 0;
			for(int j = 0; j < trainingExamples.length; j++)
			{
				String actualClassValue = trainingExamples[i].getActualClassValue();
				boolean isSimilar = classValue.equals(actualClassValue);
				if(isSimilar)
				{
					count += 1;
				}
			}
			countOfEachClassValue.add(count);
		}
		
		// Finding most common class in the count
		int mostCount = 0;
		int indexOfMostCount = -1;
		for(int i = 0; i < countOfEachClassValue.size(); i++)
		{
			if(indexOfMostCount == -1 || mostCount < countOfEachClassValue.get(i))
			{
				mostCount = countOfEachClassValue.get(i);
				indexOfMostCount = i;
			}
		}
		result = allPossibleClassValues[indexOfMostCount];
		return result;
	}
}
