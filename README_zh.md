**其他语言版本: [English](README.md), [中文](README_zh.md).**
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
