package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.ShooterOld;
import org.firstinspires.ftc.teamcode.subsystems.TurretAutoTrack;
import org.firstinspires.ftc.teamcode.subsystems.TurretTurn;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveRobotOrientated;


@TeleOp(name = "New TeleOP")
public class TeleOP extends LinearOpMode {
ShooterOld shooter = new ShooterOld();
Intake intake = new Intake();
TurretAutoTrack turret = new TurretAutoTrack();
MecanumDriveRobotOrientated mecanumDrive = new MecanumDriveRobotOrientated();
boolean maintenance = true;
int speedDivisor = 1;
int shooterSpeed = 1800;
int shooterSpeedFarrrrrrrr = 2800;
boolean teamRed = false;
private TelemetryManager panelsTelemetry;




    @Override
    public void runOpMode() {
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        turret.init(hardwareMap);
        mecanumDrive.init(hardwareMap);
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        while (opModeInInit()) {
            if (gamepad1.dpad_up){teamRed=true;}
            if (gamepad1.dpad_down){teamRed=false;}
            telemetry.addData("team red?", teamRed);
            telemetry.addData("DPAD UP FOR RED", gamepad1.dpad_up);
            telemetry.addData("DPAD UP FOR BLUE", gamepad1.dpad_down);

            telemetry.update();
        }

        waitForStart();
        while (opModeIsActive()) {
            //function testing
            if (!maintenance) {
                int speedDivisor = 1;
                telemetry.addData("UHHHHHHH", "to dooooo");
            } else {

                intake.intakePower(0);
                speedDivisor = 2;

                if (gamepad2.circle ){ shooter.start(shooterSpeed);}
                if (gamepad2.triangle){shooter.start(shooterSpeedFarrrrrrrr);}
                if (gamepad2.cross||gamepad1.cross){ shooter.kill();}
                if (gamepad2.options){speedDivisor = 1;}

                if (gamepad1.left_bumper){ intake.intakePower(1);}
                if (gamepad1.dpad_down){ intake.intakePower(-0.5);}
                if (gamepad1.right_bumper){speedDivisor = 5;}
                mecanumDrive.drive(-gamepad1.left_stick_y/speedDivisor, gamepad1.left_stick_x/speedDivisor, -gamepad1.right_stick_x/speedDivisor, telemetry);
                if (gamepad2.dpad_left){shooterSpeed = shooterSpeed + 100;}
                if (gamepad2.dpad_right){shooterSpeed = shooterSpeed -  100;}
                telemetry.addData("shooter target speed", shooterSpeed);
                telemetry.addData("shooter current speed", shooter.getVelo());
                panelsTelemetry.update(telemetry);
                telemetry.update();
            }
        }

    }

}

