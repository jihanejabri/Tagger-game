package tagger.android.upem.fr.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private JsonMessage jsonMessage = new JsonMessage();
    private TextView p1Score,p2Score,p1,p2;
    private String currentNickName="";
    private Player playerOne;
    private Player playerTwo;
    private ListView listTags;
    private ListView listTagsP1;
    private ListView listTagsP2;
    private Context context;
    private String tags;
    private ArrayAdapter<String> arrayAdapterTags;
    private ArrayAdapter<String> arrayAdapterTagsP1;
    private ArrayAdapter<String> arrayAdapterTagsP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        context = this;
        currentNickName = getIntent().getStringExtra("currentNickName");
        playerOne = (Player) getIntent().getSerializableExtra("playerOne");
        playerTwo = (Player) getIntent().getSerializableExtra("playerTwo");
        tags = getIntent().getStringExtra("tags");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        p1Score = (TextView) findViewById(R.id.txtViewP1Score);
        p2Score = (TextView) findViewById(R.id.txtViewP2Score);
        p1 = (TextView) findViewById(R.id.txtViewP1);
        p2 = (TextView) findViewById(R.id.txtViewP2);
        listTags = (ListView) findViewById(R.id.listTags);
        listTagsP1 = (ListView) findViewById(R.id.listTagsP1);
        listTagsP2 = (ListView) findViewById(R.id.listTagsP2);
        Log.d("flaki","playerOne : "  +playerOne.toString());
        Log.d("flaki","playerTwo : "  +playerTwo.toString());
        arrayAdapterTags = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, transforme(tags));
        listTags.setAdapter(arrayAdapterTags);

        arrayAdapterTagsP1 = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, transformeTagsPlayer(playerOne.getTags()));
        listTagsP1.setAdapter(arrayAdapterTagsP1);

        arrayAdapterTagsP2 = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, transformeTagsPlayer(playerTwo.getTags()));
        listTagsP2.setAdapter(arrayAdapterTagsP2);

        p1Score.setText("Score : " + playerOne.getScore());
        p2Score.setText("Score : " + playerTwo.getScore());
        p1.setText(playerOne.getNickName());
        p2.setText(playerTwo.getNickName());
    }
    public List<String> transforme(String tab){
        List<String> list = new ArrayList<>();
        String[] data = tab.split("_");
        Log.d("flaki","tab transforme : "  +tab);
        for (String  s : data ) {
            String[] data2  = s.split("-");
            list.add(data2[0] +" --> " + data2[1] );
        }
        return list;
    }
    public List<String> transformeTagsPlayer(String tab){
        List<String> list = new ArrayList<>();

        Log.d("flaki","tab : "  +tab);
        String[] data = tab.split("_");

        Log.d("flaki","data : "  +data.toString());
        for (String  s : data ) {
            list.add(s);
        }

        Log.d("flaki","list : "  +list.toString());
        return list;
    }
    @Override
    public void onBackPressed() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Disconnected();
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
    public void Disconnected(){
        jsonMessage.logOut(currentNickName);
        Toast.makeText(context,"Disconnected.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
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
