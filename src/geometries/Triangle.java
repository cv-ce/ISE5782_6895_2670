package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Triangle extends Polygon {

	/**
	 * triangle constructor
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Point p1, Point p2, Point p3) throws IllegalArgumentException  {
		super(p1, p2, p3);
	}
	
	/**
	 * returns a normal to the triangle in point p
	 */
	/*public Vector getNormal(Point p) {//inherits Polygon's methods!!!!!! no implementation of this method here
		return null;
	}*/

	/**
	 * overrides 'toString'
	 */
	@Override
	public String toString() {//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return "Triangle []" + this.vertices;
	}

	/**
	 * returns intersection points between a triangle and a given ray
	 */
	@Override
	public List<Point> findIntersections(Ray ray) throws IllegalArgumentException
	{
		/*List<Point>	pInters = plane.findIntersections(ray);

		//check if the found point is in the triangle's boarders or not...
		Vector v1 = vertices.get(0).subtract(ray.getP0());
		Vector v2 = vertices.get(1).subtract(ray.getP0());
		Vector v3 = vertices.get(2).subtract(ray.getP0());
		
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		
		//if all Vi*Ni have the same sign --> the point is in the triangle
		if (Util.alignZero(n1.dotProduct(ray.getDir())) > 0 && Util.alignZero(n2.dotProduct(ray.getDir())) > 0 && Util.alignZero(n3.dotProduct(ray.getDir())) > 0)
			{
				return pInters;
			}
			else if (Util.alignZero(n1.dotProduct(ray.getDir())) < 0 && Util.alignZero(n2.dotProduct(ray.getDir())) < 0 && Util.alignZero(n3.dotProduct(ray.getDir())) < 0)
			{
				return pInters;
			}
			if (Util.isZero(n1.dotProduct(ray.getDir())) || Util.isZero(n2.dotProduct(ray.getDir())) || Util.isZero(n3.dotProduct(ray.getDir())))
				return null; //there are no intersections
			return null;	*/
		/*List<Point> rayPoints = plane.findIntersections(ray);
		if (rayPoints == null)
			return null;
		//check if the point in out or on the triangle:
		Vector v1 = vertices.get(0).subtract(ray.getP0());
		Vector v2 = vertices.get(1).subtract(ray.getP0());
		Vector v3 = vertices.get(2).subtract(ray.getP0());
		
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();

		
		//The point is inside if all 𝒗 ∙ 𝑵𝒊 have the same sign (+/-)
		
		if (Util.alignZero(n1.dotProduct(ray.getDir())) > 0 && Util.alignZero(n2.dotProduct(ray.getDir())) > 0 && Util.alignZero(n3.dotProduct(ray.getDir())) > 0)
		{
			return rayPoints;
		}
		else if (Util.alignZero(n1.dotProduct(ray.getDir())) < 0 && Util.alignZero(n2.dotProduct(ray.getDir())) < 0 && Util.alignZero(n3.dotProduct(ray.getDir())) < 0)
		{
			return rayPoints;
		}
		if (Util.isZero(n1.dotProduct(ray.getDir())) || Util.isZero(n2.dotProduct(ray.getDir())) || Util.isZero(n3.dotProduct(ray.getDir())))
			return null; //there is no instruction point
		return null;*/

	       List<Point> rayPoints = plane.findIntersections(ray);

	       if (rayPoints == null)

	           return null;




	       //check if the point in out or on the triangle:

	       Vector v1 = vertices.get(0).subtract(ray.getP0());

	       Vector v2 = vertices.get(1).subtract(ray.getP0());

	       Vector v3 = vertices.get(2).subtract(ray.getP0());

	       

	       Vector n1 = v1.crossProduct(v2).normalize();

	       Vector n2 = v2.crossProduct(v3).normalize();

	       Vector n3 = v3.crossProduct(v1).normalize();




	       

	       //The point is inside if all the calculations have the same sign (+/-)

	       

	       if (Util.alignZero(n1.dotProduct(ray.getDir())) > 0 && Util.alignZero(n2.dotProduct(ray.getDir())) > 0 && Util.alignZero(n3.dotProduct(ray.getDir())) > 0)

	       {

	           return rayPoints;

	       }

	       else if (Util.alignZero(n1.dotProduct(ray.getDir())) < 0 && Util.alignZero(n2.dotProduct(ray.getDir())) < 0 && Util.alignZero(n3.dotProduct(ray.getDir())) < 0)

	       {

	           return rayPoints;

	       }

	       if (Util.isZero(n1.dotProduct(ray.getDir())) || Util.isZero(n2.dotProduct(ray.getDir())) || Util.isZero(n3.dotProduct(ray.getDir())))

	           return null; //there is no instruction point

	       return null;

	}
	
	@ Override
	public List<GeoPoint> findGeoIntersectionsHelper (Ray ray)
	{
	List<GeoPoint> rayPoints = plane.findGeoIntersectionsHelper(ray);
	if (rayPoints == null)
	return null;
	for(GeoPoint P: rayPoints )
	{
	P.geometry=this;
	}

	//check if the point in out or on the triangle:
	Vector v1 = vertices.get(0).subtract(ray.getP0());
	Vector v2 = vertices.get(1).subtract(ray.getP0());
	Vector v3 = vertices.get(2).subtract(ray.getP0());

	Vector n1 = v1.crossProduct(v2).normalize();
	Vector n2 = v2.crossProduct(v3).normalize();
	Vector n3 = v3.crossProduct(v1).normalize();


	//The point is inside if all have the same sign (+/-)

	if (Util.alignZero(n1.dotProduct(ray.getDir())) > 0 && Util.alignZero(n2.dotProduct(ray.getDir())) > 0 && Util.alignZero(n3.dotProduct(ray.getDir())) > 0)
	{
	return rayPoints;
	}
	else if (Util.alignZero(n1.dotProduct(ray.getDir())) < 0 && Util.alignZero(n2.dotProduct(ray.getDir())) < 0 && Util.alignZero(n3.dotProduct(ray.getDir())) < 0)
	{
	return rayPoints;
	}
	if (Util.isZero(n1.dotProduct(ray.getDir())) || Util.isZero(n2.dotProduct(ray.getDir())) || Util.isZero(n3.dotProduct(ray.getDir())))
	return null; //there is no instruction point
	return null;
	}
	}

	
//}


