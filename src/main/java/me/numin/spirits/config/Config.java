package me.numin.spirits.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.numin.spirits.SpiritElement;
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
        language.addDefault("Chat.Colors.Primal", SpiritElement.PRIMAL.getDefaultColor().getName());
        language.addDefault("Chat.Colors.PrimalSub", "LIGHT_PURPLE");
        language.addDefault("Chat.Prefixes.Spirit", "[Spirit]");
        language.addDefault("Chat.Prefixes.LightSpirit", "[LightSpirit]");
        language.addDefault("Chat.Prefixes.DarkSpirit", "[DarkSpirit]");
        language.addDefault("Chat.Prefixes.Primal", "[Primal]");

        //Descriptions & Instructions
        config.addDefault("Language.Abilities.Spirit.Agility.Description", "This ability offers you 2 modes of mobility. The first being the ability to dash forward very quickly. The second being the ability to soar through the skies as if gravity is non-existant.");
        config.addDefault("Language.Abilities.Spirit.Agility.Instructions", "Left-Click: Dash ⏐ Hold shift: Soar");
        
        config.addDefault("Language.Abilities.Spirit.Swap.Description", "Conjure a makeshift portal, which swaps places of creatures hit.");
        config.addDefault("Language.Abilities.Spirit.Swap.Instructions", "Left-Click: Blast");
        
        config.addDefault("Language.Abilities.Spirit.Vortex.Description", "Create a Gravity Well using energy that sends any being to the ground. This ability was used by the ancients to collect grapes from the giraffe palm trees");
        config.addDefault("Language.Abilities.Spirit.Vortex.Instructions", "Shift and Left-Click: Create Well");
        
        config.addDefault("Language.Abilities.Spirit.SpiritStruck.Description", "Temporarily disturb the targets spirit, stunning their body. The less HP you have, the less you can tinker with their energy and the more you are influenced by it. Deals extra damage to mobs.");
        config.addDefault("Language.Abilities.Spirit.SpiritStruck.Instructions", "Punch someone");
        
        config.addDefault("Language.Abilities.Spirit.Calling.Description", "Tap into the energies of the world and use your body as a medium to open a rift, which will let spirits out that target anything else. This will cost you health. You may also sacrifice all your energy to create a more powerful Rift");
        config.addDefault("Language.Abilities.Spirit.Calling.Instructions", "Calling: Tap Sneak. Rift: Hold Sneak + Left Click");
        
        config.addDefault("Language.Abilities.Spirit.Paint.Description", "Create a small disruption in the energy in front of you. Leaving a temporary mark");
        config.addDefault("Language.Abilities.Spirit.Paint.Instructions", "Paint: Left Click");
        
        config.addDefault("Language.Abilities.Primal.CallingRift.Description", "Unleash Terror by using your massive energy to open a rift that immediatly sucks in something into your world from another plane. You need High Health to use Dark Terror. Terrors will attack ANYTHING.");
        config.addDefault("Language.Abilities.Primal.CallingRift.Instructions", "Terror: Left-Click. Dark Terror: Hold Sneak");
        
        config.addDefault("Language.Abilities.Primal.SummonSheepOnABoatAndPiston.Description", "Unleash either terror or nurture those around you");
        config.addDefault("Language.Abilities.Primal.SummonSheepOnABoatAndPiston.Instructions", "Terror: Left-Click. Nurture: Hold Sneak");
        
        config.addDefault("Language.Abilities.LightSpirit.LightAgility.Description", "Just like a Spirits Agility, except... the landing is much rougher and you can't really keep it as long.");
        config.addDefault("Language.Abilities.LightSpirit.LightAgility.Instructions", "Left-Click: Dash ⏐ Hold shift: Soar");
 
        config.addDefault("Language.Abilities.LightSpirit.LuminousCorruption.Description", "Speed up and Invert the natural healing component of a creature by touching them. Inflict Hunger and Damage based on saturation levels to a Player . Deals a base amount to creatures. Converts Absorption, Health Boost, Regeneration effects into Wither.");
        config.addDefault("Language.Abilities.LightSpirit.LuminousCorruption.Instructions", "Punch someone");
        
        config.addDefault("Language.Abilities.LightSpirit.LightShot.Description", "At the cost of your livelihood. Send out energy to a location, exploding on contact with any being and giving positive effects to anything nearby");
        config.addDefault("Language.Abilities.LightSpirit.LightShot.Instructions", "Shift and Left-Click: Blast");
        
        config.addDefault("Language.Abilities.DarkSpirit.DarkAgility.Description", "You gave in to the earthly tethers, you are not as free but your Dash is strong");
        config.addDefault("Language.Abilities.DarkSpirit.DarkAgility.Instructions", "Left-Click: Dash ⏐ Hold shift: Soar");

        config.addDefault("Language.Abilities.DarkSpirit.Berserker.Description", "Sacrifice your spirit in exchange of a massive power surge. You need atleast half health to activate this ability and you will lose most of it, power depends on life sacrificed.");
        config.addDefault("Language.Abilities.DarkSpirit.Berserker.Instructions", "Left-Click to Activate");
        
        config.addDefault("Language.Abilities.Spirit.Passive.SpiritualBody.Description", "Spirits do not have a physical form. As such, they are immune to all forms of kinetic damage like fall damage");
        config.addDefault("Language.Abilities.Spirit.Passive.SpiritualBody.Instructions", "Spirits are passively immune to fall damage");
        //Abilities.Spirits.LightSpirit.Passive.EphemerealBody.FallDamageModifier
        config.addDefault("Language.Abilities.DarkSpirit.Passive.EtherealBody.Description", "DarkSpirits are natural fighters and their bodies have adapted to take strong hits");
        config.addDefault("Language.Abilities.DarkSpirit.Passive.EtherealBody.Instructions", "DarkSpirits are take reduced fall and attack damage");
        
        config.addDefault("Language.Abilities.LightSpirit.Passive.EphemeralBody.Description", "LightSpirits naturally manipulate and restore their energy, their flow is highly tuned to the earthly tethers");
        config.addDefault("Language.Abilities.LightSpirit.Passive.EphemeralBody.Instructions", "LightSpirits are take reduced fall and maguc damage");
        
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
        config.addDefault("Abilities.Spirits.Neutral.SpiritStruck.Cooldown", 3900);
        config.addDefault("Abilities.Spirits.Neutral.SpiritStruck.Duration", 1200);
        config.addDefault("Abilities.Spirits.Neutral.SpiritStruck.MobDamage", 12);
        
        config.addDefault("Abilities.Spirits.Neutral.Calling.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Calling.Cooldown", 100);
        config.addDefault("Abilities.Spirits.Neutral.Calling.Duration", 8000);
        config.addDefault("Abilities.Spirits.Neutral.Calling.Amount", 3);
        config.addDefault("Abilities.Spirits.Neutral.Calling.SelfDamage", 4);
        config.addDefault("Abilities.Spirits.Neutral.Calling.SpiritDamage", 0);
        config.addDefault("Abilities.Spirits.Neutral.Calling.AttackRadius", 22);
        config.addDefault("Abilities.Spirits.Neutral.Calling.AttackYRadius", 9);
        
        config.addDefault("Abilities.Spirits.Primal.CallingRift.Enabled", true);
        config.addDefault("Abilities.Spirits.Primal.CallingRift.Cooldown", 100);
        config.addDefault("Abilities.Spirits.Primal.CallingRift.Duration", 16000);
        config.addDefault("Abilities.Spirits.Primal.CallingRift.Amount", 1);
        config.addDefault("Abilities.Spirits.Primal.CallingRift.SpiritDamage", 0);
        config.addDefault("Abilities.Spirits.Primal.CallingRift.SelfDamage", 10);
        config.addDefault("Abilities.Spirits.Primal.CallingRift.AttackRadius", 22);
        config.addDefault("Abilities.Spirits.Primal.CallingRift.AttackYRadius", 9);
        
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.Cooldown", 2500);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.Duration", 8);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.luminouscorruptionMobDamage", 10);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.playerDamage", 3);
        config.addDefault("Abilities.Spirits.LightSpirit.LuminousCorruption.saturationMultiplier", 0.5);
        
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
        
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.EtherealBody.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.EtherealBody.FallDamageModifier", 0.5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.EtherealBody.AttackDamageModifier", 0.5);
        
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.EphemeralBody.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.EphemeralBody.FallDamageModifier", 0.7);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.EphemeralBody.MagicDamageModifier", 0.7);
        
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
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.Duration", 2500);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.WarmUpTime", 10);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.Damage", 4);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.PlaceRange", 16);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.DetonateRange", 2);
        config.addDefault("Abilities.Spirits.LightSpirit.Orb.EffectRange", 3);
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

        config.addDefault("Abilities.Spirits.LightSpirit.LightShot.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.LightShot.Cooldown", 4000);
        config.addDefault("Abilities.Spirits.LightSpirit.LightShot.Range", 28);
        config.addDefault("Abilities.Spirits.LightSpirit.LightShot.Radius", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.LightShot.AoeRadius", 6);
        config.addDefault("Abilities.Spirits.LightSpirit.LightShot.PotionDuration", 200);
        config.addDefault("Abilities.Spirits.LightSpirit.LightShot.SelfDamage", 8);
        
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
        config.addDefault("Abilities.Spirits.Neutral.Swap.Cooldown", 3000);
        config.addDefault("Abilities.Spirits.Neutral.Swap.Range", 32);
        config.addDefault("Abilities.Spirits.Neutral.Swap.Radius", 2);   
        
        config.addDefault("Abilities.Spirits.Neutral.Paint.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Paint.Cooldown", 50);
        config.addDefault("Abilities.Spirits.Neutral.Paint.Duration", 30000);
        
        config.addDefault("Abilities.Spirits.Neutral.Vortex.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Vortex.Cooldown", 8500);
        config.addDefault("Abilities.Spirits.Neutral.Vortex.Duration", 3600);
        config.addDefault("Abilities.Spirits.Neutral.Vortex.Radius", 16);
        config.addDefault("Abilities.Spirits.Neutral.Vortex.Pull", 14);
        config.addDefault("Abilities.Spirits.Neutral.Vortex.EffectAmplifier", 1);
        config.addDefault("Abilities.Spirits.Neutral.Vortex.EffectDuration", 1);
        
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
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.RadiantCorruption.playerDamage", 0);
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

        //Neutral - Float
        config.addDefault("Abilities.Spirits.Neutral.Float.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Float.Cooldown", 500);
        config.addDefault("Abilities.Spirits.Neutral.Float.FloatDuration", 4500);
        config.addDefault("Abilities.Spirits.Neutral.Float.FloatPower", 1);
        config.addDefault("Language.Abilities.Spirit.Float.Description", "Some spirits are able to levitate and even fly through the air! The physiology of these spirits allow them to float for a while.");
        config.addDefault("Language.Abilities.Spirit.Float.Instructions", "To use this ability, left click and you are able to float.");

        //Dark
        //Corruption
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.Cooldown", 6000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.Radius", 5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.Duration", 15);
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.EffectDuration", 2);
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.EffectAmplifier", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.AttackRadius", 22);
        config.addDefault("Abilities.Spirits.DarkSpirit.Corruption.AttackYRadius", 9);
        config.addDefault("Language.Abilities.DarkSpirit.Corruption.Description", "Dark spirits are entities filled with rage and malevolence. "
                + "They are able to infect and influence the area around them and imbue their negative "
                + "energies to it. They could also summon more dark spirits within this area in order to spread their corruption. "
                + "Mobs and land are also affected in this area of influence.");
        config.addDefault("Language.Abilities.DarkSpirit.Corruption.Instructions", "To use, hold sneak.");

        //DarkBeam
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBeam.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBeam.Cooldown", 5000);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBeam.ChargeTime", 2000);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBeam.Duration", 1300);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBeam.Damage", 3);
        config.addDefault("Abilities.Spirits.DarkSpirit.DarkBeam.Range", 30);
        config.addDefault("Language.Abilities.DarkSpirit.DarkBeam.Description", "By channeling all the stored energy within the bodies of "
                + "Dark spirits, they are able to release it in the form of a deadly beam!");
        config.addDefault("Language.Abilities.DarkSpirit.DarkBeam.Instructions", "To use, hold sneak until purple spell particles appear and left click.");

        //Onslaught
        config.addDefault("Abilities.Spirits.DarkSpirit.Onslaught.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Onslaught.Cooldown", 700);
        config.addDefault("Abilities.Spirits.DarkSpirit.Onslaught.Speed", 0.43);
        config.addDefault("Abilities.Spirits.DarkSpirit.Onslaught.EffectAmplifier", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.Onslaught.EffectDuration", 1.5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Onslaught.Duration", 0.5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Onslaught.Damage", 1);
        config.addDefault("Language.Abilities.DarkSpirit.Onslaught.Description", "Dark spirits are able to charge and assault their "
                + "victims, oftentimes badly deforming and corrupting them!");
        config.addDefault("Language.Abilities.DarkSpirit.Onslaught.Instructions", "To use, hold sneak and left click.");

        //Shadow
        config.addDefault("Abilities.Spirits.DarkSpirit.Shadow.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shadow.Cooldown", 6000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shadow.Duration", 1000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shadow.CollisionRadius", 2);
        config.addDefault("Abilities.Spirits.DarkSpirit.Shadow.TeleportRange", 3);
        config.addDefault("Language.Abilities.DarkSpirit.Shadow.Description", "Dark spirits are able to travel quickly in the darkness and render themselves "
                + "permeable for a split second to any attack by transforming into the night! When in shadow mode however, spirits are unable "
                + "to see due to transforming into pure darkness.");
        config.addDefault("Language.Abilities.DarkSpirit.Shadow.Instructions", "To use, hold sneak. While holding sneak and in shadow mode temporarily, release to teleport. "
                + "(Note: This is quick use ability, all effects are to be used within that split second of shadow mode)");

        //Light Abilities
        //Enlightenment
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.Cooldown", 21000);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.ChargeTime", 2);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.EnlightenRadius", 1.5);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.EffectAmplifier", 3);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.EffectDuration", 14);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.AbsorptionHealth", 0);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.Forcefield.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.Forcefield.ShieldRadius", 4);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.Forcefield.Damage", 3);
        config.addDefault("Abilities.Spirits.LightSpirit.Enlightenment.Forcefield.Repel", 0.3);
        config.addDefault("Abilities.LightSpirit.Enlightenment.DeathMessage", "{victim} was repelled by {attacker}'s {ability} shield!");
        config.addDefault("Language.Abilities.LightSpirit.Enlightenment.Description", "Enlightenment allows the user to gain buffs and positive effects "
                + "through the use of spiritual knowledge! With the help of other spirits and light spirits, buffs are "
                + "more stronger and effective and you are able to share your enlightenment! After gaining enlightenment, your light "
                + "attacks become stronger and you produce a temporary forcefield to ward out dark spirits.");
        config.addDefault("Language.Abilities.LightSpirit.Enlightenment.Instructions", "To use, hold sneak until a certain time and release. If close to other spirits or light spirits, "
                + "your buffs increase and you are able to enlighten them as well.");

        //LightBeam
        config.addDefault("Abilities.Spirits.LightSpirit.LightBeam.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBeam.Cooldown", 1500);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBeam.ChargeTime", 1000);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBeam.Duration", 3000);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBeam.Damage", 2);
        config.addDefault("Abilities.Spirits.LightSpirit.LightBeam.Range", 8);
        config.addDefault("Language.Abilities.LightSpirit.LightBeam.Description", "By channeling all the stored energy within the bodies of "
                + "Light spirits, they are able to release it in the form of a bright beam!");
        config.addDefault("Language.Abilities.LightSpirit.LightBeam.Instructions", "To use, hold sneak until white spell particles appear and left click.");

        //Safeguard
        config.addDefault("Abilities.Spirits.LightSpirit.Safeguard.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Safeguard.Cooldown", 8000);
        config.addDefault("Abilities.Spirits.LightSpirit.Safeguard.Duration", 7500);
        config.addDefault("Language.Abilities.LightSpirit.Safeguard.Description", "By creating a shield of positive light energy around themselves, "
                + "Light spirits are able to protect themselves from negative status effects with positive status effects. Clears all negative effects and grants buffs for a duration.");
        config.addDefault("Language.Abilities.LightSpirit.Safeguard.Instructions", "To use, tap sneak.");

        //Wish
        config.addDefault("Abilities.Spirits.LightSpirit.Wish.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Wish.Cooldown", 10000);
        config.addDefault("Abilities.Spirits.LightSpirit.Wish.ChargeTime", 1500);
        config.addDefault("Abilities.Spirits.LightSpirit.Wish.WaitDuration", 6000);
        config.addDefault("Abilities.Spirits.LightSpirit.Wish.HealAmount", 6);
        config.addDefault("Language.Abilities.LightSpirit.Wish.Description", "By wishing and having a great mind, "
                + "light spirits are able to harness and channel their inner energies and gain energy from the Spirit World after a time.");
        config.addDefault("Language.Abilities.LightSpirit.Wish.Instructions", "To use, hold sneak until white spell particles appear and release");

        //Passives
        //AfterGlow (light)
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.Afterglow.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.Afterglow.Cooldown", 2000);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.Afterglow.HealAmount", 6);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.Afterglow.Damage", 4);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.Afterglow.Duration", 20000);
        config.addDefault("Language.Abilities.LightSpirit.Passive.Afterglow.Description", "When Light spirits perish, they leave behind a piece of residual energy containing "
                        + "light. Any fellow Light spirits touching and absorbing this afterglow will gain back health and energy while "
                        + "any Dark spirit or creature will be hurt.");

        //WishfulThinking
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.WishfulThinking.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.WishfulThinking.EffectDuration", 8);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.WishfulThinking.EffectAmplifier", 0);
        config.addDefault("Abilities.Spirits.LightSpirit.Passive.WishfulThinking.Chance", 0.33);
        config.addDefault("Language.Abilities.LightSpirit.Passive.WishfulThinking.Description", "Light spirits are the embodiment of positive energy! "
                + "With the mentality of hopeful and positive thinking, they gain positive effects! "
                + "Everytime you get hit, there is a small chance of getting regeneration.");

        //DarkAlliance
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.DarkAlliance.Enabled", false);
        config.addDefault("Language.Abilities.DarkSpirit.Passive.DarkAlliance.Description", "Dark spirits and mobs alike are mutual with one another! "
                + "Their alliance compels monsters to not target the dark spirits.");

        //SinisterAura
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.Cooldown", 500);
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.Range", 3);
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.EffectAmplifier", 1);
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.EffectDuration", 3);
        config.addDefault("Abilities.Spirits.DarkSpirit.Passive.SinisterAura.Chance", 0.33);
        config.addDefault("Language.Abilities.DarkSpirit.Passive.SinisterAura.Description", "Dark spirits are full of negative energy that can influence their targets. "
                + "Dark spirits are able to release this energy and conjure an aura that gives negative effects to any nearby entity. "
                + "Everytime you get hit, there is a small chance of releasing an aura wave.");

        //Combos
        //Awakening
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.Cooldown", 21000);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.Duration", 10000);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.Damage", 1);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.Radius", 6);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.HealAmount", 0.5);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.AttackChance", 0.01);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Awakening.EnableParticles", true);
        config.addDefault("Language.Abilities.LightSpirit.Combo.Awakening.Description", "Light spirits like dark spirits are able to summon aid from "
                + "the other worlds. Your aid will attack anything nearby except you with a "
                + "powerful beam of light! By intercepting these beams as a light spirit, you are able to gain health!");
        config.addDefault("Language.Abilities.LightSpirit.Combo.Awakening.Instructions", "Alleviate (Tap sneak) > Alleviate (Tap Sneak) > Alleviate (Left click)");

        //Nightmare
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Nightmare.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Nightmare.Cooldown", 6000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Nightmare.Radius", 5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Nightmare.Damage", 2);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Nightmare.EffectAmplifier", 5);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Nightmare.EffectDuration", 3000);
        config.addDefault("Language.Abilities.DarkSpirit.Combo.Nightmare.Description", "Dark spirits are able to infect their targets with dark energy and spread "
                + "their negative energies towards their mind. By doing so, they can cause absolute chaos towards the strengths of "
                + "all creatures with every worst status effect.");
        config.addDefault("Language.Abilities.DarkSpirit.Combo.Nightmare.Instructions", "Intoxicate (Hold sneak) > Onslaught (Release sneak) > Onslaught (Left click) > Shackle (Hold sneak)");

        //Pandemonium
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Pandemonium.Enabled", true);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Pandemonium.Cooldown", 6000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Pandemonium.Duration", 5000);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Pandemonium.Radius", 10);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Pandemonium.Pull", 0.02);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Pandemonium.EffectAmplifier", 0);
        config.addDefault("Abilities.Spirits.DarkSpirit.Combo.Pandemonium.EffectDuration", 3);
        config.addDefault("Language.Abilities.DarkSpirit.Combo.Pandemonium.Description", "Dark spirits are able to use their dark influence on other creatures nearby, "
                + "slowly corrupting them by penetrating their free will and their ability to move and pulling them towards the darkness.");
        config.addDefault("Language.Abilities.DarkSpirit.Combo.Pandemonium.Instructions", "Intoxicate (Tap sneak) > Intoxicate (Hold sneak) > Shackle (Release sneak)");

        //Sanctuary
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.Enabled", true);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.Cooldown", 4500);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.Duration", 1400);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.Radius", 8);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.Repel", 0.3);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.Damage", 2);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.EffectDuration", 2.5);
        config.addDefault("Abilities.Spirits.LightSpirit.Combo.Sanctuary.EffectAmplifier", 1);
        config.addDefault("Language.Abilities.LightSpirit.Combo.Sanctuary.Description", "Light spirits are able to generate a protective " +
                "barrier that expands from their location and repel Dark Spirits and Mobs away while boosting their " +
                "light spirit allies with damage protection and removing negative effects!");
        config.addDefault("Language.Abilities.LightSpirit.Combo.Sanctuary.Instructions", "Alleviate (Tap sneak) > Alleviate (Hold sneak) > Shelter (Release sneak)");

        //Skyrocket
        config.addDefault("Abilities.Spirits.Neutral.Combo.Skyrocket.Enabled", true);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Skyrocket.Cooldown", 3000);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Skyrocket.RegenTime", 5000);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Skyrocket.Speed", 1.5);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Skyrocket.Damage", 5);
        config.addDefault("Abilities.Spirits.Neutral.Combo.Skyrocket.Radius", 3);
        config.addDefault("Language.Abilities.Spirit.Combo.Skyrocket.Description", "Spirits are able to launch themselves in the air with such high speeds and acceleration that they are able to "
                + "aggressively slam themselves on the ground to cause ruptures.");
        config.addDefault("Language.Abilities.Spirit.Combo.Skyrocket.Instructions", "Agility (Tap sneak) > Agility (Tap sneak) > Agility (Left click)");

        ConfigManager.languageConfig.save();
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}