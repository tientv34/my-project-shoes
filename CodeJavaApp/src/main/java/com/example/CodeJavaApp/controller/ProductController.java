package com.example.CodeJavaApp.controller;

import com.example.CodeJavaApp.entity.CartDot;
import com.example.CodeJavaApp.entity.Products;
import com.example.CodeJavaApp.repository.ProductRepository;
import com.example.CodeJavaApp.service.impl.ImageStoreService;
import com.example.CodeJavaApp.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Controller
@SpringBootApplication
public class ProductController {

    @Autowired
    private ProductServiceImpl prdService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageStoreService imageStoreService;
    @Autowired
    private ProductRepository repo;


    @GetMapping("/addProduct")
    public String viewAddProduct(Model model) {
        try {
            model.addAttribute("product", new Products());
            return "add_prd";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Tìm kiếm products
    @GetMapping("/product")
    public String viewProduct(Model model, @Param("name") String name) {
        try {
            List<Products> lisPrd = prdService.findByNameContaining(name);
            if (lisPrd.isEmpty()) {
//            model.addAttribute("product",lisPrd);
                model.addAttribute("ERROR", "No Find Product");
                return "product";
            }
            model.addAttribute("product", lisPrd);
            model.addAttribute("name", name);
            return "product";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //insert only product
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String savePrd(@ModelAttribute(name = "product") Products prd, ModelMap modelMap,
                          @RequestParam("fileImage") MultipartFile multiparFile) throws IOException {
        try {
            System.out.println("prd : " + prd);
            float fileSizeInMegabyte = multiparFile.getSize() / 1_000_000.0f;
            if (fileSizeInMegabyte > 5.0f) {
                modelMap.addAttribute("ERRORPRD", "File must be <= 5Mb");
                return "/addProduct";
            }
            String fileName = StringUtils.cleanPath(multiparFile.getOriginalFilename());
            prd.setImage(fileName);
            List<Products> listProduct = productRepository.findAll();
            System.out.println("listProduct: " + listProduct);
            System.out.println("prd : " + prd);
            Products SavePrd = prdService.savePrd(prd);

            String uploadPrd = "./uploads-image/" + SavePrd.getId_prd();

            System.out.println("uploadPrd " + uploadPrd);
            //////
            Path uploadPath = Paths.get(uploadPrd);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multiparFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                //in ra đường dẫn
                System.out.println(filePath.toFile().getAbsolutePath());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not save uploaded file" + fileName);
            }
            for (Products x : listProduct) {
                if (x.getName_prd().trim().equalsIgnoreCase(prd.getName_prd())
                        && x.getType().trim().equalsIgnoreCase(prd.getType())
                        && x.getSize() == prd.getSize()) {
                    x.setQuantity(x.getQuantity() + prd.getQuantity());
                    System.out.println("Quantity is x: " + x.getQuantity());
                    prdService.save(x);
                    modelMap.addAttribute("ERRORPRD111", "Sản phẩm đã tồn tại đã cập nhập lại số lượng: " + x.getName_prd());
                    return "redirect:/product/page";
                } else if (x.getName_prd().trim().equalsIgnoreCase(prd.getName_prd())
                        && x.getType().trim().equalsIgnoreCase(prd.getType())
                        && x.getSize() != prd.getSize()) {
                    prdService.save(prd);
                    modelMap.addAttribute("ERRORPRD111", "Thêm thành công!!!!");
                    return "redirect:/product/page";
                }
            }
            System.out.println("prd" + prd);
            prdService.save(prd);
            modelMap.addAttribute("ERRORPRD111", "Thêm thành công!!!!");
            return "redirect:/product/page";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Delete only Product to id_prd;
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deletePrd(@RequestParam("id_prd") Long id_prd, Model model) {
        try {
            prdService.deletePrd(id_prd);
            return "redirect:/product/page";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam("id_prd") Long id_prd, Model model) {
        try {
            Optional<Products> prdEdit = prdService.findPrdById(id_prd);
            System.out.println("prdEdit " + prdEdit);
            prdEdit.ifPresent(products -> model.addAttribute("product", products));
            return "editprd";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Edit Products
    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String editSave(Products prd, ModelMap modelMap, @RequestParam("fileImage") MultipartFile multiparFile) throws IOException {
        try {
            System.out.println("prd " + prd);
            String fileName = StringUtils.cleanPath(multiparFile.getOriginalFilename());
            prd.setImage(fileName);
            if (prd.getImage() == "") {
                Optional<Products> prdEdit = prdService.findPrdById(prd.getId_prd());
                System.out.println("OptionalProduct " + prdEdit);
                String image = prdEdit.get().getImage();
                System.out.println("Image: " + image);
                prd.setImage(image);
                System.out.println("Prd sau khi set image " + prd);
                prdService.save(prd);
                return "redirect:/product";
            }
            float fileSizeInMegabyte = multiparFile.getSize() / 1_000_000.0f;
            if (fileSizeInMegabyte > 5.0f) {
                modelMap.addAttribute("ERRORPRD", "File must be <= 5Mb");
                return "/addProduct";
            }

            Products SavePrd = prdService.savePrd(prd);

            String uploadPrd = "./uploads-image/" + SavePrd.getId_prd();

            System.out.println("uploadPrd " + uploadPrd);
            //////
            Path uploadPath = Paths.get(uploadPrd);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multiparFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                //in ra đường dẫn của filePath
                System.out.println(filePath.toFile().getAbsolutePath());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not save uploaded file" + fileName);
            }
            System.out.println("prd" + prd);
            prdService.save(prd);
            return "redirect:/product/page";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //List giay new Product
    @GetMapping("/index/new/page")
    public String PageNew(Model model) {
        return viewProductNewPage(model, 1, "id_prd", "asc");
    }

    @GetMapping("/index/new/page/{pageNumber}")
    public String viewProductNewPage(Model model, @PathVariable("pageNumber") int currentPage,
                                     @Param("sortField") String sortField,
                                     @Param("sortDir") String sortDir) {
        try {
            Page<Products> page = prdService.findByTypePage("1", currentPage, sortField, sortDir);
            System.out.println("PageProducts: " + page);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<Products> listProduct = page.getContent();
            System.out.println("listProduct111: " + listProduct);

            model.addAttribute("currentPage", currentPage);

            model.addAttribute("totalItems", totalItems);

            model.addAttribute("totalPages", totalPages);

            model.addAttribute("productjordan1", listProduct);

            model.addAttribute("sortField", sortField);

            model.addAttribute("sortDir", sortDir);

            String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
            model.addAttribute("reverseSortDir", sortDir);
            return "/danhsach/danhsachnew";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //List giày nike

    @GetMapping("/index/nike/page")
    public String PageNike(Model model) {
        return viewNike(model, 1, "id_prd", "asc");
    }

    @GetMapping("/index/nike/page/{pageNumber}")
    public String viewNike(Model model, @PathVariable("pageNumber") int currentPage,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir) {
        try {
            Page<Products> page = prdService.findByTypePage("2", currentPage, sortField, sortDir);
            System.out.println("PageProducts: " + page);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<Products> listProduct = page.getContent();
            System.out.println("listProduct111: " + listProduct);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("lisPrdNike1", listProduct);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            return "/danhsach/danhsachgiaynike";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //List giày adidas
    @GetMapping("/index/adidas/page")
    public String PageAdidas(Model model) {
        return viewAdidas(model, 1, "id_prd", "asc");
    }

    @GetMapping("/index/adidas/page/{pageNumber}")
    public String viewAdidas(Model model, @PathVariable("pageNumber") int currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir) {
        try {
            Page<Products> page = prdService.findByTypePage("3", currentPage, sortField, sortDir);
            System.out.println("PageProducts: " + page);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<Products> listProduct = page.getContent();
            System.out.println("listProduct111: " + listProduct);

            model.addAttribute("currentPage", currentPage);

            model.addAttribute("totalItems", totalItems);

            model.addAttribute("totalPages", totalPages);

            model.addAttribute("productadidas1", listProduct);

            model.addAttribute("sortField", sortField);

            model.addAttribute("sortDir", sortDir);
            return "/danhsach/danhsachgiayadidas";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //List giày converse
    @GetMapping("/index/converse/page")
    public String PageConverse(Model model) {
        String keyword = null;
        return viewConverse(model, 1, "id_prd", "asc");
    }

    @GetMapping("/index/converse/page/{pageNumber}")
    public String viewConverse(Model model, @PathVariable("pageNumber") int currentPage,
                               @Param("sortField") String sortField,
                               @Param("sortDir") String sortDir) {
        try {
            Page<Products> page = prdService.findByTypePage("4", currentPage, sortField, sortDir);
            System.out.println("PageProducts: " + page);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<Products> listProduct = page.getContent();
            System.out.println("listProduct111: " + listProduct);

            model.addAttribute("currentPage", currentPage);

            model.addAttribute("totalItems", totalItems);

            model.addAttribute("totalPages", totalPages);

            model.addAttribute("productconversse1", listProduct);

            model.addAttribute("sortField", sortField);

            model.addAttribute("sortDir", sortDir);
            return "/danhsach/danhsachgiayconverse";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Chi tiết sản phẩm
    @RequestMapping(value = "/productdetails", method = RequestMethod.GET)
    public String viewProductDetails(@RequestParam("id_prd") Long id_prd, Model model) {
        try {
            Optional<Products> prd = prdService.findPrdById(id_prd);
            System.out.println("prd: " + prd);
            List<Products> listPrdBySize = productRepository.findByNameAndTypeProduct(prd.get().getName_prd().trim(), prd.get().getType().trim());
            System.out.println("SizePrd: " + listPrdBySize);
            List<Integer> sizePrd = new ArrayList<>();
            for (Products x : listPrdBySize) {
                sizePrd.add(x.getSize());
            }
            System.out.println("Size list Prd: " + sizePrd);
            Collections.sort(sizePrd);
            System.out.println("SizePrd: " + sizePrd);
            listPrdBySize.sort(Comparator.comparing(Products::getSize));
            model.addAttribute("size", listPrdBySize);
            prd.ifPresent(products -> model.addAttribute("product", products));
            return "productdetails";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Chi tiết sản phẩm mới
    @RequestMapping(value = "/productdetails/{name}/{size}/{type}")
    public String addCart1(HttpServletRequest request, @PathVariable("name") String name,
                           @PathVariable("size") int size,
                           @PathVariable("type") String type,
                           Model model) {
        try {
            Products product = repo.findByNameAndTypeProduct(name, type, size);
            System.out.println("products: " + product);
            List<Products> listPrdBySize = productRepository.findByNameAndTypeProduct(product.getName_prd().trim(), product.getType().trim());
            System.out.println("SizePrd: " + listPrdBySize);
            listPrdBySize.sort(Comparator.comparing(Products::getSize));
            System.out.println("SizePrd: " + listPrdBySize);
            model.addAttribute("size", listPrdBySize);
            model.addAttribute("sizeItem", size);
            if (product != null) {
                model.addAttribute("product", product);
                return "productdetails";
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    //List Product Page
    @GetMapping("/product/page")
    public String paginate(Model model) {
        String keyword = null;
        return listByPages(model, 1, keyword);
    }

    //Page List Products admin
    @GetMapping("/product/page/{pageNumber}")
    public String listByPages(Model model,
                              @PathVariable("pageNumber") int currentPage,
                              @Param("keyword") String keyword) {
        try {
            Page<Products> page = prdService.listAllProductPage(currentPage, keyword);
            System.out.println("PageProducts: " + page);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<Products> listProduct = page.getContent();
            System.out.println("listProduct111: " + listProduct);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("keyword", keyword);
            model.addAttribute("listProduct", listProduct);
            return "/products/productPage";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
