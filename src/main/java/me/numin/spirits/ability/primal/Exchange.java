package me.numin.spirits.ability.primal;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.attribute.Attribute;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.SpiritAbility;

public class Exchange extends SpiritAbility {

    //TODO: Update sounds.


    @Attribute(Attribute.COOLDOWN)
    private long cooldown;
    @Attribute(Attribute.DURATION)
    private long duration;
    private long time;
    Random rand = new Random();
    private DustOptions customColor;
    private Location initialLocation;
    private Location initialEyeLocation;
    private BukkitRunnable particleTask;
    Material IteminHand = player.getInventory().getItemInMainHand().getType();

    public Exchange(Player player) {
        super(player);
        setFields();
        time = System.currentTimeMillis();
        initialEyeLocation = player.getEyeLocation();
        start();
        bPlayer.addCooldown(this);

        
    }
    
    

    
    
    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Primal.Exchange.Cooldown");
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
        else if (IteminHand != Material.AIR) {
               if (!player.isSneaking()){
                   exchangeItemForward();
               } 
               else {
                   remove(); 
               }
            
        }
    }

    private void exchangeItemForward() {

        int itemAmount = player.getInventory().getItemInMainHand().getAmount();
        ItemStack itemtest = new ItemStack(Material.GOLD_BLOCK, itemAmount);
    
        if (IteminHand == Material.IRON_ORE){
            player.getInventory().setItemInMainHand(new ItemStack(Material.GOLD_ORE, itemAmount));
        }
        else if (IteminHand == Material.DIAMOND_ORE){
            player.getInventory().setItemInMainHand(new ItemStack(Material.NETHER_STAR, itemAmount));
        }
	}
    
    
    @Override
    public void remove() {
        super.remove();

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
        return "Exchange";
    }

    @Override
    public String getAbilityType() {
        return UTILITY;
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
}