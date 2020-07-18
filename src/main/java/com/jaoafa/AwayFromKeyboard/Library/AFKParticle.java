package com.jaoafa.AwayFromKeyboard.Library;

import org.bukkit.Particle;

public enum AFKParticle {
	EXPLOSION_NORMAL(Particle.EXPLOSION_NORMAL, 20, 0.2, 2L, 0.3D, 1D, 0.3D, 0.1D),
	EXPLOSION_LARGE(Particle.EXPLOSION_LARGE),
	EXPLOSION_HUGE(Particle.EXPLOSION_HUGE, 1, 0, 10L, 0D, 0D, 0D, 1D),
	FIREWORKS_SPARK(Particle.FIREWORKS_SPARK, 3, 0, 2L, 0.4D, 0.4D, 0.4D, 2D),
	WATER_BUBBLE(Particle.WATER_BUBBLE, 30, 0, 2L, 0.4D, 0.4D, 0.4D, 1D),
	WATER_SPLASH(Particle.WATER_SPLASH, 30, 0, 2L, 0.5D, 0.2D, 0.5D, 0.1D),
	WATER_WAKE(Particle.WATER_WAKE),
	// SUSPENDED(Particle.SUSPENDED),
	SUSPENDED_DEPTH(Particle.SUSPENDED_DEPTH),
	CRIT(Particle.CRIT),
	CRIT_MAGIC(Particle.CRIT_MAGIC),
	SMOKE_NORMAL(Particle.SMOKE_NORMAL),
	SMOKE_LARGE(Particle.SMOKE_LARGE),
	SPELL(Particle.SPELL),
	SPELL_INSTANT(Particle.SPELL_INSTANT),
	SPELL_MOB(Particle.SPELL_MOB),
	SPELL_MOB_AMBIENT(Particle.SPELL_MOB_AMBIENT),
	SPELL_WITCH(Particle.SPELL_WITCH),
	DRIP_WATER(Particle.DRIP_WATER, 100, 1, 1L, 1D, 0.3D, 1D, 4D),
	DRIP_LAVA(Particle.DRIP_LAVA, 100, 1, 1L, 1D, 0.3D, 1D, 4D),
	VILLAGER_ANGRY(Particle.VILLAGER_ANGRY, 3, 0, 2L, 0.3D, 0D, 0.3D, 2D),
	VILLAGER_HAPPY(Particle.VILLAGER_HAPPY, 5, 0, 2L, 0.4D, 0.4D, 0.4D, 0.5D),
	TOWN_AURA(Particle.TOWN_AURA, 50, 1, 2L, 0.4D, 0.4D, 0.4D, 1D),
	NOTE(Particle.NOTE, 5, 1, 3L, 0.7D, 0.7D, 0.7D, 0.7D),
	PORTAL(Particle.PORTAL),
	ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE, 10, 1, 2L, 0D, 0D, 0D, 1.5D),
	FLAME(Particle.FLAME, 30, 0, 5L, 0.5D, 0.5D, 0.5D, 0.5D),
	LAVA(Particle.LAVA),
	FOOTSTEP(Particle.FOOTSTEP),
	CLOUD(Particle.CLOUD, 8, 0, 2L, 0.4D, 0.4D, 0.4D, 2.5D),
	REDSTONE(Particle.REDSTONE, 40, 0, 2L, 0.2D, 0.5D, 0.2D, 0.1D),
	SNOWBALL(Particle.SNOWBALL, 20, 1, 5L, 0.4D, 0.4D, 0.4D, 0.3D),
	SNOW_SHOVEL(Particle.SNOW_SHOVEL, 30, 0, 2L, 0.4D, 0.4D, 0.4D, 3D),
	SLIME(Particle.SLIME),
	HEART(Particle.HEART, 10, 0D),
	BARRIER(Particle.BARRIER, 10, 0D, 1L, 0.5D, 0.5D, 0.5D, 0.5D),
	ITEM_CRACK(Particle.ITEM_CRACK),
	BLOCK_CRACK(Particle.BLOCK_CRACK),
	BLOCK_DUST(Particle.BLOCK_DUST),
	WATER_DROP(Particle.WATER_DROP),
	ITEM_TAKE(Particle.ITEM_TAKE),
	DRAGON_BREATH(Particle.DRAGON_BREATH),
	END_ROD(Particle.END_ROD),
	DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR, 10, 0.5, 5L, 0.1D, 0.1D, 0.1D, 1D),
	SWEEP_ATTACK(Particle.SWEEP_ATTACK),
	FALLING_DUST(Particle.FALLING_DUST),
	TOTEM(Particle.TOTEM, 10, 0.3, 2L, 0.5D, 0.5D, 0.5D, 2D),
	SPIT(Particle.SPIT, 10, 0, 2L, 0.4D, 0.4D, 0.4D, 0.5D);

	Particle particle;
	int count = 1;
	double extra = 0D;
	double spawnOffset = 0D;
	double offsetX = 0.3D;
	double offsetY = 0.3D;
	double offsetZ = 0.3D;
	long tick = 5L;

	AFKParticle(Particle particle) {
		this.particle = particle;
	}

	AFKParticle(Particle particle, int count) {
		this.particle = particle;
		this.count = count;
	}

	AFKParticle(Particle particle, int count, double extra) {
		this.particle = particle;
		this.count = count;
		this.extra = extra;
	}

	AFKParticle(Particle particle, int count, double extra, long tick) {
		this.particle = particle;
		this.count = count;
		this.extra = extra;
		this.tick = tick;
	}

	AFKParticle(Particle particle, int count, double extra, long tick, double offsetX, double offsetY, double offsetZ) {
		this.particle = particle;
		this.count = count;
		this.extra = extra;
		this.tick = tick;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}

	AFKParticle(Particle particle, int count, double extra, long tick, double offsetX, double offsetY, double offsetZ,
			double spawnOffset) {
		this.particle = particle;
		this.count = count;
		this.extra = extra;
		this.tick = tick;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.spawnOffset = spawnOffset;
	}

	public Particle getParticle() {
		return particle;
	}

	public int getCount() {
		return count;
	}

	public double getExtra() {
		return extra;
	}

	public double getSpawnOffset() {
		return spawnOffset;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public double getOffsetZ() {
		return offsetZ;
	}

	public long getTick() {
		return tick;
	}

	public static AFKParticle fromString(String str) {
		for (AFKParticle part : values()) {
			if (part.name().equalsIgnoreCase(str)) {
				return part;
			}
		}
		return null;
	}
}
