package lista;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

public class Lista<T> implements Iterable<T> {

    protected PropertyChangeSupport ps;
    protected Contenedor<T> ultimo;
    protected Contenedor<T> raiz;
    protected int tamano;

    public Lista() {
        this.tamano = 0;
        raiz = null;
        ps = new PropertyChangeSupport(this);
    }

    public Contenedor<T> getRaiz() {
        return raiz;
    }

    public void getContenedor() {
        Contenedor<T> actual = raiz;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

    }

    public Contenedor<T> get(int i) {
        Contenedor<T> actual = raiz;
        if (i == 0) {
            return actual;
        }
        int pos = 0;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
            pos++;
            if (pos == i) {
                return actual;
            }
        }
        return null;
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
        // Verifica si la posición ingresada se encuentre en el rango
        // >= 0 y < que el numero de elementos del la lista.
        if (posicion >= 0 && posicion < tamano) {
            // Consulta si el nodo a eliminar es el primero
            if (posicion == 0) {
                // Elimina el primer nodo apuntando al siguinte.
                raiz = raiz.getSiguiente();
            }
            // En caso que el nodo a eliminar este por el medio
            // o sea el ultimo
            else {
                // Crea una copia de la lista.
                Contenedor aux = raiz;
                // Recorre la lista hasta lleger al nodo anterior al eliminar.
                for (int i = 0; i < posicion - 1; i++) {
                    aux = aux.getSiguiente();
                }
                // Guarda el nodo siguiente al nodo a eliminar.
                Contenedor siguiente = aux.getSiguiente();
                // Elimina la referencia del nodo apuntando al nodo siguiente.
                aux.setSiguiente(siguiente.getSiguiente());
            }
            // Disminuye el contador de tamaño de la lista.
            tamano--;
        }
    }

    public void insertar(T o) {
        Contenedor<T> nuevo = new Contenedor(o);
        int old = tamano;
        tamano++;
        if (raiz == null) {
            raiz = nuevo;
            ultimo = nuevo;
            return;
        }
        ultimo.setSiguiente(nuevo);
        ultimo = nuevo;

        ps.firePropertyChange("Tamaño", old, tamano);
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

    public class Contenedor<T> {

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
