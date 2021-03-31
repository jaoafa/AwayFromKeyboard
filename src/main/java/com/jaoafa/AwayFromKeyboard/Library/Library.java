package com.jaoafa.AwayFromKeyboard.Library;

import java.util.ArrayList;
import java.util.List;

public class Library {
	static AFKParticle[] particles = new AFKParticle[] {
			AFKParticle.FLAME,
			AFKParticle.LAVA,
			AFKParticle.VILLAGER_ANGRY,
			AFKParticle.HEART,
			AFKParticle.EXPLOSION_HUGE,
			AFKParticle.EXPLOSION_LARGE,
			AFKParticle.SMOKE_LARGE,
			AFKParticle.NOTE,
			AFKParticle.DAMAGE_INDICATOR,
			AFKParticle.SWEEP_ATTACK,
			AFKParticle.FIREWORKS_SPARK,
			AFKParticle.CRIT_MAGIC,
			AFKParticle.VILLAGER_HAPPY,
			AFKParticle.END_ROD,
			AFKParticle.DRIP_WATER,
			AFKParticle.DRIP_LAVA,
			AFKParticle.SPELL_INSTANT,
			AFKParticle.SPELL,
			AFKParticle.PORTAL,
			AFKParticle.DRAGON_BREATH,
			AFKParticle.CLOUD,
			AFKParticle.ENCHANTMENT_TABLE,
			AFKParticle.SPELL_WITCH,
			AFKParticle.TOTEM,
			AFKParticle.EXPLOSION_NORMAL,
			AFKParticle.CRIT,
			AFKParticle.SPELL_MOB_AMBIENT,
			AFKParticle.REDSTONE,
			AFKParticle.WATER_BUBBLE,
			AFKParticle.SPIT,
			AFKParticle.SPELL_MOB,
			AFKParticle.SNOWBALL,
			AFKParticle.WATER_WAKE,
			AFKParticle.WATER_DROP,
			AFKParticle.SLIME,
			AFKParticle.SNOW_SHOVEL,
			AFKParticle.SUSPENDED_DEPTH,
			AFKParticle.TOWN_AURA,
			AFKParticle.WATER_SPLASH,
			AFKParticle.SMOKE_NORMAL,
			AFKParticle.BARRIER,
            AFKParticle.ITEM_CRACK,
            AFKParticle.BLOCK_CRACK,
            AFKParticle.FALLING_DUST,
            AFKParticle.SQUID_INK,
            AFKParticle.BUBBLE_POP,
            AFKParticle.CURRENT_DOWN,
            AFKParticle.BUBBLE_COLUMN_UP,
            AFKParticle.NAUTILUS,
            AFKParticle.DOLPHIN,
            AFKParticle.SNEEZE,
            AFKParticle.CAMPFIRE_COSY_SMOKE,
            AFKParticle.CAMPFIRE_SIGNAL_SMOKE,
            AFKParticle.COMPOSTER,
            AFKParticle.FALLING_LAVA,
            AFKParticle.LANDING_LAVA,
            AFKParticle.FALLING_WATER,
            AFKParticle.DRIPPING_HONEY,
            AFKParticle.FALLING_HONEY,
            AFKParticle.LANDING_HONEY,
            AFKParticle.FALLING_NECTAR,
            AFKParticle.SOUL_FIRE_FLAME,
            AFKParticle.ASH,
            AFKParticle.CRIMSON_SPORE,
            AFKParticle.WARPED_SPORE,
            AFKParticle.SOUL,
            AFKParticle.DRIPPING_OBSIDIAN_TEAR,
            AFKParticle.FALLING_OBSIDIAN_TEAR,
            AFKParticle.LANDING_OBSIDIAN_TEAR,
            AFKParticle.REVERSE_PORTAL,
            AFKParticle.WHITE_ASH,
	};

	/**
	 * 投票数から使用可能なパーティクル一覧を取得する。
	 * @param voteCount 投票数
	 * @return 利用可能パーティクルの配列
	 */
	public static AFKParticle[] getAvailableAFKParticle(final int voteCount) {
		if (voteCount < 20) {
			return new AFKParticle[] { particles[0] };
		}
		int vote = ((voteCount - 16) / 4) + 1;
		List<AFKParticle> p = new ArrayList<>();
		for (int i = 0; i < vote; i++) {
			if (i >= particles.length) {
				break;
			}
			p.add(particles[i]);
		}
		AFKParticle[] ret = new AFKParticle[p.size()];
		p.toArray(ret);
		return ret;
	}

	/**
	 * 投票数から使用可能なパーティクル(最後に開放されたもの)を取得する。
	 * @param voteCount 投票数
	 * @return 利用可能パーティクルの配列
	 */
	public static AFKParticle getAvailableLastAFKParticle(final int voteCount) {
		if (voteCount < 20) {
			return particles[0];
		}
		int vote = ((voteCount - 16) / 4) + 1;
		List<AFKParticle> p = new ArrayList<>();
		for (int i = 0; i < vote; i++) {
			if (i >= particles.length) {
				break;
			}
			p.add(particles[i]);
		}
		return p.get(p.size() - 1);
	}

	/**
	 * MinecraftIDなどをDiscord用にエスケープする。
	 * @param text　MinecraftID等
	 * @return エスケープした文字列
	 */
	public static String DiscordEscape(String text) {
		return text == null ? "" : text.replace("_", "\\_").replace("*", "\\*").replace("~", "\\~");
	}
}
