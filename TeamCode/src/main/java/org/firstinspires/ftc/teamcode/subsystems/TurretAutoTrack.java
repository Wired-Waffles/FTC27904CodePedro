package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Objects;

public class TurretAutoTrack {
    private DcMotor turret;
    private final double TICKS_PER_MOTOR_REV = 751.8;
    private final double GEAR_RATIO = 225.0 / 78.0;
    private final double TICKS_PER_DEGREE = (TICKS_PER_MOTOR_REV * GEAR_RATIO) / 360.0;




    public void init(HardwareMap hardwareMap) {
        turret = hardwareMap.get(DcMotor.class, "turret");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setTargetPosition(0);
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void updateTracking(Follower follower, String colour) {
        Pose robotPose = follower.getPose();
        double GOAL_X;
        if (Objects.equals(colour, "blue")) {
            GOAL_X = 12;
        } else {
            GOAL_X = 132;
        }
        double GOAL_Y = 132;

        double deltaX = GOAL_X - robotPose.getX();
        double deltaY = GOAL_Y - robotPose.getY();
        double targetFieldAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));

        double robotHeading = Math.toDegrees(robotPose.getHeading());
        double turretTargetAngle = targetFieldAngle - robotHeading;

        while (turretTargetAngle > 180) turretTargetAngle -= 360;
        while (turretTargetAngle < -180) turretTargetAngle += 360;
        if (turretTargetAngle > 130) turretTargetAngle = 130;
        if (turretTargetAngle < -130) turretTargetAngle = -130;
        int targetTicks = (int) (turretTargetAngle * TICKS_PER_DEGREE);
        turret.setTargetPosition(targetTicks);
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void resetDrift(Follower follower, String colour) {
        double knownX = 10.0;
        double knownY = 9.0;
        if (Objects.equals(colour, "blue")) {
            knownX = 135.0;
        }
        double knownHeading = Math.toRadians(90);
        Pose resetPose = new Pose(knownX, knownY, knownHeading);

        follower.setPose(resetPose);
    }

}