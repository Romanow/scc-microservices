package ru.romanow.heisenbug.warehouse.web;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public abstract class BaseWebTest {

    @Autowired
    private ExceptionController exceptionController;

    @Rule
    public TestName testName = new TestName();

    @BeforeEach
    public void mockMvcInit() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder =
                standaloneSetup(controller())
                        .setControllerAdvice(exceptionController);

        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

    protected abstract Object controller();
}
