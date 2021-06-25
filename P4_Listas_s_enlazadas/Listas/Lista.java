
package javaapplication2.Listas;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

public class Lista<T> implements Iterable<T> {

    protected PropertyChangeSupport ps;
    protected Contenedor<T> raiz;
    protected int tamano;
    protected int limite;

    public Lista() {
        raiz = null;
        limite = 5;
        ps = new PropertyChangeSupport(this);
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public Contenedor<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(Contenedor<T> raiz) {
        this.raiz = raiz;
    }

    public void getPersona() {
        Contenedor<T> actual = raiz;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

    }

    public void insertarMejorado(T o) {
        if (raiz == null) {
            insertar(o);
            return;
        }
        Contenedor<T> actual = raiz;
        Contenedor<T> nuevo = new Contenedor<>(o);

        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        actual.setSiguiente(nuevo);
        int oldval = tamano;
        tamano++;
        ps.firePropertyChange("Tamaño", oldval, tamano);

    }

    public void removerPorPosicion(int posicion) {

        if (posicion >= 0 && posicion < tamano) {
            if (posicion == 0) {
                raiz = raiz.getSiguiente();
                ps.firePropertyChange("Cambio", 1, 2);

            } else {
                Contenedor aux = raiz;
                for (int i = 0; i < posicion - 1; i++) {
                    aux = aux.getSiguiente();
                }
                Contenedor siguiente = aux.getSiguiente();
                aux.setSiguiente(siguiente.getSiguiente());
            }
            int oldval = tamano;
            // Disminuye el contador de tamaño de la lista.
            tamano--;
            ps.firePropertyChange("Cambio", oldval, tamano);
        }
    }

    public void insertar(T o) {
        Contenedor<T> nuevo = new Contenedor(o);
        nuevo.setSiguiente(raiz);
        raiz = nuevo;
        int oldval = tamano;
        tamano++;
        ps.firePropertyChange("Tamaño", oldval, tamano);
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorLista<T>(raiz);
    }

    class IteradorLista<T> implements Iterator<T> {

        private Contenedor<T> actual;

        public IteradorLista(Contenedor<T> inicio) {
            actual = inicio;
        }

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            T result = actual.getContenido();
            actual = actual.getSiguiente();
            return result;
        }

    }

    public int getTamano() {
        return tamano;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Contenedor<T> actual = raiz;
        while (actual != null) {
            sb.append("[" + actual.getContenido().toString() + "]---");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }

    class Contenedor<T> {

        private T contenido;
        private Contenedor<T> siguiente;

        public Contenedor(T c) {
            contenido = c;
            siguiente = null;
        }

        public T getContenido() {
            return contenido;
        }

        public void setContenido(T contenido) {
            ps.firePropertyChange("Cambio Contenido", 1, 2);
            this.contenido = contenido;
        }

        public Contenedor<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Contenedor<T> siguiente) {
            this.siguiente = siguiente;
        }

    }

    public void addObserver(PropertyChangeListener xd) {

        ps.addPropertyChangeListener(xd);

    }
}
