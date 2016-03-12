package tw.slmt.lwjgl.examples.ogldev;

import org.lwjgl.glfw.GLFW;

public class Camera {

	private static final float STEP_SCALE = 0.1f;

	private Vector3f pos;
	private Vector3f target;
	private Vector3f up;
	
	public Camera() {
		this.pos = new Vector3f(0.0f, 0.0f, 0.0f);
		this.target = new Vector3f(0.0f, 0.0f, 1.0f);
		this.up = new Vector3f(0.0f, 1.0f, 0.0f);
	}

	public Camera(Vector3f pos, Vector3f target, Vector3f up) {
		this.pos = pos;
		this.target = target;
		this.up = up;
	}

	public Vector3f getPos() {
		return pos;
	}

	public Vector3f getTarget() {
		return target;
	}

	public Vector3f getUp() {
		return up;
	}

	public boolean onKeyboard(int key) {
		switch (key) {

		case GLFW.GLFW_KEY_UP:
			pos = pos.add(target.multiply(STEP_SCALE));

			return true;

		case GLFW.GLFW_KEY_DOWN:
			pos = pos.substract(target.multiply(STEP_SCALE));

			return true;

		case GLFW.GLFW_KEY_LEFT:
			Vector3f left = target.cross(up).normalize();
			left = left.multiply(STEP_SCALE);
			pos = pos.add(left);

			return true;

		case GLFW.GLFW_KEY_RIGHT:
			Vector3f right = up.cross(target).normalize();
			right = right.multiply(STEP_SCALE);
			pos = pos.add(right);

			return true;
		}

		return false;
	}
}
