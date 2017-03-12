package com.shell.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Weight Random：加权随机法，与加权轮询法一样，加权随机法也根据后端机器的配置，
 * 系统的负载分配不同的权重。不同的是，它是按照权重随机请求后端服务器，而非顺序。
 * @author xieyu
 * @date 2017年03月12日 17:45:58
 * @version
 * @see
 */
public class WeightRandom implements LoadBalance {
	
	public String getServer()
    {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =
                new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);
 
        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        Iterator<String> iterator = keySet.iterator();
 
        List<String> serverList = new ArrayList<String>();
        while (iterator.hasNext())
        {
            String server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++)
                serverList.add(server);
        }
 
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(serverList.size());
 
        return serverList.get(randomPos);
    }
}
