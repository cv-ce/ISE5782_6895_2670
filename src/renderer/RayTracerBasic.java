package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;
import lighting.*;
import static primitives.Util.*;


import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

/**
 * class RayTracerBasic in package renderer
 * 
 * @author Shirel Avivi and Chaya Epstein
 */
public class RayTracerBasic extends RayTracerBase 
{
	
	/**
	 * constructor of RayTracerBasic
	 * 
	 * @author Shirel Avivi and Chaya Epstein
	 * @param myscene Scene value
	 */
	public RayTracerBasic(Scene myscene) 
	{
		super(myscene);
	}

	@Override
	public Color traceRay(Ray ray) 
	{

			GeoPoint closestPoint = findClosestIntersection(ray);
			return closestPoint == null ? myscene.background : calcColor(closestPoint, ray);
	}
	
	private Color calcColor(Point intersection) throws IllegalArgumentException 
	{
		return AmbientLight;

	}
	
	
	
}