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
	 * 
	 */
	private static final double DELTA = 0.1;
	
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

	/**
	 * 
	 * @param gp
	 * @param l
	 * @param n
	 * @return
	 */
	/*private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
//		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);// where we need to move the point
//		Point3D point = geopoint.point.add(delta);// moving the point
		Ray lightRay = new Ray(gp.point, lightDirection, n); // refactored ray head move
		List<GeoPoint> intersections = myscene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) 
			return true;
		double lightDistance = light.getDistance(geopoint.point);
		for (GeoPoint gp : intersections) 
		{
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 && gp.geometry.getMaterial().KT == 0)
				return false;
		}
		return true;
	}*/
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
		return calcColor(closestPoint.point/*, ray*/);
	}
	
	/**
	 * returns a point's color
	 */
	/*private Color calcColor(Intersectable.GeoPoint point, Ray ray) {
		return myscene.ambientLight.getIntensity()
		.add(point.geometry.getEmission())
		.add(calcLocalEffects(point, ray));//we send the ray(v) for the specular light calculation -v*r
		}*/
	private Color calcColor(Point intersection) throws IllegalArgumentException 
	{
		return myscene.ambientLight.getIntensity();
	}	
}