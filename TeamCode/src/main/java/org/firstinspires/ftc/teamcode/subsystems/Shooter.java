package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Objects;

public class Shooter {
    private DcMotorEx motor1, motor2;
    private double integralSum = 0;
    private double lastError = 0;
    private ElapsedTime timer = new ElapsedTime();
    private double targetVelocity;

    public void init(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotorEx.class, "shooter1");
        motor2 = hwMap.get(DcMotorEx.class, "shooter2");
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

    ShooterInterpolation shooterLUT = new ShooterInterpolation();
    GoBildaPinpointDriver pinpoint;



    public void initLUT() {
        //fake points rn, replace latr
        shooterLUT.addDataPoint(24.0, 1500);
        shooterLUT.addDataPoint(48.0, 1850);
        shooterLUT.addDataPoint(72.0, 2200);
        shooterLUT.addDataPoint(120.0, 2600);
    }

    public void updateShooterAuto(Telemetry telemetry, String colour) {
        pinpoint.update();
        int goalX = 0;
        if (Objects.equals(colour, "blue")) {
            goalX = 12;
        } else {
            goalX = 132;
        }
        double goalY = 132;

        double dx = goalX - pinpoint.getPosX(DistanceUnit.INCH);
        double dy = goalY - pinpoint.getPosY(DistanceUnit.INCH);
        double distance = Math.hypot(dx, dy);
        double dynamicTarget = shooterLUT.getVelocity(distance) + ShooterConstants.shotOffset;

        targetVelocity = dynamicTarget;

        telemetry.addData("Distance to Goal", distance);
        telemetry.addData("Target Velocity", dynamicTarget);
    }

}