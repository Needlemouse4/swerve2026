package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Telemetry;
import frc.robot.generated.OldTunerConstants;
import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;



public class ElasticData extends SubsystemBase{
    private final Telemetry telemetry;
    private final PhotonVision cameraData;
    Field2d Field2d = new Field2d();
    VisionData VisionData = new VisionData("testingCamera");
    double Hubwidth = 0.6;
    double Radius = 3;
    double Rotation1 = 0.0;
    boolean fuelMakeIt = false;
    public ElasticData(Telemetry m_telemetry, PhotonVision camera){
        telemetry = m_telemetry;
        cameraData = camera;
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
      
                builder.addDoubleProperty("Robot Angle", () -> telemetry.m_poseArray[2], null);
            } 
        });

    }
    @Override
    public void periodic(){
        double MaxSpeed = 1.0 * OldTunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
        boolean targetVisible = cameraData.targetVisible();
        double[] targetIDs = cameraData.getIDs().stream()
        .mapToDouble(Double::doubleValue)
        .toArray();
        double yRotation = cameraData.getYRotation();
        double xRotation = cameraData.getXRotation();
        double zRotation = cameraData.getZRotation();
            //Pose2d.
            //= new Pose2d(cameraData.getRobotPos().getX(), cameraData.getRobotPos().getY(), cameraData.getRobotPos().getRotation().toRotation2d());
        double ambiguity = cameraData.getAmbiguity();
        double distance = cameraData.getDistance();
        SmartDashboard.putNumber("raw pitch", cameraData.getAnyPitch());
        SmartDashboard.putNumber("raw yaw", cameraData.getAnyYaw());
        SmartDashboard.putNumberArray("Target IDs", targetIDs);
        SmartDashboard.putBoolean("Target Visible", targetVisible);
        SmartDashboard.putNumber("Ambiguity", ambiguity);
        SmartDashboard.putNumber("Y Rotation", yRotation);
        SmartDashboard.putNumber("X Rotation", xRotation);
        SmartDashboard.putNumber("Z Rotation", zRotation);
        SmartDashboard.putNumber("Distance", distance);
        SmartDashboard.putData("Field",Field2d);
        //SmartDashboard.putNumber("Speed", Math.max(-MaxSpeed, Math.min(((((cameraData.getDistance() - 1.5) * 1) + (-1 * 0 )) * MaxSpeed) / 3.5, MaxSpeed)));
        if(cameraData.targetVisible() == true){
            //VisionData.findRobotPos();
            SmartDashboard.putData("Robot Position", cameraData.getRobotPos());
        }
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
        

        //AC- Additional Field2d Stuff
        Field2d.getObject("Hub").setPose(4.6,4,new Rotation2d(0.0));
        double Rotation =  new TurretMovement().returnMotor().getPosition().getValueAsDouble();
        SmartDashboard.putBoolean("Will the Fuel make it?", fuelMakeIt);
        Field2d.getObject("Aim").setPose(Field2d.getRobotPose().getX() +  (Radius * (Math.cos(Rotation))),Field2d.getRobotPose().getY() + (Radius * (Math.sin(Rotation))),new Rotation2d(Rotation));
        Pose2d AimPose = Field2d.getObject("Aim").getPose();
        Pose2d HubPose = Field2d.getObject("Hub").getPose();
        if(AimPose.getX() >= (HubPose.getX()-(Hubwidth/2)) && AimPose.getX() <= (HubPose.getX()+(Hubwidth/2))){
            if (AimPose.getY() >= (HubPose.getY()-Hubwidth) && AimPose.getY() <= (HubPose.getY()+Hubwidth)) {
                fuelMakeIt = true;
            }
            else{
                fuelMakeIt = false;
            }
        }
        else{
            fuelMakeIt = false;
        }
        
        SmartDashboard.updateValues();


        
    }

    



}
