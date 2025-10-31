package main;

import java.io.*;
import java.nio.file.*;
import java.util.jar.*;
import java.util.Enumeration;

public class GameLauncher {
    public static void main(String[] args) {
        try {
            File jarFile = new File(GameLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File parentDir = jarFile.getParentFile();

            extractJarContents(jarFile, parentDir);
            ProcessBuilder pb = new ProcessBuilder("java", "main.KiviGame");
            pb.directory(parentDir); 
            pb.inheritIO(); 
            pb.start().waitFor();

        } catch (Exception e) {e.printStackTrace();}
        new KiviGame();
    }
    private static void extractJarContents(File jarFile, File outputDir) throws IOException {
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                File outputFile = new File(outputDir, entry.getName());
                if (entry.isDirectory()) {
                    outputFile.mkdirs();
                } else {
                    outputFile.getParentFile().mkdirs();
                    try (InputStream in = jar.getInputStream(entry);
                         FileOutputStream out = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }
}
