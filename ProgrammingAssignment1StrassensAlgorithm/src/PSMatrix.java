import java.util.Random;
import java.util.Scanner;

class PSMatrix 
{
	int k;
	int[][] matrix;
	int size;

	PSMatrix(int k1) 
	{
		k = k1;
		size = 1;
		
		for (int i = 0; i < k; i++) 
		{
			size = size * 2;
		}
		
		matrix = new int[size][size];
	}

	PSMatrix(PSMatrix m) 
	{
		k = m.k;
		size = m.size;
		matrix = new int[size][size];
		
		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				matrix[i][j] = m.matrix[i][j];
			}
		}
	}

	void Random(int maxv) 
	{
		Random r1 = new Random();
		
		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				matrix[i][j] = r1.nextInt(maxv);
			}
		}
	}

	void Copy(PSMatrix m)
	{
		if (k != m.k) 
		{
			System.err.println("Array size incompatible!");
			System.exit(0);
		}

		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				matrix[i][j] = m.matrix[i][j];
			}
		}
	}

	PSMatrix Add(PSMatrix m) 
	{
		if (k != m.k) 
		{
			System.err.println("Array size incompatible!");
			System.exit(0);
		}

		PSMatrix res = new PSMatrix(k);

		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				res.matrix[i][j] = matrix[i][j] + m.matrix[i][j];
			}
		}

		return res;
	}

	void AddInto(PSMatrix m1, PSMatrix m2) 
	{
		if ((k != m1.k) || (k != m2.k)) 
		{
			System.err.println("Array size incompatible!");
			System.exit(0);
		}

		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				matrix[i][j] = m1.matrix[i][j] + m2.matrix[i][j];
			}
		}
	}

	PSMatrix Multiply(PSMatrix m) 
	{
		if (k != m.k) 
		{
			System.err.println("Array size incompatible!");
			System.exit(0);
		}

		PSMatrix res = new PSMatrix(k);
		
		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				res.matrix[i][j] = 0;
				for (int k = 0; k < size; k++) 
				{
					res.matrix[i][j] += matrix[i][k] * m.matrix[k][j];
				}
			}
		}

		return res;
	}

	void MultiplyInto(PSMatrix m1, PSMatrix m2) 
	{
		if ((k != m1.k) || (k != m2.k)) 
		{
			System.err.println("Array size incompatible!");
			System.exit(0);
		}

		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				matrix[i][j] = 0;
				
				for (int k = 0; k < size; k++) 
				{
					matrix[i][j] += m1.matrix[i][k] * m2.matrix[k][j];
				}
			}
		}
	}
	
	/*
	 * 
	 * m1 = (a11 + a22)(b11 + b22)
	 * m2 = (a21 + a22)(b11)
	 * m3 = (a11)(b12 - b22)
	 * m4 = (a22)(b21 - b11)
	 * m5 = (a11 + a12)(b22)
	 * m6 = (a21 - a11)(b11 + b12)
	 * m7 = (a12 - a22)(b21 + b22)
	 * 
	 * c11 = m1 + m4 - m5 + m7
	 * c12 = m3 + m5
	 * c21 = m2 + m4
	 * c22 = m1 + m3 - m2 + m6
	 * 
	 */
	
	// returns the product of two matrices (incomplete)
	PSMatrix MultiplyStrass(PSMatrix m)
	{
		if ((k != m.k))
		{
			System.err.println("Array size incompatible!");
			System.exit(0);
		}
	
		PSMatrix result = new PSMatrix(k);
		multiplySubmatrix(m, result, k);
		return result;
	}
	
	// recursively multiply two matrices using Strassen's algorithm (incomplete)
	void multiplySubmatrix(PSMatrix m2, PSMatrix result, int k1)
	{
		if (k1 == 0)
		{
			result.matrix[0][0] = matrix[0][0] * m2.matrix[0][0];
		}
		else
		{
			PSMatrix[] m = new PSMatrix[7];
			
			for (int i = 0; i < m.length; i++)
			{
				m[i] = new PSMatrix(k1 - 1);
			}
			
			PSMatrix temp1 = new PSMatrix(k1 - 1);
			PSMatrix temp2 = new PSMatrix(k1 - 1);
			
			int k1end = (int)(Math.pow(2, k1 - 1));
			
			// m1
			temp1 = addSubmatrix(0, 0, this, k1end, k1end, k1 - 1);
			temp2 = m2.addSubmatrix(0, 0, m2, k1end, k1end, k1 - 1);
			temp1.multiplySubmatrix(temp2, m[0], k1 - 1);
			
			// m2
			temp1 = addSubmatrix(k1end, 0, this, k1end, k1end, k1 - 1);
			temp2 = m2.getSubmatrix(0, 0, k1 - 1);
			temp1.multiplySubmatrix(temp2, m[1], k1 - 1);
			
			// m3
			temp1 = getSubmatrix(0, 0, k1 - 1);
			temp2 = m2.subtractSubmatrix(0, k1end, m2, k1end, k1end, k1 - 1);
			temp1.multiplySubmatrix(temp2, m[2], k1 - 1);
			
			// m4
			temp1 = getSubmatrix(k1end, k1end, k - 1);
			temp2 = m2.subtractSubmatrix(k1end, 0, m2, 0, 0, k1 - 1);
			temp1.multiplySubmatrix(temp2, m[3], k1 - 1);
			
			// m5
			temp1 = addSubmatrix(0, 0, this, 0, k1end, k1 - 1);
			temp2 = m2.getSubmatrix(k1end, k1end, k1 - 1);
			temp1.multiplySubmatrix(m2, m[4], k1 - 1);
			
			// m6
			temp1 = subtractSubmatrix(k1end, 0, this, 0, 0, k1 - 1);
			temp2 = m2.addSubmatrix(0, 0, m2, 0, k1end, k1 - 1);
			temp1.multiplySubmatrix(m2, m[5], k1 - 1);

			// m7
			temp1 = subtractSubmatrix(0, k1end, this, k1end, k1, k1 - 1);
			temp2 = m2.addSubmatrix(k1end, 0, m2, k1end, k1, k1 - 1);
			temp1.multiplySubmatrix(temp2, m[6], k1 - 1);
		
			//...
		}
	}
	
	PSMatrix addSubmatrix(int startRow, int startCol, PSMatrix m2, int m2startRow, int m2startCol, int k1)
	{
		int k1end = (int)(Math.pow(2, k1));
		PSMatrix result = new PSMatrix(k1);
		
		for (int i = 0; i < k1end; i++)
		{
			for (int j = 0; j < k1end; j++)
			{
				result.matrix[i][j] = this.matrix[startRow + i][startCol + j] + m2.matrix[m2startRow + i][m2startCol + j];
			}
		}
		
		return result;
	}
	
	PSMatrix subtractSubmatrix(int startRow, int startCol, PSMatrix m2, int m2startRow, int m2startCol, int k1)
	{
		int k1end = (int)(Math.pow(2, k1));
		PSMatrix result = new PSMatrix(k1);
		
		for (int i = 0; i < k1end; i++)
		{
			for (int j = 0; j < k1end; j++)
			{
				result.matrix[i][j] = this.matrix[startRow + i][startCol + j] - m2.matrix[m2startRow + i][m2startCol + j];
			}
		}
		
		return result;
	}
	
	// gets a submatrix from a particular start point
	PSMatrix getSubmatrix(int startRow, int startCol, int k1)
	{
		int k1end = (int)(Math.pow(2, k1));
		PSMatrix result = new PSMatrix(k1);
		
		for (int i = 0; i < k1end; i++)
		{
			for (int j = 0; j < k1end; j++)
			{	
				result.matrix[i][j] = this.matrix[startRow + i][startCol + j];
			}
		}
		
		return result;
	}
	
	void Read() 
	{
		Scanner stdin = new Scanner(System.in);

		for (int i = 0; i < size; i++) 
		{
			System.out.println("Enter row " + i);
			for (int j = 0; j < size; j++) 
			{
				matrix[i][j] = stdin.nextInt();
			}
		}
	}

	void Print() 
	{
		for (int i = 0; i < size; i++) 
		{
			for (int j = 0; j < size; j++) 
			{
				System.out.printf("%5d ", matrix[i][j]);
			}
			
			System.out.println(" ");
		}
	}
}
