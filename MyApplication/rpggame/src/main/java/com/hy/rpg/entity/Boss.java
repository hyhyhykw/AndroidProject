package com.hy.rpg.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * 
 * @author HY
 *
 */
public class Boss implements Serializable {
	public final String[] bossName = { "沉着的狼人战士", "疯狂的牛头人", "黑暗神之子", "复活的美杜莎",
			"黑暗神" };
	public final int[] bossHp = { 100, 200, 300, 400, 500 };
	public final int[] bossExp = { 1000, 2000, 3000, 4000, 5000 };
	public final int[] bossAtt = { 70, 110, 150, 190, 220 };
	public final int[] bossDef = { 35, 80, 125, 170, 190 };
	public final String[] bossSkill = { "普通攻击", "斩影之刺", "绝命毒刺", "毒之深渊", "生命汲取" };
}
