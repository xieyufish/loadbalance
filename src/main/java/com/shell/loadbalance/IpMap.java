package com.shell.loadbalance;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xieyu
 * @date 2017年03月12日 16:47:45
 * @version
 * @see
 * 模拟IP列表
 */
public class IpMap {
	
	// 待路由的Ip列表，key代表Ip，Value代表该Ip的权重
	public static Map<String, Integer> serverWeightMap = new HashMap<>();
	
	static {
		serverWeightMap.put("192.168.10.100", 1);
		serverWeightMap.put("192.168.10.101", 1);
		serverWeightMap.put("192.168.10.102", 4);
		serverWeightMap.put("192.168.10.103", 1);
		serverWeightMap.put("192.168.10.104", 1);
		serverWeightMap.put("192.168.10.105", 3);
		serverWeightMap.put("192.168.10.106", 1);
		serverWeightMap.put("192.168.10.107", 2);
		serverWeightMap.put("192.168.10.108", 1);
		serverWeightMap.put("192.168.10.109", 1);
		serverWeightMap.put("192.168.10.110", 1);
	}
}
