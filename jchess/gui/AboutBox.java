package jchess.gui;

import jchess.StringResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutBox extends JDialog {

    public AboutBox() {
        setTitle(StringResources.MAIN.getString("AboutBox.title.text"));
        Box b = Box.createVerticalBox();
        b.add(Box.createGlue());
        b.add(new JLabel(StringResources.MAIN.getString("AboutBox.caption.text")));
        b.add(new JLabel(StringResources.MAIN.getString("AboutBox.description.text")));
        b.add(new JLabel(StringResources.MAIN.getString("AboutBox.versionLabel.text")
                         + " " + StringResources.MAIN.getString("AboutBox.version.text")));

        b.add(Box.createRigidArea(new Dimension(0, 10)));

        b.add(new JLabel(StringResources.MAIN.getString("AboutBox.homepageLabel.text")
                         + " " + StringResources.MAIN.getString("AboutBox.homepage.text")));
        b.add(new JLabel(StringResources.MAIN.getString("AboutBox.developersLabel.text")
                         + " " + StringResources.MAIN.getString("AboutBox.developers.text")));

        b.add(Box.createRigidArea(new Dimension(0, 10)));

        b.add(new JLabel(StringResources.MAIN.getString("AboutBox.disclaimer.text")));
        b.add(Box.createGlue());
        getContentPane().add(b, "Center");

        JPanel p2 = new JPanel();
        JButton ok = new JButton(StringResources.MAIN.getString("AboutBox.closeButton.text"));
        p2.add(ok);
        getContentPane().add(p2, "South");

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });

        pack();
    }


    public static void main(String[] args) {
        AboutBox dialog = new AboutBox();
        dialog.setVisible(true);
    }
}