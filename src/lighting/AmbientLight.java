package lighting;
import primitives.Color;
import primitives.Double3;

public class AmbientLight
{
	private Color intensity; //the color

	/**
	 * constructor that save the intensity=Ia*Ka
	 * 
	 * @author
	 * @param Ia Color value
	 * @param Ka double value
	 */
	public AmbientLight(Color Ia,Double3 Ka ) 
	{
		Ia.scale(Ka);
	}

	/**
	 * A default constructor
	 * this c-tor put the defalt color - black to the ambition light
	 */
	public AmbientLight() 
	{
		intensity = Color.BLACK;
	}

	public Color getIntensity() {
		return intensity;
	}

}
