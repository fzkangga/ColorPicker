package com.mycompany.myapp2;
import android.preference.*;
import android.os.*;

public class MainFragment extends PreferenceFragment
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_fragment);
	}
	
}
