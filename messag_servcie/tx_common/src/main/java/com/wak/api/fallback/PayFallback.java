package com.wak.api.fallback;

import com.wak.api.PayApi;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2023/12/12 11:33
 * @Description TODO
 * @Version 1.0
 */
@Component
public class PayFallback implements PayApi {
    @Override
    public boolean checkMsg(String msgId) {
        return false;
    }
}
