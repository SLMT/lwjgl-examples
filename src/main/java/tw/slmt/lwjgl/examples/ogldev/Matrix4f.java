package tw.slmt.lwjgl.examples.ogldev;

import java.nio.FloatBuffer;

public class Matrix4f {
	
	public float[][] values = new float[][] {
		{1.0f, 0.0f, 0.0f, 0.0f},	
		{0.0f, 1.0f, 0.0f, 0.0f},
		{0.0f, 0.0f, 1.0f, 0.0f},
		{0.0f, 0.0f, 0.0f, 1.0f}
	};
	
	public static Matrix4f initScaleTransform(float scaleX, float scaleY, float scaleZ) {
		Matrix4f mat = new Matrix4f();
		
		mat.values[0][0] = scaleX;
		mat.values[1][1] = scaleY;
		mat.values[2][2] = scaleZ;
		
		return mat;
	}
	
	public static Matrix4f initRotateTransform(float rotateX, float rotateY, float rotateZ) {
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		float x = (float) Math.toRadians(rotateX);
		float y = (float) Math.toRadians(rotateY);
		float z = (float) Math.toRadians(rotateZ);
		
		rx.values[1][1] = (float) Math.cos(x);
		rx.values[1][2] = (float) -Math.sin(x);
		rx.values[2][1] = (float) Math.sin(x);
		rx.values[2][2] = (float) Math.cos(x);
		
		ry.values[0][0] = (float) Math.cos(y);
		ry.values[0][2] = (float) -Math.sin(y);
		ry.values[2][0] = (float) Math.sin(y);
		ry.values[2][2] = (float) Math.cos(y);
		
		rz.values[0][0] = (float) Math.cos(z);
		rz.values[0][1] = (float) -Math.sin(z);
		rz.values[1][0] = (float) Math.sin(z);
		rz.values[1][1] = (float) Math.cos(z);
		
		return rx.multiply(ry).multiply(rz);
	}
	
	public static Matrix4f initTranslationTransform(float x, float y, float z) {
		Matrix4f mat = new Matrix4f();
		
		mat.values[0][3] = x;
		mat.values[1][3] = y;
		mat.values[2][3] = z;
		
		return mat;
	}
	
	public static Matrix4f initPersProjTransform(PersProjInfo p) {
		Matrix4f mat = new Matrix4f();
		
		float ar = p.width / p.height;
		float zNear = p.zNear;
		float zFar = p.zFar;
		float zRange = p.zNear - p.zFar;
		float tanHalfFov = (float) Math.tan(Math.toRadians(p.fov / 2.0));
		
		mat.values[0][0] = 1.0f / (tanHalfFov * ar);
		mat.values[1][1] = 1.0f / tanHalfFov;
		mat.values[2][2] = (-zNear - zFar) / zRange;
		mat.values[2][3] = 2.0f * zFar * zNear / zRange;
		mat.values[3][2] = 1.0f;
		mat.values[3][3] = 0.0f;
		
		return mat;
	}
	
	public Matrix4f multiply(Matrix4f right) {
		Matrix4f result = new Matrix4f();
		
		for (int i = 0 ; i < 4 ; i++) {
            for (int j = 0 ; j < 4 ; j++) {
            	result.values[i][j] = 
            			values[i][0] * right.values[0][j] +
                        values[i][1] * right.values[1][j] +
                        values[i][2] * right.values[2][j] +
                        values[i][3] * right.values[3][j];
            }
        }
		
		return result;
	}
	
	public void transferToBuffer(FloatBuffer buf) {
		buf.rewind();
		for (int i = 0; i < 4; i++)
			buf.put(values[i]);
	}
}
