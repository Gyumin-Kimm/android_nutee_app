package nutee.nutee_m.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nutee.nutee_m.R;
import nutee.nutee_m.json.articles;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class BoardAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater inflater = null;
    private ArrayList<articles> board_list;
    public TextView txt1;
    public TextView txt2;
    public TextView txt3;
    public TextView txt4;
    int layout;

    public BoardAdapter(Context context, int alayout, ArrayList<articles> arrayList){
        this.ctx = context;
        this.layout = alayout;
        this.board_list = arrayList;
        this.inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return board_list.size();
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
            convertView = inflater.inflate(layout, parent, false);
        }

        txt1 = (TextView)convertView.findViewById(R.id.board_listitem_title);
        txt1.setText(board_list.get(position).getArticle_title());
//        txt1.setText(board_list.get(position).getBOARD_NUM());

        txt2 = (TextView)convertView.findViewById(R.id.board_listitem_usernickname);
        txt2.setText(board_list.get(position).getUser_nickname());
//        txt2.setText(board_list.get(position).getBOARD_SUBJECT());

        txt3 = (TextView)convertView.findViewById(R.id.board_listitem_date);
        txt3.setText(board_list.get(position).getTIMESTAMP());
//        txt3.setText(board_list.get(position).getBOARD_ID());

        txt4 = (TextView)convertView.findViewById(R.id.board_listitem_categoryname);
        txt4.setText(board_list.get(position).getCategory_name());

        return convertView;
    }
}
