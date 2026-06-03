package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@TeleOp (name = "Drive")
public class tankDrive extends OpMode {

    DcMotor leftFront;
    DcMotor leftRear;
    DcMotor rightFront;
    DcMotor rightRear;

    DcMotor intakeMotor;

    boolean intakeToggle = false;

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


        @TeleOp(name = "Intake Toggle")
        class IntakeToggle extends LinearOpMode {

            private DcMotor intakeMotor;

            @Override
            public void runOpMode() {

                intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

                Gamepad currentGamepad1 = new Gamepad();
                Gamepad previousGamepad1 = new Gamepad();

                boolean intakeOn = false;

                waitForStart();

                while (opModeIsActive()) {

                    // Store gamepad states
                    previousGamepad1.copy(currentGamepad1);
                    currentGamepad1.copy(gamepad1);

                    // Toggle intake when A is pressed
                    if (currentGamepad1.a && !previousGamepad1.a) {
                        intakeOn = !intakeOn;
                    }

                    // Run intake based on toggle state
                    if (intakeOn) {
                        intakeMotor.setPower(1.0);
                    } else {
                        intakeMotor.setPower(0.0);
                    }

                    telemetry.addData("Intake", intakeOn ? "ON" : "OFF");
                    telemetry.update();
                }


            }


        }
    }
}