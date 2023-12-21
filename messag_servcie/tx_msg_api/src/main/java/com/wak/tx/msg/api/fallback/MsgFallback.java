package com.wak.tx.msg.api.fallback;

import com.wak.entities.MsgDTO;
import com.wak.tx.msg.api.MsgApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2023/12/12 11:33
 * @Description TODO
 * @Version 1.0
 */
@Component
@Slf4j
public class MsgFallback implements MsgApi {
    @Override
    public void halfMsg(MsgDTO msgDTO) {
        log.error("openfeign call halfMsg fail {}", msgDTO.getMsgId());
    }

    @Override
    public void confirmMsg(String msgId) {
        log.error("openfeign call confirmMsg fail {}", msgId);
    }

    @Override
    public void deleteMsg(String msgId) {
        log.error("openfeign call deleteMsg fail {}", msgId);
    }
}
