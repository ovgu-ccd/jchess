package jchess.gui;

import jchess.StringResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutBox extends JDialog {
    private JPanel contentPane;
    private JButton closeButton;
    private JLabel caption;
    private JLabel description;
    private JLabel versionLabel;
    private JLabel version;
    private JLabel homepageLabel;
    private JLabel homepage;
    private JLabel developersLabel;
    private JLabel developers;
    private JLabel disclaimer;

    public AboutBox() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(closeButton);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });

        setTitle(StringResources.MAIN.getString("AboutBox.title.text"));
        setResizable(false);
    }

    private void onClose() {
        dispose();
    }

    public static void main(String[] args) {
        AboutBox dialog = new AboutBox();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
