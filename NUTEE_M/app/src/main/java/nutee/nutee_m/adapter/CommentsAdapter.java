package nutee.nutee_m.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nutee.nutee_m.R;
import nutee.nutee_m.json.comments;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class CommentsAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater inflater = null;
    private ArrayList<comments> boardcomment_list;
    public TextView txt1;
    public TextView txt2;
    public TextView txt3;
    public WebView web1;
    int layout;

    public CommentsAdapter(Context context, int alayout, ArrayList<comments> arrayList){
        this.ctx = context;
        this.layout = alayout;
        this.boardcomment_list = arrayList;
        this.inflater =(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return boardcomment_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView=inflater.inflate(layout,parent,false);
        }

        txt1=(TextView)convertView.findViewById(R.id.boardcomment_listitem_nickname);
        txt1.setText(boardcomment_list.get(position).getUser_nickname());

        txt2=(TextView)convertView.findViewById(R.id.boardcomment_listitem_TIMESTAMP);
        txt2.setText(boardcomment_list.get(position).getTIMESTAMP());

        txt3=(TextView)convertView.findViewById(R.id.boardcomment_listitem_webview);
        txt3.setText(Html.fromHtml(boardcomment_list.get(position).getComment_content()));

//        web1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//   -     web1=(WebView)convertView.findViewById(R.id.boardcomment_listitem_webview);
//        WebSettings set = web1.getSettings();
//        set.setLoadWithOverviewMode(true);
//        set.setUseWideViewPort(true);
//        web1.getSettings().setUseWideViewPort(true);
//        web1.setInitialScale(25);
//        web1.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        web1.getSettings().
//        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        web1.getSettings().setDefaultTextEncodingName("UTF-8");
//-        web1.loadData(Html.fromHtml(boardcomment_list.get(position).getComment_content()).toString(),"text/html; charset=UTF-8",null);


        return convertView;
    }
}
