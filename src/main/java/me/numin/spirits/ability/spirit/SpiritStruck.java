package me.numin.spirits.ability.spirit;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.airbending.Suffocate;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.MovementHandler;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.SpiritAbility;

public class SpiritStruck extends SpiritAbility {

	@Attribute(Attribute.COOLDOWN)
	private long cooldown;
	@Attribute(Attribute.DURATION)
	private long duration;
	private Entity target;

	public SpiritStruck(final Player sourceplayer, final Entity targetentity) {
		super(sourceplayer);
		if (!this.bPlayer.canBend(this)) {
			return;
		}
		this.target = targetentity;
		if (!(this.target instanceof LivingEntity)) {
			return;
		}
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.SpiritStruck.Cooldown");
        this.duration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.SpiritStruck.Duration");
		this.start();
	}

	@Override
	public void progress() {
		if (this.bPlayer.canBend(this)) {
			if (this.target instanceof Player) {
				if (Commands.invincible.contains(((Player) this.target).getName())) {
					this.remove();
					return;
				}
			}
			this.spiritstruck(this.target);
			double HPDamage = player.getHealth()*0.1;
			DamageHandler.damageEntity(player, HPDamage+1, this);
			this.bPlayer.addCooldown(this);
		}
		this.remove();
	}

	private void spiritstruck (final Entity entity) {
		if (entity instanceof Creature) {
			((Creature) entity).setTarget(null);
		}

		if (entity instanceof Player) {
			if (Suffocate.isChannelingSphere((Player) entity)) {
				Suffocate.remove((Player) entity);
			}
		}
		final MovementHandler mh = new MovementHandler((LivingEntity) entity, SpiritAbility.getAbility(SpiritStruck.class));
		mh.stopWithDuration(this.duration / 1000 * 20, Element.CHI.getColor() + "* Spirit Struck *"); //duration calculation is for milliseconds
		entity.getWorld().spawnParticle(Particle.SONIC_BOOM, target.getLocation(), 2, (float) Math.random() / 3, 1+(float) Math.random() / 3, (float) Math.random() / 3, 0);
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ALLAY_HURT, 2, 0);
		((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 20, 1));
	}

	@Override
	public String getName() {
		return "SpiritStruck";
	}

	@Override
	public Location getLocation() {
		return this.target != null ? this.target.getLocation() : null;
	}

	@Override
	public long getCooldown() {
		return this.cooldown;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	public Entity getTarget() {
		return this.target;
	}

	public void setTarget(final Entity target) {
		this.target = target;
	}

	public long getDuration() {
		return this.duration;
	}

	@Override
	public boolean isExplosiveAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIgniteAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAbilityType() {
		// TODO Auto-generated method stub
		return null;
	}
}
