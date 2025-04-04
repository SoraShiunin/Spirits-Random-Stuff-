package me.numin.spirits.ability.spirit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.SpiritAbility;
import net.md_5.bungee.api.ChatColor;

public class Calling extends SpiritAbility {

	@Attribute(Attribute.COOLDOWN)
	private long cooldown;
	@Attribute(Attribute.DURATION)
	private long duration;
	@Attribute(Attribute.DAMAGE)
	private int spiritDamage;
	@Attribute(Attribute.RADIUS)
	private int attackRadius;
	private int attackYRadius;
	private int amount;
	private long selfDamage;
	private Entity target;
	public Entity SpiritSummon;
	private long time;
	private double radius = 5;

	static String[] NeutralSpiritNames = {
		"Clowner", "Fighter", "Trickster", "Phantom", "Wraith", "Specter", 
		"Shadow", "Banshee", "Poltergeist", "Ghoul", "Revenant", "Shade", 
		"Apparition", "Haunter", "Spook", "Mischief", "Rogue", "Illusionist"
	};

	Random rand = new Random();
	private Location origin;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	int summon = 0;
	
	public Calling(Player player) {
		super(player);
		time = System.currentTimeMillis();
		if (!this.bPlayer.canBend(this)) {
			return;
		}
		this.target = player; //CHANGE THIS
		if (!(this.target instanceof LivingEntity)) {
			return;
		}
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Calling.Cooldown");
        this.duration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Calling.Duration");
        this.amount = Spirits.plugin.getConfig().getInt("Abilities.Spirits.Neutral.Calling.Amount");
        this.attackRadius = Spirits.plugin.getConfig().getInt("Abilities.Spirits.Neutral.Calling.AttackRadius");
        this.attackYRadius = Spirits.plugin.getConfig().getInt("Abilities.Spirits.Neutral.Calling.AttackYRadius");
        this.selfDamage = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Calling.SelfDamage");
        this.spiritDamage = Spirits.plugin.getConfig().getInt("Abilities.Spirits.Neutral.Calling.SpiritDamage");
		if (GeneralMethods.isRegionProtectedFromBuild(player, "Calling", origin)) {
			return;
		}
		
		if (player.getHealth() >= selfDamage+1 && player.isSneaking()) {
	        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
	        //DamageHandler.damageEntity(player, selfDamage, this);
	        player.setHealth(player.getHealth()-selfDamage);
			bPlayer.addCooldown(this);
			
			this.start();
		}

		
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
			startCallingloop();
		}
		
	}

	private void startCallingloop() {
		if (GeneralMethods.isRegionProtectedFromBuild(player, "Calling", origin)) {
			return;
		}	
		Calling2();	
		
	//Maybe add something similar to awakening/sanctuary attack;
	}
	
	private void Calling2() {
		origin = player.getLocation();
		

		while (summon < amount) {
			Location loc = origin.clone();
			loc.add((rand.nextBoolean() ? 0.3 : -0.3) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 0.3 : -0.3) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 0.3 : -0.3) * rand.nextInt((int) radius));
			
				player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 15, 1));
				ParticleEffect.PORTAL.display(loc, 4, 0.2F, 0.2F, 0.2F, 0.2F);
				
				 //remember, starts at 0, so 2 is 3 etc
				
				SpiritSummon = player.getWorld().spawnEntity(loc, EntityType.VEX);
				SpiritSummon.setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD + NeutralSpiritNames[rand.nextInt(NeutralSpiritNames.length)]);
			    ItemStack mainhandItem = new ItemStack(Material.AIR); //Hand is now poof, make it barrier or torch for funny
			    ((LivingEntity) SpiritSummon).getEquipment().setItemInMainHand(mainhandItem);
			    //((LivingEntity) SpiritSummon).setMaxHealth(player.getMaxHealth()); 
			    //Maybe make spirit health same as player at the time for balancing reasons
			    
			    
				List<Entity> spiritTargets11 = player.getNearbyEntities(attackRadius, attackYRadius, attackRadius);
				for (Entity target15 : spiritTargets11) {
				    if (target15 instanceof LivingEntity) {
				        LivingEntity livingTarget1 = (LivingEntity) target15;
				        if (!livingTarget1.equals(player) && livingTarget1.getType() != EntityType.VEX) {
				            ((Mob) SpiritSummon).setTarget(livingTarget1);
				            //System.out.println("Target13 acquired: " + livingTarget1);
				        }
				    }

				
				for (Entity e : entities) {
					for (Entity entity : GeneralMethods.getEntitiesAroundPoint(e.getLocation(), 1.5)) {
						if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
							if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation()) || ((entity instanceof Player) && Commands.invincible.contains(((Player) entity).getName()))) {
								continue;
							}
							((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 9000, 0));
							((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 9000, spiritDamage));
						}
					}
				}
				
				player.getWorld().playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 0.5F);
				
				ParticleEffect.NAUTILUS.display(loc, 2, 0.2F, 0.2F, 0.2F, 0.2F);
				}
				entities.add(SpiritSummon);
		        Bukkit.getScheduler().runTaskLater(ProjectKorra.plugin, () -> {
					
					
					
					List<Entity> spiritTargets2 = player.getNearbyEntities(attackRadius, attackYRadius, attackRadius);
					for (Entity target13 : spiritTargets2) {
					    if (target13 instanceof LivingEntity) {
					        LivingEntity livingTarget2 = (LivingEntity) target13;
					        if (!livingTarget2.equals(player) && livingTarget2.getType() != EntityType.VEX) {
					            ((Mob) SpiritSummon).setTarget(livingTarget2);
					            //System.out.println("Target13 acquired: " + livingTarget2);
					        }
					    }
					}
		            //System.out.println("Absorption Removed");
		            }, 20L);
		        summon++;
				}
				
	}
	
			

		

	
	@Override
	public void remove() {
		super.remove();

		for (Entity entity : entities) {
			ParticleEffect.ASH.display(entity.getLocation().add(0, 1, 0), 4, 0.2F, 0.2F, 0.2F, 0.2F);
			ParticleEffect.PORTAL.display(entity.getLocation(), 3, 0.2F, 0.2F, 0.2F);
			player.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.2F);
			
			entity.remove();
		}
	}
	
	@Override
	public String getName() {
		return "Calling";
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
		return OFFENSE;
	}
}
