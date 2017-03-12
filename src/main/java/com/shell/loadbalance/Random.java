package com.shell.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Random法：通过系统的随机算法，根据后端服务器的列表大小值来随机选取其中的一台服务器进行访问。
 * 有概率统一论可以得知，随着客户端调用服务器的次数增多，
 * 其实际效果越来越接近平均分配调用量到后端的每一台服务器，也就是轮询的结果
 * @author xieyu
 * @date 2017年03月12日 17:12:23
 * @version
 * @see
 */
public class Random implements LoadBalance {

	@Override
	public String getServer() {
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(serverMap);
		
		Set<String> keySet = serverMap.keySet();
		List<String> keyList = new ArrayList<>();
		keyList.addAll(keySet);
		
		java.util.Random random = new java.util.Random();
		int randomPos = random.nextInt(keyList.size());
		
		return keyList.get(randomPos);
	}

}
