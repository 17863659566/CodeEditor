package com.zh.young.codeeditor.Adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.young.codeeditor.R;

import java.io.File;
import java.util.ArrayList;

/**
 * 创建文件列表的适配器
 */

public class RecycleViewAdapter extends Adapter<RecycleViewAdapter.Holder> implements View.OnClickListener {
    private final ArrayList<File> data;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    /**
     * 用于创建ViewHolder由Adapter内部来管理这个Holder
     * @param data  需要展示的数据
     */
    public RecycleViewAdapter(ArrayList<File> data){
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("RecycleViewAdapter","onCreateViewHolder called");
        View view = View.inflate(parent.getContext(), R.layout.recycle_view_item, null);
        view.setOnClickListener(this);
        return new Holder(view);
    }

    /**
     * 在这里绑定数据
     */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.i("RecycleViewAdapter","onBindViewHolder called" + data.size());
        File file = data.get(position);
        if(file.isFile()){
            holder.fileIcon.setImageResource(R.drawable.icon_file);

        }else{
            holder.fileIcon.setImageResource(R.drawable.folder);
        }
        holder.fileName.setText(file.getName());
        holder.fileName.setTag(data.get(position));
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount",data.size()+"");
        return data.size();
    }

    @Override
    public void onClick(View v) {
        File file = (File) v.findViewById(R.id.tv_file_name).getTag();

        mOnItemClickListener.onItemClick(v, file);
    }

    /**
     * 这个内部类是为了创建Holder，来供RecyclerViewAdapter来使用，而不用我们手动设置了，RecyclerView帮忙处理了
     */
    class Holder extends RecyclerView.ViewHolder{
        ImageView fileIcon;
        TextView fileName;
        Holder(View itemView) {
            super(itemView);
            fileIcon = (ImageView) itemView.findViewById(R.id.folder_icon);
            fileName = (TextView) itemView.findViewById(R.id.tv_file_name);
        }
    }

    public interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,File data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
