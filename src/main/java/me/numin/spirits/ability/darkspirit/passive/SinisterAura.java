package me.numin.spirits.ability.darkspirit.passive;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.SpiritElement;
import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.ability.api.SpiritAbility;

public class SinisterAura extends DarkAbility implements PassiveAbility {
	
	private static String path = "Abilities.Spirits.DarkSpirit.Passive.SinisterAura.";
	FileConfiguration config = Spirits.plugin.getConfig();
	
	private ArrayList<Location> locations = new ArrayList<Location>();
	
	private long cooldown;
	private int power;
	private int duration;
	private double range;
	private long effectDuration;
	
	private double size;
	private int rotation;

	public SinisterAura(Player player) {
		super(player);
		
		if (!bPlayer.hasElement(SpiritElement.DARK)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		power = config.getInt(path + "EffectAmplifier");
		effectDuration = config.getLong(path + "EffectDuration");
		range = config.getDouble(path + "Range");
		
		duration = Math.toIntExact((effectDuration * 1000) / 50);
		
		boolean enabled = config.getBoolean("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.Enabled");
		
		if (enabled) {
			start();
		}
	}
    
	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "SinisterAura";
	}

	@Override
	public boolean isExplosiveAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isIgniteAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		size += 0.5;
		rotation++;
		for (int i = -180; i < 180; i += 40) {
	        double angle = i * 3.141592653589793D / 180.0D;
	        double x = size * Math.cos(angle + rotation);
	        double z = size * Math.sin(angle + rotation);
	        Location loc = player.getLocation().clone();
	        loc.add(x, 0, z);
	        
	        ParticleEffect.SMOKE_NORMAL.display(loc, 1, (float) Math.random() / 3, (float) Math.random() / 3, (float) Math.random() / 3, 0F);
			ParticleEffect.SPELL_WITCH.display(loc, 1, (float) Math.random() / 3, (float) Math.random() / 3, (float) Math.random() / 3, 0F);
			
			locations.add(loc);
			
			for (Location location : locations) {
				for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 2)) {
					if (entity.getUniqueId() != player.getUniqueId() && entity instanceof LivingEntity) {
						((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, duration, power));
						((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, duration, power));
						((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, power));
						((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, power));
						((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, duration, power));
					}
				}
			}
    	}
		if (size >= range) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}

	@Override
	public boolean isInstantiable() {
		return false;
	}

	@Override
	public boolean isProgressable() {
		return true;
	}

	@Override
	public String getAbilityType() {
		return SpiritAbility.PASSIVE;
	}
}
