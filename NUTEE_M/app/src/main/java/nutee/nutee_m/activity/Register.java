package nutee.nutee_m.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import nutee.nutee_m.HttpGet;
import nutee.nutee_m.R;
import nutee.nutee_m.json.form;

public class Register extends Activity {

    Button reg_backbtn, reg_donebtn;
    EditText user_id, password, password_chk, user_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_id=(EditText)findViewById(R.id.reg_user_id);
        password =(EditText)findViewById(R.id.reg_pwd);
        password_chk =(EditText)findViewById(R.id.reg_pwd_chk);
        user_nickname=(EditText)findViewById(R.id.reg_user_nickname);

        reg_donebtn = (Button)findViewById(R.id.reg_donebtn);
        reg_donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<ArrayList<NameValuePair>,Void,String> at = new AsyncTask<ArrayList<NameValuePair>, Void, String>() {
                    @Override
                    protected String doInBackground(ArrayList<NameValuePair>... params) {
                        HttpGet get = new HttpGet();
                        String result = get.httpget(HttpGet.URL + "register", params[0]);

                        Log.i("Register", result);
                        return result;
                    }
                };
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id", user_id.getText().toString()));
                params.add(new BasicNameValuePair("password", password.getText().toString()));
                params.add(new BasicNameValuePair("password_chk", password_chk.getText().toString()));
                params.add(new BasicNameValuePair("user_nickname", user_nickname.getText().toString()));
                try{
                    String result = at.execute(params).get();

                    JSONObject getform = new JSONObject(result);
                    form ab = new form();

                    ab.setCode(getform.getString("code"));
                    ab.setMessages(getform.getString("messages"));
                    try{
                        ab.setBody(getform.getString("body"));

                        Toast.makeText(getApplicationContext(),"join success!",Toast.LENGTH_LONG).show();
                        finish();
                    }catch(Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);

                        // 여기서 부터는 알림창의 속성 설정
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

        reg_backbtn =(Button)findViewById(R.id.reg_backbtn);
        reg_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
