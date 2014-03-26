package com.mgoenka.basicdrawing;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SimpleShapeView extends View {
	// Setup initial color
	private final int paintColor = Color.BLACK;
	
	// Defines Paint and Canvas
	private Paint drawPaint;
	
	// Store circles to draw each time the user touches down
	private List<Point> circlePoints;
	
    public SimpleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        circlePoints = new ArrayList<Point>();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	for (Point p : circlePoints) {
    		canvas.drawCircle(p.x, p.y, 10, drawPaint);
    	}
    }
    
    // Append new circle each time user presses on screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
      float touchX = event.getX();
      float touchY = event.getY();
      circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));
      // Indicate view should be redrawn
      postInvalidate();
      return true;
    }
    
    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
