/*package renderer;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

public class Camera
{
	private Point location;
	private Vector vUp;
	private Vector vRight;
	private Vector vTo;
	private double height;
	private double width;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;
	
	/**
	 * constructor
	 *
	public Camera(Point loc, Vector vU, Vector vT) throws IllegalArgumentException {
		if(!Util.isZero(vU.dotProduct(vT)))
			throw new IllegalArgumentException();
		location = loc;
		vUp = vU.normalize();
		vTo = vT.normalize();
		vRight = vU.crossProduct(vT).normalize();
		
	}
	
	/**
	 * sets a view plane's size, returns 'this'
	 * @param width
	 * @param height
	 * @return
	 * @throws IllegalArgumentException
	 *
	public Camera setVPSize(double width, double height) throws IllegalArgumentException {
		this.height = height;
		this.width = width;
		return this;
	}
	
	/**
	 * sets a view plane - camera's distance, returns 'this'
	 *
	public Camera setVPDistance(double distance) throws IllegalArgumentException {
		this.distance = distance;
		return this;
	}
	
	/**
	 * returns a ray from the camera to pixel Pij
	 * @param nX - amount of pixels in 'x'
	 * @param nY - amount of pixels in 'y'
	 * @param j - column's index
	 * @param i - row's index
	 * @return
	 * @throws IllegalArgumentException
	 */
	/*public Ray constructRayThroughPixel(int nX, int nY, int j, int i) throws IllegalArgumentException {
		Point pCenter;
		if (Util.isZero(distance))
			pCenter = location;
		else
			pCenter = location.add(vTo.scale(distance));
		
		double Ry = height/nY;//length of a pixel
		double Rx = width/nX;//width of a pixel
		double Yi = (i-(nY-1)/2d)*Ry;
		double Xj = (j-(nX-1)/2d)*Rx;
		
		if(Util.isZero(Xj) && Util.isZero(Yi))
			return new Ray (location, pCenter.subtract(location));
		
		Point Pij = pCenter;
		
		if(!Util.isZero(Xj))
			Pij = Pij.add(vRight.scale(Xj));
		
		if(!Util.isZero(Yi))
			Pij = Pij.add(vUp.scale(-Yi));
		
		Vector Vij = Pij.subtract(location);
		
		if(Pij.equals(location))
			return new Ray(location, new Vector(Pij.getX(), Pij.getY(), Pij.getZ()));
		return new Ray(location, Vij);
	}*
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i ) {
		Point Pc;
		if (Util.isZero(distance))
			Pc=location;
		else
			Pc=location.add(vTo.scale(distance));
		
		double Ry= height/nY;
		double Rx=width/nX;
		double Yi=(i-(nY-1)/2d)*Ry;
		double Xj=(j-(nX-1)/2d)*Rx;
		
		if(Util.isZero(Xj) && Util.isZero(Yi))
			return new Ray (location, Pc.subtract(location));
		
		Point Pij = Pc;
		
		if(!Util.isZero(Xj))
			Pij = Pij.add(vRight.scale(Xj));
		
		if(!Util.isZero(Yi))
			Pij = Pij.add(vUp.scale(-Yi));
		
		Vector Vij = Pij.subtract(location);
		
		if(Pij.equals(location))
			return new Ray(location, new Vector(Pij.getX(),Pij.getY(),Pij.getZ()));
		return new Ray(location, Vij);

	}
	
	
	/**
	 * 
	 * @throws MissingResourceException
	 * @throws IllegalArgumentException
	 *
	public Camera renderImage() throws MissingResourceException, IllegalArgumentException
	{
       try 
       {
		if (imageWriter == null)
			throw new MissingResourceException("All fields must be initialized with values", "ImageWriter", "i");
	    if (rayTracer == null)
	     	throw new MissingResourceException("All fields must be initialized with values", "RayTracerBase", "r");
		if (location == null) 
	     	throw new MissingResourceException("All fields must be initialized with values", "Point", "p0");
		if (vUp == null) 
	     	throw new MissingResourceException("All fields must be initialized with values", "Vector", "vUp");
		if (vTo == null) 
	     	throw new MissingResourceException("All fields must be initialized with values", "Vector", "vTo");
		if (vRight == null) 
	     	throw new MissingResourceException("All fields must be initialized with values", "Vector", "vRight");

        int nX=imageWriter.getNx();
        int nY=imageWriter.getNy();
        
	    for (int i= 0; i< nX; i++)
		{
			for (int j = 0; j < nY; j++)	
			{
				imageWriter.writePixel(j, i, castRay(nX,nY,j,i));
		   }
			
	     }
       }
	   catch(MissingResourceException e)
       {
	    	throw new MissingResourceException("No implemented yet",e.getClassName(),e.getKey());
       }
       return this;
}
	
	/**
	* creates a colored grid
	* @param interval
	* @param color
	*
   public void printGrid(int interval, Color color)
   {
	    if (imageWriter == null)
			throw new MissingResourceException("All fields must be initialized with values", "ImageWriter", "imageWriter");
	    
	    for (int i = 0; i < imageWriter.getNx(); i++)
	    {
			for (int j = 0; j < imageWriter.getNy(); j++)	
			{
				if(i % interval == 0 || j % interval == 0)
					imageWriter.writePixel(i, j, color); 
			}
	    }

    }
   
   /**
    * creates an image
    *
   public void writeToImage()
   {   
		if (imageWriter == null)
			throw new MissingResourceException("All fields must be initialized with values", "ImageWriter", "imageWriter");
		
		imageWriter.writeToImage();
	}

	/**
	 *  
	 * @param j
	 * @param i
	 * @return
	 *
	private Color castRay(int nX, int nY, int j,int i) 
	{
		Ray ray = constructRayThroughPixel(nX, nY, j,i);
		Color color = rayTracer.traceRay(ray);
		return color;
	}
	
	/**
	 * return location
	 * @return
	 *
	public Point getLocation() {
		return location;
	}
	
	/**
	 * return vUp
	 * @return
	 *
	public Vector getVUp() {
		return vUp;
	}
	
	/**
	 * return vRight
	 * @return
	 *
	public Vector getVRight() {
		return vRight;
	}
	
	/**
	 * return vTo
	 * @return
	 *
	public Vector getVTo() {
		return vTo;
	}
	
	/**
	 * return height
	 * @return
	 *
	public double getHeight() {
		return height;
	}
	
	/**
	 * return width
	 * @return
	 *
	public double getWidth() {
		return width;
	}
	
	/**
	 * return distance
	 * @return
	 *
	public double getDistance() {
		return distance;
	}

	/**
	 * 
	 * @return
	 *
	public ImageWriter getImage() {
		return imageWriter;
	}

	/**
	 * 
	 * @param image
	 * @return
	 *
	public Camera setImageWriter(ImageWriter image) {
		this.imageWriter = image;
		return this;
	}

	/**
	 * 
	 * @return
	 *
	public RayTracerBase getRayTracer() {
		return rayTracer;
	}

	/**
	 * 
	 * @param rayTracer
	 * @return
	 *
	public Camera setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}
}*/
package renderer;
import static primitives.Util.*;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * Class Camera creates rays from the camera towards the various geometries of the scene.
*/
public class Camera
{
	private Point p0; //location of the camera
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;

	/**
	 * camera constructor
	 * @param vTo Vector value
	 * @param vUp Vector value
	 * @param p0 Point value
	 * @return Camera
	 * @throws Exception 
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
		if(!isZero(vTo.dotProduct(vUp))) // if vTo doesn't orthogonal to vUp
			throw new IllegalArgumentException("vUp doesnt ortogonal to vTo");
		
		//all the vectors should be normalized
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		vRight = (vTo.crossProduct(vUp)).normalize();
		
		this.p0 = p0;

	}
	
	/**
	 * Updates view plane's size  
	 * @param width double value
	 * @param height double value
	 * @return Camera	 
	 */
	public Camera setViewPlaneSize(double width, double height){
		this.width = width;
		this.height = height;
		return this;
	}
	
	
	/**
	 * Updates distance
	 * @param distance double value
	 * @return Camera	 
	 */
	public Camera setDistance(double distance){
		this.distance = distance;
		return this;
	}

	/**
	 * creates camera rays
	 * @param nX int value - resolution of pixel in X
	 * @param nY int value - resolution of pixel in Y
	 * @param j int value - index of column
	 * @param i int value - index of row
	 * @return Ray that created	 
	 * @throws Exception 
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i ) {
		Point Pc;
		if (isZero(distance))
			Pc=p0;
		else
			Pc=p0.add(vTo.scale(distance));
		
		double Ry= height/nY;
		double Rx=width/nX;
		double Yi=(i-(nY-1)/2d)*Ry;
		double Xj=(j-(nX-1)/2d)*Rx;
		
		if(isZero(Xj) && isZero(Yi))
			return new Ray (p0, Pc.subtract(p0));
		
		Point Pij = Pc;
		
		if(!isZero(Xj))
			Pij = Pij.add(vRight.scale(Xj));
		
		if(!isZero(Yi))
			Pij = Pij.add(vUp.scale(-Yi));
		
		Vector Vij = Pij.subtract(p0);
		
		if(Pij.equals(p0))
			return new Ray(p0, new Vector(Pij.getX(),Pij.getY(),Pij.getZ()));
		return new Ray(p0, Vij);

	}
	
	/**
	 * returns 'p0'
	 * @return Point value for p0	 
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * returns 'vUp'
	 * @return Vector value for vUp	 
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * returns 'vTo'
	 * @return Vector value for vTo	 
	 */
	public Vector getvTo() {
		return vTo;
	}
	
	/**
	 * returns 'vRight'
	 * @return Vector value for vRight	 
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * returns 'width'
	 * @return double value for width	 
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * returns 'height'
	 * @return double value for height	 
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * returns 'distance'
	 * @return double value for distance	 
	 */
	public double getDistance() {
		return distance;
	}

    /**
     * sets 'imageWriter'
     * @param i1
     * @return camera
     */
	public Camera setImageWriter(ImageWriter i1) {
		this.imageWriter = i1;
		return this;
	}
     /***
      * sets rayTracer
      * @param r1 :RayTracerBase
      * @return
      */
	public Camera setRayTracer(RayTracerBase r1) {
		this.rayTracer = r1;
		return this;
	}
	
	/**
	 * creates a picture
	 * @throws MissingResourceException
	 * @throws IllegalArgumentException
	 */
	public Camera renderImage() throws MissingResourceException, IllegalArgumentException
	{
       try 
       {
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "i");
	    if (rayTracer == null)
	     	throw new MissingResourceException("this function must have values in all the fileds", "RayTracerBase", "r");
		if (p0 == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Point", "p0");
		if (vUp == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "vUp");
		if (vTo == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "vTo");
		if (vRight == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "vRight");

        int nX=imageWriter.getNx();
        int nY=imageWriter.getNy();
        
	    for (int i= 0; i< nX; i++)
		{
			for (int j = 0; j < nY; j++)	
			{
				imageWriter.writePixel(j, i, castRay(nX,nY,j,i));
			}
	    }
       }
	   catch(MissingResourceException e)
       {
	    	throw new MissingResourceException("No implemented yet",e.getClassName(),e.getKey());
       }
       return this;
}
	  
	/**
	 * Cast ray from camera in order to color a pixel
	 * @param nX resolution on X axis (number of pixels in row)
	 * @param nY resolution on Y axis (number of pixels in column)
	 * @param j pixel's column number (pixel index in row)
	 * @param i pixel's row number (pixel index in column)
	 */
	 private Color castRay(int nX,int nY,int j,int i)
	 {
		 Ray ray = constructRayThroughPixel(nX, nY, j, i);
		 Color color=rayTracer.traceRay(ray);
		 return color;
	 }
	 
	/**
	 * creates a grid of lines
	 * @param interval int value
	 * @param color Color value
	 * */
	public void printGrid(int interval, Color color)
	{
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "i");

		for (int i1 = 0; i1 < imageWriter.getNx(); i1++)
		{
			for (int j = 0; j < imageWriter.getNy(); j++)	
			{
				if(i1 % interval == 0 || j % interval == 0)
				    imageWriter.writePixel(i1, j, color); 
			}
		}
	}
	
	/**
	 * creates an image!!!
	 * This function delegates the function of a class imageWriter
	 * */
	public void writeToImage()
	{
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");
		
		imageWriter.writeToImage();
	}
}
