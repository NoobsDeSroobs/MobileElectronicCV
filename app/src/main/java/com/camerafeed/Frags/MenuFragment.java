package com.camerafeed.Frags;

import android.app.Activity;
import android.app.Fragment;

import android.content.Context;
import android.media.SyncParams;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.camerafeed.R;

public class MenuFragment extends Fragment {

    public enum MenuOptions{
        FrontPage(0),
        GLLights(1),
        CollapsableCV(2)
        ;

        private final int optionID;

        private MenuOptions(int menuOptionID) {
            this.optionID = menuOptionID;
        }
    }

    MenuFragmentInterface ManagerActivity;

    public interface MenuFragmentInterface{
        void DoAction(MenuOptions actionID);//We need to implement many many functions here to enable communication between fragments.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        return view;
    }

    public void setManagerActivity(MenuFragmentInterface act){
        ManagerActivity = act;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear(); //This clears the menu so remember to set all options etc again.
        // inflater.inflate(Color.RED, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int i = item.getItemId();
        switch (i) {
            case R.id.MenuOptionFrontPage:// Scanner //TODO Find a bit more readable way to do this. Instead of magic numbers, see id you can use the name or an enum.
                ((MenuFragmentInterface)getActivity()).DoAction(MenuOptions.FrontPage);
                return true;
            case R.id.MenuOptionLights://Renderer
                ((MenuFragmentInterface)getActivity()).DoAction(MenuOptions.GLLights);
                return true;
            case R.id.MenuOptionList://Settings
                ((MenuFragmentInterface)getActivity()).DoAction(MenuOptions.CollapsableCV);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            ManagerActivity = (MenuFragmentInterface) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " is not implementing MenuFragmentInterface. " +
                    "Implement this interface to attach the MenuFragment.");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }




}