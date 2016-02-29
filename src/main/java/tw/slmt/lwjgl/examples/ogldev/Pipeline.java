package tw.slmt.lwjgl.examples.ogldev;

public class Pipeline {
	
	private float[] scale = new float[] {1.0f, 1.0f, 1.0f};
	private float[] worldPos = new float[] {0.0f, 0.0f, 0.0f};
	private float[] rotateInfo = new float[] {0.0f, 0.0f, 0.0f};
	
	private Matrix4f worldTrans;
	
	public void scale(float[] scale) {
		this.scale = scale;
	}
	
	public void worldPos(float[] worldPos) {
		this.worldPos = worldPos;
	}
	
	public void rotate(float[] rotateInfo) {
		this.rotateInfo = rotateInfo;
	}
	
	public Matrix4f getWorldTrans() {
		Matrix4f scaleTrans = Matrix4f.
				initScaleTransform(scale[0], scale[1], scale[2]);
		Matrix4f rotateTrans = Matrix4f.
				initRotateTransform(rotateInfo[0], rotateInfo[1], rotateInfo[2]);
		Matrix4f translationTrans = Matrix4f.
				initTranslationTransform(worldPos[0], worldPos[1], worldPos[2]);
		
		worldTrans = translationTrans.multiply(rotateTrans).multiply(scaleTrans);
	    return worldTrans;
	}
}
