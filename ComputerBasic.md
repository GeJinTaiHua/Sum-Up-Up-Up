## 💻计算机基础

  * [线程](#%E7%BA%BF%E7%A8%8B)
    * [进程、线程](#%E8%BF%9B%E7%A8%8B%E7%BA%BF%E7%A8%8B)
    * [线程同步的方法](#%E7%BA%BF%E7%A8%8B%E5%90%8C%E6%AD%A5%E7%9A%84%E6%96%B9%E6%B3%95)
    * [进程间的通信方式](#%E8%BF%9B%E7%A8%8B%E9%97%B4%E7%9A%84%E9%80%9A%E4%BF%A1%E6%96%B9%E5%BC%8F)
  * [锁](#%E9%94%81)
    * [死锁、活锁](#%E6%AD%BB%E9%94%81%E6%B4%BB%E9%94%81)
    * [公平锁、非公平锁](#%E5%85%AC%E5%B9%B3%E9%94%81%E9%9D%9E%E5%85%AC%E5%B9%B3%E9%94%81)
    * [独享锁、共享锁](#%E7%8B%AC%E4%BA%AB%E9%94%81%E5%85%B1%E4%BA%AB%E9%94%81)
    * [可重入锁（递归锁）](#%E5%8F%AF%E9%87%8D%E5%85%A5%E9%94%81%E9%80%92%E5%BD%92%E9%94%81)
    * [分段锁](#%E5%88%86%E6%AE%B5%E9%94%81)
    * [自旋锁](#%E8%87%AA%E6%97%8B%E9%94%81)
    * [分布式锁](#%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81)
    * [锁的状态：偏向锁、轻量级锁、重量级锁](#%E9%94%81%E7%9A%84%E7%8A%B6%E6%80%81%E5%81%8F%E5%90%91%E9%94%81%E8%BD%BB%E9%87%8F%E7%BA%A7%E9%94%81%E9%87%8D%E9%87%8F%E7%BA%A7%E9%94%81)
    * [看待并发同步的角度：乐观锁、悲观锁](#%E7%9C%8B%E5%BE%85%E5%B9%B6%E5%8F%91%E5%90%8C%E6%AD%A5%E7%9A%84%E8%A7%92%E5%BA%A6%E4%B9%90%E8%A7%82%E9%94%81%E6%82%B2%E8%A7%82%E9%94%81)
  * [网路](#%E7%BD%91%E8%B7%AF)
    * [OSI 七层和 TCP/IP 四层](#osi-%E4%B8%83%E5%B1%82%E5%92%8C-tcpip-%E5%9B%9B%E5%B1%82)
    * [TCP 三次握手和四次挥手](#tcp-%E4%B8%89%E6%AC%A1%E6%8F%A1%E6%89%8B%E5%92%8C%E5%9B%9B%E6%AC%A1%E6%8C%A5%E6%89%8B)
    * [GET、POST](#getpost)
    * [Session、Cookie](#sessioncookie)
    * [分布式 session](#%E5%88%86%E5%B8%83%E5%BC%8F-session)
    * [从点击url到显示网页发生了什么](#%E4%BB%8E%E7%82%B9%E5%87%BBurl%E5%88%B0%E6%98%BE%E7%A4%BA%E7%BD%91%E9%A1%B5%E5%8F%91%E7%94%9F%E4%BA%86%E4%BB%80%E4%B9%88)
  * [内存](#%E5%86%85%E5%AD%98)
    * [内存溢出（out of memory）](#%E5%86%85%E5%AD%98%E6%BA%A2%E5%87%BAout-of-memory)
    * [内存泄漏（memory leak）](#%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8Fmemory-leak)
  * [攻击](#%E6%94%BB%E5%87%BB)
    * [重放攻击](#%E9%87%8D%E6%94%BE%E6%94%BB%E5%87%BB)
    * [文件上传漏洞攻击](#%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E6%BC%8F%E6%B4%9E%E6%94%BB%E5%87%BB)
  * [命令](#命令)
    * [Crontab表达式](#crontab%E8%A1%A8%E8%BE%BE%E5%BC%8F)
    * [Shell命令](#shell%E5%91%BD%E4%BB%A4)

### 线程  
#### 进程、线程
+ 线程的所有状态  
  + New (新创建）
  + Runnable (可运行）
  + Blocked (被阻塞）
  + Waiting (等待）
  + Timed waiting (计时等待）
  + Terminated (被终止）   
![线程的所有状态](http://mmbiz.qpic.cn/mmbiz_png/Bf4u9qKuXWupl2hClEIRRTBPpWAic4GicZDMAFRRWUIU5qYOYY9Ds9NTrI8GdwGZkOjPkgGZa234kCT7050dDk3g/640?wx_fmt=png&wxfrom=5&wx_lazy=1)  
+ 进程与线程的区别：
  - 线程是进程的子集，一个进程可以有很多线程，每条线程并行执行不同的任务；
  - 不同的进程使用不同的内存空间，而所有的线程共享一片相同的内存空间；
  - 进程间通讯依靠IPC资源，例如管道（pipes）、套接字（sockets）等；
  - 线程间通讯依靠JVM提供的API，例如wait方法、notify方法和notifyAll方法，线程间还可以通过共享的主内存来进行值的传递。
+ 纤程（Green Thread）：代指 Sun 公司的 Green Team，他们在20世纪90年代设计了最初的 Java 线程库。今天的 Java 不再使用纤程，早在2000年就已经开始使用本地线程了。一些其他编程语言，比如 Go、Haskell、Rust 等实现了类似纤程的机制代替本地线程。
  
#### 线程同步的方法
+ 方法
  + wait()：等待状态，释放所持有对象的lock；
  + sleep()：睡眠状态，静态方法；
  + notify()：唤醒一个等待状态的线程；
  + Allnotify()：唤醒所有等待状态的线程，竞争。

#### 进程间的通信方式
1) 管道(pipe)：管道是一种半双工的通信方式，数据只能单向流动，而且只能在具有亲缘关系的进程间使用。进程的亲缘关系通常是指父子进程关系。
2) 有名管道(named pipe)：有名管道也是半双工的通信方式，但是它允许无亲缘关系进程间的通信。
3) 信号量(semophore)：信号量是一个计数器，可以用来控制多个进程对共享资源的访问。它常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。因此，主要作为进程间以及同一进程内不同线程之间的同步手段。
4) 消息队列(message queue)：消息队列是由消息的链表，存放在内核中并由消息队列标识符标识。消息队列克服了信号传递信息少、管道只能承载无格式字节流以及缓冲区大小受限等缺点。
5) 信号(sinal)：信号是一种比较复杂的通信方式，用于通知接收进程某个事件已经发生。
6) 共享内存(shared memory)：共享内存就是映射一段能被其他进程所访问的内存，这段共享内存由一个进程创建，但多个进程都可以访问。共享内存是最快的 IPC 方式，它是针对其他进程间通信方式运行效率低而专门设计的。它往往与其他通信机制，如信号量，配合使用，来实现进程间的同步和通信。
7) 套接字(socket)：套解口也是一种进程间通信机制，与其他通信机制不同的是，它可用于不同机器间的进程通信。
 
### 锁
#### 死锁、活锁
+ 死锁
  + 互斥条件：一个资源每次只能被一个进程使用。
  + 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
  + 不剥夺条件：进程已获得的资源，在末使用完之前，不能强行剥夺。
  + 循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系。
+ 活锁：任务或者执行者没有被阻塞，由于某些条件没有满足，导致一直重复尝试—失败—尝试—失败的过程。

#### 公平锁、非公平锁
+ 公平锁：多个线程按照申请锁的顺序来获取锁。
+ 非公平锁：多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁。可能造成优先级反转或者饥饿现象。

#### 独享锁、共享锁
+ 独享锁：指该锁一次只能被一个线程所持有。
+ 共享锁：指该锁可被多个线程所持有。
+ 具体实现：互斥锁、读写锁。

#### 可重入锁（递归锁）
+ 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。
```
synchronized void setA() {   
    // 因为获取了setA()的锁（即获取了方法外层的锁），此时调用setB()将会自动获取setB()的锁，如果不自动获取的话方法B将不会执行  
    setB();            
}

synchronized void setB() {
}
```

#### 分段锁
+ 细化锁的粒度，当操作不需要更新整个数组的时候，就仅仅针对数组中的一项进行加锁操作。

#### 自旋锁
+ 定义：是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。

#### 分布式锁
用来协调多个进程下的所有线程多共享资源的同步访问。
+ 基于数据库实现分布式锁：锁表、数据库排他锁
+ 基于缓存实现分布式锁：Redis、memcached
+ 基于Zookeeper实现分布式锁

#### 锁的状态：偏向锁、轻量级锁、重量级锁
+ 偏向锁：指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁。降低获取锁的代价。
+ 轻量级锁：指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。
+ 重量级锁：指当锁为轻量级锁的时候，另一个线程虽然是自旋，但自旋不会一直持续下去，当自旋一定次数的时候，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。重量级锁会让其他申请的线程进入阻塞，性能降低。

#### 看待并发同步的角度：乐观锁、悲观锁
+ 乐观锁（CAS）：假设不会发生并发冲突，只在提交操作时检查是否违反数据完整性。能解决脏读的问题（适合读取操较频繁的场景）。
+ 悲观锁：假定会发生并发冲突，屏蔽一切可能违反数据完整性的操作（适合写入操作频繁的场景）。

### 网路
#### OSI 七层和 TCP/IP 四层
<table>
    <tr>
        <td >OSI 七层网络模型</td> 
        <td >TCP/IP 四层概念模型</td> 
        <td >对应网络协议</td> 
    </tr>
    <tr>
        <td >应用层（Application）</td>    
        <td rowspan="3">应用层</td>  
        <td >HTTP、TFTP, FTP, NFS, WAIS、SMTP</td>      
    </tr>
    <tr>
        <td >表示层（Presentation</td>    
        <td >Telnet, Rlogin, SNMP, Gopher</td>      
    </tr>
    <tr>
        <td >会话层（Session）</td>    
        <td >SMTP, DNS</td>      
    </tr>
    <tr>
        <td >传输层（Transport）</td> 
        <td >传输层</td> 
        <td >TCP, UDP</td> 
    </tr>
    <tr>
        <td >网络层（Network）</td> 
        <td >网络层</td> 
        <td >IP, ICMP, ARP, RARP, AKP, UUCP</td> 
    </tr>
    <tr>
        <td >数据链路层（Data Link）</td> 
        <td rowspan="2">数据链路层</td> 
        <td >FDDI, Ethernet, Arpanet, PDN, SLIP, PPP/td> 
    </tr>
    <tr>
        <td >物理层（Physical）</td> 
        <td >IEEE 802.1A, IEEE 802.2到IEEE 802.11</td> 
    </tr>
</table>

#### TCP 三次握手和四次挥手
+ 三次握手:   
![三次握手](https://images2017.cnblogs.com/blog/985821/201708/985821-20170802101806802-1497343688.png)  
  - 第一次握手：A的TCP客户进程也是首先创建传输控制块TCB，然后向B发出连接请求报文段，（首部的同步位SYN=1，初始序号seq=x），（SYN=1的报文段不能携带数据）但要消耗掉一个序号，此时TCP客户进程进入SYN-SENT（同步已发送）状态;
  - 第二次握手：B收到连接请求报文段后，如同意建立连接，则向A发送确认，在确认报文段中（SYN=1，ACK=1，确认号ack=x+1，初始序号seq=y），测试TCP服务器进程进入SYN-RCVD（同步收到）状态；
  - 第三次握手：TCP客户进程收到B的确认后，要向B给出确认报文段（ACK=1，确认号ack=y+1，序号seq=x+1）（初始为seq=x，第二个报文段所以要+1），ACK报文段可以携带数据，不携带数据则不消耗序号。TCP连接已经建立，A进入ESTABLISHED（已建立连接）;当B收到A的确认后，也进入ESTABLISHED状态。
  - 为什么A还要发送一次确认呢：主要为了防止已失效的连接请求报文段突然又传送到了B，因而产生错误。

+ 四次挥手:  
![四次挥手](https://images2017.cnblogs.com/blog/985821/201708/985821-20170802101823505-1177747613.png)  
  - 第一次握手：A的应用进程先向其TCP发出连接释放报文段（FIN=1，序号seq=u），并停止再发送数据，主动关闭TCP连接，进入FIN-WAIT-1（终止等待1）状态，等待B的确认；
  - 第二次握手：B收到连接释放报文段后即发出确认报文段，（ACK=1，确认号ack=u+1，序号seq=v），B进入CLOSE-WAIT（关闭等待）状态，此时的TCP处于半关闭状态，A到B的连接释放；A收到B的确认后，进入FIN-WAIT-2（终止等待2）状态，等待B发出的连接释放报文段
  - 第三次握手：B没有要向A发出的数据，B发出连接释放报文段（FIN=1，ACK=1，序号seq=w，确认号ack=u+1），B进入LAST-ACK（最后确认）状态，等待A的确认；
  - 第四次握手：A收到B的连接释放报文段后，对此发出确认报文段（ACK=1，seq=u+1，ack=w+1），A进入TIME-WAIT（时间等待）状态。此时TCP未释放掉，需要经过时间等待计时器设置的时间2MSL后，A才进入CLOSED状态。
  - 为什么A在TIME-WAIT状态必须等待2MSL的时间：保证A发送的最后一个ACK报文段能够到达；防止“已失效的连接请求报文段”出现在本连接中。
  
#### GET、POST
+ GET
  + 从服务器获得数据；
  + url后面 v=value& 明文传输；不安全；
  + 传输量小；
+ POST
  + 向服务器上传数据；
  + 将表单数据放到 Request.Form 体传输；
  + 传输量大；
+ PUT
  + 从客户端向服务器传送的数据取代指定的文档的内容；
+ DELETE
  + 请求服务器删除指定的页面；
  + 3种返回码：
    + 200（OK）——删除成功，同时返回已经删除的资源；
    + 202（Accepted）——删除请求已经接受，但没有被立即执行（资源也许已经被转移到了待删除区域）；
    + 204（No Content）——删除请求已经被执行，但是没有返回资源（也许是请求删除不存在的资源造成的）。  

#### Session、Cookie
+ Session
  + 存放在服务器端；
  + Session ID用来唯一标识这个Session；
  + 会话（Session）跟踪。
+ Cookie
  + 存储在客户端（浏览器）；
  + 不安全；
  + 典型应用：
    1) 判断用户是否登陆过网站，以便下次登录时能够直接登录;
    2) 在线商城“购物车”中处理和设计。
  
#### 分布式 session
+ 基于数据库的session共享
  + 原理：拿出一个数据库，专门用来存储session信息。
  + 优点：服务器出现问题，session不会丢失。
  + 缺点：如果网站的访问量很大，把session存储到数据库中，会对数据库造成很大压力，还需要增加额外的开销维护数据库。
+ 基于NFS共享文件系统
  + 原理：拿出一个服务器，搭建NFS服务器来共享session。
  + 优点：过期后可以实现自动清除，须自己设定回收机制。
  + 缺点：量比较大并且所有的session文件都在同一个子目录下的话，可能带来严重的负载问题，甚至导致网站无法使用。
+ 基于memcached 的session（不提倡）
+ 基于resin/tomcat web容器本身的session复制机制（session复制）
  + 原理：将一台机器上的Session数据广播复制到集群中其余机器上。
  + 优点：实现简单、配置较少、当网络中有机器Down掉时不影响用户访问。
  + 缺点：在机器较少，网络流量较小广播式复制到其余机器上，当机器数量增多时候会有一定廷时，带来一定网络开销。
+ 基于TT/Redis 或 jbosscache 进行 session 共享
  + 优点：memcache、redis本身就是一个分布式缓存，便于扩展。网络开销较小，几乎没有IO。性能也更好。
  + 缺点：受制于Memcache的容量，如果用户量突然增多cache由于容量的限制会将一些数据挤出缓存，另外memcache故障或重启session会完全丢失掉。所以更偏向于redis。
+ 基于cookie 进行session共享
  + 原理：将用户的session数据全部存放在cookie中。
  + 优点：服务器架构变得简单，每台web服务器都可以很独立。没有网络开销和对磁盘IO，服务器重启也不会导致数据的丢失。
  + 缺点：cookie过于庞大会耗费单位页面的下载时间。
  
#### 从点击url到显示网页发生了什么
1) 浏览器通过域名找出其IP地址（DNS解析）
+ 浏览器缓存：浏览器会缓存DNS记录一段时间，不同浏览器会储存各自固定的一个时间（2分钟到30分钟不等）；
+ 系统缓存：浏览器会做一个系统调用（windows里是gethostbyname），这样便可获得系统缓存中的记录；
+ 路由器缓存
+ ISP的DNS缓存
+ 递归搜索：从跟域名服务器开始进行递归搜索，从.com顶级域名服务器到Facebook的域名服务器。
  + 一个域名多个IP地址的解决方法
    + 循环 DNS 
    + 负载平衡器：以一个特定IP地址进行侦听并将网络请求转发到集群服务器上的硬件设备；一些大型的站点一般都会使用这种昂贵的高性能负载平衡器。 
    + 地理 DNS：根据用户所处的地理位置，通过把域名映射到多个不同的IP地址提高可扩展性。
    + Anycast：一个IP地址映射多个物理主机的路由技术。
![DNS递归搜索](http://igoro.com/wordpress/wp-content/uploads/2010/02/500pxAn_example_of_theoretical_DNS_recursion_svg.png)

2) 建立连接（[TCP/TP三次握手](#tcp-%E4%B8%89%E6%AC%A1%E6%8F%A1%E6%89%8B%E5%92%8C%E5%9B%9B%E6%AC%A1%E6%8C%A5%E6%89%8B)）

3) 浏览器向服务器发送HTTP请求
+ HTTP请求：请求行、请求头部、空行、请求数据
```
POST / HTTP1.1
Host: github.com
User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; rv:61.0) Gecko/20100101 Firefox/61.0
Accept: text/html
Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
Accept-Encoding: gzip, deflate, br
Referer: https://github.com/GeJinTaiHua
x-requested-with: XMLHttpRequest
origin: https://github.com
Connection: keep-alive

name=GeJinTaiHua
```

4) 服务器接受到请求并处理
+ Spring MVC的处理流程
  1) 发起请求到前端控制器（DispatcherServlet）；
  2) DispatcherServlet 请求 处理器映射器（HandlerMapping） 查找 Handler，可以根据 xml 配置、注解进行查找；
  3) HandlerMapping 向 DispatcherServlet 返回 Handler；
  4) DispatcherServlet 调用处理器适配器去执行 Handler；
  5) 处理器适配器执行 Handler；
  6) Handler 执行完成给适配器返回 ModelAndView；
  7) 处理器适配器向 DispatcherServlet 返回 ModelAndView；
  8) DispatcherServlet 请求视图解析器去进行视图解析，根据逻辑视图名解析成真正的视图(jsp)；
  9) 视图解析器向 DispatcherServlet 返回 View；
  10) DispatcherServlet 进行视图渲染；视图渲染将模型数据(在ModelAndView对象中)填充到request域；
  11) DispatcherServlet 向用户响应结果。

5) 服务器返回HTTP响应
+ 301 永久重定向响应
  + 搜索引擎排名
  + 不同的地址会造成缓存友好性变差
+ 302 临时跳转
+ HTTP响应：状态行、消息报头、空行、响应正文
![HTTP响应](http://upload-images.jianshu.io/upload_images/2964446-1c4cab46f270d8ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

6) 浏览器解析渲染页面
+ 浏览器首先解析HTML文件构建DOM树；
+ 然后解析CSS文件构建渲染树；
+ 等到渲染树构建完成后，浏览器开始布局渲染树并将其绘制到屏幕上。

7) 浏览器获取其他地址内容的标签：图片、视频等
+ 浏览器缓存静态文件（ETag头）
+ CDN（内容分发网站）：缓存、分流

8) 断开连接（[四次挥手](#tcp-%E4%B8%89%E6%AC%A1%E6%8F%A1%E6%89%8B%E5%92%8C%E5%9B%9B%E6%AC%A1%E6%8C%A5%E6%89%8B)）

### 内存
#### 内存溢出（out of memory）
+ 程序在申请内存时，没有足够的内存空间供其使用。

#### 内存泄漏（memory leak）
+ 程序在申请内存后，无法释放已申请的内存空间。
+ java 容易引起内存泄漏时机：
  + 静态集合类（HashMap、ThreadLocal等）；
  + 各种连接（数据库连接，网络连接，IO连接等）未调用close关闭；
  + 监听器，在释放对象的同时没有删除对应监听器；
  + 内部类和外部模块的引用；
  + 不正确的单例。
  
### 攻击
#### 重放攻击
+ 定义：又称重播攻击、回放攻击，是指攻击者发送一个目的主机已接收过的包，来达到欺骗系统的目的，主要用于身份认证过程，破坏认证的正确性。
+ 防御方案：
  1) 加随机数：需要额外保存使用过的随机数；
  2) 加时间戳；
  3) 加流水号；
  4) 挑战一应答机制；
  5) 一次性口令机制。
  
#### 文件上传漏洞攻击
+ 定义：用户上传了一个可执行的脚本文件，并通过此脚本文件获得了执行服务器端命令的能力。
+ 防御方案：
  1) 将文件上传目录直接设置为不可执行；
  2) 文件类型检查：白名单方式，结合MIME Type、后缀检查等方式；
  3) 图片处理：使用压缩函数或resize函数，处理图片的同时破坏其包含的HTML代码；
  4) 使用随机数改写文件名和文件路径，使得用户不能轻易访问自己上传的文件；
  5) 单独设置文件服务器的域名。

### 命令
```
ps aux|grep java 查看java进程
ps aux 查看所有进程
ps –ef|grep tomcat 查看所有有关tomcat的进程
ps -ef|grep --color java 高亮要查询的关键字
kill -9 19979 终止线程号位19979的进程
```

#### Crontab表达式
字段|允许值（整数）|允许的特殊字符| 
---|---|---|
秒（Seconds）|0~59|, - * /    四个字符|
分（Minutes）|0~59|, - * /    四个字符|
小时（Hours）|0~23|, - * /    四个字符|
日期（DayofMonth）|1~31|,- * ? / L W C     八个字符|
月份（Month）|1~12 或 <br>JAN, FEB, MAR, APR, MAY, JUN, <br>JUL, AUG, SEP, OCT, NOV, DEC|, - * /    四个字符|
星期（DayofWeek）|1~7 （1=SUN=星期日）或 <br>SUN, MON, TUE, WED, THU, FRI, SAT|, - * ? / L C #     八个字符|
年(可选，留空)（Year）|1970~2099|, - * /    四个字符|

```
每秒：* * * * * ? *
每分：0 * * * * ? *
每时：0 0 * * * ? *
每日：0 0 0 * * ? *
```

#### Shell命令
+ 返回当前路径的"." 
$(cd `dirname $0`; pwd)

+ 变量
  + $0：当前脚本的文件名
  + $n：传递给脚本或函数的参数。n 是一个数字，表示第几个参数。例如，第一个参数是$1，第二个参数是$2。
  + $#：传递给脚本或函数的参数个数。
  + $\*：传递给脚本或函数的所有参数。
  + $@：传递给脚本或函数的所有参数。被双引号(" ")包含时，与 $* 稍有不同，下面将会讲到。
  + $?：上个命令的退出状态，或函数的返回值。
  + $$：当前Shell进程ID。对于 Shell 脚本，就是这些脚本所在的进程ID

