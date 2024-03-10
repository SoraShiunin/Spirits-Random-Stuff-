package me.numin.spirits.ability.dark;

import com.google.errorprone.annotations.ForOverride;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
import me.numin.spirits.ability.spirit.combo.Levitation;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.joml.Math;

import me.numin.spirits.Spirits;
import me.numin.spirits.utilities.Methods;
import net.md_5.bungee.api.ChatColor;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;

public class Berserker extends DarkAbility {

    //TODO: Update sounds.

    private Location location;

    @Attribute(Attribute.COOLDOWN)
    private long cooldown;

    @Attribute("Distance")
    private double distance;
    private long realStartTime;
   
    // boolean isBerserkActive = false;
	

    public Berserker(Player player) {
        super(player);

        if (!bPlayer.canBend(this) || CoreAbility.hasAbility(player, Levitation.class)) {
            return;
        }

        setFields();
        //Even though this is just a dash ability, we do it via start() so that attribute modifiers can work
        start();

    }

    @Override
    public String getAbilityType() {
        return MOBILITY;
    }

    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.DarkSpirit.Berserker.Cooldown");
        this.distance = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.DarkSpirit.Berserker.Distance");
        this.location = player.getLocation();
    }

    @Override
    public void progress() {
        if (!bPlayer.canBend(this)) {
            remove();
            return;
        }

        itsBerserkTime();
        
        
  //Make berse4ker give you buffs and make you not take any damage but after a while you take all damage and doubled.
   //Check TremorSense
    }


    private void itsBerserkTime() {
    	if (player.getHealth() >= player.getMaxHealth()*0.5) {
    	 int healthSacrificed = (int) Math.floor(player.getHealth()*0.99);
    	Methods.playSpiritParticles(player, player.getLocation(), 0.5, 0.5, 0.5, 0, 10);
        location.getWorld().playSound(location, Sound.ITEM_TRIDENT_RETURN, 1F, 0.6F);
        GeneralMethods.setVelocity(this, player, Methods.setVelocity(player, (float) distance, 0.2));
        player.setHealth(player.getHealth()-player.getHealth()*0.99);
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, (int) Math.floor(healthSacrificed)*10, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) Math.floor(healthSacrificed)*10, (int) Math.floor(healthSacrificed/10)));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) Math.floor(healthSacrificed)*10, (int) Math.floor(healthSacrificed/3)));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, (int) Math.floor(healthSacrificed)*10, (int) Math.floor(healthSacrificed/3)));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) Math.floor(healthSacrificed)*10, (int) Math.floor(healthSacrificed/15)));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (int) Math.floor(healthSacrificed)*10, (int) Math.floor(healthSacrificed/15)));
        remove();
    	}
    	else {
    		remove();
    	}
        /*
    	realStartTime = System.currentTimeMillis();
        if (System.currentTimeMillis() > realStartTime + 3000) {
            remove();
        }
        else {   
        }*/
        
 }
    

    @Override
    public void remove() {
        bPlayer.addCooldown("Berserker", cooldown);
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
        return "Berserker";
    }

    @Override
    public boolean isExplosiveAbility() {
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
    public boolean isSneakAbility() {
        return false;
    }
}