package proyectoreloj;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ProyectoReloj extends JFrame implements Runnable {
    private Thread thread;
    private Image offScreenImage;
    private Image backgroundImage;
    private Graphics offScreenGraphics;
    private Font font = new Font("Arial", Font.PLAIN, 18);;
    
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
            Thread.sleep(20); 
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
        
        drawBackground(offScreenGraphics);
        drawClock(offScreenGraphics);
        
        g.drawImage(offScreenImage, 0, 0, null);
    }
    
    private void drawBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    private void drawClock(Graphics g) {
        // Center and clock radius
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 5;
        
        // Clock styling
        int x = centerX - radius - 19;
        int y = centerY - radius - 19;
        // Get hour from system
        Calendar time = Calendar.getInstance(); 

        int hour = time.get(Calendar.HOUR_OF_DAY); 
        int minute = time.get(Calendar.MINUTE); 
        int second = time.get(Calendar.SECOND); 
        int millisecond = time.get(Calendar.MILLISECOND);

        if (hour > 12) { 
            hour -= 12; 
        }
        g.setColor(Color.white); 
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2); 
        
        // Dot in clock's center
        int circleRadius = 5;
        g.setColor(Color.black);
        g.fillOval(centerX - circleRadius, centerY - circleRadius, circleRadius * 2, circleRadius * 2); 
        
        // Dot inside previous dot
        int smallCircleRadius = 3;
        g.setColor(Color.orange);
        g.fillOval(centerX - smallCircleRadius, centerY - smallCircleRadius, smallCircleRadius * 2, smallCircleRadius * 2);

        // Naming hours
        g.setColor(Color.black); 
        g.setFont(font);
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        ((Graphics2D) g2d).setStroke(new BasicStroke(2));
        
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(-i * 30 + 90);
            int xCoord = (int)(Math.cos(angle) * (radius - 30));
            int yCoord = (int)(Math.sin(angle) * (radius - 30));
            g2d.drawString(Integer.toString(i), centerX + xCoord - 5, centerY - yCoord + 5);
        }

        // Lines for seconds
        for (int i = 0; i < 60; i++) {
            g2d.setColor(Color.gray); 
            double angle = Math.toRadians(-i * 6 + 90);
            int x1 = (int)(Math.cos(angle) * (radius - 5));
            int y1 = (int)(Math.sin(angle) * (radius - 5));
            int x2 = (int)(Math.cos(angle) * (radius - 10));
            int y2 = (int)(Math.sin(angle) * (radius - 10));
            g2d.drawLine(centerX + x1, centerY - y1, centerX + x2, centerY - y2);
        }
        
        // Lines for hours
        for (int i = 0; i < 12; i++) {
            g2d.setColor(Color.black); 
            ((Graphics2D) g2d).setStroke(new BasicStroke(3));
            double angle = Math.toRadians(-i * 30);
            int x1 = (int)(Math.cos(angle) * (radius - 5));
            int y1 = (int)(Math.sin(angle) * (radius - 5));
            int x2 = (int)(Math.cos(angle) * (radius - 15));
            int y2 = (int)(Math.sin(angle) * (radius - 15));
            g2d.drawLine(centerX + x1, centerY - y1, centerX + x2, centerY - y2);
        }
        
        g2d.dispose();

        double angle; 

        // Seconds
        ((Graphics2D) g).setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(Color.orange); 
        angle = Math.toRadians((15 - second - (millisecond / 1000.0)) * 6);
        x = (int)(Math.cos(angle) * (radius - 20)); 
        y = (int)(Math.sin(angle) * (radius - 20)); 
        int startXS = centerX - x / 5;
        int startYS = centerY + y / 5;
        g.drawLine(centerX, centerY, centerX + x, centerY - y); 
        g.drawLine(centerX, centerY, startXS, startYS);

        // Minutes
        float initialStrokeWidth = 3;
        float finalStrokeWidth = 7;
        ((Graphics2D) g).setStroke(new BasicStroke(initialStrokeWidth));
        g.setColor(Color.black); 
        angle = Math.toRadians((15 - minute - (second / 60.0)) * 6);
        int minuteHandLength = 90;
        x = (int)(Math.cos(angle) * minuteHandLength); 
        y = (int)(Math.sin(angle) * minuteHandLength);
        int startXMin = centerX + (int)(Math.cos(angle) * circleRadius);
        int startYMin = centerY - (int)(Math.sin(angle) * circleRadius);
        
        int otherX = centerX + (int)(Math.cos(angle) * (circleRadius + 20));
        int otherY = centerY - (int)(Math.sin(angle) * (circleRadius + 20));
        
        g.drawLine(startXMin, startYMin, otherX, otherY);
        ((Graphics2D) g).setStroke(new BasicStroke(finalStrokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(otherX, otherY, centerX + x * 2, centerY - y * 2);

        // Hours
        initialStrokeWidth = 4;
        finalStrokeWidth = 8;
        ((Graphics2D) g).setStroke(new BasicStroke(initialStrokeWidth));
        g.setColor(Color.black);
        angle = Math.toRadians((15 - (hour % 12) - (minute / 60.0) - (second / 3600.0)) * 30);
        int handLength = 60;
        x = (int)(Math.cos(angle) * handLength); 
        y = (int)(Math.sin(angle) * handLength);  
        int startX = centerX + (int)(Math.cos(angle) * circleRadius);
        int startY = centerY - (int)(Math.sin(angle) * circleRadius);

        otherX = centerX + (int)(Math.cos(angle) * (circleRadius + 20));
        otherY = centerY - (int)(Math.sin(angle) * (circleRadius + 20));

        g.drawLine(startX, startY, otherX, otherY);
        ((Graphics2D) g).setStroke(new BasicStroke(finalStrokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(otherX, otherY, centerX + x * 2, centerY - y * 2);
    }
    
    public ProyectoReloj() {
        super("Clock");
        setSize(1024, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(50, 50, 50));
        setResizable(false);
        
        String currentDirectory = System.getProperty("user.dir");
        String imagePath = currentDirectory + "/../../NetBeansProjects/Analog-Clock/src/images/watch.jpeg";
        backgroundImage = new ImageIcon(imagePath).getImage();
        
        thread = new Thread(this);
        thread.start();
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ProyectoReloj();
    }
    
}