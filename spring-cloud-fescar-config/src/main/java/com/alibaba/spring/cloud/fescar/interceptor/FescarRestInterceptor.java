package com.alibaba.spring.cloud.fescar.interceptor;

import com.alibaba.fescar.core.context.RootContext;
import com.alibaba.spring.cloud.fescar.config.FescarAutoConfiguration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

/**
 * @author XCXCXCXCX
 * @since 1.0
 */

public class FescarRestInterceptor implements RequestInterceptor{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FescarRestInterceptor.class);
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String xid = RootContext.getXID();
        LOGGER.info("set header 1111xxxxxxxxxID:"+xid);
        if(!StringUtils.isEmpty(xid)){
            requestTemplate.header(FescarAutoConfiguration.FESCAR_XID, xid);
        }
        requestTemplate.header("test123","12121");

    }

}
