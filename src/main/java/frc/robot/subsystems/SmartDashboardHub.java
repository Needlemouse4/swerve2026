package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.IFollower;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotController.RadioLEDState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public class SmartDashboardHub extends SubsystemBase{

public DigitalInput TurretLimitSwitch1 = new DigitalInput(0);

public DigitalInput TurretLimitSwitch2 = new DigitalInput(1);

public DigitalInput TurretLimitSwitch3 = new DigitalInput(2);

public DigitalInput TurretLimitSwitch4 = new DigitalInput(3);

public TalonFX indexMotorFx = new TalonFX(0);

public TalonFX FRswerveMotordriveFx = new TalonFX(0);

public TalonFX FRswerveMotordirectionFx = new TalonFX(0);

public TalonFX FLswerveMotordriveFx = new TalonFX(0);

public TalonFX FLswerveMotordirectionFx = new TalonFX(0);

public TalonFX RRswerveMotordriveFx = new TalonFX(0);

public TalonFX RRswerveMotordirectionFx = new TalonFX(0);

public TalonFX RLswerveMotordriveFx = new TalonFX(0);

public TalonFX RLswerveMotordirectionFx = new TalonFX(0);

public TalonFX turretMotor1Fx = new TalonFX(0);

public TalonFX turretMotor2Fx = new TalonFX(0);

public TalonFX turretMotor3Fx = new TalonFX(0);

public TalonFX intakeMotorFx = new TalonFX(0);

public SparkMax spindex_SparkMax = new SparkMax(0, MotorType.kBrushless);

public Compressor compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);



    public SmartDashboardHub () {
        SmartDashboard.putBoolean("QC Check", false);

    }

    
    @Override
    public void periodic () {
        SmartDashboard.putNumber("test", 1);
           if (SmartDashboard.getBoolean("QC Check", false)) {
          
        SmartDashboard.putNumber("index voltage",indexMotorFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("spindex voltage",spindex_SparkMax.getBusVoltage());

        SmartDashboard.putNumber("FRSwerve direction voltage",FRswerveMotordirectionFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("FRSwerve drive 1 voltage",FRswerveMotordriveFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Swerve direction 2 voltage",FLswerveMotordirectionFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Swerve drive 2 voltage",FLswerveMotordriveFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Swere direction 3 voltage",RRswerveMotordirectionFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Swerve drive 3voltage",RRswerveMotordriveFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Swerve direction 4 voltage",RLswerveMotordirectionFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Swerve drive 4 voltage",RLswerveMotordriveFx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Turret M1 voltage",turretMotor1Fx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Turret M2 voltage",turretMotor2Fx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Turret M3 voltage",turretMotor3Fx.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putNumber("Intake voltage",intakeMotorFx.getMotorVoltage().getValueAsDouble());



        SmartDashboard.putNumber("Index RPM",indexMotorFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("Spindex RPM",spindex_SparkMax.getEncoder().getVelocity());

        SmartDashboard.putNumber("FRSwerve direction RPM",FRswerveMotordirectionFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("FRSwerve drive RPM",FRswerveMotordriveFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("FLSwerve direction RPM",FLswerveMotordirectionFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("FLSwerve drive RPM",FLswerveMotordriveFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("RRSwerve direction RPM",RRswerveMotordirectionFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("RRSwerve drive RPM",RRswerveMotordriveFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("RLSwerve direction RPM",RLswerveMotordirectionFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("RLSwerve drive RPM",RLswerveMotordriveFx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("turret M1 RPM",turretMotor1Fx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("turret M2 RPM",turretMotor2Fx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("turret M3 RPM",turretMotor3Fx.getVelocity().getValueAsDouble());

        SmartDashboard.putNumber("intake RPM",intakeMotorFx.getVelocity().getValueAsDouble());


        SmartDashboard.putNumber("Pressure",compressor.getPressure());

        SmartDashboard.putNumber("Turret Position",turretMotor1Fx.getPosition().getValueAsDouble());

        
        SmartDashboard.putBoolean("limit Switch1",TurretLimitSwitch1.get());

        SmartDashboard.putBoolean("limit switch2",TurretLimitSwitch2.get());

        SmartDashboard.putBoolean("limit switch3",TurretLimitSwitch3.get());

        SmartDashboard.putBoolean("limit switch4",TurretLimitSwitch4.get());

        SmartDashboard.putNumber("FRSwerve M1 direction",FRswerveMotordirectionFx.getPosition().getValueAsDouble());

        SmartDashboard.putNumber("FLSwerve M2 direction",FLswerveMotordirectionFx.getPosition().getValueAsDouble());

        SmartDashboard.putNumber("RRSwerve M3 direction",RRswerveMotordirectionFx.getPosition().getValueAsDouble());

        SmartDashboard.putNumber("RLSwerve M4 direction",RLswerveMotordirectionFx.getPosition().getValueAsDouble());
         }
        }

            }
