package com.ailiwean.module_grayscale;

import android.graphics.Rect;

/**
 * @Package: com.ailiwean.module_grayscale
 * @ClassName: ReductionAreaScale
 * @Description: 缩小处理
 * @Author: SWY
 * @CreateDate: 2020/9/21 10:47 AM
 */
class ReductionAreaScale implements Dispatch {
    @Override
    public byte[] dispatch(byte[] data, int width, int height) {
        return data;
    }

    @Override
    public byte[] dispatch(byte[] data, int width, int height, Rect rect) {
        byte[] newByte = data.clone();
        byte[] emptyByte = new byte[rect.width() * rect.height()];
        int areaSize = 0;
        double step = Math.random() * 2 + 1;

        for (float start_h = rect.top; start_h < rect.bottom; start_h += step) {
            for (float start_w = rect.left; start_w < rect.right; start_w += step) {
                int index = ((int) start_h) * width + (int) start_w;
                emptyByte[areaSize] = newByte[index];
                areaSize++;
            }
        }

        int reductWidth = (int) (Math.sqrt(areaSize / (double) emptyByte.length) * rect.width());
        int reductHeight = (int) (Math.sqrt(areaSize / (double) emptyByte.length) * rect.height());
        areaSize = 0;

        for (int start_h = rect.top; start_h < rect.bottom; start_h++) {
            for (int start_w = rect.left; start_w < rect.right; start_w++) {

                int index = start_h * width + start_w;
                int lef_w = (rect.width() - reductWidth) / 2 + start_w;
                int rig_w = lef_w + reductWidth;
                int top_h = (rect.height() - reductHeight) / 2 + start_h;
                int bot_h = top_h + reductHeight;

                if (start_h > top_h && start_h < bot_h && start_w > lef_w && start_w < rig_w) {
                    newByte[index] = emptyByte[areaSize < emptyByte.length ? areaSize++ : 0];
                } else newByte[index] = (byte) 255;

            }
        }

        return newByte;
    }
}
