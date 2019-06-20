package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myframework.AppManager;
import com.example.myframework.GraphicObject;
import com.example.myframework.R;

public class BackGround extends GraphicObject {

	
	public BackGround(int backtype) {
		super(null);
		if(backtype == 0)
			m_bitmap = AppManager.getInstance().getBitmap(R.drawable.title);
		else if(backtype == 1)
			m_bitmap =AppManager.getInstance().getBitmap(R.drawable.background);
		else if(backtype == 2)
			m_bitmap =AppManager.getInstance().getBitmap(R.drawable.gallery_background);
		else if(backtype == 3)
			m_bitmap =AppManager.getInstance().getBitmap(R.drawable.end);
		else if(backtype == 4)
			m_bitmap =AppManager.getInstance().getBitmap(R.drawable.gallery_background);
	}

	void Update(long GameTime){
	}

	@Override
	public void Draw(Canvas canvas){
		canvas.drawBitmap(m_bitmap, null,
				new Rect(0,0, AppManager.getInstance().getWidth(), AppManager.getInstance().getHeight()), null);
	}

}
