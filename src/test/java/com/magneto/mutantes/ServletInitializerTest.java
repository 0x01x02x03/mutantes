package com.magneto.mutantes;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.builder.SpringApplicationBuilder;

class ServletInitializerTest {

    @Test
    void configure_ShouldSetSourcesToMutantesApplication() {
        ServletInitializer servletInitializer = new ServletInitializer();
        SpringApplicationBuilder builder = mock(SpringApplicationBuilder.class);

        when(builder.sources(MutantesApplication.class)).thenReturn(builder);

        SpringApplicationBuilder result = servletInitializer.configure(builder);

        verify(builder).sources(MutantesApplication.class);
        assertSame(builder, result);
    }
}
