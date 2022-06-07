/**
 * 
 */
/*package lighting;

import primitives.Color;

/**
 * @author barak epstein
 *
 *
public abstract class Light {
	Color intensity;
	
	/**
	 * constructor
	 *
	protected Light(Color c) {
		this.intensity = c;
	}
	
	/**
	 * returns 'intensity'
	 * @return 
	 *
	public Color getIntensity() {
		return this.intensity;
	}
}
*/


/**
 * @author Noa Vales 322987801 noa4047@gmail.com
 * @author Yael Adler 322877903 yael.yula99@gmail.com
 */

package lighting;

import primitives.Color;

/**
 * Light's class.
 * Represents light by it's color's overall intensity. 
 */
class Light {
	
	private Color intensity;

	
	/**
	 * Light's Constructor
	 * @param intensity  the light's intensity
	 */
	protected Light(Color intensity) {
		super();
		this.intensity = intensity;
	}



	/**
	 * Getter intensity
	 * @return the light's intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}
