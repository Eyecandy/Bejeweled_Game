package Events;

import tiles.Tuple;

import java.util.Set;

public class DestroyEvent implements Event{
    private final EventType TYPE;
    private final Set<Tuple> TARGETS;
    public DestroyEvent(Set<Tuple> targets) {
        TARGETS = targets;
        TYPE = EventType.DESTROY_EVENT;
    }

    @Override
    public EventType getEventType() {
        return TYPE;
    }

    public Set<Tuple> getTARGETS() {
        return TARGETS;
    }
}
