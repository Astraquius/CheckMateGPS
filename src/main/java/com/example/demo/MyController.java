package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
 
import java.io.File;
import java.io.IOException;

@Controller
public class MyController {

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/submit")
    public String submit(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("imagen") MultipartFile imagen,
            Model model) {

        // Crear carpeta si no existe
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Guardar la imagen
        String fileName = imagen.getOriginalFilename();
        try {
            File file = new File(UPLOAD_DIR + fileName);
            imagen.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("nombre", nombre);
        model.addAttribute("descripcion", descripcion);
        model.addAttribute("imagen", "/"+UPLOAD_DIR + fileName);

        return "result";
    }
}
//