package com.example.game;

import com.example.myframework.AppManager;
import com.example.myframework.R;

public class Enemy_4 extends Enemy {
    public Enemy_4() {
        super(AppManager.getInstance().getBitmap(R.drawable.monster_4));
        //this.InitSpriteData(900, 785, 1,11);
        state = Enemy.STATE_OUT;
        hp = 100;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
    }
}
