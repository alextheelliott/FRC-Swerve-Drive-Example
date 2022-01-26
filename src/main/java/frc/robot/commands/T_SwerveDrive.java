package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.constants.Constants.DriveTrain.*;

public class T_SwerveDrive extends CommandBase {
    private final DriveTrain driveTrain;
  
    public T_SwerveDrive() {
      driveTrain = DriveTrain.getInstance();
      addRequirements(driveTrain);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }
  
    public double pythag(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double forward = RobotContainer.driver.getX(Hand.kLeft);
        double strafeR = RobotContainer.driver.getY(Hand.kLeft);
        double rotatCW = RobotContainer.driver.getX(Hand.kRight);

        if(FIELD_CENTRIC_SWERVE) {
            double angle = Math.toRadians(driveTrain.getGyroAngle());
            double tempF =  (forward*Math.cos(angle)) + (strafeR*Math.sin(angle));
            double tempS = -(forward*Math.sin(angle)) + (strafeR*Math.cos(angle));
            forward = tempF;
            strafeR = tempS;
        }

        double l = L_WHEELBASE;
        double w = W_TRACKWIDTH;
        double r = pythag(l, w);

        double a = strafeR - (rotatCW*(l/r));
        double b = strafeR + (rotatCW*(l/r));
        double c = forward - (rotatCW*(w/r));
        double d = forward + (rotatCW*(w/r));

        driveTrain.setSwerveSpeed(
            pythag(b, c),
            pythag(b, d),
            pythag(a, d),
            pythag(a, c)
        );

        driveTrain.setSwerveAngle(
            Math.atan2(b, c) * (180/Math.PI),
            Math.atan2(b, d) * (180/Math.PI),
            Math.atan2(a, d) * (180/Math.PI),
            Math.atan2(a, c) * (180/Math.PI)
        );
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}