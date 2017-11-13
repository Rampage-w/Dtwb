package com.sdhmw.dtwb.utils;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdhmw.dtwb.main.R;

/**
 * 维保管理查询list
 * @author wanglingsheng
 * @date 2017-11-9
 */
public class RecyclerViewAdapter_wbgl_list extends
        RecyclerView.Adapter<RecyclerViewAdapter_wbgl_list.MyViewHolder>
{

    private ArrayList<Object> mDatas;
    private LayoutInflater mInflater;


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public RecyclerViewAdapter_wbgl_list(Context context, ArrayList<Object> datas)
    {
        mInflater = LayoutInflater.from(context);
        if(datas == null){
            throw new IllegalArgumentException("model Data must not be null");
        }
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.recyclerview_item_wbgl_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {


        if (position != mDatas.size()) {
            holder.iv_detail.setVisibility(View.VISIBLE);//显示——详情图片
            holder.listlayout.setVisibility(View.VISIBLE);//显示——数据
            holder.tv_more.setVisibility(View.GONE);//隐藏——点击下一页

            holder.tv_i.setText((position + 1) < 10 ? ("0" + (position + 1)) : ((position + 1) + ""));

            Map map = (Map) mDatas.get(position);

            String zcdm = (String) map.get("zcdm");
            String sbsydd = (String) map.get("sbsydd");
            String Create_date = (String) map.get("Create_date");

            //设置条目状态
            holder.item_wbgl_list_zcdm.setText(zcdm);
            holder.item_wbgl_list_sbsydd.setText(sbsydd);
            holder.item_wbgl_list_Create_date.setText(Create_date);


        } else {

//            if(loadmore == true){
//                holder.tv_more.setText("点击加载下一页");
//            }else{
//                holder.tv_more.setText("没有更多了");
//            }

            holder.iv_detail.setVisibility(View.GONE);//隐藏——详情图片
            holder.listlayout.setVisibility(View.GONE);//隐藏——数据
            holder.tv_more.setVisibility(View.VISIBLE);//显示——点击下一页

        }



        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
//                    removeData(pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size()+1;     ///增加一行空数据
    }

    public void addData(int position)
    {
        mDatas.add(position, "Insert One");
        notifyItemInserted(position);
    }

    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends ViewHolder
    {

        TextView item_wbgl_list_zcdm;   //注册代码
        TextView item_wbgl_list_sbsydd; //使用地点
        TextView item_wbgl_list_Create_date;    //维保日期

        TextView tv_i;    //序号
        LinearLayout listlayout; //数据
        ImageView iv_detail; //小拐图标 详情
        TextView tv_more;    //加载下一页



        MyViewHolder(View view)
        {
            super(view);
            this.item_wbgl_list_zcdm = (TextView) view.findViewById(R.id.item_wbgl_list_zcdm);
            this.item_wbgl_list_sbsydd = (TextView) view.findViewById(R.id.item_wbgl_list_sbsydd);
            this.item_wbgl_list_Create_date = (TextView) view.findViewById(R.id.item_wbgl_list_Create_date);

            this.tv_i = (TextView) view.findViewById(R.id.item_wbgl_list_num);
            this.listlayout = (LinearLayout) view.findViewById(R.id.item_wbgl_list_content_layout);
            this.iv_detail = (ImageView) view.findViewById(R.id.item_wbgl_list_arr);
            this.tv_more = (TextView) view.findViewById(R.id.wbgl_list_loadmore);


        }
    }
}