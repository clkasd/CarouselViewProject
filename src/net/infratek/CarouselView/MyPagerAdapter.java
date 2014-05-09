package net.infratek.CarouselView;


import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.arduandro.CarouselView.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.TextView;

public class MyPagerAdapter extends FragmentPagerAdapter implements
ViewPager.OnPageChangeListener {



	private boolean swipedLeft=false;
	private int lastPage=9;
	private MyLinearLayout cur = null;
	private MyLinearLayout next = null;
	private MyLinearLayout prev = null;
	private MyLinearLayout prevprev = null;
	private MyLinearLayout nextnext = null;
	private MainActivity context;
	private FragmentManager fm;
	private float scale;
	private boolean IsBlured;
	private static float minAlpha=0.6f;
	private static float maxAlpha=1f;
	private static float minDegree=60.0f;
	private int counter=0;

	public static float getMinDegree()
	{
		return minDegree;
	}
	public static float getMinAlpha()
	{
		return minAlpha;
	}
	public static float getMaxAlpha()
	{
		return maxAlpha;
	}
	
	public MyPagerAdapter(MainActivity context, FragmentManager fm) {
		super(fm);
		this.fm = fm;
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) 
	{



		// make the first pager bigger than others
		if (position == MainActivity.FIRST_PAGE)
			scale = MainActivity.BIG_SCALE;     	
		else
		{
			scale = MainActivity.SMALL_SCALE;
			IsBlured=true;

		}

		Log.d("position", String.valueOf(position));
		Fragment curFragment= MyFragment.newInstance(context, position, scale,IsBlured);
		cur = getRootView(position);
		next = getRootView(position +1);
		prev = getRootView(position -1);

		
		
		return curFragment;
	}

	@Override
	public int getCount()
	{		
		return 10;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) 
	{	
		if (positionOffset >= 0f && positionOffset <= 1f)
		{
			positionOffset=positionOffset*positionOffset;
			cur = getRootView(position);
			next = getRootView(position +1);
			prev = getRootView(position -1);
			nextnext=getRootView(position +2);

			ViewHelper.setAlpha(cur, maxAlpha-0.5f*positionOffset);
			ViewHelper.setAlpha(next, minAlpha+0.5f*positionOffset);
			ViewHelper.setAlpha(prev, minAlpha+0.5f*positionOffset);
			
			
			if(nextnext!=null)
			{	
				ViewHelper.setAlpha(nextnext, minAlpha);
				ViewHelper.setRotationY(nextnext, -minDegree);
			}
			if(cur!=null)
			{
				cur.setScaleBoth(MainActivity.BIG_SCALE 
						- MainActivity.DIFF_SCALE * positionOffset);

				ViewHelper.setRotationY(cur, 0);
			}

			if(next!=null)
			{
				next.setScaleBoth(MainActivity.SMALL_SCALE 
						+ MainActivity.DIFF_SCALE * positionOffset);
				ViewHelper.setRotationY(next, -minDegree);
			}
			if(prev!=null)
			{
				ViewHelper.setRotationY(prev, minDegree);
			}

			
			/*To animate it properly we must understand swipe direction
			 * this code adjusts the rotation according to direction.
			 */
			if(swipedLeft)
			{
				if(next!=null)
					ViewHelper.setRotationY(next, -minDegree+minDegree*positionOffset);
				if(cur!=null)
					ViewHelper.setRotationY(cur, 0+minDegree*positionOffset);
			}
			else 
			{
				if(next!=null)
					ViewHelper.setRotationY(next, -minDegree+minDegree*positionOffset);
				if(cur!=null)
				{
					ViewHelper.setRotationY(cur, 0+minDegree*positionOffset);
				}
			}
		}
		if(positionOffset>=1f)
		{
			ViewHelper.setAlpha(cur, maxAlpha);
		}
	}

	@Override
	public void onPageSelected(int position) {

/*
 * to get finger swipe direction
 */
		if(lastPage<=position)
		{
			swipedLeft=true;
		}
		else if(lastPage>position)
		{
			swipedLeft=false;
		}
		lastPage=position;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}



	private MyLinearLayout getRootView(int position)
	{
		MyLinearLayout ly;
		try {
			ly = (MyLinearLayout) 
					fm.findFragmentByTag(this.getFragmentTag(position))
					.getView().findViewById(R.id.root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		if(ly!=null)
			return ly;
		return null;
	}

	private String getFragmentTag(int position)
	{
		return "android:switcher:" + context.pager.getId() + ":" + position;
	}
}
