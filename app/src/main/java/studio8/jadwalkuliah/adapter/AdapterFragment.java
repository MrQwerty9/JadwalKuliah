package studio8.jadwalkuliah.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import studio8.jadwalkuliah.R;

public class AdapterFragment extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private String[] titles ={"Aaa","Bbb"};
//    private SlidingTabLayout mSlidingTabLayout;
    private TabLayout mtabLayout;
    int[] icon = new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher_round};
    private int heightIcon;

    public AdapterFragment(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    public CharSequence getPageTitle(int position){
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

}