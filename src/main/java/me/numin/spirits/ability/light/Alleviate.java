package me.numin.spirits.ability.light;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.utilities.Methods;

public class Alleviate extends LightAbility {

    //TODO: Make new sounds.
    //TODO: Implement configuration.

    private DustOptions customColor;
    private LivingEntity target;
    private Location location;
    private Vector vector = new Vector(1, 0, 0);

    private boolean hasReached, removeNegPots;
    private double range, selfDamage;
    private int currPoint, healDuration, nightVisDuration;
    private long chargeTime, healInt, otherCooldown, potInt, selfCooldown, time;

    public Alleviate(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();
        Entity targetEntity = GeneralMethods.getTargetedEntity(player, range);
        if (targetEntity instanceof LivingEntity)
            this.target = (LivingEntity) targetEntity;

        time = System.currentTimeMillis();
        start();
    }

    private void setFields() {
        //Alleviate
        this.otherCooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.Alleviate.Others.Cooldown");
        this.range = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.Alleviate.Others.Range");
        this.potInt = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.Alleviate.Others.PotionInterval");
        this.healInt = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.Alleviate.Others.HealInterval");
        this.selfDamage = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.Alleviate.Others.SelfDamage");

        //Sanctity
        this.selfCooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.Alleviate.Self.Cooldown");
        this.chargeTime = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.Alleviate.Self.ChargeTime");
        this.healDuration = Spirits.plugin.getConfig().getInt("Abilities.Spirits.LightSpirit.Alleviate.Self.HealDuration");
        this.nightVisDuration = Spirits.plugin.getConfig().getInt("Abilities.Spirits.LightSpirit.Alleviate.Self.NightVisionDuration");
        this.removeNegPots = Spirits.plugin.getConfig().getBoolean("Abilities.Spirits.LightSpirit.Alleviate.Self.RemoveNegativePotionEffects");

        int red = Spirits.plugin.getConfig().getInt("Abilities.Spirits.LightSpirit.Alleviate.ParticleColor.Red");
        int green = Spirits.plugin.getConfig().getInt("Abilities.Spirits.LightSpirit.Alleviate.ParticleColor.Green");
        int blue = Spirits.plugin.getConfig().getInt("Abilities.Spirits.LightSpirit.Alleviate.ParticleColor.Blue");
        this.customColor = new DustOptions(Color.fromRGB(red, green, blue), 1);

        this.location = player.getLocation().clone().add(0, 1, 0);
    }

    @Override
    public void progress() {
        if (!bPlayer.canBend(this) || !player.isSneaking()) {
            remove();
            return;
        }

        if (target != null && player.getLocation().distance(target.getLocation()) > range) {
            remove();
            return;
        }

        if (player.isSneaking()) {
            if (target == null) {
                progressSanctity();
            } else {
                if (!hasReached) showSelection();
                else progressAlleviate();
            }
        }
    }

    private void showSelection() {
        Location blast = Methods.advanceLocationToPoint(vector, location, target.getLocation().add(0, 1, 0), 0.5);

        player.getWorld().spawnParticle(Particle.DUST, blast, 10, 0.1, 0.1, 0.1, 0, customColor);

        if (blast.distance(player.getLocation()) > range ||
                blast.getBlock().isLiquid() ||
                GeneralMethods.isSolid(blast.getBlock())) {
            remove();
            return;
        }

        for (Entity entity : GeneralMethods.getEntitiesAroundPoint(blast, 1.3)) {
            if (entity.equals(target)) {
                hasReached = true;
                break;
            }
        }
    }

    private void progressAlleviate() {
        showSpirals(target.getLocation());

        if (System.currentTimeMillis() - time > potInt) {
            for (PotionEffect targetEffect : target.getActivePotionEffects()) {
                if (isNegativeEffect(targetEffect.getType())) {
                    target.removePotionEffect(targetEffect.getType());
                }
            }
            bPlayer.addCooldown(this, otherCooldown);
        }
        if (System.currentTimeMillis() - time > healInt) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 220, 1));
            target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 220, 1));
            target.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 220, 1));
            target.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 220, 0));
            target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 220, 0));
            target.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 220, 4));
        	player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 220, 1));
        	player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 220, 1));
        	player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 220, 1));
    		for (final PotionEffect effect : target.getActivePotionEffects()) {
    			if (ElementalAbility.isNegativeEffect(effect.getType())) {
    				target.removePotionEffect(effect.getType());
    			}
    		}
            target.getWorld().spawnParticle(Particle.END_ROD, player.getLocation(), 5, (float) Math.random() / 3, (float) Math.random() / 3, (float) Math.random() / 3, 0.05);
            DamageHandler.damageEntity(player, selfDamage, this);

            remove();
            return;
        }
        if (new Random().nextInt(20) == 0) {
            target.getWorld().playSound(target.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1, 1);
        }
    }

    private void progressSanctity() {
        showSpirals(player.getLocation());

        if (System.currentTimeMillis() > time + chargeTime) {
            for (PotionEffect playerEffects : player.getActivePotionEffects()) {
                if (isNegativeEffect(playerEffects.getType()) && removeNegPots) {
                    player.removePotionEffect(playerEffects.getType());
                }
            }
            if (player.getHealth() > 41) {
            	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 0));
            } else if (player.getHealth() <= 40 && player.getHealth() > 31 ) {
            	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1));
            } else if (player.getHealth() <= 30 && player.getHealth() > 21 ) {
            	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 0));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 140, 0));
            } else if (player.getHealth() <= 20 && player.getHealth() > 10 ) {
            	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 140, 1));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 0));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 140, 1));
            } else if (player.getHealth() <= 10) {
            	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 1));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 1));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 60, 1));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
            	player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 160, 3));
            } 
            
    		for (final PotionEffect effect : player.getActivePotionEffects()) {
    			if (ElementalAbility.isNegativeEffect(effect.getType())) {
    				player.removePotionEffect(effect.getType());
    			}
    		}
            player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation(), 5, (float) Math.random() / 3, (float) Math.random() / 3, (float) Math.random() / 3, 0.05);
            remove();
            return;
        }

        if (new Random().nextInt(20) == 0)
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1, 1);
    }

    private void showSpirals(Location location) {
        for (int i = 0; i < 6; i++) {
            currPoint += 360 / 200;
            if (currPoint > 360) {
                currPoint = 0;
            }
            double angle = currPoint * Math.PI / 180 * Math.cos(Math.PI);
            double x = (float) 0.04 * (Math.PI * 4 - angle) * Math.cos(angle + i);
            double y = 1.2 * Math.cos(angle) + 1.2;
            double z = (float) 0.04 * (Math.PI * 4 - angle) * Math.sin(angle + i);
            location.add(x, y, z);
            player.getWorld().spawnParticle(Particle.DUST, location, 1, 0, 0, 0, 0, customColor);
            location.subtract(x, y, z);
        }
    }

    @Override
    public void remove() {
        bPlayer.addCooldown(this);
        super.remove();
    }

    @Override
    public long getCooldown() {
        return target != null ? otherCooldown : selfCooldown;
    }

    @Override
    public Location getLocation() {
        return target != null ? target.getLocation() : player.getLocation();
    }

    @Override
    public String getName() {
        return "Alleviate";
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