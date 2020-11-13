package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager screenPager;
    final List<ScreenItem> mList = new ArrayList<>();
    IntroViewPagerAdapter viewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    Animation btnAnim ;
    TextView tvSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // poner la actividad en pantalla completa

     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
     //           WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Cuando la activity se lanze debemos revisar si el view pager se vio o no

        if (restorePrefData()) {

            Intent I = new Intent(getApplicationContext(),LoginActivity.class );
            startActivity(I);
            finish();


        }

        setContentView(R.layout.activity_view_pager);

        //Inicializar Vistas
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // Esconder La Barra de Accion
      //  getSupportActionBar().hide();

        //Rellenar La Lista de elementos

        mList.add(new ScreenItem("Happy Smile","Ofreciendo Servicios Odontologicos",R.drawable.img1));
        mList.add(new ScreenItem("Mision","Satisfacer de forma integral las necesidades de salud oral de nuestros pacientes, mejorando continuamente la calidad de nuestros servicios, brindando así una atenciónpersonalizada y responsable.",R.drawable.img2));
        mList.add(new ScreenItem("Vision","Pretendemos ser un referente a seguir dentro del sector de la odontología, por la calidad del trabajo y por la calidez humana",R.drawable.img3));
        mList.add(new ScreenItem("Desde la Aplicacion","Puedes crear citas desde donde sea que estes y observar tu plan de tratamiento",R.drawable.img4));

        // Ejecutar ViewPager
        screenPager =findViewById(R.id.screen_viewpager);
        viewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(viewPagerAdapter);

        // Ejecutar Tab layout Con View Pager

        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);


                }

                if (position == mList.size()-1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();


                }



            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Abrir Login Activity

                Intent I = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(I);
                //guardamos booleano para que cuando el usuario corra la app otra vez de un solo pase al login
               //Para eso usamos sharedPreferences
                savePrefsData();
                finish();




            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });


        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size()-1) {

                    loaddLastScreen();

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;



    }
    //Guaramos Los datos con sharedPrefs
    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }

    // Carga La ultima Pantalla
    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);
    }



}