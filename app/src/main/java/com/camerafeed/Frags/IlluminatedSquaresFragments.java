package com.example.imerso.camerafeed.Frags;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imerso.camerafeed.MyOpenGL.MyGLRenderer;


public class IlluminatedSquaresFragments extends Fragment {
    private OnFragmentInteractionListener mListener;

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


        GLSurfaceView glSurfaceView = new GLSurfaceView(getActivity());
        MyGLRenderer renderer = new MyGLRenderer();
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(renderer);

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
