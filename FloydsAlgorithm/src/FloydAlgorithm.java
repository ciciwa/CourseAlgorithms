
public class FloydAlgorithm 
{
	final static String[] vertices = {"A", "B", "C", "D", "E", "F", "G"};
	
	public static void floyd(int n, int[][] W, int[][] D, String[][] P)
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				P[i][j] = "0";
			}
		}
		
		D = W;
		
		for (int k = 0; k < n; k++)
		{
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < n; j++)
				{
					if (D[i][k] + D[k][j] < D[i][j])
					{
						P[i][j] = vertices[k];
						D[i][j] = D[i][k] + D[k][j];
					}
				}
			}
			
			System.out.println("D(" + k + ") =\n");
			printMatrix(D);
			System.out.println("P =\n");
			printMatrix(P);
		}
	}
	
	public static void path(int q, int r, int[][] P)
	{
		if (P[q][r] != 0)
		{
			path(q, P[q][r], P);
			System.out.print("v" + P[q][r] + " ");
			path(P[q][r], r, P);
		}
	}
	
	public static void printMatrix(int[][] A)
	{
		for (int i = 0; i < A.length; i++)
		{
			for (int j = 0; j < A[0].length; j++)
			{
				System.out.print(A[i][j] + "\t");
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
	
	public static void printMatrix(String[][] A)
	{
		for (int i = 0; i < A.length; i++)
		{
			for (int j = 0; j < A[0].length; j++)
			{
				System.out.print(A[i][j] + "\t");
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
	
	public static void main(String[] args) 
	{
		int[][] W = 
			{
				{0, 1, 999, 8, 5, 999, 999},
				{1, 0, 4, 999, 6, 10, 999},
				{999, 4, 0, 999, 999, 999, 7},
				{8, 999, 999, 0, 999, 999, 2},
				{5, 6, 999, 999, 0, 999, 9},
				{999, 10, 999, 2, 9, 0, 3},
				{999, 999, 7, 999, 999, 3, 0}
			};
		
		int[][] D = new int[W.length][W[0].length];
		String[][] P = new String[W.length][W[0].length];

		System.out.println("W =\n");
		printMatrix(W);
		floyd(W.length, W, D, P);
	}
}
