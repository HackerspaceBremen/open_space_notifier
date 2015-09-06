package de.hackerspacebremen.commands.resultobjects.v12;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hackerspacebremen.commands.resultobjects.v13.feeds.FeedV13;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public final class Feed {

	private String name;

	private String type;

	private String url;

	public Feed(final FeedV13 feed, final String name) {
		this.name = name;
		this.type = feed.getType();
		this.url = feed.getUrl();
	}
}
