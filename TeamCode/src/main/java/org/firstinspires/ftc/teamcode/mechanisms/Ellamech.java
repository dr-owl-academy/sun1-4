package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImpl;
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
    private CRServo rightTransfer;
    private CRServo leftTransfer;


    @Override
    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightTransfer = hardwareMap.get(CRServo.class, "rightTransfer");
        leftTransfer = hardwareMap.get(CRServo.class,"leftTransfer");
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
            rightTransfer.setPower(-0.5);
        } else {
            rightTransfer.setPower(0.0);
        }

        if (gamepad1.right_bumper) {
            leftTransfer.setPower(0.5);
        } else {
            leftTransfer.setPower(0.0);
        }


        telemetry.addData("RF", rightFront.getPower());
        telemetry.addData("LF", leftFront.getPower());
        telemetry.addData("LB", leftBack.getPower());
        telemetry.addData("RB", rightBack.getPower());
        telemetry.addData("Flywheel", Flywheel.getPower());
        telemetry.update();
    }

    public void setRightTransfer(DcMotor rightTransfer) {
        this.rightTransfer = (CRServo) rightTransfer;
    }
}

