package me.kupchenko.controller;

import lombok.AllArgsConstructor;
import me.kupchenko.model.BeanInfo;
import me.kupchenko.model.ContextInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequestMapping("/context")
@AllArgsConstructor
public class ContextController {

    private ApplicationContext applicationContext;

    @GetMapping("/info")
    public String showHomePage(Model model) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        List<BeanInfo> classesnames = Stream.of(beanDefinitionNames)
                .map(name -> new BeanInfo(name, applicationContext.getType(name).getCanonicalName(), applicationContext.isSingleton(name)))
                .collect(Collectors.toList());
        model.addAttribute("contextInfo", new ContextInfo(classesnames));
        return "contextInfo";
    }
}
