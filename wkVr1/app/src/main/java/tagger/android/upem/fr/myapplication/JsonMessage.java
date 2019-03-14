package tagger.android.upem.fr.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonMessage {

    public final static String URL = "http://192.168.0.22:8080/Tagger/resources/MyRestService/";
    private boolean inQueue;
    private String msg;
    private Player player;
    public JsonMessage() {

    }
    public String checkIfTrue(String url){
        OkHttpClient client =  new OkHttpClient();
        Request req = new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    msg = response.body().string();
                }
            }
        });
        try{
            Thread.sleep(250);
        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }
    public Player getPlayer(String url){
        player = null;
        OkHttpClient client =  new OkHttpClient();
        Request req = new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                        player = getJsonPlayer(response.body().string());

                }
            }
        });
        try{
            Thread.sleep(250);
        }catch (Exception e){
            e.printStackTrace();
        }
        return player;
    }

    public void logOut(String nickName){
        OkHttpClient client =  new OkHttpClient();
        final Request req = new Request.Builder().url(URL+"Disonnected?player="+nickName).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        try{
            Thread.sleep(250);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void execute(String url){
        OkHttpClient client =  new OkHttpClient();
        final Request req = new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        try{
            Thread.sleep(250);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getString(String url){
        OkHttpClient client =  new OkHttpClient();
        final Request req = new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                msg = response.body().string();
            }
        });
        try{
            Thread.sleep(250);
        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }

    public Player getJsonPlayer(String jsonContent){
        Player player = new Player();
        try {
            JSONObject jsonObject = new JSONObject(jsonContent);
            player.setNickName(jsonObject.getString("nickName"));
            player.setPassword(jsonObject.getString("password"));
            player.setScore(jsonObject.getInt("score"));
            player.setIdImage(jsonObject.getInt("idImage"));
            player.setTags(jsonObject.getString("tags"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return player;
    }

}
