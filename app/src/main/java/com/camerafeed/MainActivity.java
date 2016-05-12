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


public class MainActivity extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, MenuFragment.MenuFragmentInterface{

    GestureDetector GestDetect;
    FragmentManager fm;
    CollapsableCV CCV = new CollapsableCV();
    FrontPageFragment FPF = new FrontPageFragment();
    IlluminatedSquaresFragments IllSqrFrag = new IlluminatedSquaresFragments();
    MenuFragment MF = new MenuFragment();

    private OpenGLActivity mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GestDetect = new GestureDetector(this, this);
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
    public boolean onSingleTapConfirmed(MotionEvent e) {
            //GestureInformer.setText("onSingleTapConfirmed");
            return false;
        }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
           // GestureInformer.setText("onDoubleTap");
            return false;
        }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
            //GestureInformer.setText("onDoubleTapEvent");
            return false;
        }

    @Override
    public boolean onDown(MotionEvent e) {
            //GestureInformer.setText("onDown");
            return false;
        }

    @Override
    public void onShowPress(MotionEvent e) {
            //GestureInformer.setText("onShowPress");
        }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
            //GestureInformer.setText("onSingleTapUp");
            return false;
        }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //GestureInformer.setText("onScroll");
            return false;
        }

    @Override
    public void onLongPress(MotionEvent e) {
            //GestureInformer.setText("onLongPress");
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.ReplaceFragment, CCV);
        ft.commit();
        //Change Activity
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            ///GestureInformer.setText("onFling");
            return false;
        }

    //////////////// End Gestures ////////////////

            @Override
    public boolean onTouchEvent(MotionEvent event) {
            this.GestDetect.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

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
