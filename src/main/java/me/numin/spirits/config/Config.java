package me.numin.spirits.config;

import me.numin.spirits.SpiritElement;
import org.bukkit.configuration.file.FileConfiguration;

import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.numin.spirits.Spirits;

public class Config {

    private static Spirits plugin;

    public Config(Spirits plugin) {
        Config.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = Spirits.plugin.getConfig();
        FileConfiguration language = ConfigManager.languageConfig.get();

        //Rank configuration
        language.addDefault("Chat.Colors.Spirit", SpiritElement.NEUTRAL.getDefaultColor().getName());
        language.addDefault("Chat.Colors.SpiritSub", "DARK_PURPLE");
        language.addDefault("Chat.Colors.LightSpirit", SpiritElement.LIGHT.getDefaultColor().getName());
        language.addDefault("Chat.Colors.LightSpiritSub", "WHITE");
        language.addDefault("Chat.Colors.DarkSpirit", SpiritElement.DARK.getDefaultColor().getName());
        language.addDefault("Chat.Colors.DarkSpiritSub", "DARK_GRAY");
        language.addDefault("Chat.Prefixes.Spirit", "[Spirit]");
        language.addDefault("Chat.Prefixes.LightSpirit", "[LightSpirit]");
        language.addDefault("Chat.Prefixes.DarkSpirit", "[DarkSpirit]");

        //Descriptions & Instructions
        config.addDefault("Language.Abilities.Spirit.Agility.Description", "This ability offers you 2 modes of mobility. The first being the ability to dash forward very quickly. The second being the ability to soar through the skies as if gravity is non-existant.");
        config.addDefault("Language.Abilities.Spirit.Agility.Instructions", "Left-Click: Dash ⏐ Hold shift: Soar");
        
        config.addDefault("Language.Abilities.Spirit.Swap.Description", "Swap places with the targets hit");
        config.addDefault("Language.Abilities.Spirit.Swap.Instructions", "Left-Click: Blast");
        
        config.addDefault("Language.Abilities.Spirit.SpiritStruck.Description", "Temporarily disturb the targets spirit, stunning their body. However this takes a toll on you, you will take damage.");
        config.addDefault("Language.Abilities.Spirit.SpiritStruck.Instructions", "Punch someone");
        
        config.addDefault("Language.Abilities.LightSpirit.LightAgility.Description", "Just like a Spirits Agility, except... the landing is much rougher and you can't really keep it as long.");
        config.addDefault("Language.Abilities.LightSpirit.LightAgility.Instructions", "Left-Click: Dash ⏐ Hold shift: Soar");
 
        config.addDefault("Language.Abilities.LightSpirit.LuminousCorruption.Description", "Speed up and Invert the natural healing component of a creature by touching them. Inflict Hunger and Damage based on saturation levels to a Player . Deals a base amount to creatures. Converts Absorption, Health Boost, Regeneration effects into Wither.");
        config.addDefault("Language.Abilities.LightSpirit.LuminousCorruption.Instructions", "Punch someone");
        
        config.addDefault("Language.Abilities.DarkSpirit.DarkAgility.Description", "You gave in to the earthly tethers, you are not as free but your Dash is strong");
        config.addDefault("Language.Abilities.DarkSpirit.DarkAgility.Instructions", "Left-Click: Dash ⏐ Hold shift: Soar");

        config.addDefault("Language.Abilities.DarkSpirit.Berserker.Description", "Sacrifice your spirit in exchange of a massive power surge. You need atleast half health to activate this ability and you will lose most of it, power depends on life sacrificed.");
        config.addDefault("Language.Abilities.DarkSpirit.Berserker.Instructions", "Left-Click to Activate");
        
        config.addDefault("Language.Abilities.Spirit.Passive.SpiritualBody.Description", "Spirits do not have a physical form. As such, they are immune to all forms of kinetic damage like fall damage");
        config.addDefault("Language.Abilities.Spirit.Passive.SpiritualBody.Instructions", "Spirits are passively immune to fall damage");
        
        config.addDefault("Language.Abilities.Spirit.Passive.EnergyVeil.Description", "Spirits naturally draw more energy to them and use it more efficiently.");
        config.addDefault("Language.Abilities.Spirit.Passive.EnergyVeil.Instructions", "Spirits gain a boosted amount of XP based on their current Health. The healthier you are, the more you get.");

        config.addDefault("Language.Abilities.Spirit.Combo.Levitation.Description", "Spirits lack any earthly tethers which allow themselves to detach and enter the void, empty and become wind. Using this combo takes a toll on your essence and may weaken your mobility for a time.");
        config.addDefault("Language.Abilities.Spirit.Combo.Levitation.Instructions", "Agility (Left-click) > Agility (Hold shift) > Vanish (Left-click)");

        config.addDefault("Language.Abilities.Spirit.Possess.Description", "Allows the spirit to possess the body of a human. Successful possession will slow the target and damage them, but if the target fights back, they can break possession and harm the possessor");
        config.addDefault("Language.Abilities.Spirit.Possess.Instructions", "Tap sneak and ram into a player/mob");
        config.addDefault("Language.Abilities.Spirit.Possess.Possessed", "&9** Possessed **");
        config.addDefault("Language.Abilities.Spirit.Possess.PossessionEnd", "&9** Possession Ended **");
        config.addDefault("Language.Abilities.Spirit.Possess.PossessionBreak", "&9** Broken Possession **");
        config.addDefault("Language.Abilities.Spirit.Possess.Durability", "&9Durability: {durability}");
        config.addDefault("Language.Abilities.Spirit.Possess.DurabilityChar", "\u2B1B");
        language.addDefault("Abilities.Spirit.Possess.DeathMessage", "{victim} succumbed to {attacker}'s {ability}");
        language.addDefault("Abilities.Spirit.PossessRecoil.DeathMessage", "{victim} failed to possess {attacker}");

        /*config.addDefault("Language.Abilities.Spirit.SpiritBlast.Description", "Release multiple blasts of spirit energy in quick succession that damages all enemies they come across");
        config.addDefault("Language.Abilities.Spirit.SpiritBlast.Instructions", "Left-Click (Multiple): Release Spirit Blast | Tap Sneak: Redirect");
        language.addDefault("Abilities.Spirit.SpiritBlast.DeathMessage", "{victim} was torn apart by {attacker}'s {ability}");
*/

        config.addDefault("Language.Abilities.Spirit.Combo.Phase.Description", "This advanced combo allows a Spirit to dematerialize into a state where they can walk through walls and fly around a certain radius. They are able to do this because of their unqiue molecular makeup not seen in any other being!");
        config.addDefault("Language.Abilities.Spirit.Combo.Phase.Instructions", "Vanish (Left-click 2x) > Possess (Left Click)");

        config.addDefault("Language.Abilities.Spirit.Vanish.Description", "Spirits are often seen disappearing into thin air and then reappearing somewhere different. With this ability, you can harness that power as well! However, there is a certain duration you are able to vanish for an a radius of how far away from your original location you're allowed to get!");
        config.addDefault("Language.Abilities.Spirit.Vanish.Instructions", "Hold shift: Disappear ⏐ Release shift: Reappear");

        config.addDefault("Language.Abilities.LightSpirit.Alleviate.Description", "The healing ability for LightSpirits, this allows you to heal yourself and others! When healing, whoever is being healed will be removed of ANY negative potion effects aswell as recieve regeneration for a period of time.");
        config.addDefault("Language.Abilities.LightSpirit.Alleviate.Instructions", "Hold Shift while lookat at a target: Heal them ⏐ Hold Shift while looking away: Heal yourself.");

        /*config.addDefault("Language.Abilities.LightSpirit.LightBlast.Description", "Shoot a radiating blast of light towards your opponent!");
        config.addDefault("Language.Abilities.LightSpirit.LightBlast.Instructions", "Left-click to shoot the blast ⏐ Tap-shift to select a target then left-click to shoot a different kind of blast.");
*/
        config.addDefault("Language.Abilities.LightSpirit.Combo.Rejuvenate.Description", "After executing the combo sequence, you will mark the ground with positively charged spiritual energy for a duration of time. Entities can come to this location to heal themselves, but dark creatures must beware!");
        config.addDefault("Language.Abilities.LightSpirit.Combo.Rejuvenate.Instructions", "Alleviate (Left Click) > Alleviate (Left Click) > Alleviate (Sneak)");

        config.addDefault("Language.Abilities.LightSpirit.Combo.RadiantCorruption.Description", "Effects similar to LuminousCorruption applied as an Area of Effect.");
        config.addDefault("Language.Abilities.LightSpirit.Combo.RadiantCorruption.Instructions", "LuminousCorruption (Left Click) > LuminousCorruption (Left Click) > LuminousCorruption (Sneak)");

        config.addDefault("Language.Abilities.LightSpirit.Orb.Description", "Plant an orb of positive energy on the ground which awaits for oncoming entities. If the orb detects something moving past it, it'll expand and harm anyone in its wake! More features to come...");
        config.addDefault("Language.Abilities.LightSpirit.Orb.Instructions", "Hold shift until you see particles. Release shift while looking at an area nearby on the ground to plant it there.");

        config.addDefault("Language.Abilities.DarkSpirit.Intoxicate.Description", "Sacrifice some of your energy to pour a bit of chaos into the souls of your nearby enemies by taking away their positive potion effects and adding negative ones. Then watch as it destroys them from the inside out! The great spirit Vaatu was known to have this influence over other unbalanced Spirits.");
        config.addDefault("Language.Abilities.DarkSpirit.Intoxicate.Instructions", "Hold shift");

        config.addDefault("Language.Abilities.LightSpirit.Shelter.Description", "A very useful tactic when group battling, a light spirit can temporarily shield a friend or even a foe from incoming enemies. Additionally, they have the options to shield themselves!");
        config.addDefault("Language.Abilities.LightSpirit.Shelter.Instructions", "Left click: Shield others ⏐ Hold shift: Shield yourself.");

        /*config.addDefault("Language.Abilities.DarkSpirit.DarkBlast.Description", "A basic offensive ability for DarkSpirits. You have the choice of shooting a basic blast for dealing quick damage. Or you can select a target and shoot a slower, powerful, homing blast. The blasts can be dodged and obstructed so be strategic!");
        config.addDefault("Language.Abilities.DarkSpirit.DarkBlast.Instructions", "Left click: Quick blast ⏐ Tap-shift while looking at an entity: Select entity > Hold shift: Move the homing blast.");
*/
        config.addDefault("Language.Abilities.DarkSpirit.Shackle.Description", "With this technique a DarkSpirit is able to temporarily trap an anyone dead in their tracks, even if you can't see them! Useful for a quick get away...");
        config.addDefault("Language.Abilities.DarkSpirit.Shackle.Instructions", "Left click");
        
        config.addDefault("Language.Abilities.DarkSpirit.Combo.Infest.Description", "After executing the combo sequence, you will mark the ground with negatively charged spiritual energy for a duration of time. Monsters can come to this location for strength, but any other entities must beware!");
        config.addDefault("Language.Abilities.DarkSpirit.Combo.Infest.Instructions", "Intoxicate (Left Click) > Intoxicate (Left Click) > Intoxicate (Sneak)");

        config.addDefault("Language.Abilities.DarkSpirit.Strike.Description", "The most basic ability of an aggressive, unbalanced Spirit is to rush towards their enemy and try to bite them in 1 swift motion. When you activate this ability, you'll see your target zone. If your target zone gets in range of another entity, you'll be rushed over to them an deal damage.");
        config.addDefault("Language.Abilities.DarkSpirit.Strike.Instructions", "Left-Click to trigger target spectacle");
        config.addDefault("Language.Errors.ChooseSpirit", "You cannot choose the Spirit element on its own! Please select a Dark or Light Spirit instead!");

        //Ability configuration
        config.addDefault("Abilities.Spirits.Neutral.Agility.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Agility.Dash.Cooldown", 2000);
        config.addDefault("Abilities.Spirits.Neutral.Agility.Dash.Distance", 2);
        config.addDefault("Abilities.Spirits.Neutral.Agility.Soar.Cooldown", 500);
        config.addDefault("Abilities.Spirits.Neutral.Agility.Soar.Duration", 20000);
        config.addDefault("Abilities.Spirits.Neutral.Agility.Soar.Speed", 0.8);

        config.addDefault("Abilities.Spirits.Neutral.SpiritStruck.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.SpiritStruck.Cooldown", 3500);
        config.addDefault("Abilities.Spirits.Neutral.SpiritStruck.Duration", 1000);   
        
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.Cooldown", 2500);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.Duration", 8);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.luminouscorruptionMobDamage", 10);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.playerDamage", 3);
        
        config.addDefault("Abilities.Spirits.LightSpirit.LightAgility.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.LightAgility.LightDash.Cooldown", 2000);
        config.addDefault("Abilities.Spirits.LightSpirit.LightAgility.LightDash.Distance", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.LightAgility.LightSoar.Cooldown", 500);
        config.addDefault("Abilities.Spirits.LightSpirit.LightAgility.LightSoar.Duration", 2000);
        config.addDefault("Abilities.Spirits.LightSpirit.LightAgility.LightSoar.Speed", 0.4);

        config.addDefault("Abilities.Spirits.DarkSpirit.DarkAgility.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkAgility.DarkDash.Cooldown", 1500);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkAgility.DarkDash.Distance", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkAgility.DarkSoar.Cooldown", 1500);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkAgility.DarkSoar.Duration", 300);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkAgility.DarkSoar.Speed", 1.0);
        
        config.addDefault("Abilities.Spirits.DarkSpirit.Berserker.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Berserker.Cooldown", 1000);

        config.addDefault("Abilities.Spirits.Neutral.Possess.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Possess.Cooldown", 6200);
        config.addDefault("Abilities.Spirits.Neutral.Possess.Range", 14);
        config.addDefault("Abilities.Spirits.Neutral.Possess.MinDamage", 4);
        config.addDefault("Abilities.Spirits.Neutral.Possess.MaxDamage", 7);
        config.addDefault("Abilities.Spirits.Neutral.Possess.FailureSelfDamage", 2);
        config.addDefault("Abilities.Spirits.Neutral.Possess.Duration", 2000);
        config.addDefault("Abilities.Spirits.Neutral.Possess.Speed", 1);
        config.addDefault("Abilities.Spirits.Neutral.Possess.Durability", 30);
        config.addDefault("Abilities.Spirits.Neutral.Possess.ChargeTime", 0);

        /*config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.Cooldown", 9000);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.Range", 50);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.Damage", 2);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.MaxBlasts", 3);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.Duration", 3000);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.Speed", 1.2D);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.Radius", 0.3D);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.CanRedirect", true);
        config.addDefault("Abilities.Spirits.Neutral.SpiritBlast.CanAlwaysRedirect", false);
        */
        
        config.addDefault("Abilities.Spirits.Neutral.Passive.SpiritualBody.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Passive.SpiritualBody.FallDamageModifier", 0.0);
        
        config.addDefault("Abilities.Spirits.Neutral.Passive.EnergyVeil.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Passive.EnergyVeil.experienceMultiplier", 1.0);
        config.addDefault("Abilities.Spirits.Neutral.Passive.EnergyVeil.healthDivider", 1.0);
        config.addDefault("Abilities.Spirits.Neutral.Passive.EnergyVeil.maxHPBonus", 1.0);
        
        config.addDefault("Abilities.Spirits.Neutral.Vanish.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.Cooldown", 2800);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.Duration", 1000);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.ChargeTime", 1);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.Range", 16);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.Radius", 6);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.ParticleFrequency", 5);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.RemoveFire", true);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.DivideRange.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.DivideRange.HealthRequired", 20);
        config.addDefault("Abilities.Spirits.Neutral.Vanish.DivideRange.DivideFactor", 2);

        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Others.Cooldown", 1000);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Others.Range", 5);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Others.PotionInterval", 2000);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Others.HealInterval", 5000);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Others.SelfDamage", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Self.Cooldown", 3000);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Self.ChargeTime", 500);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Self.HealDuration", 6);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Self.NightVisionDuration", 12);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.Self.RemoveNegativePotionEffects", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.ParticleColor.Red", 255);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.ParticleColor.Green", 255);
        config.addDefault("Abilities.Spirits.LightSpirit.Alleviate.ParticleColor.Blue", 255);

        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.Enabled", false);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.Cooldown", 0);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.Controllable", false);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.Damage", 2);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.Range", 10);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.SelectionDuration", 2000);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.PotionDuration", 10);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.PotionPower", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.FirstBlastSpeed", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.SecondBlastSpeed", 0.2);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBlast.BlastRadius", 2);

        config.addDefault("Abilities.Spirits.LightSpirit.Orb.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.Cooldown", 4500);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.ChargeTime", 2000);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.Duration", 1500);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.WarmUpTime", 10);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.Damage", 4);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.PlaceRange", 16);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.DetonateRange", 3);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.EffectRange", 6);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.BlindnessDuration", 60);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.NauseaDuration", 220);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.PotionPower", 2);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.RequireGround", false);

        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.RemoveOnDamage", false);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Duration", 3000);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Others.ClickDelay", 2000);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Others.Cooldown", 10000);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Self.Cooldown", 5200);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Others.Radius", 3);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Self.Radius", 3);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Others.KnockbackPower", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Self.KnockbackPower", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Others.Range", 5);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.RemoveIfFarAway.Enabled", false);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.RemoveIfFarAway.Range", 5);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Self.BlockArrows", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Shelter.Others.BlockArrows", true);

        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Cooldown", 2000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Range", 9);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.PotionInterval", 1000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.HarmInterval", 1200);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.SelfDamage", 6);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.BlastSpeed", 0.5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.ParticleColor.Red", 255);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.ParticleColor.Green", 0);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.ParticleColor.Blue", 0);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Potions.WitherDuration", 5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Potions.WitherPower", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Potions.HungerDuration", 50);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Potions.HungerPower", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Potions.ConfusionDuration", 15);
        config.addDefault("Abilities.Spirits.DarkSpirit.Intoxicate.Potions.ConfusionPower", 1);

        config.addDefault("Abilities.Spirits.DarkSpirit.Shackle.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shackle.Cooldown", 3500);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shackle.Duration", 1800);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shackle.Range", 16);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shackle.Radius", 2);

        config.addDefault("Abilities.Spirits.Neutral.Swap.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Swap.Cooldown", 4000);
        config.addDefault("Abilities.Spirits.Neutral.Swap.Range", 28);
        config.addDefault("Abilities.Spirits.Neutral.Swap.Radius", 2);        
        
        config.addDefault("Abilities.Spirits.DarkSpirit.Strike.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Strike.Cooldown", 800);
        config.addDefault("Abilities.Spirits.DarkSpirit.Strike.Range", 12);
        config.addDefault("Abilities.Spirits.DarkSpirit.Strike.Damage", 4);
        config.addDefault("Abilities.Spirits.DarkSpirit.Strike.Radius", 1);

        //COMBOS

        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.Cooldown", 4600);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.Duration", 2500);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.Radius", 4);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.Damage", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.EffectInterval", 15);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.HurtDarkSpirits", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Rejuvenate.HurtMonsters", true);

        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.Cooldown", 12600);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.Duration", 7500);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.Radius", 2);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.EffectInterval", 15);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.HurtDarkSpirits", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.HurtMonsters", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.playerDamage", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.radiantcorruptionMobDamage", 1);

        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.Cooldown", 5000);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.Duration", 4000);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.Range", 10);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.AllowedHealthLoss", 10);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.AbilityCooldownMultipliers.Agility.Enabled", false);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.AbilityCooldownMultipliers.Agility.Multiplier", 2);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.AbilityCooldownMultipliers.Phase.Enabled", false);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.AbilityCooldownMultipliers.Phase.Multiplier", 4);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.AbilityCooldownMultipliers.Levitation.Enabled", false);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Levitation.AbilityCooldownMultipliers.Levitation.Multiplier", 3);

        config.addDefault("Abilities.Spirits.Neutral.Combo.Phase.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Phase.CooldownMultiplier", 4);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Phase.Duration", 1500);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Phase.Range", 8);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Phase.MinHealth", 0);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Phase.Vanish.ApplyCooldown", false);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Phase.Vanish.CooldownMultiplier", 4);

        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.Enabled", false);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.Cooldown", 0);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.Controllable", false);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.Damage", 4);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.Range", 10);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.DurationOfSelection", 2000);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.PotionDuration", 5);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.PotionPower", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.FirstBlastSpeed", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.SecondBlastSpeed", 0.2);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBlast.BlastRadius", 2);

        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.Cooldown", 4500);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.Duration", 3000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.Radius", 9);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.EffectInterval", 15);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.Damage", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.DamageEntities", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Infest.HealDarkSpirits", true);

        ConfigManager.languageConfig.save();
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}