package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.TurretTurn;


@TeleOp(name = "New TeleOP")
public class TeleOP extends LinearOpMode {
Shooter shooter = new Shooter();
Intake intake = new Intake();
TurretTurn turret = new TurretTurn();

int turretPosIncrement = 29;
boolean maintenance = true;




    @Override
    public void runOpMode() {
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        turret.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            //function testing
            if (!maintenance) {
                telemetry.addData("UHHHHHHH", "to dooooo");
            } else {
            if (gamepad1.a){ shooter.start(70);}
            if (gamepad1.x){ shooter.kill();}
            if (gamepad1.right_bumper){ intake.intakePower(0.1);}
            if (gamepad2.dpad_right){ turret.setPos(turret.getPos() + turretPosIncrement);}
            if (gamepad2.dpad_left){ turret.setPos(turret.getPos() - turretPosIncrement);}
            }
        }

    }
}
