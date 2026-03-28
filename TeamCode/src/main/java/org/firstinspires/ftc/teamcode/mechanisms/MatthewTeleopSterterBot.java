package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MatthewTeleopSterterBot extends OpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor Flywheel;
    private CRServo leftTransfer;
    private CRServo rightTransfer;

    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftTransfer = hardwareMap.get(CRServo.class, "leftTransfer");
        rightTransfer = hardwareMap.get(CRServo.class, "rightTransfer");
        rightTransfer.setDirection(DcMotorSimple.Direction.REVERSE);


        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {

    }

    public void wheels () {
        if (gamepad1.a) {
            leftFront.setPower(0.5);
        } else {
            leftFront.setPower(0);
        }

        if (gamepad1.b) {
            leftBack.setPower(0.5);
        } else {
            leftBack.setPower(0);
        }

        if (gamepad1.x) {
            rightFront.setPower(0.5);
        } else {
            rightFront.setPower(0);
        }

        if (gamepad1.y) {
            rightBack.setPower(0.5);
        } else {
            rightBack.setPower(0);
        }

        if (gamepad1.right_bumper) {
            Flywheel.setPower(5);
        } else {
            Flywheel.setPower(0);
        }

        if (gamepad1.dpad_left) {
            leftTransfer.setPower(0.5);
        } else {
            leftTransfer.setPower(0);
        }

        if (gamepad1.dpad_left ) {
            rightTransfer.setPower(0.5);
        } else {
            rightTransfer.setPower(0);
        }

        telemetry.addData("a", gamepad1.a);
        telemetry.addData("b", gamepad1.b);
        telemetry.addData("x", gamepad1.x);
        telemetry.addData("y", gamepad1.y);
        telemetry.addData("right_bumper", gamepad1.right_bumper);
        telemetry.addData("right_dpad", gamepad1.dpad_right);
        telemetry.addData("left_dpad", gamepad1.dpad_left);
    }}
