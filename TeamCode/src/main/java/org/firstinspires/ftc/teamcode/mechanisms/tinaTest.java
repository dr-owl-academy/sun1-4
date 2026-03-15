package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class tinaTest {
    private DcMotor Flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;


    @Override
    public void init(HardwareMap hwMap){
        Flywheel = hwMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront = hwMap.get(DcMotor.class, "frontRight");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hwMap.get(DcMotor.class, "backRight");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hwMap.get(DcMotor.class, "frontLeft");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack = hwMap.get(DcMotor.class, "backLeft");
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }


    @Override
    public void loop() {

        init(hardwareMap);

        if (gamepad1.y) {
            rightFront.setPower(0.3);
        }else{
            rightFront.setPower(0.0);
        }
        if (gamepad1.a) {
            rightBack.setPower(0.3);
        }else{
            rightBack.setPower(0.0);
        }
        if (gamepad1.x) {
            leftFront.setPower(0.3);
        }else{
            leftFront.setPower(0.0);
        }
        if (gamepad1.b) {
            leftBack.setPower(0.3);
        }else{
            leftBack.setPower(0.0);
        }
        if (gamepad1.left_bumper) {
            Flywheel.setPower(0.3);
        }else{
            Flywheel.setPower(0.0);
        }

    }
}




