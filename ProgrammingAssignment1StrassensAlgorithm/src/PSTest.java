
class PSTest 
{
	public static void main(String[] args) 
	{
		PSMatrix a = new PSMatrix(2);
		PSMatrix b = new PSMatrix(2);
		PSMatrix c = new PSMatrix(2);
		PSMatrix d = new PSMatrix(2);
		PSMatrix e = new PSMatrix(2);
		PSMatrix f = new PSMatrix(2);
		a.Read();
		b.Random(10);
		c = a.Add(b);
		d = a.Multiply(b);
		e.AddInto(a, b);
		f.MultiplyInto(a, b);
		a.Print();
		System.out.println("-----");
		b.Print();
		System.out.println("-----");
		c.Print();
		System.out.println("-----");
		d.Print();
		System.out.println("-----");
		e.Print();
		System.out.println("-----");
		f.Print();
		System.out.println("-----");
	}
}
