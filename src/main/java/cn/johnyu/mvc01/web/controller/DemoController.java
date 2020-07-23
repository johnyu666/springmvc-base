package cn.johnyu.mvc01.web.controller;

import cn.johnyu.mvc01.pojo.Customer;
import cn.johnyu.mvc01.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@SessionAttributes(names = {"customer"})
public class DemoController {
    @Autowired
    private DemoService service;

    @RequestMapping("/login")
    public String login(int id,Date birth){
        System.out.println(birth);
        Customer c=new Customer();
        c.setId(id);
        c.setBirth(birth);
        return "suc";
    }




    @RequestMapping("/cafe")
    @ResponseBody
    public String[] findAllCafe(){
       return  service.findAllCafe();
    }

    @RequestMapping("/cafe/{id}")

    public String loadCafe(@PathVariable int id, Model model){
        model.addAttribute("name","latte");
        return "suc";
    }

    /**
     * 结束session的方法
     * @param status
     * @return
     */
    @RequestMapping("/logout")
    public String logout(SessionStatus status){
        status.setComplete();
        return "other";
    }

    /**
     * 以下两个方法用于演示异常处理的转向问题
     */
    @RequestMapping("/trouble")
    public String trouble(){
        int m=1/0;
        return "suc";
    }

    /**
     * 是拦截器的应用之一
     * 只能处理当前Controller的异常转向
     */
    @ExceptionHandler(value = Exception.class)
    public String HandleException(Exception exception, WebRequest request,Model model){
        //可以返回ModelAndView或ViewName
        //也可以使用WebRequest参数或Model传递参数
        model.addAttribute("reson",exception.getMessage());
        return "error";
    }

    /**
     * 相当于前置拦截器，返回值及放到model的数据，都会出现在同一个request以后的model
     * @param customer 自动接收前端参数（不是必须使用@RequestParam）
     * @param model 手工置入model的数据
     * @return 自动置入model的数据,@ModelAttribute的name表示key
     */
    @ModelAttribute(name="info")
    public String addToModel(Customer customer, Model model){
        //与@SessionAttributes(names = {"customer"})配合演示
        model.addAttribute("customer",customer);
        return "我会出现在所有的model中";
    }

    /**
     * 使用@InitBinder进行数据绑定的定制操作,只针对form形式提交的数据
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    Date date=sdf.parse(text);
                    setValue(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
