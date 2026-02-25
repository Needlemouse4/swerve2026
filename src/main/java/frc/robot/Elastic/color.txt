package frc.robot.subsystems.Elastic;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.util.Elastic;

public class color extends SubsystemBase {

    Color red = new Color(88, 8, 8);
    Color green = new Color(8, 88, 8);
    Color blue = new Color(8, 8, 88);
     


   private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

public Command Coloring() {

  return run(
        () -> {
         //   SmartDashboard.putString("Tis Red", red.toHexString());

   
          //  SmartDashboard.putString("Tis Green", green.toHexString());
            
               
            SmartDashboard.putString( "Single Colour View", blue.toHexString());
        });




}



public color(){

 SmartDashboard.putString( "Single Colour View", blue.toHexString());

 SmartDashboard.putNumber( "Voltage:", voltage);

 SmartDashboard.putNumber( "Trigger:", Trigger);

 SmartDashboard.putStringArray("ColorArray", new String[] {red.toHexString(), green.toHexString(), blue.toHexString()});

}


double voltage = RobotController.getBatteryVoltage();

double Trigger = m_driverController.getRightTriggerAxis();


}