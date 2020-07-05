package com.jaoafa.AwayFromKeyboard.Task;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.jaoafa.AwayFromKeyboard.Library.AFKParticle;

public class Task_Part extends BukkitRunnable {
	private Location loc;
	private AFKParticle particle;
	int count;
	double extra;
	double offsetX;
	double offsetY;
	double offsetZ;
	double SpawnOffset;
	long startTime;

	public Task_Part(Location loc, AFKParticle particle, int count, double extra, double offsetX, double offsetY,
			double offsetZ, double SpawnOffset) {
		this.loc = loc;
		this.particle = particle;
		this.count = count;
		this.extra = extra;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.SpawnOffset = SpawnOffset;

		this.startTime = System.currentTimeMillis();
	}

	public void run() {
		if (System.currentTimeMillis() - this.startTime >= 10000L) {
			cancel();
			return;
		}
		Location _loc = this.loc.clone();
		if (this.SpawnOffset != 0.0D) {
			_loc = _loc.add(0.0D, this.SpawnOffset, 0.0D);
		}
		_loc.getWorld().spawnParticle(this.particle.getParticle(), _loc, this.count,
				this.offsetX, this.offsetY, this.offsetZ, this.extra);
	}
}
