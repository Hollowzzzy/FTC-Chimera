package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp (name = "Drive")
public class tankDrive extends OpMode {

    DcMotor leftFront;
    DcMotor leftRear;
    DcMotor rightFront;
    DcMotor rightRear;

    DcMotor intakeMotor;

    boolean intakeToggle = true;

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void init() {

        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        leftRear = hardwareMap.get(DcMotor.class, "backLeft");
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        rightRear = hardwareMap.get(DcMotor.class, "backRight");
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");

        rightRear.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // forward/back
        double rx = gamepad1.right_stick_x; // rotation


// --- Cubed + blended steering ---
        rx = Math.pow(rx, 3) * 0.6;


// --- Mecanum math ---
        double denominator = Math.max(Math.abs(y) + Math.abs(rx), 1);

        double leftPower = (y + rx) / denominator;
        double rightPower = (y - rx) / denominator;


// --- Apply power ---
        leftFront.setPower(leftPower);
        leftRear.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightRear.setPower(rightPower);


        if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper) {
            // This will set intakeToggle to true if it was previously false
            // and intakeToggle to false if it was previously true,
            // providing a toggling behavior.
            intakeToggle = !intakeToggle;
        }

        if (intakeToggle) {
            intakeMotor.setPower(1);
        }
        else {
            intakeMotor.setPower(0);
        }


    }
}
