package unittests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author barak epstein
 *
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============

		//TC01: correct result of any point on the sphere
		Point p = new Point(1, 0, 0);
		double r = 1;
		Sphere s = new Sphere(p, r);
		Vector vr = s.getNormal(new Point(2, 0, 0));
		Vector norm = new Vector(2, 0, 0);
		assertEquals(vr, norm.normalize());

        // =============== Boundary Values Tests ==================
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntsersections() {
		try {
			Sphere sphere = new Sphere(new Point (1, 0, 0), 1d);

			// ============ Equivalence Partitions Tests ==============

			// TC01: Ray's line is outside the sphere (0 points)
			assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
					"Ray's line out of sphere");

			// TC02: Ray starts before and crosses the sphere (2 points)
			Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
			Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
			List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                                                           new Vector(3, 1, 0)));
			assertEquals(2, result.size(), "Wrong number of points");
			if (result.get(0).getX() > result.get(1).getX())
				result = List.of(result.get(1), result.get(0));
			assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

			// TC03: Ray starts inside the sphere (1 point)
			Ray r = new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0));
			//Point p = new Point(1, 0, 0);
			List<Point> result1 = sphere.findIntersections(r);
			assertEquals(result1.size(), 1, "One intersection");
   
			// TC04: Ray starts after the sphere (0 points)
			assertNull("The ray starts after the sphere", sphere.findIntersections(new Ray(new Point(4, 10, 0),new Vector(1, 2, 0))));

			// =============== Boundary Values Tests ==================

			// **** Group: Ray's line crosses the sphere (but not the center)
			// TC11: Ray starts at sphere and goes inside (1 points)
			result = sphere.findIntersections(new Ray(new Point(2, 0, 0),new Vector(-1, 0, 1)));
			assertEquals("Wrong number of points", 1, result.size());
   
			// TC12: Ray starts at sphere and goes outside (0 points)
			assertNull("The ray starts at sphere and goes outside", sphere.findIntersections(new Ray(new Point(3, 0, 0),new Vector(1, 0, 0))));
   		

			// **** Group: Ray's line goes through the center
			// TC13: Ray starts before the sphere (2 points)
			result = sphere.findIntersections(new Ray(new Point(1, -2, 0),new Vector(0, 1, 0)));
			assertEquals("Wrong number of points", 2, result.size());
			if (result.get(0).getX() > result.get(1).getX())
				result = List.of(result.get(1), result.get(0));
			assertEquals("Ray crosses sphere", List.of(new Point(1, 1, 0), new Point(1, -1, 0)), result);/////??????????
   
			// TC14: Ray starts at sphere and goes inside (1 points)
			result = sphere.findIntersections(new Ray(new Point(1, -1, 0),new Vector(0, 1, 0)));
			assertEquals("Wrong number of points", 1, result.size());
   
			// TC15: Ray starts inside (1 points)
			result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0),new Vector(4, 0, 0)));
			assertEquals("Wrong number of points", 1, result.size());
   
			// TC16: Ray starts at the center (1 points)
			result = sphere.findIntersections(new Ray(new Point(1, 0, 0),new Vector(2.52,-5.02, 0)));
			assertEquals("Wrong number of points", 1, result.size());
   
			// TC17: Ray starts at sphere and goes outside (0 points)
			result = sphere.findIntersections(new Ray(new Point(2, 0, 0),new Vector(1, 0, 0)));
			assertNull("Wrong number of points", result);
			assertNull("The ray starts at sphere and goes outside", sphere.findIntersections(new Ray(new Point(1, 1, 0),new Vector(0, 1, 0))));
			
			// TC18: Ray starts after sphere (0 points)	        
			assertNull("The ray starts  after sphere", sphere.findIntersections(new Ray(new Point(1, 2, 0),new Vector(0, 1, 0))));
   
			// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
			// TC19: Ray starts before the tangent point
			assertNull("The ray starts before the tangent point", sphere.findIntersections(new Ray(new Point(-0.5,-0.5, 0),new Vector(-2, -1, 0))));
   
			// TC20: Ray starts at the tangent point
			assertNull("The ray starts at the tangent point", sphere.findIntersections(new Ray(new Point(1, 0, 1),new Vector(-1,-1, 0))));
   
			// TC21: Ray starts after the tangent point
			assertNull("The ray starts after the tangent point", sphere.findIntersections(new Ray(new Point(1, 1, 1),new Vector(0, -2, 1))));
   
			// **** Group: Special cases
			// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
			assertNull("The ray's line is outside, ray is orthogonal to ray start to sphere's center line", sphere.findIntersections(new Ray(new Point(-0.5, 0, 0),new Vector(0, -2, 1))));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			//fail("not need throws exception!");
		}
	}
}

