package me.numin.spirits.ability.spirit;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.attribute.Attribute;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.SpiritAbility;

public class Paint extends SpiritAbility {

    //TODO: Update sounds.


	@Attribute(Attribute.COOLDOWN)
	private long cooldown;
	@Attribute(Attribute.DURATION)
	private long duration;
	private long time;
	Random rand = new Random();
	private DustOptions customColor;
	private Location initialLocation;
	private Location initialEyeLocation;
    private BukkitRunnable particleTask;
	
    public Paint(Player player) {
        super(player);
        setFields();
        time = System.currentTimeMillis();
        initialEyeLocation = player.getEyeLocation();
        start();
        bPlayer.addCooldown(this);

        
    }
    
    

    
    
    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Paint.Cooldown");
        this.duration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Paint.Duration");

        
    }

    @Override
    public void progress() {
    	
		if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration + 500) {
			remove();
			return;
		} else {
			initialLocation = initialEyeLocation.clone();
		       if (!player.isSneaking()){
		    	   
		    	   ParticlePaint();
		       } 
		       else {
		    	   remove(); //startParticleTask for particle trail function
		       }
			
		}
    }

    private void ParticlePaint() {
		
			 
			this.customColor = new DustOptions(Color.fromRGB(20, 119, 168), 1);

		    double offset = 3; 

		    // Calculate the direction vector
		    Vector direction = initialLocation.getDirection().normalize();

		    // Apply offset to the coordinates
		    double x = initialLocation.getX() + direction.getX() * offset;
		    double y = initialLocation.getY() + direction.getY() * offset;
		    double z = initialLocation.getZ() + direction.getZ() * offset;
		    
		    Location spawnLocation = findSafeSpawnLocation(new Location(initialLocation.getWorld(), x, y, z), direction);

		                player.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation, 1, 0, 0, 0, 0.1, customColor);
		                if (System.currentTimeMillis() - time > duration) {
		                    remove();
		                }
		            }
    
    
    @SuppressWarnings("unused")
	private void startParticleTask() {
        particleTask = new BukkitRunnable() {
            @Override
            public void run() {
                ParticleTrail();
            }
        };
        particleTask.runTaskTimer(Spirits.plugin, 0L, 2L); 
        
    }
    
    private void ParticleTrail() {
		
		 
			this.customColor = new DustOptions(Color.fromRGB(20, 119, 168), 1);

		    double offset = 3;
		    Location initialLocation = player.getEyeLocation();
		    Vector direction = initialLocation.getDirection().normalize();
		    
		    if (player.isSneaking()) {
		    	double x = initialLocation.getX() + direction.getX() * offset;
			    double y = initialLocation.getY() + direction.getY() * offset;
			    double z = initialLocation.getZ() + direction.getZ() * offset;
			    
			    Location spawnLocation = findSafeSpawnLocation(new Location(initialLocation.getWorld(), x, y, z), direction);
			     player.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation, 1, 0, 0, 0, 0.1, customColor);
			                
	        }
		    // Apply offset to the coordinates
		                /*if (System.currentTimeMillis() - time > duration) {
		                    remove();
		                } */}
    

    // Method to find a safe spawn location
    private Location findSafeSpawnLocation(Location location, Vector direction) {
        int maxIterations = 40; // Maximum number of iterations to prevent infinite loops
        double increment = -0.1; // Increment value for each iteration

        for (int i = 0; i < maxIterations; i++) {
            if (!location.getBlock().getType().isSolid()) {
                return location; // Found a non-solid block
            }
            location.add(direction.clone().multiply(increment)); // Move spawn position slightly forward
        }

        // Return the original location if no safe spawn location is found
        return location;
    }
    
    
    
    
    @Override
    public void remove() {
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
        return "Paint";
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

    @Override
    public boolean isSneakAbility() {
        return true;
    }
}