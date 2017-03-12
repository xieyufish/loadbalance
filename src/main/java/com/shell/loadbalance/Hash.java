package com.shell.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hash：源地址哈希的思想是根据获取客户端的IP地址，通过哈希函数计算得到的一个数值，
 * 用该数值对服务器列表的大小进行取模运算，得到的结果便是客户端要访问的服务器的序号。
 * 采用源地址哈希法进行负载均衡，同一IP地址的客户端，当后端服务器列表不变时，
 * 它每次都会映射到同一台后端服务器进行访问
 * @author xieyu
 * @date 2017年03月12日 17:19:46
 * @version
 * @see
 */
public class Hash implements LoadBalance {
	
	/**
	 * 源地址哈希法的优点：保证了相同客户端IP地址将会被哈希到同一台后端服务器，知道后端服务器列表变更，
	 * 根据此特性可以在服务消费者与服务提供者之间建立有状态的session会话。<br>
	 * 缺点：一旦有服务器上下线，那么通过源地址哈希算法路由到的服务器是服务器上下线之前路由到的服务器的概率非常低，
	 * 如果是session则取不到session，如果是缓存可能引发“雪崩”
	 */
	@Override
	public String getServer() {
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(IpMap.serverWeightMap);
		
		Set<String> keySet = serverMap.keySet();
		List<String> keyList = new ArrayList<>();
		keyList.addAll(keySet);
		
		String remoteIp = "127.0.0.1";	// 在web应用用可通过HttpServlet的getRemoteIp方法获取
		int hashCode = remoteIp.hashCode();
		int serverListSize = keyList.size();
		int serverPos = hashCode % serverListSize;
		
		return keyList.get(serverPos);
	}
	
}
