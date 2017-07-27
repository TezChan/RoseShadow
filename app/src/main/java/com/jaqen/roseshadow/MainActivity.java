package com.jaqen.roseshadow;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Fade;
import android.support.v4.app.FragmentManager;
import android.transition.TransitionInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.jaqen.game.GameMainFragment;
import com.jaqen.roseshadow.colors.NipponColor;
import com.jaqen.roseshadow.fragments.FishFragment;
import com.jaqen.roseshadow.fragments.ImageFragment;
import com.jaqen.roseshadow.fragments.WordListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AndroidFragmentApplication.Callbacks {

    private DrawerLayout drawer;
    private FragmentManager fragmentManager;

    private ImageFragment imageFragment;
    private WordListFragment wordListFragment;
    private FishFragment fishFragment;
    private GameMainFragment gameMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.toolbarBg).setBackgroundColor(NipponColor.getColor());
        findViewById(R.id.mainLayout).setBackgroundColor(NipponColor.getColor());
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setBackgroundColor(NipponColor.getColor());
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).setBackgroundColor(NipponColor.getColor());
        navigationView.setCheckedItem(R.id.navImg);

        imageFragment = new ImageFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contentMain, imageFragment)
                .commit();
        setTitle("美图");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navImg){
            fragmentManager.beginTransaction()
                    .replace(R.id.contentMain, imageFragment)
                    .commit();

            setTitle("美图");
        }else if (id == R.id.navManage){

        }else if (id == R.id.navWords){
            if (wordListFragment == null)
                wordListFragment = new WordListFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.contentMain, wordListFragment)
                    .commit();

            setTitle("心语");
        }else if (id == R.id.navFish){
            if (fishFragment == null)
                fishFragment = new FishFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.contentMain, fishFragment)
                    .commit();

            setTitle("小鱼");
        }else if (id == R.id.navGame){
            if (gameMainFragment == null)
                gameMainFragment = new GameMainFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.contentMain, gameMainFragment)
                    .commit();

            setTitle("游戏");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void exit() {
    }
}
