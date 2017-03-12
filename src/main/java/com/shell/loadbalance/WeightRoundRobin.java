package com.shell.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * WeightRoundRobin：加权轮询法，不同的后端服务器可能机器的配置和当前系统的负载并不相同，因此他们的抗压能力也不相同。
 * 给配置高、负载低的机器配置更高的权重，让其处理更过的请求。而配置低、负载高的机器，给其分配较低的权重，降低其系统负载，
 * 加权轮询能很好地处理这一问题，并将请求顺序且按照权重分配到后端
 * @author xieyu
 * @date 2017年03月12日 17:37:22
 * @version
 * @see
 */
public class WeightRoundRobin implements LoadBalance {
	
	private static Integer pos;
	
	@Override
	public String getServer() {
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(IpMap.serverWeightMap);
		
		Set<String> keySet = serverMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		
		List<String> serverList = new ArrayList<>();
		while(iterator.hasNext()) {
			String server = iterator.next();
			int weight = serverMap.get(server);
			
			for (int i = 0; i < weight; i ++) {
				serverList.add(server);
			}
		}
		
		String server = null;
		synchronized(pos) {
			if (pos > keySet.size()) {
				pos = 0;
			}
			server = serverList.get(pos);
			pos ++;
		}
		
		return server;
	}

}
