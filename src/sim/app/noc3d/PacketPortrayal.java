/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package sim.app.noc3d;

import javax.media.j3d.*;
//import javax.vecmath.*;
import sim.portrayal3d.simple.*;
import java.awt.Color;

/** PacketPortrayal class gives the physical specifications for Packet such as color,
 *  size, opacity etc.
 * 
 * @author mankas
 *
 */
public class PacketPortrayal extends SpherePortrayal3D
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6844068138862123337L;
	final static Color obColor = Color.red;            // Packet specifications.
	float multiply;

	public PacketPortrayal( double diam )
	{
		multiply = (float) diam;
	}

	public TransformGroup getModel(Object obj, TransformGroup j3dModel)
	{
		if (j3dModel==null )  // either the first time or when it changes
		{
			setAppearance(j3dModel, appearanceForColors(
					obColor,  // ambient color
					null,     // emissive color (black)
					obColor,  // diffuse color
					null,     // specular color (white)
					1.0f,     // no shininess
					1.0f));   // full opacity
		}
		setScale(j3dModel, multiply * (float)(((Packet)obj).rdiameter) / 2.0f);
		return super.getModel(obj, j3dModel);
	}
} 
