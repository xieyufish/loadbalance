package com.shell.loadbalance;

/**
 * @author xieyu
 * @date 2017年03月12日 16:55:17
 * @version
 * @see
 * 负载均衡接口定义
 */
public interface LoadBalance {
	
	/**
	 * 获取访问服务器
	 * @author xieyu
	 * @date 2017年03月12日 16:55:55
	 * @version
	 * @return
	 */
	String getServer();
}
