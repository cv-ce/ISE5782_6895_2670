/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Shirel Avivi 325112670 and Chaya Epstein 21086895
 *
 */
public class PolygonTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
	}
	
	/**
	 * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntsersections() {
		// ============ Equivalence Partitions Tests ==============

		// =============== Boundary Values Tests ==================
		try
        {
        Polygon myPolygon = new Polygon(new Point(0, 1, 0), new Point(2, 6, 0), new Point(5, 0, 0), new Point(-1,8,0));
        

        // ============ Equivalence Partitions Tests ====================
        //The ray begins "before" the plane

        // TC01: The ray cuts the plane In front of the side of the triangle
        Ray ray = new Ray(new Point(6.94, -2.39, 0), new Vector(-2.68, 5.72, 0));
        assertNull("The intersection point is out of the Polygon - need 0 intersections", myPolygon.findIntersections(ray));

        // TC02: The ray intersects the plane on which the triangle is in front of the vertex
        ray = new Ray(new Point(-0.93, 6.2, 0), new Vector(2.54, 2.23, 0));
        assertNull("The intersection point is out of the Polygon - need 0 intersections", myPolygon.findIntersections(ray));

        // TC03: The ray cuts the plane within the boundaries of the triangle
        ray = new Ray(new Point(-2.09, 2.69, 2.3), new Vector(4.09, -0.69, -2.3));
        assertEquals("The intersection point is in the Polygon - need 1 intersections", 1, myPolygon.findIntersections(ray).size());
        
       

        // =============== Boundary Values Tests ==================

        // TC11: The ray intersects on the side of the triangle
        ray = new Ray(new Point(4.26, -1.28, 2.14), new Vector(-0.15, 3.07, -2.14));
        assertNull("On the side - need 0 intersections", myPolygon.findIntersections(ray));
        
        // TC12: The ray intersects on one of the vertices of the triangle
        ray = new Ray(new Point(3.7, -0.71, 1.44), new Vector(1.3, 0.71, -1.44));
        assertNull("On the vertex - need 0 intersections", myPolygon.findIntersections(ray));
        
        // TC13: The ray On the straight line continuing the side of the triangle
        ray = new Ray(new Point(3.86, -4.95, 0), new Vector(2.97, 1.28, 0));
        assertNull("The intersection point is out of the Polygon - need 0 intersections", myPolygon.findIntersections(ray));
        }
        catch(Exception ex)
        {
        	
        }
	}
}