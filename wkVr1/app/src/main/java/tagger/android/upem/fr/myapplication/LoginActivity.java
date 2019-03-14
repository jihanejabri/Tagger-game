package tagger.android.upem.fr.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private AutoCompleteTextView nickName;
    private EditText password;
    private Button seConnecter;
    private Button resgistration;
    private JsonMessage jsonMessage;
    private Player currentPlayer;
    public static final String URL_CHECK_PLAYER="http://192.168.0.22:8080/Tagger/resources/MyRestService/checkPassword?";
    public static final String URL_Register_PLAYER="http://192.168.0.22:8080/Tagger/resources/MyRestService/Inscription?";
        public static final String URL_STATUS_PLAYER="http://192.168.0.22:8080/Tagger/resources/MyRestService/CheckStatus?";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        jsonMessage = new JsonMessage();
        context = this;
        seConnecter = (Button) findViewById(R.id.email_sign_in_button);
        nickName = (AutoCompleteTextView) findViewById(R.id.nickName);
        resgistration = (Button) findViewById(R.id.Resgistration);
        password = (EditText) findViewById(R.id.password);

        seConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().trim().equals("") || nickName.getText().toString().trim().equals("")){
                    Toast.makeText(context,"All Fields must be filled !",Toast.LENGTH_SHORT).show();
                }else{
                    seConnecter.setEnabled(false);
                    Boolean isCorrect = Boolean.parseBoolean(jsonMessage.checkIfTrue(URL_CHECK_PLAYER+ "nickName="+nickName.getText().toString()+"&password="+password.getText().toString()));
                    Boolean isConnected = Boolean.parseBoolean(jsonMessage.checkIfTrue(URL_STATUS_PLAYER+ "nickName="+nickName.getText().toString()));
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                        seConnecter.setEnabled(true);
                    if(isCorrect){
                        if(isConnected){
                            Toast.makeText(context,"Player " + nickName.getText().toString() +" Is Already Connected.",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("nickName", nickName.getText().toString());
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(context,"Connexion failed !",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        resgistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().trim().equals("") || nickName.getText().toString().trim().equals("")){
                    Toast.makeText(context,"All Fields must be filled !",Toast.LENGTH_SHORT).show();
                }else {
                    if(password.getText().toString().trim().length()<=7){
                        Toast.makeText(context,"Password to short !",Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean isCorrect = Boolean.parseBoolean(jsonMessage.checkIfTrue(URL_Register_PLAYER + "nickName=" + nickName.getText().toString() + "&password=" + password.getText().toString()));
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        resgistration.setEnabled(true);
                        if (isCorrect) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("nickName", nickName.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "NickName Already used !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}

