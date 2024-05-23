package me.numin.spirits.ability.light;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.LightAbility;

public class LightBeam extends LightAbility {
	
	private static String path = "Abilities.Spirits.LightSpirit.LightBeam.";
	FileConfiguration config = Spirits.plugin.getConfig();
	
	private long cooldown;
	private long duration;
	private double range;
	private double damage;
	
	private long time;
	
	private Location location;
	private Vector direction;

	public LightBeam(Player player) {
		super(player);
		
		cooldown = Spirits.plugin.getConfig().getLong(path + "Cooldown");
		duration = Spirits.plugin.getConfig().getLong(path + "Duration");
		range = Spirits.plugin.getConfig().getDouble(path + "Range");
		damage = Spirits.plugin.getConfig().getDouble(path + "Damage");
		
		time = System.currentTimeMillis();
		
		if (player.isSneaking()) {
			start();
		}
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

			if (GeneralMethods.isRegionProtectedFromBuild(player, "LightBeam", location)) {
				return;
			}
			
			ParticleEffect.CRIT_MAGIC.display(location, 2, (float) Math.random() / 3, 0.8F, (float) Math.random() / 3, 0.5F);
			ParticleEffect.ENCHANTMENT_TABLE.display(location, 3, (float) Math.random() / 7, 0.8F, (float) Math.random() / 7, 0.2F);

			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 0.5)) {
				if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId() && !(entity instanceof ArmorStand)) {
					if (entity instanceof LivingEntity) {
						DamageHandler.damageEntity(entity, damage, this);
						
						if (((LivingEntity) entity).getNoDamageTicks() > 20) {
							((LivingEntity) entity).setNoDamageTicks(10);
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
		return OFFENSE;
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
        return "LightBeam";
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
