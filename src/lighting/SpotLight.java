/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * @author barak epstein
 *
 */
public class SpotLight extends PointLight {
	
	private Vector direction;
	
	
	/**
	 * ctor 
	 */
	public SpotLight(Color intensity, Point position, Vector direction)  
	{
		super(intensity, position);
		this.direction=direction.normalize();
	}
	
	/**
	 * ctor with all parameters
	 */
	public SpotLight(Color intensity, Point position, double KC, double KL, double KQ, Vector direction)  
	{
		super(intensity, position, KC, KL, KQ);
		this.direction = direction.normalize();
	}
	
	/**
	 * returns 'intensity'
	 */
	@Override
	public Color getIntensity(Point p) throws IllegalArgumentException
	{
		double pl = Util.alignZero(direction.dotProduct(getL(p)));
		if(getL(p) == null)
			return Color.BLACK;
		if (pl <= 0)
			return Color.BLACK;
		return super.getIntensity(p).scale(pl);
	}
	
}
