package com.dave.art.controller;

import com.dave.art.Util;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        String imagePath = request.getQueryString();

        String imageSVG = Util.readFile(imagePath);
        imageSVG = imageSVG.replace("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">", "");

        ModelAndView model = new ModelAndView("Image");
        model.addObject("imageSVG", imageSVG);
        model.addObject("imagePath", imagePath);

        return model;
    }
}
