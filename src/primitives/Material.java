/**
 * 
 */
package primitives;

/**
 * @author barak epstein
 *
 */
public class Material {
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Double3 kS = Double3.ZERO;
	public Double3 kD = Double3.ZERO;
	public int nShininess = 0;
	
	/**
	 * refraction factor
	 */
	public Double3 kT = Double3.ZERO;
	
	/**
	 * reflection factor
	 */
	public Double3 kR = Double3.ZERO;
	
	/**
	 * sets 'kD', returns the object (Builder pattern)
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
	 * sets 'kS', returns the object (Builder pattern)
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
	 * sets 'kT', returns the object (Builder pattern)
	 */
	public Material setKt(Double kT) 
	{
		this.kT = new Double3(kT);
		return this;
	}
	
	/**
	 * sets 'kR', returns the object (Builder pattern)
	 */
	public Material setKr(Double kR) 
	{
		this.kR = new Double3(kR);
		return this;
	}
	
	/**
	 * sets 'nShininess', returns the object (Builder pattern)
	 */
	public Material setShininess(int nShininess) 
	{
		this.nShininess = nShininess;
		return this;
	}
	
	
	public double getKR() {
		return kR.d1;
	}
	public double getKT() {
		return kT.d1;
	}
	public double getKS() {
		return kS.d1;
	}
	public double getKD() {
		return kD.d1;
	}
}
