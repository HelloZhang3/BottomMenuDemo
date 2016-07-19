package com.rockmobile.bottommenudemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rockmobile.bottommenudemo.fragment.Fragment_Dicover;
import com.rockmobile.bottommenudemo.fragment.Fragment_Friends;
import com.rockmobile.bottommenudemo.fragment.Fragment_Msg;
import com.rockmobile.bottommenudemo.fragment.Fragment_Profile;

/**
 * 主Activity
 *
 * @author zhangyadong
 * @time 2016/7/18  14:06
 * email:  zhangyadong@rockmobile.com.cn
 */
public class MainActivity extends AppCompatActivity implements
        Fragment_Msg.OnFragmentInteractionListener,
        Fragment_Profile.OnFragmentInteractionListener,
        Fragment_Dicover.OnFragmentInteractionListener,
        Fragment_Friends.OnFragmentInteractionListener {

    //控制显示哪个选项卡
    public static final String TAB_CODE = "tab_code";

    private TextView txt_title;
    private Fragment[] fragments;
    public Fragment_Msg homefragment;
    private Fragment_Friends contactlistfragment;
    private Fragment_Dicover findfragment;
    private Fragment_Profile profilefragment;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private int index;
    private int currentTabIndex;// 当前fragment的index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initTabView();
    }


    private void initTabView() {
        txt_title = (TextView) findViewById(R.id.txt_tiele);
        homefragment = new Fragment_Msg();
        contactlistfragment = new Fragment_Friends();
        findfragment = new Fragment_Dicover();
        profilefragment = new Fragment_Profile();
        fragments = new Fragment[]{homefragment, contactlistfragment, findfragment, profilefragment};
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_find);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setTextColor(0xFF45C01A);
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homefragment)
                .add(R.id.fragment_container, contactlistfragment)
                .add(R.id.fragment_container, profilefragment)
                .add(R.id.fragment_container, findfragment)
                .hide(contactlistfragment).hide(profilefragment)
                .hide(findfragment).show(homefragment).commit();
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_weixin:
                index = 0;
                txt_title.setText("微信");
                homefragment.showCheckToast("你选择了消息");
                break;
            case R.id.re_contact_list:
                index = 1;
                txt_title.setText("通讯录");
                break;
            case R.id.re_find:
                index = 2;
                txt_title.setText("发现");
                break;
            case R.id.re_profile:
                index = 3;
                txt_title.setText("我");
                break;
        }
        changeFragment(index);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
        if (uri.toString().equals("hello world!")) {
            switchTab(3);
        }
    }

    /**
     * 控制显示Tab
     *
     * @param index
     */
    void switchTab(int index) {
        switch (index) {
            case 0:
                index = 0;
                txt_title.setText("微信");
                homefragment.showCheckToast("你选择了消息");
                break;
            case 1:
                index = 1;
                txt_title.setText("通讯录");
                break;
            case 2:
                index = 2;
                txt_title.setText("发现");
                break;
            case 3:
                index = 3;
                txt_title.setText("我");
                break;
        }
        changeFragment(index);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            String tab_code = intent.getStringExtra(TAB_CODE);
            if (tab_code != null && Integer.valueOf(tab_code) == 2) {
                changeFragment(2);
                txt_title.setText("发现");
            }
        }
    }

    /**
     * 根据index切换Fragment
     *
     * @param index
     */
    private void changeFragment(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }
}
