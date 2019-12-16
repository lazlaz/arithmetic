package com.laz.arithmetic;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class FirstDemo {
	public static void main(String[] args) {

		Cluster cluster = Cluster.builder().addContactPoint("172.18.130.32")
				.withPort(9042).build();
		Metadata metadata = cluster.getMetadata();
		for (Host host : metadata.getAllHosts()) {
			System.out.println("------" + host.getAddress());
		}

		System.out.println("======================");

		for (KeyspaceMetadata keyspaceMetadata : metadata.getKeyspaces()) {
			System.out.println("--------" + keyspaceMetadata.getName());
		}
		Session session = cluster.connect();
		ResultSet rows = session.execute("SELECT cluster_name, listen_address FROM system.local");
		
		for (Row row2 : rows) {
			System.out.println(row2.getString("cluster_name"));
			System.out.println(row2.getString("cluster_name"));
		}
				
		cluster.close();
	}

}
