package observer;

import java.util.ArrayList;

public class ObserverList implements IObservable{
    
    private ArrayList<IObserver> observerList = new ArrayList<>();
    
    private static ObserverList INSTANCE;
    public static ObserverList getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObserverList();
        }
        return INSTANCE;
    }

    @Override
    public void register(IObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void remove(IObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(int contadorCiclo) {
        for (IObserver observer : observerList) {
            observer.update(contadorCiclo);
        }
    }
    
}
