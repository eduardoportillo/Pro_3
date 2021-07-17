package Singleton;

import java.io.File;

import Limagen.Imagen;

public class ImagenSelect {
    private static Imagen img;

    public static Imagen getInstance() {
        return img;
    }

    public static Imagen setInstance(File imagen) {
        img = new Imagen(imagen);
        return img;
    }
}
