package primitives;

public class Ray {

	final Point p0;
	final Vector dir;
	
	/**
	 * ray constructor
	 */
	public Ray(Point p, Vector v){
		p0 = p;
		Point v0=new Point(0, 0, 0);// if 'v' is vector zero
		if(v.xyz.equals(v0.xyz))
			throw new IllegalArgumentException("ERROR. This is a zero vector!");
		dir = v.normalize();
	}

	/**
	 * overrides 'equals'
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ray other = (Ray) obj;
		return this.dir.equals(other.dir) && this.p0.equals(other.p0);
	}

	/**
	 * overrides 'toString'
	 */
	@Override
	public String toString() {
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}
	
	/**
	 * returns a ray's point
	 */
	public Point getP0() {
		return this.p0;
	}
	
	/**
	 * returns a ray's direction
	 */
	public Vector getDir() {
		return this.dir;
	}
	
	/**
	 * returns a general point on the ray (according to 't')
	 */
	public Point getPoint(double t) {
		Point retP = this.p0.add(dir.scale(t));
		return retP;
	}
}
