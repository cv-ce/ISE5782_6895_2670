package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Plane extends Geometry {

	final Point q0;
	final Vector normal;
	
	/**
	 * plane constructor #1
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Plane(Point p1, Point p2, Point p3){
		if (p1.equals(p2)|| p2.equals(p3)|| p3.equals(p1))//Check if there are merged points
			throw new IllegalArgumentException("Two points converge");
		q0 = p1;
		Vector v21 = p2.subtract(p1);//calculate 2 vectors of the plane
		Vector v31 = p3.subtract(p1);
		normal = v21.crossProduct(v31).normalize();
		//normal = null;
	}
	
	/**
	 * plane constructor #2
	 */
	public Plane(Point p, Vector norm){
		q0 = p;
		normal = norm.normalize();
	}
	
	/**
	 * returns a copy of 'q0'
	 */
	public Point getQ0() {
		return this.q0;
	}
	
	/**
	 * returns a copy of 'normal'
	 */
	public Vector getNormal() {
		return this.normal;
	}
	
	/**
	 * returns the normal to the plane in point p
	 * @param p
	 * @return
	 */
	public Vector getNormal(Point p) {
		return getNormal();//all points of a flat plane have the same normal!!
	}
	
	/**
	 * overrides 'toString'
	 */
	@Override
	public String toString() {
		return "Plane [q0=" + q0 + ", normal=" + normal + "]";
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * return the intersection points between a plane and a given ray
	 
	@Override
	public List<Point> findIntersections(Ray ray) throws IllegalArgumentException
	{
		double nv = normal.dotProduct(ray.getDir());
		if(Util.isZero(nv))
			return null;
		try {
			Vector v = ray.getP0().subtract(q0);//calculate vector 'Q - P'
			double t = normal.dotProduct(v) / nv;//calculate 't' according to the given instructions
			if(t <= 0)
				return null;
			Point intersectionP = ray.getPoint(t);//and then the wanted point
			List<Point> lstP = List.of(intersectionP);
			if(lstP.isEmpty())
				return null;
			return lstP;
		}
		catch(Exception ex) {
			return null;
		}
	}
	*/
}
