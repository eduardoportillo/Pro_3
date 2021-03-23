package observer;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
;

public class ObserverList implements IObservable{
    
    private static final Logger log = LogManager.getRootLogger();
    
    private ArrayList<IObserver> observerList = new ArrayList<>();
    
    
    private static ObserverList INSTANCE;
    public static ObserverList getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObserverList();
            log.info("se crea la instancia con el patron singleton de ObserverList");
        }
        return INSTANCE;
    }

    @Override
    public void register(IObserver observer) {
        observerList.add(observer);
        log.info("Se registra una instancia de IObserver en el arrayList observerList");
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
