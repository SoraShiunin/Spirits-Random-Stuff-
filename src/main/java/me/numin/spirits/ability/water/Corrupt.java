package me.numin.spirits.ability.water;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.airbending.Suffocate;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.MovementHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.SpiritElement;
import me.numin.spirits.Spirits;
import me.numin.spirits.ability.api.WaterAbility;
import net.md_5.bungee.api.ChatColor;

public class Corrupt extends WaterAbility {

    public LivingEntity target;
    @Attribute(Attribute.RANGE)
    private double range;
    public static Set<Integer> heldEntities = new HashSet<Integer>();
    public byte stage = 0;
    public Location travelLoc = null;
    @Attribute(Attribute.DURATION)
    private long duration;
    public double yaw;
    public Random random;
    @Attribute(Attribute.COOLDOWN)
    private long cooldown;
    private boolean hasReached = true;
    private int ticks;
    private int chargeTicks;
    private boolean charged = false;
    private boolean setElement;

    public Corrupt(Player player) {
        super(player);
        if (!bPlayer.canBend(this)) {
            return;
        }
        firstloop: for (int i = 20; i < 100; i++) {
            Location loc = GeneralMethods.getTargetedLocation(player, range);
            for (Entity e : GeneralMethods.getEntitiesAroundPoint(loc, 10)) {
                if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
                    target = (LivingEntity) e;
                    break firstloop;
                }
            }
        }

        if (target == null) {
            return;
        }
        heldEntities.add(target.getEntityId());
        setFields();
        if (isEnabled()) {
            start();
        }
    }

    private void setFields() {
        this.cooldown = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Water.Corrupt.Cooldown");
        this.duration = Spirits.plugin.getConfig().getLong("Abilities.Spirits.Water.Corrupt.Duration");
        this.range = Spirits.plugin.getConfig().getDouble("Abilities.Spirits.Water.Corrupt.Range");
        this.setElement = Spirits.plugin.getConfig().getBoolean("Abilities.Spirits.Water.Corrupt.SetElement");
    }

    public double calculateSize(LivingEntity entity) {
        return (entity.getEyeLocation().distance(entity.getLocation()) / 2 + 0.8D);
    }

    @Override
    public void remove() {
        super.remove();

        if (target != null) {
            heldEntities.remove(target.getEntityId());
        }
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public String getName() {
        return "Corrupt";
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
        return true;
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreCooldowns(this)) {
            remove();
            return;
        }

        if (target == null || target.isDead()) {
            remove();
            return;
        }


        if (!target.getWorld().equals(player.getWorld())) {
            remove();
            return;
        }

        if (target.getLocation().distance(player.getLocation()) > 25) {

            remove();
            return;
        }

        if (System.currentTimeMillis() - getStartTime() > 10000L) {
            MovementHandler mh = new MovementHandler((Player) player, this);
            mh.stop(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "* READY *");
            charged = true;
            createNewSpirals();
        }

        if (System.currentTimeMillis() - getStartTime() > duration) {
            remove();
            bPlayer.addCooldown(this);
            return;
        }

        if (charged) {
            if (!player.isSneaking()) {
                if (target instanceof OfflinePlayer && setElement) {
                    BendingPlayer bPlayer = BendingPlayer.getBendingPlayer((OfflinePlayer) target);
                    if (bPlayer.hasElement(SpiritElement.LIGHT)) {
                        bPlayer.addElement(SpiritElement.DARK);
                        bPlayer.getElements().remove(SpiritElement.LIGHT);
                        GeneralMethods.saveElements(bPlayer);
                        GeneralMethods.removeUnusableAbilities(bPlayer.getName());
                        target.sendMessage(SpiritElement.LIGHT.getColor() + "You are now a" + ChatColor.BOLD + "" + ChatColor.BLUE + " DarkSpirit");
                        ParticleEffect.SPELL_WITCH.display(target.getLocation(), 3, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.0);
                    } else {
                        DamageHandler.damageEntity(target, 7, this);
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 300, 2));
                        ParticleEffect.SPELL_WITCH.display(target.getLocation(), 3, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.0);
                    }
                } else if (target != null) {
                    DamageHandler.damageEntity(target, 7, this);
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 300, 2));
                    ParticleEffect.SPELL_WITCH.display(target.getLocation(), 3, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.0);
                }
            }
        }

        if (stage == 0) {

            if (!player.isSneaking()) {
                bPlayer.addCooldown(this);
                remove();
                return;
            }

            if (travelLoc == null && this.getStartTime() + duration < System.currentTimeMillis()) {
                bPlayer.addCooldown(this);
                travelLoc = player.getEyeLocation();
            } else if (travelLoc == null) {
                ticks++;
                Long chargingTime = System.currentTimeMillis() - getStartTime();
                this.chargeTicks = (int) (chargingTime / 25);
                if (!charged) {
                    createSpirals();
                } else {
                    createNewSpirals();
                }
                //ParticleEffect.MAGIC_CRIT.display(0.3F, 0.3F, 0.3F, 0.1F, 8, target.getLocation().clone().add(0, 0.8, 0), 90);
                //f7f2f6
                for (int i = -180; i < 180; i += 10) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 300, 128));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 300, 128));
                }
                return;
            }
        }
    }

    public void paralyze(Entity entity) {
        if (entity instanceof Creature) {
            ((Creature) entity).setTarget(null);
        }

        if (entity instanceof Player) {
            if (Suffocate.isChannelingSphere((Player) entity)) {
                Suffocate.remove((Player) entity);
            }
        }
        MovementHandler mh = new MovementHandler((LivingEntity) entity, this);
        mh.stop(ChatColor.DARK_PURPLE + "* CORRUPTING *");
    }
    private void createSpirals() {
        Color blue = Color.fromBGR(244, 170, 66);
        DustOptions dustBlue = new DustOptions(blue, 1);

        Color lightBlue = Color.fromBGR(255, 221, 112);
        DustOptions dustLight = new DustOptions(lightBlue, 1);

        if (hasReached) {
            int amount = chargeTicks + 2;
            double maxHeight = 4;
            double distanceFromPlayer = 1.5;

            int angle = 5 * amount + 5 * ticks;
            double x = Math.cos(Math.toRadians(angle)) * distanceFromPlayer;
            double z = Math.sin(Math.toRadians(angle)) * distanceFromPlayer;
            double height = (amount * 0.10) % maxHeight;
            Location displayLoc = target.getLocation().clone().add(x, height, z);

            int angle2 = 5 * amount + 180 + 5 * ticks;
            double x2 = Math.cos(Math.toRadians(angle2)) * distanceFromPlayer;
            double z2 = Math.sin(Math.toRadians(angle2)) * distanceFromPlayer;
            Location displayLoc2 = target.getLocation().clone().add(x2, height, z2);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc, 1, 0, 0, 0, dustBlue);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc2, 1, 0, 0, 0, dustBlue);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc, 1, 0, 0, 0, dustLight);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc2, 1, 0, 0, 0, dustLight);
        }
    }
    private void createNewSpirals() {
        Color purple = Color.fromBGR(193, 44, 176);
        DustOptions dustPurple = new DustOptions(purple, 1);

        Color darkPurple = Color.fromBGR(201, 58, 137);
        DustOptions dustDark = new DustOptions(darkPurple, 1);

        if (hasReached) {
            int amount = chargeTicks + 2;
            double maxHeight = 4;
            double distanceFromPlayer = 1.5;

            int angle = 5 * amount + 5 * ticks;
            double x = Math.cos(Math.toRadians(angle)) * distanceFromPlayer;
            double z = Math.sin(Math.toRadians(angle)) * distanceFromPlayer;
            double height = (amount * 0.10) % maxHeight;
            Location displayLoc = target.getLocation().clone().add(x, height, z);

            int angle2 = 5 * amount + 180 + 5 * ticks;
            double x2 = Math.cos(Math.toRadians(angle2)) * distanceFromPlayer;
            double z2 = Math.sin(Math.toRadians(angle2)) * distanceFromPlayer;
            Location displayLoc2 = target.getLocation().clone().add(x2, height, z2);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc, 1, 0, 0, 0, 0, dustPurple);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc2, 1, 0, 0, 0, 0, dustPurple);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc, 1, 0, 0, 0, 0, dustDark);
            target.getWorld().spawnParticle(Particle.DUST, displayLoc2, 1, 0, 0, 0, 0, dustDark);
        }
    }

    @Override
    public void load() {}

    @Override
    public void stop() {}

    @Override
    public String getAuthor() {
        return "Prride";
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean isHiddenAbility() {
        return true; //TODO Temp for now while we redo this ability
    }
}