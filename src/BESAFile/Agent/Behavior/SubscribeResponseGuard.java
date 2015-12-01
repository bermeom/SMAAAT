package BESAFile.Agent.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionDataAgent;
import BESAFile.World.Behavior.SensorsAgentGuardJME;
import simulation.utils.Const;

public class SubscribeResponseGuard extends GuardBESA {

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        AgentState state = (AgentState) this.getAgent().getState();
        ActionDataAgent data = (ActionDataAgent) ebesa.getData();
        int reply_with=state.getNextConsecutive();
        int in_reply_to=data.getReply_with();
        ActionDataAgent actionData = new ActionDataAgent(reply_with,in_reply_to,state.getType(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(),state.getPosition(), "Sensing");
        EventBESA event = new EventBESA(SensorsAgentGuardJME.class.getName(), actionData);
        AgHandlerBESA ah;
        try {
            ah = getAgent().getAdmLocal().getHandlerByAlias(Const.World+state.getIdfloor());
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }
    }
}
