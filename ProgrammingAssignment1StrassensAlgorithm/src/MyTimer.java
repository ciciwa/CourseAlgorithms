class MyTimer 
{
	long starttime;

	MyTimer() 
	{
		starttime = System.currentTimeMillis();
	}

	public double curTime() 
	{
		return (System.currentTimeMillis() - starttime) / 1000.0;
	}
}
