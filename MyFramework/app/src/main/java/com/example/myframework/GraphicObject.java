package com.example.myframework;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GraphicObject {

	protected Bitmap 	m_bitmap;
	protected int		m_x;
	protected int		m_y;
	protected int		m_left;
	protected int		m_right;
	protected int		m_top;
	protected int		m_bottom;
	protected Rect m_rect;


	public GraphicObject(Bitmap bitmap){
		m_bitmap = bitmap;
		m_x = 0;
		m_y = 0;
		m_left = 0;
		m_right = 0;
		m_top = 0;
		m_bottom = 0;
		m_rect = new Rect(0,0,0,0);
	}

	public void ChangeBitmap(Bitmap bitmap)
	{
		m_bitmap = bitmap;
	}

	public void SetPosition(int x,int y){
		m_x = x;
		m_y = y;
	}

	public void SetRectPosition(int left, int top, int right, int bottom)
	{
		m_rect = new Rect(left, top, right, bottom);
		m_left = left;
		m_right = right;
		m_top = top;
		m_bottom = bottom;
	}


	public void Draw(Canvas canvas){
		canvas.drawBitmap(m_bitmap,m_x,m_y,null);
	}
	public void DrawRect(Canvas canvas) {
		canvas.drawBitmap(m_bitmap,null, m_rect,null);
	}
	public int GetX(){
		return m_x;
	}
	public int GetY(){
		return m_y;
	}
	public int GetLeft(){
		return m_left;
	}
	public int GetRight(){
		return m_right;
	}
	public int GetTop(){
		return m_top;
	}
	public int GetBottom(){
		return m_bottom;
	}
}
