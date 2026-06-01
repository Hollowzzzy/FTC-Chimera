package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp (name = "Drive")
public class tankDrive extends OpMode {

    DcMotor leftFront;
    DcMotor leftRear;
    DcMotor rightFront;
    DcMotor rightRear;

    @Override
    public void init() {

        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        leftRear = hardwareMap.get(DcMotor.class, "backLeft");
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        rightRear = hardwareMap.get(DcMotor.class, "backRight");


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

    }
}
