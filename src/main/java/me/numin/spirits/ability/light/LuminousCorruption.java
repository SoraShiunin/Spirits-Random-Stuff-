package me.numin.spirits.ability.light;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Math;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.airbending.Suffocate;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.MovementHandler;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;

public class LuminousCorruption extends LightAbility {

	@Attribute(Attribute.COOLDOWN)
	private long cooldown;
	@Attribute(Attribute.DURATION)
	private long duration;
	@Attribute(Attribute.DAMAGE)
	private long luminouscorruptionMobDamage;
	@Attribute(Attribute.DAMAGE)
	private long playerDamage;
	private Entity target;

    
	public LuminousCorruption(final Player sourceplayer, final Entity targetentity) {
		super(sourceplayer);
		if (!this.bPlayer.canBend(this)) {
			return;
		}
		this.target = targetentity;
		if (!(this.target instanceof LivingEntity)) {
			return;
		}
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.LuminousCorruption.Cooldown");
        this.duration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.LuminousCorruption.Duration");
        this.luminouscorruptionMobDamage = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.LuminousCorruption.luminouscorruptionMobDamage");
        this.playerDamage = Spirits.plugin.getConfig().getLong("Abilities.Spirits.LightSpirit.LuminousCorruption.playerDamage");
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
			this.luminouscorruption(this.target);
			this.bPlayer.addCooldown(this);

			
		}
		this.remove();
	}
    
	private void luminouscorruption (final Entity entity) {
	    int absorptionDur = 0;
	    int absorptionLv = 0;
	    int health_boostDur = 0;
	    int health_boostLv = 0;
	    int regenerationDur = 0;
	    int regenerationLv = 0;
		if (entity instanceof HumanEntity) {
			int saturationT = (int) ((HumanEntity) entity).getSaturation();
			((HumanEntity) entity).setSaturation(0);
			((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (int) duration*20, saturationT));
			DamageHandler.damageEntity(entity, saturationT+playerDamage, this);
			System.out.println("If player Entity Triggered");
		}
		else if (entity instanceof Mob) {
			System.out.println("If Mob Entity Triggered");
			DamageHandler.damageEntity(entity, luminouscorruptionMobDamage, this);
		}

		if (((LivingEntity) entity).hasPotionEffect(PotionEffectType.ABSORPTION)) {
	          absorptionDur = ((LivingEntity) entity).getPotionEffect(PotionEffectType.ABSORPTION).getDuration();
	          absorptionLv = ((LivingEntity) entity).getPotionEffect(PotionEffectType.ABSORPTION).getAmplifier();
	          entity.getWorld().spawnParticle(Particle.SPELL_WITCH, target.getLocation(), 20, (float) Math.random() / 3, 1+(float) Math.random() / 3, (float) Math.random() / 3, 0.10);
		}
		if (((LivingEntity) entity).hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
	         health_boostDur = ((LivingEntity) entity).getPotionEffect(PotionEffectType.HEALTH_BOOST).getDuration();
	         health_boostLv = ((LivingEntity) entity).getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier();
	         entity.getWorld().spawnParticle(Particle.WHITE_ASH, target.getLocation(), 35, (float) Math.random() / 3, 1+(float) Math.random() / 3, (float) Math.random() / 3, 0.10);
		}
			
		if (((LivingEntity) entity).hasPotionEffect(PotionEffectType.REGENERATION)) {
	         regenerationDur = ((LivingEntity) entity).getPotionEffect(PotionEffectType.REGENERATION).getDuration();
	         regenerationLv = ((LivingEntity) entity).getPotionEffect(PotionEffectType.REGENERATION).getAmplifier();
	         entity.getWorld().spawnParticle(Particle.PORTAL, target.getLocation(), 35, (float) Math.random() / 3, 1+(float) Math.random() / 3, (float) Math.random() / 3, 0.10);
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
		
		entity.getWorld().spawnParticle(Particle.END_ROD, target.getLocation(), 35, (float) Math.random() / 3, 1+(float) Math.random() / 3, (float) Math.random() / 3, 0.15);
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ALLAY_HURT, 2, 2);
	}

	@Override
	public String getName() {
		return "LuminousCorruption";
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
