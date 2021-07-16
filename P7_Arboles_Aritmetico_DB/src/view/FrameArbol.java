package view;

import javax.swing.*;

import controller.ArbolAritmetico;
import estructura.Lista;
import model.expresion_aritmeticaDTO;
import model.expresion_artimeticaDAO;

import java.awt.*;
import java.util.logging.Logger;

public class FrameArbol extends JFrame {
    private static ArbolAritmetico modelo;
    private PanelArbol panel;
    private expresion_artimeticaDAO eaDao = new expresion_artimeticaDAO();
    private JFrame FUDE;

    JMenuBar bar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenu historial = new JMenu("historial");
    JMenu eliminarItemHistorial = new JMenu("Eliminar Expresión");
    JMenu actalizarItemHistorial = new JMenu("Actualizar Expresión");

    JMenuItem insertExpresion = new JMenuItem("Ingresar Expresion");
    JMenuItem ayuda = new JMenuItem("ayuda");
    JMenuItem salir = new JMenuItem("salir");

    private String regexVN = "(.*[\\+|\\-|\\*|\\/|\\%]{2,99}.*)";
    private String regexVS = ".*[\\+|\\-|\\*|\\/|\\%]$";
    private String regexVO = "^[|\\*|\\/|\\%]";

    public FrameArbol(ArbolAritmetico obj) {
        modelo = obj;
        init();
    }

    private void init() {
        this.setSize(600, 400);

        panel = new PanelArbol(modelo);
        panel.setBounds(300, 300, 300, 300);
        JScrollPane scroller = new JScrollPane(panel);

        bar.add(menu);
        bar.add(historial);
        bar.add(eliminarItemHistorial);
        bar.add(actalizarItemHistorial);

        menu.add(insertExpresion);
        menu.add(ayuda);
        menu.add(salir);

        this.setJMenuBar(bar);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(scroller, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        insertExpresion.addActionListener(e -> {

            try {
                String expresion = JOptionPane.showInputDialog(null, "Ingrese la expresion:");

                if (expresion == null || expresion.equals("") || expresion.matches(regexVN)
                        || expresion.matches(regexVS) || expresion.matches(regexVO)
                        || expresion.matches("[0-9]*[A-Za-z-\\s-?\\[-\\]{-}]+[0-9]*")) {

                    JOptionPane.showMessageDialog(null,
                            "La Expresion no es valida, Porfavor introduzca una expresión valida");
                    return;

                } else {
                    modelo = new ArbolAritmetico(expresion);
                    modelo.addObserver(panel);
                    panel.setModelo(modelo);
                    modelo.cambioOk();
                    eaDao.insertar(modelo.toString().replaceAll("[ ]?[=]+[ ]?[Infinity]*[\\d]*[.]?[\\d]*", ""));
                    cargarItem();
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        ayuda.addActionListener(l -> {
            JOptionPane.showMessageDialog(null,
                    "Inserte Una expresion Aritmetica y automaticamente se generara un Árbol Aritmetico puede seleccionar una expresión del historial, puede eliminar una expresion del historial o puede actualizar una expresion del historia");
        });

        salir.addActionListener(l -> {
            System.exit(0);
        });

        cargarItem();

    }

    public void cargarItem() {
        Lista<expresion_aritmeticaDTO> expresionArit = eaDao.getTodos();
        EliminarItemHistorial(expresionArit);
        cargarItemHistorial(expresionArit);
        ActualizarItemHistorial(expresionArit);
    }

    public void cargarItemHistorial(Lista<expresion_aritmeticaDTO> expresionArit) {
        historial.removeAll();

        for (expresion_aritmeticaDTO e : expresionArit) {
            JMenuItem expresionArt = new JMenuItem(e.getExpresion());
            historial.add(expresionArt);
            expresionArt.addActionListener(exp -> {
                try {
                    modelo = new ArbolAritmetico(e.getExpresion());
                    modelo.addObserver(panel);
                    panel.setModelo(modelo);
                    modelo.cambioOk();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public void EliminarItemHistorial(Lista<expresion_aritmeticaDTO> expresionArit) {
        eliminarItemHistorial.removeAll();
        for (expresion_aritmeticaDTO e : expresionArit) {
            JMenuItem expresionArt = new JMenuItem(e.getExpresion());
            eliminarItemHistorial.add(expresionArt);
            expresionArt.addActionListener(exp -> {
                try {
                    eaDao.eliminar(e);
                    cargarItem();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public void ActualizarItemHistorial(Lista<expresion_aritmeticaDTO> expresionArit) {
        actalizarItemHistorial.removeAll();

        for (expresion_aritmeticaDTO e : expresionArit) {
            JMenuItem expresionArt = new JMenuItem(e.getExpresion());
            actalizarItemHistorial.add(expresionArt);
            expresionArt.addActionListener(exp -> {

                try {
                    String expresion = JOptionPane.showInputDialog(null,
                            "Ingrese la expresion por la que quiere actualizar:");

                    if (expresion == null || expresion.equals("") || expresion.matches(regexVN)
                            || expresion.matches(regexVS) || expresion.matches(regexVO)
                            || expresion.matches("[0-9]*[A-Za-z-\\s-?\\[-\\]{-}]+[0-9]*")) {

                        JOptionPane.showMessageDialog(null,
                                "La Expresion no es valida, Porfavor introduzca una expresión valida");
                        return;

                    } else {
                        modelo = new ArbolAritmetico(expresion);
                        modelo.addObserver(panel);
                        panel.setModelo(modelo);
                        modelo.cambioOk();
                        e.setEAritmetica(modelo.toString().replaceAll("[ ]?[=]+[ ]?[Infinity]*[\\d]*[.]?[\\d]*", ""));
                        eaDao.actualizar(e);
                        cargarItem();
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) {
        Logger log = Logger.getLogger("logger");
        try {
            FrameArbol frameArbol = new FrameArbol(modelo);
            frameArbol.setVisible(true);
            log.info("Se ha iniciado el programa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
