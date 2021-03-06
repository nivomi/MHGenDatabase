package com.ghstudios.android.ui.detail;

import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.ghstudios.android.data.classes.Weapon;
import com.ghstudios.android.loader.WeaponLoader;
import com.ghstudios.android.mhgendatabase.R;

public class WeaponDetailFragment extends Fragment {
	protected static final String ARG_WEAPON_ID = "WEAPON_ID";
	
	protected Weapon mWeapon;

	protected TextView mWeaponLabelTextView, mWeaponTypeTextView,
			mWeaponAttackTextView,mWeaponDescription,
			mWeaponRarityTextView, mWeaponSlotTextView,
			mWeaponAffinityTextView, mWeaponDefenseTextView,mWeaponDefenseTextTextView,
			mWeaponCreationTextView, mWeaponUpgradeTextView;

	public static WeaponDetailFragment newInstance(long weaponId) {
		Bundle args = new Bundle();
		args.putLong(ARG_WEAPON_ID, weaponId);
		WeaponDetailFragment f = new WeaponDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);

		// Check for a Weapon ID as an argument, and find the weapon
		Bundle args = getArguments();
		if (args != null) {
			long weaponId = args.getLong(ARG_WEAPON_ID, -1);
			if (weaponId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.weapon_detail_fragment, args, new WeaponLoaderCallbacks());
			}
		}
	}
	
	protected void updateUI() throws IOException {
		//mWeaponLabelTextView.setText(mWeapon.getName());
		mWeaponTypeTextView.setText(mWeapon.getName());
		mWeaponAttackTextView.setText("" + mWeapon.getAttack());
		mWeaponRarityTextView.setText("" + mWeapon.getRarity());
		mWeaponSlotTextView.setText("" + mWeapon.getSlotString());
		mWeaponDescription.setText(mWeapon.getDescription());

		if(mWeapon.getDefense()==0)
		{
			mWeaponDefenseTextTextView.setVisibility(View.GONE);
			mWeaponDefenseTextView.setVisibility(View.GONE);
		}
		else
			mWeaponDefenseTextView.setText("" + mWeapon.getDefense());

		mWeaponAffinityTextView.setText(mWeapon.getAffinity()+"%");

		
		String createCost = "" + mWeapon.getCreationCost() + "z";
		String upgradeCost = "" + mWeapon.getUpgradeCost() + "z";
		
		if (createCost.equals("0z")) {
			createCost = "-";
		}
		if (upgradeCost.equals("0z")) {
			upgradeCost = "-";
		}
		
		mWeaponCreationTextView.setText(createCost);
		mWeaponUpgradeTextView.setText(upgradeCost);
		}

	public class WeaponLoaderCallbacks implements LoaderCallbacks<Weapon> {

		@Override
		public Loader<Weapon> onCreateLoader(int id, Bundle args) {
			return new WeaponLoader(getActivity(), args.getLong(ARG_WEAPON_ID));
		}

		@Override
		public void onLoadFinished(Loader<Weapon> loader, Weapon run) {
            mWeapon = run;

			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onLoaderReset(Loader<Weapon> loader) {
			// Do nothing
		}
	}
}
