package lighting;
import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light{
	//private Color intensity; //the color

	/**
	 * constructor that saves the intensity = Ia * Ka
	 */
	public AmbientLight(Color Ia, Double3 Ka) 
	{
		super(Ia.scale(Ka));
	}

	/**
	 * default ctor
	 * puts a default color - black, to the ambition light
	 */
	public AmbientLight() 
	{
		super(Color.BLACK);
		//intensity = Color.BLACK;
	}

	/*public Color getIntensity() {
		return intensity;
	}*/
}
