package com.burynyk.yahoofinance.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.burynyk.yahoofinance.web.rest.TestUtil;

public class YahooUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(YahooUser.class);
        YahooUser yahooUser1 = new YahooUser();
        yahooUser1.setId(1L);
        YahooUser yahooUser2 = new YahooUser();
        yahooUser2.setId(yahooUser1.getId());
        assertThat(yahooUser1).isEqualTo(yahooUser2);
        yahooUser2.setId(2L);
        assertThat(yahooUser1).isNotEqualTo(yahooUser2);
        yahooUser1.setId(null);
        assertThat(yahooUser1).isNotEqualTo(yahooUser2);
    }
}
