# 着迷本地云计算平台
## 简介
工程目前暂时包含下面几部分：（后续还会增加...）
*   服务注册与统一配置，二者合二为一
*   路由网管
*   用户中心服务
*   通用工具类模块

## 本地开发
每个模块都是独立的Spring Boot 应用，可以独立开发部署，在Idea 启动执行类 或 mvn 启动。系统默认启动安全认证访问，本地可以注释掉安全部分注解；如果不需要集成联调，可以注释掉服务注册注解。
* 服务注册与统一配置服务，最好通过mvn执行。默认访问路径-- http://localhost:8761
* 路由网关提供路由转发，访问控制，负载均衡。   默认访问路径 -- http://localhost:8080
* 通用工具类模块提供公用的工具包，需要发布到本地仓库
* 用户中心服务提供用户注册、登陆、个人信息维护等相关服务 。  默认访问路径 -- http://localhost:8081
