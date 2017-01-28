import java.util.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.io.IOException;

/**
 * Simple demo that uses java.util.Timer to schedule a task 
 * to execute once 5 seconds have passed.
 */

public class ScheduleTask {
  Timer timer;
  
  public ScheduleTask(int seconds) {
    timer = new Timer();
    timer.schedule(new RemindTask(), seconds*1000);
  }
  
  class RemindTask extends TimerTask {
    public void run() {
      System.out.format("Time's up!%n");
      timer.cancel(); //Terminate the timer thread
    }
  } 
  
  public static void main(String args[]) {
    new ScheduleTask(5);
    System.out.format("Task scheduled.%n");
  }
}