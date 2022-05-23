/*package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;


public class Ray {

	final Point p0;
	final Vector dir;
	
	/**
	 * ray constructor
	 *
	public Ray(Point p, Vector v){
		p0 = p;
		Point v0=new Point(0, 0, 0);// if 'v' is vector zero
		if(v.xyz.equals(v0.xyz))
			throw new IllegalArgumentException("ERROR. This is a zero vector!");
		dir = v.normalize();
	}

	/**
	 * overrides 'equals'
	 *
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ray other = (Ray) obj;
		return this.dir.equals(other.dir) && this.p0.equals(other.p0);
	}

	/**
	 * overrides 'toString'
	 *
	@Override
	public String toString() {
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}
	
	/**
	 * returns a ray's point
	 *
	public Point getP0() {
		return this.p0;
	}
	
	/**
	 * returns a ray's direction
	 *
	public Vector getDir() {
		return this.dir;
	}
	
	/**
	 * returns a general point on the ray (according to 't')
	 *
	public Point getPoint(double t) {
		Point retP = this.p0.add(dir.scale(t));
		return retP;
	}
	
	/**
	 * returns the point which is the closest to the beginning of the ray, out of the intersection points list.
	 * @param points List<Point3D> value
	 * @return Point3D value
	 * *
	public Point findClosestPoint(List<Point> points) {
	    return points == null || points.isEmpty() ? null
	           : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * 
	 * @param intersections
	 * @return
	 *
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		if(intersections == null)
			return null;
		GeoPoint closet = intersections.get(0);
		for (GeoPoint geoPoint : intersections) 
		{
			if(geoPoint.point.distance(p0) < closet.point.distance(p0))
				closet= geoPoint;
		}
		return closet;
	}
}*/
/* public Point getPoint(double t)
    {
        if(isZero(t)){
            throw new IllegalArgumentException("t is equal to 0 produce an illegal ZERO vector");
        }
        return p0.add(dir.scale(t));
        //Point tmp=new Point(p.dPoint.d1 ,p.dPoint.d2,p.dPoint.d3);
        //return isZero(t) ? p : tmp.add(v.scale(t));//takes the beginning of the ray and adds the vector*scalar point that we get.
    }
     /*public Ray(Vector v2, Point p2) {
        super();
        p0 = p2;
        dir = v2.normalize();
    }*
    
   /* public Ray(Point p2,Vector v2) {
       // super();
        //p0 = p2;
        //dir = v2.normalize();
    	
    }*/
    /*public Vector getVector() {
    	return  this.dir;
    }*/

package primitives;

import static primitives.Util.isZero;

import geometries.Intersectable;
import java.util.List;
import java.util.Objects;

public class Ray {
	
    Point p0;
    Vector dir;

    private static final double DELTA = 0.1;
    
    /**
     * ray ctor with 2 parameters
     * @param p
     * @param v
     */
    public Ray(Point p, Vector v){
		p0 = p;
		Point v0=new Point(0, 0, 0);// if 'v' is vector zero
		if(v.xyz.equals(v0.xyz))
			throw new IllegalArgumentException("ERROR. This is a zero vector!");//is really needed here???????????
		dir = v.normalize();
	}

   /**
    * ray ctor with 3 parameters
    * @param head
    * @param lightDirection
    * @param n
    */
   public Ray(Point head, Vector lightDirection, Vector n)
    {
         if(primitives.Util.alignZero(lightDirection.dotProduct(n)) < 0)
            p0= head.add(n.scale(-DELTA));
        else if(primitives.Util.alignZero(lightDirection.dotProduct(n)) > 0)
            p0= head.add(n.scale(DELTA));
        else if(primitives.Util.isZero(lightDirection.dotProduct(n)))
            p0=head;
        dir=lightDirection;
        dir.normalize();
    }
   
    /**
     * returns a ray's beginning point
     * @return
     */
    public Point getP0() {
        return  this.p0;
    }
    
    /**
	 * returns a ray's direction vector
	 */
	public Vector getDir() {
		return this.dir;
	} 
    
    /**
	 * returns a general point on the ray (according to 't')
	 */
	public Point getPoint(double t) {
		if(isZero(t)) {
            throw new IllegalArgumentException("t = 0 produces an illegal vector zero");
        }
		return this.p0.add(dir.scale(t));
	}
	
    /**
     * returns the point which is the closest to the beginning of the ray, out of the intersection points list.
     * @param points
     * @return closestP
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new Intersectable.GeoPoint(null, p)).toList()).point;
    }

    /**
     * returns the 'GeoPoint' point which is the closest to the beginning of the ray  
     * @param geoPoints
     * @return closePoint
     */
    public Intersectable.GeoPoint findClosestGeoPoint(List<Intersectable.GeoPoint> geoPoints) {

        if (geoPoints == null)//In case of an empty list
            return null;
        Intersectable.GeoPoint closePoint = geoPoints.get(0);//Save the first point in the list
        for (Intersectable.GeoPoint p : geoPoints) {
            if (closePoint.point.distance(this.p0) > p.point.distance(this.p0))//when the distance between 'closePoint' to 'p0' 
            	closePoint = p;												//is bigger than the distance between 'p' and 'p0'
        }
        return closePoint;
    }

    /**
     * overrides 'equals'
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
        	return true;
        if (obj == null) 
        	return false;
        if (!(obj instanceof Ray)) //or: if (getClass() != obj.getClass())
        	return false;
        Ray other = (Ray)obj;
        return this.dir.equals(other.dir) && this.p0.equals(other.p0);
    }
    
    /**
     * overrides 'toString'
     */
    @Override
    public String toString()
    {
        return "Ray [Point=" + p0 + ", Vector=" +dir + "]";
    }
    
    /**
     * overrides 'hashCode'
     */
    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }   
}