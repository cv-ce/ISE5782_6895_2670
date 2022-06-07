/*package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * class RayTracerBase of renderer package
 * 
 * @author Shirel Avivi 325112670 and Chaya Epstein
 *
public abstract class RayTracerBase 
{

	protected Scene myscene;
	
	/**
	 * constructor 
	 * @author Shirel Avivi 325112670 and Chaya Epstein
	 * @param myscene Scene value	
	 *
	public  RayTracerBase(Scene myscene)
	{
		this.myscene=myscene;
	}

	/**
	 * Statement of an abstract function that calculates the color for the nearest intersection point, 
	 * if no intersection points are returned - return the color of the background	
	 * @author Shirel Avivi 325112670 and Chaya Epstein
	 * @param ray Ray value
	 * @throws Exception
	 * @return Color
	 *  *
	public abstract Color traceRay(Ray ray) throws IllegalArgumentException ;
}*/



/**
 * @author Noa Vales 322987801 noa4047@gmail.com
 * @author Yael Adler 322877903 yael.yula99@gmail.com
 */


package renderer;

import primitives.*;
import scene.*;


/**
 * RayTracerBase's abstract-class.
 * Represents the using of the rays that was traced from the camera, integrating with the scene.
 *
 */
public abstract class RayTracerBase {
	
	protected Scene scene;

	
	/**
	 * RayTracerBase's Constructor
	 * @param scene The scene
	 */
	public RayTracerBase(Scene scene) {
		super();
		this.scene = scene;
	}
	
	/**
	 * Calculating the color of the starting-point by the given ray constructed from it and it's intersections with the scene (when using only one ray)
	 * @param ray The ray that was constructed - it's starting-point is the point we want to color
	 * @return The color of the starting-point of this ray
	 */
	public abstract Color traceRay(Ray ray);
	
	
	/**
	 * Calculating the color of the starting-point by the given main-ray constructed from it, a beam of rays
	 * sorrunding this ray and the beam's intersections with the scene
	 * @param ray The ray that was constructed - it's starting-point is the point we want to color
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @return The color of the starting-point of the given main-ray
	 */
	public abstract Color traceRay(Ray ray, int numOfRays);

	
	/**
	 * Calculating the color of the starting-point by the given main-ray constructed from it, a beam of rays
	 * sorrunding this ray and the beam's intersections with the scene.
	 * Includes adaptive-supersampling for performance improvement, if required.
	 * @param ray The ray that was constructed - it's starting-point is the point we want to color
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The color of the starting-point of the given main-ray
	 */
	public abstract Color traceRay(Ray ray, int numOfRays, boolean adaptiveSupersampling);

}
