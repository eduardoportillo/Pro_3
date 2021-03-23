package vista;

import javax.swing.JFrame;

public class JFrameInit extends JFrame{
    public JFrameInit (){
        this.setSize(620,400);     
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);       
        this.setLocationRelativeTo(null);      
        this.setVisible(true);
        this.setTitle("Algoritmos de ordenamiento");
        this.setResizable(false);
    
        initPanel(new JPanelInit());
        
        this.validate();
        
    }
    
    private void initPanel(JPanelInit panel){
        this.add(panel);
        this.repaint();
    }

}
