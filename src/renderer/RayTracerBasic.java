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
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
//		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);// where we need to move the point
//		Point point = geopoint.point.add(delta);// moving the point
		Ray lightRay = new Ray(gp.point/*, lightDirection*/, n); // refactored ray head move
		List<GeoPoint> intersections = myscene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) 
			return true;
		double lightDistance = light.getDistance(gp.point);
		for (GeoPoint geopoint : intersections) 
		{
			if (alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0 && geopoint.geometry.getMaterial().kT == new Double3(0, 0, 0))
				return false;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		}
		return true;
	}

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
	}*/	
	
	/**
     * calculate the local effects of the lights on the geometry
     * @param intersection -geopoint
     * @param ray-the ray from the camera
     * @return the local color of the geopoint
     */
    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray){

        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : myscene.lights) {
            Vector l = lightSource.getL(intersection.point).normalize();
            double nl = alignZero(n.dotProduct(l));      
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, nl, lightIntensity), 
                        calcSpecular(ks, l, n,nl, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * calculate the specular color
     * @param ks  the level of the speculation
     * @param l the vector from the light to the geometry
     * @param n normal of the geometry
     * @param nl the product between n and l
     * @param v the vector from the camera
     * @param nShininess the shininess of the geometry
     * @param lightIntensity 
     * @return specular color
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v,int nShininess, Color lightIntensity) {
        l = l.normalize();
        Vector r = l.subtract(n.scale(2*nl)).normalize();
        double d = alignZero(-v.dotProduct(r));
        if(d <= 0)
            return Color.BLACK;
        return lightIntensity.scale(ks.scale(Math.pow(d,nShininess)));
//        int specularN = 1;
//        double nl = alignZero(n.dotProduct(l));
//        Vector r=l.subtract(n.scale(nl*2));
//        double vr=Math.pow(v.scale(-1).dotProduct(r),nShininess);
//        return lightIntensity.scale(ks*Math.pow(vr,specularN));
    }
     /**
      * calculate the diffusive light
      * @param kd the level of the diffuse
      * @param nl 
      * @param lightIntensity
      * @return
      */
    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        if(nl < 0)
           nl = -nl;
        return lightIntensity.scale(kd).scale(nl);
//        double ln=alignZero(Math.abs(n.dotProduct(l)));
//        return lightIntensity.scale(kd*ln);
    }
}