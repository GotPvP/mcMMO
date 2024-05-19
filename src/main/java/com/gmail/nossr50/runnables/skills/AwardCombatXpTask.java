package com.gmail.nossr50.runnables.skills;

import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.experience.XPGainReason;
import com.gmail.nossr50.datatypes.experience.XPGainSource;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.mcMMO;
import org.bukkit.attribute.Attribute;
import com.gmail.nossr50.util.CancellableRunnable;
import com.gmail.nossr50.mcMMO;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class AwardCombatXpTask extends CancellableRunnable {
    private final McMMOPlayer mcMMOPlayer;
    private final double baseXp;
    private final PrimarySkillType primarySkillType;
    private final LivingEntity target;
    private final XPGainReason xpGainReason;
    private final double baseHealth;

    public AwardCombatXpTask(McMMOPlayer mcMMOPlayer, PrimarySkillType primarySkillType, double baseXp, LivingEntity target, XPGainReason xpGainReason) {
        this.mcMMOPlayer = mcMMOPlayer;
        this.primarySkillType = primarySkillType;
        this.baseXp = baseXp;
        this.target = target;
        this.xpGainReason = xpGainReason;
        baseHealth = target.getHealth();
    }

    @Override
    public void run() {
        double health = target.getHealth();
        double damage = target.getHealth() == target.getMaxHealth() / 2 ? target.getHealth() : baseHealth - health;

        // May avoid negative xp, we don't know what other plugins do with the entity health
        if (damage <= 0) {
            // PATCH: OPMobStacker kills do 0 damage when mobs are 1 shot, set damage to the mob's max health
            if (mcMMO.nskOPMobStackerStackSize != null && target.getPersistentDataContainer().has(mcMMO.nskOPMobStackerStackSize, PersistentDataType.INTEGER)) {
                damage = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            } else {
                return;
            }
        }

        // Don't reward the player for overkills
        if (health < 0) {
            damage += health;
        }

        if(ExperienceConfig.getInstance().useCombatHPCeiling()) {
            damage = Math.min(damage, ExperienceConfig.getInstance().getCombatHPCeiling());
        }

        mcMMOPlayer.beginXpGain(primarySkillType, (int) (damage * baseXp), xpGainReason, XPGainSource.SELF);
    }
}
