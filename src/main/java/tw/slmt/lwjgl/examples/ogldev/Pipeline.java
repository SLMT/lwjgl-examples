package tw.slmt.lwjgl.examples.ogldev;

public class Pipeline {
	
	private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
	private Vector3f worldPos = new Vector3f(0.0f, 0.0f, 0.0f);
	private Vector3f rotateInfo = new Vector3f(0.0f, 0.0f, 0.0f);
	
	private Vector3f cameraPos;
	private Vector3f cameraTarget;
	private Vector3f cameraUp;
	
	private Matrix4f worldTrans;
	private Matrix4f viewTrans;
	private Matrix4f projTrans;
	
	private Matrix4f wpTrans;
	private Matrix4f vpTrans;
	private Matrix4f wvpTrans;
	
	private PersProjInfo persProjInfo;
	
	public void scale(Vector3f scale) {
		this.scale = scale;
	}
	
	public void worldPos(Vector3f worldPos) {
		this.worldPos = worldPos;
	}
	
	public void rotate(Vector3f rotateInfo) {
		this.rotateInfo = rotateInfo;
	}
	
	public void setCamera(Vector3f pos, Vector3f target, Vector3f up) {
		this.cameraPos = pos;
		this.cameraTarget = target;
		this.cameraUp = up;
	}
	
	public void setCamera(Camera camera) {
		setCamera(camera.getPos(), camera.getTarget(), camera.getUp());
	}
	
	public Matrix4f getWorldTrans() {
		Matrix4f scaleTrans = Matrix4f.
				initScaleTransform(scale.getX(), scale.getY(), scale.getZ());
		Matrix4f rotateTrans = Matrix4f.
				initRotateTransform(rotateInfo.getX(), rotateInfo.getY(), rotateInfo.getZ());
		Matrix4f translationTrans = Matrix4f.
				initTranslationTransform(worldPos.getX(), worldPos.getY(), worldPos.getZ());
		
		worldTrans = translationTrans.multiply(rotateTrans).multiply(scaleTrans);
	    return worldTrans;
	}
	
	public Matrix4f getViewTrans() {
		Matrix4f cameraTranslationTrans = Matrix4f.
				initTranslationTransform(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());
		Matrix4f cameraRotateTrans = Matrix4f.
				initCameraTransform(cameraTarget, cameraUp);
		
		viewTrans = cameraRotateTrans.multiply(cameraTranslationTrans);

	    return viewTrans;
	}
	
	public Matrix4f getProjTrans() {
		projTrans = Matrix4f.initPersProjTransform(persProjInfo);
	    return projTrans;
	}
	
	public Matrix4f getWPTrans() {
		getWorldTrans();
		getProjTrans();
		
		wpTrans = projTrans.multiply(worldTrans);
		return wpTrans;
	}
	
	public Matrix4f getVPTrans() {
		getViewTrans();
		getProjTrans();
		
		vpTrans = projTrans.multiply(viewTrans);
	    return vpTrans;
	}
	
	public Matrix4f getWVPTrans() {
		getWorldTrans();
		getVPTrans();
		
		wvpTrans = vpTrans.multiply(worldTrans);
	    return wvpTrans;
	}
	
	public void setPersProjInfo(PersProjInfo persProjInfo) {
		this.persProjInfo = persProjInfo;
	}
}
