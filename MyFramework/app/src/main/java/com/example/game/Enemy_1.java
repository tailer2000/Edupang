package com.example.game;

import android.graphics.Bitmap;

import com.example.myframework.AppManager;
import com.example.myframework.R;

public class Enemy_1 extends Enemy {

    public Enemy_1() {
        // super(bitmap);
        super(AppManager.getInstance().getBitmap(R.drawable.monster_1));
        this.InitSpriteData(900, 785, 1,4);
        state = Enemy.STATE_OUT;
        hp = 100;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
    }

}
