package BESAFile.Agent.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Data.ActionDataAgent;
import BESAFile.World.Behavior.SensorsAgentGuardJME;
import simulation.utils.Const;

public class SubscribeResponseGuard extends GuardBESA {

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        AgentProtectorState state = (AgentProtectorState) this.getAgent().getState();
        ActionDataAgent actionData = new ActionDataAgent(state.getIdfloor(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(), "Sensing");
        EventBESA event = new EventBESA(SensorsAgentGuardJME.class.getName(), actionData);
        AgHandlerBESA ah;
        try {
            ah = getAgent().getAdmLocal().getHandlerByAlias(Const.World);
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }
    }
}
