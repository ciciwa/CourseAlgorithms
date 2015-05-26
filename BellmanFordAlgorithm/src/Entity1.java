public class Entity1 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity1()
    {
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		distanceTable[i][0] = 999;
    	
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		distanceTable[i][1] = NetworkSimulator.cost[i][1];
    	
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		for (int j = 2; j < NetworkSimulator.NUMENTITIES; j++)
    			distanceTable[i][j] = 999;

    	neighbors = new int[2];
    	neighbors[0] = 0;
    	neighbors[1] = 2;
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
    		if (distanceTable[i][source] > distanceTable[source][1] + p.getMincost(i))
    		{
    			distanceTable[i][source] = distanceTable[source][1] + p.getMincost(i);	
    			dtChanged = true;
    		}
    	}

    	printDT();
    	
    	if (dtChanged)
    	{
    		NetworkSimulator.toLayer2(new Packet(1, 0, this.getMinCost()));
    		NetworkSimulator.toLayer2(new Packet(1, 2, this.getMinCost()));
    	}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("         via");
        System.out.println(" D1 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 1)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {
            
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
