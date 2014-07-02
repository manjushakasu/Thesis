/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package sim.app.noc3d;
import sim.engine.*;
import sim.field.continuous.*;
import sim.field.network.*;
import sim.util.*;

/** Noc3D is a SimState of the application. It contains a continous field which holds
 * all elements such as node_elements, packets, paths, node_units.
 * 
 * @author mankas
 *
 */
public class Noc3D extends SimState
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5776187218355914487L;
	public Continuous3D node_elements;  
	public Continuous3D packets;
	public Continuous3D node_units; 
	public Continuous3D randompackets; 
    
	public Network paths;  
	public Network packetportrayal;
	public Network randompacketportrayal;
    
	/**  Count of node_elements created in this class.	 */
	public int numNode_elements;
	/** Count of paths between the all nodeelements.  */
	public int numPaths;  
	/** Number of packets created. */
	public int numps = 0;
    
	/** User defined grid width  */
	public double gridWidth = 90.0; 
	/** User defined grid height  */
	public double gridHeight = 90.0; 
	/** User defined grid length  */
	public double gridLength = 90.0; 

	public Noc3D(long seed)
	{
		super(seed);
	}
	/** 
	 * Here is the place where the node_elements,node_units,paths,packets are created.
	 */

	public void start()
	{
		super.start();

		node_elements = new Continuous3D(0.0,gridWidth, gridHeight, gridLength); 
		packets = new Continuous3D(0.0,gridWidth, gridHeight, gridLength);
		randompackets = new Continuous3D(0.0,gridWidth, gridHeight, gridLength);
		node_units = new Continuous3D(0.0,gridWidth, gridHeight, gridLength); 


		paths = new Network();
		packetportrayal = new Network();
		randompacketportrayal = new Network();
		
		int x,y,z;
  
		// Create node elements, which are nodes in the schedule.
		for(x=0; x<=3; x+=1)
		{
			for(y=0;y<=3;y+=1)
			{
				for(z=0;z<=3;z+=1)
				{               
				        Double temp_x, temp_y, temp_z;
					temp_x = x * gridWidth/3;
					temp_y = y * gridHeight/3;
					temp_z = z * gridLength/3;
					// Must be final to be used in the anonymous class below
					final NodeElement node_element = new NodeElement(temp_x,temp_y,temp_z,10.0);
					node_elements.setObjectLocation(node_element,
							new Double3D(temp_x,temp_y,temp_z));
					
					paths.addNode(node_element);
				//	System.out.println("x"+ temp_x+"y"+temp_y+"z"+ temp_z);
					// System.out.println("from noc3d"+node_element);
					schedule.scheduleRepeating(14.0,node_element);      //14.0    
				}
			}
		}
			
		

		Bag node_elementObjs = node_elements.getAllObjects();
		numNode_elements = node_elementObjs.numObjs;
		// System.out.println ("no of Coordinates " + numNode_elements);
        int i,j,k;
		NodeElement from;
		NodeElement to;
		Path path = new Path();
		
		// Create paths between node elements. // z-axis
	 	for( i=0;i<=62;i++) 
		{
			from = (NodeElement)( node_elementObjs.objs[i]);
			to = (NodeElement)( node_elementObjs.objs[i+1]);
			paths.addEdge(from,to,path);
			numPaths++;
			
			// Following conditions are given in order to elemenate the unnecessary paths in the network.
			if(i>=2)
			{
				switch(i)
				{
				case 2: case 6: case 10: case 14 : case 18: case 22: case 26: case 30: case 34: case 38: case 42: case 46: case 50: case 54: case 58 : i++;
				break;
			        default : break;
				}   
			}
		}
		
		// These paths are created by reading each node element from the array of node elements created.// y-axis
		for(j=0;j<=59;j++)    		
		{
			from = (NodeElement)(node_elementObjs.objs[j]);
			to = (NodeElement)(node_elementObjs.objs[j+4]);
			paths.addEdge(from,to,path);
			numPaths++;

			if(j>=8)
			{
				switch(j) 
				{
				case 11 : case 27: case 43: j+=4;
				break;
				default : break; 
				}   
			}  	    
		} 
		
		// This loop creates all edges in x-axis. Eleminates all the unecessary paths to create the desired network.
		for(k=0;k<=47;k++)    
		{
			from = (NodeElement)(node_elementObjs.objs[k]);
		    to = (NodeElement)(node_elementObjs.objs[k+16]);
			paths.addEdge(from,to,path);
			numPaths++;
			}  

		// System.out.println ("no of edges " + numPaths); 
		setNodeUnit(0.0,0.0,0.0,90.0,90.0,90.0);      // First three digits are the x,y,z co-ordinates of the packet's starting location (i.e address of node from which you want to send the packets) and last three digits are target location of packet.
		setNodeUnit(30.0,30.0,30.0,60.0,60.0,60.0);   // Create node units in order to initiate the packets. Pass the location of node unit and the destination address for the packet as arguments.
		setNodeUnit(0.0,90.0,90.0,0.0,0.0,30.0);
		setNodeUnit(30.0,30.0,0.0,90.0,90.0,0.0);
		setNodeUnit(0.0,0.0,60.0,90.0,30.0,90.0);
		setNodeUnit(60.0,30.0,60.0,30.0,30.0,90.0);
		setNodeUnit(30.0,30.0,0.0,0.0,0.0,0.0);
		setNodeUnit(0.0,60.0,60.0,0.0,30.0,90.0);
		setNodeUnit(90.0,0.0,90.0,0.0,0.0,30.0);
		setNodeUnit(60.0,60.0,60.0,90.0,90.0,0.0);
		setRandomPacket(30.0,0.0,30.0);
	}

	/** setPacket is the method where packets are created. This method is called from the
	 * nodeunit class. 
	 * 
	 * @param p x-coordinate of the packets location
	 * @param q y-coordinate of the packets location
	 * @param r z-coordinate of the packets location
	 * @param pkts Objects of the packets which are instatiated in nodeunit and 
	 *         passed to the setPacket method during teh call.
	 */
	public void setPacket(double p, double q, double r, Object pkts)
	{
		packets.setObjectLocation(pkts,new Double3D(p, q, r));
	} 
	
	public void setRandomPacket(double rpp, double rpq, double rpr)
	{
		if(rpp>=0 && rpp<=90 && rpq>=0 && rpq<=90 && rpr>=0 && rpr<=90)
		{
			RandomPacket randompacket = new RandomPacket(rpp,rpq,rpr,13.0);       // 11.0 is the diameter of the sphere shape.
			randompackets.setObjectLocation(randompacket,new Double3D(rpp, rpq, rpr)); 
			schedule.scheduleRepeating(0.0,randompacket);         
			
		}
	}

	/** In setNodeUnit method nodeunit is created and scheduled at the instance of 10.0.
    * 
    * @param np x-coordinate of the nodeunits location
	* @param nq y-coordinate of the nodeunits location
	* @param nr z-coordinate of the nodeunits location
	* @param tp x-coordinate of the packets target location
	* @param tq y-coordinate of the packets target location
	* @param tr z-coordinate of the packets target location
	*/
	public void setNodeUnit(double np, double nq, double nr, double tp, double tq, double tr)
	{
		if(np>=0 && np<=90 && nq>=0 && nq<=90 && nr>=0 && nr<=90 && tp>=0 && tp<=90 && tq>=0 && tq<=90 && tr>=0 && tr<=90) // The range in which address should lie. If the address is out of range then the schedule exits with statement printed in else loop.
		{
			NodeUnit nodeunit = new NodeUnit(np,nq,nr,11.0,tp,tq,tr);       // 11.0 is the diameter of the sphere shape.
			node_units.setObjectLocation(nodeunit,new Double3D(np, nq, nr)); 
			schedule.scheduleOnce(10.0,nodeunit);                    // 10.0 is the time when the nodeunit is scheduled.
		}
		else
		{
			System.out.println("Input values for address were not in the range");
			System.exit(0);                  //Exits the scheduling.
		}
	} 
	
	public static void main(String[] args)
	{
		doLoop(Noc3D.class, args);
		System.exit(0);                        
	}    
}
