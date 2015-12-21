package com.example.imerso.camerafeed;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{

    GestureDetector GestDetect;
    FragmentManager fm;
    CollapsableCV CCV = new CollapsableCV();
    FrontPageFragment FPF = new FrontPageFragment();

    private OpenGLActivity mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GestDetect = new GestureDetector(this, this);
        fm = getFragmentManager();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.ReplaceFragment, FPF);
        ft.commit();
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

}
