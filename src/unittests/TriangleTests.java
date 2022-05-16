package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *   Unit tests for geometries.Triangle class
 *   @author Shirel Avivi 325112670 and Chaya Epstein
 */
public class TriangleTests
{

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() 
	{
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		try
		{
			Triangle myTriangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
			double sqrt3 = Math.sqrt(1d / 3);
			Vector normal=new Vector(sqrt3, sqrt3, sqrt3);
		    assertEquals("Bad normal to trinagle", normal , myTriangle.getNormal(new Point(0, 0, 1)));
		}
		catch(Exception ex) 
		{
			fail("No need to any exception when no vector is vector zero");
		}
	}
	
	 /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Point3D)}.
     */
    @Test
    public void testFindIntersections() 
    {
        try
        {
        	Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(2, 6, 0), new Point(5, 0, 0));
            
            // ============ Equivalence Partitions Tests ====================

            // TC01: The ray cuts the plane In front of the side of the triangle
            Ray ray = new Ray(new Point(6.94, -2.39, 0), new Vector(-2.68, 5.72, 0));
            assertNull("Intersection point is out of the triangle - need 0 intersections", triangle.findIntersections(ray));

            // TC02: The ray intersects the plane on which the triangle is in front of the vertex
            ray = new Ray(new Point(-0.93, 6.2, 0), new Vector(2.54, 2.23, 0));
            assertNull("Intersection point is out of the triangle - need 0 intersections", triangle.findIntersections(ray));

            // TC03: The ray cuts the plane within the boundaries of the triangle
            ray = new Ray(new Point(-2.09, 2.69, 2.3), new Vector(4.09, -0.69, -2.3));
            assertEquals("Intersection point is in the triangle - need 1 intersections", 1, triangle.findIntersections(ray).size());
            
            // =============== Boundary Values Tests ==================

            // TC11: The ray intersects on the side of the triangle
            ray = new Ray(new Point(4.26, -1.28, 2.14), new Vector(-0.15, 3.07, -2.14));
            assertNull("On the side - need 0 intersections", triangle.findIntersections(ray));
            
            // TC12: The ray intersects on one of the vertices of the triangle
            ray = new Ray(new Point(3.7, -0.71, 1.44), new Vector(1.3, 0.71, -1.44));
            assertNull("On the vertex - need 0 intersections", triangle.findIntersections(ray));
            
            // TC13: The ray On the straight line continuing the side of the triangle
            ray = new Ray(new Point(3.86, -4.95, 0), new Vector(2.97, 1.28, 0));
            assertNull("Intersection point is out of the triangle - need 0 intersections", triangle.findIntersections(ray));
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        //	fail("No need to any exception!");
        }
    }
}

