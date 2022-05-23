package primitives;

public class Vector extends Point {

	/**
	 * vector constructor #1 (3 'double' parameters)
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y, double z)  throws IllegalArgumentException
	{
		super(x,y,z);
		if(this.xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("ERROR. This is a zero vector!");
	}
	
	/**
	 * vector constructor #2 (1 'Double3' parameter)
	 * @param v
	 * @throws IllegalArgumentException
	 */
	public Vector(Double3 v)  throws IllegalArgumentException
	{
		super(v);
		if(this.xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("ERROR. This is a zero vector!");
	}
	
	/**
	 * overrides 'toString'
	 */
	@Override
	public String toString() {
		return "Vector" + super.toString();
	}

	/**
	 * overrides 'equals'
	 */
	@Override//to check!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector)obj;
		return this.xyz.equals(other.xyz);
		///!!!!!!!!!!!maybe check point
		//return true;
	}
	
	/**
	 * returns the result of the addition of vector 'v' to the current vector
	 */
	public Vector add(Vector v)  throws IllegalArgumentException
	{
		Point p=new Point(super.add(v).xyz.d1, super.add(v).xyz.d2, super.add(v).xyz.d3);
		return new Vector(p.xyz.d1, p.xyz.d2, p.xyz.d3);
	}
	
	/**
	 * returns a new vector which is the result of the production of the current vector with scalar 'sca' 
	 * @param sca
	 * @return
	 */
	public Vector scale(double sca)  throws IllegalArgumentException
	{

		double x = this.xyz.d1 * sca;
		double y = this.xyz.d2 * sca;
		double z = this.xyz.d3 * sca;
		return new Vector(x, y, z);
	}

	/**
	 * returns the result of the cross product of vector 'v' and the current vector
	 * @param v
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector crossProduct(Vector v)  throws IllegalArgumentException
	{
		double x = this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2;
		double y = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
		double z = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;
		return new Vector(x, y, z);
	}
	
	/**
	 * returns the result of the dot product of vector 'v' and the current vector
	 * @param v
	 * @return
	 * @throws IllegalArgumentException
	 */
	public double dotProduct(Vector v)  throws IllegalArgumentException
	{
		double xx = this.xyz.d1 * v.xyz.d1;
		double yy = this.xyz.d2 * v.xyz.d2;
		double zz = this.xyz.d3 * v.xyz.d3;
		// / (this.length() * v.length())
		/*double cosA = Math.acos(dp);//according to the formula...
		if(cosA == 1)
			//same vector
		if(cosA == -1)
			//opposite vectors on the same line
		if(cosA == 0)
			//orthogonal vectors
		if(cosA > 0 && cosA < 1)
			//sharp angle
		if(cosA > -1 && cosA < 0)
			//big angle*/
		return (xx + yy + zz);
	}
	
	/**
	 * returns the squared length of the current vector
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}
	
	/**
	 * returns the length of the current vector
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	/**
	 * returns a copy of the current vector after normalization
	 */
	public Vector normalize()
	{
		double len = this.length();
		double x = this.xyz.d1 / len;
		double y = this.xyz.d2 / len;
		double z = this.xyz.d3 / len;		
		return new Vector(x, y, z);
	}
}
