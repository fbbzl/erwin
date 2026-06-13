<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Version-1.2.15-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Version 1.2.15"/>
  <img src="https://img.shields.io/badge/License-Apache_2.0-151515?style=for-the-badge&logo=apache&logoColor=white" alt="License"/>
</p>

<h1 align="center">erwin</h1>

<p align="center">
  <b>轻量、顺手的 Java 工具库</b><br>
  <i>聚焦异常断言、空安全访问、Lambda 适配、反射辅助与 Stream 分叉处理</i>
</p>

<p align="center">
  <a href="./README.md">English</a>
</p>

---

## 模块矩阵

```
erwin
├── exception : 条件式异常断言、消息格式化与 i18n MessageSource 兜底
├── lang      : 空安全访问、变量校验、泛型类型解析
├── lambda    : checked exception Lambda 适配、LambdaMetafactory 辅助
└── stream    : 单个 Stream 分叉为多路计算结果
```

---

## 一分钟接入

```xml
<dependency>
    <groupId>io.github.fbbzl</groupId>
    <artifactId>erwin</artifactId>
    <version>1.2.15</version>
</dependency>
```

要求 Java 21 及以上。

```bash
mvn test
mvn package
```

---

## 核心能力

### 条件断言

`Throws` 提供条件式异常抛出，`Vars` 在其基础上提供更像参数校验的 require 风格 API。

```java
import org.fz.erwin.exception.Throws;
import org.fz.erwin.lang.Vars;

Vars.requireNotBlank(username);
Vars.requireNotEmpty(userList);

Throws.ifTrue(pageSize > 100, "page size must be <= {}", 100);
Throws.ifEmpty(userList, "user list can not be empty");
```

### 空安全访问

`NullSafe` 用 Lambda 包住可能出现空指针的访问链，适合在简单取值场景里减少层层判空。

```java
import org.fz.erwin.lang.NullSafe;

Integer length = NullSafe.nullDefault(
        () -> user.getProfile().getName().length(),
        0
);
```

### Checked Exception Lambda 适配

`Try` 可以把带 checked exception 的函数适配为标准 JDK 函数式接口，并统一包装为运行时异常。

```java
import org.fz.erwin.lambda.Try;

List<String> contents = paths.stream()
        .map(Try.apply(Files::readString))
        .toList();
```

### Lambda 元信息与反射辅助

`LambdaMetas` 基于 `LambdaMetafactory` 生成构造器、getter、setter 的函数式调用入口。

```java
import org.fz.erwin.lambda.LambdaMetas;

Supplier<User> constructor = LambdaMetas.lambdaConstructor(User.class);
Function<User, String> getter = LambdaMetas.lambdaGetter(User.class, String.class, "getName");
BiConsumer<User, String> setter = LambdaMetas.lambdaSetter(User.class, String.class, "setName");
```

### Stream 分叉计算

`StreamForks` 让同一份数据源被多路 Stream 消费，用一个入口收集不同统计结果。

```java
import org.fz.erwin.stream.StreamForks;

StreamForks.ForkResult result = StreamForks.of(List.of(1, 2, 3, 4))
        .fork("sum", stream -> stream.mapToInt(Integer::intValue).sum())
        .fork("even", stream -> stream.filter(i -> i % 2 == 0).toList())
        .done();

Integer sum = result.get("sum");
List<Integer> even = result.get("even");
```

---

## 工具栈

| 技术 | 版本 |
|------|------|
| Java | 21 |
| Lombok | 1.18.46 |
| Hutool | 5.8.44 |
| Apache Commons Lang | 3.20.0 |
| Apache Commons Collections | 4.5.0 |
| Apache Commons IO | 2.22.0 |
| Apache Commons Codec | 1.22.0 |
| Apache Commons Text | 1.15.0 |
| Guava | 33.6.0-jre |
| Failsafe | 3.3.2 |
| Spring Context | 6.2.7 provided |

---

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

<p align="center">
  <sub>Made with love by <a href="https://github.com/fbbzl">fengbinbin</a></sub>
</p>
