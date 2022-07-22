package com.gmail.nossr50.events.skills.abilities;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class McMMOPlayerAbilityActivateEvent extends McMMOPlayerAbilityEvent implements Cancellable {

    private boolean cancelled;
    private long ticks;
    private long boostedTicks = 0L;

    public McMMOPlayerAbilityActivateEvent(Player player, PrimarySkillType skill, long ticks) {
        super(player, skill);
        this.ticks = ticks;
        cancelled = false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public long getTicks() {
        return ticks;
    }

    public long getBoostedTicks() {
        return boostedTicks;
    }

    public void setCancelled(boolean value) {
        this.cancelled = value;
    }

    public void setTicks(long ticks) {
        this.ticks = ticks;
    }

    public void setBoostedTicks(long boostedTicks) {
        this.boostedTicks = boostedTicks;
    }
}
