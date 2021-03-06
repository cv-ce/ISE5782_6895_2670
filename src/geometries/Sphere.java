package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

public class Sphere extends Geometry {

	final Point center;
	final double radius;
	
	/**
	 * sphere constructor
	 * @param p
	 * @param r
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
		return (p.subtract(center)).normalize();
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

	/**
	 * returns 'GeoPoint' intersection points between a sphere and a given ray
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)throws IllegalArgumentException  {
		/*if (ray.getP0().equals(center)) // if the beginning of the ray is the sphere's center, the point is on the radius
			return List.of(new GeoPoint(this,ray.getPoint(radius)));
		//List<Point3D> rayPoints = new ArrayList<Point3D>();
		Vector u = center.subtract(ray.getP0());
		double tM = Util.alignZero(ray.getDir().dotProduct(u));
		double d = Util.alignZero(Math.sqrt(u.length()*u.length()- tM * tM));
		double tH = Util.alignZero(Math.sqrt(radius*radius - d*d));
		double t1 = Util.alignZero(tM+tH);
		double t2 = Util.alignZero(tM-tH);
		
		if (d > radius)
			return null;//no intersections

		if (t1 <=0 && t2<=0)
			return null;
		
		if (t1 > 0 && t2 >0)
			return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
		if (t1 > 0)
		{
			return List.of(new GeoPoint(this,ray.getPoint(t1)));
		}
		else
			return List.of(new GeoPoint(this,ray.getPoint(t2)));*/
		
		
		try	{
			Vector u=center.subtract(ray.getP0());
			double tm=Util.alignZero(ray.getDir().dotProduct(u));
			double dSquared=Util.alignZero(u.dotProduct(u)-tm*tm);
			if(Util.alignZero(radius*radius-dSquared)<=0)
				return null;
			double th=Util.alignZero(Math.sqrt(radius*radius-dSquared));
			double t1=Util.alignZero(tm-th);
			double t2=Util.alignZero(tm+th);
			if(t1>0) {
				if(t2>0)
					return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
				else
					return List.of(new GeoPoint(this,ray.getPoint(t1)));	
			}
			else {
				if(t2>0)
					return List.of(new GeoPoint(this,ray.getPoint(t2)));
				else
					return null;				
			}
		}
		//if got here - it means that the ray starts on the sphere's center,
		//so the only intersection is ray.p0 + sphere.radius*ray.dir :
		catch(IllegalArgumentException iae) {
			return List.of(new GeoPoint(this,ray.getPoint(radius)));
		}

		
		
		
	}

	/**
	 * return the intersection points between a sphere and a given ray
	 */
	/*@Override
	public List<Point> findIntersections(Ray ray) throws IllegalArgumentException
	{
		if (ray.getP0().equals(center)) // if the begin of the ray in the center, the point, is on the radius
			return List.of(ray.getPoint(radius));
	    Vector u = center.subtract(ray.getP0());
	    double tM = Util.alignZero(ray.getDir().dotProduct(u));
	    double d = Util.alignZero(Math.sqrt(u.length()*u.length()- tM * tM));
	    double tH = Util.alignZero(Math.sqrt(radius*radius - d*d));
	    double t1 = Util.alignZero(tM+tH);
	    double t2 = Util.alignZero(tM-tH);
	    if (d > radius)
	        return null; // there are no intersections
	    if (t1 <=0 && t2<=0)
	        return null;	       
	    if (t1 > 0 && t2 >0)
	    	return List.of(ray.getPoint(t1),ray.getPoint(t2));
	    if (t1 > 0)
	    	return List.of(ray.getPoint(t1));
	    else
	    	return List.of(ray.getPoint(t2));    
		}*/
}
