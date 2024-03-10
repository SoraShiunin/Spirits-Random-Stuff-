package me.numin.spirits.ability.darkspirit.passive;

import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.SpiritElement;
import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.SpiritAbility;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.numin.spirits.listeners.Abilities;


public class EnergyVeil extends SpiritAbility implements PassiveAbility {
	
/*	public static Player test123(Player player) {
		Player playerEXP2 = event.getPlayer();
	}
	*/
	private static double healthDivider;
	private static double maxHPBonus;
	private static double experienceMultiplier;
	public static double veilApply2(double expAmount) {
		return expAmount;
		
	}
	
	public static double veilApply(Player playerEvent) {
		
		healthDivider = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Neutral.Passive.EnergyVeil.healthDivider");
		experienceMultiplier = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Neutral.Passive.EnergyVeil.experienceMultiplier");
		maxHPBonus = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Neutral.Passive.EnergyVeil.maxHPBonus");
		
		Player player1 = playerEvent;
		double playerMaxHP = player1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		double playerCurrentHP = player1.getHealth();
		double playerHPMaxCurrDiff = playerCurrentHP / playerMaxHP * healthDivider;
		double playerCurrentXP = player1.getTotalExperience();
		double totalBoost = playerHPMaxCurrDiff + experienceMultiplier;
		if (playerCurrentHP == playerMaxHP) { //if at max hp, add the max hp bonus
			totalBoost = totalBoost + maxHPBonus;
		}
		return totalBoost;

	}
	
	public static double veilApply;
	
	@Override
	public void progress() {		
	}
	
    public EnergyVeil(Player player) {
        super(player);
    }

    @Override
    public String getAbilityType() {
        return PASSIVE;
    }

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
        return "EnergyVeil";
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

}
