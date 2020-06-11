---
header-id: monitoring-garbage-collection-and-jvm
---

# Monitoring Garbage Collection and JVM

[TOC levels=1-4]

Although the 
[tuning parameters](/docs/7-0/deploy/-/knowledge_base/d/liferay-digital-enterprise-configuration-and-tuning-guidelines)
give you a good start to tuning your JVM, you must monitor GC performance to
ensure you have the best settings to meet your needs. There are several tools to
help you monitor Oracle JVM performance including.

## Visual VM

This tool provides a centralized console for viewing Oracle JVM performance
information, including garbage collector activities.

![Visual VM shows the garbage collector in real-time.](../../images-dxp/visual-vm-gc.png)

## JMX Console

This tool helps display various statistics like @product@'s distributed cache
performance, the performance of application server threads, JDBC connection pool
usage, and more. 

| **Note:** The JMX Console is the preferred tool to use when observing Tomcat
| performance information.

To enable JMX connections, add the following configuration to your application
server's JVM arguments:

    -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote. port=5000
    -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun. management.jmxremote.ssl=false

If you're running JMX Console from a another machine, add these JVM arguments too:

    -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.rmi.port=5000 -Djava.rmi.server.hostname=[place IP address here]

![Visual VM lets you monitor using Java Management Extensions.](../../images-dxp/visual-vm-jmx.png)

## Garbage Collector Verbose Logging

Add the following configuration to your JVM arguments to activate verbose
logging for the JVM garbage collector.

    -verbose:gc -Xloggc:/tmp/liferaygc1.log -XX:+PrintGCDetails 
    -XX:+PrintGCCause -XX:+PrintGCApplicationConcurrentTime 
    -XX:+PrintGCApplicationStoppedTime

You will need these logs to tune the JVM properly. 

**Note:** To ensure you do have sufficient debugging information if your JVM
encounters out of memory scenarios, you should consider adding this:

    -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/dumps

Garbage collector log files can grow huge. Arguments like the ones below rotate
the logging to a new file when the log file reaches a maximum size: 

```bash
-XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 
-XX:GCLogFileSize=50M
```

These arguments rotate the logs to as many as `10` log files, each with a `50M`
size limit. 
