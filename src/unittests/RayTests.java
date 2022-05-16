package unittests;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class RayTests 
{
	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	public void testFindClosestPoint() 
	{
		try 
		{
			// =============== Boundary Values Tests ==================
			
			Ray ray = new Ray(new Point(0,0,1), new Vector(1,0,0));
			
			Point p1 = new Point(1,0,0);
			Point p2 = new Point(2,0,0);
			Point p3 = new Point(3,0,0);

			//TC01: The first point is the closest to the beginning of the ray
			List<Point >points = List.of(p1,p2,p3);
			assertEquals("", p1, ray.findClosestPoint(points));
			
			//TC02: The last point is the closest to the beginning of the ray
			points = List.of(p2,p3,p1);
			assertEquals("", p1, ray.findClosestPoint(points));
			
			//TC03: An empty list
			points =null;
			assertEquals("", null, ray.findClosestPoint(points));
			
			
			// ============ Equivalence Partitions Tests ==============
			
			//TC04: A point in the middle of the list is the closest to the beginning of the ray
			points = List.of(p2,p1,p3);
			assertEquals("", p1, ray.findClosestPoint(points));	
		}
		catch (Exception e) {}
	}
}
