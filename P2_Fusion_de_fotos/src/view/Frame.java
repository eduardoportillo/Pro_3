package view;

import Limagen.Imagen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Frame extends JFrame {

    private Panel instance;
    private static final Logger log = LogManager.getRootLogger();

    Panel panel = new Panel();

    public Frame() {
        this.setSize(895, 585); // Tamanho del frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Para cerrar la ventana en close
        this.setLocationRelativeTo(null); // Para habrir el frame al medio
        this.setVisible(true);
        this.setTitle("Imagenes");
        this.setResizable(false);
        this.repaint();
        this.add(panel);
        crearMenu();
    }

    private void crearMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu File = new JMenu("File");
        JMenu Help = new JMenu("Help");
        JMenuItem img1 = new JMenuItem("Insertar img 1");
        JMenuItem img2 = new JMenuItem("Insertar img 2");
        JMenuItem generar = new JMenuItem("Generar");
        JMenuItem Acercade = new JMenuItem("Acerca de");
        barra.add(File);
        barra.add(Help);
        File.add(img1);
        File.add(img2);
        File.add(generar);
        Help.add(Acercade);
        setJMenuBar(barra);

        //Logica items menu
        img1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter);
                int seleccion = fileChooser.showOpenDialog(panel);
                File abreimg1 = fileChooser.getSelectedFile();
                panel.setImg1(new Imagen(abreimg1));
                panel.getImg1().toPixeles();
                log.info("se introduce img1");
            }
        });
                
        img2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter);
                int seleccion = fileChooser.showOpenDialog(panel);
                File abreimg2 = fileChooser.getSelectedFile();
                panel.setImg2(new Imagen(abreimg2));
                panel.getImg2().toPixeles();
                log.info("se introduce img");
            }
        });
        
        generar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setImg3(new Imagen(panel.getImg1(), panel.getImg2()));
                panel.getImg3().addObserver(panel);
                panel.getImg3().toPixeles();
                log.info("se genera img3");
            }
        });
    }
}
