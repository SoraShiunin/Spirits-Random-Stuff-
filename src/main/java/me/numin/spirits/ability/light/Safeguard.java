package me.numin.spirits.ability.light;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;

public class Safeguard extends LightAbility {
	
	private static String path = "Abilities.Spirits.LightSpirit.Safeguard.";
	FileConfiguration config = Spirits.plugin.getConfig();
	
	private long cooldown;
	private long duration;
	
	private int rotation;
	private double y;
	private long time;
	
	private PotionEffectType[] negativeEffects = new PotionEffectType[] {
			PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.POISON,
			PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS,
			PotionEffectType.UNLUCK, PotionEffectType.WITHER };

	public Safeguard(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		duration = config.getLong(path + "Duration");
		
		y = 2.5;
		
		time = System.currentTimeMillis();
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 1F, 1F);
		
		start();
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
		return "Safeguard";
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 0));
		if (System.currentTimeMillis() > time + duration) {
			bPlayer.addCooldown(this);
			remove();
			return;
			
		} else {
			
			animate(player);
			
			ParticleEffect.ENCHANTMENT_TABLE.display(player.getLocation().add(0, 1, 0), 3, 0F, 0F, 0F, 0.1F);
			for (PotionEffectType nEffects : negativeEffects) {
				if (player.hasPotionEffect(nEffects)) {
					player.removePotionEffect(nEffects);
				}
			}
		}
	}
	
	private void animate(Player player) {
		rotation++;
		
		y -= 0.15;
		
		if (y <= 0) {
			return;
		}
		
		for (int i = -180; i < 180; i += 20) {
			Location playerLoc = player.getLocation();
			
	        double angle = i * 3.141592653589793D / 180.0D;
	        double x = 0.9 * Math.cos(angle + rotation);
	        double z = 0.9 * Math.sin(angle + rotation);
	        
	        Location loc = playerLoc.clone();
	        
	        loc.add(x, y, z);
	        ParticleEffect.CRIT_MAGIC.display(loc, 1);
    	}
	}
	
	@Override
	public void remove() {
		super.remove();
		
		if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
			player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		}
	}

    @Override
    public boolean isEnabled() {
        return Spirits.plugin.getConfig().getBoolean(path + ".Enabled", true);
    }
    
	@Override
	public String getAbilityType() {
		return SpiritAbility.DEFENSE;
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
