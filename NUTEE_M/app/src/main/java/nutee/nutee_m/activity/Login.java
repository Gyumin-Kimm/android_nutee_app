package nutee.nutee_m.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import nutee.nutee_m.HttpGet;
import nutee.nutee_m.R;
import nutee.nutee_m.json.body;
import nutee.nutee_m.json.form;


public class Login extends Activity {

    EditText id,pwd;
    Button login_btn;
    TextView register_tv;
    String session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id=(EditText)findViewById(R.id.login_idet);
        pwd=(EditText)findViewById(R.id.login_passwordet);
        login_btn=(Button)findViewById(R.id.login_loginbtn);
        register_tv=(TextView)findViewById(R.id.login_regtv);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<ArrayList<NameValuePair>, Void, String> at = new AsyncTask<ArrayList<NameValuePair>, Void, String>() {
                    @Override
                    protected String doInBackground(ArrayList<NameValuePair>... params) {
                        HttpGet get = new HttpGet();
                        String result = get.httpget(HttpGet.URL+"login",params[0]);

                        Log.i("Login", result);
                        return result;
                    }
                };
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id", id.getText().toString()));
                params.add(new BasicNameValuePair("user_password", pwd.getText().toString()));
                try {
                    String result = at.execute(params).get();

                    JSONObject get_ai_obj = new JSONObject(result);
                    form ab = new form();

                    ab.setCode(get_ai_obj.getString("code"));
                    ab.setMessages(get_ai_obj.getString("messages"));

                    try {
                        ab.setBody(get_ai_obj.getString("body"));

                        JSONObject get_gab_obj = new JSONObject(ab.getBody());
                        body bo= new body();

                        bo.setSession_id(get_gab_obj.getString("session_id"));
                        session_id = bo.getSession_id();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("session_id", session_id);

                        startActivity(intent);
                    } catch (Exception e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);     // 여기서 this는 Activity의 this

                        // 여기서 부터는 알림창의 속성 설정
//                        builder.setTitle("종료 확인 대화 상자")        // 제목 설정
                        builder.setMessage(ab.getMessages())        // 메세지 설정
                                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    // 확인 버튼 클릭시 설정
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();    // 알림창 객체 생성
                        dialog.show();    // 알림창 띄우기
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Terms.class));
            }
        });
    }
}
