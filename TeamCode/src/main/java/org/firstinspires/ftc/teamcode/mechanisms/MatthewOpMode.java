package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class MatthewOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor Flywheel;

    public void  init(HardwareMap hwMap){
        Flywheel = hwMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hwMap.get(DcMotor.class, "rightBack");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hwMap.get(DcMotor.class, "leftFront");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack = hwMap.get(DcMotor.class, "leftBack");
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

    }
   public void wheels () {
       if (gamepad1.yWasPressed) {
           leftFront.setPower(0.5);
           if (gamepad1.yWasReleased) {
               leftFront.setPower(0);
           }
       }
       if (gamepad1.yWasPressed) {
           leftBack.setPower(0.5);
           if (gamepad1.yWasReleased) {
               leftBack.setPower(0)
           }
       }
       if (gamepad1.yWasPressed) {
           rightFront.setPower(0.5);
           if (gamepad1.yWasReleased) {
               rightFront.setPower(0)
           }
       }
       if (gamepad1.yWasPressed) {
           rightBack.setPower(0.5);
           if (gamepad1.yWasReleased) {
               rightBack.setPower(0)
           }
       }
   }