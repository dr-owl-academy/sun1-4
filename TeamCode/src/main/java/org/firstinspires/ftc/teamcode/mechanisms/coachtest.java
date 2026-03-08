package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class coachtest {
    private DcMotor Flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;




    public void init(HardwareMap hwMap){
        Flywheel = hwMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hwMap.get((DcMotor.class, "rightBack");
        rightBack.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void setMotorSpeed(double speed){
        motor.setPower(speed);
    }

    public double getMotorRevs(){
        return motor.getCurrentPosition()/ticksPerRev;
    }

    public void rotateInInches(double distance) {
        int distanceInTicks = (int) Math.round(distance / inPerTick);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(distanceInTicks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
}
