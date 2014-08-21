Flume-Json-Interceptor
======================

Overview
---------

This is a very basic Flume interceptor that extracts the fields from a json message and formats them as flume headers.

It has been tested on a netcat source that receives eventlog messages in json format from a windows server running nxlog. 
Data is subsequently sent to Elasticsearch.

I am not a java developper so the quality of the code is likely to be horrendous.

How to use
----------

The example here can be attach to a netcat source. Compile it( or simply download the jar from here), and copy the jar along with the json-simple jar https://json-simple.googlecode.com/files/json-simple-1.1.1.jar to the flume lib directory.

Afterwards edit the flume configuration to configure the interceptor:

        agent.sources.netcat.type = netcat
        agent.sources.netcat.bind = 0.0.0.0
        agent.sources.netcat.port = 5150
        agent.sources.netcat.interceptors = i1 i2 i3
        agent.sources.netcat.interceptors.i1.type = org.apache.flume.interceptor.HostInterceptor$Builder
        agent.sources.netcat.interceptors.i1.preserveExisting = false
        agent.sources.netcat.interceptors.i1.hostHeader = hostname
        agent.sources.netcat.interceptors.i2.type = org.apache.flume.interceptor.TimestampInterceptor$Builder
        agent.sources.netcat.interceptors.i3.type = com.flumetest.JsonInterceptor$Builder
        agent.sources.netcat.max-line-length = 524288
        agent.sources.netcat.ack-every-event = False

