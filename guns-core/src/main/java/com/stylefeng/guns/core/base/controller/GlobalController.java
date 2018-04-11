package com.stylefeng.guns.core.base.controller;


import com.stylefeng.guns.core.rsa.RSAUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.interfaces.RSAPublicKey;

/**
 * 全局的控制器
 *
 * @author fengshuonan
 * @date 2016年11月13日 下午11:04:45
 */
@Controller
@RequestMapping("/global")
public class GlobalController {

    /**
     * 跳转到404页面
     *
     * @author fengshuonan
     */
    @RequestMapping(path = "/error")
    public String errorPage() {
        return "/404.html";
    }

    /**
     * 跳转到session超时页面
     *
     * @author fengshuonan
     */
    @RequestMapping(path = "/sessionError")
    public String errorPageInfo(Model model) {
        model.addAttribute("tips", "session超时");
        model = generateKeyPair(model);
        return "/login.html";
    }

    /**
     * add by lgg rsa
     * @param model
     * @return
     */
    private Model generateKeyPair(Model model){
        try {
            // 加载动态的公钥
            RSAPublicKey rsap = (RSAPublicKey) RSAUtil.generateKeyPair().getPublic();
            String module = rsap.getModulus().toString(16);
            String empoent = rsap.getPublicExponent().toString(16);
            model.addAttribute("module",module);
            model.addAttribute("empoent", empoent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}
