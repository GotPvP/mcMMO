package com.gmail.nossr50.events.sound;

import com.gmail.nossr50.util.sounds.SoundType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Bradley Steele
 */
public class McMMOPlayerReceiveSoundEvent extends PlayerEvent implements Cancellable {

    private static final @NotNull HandlerList handlers = new HandlerList();

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    private boolean cancelled = false;
    private Location location;
    private SoundType soundType;

    public McMMOPlayerReceiveSoundEvent(Player player, Location location, SoundType soundType) {
        super(player);
        this.location = location;
        this.soundType = soundType;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public Location getLocation() {
        return location;
    }

    public SoundType getSoundType() {
        return soundType;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSoundType(SoundType soundType) {
        this.soundType = soundType;
    }
}
