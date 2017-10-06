package com.srmhackathon.haasil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends OnboarderActivity {

    List<OnboarderPage> onboarderPages;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = new Intent(this,Login.class);
        OnboarderPage onboarderPage1 = new OnboarderPage("EFFORTLESSLY", "Our lovely pale blu", R.drawable.slide1);
        OnboarderPage onboarderPage2 = new OnboarderPage("Venus", "The love goddess", R.drawable.slide2);
        OnboarderPage onboarderPage3 = new OnboarderPage("Mars", "Say hi to Curiosity!", R.drawable.slide3);

        onboarderPage1.setBackgroundColor(R.color.colorAccent);
        onboarderPage2.setBackgroundColor(R.color.colorPrimary);
        onboarderPage3.setBackgroundColor(R.color.colorPrimaryDark);

        List<OnboarderPage> pages = new ArrayList<>();

        pages.add(onboarderPage1);
        pages.add(onboarderPage2);
        pages.add(onboarderPage3);

        for (OnboarderPage page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.grey);
            //page.setMultilineDescriptionCentered(true);
        }

        setSkipButtonTitle("Skip");
        setFinishButtonTitle("Finish");

        setOnboardPagesReady(pages);
    }

    @Override
    public void onSkipButtonPressed() {
        // Optional: by default it skips onboarder to the end
        super.onSkipButtonPressed();

        startActivity(i);
        // Define your actions when the user press 'Skip' button
    }

    @Override
    public void onFinishButtonPressed() {

        startActivity(i);
        // Define your actions when the user press 'Finish' button
    }

}