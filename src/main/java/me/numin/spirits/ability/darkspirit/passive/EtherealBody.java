package me.numin.spirits.ability.darkspirit.passive;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.PassiveAbility;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.DarkAbility;

public class EtherealBody extends DarkAbility implements PassiveAbility {

    public EtherealBody(Player player) {
        super(player);
    }

    @Override
    public String getAbilityType() {
        return PASSIVE;
    }

    @Override
    public void progress() {}

    @Override
    public boolean isSneakAbility() {
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
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public String getName() {
        return "EtherealBody";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public boolean isInstantiable() {
        return false;
    }

    @Override
    public boolean isProgressable() {
        return false;
    }

    public static double getFallDamageModifier2() {
        return Spirits.plugin.getConfig().getDouble("Abilities.Spirits.DarkSpirit.Passive.EtherealBody.FallDamageModifier");
    }
    
    public static double getAttackDamageModifier() {
        return Spirits.plugin.getConfig().getDouble("Abilities.Spirits.DarkSpirit.Passive.EtherealBody.AttackDamageModifier");
    }
}
