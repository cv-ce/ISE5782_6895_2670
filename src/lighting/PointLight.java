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
public class PointLight extends Light implements LightSource {
	
	private Point position;
	private double kC = 1;
	private double kL = 0;
	private double kQ = 0;
	
	/**
	 * ctor
	 */
	public PointLight(Color intensity, Point p) {
		super(intensity);
		position = p;
	}
	
	/**
	 * ctor with all parameters
	 */
	public PointLight(Color intensity, Point p, double kc, double kl, double kq) {
		super(intensity);
		position = p;
		kC = kc;
		kL = kl;
		kQ = kq;
	}
	
	/**
	 * sets position, returns the object (Builder pattern)
	 */
	public PointLight setPosition(Point position) 
	{
		this.position = position;
		return this;
	}


	/**
	 * sets kC, returns the object (Builder pattern)
	 */
	public PointLight setKc(double kC) 
	{
		this.kC = kC;
		return this;
	}

	/**
	 * sets kL, returns the object (Builder pattern)
	 */
	public PointLight setKl(double kL) 
	{
		this.kL = kL;
		return this;
	}


	/**
	 *sets kQ, returns the object (Builder pattern)
	 */
	public PointLight setKq(double kQ) 
	{
		this.kQ = kQ;
		return this;
	}

	/**
	 * returns a calculated intensity according to the given formula
	 */
	@Override
	public Color getIntensity(Point p) throws IllegalArgumentException {
		return getIntensity().reduce((kC + kL * p.distance(position)+ kQ * p.distanceSquared(position)));	
	}

	/**
	 * returns the light's direction 
	 */
	@Override
	public Vector getL(Point p) throws IllegalArgumentException {
		if (p.equals(position))
			return null; //in case of vector zero - avoid exceptions
		return p.subtract(position).normalize();
	}
	
	/**
	 * 
	 */
	/*@Override
	public double getDistance(Point point) 
	{
		return position.distance(point);
	}*/
}
