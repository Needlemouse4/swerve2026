// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//test
package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import frc.robot.commands.Drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.commands.ToggleIntake;

import frc.robot.generated.TurretTunerConstants;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElasticData;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.VisionData;
//import frc.robot.subsystems.SmartDashboardHub;
import frc.robot.commands.ClimbBackward;   
import frc.robot.commands.ClimbForward;

//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
//import edu.wpi.first.wpilibj2.command.button.Trigger;
//import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.HoodCommand;
import frc.robot.commands.IndexAndSpindexCommand;
import frc.robot.commands.FlywheelCommand;
import frc.robot.commands.TurretLeft;
import frc.robot.commands.TurretRight;
import frc.robot.commands.TurretScan;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexAndSpindexSubsystem;
import frc.robot.subsystems.FlywheelSubsystem;
import frc.robot.subsystems.TurretMovement;
import frc.robot.subsystems.TurretVision;
import frc.robot.Elastic.ElasticContainer;

public class RobotContainer {
    private double MaxSpeed = 1.0 * TurretTunerConstants.kSpeedAt12Volts.in(MetersPerSecond) / 3.5; // kSpeedAt12Volts desired top speed
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

    private final double MaxYaw = 32;

    private final double speedDamper = 3.5;

    private final double desiredDistance = 2;

    private final double MaxDistance = 32;

    private double driveWithAprilTag = 1;

    private double driveWithStick = 0;

    private final Intake intake = new Intake();
  
    private final Pneumatics Pneumatics = new Pneumatics();

    private final ClimberSubsystem climber = new ClimberSubsystem();
    //private final SmartDashboardHub smartDashboardSubsystem = new SmartDashboardHub();


    private final TurretMovement m_shooter = new TurretMovement();
    private final TurretVision m_turretvision = new TurretVision();
    public FlywheelSubsystem flywheelSubsystem = new FlywheelSubsystem();
    public HoodSubsystem hoodSubsystem = new HoodSubsystem();
    public IndexAndSpindexSubsystem InSSubsystem = new IndexAndSpindexSubsystem();

    public static final CommandXboxController joystick = new CommandXboxController(0);
    public static final CommandXboxController operatorController = new CommandXboxController(1);

    final ToggleIntake activation = new ToggleIntake(Pneumatics, intake);

    public final CommandSwerveDrivetrain drivetrain = TurretTunerConstants.createDrivetrain();
    public final ElasticContainer elastic;

    public RobotContainer() {
        SmartDashboard.putNumber("flywheelSpeed", 0);
        SmartDashboard.putNumber("hood target position", 0);

        SmartDashboard.putNumber("kV", 0.1);
        SmartDashboard.putNumber("kP", 0.4);
        SmartDashboard.putNumber("kI", 0.0);
        SmartDashboard.putNumber("kD", 0.0);
        elastic = new ElasticContainer(this,logger);
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(Math.max(-MaxSpeed, Math.min((((((vision.getDistance() - desiredDistance) / MaxDistance) * driveWithAprilTag) + (-joystick.getLeftY() * driveWithStick)) * MaxSpeed), MaxSpeed))) //Drive forward with negative Y (forward)
                    .withVelocityY(((((-vision.getAnyYaw()/MaxYaw) * driveWithAprilTag) + (-joystick.getLeftX() * driveWithStick)) * MaxSpeed) / speedDamper) //Drive left with negative X (left)
                    .withRotationalRate(((((1 - (vision.getZRotation()/Math.PI)) * driveWithAprilTag ) + (-joystick.getRightX() * driveWithStick)) * MaxAngularRate) / speedDamper) // Don't rotate Drive counterclockwise with negative X (left)
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
        joystick.y().onTrue(toggleJoystix());
        //joystick.leftTrigger(0.5).whileTrue(moveAprilTagLeft());
        //joystick.rightTrigger(0.5).whileTrue(moveAprilTagRight());
        operatorController.a().whileTrue(new ClimbBackward(climber));
        operatorController.b().whileTrue(new ClimbForward(climber));
        operatorController.x().whileTrue(new Drive(intake));
        operatorController.y().onTrue(activation);//onTrue(getAutonomousCommand());//(new Activation(Pneumatics));
        joystick.a().onTrue(elastic.fieldWidget.getAuto("Test Wait Command"));
        //joystick.b().onTrue(elastic.fieldWidget.getAuto("First Test"));
        joystick.x().onTrue(elastic.fieldWidget.getAuto("Test Auto"));
        joystick.y().onTrue(elastic.fieldWidget.getAuto("HPR"));
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));
        joystick.leftTrigger().onTrue(new TurretScan(m_turretvision, m_shooter));
        // calls the method that turns the stopButton for Scan to true
        joystick.rightTrigger().onTrue(m_shooter.runOnce(()->m_shooter.getStopCommand()));
        joystick.povLeft().whileTrue(new TurretLeft(m_turretvision, m_shooter));
        joystick.povRight().whileTrue(new TurretRight(m_turretvision, m_shooter));

        // Spin flywheel and start hood
        operatorController.a().onTrue(new FlywheelCommand(flywheelSubsystem, 20).andThen(new HoodCommand(hoodSubsystem, false, 0)));
    

        // Manual hood override
        operatorController.povUp().onTrue(new HoodCommand(hoodSubsystem, true, 0.1));
        operatorController.povUp().onFalse(new HoodCommand(hoodSubsystem, true, 0));
        operatorController.povDown().onTrue(new HoodCommand(hoodSubsystem, true, -0.1));
        operatorController.povDown().onFalse(new HoodCommand(hoodSubsystem, true, 0));

        // Regular Shooting
        joystick.x().onTrue(new IndexAndSpindexCommand(InSSubsystem, false));

        // Force Shoot
        operatorController.b().onTrue(new IndexAndSpindexCommand(InSSubsystem, true));

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    private final SendableChooser<Command> autoChooser = null;

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
            System.out.println("Toggle Runs");
            if (driveWithAprilTag == 1) {
                driveWithAprilTag = 0;
                driveWithStick = 1;
                System.out.println("Controller Controls");
            } else {
                driveWithStick = 0;
                driveWithAprilTag = 1;
                System.out.println("April Tag Controls");
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
