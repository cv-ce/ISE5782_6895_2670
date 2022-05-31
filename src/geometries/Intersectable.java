/**
 * 
 */
package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * @author barak epstein
 *
 */
public abstract class Intersectable {
	
	public static class GeoPoint 
	{
		/**
		 * 
		 */
	    public Geometry geometry;
	    
	    /**
	     * 
	     */
	    public Point point;
	    
	    /**
	     * constructor
	     * @param geometry Geometry
	     * @param point Point
	     */
	    public GeoPoint(Geometry geometry,Point point)
	    {
	    	this.geometry = geometry;
	    	this.point = point;
	    }
	    
	    /**
	     * overrides 'equals'
	     */
		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof GeoPoint)) return false;
			GeoPoint other = (GeoPoint)obj;
			return this.geometry.equals(other.geometry) && this.point.equals(other.point);
		}
		
		/**
		 * overrides 'toString'
		 */
		@Override
		public String toString() {
			return "Intersectable []";
		}
	}
	
	/**
	 * returns intersections between 'ray' and a geometry
	 * @param ray
	 * @return
	 */
	public List<Point> findIntersections(Ray ray) {
	    var geoList = findGeoIntersections(ray);
	    return geoList == null ? null
	                           : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * Auxiliary function - returns 'GeoPoint' intersection points between a geometry and a given ray
	 * @param ray
	 * @return
	 * @throws IllegalArgumentException
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper (Ray ray) throws IllegalArgumentException;

	/**
	 * returns 'GeoPoint' intersection points between a geometry and a given ray (using findGeoIntersectionsHelper function)
	 * @param ray
	 * @return
	 * @throws IllegalArgumentException
	 */
	public List<GeoPoint> findGeoIntersections (Ray ray) //throws IllegalArgumentException
	{
		return findGeoIntersectionsHelper(ray);
	}
	
	/*public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}
	public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		return findGeoIntersectionsHelper(ray, maxDistance);
	}
	protected abstract List<GeoPoint>
                      findGeoIntersectionsHelper(Ray ray, double maxDistance);*/

}