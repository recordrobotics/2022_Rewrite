//This is a joke file. Do not actually use it. It will most likely not be any faster than the actual working file

package org.recordrobotics.munchkin.subsystems;import org.photonvision.PhotonCamera;import edu.wpi.first.math.geometry.*;import edu.wpi.first.wpilibj2.command.SubsystemBase;
@SuppressWarnings({"PMD.SystemPrintln", "PMD.FieldNamingConventions"})
public class VisionTooSimple extends SubsystemBase{
	public PhotonCamera camera = new PhotonCamera("OV5647"); public void runDriverMode() {camera.setDriverMode(true);camera.setPipelineIndex(2);}
	static final Transform2d[] tag_transforms = {new Transform2d(new Translation2d(15.513558, 4.424426), new Rotation2d(180)), new Transform2d(new Translation2d(15.513558, 2.748026), new Rotation2d(180)), new Transform2d(new Translation2d(15.513558, 4.424426), new Rotation2d(180)), new Transform2d(new Translation2d(16.178784, 6.749796), new Rotation2d(180)), new Transform2d(new Translation2d(0.36195, 6.749796), new Rotation2d(0)),new Transform2d(new Translation2d(1.02743, 4.424426), new Rotation2d(0)),new Transform2d(new Translation2d(1.02743, 2.748026), new Rotation2d(0)),new Transform2d(new Translation2d(1.02743, 1.071626), new Rotation2d(0)),};
	public static double[] estimateGlobalPose(PhotonCamera camera) {
	var result = camera.getLatestResult();
	if (result.hasTargets()){
		Pose2d global_to_camera = new Pose2d(result.getBestTarget().getBestCameraToTarget().inverse().getTranslation().toTranslation2d(), result.getBestTarget().getBestCameraToTarget().inverse().getRotation().toRotation2d()).plus(tag_transforms[result.getBestTarget().getFiducialId()].inverse());
		return new double[] {global_to_camera.getX(), global_to_camera.getY(), global_to_camera.getRotation().getRadians()}; 
	}else {return null;}}}