package me.numin.spirits.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

import me.numin.spirits.SpiritElement;
import me.numin.spirits.Spirits;
import me.numin.spirits.ability.dark.Corruption;
import me.numin.spirits.ability.darkspirit.passive.EtherealBody;
import me.numin.spirits.ability.lightspirit.passive.EphemeralBody;
import me.numin.spirits.ability.primal.CallingRift;
import me.numin.spirits.ability.spirit.Calling;
import me.numin.spirits.ability.spirit.passive.SpiritualBody;

public class Passives implements Listener {

	private final SpiritualBody spiritualBody;
	private final EtherealBody etherealBody;
	private final EphemeralBody ephemeralBody;

    public Passives() {
    	spiritualBody = (SpiritualBody) CoreAbility.getAbility(SpiritualBody.class);
    	etherealBody = (EtherealBody) CoreAbility.getAbility(EtherealBody.class);
    	ephemeralBody = (EphemeralBody) CoreAbility.getAbility(EphemeralBody.class);
    }

    @EventHandler(priority = EventPriority.LOWEST) //HIGH will make sure air and earth passives have already run
    public void onDamageEventTriggered(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
        	if (spiritualBody == null) return;
        	if (etherealBody == null) return;
        	if (ephemeralBody == null) return;

            if (!spiritualBody.isEnabled()) return;

            Player player = (Player) event.getEntity();
            BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
            
    		if (bPlayer == null) {
    			return;
    		}
    		
            if (event.getCause() == DamageCause.FALL && bPlayer.hasElement(SpiritElement.NEUTRAL)
                    && bPlayer.canUsePassive(spiritualBody) && bPlayer.canBendPassive(spiritualBody)) {
                double newDamage = event.getDamage() * SpiritualBody.getFallDamageModifier();
                event.setDamage(newDamage);
                event.setCancelled(newDamage <= 0);
            }
            //bPlayer.hasElement(SpiritElement.DARK && event.getCause() == DamageCause.ENTITY_ATTACK
            else if (event.getCause() == DamageCause.FALL && bPlayer.hasElement(SpiritElement.DARK)
                    && bPlayer.canUsePassive(etherealBody) && bPlayer.canBendPassive(etherealBody)) {
                double newDamage = event.getDamage() * EtherealBody.getFallDamageModifier2();
                event.setDamage(newDamage);
                event.setCancelled(newDamage <= 0);
            }
            
            else if (event.getCause() == DamageCause.FALL && bPlayer.hasElement(SpiritElement.LIGHT)
                    && bPlayer.canUsePassive(ephemeralBody) && bPlayer.canBendPassive(ephemeralBody)) {
                double newDamage = event.getDamage() * EphemeralBody.getFallDamageModifier3();
                event.setDamage(newDamage);
                event.setCancelled(newDamage <= 0);
            }
            
        	if (event.getCause() == DamageCause.ENTITY_ATTACK && bPlayer.hasElement(SpiritElement.DARK)
                    && bPlayer.canUsePassive(etherealBody) && bPlayer.canBendPassive(etherealBody)) {
                double newDamage = event.getDamage() * EtherealBody.getAttackDamageModifier();
                event.setDamage(newDamage);
                event.setCancelled(newDamage <= 0);
            }
        	
        	if (event.getCause() == DamageCause.MAGIC && bPlayer.hasElement(SpiritElement.LIGHT)
                    && bPlayer.canUsePassive(ephemeralBody) && bPlayer.canBendPassive(ephemeralBody)) {
                double newDamage = event.getDamage() * EphemeralBody.getMagicDamageModifier();
                event.setDamage(newDamage);
                event.setCancelled(newDamage <= 0);
            }
        }
    }
    //For Calling Ability and checks for targeting
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player)) {
        	return;
        }
        if (event.getTarget() instanceof Player) {
        	Player player = (Player) event.getTarget();
        	BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
            if (bPlayer == null) {
                // Handle case when BendingPlayer is null
                return;
            }
        	boolean darkallianceenabled = Spirits.plugin.getConfig().getBoolean("Abilities.Spirits.DarkSpirit.Passive.DarkAlliance.Enabled");
        	
        	if (darkallianceenabled) {
        		if (bPlayer.hasElement(SpiritElement.DARK)) {
            		event.setCancelled(true);
                    event.setTarget(null);
            	}
        	}
        	
        	if (bPlayer.hasElement(SpiritElement.DARK) && bPlayer.getBoundAbilityName().equalsIgnoreCase("Corruption") && CoreAbility.hasAbility(player, Corruption.class) || event.getTarget().getType() == EntityType.CAVE_SPIDER) 
        	{
        		event.setCancelled(true);
        		// System.out.println("NULL VEX TARGET" + " " + event.getTarget().getName());
               
        	}
        	//Can prbly delete one of these
        	if (bPlayer.hasElement(SpiritElement.DARK) && bPlayer.getBoundAbilityName().equalsIgnoreCase("Corruption") && event.getEntity().getName().contains("DarkSpirit") && event.getTarget().getName() == player.getName() /*&& event.getTarget().getName() == bPlayer.getName() && bPlayer.getBoundAbility().toString() == "Calling"*/)
        	{
        		if (CoreAbility.hasAbility(player, Corruption.class)) {
            			event.setCancelled(true);
        		}
                    //System.out.println("NULL TARGET" + " " + event.getTarget().getName());
            		//System.out.println("CASTER NAME" + " " + player.getName());
            		
            }
        	if (bPlayer.hasElement(SpiritElement.NEUTRAL) && bPlayer.getBoundAbilityName().equalsIgnoreCase("Calling") && CoreAbility.hasAbility(player, Calling.class) || event.getTarget().getType() == EntityType.VEX) 
        	{
        		event.setCancelled(true);
        		// System.out.println("NULL VEX TARGET" + " " + event.getTarget().getName());
               
        	}
        	
        	if (bPlayer.hasElement(SpiritElement.NEUTRAL) && bPlayer.getBoundAbilityName().equalsIgnoreCase("Calling") && event.getEntity().getName().contains("Spirit") && event.getTarget().getName() == player.getName() /*&& event.getTarget().getName() == bPlayer.getName() && bPlayer.getBoundAbility().toString() == "Calling"*/)
        	{
        		if (CoreAbility.hasAbility(player, Calling.class)) {
            			event.setCancelled(true);
        		}
                    //System.out.println("NULL TARGET" + " " + event.getTarget().getName());
            		//System.out.println("CASTER NAME" + " " + player.getName());
            		
            }
        		
            	
            	if (bPlayer.hasElement(SpiritElement.PRIMAL) && bPlayer.getBoundAbilityName().equalsIgnoreCase("CallingRift") && CoreAbility.hasAbility(player, CallingRift.class) || event.getTarget().getType() == EntityType.VEX) 
            	{
            		event.setCancelled(true);
            		// System.out.println("NULL VEX TARGET" + " " + event.getTarget().getName());
                   
            	}
            	if (bPlayer.hasElement(SpiritElement.PRIMAL) && bPlayer.getBoundAbilityName().equalsIgnoreCase("CallingRift") && event.getEntity().getName().contains("Spirit") && event.getTarget().getName() == player.getName() /*&& event.getTarget().getName() == bPlayer.getName() && bPlayer.getBoundAbility().toString() == "Calling"*/)
            	{
                		
                	if (CoreAbility.hasAbility(player, CallingRift.class)) {
                		event.setCancelled(true);
                	}
                        //System.out.println("NULL TARGET" + " " + event.getTarget().getName());
                		//System.out.println("CASTER NAME" + " " + player.getName());
                		
                	}
        		
        	}
        }
    }
	
