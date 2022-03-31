/**
 * 
 */
package unittests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**
 * @author barak epstein
 *
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

		//TC01: orthogonal vectors
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.add(v2);
		assertEquals(vr, new Vector(1, 5, 1));
		
		//TC02: vectors on the same line
		v2 = new Vector(2, 4, 6);
		vr = v1.add(v2);
		assertEquals(vr, new Vector(3, 6, 9));
		
		//TC03: opposite directed vectors on the same line
		v2 = new Vector(-2, -4, -6);
		vr = v1.add(v2);
		assertEquals(vr, new Vector(-1, -2, -3));
		
		//TC04: opposite directed same vectors on the same line
		Vector v = new Vector(-1, -2, -3);
		assertThrows("addition of 2 opposite same vectors should throw an exception (vector zero)",
				IllegalArgumentException.class, () -> v1.add(v));		
		}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		
        // =============== Boundary Values Tests ==================	
		
		//TC01: scale by 0 - ERROR (vector zero)
		assertThrows("scale by 0 - ERROR (vector zero)",
				IllegalArgumentException.class, () -> v.scale(0));
		
		//TC02: scale by 1 - no change
		Vector vr = v.scale(1);
		assertEquals(vr, new Vector(1, 2, 3));
		
		//TC03: scale by -1 - change direction
		vr = v.scale(-1);
		assertEquals(vr, new Vector(-1, -2, -3));
		
		//TC04: scale by positive number - coordinates change, no direction change
		vr = v.scale(3.5);
		assertEquals(vr, new Vector(3.5, 7, 10.5));
		
		//TC05: scale by negative number - coordinates and direction change
		vr = v.scale(-3.5);
		assertEquals(vr, new Vector(-3.5, -7, -10.5));
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		
        // =============== Boundary Values Tests ==================	
		
		//TC01: cross product of parallel vectors should throw an exception (vector zero)
		Vector v = new Vector(2, 4, 6);
		assertThrows("c-product of parallel vectors should throw an exception (vector zero)",
				IllegalArgumentException.class, () -> v1.crossProduct(v));
		
		//TC02: correct result of any 2 vectors
		Vector v2 = new Vector(1, 4, 5);
		Vector vr = v1.crossProduct(v2);
		assertEquals(vr, new Vector(-2, -2, 2));
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		
        // =============== Boundary Values Tests ==================	
		
		//TC01: result of dotProduct of orthogonal vectors should be 0
		Vector v2 = new Vector(0, 3, -2);
		double r = v1.dotProduct(v2);
		assertEquals(r, 0);
		
		//TC02: correct result of any 2 vectors
		v2 = new Vector(1, 4, 5);
		r = v1.dotProduct(v2);
		assertEquals(r, 24);
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============
		
        // =============== Boundary Values Tests ==================
		
		//TC: correct result of any vector
		Vector v1 = new Vector(1, 2, 3);
		double dsr = v1.lengthSquared();
		assertEquals(dsr, 14);
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Equivalence Partitions Tests ==============
		
        // =============== Boundary Values Tests ==================
		
		//TC: correct result of any vector
		Vector v1 = new Vector(1, 2, 3);
		double dr = v1.length();
		assertEquals(dr, Math.sqrt(14));
	}
	
	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============ Equivalence Partitions Tests ==============
        
		// =============== Boundary Values Tests ==================
		
		//TC: correct result of any vector
		Vector v = new Vector(1, 2, 3);
		Vector vNorm = v.normalize();
		double dr = vNorm.length();
		assertEquals(dr, 1);
	}
}