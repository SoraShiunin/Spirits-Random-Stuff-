package me.numin.spirits.ability.lightspirit.passive;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.SpiritElement;
import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;

public class WishfulThinking extends LightAbility implements PassiveAbility {
	
	private static String path = "Abilities.Spirits.LightSpirit.Passive.Awakening.";
	FileConfiguration config = Spirits.plugin.getConfig();
	
	private int regenPower;
	private int regenDuration;
	private long effectDuration;

	public WishfulThinking(Player player) {
		super(player);
		
		if (!bPlayer.hasElement(SpiritElement.LIGHT)) {
			return;
		}
		
		regenPower = config.getInt(path + "EffectAmplifier");
		effectDuration = config.getLong(path + "EffectDuration");
		
		regenDuration = Math.toIntExact((effectDuration * 1000) / 50);
		
		boolean enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.Enabled");
		
		if (enabled) {
			start();
		}
	}
	
    @Override
    public boolean isEnabled() {
        return Spirits.plugin.getConfig().getBoolean(path + ".Enabled", true);
    }
    
	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "WishfulThinking";
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenDuration, regenPower));
		ParticleEffect.HEART.display(player.getLocation().add(0, 2, 0), 1, 0F, 0F, 0F, 0F);
		ParticleEffect.SPELL_INSTANT.display(player.getLocation().add(0, 1, 0), 5, 0.3F, 0.3F, 0.3F, 0.3F);
		bPlayer.addCooldown(this);
		remove();
		return;
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

	@Override
	public boolean isExplosiveAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIgniteAbility() {
		// TODO Auto-generated method stub
		return false;
	}
}
