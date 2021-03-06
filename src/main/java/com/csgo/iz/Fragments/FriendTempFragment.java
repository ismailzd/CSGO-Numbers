package com.csgo.iz.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csgo.iz.MainActivity;
import com.csgo.iz.R;
import com.csgo.iz.Adapters.DisableSwipeViewPager;
import com.csgo.iz.Adapters.ViewPagerAdapter;
import com.csgo.iz.compare.ViewPagerCompareAdapter;
import com.csgo.iz.modal.Model;
import com.csgo.iz.modal.bean.Achievement;
import com.csgo.iz.modal.bean.Map;
import com.csgo.iz.modal.bean.Profile;
import com.csgo.iz.modal.bean.Summary;
import com.csgo.iz.modal.bean.Weapon;
import com.csgo.iz.modal.http.ModelData;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FriendTempFragment extends Fragment {

	private ListView cipherListView;
	private static String TAG_MAIN_STEAMID = "com.csgo.iz.mainfragment.steamid";
	private TabLayout tabLayout;
	private DisableSwipeViewPager pager;
	private ViewPagerAdapter adapterMain;
	private Model model;

	public static FriendTempFragment InstanceOf(String steamID) {
		FriendTempFragment fragment = new FriendTempFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TAG_MAIN_STEAMID, steamID);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Context context = getActivity().getApplicationContext();
		View rootView = inflater.inflate(R.layout.main_fragment, container, false);
		pager = (DisableSwipeViewPager) rootView.findViewById(R.id.viewpager);
		tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
		pager.setPagingEnabled(true);
		String steamID = getArguments().getString(TAG_MAIN_STEAMID);
		//76561197963801763
		Log.v("Friend Detail Tag", "steamid: " + steamID);
		Model.setToNull();
		ModelData.setToNull();
		model = Model.getInstance(context, steamID);
		allocateAdapter();
		tabLayout.post(new Runnable() {
			@Override
			public void run() {
				tabLayout.setupWithViewPager(pager);
				generateTabs();
			}
		});
		return rootView;
	}

	private void allocateAdapter() {
		HashMap<String, ArrayList<Weapon>> listOfWeapons = model.getWeaponList();
		Log.v("Weapon Tab Stat", "Main Fragement: " + listOfWeapons.size());
		HashMap<String, ArrayList<Map>> listOfMaps = model.getMapList();
		ArrayList<Summary> summary = model.getSummary();
		HashMap<Integer, List<Achievement>> listOfAchievements = model.getAchievement();
		;
		adapterMain = new ViewPagerAdapter(this.getChildFragmentManager(), this.getActivity().getApplicationContext(),
				summary, listOfMaps, listOfAchievements, listOfWeapons);
		pager.setAdapter(adapterMain);
		pager.setPagingEnabled(true);
		pager.setOffscreenPageLimit(3);
	}

	private void generateTabs() {
		for (int i = 0; i < tabLayout.getTabCount(); i++) {
			TabLayout.Tab tab = tabLayout.getTabAt(i);
			tab.setCustomView(adapterMain.getTabView(i));
		}
	}
}
