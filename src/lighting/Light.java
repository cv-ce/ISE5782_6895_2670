/**
 * 
 */
package lighting;

import primitives.Color;

/**
 * @author barak epstein
 *
 */
public abstract class Light {
	Color intensity;
	
	/**
	 * constructor
	 */
	protected Light(Color c) {
		this.intensity = c;
	}
	
	/**
	 * returns 'intensity'
	 * @return 
	 */
	public Color getIntensity() {
		return this.intensity;
	}
}
