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
public interface LightSource {

	/**
	 * 
	 */
	public Color getIntensity(Point p);

	/**
	 * 
	 * @param p
	 * @return
	 */
	public Vector getL(Point p);
	/**
	 * 
	 * @param point
	 * @return
	 */
	double getDistance(Double3 point);
}
