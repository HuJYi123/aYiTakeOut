package com.example.springboot_02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_02.mapper.AddressBookMapper;
import com.example.springboot_02.pojo.AddressBook;
import com.example.springboot_02.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * className:AddressBookServiceImpl
 * Package:com.example.springboot_02.service.impl
 * Description:一步一脚印！
 *
 * @Date: 2023/5/1 16:37
 * @Author:2692243932@qq.com
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService {
}
