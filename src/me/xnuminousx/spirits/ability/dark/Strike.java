package me.xnuminousx.spirits.ability.dark;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.xnuminousx.spirits.Methods;
import me.xnuminousx.spirits.ability.api.DarkAbility;
import net.md_5.bungee.api.ChatColor;

public class Strike extends DarkAbility implements AddonAbility {

	private long cooldown;
	private Location origin;
	private Location location;
	private int range;
	private Vector direction;
	private boolean progress;
	private double damage;

	public Strike(Player player) {
		super(player);

		if (!bPlayer.canBend(this)) {
			return;
		}
		
		setFields();
		start();
	}

	private void setFields() {
		this.cooldown = ConfigManager.getConfig().getLong("Abilities.Spirits.DarkSpirit.Strike.Cooldown");
		this.damage = ConfigManager.getConfig().getDouble("Abilities.Spirits.DarkSpirit.Strike.Damage");
		this.range = ConfigManager.getConfig().getInt("Abilities.Spirits.DarkSpirit.Strike.Range");
		this.origin = player.getLocation().clone().add(0, 1, 0);
		this.location = origin.clone();
		this.direction = player.getLocation().getDirection();
		this.progress = true;
	}

	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline() || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation()) || origin.distanceSquared(location) > range * range) {
			remove();
			return;
		}
		strike();

	}
	
	public void strike() {
		if (progress) {
			location.add(direction.multiply(1));
			ParticleEffect.CRIT.display(location, 0, 0, 0, 0, 1);
		}
		
		for (Entity target : GeneralMethods.getEntitiesAroundPoint(location, 1.5)) {
			if (((target instanceof LivingEntity) || (target instanceof Player)) && (target.getEntityId() != player.getEntityId())) {
				bPlayer.addCooldown(this);
				Location location = player.getLocation();
				progress = false;
				
				LivingEntity le = (LivingEntity)target;
				Location tLoc = le.getLocation().clone();
				tLoc.setPitch(location.getPitch());
				tLoc.setYaw(location.getYaw());
				player.teleport(tLoc);
				
				DamageHandler.damageEntity(target, damage, this);
				player.getWorld().playSound(location, Sound.ENTITY_PLAYER_BURP, 0.2F, 0.2F);
			
				remove();
				return;
			}
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
		return "Strike";
	}
	
	@Override
	public String getDescription() {
		return Methods.getSpiritDescription("dark", "Offense", "The most basic ability of an aggressive, unbalanced Spirit is to rush towards their enemy and try to bite them in 1 swift motion. When you activate this ability, you'll see your target zone. If your target zone gets in range of another entity, you'll be rushed over to them an deal damage.");
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.DARK_GRAY + "Left-Click to trigger target spectacle";
	}

	@Override
	public String getAuthor() {
		return ChatColor.DARK_GRAY + "xNuminousx";
	}

	@Override
	public String getVersion() {
		return ChatColor.DARK_GRAY + "1.0";
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigManager.getConfig().getBoolean("Abilities.Spirits.DarkSpirit.Strike.Enabled");
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
	public void load() {

	}

	@Override
	public void stop() {

	}

}