package com.camerafeed;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;

import com.camerafeed.Frags.CollapsableCV;
import com.camerafeed.Frags.FrontPageFragment;
import com.camerafeed.Frags.IlluminatedSquaresFragments;
import com.camerafeed.Frags.MenuFragment;


public class MainActivity extends AppCompatActivity implements MenuFragment.MenuFragmentInterface{

    FragmentManager fm;
    CollapsableCV CCV = new CollapsableCV();
    FrontPageFragment FPF = new FrontPageFragment();
    IlluminatedSquaresFragments IllSqrFrag = new IlluminatedSquaresFragments();
    MenuFragment MF = new MenuFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ActionBar);
        setSupportActionBar(myToolbar);
        MF.setManagerActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.ReplaceFragment, FPF);
        ft.commit();
        Log.v("Test", "Ran the create options menu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionmenu, menu);
        return true;
    }
//////////////// Begin Gestures ////////////////


    @Override
    public void DoAction(MenuFragment.MenuOptions actionID) {
        FragmentTransaction ft = fm.beginTransaction();

        switch (actionID){
            case CollapsableCV:
                ft.replace(R.id.ReplaceFragment, CCV);
                ft.commit();
                break;
            case GLLights:
                ft.replace(R.id.ReplaceFragment, IllSqrFrag);
                ft.commit();
                break;
            case FrontPage:
                ft.replace(R.id.ReplaceFragment, FPF);
                ft.commit();
                break;
            default:
                Log.e("DoAction", "Unrecognized option.");

                break;
        }
    }
}
