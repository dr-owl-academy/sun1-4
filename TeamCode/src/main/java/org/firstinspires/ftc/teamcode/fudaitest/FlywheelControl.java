package org.firstinspires.ftc.teamcode.fudaitest;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Flywheel Adjuster")
public class FlywheelControl extends OpMode {

    // Use DcMotorEx for better velocity control
    private DcMotorEx flywheel;

    double targetVelocity = 2000; // Ticks per second
    double increment = 50;       // How much to change per click
    boolean isRunning = false;

    // "Rising Edge" detection variables
    boolean lastUp = false;
    boolean lastDown = false;
    boolean lastA = false;

    @Override
    public void init() {
        // "flywheel" must match your configuration on the Hub
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");

        // Use encoders for consistent speed regardless of battery level
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Let it coast to a stop naturally to save the gearbox
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // --- ADJUST SPEED ---
        // Increase speed with Dpad Up
        if (gamepad1.dpad_up && !lastUp) {
            targetVelocity += increment;
        }
        lastUp = gamepad1.dpad_up;

        // Decrease speed with Dpad Down
        if (gamepad1.dpad_down && !lastDown) {
            targetVelocity -= increment;
        }
        lastDown = gamepad1.dpad_down;

        // --- TOGGLE ON/OFF ---
        if (gamepad1.a && !lastA) {
            isRunning = !isRunning;
        }
        lastA = gamepad1.a;

        // --- APPLY POWER ---
        if (isRunning) {
            flywheel.setVelocity(targetVelocity);
        } else {
            flywheel.setVelocity(0);
        }

        // --- FEEDBACK ---
        telemetry.addData("Motor State", isRunning ? "RUNNING" : "STOPPED");
        telemetry.addData("Target Velocity", targetVelocity);
        telemetry.addData("Actual Velocity", flywheel.getVelocity());
        telemetry.update();
    }
}
