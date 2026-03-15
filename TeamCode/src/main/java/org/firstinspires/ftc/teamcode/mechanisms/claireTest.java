package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="MotorButtonTest", group="Test")

public class claireTest extends LinearOpMode {

    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor Flywheel;

    @Override
    public void runOpMode() {

        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        rightBack = hardwareMap.get(DcMotor.class, "backRight");
        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        leftBack = hardwareMap.get(DcMotor.class, "backLeft");
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");

        waitForStart();

        while (opModeIsActive()) {

            // A button → rightFront
            if (gamepad1.a) {
                rightFront.setPower(0.3);
            } else {
                rightFront.setPower(0);
            }

            // B button → rightBack
            if (gamepad1.b) {
                rightBack.setPower(0.3);
            } else {
                rightBack.setPower(0);
            }

            // X button → leftFront
            if (gamepad1.x) {
                leftFront.setPower(0.3);
            } else {
                leftFront.setPower(0);
            }

            // Y button → leftBack
            if (gamepad1.y) {
                leftBack.setPower(0.3);
            } else {
                leftBack.setPower(0);
            }

            // Right bumper → Flywheel
            if (gamepad1.right_bumper) {
                Flywheel.setPower(0.3);
            } else {
                Flywheel.setPower(0);
            }
        }
    }
}