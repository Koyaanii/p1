/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class CreateNewFolderJDialog extends JDialog{
    private JTextField nameOfNewFolder = new JTextField(10);
    private JButton okButton = new JButton("�������");
    private JButton cancelButton = new JButton("������");
    private String newFolderName;
    private JLabel nameFolderWait = new JLabel("��� ����� �����: ");
    private boolean ready = false;

    public CreateNewFolderJDialog(JFrame jframe) {

        super(jframe, "������� ����� �����", true);
        setLayout(new GridLayout(2, 2, 5, 5));
        setSize(400, 200);

        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newFolderName = nameOfNewFolder.getText();
                setVisible(false);
                ready = true;
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ready = false;
            }
        });

        getContentPane().add(nameFolderWait);
        getContentPane().add(nameOfNewFolder);
        getContentPane().add(okButton);
        getContentPane().add(cancelButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getNewName(){
        return newFolderName;
    }

    public boolean getReady (){
        return ready;
    }

    public void Waiting(){
        while(!ready){
        }
    }

}