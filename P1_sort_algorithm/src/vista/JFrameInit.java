package vista;

import javax.swing.JFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JFrameInit extends JFrame{
    
    private static final Logger log = LogManager.getRootLogger();
    
    public JFrameInit (){
        this.setSize(620,400);     
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);       
        this.setLocationRelativeTo(null);      
        this.setVisible(true);
        this.setTitle("Algoritmos de ordenamiento");
        this.setResizable(false);
    
        initPanel(new JPanelInit());
        log.debug("corre el constructor de: "+JFrameInit.class.getName());
        this.validate();
        
    }
    
    private void initPanel(JPanelInit panel){
        this.add(panel);
        this.repaint();
    }

}
