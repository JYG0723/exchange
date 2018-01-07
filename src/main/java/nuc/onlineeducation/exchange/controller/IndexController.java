package nuc.onlineeducation.exchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ji YongGuang.
 * @date 22:25 2018/1/5.
 */
@Controller
@RestController
public class IndexController {

    @GetMapping(value = {"/", "index"})
    public String index() {
        return "exchange index~";
    }

    @GetMapping(value = "/profile/{id}")
    public String profile(@PathVariable(value = "id") Integer userId) {
        return new String("profile id:" + userId);
    }

    @GetMapping(value = "/admin")
    public String admin(@RequestParam("key") String key) throws Exception {
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new Exception("参数不对");
    }

    @ExceptionHandler
    public String error(Exception e) {// 统一异常页面
        return "error:" + e.getMessage();
    }
}
