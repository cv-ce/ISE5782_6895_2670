package scene;

import lighting.AmbientLight;
import lighting.LightSource;

import java.util.LinkedList;
import java.util.List;

import geometries.Geometries;
import primitives.Color;

/**
 * class Scene - PDS
 * 
 * @author Shirel Avivi 325112670 and Chaya Epstein
 */
public class Scene 
{
	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight();
	public Geometries geometries;// = new Geometries();
	public List<LightSource> lights = new LinkedList();
	
	/**
	 * constructor 
	 * @author Shirel Avivi325112670 and Chaya Epstein
	 * @param name
	 * */
	public Scene(String name)
	{
		geometries = new Geometries();
	}

	/**
	 * sets 'background', returns the object (Builder pattern) 
	 * @author Shirel Avivi325112670 and Chaya Epstein
	 * @param background the background to set
	 */
	public Scene setBackground(Color background) 
	{
		this.background = background;
		return this;
	}
	
	/**
	 * sets 'ambientLight', returns the object (Builder pattern)
	 * @author Shirel Avivi 325112670 and Chaya Epstein
	 * @param ambientLight the ambientLight to set
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) 
	{
		this.ambientLight = ambientLight;
		return this;
	}
	
	/**
	 * sets 'geometries', returns the object (Builder pattern)
	 * @author Shirel Avivi325112670 and Chaya Epstein
	 * @param geometries the geometries to set
	 */
	public Scene setGeometries(Geometries geometries) 
	{
		this.geometries = geometries;
		return this;
	}
	
	/**
	 * sets 'lights', returns the object (Builder pattern)
	 */
	public Scene setLights(List<LightSource> lights) 
	{
		this.lights = lights;
		return this;
	}
}