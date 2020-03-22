package com.example.gateway.web.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author tangyajun
 * @Description TO DO
 * @create 2020-03-21-19:14
 **/
public class CustomeGatewayFilter implements GatewayFilter, Ordered {
	private static final Log log= LogFactory.getLog(GatewayFilter.class);
	private static final String COUNT_Start_TIME="countStartTime";
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		exchange.getAttributes().put(COUNT_Start_TIME,System.currentTimeMillis());
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			Long startTime=exchange.getAttribute(COUNT_Start_TIME);
			Long endTime=(System.currentTimeMillis()-startTime);
			if (startTime != null) {
				log.info(exchange.getRequest().getURI().getRawPath()+": "+endTime+" ms");
			}
		}));
	}

	@Override
	public int getOrder() {

		return Ordered.LOWEST_PRECEDENCE;
	}
}
