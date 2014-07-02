/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package sim.app.noc3d;
import sim.engine.*;

/** The NodeUnit class is used to create node units. This is the place where the 
 *  packets are instantiated (i.e packet objects are created). Call for setPacket
 *  method in the Noc3d is done. Then the packets are scheduled from NodeUnit 
 *  class step method.
 *   
 * @author mankas
 *
 */
public class NodeUnit implements Steppable 
{     
	/**
	 * 
	 */
	private static final long serialVersionUID = 2197448114447955650L;
	private double loc_x,loc_y,loc_z;
	private double tl_x,tl_y,tl_z;
	public double nmass;
	public double ndiameter;
	private Packet packets;  
	public int numPacks = 4;

	public void setMass(double val) { if (val > 0) { nmass = val; ndiameter = Math.sqrt(val); } }

	public NodeUnit(double x, double y, double z,  double m, double t_x, double t_y, double t_z)
	{
		loc_x=x;
		loc_y=y;
		loc_z=z;
		tl_x = t_x;
		tl_y = t_y;
		tl_z = t_z;
		nmass = m;
		ndiameter = Math.sqrt(m);
	}

	public void step( final SimState state)
	{

		Noc3D tut = (Noc3D) state;
		double Stime = tut.schedule.getTime()+9;  // Time of the schedule when the packets starts from the node.

		for(int i=0; i<numPacks; i++)          // Create and schedule each packet.
		{     
			packets = new Packet(loc_x,loc_y,loc_z,12.0,tl_x,tl_y,tl_z);
			tut.setPacket(loc_x,loc_y,loc_z,packets);
			tut.schedule.scheduleRepeating(Stime,packets); // Scheduling packets after some delay. 
			Stime = Stime+1.0;                             // Incrementing the scheduling time in order to release the next packet in next step.
		}	
	}
}

