/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package sim.app.noc3d;

import sim.engine.*;
import sim.util.*;

/** The NodeElement class is used to create node elements. Which are the nodes in the
 *   network. Here the step method is called during the schedule and for each call
 *   the statements get executed and prints the number of packets present at the node
 *   during that time.  
 *   
 * @author mankas
 *
 */
public class NodeElement implements Steppable 
{   
	/**
	 * 
	 */
	private static final long serialVersionUID = 126094868933294710L;
	private double loc_x,loc_y,loc_z;
	/** Mass is the mass for the nodeunit. */
	public double mass;
	/** Diameter of the nodeunit which  is represented as a sphere in the network. */
	public double diameter;
	/** loc_ne is the location of the nodelement.*/
	public Double3D loc_ne;
	private double Stime_curr ; 

	public void setMass(double val) { if (val > 0) { mass = val; diameter = Math.sqrt(val); } }

	public NodeElement(double x, double y, double z, double m)
	{
		loc_x=x;
		loc_y=y;
		loc_z=z;
		loc_ne = new Double3D(loc_x,loc_y,loc_z);
		//System.out.println("loc_ne" +loc_ne);
		mass = m;
		diameter = Math.sqrt(m);
	}

	public NodeElement(double x, double y, double z)
	{
		loc_x=x;
		loc_y=y;
		loc_z=z;
	}

	public void step( final SimState state)
	{
		Noc3D tut = (Noc3D) state;
		double Stime;
		
		
		Bag pktsobjs = tut.packets.getObjectsAtLocation(loc_ne);  // Gives the packets which are at location loc_ne.
		Stime_curr = tut.schedule.getTime();
		if(Stime_curr!= 32.0)
		{
        if(pktsobjs!=null)                     
		{
			//System.out.println(pktsobjs.get);
			int numps = pktsobjs.numObjs;       // Counts the number of packets at that node.
			
          //  Double3D[] num = numps.toArray(new Double3D[numps.size()])
			
            
			if(numps!=0)	                    
			{
				
				
				// System.out.println("loc_ne at node element" + loc_ne);
				Stime = tut.schedule.getTime();    
				//System.out.println("numpackets at node :"+numps+ "@ location"+loc_ne+ "@time"+Stime); // Prints the number of packets at current scheduling time.
				if(numps>4)
				{
					//Packet top_p = (Packet)pktsobjs.top();  // trial code
			/*		Object [] top_a = pktsobjs.toArray();
					System.out.println("top_a_length"+ top_a.length+ "top_a_one" +top_a[0]+ "two"+top_a[1]);
					for(int x=0; x< top_a.length; x++ )
					{
					Class<? extends Object> objclass = top_a[x].getClass();
					//System.out.println(objclass.getDeclaredConstructors());
					Constructor[] constructors = objclass.getConstructors();
					if (constructors.length > 0)
					{
					    for (int i = 0; i < constructors.length; i++)
					    {
					       // System.out.println(constructors[i]);
					        
					   //    System.out.println(constructors[i]);
					        
					    }
					} 
					} */
					//System.out.println("top_p:"+ top_p);
					
				  System.out.println("numpackets exceeded the limit:"+numps +"more packet traffic detected at node"+ loc_ne + "@time"+Stime); //for congestion uncmment this
				}
			}
			else
				return;
		}
			else
			{
				//System.out.println("loc_ne"+loc_ne);
				return;
			//}
		}
		}
		else
		{
			 tut.kill();
		}
		
	//	Bag loc_start= tut.packets.
	}
}

