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
public interface LightSource {

	/**
	 * returns the intensity
	 */
	public Color getIntensity(Point p);

	/**
	 *
	 * @param p
	 * @return
	 */
	public Vector getL(Point p);
	
	/**
	 * returns the distance between a light source and a given point
	 * @param point
	 * @return
	 */
	double getDistance(Point point);

	/**
	 * Gets the light-source's grid's size ( - both width and height, to represent volume)
	 * @return the light-source's grid's size ( - both width and height)
	 */
	public double getGridSize();
}
