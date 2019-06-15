package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.myframework.AppManager;
import com.example.myframework.SpriteAnimation;

public class Enemy extends SpriteAnimation {

	public static final int STATE_NORMAL = 0;
	public static final int STATE_OUT = 1;
	
	public int state =  STATE_NORMAL;

	Rect m_BoundBox = new Rect();
	
	public static final int MOVE_PATTERN_1 = 0;
	public static final int MOVE_PATTERN_2 = 1;
	public static final int MOVE_PATTERN_3 = 2;

	protected int movetype;
	
	protected int hp;
	protected float attackSpeed;
	

	long LastShoot = System.currentTimeMillis();
	
	public Enemy(Bitmap bitmap) {
		super(bitmap);
	}
	public void Damage(int damage){
		if(hp > 0) {
			hp -= damage;
			System.out.println(String.valueOf(hp));
		}
		else{
			state = STATE_OUT;
		}
	}
	public int GetHP(){
		return hp;
	}
	
	void Attack(){

	}
	
	@Override
	public void Update(long GameTime){
		super.Update(GameTime);
	}
	
}
