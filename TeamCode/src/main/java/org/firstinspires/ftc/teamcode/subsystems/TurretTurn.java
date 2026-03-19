package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TurretTurn {
    private DcMotor turret;
    double ticksPerRot;
    public void init(HardwareMap hardwareMap) {
        turret = hardwareMap.get(DcMotor.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ticksPerRot = turret.getMotorType().getTicksPerRev();
    }

    public int getPos() {
        return turret.getCurrentPosition();
    }


    public void power(double power){
        turret.setPower(power);
    }
    public void kill(){
        turret.setPower(0);
    }
    public void reset(){
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
