package com.demo.diarytextitem.custom.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

import org.jetbrains.annotations.NotNull;

public class DiaryBulletSpan implements LeadingMarginSpan {

    private final Bullet bullet;
    private final Bitmap mBitmap;
    private final int mPad;
    int bulletId;

    public DiaryBulletSpan(@NotNull Bullet bullet, int pad) {
        this.bullet = bullet;
        mBitmap = bullet.getMBitmap();
        bulletId = bullet.getBulletId();
        mPad = pad;
    }

    public Bullet getBullet() {
        return bullet;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return mBitmap.getWidth() + mPad;
    }

    @Override
    public void drawLeadingMargin(Canvas canvas, Paint paint, int x, int dir,
                                  int top, int baseline, int bottom, CharSequence text,
                                  int start, int end, boolean first, Layout layout) {
        if (first) {
            int iconHeight = mBitmap.getHeight();
            int lineHeight = bottom - top;
            int y = top + (lineHeight - iconHeight) / 2;

            canvas.save();
            canvas.translate(x, y);
            canvas.drawBitmap(mBitmap, 0, 0, paint);
            canvas.restore();
        }
    }
}
