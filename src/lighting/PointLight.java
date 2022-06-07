/**
 * 
 */
/*package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author barak epstein
 *
 *
public class PointLight extends Light implements LightSource {
	
	private Point position;
	private double kC = 1;
	private double kL = 0;
	private double kQ = 0;
	/*SOFT SHADOW*
	private double gridSize;
	
	/**
	 * constructor
	 *
	public PointLight(Color intensity, Point p) {
		super(intensity);
		position = p;
	}
	
	/**
	 * constructor with all parameters
	 *
	public PointLight(Color intensity, Point p, double kc, double kl, double kq) {
		super(intensity);
		position = p;
		kC = kc;
		kL = kl;
		kQ = kq;
	}
	
	/**
	 * sets position, returns the object (Builder pattern)
	 *
	public PointLight setPosition(Point position) 
	{
		this.position = position;
		return this;
	}


	/**
	 * sets kC, returns the object (Builder pattern)
	 *
	public PointLight setKc(double kC) 
	{
		this.kC = kC;
		return this;
	}

	/**
	 * sets kL, returns the object (Builder pattern)
	 *
	public PointLight setKl(double kL) 
	{
		this.kL = kL;
		return this;
	}


	/**
	 *sets kQ, returns the object (Builder pattern)
	 *
	public PointLight setKq(double kQ) 
	{
		this.kQ = kQ;
		return this;
	}

	/**
	 * returns a calculated intensity according to the given formula
	 *
	@Override
	public Color getIntensity(Point p) throws IllegalArgumentException {
		return getIntensity().reduce((kC + kL * p.distance(position)+ kQ * p.distanceSquared(position)));	
	}

	/**
	 * returns the light's direction 
	 *
	@Override
	public Vector getL(Point p) throws IllegalArgumentException {
		if (p.equals(position))
			return null; //in case of vector zero - avoid exceptions
		return p.subtract(position).normalize();
	}
	
	/**
	 * returns the distance between a pointLight and a given point
	 *
	@Override
	public double getDistance(Point point) 
	{
		return position.distance(point);
	}
	
	/*SOFT SHADOW*
	@Override
	public double getGridSize() {
		return gridSize;
	}
	
	public Point getPosition() {
		return position;
	}
}
*/






/**
 * @author Noa Vales 322987801 noa4047@gmail.com
 * @author Yael Adler 322877903 yael.yula99@gmail.com
 */

package lighting;

import primitives.*;
import static primitives.Util.*;

/**
 * PointLight's class. Extends the class Light and implements the interface-class LightSource.
 * Represents the point light-source by inheriting it's "Light"'s field intensity, a position-point and the light's discount-factors Kc, Kl and Kq.
 */
public class PointLight extends Light implements LightSource {

	private Point position;
	private double gridSize;
	private double kC = 1;
	private double kL = 0;
	private double kQ = 0;
	
	
	
	/**
	 * PointLight's Constructor
	 * @param intensity the light's intensity
	 * @param position the light's position-point
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
		this.gridSize = 0;
	}
	
	
	/**
	 * PointLight's Constructor
	 * @param intensity the light's intensity
	 * @param position the light's position-point
	 * @param gridSize the light's grid's size
	 */
	public PointLight(Color intensity, Point position, double gridSize) {
		super(intensity);
		this.position = position;
		this.gridSize = gridSize;
	}

	
	
	
	/**
	 * Getter position
	 * @return the light-source's position
	 */
	public Point getPosition() {
		return position;
	}
	
	@Override
	public double getGridSize() {
		return gridSize;
	}



	/**
	 * Setter to the light's Kc discount-factor
	 * @param kC the light's Kc discount-factor
	 * @return The updated point-light
	 */
	public PointLight setKc(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * Setter to the light's Kl discount-factor
	 * @param kL the light's Kl discount-factor
	 * @return The updated point-light
	 */
	public PointLight setKl(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * Setter to the light's Kq discount-factor
	 * @param kQ the light's Kq discount-factor
	 * @return The updated point-light
	 */
	public PointLight setKq(double kQ) {
		this.kQ = kQ;
		return this;
	}
	


	@Override
	public Color getIntensity(Point p) {
		double dis=alignZero(p.distance(position));
		if (isZero(dis))
			return super.getIntensity();
		
		double denominator=alignZero(kC+kL*dis+kQ*dis*dis);
		return super.getIntensity().reduce(denominator);
	}

	@Override
	public Vector getL(Point p) {
		if (p.equals(position))
			return null;
		return p.subtract(position).normalize();
	}

	@Override
	public double getDistance(Point p) {
		return p.distance(position);
	}

	
}
