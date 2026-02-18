package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor intake;

    private double ticksPerRot;
    public void init(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ticksPerRot = intake.getMotorType().getTicksPerRev();
    }

    public void intakePower(double power) {
        intake.setPower(power);
    }

    public double getTotalRots (){
        double gearRatio = 5;
        return intake.getCurrentPosition() / ticksPerRot * gearRatio;
    }
}
