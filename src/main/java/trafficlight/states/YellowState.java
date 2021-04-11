package trafficlight.states;

import trafficlight.ctrl.TrafficLightCtrl;

public class YellowState implements State{

    private final TrafficLightCtrl ctrl;

    public YellowState(TrafficLightCtrl ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void nextState() {
        if(ctrl.getPreviousState().equals(ctrl.getRedState())){
            ctrl.setCurrentState(ctrl.getGreenState());
        }

        else{
            ctrl.setCurrentState(ctrl.getRedState());
        }
        ctrl.setPreviousState(this);
    }

    @Override
    public TrafficLightColor getState() {
        return TrafficLightColor.YELLOW;
    }
}
