package com.cmcc.inspection.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.lib_network.model.ManagerModel;
import com.cmcc.lib_utils.utils.Utils;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/11/11 18:41
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class PersonExpListAdapter extends BaseExpandableListAdapter {
    private List<ManagerModel.InfoBean> dataset;
    
    public PersonExpListAdapter(List<ManagerModel.InfoBean> dataset) {
        this.dataset = dataset;
    }
    
    @Override
    public int getGroupCount() {
        return dataset.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return dataset.get(groupPosition).renyuan.size();
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        return dataset.get(groupPosition);
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataset.get(groupPosition).renyuan.get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = null;
        GroupHolder groupholder = null;
        if (convertView != null) {
            view = convertView;
            groupholder = (GroupHolder) view.getTag();
        } else {
            view = View.inflate(Utils.getContext(), R.layout.item_fortress_group, null);
            groupholder = new GroupHolder();
            groupholder.mGroupName = (TextView) view.findViewById(R.id.tv_item_fortress_manager_group_name);
            groupholder.mGroupContent = (TextView) view.findViewById(R.id.tv_item_fortress_manager_group_content);
            view.setTag(groupholder);
        }
        groupholder.mGroupName.setText(dataset.get(groupPosition).name);
        groupholder.mGroupContent.setText(dataset.get(groupPosition).renyuan.size() + "人");
        return view;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        ChildHolder childHolder = null;
        if (convertView != null) {
            view = convertView;
            childHolder = (ChildHolder) view.getTag();
        } else {
            view = View.inflate(Utils.getContext(), R.layout.item_fortress_child, null);
            childHolder = new ChildHolder();
            childHolder.mChildName = (TextView) view.findViewById(R.id.tv_item_fortress_manager_child_name);
            view.setTag(childHolder);
        }
        childHolder.mChildName.setText(dataset.get(groupPosition).renyuan.get(childPosition).name);
        return view;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
    private static class GroupHolder {
        TextView mGroupName;
        TextView mGroupContent;
    }
    
    private static class ChildHolder {
        TextView mChildName;
    }
}
