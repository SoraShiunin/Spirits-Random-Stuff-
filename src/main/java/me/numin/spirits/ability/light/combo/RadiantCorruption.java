package me.numin.spirits.ability.light.combo;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Math;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.SpiritElement;
import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.utilities.Methods;

public class RadiantCorruption extends LightAbility implements ComboAbility {

    //TODO: Add sounds.

    private Location circleCenter, location, location2, location3;

    private boolean damageDarkSpirits, damageMonsters;
    @Attribute(Attribute.DAMAGE)
    private double playerDamage;
    @Attribute(Attribute.DAMAGE)
    private double radiantcorruptionMobDamage;
    @Attribute(Attribute.RADIUS)
    private double radius;
    private double counter;
    private int currPoint, effectInt;
    @Attribute(Attribute.COOLDOWN)
    private long cooldown;
    @Attribute(Attribute.DURATION)
    private long duration;
    private double rotationAngle = 0;
    
    public RadiantCorruption(Player player) {
        super(player);

        if (!bPlayer.canBendIgnoreBinds(this)) {
            return;
        }
        setFields();
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.07F, 5);
        start();
        bPlayer.addCooldown(this);
    }

    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.Cooldown");
        this.duration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.Duration");
        this.radius = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.Radius");
        this.effectInt = Spirits.plugin.getConfig().getInt("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.EffectInterval");
        this.damageDarkSpirits = Spirits.plugin.getConfig().getBoolean("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.HurtDarkSpirits");
        this.damageMonsters = Spirits.plugin.getConfig().getBoolean("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.HurtMonsters");
        this.playerDamage = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.playerDamage");
        this.radiantcorruptionMobDamage = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.radiantcorruptionMobDamage");
        location = player.getLocation();
        location2 = player.getLocation();
        location3 = player.getLocation();
        circleCenter = player.getLocation();
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        if (System.currentTimeMillis() > getStartTime() + duration) {
            remove();
            return;
        }
        spawnCircle();
        grabEntities();
    }

    private void spawnCircle() {
        Methods.createPolygon(location, 8, radius, 0.2, Particle.ENCHANT);
        for (int i = 0; i < 6; i++) {
            this.currPoint += 360 / 300;
            if (this.currPoint > 360) {
                this.currPoint = 0;
            }
            
         // Adjusting the angle to introduce rotation
            double angle = (this.currPoint + rotationAngle) * Math.PI / 180.0D;

         //   double angle = this.currPoint * Math.PI / 180.0D;
            double x = radius * Math.cos(angle);
            double x2 = radius * Math.sin(angle);
            double z = radius * Math.sin(angle);
            double z2 = radius * Math.cos(angle);
            location2.add(x, 0, z);
            ParticleEffect.END_ROD.display(location2, 0, 0, 0, 0, 1);
            location2.subtract(x, 0, z);

            location3.add(x2, 0, z2);
            ParticleEffect.END_ROD.display(location3, 0, 0, 0, 0, 1);
            location3.subtract(x2, 0, z2);
            
            location2.add(x, 3, z);
            ParticleEffect.END_ROD.display(location2, 0, 0, 0, 0, 1);
            location2.subtract(x, 3, z);

            location3.add(x2, 4, z2);
            ParticleEffect.END_ROD.display(location3, 0, 0, 0, 0, 1);
            location3.subtract(x2, 4, z2);
        }
        radius += 0.05;
        rotationAngle += 0.05;
        counter += Math.PI / 32;
        if (!(counter >= Math.PI * 4)) {
            for (double i = 0; i <= Math.PI * 2; i += Math.PI / 1.2) {
                double x = 0.5 * (Math.PI * 4 - counter) * Math.cos(counter - i);
                double y = 0.4 * counter;
                double z = 0.5 * (Math.PI * 4 - counter) * Math.sin(counter - i);
                location.add(x, y, z);
                Methods.playSpiritParticles(SpiritElement.LIGHT, location, 0, 0, 0, 0, 1);
                player.getWorld().spawnParticle(Particle.DUST, location, 1, 0.1, 0.1, 0.1, 0, new DustOptions(Color.fromBGR(255, 255, 255), 1));
                location.subtract(x, y, z);
            }
        }

        ParticleEffect.ENCHANTMENT_TABLE.display(location, 10, (float)(radius / 2), 0.4F, (float)(radius / 2), 0);
    }

    private void grabEntities() {
        for (Entity entity : GeneralMethods.getEntitiesAroundPoint(circleCenter, radius)) {
            if (entity instanceof LivingEntity && !(entity == player)) {
                healEntities(entity);
            }
        }
    }

    private void healEntities(Entity entity) {
        if (new Random().nextInt(effectInt) == 0) {
        	/*if (entity instanceof Player) {
                Player ePlayer = (Player) entity;
              BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);
                if (!bEntity.hasElement(Element.getElement("DarkSpirit"))) {
                    ParticleEffect.PORTAL.display(ePlayer.getLocation().add(0, 2, 0), 0, 0, 0, 0, 1);
                }
                } else if (entity instanceof Monster && damageMonsters) {
                DamageHandler.damageEntity(entity, damage, this);

            }*/ 
                LivingEntity le = (LivingEntity)entity;
                ParticleEffect.END_ROD.display(entity.getLocation().add(0, 2, 0), 0, 0, 0, 0, 1); 
        	    int absorptionDur = 0;
        	    int absorptionLv = 0;
        	    int health_boostDur = 0;
        	    int health_boostLv = 0;
        	    int regenerationDur = 0;
        	    int regenerationLv = 0;
        	    
        		if (entity instanceof HumanEntity) {
        			int saturationT = (int) ((HumanEntity) entity).getSaturation();
        			le.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, saturationT*20, saturationT));
        			DamageHandler.damageEntity(entity, saturationT/2+playerDamage, this);
        			((HumanEntity) entity).setSaturation(0);
        			//System.out.println("If player Entity Triggered");
        		}
        		else if (entity instanceof Mob) {
        			//System.out.println("If Mob Entity Triggered");
        			DamageHandler.damageEntity(entity, radiantcorruptionMobDamage, this);
        		}

        		if (le.hasPotionEffect(PotionEffectType.ABSORPTION)) {
        	          absorptionDur = le.getPotionEffect(PotionEffectType.ABSORPTION).getDuration();
        	          absorptionLv = le.getPotionEffect(PotionEffectType.ABSORPTION).getAmplifier();
        		}
        		if (le.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
        	         health_boostDur = le.getPotionEffect(PotionEffectType.HEALTH_BOOST).getDuration();
        	         health_boostLv = le.getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier();
        		}
        			
        		if (le.hasPotionEffect(PotionEffectType.REGENERATION)) {
        	         regenerationDur = le.getPotionEffect(PotionEffectType.REGENERATION).getDuration();
        	         regenerationLv = le.getPotionEffect(PotionEffectType.REGENERATION).getAmplifier();
        		}
        		
                int witherLv = 0; 
                int witherDur = 0;
                witherLv = absorptionLv + health_boostLv + regenerationLv;
                witherDur = absorptionDur + health_boostDur + regenerationDur;
                
                int maxWitherLv = 254; 
                witherLv = Math.min(witherLv, maxWitherLv);
                int maxWitherDur = 72000; 
                witherDur = Math.min(witherDur, maxWitherDur);
        		((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) witherDur, (int) witherLv));
        		
        /*		System.out.println("absorptionLv: " + absorptionLv + ", " +
        		        "health_boostLv: " + health_boostLv + ", " +
        		        "regenerationLv: " + regenerationLv + ", " +
        		        "absorptionDur: " + absorptionDur + ", " +
        		        "health_boostDur: " + health_boostDur + ", " +
        		        "regenerationDur: " + regenerationDur + ", " +
        		        "witherLv: " + witherLv + ", " +
        		        "witherDur: " + witherDur);
        */
        		((LivingEntity) entity).removePotionEffect(PotionEffectType.ABSORPTION);
        		((LivingEntity) entity).removePotionEffect(PotionEffectType.HEALTH_BOOST);
        		((LivingEntity) entity).removePotionEffect(PotionEffectType.REGENERATION);      
                
                
            }
        }
    

    @Override
    public Object createNewComboInstance(Player player) {
        return new RadiantCorruption(player);
    }

    @Override
    public ArrayList<AbilityInformation> getCombination() {
        ArrayList<AbilityInformation> combo = new ArrayList<AbilityInformation>();
        combo.add(new AbilityInformation("LuminousCorruption", ClickType.LEFT_CLICK));
        combo.add(new AbilityInformation("LuminousCorruption", ClickType.LEFT_CLICK));
        combo.add(new AbilityInformation("LuminousCorruption", ClickType.SHIFT_DOWN));
        combo.add(new AbilityInformation("LuminousCorruption", ClickType.SHIFT_UP));
        return combo;
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
        return "RadiantCorruption";
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
        return false;
    }
}