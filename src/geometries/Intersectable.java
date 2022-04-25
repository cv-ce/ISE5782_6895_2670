/**
 * 
 */
package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * @author barak epstein
 *
 */
public abstract class Intersectable {
	
	/**
	 * returns intersections between 'ray' and a geometry
	 * @param ray
	 * @return
	 */
	public List<Point> findIntersections(Ray ray);
}