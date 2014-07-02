/**
 * 
 */
package sim.app.noc3d;
import sim.engine.*;

//import java.awt.*;
import java.util.Random;

import sim.util.*;

/**
 * @author mankas
 *
 */
public class RandomPacket implements Steppable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9192413251598987339L;
	private double temp_dst_x,temp_dst_y,temp_dst_z ;
	
	public double rpmass;                      // Packet mass.
	public double rpdiameter;                  // Packet diameter
	private double time_curr_tmp ; 
	
	public RandomPacket(double x, double y, double z,  double m) 
	{
		rpmass = m;
		rpdiameter = Math.sqrt(m);
		}
	
	// Random packet counts the number of packets at each node when it reach that particular node at some time during the schedule.
	public void step(SimState state)
	{
		Noc3D tut = (Noc3D) state;
		double[] n = { 0.0, 30.0, 60.0, 90.0};  // Array in which the random number should be chosen from.
		double Stime;
		
		Random random = new Random();
		temp_dst_x = n[random.nextInt(n.length)];     // Gives a random number from the given array.
		temp_dst_y = n[random.nextInt(n.length)];
		temp_dst_z = n[random.nextInt(n.length)];
		Double3D loc_tmp = new Double3D(temp_dst_x,temp_dst_y,temp_dst_z);
	//	System.out.println("loc_tmp" + loc_tmp);
		Bag pktsobjs = tut.packets.getObjectsAtLocation(loc_tmp);  // Gives the packets which are at location loc_ne.
		
		time_curr_tmp = tut.schedule.getTime();
		if(time_curr_tmp != 32.0)
		{
		if(pktsobjs!=null)                     
		{
			int numps = pktsobjs.numObjs;       // Counts the number of packets at that node.

			if(numps!=0)	                    
			{
				// System.out.println("loc_ne at node element" + loc_ne);
				Stime = tut.schedule.getTime();    
				System.out.println("Random Packet detected numpackets :"+numps+ "@ location"+loc_tmp+ "@time"+Stime); // Prints the number of packets at current scheduling time.
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
		tut.randompackets.setObjectLocation(this, loc_tmp);
		}
		else
		{
			tut.randompackets.remove(this);
			// tut.kill(); 
		}
				
	}
	

}
