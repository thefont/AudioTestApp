package org.rightnow.font.audiotestservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.cast.framework.CastButtonFactory;

public class MainActivity extends AppCompatActivity {
    Activity activity;
    private final int PERMANENT = 0;
    private final int TRANSIENT = 1;
    private final int TRANSIENT_DUCK = 2;

    private AudioBroadcastReceiver audioReceiver;

    private MenuItem mediaRouteMenuItem;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        activity = this;

        Button gainPermanentButton = (Button)findViewById(R.id.gainPermanent);
        gainPermanentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AudioTestUI", "gainPermanent");
                Intent intent = new Intent(activity, AudioService.class);
                intent.putExtra("type", PERMANENT);
                activity.startService(intent);
            }
        });

        Button gainTransient = (Button)findViewById(R.id.gainTransient);
        gainTransient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AudioTestUI", "gainTransient");
                Intent intent = new Intent(activity, AudioService.class);
                intent.putExtra("type", TRANSIENT);
                activity.startService(intent);
            }
        });

        Button gainTransientDuck = (Button)findViewById(R.id.gainTransientDuck);
        gainTransientDuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AudioTestUI", "gainTransientDuck");
                Intent intent = new Intent(activity, AudioService.class);
                intent.putExtra("type", TRANSIENT_DUCK);
                activity.startService(intent);
            }
        });

        audioReceiver = new AudioBroadcastReceiver(new AudioBroadcastReceiver.Listener() {
            @Override
            public void onAudioStuff(int type) {
                String text = "";

                switch(type) {
                    case 0: {
                        text = "permanent";
                        break;
                    }
                    case 1: {
                        text = "transient";
                        break;
                    }
                    case 2: {
                        text = "transient duck";
                        break;
                    }
                }

                Toast toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(audioReceiver, new IntentFilter("org.rightnow.font.audiotestservice.BROADCAST_ACTION"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(audioReceiver);
    }
}
