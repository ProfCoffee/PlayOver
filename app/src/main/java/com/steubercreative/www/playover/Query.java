package com.steubercreative.www.playover;

import android.content.Context;
import android.os.Bundle;

import java.util.Set;

/**
 * Created by Sean on 12/6/2017.
 */

@FunctionalInterface
public interface Query {
    public void send(Context context, Bundle input, Set<String> output);
}
