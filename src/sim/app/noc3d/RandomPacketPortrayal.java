package sim.app.noc3d;

import javax.media.j3d.*;
import sim.portrayal3d.simple.*;
import java.awt.Color;

/** NodeUnitPortrayal class gives the physical specifications for node unit such as color,
 *  size, opacity etc.
 * 
 * @author mankas
 *
 */
public class RandomPacketPortrayal extends SpherePortrayal3D
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4069474448379075868L;

	final static Color obColor = Color.cyan;     // Assigned color for the object.
	float rpmultiply;

	public RandomPacketPortrayal( double diam )
	{
		rpmultiply = (float) diam;
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
		setScale(j3dModel, rpmultiply * (float)(((RandomPacket)obj).rpdiameter) / 2.0f);
		return super.getModel(obj, j3dModel);
	}
}  
