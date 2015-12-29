package org.ow2.proactive.iaas.connector.cache;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.ow2.proactive.iaas.connector.fixtures.InfrastructureFixture;
import org.ow2.proactive.iaas.connector.model.Infrastructure;

public class InfrastructureCacheTest {
	private InfrastructureCache infrastructureCache;

	@Before
	public void init() {
		infrastructureCache = new InfrastructureCache();
	}

	@Test
	public void testConstructor() {
		assertThat(infrastructureCache.getSupportedInfrastructures(), is(not(nullValue())));
		assertThat(infrastructureCache.getSupportedInfrastructures().isEmpty(), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testImmutability() {
		((Map<String, Infrastructure>) infrastructureCache.getSupportedInfrastructures()).put("openstack",
				InfrastructureFixture.getInfrastructure("openstack", "endPoint", "userName", "credential"));
	}

	@Test
	public void testRegisterInfrastructure() {
		infrastructureCache.registerInfrastructure(
				InfrastructureFixture.getInfrastructure("openstack", "endPoint", "userName", "credential"));
		assertThat(infrastructureCache.getSupportedInfrastructures().size(), is(1));
		assertThat(infrastructureCache.getSupportedInfrastructures().get("openstack"),
				is(InfrastructureFixture.getInfrastructure("openstack", "endPoint", "userName", "credential")));
	}

	@Test
	public void testDeleteInfrastructure() {
		infrastructureCache.registerInfrastructure(
				InfrastructureFixture.getInfrastructure("openstack", "endPoint", "userName", "credential"));

		infrastructureCache.deleteInfrastructure(
				InfrastructureFixture.getInfrastructure("openstack", "endPoint", "userName", "credential"));

		assertThat(infrastructureCache.getSupportedInfrastructures(), is(not(nullValue())));
		assertThat(infrastructureCache.getSupportedInfrastructures().isEmpty(), is(true));
	}

}
