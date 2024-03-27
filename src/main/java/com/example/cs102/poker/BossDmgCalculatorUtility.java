package com.example.cs102.poker;

import com.example.cs102.boss.Boss;

public class BossDmgCalculatorUtility extends ComboUtility {
    public static int bossDamageCalculation(String comboMove, Boss boss) {
        int baseDamage = 0;

        int comboDamage = getDamageValue(comboMove);

        switch (boss.getDifficulty()) {
            case "EASY":
                baseDamage = 1;
                comboDamage *= 0.2;
                break;
            case "NORMAL":
                baseDamage = 5;
                break;
            case "HARD":
                baseDamage = 20;
                comboDamage *= 2;
                break;
            case "ASIAN":
                baseDamage = 50; // literally one hit KO regardless of hand for new players
                comboDamage *= 4;
                break;
            default:
                // unknown case
                break;
        }
        return baseDamage + comboDamage;
    }
}
