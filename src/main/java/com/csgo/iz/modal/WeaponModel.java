package com.csgo.iz.modal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.csgo.iz.R;
import com.csgo.iz.modal.bean.Weapon;
import com.csgo.iz.modal.http.ModelData;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

public class WeaponModel extends ModelAbstract {
    private HashMap<String, ArrayList<Weapon>> weaponList;
    private Context context;
    private Hashtable<String, Integer> tableStats;

    public WeaponModel(String userID, Context context) {
        super(userID, context);
        this.context = context;
    }

    public WeaponModel(Context context, Hashtable<String, Integer> tableStats) {
        super(context);
        this.context = context;
        this.tableStats = tableStats;
        generateWeaponList();
    }

    public void generateWeaponList(ModelData data) {
        weaponList = new HashMap<String, ArrayList<Weapon>>();
        ArrayList<Weapon> listWeapon = getWeaponList();
        String[] KEYS = {"PISTOLS", "RIFLES", "SMG", "HEAVY"};
        for (int i = 0; i < KEYS.length; i++) {
            // Create ArrayListTemp
            ArrayList<Weapon> tempArr = new ArrayList<Weapon>();
            for (int j = 0; j < listWeapon.size(); j++) {
                String weaponType = listWeapon.get(j).getWeaponType();
                if (weaponType.equals(KEYS[i])) {
                    tempArr.add(listWeapon.get(j));
                }
            }
            weaponList.put(KEYS[i], tempArr);
        }
    }

    private void generateWeaponList() {
        if (tableStats != null) {
            weaponList = new HashMap<String, ArrayList<Weapon>>();
            ArrayList<Weapon> listWeapon = getWeaponList();
            String[] KEYS = {"PISTOLS", "RIFLES", "SMG", "HEAVY"};
            for (int i = 0; i < KEYS.length; i++) {
                // Create ArrayListTemp
                ArrayList<Weapon> tempArr = new ArrayList<Weapon>();
                for (int j = 0; j < listWeapon.size(); j++) {
                    String weaponType = listWeapon.get(j).getWeaponType();
                    if (weaponType.equals(KEYS[i])) {
                        tempArr.add(listWeapon.get(j));
                    }
                }
                weaponList.put(KEYS[i], tempArr);
            }
        }
    }

    public HashMap<String, ArrayList<Weapon>> getWeaponHash() {
        return weaponList;
    }

    private ArrayList<Weapon> getWeaponList() {
        if (tableStats != null) {
            ArrayList<Weapon> list = new ArrayList<Weapon>();
            String[] arr = context.getResources().getStringArray(R.array.weapon_names);
            TypedArray resource_array = context.getResources().obtainTypedArray(R.array.weapon_images);
            Weapon weapon;
            int counter = 0;
            for (String item : arr) {
                item = item.split(",")[0];
                item = (item.equals("dual_berettas")) ? "elite" : item;
                item = (item.equals("m4a4")) ? "m4a1" : item;
                item = (item.equals("usp_silencer")) ? "hkp2000" : item;
                // int resource_id = context.getResources().getIdentifier(item,
                // "drawable", "com.csgo.iz");
                int resource_id = resource_array.getResourceId(counter, -1);
                String weaponName = item.toUpperCase();
                String weaponType = arr[counter].split(",")[1].replaceAll("\\s+", "");
                int weaponKills = getHashValue("total_kills_" + item);
                int weaponHit = getHashValue("total_hits_" + item);
                int weaponShots = getHashValue("total_shots_" + item);
                int weaponAccuracy = (weaponHit == 0 || weaponShots == 0) ? 0 : (int) 100 * weaponHit / weaponShots;
                int missedShots = weaponShots - weaponHit;
                weapon = new Weapon(resource_id, weaponType, weaponName, weaponKills, weaponHit, weaponAccuracy,
                        missedShots, weaponShots);
                Log.v("getWeaponList Special", weapon.toString());
                list.add(weapon);
                counter++;
            }
            return list;
        }
        return null;
    }

    private int getHashValue(String key) {
        Object item = tableStats.get(key);
        if (item != null) {
            return (int) item;
        } else {
            return 0;
        }
    }
//	public ArrayList<ArrayList<Weapon>> getCompareWeaponList() {
//		return compareData.getWeaponListArray();
//	}


}
