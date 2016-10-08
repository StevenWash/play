package com.bstek.dorado.sample;

import org.bsdn.biki.SimpleLinkBuilder;

public class DefaultLinkBuilder extends SimpleLinkBuilder {

	@Override
	public String buildTopicLink(String space, String topic, String anchor) {
		return anchor;
	}

	@Override
	public String buildAttachmentLink(String space, String topic,
			String attachment) {
		return attachment;
	}

	@Override
	public String buildImageLink(String space, String topic, String image) {
		return image;
	}

	@Override
	public String buildCustomLink(String value) {
		return value;
	}

}
