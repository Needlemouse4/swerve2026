// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//test
package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.hal.ThreadsJNI;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.OldTunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElasticData;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.VisionData;

public class RobotContainer {
    private double MaxSpeed = 1.0 * OldTunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity
    //MaxSpeed * 
    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.2).withRotationalDeadband(MaxAngularRate * 0.2) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
            private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private PhotonVision vision = new PhotonVision("testingCamera");

    private ElasticData elasticData = new ElasticData(logger, vision);

    private final double MaxYaw = 29;

    private final double speedDamper = 3.5;

    private final double desiredDistance = 2;

    private double driveWithAprilTag = 1;

    private double driveWithStick = 0;

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = OldTunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX((((vision.getDistance() - desiredDistance) * driveWithAprilTag) + (-joystick.getLeftY() * driveWithStick) * MaxSpeed) / speedDamper) //Drive forward with negative Y (forward)
                    .withVelocityY((((-vision.getAnyYaw()/MaxYaw) * driveWithAprilTag) + (-joystick.getLeftX() * driveWithStick) * MaxSpeed) / speedDamper) //Drive left with negative X (left)
                    .withRotationalRate((((1 - (vision.getZRotation()/Math.PI)) * driveWithAprilTag ) + (-joystick.getRightX() * driveWithStick) * MaxAngularRate) / speedDamper) // Don't rotate Drive counterclockwise with negative X (left)
                )       
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
        joystick.x().onTrue(pipelineSwitcher());
        joystick.a().onTrue(toggleJoystix());
        //joystick.leftTrigger(0.5).whileTrue(moveAprilTagLeft());
        //joystick.rightTrigger(0.5).whileTrue(moveAprilTagRight());

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        final var idle = new SwerveRequest.Idle();
        return Commands.sequence(
            // Reset our field centric heading to match the robot
            // facing away from our alliance station wall (0 deg).
            drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
            // Then slowly drive forward (away from us) for 5 seconds.
            drivetrain.applyRequest(() ->
                drive.withVelocityX(0.5)
                    .withVelocityY(0)
                    .withRotationalRate(0)
            )
            .withTimeout(5.0),
            // Finally idle for the rest of auton
            drivetrain.applyRequest(() -> idle)
        );
    }
    public Command moveAprilTagLeft() {
        if ((vision.getAnyYaw() <= 10) && (vision.getAnyYaw() >= -10)) {
            return Commands.sequence(
                drivetrain.applyRequest(() ->
                drive.withRotationalRate(-1 * MaxAngularRate) // Drive so that april tag is left 
                // Note ** prolly change -1 and 1 in the next Command to a variable
                            )
            ); //Rotate the robot right
        } else {
            return Commands.sequence(); //Do nothing     
        }
    }
    public Command moveAprilTagRight() {
       if ((vision.getAnyYaw() <= 10) && (vision.getAnyYaw() >= -10)) {
            return Commands.sequence(
                
                drivetrain.applyRequest(() ->
                drive.withRotationalRate(1 * MaxAngularRate) // Drive so that april tag is right
                            )
            ); //Rotate the robot left
        } else {
            return Commands.sequence(); //Do nothing     
        }
    }

    public Command toggleJoystix(){
        return Commands.runOnce(()->{
            if (driveWithAprilTag == 1) {
                driveWithAprilTag = 0;
                driveWithStick = 1;
            } else {
                driveWithStick = 0;
                driveWithAprilTag = 1;
                //This is a toggle button in between the two, I use 1 and 0 instead of true and false because it is a number that is multiplyed by the different speeds.
            }
        });
    }

    public Command pipelineSwitcher(){
        VisionData visionData = new VisionData("testingCamera");
        return Commands.runOnce(()->{
            if (visionData.getPipelineMethod() == 0) {
                visionData.pipelineSwitcher(1);
                SmartDashboard.putNumber("Pipeline",visionData.getPipelineMethod());
            }
            else{
                visionData.pipelineSwitcher(0);
                SmartDashboard.putNumber("Pipeline",visionData.getPipelineMethod());
            }
        });
        
        
        
    }
}
