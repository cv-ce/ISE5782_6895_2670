package renderer;

import java.util.List;

import geometries.Intersectable;
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
public class RayTracerBasic extends RayTracerBase {
	
	/**
	 * constructor of RayTracerBasic
	 * @author Shirel Avivi and Chaya Epstein
	 * @param myscene Scene value
	 */
	public RayTracerBasic(Scene myscene) 
	{
		super(myscene);
	}

	/**
	 * 
	 */
	@Override
	public Color traceRay(Ray ray) 
	{
		var intersections = myscene.geometries.findGeoIntersections(ray);
		if (intersections == null) 
			return myscene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint, ray);
	}
	
	/**
	 * returns a point's color
	 */
	private Color calcColor(Intersectable.GeoPoint point, Ray ray) {
		return myscene.ambientLight.getIntensity()
		.add(point.geometry.getEmission())
		.add(calcLocalEffects(point, ray));//we send the ray(v) for the specular light calculation -v*r
		}
	/*private Color calcColor(Point intersection) throws IllegalArgumentException 
	{
		return myscene.ambientLight.getIntensity();
	}	*/
}