package com.jaoafa.AwayFromKeyboard.Library;

import org.bukkit.Particle;

public enum AFKParticle {
	EXPLOSION_NORMAL(Particle.EXPLOSION_NORMAL, 20, 0.2, 2L, 0.3D, 1D, 0.3D, 0.1D),
    EXPLOSION_LARGE(Particle.EXPLOSION_LARGE, 1, 0.01, 1L, 1D, 0.3D, 1D, 1D),
	EXPLOSION_HUGE(Particle.EXPLOSION_HUGE, 1, 0, 10L, 0D, 0D, 0D, 1D),
	FIREWORKS_SPARK(Particle.FIREWORKS_SPARK, 3, 0, 2L, 0.4D, 0.4D, 0.4D, 2D),
	WATER_BUBBLE(Particle.WATER_BUBBLE, 30, 0, 2L, 0.4D, 0.4D, 0.4D, 1D),
	WATER_SPLASH(Particle.WATER_SPLASH, 30, 0, 2L, 0.5D, 0.2D, 0.5D, 0.1D),
	WATER_WAKE(Particle.WATER_WAKE, 35, 0, 3L, 0.5D, 0.2D, 0.5D, 0.1D),
	// SUSPENDED(Particle.SUSPENDED),
	SUSPENDED_DEPTH(Particle.SUSPENDED_DEPTH, 50, 2, 3L, 0.5D, 0.5D, 0.5D, 0.1D),
	CRIT(Particle.CRIT, 15, 0, 5L, 0.5D, 0.5D, 0.5D, 1.5D),
	CRIT_MAGIC(Particle.CRIT_MAGIC, 15, 0, 5L, 0.5D, 0.5D, 0.5D, 1.5D),
    SMOKE_NORMAL(Particle.SMOKE_NORMAL, 15, 0.01, 1L, 0.3D, 0.1D, 0.3D, 0.1D),
    SMOKE_LARGE(Particle.SMOKE_LARGE, 5, 0.01, 1L, 0.1D, 0.1D, 0.1D, 2D),
    SPELL(Particle.SPELL, 5, 1, 1L, 0.1D, 0.1D, 0.1D, 0D),
    SPELL_INSTANT(Particle.SPELL_INSTANT, 5, 1, 1L, 1D, 0.1D, 1D, 0D),
    SPELL_MOB(Particle.SPELL_MOB, 5, 1, 1L, 0.4D, 0.1D, 0.4D, 0D),
    SPELL_MOB_AMBIENT(Particle.SPELL_MOB_AMBIENT, 10, 1, 1L, 0.4D, 0.4D, 0.4D, 0D),
    SPELL_WITCH(Particle.SPELL_WITCH, 7, 1, 1L, 0.4D, 0.01D, 0.4D, 0D),
    DRIP_WATER(Particle.DRIP_WATER, 50, 10, 1L, 0.8D, 0.3D, 0.8D, 5D),
    DRIP_LAVA(Particle.DRIP_LAVA, 50, 10, 1L, 0.8D, 0.3D, 0.8D, 5D),
	VILLAGER_ANGRY(Particle.VILLAGER_ANGRY, 3, 0, 2L, 0.3D, 0D, 0.3D, 2D),
	VILLAGER_HAPPY(Particle.VILLAGER_HAPPY, 5, 0, 2L, 0.4D, 0.4D, 0.4D, 0.5D),
	TOWN_AURA(Particle.TOWN_AURA, 50, 1, 2L, 0.4D, 0.4D, 0.4D, 1D),
	NOTE(Particle.NOTE, 5, 1, 3L, 0.7D, 0.7D, 0.7D, 0.7D),
    PORTAL(Particle.PORTAL, 10, 1, 1L, 0.1D, 0.1D, 0.1D, 0D),
	ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE, 10, 1, 2L, 0D, 0D, 0D, 1.5D),
    FLAME(Particle.FLAME, 10, 0.07, 1L, 0.5D, 0.1D, 0.5D, 0D),
    LAVA(Particle.LAVA, 5, 1, 1L, 0.5D, 0.1D, 0.5D, 2D),
	CLOUD(Particle.CLOUD, 8, 0, 2L, 0.4D, 0.4D, 0.4D, 2.5D),
	REDSTONE(Particle.REDSTONE, 40, 0, 2L, 0.2D, 0.5D, 0.2D, 0.1D),
	SNOWBALL(Particle.SNOWBALL, 20, 1, 5L, 0.4D, 0.4D, 0.4D, 0.3D),
	SNOW_SHOVEL(Particle.SNOW_SHOVEL, 30, 0, 2L, 0.4D, 0.4D, 0.4D, 3D),
    SLIME(Particle.SLIME, 10, 1, 1L, 0.5D, 0.1D, 0.5D, 0D),
    HEART(Particle.HEART, 4, 1, 5L, 0.5D, 0.1D, 0.5D, 2D),
    BARRIER(Particle.BARRIER, 1, 1, 5L, 0.5D, 0.1D, 0.5D, 2D),
    ITEM_CRACK(Particle.ITEM_CRACK, 10, 0, 1L, 0.7D, 0.1D, 0.7D, 3D),
    BLOCK_CRACK(Particle.BLOCK_CRACK, 10, 0, 1L, 0.7D, 0.1D, 0.7D, 0D),
	//BLOCK_DUST(Particle.BLOCK_DUST),
	WATER_DROP(Particle.WATER_DROP, 50, 9, 3L, 0.7D, 0.5D, 0.7D, 0.1D),
	//ITEM_TAKE(Particle.ITEM_TAKE),
	DRAGON_BREATH(Particle.DRAGON_BREATH, 6, 0.1, 3L, 0.1D, 0.1D, 0.1D, 1.8D),
	END_ROD(Particle.END_ROD, 9, 0.1, 3L, 0.5D, 0.1D, 0.5D, 1D),
	DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR, 10, 0.5, 5L, 0.1D, 0.1D, 0.1D, 1D),
	SWEEP_ATTACK(Particle.SWEEP_ATTACK, 9, 3, 3L, 0.5D, 0.1D, 0.5D, 1D),
	FALLING_DUST(Particle.FALLING_DUST, 5, 0, 5L, 0.5D, 0.1D, 0.5D, 3D),
	TOTEM(Particle.TOTEM, 10, 0.3, 2L, 0.5D, 0.5D, 0.5D, 2D),
	SPIT(Particle.SPIT, 10, 0, 2L, 0.4D, 0.4D, 0.4D, 0.5D),
    SQUID_INK(Particle.SQUID_INK, 10, 0, 1L, 0.2D, 0.1D, 0.2D, 0.5D),
    BUBBLE_POP(Particle.BUBBLE_POP, 100, 0.1, 1L, 0.5D, 0.5D, 0.5D, 0D),
    CURRENT_DOWN(Particle.CURRENT_DOWN, 100, 0, 1L, 0.5D, 0.5D, 0.5D, 0D),
    BUBBLE_COLUMN_UP(Particle.BUBBLE_COLUMN_UP, 100, 0, 1L, 0.5D, 0.5D, 0.5D, 0D),
    NAUTILUS(Particle.NAUTILUS, 5, 1, 1L, 0.5D, 0.5D, 0.5D, 2D),
    DOLPHIN(Particle.DOLPHIN, 10, 1, 1L, 0.5D, 0.5D, 0.5D, 1D),
    SNEEZE(Particle.SNEEZE, 10, 0.01, 1L, 0.5D, 0.5D, 0.5D, 2D),
    CAMPFIRE_COSY_SMOKE(Particle.CAMPFIRE_COSY_SMOKE, 10, 0.01, 1L, 0.1D, 0.1D, 0.1D, 0D),
    CAMPFIRE_SIGNAL_SMOKE(Particle.CAMPFIRE_SIGNAL_SMOKE, 10, 0.005, 1L, 0.1D, 0.1D, 0.1D, 0D),
    COMPOSTER(Particle.COMPOSTER, 20, 0.01, 1L, 0.5D, 1D, 0.5D, 0D),
    FALLING_LAVA(Particle.FALLING_LAVA, 20, 0.01, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    LANDING_LAVA(Particle.LANDING_LAVA, 20, 0.01, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    FALLING_WATER(Particle.FALLING_WATER, 20, 0.01, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    DRIPPING_HONEY(Particle.DRIPPING_HONEY, 10, 0.01, 1L, 0.3D, 0.1D, 0.3D, 2.5D),
    FALLING_HONEY(Particle.FALLING_HONEY, 20, 0.01, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    LANDING_HONEY(Particle.LANDING_HONEY, 20, 0.01, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    FALLING_NECTAR(Particle.FALLING_NECTAR, 20, 0.01, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    SOUL_FIRE_FLAME(Particle.SOUL_FIRE_FLAME, 10, 0.05, 1L, 0.6D, 0.1D, 0.1D, 0D),
    ASH(Particle.ASH, 90, 0.05, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    CRIMSON_SPORE(Particle.CRIMSON_SPORE, 90, 0.01, 1L, 0.6D, 0.1D, 0.1D, 0D),
    WARPED_SPORE(Particle.WARPED_SPORE, 90, 0.01, 1L, 0.6D, 0.1D, 0.1D, 0D),
    SOUL(Particle.SOUL, 3, 0.05, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    DRIPPING_OBSIDIAN_TEAR(Particle.DRIPPING_OBSIDIAN_TEAR, 3, 0.05, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    FALLING_OBSIDIAN_TEAR(Particle.FALLING_OBSIDIAN_TEAR, 3, 0.05, 1L, 0.3D, 0.1D, 0.3D, 2.5D),
    LANDING_OBSIDIAN_TEAR(Particle.LANDING_OBSIDIAN_TEAR, 10, 0.05, 1L, 0.6D, 0.1D, 0.1D, 2.5D),
    REVERSE_PORTAL(Particle.REVERSE_PORTAL, 30, 0.04, 1L, 0.1D, 0.1D, 0.1D, 0D),
    WHITE_ASH(Particle.WHITE_ASH, 30, 0.01, 1L, 0.1D, 0.1D, 0.1D, 2.5D);

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
