package com.mgoenka.basicdrawing;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
	
	private Path path = new Path();
	
    public SimpleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        circlePoints = new ArrayList<Point>();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	canvas.drawPath(path, drawPaint);
    }
    
    // Append new circle each time user presses on screen
    @Override
    /*
    public boolean onTouchEvent(MotionEvent event) {
      float touchX = event.getX();
      float touchY = event.getY();
      circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));
      // Indicate view should be redrawn
      postInvalidate();
      return true;
    }
    */
    // Get x and y and append them to the path
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // Starts a new line in the path
            path.moveTo(pointX, pointY);
            break;
        case MotionEvent.ACTION_MOVE:
            // Draws line between last point and this point
            path.lineTo(pointX, pointY);
            break;
        default:
            return false;
       }

       postInvalidate(); // Indicate view should be redrawn
       return true; // Indicate we've consumed the touch
    }
    
    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
