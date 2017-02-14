package com.mycompany.myapp2;

import android.app.*;
import android.os.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new MainFragment()).commit();
    }
}
