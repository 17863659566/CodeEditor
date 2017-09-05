package com.zh.young.codeeditor.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zh.young.codeeditor.Adapters.ContentViewAdapter;
import com.zh.young.codeeditor.R;
import com.zh.young.codeeditor.util.DisplayTranslateUtil;
import com.zh.young.codeeditor.util.ConfigFileOperateUtil;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class PullDownListView extends RelativeLayout implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final String TAG = "PullDownListView";
    /**
     * 用于展示选择的文件类型
     */
    private TextView mDisplayView;
    private PopupWindow mPopupWindow;

    public String getFileType() {
        return (String) mFileType;
    }

    private CharSequence mFileType = ".c";

    public PullDownListView(Context context) {
        this(context,null);
    }

    public PullDownListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullDownListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        try {
            init();
        }catch (XmlPullParserException | IOException e){
            Log.i(TAG,"文件类型数据读取异常");
        }

    }

    private void init() throws IOException, XmlPullParserException {
        View view = View.inflate(getContext(), R.layout.select_file_type, this);
        mDisplayView = (TextView) view.findViewById(R.id.display_select_file_type);
        view.findViewById(R.id.down_arrow).setOnClickListener(this);
        mPopupWindow = new PopupWindow(getContext());
        ListView contentView = getContentView();
        setPopupWindow(contentView);
    }


    private ListView getContentView() throws IOException, XmlPullParserException {
        ListView contentView = new ListView(getContext());
        ContentViewAdapter adapter = new ContentViewAdapter(ConfigFileOperateUtil.getFileType(getContext()), getContext());
        contentView.setAdapter(adapter);
        contentView.setOnItemClickListener(this);
        return contentView;
    }

    private void setPopupWindow(ListView contentView) {
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setWidth((int) DisplayTranslateUtil.PX2DP(getContext(),100));
        mPopupWindow.setHeight((int) DisplayTranslateUtil.PX2DP(getContext(),100));
        mPopupWindow.setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.down_arrow :
                //现在问题：ListView报空,原因还是设置Adapter的时候造成的
                showUp(mDisplayView);
                break;
        }
    }

    private void showUp(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY,location[0],location[1] - mPopupWindow.getHeight());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mFileType = ((TextView)view.findViewById(R.id.content)).getText();
        mDisplayView.setText(mFileType);
        mPopupWindow.dismiss();
    }
}
