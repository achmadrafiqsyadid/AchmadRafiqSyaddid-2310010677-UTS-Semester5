package UTS;

// 1. Import library FlatLaf

import com.formdev.flatlaf.FlatIntelliJLaf; // Atau FlatLightLaf
import javax.swing.UIManager;
import UTS.Ui.MainFrame;
import javax.swing.UnsupportedLookAndFeelException;

public class app {

    public static void main(String[] args) {
        
        // 3. Atur Look and Feel
        try {
            // Anda bisa ganti ke FlatDarkLaf() jika ingin tema gelap
            UIManager.setLookAndFeel(new FlatIntelliJLaf()); 
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF");
        }

        // 4. Jalankan MainFrame Anda
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}