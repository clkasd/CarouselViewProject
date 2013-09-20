package net.leolink.android.simpleinfinitecarousel;

import com.nineoldandroids.view.ViewHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyFragment extends Fragment {
	
	public static Fragment newInstance(MainActivity context, int pos, 
			float scale,boolean IsBlured)
	{
		
		Bundle b = new Bundle();
		b.putInt("pos", pos);
		b.putFloat("scale", scale);
		b.putBoolean("IsBlured", IsBlured);
		return Fragment.instantiate(context, MyFragment.class.getName(), b);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		LinearLayout l = (LinearLayout)
				inflater.inflate(R.layout.mf, container, false);
		
		int pos = this.getArguments().getInt("pos");
		
		TextView tv = (TextView) l.findViewById(R.id.viewID);
		tv.setText("Position = " + pos);

		MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
		float scale = this.getArguments().getFloat("scale");
		root.setScaleBoth(scale);
		boolean isBlured=this.getArguments().getBoolean("IsBlured");
		if(isBlured)
		{
			ViewHelper.setAlpha(root,MyPagerAdapter.getMinAlpha());
			ViewHelper.setRotationY(root, MyPagerAdapter.getMinDegree());
		}
		return l;
	}
}
