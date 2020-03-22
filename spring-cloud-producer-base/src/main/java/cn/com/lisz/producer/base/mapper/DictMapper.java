package cn.com.lisz.producer.base.mapper;

import cn.com.lisz.common.mapper.IBaseMapper;
import cn.com.lisz.producer.base.model.Dict;
import cn.com.lisz.producer.base.model.DictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictMapper extends IBaseMapper<Dict, Long> {
    long countByExample(DictExample example);

    int deleteByExample(DictExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(Dict record);

    List<Dict> selectByExample(DictExample example);

    Dict selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Dict record, @Param("example") DictExample example);

    int updateByExample(@Param("record") Dict record, @Param("example") DictExample example);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}