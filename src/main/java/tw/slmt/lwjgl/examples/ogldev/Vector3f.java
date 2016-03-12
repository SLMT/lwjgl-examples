package tw.slmt.lwjgl.examples.ogldev;

/**
 * Immutable.
 * 
 * @author SLMT
 */
public class Vector3f {
	
	private float x, y, z;
	
	public Vector3f() {
		
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f sub(Vector3f v) {
		Vector3f result = new Vector3f();
		
		result.x = x - v.x;
		result.y = y - v.y;
		result.z = z - v.z;
		
		return result;
	}

	public Vector3f cross(Vector3f v) {
		Vector3f result = new Vector3f();
		
		result.x = y * v.z - z * v.y;
		result.y = z * v.x - x * v.z;
		result.z = x * v.y - y * v.x;
		
		return result;
	}
	
	public Vector3f normalize() {
		Vector3f result = new Vector3f();
		
		float len = getLength();
		
		result.x = x / len;
		result.y = y / len;
		result.z = z / len;
		
		return result;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public float getLength() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
}
