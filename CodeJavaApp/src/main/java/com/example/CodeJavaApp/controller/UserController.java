package com.example.CodeJavaApp.controller;

import com.example.CodeJavaApp.entity.Products;
import com.example.CodeJavaApp.entity.User;
import com.example.CodeJavaApp.exception.ExceptionController;
import com.example.CodeJavaApp.repository.UserRepository;
import com.example.CodeJavaApp.service.impl.ICartDotImpl;
import com.example.CodeJavaApp.service.impl.ProductServiceImpl;
import com.example.CodeJavaApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.NumberFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@SpringBootApplication
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductServiceImpl prdService;

    @Autowired
    private ICartDotImpl cartService;

    @GetMapping("/index")
    public String viewIndexPage(Model model, HttpServletRequest request)  {
        try {
            request.getSession().removeAttribute("MAP111");
            HttpSession session = request.getSession();
            session.setAttribute("CountCart", cartService.getCount());
            Page<Products> lisPrdNew = prdService.listAllBillsPage("1", 1);
            System.out.println("Size Product New: " + lisPrdNew.getNumberOfElements());
            System.out.println("lisPrdNew: " + lisPrdNew);
//        System.out.println("Size Product: "+lisPrdNew.size());
            model.addAttribute("productjordan1", lisPrdNew);

            Page<Products> lisPrdNike = prdService.listAllBillsPage("2", 1);
            System.out.println("Size Product lisPrdNike: " + lisPrdNike.getNumberOfElements());
            System.out.println("lisPrdNike: " + lisPrdNike);
            model.addAttribute("lisPrdNike1", lisPrdNike);

            Page<Products> lisPrdAdidas = prdService.listAllBillsPage("3", 1);
            System.out.println("Size Product lisPrdAdidas: " + lisPrdAdidas.getNumberOfElements());
            System.out.println("lisPrdAdidas: " + lisPrdAdidas);
            model.addAttribute("productadidas1", lisPrdAdidas);

            Page<Products> lisPrdConversse = prdService.listAllBillsPage("4", 1);
            System.out.println("Size Product lisPrdConversse: " + lisPrdConversse.getNumberOfElements());
            System.out.println("lisPrdConversse: " + lisPrdConversse);
            model.addAttribute("productconversse1", lisPrdConversse);
            return "index";
        } catch (Exception e) {

        }
        return null;
    }

    @GetMapping("/home_prd")
    public String viewHomePrd(Model model) {
        try {
            List<Products> lisPrdNew = prdService.findByTypeContaining("1");

            System.out.println("Size Product New: " + lisPrdNew.size());
            System.out.println("lisPrdNew: " + lisPrdNew);
            System.out.println("Size Product: " + lisPrdNew.size());
            model.addAttribute("productjordan", lisPrdNew);

            List<Products> lisPrdNike = prdService.findByTypeContaining("2");
            System.out.println("Size Product lisPrdNike: " + lisPrdNike.size());
            System.out.println("lisPrdNike: " + lisPrdNike);
            model.addAttribute("lisPrdNike", lisPrdNike);

            List<Products> lisPrdAdidas = prdService.findByTypeContaining("3");
            System.out.println("Size Product lisPrdAdidas: " + lisPrdAdidas.size());
            System.out.println("lisPrdAdidas: " + lisPrdAdidas);
            model.addAttribute("productadidas", lisPrdAdidas);

            List<Products> lisPrdConversse = prdService.findByTypeContaining("4");
            System.out.println("Size Product lisPrdConversse: " + lisPrdConversse.size());
            System.out.println("lisPrdConversse: " + lisPrdConversse);
            model.addAttribute("productconversse", lisPrdConversse);
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //delete User
    @RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
    public String deletePrd(@RequestParam("id") Long id) {
        try {
            Optional<User> user = userService.findUserById(id);
            userService.deleteUser(id);
            return "redirect:/page";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Edit User
    @RequestMapping(value = "/edituser", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") Long id, Model model) {
        try {
            Optional<User> userEdit = userService.findUserById(id);
            System.out.println("userEdit " + userEdit);
            userEdit.ifPresent(users -> model.addAttribute("user", users));
            return "edituser";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser(User user, ModelMap modelMap) {
        try {
            System.out.println("User: " + user);
            user.setPassword(user.getPassword());
            System.out.println("user" + user);
            userService.save(user);
            return "redirect:/page";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/logout")
    public String viewLogin(@ModelAttribute("userLogin") User user, WebRequest request, SessionStatus status) {
        cartService.clearCart();
        status.setComplete();
        request.removeAttribute("userLogin", WebRequest.SCOPE_SESSION);
        return "redirect:/index";
    }


    //Login
    @GetMapping("/signup")
    public ModelAndView login() {
        cartService.clearCart();
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/listUsert", method = RequestMethod.POST)
    public String login(@ModelAttribute("user") User user, ModelMap modelMap, HttpServletRequest request) {
        try {

            HttpSession session = request.getSession();
            User userModel = userService.findByEmail(user.getEmail());
            if (userModel != null) {
                //Lấy password từ database giải mã và so sánh vs password từ client
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                System.out.println("passworduser: " + user.getPassword());
                boolean isSamePassword = encoder.matches(user.getPassword(), userModel.getPassword());
                System.out.println("isSamePassword " + isSamePassword);
                if (isSamePassword) {
                    if (userModel.getRoles().equals("admin")) {
                        String LastName = userModel.getLastname();
                        System.out.println("LastName: " + LastName);
                        session.setAttribute("map", "" + LastName);
                        session.setAttribute("userLogin", userModel);
                        return "redirect:/page";
                    } else if (userModel.getRoles().equals("user")) {
                        String LastName = userModel.getLastname();
                        System.out.println("LastName: " + LastName);
                        session.setAttribute("map", "" + LastName);
                        session.setAttribute("userLogin", userModel);
                        return "redirect:/index";
                    } else if (userModel.getRoles().equals("disable")) {
                        modelMap.addAttribute("ERROR", "Account is disable");
                        return "login";
                    }
                } else {
                    System.out.println("Login thất bại");
                    modelMap.addAttribute("userLogin", "");
                    modelMap.addAttribute("ERROR", "Email of password bot exits");
                }
            } else {
                modelMap.addAttribute("userLogin", "");
                modelMap.addAttribute("ERROR", "Email of password bot exits");
            }
            modelMap.addAttribute("userLogin", "");
            modelMap.addAttribute("ERROR", "Email of password bot exits");
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //end Login

    //Register
    @GetMapping("/register")
    public String viewRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    //Save user
    @RequestMapping(value = "/process_register", method = RequestMethod.POST)
    public String save(User user, ModelMap modelMap) {
        try {
            //Mã hóa password khi lưu vào database.
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePassword = encoder.encode(user.getPassword());
            boolean decodePassword = encoder.matches(user.getPassword(), encodePassword);
            System.out.println("decodePassworddecodePassword " + decodePassword);
            List<User> lstUser = userService.getAllUser();
            System.out.println("listUser: " + lstUser);
            for (User x : lstUser) {
                System.out.println("x: " + x);
                if (user.getEmail().equals(x.getEmail())) {
                    System.out.println("aaaaaaaaa");
                    modelMap.addAttribute("ERROR", "Email already exists!!!");
                    return "register";
                }
            }
            user.setPassword(encodePassword);
            System.out.println("user" + user);
            user.setRoles("user");
            userService.save(user);
            return "process_register";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/lienhe")
    public String viewLienHe() {
        return "lienhe";
    }

    @GetMapping("/tintuc")
    public String viewTinTuc() {
        return "tintuc";
    }

    //Page List user
    @RequestMapping("/page")
    public String viewListUser(Model model) {
        String keyword = null;
        return listByPages(model, 1, keyword);
    }

    //phía admin
    @GetMapping("/page/{pageNumber}")
    public String listByPages(Model model,
                              @PathVariable("pageNumber") int currentPage,
                              @Param("keyword") String keyword) {
        try {
            Page<User> page = userService.listAllUserPage(currentPage, keyword);
            System.out.println("Page11111: " + page.getNumberOfElements());
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<User> listUser = page.getContent();
            System.out.println("listUser111: " + listUser);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("keyword", keyword);
            model.addAttribute("listUser", listUser);
            return "listuser";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //phía giao diện
    @RequestMapping("/search")
    public String viewListPrdSearch(Model model) {
        String keyword = null;
        return searchPrd(model, 1, keyword);
    }

    @GetMapping("/search/{pageNumber}")
    public String searchPrd(Model model,
                            @PathVariable("pageNumber") int currentPage,
                            @Param("keyword") String keyword) {
        try {
            Page<Products> page = prdService.listAllProductPage(currentPage, keyword);
            System.out.println("Page11111: " + page.getNumberOfElements());
            System.out.println("keyword: " + keyword);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<Products> listPrdSearch = page.getContent();
            System.out.println("listPrd: " + listPrdSearch);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pageTitle", "Search Results for" + keyword);
            model.addAttribute("listPrdSearch", listPrdSearch);
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/index/doimatkhau")
    public String viewDoiMK() {
        return "doimatkhau";
    }


    @RequestMapping(value = "/index/doimatkhau", method = RequestMethod.POST)
    public String doimatkhau(@Param("password") String password,
                             @Param("newpassword") String newpassword,
                             @Param("new1password") String new1password, HttpSession session, Model model) {
        try {
            System.out.println("password: " + password);
            System.out.println("newpassword: " + newpassword);
            System.out.println("new1password: " + new1password);
            User userModel = (User) session.getAttribute("userLogin");
            if (userModel != null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                boolean isSamePassword = encoder.matches(password, userModel.getPassword());
                System.out.println("isSamePassword: " + isSamePassword);
                if (isSamePassword) {
                    if (!(newpassword.equalsIgnoreCase(new1password))) {
                        model.addAttribute("error", "Mật khẩu mới không trùng khớp");
                        return "doimatkhau";
                    } else {
                        String encodePassword = encoder.encode(newpassword);
                        userModel.setPassword(encodePassword);
                        userService.save(userModel);
                        model.addAttribute("message", "Đổi mật khẩu thành công!!!");
                        return "doimatkhau";
                    }
                }
            }
            model.addAttribute("error", "Mật khẩu mới không trùng khớp");
            return "doimatkhau";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
