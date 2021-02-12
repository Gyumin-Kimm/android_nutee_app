package nutee.nutee_m.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import nutee.nutee_m.R;


public class Terms extends Activity {

    Button back, next;
    WebView terms;
    CheckBox termschk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        back = (Button)findViewById(R.id.terms_backbtn);
        next = (Button)findViewById(R.id.terms_nextbtn);
        terms = (WebView)findViewById(R.id.terms_webview);
        termschk = (CheckBox)findViewById(R.id.terms_chkbox);

        terms.getSettings().setJavaScriptEnabled(true);      // 웹뷰에서 자바 스크립트 사용
        terms.loadUrl("http://nutee.kr/about/tos");            // 웹뷰에서 불러올 URL 입력


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(termschk.isChecked()){
                    startActivity(new Intent(getApplicationContext(),Register.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Terms chk!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
