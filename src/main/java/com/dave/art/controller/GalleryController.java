package com.dave.art.controller;

import com.dave.art.Util;
import org.apache.commons.io.FileUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GalleryController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        File artDirectory = Util.getArtDirectory();

        // load image details
        File imageDetailsFile = Util.getImageDetailsFile();
        List<String> imageFileLines = FileUtils.readLines(imageDetailsFile);

        Map<String, String> imageDescriptions = new HashMap<>();

        for (String imageFileLine : imageFileLines) {
            int firstSpace = imageFileLine.indexOf(" ");

            if (firstSpace != -1) {
                String fileName = imageFileLine.substring(0, firstSpace).trim();
                String fileDescription = imageFileLine.substring(firstSpace).trim();

                imageDescriptions.put(fileName, fileDescription);
            }
        }

        // map files
        TreeMap<File, ImageAndDescription> fileMap = new TreeMap<File, ImageAndDescription>(new Comparator<File>(){
            public int compare(File f1, File f2)
            {
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
            } });

        File[] files = artDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("svg");
            }
        });

        for (int i = 0; i < files.length; i++) {
            String fileContent = Util.readFile(files[i].getPath());
            fileContent = fileContent.replace("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">", "");

            String description = imageDescriptions.get(files[i].getName()) == null ? "banana" : imageDescriptions.get(files[i].getName());
            ImageAndDescription imageAndDescription = new ImageAndDescription(fileContent, description);

            fileMap.put(files[i], imageAndDescription);
        }

        ModelAndView model = new ModelAndView("Gallery");
        model.addObject("fileMap", fileMap);

        return model;
    }

}
