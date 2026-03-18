package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class BlueFromFarZoneAuto extends LinearOpMode {


    private Follower follower;
    private Timer pathTimer, opModeTimer;
    public enum PathState {
        Shoot,
        StartToCycleOne,
        CollectArtifactsCycleOne,
        CycleOneToShoot,
        ShootToCycleTwo,
        CollectArtifactsCycleTwo,
        CycleTwoToShoot,
        ShootToCycleThree,
        CollectArtifactsCycleThree,
        CycleThreeToShoot,
        ShootToEndPos
    }
    PathState pathState;
    private final Pose startPose = new Pose(56, 8, Math.toRadians(90));
    private final Pose shootPose = new Pose(56, 12, Math.toRadians(90));


    @Override
    public void runOpMode() throws InterruptedException {

    }
}
