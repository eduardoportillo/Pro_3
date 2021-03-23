package ordenamiento;

import observer.ObserverList;

public class Bubblesort implements ISoutAlgorithm {
    
    private int time_sleep = 1;
    
    @Override
    public void ordenar(int[] filas, String type) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int temp;
                    int vueltas = 0;
                    for (int i = 1; i < filas.length; i++) {
                        for (int j = 0; j < filas.length - 1; j++) {
                            if ((filas[j] > filas[j + 1] && type.equals("asc")) || (filas[j] < filas[j + 1] && type.equals("desc"))) {
                                temp = filas[j];
                                filas[j] = filas[j + 1];
                                filas[j + 1] = temp;
                                vueltas++;
                                notificarObserver(vueltas);
                                Thread.sleep(time_sleep);
                            }
                        }
                    }
                } catch (Exception ex) {
                    //COLOCAR EL LOG 
                }
            }
        });
        t.start();
    }
    
    @Override
    public void notificarObserver(int contadorCiclo) {
        ObserverList.getInstance().notifyObservers(contadorCiclo);
    }

    

}
