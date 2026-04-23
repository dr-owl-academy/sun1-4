package org.firstinspires.ftc.teamcode.mechanisms;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PinpointLocalizer;


@TeleOp(name = "HanmingOpModeTest", group = "StarterBot")
public class HanmingOpModeTest extends OpMode {
    final double FEED_TIME_SECONDS = 0.50;
    final double STOP_SPEED = 0.0;
    final double FULL_SPEED = 1.0;
    double LAUNCHER_TARGET_VELOCITY = 2000;
    double LAUNCHER_MIN_VELOCITY = 900;
    // Declare OpMode members.
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotorEx launcher = null;
    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;
    private PinpointLocalizer localizer;

    ElapsedTime feederTimer = new ElapsedTime();

    private enum LaunchState {
        IDLE,
        SPIN_UP,
        LAUNCH,
        LAUNCHING,
    }

    private LaunchState launchState;
    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;
    double kOffset = 0;
    private static final double RED_GOAL_X = 57.0;
    private static final double RED_GOAL_Y = 57.0;

    private static final double BLUE_GOAL_X = -57.0;
    private static final double BLUE_GOAL_Y = 57.0;

    double kTurn = 1.5;   // turning gain, tune this
    double driverTurn = 0;

    @Override
    public void init() {

        launchState = LaunchState.IDLE;
        leftFrontDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
        leftBackDrive = hardwareMap.get(DcMotor.class, "backLeft");
        rightBackDrive = hardwareMap.get(DcMotor.class, "backRight");
        launcher = hardwareMap.get(DcMotorEx.class, "Flywheel");
        leftFeeder = hardwareMap.get(CRServo.class, "leftTransfer");
        rightFeeder = hardwareMap.get(CRServo.class, "rightTransfer");
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);
        launcher.setZeroPowerBehavior(BRAKE);
        leftFeeder.setPower(STOP_SPEED);
        rightFeeder.setPower(STOP_SPEED);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));
        rightFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");

        localizer = new PinpointLocalizer(hardwareMap, 0.0019684344326, new Pose2d(0,-62, 0));

    }

    @Override
    public void init_loop() {
    }
    @Override
    public void start() {
    }
    @Override
    public void loop() {
        PoseVelocity2d currentVelocity = localizer.update();
        Pose2d currentPose = localizer.getPose();

        double robotX = currentPose.position.x;
        double robotY = currentPose.position.y;
        double actualHeading = currentPose.heading.toDouble();

        double targetX = RED_GOAL_X;
        double targetY = RED_GOAL_Y;

        if (gamepad1.left_bumper) {
            targetX = BLUE_GOAL_X;
            targetY = BLUE_GOAL_Y;
        }

        double dx = targetX - robotX;
        double dy = targetY - robotY;

        double targetHeading = -Math.atan2(dx, dy);
        double headingError = targetHeading - actualHeading;
        headingError = Math.atan2(Math.sin(headingError), Math.cos(headingError));

        if (gamepad1.right_bumper) {
            driverTurn = aimToRed(currentPose);
        } else if (gamepad1.left_bumper) {
            driverTurn = aimToBlue(currentPose);
        } else {
            driverTurn = gamepad1.right_stick_x;
        }

        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, driverTurn);

        if (gamepad1.dpadUpWasPressed()) {
            kOffset += 10;
        }

        if (gamepad1.dpadDownWasPressed()) {
            kOffset -= 10;
        }

       // if (gamepad1.dpadLeftWasPressed()) {
       //     LAUNCHER_TARGET_VELOCITY += 25;
       // }

       // if (gamepad1.dpadRightWasPressed()) {
       //     LAUNCHER_TARGET_VELOCITY -= 25;
       // }

        if (gamepad2.right_trigger > 0.1) {
            leftFeeder.setPower(FULL_SPEED);
            rightFeeder.setPower(FULL_SPEED);
        } else {
            leftFeeder.setPower(STOP_SPEED);
            rightFeeder.setPower(STOP_SPEED);
        }

// Red goal
        double redGoalX = 57;
        double redGoalY = 57;
// Blue goal
        double blueGoalX = -57;
        double blueGoalY = 57;
// Distance calculations
        double redDist = Math.hypot(RED_GOAL_X - robotX, RED_GOAL_Y - robotY);
        double blueDist = Math.hypot(BLUE_GOAL_X - robotX, BLUE_GOAL_Y - robotY);

        if (gamepad2.y) {
            LAUNCHER_TARGET_VELOCITY = velocityFromDistance(redDist) + kOffset;
            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else if (gamepad2.x) {
            LAUNCHER_TARGET_VELOCITY = velocityFromDistance(blueDist) + kOffset;
            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else if (gamepad2.b) {
            launcher.setVelocity(STOP_SPEED);
        }

        telemetry.addData("Pinpoint Status", localizer.driver.getDeviceStatus());
        telemetry.addLine();
        telemetry.addData("Pos X", currentPose.position.x);
        telemetry.addData("Pos Y", currentPose.position.y);
        telemetry.addData("Pose", "(%.1f, %.1f, %.1f)", currentPose.position.x, currentPose.position.y, Math.toDegrees(currentPose.heading.toDouble()));
        telemetry.addData("Red Goal Dist", "%.2f", redDist);
        telemetry.addData("Blue Goal Dist", "%.2f", blueDist);
        telemetry.addLine();
        telemetry.addData("Target Heading", Math.toDegrees(targetHeading));
        telemetry.addData("Actual Heading", Math.toDegrees(actualHeading));
        telemetry.addData("Heading Error", Math.toDegrees(headingError));
        telemetry.addLine();
        telemetry.addData("Left Transfer", gamepad2.dpad_left ? "Forward" : "Off");
        telemetry.addData("Right Transfer", gamepad2.dpad_right ? "Reverse" : "Off");
        telemetry.addData("Flywheel Power", launcher.getPower());
        telemetry.addData("Flywheel Target Speed", LAUNCHER_TARGET_VELOCITY);
        telemetry.addData("Launch Min Vel", LAUNCHER_MIN_VELOCITY);
        telemetry.addData("Offset", kOffset);
        telemetry.update();

    }

    @Override
    public void stop() {
    }

    void mecanumDrive(double forward, double strafe, double rotate){
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);

        leftFrontPower = (forward + strafe + rotate) / denominator;
        rightFrontPower = (forward - strafe - rotate) / denominator;
        leftBackPower = (forward - strafe + rotate) / denominator;
        rightBackPower = (forward + strafe - rotate) / denominator;

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

    }

    void launch(boolean bumperHeld) {
        switch (launchState) {

            case IDLE:
                if (bumperHeld) {
                    launchState = LaunchState.SPIN_UP;
                }
                break;

            case SPIN_UP:
                if (!bumperHeld) {
                    launchState = LaunchState.IDLE;
                    break;
                }

                launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);

                if (launcher.getVelocity() > LAUNCHER_MIN_VELOCITY) {
                    feederTimer.reset();
                    launchState = LaunchState.LAUNCH;
                }
                break;

            case LAUNCH:
                if (!bumperHeld) {
                    leftFeeder.setPower(STOP_SPEED);
                    rightFeeder.setPower(STOP_SPEED);
                    launchState = LaunchState.IDLE;
                    break;
                }

                if (feederTimer.seconds() > 1.0) {
                    leftFeeder.setPower(FULL_SPEED);
                    rightFeeder.setPower(FULL_SPEED);
                    launchState = LaunchState.LAUNCHING;
                }
                break;

            case LAUNCHING:
                if (!bumperHeld) {
                    leftFeeder.setPower(STOP_SPEED);
                    rightFeeder.setPower(STOP_SPEED);
                    launchState = LaunchState.IDLE;
                }
                break;
        }
    }
    double velocityFromDistance(double x) {

        x = Math.max(18, x);

        //return -0.00276858 * x * x * x
          //      + 0.734433 * x * x
            //    - 53.4832 * x
              //  + 2467.23833;

        //y=-0.000439386x^{3}+0.128207x^{2}-5.0367x+1298.79524

        return -0.000439386 * x * x * x
                + 0.128207 * x * x
                - 5.0367 * x
                + 1298.79524;
    }
    double aimToRed(Pose2d pose2d) {
        double robotX = pose2d.position.x;
        double robotY = pose2d.position.y;
        double robotHeading = pose2d.heading.toDouble(); // radians

        double dx = RED_GOAL_X - robotX;
        double dy = RED_GOAL_Y - robotY;

        double targetAngle = -Math.atan2(dx, dy); // radians
        double angleError = targetAngle - robotHeading;

        angleError = Math.atan2(Math.sin(angleError), Math.cos(angleError));

        return -kTurn * angleError;
    }

    double aimToBlue(Pose2d pose2d) {
        double robotX = pose2d.position.x;
        double robotY = pose2d.position.y;
        double robotHeading = pose2d.heading.toDouble(); // radians

        double dx = BLUE_GOAL_X - robotX;
        double dy = BLUE_GOAL_Y - robotY;

        double targetAngle = -Math.atan2(dx, dy); // radians
        double angleError = targetAngle - robotHeading;

        angleError = Math.atan2(Math.sin(angleError), Math.cos(angleError));

        return -kTurn * angleError;
    }
}