package top.liuwenai.es;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class Main {
	
	private static final Logger LOG = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws UnknownHostException {
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9200));
		LOG.info("connected");
		Map<String, Object> json = new HashMap<>();
		json.put("user", "ysls");
		json.put("message", "cccca == s");
		IndexResponse response = client.prepareIndex("twitter"	, "tweet", "4")
				.setSource(json)
				.get();
		
		//close
		String _index = response.getIndex();
		String _type = response.getType();
		String _id = response.getIndex();
		long _version = response.getVersion();
		RestStatus status = response.status();
		
		LOG.info("index:"+_index+", type="+_type+", id="+_id+", version="+_version+", status="+ status);
		
		client.close();
	}
}
