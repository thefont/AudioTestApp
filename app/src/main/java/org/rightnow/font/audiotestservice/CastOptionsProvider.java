package org.rightnow.font.audiotestservice;

import android.content.Context;

import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;

import java.util.List;

/**
 * Created by eric on 12/2/16.
 */

public class CastOptionsProvider implements OptionsProvider {

    @Override
    public CastOptions getCastOptions(Context context) {
//        NotificationOptions notificationOptions = new NotificationOptions.Builder()
//                .setActions(Arrays.asList(MediaIntentReceiver.ACTION_SKIP_NEXT,
//                        MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK,
//                        MediaIntentReceiver.ACTION_STOP_CASTING), new int[]{1, 2})
//                .setTargetActivityClassName(ExpandedControlsActivity.class.getName())
//                .build();
//        CastMediaOptions mediaOptions = new CastMediaOptions.Builder()
//                .setImagePicker(new ImagePickerImpl())
//                .setNotificationOptions(notificationOptions)
//                .setExpandedControllerActivityClassName(ExpandedControlsActivity.class.getName())
//                .build();
        return new CastOptions.Builder()
                .setReceiverApplicationId("FAB7BACE")
//                .setCastMediaOptions(mediaOptions)
                .build();
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context appContext) {
        return null;
    }

//    private static class ImagePickerImpl extends ImagePicker {
//
//        @Override
//        public WebImage onPickImage(MediaMetadata mediaMetadata, int type) {
//            if ((mediaMetadata == null) || !mediaMetadata.hasImages()) {
//                return null;
//            }
//            List<WebImage> images = mediaMetadata.getImages();
//            if (images.size() == 1) {
//                return images.get(0);
//            } else {
//                if (type == ImagePicker.IMAGE_TYPE_MEDIA_ROUTE_CONTROLLER_DIALOG_BACKGROUND) {
//                    return images.get(0);
//                } else {
//                    return images.get(1);
//                }
//            }
//        }
//    }
}

