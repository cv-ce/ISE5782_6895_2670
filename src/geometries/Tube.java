package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends Intersectable{

	final double radius;
	final Ray axis;
	
	/**
	 * tube constructor
	 */
	public Tube(double rad, Ray ray){
		this.radius=rad;
		this.axis=ray;
	}
	
	/**
	 * returns a normal to the tube in point p
	 * @param p
	 * @return
	 */
	public Vector getNormal(Point p) {
		double t = axis.getDir().dotProduct(p.subtract(axis.getP0()));
		Point o = axis.getP0().add(axis.getDir().scale(t));
		Vector vr = (p.subtract(o)).normalize();
		return vr;
	}

	/**
	 * overrides 'toString'
	 */
	@Override
	public String toString() {
		return "Tube [radius=" + radius + ", ray=" + axis + "]";
	}
	
	/**
	 * returns a tube's radius
	 */
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * returns a tube's ray
	 */
	public Ray getRay() {
		return this.axis;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
