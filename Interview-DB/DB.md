## 💾DB

  * [数据库](#数据库)
    * [常用数据库](#常用数据库)
    * [Redis](#redis)
    * [MongoDB](#mongodb)
    * [MySql](#MySql)
  * [事务](#%E4%BA%8B%E5%8A%A1)
    * [3种并发问题](#3种并发问题)
    * [4大特性](#4大特性)
    * [5种事务隔离级别](#5种事务隔离级别)
    * [加锁协议](#加锁协议)
    * [分布式事务](#分布式事务)
  * [缓存](#%E7%BC%93%E5%AD%98)
    * [缓存穿透](#%E7%BC%93%E5%AD%98%E7%A9%BF%E9%80%8F)
    * [缓存雪崩](#%E7%BC%93%E5%AD%98%E9%9B%AA%E5%B4%A9)
  * [优化](#%E4%BC%98%E5%8C%96)
    * [数据库优化](#%E6%95%B0%E6%8D%AE%E5%BA%93%E4%BC%98%E5%8C%96)
    * [数据库切分](#数据库切分)
    * [计数器表](#%E8%AE%A1%E6%95%B0%E5%99%A8%E8%A1%A8)
    * [SQL技巧](#SQL技巧)
  * [基础知识](#%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86)
    * [三大范式](#%E4%B8%89%E5%A4%A7%E8%8C%83%E5%BC%8F)
    * [五大约束](#%E4%BA%94%E5%A4%A7%E7%BA%A6%E6%9D%9F)
    * [数据仓库](#数据仓库)
    * [视图](#视图)
   

### 数据库
#### 常用数据库
+ 关系数据库（SQL）
  + Oracle
  + MySQL
  + PostgreSQL
  + Microsoft SQL Server
  + Microsoft Access
+ 非关系数据库（NoSQL）
  + 面向检索的列式存储（Column-Oriented） 
    + BigTable
    + HaBase
  + 面向高性能并发读/写的缓存存储（Key-Value）
    + Redis
    + MemcacheDB
    + BerkeleyDB
    + Flare
  + 面向海量数据访问的文档存储（Document-Oriented）
    + MongoDB
    + CouchDB
  ![流程](/Interview-DB/Pic/NOSQL.jpg)
+ NewSql
  + SAP HANA
  + MemSQL
   
#### Redis
+ 为什么快
  + 完全基于内存；
  + 单线程，避免了多线程的频繁上下文切换问题；
  + 基于非阻塞的IO多路复用机制；
  + 数据结构简单，对数据操作也简单；

+ 五种数据结构：
  + String：缓存、限流、计数器、分布式锁、分布式Session
    + 底层实现：int、raw、embstr-54
    + embstr：<=39字节的字符串时使用；
    + int：8个字节的长整型时使用；
    + raw：<39个字节的字符串时使用。
  + Hash：存储用户信息、用户主页访问量、组合查询
    + 底层实现：ziplist（压缩列表）或者hashtable（字典或者也叫哈希表）
  + List：微博关注人时间轴列表、简单队列
    + 底层实现：quicklist（快速列表，是ziplist 压缩列表 和linkedlist 双端链表 的组合）
    + linkedlist（双端链表）
    + ziplist（压缩列表）:当一个列表键只包含少量列表项，且是小整数值或长度比较短的字符串时使用；
  + Set：赞、踩、标签、好友关系
    + 底层实现：intset（整数集合）或者hashtable（字典或者也叫哈希表）
  + Zset：排行榜
    + 底层实现：ziplist（压缩列表）或者skiplist（跳跃表）

+ 四种持久化存储方案：
  + RDB（快照/内存快照）：按照一定的时间周期将目前服务中的所有数据全部写入到磁盘中；
  + AOF：写到一个类似日志文件里；
  + VM（虚拟内存）：从 Redis Version 2.4 开始就被官方明确表示不再建议使用；
  + DISKSTORE：Redis Version 2.8 开始提出的一个存储设想，官方没有明确建议使用这用方式。

+ Redis事务
  + MULTI：开始事务；
    + Redis事务是不可嵌套，再次发送MULTI会返回错误，但当前事务不会失败；
  + EXEC：执行事务；
  + DISCARD：取消事务；
  + WATCH：在事务开始之前监视任意数量的键；
    + 当调用 EXEC 执行事务时，如果任意一个被监视的键已经被其他客户端修改了，那么事务不再执行，直接返回失败；
    + 只能在客户端进入事务状态之前执行，否则返回错误，但当前事务不会失败；
  ![流程](/Interview-DB/Pic/RedisTran.png)
  + ACID
    + 原子性：不保证；事务失败，不会回滚；
    + 持久性：不保证；由 Redis 所使用的持久化模式决定；
    + 一致性：保证；
    + 隔离性：保证；

+ Redis分布式锁
  + setnx
  + 通过get命令获取锁的时间戳，通过它进行超时判断，并进行释放，防止宕机死锁问题；
  + 加上线程特征码，确保在非超时情况下，锁只能由有锁的线程进行释放,

+ 查询慢原因
  + 慢查询语句；
  ``
   INFO commandstats
  ``
  + 排队等待时间；
  + RDB备份时，数据量比较大；
  + slowlog get {n} :获取最近的n条慢查询命令，默认对于执行超过10毫秒(可配置)的命令都会记录到一个定长队列中。

+ Linux安装
   + 安装
   ```
   yum install gcc-c++  
   tar zxvf redis-5.0.8.tar.gz #解压
   cd redis-5.0.8 
   make
   mkdir /usr/local/redis
   make install PREFIX=/usr/local/redis #编译
   ```
   + 启动
   ```
   cd /usr/local/redis/bin 
   ./redis-server  #启动
   cd /root/redis-5.0.8 #进入解压目录
   cp redis.conf /usr/local/redis/bin #拷贝解压目录中的配置文件到安装目录
   cd /usr/local/redis/bin
   vi  redis.conf   设置后台运行
      daemonize yes
      requirepass 12355
   ./redis-server redis.conf  启动redis
   ```

#### MongoDB
+ 数据库（database）
+ 集合（collection）
+ 文档对象（document）
+ 优势：
  + 快速：将热数据存储在内存中；
  + 高扩展性；
  + failover机制，故障切换；
  + 存储格式Json、Bson。
+ 劣势：
  + 4.0之前不支持事务；
  + 全局锁机制；
  + 占用空间大；
  + 无成熟的维护工具。

#### MySql
+ 索引
  + 使用时机
    + 经常出现在 group by,order by 和 distinc 关键字后面的字段；
    + 经常与其他表进行连接的表，在连接字段上应该建立索引；
    + 经常出现在 Where 子句中的字段；
    + 经常出现用作查询选择的字段。
  + 单列索引
    + 多个单列索引在多条件查询时只会生效第一个索引；
  + 联合索引
    + 最左前缀原则：以最左边的为起点任何连续的索引都能匹配上；
    + 当创建(a,b,c)联合索引时，相当于创建了(a)单列索引，(a,b)联合索引以及(a,b,c)联合索引（a,c组合也可以，但实际上只用到了a的索引，c并没有用到！）。
  + 索引失效情况：
    + where 子句中使用 !=、in、or、函数操作；
    + 模糊查询：like 的前通配（%XX） 索引失效，后通配（XX%）走索引；
    + 使用<>、not in 、not exist，对于这三种情况大多数情况下认为结果集很大，一般大于5%-15%就不走索引而走FTS；
    + 多列索引，不使用的第一部分，则不会使用索引；
    + 列类型是字符串，那一定要在条件中将数据使用引号引用起来，否则不会使用索引；
    + 如果MySQL估计使用全表扫描要比使用索引快，则不使用索引；
    + B-tree索引 is null不会走，is not null会走；位图索引 is null，is not null  都会走；联合索引 is not null 只要在建立的索引列（不分先后）都会走；
  + MySQL一条查询语句一般只使用一个索引，因为N条独立索引同时在一条语句使用的消耗比只使用一个索引还要慢。

+ count(1) count(主键) count(\*)
  + count(1)：
    + 只扫描主键；
    + 考虑null；
  + count(主键)：
    + 只扫描主键；
    + 忽略null；
  + count(\*)：
    + 扫描表；
    + 考虑null；
    + 系统会对count(\*)做优化；

+ Limit 
  + LIMIT N,M：LIMIT首先要找查 N+M 行，然后从N行处，取M行。
  + 优化：让N变的尽可能的小或是不用
  ```
  原查询：SELECT * FROM pw_gbook WHERE uid='48' ORDER BY postdate DESC LIMIT 1275480,20;
  转换成：SELECT * FROM pw_gbook WHERE id>1275480 and uid='48' ORDER BY postdate DESC LIMIT 20;
  ```

+ Select For update
  + InnoDB 使用行锁定，BDB 使用页锁定，MyISAM 使用表锁；

+ 慢SQL
  + 开启日志
  ```
  set global slow_query_log=1; set long_query_time=1；
  ```
  + 慢日志查询
  ```
  show variables like 'slow_query%'; show variables like '%long_query%';
  ```  

+ Linux安装
  + 先卸载CentOS自带的MariaDB
  ```
  rpm -qa | grep mariadb
  rpm -e --nodeps mariadb-libs-5.5.64-1.el7.x86_64
  ```
  + 解压安装
  ```
  tar -xvf mysql-5.7.27-1.el7.x86_64.rpm-bundle.tar 
  rpm -ivh mysql-community-common-8.0.15-1.el7.x86_64.rpm
  rpm -ivh mysql-community-libs-8.0.15-1.el7.x86_64.rpm
  rpm -ivh mysql-community-libs-compat-5.7.27-1.el7.x86_64.rpm 
  rpm -ivh mysql-community-client-8.0.15-1.el7.x86_64.rpm
  rpm -ivh mysql-community-server-8.0.15-1.el7.x86_64.rpm
  ```
  + 启动
  ``` 
  systemctl start mysqld  # 启动
  systemctl enable mysqld # 设置开机启动
  ```
  + 设置密码
  ```
  cat /var/log/mysqld.log|grep password # 查询初始密码
  mysql -u root -p 
  mysql>update mysql.user set authentication_string=password('1213') where user='root'; # 修改密码
  mysql>grant all privileges on *.* to 'root'@'%' identified by '1213'; # 允许远程
  ```
  + 配置my.cnf
  ```
  vi /etc/my.cnf

  [client]
  default-character-set=utf8
  port=3306
  socket=/var/lib/mysql/mysql.sock
  [mysqld]
  datadir=/var/lib/mysql
  socket=/var/lib/mysql/mysql.sock
  character-set-server=utf8
  port=3306
  max_allowed_packet=100M
  validate_password_policy=0
  validate_password_length=4
  ```

### 事务
#### 3种并发问题
+ 脏读：一个事务读到了另一个事务的未提交的数据；
+ 不可重复读：一个事务读到了另一个事务已经提交的 update 的数据导致多次查询结果不一致；
+ 虚幻读：一个事务读到了另一个事务已经提交的 insert 的数据导致多次查询结果不一致。

#### 4大特性
+ ACID：
  + 原子性（Atomicity）：一个事务是一个不可分割的工作单位，事务中的所有操作，要么全部完成，要么全部不完成；
  + 一致性（Consistency）：对数据完整性约束的遵循；
  + 隔离性（Isolation）：一个事务的执行不能被其他事务干扰；
  + 持久性（Durability）：一个事务一旦提交，它对数据库中数据的改变就应该是永久性的，即便是在数据库系统遇到故障的情况下也不会丢失提交事务的操作。
+ 原理
  + 事务的 ACID 是通过 InnoDB 日志和锁来保证；
  + 事务的隔离性是通过数据库锁的机制实现的，持久性通过 Redo Log（重做日志）来实现，原子性和一致性通过 Undo Log 来实现。

#### 5种事务隔离级别
+ DEFAULT 这是一个PlatfromTransactionManager默认的隔离级别，使用数据库默认的事务隔离级别；
  + MySQL默认：可重复读
  + Oracle默认：已提交读
  + SQL Server默认：已提交读
+ 未提交读（read uncommited）：脏读，不可重复读，虚读都有可能发生；
+ 已提交读（read commited）：避免脏读。但是不可重复读和虚读有可能发生；
+ 可重复读（repeatable read）：避免脏读和不可重复读.但是虚读有可能发生；
+ 串行化的（serializable）：避免以上所有读问题。  

---|脏读|不可重复读|幻读|加锁协议|
---|---|---|---|---|
未提交读|✔️ |✔️ |✔️ |一级加锁协议|
已提交读|✖️ |✔️ |✔️ |二级加锁协议|
可重复读|✖️ |✖️ |✔️ |三级加锁协议|
串行化的|✖️ |✖️ |✖️ |两段锁协议|

#### 加锁协议
1) 一级加锁协议：
   + 事务在修改数据前必须加X锁，直到事务结束（事务结束包括正常结束(COMMIT)和非正常结束(ROLLBACK)）才可释放；如果仅仅是读数据，不需要加锁。
   + 一级封锁协议可以防止丢失修改，并保证事务T是可恢复的。使用一级封锁协议可以解决丢失修改问题。
   + 在一级封锁协议中，如果仅仅是读数据不对其进行修改，是不需要加锁的，它不能保证可重复读和不读“脏”数据。
```
SELECT xxx FOR UPDATE;
UPDATE xxx;
```
 
2) 二级加锁协议：
   + 满足一级加锁协议，且事务在读取数据之前必须先加S锁，读完后即可释放S锁。
   + 二级封锁协议除防止了丢失修改，还可以进一步防止读“脏”数据。但在二级封锁协议中，由于读完数据后即可释放S锁，所以它不能保证可重复读。

3) 三级加锁协议：
   + 满足一级加锁协议，且事务在读取数据之前必须先加S锁，直到事务结束才释放。
   + 三级封锁协议除防止了丢失修改和不读“脏”数据外，还进一步防止了不可重复读。
   
+ 两段锁协议（2-phase locking）
  + 两段锁协议是指每个事务的执行可以分为两个阶段：生长阶段（加锁阶段）和衰退阶段（解锁阶段）。
    + 加锁阶段：在该阶段可以进行加锁操作。在对任何数据进行读操作之前要申请并获得S锁，在进行写操作之前要申请并获得X锁。加锁不成功，则事务进入等待状态，直到加锁成功才继续执行。
    + 解锁阶段：当事务释放了一个封锁以后，事务进入解锁阶段，在该阶段只能进行解锁操作不能再进行加锁操作。
  + 若并发执行的所有事务均遵守两段锁协议，则对这些事务的任何并发调度策略都是可串行化的；遵循两段锁协议的事务调度处理的结果是可串行化的充分条件，但是可串行化并不一定遵循两段锁协议。
  + 与防止死锁的一次封锁法的异同之处：一次封锁法要求每个事务必须一次将所有要使用的数据全部加锁，否则就不能继续执行，因此一次封锁法遵守两段锁协议；但是两段锁协议并不要求事务必须一次将所有要使用的数据全部加锁，因此遵守两段锁协议的事务可能发生死锁。
  
#### 分布式事务
+ 2PC（XA）：强一致、中心化的原子提交协议。中心化协调者节点（coordinator）和N个参与者节点（partcipant）。
  + 第一阶段：请求/表决阶段；
  + 第二阶段：提交/执行阶段。
  ![2PC](/Interview-DB/Pic/2pc.jpg)
  + 特点：牺牲一部分可用性来换取强一致性。
  
+ 3PC：增加了CanCommit阶段，并引入了超时机制。
  + 第一阶段：CanCommit阶段；
  + 第二阶段：PreCommit阶段；
  + 第三阶段：DoCommit阶段。
  
+ TCC（Try-Confirm-Cancel）：针对每个操作，都要注册一个与其对应的确认和补偿（撤销）操作。
  + Try阶段：主要是对业务系统做检测及资源预留；
  + Confirm阶段：确认真正执行业务，不作任何业务检查，只使用 Try 阶段预留的业务资源。
  + Cancel阶段：取消执行，释放 Try 阶段预留的业务资源。
  ![TCC](/Interview-DB/Pic/tcc.png)
  
+ 本地消息表：将需要分布式处理的任务通过消息日志的方式来异步执行。消息日志可以存储到本地文本、数据库或消息队列，再通过业务规则自动或人工发起重试。
  ![eBay ](/Interview-DB/Pic/eBay.png)

+ MQ 事务：RocketMQ 中实现了分布式事务，实际上是对本地消息表的一个封装，将本地消息表移动到了 MQ 内部。 

+ Saga 事务：将长事务拆分为多个本地短事务，由 Saga 事务协调器协调，如果正常结束那就正常完成，如果某个步骤失败，则根据相反顺序一次调用补偿操作。
  + 向后恢复：执行结果撤销；
  + 向前恢复：适用于必须要成功的场景。
  
### 缓存
#### 缓存穿透
+ 查询一个一定不存在的数据，由于缓存是不命中时需要从数据库查询，查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到数据库去查询；
+ 解决办法：
  1) 布隆过滤器，对所有可能查询的参数以hash形式存储，在控制层先进行校验，不符合则丢弃；
  2) 如果一个查询返回的数据为空，我们仍然把这个空结果进行缓存，但它的过期时间会很短，最长不超过五分钟。
  
#### 缓存雪崩
+ 如果缓存集中在一段时间内失效，发生大量的缓存穿透，所有的查询都落在数据库上，造成了缓存雪崩;
+ 解决方法：
  1) 事前：redis 高可用，主从+哨兵，redis cluster，避免全盘崩溃。
  2) 事中：本地 ehcache 缓存 + hystrix 限流&降级，避免 MySQL 被打死。
  3) 事后：redis 持久化，一旦重启，自动从磁盘上加载数据，快速恢复缓存数据。

### 优化
#### 数据库优化
+ 选择合适的字段；尽量吧字段设置为not null，这样查询时不需要比较null；
+ 使用关联查询（left join on）代替子查询；
+ 使用联合查询（union）手动创建临时表；
+ 开启事务；
+ 使用外键保证数据的关联性；
+ 使用索引提高检索特定行的速度；
+ optimize table（优化表）
  + 回收磁盘碎片；
  + 只对MyISAM，BDB和InnoDB表起作用；
  + 会锁表。

#### 数据库切分
+ 垂直拆分：把表按模块划分到不同数据库表中。
![垂直拆分](/Interview-DB/Pic/czcf.png)

+ 水平拆分：把一个表按照某种规则（时间、用户...）把数据划分到不同表或数据库里。
  + 会将一段时间以前的数据归档，存放到类似HBase这种非关系型数据库中。
  + 非每个查询都能携带分库分表键：建立“异构索引表”，即采用异步机制将原表内的每次一创建或更新，都换一个维度保存一份完整的数据表或索引表，拿空间换时间。
![水平拆分](/Interview-DB/Pic/spcf.png)

+ 分库分表：数据库500W以上考虑。

#### 计数器表
+ 只有一行，更新会串行执行
```
update table set cnt=cnt+1 limit 1;
```
+ 优化：可以加入100行，随机更新
```
update table set cnt=cnt+1 where id=RAND()*100 limit 1;
```
查询计数：
```
select sun(cnt) from table;
```

#### SQL技巧
+ [根据已有的表创建新表](/Interview-DB/SQL/skill.sql#L1)
+ [复制表（只复制结构）](/Interview-DB/SQL/skill.sql#L9)
+ [删除重复记录](/Interview-DB/SQL/skill.sql#L19)
+ [随机取出10条数据](/Interview-DB/SQL/skill.sql#L28)
+ [分组查询每组前三](/Interview-DB/SQL/skill.sql#L32)
+ [按数字大小排序String字段 str](/Interview-DB/SQL/skill.sql#L59)
+ EXPLAIN
  + type列：连接类型。一个好的sql语句至少要达到range级别，杜绝出现all级别；
  + key列：使用到的索引名。如果没有选择索引，值是NULL，可以采取强制索引方式；
  + key_len列：索引长度；
  + rows列：扫描行数。该值是个预估值；
  + extra列：详细说明。注意常见的不太友好的值有：Using filesort, Using temporary。

+ in和exists
  + 如果是exists，那么以外层表为驱动表，先被访问；如果是IN，那么先执行子查询。所以IN适合于外表大而内表小的情况；EXISTS适合于外表小而内表大的情况。
  + 关于not in和not exists，推荐使用not exists，不仅仅是效率问题，not in可能存在逻辑问题。
```
select * from A where id in (select id from B)

select * from A where exists
(select * from B where B.id=A.id)
```
```
select * from A
where a.id not in (select b.id from B)

# 高效
select * from A Left join B on
where a.id = b.id where b.id is null
```
  
+ 清空表
   + id从1开始
   ```
   truncate table table_name
   ```
   + id继承
   ```
   delete from table_name
   ```
   + 效率上 truncate 比 delete 快，但 truncate 删除后不记录 mysql 日志，不可以恢复数据。
  
### 基础知识
#### 三大范式
+ 第一范式（1NF）：
每一列都是不可分割的原子数据项；
+ 第二范式：
满足1NF后，要求表中的所有列，都必须依赖于主键，而不能有任何一列与主键没有关系（一个表只描述一件事情）；
+ 第三范式：
在2NF基础上，表中的每一列只能依赖于主键（在2NF基础上消除传递依赖）。

#### 五大约束
+ [主键约束](/Interview-DB/SQL/coustraint.sql#L1)（Primay Key Coustraint）：唯一性，非空性；  
+ [唯一约束](/Interview-DB/SQL/coustraint.sql#L6)（Unique Counstraint）：唯一性，可以空，但只能有一个；  
+ [检查约束](/Interview-DB/SQL/coustraint.sql#L11) (Check Counstraint) ：对该列数据的范围、格式的限制（如：年龄、性别等）；  
+ [默认约束](/Interview-DB/SQL/coustraint.sql#L16) (Default Counstraint) ：该数据的默认值；  
+ [外键约束](/Interview-DB/SQL/coustraint.sql#L21) (Foreign Key Counstraint) ：需要建立两表间的关系并引用主表的列。  

#### 数据仓库
+ 特点
  + 主题性：是针对某个主题来进行组织。
  + 集成性：需要将多个数据源的数据存到一起，但是这些数据以前的存储方式不同，所以需要经过抽取、清洗、转换的过程。
  + 稳定性：保存的数据是一系列历史快照，不允许修改，只能分析。
  + 时变性：会定期接收到新的数据，反应出最新的数据变化。
+ ETL
  + 数据抽取(Extract)：把数据从数据源读出来；
  + 数据转换(Transform)：把数据转换为特定的格式；
  + 数据加载(Load)：把处理后的数据加载到目标处。
  ![](/Interview-DB/Pic/ETL.png)
+ 主流的数据仓库
  + Hive：基于Hadoop
  + Teradata    

#### 视图
视图是基于一张表或多张表或另外一个视图的逻辑表。
+ 优点：
  1) 提高数据访问的安全性：通过视图往往只可以访问数据库中表的特定部分，限制了用户访问表的全部行和列。 
  2) 简化查询：视图的数据来自一个复杂的查询，用户对视图的检索却很简单。 
  3) 一个视图可以检索多张表的数据，因此用户通过访问一个视图，可完成对多个表的访问。 
  4) 视图是相同数据的不同表示，通过为不同的用户创建同一个表的不同视图，使用户可分别访问同一个表的不同部分。 

+ 对视图的操作将传递到基表。






