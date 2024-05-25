package me.numin.spirits.ability.api;

import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.Element;

import me.numin.spirits.SpiritElement;

public abstract class LightAbility extends SpiritAbility {

    public LightAbility(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return SpiritElement.LIGHT;
    }
}
