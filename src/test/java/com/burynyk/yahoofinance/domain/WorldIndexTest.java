package com.burynyk.yahoofinance.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.burynyk.yahoofinance.web.rest.TestUtil;

public class WorldIndexTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorldIndex.class);
        WorldIndex worldIndex1 = new WorldIndex();
        worldIndex1.setId(1L);
        WorldIndex worldIndex2 = new WorldIndex();
        worldIndex2.setId(worldIndex1.getId());
        assertThat(worldIndex1).isEqualTo(worldIndex2);
        worldIndex2.setId(2L);
        assertThat(worldIndex1).isNotEqualTo(worldIndex2);
        worldIndex1.setId(null);
        assertThat(worldIndex1).isNotEqualTo(worldIndex2);
    }
}
