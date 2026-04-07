package org.firstinspires.ftc.teamcode.mechanisms;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.PinpointLocalizer;

@TeleOp
public class HanmingOpMode extends OpMode {
    private DcMotor Flywheel;
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private CRServo leftTransfer;
    private CRServo rightTransfer;
    private double flywheelspeed = 0.3;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private PinpointLocalizer localizer;

    @Override
    public void init() {
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftTransfer = hardwareMap.get(CRServo.class, "leftTransfer");
        rightTransfer = hardwareMap.get(CRServo.class, "rightTransfer");
        rightTransfer.setDirection(DcMotorSimple.Direction.REVERSE);

        localizer = new PinpointLocalizer(hardwareMap, 1.0, new Pose2d(0, 0, 0));

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
            backRight.setPower(0.3);
        } else {
            backRight.setPower(0.0);
        }

        if (gamepad1.dpad_up && !lastDpadUp) {
            flywheelspeed += 0.05;
        }

        if (gamepad1.dpad_down && !lastDpadDown) {
            flywheelspeed -= 0.05;
        }

        flywheelspeed = Math.max(0.0, Math.min(1.0, flywheelspeed));

        lastDpadUp = gamepad1.dpad_up;
        lastDpadDown = gamepad1.dpad_down;

        if (gamepad1.left_bumper) {
            Flywheel.setPower(flywheelspeed);
        } else {
            Flywheel.setPower(0.0);
        }

        if (gamepad1.dpad_left) {
            leftTransfer.setPower(1.0);
            rightTransfer.setPower(1.0);
        } else if (gamepad1.dpad_right) {
            leftTransfer.setPower(-1.0);
            rightTransfer.setPower(-1.0);
        } else {
            leftTransfer.setPower(0.0);
            rightTransfer.setPower(0.0);
        }

        PoseVelocity2d currentVelocity = localizer.update();
        Pose2d currentPose = localizer.getPose();

        double robotX = currentPose.position.x;
        double robotY = currentPose.position.y;
// Red goal
        double redGoalX = 69;
        double redGoalY = 69;
// Blue goal
        double blueGoalX = -69;
        double blueGoalY = 69;
// Distance calculations
        double redDist = Math.hypot(redGoalX - robotX, redGoalY - robotY);
        double blueDist = Math.hypot(blueGoalX - robotX, blueGoalY - robotY);

        telemetry.addData("RF", frontRight.getPower());
        telemetry.addData("LF", frontLeft.getPower());
        telemetry.addData("LB", backLeft.getPower());
        telemetry.addData("RB", backRight.getPower());
        telemetry.addData("Left Transfer", gamepad1.dpad_left ? "Forward" : "Off");
        telemetry.addData("Right Transfer", gamepad1.dpad_right ? "Reverse" : "Off");
        telemetry.addData("Flywheel Power", Flywheel.getPower());
        telemetry.addData("Flywheel Target Speed", flywheelspeed);
        telemetry.addData("Pose", "(%.1f, %.1f, %.1f)", currentPose.position.x, currentPose.position.y, Math.toDegrees(currentPose.heading.toDouble()));
        telemetry.addData("Red Goal Dist", "%.2f", redDist);
        telemetry.addData("Blue Goal Dist", "%.2f", blueDist);
        telemetry.update();

    }
}