package com.wak.tx.msg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wak.entities.Msg;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/12/11 16:19
 * @Description TODO
 * @Version 1.0
 */
public interface MsgMapper extends Mapper<Msg> {
    /**
     * 更新状态根据消息id
     *
     * @param updatedStatus 更新状态
     * @param msgId
     * @return int
     */
    int updateStatusByMsgId(@Param("updatedStatus") Byte updatedStatus, @Param("msgId") String msgId);

    /**
     * 找到一个根据消息id
     *
     * @param msgId 消息id
     * @return {@code Msg}
     */
    Msg findOneByMsgId(@Param("msgId") String msgId);

    /**
     * 删除根据消息id
     *
     * @param msgId 消息id
     * @return int
     */
    int deleteByMsgId(@Param("msgId") String msgId);

    /**
     * 找到所有根据状态
     *
     * @param status 状态
     * @return {@code List<Msg>}
     */
    List<Msg> findAllByStatus(@Param("status") Byte status);

    /**
     * 选择过期消息
     *
     * @param status 状态
     * @param time   时间
     * @return {@code List<Msg>}
     */
    List<Msg> selectOverdueMsg(@Param("status")Byte status, @Param("time") Integer time);

    /**
     * 更新删除根据消息id
     *
     * @param updatedDeleted 更新删除
     * @param msgId          消息id
     * @return int
     */
    int updateDeletedByMsgId(@Param("updatedDeleted")Byte updatedDeleted,@Param("msgId")String msgId);

}