package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myframework.AppManager;
import com.example.myframework.GraphicObject;
import com.example.myframework.R;

public class BackGround extends GraphicObject {

	static final float SCROLL_SPEED = 1.f;
	private float m_scroll = -2000 + 480;
	
	Bitmap m_layer2;

	static final float SCROLL_SPEED_2 = 0.5f;
	private float m_scroll_2 = -2000 + 480; 
	
	
	
	public BackGround(int backtype) {
		super(null);
		if(backtype == 0)
			m_bitmap = AppManager.getInstance().getBitmap(R.drawable.background);
		else if(backtype == 1)
			m_bitmap =AppManager.getInstance().getBitmap(R.drawable.background);
		m_layer2 = AppManager.getInstance().getBitmap(R.drawable.background_2);
		SetPosition(0,(int)m_scroll);
	}
	void Update(long GameTime){
		m_scroll = m_scroll + SCROLL_SPEED;
		if(m_scroll >= 0)
			m_scroll = 0;
		SetPosition(0,(int)m_scroll); 
		m_scroll_2 = m_scroll_2 + SCROLL_SPEED_2;
		if(m_scroll_2 >= 0)
			m_scroll_2 = 0;
	} 
	@Override
	public void Draw(Canvas canvas){
		canvas.drawBitmap(m_bitmap, null,
				new Rect(0,0, AppManager.getInstance().getWidth(), AppManager.getInstance().getHeight()), null);
		//canvas.drawBitmap(m_layer2,m_x,m_scroll_2,null);
	}

}
