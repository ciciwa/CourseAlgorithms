// Jordan Davis
// 12.5.12
// COMP 4030
// Programming Project 2


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CoinGame 
{
	public static void printCoins(int[] coins)
	{
		for (int i = 0; i < coins.length; i++)
			System.out.print(coins[i] + " ");
		System.out.println("\n");
	}
	
	public static void printMatrix(int [][] V)
	{
		for (int i = 0; i < V.length; i++)
		{
			for (int j = 0; j < V[0].length; j++)
			{
				System.out.print(V[i][j] + " ");
			}
			
			System.out.println();
		}
		
		System.out.println("\n");
	}
	
	// optimize the score with the given coins
	public static int maxScore(int[][] V, int[] c)
	{
		// a single coin is always the maximum
		for (int i = 0; i < V.length; i++)
			V[i][i] = c[i];
		
		// fill up the diagonal of the matrix
		for (int diag = 1; diag <= V.length - 1; diag++)
		{
			for (int p = 0, q; p < V.length - diag; p++)
			{
				q = p + diag;
				
				// base case
				if (q - p == 1)
				{
					V[p][q] = Math.max(c[p], c[q]);
				}
				// recursive step
				else
				{
					V[p][q] = Math.max(Math.min(V[p + 1][q - 1], V[p + 2][q]) + c[p], Math.min(V[p][q - 2], V[p + 1][q - 1]) + c[q]);
				}
			}
		}
		
		printMatrix(V);
		
		return V[0][V.length - 1];
	}
	
	public static void main(String[] args) 
	{
		String buffer = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		int n = 0;
		
		System.out.print("Enter an even number: ");
		
		try
		{
			buffer = input.readLine();
			n = Integer.parseInt(buffer);
			
			if (n % 2 != 0)
			{
				System.out.println(n + " is an odd number...now terminating");
				System.exit(1);
			}
		}
		catch (IOException ioe)
		{
			System.out.println("Error reading input...now terminating.");
			System.exit(1);
		}
		catch (NumberFormatException nfe)
		{
			System.out.println(buffer + " is not an integer...now terminating");
			System.exit(1);
		}
		
		int[] coins = new int[n];
		
		for (int i = 0; i < n; i++)
		{
			coins[i] = (int)(101 * Math.random());
		}
		
		printCoins(coins);
		
		int[][] V = new int[n][n];
		System.out.println("The maximum score you can get is: " + maxScore(V, coins));
	}
}
