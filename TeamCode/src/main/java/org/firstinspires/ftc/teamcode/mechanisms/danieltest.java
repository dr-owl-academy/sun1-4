package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp

public class danieltest extends OpMode {
    private DcMotor Flywheel;
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    @Override
    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "rightBack");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = hardwareMap.get(DcMotor.class, "leftBack");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }


    @Override
    public void loop() {
        if (gamepad1.y) {
            frontRight.setPower(0.3);
        } else {
            frontRight.setPower(0.0);
        }
        if (gamepad1.a) {
            backRight.setPower(0.3);
        } else {
            backRight.setPower(0.0);
        }
        if (gamepad1.x) {
            frontLeft.setPower(0.3);
        } else {
            frontLeft.setPower(0.0);
        }
        if (gamepad1.b) {
            backLeft.setPower(0.3);
        } else {
            backLeft.setPower(0.0);
        }
        if (gamepad1.right_bumper) {
            Flywheel.setPower(0.3);
        } else {
            Flywheel.setPower(0.0);
        }
        telemetry.addData("FR", frontRight.getPower());
        telemetry.addData("FL", frontLeft.getPower());
        telemetry.addData("BL", backLeft.getPower());
        telemetry.addData("BR", backRight.getPower());
        telemetry.addData("Flywheel", Flywheel.getPower());
        telemetry.update();
    }
}

