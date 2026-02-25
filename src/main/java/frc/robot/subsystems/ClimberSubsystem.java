// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//                         

//    >:3
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//cool stuff is happening
public class ClimberSubsystem extends SubsystemBase {

   // Compressor connected to a PCM with a default CAN ID (0)
  public static final PneumaticsControlModule pneumaticsControlModule = new PneumaticsControlModule(10);
  //creates the pcm10
   private final Compressor m_compressor = new Compressor(10, PneumaticsModuleType.CTREPCM);
private final DoubleSolenoid solenoid = new DoubleSolenoid(10, PneumaticsModuleType.CTREPCM, 1,0);
//creates channel. forward channel is 1. backward channel is 0
  /** Creates a new can ball!!!!!
   * 
   */
  public ClimberSubsystem() {   m_compressor.enableDigital();}

    
  
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor). digital censor. digital center. digital canter, digital circus
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run. "are you sure" - omniman
  }

  @Override
  public void simulationPeriodic() {
    //lock in bro
  }
public void forward (){
 solenoid.set(DoubleSolenoid.Value.kForward);
}

public void backward () {
 solenoid.set(DoubleSolenoid.Value.kReverse);
}
 
}
//qwertyuiopasdfghjklzxcvbnmabcdefghijklmnopqrstuvwxyzqazwsxedcrfvtgbyhnujmikolp12345678908.1853412