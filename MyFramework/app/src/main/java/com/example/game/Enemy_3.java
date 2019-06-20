package com.example.game;

import com.example.myframework.AppManager;
import com.example.myframework.R;

public class Enemy_3 extends Enemy {
    public Enemy_3() {
        super(AppManager.getInstance().getBitmap(R.drawable.monster_3));
        //this.InitSpriteData(900, 785, 1,8);
        state = Enemy.STATE_OUT;
        hp = 100;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
    }

    @Override
    public void Damage(int damage) {
        if(hp > 0) {
            hp -= damage;
            System.out.println(String.valueOf(hp));
        }
        else{
            state = STATE_OUT;
        }
    }

    @Override
    public int GetHP() {
        return super.GetHP();
    }
}
