package org.rightnow.font.audiotestservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ericfontenault on 9/2/16.
 */
public class AudioService extends IntentService {
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioListener;

    IntentService self;

    public AudioService() {
        super("AudioService");
        self = this;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra("type", -1);

        Log.d("AudioTestService", String.format("type: %d", type));

        switch(type) {
            case 0:
                handlePermanent();
                break;
            case 1:
                handleTransient();
                break;
            case 2:
                handleTransientDuck();
                break;
        }
    }

    private void handlePermanent() {
        TimerTask requestAudioTask = new TimerTask() {
            @Override
            public void run() {
                audioListener = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        Log.d("AudioTestService", String.format("method: handlePermanent(). type: %d", i));
                    }
                };
                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                int result = audioManager.requestAudioFocus(
                        audioListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN
                );

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Log.d("AudioTestService", String.format("method: handlePermanent(). access granted"));
                } else {
                    Log.d("AudioTestService", String.format("method: handlePermanent(). access denied"));
                }

                Intent localIntent = new Intent("org.rightnow.font.audiotestservice.BROADCAST_ACTION")
                        .putExtra("STATUS", 0);

                audioManager.abandonAudioFocus(audioListener);

                LocalBroadcastManager.getInstance(self).sendBroadcast(localIntent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(requestAudioTask, 10000);
    }

    private void handleTransient() {
        Log.d("AudioTestService", String.format("method: handleTransient()"));
        final TimerTask endAudioFocusTask = new TimerTask() {
            @Override
            public void run() {
                audioManager.abandonAudioFocus(audioListener);

                Intent localIntent = new Intent("org.rightnow.font.audiotestservice.BROADCAST_ACTION")
                        .putExtra("STATUS", -1);

                LocalBroadcastManager.getInstance(self).sendBroadcast(localIntent);
            }
        };

        TimerTask requestAudioTask = new TimerTask() {
            @Override
            public void run() {
                audioListener = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        Log.d("AudioTestService", String.format("method: handlePermanent(). type: %d", i));
                    }
                };
                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                int result = audioManager.requestAudioFocus(
                        audioListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                );

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Log.d("AudioTestService", String.format("method: handlePermanent(). access granted"));
                } else {
                    Log.d("AudioTestService", String.format("method: handlePermanent(). access denied"));
                }

                Intent localIntent = new Intent("org.rightnow.font.audiotestservice.BROADCAST_ACTION")
                        .putExtra("STATUS", 1);

                LocalBroadcastManager.getInstance(self).sendBroadcast(localIntent);

                Timer endAudioTimer = new Timer();
                endAudioTimer.schedule(endAudioFocusTask, 5000);
            }
        };

        Timer timer = new Timer();
        timer.schedule(requestAudioTask, 10000);
    }

    private void handleTransientDuck() {
        Log.d("AudioTestService", String.format("method: handleTransientDuck()"));
        final TimerTask endAudioFocusTask = new TimerTask() {
            @Override
            public void run() {
                audioManager.abandonAudioFocus(audioListener);

                Intent localIntent = new Intent("org.rightnow.font.audiotestservice.BROADCAST_ACTION")
                        .putExtra("STATUS", -1);

                LocalBroadcastManager.getInstance(self).sendBroadcast(localIntent);
            }
        };

        TimerTask requestAudioTask = new TimerTask() {
            @Override
            public void run() {
                audioListener = new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        Log.d("AudioTestService", String.format("method: handlePermanent(). type: %d", i));
                    }
                };
                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                int result = audioManager.requestAudioFocus(
                        audioListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                );

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Log.d("AudioTestService", String.format("method: handlePermanent(). access granted"));
                } else {
                    Log.d("AudioTestService", String.format("method: handlePermanent(). access denied"));
                }

                Intent localIntent = new Intent("org.rightnow.font.audiotestservice.BROADCAST_ACTION")
                        .putExtra("STATUS", 2);

                LocalBroadcastManager.getInstance(self).sendBroadcast(localIntent);

                Timer endAudioTimer = new Timer();
                endAudioTimer.schedule(endAudioFocusTask, 5000);
            }
        };

        Timer timer = new Timer();
        timer.schedule(requestAudioTask, 10000);
    }
}
