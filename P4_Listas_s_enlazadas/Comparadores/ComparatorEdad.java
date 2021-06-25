
package javaapplication2.Comparadores;

import java.util.Comparator;
import javaapplication2.Ventanas.Persona;

public class ComparatorEdad implements Comparator<Persona> {

    @Override
    public int compare(Persona o1, Persona o2) {

        if (o2.getEdad() == o1.getEdad()) {
            return 0;
        }

        if (o2.getEdad() < o1.getEdad()) {
            return 1;
        }

        if (o2.getEdad() > o1.getEdad()) {
            return -1;
        }
        return 0;
    }
}
