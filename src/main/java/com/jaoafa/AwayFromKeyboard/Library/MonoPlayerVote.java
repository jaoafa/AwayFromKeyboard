package com.jaoafa.AwayFromKeyboard.Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.jaoafa.AwayFromKeyboard.Main;

public class MonoPlayerVote {
	static Map<UUID, MonoPlayerVote> data = new HashMap<>();

	Player player;
	int voteCount = 0;
	AFKParticle selectedParticle = null;
	AFKParticle[] availableAFKParticles = new AFKParticle[] {};
	String customize = null; // ?

	long DBSyncTime = -1L;

	public MonoPlayerVote(Player player) {
		if (data.containsKey(player.getUniqueId())) {
			MonoPlayerVote mpv = data.get(player.getUniqueId());
			this.voteCount = mpv.getVoteCount();
			this.selectedParticle = mpv.getSelectedParticle();
			this.availableAFKParticles = mpv.getAvailableAFKParticles();
			this.customize = mpv.getCustomize();
			this.DBSyncTime = mpv.getDBSyncTime();
		}
		this.player = player;
		DBSync();
	}

	public Player getPlayer() {
		return player;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public AFKParticle getSelectedParticle() {
		return selectedParticle;
	}

	public AFKParticle[] getAvailableAFKParticles() {
		return availableAFKParticles;
	}

	public String getCustomize() {
		return customize;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public void setSelectedParticle(AFKParticle selectedParticle) {
		// check つかえるパーティクル
		if (Main.getMySQLDBManager() == null) {
			throw new IllegalStateException("Main.MySQLDBManager == null");
		}
		try {
			Connection conn = Main.getMySQLDBManager().getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE vote_monocraft SET afkparticle = ? WHERE uuid = ?");
			statement.setString(1, selectedParticle.name());
			statement.setString(2, player.getUniqueId().toString());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.selectedParticle = selectedParticle;
		data.put(player.getUniqueId(), this);
	}

	public void setSelectedParticle(AFKParticle selectedParticle, String customize) {
		if (Main.getMySQLDBManager() == null) {
			throw new IllegalStateException("Main.MySQLDBManager == null");
		}
		try {
			Connection conn = Main.getMySQLDBManager().getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE vote_monocraft SET afkparticle = ? WHERE uuid = ?");
			statement.setString(1, selectedParticle.name() + ":" + customize);
			statement.setString(2, player.getUniqueId().toString());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.selectedParticle = selectedParticle;
		data.put(player.getUniqueId(), this);
	}

	public void setAvailableAFKParticles(AFKParticle[] availableAFKParticles) {
		this.availableAFKParticles = availableAFKParticles;
	}

	public void setCustomize(String customize) {
		this.customize = customize;
	}

	public long getDBSyncTime() {
		return DBSyncTime;
	}

	/**
	 * このユーザーのデータをデータベースから取得します。<br>
	 * 取得前に前回の取得から30分を経過しているかを調べ、経過していなければなにもしません。
	 */
	public void DBSync() {
		DBSync(false);
	}

	/**
	 * このユーザーのデータをデータベースから取得します。<br>
	 * forceがfalseの場合、取得前に前回の取得から30分を経過しているかを調べ、経過していなければなにもしません。
	 * @param force 30分経過をチェックせずに強制的に取得するか
	 */
	public void DBSync(boolean force) {
		if (!force && ((DBSyncTime + 30 * 60 * 1000) > System.currentTimeMillis())) {
			return; // 30分未経過
		}
		if (Main.getMySQLDBManager() == null) {
			throw new IllegalStateException("Main.MySQLDBManager == null");
		}
		try {
			Connection conn = Main.getMySQLDBManager().getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM vote_monocraft WHERE uuid = ? ORDER BY id DESC LIMIT 1");
			statement.setString(1, player.getUniqueId().toString());
			ResultSet res = statement.executeQuery();
			if (res.next()) {
				this.voteCount = res.getInt("count");

				if (res.getString("afkparticle") == null) {
					this.selectedParticle = null;
				} else if (res.getString("afkparticle").contains(":")) {
					this.selectedParticle = AFKParticle.fromString(res.getString("afkparticle").split(":")[0]);
					this.customize = res.getString("afkparticle").split(":")[1];
				} else {
					this.selectedParticle = AFKParticle.fromString(res.getString("afkparticle"));
				}
				this.availableAFKParticles = Library.getAvailableAFKParticle(this.voteCount);
			} else {
				this.voteCount = 0;
				this.selectedParticle = null;
				this.customize = null;
				this.availableAFKParticles = Library.getAvailableAFKParticle(this.voteCount);
			}
			res.close();
			statement.close();

			DBSyncTime = System.currentTimeMillis();
		} catch (SQLException e) {
			e.printStackTrace();

			this.voteCount = 0;
			this.selectedParticle = null;
			this.customize = null;
			this.availableAFKParticles = Library.getAvailableAFKParticle(this.voteCount);
		}
		data.put(player.getUniqueId(), this);
	}
}
