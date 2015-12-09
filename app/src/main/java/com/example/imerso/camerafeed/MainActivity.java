package com.example.imerso.camerafeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{

    TextView GestureInformer;
    GestureDetector GestDetect;

    private OpenGLActivity mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GestureInformer = (TextView)findViewById(R.id.GestureInformer);
        GestDetect = new GestureDetector(this, this);
    }


    //////////////// Begin Gestures ////////////////

            @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
            GestureInformer.setText("onSingleTapConfirmed");
            return false;
        }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
            GestureInformer.setText("onDoubleTap");
            return false;
        }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
            GestureInformer.setText("onDoubleTapEvent");
            return false;
        }

    @Override
    public boolean onDown(MotionEvent e) {
            GestureInformer.setText("onDown");
            return false;
        }

    @Override
    public void onShowPress(MotionEvent e) {
            GestureInformer.setText("onShowPress");
        }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
            GestureInformer.setText("onSingleTapUp");
            return false;
        }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            GestureInformer.setText("onScroll");
            return false;
        }

    @Override
    public void onLongPress(MotionEvent e) {
            GestureInformer.setText("onLongPress");
        //Change Activity
        mGLView = new OpenGLActivity(this);
        setContentView(mGLView);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            GestureInformer.setText("onFling");
            return false;
        }

    //////////////// End Gestures ////////////////

            @Override
    public boolean onTouchEvent(MotionEvent event) {
            this.GestDetect.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

}
