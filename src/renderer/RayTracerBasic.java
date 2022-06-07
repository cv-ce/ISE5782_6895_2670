/*package renderer;
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
 *
public class RayTracerBasic extends RayTracerBase
{

	/**
	 * limits the recursion depth
	 *
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;

    /**
     * Constructor
     * @param scene that the ray cross
     *
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * find intersections of the scene geometries with the
     * received ray ad calculate the color of the intersection points
     * @param ray ray that was shot from the camera
     * @return color of the intersection point if there is otherwise color of the background
     *
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
     *
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
	 * *
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
     *
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
     *
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k)
    {

		/*𝑰𝑷 = 𝒌𝑨 ∙ 𝑰𝑨 + 𝑰𝑬 + (𝒌𝑫 ∙ |𝒍 ∙ 𝒏| + 𝒌𝑺 ∙ (−𝒗 ∙ 𝒓)^ 𝒏𝒔𝒉)) ∙ 𝑰L*
		Color Ie = gp.geometry.getEmission(); 
    	Color color = Ie.add(calcLocalEffects(gp, ray,k));
    	return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }
    
   /**
    * help function that calculate the local color
    * @param intersection GeoPoint value
    * @param ray Ray value
    *
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
    
    
    /*private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k, int numOfRays, boolean adaptiveSupersampling) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (isZero(nv))
			return Color.BLACK;
		
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		Double3 kd = material.kD, ks = material.kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : myscene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				// PICTURE IMPROVEMENT SOFT SHADOWS - use beam of rays in the function transparency:
				Double3 ktr = transparency(lightSource, l, n, intersection, numOfRays,adaptiveSupersampling);
				//if (unshaded(lightSource,l,n,intersection)) {
				if (ktr.product(new Double3(k)).lowerThan(MIN_CALC_COLOR_K) ) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(lightIntensity.scale(calcDiffusive(kd, nl)+calcSpecular(ks, l, n, nl, v, nShininess)));
					}
			}
		}
		return color;
	}*
	
	
	/**
	 * A function that calculate the globals effects of the color-reflection and transparency
	 * @param intersection GeoPoint value
	 * @param ray Ray value
	 * @param level int value
	 * @param k Double value
	 * @return Color
	 * *
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
     *
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
     *
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
      *
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
	 * *
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
	 * *
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
	 * *
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
*/


    /**
     * @author Noa Vales 322987801 noa4047@gmail.com
     * @author Yael Adler 322877903 yael.yula99@gmail.com
     */


   /* package renderer;

    import java.util.LinkedList;
    import java.util.List;
    import lighting.LightSource;
    import primitives.*;
    import scene.Scene;
    import geometries.Intersectable.GeoPoint;
    import static primitives.Util.*;



    /**
     * RayTracerBasic's class. Extends the abstract-class RayTracerBase.
     * Represents the using of the rays that was traced from the camera, integrating with the scene - when each pixel traces a single ray.
     *
    public class RayTracerBasic extends RayTracerBase {
    	
    	private static final double INITIAL_K = 1.0;
    	private static final int MAX_CALC_COLOR_LEVEL = 100;
    	private static final double MIN_CALC_COLOR_K = 0.001;

    	
    	
    	
    	/**
    	 * RayTracerBasic's Constructor
    	 * @param scene The scene
    	 *
    	public RayTracerBasic(Scene scene) {
    		super(scene);
    	}

    	
    	
    	@Override
    	public Color traceRay(Ray ray) {
    		return traceRay(ray, 1, false);
    	}

    	@Override	
    	public Color traceRay(Ray ray, int numOfRays) {
    		return traceRay(ray, numOfRays, false);
    	}
    	
    	@Override	
    	public Color traceRay(Ray ray, int numOfRays, boolean adaptiveSupersampling) {
    		GeoPoint closestPoint = findClosestIntersection(ray);
    		return closestPoint == null ? scene.background : calcColor(closestPoint, ray, numOfRays, adaptiveSupersampling);
    	}
    	
    	/**
    	 * Finds the closest intersection-geoPoint to the starting-point of the given ray.
    	 * @param ray the given constructed-ray
    	 * @return The closest intersection-geoPoint to the ray's starting-point
    	 *
    	private GeoPoint findClosestIntersection(Ray ray) {
    		List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
    		return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    	}
    	
    	
    	/**
    	 * Calculates the color of the given geoPoint that's on the given ray.
    	 * @param geoPoint the geoPoint
    	 * @param ray the constructed-ray
    	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
    	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
    	 * @return The calculated color
    	 *
    	private Color calcColor(GeoPoint geoPoint, Ray ray, int numOfRays, boolean adaptiveSupersampling) {
    		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K, numOfRays, adaptiveSupersampling)
    		.add(scene.ambientLight.getIntensity());
    		}
    	
    	/**
    	 * Calculates the color of the given geoPoint that's on the given ray, includes the claculations of the local and global effects.
    	 * @param geoPoint the geoPoint
    	 * @param ray the constructed-ray
    	 * @param level the max-level of recursion
    	 * @param k initial k for the discount-factors
    	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
    	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
    	 * @return The calculated color of the point's emission, local and global effects
    	 *
    	private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k, int numOfRays, boolean adaptiveSupersampling) {
    		Color color = geoPoint.geometry.getEmission();
    		color = color.add(calcLocalEffects(geoPoint, ray, k,numOfRays, adaptiveSupersampling));
    		return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k, numOfRays, adaptiveSupersampling));
    	}
    	
    	
    	/**
    	 * Calculates the local-effects of the scene's light-sources on the intersection-geoPoint's color.
    	 * @param intersection the intersection-geoPoint
    	 * @param ray the constructed-ray
    	 * @param k initial k for the discount-factors
    	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
    	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
    	 * @return The color of the intersection-geoPoint determined by the local-effects of the scene's light-sources
    	 *
    	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k, int numOfRays, boolean adaptiveSupersampling) {
    		Vector v = ray.getDir();
    		Vector n = intersection.geometry.getNormal(intersection.point);
    		double nv = alignZero(n.dotProduct(v));
    		if (isZero(nv))
    			return Color.BLACK;
    		
    		Material material = intersection.geometry.getMaterial();
    		int nShininess = material.nShininess;
    		Double3 kd = material.kD, ks = material.kS;
    		Color color = Color.BLACK;
    		for (LightSource lightSource : scene.lights) {
    			Vector l = lightSource.getL(intersection.point);
    			double nl = alignZero(n.dotProduct(l));
    			if (nl * nv > 0) { // sign(nl) == sign(nv)
    				// PICTURE IMPROVEMENT SOFT SHADOWS - use beam of rays in the function transparency:
    				double ktr = transparency(lightSource, l, n, intersection, numOfRays,adaptiveSupersampling);
    				//if (unshaded(lightSource,l,n,intersection)) {
    				if (ktr * k > MIN_CALC_COLOR_K) {
    					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
    					color = color.add(lightIntensity.scale(calcDiffusive(kd, nl)+calcSpecular(ks, l, n, nl, v, nShininess)));
    					}
    			}
    		}
    		return color;
    	}
    	
    	/**
    	 * Calculates the difusion-addition to the geoPoint's color, determined by one of the light-sources.
    	 * @param kd the defusion-factor of the geometry's material 
    	 * @param nl the dot-product of the intersected-geometry's normal and the light-vector of the light-source
    	 * @return The difusion-addition to the geoPoint's color
    	 *
    	private double calcDiffusive(double kd, double nl) {
    		//double nl = alignZero(n.dotProduct(l));
    		if(nl < 0)
    			nl = (-1)*nl;
    		return kd*nl;		
    	}
    	
    	/**
    	 * Calculates the specularization-addition to the geoPoint's color, determined by one of the light-sources.
    	 * @param ks the specularization-factor of the geometry's material 
    	 * @param l the light-vector of the light-source
    	 * @param n the intersected-geometry's normal
    	 * @param nl the dot-product of the intersected-geometry's normal and the light-vector of the light-source (SAVES RECALCULATING)
    	 * @param v the direction-vector of the constructed-ray
    	 * @param nShininess the specularization's-exponential-factor of the geometry's material 
    	 * @return The specularization-addition to the geoPoint's color, determined by one of the light-sources
    	 *
    	private double calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess) {
    		//double twiceNL = alignZero(n.dotProduct(l)*2);
    		//Vector r = l.subtract(n.scale(twiceNL)).normalized();
    		Vector r = l.subtract(n.scale(2*nl)).normalize();
    		double vr = alignZero(v.dotProduct(r));
    		if(vr>0)
    			return 0;
    		
    		double vrPowed = Math.pow((-1)*vr,nShininess);
    		return ks.product(new Double3(vrPowed));
    	}

    	/**
    	 * Checks wether the intersection-geoPoint is unshaded regarding one of the light-sources.
    	 * @param light the light-source
    	 * @param l the light-vector of the light-source
    	 * @param n the intersected-geometry's normal
    	 * @param geoPoint the intersection-geoPoint
    	 * @return If the intersection-geoPoint is unshaded - true, otherwise - false
    	 *
    	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
    		Vector lightDirection = l.scale(-1); // from point to light source
    		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
    		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
    		if (intersections == null) 
    			return true;
    		double lightDistance = light.getDistance(geoPoint.point);
    		for (GeoPoint gp : intersections) {
    			if(alignZero(gp.point.distance(geoPoint.point)-lightDistance)<=0 && isZero(gp.geometry.getMaterial().kT))
    				return false;
    		}
    		return true;
    	}
    	
    	/**
    	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources.
    	 * @param light the light-source
    	 * @param l the light-vector of the light-source
    	 * @param n the intersected-geometry's normal
    	 * @param geoPoint the intersection-geoPoint
    	 * @return The discount-factor of the shading on this intersection-geoPoint
    	 *
    	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
    		Vector lightDirection = l.scale(-1); // from point to light source
    		Ray lightRay = new Ray(geoPoint.point, lightDirection,n);
    		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
    		if (intersections == null)
    			return 1;
    		Double3 ktr = Double3.ONE;
    		double lightDistance = light.getDistance(geoPoint.point);
    		for (GeoPoint gp : intersections) {
    			if(alignZero(gp.point.distance(geoPoint.point)-lightDistance)<=0) {
    				ktr = ktr.product(gp.geometry.getMaterial().kT);
    				if (ktr.lowerThan(MIN_CALC_COLOR_K) )
    					return 0;
    				}
    			}
    		return ktr;
    	}
    	
    	/**
    	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources.
    	 * @param ray the light-ray from the light-source (or from a spot on it's grid)
    	 * @param n the intersected-geometry's normal
    	 * @param geoPoint the intersection-geoPoint
    	 * @return The discount-factor of the shading on this intersection-geoPoint
    	 *
    	private double transparency(Ray ray, Vector n, GeoPoint geoPoint) {
    		Vector thisRayToLightDir = ray.getDir().scale(-1); // from point to light source ( - in the fitting position in the light-source's grid)
    		Ray thisRayToLight = new Ray(geoPoint.point, thisRayToLightDir,n);
    		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(thisRayToLight);
    		if (intersections == null)
    			return 1;
    		Double3 ktr = Double3.ONE; // this ray's shading discount-factor
    		double thisRayToLightDistance = ray.getP0().distance(geoPoint.point); // distance of this ray - from the intersection-geoPoint to the current spot in the light-source's grid which is "ray"'s starting point...
    		for (GeoPoint gp : intersections) {
    			if(alignZero(gp.point.distance(geoPoint.point)-thisRayToLightDistance)<=0) {
    				ktr = ktr.product(gp.geometry.getMaterial().kT) ;
    				if (ktr.lowerThan(MIN_CALC_COLOR_K) )
    					return 0;
    			}
    		}
    		return ktr;
    	}
    	
    	

    	/**
    	 * MINI PROJECT 1 - PICTURE IMPROVEMENT SOFT SHADOWS
    	 * THE UPDATED FUNCTION TRANSPARENCY:
    	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources,
    	 * using a beam of rays to create soft shadows for picture improvement ( - getting the average discount-factor from the rays in the beam).
    	 * @param light the light-source
    	 * @param l the light-vector of the light-source
    	 * @param n the intersected-geometry's normal
    	 * @param geoPoint the intersection-geoPoint
    	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
    	 * @return The discount-factor of the shading on this intersection-geoPoint
    	 *
    	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint, int numOfRays) {
    		double lightDistance = light.getDistance(geoPoint.point);
    		// if the distance between the intersection-geoPoint and the light-source is 0 then there's no shading at all - return discount-factor=1:
    		if(isZero(lightDistance))
    			return 1;
    		
    		// construct a beam of rays from the light-source towards the intersection-geoPoint:
    		List<Ray> rays = constructBeamOfRaysUsingGrid(geoPoint.point, l, lightDistance, light.getGridSize(), numOfRays);
    		double ktrSum = 0; // sum of the rays' - from the beam of rays - shading discount-factors
    		for (Ray ray : rays)
    			// calculate this ray's discount-factors and update the sum of shading discount-factors:
    			ktrSum+=transparency(ray, n, geoPoint);
    			
    		// divide ktrSum by the overall number of rays in the beam to get an average of all discount-factors, and return it:
    		return ktrSum / rays.size();
    	}
    	
    	/**
    	 * MINI PROJECT 1 - PICTURE IMPROVEMENT SOFT SHADOWS
    	 * THE NEW FUNCTION - CONSTRUCTING THE BEAM OF RAYS:
    	 * Constructs and returns a beam of rays from the grid towards the given point.
    	 * @param point the point - where the beam of ray "points" to
    	 * @param dirToPoint the direction-vector from the grid's center point towards the point
    	 * @param distance the distance between the grid and the point
    	 * @param gridSize the grid's size ( - both width and height)
    	 * @param numOfRays the number of constructed rays wanted in the beam of rays
    	 * @return a beam of rays from the grid towards the given point
    	 *
    	private List<Ray> constructBeamOfRaysUsingGrid(Point point, Vector dirToPoint, double distance, double gridSize, int numOfRays) {
    		Point gridCenter=point.add(dirToPoint.scale(-distance));
    		List<Ray> beamOfRays = new LinkedList<Ray>();
    		// add the main-ray to the beam of rays:
    		beamOfRays.add(new Ray(gridCenter, dirToPoint));
    		// if the grid's size is 0 then only one ray can be constructed - the main-ray:
    		if(isZero(gridSize))
        		return beamOfRays;
    		
    		Vector gridVright=dirToPoint.findOrthogonal().normalize();
        	Vector gridVup=gridVright.crossProduct(dirToPoint).normalize();
        	int numRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
        	double cubeSize = gridSize/numRaysInRowCol;
        	
        	Point tempCenter = gridCenter;
        	for(int i = 0 ; i < numRaysInRowCol ; i++) {
        		double PX= alignZero((i - 0.5*(numRaysInRowCol-1))*cubeSize);
        		for(int j = 0 ; j < numRaysInRowCol ; j++){
        			if(i!=0 || j!=0){
        				double PY= alignZero((j - 0.5*(numRaysInRowCol-1))*cubeSize);
                        if(PX != 0)
                        	tempCenter = tempCenter.add(gridVright.scale(PX));
                        if(PY != 0)
                        	tempCenter = tempCenter.add(gridVup.scale(PY));
                        // construct this ray and add it to the beam of rays:
                        beamOfRays.add(new Ray(tempCenter, point.subtract(tempCenter)));
                        // reset tempCenter - the center of the current cube - to be the grid's center, to allow making the next iterations correctly:
                        tempCenter = gridCenter;
        			}
        		}    		 
        	}
        	
        	return beamOfRays;
    	}
    	
    	
    	
    	/**
    	 * MINI PROJECT 2 - PICTURE IMPROVEMENT SOFT SHADOWS AND PERFORMANCE IMPROVEMENT ADAPTIVE-SUPERSAMPLING
    	 * THE NEW "WRAPPING" FUNCTION TRANSPARENCY:
    	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources,
    	 * using a beam of rays to create soft shadows for picture improvement and adaptive-supersampling (if required) for performance improvement.
    	 * @param light the light-source
    	 * @param l the light-vector of the light-source
    	 * @param n the intersected-geometry's normal
    	 * @param geoPoint the intersection-geoPoint
    	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
    	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
    	 * @return The discount-factor of the shading on this intersection-geoPoint
    	 *
    	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint, int numOfRays, boolean adaptiveSupersampling) {
    		// if adaptive-supersampling isn't required or there is only 1 ray required or the grid's size is 0 -
    		// then use transparency from mini project 1 (without adaptive-supersampling):
    		if(!adaptiveSupersampling || numOfRays==1 || isZero(light.getGridSize()))
    			return transparency(light, l, n, geoPoint, numOfRays);
    		
    		// else - use the function that improve performance by adaptive-supersampling:
    		double lightDistance = light.getDistance(geoPoint.point);
    		Point gridCenter=geoPoint.point.add(l.scale(-lightDistance));
    		Vector gridVright=l.findOrthogonal().normalize();
        	Vector gridVup=gridVright.crossProduct(l).normalize();
        	int numRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
    		return adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter, gridVright, gridVup, light.getGridSize(), numRaysInRowCol);
    	}
    	
    	/**
    	 * MINI PROJECT 2 - PICTURE IMPROVEMENT SOFT SHADOWS AND PERFORMANCE IMPROVEMENT ADAPTIVE SUPERSAMPLING
    	 * THE NEW FUNCTION ADAPTIVESUPERSAMPLING:
    	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources,
    	 * using a beam of rays to create soft shadows for picture improvement and adaptive-supersampling for performance improvement.
    	 * @param geoPoint the intersection-geoPoint
    	 * @param n the intersected-geometry's normal
    	 * @param gridCenter the grid's center
    	 * @param gridVright the grid's "right"-direction
    	 * @param gridVup the grid's "up"-direction
    	 * @param gridSize the grid's size ( - both width and height)
    	 * @param numOfRaysLeftInRowCol how many rays should yet be traced until we get a grid with x cubes when x=the original number of rays wanted in the beam
    	 * @return The discount-factor of the shading on this intersection-geoPoint
    	 *
    	private double adaptiveSupersamplingForTransparency(GeoPoint geoPoint, Vector n, Point gridCenter, Vector gridVright, Vector gridVup, double gridSize, double numOfRaysLeftInRowCol) {

    		double halfGridSize=alignZero(gridSize/2);
    		// 4 corners of the grid:
    		Point corner1 = (gridCenter.add(gridVright.scale(halfGridSize))).add(gridVup.scale(halfGridSize));
    		Point corner2 = (gridCenter.add(gridVright.scale(-halfGridSize))).add(gridVup.scale(halfGridSize));
    		Point corner3 = (gridCenter.add(gridVright.scale(halfGridSize))).add(gridVup.scale(-halfGridSize));
    		Point corner4 = (gridCenter.add(gridVright.scale(-halfGridSize))).add(gridVup.scale(-halfGridSize));
    		// 4 rays from each of the corners towards the intersection-geoPoint: 
    		Ray ray1 = new Ray(corner1, geoPoint.point.subtract(corner1));
    		Ray ray2 = new Ray(corner2, geoPoint.point.subtract(corner2));
    		Ray ray3 = new Ray(corner3, geoPoint.point.subtract(corner3));
    		Ray ray4 = new Ray(corner4, geoPoint.point.subtract(corner4));
    		// 4 discount-factors of the intersection-geoPoint's color gotten from each of the rays:
    		double ktr1 = alignZero(transparency(ray1, n, geoPoint));
    		double ktr2 = alignZero(transparency(ray2, n, geoPoint));
    		double ktr3 = alignZero(transparency(ray3, n, geoPoint));
    		double ktr4 = alignZero(transparency(ray4, n, geoPoint));
    		
    		// if all 4 ktr are equal - no need for further recursion, return this ktr:
    		if(ktr1==ktr2 && ktr2==ktr3 && ktr3==ktr4)
    			return ktr1;
    		
    		// the numOfRaysLeftInRowCol for the grid's quarters in the next recursion level - for each grid's quarter 
    		// there'll be needed half rays in the row/column than the rays in the row/column that were needed in the original grid:
    		double nextNumOfRaysLeftInRowCol=alignZero(numOfRaysLeftInRowCol/2);
    		// if all wanted rays were traced or saved from unnecessary tracing - stop further recursions, return the average of ktrs from this level:
    		if(nextNumOfRaysLeftInRowCol<1)
    			return alignZero((ktr1+ktr2+ktr3+ktr4)/4);
    		
    		// the grid's size for the grid's quarters in the next recursion level:
    		double quarterGridSize=alignZero(gridSize/4);
    		// if the grid's size of next recursion level is too small - stop further recursions, return the average of ktrs from this level:
    		if(isZero(halfGridSize))
    			return alignZero((ktr1+ktr2+ktr3+ktr4)/4);
    		
    		Point gridCenter1 = (gridCenter.add(gridVright.scale(quarterGridSize))).add(gridVup.scale(quarterGridSize));
    		Point gridCenter2 = (gridCenter.add(gridVright.scale(-quarterGridSize))).add(gridVup.scale(quarterGridSize));
    		Point gridCenter3 = (gridCenter.add(gridVright.scale(quarterGridSize))).add(gridVup.scale(-quarterGridSize));
    		Point gridCenter4 = (gridCenter.add(gridVright.scale(-quarterGridSize))).add(gridVup.scale(-quarterGridSize));
    		
    		ktr1 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter1, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
    		ktr2 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter2, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
    		ktr3 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter3, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
    		ktr4 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter4, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
    		
    		
    		// return the average of the 4 ktrs got from the recursion steps:
    		return alignZero((ktr1+ktr2+ktr3+ktr4)/4);
    	}
    	
    	
    	
    	/**
    	 * Calculates the global-effects of the scene's light-sources on the intersection-geoPoint's color.
    	 * @param intersection the intersection-geoPoint
    	 * @param ray the constructed-ray
    	 * @param level the max-level of recursion
    	 * @param k initial k for the discount-factors
    	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
    	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
    	 * @return The color of the intersection-geoPoint determined by the global-effects of the scene's light-sources
    	 *
    	private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, double k, int numOfRays, boolean adaptiveSupersampling) {
    		Vector v = ray.getDir();
    		Vector n = intersection.geometry.getNormal(intersection.point);
    		Material material = intersection.geometry.getMaterial();
    		double kkr = k*material.kR;
    		double kkt = k*material.kT;
    		Color color = Color.BLACK;
    		
    		if (kkr > MIN_CALC_COLOR_K)
    			color = calcGlobalEffect(constructReflectedRay(intersection.point, v, n), level, material.kR, kkr, numOfRays, adaptiveSupersampling);
    		if (kkt > MIN_CALC_COLOR_K) 
    			color = color.add(calcGlobalEffect(constructRefractedRay(intersection.point, v, n), level, material.kT, kkt,numOfRays, adaptiveSupersampling));
    		
    		return  color;
    	}
    	
    	/**
    	 * The function calcGlobalEffects that allows the recursioning
    	 * @param ray the ray constructed in this recursion-stage, which starts at some geometry
    	 * @param level the max-level of recursion
    	 * @param kx the initial k for the discount-factor regarding the value of the geometry's material's feature - on which the ray starts
    	 * @param kkx the discount-factor of the color so far
    	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
    	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
    	 * @return The color of the intersection-geoPoint determined by this recursion-stage's global-effect
    	 *
    	private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx,int numOfRays, boolean adaptiveSupersampling) {
    		GeoPoint gp = findClosestIntersection(ray);
    		return (gp == null ? scene.background : calcColor(gp, ray, level-1, kkx, numOfRays, adaptiveSupersampling)).scale(kx);
    	}
    	
    	/**
    	 * Constructs a reflected ray to a former-ray which ends on the given point and it's direction is the given direction
    	 * @param point the former-ray's end point - and the reflected-ray's starting point
    	 * @param v the former-ray's direction
    	 * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
    	 * @return the constructed reflected-ray
    	 *
    	private Ray constructReflectedRay(Point point, Vector v, Vector n) {
    		double vn = alignZero(v.dotProduct(n));
    		// if v.dotProduct(n)==0 then the ray we construct is tangent to the geometry:
    		if (isZero(vn))
    			return null;
    		Vector dir = v.subtract(n.scale(2*vn));
    		return new Ray(point, dir,n);
    	}
    	
    	/**
    	 * Constructs a refracted-ray to a former-ray which ends on the given point and it's direction is the given direction
    	 * @param point the former-ray's end point - and the refracted-ray's starting point
    	 * @param v the former-ray's direction - and the refracted-ray's direction
    	 * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
    	 * @return the constructed refracted-ray
    	 *
    	private Ray constructRefractedRay(Point point, Vector v, Vector n) {
    		return new Ray(point, v, n);
    	}
    	
    }
*/














package renderer;

import static primitives.Util.alignZero;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;
import lighting.*;

/**
 * this class extends the RayTracerBase and implements the traceRay function.
 * this class in responsible to calculate the right color of the 
 * intersection point with the ray (through the pixel)
 */
public class RayTracerBasic extends RayTracerBase
{

	private static final double DELTA = 0.01;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double INITIAL_K = 1.0;
	/** SOFT SHADOW **/
	//private int NumR = 1;// number of rays for soft shadow feature
	private double SOFT_SHADOW=1;
	private static final int NUM_OF_RAYES=60;


	
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
		Point point = geopoint.point.add(delta);//add it to the point by the normal direction,
		//move the point up a little bit, to avoid case of point is inside the object 
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)//if there are no intersections the point is unshaded
			return true;

		double lightDistance = light.getDistance(geopoint.point);//the distance of the point of geoPoint from the light source
		for (GeoPoint geop : intersections) 
		{
			if(geop.geometry.getMaterial().getKT()==0.0)//only if the material of the geo is "atum"- it makes shadow
			{
				//there is something shadowing our intersection of geopoint.
				if (Util.alignZero(geop.point.distance(geopoint.point)-lightDistance) <= 0)
					return false;
			}
		}
		return true;	
	}
	private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
		if(SOFT_SHADOW == 0)
		{
			Vector lightDirection = l.scale(-1); // from point to light source
			Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
			double lightDistance = ls.getDistance(geoPoint.point); 
			var intersections = scene.geometries.findGeoIntersections(lightRay);
			if (intersections == null) return 1.0;
			
			double ktr = 1.0;//transparency initial
			
			for (GeoPoint gp : intersections) 
			{
				//if there are geometries between the point to the light, calculate the transparency 
				// to know how much shadow is on the point
				if (Util.alignZero(gp.point.distance(geoPoint.point)-lightDistance) <= 0) 
				{
					ktr *= gp.geometry.getMaterial().getKT();//add this transparency to ktr
					if (ktr < MIN_CALC_COLOR_K) //very small transparency
						return 0.0;
				}
			}
			return ktr;
		}
		else {
			
		double sum_ktr = 0;
        List<Ray> rays = constructRayBeamThroughPoint(ls, l, n, geoPoint);
        for (Ray ray : rays) 
        {
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
            if (intersections != null) 
            {
                double lightDistance = ls.getDistance(geoPoint.point);
                double ktr = 1;
                for (GeoPoint geo : intersections) 
                {
                    if (primitives.Util.alignZero(geo.point.distance(geoPoint.point) - lightDistance) <= 0) //check if the geometry is before the light source or behind
                    {
                        ktr *= geo.geometry.getMaterial().getKT();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        if (ktr < MIN_CALC_COLOR_K) 
                        {
                            ktr = 0;
                            break;
                        }
                    }
                }
                sum_ktr += ktr;
            } 
            else
                sum_ktr += 1;
          }
        return sum_ktr/rays.size();
		}
	}
	private GeoPoint findClosestIntersection(Ray ray) {
		if (ray == null) {
			return null;
		}

		GeoPoint closestPoint = null;
		double closestDistance = Double.MAX_VALUE;
		Point ray_p0 = ray.getP0();

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return null;

		for (GeoPoint geoPoint : intersections) 
		{
			double distance = ray_p0.distance(geoPoint.point);
			if (distance < closestDistance) 
			{
				closestDistance = distance;
				closestPoint = geoPoint;
			}
		}
		return closestPoint;
	}


			
	public RayTracerBasic(Scene _scene)
	{
		super(_scene);
	}

	/**
	 * @param ray
	 * @return the color of the pixel that the ray pass through it
	 */
	public Color traceRay(Ray ray)
	{
		GeoPoint pointAndGeo=findClosestIntersection(ray);
		if(pointAndGeo!=null)
			return calcColor(pointAndGeo, ray);
		else 
			return scene.background;
	}
	
	/**
	 * calculates the color of the point that the ray intersect it
	 * @param point
	 * @return the color 
	 */

	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k)
	{
		
		if(intersection==null)
			return Color.BLACK;

		Color color = intersection.geometry.getEmission();//the color of the object itself
		color = color.add(calcLocalEffects(intersection, ray, level, k));
		return 1 == level ? color : color.add((calcGlobalEffects(intersection, ray, level, k)));//reflection and refraction- may cause recursion
		
		
	}
	public Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
				.add(scene.ambientLight.getIntensity());
	}

	private Color calcGlobalEffects(GeoPoint geopoint, Ray inRay, int level, double k) 
	{
		Vector n = geopoint.geometry.getNormal(geopoint.point);

		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();

		if (level == 1 || k < MIN_CALC_COLOR_K) //stop recursion when it gets to the min limit
		{
			return color;
		}

		double kr = material.getKR(), kkr = k * kr;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (kkr > MIN_CALC_COLOR_K) //stop recursion when it gets to the min limit
		{
			Ray reflectedRay = constructReflectedRay(geopoint.point, inRay, n);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
		}
		double kt = material.getKT(), kkt = k * kt;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (kkt > MIN_CALC_COLOR_K) //stop recursion when it gets to the min limit
		{
			Ray refractedRay = constructRefractedRay(geopoint.point, inRay, n);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
		}
		return color;
	}

	
	private Ray constructRefractedRay(Point point, Ray r , Vector n) 
	{
		return new Ray(point, r.getDir(), n);
	}
	
	private Ray constructReflectedRay(Point pointGeo, Ray inRay, Vector n) 
	{
		Vector v = inRay.getDir();
		double vn = v.dotProduct(n);

		if (Util.isZero(vn)) 
		{
			return null;
		}
		Vector r = v.subtract(n.scale(2 * vn));
		return new Ray(pointGeo, r, n);
	}


	

	
	/**
	 * 
	 * @param geoPoint
	 * @param ray
	 * @return the local effects of the light sources 
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, int level, double k) 
	{
		Vector v = ray.getDir ();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) 
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().getKD(), ks = intersection.geometry.getMaterial().getKS();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) 
		{
			Vector l = lightSource.getL(intersection.point).normalize(); 
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) 
			{ 
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) 
				{	
					//it means if there is transparency and its not "atum"-
					//take it in account for calculating how much shadow.
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);//ktr=transparency- for shadow
;
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),//diffusive
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));//specular
				}
				//if not- it's "atum" and don't calculate the light
			}
		}
		return color;
	}
	
	/**
	 * 
	 * @param ks
	 * @param l
	 * @param n
	 * @param v
	 * @param nShininess
	 * @param ip
	 * @return specular component light effect at the point
	 * according to Phong model
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color ip) 
	{
		double p = Util.alignZero(nShininess);
		
		double nl = Util.alignZero(n.dotProduct(l));//the light normal
		
		Vector R = l.subtract(n.scale(2 * nl)).normalize(); // nl must not be zero
		double minusVR = -Util.alignZero(R.dotProduct(v));
		if (minusVR <= 0) {
			return Color.BLACK; // view from direction opposite to r vector
		}
		return ip.scale(ks * Math.pow(minusVR, p));
	}
	/**
	 * 
	 * @param kd
	 * @param l
	 * @param n
	 * @param ip
	 * @return diffusive component of light reflection
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color ip) 
	{
		double nl = Util.alignZero(n.dotProduct(l));//the light normal
		
		if (nl < 0) 
			nl = -nl;
		return ip.scale(nl * kd);
	}
	
	/** SOFT SHADOW **/
	/**
	 * 
	 * @param light
	 * @param l
	 * @param n
	 * @param geopoint
	 * @return this function is for the "soft shadow" feature
	 */
    public List<Ray> constructRayBeamThroughPoint(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
    	Vector centralLightDirection = l.scale(-1).normalize();	//
        Point point = geopoint.point;
        Ray centralRay = new Ray(point, centralLightDirection, n);
        List<Ray> beam = new LinkedList<Ray>();
        beam.add(centralRay);

        if(!(light instanceof PointLight)) {
            return beam;
        }

        PointLight pointLight = (PointLight) light;
        if (SOFT_SHADOW == 1) {   // if soft shadows is enabled
            Vector normalToCentralRay = new Vector(-centralLightDirection.getY(),
            		centralLightDirection.getX(), 0).normalize();
            Vector cross = normalToCentralRay.crossProduct(centralLightDirection).normalize();
            
            
            double radius = pointLight.getRadius();
            if (radius == 0d) {
                return beam;
            }
            
            Point randomPoint, position = pointLight.getPosition();
            Ray randomRay;
            Vector randomDirection;
            double rnd1, rnd2, upper = radius, lower = -radius; // upper and lower range to rand numbers in order to get random points in the circle

            for (int i = 0; i < NUM_OF_RAYES-1; i++) {
                do {
                    rnd1 = Math.random() *(upper -lower + 1) + lower;
                    rnd2 = Math.random() *(upper -lower + 1) + lower;
                } while (Math.abs(rnd1 + rnd2) > radius); // check that we don't exceed from the circle
                randomPoint = position.add(normalToCentralRay.scale(rnd1).add(cross.scale(rnd2))); // get the new point by adding the 2 vectors scaled by random numbers
                randomDirection = randomPoint.subtract(point).normalize();  // vector from the point to the new random point
                randomRay = new Ray(point, randomDirection, n); // the new ray shifted by delta from the shape
                beam.add(randomRay);    // add the ray to the list
            }
        }
        return beam;
    }
}



