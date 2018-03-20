package com.xalianhui.wifimanage.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.db.enty.SlideItem;

public class MainGradViewAdapter extends BaseAdapter {

    private List<SlideItem> list;;
    private Context context;
    private LayoutInflater inflater;
    private int cell_Width;

    private int onSelectItem = -1;

    public void setOnSelectItem(int onSelectItem) {
        this.onSelectItem = onSelectItem;
    }
    public MainGradViewAdapter(Context context, List<SlideItem> listdata, int cell_Width) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.cell_Width = cell_Width;
        setData(listdata);
    }
    
    private void setData(List<SlideItem> listdata) {
       if (listdata != null) {
           this.list = listdata;
       }else {
           this.list = new ArrayList<SlideItem>();
       }
    }
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public SlideItem getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            // 根据自定义的Item布局加载布局
            convertView = inflater.inflate(R.layout.item_maingradview,null);
            holder.title = (TextView) convertView.findViewById(R.id.item_maingrad_name);
            holder.image = (ImageView) convertView.findViewById(R.id.item_maingrad_img);
            holder.remark = (ImageView) convertView.findViewById(R.id.item_maingrad_remark);
//            holder.remark = (TextView) convertView.findViewById(R.id.home_dm_listtop_remake);
            // 将设置好的布�?保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.image.getLayoutParams();
            layoutParams.height = cell_Width;
            layoutParams.width = cell_Width;
//            holder.image.setLayoutParams(LayoutParams(cell_Width, cell_Width));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SlideItem entity = list.get(position);
            holder.title.setText(entity.getText());
            holder.image.setImageResource(entity.getResId());
        if (entity.getIsShow() == 1){
            holder.remark.setVisibility(View.VISIBLE);
        }else {
            holder.remark.setVisibility(View.GONE);
         }
        return convertView;
    }
 // ViewHolder静�?�类
   private  class ViewHolder {
        public TextView title;
        public ImageView image;
        public ImageView remark;
    }
public void updata(List<SlideItem> data) {
    setData(data);
    notifyDataSetChanged();
}

    
}
