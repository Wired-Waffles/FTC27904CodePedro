package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.TurretTurn;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveRobotOrientated;


@TeleOp(name = "New TeleOP")
public class TeleOP extends LinearOpMode {
Shooter shooter = new Shooter();
Intake intake = new Intake();
TurretTurn turret = new TurretTurn();
MecanumDriveRobotOrientated mecanumDrive = new MecanumDriveRobotOrientated();

int turretPosIncrement = 29;
boolean maintenance = true;
int speedDivisor = 1;
int shooterSpeed = 1000;




    @Override
    public void runOpMode() {
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        turret.init(hardwareMap);
        mecanumDrive.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            //function testing

            turret.reset();
            if (!maintenance) {
                int speedDivisor = 2;
                telemetry.addData("UHHHHHHH", "to dooooo");
            } else {
                turret.kill();
                intake.intakePower(0);
                int speedDivisor = 2;
                if (gamepad1.a){ shooter.start(shooterSpeed);}
                if (gamepad1.x){ shooter.kill();}
                if (gamepad1.dpad_up){ intake.intakePower(-0.5);}
                if (gamepad1.dpad_down){ intake.intakePower(-1);}
                if (gamepad1.dpad_left){ intake.intakePower(-0.25);}
                if (gamepad1.dpad_right){ intake.intakePower(0.5);}
                if (gamepad2.dpad_right){ turret.setPos(turret.getPos() + turretPosIncrement);}
                if (gamepad2.dpad_left){ turret.setPos(turret.getPos() - turretPosIncrement);}
                if (gamepad2.dpad_up){turret.power(1);}
                if (gamepad2.dpad_down){turret.power(-1);}
                if (gamepad1.left_bumper){speedDivisor = 5;}
                if (gamepad2.circle){shooterSpeed += 100; shooter.start(shooterSpeed);}
                if (gamepad2.cross){shooterSpeed -= 100; shooter.start(shooterSpeed);}
                mecanumDrive.drive(-gamepad1.left_stick_y/speedDivisor, gamepad1.left_stick_x/speedDivisor, (-gamepad1.left_trigger+gamepad1.right_trigger)/speedDivisor, telemetry);
                telemetry.addData("Core hex annoying thing", turret.getPos());
            }
        }

    }
}
