package com.mgoenka.basicdrawing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ShapeSelectorView extends View {
	private int shapeColor;
	private boolean displayShapeName;
	
	private int shapeWidth = 100;
    private int shapeHeight = 100;
    private int textOffset = 30;
    private Paint paintShape;
    
    private String[] shapeValues = { "square", "circle", "triangle" };
    private int currentShapeIndex = 0;
	
	// We must provide a constructor that takes a Context and an AttributeSet.
    // This constructor allows the UI to create and edit an instance of your view.
    public ShapeSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintShape = new Paint();
        setupAttributes(attrs);
        paintShape.setColor(shapeColor);
        paintShape.setStyle(Paint.Style.FILL);
    }
    
    public boolean isDisplayingShapeName() {
        return displayShapeName;
    }

    public void setDisplayingShapeName(boolean state) {
        this.displayShapeName = state;
        invalidate();
        requestLayout();
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int color) {
        this.shapeColor = color;
        invalidate();
        requestLayout();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
        String shapeSelected = shapeValues[currentShapeIndex];
        if (shapeSelected.equals("square")) {
            canvas.drawRect(0, 0, shapeWidth, shapeHeight, paintShape);
            textOffset = 0;
        } else if (shapeSelected.equals("circle")) {
            canvas.drawCircle(shapeWidth / 2, shapeHeight / 2, shapeWidth / 2, paintShape);
            textOffset = 12;
        } else if (shapeSelected.equals("triangle")) {
            canvas.drawPath(getTrianglePath(), paintShape);
            textOffset = 0;
        }
        if (displayShapeName) {
            canvas.drawText(shapeSelected, 0 + textOffset, shapeHeight + textOffset, paintShape);
        }
    }
    
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    // Defines the extra padding for the shape name text
	    int textPadding = 10;
	    int contentWidth = shapeWidth + getSuggestedMinimumWidth();
	    
	    // Try for a width based on our minimum
	    int minw = contentWidth + getPaddingLeft() + getPaddingRight();
	    if (displayShapeName) {
	    	minw += textPadding;
	    }
	    int w = resolveSizeAndState(minw, widthMeasureSpec, 0);
	    
	    // Ask for a height that would let the view get as big as it can
	    int minh = shapeHeight + getPaddingBottom() + getPaddingTop();
	    if (displayShapeName) {
	    	minh += textOffset + textPadding;
	    }
	    int h = resolveSizeAndState(minh, heightMeasureSpec, 0);
	    
	    // Calling this method determines the measured width and height
	    setMeasuredDimension(w, h);
	}
	
	// Change the currentShapeIndex whenever the shape is clicked
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    boolean result = super.onTouchEvent(event);
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        currentShapeIndex ++;
	        if (currentShapeIndex > (shapeValues.length - 1)) {
	            currentShapeIndex = 0;
	        }
	        postInvalidate();
	        return true;
	    }
	    return result;
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		return super.onSaveInstanceState();
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(state);
	}
	
	protected Path getTrianglePath() {
	    Point p1 = new Point(0, shapeHeight), p2 = null, p3 = null;
	    p2 = new Point(p1.x + shapeWidth, p1.y);
	    p3 = new Point(p1.x + (shapeWidth / 2), p1.y - shapeHeight);
	    Path path = new Path();
	    path.moveTo(p1.x, p1.y);
	    path.lineTo(p2.x, p2.y);
	    path.lineTo(p3.x, p3.y);
	    return path;
	}
    
    private void setupAttributes(AttributeSet attrs) {
        // Obtain a typed array of attributes
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ShapeSelectorView, 0, 0);
        // Extract custom attributes into member variables
        try {
            shapeColor = a.getColor(R.styleable.ShapeSelectorView_shapeColor, Color.BLACK);
            displayShapeName = a.getBoolean(R.styleable.ShapeSelectorView_displayShapeName, false);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }
    }
}
