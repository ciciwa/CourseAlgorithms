public class Entity2 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity2()
    {
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		for (int j = 0; j < NetworkSimulator.NUMENTITIES - 2; j++)
    			distanceTable[i][j] = 999;
    	
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		distanceTable[i][2] = NetworkSimulator.cost[i][2];
    	
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		distanceTable[i][3] = 999;
    	
    	neighbors = new int[3];
    	neighbors[0] = 0;
    	neighbors[1] = 1;
    	neighbors[2] = 3;
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
    	int source = p.getSource();
    	boolean dtChanged = false;
    	
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    	{		
    		if (distanceTable[i][source] > distanceTable[source][2] + p.getMincost(i))
    		{
    			distanceTable[i][source] = distanceTable[source][2] + p.getMincost(i);	
    			dtChanged = true;
    		}
    	}

    	printDT();
    	
    	if (dtChanged)
    	{
    		NetworkSimulator.toLayer2(new Packet(2, 0, this.getMinCost()));
    		NetworkSimulator.toLayer2(new Packet(2, 1, this.getMinCost()));
    		NetworkSimulator.toLayer2(new Packet(2, 3, this.getMinCost()));
    	}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D2 |   0   1   3");
        System.out.println("----+------------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 2)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (j == 2)
                {
                    continue;
                }
                
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
