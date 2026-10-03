package de.teamlapen.werewolves.util;

import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphanumericComparator implements Comparator<ResourceLocation> {

    private final Pattern p = Pattern.compile("(\\D*)(\\d*)");

    @Override
    public int compare(ResourceLocation s, ResourceLocation t1) {
        String first = s.toString();
        String second = t1.toString();
        Matcher m1 = p.matcher(first);
        Matcher m2 = p.matcher(second);

        // The loop is required because the string can contain multiple numeric parts,
        // e.g., beast2part10
        while (m1.find() && m2.find()) {
            // Non-numeric part (could be empty)
            int nonNumericCompare = m1.group(1).compareTo(m2.group(1));
            if (nonNumericCompare != 0) {
                return nonNumericCompare;
            }

            // Numeric part (could be empty)
            if (!m1.group(2).isEmpty() && !m2.group(2).isEmpty()) {
                int numericCompare = Integer.compare(Integer.parseInt(m1.group(2)), Integer.parseInt(m2.group(2)));
                if (numericCompare != 0) {
                    return numericCompare;
                }
            }
        }

        // Handle if one string is a prefix of the other.
        // E.g., beast2 and beast2part10
        if (m1.hitEnd() && m2.hitEnd()) {
            return 0;
        } else {
            return m1.hitEnd() ? -1 : 1;
        }

    }
}
