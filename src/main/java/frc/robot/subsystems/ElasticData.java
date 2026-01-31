package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Telemetry;

public class ElasticData extends SubsystemBase{
    PhotonVision cameraData = new PhotonVision("testingCamera");
    private final Telemetry telemetry;

    public ElasticData(Telemetry m_telemetry){
        telemetry = m_telemetry;
    }

    @Override
    public void periodic(){
        cameraData.update();
        boolean targetVisible = cameraData.targetVisible();
        double[] targetIDs = cameraData.getIDs().stream()
        .mapToDouble(Double::doubleValue)
        .toArray();
        double ambiguity = cameraData.getAmbiguity();

        SmartDashboard.putNumberArray("Target IDs", targetIDs);
        SmartDashboard.putBoolean("Target Visible", targetVisible);
        SmartDashboard.putNumber("Ambiguity", ambiguity);
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
        SmartDashboard.putData("Swerve Drive", new Sendable() {

      @Override      
        public void initSendable(SendableBuilder builder) {
          builder.setSmartDashboardType("SwerveDrive");
      
          builder.addDoubleProperty("Front Left Angle", () -> telemetry.m_moduleDirections[0].getAngle() /* * 2 * Math.PI */, null);
          builder.addDoubleProperty("Front Left Velocity", () -> telemetry.m_moduleSpeeds[0].getLength(), null);
      
          builder.addDoubleProperty("Front Right Angle", () -> telemetry.m_moduleDirections[2].getAngle() /* * 2 * Math.PI */, null);
          builder.addDoubleProperty("Front Right Velocity", ()  -> telemetry.m_moduleSpeeds[2].getLength(), null);
      
          builder.addDoubleProperty("Back Left Angle", () -> telemetry.m_moduleDirections[3].getAngle() /*  * 2 * Math.PI */, null);
          builder.addDoubleProperty("Back Left Velocity", () -> telemetry.m_moduleSpeeds[3].getLength(), null);
      
          builder.addDoubleProperty("Back Right Angle", () -> telemetry.m_moduleDirections[1].getAngle() /*  * 2 * Math.PI */, null);
          builder.addDoubleProperty("Back Right Velocity", () -> telemetry.m_moduleSpeeds[1].getLength(), null);
      
         // builder.addDoubleProperty("Robot Angle", () -> elasticContainer.robotAngle / 2 * Math.PI, null);
        } 
      });
    }

    



}
