package Limagen;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONObject;

import Limagen.Imagen;
import Singleton.SessionEnemigo;

public class PanelImagen extends JPanel implements PropertyChangeListener {

    private Imagen img;
    private int tamanhoPixel = 1;
    private PanelImagen INSTANCE;
    private boolean enemigo;

    public PanelImagen(boolean enemigo) {
        this.enemigo = enemigo;
        this.INSTANCE = this;
        init();
        mouse();
    }

    private void init() {
        this.setBounds(0, 0, 0, 0);
        this.setLayout(null);
        this.setVisible(true);

        this.repaint();
        this.validate();
    }

    public void setImg(Imagen img1) {
        this.img = img1;
        img.addObserver(this);
        this.setBounds(50, 50, this.img.getWidth() * tamanhoPixel, this.img.getHeight() * tamanhoPixel);
        this.validate();
        this.repaint();
    }

    public void setImg(Imagen img1, int x, int y) {
        this.img = img1;
        img.addObserver(this);
        this.setBounds(x, y, this.img.getWidth() * tamanhoPixel, this.img.getHeight() * tamanhoPixel);
        this.validate();
        this.repaint();
    }

    public Imagen getImg() {
        return img;
    }

    private void mouse() {
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                while (this != null) {
                    // Se inserta la ubicacion del mouse
                    int x = (int) e.getPoint().getX() / tamanhoPixel;
                    int y = (int) e.getPoint().getY() / tamanhoPixel;

                    // JOptionPane.showMessageDialog(null, "Posicion x = " + x + " Posicion y = " +
                    // y);
                    if (enemigo) {
                        INSTANCE.img.pintarPixel(x, y);
                        // img.iniciarPintarParecidos(x, y);
                        JSONObject obj = new JSONObject();
                        obj.put("type", "click");
                        obj.put("x", x);
                        obj.put("y", y);
                        SessionEnemigo.send(obj.toString());

                    }
                    return;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (this.img != null) {
            this.img.paint(g, 0, 0, tamanhoPixel);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("perdiste")) {
            JOptionPane.showMessageDialog(null, "PERDISTE :(");
        }
        repaint();
    }
}
