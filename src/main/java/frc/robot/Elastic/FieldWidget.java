
package frc.robot.Elastic;

import java.util.ArrayList;
import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPoint;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FieldWidget<Elastic> extends SubsystemBase{

//Initialization for Field and Trajectories
public Field2d m_Field = new Field2d();
public Trajectory elastic = new Trajectory();
//public Trajectory Path2 = new Trajectory();
//public Trajectory Path3 = new Trajectory();


//Field Widget
public FieldWidget() {
    SmartDashboard.putData("Field", m_Field);
    //AutoBuilder.configure(null, null, null, null, null, null, null, null);
}

public void addPath(String pathName,PathPlannerPath path){
    m_Field.getObject(pathName).setPoses(path.getPathPoses());
}

public Command getAuto(String name) {
    try{
        
    //PathPlannerPath path = PathPlannerPath.fromPathFile(name);
    //return AutoBuilder.followPath(path).andThen(null);
        var autoTest = AutoBuilder.buildAuto(name);
        return autoTest;
    }catch(Exception e){
        System.out.print(e.getMessage());
        return null;
    }

}
//Auto Chooser Widget
    public void addChooser(){
        try{
            SendableChooser<PathPlannerPath> sendableChooser = new SendableChooser<>();
            sendableChooser.setDefaultOption("Elastic Test", PathPlannerPath.fromPathFile("Elastic Test"));
            sendableChooser.addOption("Human Player Left", PathPlannerPath.fromPathFile("Human Player Left"));
            sendableChooser.addOption("Human Player Mid", PathPlannerPath.fromPathFile("Human Player Mid"));
            sendableChooser.addOption("Human Player Right", PathPlannerPath.fromPathFile("Human Player Right"));
            sendableChooser.addOption("Neutral Left", PathPlannerPath.fromPathFile("Neutral Left"));
            sendableChooser.addOption("Neutral Right", PathPlannerPath.fromPathFile("Neutral Right"));
            sendableChooser.addOption("Neutral Mid", PathPlannerPath.fromPathFile("Neutral Mid"));
            sendableChooser.addOption("Depot Right", PathPlannerPath.fromPathFile("Depot Right"));
            sendableChooser.addOption("Depot Left", PathPlannerPath.fromPathFile("Depot Left"));
            sendableChooser.addOption("Depot Mid", PathPlannerPath.fromPathFile("Depot Mid"));
            sendableChooser.addOption("Shoot+Climb", PathPlannerPath.fromPathFile("Shoot+Climb"));
            sendableChooser.addOption("Long Auto Test", PathPlannerPath.fromPathFile("New Auto"));

//PathPlannerAuto autoTest = new PathPlannerAuto("");
//autoTest;
//IDK IF I NEED THESE
//PathPlannerPath testPATH = PathPlannerPath.fromPathFile("Neutral Right");

SmartDashboard.putData("Auto Choice", sendableChooser);
            
            sendableChooser.onChange((path)->{
                if(path != null) addPath("AutoPath", path);
            });

        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
      


//Trajectory Generation
public void TrajGen(){

          
            // First Trajectory Generation
             elastic = TrajectoryGenerator.generateTrajectory(
                new Pose2d(7, 2, Rotation2d.fromDegrees(0)),
                List.of(new Translation2d(8.1, 1.3), new Translation2d(
                8.1, 1.3)),
                new Pose2d(3.7, 3.7, Rotation2d.fromDegrees(90)),
                new TrajectoryConfig(Units.metersToFeet(1.0), Units.metersToFeet(1.0))
                );
                m_Field.getObject("Elastic Test").setTrajectory(elastic);


                // Second Trajectory Generation
           /*  Path2 = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
                List.of(new Translation2d(1, 1), new Translation2d(
                2, -1)),
                new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
                new TrajectoryConfig(Units.metersToFeet(3.0), Units.metersToFeet(3.0))
                );
                m_Field.getObject("Path2").setTrajectory(Path2);*/
             
                // Third Trajectory Generation
           /* Path3 = TrajectoryGenerator.generateTrajectory(
                new Pose2d(9, 0, Rotation2d.fromDegrees(0)),
                List.of(new Translation2d(1, 1), new Translation2d(
                2, -1)),
                new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
                new TrajectoryConfig(Units.feetToMeters(3.0), Units.feetToMeters(3.0))
                );
                m_Field.getObject("Path3").setTrajectory(Path3); */
}
}

