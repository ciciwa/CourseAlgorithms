public abstract class Entity
{
    // Each entity will have a distance table
    protected int[][] distanceTable = new int[NetworkSimulator.NUMENTITIES]
                                           [NetworkSimulator.NUMENTITIES];

    protected int[] neighbors;
    
    // The update function.  Will have to be written in subclasses by students
    public abstract void update(Packet p);
    
    // The link cost change handler.  Will have to be written in appropriate
    // subclasses by students.  Note that only Entity0 and Entity1 will need
    // this, and only if extra credit is being done
    public abstract void linkCostChangeHandler(int whichLink, int newCost);

    // Print the distance table of the current entity.
    protected abstract void printDT();
    
    // gets the minimum cost from this entity to each of the other entities
    protected int[] getMinCost()
    {
    	int min;
    	int[] minCost = new int[NetworkSimulator.NUMENTITIES];
    	
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    	{
    		min = distanceTable[i][0];
    		
    		for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
    		{
    			if (distanceTable[i][j] < min)
    				min = distanceTable[i][j];
    		}
    		
    		minCost[i] = min;
    	}
    	
    	return minCost;
    }
}
