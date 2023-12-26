## Language

- [中文](#中文)
- [English](#english)

---
### 中文
# Sundial

Sundial是一个基于Spring Schedule、Zookeeper和VUE实现的分布式定时任务工具，详细介绍见我的博客：https://blog.csdn.net/jingzuangod/article/details/122676662?spm=1001.2014.3001.5502

## 主要特性

- 基于Spring Schedule，使用方便，与Spring框架集成良好。
- 使用Zookeeper作为协调服务，保证任务的高可用和分布式一致性。
- 可在页面里动态添加、修改和删除任务，也可控制任务的启用和暂停，无需重启服务，即时生效。

## 技术栈

- Java
- zookeeper
- VUE
- MySQL
- Spring Boot
- Mybatis

## 如何开始

1. 克隆此仓库到本地
2. 使用IntelliJ IDEA打开项目
3. 确保本地已安装Maven、Java、MySQL、zookeeper环境
4. 往MySQL中存入的定时任务名称必须与源码中task注解的name属性一致
5. 运行`mvn install`命令安装依赖
6. 运行项目

## 贡献

欢迎任何形式的贡献，包括但不限于提交问题、提出改进建议、或直接提交代码。

## 许可证

此项目遵循MIT许可证。

## 联系方式

如果您有任何问题或建议，欢迎联系我。

---
### English
# Sundial
Sundial is a distributed timing task tool based on Spring Schedule, Zookeeper, and VUE. For a detailed introduction, see my blog: https://blog.csdn.net/jingzuangod/article/details/122676662?spm=1001.2014.3001.5502  
## Main Features
- Based on Spring Schedule, easy to use, and well integrated with the Spring framework.
- Uses Zookeeper as a coordination service to ensure high availability and distributed consistency of tasks.
- Tasks can be dynamically added, modified, and deleted on the page, and the enablement and suspension of tasks can be controlled. No need to restart the service, it takes effect immediately.
## Technology Stack
- Java
- Zookeeper
- VUE
- MySQL
- Spring Boot
- Mybatis
## How to Start
1. Clone this repository to local
2. Open the project with IntelliJ IDEA
3. Make sure Maven, Java, MySQL, and Zookeeper environments are installed locally
4. The timing task name stored in MySQL must be consistent with the name attribute of the task annotation in the source code
5. Run the mvn install command to install dependencies
6. Run the project
## Contribution
Welcome any form of contribution, including but not limited to submitting issues, proposing improvements, or directly submitting code.  
## License
This project follows the MIT license.  
## Contact
If you have any questions or suggestions, feel free to contact me
