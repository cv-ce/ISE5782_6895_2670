/**
 * 
 */
package primitives;

/**
 * @author barak epstein
 *
 */
public class Material {

	Double3 kS = Double3.ZERO;
	Double3 kD = Double3.ZERO;
	int nShininess = 0;
	
	/**
	 * refraction factor
	 */
	public Double3 kT = Double3.ZERO;
	
	/**
	 * reflection factor
	 */
	public Double3 kR = Double3.ZERO;
	
	/**
	 * sets 'kD', return the object (Builder pattern)
	 */
	public Material setKd(Double3 kD) 
	{
		this.kD = kD;
		return this;
	}

	public Material setKd(double kD) 
	{
		this.kD = new Double3(kD);
		return this;
	}
	
	/**
	 * sets 'kS', return the object (Builder pattern)
	 */
	public Material setKs(Double3 kS) 
	{
		this.kS = kS;
		return this;
	}

	public Material setKs(double kS) 
	{
		this.kD = new Double3(kS);
		return this;
	}
	
	
	/**
	 * sets 'nShininess', return the object (Builder pattern)
	 */
	public Material setShininess(int nShininess) 
	{
		this.nShininess = nShininess;
		return this;
	}
}
