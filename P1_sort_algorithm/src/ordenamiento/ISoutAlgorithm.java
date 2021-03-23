package ordenamiento;

public interface ISoutAlgorithm {
    
    public void notificarObserver(int contadorCiclo);
    public void ordenar(int[] filas, String type);
    
}
