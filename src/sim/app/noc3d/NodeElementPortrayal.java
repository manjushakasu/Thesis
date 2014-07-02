/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package sim.app.noc3d;

import javax.media.j3d.*;
import sim.portrayal3d.simple.*;
import java.awt.Color;

/** NodeElementPortrayal class gives the physical specifications for node element such as color,
 *  size, opacity etc.
 * 
 * @author mankas
 *
 */
public class NodeElementPortrayal extends SpherePortrayal3D
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6484626656596857245L;
	final static Color obColor = Color.green;        // Color assigned to the node element.
	float multiply;

	public NodeElementPortrayal( double diam )
	{
		multiply = (float) diam;
	}

	public TransformGroup getModel(Object obj, TransformGroup j3dModel)
	{             
		if (j3dModel==null )  // Either the first time or when it changes
		{
			setAppearance(j3dModel, appearanceForColors(
					obColor,  // ambient color
					null,     // emissive color (black)
					obColor,  // diffuse color
					null,     // specular color (white)
					1.0f,     // no shininess
					1.0f));   // full opacity
		}
		setScale(j3dModel, multiply * (float)(((NodeElement)obj).diameter) / 2.0f);
		return super.getModel(obj, j3dModel);
	}
}
