package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDriveRobotOrientated {
    private DcMotor frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive;
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

    public void drive(double drive, double strafe, double turn) {
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
        frontLeftDrive.setPower(speeds[0]);
        frontRightDrive.setPower(speeds[1]);
        backLeftDrive.setPower(speeds[2]);
        backRightDrive.setPower(speeds[3]);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", speeds[0], speeds[1]);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", speeds[2], speeds[3]);
        telemetry.update();
    }
    }
