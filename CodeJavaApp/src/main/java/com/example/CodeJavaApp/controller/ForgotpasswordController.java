package com.example.CodeJavaApp.controller;

import com.example.CodeJavaApp.Utility.Utility;
import com.example.CodeJavaApp.service.impl.CustomerNotFoundException;
import com.example.CodeJavaApp.service.impl.UserServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotpasswordController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle","Forgot Password");
        return "forgot_password_form";
    }

    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public String processForgotPassword(HttpServletRequest request,Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(100);
        System.out.println("email: "+email);
        System.out.println("token: "+token);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            senEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (CustomerNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "forgot_password_form";
    }
    private void senEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("contact@sneakerstore.com", "Sneaker support");
        helper.setTo(email);
        String subject ="Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }


//
//    public void sendEmail(){
//
//    }
//
//
//    @GetMapping("/reset_password")
//    public String showResetPasswordForm() {
//
//    }
//
//    @PostMapping("/reset_password")
//    public String processResetPassword() {
//
//    }
}
