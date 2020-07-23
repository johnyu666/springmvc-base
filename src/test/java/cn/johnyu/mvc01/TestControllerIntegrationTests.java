package cn.johnyu.mvc01;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cn.johnyu.mvc01.service.DemoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class,RootConfig.class})
@WebAppConfiguration(value = "src/main/resources") //1
public class TestControllerIntegrationTests {
	private MockMvc mockMvc; //2

	@Autowired
	private DemoService demoService;//3

	@Autowired
	WebApplicationContext wac; //4

    @Autowired
    MockHttpSession session; //5

    @Autowired
    MockHttpServletRequest request; //6

    @Before //7
    public void setup() {
    	mockMvc =
    			MockMvcBuilders.webAppContextSetup(this.wac).build(); //2
    	}

	/**
	 * 测试逻辑视图和物理视图的返回
	 */
	@Test
	public void testViewController() throws Exception{
		mockMvc.perform(get("/suc")) //向/app/suc发get请求
				.andExpect(status().isOk())//响应字"ok"
				.andExpect(status().is2xxSuccessful())//响应码"2xx"
				.andExpect(view().name("suc"))//视图名"suc"
				.andExpect(forwardedUrl("/WEB-INF/classes/views/suc.jsp"));//转向位置
//				.andExpect(model().attribute("msg", demoService.saySomething()));//12

	}

	/**
	 * 测试json数据的返回
	 */
	@Test
	public void testDemoController() throws Exception{
		ObjectMapper mapper=new ObjectMapper();
		String data=mapper.writer().writeValueAsString(demoService.findAllCafe());
    	mockMvc.perform(get("/cafe"))
				.andExpect(content().contentType("application/json"))
				.andExpect(content().string(data));
	}

	/**
	 * 测试返回ModelAndView的情况
	 */
	@Test
	public void testDemoController1() throws Exception{
		mockMvc.perform(get("/cafe/12"))
				.andExpect(model().attribute("name","latte"))
				.andExpect(view().name("suc"));
	}
}
