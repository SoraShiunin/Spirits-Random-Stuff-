package me.numin.spirits.ability.light.combo;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;
import net.md_5.bungee.api.ChatColor;

public class Awakening extends LightAbility implements ComboAbility {
	
	private static String path = "Abilities.Spirits.LightSpirit.Combo.Awakening.";
	FileConfiguration config = Spirits.plugin.getConfig();
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<TempBlock> tempBlocks = new ArrayList<TempBlock>();
	
	private long cooldown;
	private double radius;
	private long duration;
	private double damage;
	private double healAmount;
	private boolean enabled;
	private double attackChance;
	private boolean enableParticles;
	
	private long time;
	
	private Location origin;
	private Location location;
	private Vector direction;
	private Entity lightSpirit;
	static String[] lightSpiritNames = {"Aurora", "Celestia", "Dawn", "Eos", "Helios", "Luna", "Phoebe", "Ra", "Sol", "Sun", "Venus", "Zephyr", "Harmony", "Joy", "Bliss", 
	"Grace", "Hope", "Faith", "Charity", "Serenity", "Peace", "Love", "Kindness", "Purity", "Radiance", "Virtue", "Bliss", "Euphoria", "Tranquility", "Compassion", "Gentleness", 
	"Mercy", "Benevolence", "Generosity", "Forgiveness", "Humility", "Patience", "Wisdom", "Courage", "Strength", "Unity", "Friendship", "Honesty", "Integrity", "Loyalty", "Respect",
	 "Dignity", "Empathy"};
	Random rand = new Random();
	
	private Material[] plants = new Material[] {
    		Material.SHORT_GRASS, Material.FERN, Material.TALL_GRASS, Material.LARGE_FERN, Material.DANDELION, Material.POPPY, 
    		Material.OXEYE_DAISY, Material.SUNFLOWER, Material.CACTUS, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, 
    		Material.RED_TULIP, Material.ORANGE_TULIP, Material.PINK_TULIP, Material.WHITE_TULIP, Material.LILAC, Material.ROSE_BUSH, Material.PEONY };

	public Awakening(Player player) {
		super(player);

		if (!bPlayer.canBendIgnoreBinds(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		radius = config.getDouble(path + "Radius");
		duration = config.getLong(path + "Duration");
		healAmount = config.getDouble(path + "HealAmount");
		damage = config.getDouble(path + "Damage");
		attackChance = config.getDouble(path + "AttackChance");
		enableParticles = config.getBoolean(path + "EnableParticles");
		
		time = System.currentTimeMillis();
		origin = player.getLocation();
		
		bPlayer.addCooldown(this);
		
		enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.Disabled");
		
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 1.5F, 1F);
		
		start();
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return origin;
	}

	@Override
	public String getName() {
		return "Awakening";
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration) {
			remove();
			return;
		} else {
			awakenSpirits();
		}
	}
	
	private void awakenSpirits() {
		if (GeneralMethods.isRegionProtectedFromBuild(player, "Awakening", origin)) {
			return;
		}
		
		summonLightSpirits();
		
		changeBlocks();
		
		attack();
	}
	
	private void changeBlocks() {
		for (int i = 1; i < 16; i++) {
			Location loc = origin.clone();
			loc.add((rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius));
			
			Block block = loc.getBlock().getRelative(BlockFace.UP);
			
			if (block.getType() != Material.AIR) {
				if (GeneralMethods.isRegionProtectedFromBuild(this, block.getLocation())) {
					continue;
				}
				
				ParticleEffect.END_ROD.display(origin, 1, radius, 0F, radius, 0.05F);
				
				if (GeneralMethods.isSolid(block)) {
					// final TempBlock temp = new TempBlock(block, Material.BARRIER);
					
					if (enableParticles) {
						ParticleEffect.SPELL_INSTANT.display(block.getLocation().add(0, 1, 0), 3, 0.2F, 0.2F, 0.2F, 0.2F);
					}
					
				//	tempBlocks.add(temp);
				}
				
				for (Material plants : this.plants) {
					if (block.getType() == plants) {
						final TempBlock temp = new TempBlock(block, Material.AIR);
						ParticleEffect.SPELL_INSTANT.display(block.getLocation().add(0, 1, 0), 3, 0.2F, 0.2F, 0.2F, 0.2F);
						
						tempBlocks.add(temp);
					}
				}
			}
		}
	}
	
	private void summonLightSpirits() {
		if (Math.random() < 0.15) {
			Location loc = origin.clone();
			loc.add((rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius));
			
			if (loc.getBlock().getType() == Material.AIR) {
				if (GeneralMethods.isRegionProtectedFromBuild(this, loc)) {
					return;
				}
				if (Math.random() < 0.35) {
					lightSpirit = player.getWorld().spawnEntity(loc, EntityType.ALLAY);
				}
				else if (Math.random() < 0.10) {
					lightSpirit = player.getWorld().spawnEntity(loc, EntityType.FOX);
				}
				else if (Math.random() < 0.10) {
					lightSpirit = player.getWorld().spawnEntity(loc, EntityType.RABBIT);
				}
				else if (Math.random() < 0.10) {
					lightSpirit = player.getWorld().spawnEntity(loc, EntityType.FROG);
				}
				else if (Math.random() < 0.05) {
					lightSpirit = player.getWorld().spawnEntity(loc, EntityType.SHEEP);
				}
				else if (Math.random() < 0.05) {
					lightSpirit = player.getWorld().spawnEntity(loc, EntityType.PARROT);
				}
				else {
					lightSpirit = player.getWorld().spawnEntity(loc, EntityType.ALLAY);
				}
				
				lightSpirit.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + lightSpiritNames[rand.nextInt(lightSpiritNames.length)]);
				lightSpirit.setInvulnerable(true);
				lightSpirit.setGlowing(true);
				
				player.getWorld().playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 2F);
				
				ParticleEffect.ENCHANTMENT_TABLE.display(loc, 10, 0.2F, 0.2F, 0.2F, 0.2F);
				
				entities.add(lightSpirit);
			}
		}
	}
	
	private void attack() {
		if (entities.contains(lightSpirit)) {
			for (Entity lightSpirits : entities) {
				if (Math.random() < attackChance) {
					
					for (Entity entity : GeneralMethods.getEntitiesAroundPoint(origin, radius)) {
						if (entity.getUniqueId() != player.getUniqueId()) {
							if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation()) || ((entity instanceof Player) && Commands.invincible.contains(((Player) entity).getName()))) {
								continue;
							}
							
							if (entity instanceof Player) {
								Player pent = (Player) entity;
								BendingPlayer bEntity = BendingPlayer.getBendingPlayer(pent);
								
							/*	if (bEntity.hasElement(SpiritElement.DARK)) {
									beam(lightSpirits, entity);
								}*/
								
							} 
							else if (entity instanceof Allay || entity instanceof Fox || entity instanceof Rabbit || entity instanceof Frog || entity instanceof Sheep || entity instanceof Parrot) {
								
							}
							else if (entity instanceof LivingEntity)/*|| entity instanceof Allay || entity instanceof Fox || entity instanceof Rabbit || entity instanceof Frog || entity instanceof Sheep || entity instanceof Parrot*/ {
								beam(lightSpirits, entity);
								if (Math.random() < attackChance) {
									if (entity instanceof Monster) {
										beam(lightSpirits, entity);
									}
									
								}
							} 
						}
					}
				}
			}
		}
	}
	
	private void beam(Entity lightSpirits, Entity entity) {
		location = lightSpirits.getLocation().add(0, 1, 0);
		
		direction = GeneralMethods.getDirection(location, entity.getLocation().add(0, 1, 0));
		
		double distance = location.distance(entity.getLocation().add(0, 1, 0));
		
		for (double i = 0; i < distance; i++) {
			location = location.add(direction.multiply(0.005).normalize());
			
			ParticleEffect.CRIT_MAGIC.display(location, 5);
			
			location.getWorld().playSound(location, Sound.BLOCK_BEACON_DEACTIVATE, 0.5F, 1F);
			
			for (Entity pEntity : GeneralMethods.getEntitiesAroundPoint(location, 1.5)) {
				if (pEntity.getUniqueId() != player.getUniqueId()) {
					if (GeneralMethods.isRegionProtectedFromBuild(this, pEntity.getLocation()) || ((pEntity instanceof Player) && Commands.invincible.contains(((Player) pEntity).getName()))) {
						continue;
					}
					
					if (pEntity instanceof Player) {
						Player pent = (Player) pEntity;
						BendingPlayer bEntity = BendingPlayer.getBendingPlayer(pent);
						
					/*	if (bEntity.hasElement(SpiritElement.DARK)) {
							DamageHandler.damageEntity(pEntity, damage, this);
						} */
					}
					
				if (pEntity instanceof LivingEntity && !(pEntity instanceof Allay) && !(pEntity instanceof Fox) && !(pEntity instanceof Rabbit) && !(pEntity instanceof Frog) && !(pEntity instanceof Sheep && !(pEntity instanceof Parrot))) {
						DamageHandler.damageEntity(pEntity, damage, this);
						if (pEntity instanceof Monster) {
							DamageHandler.damageEntity(pEntity, 1+damage*3, this);
						} 
					} 
				} else {
						 //Added this to damage anything
						
					/*if (pEntity.getUniqueId() == player.getUniqueId()) {
						ParticleEffect.HEART.display(player.getLocation().add(0, 2, 0), 1);
						
						double health = player.getHealth() + healAmount;
						if (health > player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
				            health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				        }
						for (double j = 0; j < 1; j += 0.5) {
							player.setHealth(health);
						}
					}*/
				}
			}
		}
	}
	
	@Override
	public void remove() {
		super.remove();
		
		for (TempBlock temp : tempBlocks) {
			if(TempBlock.isTempBlock(temp.getBlock())) {
				temp.revertBlock();
			}
		}
		
		for (Entity entity : entities) {
			ParticleEffect.SPELL_INSTANT.display(entity.getLocation().add(0, 1, 0), 10, 0.2F, 0.2F, 0.2F, 0.2F);
			ParticleEffect.ENCHANTMENT_TABLE.display(entity.getLocation(), 20, 0.2F, 0.2F, 0.2F, 0.2F);
			player.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
			entity.remove();
		}
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new Awakening(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Alleviate", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Alleviate", ClickType.SHIFT_UP));
		combo.add(new AbilityInformation("Alleviate", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Alleviate", ClickType.SHIFT_UP));
		combo.add(new AbilityInformation("Alleviate", ClickType.LEFT_CLICK));
		return combo;
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
