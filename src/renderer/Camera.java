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






/*package renderer;
import static primitives.Util.*;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * Class Camera creates rays from the camera towards the various geometries of the scene.
*
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
	 *
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
	 *
	public Camera setViewPlaneSize(double width, double height){
		this.width = width;
		this.height = height;
		return this;
	}
	
	
	/**
	 * Updates distance
	 * @param distance double value
	 * @return Camera	 
	 *
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
	 *
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
	 *
	public Point getP0() {
		return p0;
	}

	/**
	 * returns 'vUp'
	 * @return Vector value for vUp	 
	 *
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * returns 'vTo'
	 * @return Vector value for vTo	 
	 *
	public Vector getvTo() {
		return vTo;
	}
	
	/**
	 * returns 'vRight'
	 * @return Vector value for vRight	 
	 *
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * returns 'width'
	 * @return double value for width	 
	 *
	public double getWidth() {
		return width;
	}

	/**
	 * returns 'height'
	 * @return double value for height	 
	 *
	public double getHeight() {
		return height;
	}

	/**
	 * returns 'distance'
	 * @return double value for distance	 
	 *
	public double getDistance() {
		return distance;
	}

    /**
     * sets 'imageWriter'
     * @param i1
     * @return camera
     *
	public Camera setImageWriter(ImageWriter i1) {
		this.imageWriter = i1;
		return this;
	}
     /***
      * sets rayTracer
      * @param r1 :RayTracerBase
      * @return
      *
	public Camera setRayTracer(RayTracerBase r1) {
		this.rayTracer = r1;
		return this;
	}
	
	/**
	 * creates a picture
	 * @throws MissingResourceException
	 * @throws IllegalArgumentException
	 *
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
	 *
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
	 * *
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
	 * *
	public void writeToImage()
	{
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");
		
		imageWriter.writeToImage();
	}
}*/










/**
 * @author Noa Vales 322987801 noa4047@gmail.com
 * @author Yael Adler 322877903 yael.yula99@gmail.com
 */

package renderer;

import primitives.*;
import static primitives.Util.*;

import java.util.MissingResourceException;

/**
 * Camera's class.
 * Represents the camera by it's position-point p0 and three orthogonal direction-vectors: vTo, vUp and vRight.
 * In additon, the class holds the camera related parameters of the view-plane:
 * the view-plane's width and height, and the distance between the camera and the view-plane.
 */
public class Camera {

	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;
	
	
	
	
	////////////////////////////////////////////////////////
	private ImageWriter imageWriter;
	private RayTracerBase tracer;
	private static final String RESOURCE_ERROR = "Renderer resource not set";
	private static final String RENDER_CLASS = "Render";
	private static final String IMAGE_WRITER_COMPONENT = "Image writer";
	private static final String CAMERA_COMPONENT = "Camera";
	private static final String RAY_TRACER_COMPONENT = "Ray tracer";

	private int threadsCount = 0;
	private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean print = false; // printing progress percentage
	//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Camera's Constructor
	 * @param p0 Camera's position-point
	 * @param vTo Camera's "to"-direction
	 * @param vUp Camera's "up"-direction
	 * @throws IllegalArgumentException Given directions must be orthogonal to each other
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
		super();
		if(alignZero(vTo.dotProduct(vUp))!=0)
			throw new IllegalArgumentException ("Camera's directions: vUp and vTo, must be orthogonal");
		this.p0 = p0;
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.vRight=vTo.crossProduct(vUp).normalize();	
	}
	
	/**
	 * Getter p0
	 * @return Camera's position-point
	 */
	public Point getP0() {
		return p0;
	}
	/**
	 * Getter vTo
	 * @return Camera's "to"-direction
	 */
	public Vector getvTo() {
		return vTo;
	}
	/**
	 * Getter vUp
	 * @return Camera's "up"-direction
	 */
	public Vector getvUp() {
		return vUp;
	}
	/**
	 * Getter vRight
	 * @return Camera's "right"-direction
	 */
	public Vector getvRight() {
		return vRight;
	}
	/**
	 * Getter width
	 * @return View-Plane's width
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * Getter height
	 * @return View-Plane's height
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * Getter distance
	 * @return The distance between the camera and the view-plane
	 */
	public double getDistance() {
		return distance;
	}
	
	
	/**
	 * Setter to the view-plane's width and height
	 * @param width The view-plane's width
	 * @param height The view-plane's height
	 * @return The updated camera
	 * @throws IllegalArgumentException Width and height must be positive
	 */
	public Camera setViewPlaneSize(double width, double height) throws IllegalArgumentException {
		if(alignZero(width)<=0)
			throw new IllegalArgumentException("Width must be positive");
		if(alignZero(height)<=0)
			throw new IllegalArgumentException("Hight must be positive");
		
		this.width = width;
		this.height = height;
		return this;
	}
	
	/**
	 * Setter to the distance between the camera and the view-plane
	 * @param distance The distance between the camera and the view-plane
	 * @return The updated camera
	 * @throws IllegalArgumentException Distance must be positive
	 */
	public Camera setDistance(double distance) throws IllegalArgumentException {
		if(alignZero(distance)<=0)
			throw new IllegalArgumentException("Distance must be positive");
		
		this.distance = distance;
		return this;
	}
	
	
	
	/**
	 * Constructing a ray from the camera through the wanted pixel
	 * @param nX The number of columns in the view-plane
	 * @param nY The number of rows in the view-plane
	 * @param j The wanted pixel's column-index
	 * @param i The wanted pixel's row-index
	 * @return The constructed ray
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) throws MissingResourceException {
		if(alignZero(width)<=0 || alignZero(height)<=0 || alignZero(distance)<=0)
			throw new MissingResourceException("The view-plane's fields saved in the camera (- width, height and distance) must be updated to a positive number", "double", null);
		
		Point pCenter=this.p0.add(this.vTo.scale(this.distance));
		double rY=alignZero(this.height/nY);
		double rX=alignZero(this.width/nX);
	    double yI=alignZero((i-0.5*(nY-1))*rY);
	    double xJ=alignZero((j-0.5*(nX-1))*rX);
	    
	    Point pIJ=pCenter;
	    if (!isZero(xJ))
	    	pIJ = pIJ.add(vRight.scale(xJ));
	    if (!isZero(yI))
	    	pIJ = pIJ.add(vUp.scale(-yI));
	    
	    
	    return new Ray(this.p0, pIJ.subtract(this.p0));
	}
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @author Noa Vales 322987801 noa4047@gmail.com
	 * @author Yael Adler 322877903 yael.yula99@gmail.com
	 */




	/**
	 * Render's class.
	 * Represents the rendering of the whole image, by all of it's required components: the camera, the imageWriter and the rayTracer.
	 */

	/**
	public class Render {
		
		private Camera camera;
		private ImageWriter imageWriter;
		private RayTracerBase rayTracer;
		

		/**
		 * Setter to the render's camera
		 * @param camera The camera
		 * @return The updated render
		 
		public Render setCamera(Camera camera) {
			this.camera = camera;
			return this;
		}
		
		/**
		 * Setter to the render's imageWriter
		 * @param imageWriter The imageWriter
		 * @return The updated render
		 
		public Render setImageWriter(ImageWriter imageWriter) {
			this.imageWriter = imageWriter;
			return this;
		}
		
		/**
		 * Setter to the render's rayTracer
		 * @param rayTracer The rayTracer
		 * @return The updated render
		 
		public Render setRayTracer(RayTracerBase rayTracer) {
			this.rayTracer = rayTracer;
			return this;
		}


		/**
		 * Rendering the image - coloring each pixel by it's suitable color and getting the image by doing it
		 * @throws MissingResourceException All the render's fields mustn't be null
		 
		public void renderImage() throws MissingResourceException, IllegalArgumentException {
			renderImage(1);
		}
		
		public void renderImage(int numOfRays) throws MissingResourceException, IllegalArgumentException {
			if(this.camera==null)
				throw new MissingResourceException("All the render's fields mustn't be null, including the camera", "Camera", null);
			if(this.imageWriter==null)
				throw new MissingResourceException("All the render's fields mustn't be null, including the imageWriter", "ImageWriter", null);
			if(this.rayTracer==null)
				throw new MissingResourceException("All the render's fields mustn't be null, including the rayTracer", "RayTracerBase", null);
			if(numOfRays<=0)
				throw new IllegalArgumentException("Num of rays must be at least 1");
			
			for (int i=0; i<this.imageWriter.getNx(); i++) {
				for (int j=0; j<this.imageWriter.getNy(); j++) {
					Ray r = this.camera.constructRayThroughPixel(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
					Color col = this.rayTracer.traceRay(r, numOfRays);
					this.imageWriter.writePixel(j, i, col);
				}
			}
		}
		
		
		/**
		 * Producing a grid on top of the image, while the grid is made of equal squares
		 * @param interval The wanted interval between the grid's rows and columns
		 * @param color The color of the grid
		 * @throws MissingResourceException The render's field imageWriter mustn't be null
		 * @throws IllegalArgumentException The grid is supposed to have squares, therefore the given interval must be a divisor of both Nx and Ny
		 
		public void printGrid(int interval, Color color) throws MissingResourceException, IllegalArgumentException {
			if(this.imageWriter==null)
				throw new MissingResourceException("The render's field imageWriter mustn't be null", "ImageWriter", null);
			
			if(this.imageWriter.getNx()%interval!=0 || this.imageWriter.getNy()%interval!=0)
				throw new IllegalArgumentException("The grid is supposed to have squares, therefore the given interval must be a divisor of both Nx and Ny");
		
			for (int i=0; i<this.imageWriter.getNx(); i++)
				for (int j=0; j<this.imageWriter.getNy(); j++)
					if(i%interval==0 || (i+1)%interval==0 || j%interval==0 || (j+1)%interval==0)
						this.imageWriter.writePixel(i, j, color);

		}
		
		
		/**
		 * "Printing" the image - producing an unoptimized png file of the image
		 * @throws MissingResourceException The render's field imageWriter mustn't be null
		 
		public void writeToImage() throws MissingResourceException {
			if(this.imageWriter==null)
				throw new MissingResourceException("The render's field imageWriter mustn't be null", "ImageWriter", null);
			this.imageWriter.writeToImage();
		}
		
		
	}
	*/


	/**
	 * Renderer class is responsible for generating pixel color map from a graphic
	 * scene, using ImageWriter class
	 * 
	 * @author Dan
	 *
	 */
	
		

		/**
		 * Set multi-threading <br>
		 * - if the parameter is 0 - number of cores less 2 is taken
		 * 
		 * @param threads number of threads
		 * @return the Render object itself
		 *////////////////////////////////////////////////////////////////////////////////////////
		public Camera setMultithreading(int threads) {
			if (threads < 0)
				throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
			if (threads != 0)
				this.threadsCount = threads;
			else {
				int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
				this.threadsCount = cores <= 2 ? 1 : cores;
			}
			return this;
		}

		/**
		 * Set debug printing on
		 * 
		 * @return the Render object itself
		 */
		public Camera setDebugPrint() {
			print = true;
			return this;
		}

		/**
		 * Pixel is an internal helper class whose objects are associated with a Render
		 * object that they are generated in scope of. It is used for multithreading in
		 * the Renderer and for follow up its progress.<br/>
		 * There is a main follow up object and several secondary objects - one in each
		 * thread.
		 * 
		 * @author Dan
		 *
		 */
		private class Pixel {
			private long maxRows = 0;
			private long maxCols = 0;
			private long pixels = 0;
			public volatile int row = 0;
			public volatile int col = -1;
			private long counter = 0;
			private int percents = 0;
			private long nextCounter = 0;

			/**
			 * The constructor for initializing the main follow up Pixel object
			 * 
			 * @param maxRows the amount of pixel rows
			 * @param maxCols the amount of pixel columns
			 */
			public Pixel(int maxRows, int maxCols) {
				this.maxRows = maxRows;
				this.maxCols = maxCols;
				this.pixels = (long) maxRows * maxCols;
				this.nextCounter = this.pixels / 100;
				if (Camera.this.print)
					System.out.printf("\r %02d%%", this.percents);
			}

			/**
			 * Default constructor for secondary Pixel objects
			 */
			public Pixel() {
			}

			/**
			 * Internal function for thread-safe manipulating of main follow up Pixel object
			 * - this function is critical section for all the threads, and main Pixel
			 * object data is the shared data of this critical section.<br/>
			 * The function provides next pixel number each call.
			 * 
			 * @param target target secondary Pixel object to copy the row/column of the
			 *               next pixel
			 * @return the progress percentage for follow up: if it is 0 - nothing to print,
			 *         if it is -1 - the task is finished, any other value - the progress
			 *         percentage (only when it changes)
			 */
			private synchronized int nextP(Pixel target) {
				++col;
				++this.counter;
				if (col < this.maxCols) {
					target.row = this.row;
					target.col = this.col;
					if (Camera.this.print && this.counter == this.nextCounter) {
						++this.percents;
						this.nextCounter = this.pixels * (this.percents + 1) / 100;
						return this.percents;
					}
					return 0;
				}
				++row;
				if (row < this.maxRows) {
					col = 0;
					target.row = this.row;
					target.col = this.col;
					if (Camera.this.print && this.counter == this.nextCounter) {
						++this.percents;
						this.nextCounter = this.pixels * (this.percents + 1) / 100;
						return this.percents;
					}
					return 0;
				}
				return -1;
			}

			/**
			 * Public function for getting next pixel number into secondary Pixel object.
			 * The function prints also progress percentage in the console window.
			 * 
			 * @param target target secondary Pixel object to copy the row/column of the
			 *               next pixel
			 * @return true if the work still in progress, -1 if it's done
			 */
			public boolean nextPixel(Pixel target) {
				int percent = nextP(target);
				if (Camera.this.print && percent > 0)
					synchronized (this) {
						notifyAll();
					}
				if (percent >= 0)
					return true;
				if (Camera.this.print)
					synchronized (this) {
						notifyAll();
					}
				return false;
			}

			/**
			 * Debug print of progress percentage - must be run from the main thread
			 */
			public void print() {
				if (Camera.this.print)
					while (this.percents < 100)
						try {
							synchronized (this) {
								wait();
							}
							System.out.printf("\r %02d%%", this.percents);
							System.out.flush();
						} catch (Exception e) {
						}
			}
		}

		/**
		 * Camera setter
		 * 
		 * @param camera to set
		 * @return renderer itself - for chaining
		 */
		/*public Render setCamera(Camera camera) {
			this.camera = camera;
			return this;
		}*/

		/**
		 * Image writer setter
		 * 
		 * @param imgWriter the image writer to set
		 * @return renderer itself - for chaining
		 */
		public Camera setImageWriter(ImageWriter imgWriter) {
			this.imageWriter = imgWriter;
			return this;
		}

		/**
		 * Ray tracer setter
		 * 
		 * @param tracer to use
		 * @return renderer itself - for chaining
		 */
		public Camera setRayTracer(RayTracerBase tracer) {
			this.tracer = tracer;
			return this;
		}

		/**
		 * Produce a rendered image file
		 */
		public void writeToImage() {
			if (imageWriter == null)
				throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

			imageWriter.writeToImage();
		}

		
		/**
		 * Cast ray from camera in order to color a pixel
		 * @param nX resolution on X axis (number of pixels in row)
		 * @param nY resolution on Y axis (number of pixels in column)
		 * @param col pixel's column number (pixel index in row)
		 * @param row pixel's row number (pixel index in column)
		 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
		 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
		 */
		private void castRay(int nX, int nY, int col, int row, int numOfRays, boolean adaptiveSupersampling) {
			Ray ray =/* camera.*/constructRayThroughPixel(nX, nY, col, row);
			Color color = tracer.traceRay(ray, numOfRays, adaptiveSupersampling);
			imageWriter.writePixel(col, row, color);
		}

		/**
		 * This function renders image's pixel color map from the scene included with the Renderer object - with multi-threading
		 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
		 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
		 */
		private void renderImageThreaded(int numOfRays, boolean adaptiveSupersampling) {
			final int nX = imageWriter.getNx();
			final int nY = imageWriter.getNy();
			final Pixel thePixel = new Pixel(nY, nX);
			// Generate threads
			Thread[] threads = new Thread[threadsCount];
			for (int i = threadsCount - 1; i >= 0; --i) {
				threads[i] = new Thread(() -> {
					Pixel pixel = new Pixel();
					while (thePixel.nextPixel(pixel))
						castRay(nX, nY, pixel.col, pixel.row, numOfRays, adaptiveSupersampling);
				});
			}
			// Start threads
			for (Thread thread : threads)
				thread.start();

			// Print percents on the console
			thePixel.print();

			// Ensure all threads have finished
			for (Thread thread : threads)
				try {
					thread.join();
				} catch (Exception e) {
				}

			if (print)
				System.out.print("\r100%");
		}
		
		
		/**
		 * This function renders image's pixel color map from the scene included with the Renderer object.
		 * This is a wrapping function to the function renderImageThreaded (when using only one ray and adaptive-supersampling isn't required).
		 */
		public Camera renderImage() throws MissingResourceException {
			renderImage(1, false);
			return this;///////////////////////////////////////////////////////////
		}
		
		/**
		 * This function renders image's pixel color map from the scene included with the Renderer object,
		 * including the use in beam of rays.
		 * This is a wrapping function to the function renderImageThreaded (when adaptive-supersampling isn't required).
		 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
		 */
		public Camera renderImage(int numOfRays) throws MissingResourceException {
			renderImage(numOfRays ,false);
			return this;//////////////////////////////////////////////////////////
		}

		/**
		 * This function renders image's pixel color map from the scene included with the Renderer object,
		 * including the use in beam of rays and adaptive-supersampling if required.
		 * This is a wrapping function to the function renderImageThreaded.
		 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
		 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
		 */
		public void renderImage(int numOfRays, boolean adaptiveSupersampling) {
			if (imageWriter == null)
				throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
			/*if (camera == null)
				throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);*/
			if (tracer == null)
				throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

			final int nX = imageWriter.getNx();
			final int nY = imageWriter.getNy();
			if (threadsCount == 0)
				for (int i = 0; i < nY; ++i)
					for (int j = 0; j < nX; ++j)
						castRay(nX, nY, j, i, numOfRays, adaptiveSupersampling);
			else
				renderImageThreaded(numOfRays, adaptiveSupersampling);
		}

		/**
		 * Create a grid [over the picture] in the pixel color map. given the grid's
		 * step and color.
		 * 
		 * @param step  grid's step
		 * @param color grid's color
		 */
		public void printGrid(int step, Color color) {
			if (imageWriter == null)
				throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

			int nX = imageWriter.getNx();
			int nY = imageWriter.getNy();

			for (int i = 0; i < nY; ++i)
				for (int j = 0; j < nX; ++j)
					if (j % step == 0 || i % step == 0)
						imageWriter.writePixel(j, i, color);
		}
	}


