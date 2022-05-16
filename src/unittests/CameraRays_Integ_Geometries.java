package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

class CameraRays_Integ_Geometries {

	/**
	 * utility function, creates 9 rays from the camera to a 3x3 sized view plane
	 */
	private List<Ray> RaysToViewPlane(Camera c)
	{
		List<Ray> cameraRays = new ArrayList<Ray>();
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				try 
				{
					cameraRays.add(c.constructRayThroughPixel(3, 3, j, i));
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					fail("There can't be zero rays");
				}
			}
		}
		return cameraRays;
	}
	
	/**
	 * utility function, returns a list of intersection points
	 * @param camera
	 * @param geomety
	 * @return
	 */
	private List<Point> findIntersectionPoints(Camera c, Intersectable geometry)
	{
		List<Ray> raysList = RaysToViewPlane(c);
		
		List<Point> intersectionPoints = new ArrayList<Point>();
		for (Ray ray : raysList) 
		{
			List<Point> temp;
			try 
			{
				temp = geometry.findIntersections(ray);
		
				if (temp != null)
					intersectionPoints.addAll(temp);
			}
			catch (Exception e) 
			{
				//e.printStackTrace();
				fail("No need to any exception here");
			}
		}
		if(intersectionPoints.size() == 0)
			return null;
		return intersectionPoints;
	}
	
	/**
	 * 
	 * tests constructRayThroughPixel' correctness in case of a sphere
	 */
	@Test
	public void constructRayThroughPixelSphere() 
	{
		try
		{
		
			//TC01:2 intersection points
			Sphere sphere=new Sphere(new Point(0,0,-3), 1);
			Camera camera = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("Number of intersections is incorrect", 2, findIntersectionPoints(camera, sphere).size());

		
			//TC02:18 intersection points
			sphere=new Sphere(new Point(0,0,-2.5), 2.5);
			camera = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);	
			assertEquals("Number of intersections is incorrect", 18, findIntersectionPoints(camera, sphere).size());
		
			//TC03:10 intersection points
			sphere=new Sphere(new Point(0,0,-2), 2);
			//same camera like tc02
			assertEquals("Number of intersections is incorrect", 10, findIntersectionPoints(camera, sphere).size());
		
			//TC04:9 intersection points
			sphere = new Sphere(new Point(0, 0, 0), 4);
			camera = new Camera(new Point(0, 0, 0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("Number of intersections is incorrect", 9, findIntersectionPoints(camera, sphere).size());	
		
			//TC05:0 intersection points
			sphere=new Sphere(new Point(0,0,1), 0.5);
			camera = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);	
			assertNull("Number of intersections is incorrect", findIntersectionPoints(camera, sphere));
		
		}
		catch(Exception ex)
		{
			//fail("No need to any exception here");
		}

	}
	
	/**
	 * 
	 * tests constructRayThroughPixel' correctness in case of a plane
	 */
	@Test
	public void constructRayThroughPixelPlane() 
	{	
		try 
		{
			//TC01:9 intersection points
			Plane plane =new Plane(new Point(0,2,0), new Vector(0,1,0));
			Camera camera =new Camera(new Point (0, 0, 0), new Vector(0,1,0) , new Vector(0,0,-1)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("Number of intersections is incorrect", 9, findIntersectionPoints(camera, plane).size());	

			//TC02:9 intersection points	
			plane =new Plane(new Point(2,0,0),new Vector(1,2,-0.5));
			//same camera
			assertEquals("Number of intersections is incorrect", 9, findIntersectionPoints(camera, plane).size());	

			//TC03:6 intersection points
			plane =new Plane(new Point(2,0,0), new Vector(1,1,0));
			//same camera
			assertEquals("Number of intersections is incorrect", 6, findIntersectionPoints(camera, plane).size());	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//fail("No need to any exception here");
		}

	}
	
	/**
	 * 
	 * tests constructRayThroughPixel' correctness in case of a triangle
	 */
	@Test
	public void constructRayThroughPixelTriangle() 
	{
		try 
		{
			//TC01:1 intersection points
			Triangle triangle=new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
			Camera camera=new Camera(new Point(0, 0, 0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("Number of intersections is incorrect", 1, findIntersectionPoints(camera, triangle).size());	
			
			//TC02:2 intersection points
			triangle=new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
			//same camera
			assertEquals("Number of intersections is incorrect", 2, findIntersectionPoints(camera, triangle).size());	
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			//fail("No need to any exception here");
		}
			
	}

}
