package com.hy.rpg.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * 
 * @author HY
 *
 */
public class Monster implements Serializable {
	final String[] monsterName = { "骡鹿", "森林狼", "棕熊", "绿色黏液怪", "水晶蜘蛛", "猛犸象",
			"灰熊", "幽灵", "冤魂", "地狱犬", "", "", "", "", "", "", "", "", "", "" };
	final int[] monsterLevel = { 1, 2, 3, 5, 8, 10, 13, 15, 17, 20, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0 };
	final int[] monsterHP = { 30, 35, 40, 50, 55, 60, 80, 100, 110, 115, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0 };
	int[] monsterAtt = new int[20];
	int[] monsterDef = new int[20];
	final int[] monsterEXP = { 50, 80, 125, 137, 155, 193, 235, 330, 455, 500,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	{// 怪物攻击
		monsterAtt[0] = 30;
		for (int i = 1; i < 20; i++) {
			if (i < 10) {
				monsterAtt[i] = monsterAtt[i] + 2;
			} else {
				monsterAtt[i] = 0;
			}
		} // 怪物攻击
	}
	{// 怪物防御
		monsterDef[0] = 15;
		for (int i = 1; i < 20; i++) {
			if (i < 10) {
				monsterDef[i] = monsterDef[i] + 3;
			} else {
				monsterDef[i] = 0;
			}
		}
	}// 怪物防御
}
