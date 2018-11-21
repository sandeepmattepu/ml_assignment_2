package ml_prgramming_task_2;

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
		}
	}
}
