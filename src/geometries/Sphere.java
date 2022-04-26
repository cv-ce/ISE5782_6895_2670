package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

public class Sphere extends Intersectable{

	final Point center;
	final double radius;
	
	/**
	 * sphere constructor
	 */
	public Sphere(Point p, double r) {
		center=p;
		radius=r;
	}
	
	/**
	 * returns a normal to the sphere in point p
	 * @param p
	 * @return
	 */
	public Vector getNormal(Point p) {
		Vector vr = p.subtract(center);
		return vr.normalize();
	}

	/**
	 * overrides 'toString'
	 */
	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}
	
	/**
	 * returns a sphere's center
	 */
	public Point getCenter() {
		return this.center;
	}
	
	/**
	 * returns a sphere's radius
	 */
	public double getRadius() {
		return this.radius;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * return the intersection points between a sphere and a given ray
	 
	@Override
	public List<Point> findIntersections(Ray ray) throws IllegalArgumentException
	{
		if (ray.getP0().equals(center)) // if the ray begins at the center, the point is on the radius
			return List.of(ray.getPoint(radius));
		Vector u = center.subtract(ray.getP0());
		double tM = Util.alignZero(ray.getDir().dotProduct(u));
		double d = Util.alignZero(Math.sqrt(u.length()*u.length()- tM * tM));
		double tH = Util.alignZero(Math.sqrt(radius*radius - d*d));
		double t1 = Util.alignZero(tM+tH);
		double t2 = Util.alignZero(tM-tH);
		
		if (d > radius)
			return null; // if there are no instructions
		if (t1 <= 0 && t2 <= 0)
			return null;
		if (t1 > 0 && t2 > 0)
			return List.of(ray.getPoint(t1), ray.getPoint(t2));
		if (t1 > 0)
			return List.of(ray.getPoint(t1));
		else
			return List.of(ray.getPoint(t2));	
		}
		*/
}
