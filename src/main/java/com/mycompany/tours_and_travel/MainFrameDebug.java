package com.mycompany.tours_and_travel;

import java.awt.event.ActionEvent;

public class MainFrameDebug extends MainFrame {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action command received: " + e.getActionCommand());
        super.actionPerformed(e);
    }
}
