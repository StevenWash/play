package com.toulezu.service.start;

/**
 * 启动后会在本地测试环境的 dubbo admin 可见
 * @author Administrator
 *
 */
public class DemoProvider {

    public static void main(String[] args) {
        com.alibaba.dubbo.container.Main.main(args);
    }
}
