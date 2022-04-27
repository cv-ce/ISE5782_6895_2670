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

	
	public List<GeoPoint> findGeoIntersections (Ray ray) throws IllegalArgumentException
	{
		return findGeoIntersectionsHelper(ray);
	}
	
	protected abstract List<GeoPoint> findGeoIntersectionsHelper (Ray ray) throws IllegalArgumentException;
	
	public static class GeoPoint 
	{
	    public Geometry geometry;
	    public Point point;
	    
	    
	    /**
	     * constructor for geo point
	     * 
	     * @param geometry Geometry
	     * @param point Point
	     * */
	    public GeoPoint(Geometry geometry,Point point)
	    {
	    	this.geometry = geometry;
	    	this.point = point;
	    }
	    
		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof GeoPoint)) return false;
			GeoPoint other = (GeoPoint)obj;
			return this.geometry.equals(other.geometry) && this.point.equals(other.point);
		}
		
		@Override
		public String toString() {
			return "Intersectable []";
		}
		
	}

	
}