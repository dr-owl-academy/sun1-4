package org.firstinspires.ftc.teamcode.mechanism;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class matthewtest {
private DcMotor leftFront;
private DcMotor rightFront;
private DcMotor leftBack;
private DcMotor rightBack;
private DcMotor Flywheel;

public void  init(HardwareMap hwMap){
    Flywheel = hwMap.get(DcMotor.class, "Flywheel");
    Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
}
}
