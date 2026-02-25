package frc.robot.subsystems.Elastic;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.RobotContainer;


public class Rumbling extends SubsystemBase {
    
public boolean rumble = false;

private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);


public Rumbling() {

        
            SmartDashboard.putBoolean("rumble toggle", rumble);


}


public Command RumbleToggle(){


    return run(
        () -> {
       
            if (rumble == false)
            { rumble = true; }
            else 
            { rumble = false; }

        });


}

public Command RumbleTest(){

 return run(
        () -> {
            m_driverController.setRumble(RumbleType.kLeftRumble, 1.0); 
            m_driverController.setRumble(RumbleType.kRightRumble, 1.0);
        });



}

public Command Rumbles(){


 return run(
        () -> {
            if (rumble == true)
            { m_driverController.setRumble(RumbleType.kLeftRumble, 1.0); 
            m_driverController.setRumble(RumbleType.kRightRumble, 1.0); }
            else 
            {  m_driverController.setRumble(RumbleType.kLeftRumble, 0.0);  
            m_driverController.setRumble(RumbleType.kRightRumble, 0.0); }
        });

}
}