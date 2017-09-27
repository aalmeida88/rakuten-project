package com.rakuten.product.context;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.rakuten.product.persistance.dao.ProductDAO;

@Configuration
@EnableMongoRepositories(basePackageClasses = ProductDAO.class)
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${persistance.db.name}")
	private String databaseName;

	@Value("${persistance.db.host}")
	private String databaseHost;

	@Value("${persistance.db.username}")
	private String databaseUsername;

	@Value("${persistance.db.password}")
	private String databasePassword;

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext());
		converter.setCustomConversions(customConversions());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return converter;
	}

	@Override
	public Mongo mongo() throws Exception {
		// TODO connect on startup slow first query...
		MongoCredential credential = MongoCredential.createScramSha1Credential(databaseUsername, databaseName,
				databasePassword.toCharArray());
		ServerAddress sa = new ServerAddress(databaseHost);
		MongoClient mongo = new MongoClient(sa, Arrays.asList(credential));

		// in production use replicaset by default you can read from secondaries
		// to balance load on database across all nodes.
		// mongo.setReadPreference(ReadPreference.secondaryPreferred());// read
		// from secondaries preferred
		// mongo.setWriteConcern(WriteConcern.REPLICAS_SAFE); // primary and at
		// least one replica
		return mongo;
	}

}
