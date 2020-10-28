package com.stalkstock.consumer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.stalkstock.R;


public class View_detailAdapter extends PagerAdapter
{
    LayoutInflater layoutInflater;
    Context mcontext;
    int current_position;
   // List<Detail_response.PlacesGallery>placesGalleryList;
    int[] images_array;
    //ArrayList<Tutorial_Model> slide_arraylist;

    public View_detailAdapter(Context context
    ) {
        this.mcontext = context;
       // this.placesGalleryList = placesGalleryList;
   //     this.slide_arraylist = slide_arraylist;
      //  this.featured_list = featured_list;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        current_position = position;

        layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_viewpager, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.img);
      //  LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);

//        try {
//            Glide.with(mcontext)
//                    .load(Constant.Img_url +placesGalleryList.get(position).getImage())
//                    //.placeholder(R.drawable.user_empty)
//                    .into(img);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        container.addView(view);
        return view;
    }
}
