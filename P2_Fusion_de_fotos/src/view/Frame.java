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

    private Frame instance;
    private static final Logger log = LogManager.getRootLogger();

    Panel panel = new Panel();

    public Frame() {
        init();
        crearMenu();
    }

    private void init() {
        instance = this;
        this.setSize(895, 585);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Imagenes");
        this.setResizable(false);
        this.repaint();
        this.add(panel);
    }

    private void crearMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");

        JMenuItem img1 = new JMenuItem("Insertar img 1");
        JMenuItem img2 = new JMenuItem("Insertar img 2");
        JMenuItem generar = new JMenuItem("Generar");
        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem exit = new JMenuItem("salir");

        JMenuItem Acercade = new JMenuItem("Acerca de");

        barra.add(file);
        barra.add(help);

        file.add(img1);
        file.add(img2);
        file.add(generar);
        file.add(reset);
        file.add(exit);

        help.add(Acercade);
        setJMenuBar(barra);

        logicaItemMenuPanel(img1, img2, generar);
        logicaItemsExtra(exit, reset, Acercade);
    }

    private void logicaItemMenuPanel(JMenuItem img1, JMenuItem img2, JMenuItem generar) {
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

    private void logicaItemsExtra(JMenuItem exit, JMenuItem reset, JMenuItem Acercade) {

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setImg1(null);
                panel.setImg2(null);
                panel.setImg3(null);
                instance.repaint();
            }
        });
    }
}
