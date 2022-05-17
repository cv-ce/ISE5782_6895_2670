package geometries;

import primitives.Point;
import primitives.Color;
import primitives.Material;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

	/**
	 * returns the vector which is the normal to the geometry in point 'p'
	 * @param p
	 * @return
	 */
	public abstract Vector getNormal(Point p);
	
	protected Color emission=Color.BLACK;
	private Material material = new Material();
	
	/**
	 * returns 'material'
	 */
	public Material getMaterial() 
	{
		return material;
	}

	/**
	 * sets 'material', returns the object (Builder pattern)
	 */
	public Geometry setMaterial(Material material) 
	{
		this.material = material;
		return this;
	}

	/**
	 * sets 'emission', returns the object (Builder pattern)
	 */
	public Geometry setEmission(Color emission) 
	{
		this.emission = emission;
		return this;
	}
	
	/**
	 * returns 'material'
	 */
	public Color getEmission() 
	{
		return emission;
	}
}
