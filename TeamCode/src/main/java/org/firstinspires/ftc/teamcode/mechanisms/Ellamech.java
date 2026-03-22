package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


@TeleOp
public class Ellamech extends OpMode {
    private DcMotor Flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;


    @Override
    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "rightBack");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft = hardwareMap.get(DcMotor.class,"leftFront");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft = hardwareMap.get(DcMotor.class,"leftBack");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {

        if (gamepad1.y) {
            rightFront.setPower(0.3);
        } else {
            rightFront.setPower(0.0);
        }

        if (gamepad1.x) {
            leftFront.setPower(0.3);
        } else {
            leftFront.setPower(0.0);
        }

        if (gamepad1.a) {
            leftBack.setPower(0.3);
        } else {
            leftBack.setPower(0.0);
        }

        if (gamepad1.b) {
            rightBack.setPower(0.3);
        } else {
            rightBack.setPower(0.0);
        }

        if (gamepad1.left_bumper) {
            Flywheel.setPower(0.3);
        } else {
            Flywheel.setPower(0.0);
        }

        telemetry.addData("RF", rightFront.getPower());
        telemetry.addData("LF", leftFront.getPower());
        telemetry.addData("LB", leftBack.getPower());
        telemetry.addData("RB", rightBack.getPower());
        telemetry.addData("Flywheel", Flywheel.getPower());
        telemetry.update();
    }
}
