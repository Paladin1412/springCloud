package com.lv.cloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    //如果直接用 @Autowired中无法注入RestTemplate，那么手动注入
    @Bean
    @LoadBalanced  //ribbon服务调用，可以通过 注册进eureka中并对外暴露微服务名字(大小写敏感)，查找到，并调用
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    /**
     * 自己设置算法规则
     *
     RoundRobinRule  轮询（个人认为：默认调用服务的算法）

     RandomRule	随机

     AvailabilityFilteringRule
     会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，
     还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问

     WeightedResponseTimeRule
     根据平均响应时间计算所有服务的权重，响应时间越快服务权重越大被选中的概率越高。
     刚启动时如果统计信息不足，则使用RoundRobinRule策略，等统计信息足够，
     会切换到WeightedResponseTimeRule

     RetryRule
     先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务

     BestAvailableRule
     会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务

     ZoneAvoidanceRule
     默认规则,复合判断server所在区域的性能和server的可用性选择服务器
     个人认为：默认算法，在 eureka server拉去服务实例列表
     *
     * @Title: myIRule
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @return
     */
    //@Bean
    public IRule myIRule(){
        return new RandomRule();//用重新选择的随机算法替换默认的轮询算法
    }
}
