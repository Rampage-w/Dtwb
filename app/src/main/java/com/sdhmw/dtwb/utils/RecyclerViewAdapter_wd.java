package com.sdhmw.dtwb.utils;

import java.util.List;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sdhmw.dtwb.main.R;
import com.sdhmw.dtwb.model.MenuBean;

public class RecyclerViewAdapter_wd extends RecyclerView.Adapter<RecyclerViewAdapter_wd.MyViewHolder>
{

	private List<MenuBean> mDatas;
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
	

	public RecyclerViewAdapter_wd(Context context, List<MenuBean> datas)
	{
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		MyViewHolder holder = new MyViewHolder(mInflater.inflate(
				R.layout.item_recycleview_wd, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position)
	{
		holder.tv.setText(mDatas.get(position).getMenu());	
		holder.iv.setImageResource(mDatas.get(position).getImg());
		
		
		
		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null)
		{
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int pos = holder.getPosition();
					mOnItemClickLitener.onItemClick(holder.itemView, pos);
				}
			});
			
			//注释掉长按删除
			/*holder.itemView.setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					int pos = holder.getPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
					removeData(pos);
					return false;
				}
			});*/
		}
	}

	@Override
	public int getItemCount()
	{
		return mDatas.size();
	}

	public void addData(int position)
	{
//		mDatas.add(position, "Insert One");
		notifyItemInserted(position);
	}


	public void removeData(int position)
	{
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	class MyViewHolder extends ViewHolder
	{

		TextView tv;
		ImageView iv;

		public MyViewHolder(View view)
		{
			super(view);
			tv = (TextView) view.findViewById(R.id.id_num);
			iv = (ImageView) view.findViewById(R.id.home_icon_iv);
		
		}
	}
}
