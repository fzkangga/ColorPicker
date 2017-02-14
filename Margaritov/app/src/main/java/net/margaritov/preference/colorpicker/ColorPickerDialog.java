/*
 * Copyright (C) 2010 Daniel Nilsson
 * Copyright (C) 2013 Slimroms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.margaritov.preference.colorpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.*;
import com.mycompany.myapp2.*;

public class ColorPickerDialog extends Dialog
implements ColorPickerView.OnColorChangeListener,View.OnClickListener
{
	int textColor = 0xff2196f3;
    private ColorPickerView mColorPicker;
    private ColorPickerPanelView mNewColor;
    private EditText mHex;
    private Button mSetButton;
	
    private OnColorChangeListener mListener;

    public interface OnColorChangeListener {
        public void onColorChange(int color);
    }

    public ColorPickerDialog(Context context, int initialColor) {
        super(context);

        init(initialColor);
    }

    private void init(int color) {
        // To fight color branding.
        getWindow().setFormat(PixelFormat.RGBA_8888);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setUp(color);

    }

    private void setUp(int color) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.dialog_color_picker, null);

        setContentView(layout);

        
        mColorPicker = (ColorPickerView) layout.findViewById(R.id.color_picker_view);
        mNewColor = (ColorPickerPanelView) layout.findViewById(R.id.color_picker_panel_view);

        
        mHex = (EditText) layout.findViewById(R.id.hexColor);
		mHex.setTextColor(textColor);
        mSetButton = (Button) layout.findViewById(R.id.setButton);
		mSetButton.setBackgroundColor(0);
		mSetButton.setTextColor(textColor);
       
        mNewColor.setOnClickListener(this);
        mColorPicker.setOnColorChangeListener(this);
        
        mColorPicker.setColor(color, true);
		
        if (mHex != null) {
            mHex.setText(ColorPickerPreference.convertToARGB(color));
        }
        if (mSetButton != null) {
           mSetButton.setOnClickListener(this);
        }
    }

    @Override
    public void onColorChange(int color) {

        mNewColor.setColor(color);
		mColorPicker.setAlphaProgress(Color.alpha(color));
		mColorPicker.setRedProgress(Color.red(color));
		mColorPicker.setGreenProgress(Color.green(color));
		mColorPicker.setBlueProgress(Color.blue(color));
        try {
            if (mHex != null) {
                mHex.setText(ColorPickerPreference.convertToARGB(color));
            }
        } catch (Exception e) {

        }
        /*
         * if (mListener != null) { mListener.onColorChanged(color); }
         */

    }

    /**
     * Set a OnColorChangedListener to get notified when the color selected by the user has changed.
     *
     * @param listener
     */
    public void setOnColorChangeListener(OnColorChangeListener listener) {
        mListener = listener;
    }

    public int getColor() {
        return mColorPicker.getColor();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.setButton) {
            if (mListener != null) {
                mListener.onColorChange(mNewColor.getColor());
            }
        }
        dismiss();
    }
	
	public void setAlphaSeekBarVisible(boolean visible) {
        mColorPicker.setAlphaSeekBarVisible(visible);
    }
    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
		state.putInt("new_color", mNewColor.getColor());
		state.putInt("alpha_color", Color.alpha(mNewColor.getColor()));
		state.putInt("red_color", Color.red(mNewColor.getColor()));
		state.putInt("green_color", Color.green(mNewColor.getColor()));
		state.putInt("blue_color", Color.blue(mNewColor.getColor()));
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mColorPicker.setColor(savedInstanceState.getInt("new_color"), true);
		mColorPicker.setAlphaProgress(savedInstanceState.getInt("alpha_color"), true);		
		mColorPicker.setRedProgress(savedInstanceState.getInt("red_color"), true);		
		mColorPicker.setGreenProgress(savedInstanceState.getInt("green_color"), true);		
		mColorPicker.setBlueProgress(savedInstanceState.getInt("blue_color"), true);		
		
    }

}
