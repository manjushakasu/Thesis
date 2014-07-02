/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package sim.app.noc3d;
import sim.portrayal3d.network.*;
import sim.portrayal3d.continuous.*;
import sim.display3d.*;
import sim.engine.*;
import sim.display.*;
import javax.swing.*;
import sim.portrayal3d.simple.*;

/** Noc3DWithUI is a GUIState of the application. 
 * This class is responsible for the GUI's in the application.
 * 
 * @author mankas
 *
 */
public class Noc3DWithUI extends GUIState
{
	public Display3D display;
	public JFrame displayFrame;

	NetworkPortrayal3D pathPortrayal = new NetworkPortrayal3D();               //edgePortrayal
	ContinuousPortrayal3D node_elementPortrayal = new ContinuousPortrayal3D(); //nodePotrayal
	ContinuousPortrayal3D node_unitPortrayal = new ContinuousPortrayal3D();
	ContinuousPortrayal3D packetPortrayal = new ContinuousPortrayal3D();
	ContinuousPortrayal3D randompacketPortrayal = new ContinuousPortrayal3D();
	

	public static void main(String[] args)
	{
		new Noc3DWithUI().createController();
	}

	public Noc3DWithUI() { super(new Noc3D( System.currentTimeMillis())); }

	public Noc3DWithUI(SimState state) 
	{ 
		super(state); 
	}
	
    /** Name given to the application. This is displayed in the new simulation 
     *  window where we choose to start the application.
     * 
     * @return Name as "3D Node Elements and Paths".
     */
	public static String getName() { return "3D Node Elements and Paths"; }

	public Object getSimulationInspectedObject() { return state; }

	public void start()
	{
		super.start();
		setupPortrayals();
	}

	public void load(SimState state)
	{
		super.load(state);
		setupPortrayals();
	}

	/** Sets field portrayals and tell the portrayals what and how to portray them.
	 * 
	 */
	public void setupPortrayals()
	{

		Noc3D tut = (Noc3D) state;      
		// Tell the portrayals what to portray and how to portray them.
		pathPortrayal.setField( new SpatialNetwork3D( tut.node_elements, tut.paths ) );
		SimpleEdgePortrayal3D portrayal = new CylinderEdgePortrayal3D()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public String getLabel(sim.field.network.Edge e)
			{
				return "";
			}
		}; 

		pathPortrayal.setPortrayalForAll( portrayal );
		node_elementPortrayal.setField( tut.node_elements );
		node_unitPortrayal.setField( tut.node_units );
		packetPortrayal.setField( tut.packets );
		randompacketPortrayal.setField( tut.randompackets );

		try
		{
			node_elementPortrayal.setPortrayalForAll(new CircledPortrayal3D(
					new NodeElementPortrayal(2.0f),
					20f, true));
			node_unitPortrayal.setPortrayalForAll(new CircledPortrayal3D(
					new NodeUnitPortrayal(2.0f),
					20f, true));
			packetPortrayal.setPortrayalForAll(new CircledPortrayal3D(
					new PacketPortrayal(2.0f),
					20f, true)); 
			randompacketPortrayal.setPortrayalForAll(new CircledPortrayal3D(
					new RandomPacketPortrayal(2.0f),
					20f, true)); 
		}
		catch (Exception e) { throw new RuntimeException("yo", e); }
		display.createSceneGraph(); 
		display.reset();
	}

	public void init(Controller c)
	{
		super.init(c);

		Noc3D tut = (Noc3D) state;
		display = new Display3D(600,600,this);     // Create displayer with specific dimensions.                                 
		display.attach( pathPortrayal, "Paths" );
		display.attach( node_elementPortrayal, "NodeElements" );
		display.attach( node_unitPortrayal, "NodeUnits" );
		display.attach( packetPortrayal, "Packets" );
		display.attach( randompacketPortrayal, "RandomPackets" );
		display.translate(-tut.gridWidth/2,
				-tut.gridHeight/2,
				-tut.gridLength/2);   
		display.scale(1.0/tut.gridWidth);
		displayFrame = display.createFrame();
		displayFrame.setTitle("NodeElements and Paths");
		c.registerFrame(displayFrame);   // Register the frame so it appears in the "Display" list
		displayFrame.setVisible(true);
	}

	public void quit()
	{
		super.quit();

		if (displayFrame!=null) displayFrame.dispose();
		displayFrame = null;
		display = null;
	}

}
