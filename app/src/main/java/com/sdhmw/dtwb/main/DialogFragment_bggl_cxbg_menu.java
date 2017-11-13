package com.sdhmw.dtwb.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdhmw.dtwb.model.Classes;
import com.sdhmw.dtwb.model.College;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wanglingsheng on 2017/9/22.
 * <p>
 * 报告管理-查询报告_list列表的二级菜单弹出窗口
 */

public class DialogFragment_bggl_cxbg_menu extends DialogFragment {


    private ExpandableListView listview;

    private List<College> colleges;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_bggl_cxbg_menu, container, false);

        initData();

        listview = (ExpandableListView) rootView.findViewById(R.id.tree_view_simple);




/*

        // 初始化数据
        List<Classes> classesList = new ArrayList<>();

        for(int i = 1 ;i<3;i++) {
            Classes classes = new Classes();

            classes.name = "计算机"+i+"班";

            List<String> list = new ArrayList<>();

            list.add("mm");
            list.add("dd");
            classes.students = list;

            classesList.add(classes);
        }


        // 构造适配器
        ClassesExpandableListViewAdapter adapter = new ClassesExpandableListViewAdapter(classesList,getActivity());
*/

        final SimpleExpandableListViewAdapter adapter = new SimpleExpandableListViewAdapter(colleges, getActivity());


        // 设置适配器
        listview.setAdapter(adapter);
        listview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                System.out.println("groupPosition:" + groupPosition);

                // 保证每次只有一个组是展开状态
                for(int i = 0; i < adapter.getGroupCount(); i++) {
                    if (i != groupPosition) {
                        listview.collapseGroup(i);
                    }
                }

            }
        });

        return rootView;

    }



    private void initData() {

        colleges = new ArrayList<>();

        for (int j = 1 ;j<3;j++) {

            College college = new College();

            college.name = "201" + j + "年度";

            List<Classes> classesList = new ArrayList<>();

            for(int i = 1 ;i<3;i++) {
                Classes classes = new Classes();

                classes.name = "半月";

                List<String> list = new ArrayList<>();

                list.add("20170101");
                list.add("20170102");
                classes.students = list;

                classesList.add(classes);
            }

            college.classList = classesList;
            colleges.add(college);
        }


    }


    /**
     *
     * 班级的适配器
     * Created by MH on 2016/6/16.
     */
    public class ClassesExpandableListViewAdapter extends BaseExpandableListAdapter {


        // 班级的集合
        private List<Classes> classes;

        // 创建布局使用
        private Activity activity;


        public ClassesExpandableListViewAdapter(List<Classes> classes, Activity activity) {
            this.classes = classes;
            this.activity = activity;
        }

        @Override
        public int getGroupCount() {
            // 获取一级条目的数量  就是班级的大小
            return classes.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // 获取对应一级条目下二级条目的数量，就是各个班学生的数量
            return classes.get(groupPosition).students.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            // 获取一级条目的对应数据  ，感觉没什么用
            return classes.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // 获取对应一级条目下二级条目的对应数据  感觉没什么用
            return classes.get(groupPosition).students.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            // 直接返回，没什么用
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // 直接返回，没什么用
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            // 谁知道这个是干什么。。。。
            return false;
        }

        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandablelistview_parent_item, null);
            }
            view.setTag(R.layout.expandablelistview_parent_item,parentPos);
            view.setTag(R.layout.expandablelistview_child_item, -1);
            TextView text = (TextView) view.findViewById(R.id.parent_title);
            text.setText(classes.get(parentPos).name);

            return view;
        }

        @Override

        public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandablelistview_child_item, null);
            }
            view.setTag(R.layout.expandablelistview_parent_item, parentPos);
            view.setTag(R.layout.expandablelistview_child_item, childPos);
            TextView text = (TextView) view.findViewById(R.id.child_title);
            text.setText(classes.get(parentPos).students.get(childPos));
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // 根据方法名，此处应该表示二级条目是否可以被点击   先返回true 再讲
            return true;
        }


        /**
         * 根据字符串生成布局，，因为我没有写layout.xml 所以用java 代码生成
         *
         *      实际中可以通过Inflate加载自己的自定义布局文件，设置数据之后并返回
         * @param string
         * @return
         */
        private TextView getGenericView(String string) {

            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(activity);
            textView.setLayoutParams(layoutParams);

            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            textView.setPadding(40, 20, 0, 20);
            textView.setText(string);
            textView.setTextColor(Color.RED);
            return textView;
        }

    }



    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                getContext());
        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }








    /**
     * 外层ExpandListView 适配器的实现
     */
    public class SimpleExpandableListViewAdapter extends BaseExpandableListAdapter {

        // 大学的集合
        private List<College> colleges;

        private Activity activity;


        public SimpleExpandableListViewAdapter(List<College> colleges, Activity activity) {
            this.colleges = colleges;
            this.activity = activity;
        }

        @Override
        public int getGroupCount() {
            return colleges.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // 很关键，，一定要返回  1
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return colleges.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return colleges.get(groupPosition).classList.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandablelistview_parent_item, null);
            }
            view.setTag(R.layout.expandablelistview_parent_item,parentPos);
            view.setTag(R.layout.expandablelistview_child_item, -1);
            TextView text = (TextView) view.findViewById(R.id.parent_title);
            text.setText(colleges.get(parentPos).name);
            return view;
        }



        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            // 返回子ExpandableListView 的对象  此时传入是该父条目，即大学的对象（有歧义。。）

            return getGenericExpandableListView(colleges.get(groupPosition));
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private TextView getGenericView(String string) {
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(activity);
            textView.setLayoutParams(layoutParams);

            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            textView.setPadding(40, 20, 0, 20);
            textView.setText(string);
            textView.setTextColor(Color.RED);
            return textView;
        }


        /**
         *  返回子ExpandableListView 的对象  此时传入的是该大学下所有班级的集合。
         */
        public ExpandableListView getGenericExpandableListView(College college){
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            CustomExpandableListView view = new CustomExpandableListView(activity);

            // 加载班级的适配器
            ClassesExpandableListViewAdapter adapter = new ClassesExpandableListViewAdapter(college.classList,activity);

            view.setAdapter(adapter);

            view.setPadding(20,0,0,0);


            //二级菜单的点击
            view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int parentPos, long l) {

                    System.out.println("二级菜单点击");



                    return false;
                }
            });

            view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPos, int childPos, long l) {

                    System.out.println("三级菜单点击");

                    System.out.println("parentPos:" + parentPos + "-" + "childPos:" + childPos);



                    return false;
                }
            });





            return view;
        }
    }


    /**
     *
     * 自定义ExpandableListView  解决嵌套之下显示不全的问题
     */
    public class CustomExpandableListView extends ExpandableListView {


        public CustomExpandableListView(Context context) {
            super(context);
        }

        public CustomExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public CustomExpandableListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // 解决显示不全的问题
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2
                    , MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
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
