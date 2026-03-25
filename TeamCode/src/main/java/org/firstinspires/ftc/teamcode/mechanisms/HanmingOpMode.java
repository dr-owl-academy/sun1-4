package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class HanmingOpMode extends OpMode {
    private DcMotor Flywheel;
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private CRServo leftTransfer;
    private CRServo rightTransfer;

    @Override
    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftTransfer = hardwareMap.get(CRServo.class, "leftTranfser");
        rightTransfer = hardwareMap.get(CRServo.class, "rightTransfer");
    }

    @Override
    public void loop() {

        if (gamepad1.y) {
            frontRight.setPower(0.3);
        } else {
            frontRight.setPower(0.0);
        }

        if (gamepad1.x) {
            frontLeft.setPower(0.3);
        } else {
            frontLeft.setPower(0.0);
        }

        if (gamepad1.a) {
            backLeft.setPower(0.3);
        } else {
            backLeft.setPower(0.0);
        }

        if (gamepad1.b) {
            backRight.setPower(-0.3);
        } else {
            backRight.setPower(0.0);
        }

        if (gamepad1.left_bumper) {
            Flywheel.setPower(0.3);
        } else {
            Flywheel.setPower(0.0);
        }

        if (gamepad1.dpad_left) {
            leftTransfer.setPower(1);
        }

        if (gamepad1.dpad_right) {
            rightTransfer.setPower(1);
        }

        telemetry.addData("RF", frontRight.getPower());
        telemetry.addData("LF", frontLeft.getPower());
        telemetry.addData("LB", backLeft.getPower());
        telemetry.addData("RB", backRight.getPower());
        telemetry.addData("Flywheel", Flywheel.getPower());
        telemetry.update();
    }
}
