package de.teamlapen.vampirewerewolf.config;

import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.util.Helper;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import de.teamlapen.vampirism.config.BloodValueLoader;

import net.minecraft.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handle loading and saving of raw meat items.
 */
public class RawMeatLoader {
    private static final String TAG = "RawMeatLoader";

    public static void init(File configDir) {
        File rawMeatConfigFile = new File(configDir, REFERENCE.MODID + "_raw_meat");

        try {
            Map<String, Float> defaultValues = loadRawMeatValuesFromReader(new InputStreamReader(BloodValueLoader.class.getResourceAsStream("/raw_meat_values/default_raw_meat_values.txt")), "default_raw_meat_values.txt");
            Helper.addRawMeat(defaultValues);
        } catch (IOException e) {
            VampireWerewolfMod.log.bigWarning(TAG, "Could not read default raw meat values, this should not happen and destroys the mod experience");
            VampireWerewolfMod.log.e(TAG, e, "Exception");
        }

        if (rawMeatConfigFile.exists()) {
            try {
                Map<String, Float> override = loadRawMeatValuesFromReader(new FileReader(rawMeatConfigFile), rawMeatConfigFile.getName());
                Helper.overrideRawMeat(override);
                VampireWerewolfMod.log.i(TAG, "Successfully loaded additional raw meat value file");
            } catch (IOException e) {
                VampireWerewolfMod.log.e(TAG, "Could not read raw meat values from config file %s", rawMeatConfigFile.getName());
            }
        }
    }

    private static Map<String, Float> loadRawMeatValuesFromReader(Reader r, String file) throws IOException {
        Map<String, Float> rawmeatValues = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue;
                if (StringUtils.isNullOrEmpty(line)) continue;
                String[] p = line.split("=");
                if (p.length != 2) {
                    VampireWerewolfMod.log.w("ReadRawMeat", "Line %s in %s is not formatted properly", line, file);
                    continue;
                }
                float val;
                try {
                    val = Float.parseFloat(p[1]);
                } catch (NumberFormatException e) {
                    VampireWerewolfMod.log.w("ReadRawMeat", "Line %s in %s is not formatted properly", line, file);
                    continue;
                }
                rawmeatValues.put(p[0], val);
            }
        } finally {
            if (br != null) {
                br.close();
            }
            r.close();
        }
        return rawmeatValues;
    }
}
