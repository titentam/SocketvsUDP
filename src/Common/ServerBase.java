package Common;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerBase extends JFrame {
    protected JTextField txtIP, txtPort;
    protected JTextArea textArea;
    protected JButton btnOK;

    public ServerBase() throws UnknownHostException {
        setTitle("Chat Room Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 300);

        // Tạo panel cho IP và Port
        JPanel panelIPPort = new JPanel();
        panelIPPort.setLayout(new FlowLayout());

        // Tạo và thêm label và textbox cho IP
        JLabel lblIP = new JLabel("IP:");
        txtIP = new JTextField(10);
        txtIP.setText(InetAddress.getLocalHost().getHostAddress());
        panelIPPort.add(lblIP);
        panelIPPort.add(txtIP);

        // Tạo và thêm label và textbox cho Port
        JLabel lblPort = new JLabel("Port:");
        txtPort = new JTextField(5);
        txtPort.setText("7788");
        panelIPPort.add(lblPort);
        panelIPPort.add(txtPort);

        btnOK = new JButton("OK");
        panelIPPort.add(btnOK);

        // Thêm panel vào frame
        add(panelIPPort, BorderLayout.NORTH);

        // Tạo và thêm text area cho chat
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        //pack();
        setVisible(true);
    }

}
