package com.android.compus.view;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * ��������ҳ--ViewPagerCompat������
 */
public class ViewPagerAdapter extends PagerAdapter {
	
	private List<View> mListViews;  
	private List<String> mTitleList;
	
	public ViewPagerAdapter(List<View> mListViews, List<String> mTitleList) {
		this.mListViews = mListViews;
		this.mTitleList = mTitleList;
	}
	
	@Override  
    public void destroyItem(ViewGroup container, int position, Object object)   {     
        container.removeView(mListViews.get(position));//ɾ��ҳ��  
    }  
	
	@Override  
    public CharSequence getPageTitle(int position) {  

        return mTitleList.get(position);   //ֱ��������������ɱ������ʾ�����Դ�������Կ���������û��ʹ��PagerTitleStrip����Ȼ�����ʹ�á�  
    }  


    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  //�����������ʵ����ҳ��         
         container.addView(mListViews.get(position), 0);//���ҳ��  
         return mListViews.get(position);  
    }  

    @Override  
    public int getCount() {           
        return  mListViews.size();       //����ҳ��������  
    }  
      
    @Override  
    public boolean isViewFromObject(View arg0, Object arg1) {             
        return arg0==arg1;              //�ٷ���ʾ����д  
    }  

}
