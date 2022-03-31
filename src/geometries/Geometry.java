package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable{

	/**
	 * returns the vector which is the normal to the geometry in point 'p'
	 * @param p
	 * @return
	 */
	public Vector getNormal(Point p);
}
