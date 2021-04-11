package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Limagen.Imagen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Panel extends JPanel implements PropertyChangeListener {

    private Imagen img1;
    private Imagen img2;
    private Imagen img3;
    
    private Panel instance;private static final Logger log = LogManager.getRootLogger();


    public Panel() {
        instance = this;
        this.setBounds(1000, 600, 0, 0);
        this.setLayout(null);

        this.repaint();
        this.validate();
        
//        crearBtnImg1();
//        crearBtnImg2();
//        crearBtnGenerar();
    }
    
    public void setImg1(Imagen img1) {
        this.img1 = img1;
    }

    public void setImg2(Imagen img2) {
        this.img2 = img2;
    }

    public void setImg3(Imagen img3) {
        this.img3 = img3;
    }
    
    public Imagen getImg1() {
        return img1;
    }

    public Imagen getImg2() {
        return img2;
    }

    public Imagen getImg3() {
        return img3;
    }

    

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (this.img3 != null) {
            this.img3.paint(g, 10, 55);
        }
    }

//    public void crearBtnImg1() {
//        JButton btnOrdenar = new JButton();
//        btnOrdenar.setText("Import img 1");
//        btnOrdenar.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                JFileChooser fileChooser = new JFileChooser();
//                FileFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
//                fileChooser.addChoosableFileFilter(filter);
//                fileChooser.setFileFilter(filter);
//                int seleccion = fileChooser.showOpenDialog(instance);
//                File abreimg1 = fileChooser.getSelectedFile();
//                instance.img1 = new Imagen(abreimg1);
//                instance.img1.toPixeles();
//                log.info("se introduce img1");
//            }
//        });
//
//        btnOrdenar.setBounds(10, 10, 110, 40);
//        this.add(btnOrdenar);
//    }

//    public void crearBtnImg2() {
//        JButton crearBtnImg2 = new JButton();
//        crearBtnImg2.setText("Import Img 2");
//        crearBtnImg2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                JFileChooser fileChooser = new JFileChooser();
//                FileFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
//                fileChooser.addChoosableFileFilter(filter);
//                fileChooser.setFileFilter(filter);
//                int seleccion = fileChooser.showOpenDialog(instance);
//                File abreimg2 = fileChooser.getSelectedFile();
//                instance.img2 = new Imagen(abreimg2);
//                instance.img2.toPixeles();
//                log.info("se introduce img2");
//            }
//        });
//
//        crearBtnImg2.setBounds(130, 10, 110, 40);
//        this.add(crearBtnImg2);
//    }

//    public void crearBtnGenerar() {
//        JButton btnOrdenar = new JButton();
//        btnOrdenar.setText("Generar");
//        btnOrdenar.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                instance.img3 = new Imagen(img1, img2);
//                instance.img3.addObserver(instance);
//                instance.img3.toPixeles();
//                log.info("se genera img3");
//            }
//       });
//
//        btnOrdenar.setBounds(400, 10, 100, 40);
//        this.add(btnOrdenar);
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
}
