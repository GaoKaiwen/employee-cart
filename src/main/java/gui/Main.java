package gui;

import javax.swing.*;

public class Main {

    private JPanel panel1;
    private JLabel nameLabel;
    private JComboBox<String> nameCBox;
    private JButton selecionarButton;

    public Main() {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        nameCBox.addItem("Kaiwen");

        selecionarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Success! " + nameCBox.getSelectedItem()));
    }
}
