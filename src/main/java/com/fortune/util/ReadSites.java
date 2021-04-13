package com.fortune.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadSites {
    List<Vector2D> sites;
    public ReadSites(){
        Pattern p = Pattern.compile("\\((.*?)\\)");
        Pattern p2 = Pattern.compile("-?[0-9](\\.[0-9]+)?");
        sites = new ArrayList<>();

        String fileName = "sites.txt";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());

        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()){
                Matcher m = p.matcher(sc.nextLine());
                while(m.find()) {
                    Matcher m2 = p2.matcher(m.group());
                    m2.find();
                    double x = Double.parseDouble(m2.group());
                    m2.find();
                    double y = Double.parseDouble(m2.group());

                    sites.add(new Vector2D(x, y));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<Vector2D> getSites() {
        return sites;
    }
}
