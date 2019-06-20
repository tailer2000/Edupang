package com.example.game;

import com.example.myframework.AppManager;
import com.example.myframework.R;

public class Enemy_5 extends Enemy {
    public Enemy_5() {
        super(AppManager.getInstance().getBitmap(R.drawable.monster_5));
        //this.InitSpriteData(900, 785, 1,54);
        state = Enemy.STATE_OUT;
        hp = 100;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
    }

    @Override
    public int GetHP() {
        return super.GetHP();
    }
}
