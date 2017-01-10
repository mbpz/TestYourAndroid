package hibernate.v2.testyourandroid.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hibernate.v2.testyourandroid.R;
import hibernate.v2.testyourandroid.model.InfoItem;
import hibernate.v2.testyourandroid.ui.adapter.InfoItemAdapter;

/**
 * Created by himphen on 21/5/16.
 */
public class InfoGSMFragment extends BaseFragment {

	@BindView(R.id.rvlist)
	RecyclerView recyclerView;

	private TelephonyManager telephonyManager;
	private String[] simStateArray;

	public InfoGSMFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_info_listview, container, false);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView.setLayoutManager(
				new LinearLayoutManager(mContext,
						LinearLayoutManager.VERTICAL, false)
		);
		init();
	}

	private void init() {
		telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

		List<InfoItem> list = new ArrayList<>();
		String[] stringArray = getResources().getStringArray(R.array.info_gsm_string_array);
		simStateArray = getResources().getStringArray(R.array.info_sim_status_string_array);

		for (int i = 0; i < stringArray.length; i++) {
			list.add(new InfoItem(stringArray[i], getData(i)));
		}

		InfoItemAdapter adapter = new InfoItemAdapter(list);
		recyclerView.setAdapter(adapter);
	}

	@SuppressLint("HardwareIds")
	private String getData(int j) {
		try {
			switch (j) {
				case 0:
					return telephonyManager.getSimCountryIso();
				case 1:
					return telephonyManager.getSimOperator();
				case 2:
					return telephonyManager.getSimOperatorName();
				case 3:
					return simStateArray[telephonyManager.getSimState()];
				case 4:
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						if (telephonyManager.getPhoneCount() > 1) {
							return "Sim Card 1: " + telephonyManager.getDeviceId(0) + "\nSim Card 2: " + telephonyManager.getDeviceId(1);
						} else {
							return telephonyManager.getDeviceId(0);
						}
					} else {
						return telephonyManager.getDeviceId();
					}
				case 5:
					return telephonyManager.getDeviceSoftwareVersion();
				case 6:
					return telephonyManager.getLine1Number();
				case 7:
					return telephonyManager.getNetworkCountryIso();
				case 8:
					return telephonyManager.getNetworkOperator();
				case 9:
					return telephonyManager.getNetworkOperatorName();
				case 10:
					return String.valueOf(telephonyManager.isNetworkRoaming());
				case 11:
					return telephonyManager.getSimSerialNumber();
				case 12:
					return telephonyManager.getSubscriberId();
				default:
					return "N/A";
			}
		} catch (Exception e) {
			return "N/A";
		}
	}
}
