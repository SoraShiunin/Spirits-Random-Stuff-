package me.numin.spirits.ability.api;

import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;

import me.numin.spirits.Spirits;

public abstract class WaterAbility extends com.projectkorra.projectkorra.ability.WaterAbility implements AddonAbility {

    public static final String OFFENSE = "Offense";
    public static final String DEFENSE = "Defense";
    public static final String MOBILITY = "Mobility";
    public static final String UTILITY = "Utility";


    public WaterAbility(Player player) {
        super(player);
    }

    @Override
    public boolean isEnabled() {
        String combo = this instanceof ComboAbility ? ".Combo" : "";
        return Spirits.plugin.getConfig().getBoolean("Abilities.Spirits.Water" + combo + "." + getName() + ".Enabled");
    }

    @Override
    public String getVersion() {
        return this.getElement().getColor().toString() + Spirits.plugin.getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return Spirits.plugin.getConfig().getString("Language.Abilities.Water." + getName() + ".Description");
    }

    @Override
    public String getInstructions() {
        return this.getElement().getColor().toString() +
                Spirits.plugin.getConfig().getString("Language.Abilities." + this.getElement().getName() + "." + getName() + ".Instructions");
    }

    @Override
    public void load() {}
    @Override
    public void stop() {}
}
