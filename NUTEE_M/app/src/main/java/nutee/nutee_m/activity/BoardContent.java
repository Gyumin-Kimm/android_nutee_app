package nutee.nutee_m.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nutee.nutee_m.HttpGet;
import nutee.nutee_m.R;
import nutee.nutee_m.adapter.CommentsAdapter;
import nutee.nutee_m.function.insert_comment;
import nutee.nutee_m.function.remove;
import nutee.nutee_m.json.article;
import nutee.nutee_m.json.comments;
import nutee.nutee_m.json.form;

public class BoardContent extends Activity {

    TextView boardcontent_title;
    EditText boardcontent_comment_write;
    Button boardcontent_comment_button;
    ListView boardcontent_comment_listview;

    String article_id, session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        Intent intent = getIntent();
        session_id = intent.getExtras().getString("session_id");
        article_id = intent.getExtras().getString("article_id");
//        Log.i("BCsession_id",session_id);

//        Toast.makeText(getApplicationContext(),intent.getExtras().getString("board_id"),Toast.LENGTH_LONG).show();

        ArrayList<NameValuePair> params =  new ArrayList<NameValuePair>();
        get_article task = new get_article();
        task.execute(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_content, menu);
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
    class get_article extends AsyncTask<ArrayList<NameValuePair>,Void,String> {    //comment_listview print
        ArrayList<comments> boardcomment_list;
        article ga;
        comments comm;
        form ab;

        @Override
        protected String doInBackground(ArrayList<NameValuePair>... params) {
            HttpGet get= new HttpGet();
            String result=get.httpget(HttpGet.URL+"get_article?article_id=" + article_id ,params[0]);
            Log.i("BContent:get_article", result);
            return result;
        }
        @Override
        protected void onPostExecute(String Jason)
        {
            super.onPostExecute(Jason);

            try {
                JSONObject obj = new JSONObject(Jason);

                ab = new form();

                ab.setCode(obj.getString("code"));
                ab.setMessages(obj.getString("messages"));
                ab.setBody(obj.getString("body"));

                JSONObject body = new JSONObject(ab.getBody());

                ga = new article();

                ga.setId(body.getString("id"));
                ga.setArticle_writer(body.getString("article_writer"));
                ga.setArticle_title(body.getString("article_title"));
                ga.setTIMESTAMP(body.getString("TIMESTAMP"));
                ga.setBoard_permalink(body.getString("board_permalink"));
                ga.setBoard_name(body.getString("board_name"));
                ga.setUser_nickname(body.getString("user_nickname"));
                ga.setComments(body.getString("comments"));

                boardcomment_list = new ArrayList<comments>();
                final JSONArray article_comment = new JSONArray(ga.getComments());

                for(int i=0;i<article_comment.length();i++){

                    JSONObject get_cmt_obj = article_comment.getJSONObject(i);

                    comm= new comments();

                    comm.setId(get_cmt_obj.getString("id"));
                    comm.setComment_content(get_cmt_obj.getString("comment_content"));
                    comm.setComment_articleId(get_cmt_obj.getString("comment_articleId"));
                    comm.setComment_userId(get_cmt_obj.getString("comment_userId"));
                    comm.setTIMESTAMP(get_cmt_obj.getString("TIMESTAMP"));
                    comm.setUser_nickname(get_cmt_obj.getString("user_nickname"));

                    boardcomment_list.add(comm);

                }
                boardcontent_comment_button = (Button)findViewById(R.id.boardcontent_comment_button);       // write comment
                boardcontent_comment_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boardcontent_comment_write = (EditText)findViewById(R.id.boardcontent_comment);
                        boardcontent_comment_write.getText();

                        ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();

                        params.add(new BasicNameValuePair("comment_articleId",article_id));
                        params.add(new BasicNameValuePair("comment_content",boardcontent_comment_write.getText().toString()));
                        params.add(new BasicNameValuePair("session_id",session_id));
                        insert_comment task=new insert_comment();
                        task.execute(params);

//                        Intent intent = new Intent(getApplicationContext(), BoardContent.class);
//                        intent.putExtra("article_id",article_id);

                        get_article task2 = new get_article();
                        task2.execute(params);

                        boardcontent_comment_write.setText(null);

                    }
                });

                CommentsAdapter BCAdapter = new CommentsAdapter(getApplicationContext(),R.layout.listitem_comment,boardcomment_list);
                boardcontent_comment_listview = (ListView)findViewById(R.id.boardcontent_comment_listview);
                boardcontent_comment_listview.setAdapter(BCAdapter);

                boardcontent_comment_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {    // comment Longclick event(delete)
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            final JSONObject selected_obj = article_comment.getJSONObject(position);
                            comm = new comments();

                            comm.setId(selected_obj.getString("id"));

                            AlertDialog.Builder builder = new AlertDialog.Builder(BoardContent.this);

                            builder.setMessage("delete this")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("kind","comment"));
                                            params.add(new BasicNameValuePair("id",comm.getId()));
                                            params.add(new BasicNameValuePair("session_id",session_id));

                                            remove rm = new remove();
                                            try {
                                                String result = rm.execute(params).get();

                                                JSONObject getform = new JSONObject(result);
                                                form ab = new form();

                                                ab.setCode(getform.getString("code"));
                                                ab.setMessages(getform.getString("messages"));
                                                try {
                                                    ab.setBody(getform.getString("body"));
                                                } catch (Exception e) {
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(BoardContent.this);

                                                    // 여기서 부터는 알림창의 속성 설정
                                                    builder1.setMessage(ab.getMessages())        // 메세지 설정
                                                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                // 확인 버튼 클릭시 설정
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    Intent intent = new Intent(getApplicationContext(), BoardContent.class);
                                                                    intent.putExtra("session_id", session_id);
                                                                    intent.putExtra("article_id", article_id);
                                                                    startActivity(intent);
                                                                    finish();

                                                                    dialog.cancel();
                                                                }
                                                            });
                                                    AlertDialog dialog1 = builder1.create();    // 알림창 객체 생성
                                                    dialog1.show();    // 알림창 띄우기
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return true;
                    }
                });

                boardcontent_title = (TextView)findViewById(R.id.boardcontent_title);
                boardcontent_title.setText(ga.getArticle_title());

            }
            catch(JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
