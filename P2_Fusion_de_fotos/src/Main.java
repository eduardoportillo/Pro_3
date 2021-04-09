import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.Frame;
import view.Panel;


public class Main {
    
    private static final Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) throws Exception {
        
        log.info("inicia: P2_Fusion_de_fotos ");
        
        Frame frame = new Frame();
        frame.add(new Panel());
        frame.validate();
    }
}
