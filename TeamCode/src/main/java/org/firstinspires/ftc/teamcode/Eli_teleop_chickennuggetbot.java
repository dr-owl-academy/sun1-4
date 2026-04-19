package org.firstinspires.ftc.teamcode;

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

import org.firstinspires.ftc.teamcode.mechanisms.Elipinpoint;

@TeleOp(name = "Eli_teleop_chickennuggetbot", group = "StarterBot")
public class Eli_teleop_chickennuggetbot extends OpMode {

    final double FEED_TIME_SECONDS = 0.20;
    final double STOP_SPEED = 0.0;
    final double FULL_SPEED = 1.0;

    private static final double BLUE_GOAL_X = -57.0;
    private static final double BLUE_GOAL_Y = 57.0;

    private static final double RED_GOAL_X = 57.0;
    private static final double RED_GOAL_Y = 57.0;

    double LAUNCHER_TARGET_VELOCITY = 1225;
    double LAUNCHER_MIN_VELOCITY    = 1000;
    static final double LAUNCHER_MAX_VELOCITY = 2200;

    // Cubic regression: flywheel velocity (ticks/s) vs distance (in)
    // f(d) = A·d³ + B·d² + C·d + D  — tune coefficients via FTC Dashboard
    double CUBIC_A =  -0.00040;
    double CUBIC_B =   0.1086;
    double CUBIC_C =  -1.47;
    double CUBIC_D =   1120;

    // Drive motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    // Launcher motor
    private DcMotorEx launcher = null;

    // Feed servos
    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;


    private Elipinpoint localizer = null;

    // Change this to your desired starting pose: x, y in inches, heading in radians
    private Pose2d initialRobotPose = new Pose2d(0, 0, 0);
    private static final double PINPOINT_IN_PER_TICK = 0.0019684344326;


    ElapsedTime feederTimer = new ElapsedTime();

    private enum LaunchState {
        IDLE,
        SPIN_UP,
        LAUNCH,
        LAUNCHING
    }

    private LaunchState launchState;

    private boolean targetingBlue  = true;  // left bumper toggles BLUE ↔ RED
    private boolean autoSpeedMode  = false; // X toggles AUTO ↔ MANUAL

    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;

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
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);
        launcher.setZeroPowerBehavior(BRAKE);

        leftFeeder.setPower(STOP_SPEED);
        rightFeeder.setPower(STOP_SPEED);

        launcher.setPIDFCoefficients(
                DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(300, 0, 0, 10)
        );

        rightFeeder.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize PinpointLocalizer with starting pose
        localizer = new Elipinpoint(hardwareMap, PINPOINT_IN_PER_TICK, initialRobotPose);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Initial Pose", "(%.2f, %.2f, %.2f rad)", initialRobotPose.position.x, initialRobotPose.position.y, initialRobotPose.heading.toDouble());
        telemetry.update();
    }

    @Override
    public void loop() {



        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        // Compute pose & distances early so auto speed is available below
        PoseVelocity2d currentVelocity = localizer.update();
        Pose2d currentPose = localizer.getPose();
        double robotX   = currentPose.position.x;
        double robotY   = currentPose.position.y;
        double distBlue = Math.hypot(BLUE_GOAL_X - robotX, BLUE_GOAL_Y - robotY);
        double distRed  = Math.hypot(RED_GOAL_X  - robotX, RED_GOAL_Y  - robotY);
        double activeDist = targetingBlue ? distBlue : distRed;
        double autoSpeed  = calcFlywheelSpeed(activeDist);

        // X: toggle AUTO / MANUAL speed mode
        if (gamepad1.xWasPressed()) {
            autoSpeedMode = !autoSpeedMode;
        }

        // D-pad: adjust manual target velocity
        if (gamepad1.dpadUpWasPressed())   LAUNCHER_TARGET_VELOCITY += 10;
        if (gamepad1.dpadDownWasPressed()) LAUNCHER_TARGET_VELOCITY -= 10;

        double targetVel = autoSpeedMode ? autoSpeed : LAUNCHER_TARGET_VELOCITY;

        if (gamepad1.y) {
            launcher.setVelocity(targetVel);
        } else if (gamepad1.b) {
            launcher.setVelocity(STOP_SPEED);
        }

        launch(gamepad1.right_bumper, targetVel);

        // A: reset localizer position to (0, 0), preserve heading
        if (gamepad1.aWasPressed()) {
            resetPosition();
        }

        // Left bumper: toggle BLUE / RED target
        if (gamepad1.leftBumperWasPressed()) {
            targetingBlue = !targetingBlue;
        }

        telemetry.addData("── Flywheel ──────────", "");
        telemetry.addData("Mode",        autoSpeedMode ? "AUTO" : "MANUAL");
        telemetry.addData("State",       launchState);
        telemetry.addData("Actual vel",  "%.0f t/s", launcher.getVelocity());
        telemetry.addData("Target vel",  "%.0f t/s", targetVel);
        telemetry.addData("Auto speed",  "%.0f t/s", autoSpeed);
        telemetry.addData("Manual tgt",  "%.0f t/s", LAUNCHER_TARGET_VELOCITY);
        telemetry.addData("Min vel",     "%.0f t/s", LAUNCHER_MIN_VELOCITY);
        telemetry.addData("── Pose ──────────────", "");
        telemetry.addData("Pose",        "(%.1f, %.1f, %.1f°)", robotX, robotY, Math.toDegrees(currentPose.heading.toDouble()));
        telemetry.addData("Velocity",    "(%.1f, %.1f)", currentVelocity.linearVel.x, currentVelocity.linearVel.y);
        telemetry.addData("── Goals ─────────────", "");
        telemetry.addData("Target",      targetingBlue ? "BLUE" : "RED");
        telemetry.addData("Active dist", "%.1f in", activeDist);
        telemetry.addData("Dist → BLUE", "%.1f in", distBlue);
        telemetry.addData("Dist → RED",  "%.1f in", distRed);
        telemetry.update();
    }

    /** Zeroes the localizer's X/Y position while keeping the current heading. */
    private void resetPosition() {
        double heading = localizer.getPose().heading.log();
        localizer.setPose(new Pose2d(0, 0, heading));
    }

    /** Cubic regression: flywheel velocity (ticks/s) for a given distance (in). Capped at max. */
    private double calcFlywheelSpeed(double dist) {
        double speed = CUBIC_A * dist * dist * dist
                     + CUBIC_B * dist * dist
                     + CUBIC_C * dist
                     + CUBIC_D;
        return Math.min(Math.max(speed, 0), LAUNCHER_MAX_VELOCITY);
    }

    void mecanumDrive(double forward, double strafe, double rotate) {

        double denominator = Math.max(
                Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1
        );

        leftFrontPower = (forward + strafe + rotate) / denominator;
        rightFrontPower = (forward - strafe - rotate) / denominator;
        leftBackPower = (forward - strafe + rotate) / denominator;
        rightBackPower = (forward + strafe - rotate) / denominator;

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }

    void launch(boolean shotRequested, double targetVel) {

        switch (launchState) {

            case IDLE:
                if (shotRequested) {
                    launchState = LaunchState.SPIN_UP;
                }
                break;

            case SPIN_UP:
                launcher.setVelocity(targetVel);
                if (launcher.getVelocity() > LAUNCHER_MIN_VELOCITY) {
                    launchState = LaunchState.LAUNCH;
                }
                break;

            case LAUNCH:
                leftFeeder.setPower(FULL_SPEED);
                rightFeeder.setPower(FULL_SPEED);
                feederTimer.reset();
                launchState = LaunchState.LAUNCHING;
                break;

            case LAUNCHING:
                if (feederTimer.seconds() > FEED_TIME_SECONDS) {
                    launchState = LaunchState.IDLE;
                    leftFeeder.setPower(STOP_SPEED);
                    rightFeeder.setPower(STOP_SPEED);
                }
                break;
        }
    }
}

