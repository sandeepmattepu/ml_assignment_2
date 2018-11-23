package ml_prgramming_task_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVFileReader 
{
	private final List<TrainingExample> trainingExamples;
	private int numberOfAttributes = -1;
	private final List<ArrayList<String>> listOfAttributesAndPossibleValues;
	private final List<String> possibleClassValues;
	
	public CSVFileReader(String fileLocation)
	{
		trainingExamples = new ArrayList<TrainingExample>();
		listOfAttributesAndPossibleValues = new ArrayList<ArrayList<String>>();
		possibleClassValues = new ArrayList<String>();
		BufferedReader reader = null;
		String splitBy = ",";
		
		try
		{
			reader = new BufferedReader(new FileReader(fileLocation));
			String line = "";
            while ((line = reader.readLine()) != null)
            {
                // use comma as separator
                String[] example = line.split(splitBy);
                if(numberOfAttributes == -1)		// For initializing only once
                {
                	numberOfAttributes = example.length - 1;
                	
                	for(int i = 1; i <= numberOfAttributes; i++)
                	{
                		listOfAttributesAndPossibleValues.add(new ArrayList<String>());
                	}
                }
                addAttributeValues(example);
                
                // Add class value if it is not present in possibleClassValues
                boolean doesPresent = possibleClassValues.contains(example[numberOfAttributes]);
                if(!doesPresent)
                {
                	possibleClassValues.add(example[numberOfAttributes]);
                }
                
                TrainingExample trainingExample = new TrainingExample(example);
                trainingExamples.add(trainingExample);
            }
		}
		catch(Exception e)
		{
			numberOfAttributes = 0;
			e.printStackTrace();
		}
	}
	
	/**
	 * This function takes a line(including class value) and checks whether attribute value present in line is also present
	 *  in "listOfAttributesAndPossibleValues" variable. If it is not present it will add that value to respective attribute
	 *   list else it will do nothing.
	 * @param line
	 */
	private void addAttributeValues(String[] line)
	{
		int numOfAttributes = line.length - 1;
		
		for(int i = 0; i < numOfAttributes; i++)
		{
			boolean doesValueContains = listOfAttributesAndPossibleValues.get(i).contains(line[i]);
			if(!doesValueContains)
			{
				listOfAttributesAndPossibleValues.get(i).add(line[i]);
			}
		}
	}
	
	public String[] getPossibleAttributeValues(int atColumnIndex)
	{
		String[] result = null;
		if(atColumnIndex >=0 && atColumnIndex < listOfAttributesAndPossibleValues.size())
		{
			int numberOfValues = listOfAttributesAndPossibleValues.get(atColumnIndex).size();
			result = new String[numberOfValues];
			return listOfAttributesAndPossibleValues.get(atColumnIndex).toArray(result);
		}
		return result;
	}
	
	public String[] getAllPossibleClassValues()
	{
		String[] result = new String[possibleClassValues.size()];
		return possibleClassValues.toArray(result);
	}
	
	public List<ArrayList<String>> getAllPossibleAttributesAndValues()
	{
		return listOfAttributesAndPossibleValues;
	}
	
	public int getNumberOfAttributes()
	{
		return numberOfAttributes;
	}
	
	public TrainingExample[] getTrainingExamples()
	{
		TrainingExample result[] = new TrainingExample[trainingExamples.size()];
		result = trainingExamples.toArray(result);
		return result;
	}
	
	public Map<String, String[]> getAttributeAndItsPossibleValues()
	{
		Map<String, String[]> result = new HashMap<String, String[]>();
		
		for(int i = 0; i < listOfAttributesAndPossibleValues.size(); i++)
		{
			String[] temp = new String[listOfAttributesAndPossibleValues.get(i).size()];
			String[] possibleValues = listOfAttributesAndPossibleValues.get(i).toArray(temp);
			String key = "att" + Integer.toString(i);
			result.put(key, possibleValues);
		}
			
		return result;
	}
}
