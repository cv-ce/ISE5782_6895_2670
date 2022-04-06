package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * class Scene for PDS
 * 
 * @author Shirel Avivi325112670 and Chaya Epstein
 */
public class Scene 
{
	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight();
	public Geometries geometries;
	
	/**
	 * constructor 
	 * 
	 * @author Shirel Avivi325112670 and Chaya Epstein
	 * @param name
	 * */
	public Scene(String name)
	{
		geometries = new Geometries();
	}

	
	/**
	 * setter function to background, and return this for builder pattern
	 * 
	 * @author Shirel Avivi325112670 and Chaya Epstein
	 * @param background the background to set
	 */
	public Scene setBackground(Color background) 
	{
		this.background = background;
		return this;
	}
	

	/**
	 * setter function to ambientLight, and return this for builder pattern
	 * 
	 * @author Shirel Avivi 325112670 and Chaya Epstein
	 * @param ambientLight the ambientLight to set
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) 
	{
		this.ambientLight = ambientLight;
		return this;
		
	}
	

	/**
	 * setter function to geometries, and return this for builder pattern
	 * 
	 * @author Shirel Avivi325112670 and Chaya Epstein
	 * @param geometries the geometries to set
	 */
	public Scene setGeometries(Geometries geometries) 
	{
		this.geometries = geometries;
		return this;
	}
	

}