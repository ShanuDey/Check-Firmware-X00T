package ml.shanudey.root;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static String readFile(String file) {
        return readFile(file, true);
    }

    public static String readFile(String file, boolean root) {
        return readFile(file, root ? RootUtils.getSU() : null);
    }

    public static String readFile(String file, RootUtils.SU su) {
        if (su != null) {
            return new RootFile(file, su).readFile();
        }

        BufferedReader buf = null;
        try {
            buf = new BufferedReader(new FileReader(file));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = buf.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            return stringBuilder.toString().trim();
        } catch (IOException ignored) {
        } finally {
            try {
                if (buf != null) buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean existFile(String file) {
        return existFile(file, true);
    }

    public static boolean existFile(String file, boolean root) {
        return existFile(file, root ? RootUtils.getSU() : null);
    }

    public static boolean existFile(String file, RootUtils.SU su) {
        return su == null ? new File(file).exists() : new RootFile(file, su).exists();
    }
}
