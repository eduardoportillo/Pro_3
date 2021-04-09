package Limagen;

import Image.Pixel;
import java.io.File;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Imagen {

    private File file;
    private BufferedImage bufferedImage;
    private int width;
    private int height;
    private Pixel[][] pixels;

    private static final Logger log = LogManager.getRootLogger();

    private PropertyChangeSupport observed;

    public void addObserver(PropertyChangeListener panel) {
        observed.addPropertyChangeListener(panel);
    }

    public void cambioOk() {
        observed.firePropertyChange("Imagen", 1, 2);
    }

    public Imagen(String URL) {
        observed = new PropertyChangeSupport(this);
        this.file = new File(URL);
        try {
            this.bufferedImage = ImageIO.read(this.file);
            // toPixeles();
        } catch (Exception e) {
            log.error("Error al cambiar a buffered linea 38 ");
        }
    }

    public Imagen(File _file) {
        observed = new PropertyChangeSupport(this);
        this.file = _file;
        try {
            this.bufferedImage = ImageIO.read(this.file);
            // toPixeles();
        } catch (Exception e) {
            log.error("Error al cambiar a buffered 49");
        }
    }

    public Imagen(Imagen img1, Imagen img2) {
        observed = new PropertyChangeSupport(this);
        // this.file = new File("img/imgResultante-" + new Date().toString() + ".png");

        Pixel[][] pixels1 = img1.getPixels();
        Pixel[][] pixels2 = img2.getPixels();
        try {
            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                log.info("img2 null");
                img2 = null;
            }
            width = (img1.getWidth() + img2.getWidth()) / 2;
            height = (img1.getHeight() + img2.getHeight()) / 2;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (x < width / 3) {
                        image.setRGB(x, y, pixels1[y][x].getColor().getRGB());
                    } else if (x < (width / 3) * 2) {
                        int red = (pixels1[y][x].getRed() + pixels2[y][x].getRed()) / 2;
                        int green = (pixels1[y][x].getGreen() + pixels2[y][x].getGreen()) / 2;
                        int blue = (pixels1[y][x].getBlue() + pixels2[y][x].getBlue()) / 2;
                        int alpha = (pixels1[y][x].getAlpha() + pixels2[y][x].getAlpha()) / 2;
                        Color colorTemp = new Color(red, green, blue, alpha);
                        image.setRGB(x, y, colorTemp.getRGB());
                    } else {
                        image.setRGB(x, y, pixels2[y][x].getColor().getRGB());
                    }
                }
            }
            this.bufferedImage = image;

        } catch (Exception e) {
            log.error("Las imagenes no son del mismo tamaño Introdusca otra");
        }

    }

    public void toPixeles() {
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        pixels = new Pixel[height][width];
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                Pixel p = new Pixel(bufferedImage.getRGB(x, y));
                pixels[y][x] = p;
            }
        }
        cambioOk();
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void paint(Graphics g, int _x, int _y) {
        int tamnho = 1;
        for (int y = 0; y < this.pixels.length; y++) {
            for (int x = 0; x < this.pixels[0].length; x++) {
                Pixel pTemp = this.pixels[y][x];
                g.setColor(pTemp.getColor());
                g.fillRect(_x + (x * tamnho), _y + (y * tamnho), tamnho, tamnho);
            }
        }
    }
}
