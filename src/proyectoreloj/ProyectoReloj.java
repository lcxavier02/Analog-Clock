package proyectoreloj;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ProyectoReloj extends JFrame implements Runnable {
    private Thread thread;
    private Image offScreenImage;
    private Graphics offScreenGraphics;
    
    @Override
    public void run() {
        while (true) {
            repaint();
            delayAnimation();
        }
    }
    
    private void delayAnimation() 
    { 
        try { 
  
            // Animation delay is 1000 milliseconds 
            Thread.sleep(1); 
        } 
        catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 
    }
    
    @Override
    public void paint(Graphics g) 
    { 
        if (offScreenImage == null) {
            offScreenImage = createImage(getWidth(), getHeight());
            offScreenGraphics = offScreenImage.getGraphics();
        }
        
        // Draw on the off-screen image
        drawClock(offScreenGraphics);
        
        // Draw the off-screen image to the screen
        g.drawImage(offScreenImage, 0, 0, null);
    }
    
    private void drawClock(Graphics g) {
        // Clear the background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Get the system time 
        Calendar time = Calendar.getInstance(); 

        int hour = time.get(Calendar.HOUR_OF_DAY); 
        int minute = time.get(Calendar.MINUTE); 
        int second = time.get(Calendar.SECOND); 
        int millisecond = time.get(Calendar.MILLISECOND);

        // 12 hour format 
        if (hour > 12) { 
            hour -= 12; 
        } 
        
        // Calculate center of the JFrame
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Draw clock body center at (centerX, centerY)
        int radius = Math.min(getWidth(), getHeight()) / 4;
        g.setColor(Color.white); 
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2); 

        // Labeling 
        g.setColor(Color.black); 
        g.drawString("12", centerX - 7, centerY - radius + 15); 
        g.drawString("9", centerX - radius + 10, centerY + 5); 
        g.drawString("6", centerX - 3, centerY + radius - 5); 
        g.drawString("3", centerX + radius - 15, centerY + 5); 

        // Declaring variables to be used 
        double angle; 
        int x, y; 

        // Second hand's angle in Radian 
        angle = Math.toRadians((15 - second - (millisecond / 1000.0)) * 6); 

        // Position of the second hand 
        // with length 100 unit 
        x = (int)(Math.cos(angle) * (radius - 20)); 
        y = (int)(Math.sin(angle) * (radius - 20)); 

        // Red color second hand 
        g.setColor(Color.red); 
        g.drawLine(centerX, centerY, centerX + x, centerY - y); 

        // Minute hand's angle in Radian 
        angle = Math.toRadians((15 - minute - (second / 60.0)) * 6); 

        // Position of the minute hand 
        // with length 80 unit 
        x = (int)(Math.cos(angle) * (radius - 40)); 
        y = (int)(Math.sin(angle) * (radius - 40)); 

        // blue color Minute hand 
        g.setColor(Color.blue); 
        g.drawLine(centerX, centerY, centerX + x, centerY - y); 

        // Hour hand's angle in Radian 
        angle = Math.toRadians((15 - (hour % 12) - (minute / 60.0) - (second / 3600.0)) * 30);

        // Position of the hour hand 
        // with length 50 unit 
        x = (int)(Math.cos(angle) * (radius - 60)); 
        y = (int)(Math.sin(angle) * (radius - 60)); 

        // Black color hour hand 
        g.setColor(Color.black); 
        g.drawLine(centerX, centerY, centerX + x, centerY - y);
    }
    
    public ProyectoReloj() {
        super("Atomic Clock");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(50, 50, 50));
        
        
        thread = new Thread(this);
        thread.start();
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ProyectoReloj();
    }
    
}