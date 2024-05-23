package me.numin.spirits.ability.api;

import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.Element;

import me.numin.spirits.SpiritElement;

public abstract class PrimalAbility extends SpiritAbility {

    public PrimalAbility(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return SpiritElement.PRIMAL;
    }
}
