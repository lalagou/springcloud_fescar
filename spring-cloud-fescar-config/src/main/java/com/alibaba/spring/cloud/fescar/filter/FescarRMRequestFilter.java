package com.alibaba.spring.cloud.fescar.filter;

import com.alibaba.fescar.core.context.RootContext;
import com.alibaba.spring.cloud.fescar.config.FescarAutoConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XCXCXCXCX
 * @since 1.0
 */
public class FescarRMRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FescarRMRequestFilter.class);

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currentXID = request.getHeader(FescarAutoConfiguration.FESCAR_XID);
        String xid = RootContext.getXID();
        LOGGER.info("get ----------currentXID:" + currentXID);
        LOGGER.info("12121uuuuu"+request.getHeader("test123"));
        LOGGER.info("xid"+xid);
        String restXid = request.getHeader(FescarAutoConfiguration.FESCAR_XID);
        boolean bind = false;
        if(StringUtils.isBlank(xid)&&StringUtils.isNotBlank(restXid)){
            RootContext.bind(restXid);
            bind = true;
        }
        try{
            filterChain.doFilter(request, response);
        } finally {
            if (bind) {
                RootContext.unbind();
            }
        }
    }
}
