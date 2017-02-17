package com.exz.antcargo.activity.popupwindow;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.exz.antcargo.R;

import java.util.ArrayList;
import java.util.LinkedList;


public class MiddleFilterLiftView extends LinearLayout implements ViewBaseAction{

	private ListView regionListView;
	private ListView plateListView;


	private String[] provice;
	private String[][] city;

	private ArrayList<String> groups = new ArrayList<String>();
	private LinkedList<String> childrenItem = new LinkedList<String>();
	private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();

	private TextAdapterGu plateListViewAdapter;
	private TextAdapterGu earaListViewAdapter;
	private OnItemSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "不限";

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

	private enum showSubwayOrBcd {
		SUBWAY, BCD
	}

	private showSubwayOrBcd displayItem = showSubwayOrBcd.BCD;

	public MiddleFilterLiftView(Context context, String[] provice, String[][] city) {
		super(context);
		this.provice = provice;
		this.city = city;
		init(context);
	}

	public MiddleFilterLiftView(Context context) {
		super(context);
		init(context);
	}
	
	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}

	private void init(final Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		regionListView = (ListView) findViewById(R.id.listView);
		plateListView = (ListView) findViewById(R.id.listView2);

		setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_right));

		for (int i = 0; i < provice.length ; i++) {
			groups.add(provice[i]);
			LinkedList<String> tItem = new LinkedList<String>();
			
			for (int j = 0; j < city[i].length; j++) {
				tItem.add(city[i][j]);
			}
			children.put(i, tItem);
		}

		earaListViewAdapter = new TextAdapterGu(context, groups, R.drawable.choose_item_selected, R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(17);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		regionListView.setAdapter(earaListViewAdapter);
		earaListViewAdapter.setOnItemClickListener(new TextAdapterGu.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				if (position < children.size()) {
					childrenItem.clear();
					childrenItem.addAll(children.get(position));
					plateListViewAdapter.notifyDataSetChanged();
				}
			}
		});
		if (tEaraPosition < children.size())
			childrenItem.addAll(children.get(tEaraPosition));
		plateListViewAdapter = new TextAdapterGu(context, childrenItem, R.drawable.choose_item_right, R.drawable.choose_plate_item_selector);
		plateListViewAdapter.setTextSize(15);
		plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		plateListView.setAdapter(plateListViewAdapter);
		plateListViewAdapter.setOnItemClickListener(new TextAdapterGu.OnItemClickListener() {

			@Override
			public void onItemClick(View view, final int position) {

				showString = childrenItem.get(position);
				if (mOnSelectListener != null) {

					mOnSelectListener.getValue(showString);
				}
			}
		});
		
		if (tBlockPosition < childrenItem.size())
			showString = childrenItem.get(tBlockPosition);
		if (showString.contains("不限")) {
			showString = showString.replace("不限", "");
		}
		setDefaultSelect();

	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnItemSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnItemSelectListener {
		public void getValue(String showText);
	}

}
