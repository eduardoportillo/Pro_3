package main;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vista.JFrameInit;

public class Main {

     private static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        log.debug("inicia: P1_sort_algorithm ");
        new JFrameInit();
    }

}
