package uts;

import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import uts.ui.MainFrame;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
