package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Value;

import java.net.SocketImpl;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("unused")
public class Pneumatics extends SubsystemBase {
    public static final PneumaticsControlModule pneumaticsControlModule = new PneumaticsControlModule(10);
      private final Compressor compressor = new Compressor(10, PneumaticsModuleType.CTREPCM);
// DoubleSolenoid(channelForward, channelReverse)
    private final DoubleSolenoid solenoid = new DoubleSolenoid(10,
            PneumaticsModuleType.CTREPCM, 1, 0);

    private final DoubleSolenoid solenoid2 = new DoubleSolenoid(2,
            PneumaticsModuleType.CTREPCM, 2, 3);

    public Pneumatics() {
        // Enable closed-loop control for compressor
        compressor.enableDigital(); 
        
    }

    /*
    public void testMove(){
        System.out.println("******");
        System.out.println("Start State: "+ solenoid.get());

        boolean isReverse = solenoid.get() == DoubleSolenoid.Value.kReverse || solenoid.get() == DoubleSolenoid.Value.kOff;
        System.out.println("isReverse: "+isReverse);
        if(solenoid.get() == DoubleSolenoid.Value.kOff) System.out.println("KOFF");
        if(isReverse) solenoid.set(DoubleSolenoid.Value.kForward);
        else solenoid.set(DoubleSolenoid.Value.kReverse);
        
        System.out.println("End State: "+ solenoid.get());
        System.out.println("Done Test Move");
        
        System.out.println("******");
    }*/

    public void forwardSolenoid() {
        //solenoid.toggle();    
            solenoid.set(DoubleSolenoid.Value.kForward);
        }
    
        public void reverseSolenoid() {
            //solenoidTest.set(false);
                solenoid.set(DoubleSolenoid.Value.kReverse);
             }

    }

