package tagger.android.upem.fr.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private Button next;
    private Context context;
    public static final String SERVER_URL="http://192.168.0.22:8080/Tagger/resources/MyRestService/";
    public static final String URL_IMAGE="http://192.168.0.22:8080/Tagger/resources/MyRestService/getImage";
    public static final String URL_CHECK="http://192.168.0.22:8080/Tagger/resources/MyRestService/CheckPlayer";
    public static final String URL_GET_IN_QUEUE="http://192.168.0.22:8080/Tagger/resources/MyRestService/getInQueue?nickName=";
    public static final String URL_GET_TAGS="http://192.168.0.22:8080/Tagger/resources/MyRestService/getImageTagger?idImage=";
    public static final String URL_END_GAME="http://192.168.0.22:8080/Tagger/resources/MyRestService/EndGame?";
    private String currentNickName;
    private Player currentPlayer,playerOne,playerTwo;
    private JsonMessage jsonMessage;
    private GifImageView gifImageView;
    private GifImageView gifCountDown;
    private EditText tagsEditText;
    private TextView tagsShow;
    private Boolean gameON;
    private TextView textView;
    private Layout layout;
    private List<String> tags;
    private boolean gameEnd;
    private Toolbar toolbar;
    private Handler handlerMain = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        jsonMessage = new JsonMessage();
        Intent intent = getIntent();
        currentNickName = intent.getStringExtra("nickName");
        currentPlayer = jsonMessage.getPlayer(URL_GET_IN_QUEUE+currentNickName);
        next = (Button) findViewById(R.id.Next);
        gifImageView = (GifImageView) findViewById(R.id.gifImage);
        gifCountDown = (GifImageView) findViewById(R.id.TimeGif);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tagsShow = (TextView) findViewById(R.id.tagsShow);
        textView = (TextView) findViewById(R.id.PlayerNames);
        tagsEditText = (EditText) findViewById(R.id.tagsEditText);
        tags = new ArrayList<>();
        context = this;
        gameEnd = false;

        gameON = false;
        next.setEnabled(false);
        tagsEditText.setEnabled(false);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    if(tagsEditText.getText().toString().trim().equals("")){
                        Toast.makeText(context,"Tag could not be empty",Toast.LENGTH_SHORT).show();
                    }else{
                        if(tags.contains(tagsEditText.getText().toString())){
                            Toast.makeText(context,tagsEditText.getText().toString() + " Already Proposed !" , Toast.LENGTH_SHORT).show();
                        }else{
                            tags.add(tagsEditText.getText().toString().toUpperCase());
                            tagsEditText.setText("");
                            tagsShow.setText("Tags proposed : \n" + tags.toString());
                            Toast.makeText(context,"Addeded",Toast.LENGTH_SHORT).show();
                        }

                    }

            }
        });
        Thread waitThread = new Thread(new waitInQueueAsyncTask());
        waitThread.start();
        Thread runTheGame = new Thread(new GameInProgressThread());
        runTheGame.start();
        new GameEndAsyncTask().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.disconnected:
                Disconnected();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //DoNothing
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        GifImageView bmImage;

        public DownloadImageTask(GifImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    private class GameInProgressThread implements Runnable{

        @Override
        public void run() {
            while (true){
                if(gameON){
                    handlerMain.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Game Will Start In 3 Second.");
                            gifImageView.setImageResource(R.mipmap.treesecond);
                        }
                    });
                    waitForAMoment(3);
                    handlerMain.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("");
                            tagsEditText.setEnabled(true);
                            next.setEnabled(true);
                            gifCountDown.setImageResource(R.mipmap.loadgif5);
                            textView.setText(playerOne.getNickName() + " : " + playerOne.getScore() + "\n" + playerTwo.getNickName() + " : " + playerTwo.getScore());

                        }
                    });
                    LoadImage();
                    waitForAMoment(30);
                    gameEnd = true;
                    Thread.currentThread().interrupted();
                    return;
                }
            }
        }
    }
    private class waitInQueueAsyncTask implements Runnable{
        Boolean check = false;
        @Override
        public void run() {

            while (true){
                check = Boolean.parseBoolean(jsonMessage.checkIfTrue(URL_CHECK));
                if(check){
                    handlerMain.post(new Runnable() {
                    @Override
                    public void run() {
                        gifImageView.setImageResource(R.mipmap.loadgif5);
                    }
                    });
                    Log.d("flaki","Check In");
                    gameON  = true;
                    playerOne = jsonMessage.getPlayer(SERVER_URL+"getPlayerOne");
                    playerTwo = jsonMessage.getPlayer(SERVER_URL+"getPlayerTwo");
                    waitForAMoment(1);
                    Thread.currentThread().interrupted();
                    return;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Disconnected();
    }
    public void Disconnected(){
        jsonMessage.logOut(currentNickName);
        waitForAMoment(1);
        Toast.makeText(context,"Disconnected.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
    }
    public class GameEndAsyncTask extends AsyncTask<String, Integer , String> {

        @Override
        protected String doInBackground(String... strings) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true){
                        //Log.d("flaki","Game End AsyncTask");
                        //waitForAMoment(1);
                        if(gameEnd){
                            handlerMain.post(new Runnable() {
                                @Override
                                public void run() {
                                    gifImageView.setImageResource(R.mipmap.loadgif6);
                                    gifCountDown.setVisibility(View.INVISIBLE);
                                    tagsEditText.setEnabled(false);
                                    next.setEnabled(false);
                                    textView.setText("Time's up !!\n Please Wait We Are Collecting Informations.");
                                }
                            });
                            currentPlayer = jsonMessage.getPlayer(SERVER_URL+"getPlayer?nickName="+currentNickName);
                            currentPlayer.setTags(transforme(tags));
                            waitForAMoment(2);
                            jsonMessage.execute(URL_END_GAME+"currentPlayer="+ currentPlayer.getNickName() +  "&tagsCurrentPlayer=" +currentPlayer.getTags());
                            waitForAMoment(2);
                            playerOne = jsonMessage.getPlayer(SERVER_URL+"getPlayerFromDao?nickName="+playerOne.getNickName());
                            playerTwo = jsonMessage.getPlayer(SERVER_URL+"getPlayerFromDao?nickName="+playerTwo.getNickName());
                            String Stags = jsonMessage.getString(URL_GET_TAGS+playerOne.getIdImage());
                            waitForAMoment(2);
                            Intent intent = new Intent(context,ResultActivity.class);
                            intent.putExtra("currentNickName",currentNickName);
                            intent.putExtra("playerOne",playerOne);
                            intent.putExtra("playerTwo",playerTwo);
                            intent.putExtra("tags",Stags);
                            startActivity(intent);
                            Thread.currentThread().interrupted();
                            return;
                        }
                    }

                }
            });
            t.start();
            return null;
        }
    }
    public String transforme(List<String> tags) {
        String currentTag="";
        for(int i  = 0; i < tags.size() ; i++){
            if(i+1 == tags.size()) {
                currentTag += tags.get(i);
            }else {
                currentTag += tags.get(i) +"_";
            }
        }
        return currentTag;
    }
    public void LoadImage(){
        new DownloadImageTask(gifImageView).execute(URL_IMAGE);
    }

    public void waitForAMoment(int second){
            for (int i = 0 ; i < second ; i++){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

}


