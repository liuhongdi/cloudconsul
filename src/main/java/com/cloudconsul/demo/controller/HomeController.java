package com.cloudconsul.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${provider.name}")
    private String name;

    @Value("${server.port}")
    private String port;

    // list services
    @GetMapping("/serviceslist")
    public Object serviceslist() {
        return discoveryClient.getServices();
    }

    // list instances in a service id
    @GetMapping("/instanceslist")
    public Object instanceslist() {
        return discoveryClient.getInstances("consul");
    }

    //test api
    @GetMapping("/hello")
    public String hello() {
        //return discoveryClient.getInstances("consul");
        String res = "name:"+name+";port:"+port;
        return res;
    }
}
