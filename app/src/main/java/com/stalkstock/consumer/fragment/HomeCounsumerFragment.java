package com.stalkstock.consumer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.stalkstock.R;
import com.stalkstock.advertiser.activities.Notification_firstActivity;
import com.stalkstock.consumer.activities.SearchActivity;
import com.stalkstock.consumer.adapter.CategoryAdapter;
import com.stalkstock.consumer.adapter.SuggestedAdapter;
import com.stalkstock.consumer.adapter.View_detailAdapter;
import com.stalkstock.consumer.model.CategoryModel;
import com.stalkstock.vender.ui.MessageActivity;
import com.stalkstock.vender.ui.SearchScreen;
import com.viewpagerindicator.CirclePageIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;

import stalkstockcommercial.ui.view.activities.FilterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCounsumerFragment extends Fragment {
    View view;
    CategoryAdapter adapter;

    RecyclerView category_recycle,suggested_recycle;
    ViewPager viewPagerDetail;
    CirclePageIndicator indicator;
    View_detailAdapter detailAdapter;
    SuggestedAdapter adapter3;
    ImageView notification,fillter;
    RelativeLayout etSearch;

    ImageView iv_tick_popuplar;
    ImageView iv_tick_most;
    ImageView iv_tick_rating;
    TextView tv_default;
    TextView tv_most;
    TextView tv_rating;
    int statusClick=1;
    int tickClick=1;

    int clickMsg=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_consumer, container, false);

        category_recycle=view.findViewById(R.id.category_recycle);
        viewPagerDetail=view.findViewById(R.id.viewPagerDetail);
        suggested_recycle=view.findViewById(R.id.suggested_recycle);
        indicator=view.findViewById(R.id.indicator);
        notification=view.findViewById(R.id.notification);
        fillter=view.findViewById(R.id.fillter);
        etSearch=view.findViewById(R.id.etSearch);
       ImageView iv_msg=view.findViewById(R.id.iv_msg);

        final Button bt_sort=view.findViewById(R.id.bt_sort);
        final Button bt_pickup=view.findViewById(R.id.bt_pickup);
        final Button tv_delivery=view.findViewById(R.id.tv_delivery);

        ArrayList<CategoryModel> arrayList=new ArrayList<>();


        arrayList.add(new CategoryModel("Vegetables",R.drawable.vegi_new));
        arrayList.add(new CategoryModel("Fruits",R.drawable.fruit_new));
        arrayList.add(new CategoryModel(getString(R.string.greate_bean_nut),R.drawable.grain_new));
        arrayList.add(new CategoryModel(getString(R.string.meat_and_poultry),R.drawable.ic_meat_new));
        arrayList.add(new CategoryModel("Seafood",R.drawable.seafood_new));
        arrayList.add(new CategoryModel("Dairy",R.drawable.dairy_new));
        arrayList.add(new CategoryModel("Baked Goods",R.drawable.baked_new));
        arrayList.add(new CategoryModel("More",R.drawable.more_new));

      /* arrayList.add(new CategoryModel("Vegetables",R.drawable.vegi_new));
        arrayList.add(new CategoryModel("Fruits",R.drawable.fruit_icon));
        arrayList.add(new CategoryModel(getString(R.string.greate_bean_nut),R.drawable.grains_icon));
        arrayList.add(new CategoryModel(getString(R.string.meat_and_poultry),R.drawable.meat_ico));
        arrayList.add(new CategoryModel("Seafood",R.drawable.fish_icon1));
        arrayList.add(new CategoryModel("Dairy",R.drawable.dairy_ico));
        arrayList.add(new CategoryModel("Baked Goods",R.drawable.ic_baked_goods));
        arrayList.add(new CategoryModel("More",R.drawable.more_ico));



       arrayList.add(new CategoryModel("Vegetables",R.drawable.veg_icon));
        arrayList.add(new CategoryModel("Fruits",R.drawable.fruit_icon));
        arrayList.add(new CategoryModel(getString(R.string.greate_bean_nut),R.drawable.grains_icon));
        arrayList.add(new CategoryModel(getString(R.string.meat_and_poultry),R.drawable.meat_ico));
        arrayList.add(new CategoryModel("Seafood",R.drawable.fish_icon1));
        arrayList.add(new CategoryModel("Dairy",R.drawable.dairy_ico));
        arrayList.add(new CategoryModel("Baked Goods",R.drawable.ic_baked_goods));
        arrayList.add(new CategoryModel("More",R.drawable.more_ico));


 */

        iv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clickMsg==0){
                    clickMsg=1;
                    Intent intent=new Intent(requireActivity(), MessageActivity.class);
                    startActivity(intent);
                }else {

                }


            }
        });
        adapter = new CategoryAdapter(getActivity(),arrayList);
        category_recycle.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        category_recycle.setAdapter(adapter) ;

        detailAdapter = new View_detailAdapter(getActivity());
        viewPagerDetail.setAdapter(detailAdapter);
        indicator.setFillColor(getResources().getColor(R.color.theme_green));
         indicator.setViewPager(viewPagerDetail);

        adapter3 = new SuggestedAdapter(getActivity());
        suggested_recycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        suggested_recycle.setAdapter(adapter3);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Notification_firstActivity.class);
                startActivity(intent);
            }
        });
        fillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SearchScreen.class);
                startActivity(intent);
            }
        });


        bt_sort.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                bt_sort.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape));
                bt_sort.setTextColor(requireActivity().getColor(R.color.white));

                bt_pickup.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape_gray));
                bt_pickup.setTextColor(requireActivity().getColor(R.color.green_ss));

                tv_delivery.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape_gray));
                tv_delivery.setTextColor(requireActivity().getColor(R.color.green_ss));

                popupSort();

            }
        });

        bt_pickup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                bt_sort.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape_gray));
                bt_sort.setTextColor(requireActivity().getColor(R.color.green_ss));

                bt_pickup.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape));
                bt_pickup.setTextColor(requireActivity().getColor(R.color.white));

                tv_delivery.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape_gray));
                tv_delivery.setTextColor(requireActivity().getColor(R.color.green_ss));



            }
        });

        tv_delivery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                bt_sort.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape_gray));
                bt_sort.setTextColor(requireActivity().getColor(R.color.green_ss));

                bt_pickup.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape_gray));
                bt_pickup.setTextColor(requireActivity().getColor(R.color.green_ss));

                tv_delivery.setBackground(requireActivity().getResources().getDrawable(R.drawable.btn_shape));
                tv_delivery.setTextColor(requireActivity().getColor(R.color.white));


            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clickMsg=0;

    }

    private void popupSort() {
        final Dialog logoutUpdatedDialog2 = new  Dialog(requireContext());
        logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutUpdatedDialog2.setContentView(R.layout.layout_sort_popup);

        logoutUpdatedDialog2.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        logoutUpdatedDialog2.setCancelable(true);
        logoutUpdatedDialog2.setCanceledOnTouchOutside(true);
        logoutUpdatedDialog2.getWindow().setGravity(Gravity.BOTTOM);

        logoutUpdatedDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        ImageView iv_cross=logoutUpdatedDialog2.findViewById(R.id.iv_cross);
        Button btn_apply=logoutUpdatedDialog2.findViewById(R.id.btn_apply);

        final RelativeLayout rl_delivery=logoutUpdatedDialog2.findViewById(R.id.rl_delivery);
        final TextView tv_delivery=logoutUpdatedDialog2.findViewById(R.id.tv_delivery);
        final ImageView ic_delivery=logoutUpdatedDialog2.findViewById(R.id.ic_delivery);
        iv_tick_popuplar=logoutUpdatedDialog2.findViewById(R.id.iv_tick_popuplar);
        iv_tick_most=logoutUpdatedDialog2.findViewById(R.id.iv_tick_most);
        iv_tick_rating=logoutUpdatedDialog2.findViewById(R.id.iv_tick_rating);

        final RelativeLayout rl_pickups=logoutUpdatedDialog2.findViewById(R.id.rl_pickups);
        final TextView tv_pickup_o=logoutUpdatedDialog2.findViewById(R.id.tv_pickup_o);
        final ImageView ic_pickup=logoutUpdatedDialog2.findViewById(R.id.ic_pickup);

        final RelativeLayout rl_dining=logoutUpdatedDialog2.findViewById(R.id.rl_dining);
        final TextView tv_dining=logoutUpdatedDialog2.findViewById(R.id.tv_dining);
        final ImageView ic_dining=logoutUpdatedDialog2.findViewById(R.id.ic_dining);

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUpdatedDialog2.dismiss();

            }
        });

        tv_default=logoutUpdatedDialog2.findViewById(R.id.tv_default);
        tv_most=logoutUpdatedDialog2.findViewById(R.id.tv_most);
        tv_rating=logoutUpdatedDialog2.findViewById(R.id.tv_rating);



        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUpdatedDialog2.dismiss();
            }
        });

        rl_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusClick=1;
                iconAndTextColorChange(statusClick,tv_delivery,tv_pickup_o,tv_dining,rl_delivery,rl_pickups,rl_dining,ic_delivery,ic_pickup,ic_dining);

            }
        });

        rl_pickups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                statusClick=2;
                iconAndTextColorChange(statusClick,tv_delivery,tv_pickup_o,tv_dining,rl_delivery,rl_pickups,rl_dining,ic_delivery,ic_pickup,ic_dining);

            }
        });

        rl_dining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusClick=3;
                iconAndTextColorChange(statusClick,tv_delivery,tv_pickup_o,tv_dining,rl_delivery,rl_pickups,rl_dining,ic_delivery,ic_pickup,ic_dining);

             }
        });


        iconAndTextColorChange(statusClick,tv_delivery,tv_pickup_o,tv_dining,rl_delivery,rl_pickups,rl_dining,ic_delivery,ic_pickup,ic_dining);

        tv_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickClick=1;

            tickVisible(  tickClick);
            }
        });
         tv_most.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickClick=2;
                tickVisible(tickClick);
            }
        });
        tv_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickClick=3;
                tickVisible(tickClick);
            }
        });
        tickVisible(tickClick);
        logoutUpdatedDialog2.show();

    }

    public  void tickVisible(int tickClick){

        if (tickClick==1){
            iv_tick_popuplar.setVisibility(View.VISIBLE);
            iv_tick_most.setVisibility(View.GONE);
            iv_tick_rating.setVisibility(View.GONE);
        }else if (tickClick==2){
            iv_tick_popuplar.setVisibility(View.GONE);
            iv_tick_most.setVisibility(View.VISIBLE);
            iv_tick_rating.setVisibility(View.GONE);
        }else if (tickClick==3){
            iv_tick_popuplar.setVisibility(View.GONE);
            iv_tick_most.setVisibility(View.GONE);
            iv_tick_rating.setVisibility(View.VISIBLE);
        }
    }
    public  void iconAndTextColorChange(int status,TextView t1,TextView t2,TextView t3 ,RelativeLayout rl1,RelativeLayout rl2,RelativeLayout rl3,ImageView iv1,ImageView iv2,ImageView iv3){

        if (status==1){
            rl1.setBackground(getResources().getDrawable(R.drawable.strokegreen));
            t1.setTextColor(getResources().getColor(R.color.green_colour));
          //  iv1.setColorFilter(getResources().getColor(R.color.green_colour), PorterDuff.Mode.SRC_OVER);
            iv1.setColorFilter(ContextCompat.getColor(getContext(), R.color.green_colour));

            rl2.setBackground(getResources().getDrawable(R.drawable.strokegray_sort));
            t2.setTextColor(getResources().getColor(R.color.sort_popup_gray_color));
            //iv2.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv2.setColorFilter(ContextCompat.getColor(getContext(), R.color.sort_popup_gray_color));

            rl3.setBackground(getResources().getDrawable(R.drawable.strokegray_sort));
            t3.setTextColor(getResources().getColor(R.color.sort_popup_gray_color));
            //iv3.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv3.setColorFilter(ContextCompat.getColor(getContext(), R.color.sort_popup_gray_color));
//            iv_tick_popuplar.setVisibility(View.VISIBLE);
//            iv_tick_most.setVisibility(View.GONE);
//            iv_tick_rating.setVisibility(View.GONE);


        }else  if (status==2){
            rl1.setBackground(getResources().getDrawable(R.drawable.strokegray_sort));
            t1.setTextColor(getResources().getColor(R.color.sort_popup_gray_color));
            //iv1.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv1.setColorFilter(ContextCompat.getColor(getContext(), R.color.sort_popup_gray_color));

            rl2.setBackground(getResources().getDrawable(R.drawable.strokegreen));
            t2.setTextColor(getResources().getColor(R.color.green_colour));
            //iv2.setColorFilter(getResources().getColor(R.color.green_colour), PorterDuff.Mode.SRC_OVER);
            iv2.setColorFilter(ContextCompat.getColor(getContext(), R.color.green_colour));


            rl3.setBackground(getResources().getDrawable(R.drawable.strokegray_sort));
            t3.setTextColor(getResources().getColor(R.color.sort_popup_gray_color));
            //iv3.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv3.setColorFilter(ContextCompat.getColor(getContext(), R.color.sort_popup_gray_color));




        }else  if (status==3){

            rl1.setBackground(getResources().getDrawable(R.drawable.strokegray_sort));
            t1.setTextColor(getResources().getColor(R.color.sort_popup_gray_color));
           // iv1.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv1.setColorFilter(ContextCompat.getColor(getContext(), R.color.sort_popup_gray_color));


            rl2.setBackground(getResources().getDrawable(R.drawable.strokegray_sort));
            t2.setTextColor(getResources().getColor(R.color.sort_popup_gray_color));
         //   iv2.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv2.setColorFilter(ContextCompat.getColor(getContext(), R.color.sort_popup_gray_color));

            rl3.setBackground(getResources().getDrawable(R.drawable.strokegreen));
            t3.setTextColor(getResources().getColor(R.color.green_colour));
            //iv3.setColorFilter(getResources().getColor(R.color.green_colour), PorterDuff.Mode.SRC_OVER);
            iv3.setColorFilter(ContextCompat.getColor(getContext(), R.color.green_colour));


        }


    }
}
