package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp
public class iris_test1 extends OpMode {
    private DcMotor Flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;
    private CRServo leftFeeder;
    private CRServo rightFeeder;


    @Override
    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hardwareMap.get(DcMotor.class, "backRight");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hardwareMap.get(DcMotor.class,"frontLeft");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack = hardwareMap.get(DcMotor.class,"backLeft");
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        CRServo leftFeeder = hardwareMap.get(CRServo.class, "leftTransfer");
        CRServo rightFeeder = hardwareMap.get(CRServo.class, "rightTransfer");
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

        if (gamepad1.right_bumper) {
           leftFeeder.setPower(0.5);
           rightFeeder.setPower(0.5);
        } else {
            leftFeeder.setPower(0.0);
            rightFeeder.setPower(0.0);
        }

        telemetry.addData("RF", rightFront.getPower());
        telemetry.addData("LF", leftFront.getPower());
        telemetry.addData("LB", leftBack.getPower());
        telemetry.addData("RB", rightBack.getPower());
        telemetry.addData("Flywheel", Flywheel.getPower());
        telemetry.addData("leftTransfer", leftFeeder.getPower());
        telemetry.addData("rightTransfer", rightFeeder.getPower());
        telemetry.update();
    }
}
