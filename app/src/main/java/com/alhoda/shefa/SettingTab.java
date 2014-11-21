package com.alhoda.shefa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;

/**
 * Created by Hassan on 27/09/2014.
 */
public class SettingTab extends Fragment implements NumberPicker.OnValueChangeListener {

    public static SharedPreferences settingPref;
    public static SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View setting = inflater.inflate(R.layout.setting_frag, container, false);
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Cocon.otf");
        ((TextView) setting.findViewById(R.id.notifyTitle)).setTypeface(typeFace);
        ((TextView) setting.findViewById(R.id.hourTitle)).setTypeface(typeFace);
        ((Switch) setting.findViewById(R.id.stopSwitch)).setTypeface(typeFace);

        //Get default shared Preferences and assign editor to it
        settingPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        editor = settingPref.edit();

        NumberPicker numPicker = (NumberPicker) setting.findViewById(R.id.numberPicker1);

        String[] nums = new String[1000];
        //Add numbers to num picker
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.toString(i + 1);
        numPicker.setMaxValue(nums.length - 1);
        numPicker.setMinValue(1);
        numPicker.setWrapSelectorWheel(false);
        numPicker.setDisplayedValues(nums);
        numPicker.setOnValueChangedListener(this);
        numPicker.setValue(settingPref.getInt("hoursNum", 1));
        Switch stopSwitchSetting = (Switch) setting.findViewById(R.id.stopSwitch);
        stopSwitchSetting.setChecked(settingPref.getBoolean("programStatus", true));
        stopSwitchSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editor.putBoolean("programStatus", true);
                    editor.commit();
                    Intent intentService = new Intent(getActivity().getApplicationContext(), MyService.class);
                    getActivity().startService(intentService);
                    Toast.makeText(getActivity(), "تم تشغيل البرنامج بنجاح", Toast.LENGTH_LONG).show();
                } else {
                    editor.putBoolean("programStatus", false);
                    editor.commit();
//                    //Intent intentService = new Intent(getActivity().getApplicationContext(), MyService.class);
//                    //getActivity().stopService(intentService);
                    Toast.makeText(getActivity(), "تم ايقاف البرنامج", Toast.LENGTH_LONG).show();
                }
            }
        });
        return setting;
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i2) {
        editor.putInt("hoursNum", numberPicker.getValue());
        editor.commit();
    }
}
