package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class MecanumFieldCentricPinpoint {
    private DcMotor frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive;
    private GoBildaPinpointDriver pinpoint;

    private double headingOffset = 0;

    public void init(HardwareMap hardwareMap) {
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(-99.75, -111.23, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        pinpoint.resetPosAndIMU();
    }

    public void setTeam(boolean isBlue) {
        headingOffset = isBlue ? Math.PI : 0;
    }

    public void drive(double x, double y, double rx) {
        double botHeading = pinpoint.getHeading(AngleUnit.RADIANS) + headingOffset;

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);

        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftDrive.setPower(frontLeftPower);
        backLeftDrive.setPower(backLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backRightDrive.setPower(backRightPower);
    }

    public void resetHeading() {
        pinpoint.resetPosAndIMU();
    }

    public void resetPose(float x, float y, double headingDegrees) {
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, x, y, AngleUnit.DEGREES, headingDegrees));
    }
}