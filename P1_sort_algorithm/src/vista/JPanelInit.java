package vista;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import observer.IObserver;
import observer.ObserverList;
import ordenamiento.Bubblesort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JPanelInit extends JPanel implements IObserver{
    
    private static final Logger log = LogManager.getRootLogger();
    
    private int x = 300; //cantida filas
    private int max_y = 400; //cantidad maxima en y
    private int min_y = 1; //cantidad minima en y

    private JLabel info;
    private int numMayor;
    private int numMenor;
    private JLabel conteoCiclo;

    private int[] filas;
    private Graphics _g;
    private JPanel instance;

    public JPanelInit() {
        instance = this;
        this.setBounds(600, 400, 0, 0);
        this.setLayout(null);
        filas = new int[x];
        
        info = new JLabel();
        info.setBounds(10, 280, 500, 100);
        info.setVisible(true);
        this.add(info);
        
        conteoCiclo = new JLabel();
        conteoCiclo.setBounds(370, 280, 100, 100);
        conteoCiclo.setVisible(true);
        this.add(conteoCiclo);
        
        generarNroAleatorio();

        // Crear componentes
        crearBtnBubblesortDesc();
        crearBtnBubblesortAsc();
        crearBtnReset();
        this.validate();
        ObserverList.getInstance().register(this);
        
        
    }

    private void generarNroAleatorio() {
        numMayor = 0;
        numMenor = max_y;
        double media = 0;

        for (int i = 0; i < x; i++) {
            filas[i] = (int) ((Math.random() * max_y) + min_y); // valor randomico entre min_y - max_y
//            System.out.println(filas[i]);

            media += filas[i];

            if (filas[i] > numMayor) {
                numMayor = filas[i];
            }

            if (filas[i] < numMenor) {
                numMenor = filas[i];
            }

        }

        media = media / x;
        info.setText("Numero Menor: " + numMenor + " | " + "Numero Mayor: " + numMayor + " | " + "Media: " + String.format("%.3f", media ) + " | " + "vuelta: " );
    }

    public void pintarLineas() {
        int tamanho = (this.getWidth() - 1) / x;
        int posxTemp = 1;
        for (int i = 0; i < filas.length; i++) {
            _g.fillRect(posxTemp, 300 - (filas[i] / 2), tamanho - 1, (filas[i] / 2));
            posxTemp += tamanho;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this._g = g;
        pintarLineas();
    }

    public void crearBtnBubblesortDesc() {
        JButton btnOrdenar = new JButton();
        btnOrdenar.setText("Bubblesort desc");
        btnOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Bubblesort().ordenar(filas, "desc");
                log.info("Se oprime el botón: Bubblesort desc");
            }
        });

        btnOrdenar.setBounds(400, 10, 180, 30);
        this.add(btnOrdenar);
    }

    public void crearBtnBubblesortAsc() {
        JButton btnOrdenar = new JButton();
        btnOrdenar.setText("Bubblesort asc");
        btnOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                new Bubblesort().ordenar(filas, "asc");
                log.info("Se oprime el botón: Bubblesort asc");
            }
        });

        btnOrdenar.setBounds(200, 10, 180, 30);
        this.add(btnOrdenar);
    }

    public void crearBtnReset() {
        JButton btnOrdenar = new JButton();
        btnOrdenar.setText("Reset");
        btnOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filas = new int[x];
                generarNroAleatorio();
                instance.repaint(); 
                Bubblesort bubbleSort = new Bubblesort();
                bubbleSort.vueltas = 0;
                log.info("Se oprime el botón: Reset");
            }
        });

        btnOrdenar.setBounds(10, 10, 100, 30);
        this.add(btnOrdenar);
    }
    
    @Override
    public void update(int contadorCiclo) {
        instance.repaint();
        conteoCiclo.setText(contadorCiclo+"");
    }
    
}
