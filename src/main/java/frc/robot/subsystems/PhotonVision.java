package frc.robot.subsystems;

import java.util.List;
import java.util.OptionalDouble;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonVision extends SubsystemBase{
    VisionData vision;

    public PhotonVision(String cameraName){
        vision = new VisionData(cameraName);
    }

    @Override
    public void periodic(){
        vision.update();
    }


    public double getAmbiguity(){
        return vision.getAmbiguity();
    }

    public boolean hasTarget(int id) {
        return vision.hasTarget(id);
    }

    public boolean targetVisible() {
        return vision.targetVisible();
    }

    public double getAnyYaw() {
        return vision.getAnyYaw();
    }

    public double getAnyPitch() {
        return vision.getAnyPitch();
    }

    public List<Double> getIDs() {
        return vision.getIDs();
    }

    public OptionalDouble getTargetYaw(int id) {
        return vision.getTargetYaw(id);
    }

    public OptionalDouble getTargetPitch(int id) {
        return vision.getTargetPitch(id);
    }

    //public Pose2d getPose2d(){
        //return vision.getPose2d();
    //}

    public Field2d getRobotPos(){
        return vision.findRobotPos();
    }

    public double getYRotation(){
        return vision.getYRotation();
    }

    public double getXRotation(){
        return vision.getXRotation();
    }

    public double getZRotation(){
        return vision.getZRotation();
    }

    public double getDistance(){
        return vision.getDistance();
    }

}
