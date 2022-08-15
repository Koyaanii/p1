package test2;

import bridge.Abstraction;
import javafx.event.ActionEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JButton button = new JButton("press");
    private JTextField field = new JTextField("",4);
    private JLabel label = new JLabel("input");
    private JRadioButton radio1 = new JRadioButton("Select this");
    private JRadioButton radio2 = new JRadioButton("Select that");
    private JCheckBox check = new JCheckBox("check",false);

    public GUI  (){
        super("Super Example");
        this.setBounds(1000,1000,400,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,2,2,2));
        container.add(label);
        container.add(field);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        container.add(radio1);
        radio1.setSelected(true);
        container.add(radio2);
        container.add(check);
        button.addActionListener(new ButtonEventListener());
        container.add(button);


    }
    class ButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String message = "";
            message += "дима лох\n";
            message += "Text is " + field.getText() + "\n";
            message += (radio1.isSelected() ? "Radio #1" : "Radio #2") + "is selected!\n";
            message += "Check is " + ((check.isSelected()) ? "checked" : "unchecked");
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
