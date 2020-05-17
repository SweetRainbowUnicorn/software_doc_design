package com.burynyk.yahoofinance.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.burynyk.yahoofinance.web.rest.TestUtil;

public class SavedChartTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SavedChart.class);
        SavedChart savedChart1 = new SavedChart();
        savedChart1.setId(1L);
        SavedChart savedChart2 = new SavedChart();
        savedChart2.setId(savedChart1.getId());
        assertThat(savedChart1).isEqualTo(savedChart2);
        savedChart2.setId(2L);
        assertThat(savedChart1).isNotEqualTo(savedChart2);
        savedChart1.setId(null);
        assertThat(savedChart1).isNotEqualTo(savedChart2);
    }
}
