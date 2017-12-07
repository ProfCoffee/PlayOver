package com.steubercreative.www.playover;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class DBConnectionService extends IntentService {
    private static final String INPUT = "input";
    private static final String OUTPUT = "output";
    private static final String TYPE = "type";
    private static final String ARRAY_OUTPUT = "array_output";
    private static final String ACTION_SINGLE = "com.steubercreative.www.playover.action.ACTION_ACTION";
    private static final String ACTION_ARRAY = "com.steubercreative.www.playover.action.ACTION_ARRAY";

    public DBConnectionService() {
        super("DBConnectionService");
    }

    public static void startArrayAction(Context context, Bundle input, Set<String> stringOutput,
                                        Set<String> arrayOutput, String queryType) {
        Intent intent = new Intent(context, DBConnectionService.class);
        intent.putExtra(INPUT, input);
        intent.putExtra(OUTPUT, (Serializable) stringOutput);
        intent.putExtra(ARRAY_OUTPUT, (Serializable) arrayOutput);
        intent.putExtra(TYPE, queryType);
        intent.setAction(ACTION_SINGLE);
        context.startService(intent);
    }
    public static void startAction(Context context, Bundle input, Set<String> output, String queryType) {
        Intent intent = new Intent(context, DBConnectionService.class);
        intent.putExtra(INPUT, input);
        intent.putExtra(OUTPUT, (Serializable) output);
        intent.putExtra(TYPE, queryType);
        intent.setAction(ACTION_SINGLE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            String type = intent.getStringExtra(TYPE);
            Bundle input = intent.getBundleExtra(INPUT);
            Set<String> output = (Set<String>) intent.getSerializableExtra(OUTPUT);
            if(action.equals(ACTION_SINGLE)) {
                try {
                    handleAction(QueryMapper.getPHPLink(type), input, output, QueryMapper.getQueryReturnAction(type));
                } catch(IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            else if(action.equals(ACTION_ARRAY)){
                Set<String> arrayOutput = (Set<String>) intent.getSerializableExtra(ARRAY_OUTPUT);
                try {
                    handleArrayAction(QueryMapper.getPHPLink(type), input, output, arrayOutput, QueryMapper.getQueryReturnAction(type));
                } catch(IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            else throw new IllegalArgumentException("No such action on this database connection.");
        }
    }

    private String readAll(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int ch;
        while((ch = reader.read()) != -1) {
            builder.append((char) ch);
        }
        return builder.toString();
    }

    private void handleArrayAction(String page, Bundle input, Set<String> stringOutputs,
                                   Set<String> arrayOutputs, String action) throws IOException {
        URL url = null;
        try {
            url = new URL(buildURLString(page, input));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            String json = readAll(getInputReader(urlConnection));
            JSONObject obj = new JSONObject(json);
            Intent intent = new Intent(action);
            addStringOutput(intent, obj, stringOutputs);
            addArrayOutput(intent, obj, arrayOutputs);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            urlConnection.disconnect();
        }
    }

    private void handleAction(String page, Bundle input, Set<String> output, String action) throws IOException {
        URL url = null;
        try {
            url = new URL(buildURLString(page, input));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            String json = readAll(getInputReader(urlConnection));
            JSONObject obj = new JSONObject(json);
            Intent intent = new Intent(action);
            addStringOutput(intent, obj, output);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            urlConnection.disconnect();
        }
    }

    private BufferedReader getInputReader(HttpURLConnection urlConnection) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(urlConnection.getInputStream())));

    }
    private void addArrayOutput(Intent intent, JSONObject obj, Set<String> output) {
        for(String name : output) {
            try {
                List<Map<String, String>> entries = new ArrayList<>();      //THIS is the type of the array
                JSONArray arr = obj.getJSONArray(name);
                for(int i = 0; i < arr.length(); i++) {
                    Map<String, String> fields = new TreeMap<>();
                    JSONArray keys = arr.getJSONObject(i).names();
                    for (int k = 0; k < keys.length(); k++) {
                        fields.put(keys.getString(k), arr.getJSONObject(i).getString(keys.getString(k)));
                    }
                    entries.add(fields);
                }
                intent.putExtra(name, (Serializable) entries);
            } catch(JSONException e) {
//                    intent.putExtra(name, (String) null);
            }
        }
    }
    private void addStringOutput(Intent intent, JSONObject obj, Set<String> output) {
        for(String name : output) {
            try {
                intent.putExtra(name, obj.get(name).toString());
            } catch(JSONException e) {
//                    intent.putExtra(name, (String) null);
            }
        }
    }
    private String buildURLString(String page, Bundle input) {
        StringBuilder builder = new StringBuilder();
        builder.append(ConnectionMapper.HOME + "/" + page);
        if(input != null) {
            boolean first = true;
            for(String key : input.keySet()) {
                if(first) {
                    builder.append("?");
                    first = false;
                }
                else {
                    builder.append("&");
                }
                builder.append(key + "=" + input.get(key).toString());
            }
        }
        return builder.toString();
    }
}
