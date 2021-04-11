package trafficlight.gui;

import trafficlight.ctrl.TrafficLightCtrl;
import trafficlight.states.TrafficLightColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrafficLightGui extends JFrame implements ActionListener {

    public static final String ACTION_COMMAND_NEXT = "next";

    public static final String ACTION_COMMAND_AUTO_MODE = "autoMode";
    public static final String NAME_OF_THE_GAME = "Traffic Light";
    public static final String ACTION_COMMAND_FLASHYELLOW = "flashYellow";

    private JButton buttonNextState;
    private JCheckBox CheckBoxFlashYellow;
    private JCheckBox checkBoxAutoMode;
    private JLabel labelAutoMode;
    private JLabel labelFlashYellow;

    private Light green = null;
    private Light yellow = null;
    private Light red = null;

    private TrafficLightCtrl trafficLightCtrl = null;

    private boolean isAutoMode = false;
    private boolean doExit = false;
    private boolean flashYellow = false;

    private int yellowIntervall = 500;

    private int intervall = 1500;

    public TrafficLightGui(TrafficLightCtrl ctrl){
        super(NAME_OF_THE_GAME);
        trafficLightCtrl = ctrl;
        initLights();
        init();
    }

    private void initLights() {
        green  = new Light(Color.green);
        yellow = new Light(Color.yellow);
        red    = new Light(Color.red);
    }

    private void init() {
        getContentPane().setLayout(new GridLayout(2, 1));
        buttonNextState = new JButton("next State");
        buttonNextState.setActionCommand(ACTION_COMMAND_NEXT);
        buttonNextState.addActionListener(this);

        labelFlashYellow = new JLabel("FlashYellow ");
        CheckBoxFlashYellow = new JCheckBox();
        CheckBoxFlashYellow.setActionCommand("flashYellow");
        CheckBoxFlashYellow.addActionListener(this);

        labelAutoMode = new JLabel("AutoMode ");
        checkBoxAutoMode = new JCheckBox();
        checkBoxAutoMode.setActionCommand("autoMode");
        checkBoxAutoMode.addActionListener(this);


        JPanel p1 = new JPanel(new GridLayout(3,1));
        p1.add(red);
        p1.add(yellow);
        p1.add(green);


        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(buttonNextState);
        p2.add(labelAutoMode);
        p2.add(checkBoxAutoMode);
        p2.add(labelFlashYellow);
        p2.add(CheckBoxFlashYellow);

        getContentPane().add(p1);
        getContentPane().add(p2);
        pack();
    }

    public void run() {
        while (!doExit) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                JOptionPane pane = new JOptionPane();
                JDialog dialog = pane.createDialog(this,"Traffic Light Problem");
                JOptionPane.showMessageDialog(dialog, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
             while (isAutoMode) {
                 //TODO call the controller
                TrafficLightCtrl ctrl = TrafficLightCtrl.getInstance();
                ctrl.nextState();

                try {
                    if (yellow.isOn) {
                        Thread.sleep(yellowIntervall);

                    } else {
                        Thread.sleep(intervall);
                    }
                } catch (InterruptedException e) {
                    JOptionPane pane = new JOptionPane();
                    JDialog dialog = pane.createDialog(this,"Traffic Light Problem");
                    JOptionPane.showMessageDialog(dialog, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    isAutoMode = false;
                }
            }
             while (flashYellow) {
                 TrafficLightCtrl ctrl = TrafficLightCtrl.getInstance();

                 ctrl.setPreviousState(ctrl.getYellowState());
                 ctrl.setCurrentState(ctrl.getOffState());
                 ctrl.nextState();

                 try {
                     if (yellow.isOn) {
                         Thread.sleep(yellowIntervall);

                     } else {
                         Thread.sleep(intervall);
                     }
                 } catch (InterruptedException e) {
                     JOptionPane pane = new JOptionPane();
                     JDialog dialog = pane.createDialog(this,"Traffic Light Problem");
                     JOptionPane.showMessageDialog(dialog, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                     flashYellow = false;
                 }

             }
        }
    }

    public void actionPerformed(ActionEvent e){
        //if(ACTION_COMMAND_FLASHYELLOW.equals())

        if (ACTION_COMMAND_NEXT.equals(e.getActionCommand())){
           trafficLightCtrl.nextState();
        } else if (ACTION_COMMAND_AUTO_MODE.equals(e.getActionCommand())){
            isAutoMode = !isAutoMode;
            System.out.println("set Auto mode to "+isAutoMode);
        } else if(ACTION_COMMAND_FLASHYELLOW.equals(e.getActionCommand())){
            flashYellow = !flashYellow;
            System.out.println("set Flash Mode to "+flashYellow);
        }
    }

    public void setLight(TrafficLightColor trafficLightColor){
        //TODO setLight
        if(trafficLightColor == TrafficLightColor.RED){
            red.turnOn(true);
            green.turnOn(false);
            yellow.turnOn(false);
        }
        if(trafficLightColor == TrafficLightColor.YELLOW){
            red.turnOn(false);
            green.turnOn(false);
            yellow.turnOn(true);
        }
        if(trafficLightColor == TrafficLightColor.GREEN){
            red.turnOn(false);
            green.turnOn(true);
            yellow.turnOn(false);
        }
        if(trafficLightColor == TrafficLightColor.OFF){
            red.turnOn(false);
            green.turnOn(false);
            if(yellow.isOn){
                yellow.turnOn(false);
            } else yellow.turnOn(true);
        }
    }
}
