package sim.app.noc3d;
import sim.engine.*;
import sim.util.*;


public class Packet implements Steppable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 818147465636952625L;
	public double rmass;                      // Packet mass.
	public double rdiameter;                  // Packet diameter
	private Double3D target_loc;               // Target location to which packet should reach.
	private double Distance = 30.0;           // Distance packet's address is incremented in each step. (It is the distance between any two nodes in the schedule)
	private double time_curr ;                

	public void setrMass(double val) { if (val > 0) { rmass = val; rdiameter = Math.sqrt(val); } }
   
	/** This is a constructor to the Packet class.
	 * 
	 * @param x  x-coordinate of packet's initial location
	 * @param y  y-coordinate of packet's initial location
	 * @param z  z-coordinate of packet's initial location
	 * @param m  Mass of the packet which is represented in the form of a sphere.
	 * @param t_x x-coordinate of packet's target location
	 * @param t_y y-coordinate of packet's target location
	 * @param t_z z-coordinate of packet's target location
	 */
	public Packet(double x, double y, double z,  double m, double t_x, double t_y, double t_z)
	{
		target_loc = new Double3D(t_x,t_y,t_z); 
		rmass = m;
		rdiameter = Math.sqrt(m);
	}


	public void step(SimState state)
	{
		Noc3D tut = (Noc3D) state;
		Double3D rpos = tut.packets.getObjectLocation(this);
		
		
		double txcor = target_loc.getX();		     // X-coordinate of target location where the packet should be sent.
		double tycor = target_loc.getY();           // Y-coordinate of target location
		double tzcor = target_loc.getZ();           // Z of target location
		double xcoor=  rpos.getX();			         // X-coordinate of current node.
		double ycoor = rpos.getY();                 // Y "    "      "    "
		double zcoor = rpos.getZ();                 // Z "  "   "    " 
        
		// Routing algorithm of the packet.
		if(txcor > xcoor){                                    // if target x coordinate is greater than current xcoor execute following statements.
			// System.out.println("txcor>xcoor:"+"txcor"+txcor+"xcoor"+xcoor);  //routing check
			// System.out.println("rpos"+rpos);
			Double3D newpos = new Double3D(rpos.x+Distance, rpos.y, rpos.z);  // Increment the x-coordinate value in order to follow the path in x direction.
			tut.packets.setObjectLocation(this,newpos);                       // Change the packet position to the new position.
			// System.out.println("rpos"+rpos+"newpos"+newpos);  // routing check
			rpos=newpos;
		}
		else if( txcor < xcoor){
			// System.out.println("pos @less than"+pos);
			// System.out.println("txcor<xcoor:"+"txcor"+txcor+"xcoor"+xcoor);  //routing check
			Double3D newpos = new Double3D(rpos.x-Distance, rpos.y, rpos.z);
			tut.packets.setObjectLocation(this,newpos);
			// System.out.println("rpos"+rpos+"newpos"+newpos);      //routing check
			rpos=newpos;
		}
		else if(txcor == xcoor) {
			// System.out.println("x equal");
			if(tycor>ycoor) {
				// System.out.println("tycor>ycoor:"+"tycor"+tycor+"ycoor"+ycoor); //routing check
				// System.out.println("rpos"+rpos);
				Double3D newpos = new Double3D(rpos.x, rpos.y+Distance, rpos.z);
				tut.packets.setObjectLocation(this,newpos);
				// System.out.println("rpos"+rpos+"newpos"+newpos);  //routing check
				rpos=newpos;
			} 
			else if(tycor<ycoor){
				// System.out.println("tycor<ycoor:"+"tycor"+tycor+"ycoor"+ycoor);   //routing check
				// System.out.println("rpos"+rpos);
				Double3D newpos = new Double3D(rpos.x, rpos.y-Distance, rpos.z);
		     	tut.packets.setObjectLocation(this,newpos); 
				// System.out.println("rpos"+rpos+"newpos"+newpos);  //routing check
				rpos=newpos;
			}
			else if((txcor == xcoor)&&(tycor == ycoor)) {
				// System.out.println("x and y are equal");
				if(tzcor>zcoor){
					// System.out.println("tzcor>zcoor:"+"tzcor"+tzcor+"zcoor"+zcoor); //routing check
					// System.out.println("rpos"+rpos);
					Double3D newpos = new Double3D(rpos.x, rpos.y, rpos.z+Distance);
					tut.packets.setObjectLocation(this,newpos);
					// System.out.println("rpos"+rpos+"newpos"+newpos);  //routing check
					rpos=newpos;
				}
				else if(tzcor<zcoor){
					// System.out.println("tzcor<zcoor:"+"tzcor"+tzcor+"zcoor"+zcoor);  //routing check
					// System.out.println("rpos"+rpos);
					Double3D newpos = new Double3D(rpos.x, rpos.y, rpos.z-Distance);
					tut.packets.setObjectLocation(this,newpos);
					// System.out.println("rpos"+rpos+"newpos"+newpos);  //routing check
					rpos=newpos;
				}
				else {
					//System.out.println("Reached destination"+" @time :" + tut.schedule.getTime() ); // when all the packet coordinates are equal to the target location coordinates then packet reached the destination.
					// System.out.println("Current location:"+rpos+ " @time :" + tut.schedule.getTime() );
					time_curr = tut.schedule.getTime();  // Gives the current time in the schedule.
					if(time_curr == 32.0)   // 31 is the maximum possible time a packet can reach its destination. It is the time take for a packet to travel in longest possible route.
					{
						tut.packets.remove(this); // When all packets reaches the destination then they are removed fro the schedule.
						tut.kill();               // This ends the schedule.
						System.exit(0);
					} 
				}
			}

		}
	}    
}





