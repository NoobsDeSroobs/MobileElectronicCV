package com.camerafeed.Frags;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.camerafeed.MyOpenGL.GameofLife;
import com.camerafeed.MyOpenGL.GoLRenderer;


public class IlluminatedSquaresFragments extends Fragment {
    private OnFragmentInteractionListener mListener;
    GameofLife GoL;
    PointF screenSize;

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
        final int GoLX = 30, GoLY = 30;
        GoL = new GameofLife(GoLX, GoLY);

        GLSurfaceView glSurfaceView = new GLSurfaceView(getActivity());
        screenSize = new PointF();
        Point p = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(p);
        screenSize.x = p.x;
        screenSize.y = p.y;
        final GoLRenderer renderer = new GoLRenderer(screenSize);
        renderer.init(GoL, GoLX, GoLY);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(renderer);



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

                        oldPosition.set((int)event.getX(), (int)event.getY());
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if(extraFingerID == 0) {
                            extraFingerID = event.getPointerId(event.getActionIndex());
                            oldDist = getSpacing(event, 0, extraFingerID);
                            if (oldDist > 10f) {
                                mode = ZOOM;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerCount()<3) {
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
                                float scale = oldDist/newDist ;
                                renderer.getCamera().zoom(scale);
                            }
                        }else{
                            //Move the camera
                            PointF newPos = new PointF((int)event.getX(), (int)event.getY());
                            PointF dir = new PointF(((newPos.x - oldPosition.x)/screenSize.x)*60, ((newPos.y - oldPosition.y)/screenSize.y)*60);
                            oldPosition = newPos;
                            renderer.getCamera().rotate(dir);
                        }
                        break;
                }
                return true;
            }

            private float getSpacing(MotionEvent event, int pointIndex1, int pointIndex2) {
                if(event.getPointerCount() < 2){
                    return 1f;
                }
                float x = event.getX(pointIndex1) - event.getX(pointIndex2);
                float y = event.getY(pointIndex1) - event.getY(pointIndex2);
                return (float)Math.sqrt(x * x + y * y);
            }

        });

        return glSurfaceView;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
