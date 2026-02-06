package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Transform3d;

public class VisionData{
    //making the variable for the camera used--
    //when using this class and making the variable for the--
    //latest result that gets updated using the update method--
    //inside of a periodic keeping the latest result uniform among--
    //all methods in the file.
    private PhotonCamera camera;
    private PhotonPipelineResult latestResult;

    //the constructor, having the camera as a parameter--
    //means the methods in this class can be used dynamically--
    //with any camera and dont need to be changed if the camera--
    //name gets changed throughout the season.
    public VisionData(String cameraName){
        camera = new PhotonCamera(cameraName);
    }

    //called at the top of the periodic, keeps the camera frame used uniform.
    public void update() {
        var results = camera.getAllUnreadResults();
        if (!results.isEmpty()) {
            latestResult = results.get(results.size() - 1);
        }
    }
    
/*  public void getx(){
        target = latestResult.getBestTarget();


    }
 */
    public double getAmbiguity(){
        if (latestResult != null && latestResult.hasTargets()){
            PhotonTrackedTarget target = latestResult.getBestTarget();

            return target.getPoseAmbiguity();
        } else {
            return -1.0;
        }

    }


    //returns an array list of all april tag IDs seen by the camera.
    public List<Double> getIDs(){
        List<Double> targetIDs = new ArrayList<>();
        if(latestResult != null && latestResult.hasTargets()){
            for (var target : latestResult.getTargets()){
                targetIDs.add(targetIDs.size(), (double) target.getFiducialId());
            }
        }
        return targetIDs;
    }

    //returns the yaw of a desired target if that target is seen by the camera.
    public OptionalDouble getTargetYaw(int tagID){
        if(latestResult != null && latestResult.hasTargets()){
            for (var target : latestResult.getTargets()){
                if (target.getFiducialId() == tagID){
                    return OptionalDouble.of(target.getYaw());
                }
            }
        }
        return OptionalDouble.empty();
    }

    //returns the pitch of a desired target if that target is seen by the camera.
    public OptionalDouble getTargetPitch(int tagID){
        if(latestResult != null && latestResult.hasTargets()){
            for (var target : latestResult.getTargets()){
                if (target.getFiducialId() == tagID){
                    return OptionalDouble.of(target.getPitch());
                }
            }
        }
        return OptionalDouble.empty();
    }

    public double getAnyYaw(){
        if (latestResult != null && latestResult.hasTargets()){
            var target = latestResult.getBestTarget();
            return target.getYaw();
        } else {
            return 0.0;
        }

    }

        public double getAnyPitch(){
        if (latestResult != null && latestResult.hasTargets()){
            var target = latestResult.getBestTarget();
            return target.getPitch();
        } else {
            return 0.0;
        }
    }

    //returns a boolean for if the camera sees a target.
    public boolean targetVisible(){
        return latestResult != null && latestResult.hasTargets();
    }

    //returns a boolean for if the camera sees a desired target.
    public boolean hasTarget(int tagID){
        return getTargetYaw(tagID).isPresent();
    }

    public Transform3d findRobotPos(){
        var cameraResult = latestResult.getMultiTagResult();
        var fieldToCamera = cameraResult.get().estimatedPose.best;
        return fieldToCamera;
    }

}
