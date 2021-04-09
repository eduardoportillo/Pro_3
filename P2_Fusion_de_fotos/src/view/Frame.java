package view;

import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame() {
        this.setSize(895, 585); // Tamanho del frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Para cerrar la ventana en close
        this.setLocationRelativeTo(null); // Para habrir el frame al medio
        this.setVisible(true);
        this.setTitle("Imagenes");
        this.setResizable(false);
        this.repaint();
    }
}
