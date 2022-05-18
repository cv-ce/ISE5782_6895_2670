/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * @author barak epstein
 *
 */
public class DirectionalLight extends Light implements LightSource {
	
	private Vector direction;
	
	/**
	 * ctor
	 */
	public DirectionalLight(Color intensity, Vector direction) 
	{
		super(intensity);
		this.direction = direction.normalize();
	}

	/**
	 * returns 'intensity'
	 * @param p
	 * @return
	 */
	@Override
	public Color getIntensity(Point p)
	{
		return super.getIntensity();
	}
	
	/**
	 * returns light's direction
	 */
	@Override
	public Vector getL(Point p)
	{
		return direction;
	}

	/**
	 * 
	 */
	@Override
	public double getDistance(Point point) 
	{
		return Double.POSITIVE_INFINITY;
	}
}
