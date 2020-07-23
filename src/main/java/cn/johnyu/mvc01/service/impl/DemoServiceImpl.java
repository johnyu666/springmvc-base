package cn.johnyu.mvc01.service.impl;

import cn.johnyu.mvc01.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String[] findAllCafe() {
        return new String[]{"espresso","mocha"};
    }
}
