package com.cloudconsul.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

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
        /*
        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("ip:"+addr.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        */
        String ip = "";
        try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface current = interfaces.nextElement();
            if (!current.isUp() || current.isLoopback() || current.isVirtual()) continue;
            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr.isLoopbackAddress()) continue;
                /*
                if (condition.isAcceptableAddress(addr)) {
                    return addr;
                }
                */
                ip = addr.getHostAddress();
            }
        }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("ip:"+ip);
        //return discoveryClient.getInstances("consul");
        String res = "ip:"+ip+";name:"+name+";port:"+port;
        return res;
    }
}
