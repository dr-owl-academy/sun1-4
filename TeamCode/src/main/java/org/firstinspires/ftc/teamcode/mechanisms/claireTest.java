package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class claireTest extends OpMode {
    private DcMotor Flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;
    private CRServo leftTransfer;
    private CRServo rightTransfer;


    @Override
    public void init() {
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hardwareMap.get(DcMotor.class, "backRight");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack = hardwareMap.get(DcMotor.class, "backLeft");
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftTransfer = hardwareMap.get(CRServo.class, "leftTransfer");
        rightTransfer = hardwareMap.get(CRServo.class, "rightTransfer");


    }


    @Override
    public void loop() {

        // Drive motors (controlled by ABXY)
        if (gamepad1.a) {
            rightFront.setPower(0.3);
        } else {
            rightFront.setPower(0);
        }

        if (gamepad1.b) {
            rightBack.setPower(0.3);
        } else {
            rightBack.setPower(0);
        }

        if (gamepad1.x) {
            leftFront.setPower(0.3);
        } else {
            leftFront.setPower(0);
        }

        if (gamepad1.y) {
            leftBack.setPower(0.3);
        } else {
            leftBack.setPower(0);
        }

        // Flywheel (controlled by rightBumper)
        if (gamepad1.right_bumper) {
            Flywheel.setPower(0.7);
        } else {
            Flywheel.setPower(0);
        }

        // Transfer servos (controlled by dpad_up)
        if (gamepad1.dpad_up) {
            leftTransfer.setPower(0.3);
            rightTransfer.setPower(0.3);
        } else {
            leftTransfer.setPower(0);
            rightTransfer.setPower(0);
        }

        // Telemetry
        telemetry.addData("rF", rightFront.getPower());
        telemetry.addData("rB", rightBack.getPower());
        telemetry.addData("lF", leftFront.getPower());
        telemetry.addData("lB", leftBack.getPower());
        telemetry.addData("fly.", Flywheel.getPower());
        telemetry.addData("lT", leftTransfer.getPower());
        telemetry.addData("rT", rightTransfer.getPower());
        telemetry.update();
    }
}