package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.CoachTeleopStarterBot;

@TeleOp
public class MatthewTeleopSterterBot extends OpMode {
    final double FEED_TIME_SECONDS = 0.50; //The feeder servos run this long when a shot is requested.
    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;
    private DcMotor leftFront;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor Flywheel;
    private CRServo leftTransfer;
    private CRServo rightTransfer;
    final double LAUNCHER_TARGET_VELOCITY = 2000;
    final double LAUNCHER_MIN_VELOCITY = 1200;

    private enum LaunchState {
        IDLE,
        SPIN_UP,
        LAUNCH,
        LAUNCHING,
    }

    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = hardwareMap.get(DcMotor.class, "leftBack");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftTransfer = hardwareMap.get(CRServo.class, "leftTransfer");
        rightTransfer = hardwareMap.get(CRServo.class, "rightTransfer");
        rightTransfer.setDirection(DcMotorSimple.Direction.REVERSE);


        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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
            backLeft.setPower(0.5);
        } else {
            backLeft.setPower(0);
        }

        if (gamepad1.x) {
            frontRight.setPower(0.5);
        } else {
            frontRight.setPower(0);
        }

        if (gamepad1.y) {
            backRight.setPower(0.5);
        } else {
            backRight.setPower(0);
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
    }
    void launch(boolean shotRequested) {
        switch (launchState) {
            case IDLE:
                if (shotRequested) {
                    launchState = CoachTeleopStarterBot.LaunchState.SPIN_UP;
                }
                break;
            case SPIN_UP:
                launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
                if (launcher.getVelocity() > LAUNCHER_MIN_VELOCITY) {
                    launchState = CoachTeleopStarterBot.LaunchState.LAUNCH;
                }
                break;
            case LAUNCH:
                leftTransfer.setPower(FULL_SPEED);
                rightTransfer.setPower(FULL_SPEED);
                feederTimer.reset();
                launchState = CoachTeleopStarterBot.LaunchState.LAUNCHING;
                break;
            case LAUNCHING:
                if (feederTimer.seconds() > FEED_TIME_SECONDS) {
                    launchState = CoachTeleopStarterBot.LaunchState.IDLE;
                    leftTransfer.setPower(STOP_SPEED);
                    rightTransfer.setPower(STOP_SPEED);
                }
                break;
        }
    }
}
