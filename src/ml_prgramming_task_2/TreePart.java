package ml_prgramming_task_2;

public abstract class TreePart 
{
	private final double entropyAtPart;
	private final String nameOfPart;
	
	public abstract boolean isNode();
	
	public TreePart(double EntropyAtPart, String NameOfPart)
	{
		entropyAtPart = EntropyAtPart;
		nameOfPart = NameOfPart;
	}
	
	public double getEntropyAtThisPart()
	{
		return entropyAtPart;
	}
	
	public String getNameOfPart()
	{
		return nameOfPart;
	}
}
