// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
    
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

/** An example command that uses an example subsystem. */
public class ClimbBackward extends Command {
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  ClimberSubsystem Pneumatics;
    private boolean interrupted;
    public ClimbBackward(ClimberSubsystem Pneumatics) {
      // Use addRequirements() here to declare subsystem dependencies.
      this.Pneumatics = Pneumatics;
      addRequirements(Pneumatics);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      Pneumatics.backward();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {                  
  
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
     // Called when the cool stuff happens i guess?
  
  }
  //hoi!