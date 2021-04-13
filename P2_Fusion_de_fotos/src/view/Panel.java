package view;

import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import Limagen.Imagen;

public class Panel extends JPanel implements PropertyChangeListener {

    private Imagen img1;
    private Imagen img2;
    private Imagen img3;

    public Panel() {
        init();
    }
    
    private void init(){
        this.setBounds(1000, 600, 0, 0);
        this.setLayout(null);

        this.repaint();
        this.validate();
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
}
