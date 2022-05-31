package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 *author Shirel Avivi 325112670 and Chaya Epstein
 *
 */
public class Geometries extends Intersectable{
	
	List<Intersectable> geometriesLst;
	
	/**
	 * default constructor
	 */
	public Geometries()
	{
		//ArrayList was chosen because it works better when the application demands storing the data and accessing it.
		geometriesLst = new ArrayList<Intersectable>();
	}
	
	/**
	 * Constructor. Receives a list of geometries and puts them in a new arrayList 
	 * @author Shirel Avivi 325112670 and Chaya Epstein
	 * @param geometries
	 */
	public Geometries(Intersectable... geometries) {
		geometriesLst = new ArrayList<Intersectable>(Arrays.asList(geometries));
	}
	
	/**
	 * adds elements to the collection
	 * @author Shirel Avivi 325112670 and Chaya Epstein
	 * @param geometries 
	 * */
	public void add(Intersectable... geometries) {
		if (geometries != null)
			geometriesLst.addAll(Arrays.asList(geometries));
	}
	
	/**
	 * returns the intersections between 'ray' and a geometry              ���� ����� ����???????????
	 */
	@Override
	public List<Point> findIntersections(Ray ray) throws IllegalArgumentException
	{	
	    List<Point> temp = new ArrayList<Point>();
	    for ( Intersectable intersectable : geometriesLst)
	    {
	        List<Point> intersection = intersectable.findIntersections(ray);
	        if (intersection != null)
	           temp.addAll(intersection);
	    }
	    if (temp.isEmpty())
	       return null;
	    return temp;        
	}
	
	/**
	 * returns 'GeoPoint' intersection points between a geometry and a ray
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
	
		List<GeoPoint> temp = new ArrayList<GeoPoint>();
		for (Intersectable intersectable : geometriesLst)
		{
			List<GeoPoint> intersection = intersectable.findGeoIntersections(ray);
			if (intersection != null)
				temp.addAll(intersection);
		}

		if (temp.isEmpty())
			return null;
		return temp;
	}
}
