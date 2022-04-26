package renderer;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

public class Camera
{
	private ImageWriter image;
	private RayTracerBase rayTracer;
	private Point location;
	private Vector vUp;
	private Vector vRight;
	private Vector vTo;
	private double height;
	private double width;
	private double distance;
	
	/**
	 * constructor
	 */
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
	 */
	public Camera setVPSize(double width, double height) throws IllegalArgumentException {
		this.height = height;
		this.width = width;
		return this;
	}
	
	/**
	 * sets a view plane - camera's distance, returns 'this'
	 */
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
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) throws IllegalArgumentException {
		//return null;
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
	}
	
	public void renderImage() throws MissingResourceException, IllegalArgumentException {
		if (image == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");
		if (rayTracer == null)
			throw new MissingResourceException("this function must have values in all the fileds", "RayTracerBase", "rayTracer");
		for (int i = 0; i < image.getNx(); i++)
		{
				for (int j = 0; j < image.getNy(); j++)	
				{
					Color rayColor = castRay(j, i);
					image.writePixel(j,i, rayColor);
					
				}
			}
		
	}
	
   private Color castRay(int j,int i) 
	
	{
				Ray ray = Camera.constructRayThroughPixel(j,i);
				Color color = rayTracer.traceRay(ray);
	}
   
   public void printGrid(int interval, Color color)
   {
	    if (image == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");
		

	    for (int i = 0; i < image.getNx(); i++)
	    {
			for (int j = 0; j < image.getNy(); j++)	
			{
				if(i % interval == 0 || j % interval == 0)
					image.writePixel(i, j, color); 
			}
	    }

    }
   
   public void writeToImage()
   {
		if (image == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");
		
		image.writeToImage();
	}

	
	/**
	 * return location
	 * @return
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * return vUp
	 * @return
	 */
	public Vector getVUp() {
		return vUp;
	}
	
	/**
	 * return vRight
	 * @return
	 */
	public Vector getVRight() {
		return vRight;
	}
	
	/**
	 * return vTo
	 * @return
	 */
	public Vector getVTo() {
		return vTo;
	}
	
	/**
	 * return height
	 * @return
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * return width
	 * @return
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * return distance
	 * @return
	 */
	public double getDistance() {
		return distance;
	}

	public ImageWriter getImage() {
		return image;
	}

	public Camera setImage(ImageWriter image) {
		this.image = image;
		return this;
	}

	public RayTracerBase getRayTracer() {
		return rayTracer;
	}

	public Camera setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}
}