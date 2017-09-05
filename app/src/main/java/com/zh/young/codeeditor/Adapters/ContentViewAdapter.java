package com.zh.young.codeeditor.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zh.young.codeeditor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作为新建文件的选择文件类型的适配器
 * {@link com.zh.young.codeeditor.views.PullDownListView}
 */
public class ContentViewAdapter extends BaseAdapter {
    //作为填充的数据使用
    private  List<String> list = new ArrayList<>();
    private final Context context;

    public ContentViewAdapter(List<String> list, Context context){
        this.list = list;
        this.context = context;
        Log.i("lists", Arrays.toString(list.toArray()));
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.content_item,null);
            holder = new ViewHolder();
            holder.setItem((TextView) convertView.findViewById(R.id.content));
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        TextView item = holder.getItem();
        item.setText((String)getItem(position));
        return convertView;
    }

    private class ViewHolder{
         TextView getItem() {
            return item;
        }

         void setItem(TextView item) {
            this.item = item;
        }

        private TextView item;
    }
}

