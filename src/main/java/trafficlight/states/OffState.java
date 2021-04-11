package trafficlight.states;

import trafficlight.ctrl.TrafficLightCtrl;

public class OffState implements State{

    private final TrafficLightCtrl ctrl;

    public OffState(TrafficLightCtrl ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void nextState() {
        if (!ctrl.getPreviousState().equals(ctrl.getOffState())) {
            ctrl.setPreviousState(this);
        }
        ctrl.setCurrentState(ctrl.getYellowState());

    }

    @Override
    public TrafficLightColor getState() {
        return TrafficLightColor.OFF;
    }
}
