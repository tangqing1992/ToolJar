package www.tq.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BottomRecyAdapter  extends RecyclerView.Adapter<BottomRecyAdapter.HolderView> {

    private Context context =null;
    private int [] resblue = new int[]{R.drawable.icon_weatherblue,R.drawable.icon_mapblue,R.drawable.icon_meblue};
    private int [] resgray = new int[]{R.drawable.icon_weathergray,R.drawable.icon_mapgray,R.drawable.icon_megray};
    private int [] resgtxt = new int[]{R.string.bottom1,R.string.bottom2,R.string.bottom3};
    private int position = 0;
    private OnClickItem onClickItem = null;

    BottomRecyAdapter(Context context,OnClickItem onClickItem) {
        this.context = context;
        this.onClickItem = onClickItem;
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottomitem, parent, false);
        //将view传递给我们自定义的ViewHolder
        HolderView holder = new HolderView(view);
        //返回这个MyHolder实体
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.text_name.setText(context.getResources().getString(resgtxt[position]));
        if (this.position==position){
            holder.image_icon.setImageResource(resblue[position]);
            holder.text_name.setTextColor(context.getResources().getColor(R.color.txt_blue));
        }else {
            holder.image_icon.setImageResource(resgray[position]);
            holder.text_name.setTextColor(context.getResources().getColor(R.color.txt_black));
        }
        holder.layout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.callback(position);
            }
        });
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return resgtxt.length;
    }

    /**
     * 自定义的ViewHolder
     */
    class HolderView extends RecyclerView.ViewHolder {

        TextView text_name;
        ImageView image_icon;
        LinearLayout layout_parent;

        public HolderView(View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            image_icon = itemView.findViewById(R.id.image_icon);
            layout_parent = itemView.findViewById(R.id.layout_parent);

        }
    }


    public  interface  OnClickItem{
        void  callback(int position);
    }
}