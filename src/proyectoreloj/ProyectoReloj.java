package proyectoreloj;

import javax.swing.*;
import java.awt.*;

public class ProyectoReloj extends JFrame {
    
    public ProyectoReloj() {
        super("Atomic Clock");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ProyectoReloj();
    }
    
}