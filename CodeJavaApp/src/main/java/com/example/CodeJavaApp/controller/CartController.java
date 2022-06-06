package com.example.CodeJavaApp.controller;

import com.example.CodeJavaApp.entity.*;
import com.example.CodeJavaApp.repository.BillDetailRepository;
import com.example.CodeJavaApp.repository.ProductRepository;
import com.example.CodeJavaApp.service.impl.IBillsServiceImpl;
import com.example.CodeJavaApp.service.impl.ICartDotImpl;
import com.example.CodeJavaApp.service.impl.ProductServiceImpl;
import com.example.CodeJavaApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CartController {
    @Autowired
    private ICartDotImpl cartService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IBillsServiceImpl billsService;
    @Autowired
    private BillDetailRepository billDetailRepository;

    @RequestMapping("/shopingcart")
    public String viewListShopingcart(Model model, HttpSession session, HttpServletRequest request) {
        try {
//            model.addAttribute("Cart",cartService.getAllItems());
            System.out.println("cart: " + cartService.getAllItems());
            request.getSession().setAttribute("Cart", cartService.getAllItems());
            Collection<CartDot> cartDots = cartService.getAllItems();
            if (cartDots.isEmpty()){
                model.addAttribute("error","Giỏ hàng rỗng");
                return "liscartdot";
            }
            double vnd = cartService.getAmount();
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String price = currencyVN.format(vnd);
            model.addAttribute("totalPrice", price);
            session.setAttribute("totalPrice", price);
            return "liscartdot";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Add only product ****
    @RequestMapping(value = "/Addcart/{id}")
    public String addCart(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            Optional<Products> prd = productService.findPrdById(id);
            Products product = prd.get();
            System.out.println("Product: " + product);
            if (product != null) {
                CartDot itemCart = new CartDot();
                itemCart.setProduct(product);
                itemCart.setQuantity(1);
                itemCart.setTotalPrice(product.getPrice());
                cartService.add(itemCart);
            }
            return "redirect:/shopingcart";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Delete only product bên giao diện người dùng
    @GetMapping("delete/{id}")
    public String deleteCart(@PathVariable("id") Long id) {
        try {
            cartService.deleteCart(id);
            return "redirect:/shopingcart";
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    //Edit quantily product bên giao diện người dùng(Cấn sửa)
    @RequestMapping(value = "EditCart/{id}/{quantity}")
    public String EditCart(@PathVariable Long id, @PathVariable Integer quantity, Model model) {
        try {
            System.out.println(id + "-" + quantity);
            Optional<Products> prd = productService.findPrdById(id);
            Products products = prd.get();
            if (products.getQuantity() < quantity) {
                model.addAttribute("AAA", "Số lượng sản phẩm không đủ");
                return "redirect:/shopingcart";
            }
            cartService.EditCart(id, quantity);
            return "redirect:/shopingcart";
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @GetMapping("/shopingcart/checkout")
    public ModelAndView CheckOut(HttpServletRequest request, HttpSession session, Model model) {
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("cartcheckout");
            Collection<CartDot> cartDot = (Collection<CartDot>) session.getAttribute("Cart");
            System.out.println("cartDot: " + cartDot);
            if (cartDot.size() <= 0) {
                model.addAttribute("Cardisempty", "Giỏ hàng của bạn chưa có sản phẩm");
                modelAndView.setViewName("liscartdot");
                return modelAndView;
            }
            Bills bills = new Bills();
            LocalDateTime date = LocalDateTime.now();
            Instant instant = Instant.now();
            String d1 = instant.toString();
            System.out.println("d1: " + d1);
            Long miliSeconds = System.currentTimeMillis();
            String mahd = "#" + date + miliSeconds;
            User userModel = (User) session.getAttribute("userLogin");
            System.out.println("userModel: " + userModel);
            if (userModel != null) {
                bills.setUser(userModel);
            }
            System.out.println("bills: " + bills);
            modelAndView.addObject("bills", bills);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String CheckOutBill(HttpServletRequest request, HttpSession session, @ModelAttribute("bills") Bills bills) {
        try {
            User userModel = (User) session.getAttribute("userLogin");
            System.out.println("userModel: " + userModel);
            System.out.println("bills: " + bills);
            Instant instant = Instant.now();
            String d1 = instant.toString();
            System.out.println("d1: " + d1);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println(timestamp.getTime());
            String mahd = "#" + timestamp.getTime();
            System.out.println("bills: " + bills);

            Collection<CartDot> cartDot = (Collection<CartDot>) session.getAttribute("Cart");
            System.out.println("cartDot: " + cartDot);
            if (cartDot == null) {
                session.setAttribute("warning", "Cart not is Empty");
                return "redirect:/shopingcart";
            }
            if (userModel != null) {
                bills.setMahd(mahd);
                bills.setDate(date);
                bills.setStatus(1); //Set mặc định trạng thái 1 là đặt hàng thành công.
                bills.setUser(userModel);
                bills.setAddress1(userModel.getAddress());
                userModel.setBills(Collections.singleton(bills));
                System.out.println("UserModel: " + userModel);
                System.out.println("Billls: " + bills);
            } else if (userModel == null) {
                System.out.println("billsusernull: " + bills);
                bills.setMahd(mahd);
                bills.setDate(date);
                bills.setStatus(1);
                bills.setUser(userModel);
                String userIDStr = "*" + timestamp.getTime();
                int number = (int) timestamp.getTime();
                bills.setUserid(number);
                System.out.println("bills save: " + bills);
            }

            int a = billsService.AddBills(bills);
            System.out.println("a: " + a);
            if (a > 0) {
                for (CartDot x : cartDot) {
                    BillDetail billDetail = new BillDetail();
                    x.getProduct().setBillDetails(Collections.singleton(billDetail));
                    bills.setBillDetails(Collections.singleton(billDetail));
                    billDetail.setBills(bills);
                    double totalPrice = cartService.getAmount();
                    System.out.println("totalPrice: " + totalPrice);
                    billDetail.setPrice(x.getTotalPrice());
                    billDetail.setProducts(x.getProduct());
                    billDetail.setQuantity(x.getQuantity());
                    System.out.println("BillDetail: " + billDetail);
                    billsService.addBillDetail(billDetail);

                    //Set quantity cho product khi mua hang
                    Long idprd = x.getProduct().getId_prd();
                    System.out.println("IDProduct: " + idprd);
                    Optional<Products> products = productService.findPrdById(idprd);
                    System.out.println("ProductByID: " + products);
                    int quantityPrd = products.get().getQuantity();
                    System.out.println("QuantityPrd: " + quantityPrd);
                    quantityPrd = quantityPrd - x.getQuantity();
                    System.out.println("Quantity: " + quantityPrd);
                    Products prd = products.get();
                    prd.setQuantity(quantityPrd);
                    System.out.println("prd: " + prd);
                    productService.save(prd);
                    //end....
                }
            }
            System.out.println("session:" + session.getAttribute("Cart"));
            session.removeAttribute("Cart");
            cartService.clearCart();
            request.getSession().setAttribute("MAP111", "Đặt hàng thành công! Đơn hàng của bạn là: "+ mahd);
            return "redirect:/shopingcart";
        } catch (Exception e) {
            System.out.println("Lỗi rồi");
        }
        return null;
    }
    //List Bill
    @GetMapping("/bill")
    public String viewProduct(Model model) {
        return listByPages(model, 1);
    }

    @GetMapping("/bill/{pageNumber}")
    public String listByPages(Model model,
                              @PathVariable("pageNumber") int currentPage) {
        try {
            Page<Bills> page = billsService.listAllBillsPage(currentPage);
            System.out.println("PageBills: " + page);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            List<Bills> listBills = page.getContent();
            System.out.println("listBills: " + listBills);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listBills", listBills);
            return "listBills";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Chi tiết hóa đơn
    @RequestMapping(value = "/editbill", method = RequestMethod.GET)
    public String edithd(@RequestParam("id") Long id, Model model) {
        try {
            List<BillDetail> billDetail = billsService.findBillDetailByIdHd(id);
            System.out.println("billDetail: " + billDetail);
            model.addAttribute("billDetail", billDetail);
            List<Products> listproducts = new ArrayList<>();
            for (BillDetail x : billDetail) {
                listproducts.add(x.getProducts());
            }
            System.out.println("listproducts: " + listproducts);
            model.addAttribute("listproducts", listproducts);
            return "/allList/listBillDetail";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Hủy Hóa đơn
    @RequestMapping(value = "/huybill", method = RequestMethod.GET)
    public String duyhd(@RequestParam("id") Long id, Model model) {
        try {
            List<BillDetail> billDetail = billsService.findBillDetailByIdHd(id);
            System.out.println("billDetail: " + billDetail);
            List<Products> listproducts = new ArrayList<>();
            for (BillDetail x : billDetail) {
                listproducts.add(x.getProducts());
            }
            for (BillDetail x : billDetail) {
                for (Products y : listproducts) {
                    if (x.getProducts().getId_prd() == y.getId_prd()) {
                        System.out.println("Quantity y: " + y.getQuantity());
                        y.setQuantity(x.getQuantity() + y.getQuantity());
                        System.out.println("Quantity y sau khi update: " + y.getQuantity());
                        productService.save(y);
                    }
                }
            }
            Optional<Bills> bills = billsService.findBillById(id);
            System.out.println("bills: " + bills);
            Bills bill = bills.get();
            bill.setStatus(3);
            System.out.println("bill: " + bill);
            billsService.save(bill);
            return "redirect:/bill";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Duyệt Hóa đơn
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String Updata(@PathVariable("id") Long id, Model model) {
        try {
            Optional<Bills> bills = billsService.findBillById(id);
            System.out.println("bills: " + bills);
            Bills bill = bills.get();
            bill.setStatus(2);
            System.out.println("bill: " + bill);
            billsService.save(bill);
            return "redirect:/bill";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //xóa sản phẩm trong Hóa đơn
    @RequestMapping(value = "/cancel/{id_prd}/{id}", method = RequestMethod.GET)
    public String cancelData(@PathVariable("id_prd") Long id_prd,
                             @PathVariable("id") Long id) {
        try {
            Optional<Products> products = productService.findPrdById(id_prd);
            Optional<BillDetail> billDetail = billsService.findBillDetailId(id);
            System.out.println("Products: " + products);
            BillDetail billDetail1 = billDetail.get();
            Products prd = products.get();
            int quantity1 = billDetail1.getQuantity();
            prd.setQuantity(prd.getQuantity() + quantity1);
            System.out.println("quantityprd: " + prd.getQuantity());
            productService.save(prd);
            billDetailRepository.deleteById(id);
            return "redirect:/shopingcart";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //don hang nguoi dung
    @GetMapping("/index/donhang")
    public String viewDonHang(HttpSession session,Model model){
        User user = (User) session.getAttribute("userLogin");
        System.out.println("user: "+user);
        List<Bills> listBills = billsService.findByIduser(user.getId());
        System.out.println("listBills: "+listBills);
        model.addAttribute("listBills",listBills);
        return "cartUser/donhang";
    }
    @RequestMapping(value = "/showbill", method = RequestMethod.GET)
    public String chiTietDonhang(@RequestParam("id") Long id, Model model,HttpSession session) {
        try {
            List<BillDetail> billDetail = billsService.findBillDetailByIdHd(id);
            System.out.println("billDetail: " + billDetail);
            model.addAttribute("billDetail", billDetail);
            List<Products> listproducts = new ArrayList<>();
            double tong = 0;
            for (BillDetail x : billDetail) {
                listproducts.add(x.getProducts());
                tong = (double) (tong + (x.getPrice() * x.getQuantity()));
            }
            System.out.println("Tong tien: "+tong);

            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String price = currencyVN.format(tong);
            model.addAttribute("totalPrice", price);
            session.setAttribute("totalPrice", price);

            System.out.println("listproducts: " + listproducts);
            model.addAttribute("listproducts", listproducts);

           return "cartUser/hoadonchitiet";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
