package com.shell.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Round Robin：轮询法，轮询调度算法的原理是每一次把来自用户的请求轮流分配
 * 给内部服务器，从1开始，知道N（内部服务器个数），然后重新开始循环。
 * 优点：无需记录当前所有连接的状态，所以它是一种无状态调度
 * @author xieyu
 * @date 2017年03月12日 16:56:28
 * @version
 * @see
 */
public class RoundRobin implements LoadBalance {
	
	private static Integer pos = 0;
	
	/**
	 * 由于服务器的地址列表是动态的，随时可能有机器上线、下线或者宕机，
	 * 因此为了避免可能出现的并发问题，方法内部要新建局部变量serverMap，
	 * 先将已有的服务器地址信息的内容复制到线程本地，以避免被多个线程修改。
	 * 这样可能会引入新的问题，复制以后服务器动态的修改无法反映给serverMap，
	 * 也就是说这一轮选择服务器的过程中，新增服务器或者下线服务器，负载均衡算法将无法获知。
	 * 新增无所谓，如果有服务器下线或者宕机，那么可能会访问到不存在的地址。
	 * 因此，服务调用端需要有相应的容错处理，比如重新发起一次server选择并调用。<br>
	 * 对于当前轮询的位置变量pos，为了保证服务器选择的顺序性，需要在操作时对其加锁，
	 * 使得同一时刻只能有一个线程可以修改pos的值，否则当pos变量被并发修改，则无法保证服务器选择的顺序性，
	 * 甚至有可能导致keyList数组越界。<br>
	 * 轮询法的优点在于：试图做到请求转移的绝对均衡。
	 * 轮询法的缺点在于：为了做到请求转移的绝对均衡，必须付出相当大的代价，因为为了保证pos变量修改的互斥性，
	 * 需要引入重量级的悲观锁synchronized，这将会导致该段轮询代码的并发吞吐量发生明显的下降。
	 */
	@Override
	public String getServer() {
		
		// 重建一个Map，避免服务器的上下线导致并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(IpMap.serverWeightMap);
		
		Set<String> keySet = serverMap.keySet();
		List<String> keyList = new ArrayList<>();
		keyList.addAll(keySet);
		
		String server = null;
		synchronized(pos) {
			if (pos > keySet.size()) {
				pos = 0;
			}
			
			server = keyList.get(pos);
			pos ++;
		}
		
		return server;
	}
}
