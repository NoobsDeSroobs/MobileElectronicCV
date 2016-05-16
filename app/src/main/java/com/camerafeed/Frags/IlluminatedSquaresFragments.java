package com.camerafeed.Frags;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.camerafeed.MyOpenGL.GameofLife;
import com.camerafeed.MyOpenGL.GoLRenderer;
import com.camerafeed.R;
import com.camerafeed.Views.JoystickMovedListener;
import com.camerafeed.Views.JoystickView;

import java.util.Calendar;


public class IlluminatedSquaresFragments extends Fragment implements JoystickMovedListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private GameofLife GoL;
    private PointF screenSize;
    private GoLRenderer renderer;
    private JoystickView joystick;
    private SeekBar speedBar;
    private long delay = 10;

    public IlluminatedSquaresFragments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*View rootView = inflater.inflate(R.layout.fragment_sketch_board,container,false);
        return rootView;*/

        View view = inflater.inflate(R.layout.fragment_illuminated_squares_fragments, container, false);//new GLSurfaceView(getActivity());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        final int GoLX = 100, GoLY = 100;
        GoL = new GameofLife(GoLX, GoLY);

        screenSize = new PointF();
        Point p = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(p);
        screenSize.x = p.x;
        screenSize.y = p.y;
        renderer = new GoLRenderer(screenSize);
        renderer.init(GoL, GoLX, GoLY);
        GLSurfaceView glSurfaceView = (GLSurfaceView)getActivity().findViewById(R.id.OpenGLView);
        if(joystick == null) {
            glSurfaceView.setEGLContextClientVersion(2);
        }
        glSurfaceView.setRenderer(renderer);
        joystick = (JoystickView)getActivity().findViewById(R.id.JoyStick);
        joystick.setOnJostickMovedListener(this);
        speedBar = (SeekBar)getActivity().findViewById(R.id.seekBar);
        speedBar.setOnSeekBarChangeListener(this);
        Button b = (Button)getActivity().findViewById(R.id.button);
        b.setOnClickListener(this);

        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {

            // We can be in one of these 2 states
            static final int NONE = 0;
            static final int ZOOM = 1;
            int mode = NONE;


            float oldDist = 5f;
            PointF oldPosition = new PointF();
            int extraFingerID = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:

                        oldPosition.set((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (extraFingerID == 0) {
                            extraFingerID = event.getPointerId(event.getActionIndex());
                            oldDist = getSpacing(event, 0, extraFingerID);
                            if (oldDist > 10f) {
                                mode = ZOOM;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerCount() < 3) {
                            mode = NONE;
                            extraFingerID = 0;
                            renderer.getCamera().saveCamera();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == ZOOM) {
                            float newDist = getSpacing(event, 0, extraFingerID);
                            // If you want to tweak font scaling, this is the place to go.
                            if (newDist > 10f) {
                                float scale = oldDist / newDist;
                                renderer.getCamera().zoom(scale);
                            }
                        } else {
                            //Move the camera
                            PointF newPos = new PointF((int) event.getX(), (int) event.getY());
                            PointF dir = new PointF(((newPos.x - oldPosition.x) / screenSize.x) * 60, ((newPos.y - oldPosition.y) / screenSize.y) * 60);
                            oldPosition = newPos;
                            renderer.getCamera().rotate(dir);
                        }
                        break;
                }
                return true;
            }

            private float getSpacing(MotionEvent event, int pointIndex1, int pointIndex2) {
                if (event.getPointerCount() < 2) {
                    return 1f;
                }
                float x = event.getX(pointIndex1) - event.getX(pointIndex2);
                float y = event.getY(pointIndex1) - event.getY(pointIndex2);
                return (float) Math.sqrt(x * x + y * y);
            }

        });


        Thread thread = new Thread() {
//            Calendar time = Calendar.getInstance();
//            long startTime = time.getTimeInMillis();

            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(delay);
                        if(delay != speedBar.getMax()) {
                            GoL.tick();
                            renderer.update();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Joystick
    @Override
    public void OnMoved(int pan, int tilt) {
        renderer.getCamera().move(pan, tilt);
    }

    @Override
    public void OnReleased() {

    }

    //Speedbar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        delay = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        GoL.tick();
        renderer.update();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
