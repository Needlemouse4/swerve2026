package frc.robot.Elastic;

import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import frc.robot.RobotContainer;
import frc.robot.Telemetry;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class ElasticContainer {

    public FieldWidget fieldWidget = new FieldWidget();
    //color color = new color();
    //Rumbling rumble = new Rumbling();
   // Alerting alert = new Alerting();
    //FieldAndTraj fieldAndTraj = new FieldAndTraj();
    private final SwerveWidget swervelogger;
    public final CommandSwerveDrivetrain drivetrain;
    Telemetry telemetry;
    public Double robotAngle;

    // Initializes the Elastic subsystems.
public ElasticContainer(RobotContainer robotContainer, Telemetry _telemetry) {
    telemetry = _telemetry;
    drivetrain = robotContainer.drivetrain;
    robotAngle = drivetrain
        .getRotation3d()
        .toRotation2d()
        .getRadians();
    swervelogger = new SwerveWidget(this,telemetry);


    try{
        //PathPlannerPath path = PathPlannerPath.fromPathFile("Elastic Test");
        fieldWidget.addChooser();
        //fieldWidget.addPath("ElasticTest", path);

    }catch(Exception e){
        System.out.println(e.getMessage());
    }
}
}
