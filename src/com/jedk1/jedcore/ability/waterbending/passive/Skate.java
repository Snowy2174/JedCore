package com.jedk1.jedcore.ability.waterbending.passive;

import com.jedk1.jedcore.JCMethods;
import com.jedk1.jedcore.JedCore;
import com.jedk1.jedcore.configuration.JedCoreConfig;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.IceAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.util.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Skate extends IceAbility implements AddonAbility, PassiveAbility {

	@Attribute(Attribute.SPEED)
	private int speedPower;
	private int duration;
	private boolean particles;

	public Skate(Player player) {
		super(player);
		this.setFields();
	}

	private void setFields() {
		ConfigurationSection config = JedCoreConfig.getConfig(this.player);

		this.speedPower = config.getInt("Abilities.Water.Ice.Passive.Skate.SpeedFactor");
		this.duration = config.getInt("Abilities.Water.Ice.Passive.Skate.LeaveIceDuration", 60);
		this.particles = config.getBoolean("Abilities.Water.Ice.Passive.Skate.Particles", true);
	}

	@Override
	public void progress() {
		if (JCMethods.isDisabledWorld(player.getWorld()) || !player.isOnGround() || !player.isSprinting() || !IceAbility.isIce(player.getLocation().getBlock().getRelative(BlockFace.DOWN))) return;

		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (bPlayer == null || !bPlayer.canIcebend() || !bPlayer.isPassiveToggled(Element.WATER) || !player.hasPermission("bending.ability.IceSkate")) return;

		player.removePotionEffect(PotionEffectType.SPEED);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.duration, this.speedPower));
		if (this.particles) {
			ParticleEffect.SNOW_SHOVEL.display(player.getLocation().clone().add(0, 0.2, 0), 15, Math.random() / 2, Math.random() / 2, Math.random() / 2, 0);
		}
	}


	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return true;
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public String getName() {
		return "IceSkate";
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public void load() {}

	@Override
	public void stop() {}

	@Override
	public String getAuthor() {
		return JedCore.dev;
	}

	@Override
	public String getVersion() {
		return JedCore.version;
	}

	@Override
	public String getDescription() {
		ConfigurationSection config = JedCoreConfig.getConfig(this.player);
		return "* JedCore Addon *\n" + config.getString("Abilities.Water.Ice.Passive.Skate.Description");
	}

	@Override
	public boolean isInstantiable() {
		return true;
	}

	@Override
	public boolean isProgressable() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		ConfigurationSection config = JedCoreConfig.getConfig(this.player);
		return config.getBoolean("Abilities.Water.Ice.Passive.Skate.Enabled");
	}
}
