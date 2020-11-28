package com.lixiongzi.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.dao.MemberDao;
import com.lixiongzi.health.dao.OrderDao;
import com.lixiongzi.health.dao.OrderSettingDao;
import com.lixiongzi.health.pojo.Member;
import com.lixiongzi.health.pojo.Order;
import com.lixiongzi.health.service.CheckItemService;
import com.lixiongzi.health.service.OrderService;
import com.lixiongzi.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Transactional
@Service(interfaceClass =OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public int submit(Map map) {
        Date date;
        try {
            date = DateUtils.parseString2Date((String) map.get("orderDate"), "yyyy-MM-dd");
            map.put("orderDate", date);
        } catch (Exception e) {
            throw new MyException("时间格式转化错误");
        }
        if (orderSettingDao.findSetmealSettingDate(date) == null) {
            //不在可以预约的时间内
            throw new MyException("预约日期不在可预约范围之内");
        }
        //在可以预约的时间内
        if (orderSettingDao.findSetmelSettingReservation(date) == null) {
            //日期的预约人数已经大于或等于可预约人数了
            throw new MyException("该预约日期的人数已满");
        }
        //预约人数可以满足要求,查看是否是会员
        if (memberDao.findMemberByTelephone((String) map.get("telephone")) == null) {
            //不是会员,那就注册,先new一个member,传入,插入即可
            Member member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setRegTime(new Date());
            memberDao.addNewMember(member);
        }
        Order order = new Order();
        //现在是会员,那根据信息去查是不是有同样的订单,根据memberId,orderDate,setmeal_id,这个地方直接New 一个order去查,别传两参数,不好处理
        Member member = memberDao.findMemberByTelephone((String) map.get("telephone"));
        order.setMemberId(member.getId());
        order.setSetmealId(Integer.valueOf((String) map.get("setmealId")));
        order.setOrderDate((Date) map.get("orderDate"));
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus((String) map.get("orderStatus"));
        if (orderDao.findSameOrder(order) != null) {
            //不为null那就抛出异常,有相同的订单
            throw new MyException("请勿重复预约");
        }
        //为null,那就说明,可以插入了,直接在两个表中插入即可
        orderDao.addOrder(order);
        if (orderSettingDao.updateReservation(date) == 0) {
            //预约已满,请选择其他时间
            throw new MyException("预约已满,请选择其他时间");
        }
        return order.getId();
    }
}
