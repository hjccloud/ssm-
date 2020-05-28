package com.he.controller;

import com.he.domain.Permission;
import com.he.servicce.PermissionService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/findAll")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Permission> list= permissionService.findAll();
        mv.addObject("list",list);
        mv.setViewName("permission-list");
        return mv;

    }

    @RequestMapping("/save")
    public String save(Permission permission) throws Exception{
        permissionService.save(permission);
        return "redirect:findAll";

    }
}
