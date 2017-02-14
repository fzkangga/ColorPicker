package net.margaritov.preference.colorpicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.*;
import android.util.*;
import com.mycompany.myapp2.*;
import android.view.*;

/**
 * This is the only class of the project. Consist in a costum dialog that show
 * the GUI for choose the color.
 *
 * @author Simone Pessotto
 *
 */
public class ColorPickerView extends RelativeLayout
implements SeekBar.OnSeekBarChangeListener
{
	public interface OnColorChangeListener
	{
		public void onColorChange(int color);
	}
	private OnColorChangeListener mListener;
	public void setOnColorChangeListener(OnColorChangeListener listener)
	{
		mListener = listener;
	}
    public Context context;
    SeekBar alphaSeekBar,redSeekBar, greenSeekBar, blueSeekBar;
    TextView alphaToolTip,redToolTip, greenToolTip, blueToolTip;
    private int alpha,red, green, blue;
    int seekBarLeft;
    Rect thumbRect;
	boolean showAlphaSeekBar = false;
	RelativeLayout alphaArea;


    /**
     * Creator of the class. It will initialize the class with black color as default
     * @param a The reference to the activity where the color picker is called
     */
    public ColorPickerView(Context c,AttributeSet a) {
        super(c,a);

        context = c;
		alpha = 255;
       	red = 0;
        green = 0;
        blue = 0;
		initLayout();
    }

	private void initLayout()
	{
		// TODO: Implement this method
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.view_color_picker,this);

		alphaArea = (RelativeLayout)v.findViewById(R.id.alphaArea);
		alphaSeekBar = (SeekBar)v.findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar)v.findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar)v.findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar)v.findViewById(R.id.blueSeekBar);

        seekBarLeft = redSeekBar.getPaddingLeft();

		alphaToolTip = (TextView)v.findViewById(R.id.alphaIndicator);
        redToolTip = (TextView)v.findViewById(R.id.redIndicator);
        greenToolTip = (TextView)v.findViewById(R.id.greenIndicator);
        blueToolTip = (TextView)v.findViewById(R.id.blueIndicator);

		alphaSeekBar.setOnSeekBarChangeListener(this);
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        setAlphaProgress(getAlphaKu(),true);
		setRedProgress(getRed(),true);
		setGreenProgress(getGreen(),true);
		setBlueProgress(getBlue(),true);

		setAlphaSeekBarVisible(showAlphaSeekBar);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){

		thumbRect = alphaSeekBar.getThumb().getBounds();

        alphaToolTip.setX(seekBarLeft + thumbRect.left);
        if (alpha<10)
            alphaToolTip.setText("  "+alpha);
        else if (alpha<100)
            alphaToolTip.setText(" "+alpha);
        else
            alphaToolTip.setText(alpha+"");
		
        thumbRect = redSeekBar.getThumb().getBounds();

        redToolTip.setX(seekBarLeft + thumbRect.left);
        if (red<10)
            redToolTip.setText("  "+red);
        else if (red<100)
            redToolTip.setText(" "+red);
        else
            redToolTip.setText(red+"");

        thumbRect = greenSeekBar.getThumb().getBounds();

        greenToolTip.setX(seekBarLeft + thumbRect.left);
        if (green<10)
            greenToolTip.setText("  "+green);
        else if (red<100)
            greenToolTip.setText(" "+green);
        else
            greenToolTip.setText(green+"");

        thumbRect = blueSeekBar.getThumb().getBounds();

        blueToolTip.setX(seekBarLeft + thumbRect.left);
        if (blue<10)
            blueToolTip.setText("  "+blue);
        else if (blue<100)
            blueToolTip.setText(" "+blue);
        else
            blueToolTip.setText(blue+"");

    }

    /**
     * Method called when the user change the value of the bars. This sync the colors.
     *
     * @param seekBar SeekBar that has changed
     * @param progress The new progress value
     * @param fromUser If it coem from User
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

		if (seekBar.getId() == R.id.alphaSeekBar) {

            alpha = progress;
            thumbRect = seekBar.getThumb().getBounds();

            alphaToolTip.setX(seekBarLeft + thumbRect.left);

            if (progress<10)
                alphaToolTip.setText("  " + alpha);
            else if (progress<100)
                alphaToolTip.setText(" "+alpha);
            else
                alphaToolTip.setText(alpha+"");

        }
        else if (seekBar.getId() == R.id.redSeekBar) {

            red = progress;
            thumbRect = seekBar.getThumb().getBounds();

            redToolTip.setX(seekBarLeft + thumbRect.left);

            if (progress<10)
                redToolTip.setText("  " + red);
            else if (progress<100)
                redToolTip.setText(" "+red);
            else
                redToolTip.setText(red+"");

        }
        else if (seekBar.getId() == R.id.greenSeekBar) {

            green = progress;
            thumbRect = seekBar.getThumb().getBounds();

            greenToolTip.setX(seekBar.getPaddingLeft()+thumbRect.left);
            if (progress<10)
                greenToolTip.setText("  "+green);
            else if (progress<100)
                greenToolTip.setText(" "+green);
            else
                greenToolTip.setText(green+"");

        }
        else if (seekBar.getId() == R.id.blueSeekBar) {

            blue = progress;
            thumbRect = seekBar.getThumb().getBounds();

            blueToolTip.setX(seekBarLeft + thumbRect.left);
            if (progress<10)
                blueToolTip.setText("  "+blue);
            else if (progress<100)
                blueToolTip.setText(" "+blue);
            else
                blueToolTip.setText(blue+"");

        }
		setColor(Color.argb(alpha,red,green,blue),true);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

	
	public int getAlphaKu()
	{
		return alpha;
	}

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getColor(){
        return Color.argb(alpha,red,green, blue);
    }
	
	
	public void setAlphaProgress(int color)
	{
		setAlphaProgress(color,false);
	}
	public void setAlphaProgress(int color,boolean callback)
	{
		alphaSeekBar.setProgress(color);
		if(callback && mListener != null)
		{
			mListener.onColorChange(Color.alpha(color));
		}
		invalidate();
	}
	
	public void setRedProgress(int redColor)
	{
		setRedProgress(redColor,false);
	}
	public void setRedProgress(int redColor,boolean callback)
	{
		redSeekBar.setProgress(redColor);
		if(callback && mListener != null)
		{
			mListener.onColorChange(Color.red(redColor));
		}
		invalidate();
	}
	
	public void setGreenProgress(int greenColor)
	{
		setGreenProgress(greenColor,false);
	}
	public void setGreenProgress(int greenColor,boolean callback)
	{
		greenSeekBar.setProgress(greenColor);
		if(callback && mListener != null)
		{
			mListener.onColorChange(Color.green(greenColor));
		}
		invalidate();
	}
	
	public void setBlueProgress(int blueColor)
	{
		setBlueProgress(blueColor,false);
	}
	public void setBlueProgress(int blueColor,boolean callback)
	{
		int myblue = Color.blue(blueColor);
		blueSeekBar.setProgress(myblue);
		if(callback && mListener != null)
		{
			mListener.onColorChange(Color.blue(myblue));
		}
		invalidate();
	}
	
	public void setColor(int color){
        setColor(color, false);
    }

    /**
     * Set the color this view should show.
     * @param color The color that should be selected.
     * @param callback If you want to get a callback to
     * your OnColorChangedListener.
     */
    public void setColor(int color, boolean callback){

		int myColor = Color.argb(Color.alpha(color),Color.red(color),Color.green(color),Color.blue(color));
        if(callback && mListener != null){
            mListener.onColorChange(myColor);
        }

        invalidate();
    }
	
	public void setAlphaSeekBarVisible(boolean visible)
	{
		if(visible == true)
		{
			alphaArea.setVisibility(VISIBLE);
		}
		else{
			alphaArea.setVisibility(GONE);
		}

    }
}
