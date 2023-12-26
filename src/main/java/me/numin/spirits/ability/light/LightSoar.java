package me.numin.spirits.ability.light;

import java.util.Random;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
import me.numin.spirits.ability.spirit.combo.Levitation;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.numin.spirits.Spirits;
import me.numin.spirits.utilities.Methods;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;

public class LightSoar extends LightAbility {

    //TODO: Update sounds.

    @Attribute(Attribute.SPEED)
    private double speed;
    @Attribute(Attribute.COOLDOWN)
    private long cooldown;
    @Attribute(Attribute.DURATION)
    private long duration;

    public LightSoar(Player player) {
        super(player);

        if (!bPlayer.canBend(this) || CoreAbility.hasAbility(player, Levitation.class)) {
            return;
        }
        setFields();
        start();
    }

    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.LightAgility.LightSoar.Cooldown");
        this.duration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.LightAgility.LightSoar.Duration");
        this.speed = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.LightAgility.LightSoar.Speed");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBend(this)) {
            remove();
            return;
        }
        progressSoar();
    }

    private void progressSoar() {
        if (player.isSneaking()) {
            if (System.currentTimeMillis() > getStartTime() + duration) {
                remove();
            } else {
                GeneralMethods.setVelocity(this, player, player.getLocation().getDirection().multiply(speed));
                if (getRunningTicks() % 5 == 0) {
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 0.3F, 5F);
                }
                Methods.playSpiritParticles(player, player.getLocation(), 0.5, 0.5, 0.5, 0, 2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 15, 0));
            }
        }
    }

    @Override
    public void remove() {
        bPlayer.addCooldown("LightSoar", cooldown);
        super.remove();
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public String getName() {
        return "LightAgility";
    }

    @Override
    public String getAbilityType() {
        return MOBILITY;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return true;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isSneakAbility() {
        return true;
    }
}