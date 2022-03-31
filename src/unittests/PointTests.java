package unittests;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import primitives.Point;
import primitives.Vector;

/**
 * Test class for Point
 * @author Shirel Avivi 325112670 and Chaya Epstein
 *
 */
public class PointTests 
{

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 **/
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
				
		// =============== Boundary Values Tests ==================
		//TC: correct result of addition of any values
		try {
			Point p = new Point(0, 0, 1);
			Vector v = new Vector(1, 2, 3);
			Point pResult = p.add(v);
			assertEquals("Error. 'Add' doesn't work correctly", new Point(1, 2, 4), pResult);
		}
		catch(Exception ex) {
			// TODO Auto-generated catch block
			fail("No need to throw any exception");
		}
		
		try 
		{
			Point point = new Point(1,1,1);
			assertEquals("Error. 'Add' doesn't work correctly", point, point.add(new Vector(0, 0, 0)));
			fail("No need to throw any exception");
		} 
		catch (Exception e) {}	
	}

	/**
	 * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}.
	 */
	@Test
	public void testSubtract() 
	{
		// ============ Equivalence Partitions Tests ==============
		
		// =============== Boundary Values Tests ==================
		try 
		{
			//TC: correct result of subtraction of any values
			Point p = new Point(0, 0, 1);
			Point psub = new Point(1,2,3);
			Vector vResult = p.subtract(psub);
			assertEquals("Error. 'Subtract' doesn't work correctly", new Vector(-1, -2, -2), vResult);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			fail("No need to throw exception");
		}
		
		try 
		{
			Point point=new Point(1,1,1);
			assertEquals("Error. 'Subtract' doesn't work correctly", new Vector(1, 1, 1), point.subtract(new Point(0, 0, 0)));
		} 
		catch (Exception e) {}
	}

	/**
	 * Test method for {@link primitives.Point3D#distanceSquared(primitives.Point3D)}.
	 */
	@Test
	public void testDistanceSquared() 
	{
		// ============ Equivalence Partitions Tests ==============
		
		// =============== Boundary Values Tests ==================
		//TC: correct result of squared distance between any 2 points
		Point p = new Point(1, 2, 3);
		Point p2 = new Point(1, 2, 4);
		double distanceSquaredResult = p.distanceSquared(p2);
		assertEquals("Error. 'DistanceSquared' doesn't work correctly", 1, distanceSquaredResult, 0.0001);
	}

	/**
	 * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}.
	 */
	@Test
	public void testDistance() 
	{ 
		// ============ Equivalence Partitions Tests ==============
		
		// =============== Boundary Values Tests ==================
		//TC: correct result of distance between any 2 points
		Point p = new Point(1, 2, 3);
		Point p2 = new Point(1, 2, 4);
		double distanceResult = p.distance(p2);
		assertEquals("Error. 'Distance' doesn't work correctly", Math.sqrt(1), distanceResult, 0.0001);
	}
}

