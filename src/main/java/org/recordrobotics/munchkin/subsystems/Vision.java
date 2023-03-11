package org.recordrobotics.munchkin.subsystems;

import javax.xml.crypto.dsig.Transform;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings({"PMD.SystemPrintln", "PMD.FieldNamingConventions"})
public class Vision extends SubsystemBase{

	public PhotonCamera camera = new PhotonCamera("OV5647"); //IMPORTANT: This camera name MUST match the one on the Raspberry Pi, accessible through the PhotonVision UI.
	public Transform3d robotToCam = new Transform3d(new Translation3d(Units.inchesToMeters(11), -1*Units.inchesToMeters(9), 0.1725), new Rotation3d(0,0,0)); //The offset from the center of the robot to the camera, and from facing exactly forward to the orientation of the camera.
	//TODO: SET TRANSFORM!!!!!!


	// Sends photonvision camera output to shuffleboard
	public void runDriverMode() {
		camera.setDriverMode(true);
		camera.setPipelineIndex(2);
	}


	static final double[][] tags = {//april tags 1-8 in order. values contained are x, y, z, theta, in that order. x, y, z are distances in meters, theta is in radians.
		{15.513558, 1.071626, 0.462788, Math.PI}, //tag 1
		{15.513558, 2.748026, 0.462788, Math.PI}, //tag 2
		{15.513558, 4.424426, 0.462788, Math.PI}, //tag 3
		{16.178784, 6.749796, 0.695452, Math.PI}, //tag 4
		{0.36195, 6.749796, 0.695452, 0}, //tag 5
		{1.02743, 4.424426, 0.462788, 0}, //tag 6
		{1.02743, 2.748026, 0.462788, 0}, //tag 7
		{1.02743, 1.071626, 0.462788, 0}}; //tag 8
	double[] fieldDimensions = {16.54175, 8.0137};//x, y. The origin is at the bottom corner of the blue alliance wall as seen on the field drawings. 0 radians is parallel to the positive x-axis. Distances are meters.

	
	// Josh code activities lol
	/* How this code works: 
	 *
	*/

	static final Transform2d[] tag_transforms = {//april tags 1-8 in order. values contained are x, y, z, theta, in that order. x, y, z are distances in meters, theta is in radians.
		
		new Transform2d(new Translation2d(15.513558, 4.424426), new Rotation2d(180)), //tag 1
		new Transform2d(new Translation2d(15.513558, 2.748026), new Rotation2d(180)), //tag 2
		new Transform2d(new Translation2d(15.513558, 4.424426), new Rotation2d(180)), //tag 3
		new Transform2d(new Translation2d(16.178784, 6.749796), new Rotation2d(180)), //tag 4
		new Transform2d(new Translation2d(0.36195, 6.749796), new Rotation2d(0)), //tag 5
		new Transform2d(new Translation2d(1.02743, 4.424426), new Rotation2d(0)), //tag 6
		new Transform2d(new Translation2d(1.02743, 2.748026), new Rotation2d(0)), //tag 7
		new Transform2d(new Translation2d(1.02743, 1.071626), new Rotation2d(0)),}; //tag 8
	
	public static double[] estimateGlobalPose(PhotonCamera camera) {

		// Gets a frame from the camera
		var result = camera.getLatestResult();

		// Checks if there is a target. If there is one, the "if" statement passes. 
		boolean hasTargets = result.hasTargets();
		if (hasTargets){
			
			// Gets target object from apriltag perspective photonvision
			PhotonTrackedTarget target = result.getBestTarget();
			Transform3d robot_to_april = target.getBestCameraToTarget()/*.plus(robotToCam.inverse())*/; // you could put the offset here if you were testing for reals
			Transform3d april_to_robot = robot_to_april.inverse();
			// Converts the Transform3d object into a Pose2d object. Probably not entirely necessary to shift everything to 2d but its 2:30 in the morning and honestly my brain is kind of not working
			Pose2d april_to_robot_pose2d = new Pose2d(
				april_to_robot.getTranslation().toTranslation2d(), 
				april_to_robot.getRotation().toRotation2d());

			// Gets the fiducial ID and uses it to get the correct transform 2d object, which it then inverses to get the april to global perspective
			int targetID = target.getFiducialId();
			Transform2d april_to_global = tag_transforms[targetID].inverse();
			
			// Uses the "Transform2d(Pose2d initial, Pose2d final)" feature to take the difference between april to robot and april to global
			Pose2d global_to_camera = april_to_robot_pose2d.plus(april_to_global);

			// Gets the X and Y or the transform
			double global_x = global_to_camera.getX();
			double global_y = global_to_camera.getY();
			double global_theta = global_to_camera.getRotation().getRadians();

			// Returns final global pose
			return new double[] {global_x, global_y, global_theta}; // If this line doesnt work create a double[] called "global_pose" and return that instead
		}

		else {return null;}
	}





	public static double getTagAngle(PhotonTrackedTarget target){//Currently not in use, but being left in for future reference.
		double bottomLeftY = target.getDetectedCorners().get(0).y;
		double topRightX = target.getDetectedCorners().get(2).x;
		double topLeftX = target.getDetectedCorners().get(3).x;
		double topLeftY = target.getDetectedCorners().get(3).y;
		double height = topLeftY - bottomLeftY;
		double width = topLeftX - topRightX;
		double angle = Math.PI - Math.asin(width/height);//necessary to get the angle off of the line normal to the tag
		
		System.out.println("Height, width, angle: " + height + " " + width + " " + angle);

		
		return Math.PI/2-angle;
	}

	public static double[] getVisionPoseEstimate(PhotonCamera camera, Transform3d robotToCam){
		var result = camera.getLatestResult();//get a frame from the camera
		boolean hasTargets = result.hasTargets();//check for targets. This MUST be checked for, otherwise an error will occur if there isn't a target.
		if (hasTargets){
			PhotonTrackedTarget target = result.getBestTarget();//Choose the closest Apriltag

			System.out.println("TARGET: " + target);

			int targetID = target.getFiducialId();//get the ID
			Transform3d bestRobotToTarget = target.getBestCameraToTarget();//.plus(robotToCam.inverse());//getting the transform from the robot center to the tag. This transform's values are robot relative, and so will be converted to global coordinates.
			
			System.out.println("BEST ROBOT TO TARGET: " + bestRobotToTarget);
			
			//double yaw = target.getYaw()*Math.PI/180
			double targetAngle = getTagAngle(target);//getting the angle to the robot from the center of the april tag.
			
			System.out.println("ANGLE: " + targetAngle);
			
			double distance = Math.sqrt(bestRobotToTarget.getX()*bestRobotToTarget.getX() + bestRobotToTarget.getY()*bestRobotToTarget.getY());//getting the distance between the camera and tag from the x and y components of the transform.
			double x_transform = Math.cos(targetAngle)*distance;//getting the x transform from the tag to the robot.
			double y_transform = Math.sin(targetAngle)*distance;//getting the y transform from the tag to the robot.
			double global_x = tags[targetID][0] + Math.cos(tags[targetID][3])*x_transform;//the x transform from the tag to the robot is added to the tag's x coordinate to get the robot's global x coordinate.
			double global_y = tags[targetID][1] + y_transform;//the y transform from the tag to the robot is added to the tag's y coordinate to get the robot's global y coordinate.
			double global_theta = tags[targetID][3] + Math.PI + bestRobotToTarget.getRotation().toRotation2d().getRadians(); //getting the orientation of the robot from the tag's orientation and the transform. Pi is added because if the camera sees the tag, it is necessarily looking in the direction opposite the tag's orientation.
			double[] globalPose = {global_x, global_y, global_theta};//returns a [x, y, theta] vector. The z of the robot is irrelevant for pose estimation this year, and so ignored here.
			
			
			return globalPose;
		} else
		return null;//if no tag is visible, nothing will be returned.
	}

	public static double[] getColoredObjectPose(PhotonCamera camera, Transform3d robotToCam, DifferentialDrivePoseEstimator estimator){//THIS DOES NOT WORK; NO 3D FUNCTIONS FOR THIS YEARS GAME PIECES
		var result = camera.getLatestResult();//get a frame from the camera
		boolean hasTargets = result.hasTargets();//check for targets. This MUST be checked for, otherwise an error will occur if there isn't a target.
		if (hasTargets){
			PhotonTrackedTarget target = result.getBestTarget();//Choose the closest colored object of the type being looked for
			Transform3d bestRobotToTarget = target.getBestCameraToTarget().plus(robotToCam.inverse());//getting the transform from the robot center to the object. These coordinates are robot-relative, and so for trajectory following will be converted to global coordinates below.
			Pose2d robotPose = estimator.getEstimatedPosition();
			double distance = Math.sqrt(bestRobotToTarget.getX()*bestRobotToTarget.getX() + bestRobotToTarget.getY()*bestRobotToTarget.getY());//getting the distance between the camera and the object from the x and y components of the transform.
			double globalX = Math.cos(robotPose.getRotation().getRadians())*distance;
			double globalY = Math.sin(robotPose.getRotation().getRadians())*distance;
			double globalTheta = bestRobotToTarget.getRotation().toRotation2d().getRadians() + robotPose.getRotation().getRadians();
			double[] globalPose = {globalX, globalY, globalTheta};
			return globalPose;
		} else
		return null;
	}

	public static boolean checkForTarget(PhotonCamera camera){
		var result = camera.getLatestResult();//get a frame from the camera
		boolean hasTargets = result.hasTargets();//check for targets. This MUST be checked for, otherwise an error will occur if there isn't a target.
		return hasTargets;
	}

	public static boolean checkForTargetAndAmbiguity(PhotonCamera camera){
		var result = camera.getLatestResult();//get a frame from the camera
		boolean hasTargets = result.hasTargets();//check for targets. This MUST be checked for, otherwise an error will occur if there isn't a target.
		boolean ambiguityAcceptible = false;
		if (hasTargets){
			PhotonTrackedTarget target = result.getBestTarget();//Choose the closest Apriltag
			ambiguityAcceptible = target.getPoseAmbiguity() <= 0.2;}
		if (hasTargets && ambiguityAcceptible){
			return true;
		} else {
			return false;
		}
}
}
