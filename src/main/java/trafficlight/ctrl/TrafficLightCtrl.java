/*https://github.com/todoroal/trafficlight2021.git*/

package trafficlight.ctrl;

import trafficlight.gui.TrafficLightGui;
import trafficlight.states.*;

public class TrafficLightCtrl {

    private static TrafficLightCtrl instance;

    private State greenState;

    private State redState;

    private State yellowState;

    private  State offState;

    private State currentState;

    private State previousState;

    private TrafficLightGui gui;


    private TrafficLightCtrl() {
        super();
        initStates();
        gui = new TrafficLightGui(this);
        gui.setVisible(true);
    }

    public static TrafficLightCtrl getInstance(){
        if(instance == null){
            instance = new TrafficLightCtrl();
        }
        return instance;
    }


    private void initStates() {
        //TODO create the states and set current and previous state
        redState = new RedState(this);
        greenState = new GreenState(this);
        yellowState = new YellowState(this);
        offState = new OffState(this);
        setCurrentState(greenState);
        setPreviousState(yellowState);
    }

    public State getGreenState() {
        return greenState;
    }

    public State getRedState() {
        return redState;
    }

    public State getYellowState() {
        return yellowState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getOffState() {
        return offState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public void run() {
        gui.run();
    }

    public void nextState() {
        //TODO handle GUi request and update GUI
        gui.setLight(getCurrentState().getState());
        getCurrentState().nextState();
    }
}