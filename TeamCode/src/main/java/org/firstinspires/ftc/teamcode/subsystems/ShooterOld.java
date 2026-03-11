package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ShooterOld {
    private DcMotorEx shooter;
    private DcMotorEx shooter2;

    private double ticksPerRot;
    public void init(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter1");
        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ticksPerRot = shooter.getMotorType().getTicksPerRev();
    }

    public void start(double power) {
        shooter.setVelocity(power);
        shooter2.setVelocity(power);
    }

    public void kill() {
        shooter.setVelocity(0);
        shooter2.setVelocity(0);
    }

    public double getVelo() {
        return shooter.getVelocity();
    }

}
