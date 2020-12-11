package www.tq.weather.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;

/*自定义可随机设置viewpger高度的控件*/
public class CustomViewPager extends ViewPager {
    private Map<Integer,Integer> map=new HashMap<>(2);
    private int currentPage;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height=0;
        if(map.size()>0 && map.containsKey(currentPage)){
            height=map.get(currentPage);
        }
        //得到ViewPager的MeasureSpec，使用固定值和MeasureSpec.EXACTLY，
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 在切换tab的时候，重置ViewPager的高度
     * @param current
     */
    public void resetHeight(int current){
        this.currentPage=current;
        MarginLayoutParams params= (MarginLayoutParams) getLayoutParams();
        if(map.size()>0 && map.containsKey(currentPage)){
            if(params==null){
                params=new MarginLayoutParams(LayoutParams.MATCH_PARENT,map.get(current));
            }else {
                params.height=map.get(current);
            }
            setLayoutParams(params);
        }
    }

    /**
     * 获取、存储每一个tab的高度，在需要的时候显示存储的高度
     * @param current  tab的position
     * @param height   当前tab的高度
     */
    public void addHeight(int current,int height){
        map.put(current,height);
    }

    public int getCurrentHeight(int currentPage) {
        return map.get(currentPage);
    }
}
