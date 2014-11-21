package com.alhoda.shefa;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.lang.Override;

/**
 * Created by Hassan on 27/09/2014.
 */
public class MainTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainFrag = inflater.inflate(R.layout.main_frag, container, false);
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Cocon.otf");
        TextView tittleTextview = ((TextView) mainFrag.findViewById(R.id.title));
        tittleTextview.setTypeface(typeFace);

        TextView introTxtTextview = (TextView) mainFrag.findViewById(R.id.introTxt);
        introTxtTextview.setTypeface(typeFace);
        ((TextView) mainFrag.findViewById(R.id.aya)).setTypeface(typeFace);
        ((TextView) mainFrag.findViewById(R.id.hadith)).setTypeface(typeFace);
        ((TextView) mainFrag.findViewById(R.id.do3a)).setTypeface(typeFace);
        ((TextView) mainFrag.findViewById(R.id.sa7aba)).setTypeface(typeFace);
        ((TextView) mainFrag.findViewById(R.id.salaf)).setTypeface(typeFace);
        ((TextView) mainFrag.findViewById(R.id.m3loma)).setTypeface(typeFace);
        return
                mainFrag;

    }
}
