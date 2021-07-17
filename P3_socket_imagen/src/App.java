import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Frames.FrameTwoBtn;

public class App {

    private static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws Exception {
        log.info("inicia: proyecto 3");
        new FrameTwoBtn();
    }
}
