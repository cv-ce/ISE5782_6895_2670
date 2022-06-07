/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author barak epstein
 *
 */
public class DirectionalLight extends Light implements LightSource {
	
	private Vector direction;
	
	/**
	 * constructor
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
		return direction.normalize();
	}

	/**
	 * returns the distance between  a directional light and a given point (infinity)
	 */
	@Override
	public double getDistance(Point point) 
	{
		return Double.POSITIVE_INFINITY;
	}
	
	/*SOFT SHADOW*/
	@Override
	public double getGridSize() {
		return 0;
	}
}
