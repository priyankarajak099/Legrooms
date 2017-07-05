package com.legrooms;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.legrooms.Fragments.HomeFragment;
import com.legrooms.Fragments.FrangmetsName;
import com.legrooms.Utility.SPUser;
import com.legrooms.Views.TextViewPlus;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FrangmetsName currentFragment;
    private HomeFragment fragment;
    private android.support.v4.app.FragmentTransaction transaction;
    private RadioGroup rgSelect;
    private String view_type="map";
    private TextViewPlus tvFooterSearch,tvMessage,tvBooking,tvProfile;
    private ImageView imFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();
        initListener();
        changeFragment(FrangmetsName.HOME, null);
    }

    private void initListener() {
        rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_list:
                        view_type="list";
                        changeFragment(FrangmetsName.HOME, view_type);

                       // lvClubList.setVisibility(View.VISIBLE);
                     //   mapFragment.getView().setVisibility(View.GONE);
                        break;
                    case R.id.rb_map:
                        view_type="map";
                        changeFragment(FrangmetsName.HOME, view_type);

                      //  lvClubList.setVisibility(View.GONE);
                      //  mapFragment.getView().setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        tvFooterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFooterSearch.setSelected(true);


            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMessage.setSelected(true);
                startActivity(new Intent(HomeActivity.this, MessageActivity.class));
                finish();
            }
        });
        tvBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBooking.setSelected(true);
                startActivity(new Intent(HomeActivity.this, BookingActivity.class));
                finish();
            }
        });
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvProfile.setSelected(true);
                startActivity(new Intent(HomeActivity.this, ProfileScreen.class));
                finish();
            }
        });
        imFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FilterActivity.class));
            }
        });
    }

    private void initComponents() {
        fragmentManager = getSupportFragmentManager();
        rgSelect = (RadioGroup) findViewById(R.id.rg_category);
        tvFooterSearch = (TextViewPlus) findViewById(R.id.tv_footer_search);
        tvMessage = (TextViewPlus) findViewById(R.id.tv_message);
        tvBooking = (TextViewPlus) findViewById(R.id.tv_booking);
        tvProfile = (TextViewPlus) findViewById(R.id.tv_profile);
        imFilter=(ImageView)findViewById(R.id.im_filter);
        tvFooterSearch.setSelected(true);


    }

    public void changeFragment(FrangmetsName vFragment, Object object) {

        switch (vFragment) {
            case HOME:
                this.currentFragment = FrangmetsName.HOME;
                fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("view_type", view_type);
                fragment.setArguments(bundle);
               // getSupportActionBar().setTitle("HOME");

                break;

            default:
                this.currentFragment = FrangmetsName.HOME;
                fragment = new HomeFragment();
                break;
        }

        this.transaction = this.fragmentManager.beginTransaction();
      //  this.transaction.addToBackStack(this.currentFragment.toString());
        this.transaction.replace(R.id.fl_home_frame, fragment, currentFragment.toString());
        this.transaction.commit();

    }

}
