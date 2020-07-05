package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    //如果当前有事务，则使用事务；如果当前没有事务，则不使用事务。
    //举例：领导没饭吃，我也没饭吃；领导有饭吃，我也有饭吃。
    //Not_SUPPORTS:如果当前有事务，则把事务挂起，自己不使用事务去运行数据库操作
    //Never:如果当前有事务存在，则抛出异常
    //NESTED:如果当前有事务，则开启子事务（嵌套事务），嵌套事务是独立提交或者回滚；
    //如果当前没有事务，则同REQUIRED;
    //但是如果住事务提交，则会携带子事务一起提交；
    //如果主事务回滚，则子事务一起回滚。相反，子事务异常，则父事务可以回滚或不回滚。
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    //使用当前的事务，如果当前没有事务，则自己新建一个事务，子方法是必须运行在一个事务中的；
    //如果当前存在事务，则加入到这个事务中，成为一个整体
    //默认(增删改)；举例：领导没饭吃，自己有钱，自己买了吃；领导有的吃，会分给自己吃。
    //REQUIRED_New:如果有事务，则挂起该事务，并且自己创建一个新的事务给自己使用；如果当前没有事务，则同REQUIRED
    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setAge(19);
        stu.setName("Lucy");
        stuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setId(id);
        stu.setAge(19);
        stu.setName("Chuck");
        stuMapper.updateByPrimaryKey(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }
}
