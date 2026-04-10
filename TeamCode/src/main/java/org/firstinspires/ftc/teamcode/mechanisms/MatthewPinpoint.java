/*
 * Copyright (c) 2025 Alan Smith
 *
 * ... [Standard License Text] ...
 */
package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@TeleOp(name = "MatthewPinpoint", group = "mechanisms")
public class MatthewPinpoint extends OpMode {

    // Create an instance of the sensor
    GoBildaPinpointDriver pinpoint;

    // --- Define Goal Coordinates ---
    public static final double RED_GOAL_X = 57.0;
    public static final double RED_GOAL_Y = 57.0;

    public static final double BLUE_GOAL_X = -57.0;
    public static final double BLUE_GOAL_Y = 58.0;

    @Override
    public void init() {
        // Get a reference to the sensor
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        // Configure the sensor
        configurePinpoint();

        // Set the location of the robot - this should be the place you are starting the robot from
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
    }

    @Override
    public void loop() {
        telemetry.addLine("Push your robot around to see it track");
        telemetry.addLine("Press A to reset the position");

        if(gamepad1.a){
            // You could use readings from April Tags here to give a new known position to the pinpoint
            pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
        }

        pinpoint.update();
        Pose2D pose2D = pinpoint.getPosition();

        // Extract current X and Y.
        // Note: Keeping your *-1 inversion so it matches your field perspective.
        double currentX = pose2D.getX(DistanceUnit.INCH) * -1;
        double currentY = pose2D.getY(DistanceUnit.INCH) * -1;

        // --- Calculate Distances using Math.hypot ---
        double distToRed = Math.hypot(RED_GOAL_X - currentX, RED_GOAL_Y - currentY);
        double distToBlue = Math.hypot(BLUE_GOAL_X - currentX, BLUE_GOAL_Y - currentY);

        // Display current position
        telemetry.addData("X coordinate (IN)", currentX);
        telemetry.addData("Y coordinate (IN)", currentY);
        telemetry.addData("Heading angle (DEGREES)", pose2D.getHeading(AngleUnit.DEGREES) * -1);

        // Display distances
        telemetry.addLine("--- Goal Distances ---");
        telemetry.addData("Distance to Red Goal (IN)", "%.2f", distToRed);
        telemetry.addData("Distance to Blue Goal (IN)", "%.2f", distToBlue);

        telemetry.update(); // Good practice to ensure telemetry pushes to the DS
    }

    public void configurePinpoint(){
        // [Your existing pinpoint configuration remains exactly the same]
        pinpoint.setOffsets(-20, -20, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
    }
}