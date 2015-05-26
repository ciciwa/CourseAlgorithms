public class Entity0 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity0()
    {
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		distanceTable[i][0] = NetworkSimulator.cost[i][0];
    	
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    		for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
    			distanceTable[i][j] = 999;
    	
    	neighbors = new int[3];
    	neighbors[0] = 1;
    	neighbors[1] = 2;
    	neighbors[2] = 3;
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    
    // the comments for the update method of Entity0 are the same for the other Entities
    
    public void update(Packet p)
    {
    	int source = p.getSource();
    	boolean dtChanged = false;		// whether or not this entity's distance 
    									// table changed during this update
    	
    	// for each entity that the source is connected to apply the Bellman-Ford algorithm
    	// dx(y) = minimum{dx(y), c(x, v) + dv(y)} (Bellman-Ford equation)
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    	{		
    		// if the current cost to the source's neighbor through source from this entity
    		// is greater than the sum of cost to source directly from this entity and 
    		// the minimum cost from source to its neighbor, then update
    		if (distanceTable[i][source] > distanceTable[source][0] + p.getMincost(i))
    		{
    			distanceTable[i][source] = distanceTable[source][0] + p.getMincost(i);	
    			dtChanged = true;
    		}
    	}
    	
    	printDT();
    	
    	// if this entity's distance table was changed then broadcast 
    	// the new information to all connected entities
    	if (dtChanged)
    	{
    		NetworkSimulator.toLayer2(new Packet(0, 1, this.getMinCost()));
    		NetworkSimulator.toLayer2(new Packet(0, 2, this.getMinCost()));
    		NetworkSimulator.toLayer2(new Packet(0, 3, this.getMinCost()));
    	}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
       
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   1   2   3");
        System.out.println("----+------------");
        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
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
