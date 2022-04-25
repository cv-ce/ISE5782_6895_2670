package geometries;

import primitives.Point;
import primitives.Color;
import primitives.Vector;

public abstract class Geometry extends Intersectable{

	/**
	 * returns the vector which is the normal to the geometry in point 'p'
	 * @param p
	 * @return
	 */
	public abstract Vector getNormal(Point p);
	/**
	 * @param emission
	 * @return Color 
	 */
	protected Color emission=Color.BLACK;
	/**
	 * getter function for the Material filed in geometry class
	 * 
	 * @author Shirel Avivi 325112670and Chaya Epstein
	 * @return the material
	 */
	public Color getEmission()
	{
		return emission;
	}
	/**
	 * setter function for the color filed and return this- geometry class
	 * 
	 * @author Shirel Avivi 325112670and Chaya Epstein
	 * @return the geometry-this
	 * */
	public Geometry setEmission(Color emission) 
	{
		this.emission = emission;
		return this;
	}
}
