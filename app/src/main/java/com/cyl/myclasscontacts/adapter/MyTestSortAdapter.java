package com.cyl.myclasscontacts.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.cyl.myclasscontacts.R;
import com.cyl.myclasscontacts.utils.UserModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by 永龙 on 2015/11/18.
 * 排序
 */
public class MyTestSortAdapter extends BaseAdapter implements SectionIndexer{
    private List<UserModel> list =null;
    private Context context =null;

    public MyTestSortAdapter(Context context, List<UserModel> list) {
        this.context = context;
        this.list =list;
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
        ViewHolder mHolder = null;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item, parent, false);
            mHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            mHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        UserModel userModel = list.get(position);

//
        /**
         * 加载网络图片
         */


        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            // 第一次出现该section
            mHolder.tvLetter.setVisibility(View.VISIBLE);
            mHolder.tvTitle.setText(userModel
                    .getFirstLetter());
            } else {
            mHolder.tvLetter.setVisibility(View.GONE);
        }
        mHolder.tvTitle.setText(userModel.getUsername());
        return convertView;
    }
    class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String firstLetter = list.get(i).getFirstLetter();
            char firstChar = firstLetter.charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    // 根据position获取分类的首字母的Char ascii值
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstLetter().charAt(0);
    }
}
