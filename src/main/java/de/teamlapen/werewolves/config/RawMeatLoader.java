package de.teamlapen.werewolves.config;

import de.teamlapen.vampirism.config.BloodValueLoader;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

//TODO eventually kick raw meat out waiting for forge 1.14
/**
 * Handle loading and saving of raw meat items.
 */
public class RawMeatLoader {
    private static final String TAG = "RawMeatLoader";

    public static void init(File configDir) {
        File rawMeatConfigFile = new File(configDir, REFERENCE.MODID + "_raw_meat_values");

        try {
            Map<ResourceLocation, Float> defaultValues = loadRawMeatValuesFromReader(new InputStreamReader(BloodValueLoader.class.getResourceAsStream("/raw_meat_values/default_raw_meat_values.txt")), "default_raw_meat_values.txt");
            Helper.addRawMeat(defaultValues);
        } catch (IOException e) {
            WerewolvesMod.log.bigWarning(TAG, "Could not read default raw meat values, this should not happen and destroys the mod experience");
            WerewolvesMod.log.e(TAG, e, "Exception");
        }

        if (rawMeatConfigFile.exists()) {
            try {
                Map<ResourceLocation, Float> override = loadRawMeatValuesFromReader(new FileReader(rawMeatConfigFile), rawMeatConfigFile.getName());
                Helper.overrideRawMeat(override);
                WerewolvesMod.log.i(TAG, "Successfully loaded additional raw meat value file");
            } catch (IOException e) {
                WerewolvesMod.log.e(TAG, "Could not read raw meat values from config file %s", rawMeatConfigFile.getName());
            }
        }
    }

    private static Map<ResourceLocation, Float> loadRawMeatValuesFromReader(Reader r, String file) throws IOException {
        Map<ResourceLocation, Float> rawmeatValues = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#"))
                    continue;
                if (StringUtils.isNullOrEmpty(line))
                    continue;
                String[] p = line.split("=");
                if (p.length != 2) {
                    WerewolvesMod.log.w("ReadRawMeat", "Line %s in %s is not formatted properly", line, file);
                    continue;
                }
                float val;
                try {
                    val = Float.parseFloat(p[1]);
                } catch (NumberFormatException e) {
                    WerewolvesMod.log.w("ReadRawMeat", "Line %s in %s is not formatted properly", line, file);
                    continue;
                }
                rawmeatValues.put(new ResourceLocation(p[0]), val);
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
