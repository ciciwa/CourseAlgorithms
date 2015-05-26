
public class LinbonacciNumbers 
{
	public static int linbonacci(int n)
	{
		int[] lin = new int[n + 1];
		
		for (int i = 0; i < n + 1; i++)
		{
			if (i <= 4)
			{
				lin[i] = i - 1;
			}
			else
			{
				lin[i] = i + lin[i - 2] + lin[i - 4] + lin[i - 5];
			}
			
			//System.out.print(lin[i] + " ");
		}
		
		return lin[n];
	}
	
	public static int linbonacci2(int n)
	{
		int[] lin = new int[6];
		
		for (int i = 0; i < n + 1; i++)
		{
			if (i <= 4)
			{
				lin[i] = i - 1;
			}
			else
			{
				lin[i % 6] = i + lin[(i - 2) % 6] + lin[(i - 4) % 6] + lin[(i - 5) % 6];
			}
			
			//System.out.print(lin[i % 6] + " ");
		}	
		
		return lin[n % 6];
	}
	
	public static void main(String[] args) 
	{
		System.out.println(linbonacci(20));
		System.out.println(linbonacci2(20));
	}
}
