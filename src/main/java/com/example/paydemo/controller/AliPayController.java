package com.example.paydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.paydemo.config.AliPayConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/alipay")
public class AliPayController {

    @Resource
    private AliPayConfig aliPayConfig;

    @GetMapping("pay")
    public String pay(String orderId, HttpServletResponse response) {
        System.out.println(6666);
        try {
            // 配置支付宝客户端
            AlipayClient alipayClient = new DefaultAlipayClient(
                    aliPayConfig.getGatewayUrl(),
                    aliPayConfig.getAppId(),
                    aliPayConfig.getAppPrivateKey(),
                    "json",
                    "UTF-8",
                    aliPayConfig.getAlipayPublicKey(),
                    "RSA2");

            // 创建API请求对象
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setNotifyUrl(aliPayConfig.getNotifyUrl());

            // 构建请求参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderId);//我们自己生成的订单id
            bizContent.put("total_amount", "88.88"); // 订单的总金额
            bizContent.put("subject", "测试商品");//支付的名称
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");//固定配置
            request.setBizContent(bizContent.toString());
            request.setReturnUrl("http://27.157.144.127:9200/api/hello/hello");//支付成功后跳转的地址
            // 调用SDK生成表单
            return alipayClient.pageExecute(request).getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return "调用支付宝失败";
        }
    }

    @PostMapping("notify")
    public String payNotify(HttpServletRequest request) throws AlipayApiException {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            // 支付宝验签
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    aliPayConfig.getAlipayPublicKey(),
                    "UTF-8",
                    "RSA2"
            );
            if (signVerified) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                // 更新订单状态
            }
        }
        return "success";
    }


}
