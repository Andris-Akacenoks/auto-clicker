package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.InputEvent;

public class Controller {
    @FXML public Button startClicking ;
    @FXML public Button stopClicking ;
    @FXML public TextField frequencyInMilliseconds;
    private boolean isClickingActive = false;

    public void handleStartClicking() {
        activateClicking();
        startClicking.setDisable(true);
        stopClicking.setDisable(false);
        performClicking();
    }

    public void handleStopClicking(){
        disableClicking();
        startClicking.setDisable(false);
        stopClicking.setDisable(true);
    }

    private void performClicking(){
        new Thread(new Runnable() {
            public void run() {
                Robot robot = setRobot();
                int frequency = getFrequency();
                do {
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    try {
                        Thread.sleep(frequency);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PointerInfo a = MouseInfo.getPointerInfo();
                    Point b = a.getLocation();
                    robot.mouseMove((int) b.getX(), (int) b.getY());
                } while(isClickingActive);
            }
        }).start();
    }

    private void activateClicking(){
        this.isClickingActive = true;
    }

    private void disableClicking(){
        this.isClickingActive = false;
    }

    private int getFrequency(){
        try {
            int frequency = Integer.valueOf(frequencyInMilliseconds.getText());
            if (frequency >= 50 && frequency <= 60 * 1000) {
                return frequency;
            } else {
                frequencyInMilliseconds.setText(String.valueOf(1000));
                return 1000;
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("No integer is specified");
        }
    }

    private Robot setRobot(){
        try {
            return new Robot();
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        return null;
    }

}
