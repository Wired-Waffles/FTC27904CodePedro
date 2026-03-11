package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    private DcMotorEx motor1, motor2;
    private double integralSum = 0;
    private double lastError = 0;
    private ElapsedTime timer = new ElapsedTime();
    private double targetVelocity;

    public void init(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotorEx.class, "shooter_left");
        motor2 = hwMap.get(DcMotorEx.class, "shooter_right");
        motor1.setDirection(DcMotorEx.Direction.FORWARD);
        motor2.setDirection(DcMotorEx.Direction.FORWARD);

        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        timer.reset();
    }

    public void update(Telemetry telemetry) {
        double dt = timer.seconds();
        if (dt == 0) return;

        double currentVelocity = motor1.getVelocity();
        double error = targetVelocity - currentVelocity;

        if (Math.abs(error) < 200) {
            integralSum += (error * dt);
        } else {
            integralSum = 0;
        }

        double derivative = (error - lastError) / dt;
        double out = (ShooterConstants.kP * error) +
                (ShooterConstants.kI * integralSum) +
                (ShooterConstants.kD * derivative) +
                (ShooterConstants.kF * targetVelocity);

        motor1.setPower(out);
        motor2.setPower(out);

        lastError = error;
        timer.reset();

        telemetry.addData("Shooter/Target", targetVelocity);
        telemetry.addData("Shooter/Actual", currentVelocity);
        telemetry.addData("Shooter/Current_mA", motor1.getCurrent(org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit.MILLIAMPS) + motor2.getCurrent(org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit.MILLIAMPS));
    }
    public void setVelo(double targetVelocity) {
        this.targetVelocity = targetVelocity;
    }
    public void kill(){
        setVelo(0);
    }
    public double getTargetVelo() {
        return targetVelocity;
    }

}