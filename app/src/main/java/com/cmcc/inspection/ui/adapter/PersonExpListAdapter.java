package com.cmcc.inspection.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, List<String>> dataset = new HashMap<>();
    private List<String> mGroupList;
    
    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return dataset.get(mGroupList.get(groupPosition)).size();
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataset.get(mGroupList.get(groupPosition)).get(childPosition);
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
        return null;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
