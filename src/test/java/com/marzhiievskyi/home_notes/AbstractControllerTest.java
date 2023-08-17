package com.marzhiievskyi.home_notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@EnableTestcontainers
@SpringBootTest
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, config = @SqlConfig(encoding = "UTF-8"))// TODO add classpath for insert data's into table for test
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }
}
