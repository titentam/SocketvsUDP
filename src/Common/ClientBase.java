package Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientBase extends JFrame {
    protected String nickName;
    protected JLabel lbName;
    protected JTextField txtName;
    protected JButton btnOK;
    protected JTextField txtIP, txtPort;

    public ClientBase() {
        setTitle("Window");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Tạo panel cho IP và Port
        JPanel panelIPPort = new JPanel();
        panelIPPort.setLayout(new FlowLayout());

        // Tạo và thêm label và textbox cho IP
        JLabel lblIP = new JLabel("IP:");
        txtIP = new JTextField(10);
        txtIP.setText("localhost");
        panelIPPort.add(lblIP);
        panelIPPort.add(txtIP);

        // Tạo và thêm label và textbox cho Port
        JLabel lblPort = new JLabel("Port:");
        txtPort = new JTextField(5);
        txtPort.setText("7788");
        panelIPPort.add(lblPort);
        panelIPPort.add(txtPort);

        // Thêm panel vào frame
        add(panelIPPort, BorderLayout.NORTH);

        JPanel panelName = new JPanel();
        panelName.setLayout(new FlowLayout());

        lbName = new JLabel("Enter your name:");
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(200, 20));
        btnOK = new JButton("OK");

        panelName.add(lbName);
        panelName.add(txtName);
        panelName.add(btnOK);

        this.add(panelName, BorderLayout.CENTER);

        this.setVisible(true);

    }

}
