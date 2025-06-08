package org.example.visual_programming.blocks.conditions;

public class SystemCondition implements Condition {
    public enum SystemEvent {
        PROGRAM_STARTED,
        PROGRAM_FINISHED,
        TIMER_EXPIRED
    }

    private SystemEvent event;

    public SystemCondition(SystemEvent event) {
        this.event = event;
    }

    @Override
    public boolean evaluate() {
        // Здесь должна быть логика проверки системных событий
        // Для примера всегда возвращаем true для PROGRAM_STARTED
        return event == SystemEvent.PROGRAM_STARTED;
    }

    @Override
    public String toJavaCode() {
        switch (event) {
            case PROGRAM_STARTED: return "true /* PROGRAM_STARTED */";
            case PROGRAM_FINISHED: return "false /* PROGRAM_FINISHED */";
            case TIMER_EXPIRED: return "timer.isExpired()";
            default: return "false";
        }
    }

    @Override
    public boolean isValid() {
        return event != null;
    }
}
