package com.sdhmw.dtwb.utils;

import java.util.ArrayList;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sdhmw.dtwb.main.R;

/**
 * 我的任务 表格 列表
 * @author wanglingsheng
 * @date 2017-4-27
 */
public class RecyclerViewAdapter_bggl_dbrw extends RecyclerView.Adapter<ViewHolder>{
	
	private ArrayList<String> mList;
	private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
	private boolean mIsSelectable = false;
	
	private LayoutInflater mInflater;
	
	
	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
		void onItemCheckBoxClick(View view, int position);
	}
	private OnItemClickLitener mOnItemClickLitener;
	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	
	public RecyclerViewAdapter_bggl_dbrw(Context context, ArrayList<String> list){
		mInflater = LayoutInflater.from(context);
		if(list == null){
			 throw new IllegalArgumentException("model Data must not be null");
		}
		mList = list;
	}
	
	//更新adpter的数据和选择状态
	 public void updateDataSet(ArrayList<String> list) {
        this.mList = list;
        mSelectedPositions = new SparseBooleanArray();
//	        ab.setTitle("已选择" + 0 + "项");
    }
	
	//获得选中条目的结果
	public ArrayList<String> getSelectedItem(){
		  ArrayList<String> selectList = new ArrayList<String>();
		  for (int i = 0; i < mList.size(); i++) {
                if (isItemChecked(i)) {
                    selectList.add(mList.get(i));
                }
            }
            return selectList;
	}
	
	
	
	public class ListItemViewHolder extends ViewHolder{
        //ViewHolder
        CheckBox checkBox;
        TextView mainTitle;

        ListItemViewHolder(View view) {
            super(view);
            this.mainTitle = (TextView) view.findViewById(R.id.text);
            this.checkBox = (CheckBox) view.findViewById(R.id.select_checkbox);
        }
    }
	
	
	@Override
	public int getItemCount() {
		return mList == null ? 0 : mList.size();
	}
	
	@Override
	public void onBindViewHolder(final ViewHolder holder,final int i) {
		
//		Log.e("onBindViewHolder", "onBindViewHolder");
		
	     //设置条目状态
        ((ListItemViewHolder) holder).mainTitle.setText(mList.get(i));
        ((ListItemViewHolder) holder).checkBox.setChecked(isItemChecked(i));

        //checkBox的监听
        ((ListItemViewHolder) holder).checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
//                ab.setTitle("已选择" + getSelectedItem().size() + "项");
            }
        });

        //条目view的监听
       /* ((ListItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
                notifyItemChanged(i);
//                ab.setTitle("已选择" + getSelectedItem().size() + "项");
            }
        });*/

        // 如果设置了回调，则设置点击事件
        if(mOnItemClickLitener != null){
        	//checkBox
        	 ((ListItemViewHolder) holder).checkBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int pos = holder.getPosition();
					mOnItemClickLitener.onItemCheckBoxClick(((ListItemViewHolder) holder).checkBox, pos);
				}
			});
        	
        	holder.itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int pos = holder.getPosition();
					mOnItemClickLitener.onItemClick(holder.itemView, pos);
				}
			});
        	
        	holder.itemView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					int pos = holder.getPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
//					removeData(pos);
					return false;
				}
			});
        }
        
        
	}
	

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View itemView = mInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_bggl_dbrw, viewGroup, false);
		return new ListItemViewHolder(itemView);
	}
	
	
	//设置给定位置条目的选择状态
    public void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }
    
  //根据位置判断条目是否选中
	public boolean isItemChecked(int position) {
		return mSelectedPositions.get(position);
	}
	
	//根据位置判断条目是否可选
    private boolean isSelectable() {
        return mIsSelectable;
    }
    //设置给定位置条目的可选与否的状态
    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }
    
    
//    添加item
    public void addData(int position)
	{
		mList.add(position, "Insert One");
		notifyItemInserted(position);
	}
    
//   删除item
    public void removeData(int position)
	{
		mList.remove(position);
		notifyItemRemoved(position);
	}
    
    
    //全选
    public ArrayList<String> getSelectedItemAll(boolean flags){
//    样式选中
//    数值添加进list
    	ArrayList<String> selectListALl = new ArrayList<String>();
    	
    	if(flags){
    		for (int i = 0; i < mList.size(); i++) {
    			
    			setItemChecked(i, true);	//每添加一次 就往mSelectedPositions 里面添加一个数据
    			//获取list集合
    			if (isItemChecked(i)) {
    				selectListALl.add(mList.get(i));
    			}
    			
    		}
    	}else {
    		for (int i = 0; i < mList.size(); i++) {
    			
    			setItemChecked(i, false);	//每添加一次 就往mSelectedPositions 里面添加一个数据
    			//获取list集合
    			selectListALl.clear();
//    			if (isItemChecked(i)) {
//    				selectListALl.add(mList.get(i));
//    			}
    			
    		}
    	}
    	
    	return selectListALl;
    	
    }
	  
}
