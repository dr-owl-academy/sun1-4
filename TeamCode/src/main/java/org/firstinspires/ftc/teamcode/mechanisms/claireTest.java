package org.firstinspires.ftc.teamcode.mechanisms

public class claireTest {
    private DcMotor Flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;




    Flywheel = hmMap.get(DcMotor .class, "Flywheel");
    Flywheel.setMode(DcMotor . RunMode. RUN_USING_ENCODER);

}