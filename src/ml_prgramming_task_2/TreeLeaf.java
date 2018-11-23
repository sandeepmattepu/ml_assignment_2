package ml_prgramming_task_2;

public class TreeLeaf extends TreePart 
{

	public TreeLeaf(double EntropyAtPart, String NameOfPart)
	{
		super(EntropyAtPart, NameOfPart);
	}

	@Override
	public boolean isNode()
	{
		return false;
	}

}
