package ml_prgramming_task_2;

import java.util.LinkedHashMap;

public class Main 
{
	public static void main(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("Invalid arguments");
		}
		else
		{
			String trainingDataLocation = args[0];
			CSVFileReader reader = new CSVFileReader(trainingDataLocation);
			
			TrainingExample[] examples = reader.getTrainingExamples();
			LinkedHashMap<String, String[]> attributeAndValues = reader.getAttributeAndItsPossibleValues();
			String[] allPossibleClassValues = reader.getAllPossibleClassValues();
			DecisionTreeBuilder treeBuilder = new DecisionTreeBuilder(examples, attributeAndValues, allPossibleClassValues);
			
			TreePart rootNode = treeBuilder.buildDecisionTree();
			try 
			{
				TreeToXMLParser parser = new TreeToXMLParser(rootNode);
				String result = parser.toString(false);
				System.out.println(result);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
	}
}
