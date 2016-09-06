package org.rightnow.font.audiotestservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ericfontenault on 9/2/16.
 */
public class AudioBroadcastReceiver extends BroadcastReceiver {
    public interface Listener {
        void onAudioStuff(int type);
    }

    private Listener mListener;

    public AudioBroadcastReceiver(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("STATUS", -1);

        mListener.onAudioStuff(type);
    }
}
