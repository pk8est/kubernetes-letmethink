package com.pkest.repo.mapper;

import com.pkest.lib.myibatis.HYBaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@NoRepositoryBean
public interface BaseMapper<T, K> extends HYBaseRepository<T, K> {

}
