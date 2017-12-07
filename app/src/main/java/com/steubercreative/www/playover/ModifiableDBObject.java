package com.steubercreative.www.playover;

import android.content.Context;

/**
 * Created by Sean on 12/6/2017.
 */

public interface ModifiableDBObject {


    public void retrieve(Context context);
    public void save(Context context);
    public void create(Context context);
    public void delete(Context context);

}
