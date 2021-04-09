package Image;

import java.awt.Color;

public class Pixel {

    private int alpha;
    private int red;
    private int green;
    private int blue;
    private int colorInt;
    private Color color;

    public Pixel(int _colorInt) {
        this.colorInt = _colorInt;
        this.color = new Color(colorInt);
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getAlpha();
    }

    public Pixel(int a, int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
        rgbaToInt();
        this.color = new Color(colorInt);
    }

    public int rgbaToInt() {
        int alpha = this.alpha << 24 & 0xFF000000;
        int rojo = this.red << 16 & 0x00FF0000;
        int verde = this.green << 8 & 0x0000FF00;
        int azul = this.blue & 0x000000FF;
        int c = alpha | rojo | verde | azul;
        this.colorInt = c;
        return c;
    }
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "rgba(" + red+","+green+","+blue+","+alpha+")";
    }


    public int getAlpha() {
        return alpha;
    }
    public int getBlue() {
        return blue;
    }
    public int getColorInt() {
        return colorInt;
    }
    public int getGreen() {
        return green;
    }
    public int getRed() {
        return red;
    }
    
}
