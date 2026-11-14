package org.firstinspires.ftc.teamcode;
//3795 TeleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Ttele extends OpMode
{
    public DcMotor BR, BL, FR, FL;

    @Override
    public void init()
    {
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BR.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        FL.setPower(0);
    }

    @Override
    public void loop()
    {
        // Left Joystick for Movement (Mecanum drive)
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        double lf = drive + strafe + rotate;
        double lb = drive - strafe + rotate;
        double rf = drive - strafe - rotate;
        double rb = drive + strafe - rotate;

        // Normalize wheel powers if any exceeds 1.0
        double max = Math.max(Math.max(Math.abs(lf), Math.abs(lb)), Math.max(Math.abs(rf), Math.abs(rb)));
        if (max > 1.0) {
            lf /= max;
            lb /= max;
            rf /= max;
            rb /= max;
        }

        FL.setPower(lf);
        BL.setPower(lb);
        FR.setPower(rf);
        BR.setPower(rb);

        // Telemetry
        telemetry.addData("FL Power", lf);
        telemetry.addData("BL Power", lb);
        telemetry.addData("FR Power", rf);
        telemetry.addData("BR Power", rb);
        telemetry.update();
    }
}
