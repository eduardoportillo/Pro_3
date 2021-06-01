package Comunication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mensaje implements Serializable {

    private static final Logger log = LogManager.getRootLogger();

    private String id;
    private String mensaje;
    private Date fecha;
    private String imagen;

    public Mensaje() {

    }

    public Mensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje(String mensaje, String foto) {
        this.mensaje = mensaje;
        this.imagen = foto;
    }

    public void load(String url) {
        try {
            FileInputStream fileIn = new FileInputStream(url);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Mensaje msn = (Mensaje) objectIn.readObject();
            this.setMensaje(msn.getMensaje());
            this.setId(msn.getId());
            this.setFecha(msn.getFecha());
            log.info("The Object has been read from the file");
            objectIn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save(String url) {
        FileOutputStream fout;
        try {
            fout = new FileOutputStream(url);
            log.info("File guardado en " + url);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
