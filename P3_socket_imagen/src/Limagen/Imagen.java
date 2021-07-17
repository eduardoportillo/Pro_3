package Limagen;

import java.io.File;
import java.util.Date;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.json.JSONObject;

import Singleton.SessionEnemigo;

import java.awt.Color;
import java.awt.Graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Imagen {

    private static final Logger log = LogManager.getRootLogger();

    private File file;
    private BufferedImage bufferedImage;
    private int width;
    private int height;
    private Pixel[][] pixels;

    private PropertyChangeSupport observed;

    public void addObserver(JPanel instancePanel) {
        observed.addPropertyChangeListener((PropertyChangeListener) instancePanel);
    }

    public void cambioOk() {
        try {
            observed.firePropertyChange("Imagen", 1, 2);

        } catch (Exception e) {
            log.info(e);
        }
    }

    public Imagen(String URL) {
        observed = new PropertyChangeSupport(this);
        this.file = new File(URL);
        try {
            this.bufferedImage = ImageIO.read(this.file);
            // toPixeles();
        } catch (Exception e) {
            log.error("Error al cambiar a buffered");
        }
    }

    public Imagen(File _file) {
        observed = new PropertyChangeSupport(this);
        this.file = _file;
        try {
            this.bufferedImage = ImageIO.read(this.file);
            toPixeles();
        } catch (Exception e) {
            log.error("Error al cambiar a buffered");
        }
    }

    public Imagen(int r, int g, int b, int width, int height) {
        observed = new PropertyChangeSupport(this);
        this.width = width;
        this.height = height;
        pixels = new Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel p = new Pixel(1, r, g, b);
                pixels[y][x] = p;
            }
        }
        // cambioOk();
    }

    public void pintarPixel(int x, int y) {
        pixels[y][x] = new Pixel(1, 255, 255, 255);
        cambioOk();
    }

    public void iniciarPintarParecidos(int x, int y) {
        new Thread() {
            public void run() {
                Pixel p = pixels[y][x];
                pintarParecidos(x, y, p, pixels, "");
                cambioOk();
                int cantidadBlancos = 0;
                for (int yi = 0; yi < pixels.length; yi++) {
                    for (int xi = 0; xi < pixels[yi].length; xi++) {
                        Pixel pixelBlanco = new Pixel(1, 255, 255, 255);
                        if (pixelBlanco.getColorInt() == pixels[yi][xi].getColorInt()) {
                            cantidadBlancos++;
                        }
                    }
                }

                int totalPixel = pixels.length * pixels[0].length;
                double porcentaje = (cantidadBlancos * 100) / totalPixel;
                log.info("CANTIDAD DE SELECCIONADOS = " + cantidadBlancos + " / " + totalPixel);
                log.info("%" + porcentaje);
                if (porcentaje > 50) {
                    JSONObject mensaje = new JSONObject();
                    mensaje.put("type", "gano_enemigo");
                    mensaje.put("porc", porcentaje);
                    SessionEnemigo.send(mensaje.toString());
                    observed.firePropertyChange("perdiste", 1, 2);
                }

            };
        }.start();
    }

    private void pintarParecidos(int x, int y, Pixel base, Pixel[][] pixels, String dir) {

        if (pixels[y][x].rgbaToInt() == new Pixel(255, 255, 255, 255).rgbaToInt() || pixels[y][x].getVerificado()) {
            return;
        } else {
            pixels[y][x] = new Pixel(1, 255, 255, 255);
            // verificar arriba
            try {
                if ((x - 1) > 0 && !dir.equals("abajo")) {
                    if (pixels[y][(x - 1)].rgbaToInt() == base.rgbaToInt()
                            && pixels[y][(x - 1)].getVerificado() != true) {
                        pintarParecidos((x - 1), y, base, pixels, "arriba");
                        pixels[y][(x - 1)].setVerificado(true);
                    }
                }
            } catch (Exception e) {
                log.error("ERROR:" + e.getLocalizedMessage());
            }

            // verificar abajo
            try {
                if ((y + 1) <= pixels.length && !dir.equals("arriba")) {
                    if (pixels[(y + 1)][x].rgbaToInt() == base.rgbaToInt()
                            && pixels[(y + 1)][x].getVerificado() != true) {
                        pintarParecidos(x, (y + 1), base, pixels, "abajo");

                        pixels[(y + 1)][x].setVerificado(true);
                    }
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }

            // verificar derecha
            try {
                if ((x + 1) <= pixels[y].length && !dir.equals("izquierda")) {
                    if (pixels[y][(x + 1)].rgbaToInt() == base.rgbaToInt()
                            && pixels[y][(x + 1)].getVerificado() != true) {
                        pintarParecidos((x + 1), y, base, pixels, "derecha");
                        pixels[y][(x + 1)].setVerificado(true);
                    }
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }

            // verificar izquierda
            try {
                if ((y - 1) >= 0 && !dir.equals("derecha")) {
                    if (pixels[(y - 1)][x].rgbaToInt() == base.rgbaToInt()
                            && pixels[(y - 1)][x].getVerificado() != true) {
                        pintarParecidos(x, (y - 1), base, pixels, "izquierda");
                        pixels[(y - 1)][x].setVerificado(true);
                    }
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        }
        return;
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

    public void paint(Graphics g, int _x, int _y, int tamnho) {
        for (int y = 0; y < this.pixels.length; y++) {
            for (int x = 0; x < this.pixels[0].length; x++) {
                Pixel pTemp = this.pixels[y][x];
                g.setColor(pTemp.getColor());
                g.fillRect(_x + (x * tamnho), _y + (y * tamnho), tamnho, tamnho);
            }
        }
    }
}