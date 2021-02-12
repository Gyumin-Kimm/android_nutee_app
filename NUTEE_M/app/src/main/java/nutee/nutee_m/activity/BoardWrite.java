package nutee.nutee_m.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import nutee.nutee_m.R;
import nutee.nutee_m.function.insert_article;
import nutee.nutee_m.function.insert_comment;
import nutee.nutee_m.json.body;
import nutee.nutee_m.json.categories;
import nutee.nutee_m.json.form;

public class BoardWrite extends Activity implements AdapterView.OnItemSelectedListener {

    EditText boardwrite_title, boardwrite_content;
    Button boardwrite_write_button;
    Spinner boardwrite_categories;

    ArrayList<String> spinner_list;

    String at_id, session_id, board_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        Intent intent = getIntent();
        session_id = intent.getExtras().getString("session_id");
        board_type = intent.getExtras().getString("board_type");
//        Log.i("BWsession_id",session_id);
//        categories cgs = (categories)intent.getSerializableExtra("categories");
        boardwrite_categories = (Spinner)findViewById(R.id.boardwrite_spinner);
        final ArrayList<categories> cgs_list = (ArrayList<categories>)intent.getSerializableExtra("categories");

        spinner_list = new ArrayList<String>();
        for(int i=0;i<cgs_list.size();i++){
//            Log.i("cg_name",cgs_list.get(i).getCategory_name());
            spinner_list.add(cgs_list.get(i).getCategory_name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, spinner_list);
        //스피너 속성
        boardwrite_categories.setPrompt("category"); // 스피너 제목
        boardwrite_categories.setAdapter(adapter);
        boardwrite_categories.setOnItemSelectedListener(this);

//        setSpinnerAdapter(spinner_list);

        boardwrite_title=(EditText)findViewById(R.id.boardwrite_title);
        boardwrite_content=(EditText)findViewById(R.id.boardwrite_content);

        boardwrite_write_button=(Button)findViewById(R.id.boardwrite_write_button);     // write button event
        boardwrite_write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
                insert_article task=new insert_article();
                params.add(new BasicNameValuePair("article_boardId","26"));                     // board type*********************
                params.add(new BasicNameValuePair("article_title",boardwrite_title.getText().toString()));
                params.add(new BasicNameValuePair("article_category",cgs_list.get(0).getId()));
                params.add(new BasicNameValuePair("session_id",session_id));

                try {
                    String test_json = task.execute(params).get();

                    JSONObject get_ai_obj = new JSONObject(test_json);
                    form ab = new form();

                    ab.setCode(get_ai_obj.getString("code"));
                    ab.setMessages(get_ai_obj.getString("messages"));
                    ab.setBody(get_ai_obj.getString("body"));

                    JSONObject get_atid_obj = new JSONObject(ab.getBody());
                    body id = new body();

                    id.setArticle_id(get_atid_obj.getString("article_id"));
                    at_id = id.getArticle_id();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                insert_comment task2 = new insert_comment();
                ArrayList<NameValuePair> params2=new ArrayList<NameValuePair>();

//                new Handler().postDelayed(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        Toast.makeText(getApplicationContext(),"Delay 1000!",Toast.LENGTH_LONG).show();
//                    }
//                }, 1000);

                params2.add(new BasicNameValuePair("comment_articleId", at_id));
                params2.add(new BasicNameValuePair("comment_content", boardwrite_content.getText().toString()));
                params2.add(new BasicNameValuePair("session_id", session_id));
                task2.execute(params2);

                Intent intent = new Intent(getApplicationContext(),BoardMain.class);
                intent.putExtra("session_id",session_id);
                intent.putExtra("board_type",board_type);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, spinner_list.get(position), Toast.LENGTH_LONG).show();//해당목차눌렸을때
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
