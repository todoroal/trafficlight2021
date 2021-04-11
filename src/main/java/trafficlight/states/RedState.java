package trafficlight.states;

import trafficlight.ctrl.TrafficLightCtrl;

public class RedState implements State{

    private final TrafficLightCtrl ctrl;

    public RedState(TrafficLightCtrl ctrl){
        this.ctrl = ctrl;
    }

    @Override
    public void nextState() {
        ctrl.setPreviousState(this);
        ctrl.setCurrentState(ctrl.getYellowState());
    }
    @Override
    public TrafficLightColor getState() {
        return TrafficLightColor.RED;
    }
}
