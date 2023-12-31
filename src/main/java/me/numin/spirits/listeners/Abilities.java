package me.numin.spirits.listeners;

import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.chiblocking.Paralyze;
import com.projectkorra.projectkorra.chiblocking.QuickStrike;
import com.projectkorra.projectkorra.chiblocking.RapidPunch;
import com.projectkorra.projectkorra.chiblocking.SwiftKick;
import com.projectkorra.projectkorra.chiblocking.passive.ChiPassive;
import com.projectkorra.projectkorra.event.PlayerSwingEvent;
import com.projectkorra.projectkorra.util.DamageHandler;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.ability.api.SpiritAbility;
import me.numin.spirits.ability.dark.Berserker;
import me.numin.spirits.ability.dark.DarkBlast;
import me.numin.spirits.ability.dark.DarkDash;
import me.numin.spirits.ability.dark.DarkSoar;
import me.numin.spirits.ability.light.LightBlast;
import me.numin.spirits.ability.light.LightDash;
import me.numin.spirits.ability.light.LightSoar;
import me.numin.spirits.ability.light.LuminousCorruption;
import me.numin.spirits.ability.spirit.*;
import me.numin.spirits.utilities.TempSpectator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import me.numin.spirits.SpiritElement;

import me.numin.spirits.ability.dark.Intoxicate;
import me.numin.spirits.ability.dark.Shackle;
import me.numin.spirits.ability.spirit.Swap;
import me.numin.spirits.ability.spirit.passive.EnergyVeil;
import me.numin.spirits.ability.dark.Berserker;
import me.numin.spirits.ability.dark.Strike;
import me.numin.spirits.ability.light.Alleviate;
import me.numin.spirits.ability.light.Orb;
import me.numin.spirits.ability.light.Shelter;
import me.numin.spirits.ability.light.Shelter.ShelterType;

import org.spigotmc.event.entity.EntityDismountEvent;


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
    	        System.out.println(playerEXP + " gained " + originalExp + " XP. Modified XP: " + modifiedExp + " Veil Multipler: " + EnergyVeil.veilApply(playerEXP));
 
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

	
	
    @EventHandler
    public void onClick(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        Possess.punchPossessing(event.getPlayer());

        if (event.isCancelled() || bPlayer == null) return;
			
	
        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Agility")) {
            if (bPlayer.isOnCooldown("Dash")) return;
            new Dash(player);

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