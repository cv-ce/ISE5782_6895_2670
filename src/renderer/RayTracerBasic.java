package renderer;
import static primitives.Util.*;
import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase
{

	/**
	 * limits the recursion depth
	 */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;

    /**
     * Constructor
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * find intersections of the scene geometries with the
     * received ray ad calculate the color of the intersection points
     * @param ray ray that was shot from the camera
     * @return color of the intersection point if there is otherwise color of the background
     */
    public Color traceRay(Ray ray) 
    {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? myscene.background : calcColor(closestPoint, ray);
    }
    
    private static final double DELTA = 0.1;

    /**
     * 
     * @param l
     * @param n
     * @param geopoint
     * @param ls
     * @return
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint geopoint, LightSource ls)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = geopoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = myscene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;

        double lightDistance = ls.getDistance(geopoint.point);
        for (GeoPoint geop : intersections) {
            if (alignZero(geop.point.distance(geopoint.point) - lightDistance) <= 0)
                if(geopoint.geometry.getMaterial().kT.equals(Double3.ZERO))
                    return false;
        }
        return true; //in case all intersections are in between lightDistance and gp.
    }

	/**
	 * A function that find the most closet point to the ray
	 * @param ray Ray value
	 * @return the closet point
	 * */
	private GeoPoint findClosestIntersection(Ray ray)
	{
		List<GeoPoint> intersections = myscene.geometries.findGeoIntersections(ray);
		if(intersections == null)
			return  null;
		return ray.findClosestGeoPoint(intersections);
	}
	
    /**
     * calculates the color of a geopoint 
     * @param gp geopoint
     * @param ray 
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray) 
    {
    	return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K))
    			.add(myscene.ambientLight.getIntensity());
    }
    
    /**
     * calcualulate the color with all its parameters
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k)
    {

		/*𝑰𝑷 = 𝒌𝑨 ∙ 𝑰𝑨 + 𝑰𝑬 + (𝒌𝑫 ∙ |𝒍 ∙ 𝒏| + 𝒌𝑺 ∙ (−𝒗 ∙ 𝒓)^ 𝒏𝒔𝒉)) ∙ 𝑰L*/
		Color Ie = gp.geometry.getEmission(); 
    	Color color = Ie.add(calcLocalEffects(gp, ray,k));
    	return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }
    
   /**
    * help function that calculate the local color
    * @param intersection GeoPoint value
    * @param ray Ray value
    */  
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) 
	{
		Vector v = ray.getDir().normalize();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) 
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : myscene.lights) 
		{
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) //if they have the same direction
			{ 
				Double3 ktr = transparency(lightSource, l, n, intersection);
				if (!ktr.product(k).lowerThan( MIN_CALC_COLOR_K) )//if (ktr * k > MIN_CALC_COLOR_K) 
				{
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					 color = color.add(calcDiffusive(kd, nl, lightIntensity),
		                        calcSpecular(ks, l, n,nl, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}
	
	/**
	 * A function that calculate the globals effects of the color-reflection and transparency
	 * @param intersection GeoPoint value
	 * @param ray Ray value
	 * @param level int value
	 * @param k Double value
	 * @return Color
	 * */
    private Color calcGlobalEffects(GeoPoint gp,Ray v, int level, Double3 k)
    {
    	Color color = Color.BLACK;
    	Vector n = gp.geometry.getNormal(gp.point);
    	Material material = gp.geometry.getMaterial();
    	Double3 kkr = material.kR.product(k);
    	if (!kkr.lowerThan( MIN_CALC_COLOR_K))//if (kkr > MIN_CALC_COLOR_K)
    	color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
    	Double3 kkt = material.kT.product(k);
    	if (!kkt.lowerThan( MIN_CALC_COLOR_K))//if (kkt > MIN_CALC_COLOR_K)
    	color = color.add(
    	calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
    	return color;
    }
    
    /**
     * 
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) 
    {
    	GeoPoint gp = findClosestIntersection (ray);
    	return (gp == null ? myscene.background : calcColor(gp, ray, level-1, kkx)
    	.scale(kx));
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
    }
       
	/**
	 * A function that calculates the refracted rays.
	 * @param normal Vector value
	 * @param point Point value
	 * @param ray Ray value
	 * @return ray for refracted
	 * */
	private Ray constructRefractedRay( Point point, Ray ray,Vector normal)
	{
		Vector v = ray.getDir();
		return new Ray(point, v ,normal);
	}	
	
	/**
	 * A function that calculates the reflected rays.
	 * @param normal Vector value
	 * @param point Point value
	 * @param ray Ray value
	 * @return ray for reflected
	 * */
	private Ray constructReflectedRay( Point point, Ray ray,Vector normal) 
	{
		// 𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ n
		Vector v = ray.getDir();
		double nv = alignZero(normal.dotProduct(v));
		if (isZero(nv))
			return null;
		Vector r = v.subtract(normal.scale(nv*2));
		return new Ray(point, r, normal);
	}

	/**
	 * A function that allows partial shading
	 * @param light LightSource value
	 * @param l Vector value
	 * @param n Vector value
	 * @param geoPoint GeoPoint value
	 * */
	private Double3 transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move

		double lightDistance = light.getDistance(geoPoint.point);
		var intersections = myscene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) 
			return Double3.ONE;
		Double3 ktr =Double3.ONE ;
		for (GeoPoint gp : intersections) 
		{
			if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0)
			{
				ktr = gp.geometry.getMaterial().kT.product(ktr);
				if (ktr.lowerThan(MIN_CALC_COLOR_K))
					return Double3.ZERO;
			}
		}
		return ktr;
	}
}