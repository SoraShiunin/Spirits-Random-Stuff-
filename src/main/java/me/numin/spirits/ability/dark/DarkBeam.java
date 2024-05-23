package me.numin.spirits.ability.dark;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.ability.api.SpiritAbility;

public class DarkBeam extends DarkAbility {
	
	private static String path = "Abilities.Spirits.DarkSpirit.DarkBeam.";
	FileConfiguration config = Spirits.plugin.getConfig();
	
	private long cooldown;
	private long duration;
	private double range;
	private double damage;
	
	private long time;
	
	private Location location;
	private Vector direction;

	public DarkBeam(Player player) {
		super(player);
		
		cooldown = config.getLong(path + "Cooldown");
		duration = config.getLong(path + "Duration");
		range = config.getDouble(path + "Range");
		damage = config.getDouble(path + "Damage");
		
		time = System.currentTimeMillis();
		
		if (player.isSneaking()) {
			start();
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
		return "DarkBeam";
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		if (!bPlayer.canBendIgnoreBinds(this)) {
			remove();
			return;
		}
		if (!player.isOnline() || player.isDead()) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		if (player.isSneaking()) {
			createBeam();
		} else {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}
	
	private void createBeam() {
		location = player.getLocation().add(0, 1, 0);
		direction = location.getDirection();
		for (double i = 0; i < range; i += 0.5) {
			location = location.add(direction.multiply(0.5).normalize());

			if (GeneralMethods.isRegionProtectedFromBuild(player, "DarkBeam", location)) {
				return;
			}
			
			ParticleEffect.SPELL_WITCH.display(location, 5, (float) Math.random() / 8, 0.8F, (float) Math.random() / 8, 2F);
			ParticleEffect.PORTAL.display(location, 3, (float) Math.random() / 8, 0.8F, (float) Math.random() / 8, 0);

			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 0.5)) {
				if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId() && !(entity instanceof ArmorStand)) {
					if (entity instanceof LivingEntity) {
						DamageHandler.damageEntity(entity, damage, this);
						for(PotionEffect effect:((LivingEntity) entity).getActivePotionEffects()) {
						((LivingEntity) entity).removePotionEffect(effect.getType());
						}
						if (((LivingEntity) entity).getNoDamageTicks() > 20) {
							((LivingEntity) entity).setNoDamageTicks(15);
						}
					}
				}
			}
			if (GeneralMethods.isSolid(location.getBlock())) {
				return;
			}
		}
	}
    @Override
    public boolean isEnabled() {
        return Spirits.plugin.getConfig().getBoolean(path + ".Enabled", true);
    }
	@Override
	public String getAbilityType() {
		return SpiritAbility.OFFENSE;
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
