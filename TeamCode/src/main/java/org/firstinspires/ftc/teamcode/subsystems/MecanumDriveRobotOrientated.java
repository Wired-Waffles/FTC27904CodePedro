package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumDriveRobotOrientated {
    private DcMotor frontLeftDrive,
            frontRightDrive,
            backLeftDrive,
            backRightDrive;
    private ElapsedTime runtime = new ElapsedTime();
    public void init(HardwareMap hardwareMap) {
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right");
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void drive(double drive, double strafe, double turn, Telemetry telemetry) {
        double[] speeds = {
                (drive + strafe + turn),
                (drive - strafe - turn),
                (drive - strafe + turn),
                (drive + strafe - turn)
                
        };


        double max = Math.abs(speeds[0]);
        for(int i = 0; i < speeds.length; i++) {
            if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
        }

        if (max > 1) {
            for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
        }
        frontLeftDrive.setPower(drive + strafe + turn);
        frontRightDrive.setPower(drive - strafe - turn);
        backLeftDrive.setPower(drive - strafe + turn);
        backRightDrive.setPower(drive + strafe - turn);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftDrive.getPower(), frontRightDrive.getPower());
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftDrive.getPower(), backRightDrive.getPower());
        telemetry.update();
    }
}
