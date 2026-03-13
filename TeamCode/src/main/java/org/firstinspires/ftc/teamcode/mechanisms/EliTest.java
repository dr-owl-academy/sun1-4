package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.security.PublicKey;

public class EliTest {
}
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack
    public DcMotor rightBack

@Override
public void init(HardwareMap hwMap) {
    Flywheel = hwMap.get(DcMotor .class,"Flywheel");
    Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightFront = hwMap.get(DcMotor .class,"rightFront");
    rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightBack = hwMap.get(DcMotor.class, "rightBack");
    rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftFront = hwMap.get(DcMotor.class, "leftFront");
    leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftFront.setDirection(DcMotor.Direction.REVERSE);
    leftBack = hwMap.get(DcMotor.class, "leftBack");
    leftBack.setDirection(DcMotor.Direction.REVERSE);
    leftBack.setDirection(DcMotor.Direction.REVERSE);

    @Override
            public void loop(){
}



