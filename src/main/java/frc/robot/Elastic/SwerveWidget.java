package frc.robot.Elastic;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Telemetry;
import frc.robot.generated.TunerConstants;
import static edu.wpi.first.units.Units.*;
import com.ctre.phoenix6.SignalLogger;


public class SwerveWidget extends SubsystemBase{

    // Telemetry instance for logging swerve drive data.
    private final Telemetry telemetry;

    // Initializes Phoenix 6 signal logging and telemetry with configured max speed.
    public SwerveWidget(ElasticContainer elasticContainer, Telemetry _telemetry) {
        SignalLogger.start();

        telemetry = _telemetry;
        smartDash(elasticContainer);
}

    // Publishes swerve drive data to SmartDashboard.
    public void smartDash(ElasticContainer elasticContainer) {
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
      
          builder.addDoubleProperty("Robot Angle", () -> elasticContainer.robotAngle / 2 * Math.PI, null);
        } 
      });
  }
}


