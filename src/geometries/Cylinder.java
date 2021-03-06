package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Cylinder extends Tube {

	final double height;
	
	/**
	 * cylinder constructor
	 * @param h
	 * @param rad
	 * @param ray
	 */
	Cylinder(double h, double rad, Ray ray){
		super(rad, ray);
		this.height=h;
	}
	
	/**
	 * returns a normal to the cylinder in point p
	 */
	public Vector getNormal(Point p) {
		return null;
	}

	/**
	 * overrides 'toString'
	 */
	@Override
	public String toString() {
		return "Cylinder [height=" + height + "" + super.toString() + "]";
	}
	
	/**
	 * returns a cylinder's height
	 */
	public double getHeight() {
		return this.height;
	}
}
