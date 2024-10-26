package com.example.demo.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.blogs;
import com.example.demo.model.blogsdto;
import com.example.demo.model.products;
import com.example.demo.model.productsdto;
import com.example.demo.repository.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class blogcontroller {

    @Autowired
    customerrepository customerrepo;

    @Autowired
    productrepository productrepo;

    @Autowired
    orderrepository orderrepo;

    @Autowired
    adminrepository adminrepo;

    @Autowired
    blogrepository blogrepo;

    // líst blog

    @GetMapping("apps-ecommerce-blog")
    public String blog(Model model) {
        List<blogs> blogs = (List<blogs>) blogrepo.findAll();
        model.addAttribute("blogs", blogs);
        return ("admin/apps-ecommerce-blog");
    }

    @GetMapping("apps-ecommerce-create-blog")
    public String addblog(Model model) {
        blogsdto blogsdto = new blogsdto();
        model.addAttribute("blogsdto", blogsdto);
        return "admin/apps-ecommerce-create-blog";
    }

    @PostMapping("apps-ecommerce-create-blog/save")
    public String saveblog(@ModelAttribute("blogsdto") blogsdto blogsdto, BindingResult result) {
        // Check for form validation errors
        if (result.hasErrors()) {
            return "admin/apps-ecommerce-create-blog";
        }

        // Check if the uploaded image is empty
        if (blogsdto.getBlogImage().isEmpty()) {
            result.addError(new FieldError("blogsdto", "BlogImage", "BlogImage is required"));
            return "admin/apps-ecommerce-create-blog"; // Return to the form if there's an error
        }

        MultipartFile image = blogsdto.getBlogImage();
        String storagefilename = image.getOriginalFilename(); // Get the original file name

        // Define the absolute path for the images directory
        String uploaddir = "E:\\doanB\\projectB_cse411\\demo\\src\\main\\resources\\static\\blogimages";
        // Print the upload directory for debugging
        System.out.println("Upload Directory: " + uploaddir);

        Path uploadpath = Paths.get(uploaddir);

        try {
            // Ensure the directory exists, if not, create it
            if (!Files.exists(uploadpath)) {
                Files.createDirectories(uploadpath);
                System.out.println("Directory created: " + uploadpath.toString());
            } else {
                System.out.println("Directory already exists: " + uploadpath.toString());
            }

            // Save the uploaded image to the directory
            try (InputStream inputStream = image.getInputStream()) {
                Path targetPath = uploadpath.resolve(storagefilename);

                // Print target path for debugging
                System.out.println("Target File Path: " + targetPath.toString());

                // Only copy the file if it does not already exist
                if (!Files.exists(targetPath)) {
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File successfully saved at: " + targetPath.toString());
                } else {
                    System.out.println("File already exists: " + targetPath.toString());
                }
            }

        } catch (IOException e) {
            // Handle file saving error
            System.out.println("Error occurred while saving image: " + e.getMessage());
            result.addError(new FieldError("blogsdto", "BlogImage", "Unable to save the image. Try again."));
            return "admin/apps-ecommerce-create-blog";
        }

        // Create a new product entity and set its fields
        blogs bl = new blogs();
        bl.setBlogId(blogsdto.getBlogId());
        bl.setBlogTitle(blogsdto.getBlogTitle());
        bl.setBlogDescription(blogsdto.getBlogDescription());
        bl.setBlogStatus(blogsdto.getBlogStatus());
        bl.setBlogCreateDate(blogsdto.getBlogCreateDate());
        bl.setBlogPostBy(blogsdto.getBlogPostBy());
        bl.setBlogtag(blogsdto.getBlogtag());
        bl.setBlogImage(storagefilename); // Save the image filename in the database

        // Save the product to the repository
        blogrepo.save(bl);

        // Redirect to the product listing page after successful save
        return "redirect:/admin/apps-ecommerce-blog";
    }

    // edit product

    @GetMapping("/apps-ecommerce-edit-blog")
    public String editblog(HttpSession session, Model model) {
        Integer id = (Integer) session.getAttribute("currentProductId");
        if (id == null) {
            model.addAttribute("errorMessage", "No product selected!");
            return "admin/apps-ecommerce-edit-blog";
        }

        products product = productrepo.findById(id).orElse(null);
        if (product == null) {
            model.addAttribute("errorMessage", "Product not found!");
            return "admin/apps-ecommerce-edit-blog";
        }

        model.addAttribute("product", product);
        return "admin/apps-ecommerce-edit-blog";
    }

    @GetMapping("/set-current-blog-id/{id}")
    public String setCurrentBlogId(@PathVariable("id") int id, HttpSession session) {
        session.setAttribute("currentProductId", id);
        return "redirect:/admin/apps-ecommerce-edit-blog";
    }

    // delete product

    @GetMapping("/blogdelete/{id}")
    public String deleteblog(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            blogrepo.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        } catch (EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found or already deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the product.");
        }

        return "redirect:/admin/apps-ecommerce-blogs";
    }

    @GetMapping("apps-ecommerce-blog-details")
    public String blogdetail() {
        return ("admin/apps-ecommerce-blog-details");
    }

}
