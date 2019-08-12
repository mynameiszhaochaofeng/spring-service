package com.zcf.springservice.controller;

import com.zcf.springservice.dao.UserMapper;
import com.zcf.springservice.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private UserMapper userMapper;

    /**
     * ///方法说明: 普通查询
     * ///示例请求：http://localhost:8091/common/queryAllUser
     * @return
     */
    @RequestMapping(value="/queryAllUser",method = {RequestMethod.GET,RequestMethod.POST})
    public List<User> queryAllUser() {
        User user = new User();
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        return userList;
    }

    /**
     * 方法说明：url参数的使用-查询字符串 ,且name必填
     * 示例请求：http://localhost:8091/common/queryUserByName?name=zhaochaofeng
     * @param name
     * @return
     */
    @RequestMapping(value="/queryUserByName",method = {RequestMethod.GET,RequestMethod.POST})
    public User queryUserByName(@RequestParam(value="name",required = true) String name) {
        User user = userMapper.selectByUserName(name);
        return user;
    }

    /**
     * 方法说明：添加数据
     *  1:使用json数据提交，直接使用实体对象接收
     *  2:hibernate 添加数据
     * 请求参数： {"id":1,"userId":1,"pwd":"123","name":"123","pwd":"123","headPortait":"123","isEnable":"123","createDate":"2015-05-12","lastLogin":"2015-05-12"}
     * 请求头：Content-Type : application/json
     * 请求ur：http://localhost:8091/common/addUser
     * @param user
     * @return
     */
    @RequestMapping(value="/addUser",method = {RequestMethod.GET,RequestMethod.POST})
    public User addUser(@RequestBody User user) {
        userMapper.insert(user);
        return user;
    }

    /**
     * ///方法说明：url参数的使用
     * ///示例请求：http://localhost:8091/common/queryUserById/2
     * 高级用法：路径参数可以自由设置自己的规则，比如，你有个请求需要两个参数，月和日，你可以写成{month}-{day}
     * @param id
     * @return
     */
    @RequestMapping(value="/queryUserById/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public User queryUserById(@PathVariable("id") int id) {
        User user = userMapper.selectByPrimaryKey(String.valueOf(id));
        return user;
    }

    /**
     * 方法说明：使用表单方式提交数据
     * 请求参数:isEnable=1&name=cool
     * 配置
     * 请求头：Content-Type : application/x-www-form-urlencoded
     * 请求url：http://localhost:8091/demo/findUsersByName
     * */
    @RequestMapping(value="/findUsersByName",method = RequestMethod.POST)
    @ResponseBody
    public void findUsersByName(boolean isEnable , String name)
    {
        System.out.println(isEnable);
        System.out.println(name);
    }

    @RequestMapping("upload")
    public String upload(HttpServletRequest request,
                       HttpServletResponse response, @RequestParam("file") MultipartFile file){
        try {
            //MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            System.out.println(file);
            System.out.println(request);
            return "文件上传成功";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "文件上传失败";
    }


    @RequestMapping("batchUpload")
    public String batchUpload(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("files") MultipartFile[] files) throws IOException {
        try {
            System.out.println(request);
            for (MultipartFile file :files) {
                System.out.println(file);
            }
            return "文件上传成功";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  "文件上传失败";
    }
}
