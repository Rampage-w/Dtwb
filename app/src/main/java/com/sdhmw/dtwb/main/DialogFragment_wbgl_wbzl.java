package com.sdhmw.dtwb.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglingsheng on 2017/9/22.
 * <p>
 * 维保管理 维保种类的二级菜单弹出窗口
 */

public class DialogFragment_wbgl_wbzl extends DialogFragment {

    private ExpandableListView mListview;

    private Map<String, List<String>> dataset = new HashMap();
    private String[] parentList = new String[]{"曳引与强制驱动电梯", "液压驱动电梯", "其他类型电梯","自动扶梯与自动人行道"};
    private List<String> childrenList1 = new ArrayList<>();
    private List<String> childrenList2 = new ArrayList<>();
    private List<String> childrenList3 = new ArrayList<>();
    private List<String> childrenList4 = new ArrayList<>();


    private MyExpandableListViewAdapter mAdapter;

    private String zcdm;
    private String sbsydd;
    private String Create_date;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_wbgl_wbzl, container, false);


        Bundle bundle = getArguments();

        zcdm = bundle.getString("zcdm");
        sbsydd = bundle.getString("sbsydd");
        Create_date = bundle.getString("Create_date");

        System.out.println("Dialog ---> zcdm:" + zcdm);
        System.out.println("Dialog ---> sbsydd:" + sbsydd);
        System.out.println("Dialog ---> Create_date:" + Create_date);


        initView(rootView);
        initData();

        return rootView;

    }

    private void initView(View rootView) {
        mListview = (ExpandableListView) rootView.findViewById(R.id.expandablelistview);

        mAdapter = new MyExpandableListViewAdapter();
        mListview.setAdapter(mAdapter);
        mListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPos, int childPos, long l) {


                String dt = parentList[parentPos];
                String dtWb = dataset.get(parentList[parentPos]).get(childPos);

                System.out.println("选择的电梯：" + dt);

                System.out.println("选择的维保种类：" + dtWb);

                Toast.makeText(getActivity(), dt + "——" + dtWb, Toast.LENGTH_SHORT).show();

                getDialog().dismiss();

                //切换 fragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                //系统 动画
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


                //V4包下 的Fragment不支持属性动画，只支持位移动画
                //自定义动画
                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_right_in, R.anim.push_right_out);

                Fragment_wbgl_wb fragment = new Fragment_wbgl_wb();

                Bundle bundle = new Bundle();
                bundle.putString("zcdm", zcdm);
                bundle.putString("sbsydd", sbsydd);
                bundle.putString("Create_date", Create_date);

                bundle.putString("dt", dt);
                bundle.putString("dtWb", dtWb);
                fragment.setArguments(bundle);

                transaction.replace(R.id.wbgl_Fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            }
        });

    }


    private void initData() {

        childrenList1.add("半月维保");
        childrenList1.add("季度维保");
        childrenList1.add("半年维保");
        childrenList1.add("年度维保");

        childrenList2.add("半月维保");
        childrenList2.add("季度维保");
        childrenList2.add("半年维保");
        childrenList2.add("年度维保");

        childrenList3.add("半月维保");
        childrenList3.add("季度维保");
        childrenList3.add("半年维保");
        childrenList3.add("年度维保");

        childrenList4.add("半月维保");
        childrenList4.add("季度维保");
        childrenList4.add("半年维保");
        childrenList4.add("年度维保");


//        childrenList1.add(parentList[0] + "-" + "second");
//        childrenList1.add(parentList[0] + "-" + "third");
//
//        childrenList2.add(parentList[1] + "-" + "first");
//        childrenList2.add(parentList[1] + "-" + "second");
//        childrenList2.add(parentList[1] + "-" + "third");
//
//        childrenList3.add(parentList[2] + "-" + "first");
//        childrenList3.add(parentList[2] + "-" + "second");
//        childrenList3.add(parentList[2] + "-" + "third");

        dataset.put(parentList[0], childrenList1);
        dataset.put(parentList[1], childrenList2);
        dataset.put(parentList[2], childrenList3);
        dataset.put(parentList[3], childrenList4);

    }


    private class MyExpandableListViewAdapter extends BaseExpandableListAdapter{

        //  获得父项的数量
        @Override
        public int getGroupCount() {
            return dataset.size();
        }

        //  获得某个父项的子项数目
        @Override
        public int getChildrenCount(int parentPos) {
            return dataset.get(parentList[parentPos]).size();
        }

        //  获得某个父项
        @Override
        public Object getGroup(int parentPos) {
            return dataset.get(parentList[parentPos]);
        }

        //  获得某个父项的某个子项
        @Override
        public Object getChild(int parentPos, int childPos) {
            return dataset.get(parentList[parentPos]).get(childPos);
        }

        //  获得某个父项的id
        @Override
        public long getGroupId(int parentPos) {
            return parentPos;
        }

        //  获得某个父项的某个子项的id
        @Override
        public long getChildId(int parentPos, int childPos) {
            return childPos;
        }

        //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
        @Override
        public boolean hasStableIds() {
            return false;
        }

        //  获得父项显示的view
        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandablelistview_parent_item, null);
            }
            view.setTag(R.layout.expandablelistview_parent_item,parentPos);
            view.setTag(R.layout.expandablelistview_child_item, -1);
            TextView text = (TextView) view.findViewById(R.id.parent_title);
            text.setText(parentList[parentPos]);
            return view;
        }

        //  获得子项显示的view
        @Override
        public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandablelistview_child_item, null);
            }
            view.setTag(R.layout.expandablelistview_parent_item, parentPos);
            view.setTag(R.layout.expandablelistview_child_item, childPos);
            TextView text = (TextView) view.findViewById(R.id.child_title);
            text.setText(dataset.get(parentList[parentPos]).get(childPos));
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //isChildSelectable 即时返回false子项布局中的点击事件(there)一样生效 但子项的点击不生效
//                    Toast.makeText(getActivity(),"点到了内置的textview",Toast.LENGTH_SHORT).show();
                }
            });


            return view;
        }

        //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
        @Override
        public boolean isChildSelectable(int parentPos, int childPos) {
//            return false;
            return true;
        }
    }


    //横向充满屏幕
    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }
}
