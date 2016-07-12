package com.test.string;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestString {

	@Test
	public void testStringFormat() {
		/**
		 * %s 格式化字符串，
		 * %-10s ：从左到右可以显示10个字符，如果不够以空格补充
		 * %10s：从右到左可以显示10个字符，如果不够以空格补充
		 * 
		 * 
		 */
		String str = "sdfdf";
		String forstr = String.format("%10s", str);
		System.out.println(forstr);
	}
	
	@Test
	public void testStringUtilsContains() {
		Assert.assertEquals(true, StringUtils.contains("2,3,4", "2"));
		Assert.assertEquals(true, StringUtils.contains("2,3,4", "3"));
		Assert.assertEquals(true, StringUtils.contains("2,3,4", "4"));
		Assert.assertEquals(false, StringUtils.contains("2,3,4", "6"));
	}
	
	@Test
	public void testStringBuilderReverse() {
		System.out.println("abc");
		StringBuilder str = new StringBuilder("abc");
		System.out.println(str.reverse().toString());
	}
	
	@Test
	public void testBinarySearch() {
		String str = "123456";
		List<String> list = new ArrayList<String>();
		for (Character ch : str.toCharArray()) {
			list.add(ch.toString());
		}
		
		String str1 = "456";
		List<String> list1 = new ArrayList<String>();
		for (Character ch : str1.toCharArray()) {
			list1.add(ch.toString());
		}
		
		list.retainAll(list1);
		
		for (String string : list) {
			System.out.print(string+",");
		}
	}
	
	@Test
	public void testSubString() {
		String majorName = "aaaaaaaaa aaaaaaaaaaaaab";
		System.out.println(majorName.length() > 21 ? majorName.substring(0, 21).concat("...") : majorName);
	}
	
	@Test
	public void testSubString2() {
		String str = "Failfast invoke providers dubbo://10.173.29.147:20900/com.horizon.biz_c.offer.service.order.IOrderService?anyhost=true&application=mobile-api&check=false&default.client=netty&default.cluster=failfast&default.codec=dubbo&default.connections=1&default.delay=-1&default.loadbalance=consistenthash&default.serialization=kryo&default.server=netty&default.timeout=1000000&delay=-1&dubbo=2.8.3&generic=false&interface=com.horizon.biz_c.offer.service.order.IOrderService&methods=findOrderList,findOrder,existOrder,confirmSchool&organization=com.horizon&owner=leo&pid=21101&revision=2.0.0-SNAPSHOT&serialization=kryo&side=consumer&timestamp=1466073669473&version=2.0.0 ConsistentHashLoadBalance select from all providers [com.alibaba.dubbo.registry.integration.RegistryDirectory$InvokerDelegete@449ec91e, com.alibaba.dubbo.registry.integration.RegistryDirectory$InvokerDelegete@14a5f3c4] for service com.horizon.biz_c.offer.service.order.IOrderService method findOrder on consumer 10.171.71.218 use dubbo version 2.8.3, but no luck to perform the invocation. Last error is: Failed to invoke remote method: findOrder, provider: dubbo://10.173.29.147:20900/com.horizon.biz_c.offer.service.order.IOrderService?anyhost=true&application=mobile-api&check=false&default.client=netty&default.cluster=failfast&default.codec=dubbo&default.connections=1&default.delay=-1&default.loadbalance=consistenthash&default.serialization=kryo&default.server=netty&default.timeout=1000000&delay=-1&dubbo=2.8.3&generic=false&interface=com.horizon.biz_c.offer.service.order.IOrderService&methods=findOrderList,findOrder,existOrder,confirmSchool&organization=com.horizon&owner=leo&pid=21101&revision=2.0.0-SNAPSHOT&serialization=kryo&side=consumer&timestamp=1466073669473&version=2.0.0, cause: java.io.IOException: com.esotericsoftware.kryo.KryoException: Unable to find class: \u0001\u0002com.horizon.api.school.basic.Major\nSerialization trace:\nmajor (com.horizon.biz_c.offer.entity.order.vo.OrderVO)\nresult (com.horizon.core.common.ServiceResult)\njava.io.IOException: com.esotericsoftware.kryo.KryoException: Unable to find class: \u0001\u0002com.horizon.api.school.basic.Major\nSerialization trace:\nmajor (com.horizon.biz_c.offer.entity.order.vo.OrderVO)\nresult (com.horizon.core.common.ServiceResult)\n\tat com.alibaba.dubbo.common.serialize.support.kryo.KryoObjectInput.readObject(KryoObjectInput.java:127)\n\tat com.alibaba.dubbo.common.serialize.support.kryo.KryoObjectInput.readObject(KryoObjectInput.java:140)\n\tat com.alibaba.dubbo.common.serialize.support.kryo.KryoObjectInput.readObject(KryoObjectInput.java:151)\n\tat com.alibaba.dubbo.rpc.protocol.dubbo.DecodeableRpcResult.decode(DecodeableRpcResult.java:85)\n\tat com.alibaba.dubbo.rpc.protocol.dubbo.DecodeableRpcResult.decode(DecodeableRpcResult.java:117)\n\tat com.alibaba.dubbo.rpc.protocol.dubbo.DubboCodec.decodeBody(DubboCodec.java:100)\n\tat com.alibaba.dubbo.remoting.exchange.codec.ExchangeCodec.decode(ExchangeCodec.java:134)\n\tat com.alibaba.dubbo.remoting.exchange.codec.ExchangeCodec.decode(ExchangeCodec.java:95)\n\tat com.alibaba.dubbo.rpc.protocol.dubbo.DubboCountCodec.decode(DubboCountCodec.java:46)\n\tat com.alibaba.dubbo.remoting.transport.netty.NettyCodecAdapter$InternalDecoder.messageReceived(NettyCodecAdapter.java:134)\n\tat org.jboss.netty.channel.SimpleChannelUpstreamHandler.handleUpstream(SimpleChannelUpstreamHandler.java:80)\n\tat org.jboss.netty.channel.DefaultChannelPipeline.sendUpstream(DefaultChannelPipeline.java:564)\n\tat org.jboss.netty.channel.DefaultChannelPipeline.sendUpstream(DefaultChannelPipeline.java:559)\n\tat org.jboss.netty.channel.Channels.fireMessageReceived(Channels.java:274)\n\tat org.jboss.netty.channel.Channels.fireMessageReceived(Channels.java:261)\n\tat org.jboss.netty.channel.socket.nio.NioWorker.read(NioWorker.java:349)\n\tat org.jboss.netty.channel.socket.nio.NioWorker.processSelectedKeys(NioWorker.java:280)\n\tat org.jboss.netty.channel.socket.nio.NioWorker.run(NioWorker.java:200)\n\tat org.jboss.netty.util.ThreadRenamingRunnable.run(ThreadRenamingRunnable.java:108)\n\tat org.jboss.netty.util.internal.DeadLockProofWorker$1.run(DeadLockProofWorker.java:44)\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)\n\tat java.lang.Thread.run(Thread.java:744)\nCaused by: com.esotericsoftware.kryo.KryoException: Unable to find class: \u0001\u0002com.horizon.api.school.basic.Major\nSerialization trace:\nmajor (com.horizon.biz_c.offer.entity.order.vo.OrderVO)\nresult (com.horizon.core.common.ServiceResult)\n\tat com.esotericsoftware.kryo.util.DefaultClassResolver.readName(DefaultClassResolver.java:138)\n\tat com.esotericsoftware.kryo.util.DefaultClassResolver.readClass(DefaultClassResolver.java:115)\n\tat com.esotericsoftware.kryo.Kryo.readClass(Kryo.java:641)\n\tat com.esotericsoftware.kryo.serializers.ObjectField.read(ObjectField.java:99)\n\tat com.esotericsoftware.kryo.serializers.FieldSerializer.read(FieldSerializer.java:528)\n\tat com.esotericsoftware.kryo.Kryo.readClassAndObject(Kryo.java:761)\n\tat com.esotericsoftware.kryo.serializers.CollectionSerializer.read(CollectionSerializer.java:116)\n\tat com.esotericsoftware.kryo.serializers.CollectionSerializer.read(CollectionSerializer.java:22)\n\tat com.esotericsoftware.kryo.Kryo.readObject(Kryo.java:679)\n\tat com.esotericsoftware.kryo.serializers.ObjectField.read(ObjectField.java:106)\n\tat com.esotericsoftware.kryo.serializers.FieldSerializer.read(FieldSerializer.java:528)\n\tat com.esotericsoftware.kryo.Kryo.readClassAndObject(Kryo.java:761)\n\tat com.alibaba.dubbo.common.serialize.support.kryo.KryoObjectInput.readObject(KryoObjectInput.java:125)\n\t... 22 more\nCaused by: java.lang.ClassNotFoundException: \u0001\u0002com.horizon.api.school.basic.Major\n\tat org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1680)\n\tat org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1526)\n\tat java.lang.Class.forName0(Native Method)\n\tat java.lang.Class.forName(Class.java:270)\n\tat com.esotericsoftware.kryo.util.DefaultClassResolver.readName(DefaultClassResolver.java:136)\n\t... 34 more\n";
		if (str.contains("Failfast invoke providers")) {
			int dex = str.indexOf("dubbo");
			int dex2 = str.indexOf("/");
			int dex3 = str.indexOf("?");
			System.out.println(dex);
			System.out.println(dex2);
			System.out.println(dex3);
			String str2 = str.substring(dex2, dex3);
			System.out.println(str2);
			if (str2 != null && str2.lastIndexOf("/") != -1) {
				System.out.println(str2.substring(str2.lastIndexOf("/")+1));
			}
		}
		
	}
}
