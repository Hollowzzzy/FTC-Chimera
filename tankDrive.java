package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "Drive")
public class tankDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor leftFront;
        DcMotor leftRear;
        DcMotor rightFront;
        DcMotor rightRear;
        Servo servo;

        DcMotor intake;

        boolean intakeToggle = false;
        boolean eject = false;

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        waitForStart();



        if (isStopRequested()) return;

        while (opModeIsActive()) {
            leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
            leftRear = hardwareMap.get(DcMotor.class, "backLeft");
            rightFront = hardwareMap.get(DcMotor.class, "frontRight");
            rightRear = hardwareMap.get(DcMotor.class, "backRight");
            intake = hardwareMap.get(DcMotor.class, "intake");
            servo = hardwareMap.get(Servo.class, "servo");

            rightRear.setDirection(DcMotor.Direction.REVERSE);
            rightFront.setDirection(DcMotor.Direction.REVERSE);


            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);


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

// Using the toggle variable to control the robot.
            if (intakeToggle) {
                intake.setPower(1);
            }
            else {
                intake.setPower(0);
            }
// Using the toggle variable to control the robot.
            if (currentGamepad1.right_trigger > 0.5) {
                intake.setPower(-1);
            }

            if (currentGamepad1.left_bumper) {
                servo.setPosition(1);
            } else {
                servo.setPosition(0);

            }
        }
    }
}


