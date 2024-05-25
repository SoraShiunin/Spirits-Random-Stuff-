package me.numin.spirits.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.spigotmc.event.entity.EntityDismountEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.event.PlayerSwingEvent;
import com.projectkorra.projectkorra.util.DamageHandler;

import me.numin.spirits.SpiritElement;
import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;
import me.numin.spirits.ability.dark.Berserker;
import me.numin.spirits.ability.dark.Corruption;
import me.numin.spirits.ability.dark.DarkBeam;
import me.numin.spirits.ability.dark.DarkBeamCharge;
import me.numin.spirits.ability.dark.DarkBlast;
import me.numin.spirits.ability.dark.DarkDash;
import me.numin.spirits.ability.dark.DarkSoar;
import me.numin.spirits.ability.dark.Intoxicate;
import me.numin.spirits.ability.dark.Onslaught;
import me.numin.spirits.ability.dark.Shackle;
import me.numin.spirits.ability.dark.Shadow;
import me.numin.spirits.ability.dark.Strike;
import me.numin.spirits.ability.darkspirit.passive.SinisterAura;
import me.numin.spirits.ability.light.Alleviate;
import me.numin.spirits.ability.light.Enlightenment;
import me.numin.spirits.ability.light.LightBeam;
import me.numin.spirits.ability.light.LightBeamCharge;
import me.numin.spirits.ability.light.LightBlast;
import me.numin.spirits.ability.light.LightDash;
import me.numin.spirits.ability.light.LightShot;
import me.numin.spirits.ability.light.LightSoar;
import me.numin.spirits.ability.light.LuminousCorruption;
import me.numin.spirits.ability.light.Orb;
import me.numin.spirits.ability.light.Safeguard;
import me.numin.spirits.ability.light.Shelter;
import me.numin.spirits.ability.light.Shelter.ShelterType;
import me.numin.spirits.ability.light.Wish;
import me.numin.spirits.ability.lightspirit.passive.Afterglow;
import me.numin.spirits.ability.lightspirit.passive.WishfulThinking;
import me.numin.spirits.ability.primal.CallingRift;
import me.numin.spirits.ability.spirit.Calling;
import me.numin.spirits.ability.spirit.Dash;
import me.numin.spirits.ability.spirit.Float;
import me.numin.spirits.ability.spirit.Paint;
import me.numin.spirits.ability.spirit.Possess;
import me.numin.spirits.ability.spirit.Soar;
import me.numin.spirits.ability.spirit.SpiritBlast;
import me.numin.spirits.ability.spirit.SpiritStruck;
import me.numin.spirits.ability.spirit.Swap;
import me.numin.spirits.ability.spirit.Vanish;
import me.numin.spirits.ability.spirit.Vortex;
import me.numin.spirits.ability.spirit.combo.Skyrocket;
import me.numin.spirits.ability.spirit.passive.EnergyVeil;
import me.numin.spirits.utilities.RandomChance;
import me.numin.spirits.utilities.TempSpectator;


public class Abilities implements Listener {
	
	//TEST
    @EventHandler
    public void EnergyVeilxpGain(PlayerExpChangeEvent event) {
        
    	Player playerEXP = event.getPlayer();
    	

 
		final BendingPlayer sourceBPlayer = BendingPlayer.getBendingPlayer(playerEXP);
		if (sourceBPlayer == null) {
			return;
		}
    	if (sourceBPlayer.hasElement(SpiritElement.NEUTRAL)) {
    		if (sourceBPlayer.canBendPassive(CoreAbility.getAbility(EnergyVeil.class))){
    			
    	        
    	        double originalExp = event.getAmount();
    	    	EnergyVeil.veilApply(event.getPlayer());
    	    	EnergyVeil.veilApply2(event.getAmount());
    			int modifiedExp = (int) Math.round(originalExp * EnergyVeil.veilApply(playerEXP));
    			
    	        event.setAmount(modifiedExp);
    	        //System.out.println(playerEXP + " gained " + originalExp + " XP. Modified XP: " + modifiedExp + " Veil Multipler: " + EnergyVeil.veilApply(playerEXP));
 
    		}
    	}
    }
	
	//TEST
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerDamageByPlayer(final EntityDamageByEntityEvent e) { //onPlayerDamageByPlayer is a lie
		final Entity source = e.getDamager();
		final Entity entity = e.getEntity();
		if (entity instanceof LivingEntity && DamageHandler.isReceivingDamage((LivingEntity) e.getEntity())) return; //This is what stops it from being activated from range with eg: fireblast
		if (source instanceof Player) { // This is the player hitting someone.
		final Player sourcePlayer = (Player) source;
		final BendingPlayer sourceBPlayer = BendingPlayer.getBendingPlayer(sourcePlayer);
		if (sourceBPlayer == null) {
			return;
		}
		final Ability boundAbil = sourceBPlayer.getBoundAbility();
		if (e.getDamage() == 0) return;
		if (sourceBPlayer.getBoundAbility() != null) {
			if (!sourceBPlayer.isOnCooldown(boundAbil)) {
				if (sourceBPlayer.canBendPassive(sourceBPlayer.getBoundAbility())) {
					if (e.getCause() == DamageCause.ENTITY_ATTACK) {
						if (sourceBPlayer.getBoundAbility() instanceof SpiritAbility || sourceBPlayer.getBoundAbility() instanceof LightAbility || sourceBPlayer.getBoundAbility() instanceof DarkAbility) {
							if (sourceBPlayer.canCurrentlyBendWithWeapons()) {
								//if (sourceBPlayer.isElementToggled(Element.LightSpirit)) {
									if (boundAbil.equals(CoreAbility.getAbility(SpiritStruck.class))) {
										new SpiritStruck(sourcePlayer, entity);
									} else if (boundAbil.equals(CoreAbility.getAbility(LuminousCorruption.class))) {
										new LuminousCorruption(sourcePlayer, entity);
									}
							
							}
						
							}
						}
					}
				}
		} else {
			/*if (e.getCause() == DamageCause.ENTITY_ATTACK) {
				if (sourceBPlayer.canCurrentlyBendWithWeapons()) {
					if (sourceBPlayer.isElementToggled(Element.CHI)) {
						if (entity instanceof Player) {
							final Player targetPlayer = (Player) entity;
							if (ChiPassive.willChiBlock(sourcePlayer, targetPlayer)) {
								ChiPassive.blockChi(targetPlayer);
							}
						}
					}
				}
			}*/
		}

		if (e.getCause() == DamageCause.ENTITY_ATTACK) {
			PlayerSwingEvent swingEvent = new PlayerSwingEvent((Player)e.getDamager()); //Allow addons to handle a swing without
			Bukkit.getPluginManager().callEvent(swingEvent);                       		//needing to repeat the checks above themselves
			if (swingEvent.isCancelled()) {
				e.setCancelled(true);
				return;
			}
		}
	}
}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDamage(EntityDamageEvent event) {
		
		double darkChance = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.Chance");
		double lightChance = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.LightSpirit.Passive.WishfulThinking.Chance");
		//System.out.println("Triggered EntityDamageEvent");
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			
			if (bPlayer == null) {
				return;
			}
			
			if (event.getDamage() == 0) {
				event.setCancelled(true);
				return;
			}
			
			if (event.getDamage() >= 1) {
				
	
					if (event.getEntity() instanceof Player) {
						if (bPlayer.hasElement(SpiritElement.DARK) && bPlayer.canUsePassive(CoreAbility.getAbility(SinisterAura.class))) {
							if (new RandomChance(darkChance).chanceReached()) {
								System.out.println("Triggered SinisterAura");
								new SinisterAura(player);
							
						}
					}	
				}
				
		
					if (event.getEntity() instanceof Player) {
						if (bPlayer.hasElement(SpiritElement.LIGHT) && bPlayer.canUsePassive(CoreAbility.getAbility(WishfulThinking.class))) {
							if (new RandomChance(lightChance).chanceReached()) {
								//System.out.println("Triggered WishFul");
								new WishfulThinking(player);
							
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDeath(EntityDeathEvent event) {
		System.out.println("Triggered Death");
		if (!(event.getEntity() instanceof Player)) {
        	return;
        }
		
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			System.out.println("Triggered Death 2");
			if (bPlayer == null) {
				return;
			}
			
			CoreAbility afterglow = CoreAbility.getAbility(Afterglow.class);
			
			Location location = player.getLocation();
			
			if (bPlayer.hasElement(SpiritElement.LIGHT) && bPlayer.canUsePassive(afterglow)) {
				System.out.println("Triggered Afterglow");
				new Afterglow(player, location);	
			}
		}
	}

	@EventHandler
	public void removeBlocks(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.FALLING_BLOCK) {
			if(Skyrocket.fallingBlocks.remove(event.getEntity())){
				event.setCancelled(true);
			}
		}
	}
    @EventHandler
    public void onClick(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        Possess.punchPossessing(event.getPlayer());

        if (event.isCancelled() || bPlayer == null) return;
			
	
        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Agility")) {
            if (bPlayer.isOnCooldown("Dash")) return;
            new Dash(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Vortex")) {
            if (bPlayer.isOnCooldown("Vortex")) return;
            new Vortex(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightAgility")) {
            if (bPlayer.isOnCooldown("LightDash")) return;
            new LightDash(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Berserker")) {
            new Berserker(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("DarkAgility")) {
            if (bPlayer.isOnCooldown("DarkDash")) return;
            new DarkDash(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Shackle")) {
            new Shackle(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Swap")) {
            new Swap(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightShot")) {
            new LightShot(player);
                        
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Shelter") && !CoreAbility.hasAbility(player, Shelter.class)) {
            new Shelter(player, ShelterType.CLICK);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Strike")) {
            new Strike(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightBlast") && !CoreAbility.hasAbility(player, LightBlast.class)) {
            new LightBlast(player, LightBlast.LightBlastType.CLICK);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("DarkBlast") && !CoreAbility.hasAbility(player, DarkBlast.class)) {
            new DarkBlast(player, DarkBlast.DarkBlastType.CLICK);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("SpiritBlast")) {
            new SpiritBlast(player);
            
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("CallingRift")) {
            new CallingRift(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Paint")) {
            new Paint(player);

        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (event.isCancelled() || bPlayer == null) return;

        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Alleviate")) {
            new Alleviate(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Intoxicate")) {
            new Intoxicate(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Agility") && event.isSneaking()) {
            if (bPlayer.isOnCooldown("Soar")) return;
            new Soar(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightAgility") && event.isSneaking()) {
            if (bPlayer.isOnCooldown("LightSoar")) return;
            new LightSoar(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("DarkAgility") && event.isSneaking()) {
            if (bPlayer.isOnCooldown("DarkSoar")) return;
            new DarkSoar(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Possess")) {
            if (event.isSneaking()) {
                if (!CoreAbility.hasAbility(player, Possess.class)) new Possess(player);
                else if (Possess.stopSpectating(event.getPlayer())) event.setCancelled(true);
            }
        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Shelter") && !CoreAbility.hasAbility(player, Shelter.class)) {
            new Shelter(player, ShelterType.SHIFT);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Vanish") && event.isSneaking()) {
            new Vanish(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Orb")) {
            new Orb(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Calling")) {
            new Calling(player);

        } else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Wish") && bPlayer.canBend(CoreAbility.getAbility(Wish.class)) && !CoreAbility.hasAbility(player, Wish.class)) {
			new Wish(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Enlightenment") && bPlayer.canBend(CoreAbility.getAbility(Enlightenment.class)) && !CoreAbility.hasAbility(player, Enlightenment.class)) {
			new Enlightenment(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Corruption")) {
			new Corruption(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightBeam") && bPlayer.canBendIgnoreBinds(CoreAbility.getAbility(LightBeamCharge.class)) && !CoreAbility.hasAbility(player, LightBeamCharge.class)) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.3F, 0.5F);
			new LightBeamCharge(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("DarkBeam") && bPlayer.canBendIgnoreBinds(CoreAbility.getAbility(DarkBeamCharge.class)) && !CoreAbility.hasAbility(player, DarkBeamCharge.class)) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.3F, 0.5F);
			//System.out.println("DarkBeamCharge Triggered");
			new DarkBeamCharge(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Safeguard") && bPlayer.canBend(CoreAbility.getAbility(Safeguard.class)) && !CoreAbility.hasAbility(player, Safeguard.class)) {
			new Safeguard(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Shadow") && bPlayer.canBend(CoreAbility.getAbility(Shadow.class)) && !CoreAbility.hasAbility(player, Shadow.class)) {
			new Shadow(player);
			
		}//boundAbil.equals(CoreAbility.getAbility(SpiritStruck.class))
    }
    
	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(final PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND) {
			return;
		}
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) {
			return;
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.isCancelled()){
			return;
		}

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (bPlayer == null) {
			return;
		}
		final CoreAbility coreAbil = bPlayer.getBoundAbility();
		final String abil = bPlayer.getBoundAbilityName();

		if (coreAbil == null) {
			return;
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightBeam") && bPlayer.canBend(CoreAbility.getAbility(LightBeam.class)) && !CoreAbility.hasAbility(player, LightBeam.class)) {
			LightBeamCharge lightBeam = CoreAbility.getAbility(player, LightBeamCharge.class);
			if (lightBeam != null && lightBeam.charged) {
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.4F, 1.5F);
				new LightBeam(player);
			}
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("DarkBeam") && bPlayer.canBend(CoreAbility.getAbility(DarkBeam.class)) && !CoreAbility.hasAbility(player, DarkBeam.class)) {
			DarkBeamCharge darkBeam = CoreAbility.getAbility(player, DarkBeamCharge.class);
			if (darkBeam != null && darkBeam.charged) {
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.4F, 0.5F);
				//System.out.println("DarkBeam Triggered");
				new DarkBeam(player);
			}
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Float") && bPlayer.canBend(CoreAbility.getAbility(Float.class)) && !CoreAbility.hasAbility(player, Float.class)) {
			new Float(player);

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Onslaught") && bPlayer.canBend(CoreAbility.getAbility(Onslaught.class)) && !CoreAbility.hasAbility(player, Onslaught.class)) {
			new Onslaught(player);
			
		}
	}
	
	
    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (Possess.getPossessed(event.getEntity()) != null) {
            Possess possess = Possess.getPossessed(event.getEntity());
            possess.remove();
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {

        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null) {
            return;
        }
        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Vanish")
                || bPlayer.getBoundAbilityName().equalsIgnoreCase("Possess")) {
            if (event.getCause() == TeleportCause.SPECTATE) {
                event.setCancelled(true);
            }
        }
    }
    
//WIP
/*	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteraction(final PlayerInteractEvent event) {
	    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
	        return;
	    }
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        Possess.punchPossessing(event.getPlayer());

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }
        Material clickedMaterial = clickedBlock.getType();
        if (clickedMaterial != null) {
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Paint") && event.getHand() == EquipmentSlot.HAND) {
                int color = PaintChangeColor.getColor(clickedMaterial);
                System.out.println("Color: " + color);
                new PaintChangeColor(player);
            }
        }
        
	}*/

			
			
    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        if (event.getDismounted() instanceof Player) {
            //event.getDismounted().sendMessage("Worked");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        TempSpectator.destroy(event.getPlayer());
    }
}