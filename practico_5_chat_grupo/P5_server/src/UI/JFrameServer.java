package UI;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFrameServer extends JFrame {

    JLabel chat, participantes;
    JPanelParticipantes panelParticipantes;
    PanelChat panelChat;

    public JFrameServer() {
        initFrameServer();
        initLabel();

    }

    private void initFrameServer() {
        this.setTitle("Server");
        this.setVisible(true);
        this.setBounds(0, 0, 600, 600);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panelParticipantes = new JPanelParticipantes();
        this.add(panelParticipantes);

        panelChat = new PanelChat();
        this.add(panelChat);

        this.repaint();
        this.validate();
    }

    private void initLabel() {
        chat = new JLabel("Chat");
        chat.setFont(new Font("Serif", Font.PLAIN, 25));
        chat.setBounds(50, 50, 100, 30);
        this.add(chat);
        chat.setVisible(true);

        participantes = new JLabel("participantes");
        participantes.setFont(new Font("Serif", Font.PLAIN, 25));
        participantes.setBounds(400, 50, 200, 30);
        this.add(participantes);
        participantes.setVisible(true);

        this.repaint();
        this.validate();
    }
}
