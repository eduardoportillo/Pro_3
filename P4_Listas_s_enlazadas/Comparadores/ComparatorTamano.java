
package javaapplication2.Comparadores;

import java.util.Comparator;
import javaapplication2.Ventanas.Persona;

public class ComparatorTamano implements Comparator<Persona> {

    @Override
    public int compare(Persona o1, Persona o2) {
        if (o2.getTamano() == o1.getTamano()) {
            return 0;
        }

        if (o2.getTamano() < o1.getTamano()) {
            return -1;
        }

        if (o2.getTamano() > o1.getTamano()) {
            return 1;
        }
        return o2.getNombre().compareTo(o1.getNombre());
    }
}