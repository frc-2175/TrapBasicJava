/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc2175.TrapBasicJava;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Trap extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    SpeedController drivetrainLeft;
    SpeedController drivetrainRight;
    RobotDrive drivetrainRobotDrive;
    Solenoid drivetrainShifters;
    Solenoid shooterPlunger;
    SpeedController shooterWheels;
    //DoubleSolenoid hatchet;
    //boolean hatchetsUp = false;
    
    Compressor compressor;
    
    Joystick leftStick;
    Joystick rightStick;
    
    public void robotInit() {
        drivetrainLeft = new Talon(1, 1);
	LiveWindow.addActuator("Drivetrain", "Left", (Talon) drivetrainLeft);
        
        drivetrainRight = new Talon(1, 2);
	LiveWindow.addActuator("Drivetrain", "Right", (Talon) drivetrainRight);
        
        drivetrainRobotDrive = new RobotDrive(drivetrainLeft, drivetrainRight);
	
        drivetrainRobotDrive.setSafetyEnabled(true);
        drivetrainRobotDrive.setExpiration(0.1);
        drivetrainRobotDrive.setSensitivity(0.5);
        drivetrainRobotDrive.setMaxOutput(1.0);
        
        drivetrainShifters = new Solenoid(1, 4);
       	LiveWindow.addActuator("Drivetrain", "Shifters", (Solenoid) drivetrainShifters);
        drivetrainShifters.set(false);
        
        shooterPlunger = new Solenoid(1, 3);
       	LiveWindow.addActuator("Shooter", "Plunger", (Solenoid) shooterPlunger);
        shooterPlunger.set(false);
        
        shooterWheels = new Talon(1, 3);
	LiveWindow.addActuator("Shooter", "Shooter Wheels", (Talon) shooterWheels);
        
        /*hatchet = new DoubleSolenoid(1, 1, 2);
       	LiveWindow.addActuator("Hatchet", "Hatchet", (DoubleSolenoid) hatchet);
        hatchetUp = false;
        hatchet.set(DoubleSolenoid.Value.kReverse);*/
        
        compressor = new Compressor(1, 14, 1, 1);
        compressor.start();
        
        leftStick = new Joystick(1);
        rightStick = new Joystick(2);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        drivetrainRobotDrive.arcadeDrive(0.0,0.0);
        drivetrainShifters.set(false);
        
        shooterWheels.set(0.0);
        shooterPlunger.set(false);
        
        //hatchet.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        double forward = leftStick.getRawAxis(1);
        double limit = leftStick.getRawAxis(3);
        double lim_ratio = Math.abs(limit/forward);
        if (lim_ratio < 1.0)
            forward *= lim_ratio;
        double steer = rightStick.getRawAxis(2);
        drivetrainRobotDrive.arcadeDrive(forward, steer);
        drivetrainShifters.set(false);
        
        boolean spinWheels = leftStick.getRawButton(1);
        boolean fire = rightStick.getRawButton(1);
        shooterWheels.set(spinWheels?-0.6:0.0);
        shooterPlunger.set(fire);
        
        
        //hatchet.set(DoubleSolenoid.Value.kOff);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
