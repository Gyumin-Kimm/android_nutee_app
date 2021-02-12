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
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nutee.nutee_m.HttpGet;
import nutee.nutee_m.R;
import nutee.nutee_m.adapter.BoardAdapter;
import nutee.nutee_m.function.remove;
import nutee.nutee_m.json.articles;
import nutee.nutee_m.json.body;
import nutee.nutee_m.json.categories;
import nutee.nutee_m.json.form;

public class BoardMain extends Activity {

    ListView boardlv;
    BoardAdapter BAdapter;
    ArrayList<categories> cgs_list;

    Button writebtn;

    String session_id, board_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);

        Intent intent = getIntent();
        session_id =intent.getExtras().getString("session_id");
        board_type =intent.getExtras().getString("board_type");
        Log.i("session_id", session_id);

        writebtn = (Button)findViewById(R.id.boardmain_writebtn);
        writebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BoardWrite.class);
                intent.putExtra("categories",cgs_list);
                intent.putExtra("session_id",session_id);
                intent.putExtra("board_type",board_type);
                startActivity(intent);
            }
        });

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        get_articles task = new get_articles();
        task.execute(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.menu_write){
            Intent intent = new Intent(getApplicationContext(),BoardWrite.class);
            intent.putExtra("categories",cgs_list);
            intent.putExtra("session_id",session_id);
            intent.putExtra("board_type",board_type);
            startActivity(intent);
        }else if(id == R.id.menu_refresh){
            BAdapter.notifyDataSetChanged();    // 안먹힘
            Intent intent = new Intent(getApplicationContext(),BoardMain.class);
            intent.putExtra("session_id",session_id);
            intent.putExtra("board_type",board_type);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    class get_articles extends AsyncTask<ArrayList<NameValuePair>,Void,String> {
        ArrayList<articles> board_list;
        HttpGet get;
        articles gas;
        categories cgs;
        form ab;
        body gas_body;

        @Override
        protected String doInBackground(ArrayList<NameValuePair>... params) {

            get= new HttpGet();
            String result=get.httpget(HttpGet.URL+"get_articles?board_id="+board_type,params[0]);

            Log.i("BoardMain/get_articles",result);
            return result;
        }

        @Override
        protected void onPostExecute(String Jason)

        {
            super.onPostExecute(Jason);
            try {
                JSONObject obj = new JSONObject(Jason); // api_bean
                ab = new form();
                ab.setCode(obj.getString("code"));
                ab.setMessages(obj.getString("messages"));
                ab.setBody(obj.getString("body"));

                JSONObject bodyobj = new JSONObject(ab.getBody());  //  body divide
                gas_body = new body();
                gas_body.setArticles(bodyobj.getString("articles"));
                gas_body.setCategories(bodyobj.getString("categories"));

                final JSONArray articles = new JSONArray(gas_body.getArticles()); //  articles divide
                board_list = new ArrayList<articles>();
                for (int i=0; i<articles.length(); i++) {

                    JSONObject get_as_obj = articles.getJSONObject(i);
                    gas = new articles();

                    gas.setId(String.valueOf(get_as_obj.getString("id")));
                    gas.setArticle_boardId(get_as_obj.getString("article_boardId"));
                    gas.setArticle_title(String.valueOf(get_as_obj.getString("article_title")));
                    gas.setArticle_writerId(get_as_obj.getString("article_writerId"));
                    gas.setUser_auth(get_as_obj.getString("user_auth"));
                    gas.setUser_nickname(get_as_obj.getString("user_nickname"));
                    gas.setArticle_commentCount(get_as_obj.getString("article_commentCount"));
                    gas.setTIMESTAMP(String.valueOf(get_as_obj.getString("TIMESTAMP")));
                    gas.setCategory_name(get_as_obj.getString("category_name"));
//                    gas.setArticle_writerNickname(get_as_obj.getString("article_writerNickname"));
//                    gas.setBoard_permalink(String.valueOf(board_object.getString("board_permalink")));
//                    gas.setUser_nickname(String.valueOf(board_object.getString("user_nickname")));
//                    gas.setUser_pk(String.valueOf(board_object.getString("user_pk")));

                    board_list.add(gas);
                }

                BAdapter = new BoardAdapter(getApplicationContext(), R.layout.listitem_board, board_list);  // ListView adapt
                boardlv=(ListView)findViewById(R.id.board_listview);
                boardlv.setAdapter(BAdapter);

                boardlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  // Listview click event
                        Intent intent = new Intent(getApplicationContext(), BoardContent.class);
                        intent.putExtra("article_id", board_list.get(position).getId().toString());
                        intent.putExtra("session_id", session_id);

                        BoardMain.this.startActivity(intent);
                    }
                });
                boardlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {      //Listview Longclick event(delete)
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        try {
                            final JSONObject selected_obj = articles.getJSONObject(position);

                            gas = new articles();

                            gas.setId(selected_obj.getString("id"));

                            AlertDialog.Builder builder = new AlertDialog.Builder(BoardMain.this);
                                     builder.setMessage("게시글을 삭제하시겠습니까?")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("kind", "article"));
                                            params.add(new BasicNameValuePair("id", gas.getId()));
                                            params.add(new BasicNameValuePair("session_id", session_id));

                                            remove rm = new remove();
                                            try {
                                                String result = rm.execute(params).get();

                                                JSONObject getform = new JSONObject(result);
                                                form ab = new form();

                                                ab.setCode(getform.getString("code"));
                                                ab.setMessages(getform.getString("messages"));
                                                try {
                                                    ab.setBody(getform.getString("body")); // form body check
                                                } catch (Exception e) {
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(BoardMain.this);

                                                    // 여기서 부터는 알림창의 속성 설정
                                                    builder1.setMessage(ab.getMessages())        // 메세지 설정
                                                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                // 확인 버튼 클릭시 설정
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    Intent intent = new Intent(getApplicationContext(), BoardMain.class);
                                                                    intent.putExtra("session_id", session_id);
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

                JSONArray categories = new JSONArray(gas_body.getCategories()); //  categories divide
                cgs_list = new ArrayList<categories>();
                for(int i=0;i<categories.length();i++){
                    JSONObject get_cgs_obj = categories.getJSONObject(i);
                    cgs = new categories();

                    cgs.setId(get_cgs_obj.getString("id"));
                    cgs.setCategory_name(get_cgs_obj.getString("category_name"));
                    cgs.setCategory_boardId(get_cgs_obj.getString("category_boardId"));

                    cgs_list.add(cgs);
                }
            }
            catch(JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
