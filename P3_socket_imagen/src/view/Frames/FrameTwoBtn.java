package Frames;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import Singleton.ImagenSelect;
import paneles.PanelSeleccionSC;

public class FrameTwoBtn extends JFrame {

    private PanelSeleccionSC panelSeleccionSC;

    public FrameTwoBtn() {
        chooseFile();
        initFrameServer();
        crearPanelSeleccionSC();

    }

    private void initFrameServer() {
        this.setVisible(true);
        this.setBounds(0, 0, 250, 150);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        int seleccion = fileChooser.showOpenDialog(this);
        File abreimg = fileChooser.getSelectedFile();
        ImagenSelect.setInstance(abreimg);
    }

    private void crearPanelSeleccionSC() {
        panelSeleccionSC = new PanelSeleccionSC();
        this.add(panelSeleccionSC);
        panelSeleccionSC.setVisible(true);
        panelSeleccionSC.repaint();
        panelSeleccionSC.validate();
    }
}
