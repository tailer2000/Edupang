package com.example.game;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Debug;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ProgressBar;

import com.example.myframework.AppManager;
import com.example.myframework.Collision;
import com.example.myframework.CollisionManager;
import com.example.myframework.GraphicObject;
import com.example.myframework.IState;
import com.example.myframework.R;

public class GameState implements IState {

	public static final int GAMESTART_SCENE = 0;
	public static final int BATTLE_SCENE = 1;
	public static final int COLLECTION_SCENE = 2;
	public static final int END_SCENE = 3;

	private long m_LastShoot = System.currentTimeMillis();
	private Player m_player;
	private BackGround m_background;
	private GraphicObject m_battleground;
	// 계산기 대입
	private Calcultation cal;

	private GraphicObject m_keypad;
    private GraphicObject m_shootpad;
    private GraphicObject m_title;
    private GraphicObject m_startButton;
	private GraphicObject m_plusButton;
	private GraphicObject m_minusButton;
	private GraphicObject m_mutipleButton;
	private GraphicObject m_divideButton;
    private Enemy_1 m_monster_1;

    //int time = 0;
	//ProgressBar bar;

	public int m_score = 0;
	public int tile_x = 0;
	public int tile_y = 0;

	public int state = GAMESTART_SCENE;

	long m_LastRegenEnemy = System.currentTimeMillis();

	Random m_randEnem = new Random();
	Random m_randItem = new Random();
	
	public GameState(){
		AppManager.getInstance().m_gamestate = this;
	}
	
	@Override
	public void Init() throws Exception {
		m_title = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.title));
		// background
		m_background = new BackGround(0);
		m_battleground = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.monsterground));
		// button
		m_startButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.gamestart_release));
		m_plusButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.plus_released));
		m_minusButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.minus_released));
		m_mutipleButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.multiple_released));
		m_divideButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.divide_released));
		// enemy
		m_monster_1 = new Enemy_1();
		cal = new Calcultation();
	}

	public void CheckCollision(){

	}

	public void MakeEnemy(){

	}

	public void MakeTest() throws Exception {
		cal.MakeAnwser();
	}
	
	@Override
	public void Render(Canvas canvas) {
		Paint p = new Paint();
		p.setTextSize((int)tile_x*15);
		m_background.Draw(canvas);
    	if(state == GAMESTART_SCENE)
		{
			m_title.Draw(canvas);
			m_startButton.Draw(canvas);
		}
    	else if(state == BATTLE_SCENE)
		{
			m_battleground.DrawRect(canvas);
			m_monster_1.Draw(canvas);
			// button
			m_plusButton.Draw(canvas);
			m_divideButton.Draw(canvas);
			m_mutipleButton.Draw(canvas);
			m_minusButton.Draw(canvas);
			canvas.drawText(cal.GetFormula(),tile_x*7, tile_y*40, p);
			canvas.drawText(cal.GetTestString(),tile_x*7, tile_y*50, p);
			canvas.drawText(cal.GetAnwserString(), tile_x*25, tile_y*60, p);
		}
		else if(state == END_SCENE)
		{

		}
		else if(state == COLLECTION_SCENE)
		{

		}

		//canvas.drawText("Score :"+String.valueOf(m_score),0,40,p);
	}

	public void TileMap_Init()
	{
		tile_x = AppManager.getInstance().getWidth()/100;
		tile_y = AppManager.getInstance().getHeight()/100;
	}

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        m_background.Update(GameTime);
        if(state == GAMESTART_SCENE)
		{
			TileMap_Init();
			m_title.SetPosition(tile_x*13, tile_y*15);
			m_startButton.SetPosition(tile_x*6, tile_y*50);
		}
		else if(state == BATTLE_SCENE)
		{
			m_monster_1.Update(GameTime);
			// button
			m_plusButton.SetPosition(tile_x*5, tile_y*80);
			m_minusButton.SetPosition(tile_x*30, tile_y*80);
			m_mutipleButton.SetPosition(tile_x*55, tile_y*80);
			m_divideButton.SetPosition(tile_x*80, tile_y*80);
		}
		else if(state == END_SCENE)
		{

		}
		else if(state == COLLECTION_SCENE)
		{

		}
        MakeEnemy();
        CheckCollision();
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}


	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		

		for(int i =0; i<event.getPointerCount();i++){
			int _x = (int) event.getX(i);
			int _y = (int) event.getY(i);

			if(state == GAMESTART_SCENE)
			{
				//click start button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_startButton.GetX(), m_startButton.GetY(),
						m_startButton.GetX() + tile_x * 80, m_startButton.GetY() + tile_y * 20)){
					m_startButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.gamestart_click));
					state = BATTLE_SCENE;
					ChangeBattleScene();
				}
				else
				{
				}
			}
			else if(state == BATTLE_SCENE)
			{
				//click plus button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_plusButton.GetX(), m_plusButton.GetY(),
						m_plusButton.GetX() + tile_x * 15, m_plusButton.GetY() + tile_y * 8)){
					m_plusButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.plus_click));
				}
				//click minus button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_minusButton.GetX(), m_minusButton.GetY(),
						m_minusButton.GetX() + tile_x * 15, m_minusButton.GetY() + tile_y * 8)){
					m_minusButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.minus_click));
				}
				//click multiple button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_mutipleButton.GetX(), m_mutipleButton.GetY(),
						m_mutipleButton.GetX() + tile_x * 15, m_mutipleButton.GetY() + tile_y * 8)){
					m_mutipleButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.multiple_click));
				}
				//click divide button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_divideButton.GetX(), m_divideButton.GetY(),
						m_divideButton.GetX() + tile_x * 15, m_divideButton.GetY() + tile_y * 8)){
					m_divideButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.divide_click));
				}
			}
			else if(state == END_SCENE)
			{

			}
			else if(state == COLLECTION_SCENE)
			{

			}


			if(Collision.CollisionCheckPointToBox(_x,_y, 40, 345, 80, 385)){
			}
			if(Collision.CollisionCheckPointToBox(_x,_y, 80, 385, 120, 425)){
			}
			if(Collision.CollisionCheckPointToBox(_x,_y, 40, 425, 80, 465)){
			}
		}
		return true;
	}

	public void ChangeBattleScene()
	{
		m_battleground.SetRectPosition(0,0, AppManager.getInstance().getWidth(), tile_y *30);
		m_monster_1.SetPosition(tile_x*20, tile_y);
	}

	@Override
	public void Destroy() {
		// TODO Auto-generated method stub
		
	}


}
