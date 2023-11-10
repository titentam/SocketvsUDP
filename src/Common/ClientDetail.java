package Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientDetail extends JFrame {
    protected JLabel lbName;
    protected JTextField txtName;
    protected JTextArea txtaMessages;
    protected JTextField txtMessage;
    protected JButton btnSend;


    public ClientDetail(String nickName) {

        setTitle("Balan Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());

        lbName = new JLabel("Your name: ");
        txtName = new JTextField(nickName, 20);
        txtName.setEditable(false);

        northPanel.add(lbName);
        northPanel.add(txtName);

        this.add(northPanel, BorderLayout.NORTH);

        txtaMessages = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(txtaMessages);

        this.add(jScrollPane,BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());

        txtMessage = new JTextField(20);
        btnSend = new JButton("Send");

        southPanel.add(txtMessage);
        southPanel.add(btnSend);

        this.add(southPanel, BorderLayout.SOUTH);

        setVisible(true);

    }
}
