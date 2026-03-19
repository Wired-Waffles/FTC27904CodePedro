package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class TurretAutoTrack {
    private DcMotor turret;
    private GoBildaPinpointDriver pinpoint;
    private final double TICKS_PER_MOTOR_REV = 751.8;
    private final double GEAR_RATIO = 224.0 / 78.0;
    private final double TICKS_PER_DEGREE = (TICKS_PER_MOTOR_REV * GEAR_RATIO) / 360.0;

    private final double BLUE_GOAL_X = 12.0;
    private final double RED_GOAL_X = 132.0;
    private final double GOAL_Y = 132.0;

    private double manualOffsetDegrees = 0;
    private boolean isRedGoal = true;

    public void init(HardwareMap hardwareMap) {
        turret = hardwareMap.get(DcMotor.class, "turret");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setTargetPosition(0);
        turret.setPower(1.0); // Set power once for RUN_TO_POSITION
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
    }
    public void updateTracking(Telemetry telemetry) {
        pinpoint.update();
        Pose2D robotPose = pinpoint.getPosition();

        // 1. Determine target coordinates
        double targetX = isRedGoal ? RED_GOAL_X : BLUE_GOAL_X;

        // 2. Calculate Angle to Goal relative to Field
        double deltaX = targetX - robotPose.getX(DistanceUnit.INCH);
        double deltaY = GOAL_Y - robotPose.getY(DistanceUnit.INCH);
        double fieldAngleToGoal = Math.toDegrees(Math.atan2(deltaY, deltaX));

        // 3. Calculate Angle relative to Robot Heading
        double robotHeading = robotPose.getHeading(AngleUnit.DEGREES);
        double turretTargetAngle = fieldAngleToGoal - robotHeading;

        // 4. Add Manual Adjustment
        turretTargetAngle += manualOffsetDegrees;

        // 5. Normalize angle to -180 to 180
        while (turretTargetAngle > 180) turretTargetAngle -= 360;
        while (turretTargetAngle < -180) turretTargetAngle += 360;

        // 6. Mechanical Software Limits (Safety)
        if (turretTargetAngle > 45) turretTargetAngle = 45;
        if (turretTargetAngle < -45) turretTargetAngle = -45;

        // 7. Set Motor
        int targetTicks = (int) (turretTargetAngle * TICKS_PER_DEGREE);
        turret.setTargetPosition(targetTicks);
        telemetry.addData("heading", robotHeading);
        telemetry.addData("target ticks", targetTicks);
        telemetry.addData("target angle", turretTargetAngle);
    }

    // --- Helper Functions for TeleOp ---

    public void setRedGoal(boolean red) {
        this.isRedGoal = red;
    }

    public void adjustManualOffset(double deltaDegrees) {
        this.manualOffsetDegrees += deltaDegrees;
        // Optional: clamp the manual offset so it doesn't get crazy
        if (manualOffsetDegrees > 60) manualOffsetDegrees = 60;
        if (manualOffsetDegrees < -60) manualOffsetDegrees = -60;
    }

    public void resetManualOffset() {
        this.manualOffsetDegrees = 0;
    }
}