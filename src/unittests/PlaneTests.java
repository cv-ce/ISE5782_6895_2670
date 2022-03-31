package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author barak epstein
 *
 */
class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		// ============ Equivalence Partitions Tests ==============
		
		//TC01: correct result of any plane
		try
		{
			Plane p = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
			Vector vNorm = p.getNormal();
			Vector vCheck = new Vector(1, 1, 1);
			assertEquals(vNorm, vCheck.normalize());
		}
		catch(Exception ex)
		{
			fail("No need to any exception if the vectors aren't vector zero");
		}
        
		// =============== Boundary Values Tests ==================
		
		//TC02: 2 points merge
			/*	Plane p1 = new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 0, 1));
				assertThrows("2 points merge, getNormal should throw an exception (vector zero)",
						IllegalArgumentException.class, () -> p1.getNormal());*/	
				
				//TC03: points are on the same line
				/*Plane p2 = new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0, 0));
				assertThrows("2 points merge, getNormal should throw an exception (vector zero)",
						IllegalArgumentException.class, () -> p2.getNormal());*/
		try
		{
			Point p1=new Point(1,2,3);
			Point p2=new Point(4,5,6);
			Point p3=new Point(1,7,5);
			Plane myPlane =new Plane(p1, p2 , p3);
			double normalLength=Math.sqrt(38)*3;
			Vector normal=new Vector(-9/normalLength, -6/normalLength, 15/normalLength);
			assertEquals("Bad normal to plane", normal, myPlane.getNormal());
		
		}
		catch(Exception ex)
		{
			fail("for vectors that not zero vector does not need throw an exception");
		}
		
		}
	
	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntsersections() {
		try
		{
			Plane myPlane = new Plane(new Point(0,5,0), new Point(-5,0,0), new Point(0,0,3));
			// =============== Boundary Values Tests ==================
			
			//Ray is parallel to the plane
			// TC01: the ray included in the plane
			Ray myRay= new Ray(new Point(0,5,0), new Vector(-5,0,0));//the plane include this ray
			assertNull("A included ray has zero intersection points", myPlane.findIntersections(myRay));
			// TC02: the ray not included in the plane
			//myRay= new Ray(new Point(0,-5,0), new Vector(5,0,0));//the plane isn't included this ray
			//assertNull("A non included ray has zero intersection points", myPlane.findIntersections(myRay));
			
			//Ray is orthogonal to the plane
			// TC03:נ�‘ƒ0 before the plane
			myRay= new Ray(new Point(2,4,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertEquals("Ray is orthogonal to the plane and starts before the plane",1, myPlane.findIntersections(myRay).size());
			// TC04:נ�‘ƒ0 at the plane
			myRay= new Ray(new Point(-5,0,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertNull("Ray is orthogonal to the plane and starts at the plane", myPlane.findIntersections(myRay));
			// TC05:נ�‘ƒ0 after the plane
			myRay= new Ray(new Point(-7,2,4), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertNull("Ray is orthogonal to the plane and starts after the plane",myPlane.findIntersections(myRay));
			
			//Ray is neither orthogonal nor parallel to and begins at the plane
			// TC06:
			myRay= new Ray(new Point(-1,-1,0), new Vector(1,0,0));//the ray isn't orthogonal or parallel to the plane
			assertNull("Ray is neither orthogonal nor parallel to and begins at reference point in the plane", myPlane.findIntersections(myRay));
			
			//Ray is neither orthogonal nor parallel to the plane and begins in
			//the same point which appears as reference point in the plane
			// TC07:
			myRay= new Ray(new Point(0,0,3), new Vector(-5,4,-3));//the ray isn't orthogonal or parallel to the plane but not intersects the plane
			assertNull("Ray is neither orthogonal nor parallel to and begins at the plane", myPlane.findIntersections(myRay));
			
			// ============ Equivalence Partitions Tests ================
			
			// TC08: The Ray must be neither orthogonal nor parallel to the plane
			//Ray does not intersect the plane
			myRay= new Ray(new Point(1,2,0), new Vector(-3,-7,0));
			assertNull("Ray is neither orthogonal nor parallel but doesn't intersects the plane", myPlane.findIntersections(myRay));
			
			// TC09:
			// Ray intersects the plane
			myRay= new Ray(new Point(4,3,0), new Vector(-5.75,3.57,0));//the ray isn't orthogonal or parallel to the plane and intersects the plane
			assertEquals("Ray is neither orthogonal nor parallel and intersects the plane ",1, myPlane.findIntersections(myRay).size());
		}
		catch(Exception ex)
		{
			
		}
	}
}

