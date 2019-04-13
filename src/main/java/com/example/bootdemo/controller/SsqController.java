package com.example.bootdemo.controller;

import com.example.bootdemo.utils.function.DubbleChomosphere;
import com.example.bootdemo.utils.function.concurrent.ConcurrentParams;
import com.example.bootdemo.utils.function.concurrent.LimitControllerAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description
 * @Author panpan
 * @Date 2019-04-10 12:50
 * @Version 1.0
 **/
@LimitControllerAnnotation(SsqController.class)
@Controller
@RequestMapping(value = "ssq", produces = {"text/html;charset=UTF-8"})
public class SsqController extends BaseController {

    @ConcurrentParams(timer = 10, count = 10)
    @RequestMapping("randomBall")
    public void getRandomBall(Integer init, HttpServletResponse response, HttpServletRequest request) {
        boolean flush = false;
        if (init != null && init == 1) {
            flush = true;
        }
        DubbleChomosphere.initSSQData(flush);
        DubbleChomosphere.randomMethod2Out();
        String result = createResultString(DubbleChomosphere.lastBalls, DubbleChomosphere.random);
        response(response, result);
    }

    /**
     * 封装返回结果
     * @param lastBalls
     * @param random
     * @return
     */
    private String createResultString(List<Byte[]> lastBalls, List<Byte[]> random) {
        StringBuffer sb = new StringBuffer();
        sb.append("random " + DubbleChomosphere.randomCount + " set:\n");
        sb.append(displayBall(random));
        sb.append("\nlately " + DubbleChomosphere.lastCount + " set:\n");
        sb.append(displayBall(lastBalls));
        return sb.toString();
    }

    private String displayBall(List<Byte[]> balls) {
        StringBuffer sb = new StringBuffer();
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            for (Byte b : balls.get(i)) {
                sb.append(b).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
