package frc.robot.Elastic;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorOutputStatusValue;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.Odometry;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog.MotorLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.ObjectInputFilter.Config;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ejml.equation.Variable;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.Elastic.ElasticContainer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class FieldAndTraj extends SubsystemBase {
  

    public Field2d Fyield = new Field2d();
    public Trajectory Path1 = new Trajectory();
    public Trajectory Path2 = new Trajectory();
    public Trajectory Path3 = new Trajectory();
    public Trajectory Nothing = new Trajectory();
    public final SendableChooser<Command> m_Autochooser = new SendableChooser<>();

    public void debug() {
        // 1. Generate all trajectories
        System.out.println("Generating trajectories...");
        
        Path1 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(7, 2, Rotation2d.fromDegrees(0)),
            List.of(new Translation2d(1.8, 1.3), new Translation2d(1.8, 1.3)),
            new Pose2d(3.7, 3, Rotation2d.fromDegrees(90)),
            new TrajectoryConfig(Units.metersToFeet(1.0), Units.metersToFeet(1.0))
        );
        
        Path2 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(3.7, 3, Rotation2d.fromDegrees(90)),
            List.of(new Translation2d(4.5, 4), new Translation2d(5.5, 4)),
            new Pose2d(6.3, 4.5, Rotation2d.fromDegrees(180)),
            new TrajectoryConfig(Units.metersToFeet(1.0), Units.metersToFeet(1.0))
        );
        
        Path3 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(6.3, 4.5, Rotation2d.fromDegrees(180)),
            List.of(new Translation2d(5, 5), new Translation2d(4, 5)),
            new Pose2d(3, 5.5, Rotation2d.fromDegrees(270)),
            new TrajectoryConfig(Units.metersToFeet(1.0), Units.metersToFeet(1.0))
        );
    
        // 2. Verify trajectories generated
        System.out.println("Path1 generated: " + (Path1 != null));
        System.out.println("Path2 generated: " + (Path2 != null));
        System.out.println("Path3 generated: " + (Path3 != null));
    
        // 3. Set up dashboard
        SmartDashboard.putData("Auto Choice", m_Autochooser);
        SmartDashboard.putData("Field", Fyield);
        
        // 4. Set up chooser options
        m_Autochooser.setDefaultOption("Path1", Path1C());
        m_Autochooser.addOption("Path2", Path2C());
        m_Autochooser.addOption("Path3", Path3C());
        SmartDashboard.putData("Auto choices", m_Autochooser);
    
        // 5. Set initial trajectory
        Fyield.getObject("Trajectory").setTrajectory(Path1);
    } 
        
          
    public FieldAndTraj() {
          // 5. Add onChange listener with debug
          m_Autochooser.onChange((Command command) -> {
            System.out.println("Chooser selection changed to: " + command);
            updateTrajectoryDisplay(command);
        });
    }

  
        private void updateTrajectoryDisplay(Command command) {
            try {
                if (command.equals(Path1C())) {
                    Fyield.getObject("Trajectory").setTrajectory(Path1);
                    System.out.println("Updated to Path1");
                } else if (command.equals(Path2C())) {
                    Fyield.getObject("Trajectory").setTrajectory(Path2);
                    System.out.println("Updated to Path2");
                } else if (command.equals(Path3C())) {
                    Fyield.getObject("Trajectory").setTrajectory(Path3);
                    System.out.println("Updated to Path3");
                }
            } catch (Exception e) {
                System.out.println("Error updating trajectory: " + e);
            }


    }



    /* 
    public Field2d Fyield = new Field2d();
    public Trajectory Path1 = new Trajectory();
    public Trajectory Path2 = new Trajectory();
    public Trajectory Path3 = new Trajectory();
    public Trajectory Nothing = new Trajectory();

    final SendableChooser<Command> m_Autochooser = new SendableChooser<>();

  

    public FieldAndTraj() {

        SmartDashboard.putData("Auto Choice", m_Autochooser);
        SmartDashboard.putData("Field", Fyield);
        m_Autochooser.setDefaultOption("Path1", Path1C());
        m_Autochooser.addOption("Path2", Path2C());
        m_Autochooser.addOption("Path3", Path3C());
       // SmartDashboard.putData("Path1", m_Path1);
        //Fyield.show_trajectories = true;
      // SmartDashboard.putData("test", Path3C());


       Fyield.getObject("Path1").setTrajectory(Path1);

        robotInit();

        
        m_Autochooser.onChange((Command command) -> {
            System.out.println("Auto selection changed: " + command); // Debug print
            
            if (command.equals(Path1C())) {
                System.out.println("Setting Path1");
                Fyield.getObject("Selected Path").setTrajectory(Path1);
                SmartDashboard.putString("Current Path", "Path1");
            } 
            else if (command.equals(Path2C())) {
                System.out.println("Setting Path2");
                Fyield.getObject("Selected Path").setTrajectory(Path2);
                SmartDashboard.putString("Current Path", "Path2");
            } 
            else if (command.equals(Path3C())) {
                System.out.println("Setting Path3");
                Fyield.getObject("Selected Path").setTrajectory(Path3);
                SmartDashboard.putString("Current Path", "Path3");
            }
                
    });
}

    public void robotInit(){

          Nothing = TrajectoryGenerator.generateTrajectory(
            new Pose2d(7, 2, Rotation2d.fromDegrees(0)),
            List.of(new Translation2d(1.8, 1.3), new Translation2d(
            1.8, 1.3)),
            new Pose2d(3.7, 3, Rotation2d.fromDegrees(90)),
            new TrajectoryConfig(Units.metersToFeet(1.0), Units.metersToFeet(1.0))
            );


        Path1 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(7, 2, Rotation2d.fromDegrees(0)),
            List.of(new Translation2d(1.8, 1.3), new Translation2d(
            1.8, 1.3)),
            new Pose2d(3.7, 3, Rotation2d.fromDegrees(90)),
            new TrajectoryConfig(Units.metersToFeet(1.0), Units.metersToFeet(1.0))
            );
          
      


            Path2 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
            List.of(new Translation2d(1, 1), new Translation2d(
            2, -1)),
            new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
            new TrajectoryConfig(Units.metersToFeet(3.0), Units.metersToFeet(3.0))
            );
          
         


            Path3 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
            List.of(new Translation2d(1, 1), new Translation2d(
            2, -1)),
            new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
            new TrajectoryConfig(Units.feetToMeters(3.0), Units.feetToMeters(3.0)));
           
           
           
     };

    
public void attemptTrajectory() {

    Command selectedPath = m_Autochooser.getSelected();
        
        if (selectedPath == Path1C()){
            Fyield.getObject("Path1").setTrajectory(Path1);
        }
        else if (selectedPath == Path2C()){
            Fyield.getObject("Path2").setTrajectory(Path2);
        }
        else if (selectedPath == Path3C()){
            Fyield.getObject("Path3").setTrajectory(Path3);
        };
    };

 


public void trajTest(){

    Fyield.getObject("Path1").setTrajectory(Path1);



}

*/

    public Command Path1C(){

        return run(
        ()->{
        Fyield.getObject("Path1").setTrajectory(Path1);
        

    
        });
    }
    
    public Command Path2C(){
    
      return run(
        ()->{
        
        Fyield.getObject("Path2").setTrajectory(Path2);
        
    
        });
    }
    
    public Command Path3C(){
    
        return run(
            ()->{
        
        Fyield.getObject("Path3").setTrajectory(Path3);
    
            });
    }
    


}
