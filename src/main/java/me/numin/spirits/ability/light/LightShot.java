package me.numin.spirits.ability.light;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.Manager;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.FlightHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.Spirits;
import me.numin.spirits.utilities.Methods;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class LightShot extends LightAbility {

    //TODO: Maybe smooth out the blast and entity detection logic, outdated.
    //TODO: Remove the checkEntities boolean and add a Entity variable instead.
    //TODO: Update sounds.

    private Entity target;
    private Location location, origin;
    private Vector direction;

    private boolean checkEntities;
    @Attribute(Attribute.DURATION)
    private int potionduration;
    @Attribute(Attribute.RADIUS)
    private double radius;
    private int currPoint;
    @Attribute(Attribute.RANGE)
    private double range;
    @Attribute(Attribute.COOLDOWN)
    private long cooldown;
	private long time;
    private long realStartTime;
    private double selfDamage;
	private double aoeRadius;

	
    public LightShot(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

		if (player.isSneaking()) {
	        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 2);
	        //DamageHandler.damageEntity(player, selfDamage, this);
	        player.setHealth(player.getHealth()-selfDamage);
			bPlayer.addCooldown(this);
			
			start();
		}
    }

    @Override
    public String getAbilityType() {
        return UTILITY;
    }

    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.LightShot.Cooldown");
        this.range = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.LightShot.Range");
        this.radius = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.LightShot.Radius");
        this.selfDamage = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.LightShot.SelfDamage");
        this.aoeRadius = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.LightShot.AoeRadius");
        this.potionduration = (int) Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.LightShot.PotionDuration");
        this.origin = player.getLocation().clone().add(0, 1, 0);
        this.location = origin.clone();
        this.direction = player.getLocation().getDirection();
        this.checkEntities = true;
    }

    @Override
    public void progress() {
 
	  bind();
	
    }

    private void bind() {
    	Location playerLoc = player.getLocation();
    	//player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, location, 20, 0, 1, 0, 0);
        if (checkEntities) {
        	//blastSpiral(player.getLocation());
            Location blast = location.add(direction.multiply(1).normalize());
            blastSpiral(blast);

            if (origin.distance(blast) > range) {
                remove();
                return;
            }
            for (Entity entity : GeneralMethods.getEntitiesAroundPoint(blast, radius)) {
                if (entity instanceof LivingEntity && !entity.getUniqueId().equals(player.getUniqueId())) {
                    
                    this.target = entity;
                    checkEntities = false;
                }
            }
        } else {
            if (this.target == null || this.target.isDead() || this.target.getWorld() != player.getWorld()) {
                remove();
                return;
            }
            if (true) {
            	holdSpiral(target.getLocation());
            	holdSpiral(player.getLocation());
            	
                remove();
            } 

        }
    }


	
    private void blastSpiral(Location location) {
        for (int i = 0; i < 6; i++) {
            currPoint += 360 / 200;
            if (currPoint > 360) {
                currPoint = 0;
            }
            double angle = currPoint * Math.PI / 180 * Math.cos(Math.PI);
            double x = 0.04 * (Math.PI * 3 - angle) * Math.cos(angle + i); //Modified from 4 to 3
            double z = 0.04 * (Math.PI * 3 - angle) * Math.sin(angle + i); //Modified from 4 to 3
            location.add(x, 0.1F, z);
            player.getWorld().spawnParticle(Particle.REDSTONE, location, 5, 0.1, 0.1, 0.1, 0, new DustOptions(Color.fromBGR(255, 255, 255), 1));
            player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, location, 5, 0, 0.8, 0, 0);
            location.subtract(x, 0.1F, z);
        }
    }
    private void holdSpiral(Location location) {

        for (int t = 0; t < 2; t++) {
            currPoint += 360 / 30;
            if (currPoint > 360) {
                currPoint = 0;
            }
            double angle2 = currPoint * Math.PI / 180 * Math.cos(Math.PI);
            double x2 = 0.04 * (Math.PI * 2 - angle2) * Math.cos(angle2 + t); //MODIFIED
            double z2 = 0.04 * (Math.PI * 4 - angle2) * Math.sin(angle2 + t); //MODIFIED
            location.add(x2, 0.2F, z2); //MODIFIED FROM 01 TO 02
            //player.getWorld().spawnParticle(Particle.HEART, location, 1, 0, 0, 0, 0.3);
            location.subtract(x2, 0.1F, z2);
    		final List<Entity> entities = GeneralMethods.getEntitiesAroundPoint(this.location, this.aoeRadius);
    		LivingEntity livingTarget = (LivingEntity)this.target;
    		for (final Entity entity : entities) {
    			if (entity instanceof LivingEntity && !(entity == player)) {
    				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, potionduration, 4));
    	            //livingTarget.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 60, 1));
    				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, potionduration, 0));
    				Location elocation1 = entity.getLocation();
    				((LivingEntity) entity).getWorld().spawnParticle(Particle.END_ROD, elocation1, 5, 0, 0, 0, 0.3);
    				((LivingEntity) entity).getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, elocation1, 1, 0, 0.1, 0, 0.3);
    				((LivingEntity) entity).getWorld().spawnParticle(Particle.ELECTRIC_SPARK, elocation1, 1, 0, 0, 0.2, 0.3);
    				((LivingEntity) entity).getWorld().spawnParticle(Particle.REDSTONE, elocation1, 2, 0.1, 0.1, 0.1, 0, new DustOptions(Color.fromBGR(255, 255, 255), 1));
    				((LivingEntity) entity).getWorld().spawnParticle(Particle.REDSTONE, elocation1, 2, 0.1, 0.1, 0.1, 0, new DustOptions(Color.fromBGR(235, 207, 0), 1));
    	            
    			}
    		}
        }
    }

    @Override
    public void remove() {


        if (this.target != null) {
            if (this.target instanceof Player) {
            }
            
        }
       
        super.remove();
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
    public double getCollisionRadius() {
        return radius;
    }

    @Override
    public String getName() {
        return "LightShot";
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
}