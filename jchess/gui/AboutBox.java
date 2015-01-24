package jchess.gui;

import jchess.util.StringResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SuppressWarnings("PMD.UnusedLocalVariable")
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

        openWebsite(homepage, StringResources.MAIN.getString("AboutBox.homepage.text"), null);
    }

    public static void main(String[] args) {
        AboutBox dialog = new AboutBox();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onClose() {
        dispose();
    }

    private void openWebsite(JLabel label, final String url, String text) {
        label.setText("<html><a href=\"\">" + (text == null ? url : text) + "</a></html>");
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
