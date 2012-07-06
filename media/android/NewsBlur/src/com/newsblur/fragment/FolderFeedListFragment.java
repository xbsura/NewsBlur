package com.newsblur.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.newsblur.R;
import com.newsblur.database.DatabaseConstants;
import com.newsblur.database.FeedProvider;
import com.newsblur.database.FolderTreeAdapter;
import com.newsblur.view.FolderTreeViewBinder;

public class FolderFeedListFragment extends Fragment {

	private ExpandableListView list;
	private ContentResolver resolver;
	private FolderTreeAdapter folderAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resolver = getActivity().getContentResolver();
		Cursor cursor = resolver.query(FeedProvider.FOLDERS_URI, null, null, null, null);

		final String[] groupFrom = new String[] { DatabaseConstants.FOLDER_NAME };
		final int[] groupTo = new int[] { R.id.row_foldername };
		final String[] childFrom = new String[] { DatabaseConstants.FEED_TITLE, DatabaseConstants.FEED_FAVICON, DatabaseConstants.FEED_NEUTRAL_COUNT, DatabaseConstants.FEED_NEGATIVE_COUNT, DatabaseConstants.FEED_POSITIVE_COUNT };
		final int[] childTo = new int[] { R.id.row_feedname, R.id.row_feedfavicon, R.id.row_feedneutral, R.id.row_feednegative, R.id.row_feedpositive };

		folderAdapter = new FolderTreeAdapter(getActivity(), cursor, R.layout.row_folder_collapsed, R.layout.row_folder_expanded, groupFrom, groupTo, R.layout.row_feed, childFrom, childTo);
		folderAdapter.setViewBinder(new FolderTreeViewBinder());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_folderfeedlist, container);
		list = (ExpandableListView) v.findViewById(R.id.folderfeed_list);
		
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		list.setIndicatorBounds(width - 50, width - 10);
		list.setChildDivider(getActivity().getResources().getDrawable(R.drawable.divider_light));
		list.setAdapter(folderAdapter);
		
		return v;
	}

}
