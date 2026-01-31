package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElasticData extends SubsystemBase{
    PhotonVision cameraData = new PhotonVision("testingCamera");

    @Override
    public void periodic(){
        cameraData.update();
        boolean targetVisible = cameraData.targetVisible();
        double[] targetIDs = cameraData.getIDs().stream()
        .mapToDouble(Double::doubleValue)
        .toArray();

        SmartDashboard.putNumberArray("Target IDs", targetIDs);
        SmartDashboard.putBoolean("Target Visible", targetVisible);
        for(var id : targetIDs){
            double yaw = cameraData
            .getTargetYaw((int) id)
            .orElse(Double.NaN);
            if (!Double.isNaN(yaw)){
                SmartDashboard.putNumber("Target" + id + "yaw", yaw);
            }
        }
        for(var id : targetIDs){
            double pitch = cameraData
            .getTargetYaw((int) id)
            .orElse(Double.NaN);
            if (!Double.isNaN(pitch)){
                SmartDashboard.putNumber("Target" + id + "pitch", pitch);
            }
        }
    }





}
