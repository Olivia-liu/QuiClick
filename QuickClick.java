import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.io.*;
  
public class QuickClick extends JFrame implements KeyListener {

    JLabel label;
    Timer timer;
    static int counter;
    static boolean restart = true;
    static int times = 1;
    static double seconds = 2;
    static boolean print = false;
    static boolean excess = false;
    static int three_two_one_zero = 3;
    static int num_hits;
    static int score = 0;
    static double score_base = 10;
    static double score_constant = 1.2;
    static int lives = 5;
    static boolean highest = false;

    public QuickClick(String s) {
        
        super(s);
        JPanel p = new JPanel();
        label = new JLabel("Key Listener!");
        p.add(label);
        add(p);
        addKeyListener(this);
        setSize(200, 100);
        setVisible(true); 
        
    }
    
    public void countDown(Timer timer2){
      timer2.schedule(new CountDown(), 1000);
    }
      
      
    public void doThings(double seconds){
   
      timer = new Timer();
      timer.schedule(new RemindTask(), (int)seconds*1000);
      
      
      counter = 0;
      restart = false;
      excess = true;
      
    }
    
    class RemindTask extends TimerTask {
      public void run() {
        double constant = 0.3;
          if (counter == num_hits){
            print = false;
            System.out.println("Good Job!");
            
            score += (int)score_base;
            score_base *= score_constant;
            
            System.out.println("Your score is: " + score);
            System.out.println();
            timer.cancel();
            
            seconds = seconds - constant/times;
            times*=2;
          }
          
          else if (counter < num_hits){
            print = false;
            System.out.println("Time's up!");
            System.out.println("Your score is: " + score);
            lives--;
            System.out.println();
            timer.cancel(); //Terminate the timer thread
            
            //System.exit(0);
          }
          else {
            //System.out.println("Too many!");
            timer.cancel();
          }
          counter = 0;
          restart = true;
          
        
      }
    } 
    
    class CountDown extends TimerTask{
      public void run(){
        System.out.println( three_two_one_zero );
      }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

       /* if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key typed");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key typed");
        }*/

    }

    @Override
    public void keyPressed(KeyEvent e) {

        /*if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key pressed");
        }*/
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT && print) {
            System.out.println("Left key pressed");
            counter++;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {/*
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key Released");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key Released");
        }*/
    }

    public static void main(String[] args) throws InterruptedException{
      
      QuickClick mygame = new QuickClick("Click Left key 5 times Quickly!");      
      
      while(true){
        if (counter > num_hits && excess){
          print = false;
          System.out.println("Too many!");
          System.out.println("Your score is: " + score);
          lives--;
          System.out.println();
          excess = false;
          
        }
        
        if (lives == 0){
          restart = false;
          System.out.println("You are dead!!");
          System.out.println("You final score is: " + score);
          
          int highest_in_file = 0;
          String new_name = "Tommy Trojan";
          
          String line1 = "0";
          String line2 = "Tommy Trojan";
          
          try{
            Scanner fileReader = new Scanner(new File("highest_score.txt"));
            line1 = fileReader.nextLine();
            line2 = fileReader.nextLine();
            highest_in_file = Integer.parseInt(line1);
            fileReader.close();     
          }
          
          catch(FileNotFoundException ex) {
            System.out.println(
                               "Unable to open file '" + 
                               "highest_score.txt" + "'");                
          }
          
          catch(IOException ex) {
            System.out.println(
                               "Error reading file '" 
                                 + "highest_score.txt" + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
          }
          
          if (score > highest_in_file){
            System.out.println("New highest score!! What's your name hero?");
            Scanner ask_name = new Scanner(System.in);
            new_name = ask_name.next();
            highest = true;
            ask_name.close();
          }
          
          else{
            System.out.println("The highest score was " + line1 + " acheived by " + line2);
          }
          
          if (highest) { 
            try {
              // Assume default encoding.
              FileWriter fileWriter =
                new FileWriter("highest_score.txt");
              
              // Always wrap FileWriter in BufferedWriter.
              BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);
              
              // Note that write() does not automatically
              // append a newline character.
              //String score_string = new String(score);
              bufferedWriter.write(Integer.toString(score));
              bufferedWriter.newLine();
              bufferedWriter.write(new_name);
              
              // Always close files.
              bufferedWriter.close();
            }
            catch(IOException ex) {
              System.out.println(
                                 "Error writing to file '"
                                   + "higest_score.txt" + "'");
              // Or we could just do this:
              // ex.printStackTrace();
            }
          }
          
          System.out.println("Wanna play again? (y/n)");
          Scanner scanner = new Scanner(System.in);
          String isAgain = scanner.next();
          scanner.close();
          if (isAgain.equals("y")){
            restart = true;
            score = 0;
            seconds = 2;
            lives = 5;
            highest = false;
            score_base = 10;
            times = 1;
          }
          else break;
        }
        
        if (restart){
          three_two_one_zero = 3;
          
          Random random_hits = new Random();
          num_hits = random_hits.nextInt(5);
          num_hits += 3;
          
          System.out.println("Click Left key " + num_hits + " times Quickly in " + seconds/5*num_hits + " seconds!");
          
          
          Thread.sleep(1000);
          System.out.println(three_two_one_zero--);
          Thread.sleep(1000);
          System.out.println(three_two_one_zero--);
          Thread.sleep(1000);
          System.out.println(three_two_one_zero--);
          Thread.sleep(1000);
          System.out.println("Go!");
          
          mygame.doThings(seconds);
          print = true;
        }
      }
    }
}