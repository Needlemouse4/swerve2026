// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;

import javax.naming.LimitExceededException;

import com.revrobotics.spark.config.LimitSwitchConfig;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
/** An example command that uses an example subsystem. 
 * @param <Drive>*/
@SuppressWarnings("unused")
public class Drive extends Command {
boolean limitSwitchStatus;
Intake motorSpin;
/**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public Drive(Intake motorSpin) {
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(motorSpin);
      this.motorSpin = motorSpin;
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      limitSwitchStatus =false;
      motorSpin.setFeederRoller(5);

     System.out.println("Drive Command Initialized");  
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   limitSwitchStatus = motorSpin.checkswitchstatus();
  }
 
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return limitSwitchStatus;
  }
}
