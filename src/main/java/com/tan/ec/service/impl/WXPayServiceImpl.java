package com.tan.ec.service.impl;

import com.tan.ec.service.WXPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class WXPayServiceImpl implements WXPayService {
    //100
    private final BigDecimal HUNDRED = new BigDecimal("100");

    /**
     * 处理订单回调
     * @param map
     */
    @Override
    public void doOrderNotify(Map<String, String> map) {
        log.debug("处理订单回调-----------start");
        //平台订单流水号
        String outTradeNo = map.get("out_trade_no");
        //total_fee(单位:分)
        BigDecimal totalFee = new BigDecimal(map.get("total_fee")).divide(HUNDRED);
        //微信支付订单号
        String transactionId = map.get("transaction_id");
//        BsProductOrder order = bsProductOrderMapper.selectInfoBySerialNumber(outTradeNo);
//        if (order == null){
//            log.info("---------------------------------订单已被处理");
//            return;
//        }
//        //订单金额
//        BigDecimal amount = order.getAmount();
//        if (amount.compareTo(totalFee) != 0) {
//            log.info("支付金额与订单金额不匹配,订单号:{}", outTradeNo);
//            return;
//        }
//        //保存流水记录
//        BsProductOrderRecord bor = new BsProductOrderRecord();
//        bor.setAmount(totalFee);
//        bor.setPayWay(2);
//        bor.setUid(order.getCreateBy());
//        bor.setSerialNumber(outTradeNo);
//        bor.setTradeNo(transactionId);
//        Date timeEnd = DateUtil.parseStrToDate(map.get("time_end"), DateUtil.DATE_TIME_FORMAT_YYYYMMDDHHMISS, null);
//        bor.setGmtPayment(timeEnd);
//
//        if(order.getIsRenew().equals("N")){
//            bsProductOrderRecordService.insertRecordAndMasterNode(bor);
//        }else {
//            bsProductOrderRecordService.insertRecordAndUpdateNode(bor);
//        }
        log.debug("处理订单回调-----------end");
    }

    /**
     * 处理充值回调
     * @param map
     */
    @Override
    public void doRechargeNotify(Map<String, String> map) {
        log.debug("处理充值订单回调-----------start");
        //平台订单流水号
        String outTradeNo = map.get("out_trade_no");
        //total_fee(单位:分)
        BigDecimal totalFee = new BigDecimal(map.get("total_fee")).divide(HUNDRED);
        //微信支付订单号
        String transactionId = map.get("transaction_id");
//        BsRecharge order = bsRechargeMapper.selectBySerialNumber(outTradeNo);
//        if (order == null){
//            log.info("---------------------------------订单已被处理");
//            return;
//        }
//        //订单金额
//        BigDecimal amount = order.getAmount();
//        if (amount.compareTo(totalFee) != 0) {
//            log.info("支付金额与订单金额不匹配,订单号:{}", outTradeNo);
//            return;
//        }
//        //保存流水记录
//        BsRechargeRecord bor = new BsRechargeRecord();
//        bor.setAmount(totalFee);
//        bor.setRechargeWay(2);
//        bor.setUid(order.getCreateBy());
//        bor.setSerialNumber(outTradeNo);
//        bor.setTradeNo(transactionId);
//        Date timeEnd = DateUtil.parseStrToDate(map.get("time_end"), DateUtil.DATE_TIME_FORMAT_YYYYMMDDHHMISS, null);
//        bor.setGmtPayment(timeEnd);
//        bsRechargeRecordService.insertRecordAndRechargeOrder(bor);
        log.debug("处理充值订单回调-----------end");
    }
}
