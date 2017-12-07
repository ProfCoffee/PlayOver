package com.steubercreative.www.playover;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Sean on 12/6/2017.
 *
 * whenever a new type of query is added, simply:
 * 1. add a public static final String below, to identify it
 * 2. add a php link to the phpLinks map in the method builder() below
 * 3. add a String value for the action of the returned Intent to the action map in the method
 * builder() below.
 */

public class QueryMapper {

    public static final String ACTION_GET_AIRPORTS = "com.steubercreative.www.playover.action.GET_AIRPORTS";

    public static final String ACTION_USER_LOGIN = "com.steubercreative.www.playover.action.USER_LOGIN";
    public static final String ACTION_FETCH_USER_INFO = "com.steubercreative.www.playover.action.FETCH_USER_INFO";
    public static final String ACTION_UPDATE_USER_INFO = "com.steubercreative.www.playover.action.UPDATE_USER_INFO";
    public static final String ACTION_DELETE_USER = "com.steubercreative.www.playover.action.DELETE_USER";
    public static final String ACTION_CREATE_USER = "com.steubercreative.www.playover.action.CREATE_USER";

    public static final String ACTION_PROVIDER_LOGIN = "com.steubercreative.www.playover.action.PROVIDER_LOGIN";
    public static final String ACTION_CREATE_PROVIDER = "com.steubercreative.www.playover.action.CREATE_PROVIDER";
    public static final String ACTION_FETCH_PROVIDER_INFO = "com.steubercreative.www.playover.action.FETCH_PROVIDER_INFO";
    public static final String ACTION_UPDATE_PROVIDER_INFO = "com.steubercreative.www.playover.action.UPDATE_PROVIDER_INFO";
    public static final String ACTION_DELETE_PROVIDER = "com.steubercreative.www.playover.action.DELETE_PROVIDER";

    public static final String ACTION_FETCH_ACTIVITY_INFO = "com.steubercreative.www.playover.action.FETCH_ACTIVITY_INFO";
    public static final String ACTION_UPDATE_ACTIVITY_INFO = "com.steubercreative.www.playover.action.UPDATE_ACTIVITY_INFO";
    public static final String ACTION_CREATE_ACTIVITY = "com.steubercreative.www.playover.action.CREATE_ACTIVITY";

    public static final String ACTION_FETCH_PROVIDER_ACTIVITIES = "com.steubercreative.www.playover.action.FETCH_PROVIDER_ACTIVITIES";
    public static final String ACTION_FETCH_AIRPORT_ACTIVITIES = "com.steubercreative.www.playover.action.FETCH_AIRPORT_ACTIVITIES";

    public static final String ACTION_FETCH_PROVIDER_FEEDBACK = "com.steubercreative.www.playover.action.FETCH_PROVIDER_FEEDBACK";
    public static final String ACTION_FETCH_ACTIVITY_FEEDBACK = "com.steubercreative.www.playover.action.FETCH_ACTIVITY_FEEDBACK";
    public static final String ACTION_CREATE_FEEDBACK = "com.steubercreative.www.playover.action.CREATE_FEEDBACK";


    public static String getPHPLink(String queryType){
        return getInstance().phpLinks.get(queryType);
    }
    public static String getQueryReturnAction(String queryType) {
        return getInstance().actions.get(queryType);
    }

    private static QueryMapper instance = null;
    private static QueryMapper getInstance(){
        if(instance == null)
            instance = buildInstance();
        return instance;
    }

    private Map<String, String> actions;
    private Map<String, String> phpLinks;

    /**
     * Builds the map between each type of DB query, its corresponding
     * php file to connect to, and String used as the action in the broadcasted
     * Intent upon receiving the result from the DB.
     * @return
     */

    private static QueryMapper buildInstance(){
        QueryMapper mapper = new QueryMapper();
        mapper.actions = new TreeMap<>();
        mapper.phpLinks = new TreeMap<>();


        //every public static final String above should have an entry
        //in both maps
        mapper.phpLinks.put(ACTION_GET_AIRPORTS, "airport.php");
        mapper.actions.put(ACTION_GET_AIRPORTS, "get_airports");

        mapper.phpLinks.put(ACTION_USER_LOGIN, "user_login.php");
        mapper.actions.put(ACTION_USER_LOGIN, "user_login");
        mapper.phpLinks.put(ACTION_FETCH_USER_INFO, "user_info.php");
        mapper.actions.put(ACTION_FETCH_USER_INFO, "user_info");
        mapper.phpLinks.put(ACTION_UPDATE_USER_INFO, "user_update.php");
        mapper.actions.put(ACTION_UPDATE_USER_INFO, "user_update");
        mapper.phpLinks.put(ACTION_CREATE_USER, "user_create.php");
        mapper.actions.put(ACTION_CREATE_USER, "create_user");
        mapper.phpLinks.put(ACTION_DELETE_USER, "user_delete.php");
        mapper.actions.put(ACTION_DELETE_USER, "delete_user");

        mapper.phpLinks.put(ACTION_PROVIDER_LOGIN, "provider_login.php");
        mapper.actions.put(ACTION_PROVIDER_LOGIN, "provider_login");
        mapper.phpLinks.put(ACTION_FETCH_PROVIDER_INFO, "provider_info.php");
        mapper.actions.put(ACTION_FETCH_PROVIDER_INFO, "provider_info");
        mapper.phpLinks.put(ACTION_UPDATE_PROVIDER_INFO, "provider_update.php");
        mapper.actions.put(ACTION_UPDATE_PROVIDER_INFO, "provider_update");
        mapper.phpLinks.put(ACTION_CREATE_PROVIDER, "provider_create.php");
        mapper.actions.put(ACTION_CREATE_PROVIDER, "create_provider");
        mapper.phpLinks.put(ACTION_DELETE_PROVIDER, "provider_delete.php");
        mapper.actions.put(ACTION_DELETE_PROVIDER, "delete_provider");

        mapper.phpLinks.put(ACTION_FETCH_ACTIVITY_INFO, "activity_info.php");
        mapper.actions.put(ACTION_FETCH_ACTIVITY_INFO, "activity_info");
        mapper.phpLinks.put(ACTION_UPDATE_ACTIVITY_INFO, "activity_update.php");
        mapper.actions.put(ACTION_UPDATE_ACTIVITY_INFO, "activity_update");
        mapper.phpLinks.put(ACTION_CREATE_ACTIVITY, "activity_create.php");
        mapper.actions.put(ACTION_CREATE_ACTIVITY, "create_activity");

        mapper.phpLinks.put(ACTION_FETCH_PROVIDER_ACTIVITIES, "provider_activities.php");
        mapper.actions.put(ACTION_FETCH_PROVIDER_ACTIVITIES, "provider_activities");
        mapper.phpLinks.put(ACTION_FETCH_AIRPORT_ACTIVITIES, "airport_activities.php");
        mapper.actions.put(ACTION_FETCH_AIRPORT_ACTIVITIES, "airport_activities");

        mapper.phpLinks.put(ACTION_FETCH_ACTIVITY_FEEDBACK, "activity_feedback.php");
        mapper.actions.put(ACTION_FETCH_ACTIVITY_FEEDBACK, "activity_feedback");
        mapper.phpLinks.put(ACTION_FETCH_PROVIDER_FEEDBACK, "provider_feedback.php");
        mapper.actions.put(ACTION_FETCH_PROVIDER_FEEDBACK, "provider_feedback");
        mapper.phpLinks.put(ACTION_CREATE_FEEDBACK, "feedback_create.php");
        mapper.actions.put(ACTION_CREATE_FEEDBACK, "create_feedback");

        return mapper;
    }

}
