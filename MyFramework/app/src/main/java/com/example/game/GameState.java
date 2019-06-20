package com.example.game;

import java.util.ArrayList;
import java.util.Random;


import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Debug;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.myframework.AppManager;
import com.example.myframework.Collision;
import com.example.myframework.CollisionManager;
import com.example.myframework.GraphicObject;
import com.example.myframework.IState;
import com.example.myframework.R;
import com.example.myframework.SoundManager;

public class GameState implements IState {

	public static final int GAMESTART_SCENE = 0;
	public static final int BATTLE_SCENE = 1;
	public static final int COLLECTION_SCENE = 2;
	public static final int END_SCENE = 3;

	private long m_LastShoot = System.currentTimeMillis();
	private Player m_player;
	private BackGround m_title;
	private BackGround m_background;
    private BackGround m_gallery_background;
    private BackGround m_end;
	private GraphicObject m_battleground;
	// 계산기 대입
	private Calcultation cal;
	private String formula_first = "";
	private String formula_second = "";
	private String formula_third = "";
	private String formula_four = "";
	private int formula_count = 0;

	//입력값 늦추기 위한 변수
	private long afterTime = System.currentTimeMillis();

	private GraphicObject m_keypad;
    private GraphicObject m_shootpad;
    private GraphicObject m_startButton;
    private GraphicObject m_galleryButton;
    private GraphicObject m_quitButton;
	private GraphicObject m_plusButton;
	private GraphicObject m_minusButton;
	private GraphicObject m_mutipleButton;
	private GraphicObject m_divideButton;
	private GraphicObject m_cancle;
	private GraphicObject m_endicon;

	private Enemy_1 m_monster_1;
	private Enemy_1 m_monster_2;
	private Enemy_1 m_monster_3;
	private Enemy_1 m_monster_4;
	private Enemy_1 m_monster_5;

    private GraphicObject m_progressbar_empty;
    private GraphicObject m_progressbar_hp;
    private GraphicObject m_hp;

	private ArrayList<Enemy> m_monster_list = new ArrayList<Enemy>();
	private Enemy m_monster;

	private Context m_context;

	float current_hp_right = 0;


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

		//사운드를 위한 컨텍스트 받아오기
		//피피티 4장 보기
		m_context = AppManager.getInstance().getContext();
		SoundManager.getInstance().Init(m_context);


		//m_title = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.title));

		// background
		m_title = new BackGround(0);
		m_background = new BackGround(1);
        m_gallery_background = new BackGround(2);
        m_end = new BackGround(3);
		m_battleground = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.monsterground));
		// button
		m_startButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.gamestart));
        m_galleryButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.gallery));
        m_quitButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.gamequit));
        // button(battle)
		m_plusButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.plus_released));
		m_minusButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.minus_released));
		m_mutipleButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.multiple_released));
		m_divideButton = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.divide_released));
		m_cancle = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.resetbutton));
		// Time & HP
        m_progressbar_empty = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.progressbar_empty));
        m_progressbar_hp = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.progressbar_hp));
        m_hp = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.heart));
        // endscene
		m_endicon = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.end_icon));
		// enemy
		m_monster_1 = new Enemy_1();
		m_monster_1.state = Enemy.STATE_NORMAL;

		m_monster_2 = new Enemy_1();
		m_monster_2.state = Enemy.STATE_OUT;

		m_monster_3 = new Enemy_1();
		m_monster_3.state = Enemy.STATE_OUT;

		m_monster_4 = new Enemy_1();
		m_monster_4.state = Enemy.STATE_OUT;

		m_monster_5 = new Enemy_1();
		m_monster_5.state = Enemy.STATE_OUT;

		cal = new Calcultation();
	}

	// 플레이어 체력 및 제한시간
	public void Life_Timer(){
		current_hp_right = m_progressbar_hp.GetRight();

		if(current_hp_right <= 0)
		{
			state = END_SCENE;
			m_endicon.SetRectPosition(tile_x*30, tile_y*20, tile_x*80, tile_y*70);
		}
		current_hp_right = current_hp_right - 1;
        m_progressbar_hp.SetRectPosition(tile_x*10, tile_y*33, (int)current_hp_right, tile_y*35);
	}

	public void CheckCollision(){

	}

	public void MakeEnemy(){

			Random random = new Random();
			int randInt = random.nextInt(5);
			switch (randInt){
				case 0:
					m_monster_1.SetHp(100);
					m_monster_1.state = Enemy.STATE_NORMAL;
					break;
				case 1:
					m_monster_2.SetHp(100);
					m_monster_2.state = Enemy.STATE_NORMAL;
					break;
				case 2:
					m_monster_3.SetHp(100);
					m_monster_3.state = Enemy.STATE_NORMAL;
					break;
				case 3:
					m_monster_4.SetHp(100);
					m_monster_4.state = Enemy.STATE_NORMAL;
					break;
				case 4:
					m_monster_5.SetHp(100);
					m_monster_5.state = Enemy.STATE_NORMAL;

					Enemy monster_1 = new Enemy_1();
					monster_1.SetPosition(tile_x*20, tile_y);
					m_monster_list.add(monster_1);
					break;
			}
	}

	public void MakeTest() throws Exception {
		cal.MakeAnwser();
	}

	@Override
	public void Render(Canvas canvas) {
		Paint p = new Paint();
		p.setTextSize((int)tile_x*15);

    	if(state == GAMESTART_SCENE)
		{
			m_title.Draw(canvas);
			m_startButton.Draw(canvas);
            m_galleryButton.Draw(canvas);
            m_quitButton.Draw(canvas);
		}
    	else if(state == BATTLE_SCENE)
		{
			m_background.Draw(canvas);
			m_battleground.DrawRect(canvas);

			if(m_monster_1.state == Enemy.STATE_NORMAL)
				m_monster_1.Draw(canvas);
			else if(m_monster_2.state == Enemy.STATE_NORMAL)
				m_monster_2.Draw(canvas);
			else if(m_monster_3.state == Enemy.STATE_NORMAL)
				m_monster_3.Draw(canvas);
			else if(m_monster_4.state == Enemy.STATE_NORMAL)
				m_monster_4.Draw(canvas);
			else if(m_monster_5.state == Enemy.STATE_NORMAL)
				m_monster_5.Draw(canvas);

			// button
			m_plusButton.Draw(canvas);
			m_divideButton.Draw(canvas);
			m_mutipleButton.Draw(canvas);
			m_minusButton.Draw(canvas);
			m_cancle.Draw(canvas);

			// Life_Timer
            m_progressbar_empty.Draw(canvas);
            m_progressbar_hp.DrawRect(canvas);
            m_hp.Draw(canvas);

			//canvas.drawText(cal.GetFormula(),tile_x*7, tile_y*40, p);
			canvas.drawText(cal.GetTestString(),tile_x*7, tile_y*50, p);
			canvas.drawText(cal.GetAnwserString(), tile_x*25, tile_y*60, p);

			if(formula_count > 0){
				canvas.drawText(formula_first, tile_x*17, tile_y*50, p);
				if(formula_count > 1){
					canvas.drawText(formula_second, tile_x*37, tile_y*50, p);
					if(formula_count > 2){
						canvas.drawText(formula_third, tile_x*57, tile_y*50, p);
						if(formula_count > 3) {
							canvas.drawText(formula_four, tile_x*77, tile_y*50, p);
						}
					}
				}
			}

		}
		else if(state == END_SCENE)
		{
		    m_end.Draw(canvas);
		    m_endicon.DrawRect(canvas);
            m_cancle.Draw(canvas);
		}
		else if(state == COLLECTION_SCENE)
		{
		    m_gallery_background.Draw(canvas);
            m_cancle.Draw(canvas);
		}

		//canvas.drawText("Score :"+String.valueOf(m_score),0,40,p);
	}

	public void TileMap_Init()
	{
		tile_x = AppManager.getInstance().getWidth()/100;
		tile_y = AppManager.getInstance().getHeight()/100;
	}

	public void RightAnwser() throws Exception {

		if(m_monster_1.state != Enemy.STATE_OUT) {
			m_monster_1.Damage(25);
			if(m_monster_1.state == Enemy.STATE_OUT)
				MakeEnemy();
		}
		else if (m_monster_2.state != Enemy.STATE_OUT){
			m_monster_2.Damage(25);
			if(m_monster_2.state == Enemy.STATE_OUT)
				MakeEnemy();
		}
		else if (m_monster_3.state != Enemy.STATE_OUT){
			m_monster_3.Damage(25);
			if(m_monster_3.state == Enemy.STATE_OUT)
				MakeEnemy();
		}
		else if (m_monster_4.state != Enemy.STATE_OUT){
			m_monster_4.Damage(25);
			if(m_monster_4.state == Enemy.STATE_OUT)
				MakeEnemy();
		}
		else if (m_monster_5.state != Enemy.STATE_OUT){
			m_monster_5.Damage(25);
			if(m_monster_5.state == Enemy.STATE_OUT)
				MakeEnemy();
		}

		formula_first = "";
		formula_second = "";
		formula_third = "";
		formula_four = "";
		formula_count = 0;
		MakeTest();
		current_hp_right = m_progressbar_hp.GetRight() + 30;
	}

	public void WrongAnwser()
	{
		formula_first = "";
		formula_second = "";
		formula_third = "";
		formula_four = "";
		formula_count = 0;

		//나중에 바꿀 곳
		if(m_monster_1.state == Enemy.STATE_NORMAL)
			m_monster_1.Attack();
		else if(m_monster_2.state == Enemy.STATE_NORMAL)
			m_monster_2.Attack();
		else if(m_monster_3.state == Enemy.STATE_NORMAL)
			m_monster_3.Attack();
		else if(m_monster_4.state == Enemy.STATE_NORMAL)
			m_monster_4.Attack();
		else if(m_monster_5.state == Enemy.STATE_NORMAL)
			m_monster_5.Attack();

		current_hp_right = m_progressbar_hp.GetRight() - 20;
	}

    @Override
    public void Update() throws Exception {
        long GameTime = System.currentTimeMillis();
        m_background.Update(GameTime);
        if(state == GAMESTART_SCENE)
		{
			TileMap_Init();
			m_startButton.SetPosition(tile_x*32, tile_y*40);
            m_galleryButton.SetPosition(tile_x*32, tile_y*55);
            m_quitButton.SetPosition(tile_x*32, tile_y*70);
		}
		else if(state == BATTLE_SCENE)
		{
			if(m_monster_1.state == Enemy.STATE_NORMAL)
				m_monster_1.Update(GameTime);
			else if(m_monster_2.state == Enemy.STATE_NORMAL)
				m_monster_2.Update(GameTime);
			else if(m_monster_3.state == Enemy.STATE_NORMAL)
				m_monster_3.Update(GameTime);
			else if(m_monster_3.state == Enemy.STATE_NORMAL)
				m_monster_4.Update(GameTime);
			else if(m_monster_3.state == Enemy.STATE_NORMAL)
				m_monster_5.Update(GameTime);


			Life_Timer();

			//식을 전부 채웠을 때
			if(formula_count == 4)
			{
				String str = "";
				ArrayList<Integer> numList = cal.GetNumList();
				str +=  String.valueOf(numList.get(0));

				if(formula_first == "×")
					str += "*";
				else if(formula_first == "÷")
					str += "/";
				else
					str += formula_first;

				str +=  String.valueOf(numList.get(1));

				if(formula_second == "×")
					str += "*";
				else if(formula_second == "÷")
					str += "/";
				else
					str += formula_second;

				str +=  String.valueOf(numList.get(2));

				if(formula_third == "×")
					str += "*";
				else if(formula_third == "÷")
					str += "/";
				else
					str += formula_third;

				str +=  String.valueOf(numList.get(3));

				if(formula_four == "×")
					str += "*";
				else if(formula_four == "÷")
					str += "/";
				else
					str += formula_four;

				str +=  String.valueOf(numList.get(4));

				if(cal.GetRealAnwser() == Integer.valueOf(cal.bracketCalMain(str))){
					System.out.println("정답");
					RightAnwser();
				}
				else {
					System.out.println("틀림");
					WrongAnwser();
				}

			}

		}
		else if(state == END_SCENE)
		{

		}
		else if(state == COLLECTION_SCENE)
		{

		}
        //MakeEnemy();
        //CheckCollision();
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}


	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		long CurruntTime = System.currentTimeMillis();
		if(CurruntTime - afterTime < 100)
			return false;
		afterTime = CurruntTime;

		for(int i =0; i<event.getPointerCount();i++){
			int _x = (int) event.getX(i);
			int _y = (int) event.getY(i);

			if(state == GAMESTART_SCENE)
			{
				//click start button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_startButton.GetX(), m_startButton.GetY(),
						m_startButton.GetX() + tile_x * 35, m_startButton.GetY() + tile_y * 7)){
					//m_startButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.gamestart_click));
					state = BATTLE_SCENE;
					ChangeBattleScene();
				}
				// click galleryButton
                if(Collision.CollisionCheckPointToBox(_x,_y, m_galleryButton.GetX(), m_galleryButton.GetY(),
                        m_galleryButton.GetX() + tile_x * 35, m_galleryButton.GetY() + tile_y * 7)){
                    state = COLLECTION_SCENE;
                    m_cancle.SetPosition(0, 0);
                }
                // click quitButton
                if(Collision.CollisionCheckPointToBox(_x,_y, m_quitButton.GetX(), m_quitButton.GetY(),
                        m_quitButton.GetX() + tile_x * 35, m_quitButton.GetY() + tile_y * 7)){
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

			}
			else if(state == BATTLE_SCENE)
			{
				//click plus button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_plusButton.GetX(), m_plusButton.GetY(),
						m_plusButton.GetX() + tile_x * 15, m_plusButton.GetY() + tile_y * 8)){
					//m_plusButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.plus_click));

					if(formula_count < 4){
						switch (formula_count){
							case 0:
								formula_first = "+";
								break;
							case 1:
								formula_second = "+";
								break;
							case 2:
								formula_third = "+";
								break;
							case 3:
								formula_four = "+";
								break;
						}
						formula_count++;
					}

				}
				//click minus button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_minusButton.GetX(), m_minusButton.GetY(),
						m_minusButton.GetX() + tile_x * 15, m_minusButton.GetY() + tile_y * 8)){
					//m_minusButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.minus_click));

					if(formula_count < 4){
						switch (formula_count){
							case 0:
								formula_first = "-";
								break;
							case 1:
								formula_second = "-";
								break;
							case 2:
								formula_third = "-";
								break;
							case 3:
								formula_four = "-";
								break;
						}
						formula_count++;
					}

				}
				//click multiple button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_mutipleButton.GetX(), m_mutipleButton.GetY(),
						m_mutipleButton.GetX() + tile_x * 15, m_mutipleButton.GetY() + tile_y * 8)){
					//m_mutipleButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.multiple_click));

					if(formula_count < 4){
						switch (formula_count){
							case 0:
								formula_first = "×";
								break;
							case 1:
								formula_second = "×";
								break;
							case 2:
								formula_third = "×";
								break;
							case 3:
								formula_four = "×";
								break;
						}
						formula_count++;
					}
				}
				//click divide button
				if(Collision.CollisionCheckPointToBox(_x,_y, m_divideButton.GetX(), m_divideButton.GetY(),
						m_divideButton.GetX() + tile_x * 15, m_divideButton.GetY() + tile_y * 8)){
					//m_divideButton.ChangeBitmap(AppManager.getInstance().getBitmap(R.drawable.divide_click));

					if(formula_count < 4){
						switch (formula_count){
							case 0:
								formula_first = "÷";
								break;
							case 1:
								formula_second = "÷";
								break;
							case 2:
								formula_third = "÷";
								break;
							case 3:
								formula_four = "÷";
								break;
						}
						formula_count++;
					}
				}

				if(Collision.CollisionCheckPointToBox(_x,_y, m_cancle.GetX(), m_cancle.GetY(),
						m_cancle.GetX() + tile_x * 15, m_cancle.GetY() + tile_y * 8)){
					if(formula_count > 0){
						switch (formula_count){
							case 1:
								formula_first = "";
								break;
							case 2:
								formula_second = "";
								break;
							case 3:
								formula_third = "";
								break;
							case 4:
								formula_four = "";
								break;
						}
						formula_count--;
					}
				}

			}
			else if(state == END_SCENE)
			{
                if(Collision.CollisionCheckPointToBox(_x,_y, m_cancle.GetX(), m_cancle.GetY(),
                        m_cancle.GetX() + tile_x * 15, m_cancle.GetY() + tile_y * 8)){
                    state = GAMESTART_SCENE;
                }
			}
			else if(state == COLLECTION_SCENE)
			{
                if(Collision.CollisionCheckPointToBox(_x,_y, m_cancle.GetX(), m_cancle.GetY(),
                        m_cancle.GetX() + tile_x * 15, m_cancle.GetY() + tile_y * 8)){
                    state = GAMESTART_SCENE;
                }
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
		//MakeEnemy();
		m_battleground.SetRectPosition(0,0, AppManager.getInstance().getWidth(), tile_y *30);
		m_monster_1.SetPosition(tile_x*20, tile_y);
		m_monster_2.SetPosition(tile_x*20, tile_y);
		m_monster_3.SetPosition(tile_x*20, tile_y);
		m_monster_4.SetPosition(tile_x*20, tile_y);
		m_monster_5.SetPosition(tile_x*20, tile_y);

		// button
		m_plusButton.SetPosition(tile_x*5, tile_y*70);
		m_minusButton.SetPosition(tile_x*30, tile_y*70);
		m_mutipleButton.SetPosition(tile_x*55, tile_y*70);
		m_divideButton.SetPosition(tile_x*80, tile_y*70);
		m_cancle.SetPosition(tile_x*45, tile_y*85);
		// progressbar
        m_progressbar_empty.SetPosition(tile_x*10, tile_y*33);
        m_progressbar_hp.SetRectPosition(tile_x*10, tile_y*33, tile_x*96, tile_y*35);
        m_hp.SetPosition(tile_x*2, tile_y*32);
	}

	@Override
	public void Destroy() {
		// TODO Auto-generated method stub
		
	}


}
