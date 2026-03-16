package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class EliTest {
    // your code here


    private DcMotor flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;

    // constants for encoder calculations
    private static final double ticksPerRev = 537.6; // example for GoBilda motor
    private static final double inPerTick = 0.01;    // adjust for your wheel setup


    public void init(HardwareMap hwMap){

        flywheel = hwMap.get(DcMotor.class, "Flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront = hwMap.get(DcMotor.class, "rightFront");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront = hwMap.get(DcMotor.class, "leftFront");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightBack = hwMap.get(DcMotor.class, "rightBack");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftBack = hwMap.get(DcMotor.class, "leftBack");
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMotorSpeed(double speed) {
        flywheel.setPower(speed);
    }

    public double getMotorRevs() {
        return flywheel.getCurrentPosition() / ticksPerRev;
    }

    public void rotateInInches(double distance) {
        int distanceInTicks = (int) Math.round(distance / inPerTick);

        flywheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel.setTargetPosition(distanceInTicks);
        flywheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flywheel.setPower(1.0);
    }
}
