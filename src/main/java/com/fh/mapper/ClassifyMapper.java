package com.fh.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Classify;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClassifyMapper extends BaseMapper<Classify> {
}
