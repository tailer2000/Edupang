package com.example.game;

import android.graphics.Bitmap;

import com.example.myframework.AppManager;
import com.example.myframework.R;
import com.example.myframework.SpriteAnimation;

public class Effect_Explosion extends SpriteAnimation {

	public Effect_Explosion(int x , int y) {
		super(AppManager.getInstance().getBitmap(R.drawable.explosion));
		this.InitSpriteData(104	, 66, 3, 6);
		
		m_x = x;
		m_y = y;
		
		mbReply = false;
	}

}
