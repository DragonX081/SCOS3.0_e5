package es.source.code.self_util;

import android.content.Context;

public class pixelTrans {
    public static int DpToPix(Context context, float dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp*scale+0.5f);
    }
    public static int PixToDp(Context context,float pix){
        final float  scale = context.getResources().getDisplayMetrics().density;
        return (int)(pix/scale+0.5f);
    }
}
