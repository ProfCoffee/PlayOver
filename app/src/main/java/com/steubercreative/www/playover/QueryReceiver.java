package com.steubercreative.www.playover;

import android.content.Intent;

/**
 * Created by Sean on 12/6/2017.
 */

@FunctionalInterface
public interface QueryReceiver {
    void processQuery(Intent intent);
}
