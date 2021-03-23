package observer;

public interface IObservable {
    public void register(IObserver observer);
    public void remove(IObserver observer);
    public void notifyObservers(int contadorCiclo);
}
