package me.numin.spirits.ability.spirit;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.SpiritAbility;

public class Vortex extends SpiritAbility {
	
	private long cooldown;
	private double radius;
	private double pull;
	private long duration;
	private int slowPower;
	private int slowDuration;
	private long effectDuration;
	
	private long time;
	private double size;
	private int rotation;
	
	private Location origin;
	private Location location;
	private Vector pullDirection;
	Random rand = new Random();

	public Vortex(Player player) {
		super(player);

		if (!bPlayer.canBendIgnoreBinds(this)) {
			return;
		}

		if (GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			return;
		}
		
        boolean enabled = Spirits.plugin.getConfig().getBoolean("Abilities.Spirits.Neutral.Vortex.Enabled");
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Vortex.Cooldown");
        this.radius = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Neutral.Vortex.Radius");
        this.pull = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Neutral.Vortex.Pull");
        this.duration = Spirits.plugin.getConfig().getInt("Abilities.Spirits.Neutral.Vortex.Duration");
        this.slowPower = Spirits.plugin.getConfig().getInt("Abilities.Spirits.Neutral.Vortex.EffectAmplifier");
        this.effectDuration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Vortex.EffectDuration");
		
		slowDuration = Math.toIntExact((effectDuration * 1000) / 50);
		time = System.currentTimeMillis();
		origin = player.getLocation();
		size = radius;
		
		
		
		
		
		if (enabled) {
			if (player.isSneaking()) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 2F, 2F);
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2F, 1.5F);
			start();
			}
		}
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public String getName() {
		return "Vortex";
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration) {
			remove();
			return;
		} else {
			Vortex();
			bPlayer.addCooldown(this);
		}
	}
	
	private void Vortex() {
		rotation++;
		
		if (rotation % 3 == 0) {
			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(origin, radius)) {
				if (/*entity.getUniqueId() != player.getUniqueId() && */entity instanceof LivingEntity) {
					if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation()) /*|| ((entity instanceof Player) && Commands.invincible.contains(((Player) entity).getName()))*/) {
						continue;
					}
					if (GeneralMethods.locationEqualsIgnoreDirection(origin, entity.getLocation())) {
						continue;
					}
					//PullDirection is the direction to pull in :)
					//pullDirection = GeneralMethods.getDirection(entity.getLocation(), origin);
					pullDirection = new Vector(0, -2, 0);
					entity.setVelocity(pullDirection.multiply(pull));
					
					((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, slowDuration, slowPower));
				}
			}
		}
		
		if (size <= 0) {
			size = radius;
			
			player.getWorld().playSound(origin, Sound.BLOCK_BELL_RESONATE, 2F, 1.5F);
			
			if (rand.nextInt(2) == 0) {
				player.getWorld().playSound(location, Sound.BLOCK_BELL_RESONATE, 2F, 1.5F);
			}
		} else {
			size -= 0.3;
			for (int i = -180; i < 180; i += 90) {
				double angle = Math.toRadians(i + rotation * 1.5); // Increase the rotation speed for a more pronounced spiral
		        //double angle = i * 3.141592653589793D / 180.0D;
		        double x = size * Math.cos(angle /*+ rotation*/);
		        double z = size * Math.sin(angle /*+ rotation*/);
		        double y = radius - size;
		        //Location loc = origin.clone();
		        Location loc = origin.clone().add(x, -y, z);
		        //loc.add(x, 0, z);
		        ParticleEffect.SMOKE_NORMAL.display(loc, 3, 0.2F, 2.2F, 0.2F, 0F);
		        ParticleEffect.SMOKE_NORMAL.display(loc, 3, 0.8F, 2.3F, 0.8F, 0F);
		        ParticleEffect.CRIT_MAGIC.display(loc, 3, 0.1F, 2.1F, 0.1F, 0.1F);
		    
	    	}
			
			for (int j = -180; j < 180; j += 20) {
	        	double angle = Math.toRadians(j + rotation * 5);
	        	double x = size * Math.cos(angle /*+ rotation*/);
	        	double z = size * Math.sin(angle /*+ rotation*/);
	        	double y = radius - size;
	        	//Location loc = origin.clone();
	        	//loc.add(x, 0, z);
	        	Location loc = origin.clone().add(x, -y, z);
	        	ParticleEffect.CRIT_MAGIC.display(loc, 3, 0.1F, 2.1F, 0.1F, 0.1F);
	        	ParticleEffect.CURRENT_DOWN.display(loc, 3, 0.2F, 2.4F, 0.2F, 0.1F);
	        	
	        	for (Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, 1.5)) {
					if (entity instanceof LivingEntity) {
						if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation())) {
							continue;
						}
						ParticleEffect.CRIT_MAGIC.display(entity.getLocation(), 5, 0.3F, 0.3F, 0.3F, 0.05F);
					}
				}
	        }
		}
	}


    @Override
    public String getAbilityType() {
        return UTILITY;
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

}
