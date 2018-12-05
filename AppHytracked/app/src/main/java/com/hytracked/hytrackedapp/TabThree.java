package com.hytracked.hytrackedapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class TabThree extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        System.out.println("aquiiiii");
        System.out.println(R.layout.tab_three);

        return inflater.inflate(R.layout.tab_three, container, false);

    }

    public void setName(String name){


    }
}
