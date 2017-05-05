package com.hy.rpg.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * roles
 * @author HY
 *
 */
public class Role implements Serializable {
	
	/**
	 * initial level
	 */
	public int level = 1;
	
	/**
	 * three jobs
	 */
	public final String[] roleJob = { "狂战士", "法师", "弓箭手" };
	
	/**
	 * skills
	 */
	public final String[][] roleSkill = { { "普通攻击", "狂乱突击", "血之渴望", "盾牌格挡" },
			{ "普通攻击", "爆裂火球", "魔法风暴", "九天玄雷" },
			{ "普通攻击", "死亡狙击", "火焰之箭", "万箭齐发" } };
	
	/**
	 * mp cost
	 */
	public final int[] skillMp = { -10, 20, 50, 100 };
	
	/**
	 * initial hp
	 */
	int[] roleHp = { 200, 150, 150 };
	
	/**
	 * initial mp
	 */
	int[] roleMp = { 100, 200, 150 };
	
	/**
	 * initial defense
	 */
	int[] roleDef = { 20, 10, 15 };
	
	/**
	 * initial attack
	 */
	int[] roleAtt = { 10, 20, 15 };

	public final int[] levelExp = new int[50];
	{// 升级所需经验值
		levelExp[0] = 15;
		levelExp[1] = 23;
		for (int i = 2; i < 50; i++) {
			if (i < 19) {
				levelExp[i] = levelExp[i - 1] + levelExp[i - 2];
			} else {
				levelExp[i] = (int) (levelExp[i - 1] * 1.1);
			}
		} // 升级所需经验值
	}
}
