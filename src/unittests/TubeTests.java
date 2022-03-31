package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Tube;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author barak epstein
 *
 */
class TubeTests {

	/**
 	* Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	*/
	@Test
	void testGetNormal()
	{
		try
		{
			// ============ Equivalence Partitions Tests ==============

			//TC01: correct result of any point on the tube
			Point p0 = new Point(1, 0, 0);// for tube's ray
			Vector dir = new Vector(0, 1, 0);// " " "
			Ray axis = new Ray(p0, dir);
			Tube t = new Tube(5, axis);
			Vector vr = t.getNormal(new Point(3, 1, 0));
			assertEquals("Incorrect normal", new Vector(1, 0, 0), vr);
			
			// =============== Boundary Values Tests ==================

			/*//TC02: point is in front of the head of the ray (creates a right angle with the tube's direction)
			Point p1 = new Point(0, 0, 0);// for tube's ray
			Vector dir1 = new Vector(0, 0, 1);// " " "
			Ray axis1 = new Ray(p1, dir1);
			Tube t1 = new Tube(1, axis1);
			vr = t1.getNormal(new Point(1, 0, 0));
			assertEquals(vr, new Vector(0, 1, 0));*/

			
			// =============== Boundary Values Tests ==================
			// Checks if the point is on the circumference of the circle, 
			//checks if the distance between the point and the center equals to the radius. 
			//If so, the point must be on the circumference of the circle.
			double lengthNormal=Math.sqrt(21);
			Vector normal=new Vector(4d/lengthNormal, 0, Math.sqrt(5)/lengthNormal);
			assertEquals("Incorrect normal", normal , t.getNormal(new Point(5,4,Math.sqrt(5))));
		}
		catch (Exception e)
		{
			fail("No need to throw any eception when no vector is vector zero");
		}
	}
}