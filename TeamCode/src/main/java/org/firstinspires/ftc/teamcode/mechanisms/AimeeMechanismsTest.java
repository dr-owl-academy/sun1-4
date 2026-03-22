package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class AimeeMechanismsTest extends OpMode {
    private DcMotor Flywheel;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;

    private CRServo leftTransfer;

    private CRServo rightTransfer;


    @Override
    public void init(){
        Flywheel = hardwareMap.get(DcMotor.class, "Flywheel");
        Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack = hardwareMap.get(DcMotor.class, "backRight");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack = hardwareMap.get(DcMotor.class, "backLeft");
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftTransfer = hardwareMap.get(CRServo.class, "leftTransfer");
        rightTransfer = hardwareMap.get(CRServo.class, "rightTransfer");

    }


    @Override
    public void loop() {
        if (gamepad1.x){
            leftFront.setPower(0.5);
        } else {
            leftFront.setPower(0.0);
        }


        if (gamepad1.y){
            rightFront.setPower(0.5);
        } else {
            rightFront.setPower(0.0);
        }


        if (gamepad1.a){
            leftBack.setPower(0.5);
        } else {
            leftBack.setPower(0.0);
        }


        if (gamepad1.b){
            rightBack.setPower(0.5);
        } else {
            rightBack.setPower(0.0);
        }

        if (gamepad1.left_bumper) {
            Flywheel.setPower(0.5);
        } else {
            Flywheel.setPower(0.0);
        }

        if (gamepad1.dpad_left) {
            leftTransfer.setPower(0.5);
        } else {
            leftTransfer.setPower(0.0);
        }

        if(gamepad1.dpad_right) {
            rightTransfer.setPower(0.5);
        } else {
            rightTransfer.setPower(0.0);
        }

        /** one of the Transfers has to be in reverse I think so add that later **/

        telemetry.addData("RF", rightFront.getPower());
        telemetry.addData("LF", leftFront.getPower());
        telemetry.addData("LB", leftBack.getPower());
        telemetry.addData("RB", rightBack.getPower());
        telemetry.addData("Flywheel", Flywheel.getPower());
        telemetry.addData("leftTransfer", leftTransfer.getPower());
        telemetry.addData("rightTransfer", rightTransfer.getPower());
        telemetry.update();

    }
}




