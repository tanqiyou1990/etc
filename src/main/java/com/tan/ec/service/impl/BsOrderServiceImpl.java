package com.tan.ec.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsActivePerson;
import com.tan.ec.entity.BsOrder;
import com.tan.ec.entity.BsPostalList;
import com.tan.ec.mapper.BsOrderMapper;
import com.tan.ec.service.IBsActivePersonService;
import com.tan.ec.service.IBsActivitiesService;
import com.tan.ec.service.IBsOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tan.ec.service.IBsPostalListService;
import com.tan.ec.utils.IdUtil;
import com.tan.ec.utils.OrderCodeUtil;
import com.tan.ec.vo.CreateActiveOrderVo;
import com.tan.ec.vo.CreateOrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
@Service
public class BsOrderServiceImpl extends ServiceImpl<BsOrderMapper, BsOrder> implements IBsOrderService {

    @Autowired
    private IBsPostalListService bsPostalListService;

    @Autowired
    private IBsActivePersonService bsActivePersonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createByVo(CreateOrderVo createOrderVo) {

        BsOrder order = new BsOrder();
        BeanUtils.copyProperties(createOrderVo,order);
        order.setId(IdUtil.generateUuid());

        if(createOrderVo.getCreatePostalInfoVo() == null){
            throw new RuntimeException("缺少收货人信息");
        }

        BsPostalList p = new BsPostalList();
        BeanUtils.copyProperties(createOrderVo.getCreatePostalInfoVo(),p);
        p.setBatchNo(createOrderVo.getBatchNo());
        p.setId(IdUtil.generateUuid());
        bsPostalListService.insert(p);

        return baseMapper.insert(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createByVo(CreateActiveOrderVo createActiveOrderVo) {
        BsOrder order = new BsOrder();
        BeanUtils.copyProperties(createActiveOrderVo,order);
        order.setId(IdUtil.generateUuid());
        if(createActiveOrderVo.getActivePersonVo() == null){
            throw new RuntimeException("缺少联系人信息");
        }

        BsActivePerson activePerson = new BsActivePerson();
        BeanUtils.copyProperties(createActiveOrderVo.getActivePersonVo(),activePerson);
        activePerson.setBatchNo(createActiveOrderVo.getBatchNo());
        activePerson.setId(IdUtil.generateUuid());
        bsActivePersonService.insert(activePerson);

        return baseMapper.insert(order);
    }

    @Override
    public Integer updateByVo(CreateOrderVo createOrderVo) {
        BsOrder order = new BsOrder();
        BeanUtils.copyProperties(createOrderVo,order);
        return baseMapper.updateById(order);
    }

    @Override
    public Page selectOrderPostal(Page page) {
        return page.setRecords(baseMapper.selectOrderPostal(page));
    }

    @Override
    public Page selectOrderActive(Page page) {
        return page.setRecords(baseMapper.selectOrderActive(page));
    }

    @Override
    public synchronized Integer payflag(String oid) {
        return baseMapper.payflag(oid);
    }
}
