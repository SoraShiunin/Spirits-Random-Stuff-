package me.numin.spirits.ability.spirit;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.attribute.Attribute;

import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.SpiritAbility;

public class Swap extends SpiritAbility {

    //TODO: Maybe smooth out the blast and entity detection logic, outdated.
    //TODO: Remove the checkEntities boolean and add a Entity variable instead.
    //TODO: Update sounds.

    private Entity target;
    private Location location, origin;
    private Vector direction;

    private boolean checkEntities;
    @Attribute(Attribute.RADIUS)
    private double radius;
    private int currPoint;
    @Attribute(Attribute.RANGE)
    private double range;
    @Attribute(Attribute.COOLDOWN)
    private long cooldown;
    private long realStartTime;

    public Swap(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, -1);
        start();
    }

    @Override
    public String getAbilityType() {
        return OFFENSE;
    }

    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Neutral.Swap.Cooldown");
        this.range = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Neutral.Swap.Range");
        this.radius = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Neutral.Swap.Radius");
        this.origin = player.getLocation().clone().add(0, 1, 0);
        this.location = origin.clone();
        this.direction = player.getLocation().getDirection();
        this.checkEntities = true;
    }

    @Override
    public void progress() {

        bind();
    }

    private void bind() {
    	Location playerLoc = player.getLocation();
        if (checkEntities) {
        	blastSpiral(player.getLocation());
            Location blast = location.add(direction.multiply(1).normalize());
            blastSpiral(blast);

            if (origin.distance(blast) > range) {
                remove();
                return;
            }
            for (Entity entity : GeneralMethods.getEntitiesAroundPoint(blast, radius)) {
                if (entity instanceof LivingEntity && !entity.getUniqueId().equals(player.getUniqueId())) {
                    this.realStartTime = System.currentTimeMillis();
                    this.target = entity;
                    checkEntities = false;
                }
            }
        } else {
            if (this.target == null || this.target.isDead() || this.target.getWorld() != player.getWorld()) {
                remove();
                return;
            }
            if (true) {
                target.getWorld().spawnParticle(Particle.CRIT_MAGIC, target.getLocation(), 5, 0, 0, 0, 0.08);
                target.getWorld().playSound(target.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1F, 2F);
            	player.teleport(target.getLocation());
            	target.teleport(playerLoc);
            	holdSpiral(target.getLocation());
            	holdSpiral(player.getLocation());
                remove();
            } 

        }
    }

    private void blastSpiral(Location location) {
        for (int i = 0; i < 6; i++) {
            currPoint += 360 / 200;
            if (currPoint > 360) {
                currPoint = 0;
            }
            double angle = currPoint * Math.PI / 180 * Math.cos(Math.PI);
            double x = 0.04 * (Math.PI * 4 - angle) * Math.cos(angle + i);
            double z = 0.04 * (Math.PI * 4 - angle) * Math.sin(angle + i);
            location.add(x, 0.1F, z);
            player.getWorld().spawnParticle(Particle.CRIT_MAGIC, location, 1, 0, 0, 0, 0);
            location.subtract(x, 0.1F, z);
        }
    }
    private void holdSpiral(Location location) {
        for (int t = 0; t < 2; t++) {
            currPoint += 360 / 30;
            if (currPoint > 360) {
                currPoint = 0;
            }
            double angle2 = currPoint * Math.PI / 180 * Math.cos(Math.PI);
            double x2 = 0.04 * (Math.PI * 5 - angle2) * Math.cos(angle2 + t);
            double z2 = 0.04 * (Math.PI * 5 - angle2) * Math.sin(angle2 + t);
            location.add(x2, 0.1F, z2);
            player.getWorld().spawnParticle(Particle.CRIT_MAGIC, location, 1, 0, 0, 0, 0);
            location.subtract(x2, 0.1F, z2);
        }
    }

    @Override
    public void remove() {
        if (this.target != null) {
            if (this.target instanceof Player) {
            }
            LivingEntity livingTarget = (LivingEntity)this.target;
            // livingTarget.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 7));
        }
        bPlayer.addCooldown(this);
        super.remove();
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
    public double getCollisionRadius() {
        return radius;
    }

    @Override
    public String getName() {
        return "Swap";
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